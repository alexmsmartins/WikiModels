/**
 * A MathEditor for use in the extJS library.
 * 
 * @param textBox
 * @param c2p
 */
function MathEditor(textBox, c2p) {
	if (!Ext.isGecko) {
		this.tryAnyway = false;
		if(!confirm( 
				'This Math Editor only works on the Mozilla Firefox web browser.\n' +
				'Would you like to try it anyway?')) {
			return;
		}
	}
	
	this._textBox = new Ext.form.TextArea({applyTo:textBox, hidden:false, selectOnFocus:true, validator:function(xml){
		var ret = MathEditor.prototype.guiParseXML(xml, true/* hide popup */);
		return ret.error || true/* valid */;
	} ,growMin: 400});
	// Need to re-set the width too 100% since Ext.form.TextArea does something to it. 
	textBox.style.width='100%';
	// Clear padding. otherwise the up/down scrollbar is only partially visible.
	textBox.style.padding=0;
	// When textarea is hidden, don't use it to draw the box around the editor.
	this._textBox.getEl().setVisibilityMode(Ext.Element.DISPLAY);

	try { //If an error occurs, re-show the text box and hide the editor
		
		this.visual = this._textBox.getEl().wrap( {
			cls : "math-editor",
			cn : [ {/*toolbar*/}, {/*render*/}, {/*statusbar*/} ]
		});

		var toolbarDiv = this.visual.dom.firstChild;
		var statusDiv = this.visual.dom.firstChild.nextSibling;
		var div = statusDiv.nextSibling;
		
		this.canvas = Ext.get(div);
		//this.canvas.on("contextmenu", this.contextMenuHandler, this);
		this.statusBar = new Ext.StatusBar( {
			//statusAlign: 'right', //Only the text is right-aligned. buttons aren't flipped
				renderTo : statusDiv
			});
		// Set status bar loading to true (and we'll have progress show up)
		this.statusBar.showBusy("Loading Initial XML");

		
		c2p = MathUtil.getXsl("xsl/c2p.xsl");
		var previewXslt = MathUtil.getXsl("xsl/preview.xsl");
		var xml = textBox.value;
		var doc = this.guiParseXML(xml).doc || MathUtil.parseFromString('<math xmlns="http://www.w3.org/1998/Math/MathML" />');
	
	
		// Update status
		this.statusBar.showBusy("Creating Main Editing Area");
	
		var model = new Model("test", doc);
		this.model = model;
		this.render = new MathRender(model, c2p, div, previewXslt, this, doc);
		this._mathInput = new MathInput(this, this.render, div);
		this._mathInput._position.registerListener("editor", this);
		this.model.registerListener("editor", this);
		
		//Add mouse handlers onto the rendered MathML
		var el = Ext.get(div);
		el.on('mousedown', this._mathInput.onmousedown, this._mathInput);
		el.on('mouseup', this._mathInput.onmouseup, this._mathInput);
		el.on('click', this._mathInput.onclick, this._mathInput);
		el.on('dblclick', this._mathInput.ondoubleclick, this._mathInput);
		

		// Add undo/redo information
		this._undoStack = [ /*this.model.root.clone() added when we call notifyModelUpdated*/];
		this._redoStack = [];

		// Update status
		this.statusBar.showBusy("Loading Toolbar");

		// Create a toolbar and then attach it to the editor.
		Ext.QuickTips.init();
		var tb = new Ext.Toolbar( {
			renderTo : toolbarDiv
		});
		this.actions = {};

		
		var panel = new Ext.form.FormPanel( {
			applyTo : this.visual,
			width : '100%',
			//height : 200,
			autoHeight: true,
			layout : 'anchor',
			items : [div, this._textBox]
		});
		
		// Undo Action
		this.actions.undo = new Ext.Action( {
			text : "Undo",
			tooltip : {
				title : "Undo (Ctrl+Z)",
				text : "Undo the most recent change"
			},
			iconCls : 'x-edit-undo',
			handler : this.undoHandler,
			scope : this
		});
		this.actions.redo = new Ext.Action( {
			text : "Redo",
			tooltip : {
				title : "Redo (Ctrl+Y)",
				text : "Redo the most recently undone change"
			},
			iconCls : 'x-edit-redo',
			handler : this.redoHandler,
			scope : this
		});

		this.actions.source = new Ext.Toolbar.Button( {
			tooltip : {
				title : "MathML Source (Ctrl+E)",
				text : "View and edit the MathML Source"
			},
			iconCls : 'x-edit-source',
			enableToggle : true,

			handler : this.toggleSourceHandler,
			scope : this
		});
		this.actions.preview = new Ext.Action( {
			tooltip : {
				title : "Preview the MathML",
				text : "View a preview of what the MathML will look like"
			},
			iconCls : 'x-edit-preview',
			enableToggle : true,

			handler : this.togglePreviewHandler,
			scope : this
		});
		this.actions.selectAll = new Ext.Action( {
			tooltip : {
				title : "Select All (Ctrl+A)",
				text : "Selects all Math in the editor"
			},
			//iconCls : 'x-edit-selectall',
			handler : this.selectAllHandler,
			scope : this
		});


		// Add keyboard shortcuts
		// We have a helper function because some actions (like Toggling source
		// mode aren't really actions and need to be "executed" differently
		this.undoRedoKeyMap = new Ext.KeyMap(this.visual);
		function addCtrlKeyAction(key, act, keyMap) {
			//Unfortunately actions cannot be consistently called (some are toggle buttons)
			function wrapped(key, event) {
				var action = this; // set in the createDelegate
				// You can never do an OS undo (like in text boxes) bc it causes
				// problems like inserting text into an invisible cursor (since
				// the cursor is moved and not recreated)
				event.stopEvent();
				if (!(action.isDisabled && action.isDisabled() || action.disabled)) {//changed for Toolbar.Button
					// Toolbar.Button has a toggle() (view source)
					if (action.execute) {
						action.execute();
					} else {
						// "Press" the toggle button
						action.toggle();
						var handler = action.initialConfig.handler
								.createDelegate(action.initialConfig.scope);
						handler(action);
					}
				} else { //fall through to OS handling
				}
			};
			
			keyMap.on({key:key,ctrl:true}, wrapped.createDelegate(act), this);
		}
		var editSelectAllKeyMap = new Ext.KeyMap(this.visual);
		
		addCtrlKeyAction("Z", this.actions.undo, this.undoRedoKeyMap);
		addCtrlKeyAction("Y", this.actions.redo, this.undoRedoKeyMap);
		addCtrlKeyAction("A", this.actions.selectAll, this.undoRedoKeyMap);
		addCtrlKeyAction("E", this.actions.source, editSelectAllKeyMap);

		// Create the context menu
		this.contextMenu = new Ext.menu.Menu( {
			items : [ this.actions.undo, this.actions.redo, '-']
		});

		// Add the Main buttons
		function removeText(action) {
			var button = new Ext.Toolbar.Button(action);
			delete button.text;
			return button;
		}

		this.statusBar.add(removeText(this.actions.undo));
		this.statusBar.add(removeText(this.actions.redo));
		this.statusBar.add(removeText(this.actions.preview));
		this.statusBar.add('-');
		// this.statusBar.add('->');
		this.statusBar.add(this.actions.source);

		// Disable all the buttons
		for ( var id in this.actions) {
			this.actions[id].setDisabled(true);
		}
		this.actions.source.setDisabled(false);
		this.actions.selectAll.setDisabled(false); // Should always enable

		
		// Create Buttons for all of the operations
		this.templateActions = {};
		for ( var tempId in MathEditor_Templates) {
			var temp = MathEditor_Templates[tempId];

			// capitalize 1st character by default
			var cap = tempId.substring(0, 1).toUpperCase()
					+ tempId.substring(1);
			var title = temp.title ? temp.title : cap;
			var keyboard = temp.shortcut ? temp.shortcut : tempId;

			var tempAction = new Ext.menu.Item( {
				//text: xmlStr + " " + tempId,
				text : '<div style="text-align:right"><span style="float:left;">' + title
						+ '</span><span class="shortcut">' + keyboard
						+ '</span></div>',
				handler : function() {
					var model = this.editor.model;
					var position = this.editor._mathInput._position;
					var currentNode = position.getCurrentNode();
					var mathNode = model.buildMathNode(this.tempId);
					currentNode.getParent().replaceChildWith(currentNode,
							mathNode);
				},
				scope : {
					editor : this,
					tempId : tempId
				}
			});
			this.templateActions[tempId] = tempAction;
		}

		// Custom category menus:
		var customCategories = {
			//
			greek:new MathGreekMenu({handler : function(palette, sel) {
					if(palette.value) {
						var letter = palette.value.symbol;
						var input = this._mathInput._cursor;
						// For some reason input.selectionStart may throw an
						// error
						var start = 0;
						var end = 0;
						try {
							start = input.selectionStart;
							end = input.selectionEnd;
						} catch(e) {}
						
						var before = input.value.substring(0, start);
						var after = input.value.substring(end);
						input.value = before + letter + after;
						this._mathInput.handleReturn();
					}
				},
				scope : this
			}),
			classical : {
				items : [
						new Ext.menu.Item( {
							text : "Trigonometric Functions",
							menu : {
								items : [ this.templateActions["sin"],
										this.templateActions["cos"],
										this.templateActions["tan"],
										this.templateActions["sec"],
										this.templateActions["csc"],
										this.templateActions["cot"] ]
							}
						}),
						new Ext.menu.Item( {
							text : "Hyperbolic Functions",
							menu : {
								items : [ this.templateActions["sinh"],
										this.templateActions["cosh"],
										this.templateActions["tanh"],
										this.templateActions["sech"],
										this.templateActions["csch"] ]
							}
						}),
						new Ext.menu.Item( {
							text : "Arc Trigonometric Functions",
							menu : {
								items : [ this.templateActions["arcsin"],
										this.templateActions["arccos"],
										this.templateActions["arctan"],
										this.templateActions["arcsec"],
										this.templateActions["arccsc"],
										this.templateActions["arccot"] ]
							}
						}),
						new Ext.menu.Item( {
							text : "Arc Hyperbolic Functions",
							menu : {
								items : [ this.templateActions["arcsinh"],
										this.templateActions["arccosh"],
										this.templateActions["arctanh"],
										this.templateActions["arcsech"],
										this.templateActions["arccsch"] ]
							}
						}), '-', this.templateActions["exp"],
						this.templateActions["ln"], this.templateActions["log"],
		
				]
			},
			
			constants : {
				items:[ new Ext.menu.Item( {
					text : "Sets",
					menu : {
						items : [ this.templateActions["emptyset"],
								this.templateActions["integers"],
								this.templateActions["reals"],
								this.templateActions["rationals"],
								this.templateActions["naturalnumbers"],
								this.templateActions["complexes"],
								this.templateActions["primes"] ]
					}
				}),
				new Ext.menu.Item( {
					text : "Values",
					menu : {
						items : [ this.templateActions["exponentiale"],
								this.templateActions["imaginaryi"],
								this.templateActions["notanumber"],
								this.templateActions["pi"],
								this.templateActions["eulergamma"],
								this.templateActions["infinity"] ]
					}
				}),
				'-',
				this.templateActions["true"],
				this.templateActions["false"] 
				]
			}
		};


		this.categoryActions = [];
		this.categoryReturnTypes = [];
		for ( var catId = 0; catId < MathCategories.length; catId++) {
			var cat = MathCategories[catId];
			// Map template id's to actions
			var catReturnTypes = {}; // returnType -> true
			
			// Load up the catReturnTypes regardless if it's a custom menu
			var items = [];
			for ( var tempIndex = 0; tempIndex < cat.templates.length; tempIndex++) {
				var tid = cat.templates[tempIndex];
				if (this.templateActions[tid]) {
					
					//Custom build greek menu
					if("greek" == cat.custom) {
						var p = customCategories["greek"];
						p.palette.colors.push({name:tid, symbol:MathEditor_Templates[tid].symbol});
					}
					
					items.push(this.templateActions[tid]);
					var retType = MathEditor_Templates[tid].returnType;
					catReturnTypes[retType] = true;
				} else {
					getConsole().log(
							"Could not find template for category:" + tid);
				}
			}
			var menu;
			if(cat.custom) {
				menu = customCategories[cat.custom];
				getConsole().log("Custom Menu:");
				getConsole().dir(menu);
			} else {
				menu = {
						items : items
					};
			}

			var catButton = new Ext.Toolbar.MenuButton( {
				text : cat.button,
				tooltip : {
					title : cat.name,
					text : "Operations that have to do with <b>" + cat.name
							+ "</b>"
				},
				menu : menu,
				handler : function(b) {
					b.showMenu();
				},
				scope:this
			});

			catButton.showMenu = catButton.showMenu.createInterceptor(function() { 
				//Re-highlight the selection (removed by cursor.onblur)
				var sel = this._mathInput._position._currentNode;
				this._mathInput.notifySelectionChanged2(sel);
			}, this);
			this.categoryReturnTypes.push(catReturnTypes);
			this.categoryActions.push(catButton);
			tb.add(catButton);
		}

		//Update status
		this.statusBar.showBusy("Refreshing...");

		this.model.refresh(); // calls notifyModelUpdated
		// Move the cursor
		// Currently, the cursor isn't shown, so cause it to be shown by
		// focusing on the root
		this._mathInput._position.moveTo(this.model.root.getChildren()[0], false);
		
		//When the window resizes, set the textarea's width to be the width of the toolbar.
		this.resizeTextarea = function(width,height) {
			// Subtract 20 to miss the bottom scrollbar
			var h = Math.max(100, height - this._textBox.getEl().getTop());
			this._textBox.getEl().setHeight(h - 20);
		}
		Ext.EventManager.onWindowResize(this.resizeTextarea, {_textBox:this._textBox});

		this._textBox.getEl().setVisible(false);

	} catch (e) {
		//Re-show the textbox
		Ext.get(textBox).show();

		getConsole().log("DUMP OF DOC!!! Error, and document:");
		getConsole().dir(e);
		getConsole().dirxml(doc.documentElement);
		throw e;
	}

};

MathEditor.prototype.notifyModelUpdated = function(newMathNode) {
	//Add to the undo stack and clear the redo stack (if NOT triggered by undo)
	if (!this._ignoreModelChanges) {
		var root = this.model.root;
		assert(root, "No root!!!");
		var clone = root.clone();
		this._undoStack.push(clone);
		this._redoStack.length = 0;
	}

	//Update the undo/redo buttons
	this.actions.undo.setDisabled(this._undoStack.length == 1);
	this.actions.redo.setDisabled(this._redoStack.length == 0);

	// Update the preview button
	var dom = MathType.toDom(this.render.doc, this.model.root, MathRender.toDomFilter);
	var disablePrev = MathUtil.xpathNode(
			"//mmled:block[not(@extra) and not(../@__optional) and not(@__optional)]",
			dom);
	this.actions.preview.setDisabled(disablePrev);

	// Update the textarea with the source.
	var xml = MathUtil.xmlToString(dom);
	this._textBox.setValue(xml);

	// Update the toggle source button if needed
	this.actions.source.enable();

};

MathEditor.prototype.notifySelectionChanged = function(newMathNode) {
	assert(!newMathNode || newMathNode.getParent(), "SHould ALWAYS have a parent");
	
	// expectedType is the expected type of the selected node
	var expectedType = newMathNode && newMathNode.getParent() ? newMathNode.getParent().getTypeOfChild(
			newMathNode).base : "any";

	// Update Categories
	for ( var catId = 0; catId < this.categoryActions.length; catId++) {
		var action = this.categoryActions[catId];
		// If no node is selected, then really disable it.
		// Otherwise, if the returnTypes don't match, then just unadvise (grey
		// it out)
		if (newMathNode) {
			var amenable = expectedType == "any"
					|| this.categoryReturnTypes[catId]["any"]
					|| this.categoryReturnTypes[catId][expectedType];
			action.setDisabled(false);
			if (amenable) {
				action.removeClass("unadvised");
			} else {
				action.addClass("unadvised");
			}
		} else {
			action.setDisabled(true);
		}
	}

	//Update individual templates
	for ( var tid in this.templateActions) {
		var action = this.templateActions[tid];
		var retType = MathEditor_Templates[tid].returnType;
		var disabled;
		if (newMathNode) {
			var amenable = expectedType == "any" || retType == "any"
					|| expectedType == retType;
			action.setDisabled(false);
			if (amenable) {
				action.removeClass("unadvised");
			} else {
				action.addClass("unadvised");
			}
		} else {
			action.setDisabled(true);
		}
	}
};

MathEditor.prototype.notifyCursorMoved = function(mathNode, isAfter) {
	//Update select all (if newMathNode is not null, then be able to call selectAll
	this.actions.selectAll.setDisabled(!mathNode);

	// Update the Status Bar
	// this.statusBar.setStatus()
	this.statusBar.clearStatus();
	this.statusBar.setText("Path:"
			+ (mathNode ? this.getStatusBarSelectionPath(mathNode) : " None"));
};

MathEditor.prototype.getCurrentNode = function() {
	return this._mathInput._position.getCurrentNode();
};

MathEditor.prototype.guiParseXML = function(str, hidePopup) {
	function popupOrReturn(msg) {
		if(!hidePopup) {
			Ext.MessageBox.alert("XML Parsing Error", msg);
		}
		return {error:msg};
	}
	if(this.statusBar) {
		var oldStatus = this.statusBar.getText();
		this.statusBar.showBusy("Parsing XML...");
	}
	str = "<root xmlns='" + NS_MATHML + "' xmlns:m='" + NS_MATHML + "'>\n"
			+ str + "\n</root>";
	var doc = MathUtil.parseFromString(str);
	if(this.statusBar) {
		this.statusBar.clearStatus();
		this.statusBar.setStatus(oldStatus);
	}
	// Several cases to consider:
	// (1) Root is "parseerror"
	// (2) Root is "root" but no child elements
	// (3) Root is "root" and child is "math"
	// (4) Root is "root" and child is a math element
	// (5) Root is "root" and child is a non-mathml element
	var root = doc.documentElement;
	if ("parsererror" == MathUtil.localName(root)) {
		return popupOrReturn("The text contains Invalid XML. Please correct before continuing.");
	}
	
	var math;
	var singleMath;
	var childCount = MathUtil.xpathNumber("count(*)", root);
	if (childCount == 1) {
		singleMath = ("math" == MathUtil.localName(MathUtil.xpathNode("*[1]",
				root)));
	} else {
		singleMath = false;
	}

	if (childCount > 1) {
		return popupOrReturn("Can only have 1 root element. Please try again.");
	}
	if (singleMath) {
		math = MathUtil.xpathNode("*", root);
	} else {
		math = doc.createElementNS(NS_MATHML, "math");
		var child;
		while (child = MathUtil.xpathNode("*[1]", root)) {
			math.appendChild(child);
		}
	}

	doc.replaceChild(math, root);

// getConsole().log("Returning");
// getConsole().dirxml(doc);

	MathExceptions.cleanupMathML(doc.documentElement);

	return {doc:doc};
};
/**
 * Disables all Actions except the one passed in
 * 
 * @param {Ext.Action}
 *            action
 */
MathEditor.prototype.disableAllActionsExcept = function(action) {
	for ( var id in this.actions) {
		if (this.actions[id] != action) {
			this.actions[id].setDisabled(true);
		}
	}
};

MathEditor.prototype.undoHandler = function() {
	this._ignoreModelChanges = true;
	// Move the top of the undo stack to the redo stack
	var top = this._undoStack.pop();
	this._redoStack.push(top);
	var newMathNode = this._undoStack[this._undoStack.length - 1];
	newMathNode = newMathNode.getChildren()[0].clone();// clone the child
	// of mml:math

	var math = this.model.root;
	var root = math.getChildren()[0];
	math.replaceChildWith(root, newMathNode);
	this._ignoreModelChanges = false;
};
MathEditor.prototype.redoHandler = function() {
	this._ignoreModelChanges = true;
	// Move the top of the undo stack to the redo stack
	var top = this._redoStack.pop();
	this._undoStack.push(top);
	var newMathNode = this._undoStack[this._undoStack.length - 1];
	newMathNode = newMathNode.getChildren()[0].clone();// clone the child
	// of mml:math

	var math = this.model.root;
	var root = math.getChildren()[0];
	math.replaceChildWith(root, newMathNode);
	this._ignoreModelChanges = false;
};
MathEditor.prototype.selectAllHandler = function() {
	this._mathInput._position.moveTo(this.model.root.getChildren()[0], /*isAfter*/true);
	this._mathInput._position.select(true);
};
MathEditor.prototype.toggleSourceHandler = function(button) {
	if (!button || button.pressed) {//!button for the hotkey handling
		getConsole().log("Actual XML in the textarea:");
		getConsole().dirxml(MathType.toDom(this.render.doc, this.model.root));

		//First, try and save the changes (like input.onblur)
		this._mathInput.onblur();
		
		// Clear the position (and disables categories)
		this._mathInput._position.select(false);
		// Disable all buttons/actions
		this.disableAllActionsExcept(this.actions.source);
		// Hide the rendered text and display the textarea
		this.render.hide();
		this._textBox.getEl().setVisible(true);
		this._textBox.focus(10);
		// enable OS Ctrl+Z/Y handling and selectAll
		this.undoRedoKeyMap.disable();

		//Resize the textarea (just in case it hasn't been resized before)
		this.resizeTextarea.defer(10, this, [window.innerWidth, window.innerHeight]);

	} else {
		//Re-parse the XML if it hasn't changed
		var xml = this._textBox.getValue();
		var doc = this.guiParseXML(xml).doc;
		if (doc) {
			this._textBox.getEl().setVisible(false);
			this.model.replaceDoc(doc);
			this._mathInput._position.moveTo(this.model.root
					.getChildren()[0], /*isAfter*/true);
			this.undoRedoKeyMap.enable();
		} else {
			button.toggle(true);
		}
	}
};
MathEditor.prototype.togglePreviewHandler = function(button) {
	if (button.pressed) {
		// Clear the position (and disables categories)
		this._mathInput._position.moveTo(
				this.model.root.getChildren()[0], /*isAfter*/false);
		// Disable all actions except preview
		this.disableAllActionsExcept(this.actions.preview);

		var dom = MathType.toDom(this.render.doc, this.model.root, MathRender.toDomFilter);

		// run the transform:
		this.render.setDiv(this.render.transform(dom, /*usePreview*/
		true));

		// TODO: Just have the renderer have 2 divs.
		var el = Ext.get(this.render._dest);
		el.un('mousedown', this._mathInput.onmousedown, this._mathInput);
		el.un('mouseup', this._mathInput.onmouseup, this._mathInput);
		el.un('click', this._mathInput.onclick, this._mathInput);
		el.un('dblclick', this._mathInput.ondoubleclick, this._mathInput);

	} else {
		this._ignoreModelChanges = true; // don't update undo info
		this.model.refresh();
		this._ignoreModelChanges = false;

		var el = Ext.get(this.render._dest);
		el.on('mousedown', this._mathInput.onmousedown, this._mathInput);
		el.on('mouseup', this._mathInput.onmouseup, this._mathInput);
		el.on('click', this._mathInput.onclick, this._mathInput);
		el.on('dblclick', this._mathInput.ondoubleclick, this._mathInput);
	}
};

MathEditor.prototype.contextMenuHandler = function(e) {
	var tagName = MathUtil.localName(e.target);
	if ("input" != tagName && "button" != tagName) {
		var id = this._mathInput.findNearestId(e.target);
		this._mathInput.setPositionById(id, /*isAfter*/false, /*select?*/
		true);
		e.stopEvent();

		if (!this.contextMenu.el) {
			this.contextMenu.render();
		}
		var xy = e.getXY();
		this.contextMenu.showAt(xy);
	}
};

MathEditor.prototype.addAttributesToMenu = function(mathNode) {

	//remove previously added items to the context menu
	if (!this._itemsToRemoveFromContextMenu)
		this._itemsToRemoveFromContextMenu = [];
	for ( var x = 0; x < this._itemsToRemoveFromContextMenu.length; x++) {
		this.contextMenu.remove(this._itemsToRemoveFromContextMenu[x]);
	}
	this._itemsToRemoveFromContextMenu = [];

	if (mathNode.template.attributes) {
		for ( var id in mathNode.template.attributes) {
			var values = mathNode.template.attributes[id];

			var valItems = [];
			for ( var i = 0; i < values.length; i++) {
				var value = values[i];
				var checked = mathNode.attributes[id] == value;
				var valueItem = new Ext.menu.CheckItem( {
					text : value == "" ? "None (Optional)" : value,
					checked : checked,
					group : "group-" + id,
					checkHandler : function(btn) {
						if (btn.checked) {
					this.mathNode.attributes[this.id] = this.value;
				} else {
					delete this.mathNode.attributes[this.id];
				}
				this.model.refresh(this.mathNode);
			},
			scope : {
				mathNode : mathNode,
				model : this.model,
				id : id,
				value : value
			}
				});
				valItems.push(valueItem);
			}

			var item = new Ext.menu.Item( {
				text : "@" + id,
				iconCls : 'x-edit-source',
				menu : {
					items : valItems
				}
			});

			this.contextMenu.add(item);
			this._itemsToRemoveFromContextMenu.push(item);
		}
	}
}

MathEditor.prototype.getStatusBarSelectionPath = function(mathNode) {
	var myName;
	if("csymbol" == mathNode.template.name && mathNode.template.definitionURL) {
		var pound = mathNode.template.definitionURL.indexOf("#");
		if(pound >= 0) {
			myName = mathNode.template.definitionURL.substring(pound + 1);
		} else {
			myName = mathNode.template.definitionURL;
		}
	} else {
		myName = mathNode.template.name;
	}
	var parent = mathNode.getParent();
	assert(parent, "Should ALWAYS have a parent. weird...");
	myName = " / " + myName;
	if (this.model.root.getChildren()[0] != mathNode && parent) {
		return this.getStatusBarSelectionPath(parent) + myName;
	} else
		return myName;
}

// Taken from Ext.ColorPalette
MathGreekPalette = function(config){
    MathGreekPalette.superclass.constructor.call(this, config);
    this.addEvents(
        /**
	     * @event select
	     * Fires when a color is selected
	     * @param {ColorPalette} this
	     * @param {String} color The 6-digit color hex code (without the # symbol)
	     */
        'select'
    );

    if(this.handler){
        this.on("select", this.handler, this.scope, true);
    }
};
Ext.extend(MathGreekPalette, Ext.Component, {
	/**
	 * @cfg {String} tpl An existing XTemplate instance to be used in place of the default template for rendering the component.
	 */
    /**
	 * @cfg {String} itemCls The CSS class to apply to the containing element
	 *      (defaults to "x-color-palette")
	 */
    itemCls : "x-color-palette",
    /**
     * @cfg {String} value
     * The initial color to highlight (should be a valid 6-digit color hex code without the # symbol).  Note that
     * the hex codes are case-sensitive.
     */
    value : null,
    clickEvent:'click',
    // private
    ctype: "MathGreekPalette",

    /**
     * @cfg {Boolean} allowReselect If set to true then reselecting a color that is already selected fires the {@link #select} event
     */
    allowReselect : false,

    colors : [],

    // private
    onRender : function(container, position){
        var t = this.tpl || new Ext.XTemplate(
            '<tpl for="."><a href="#" class="color-{name}" hidefocus="on"><span unselectable="on" title="{name}">{symbol}</span></a></tpl>'
        );
        var el = document.createElement("div");
        el.id = this.getId();
        el.className = this.itemCls;
        t.overwrite(el, this.colors);
        container.dom.insertBefore(el, position);
        this.el = Ext.get(el);
        this.el.on(this.clickEvent, this.handleClick,  this, {delegate: "a"});
        if(this.clickEvent != 'click'){
            this.el.on('click', Ext.emptyFn,  this, {delegate: "a", preventDefault:true});
        }
    },

    // private
    afterRender : function(){
        MathGreekPalette.superclass.afterRender.call(this);
        if(this.value){
            var s = this.value;
            this.value = null;
            this.select(s);
        }
    },

    // private
    handleClick : function(e, t){
        e.preventDefault();
        if(!this.disabled){
            var c = t.className.match(/(?:^|\s)color-(.*)(?:\s|$)/)[1];
            for(var i=0;i<this.colors.length;i++) {
            	if(this.colors[i].name == c) {
            		this.select(this.colors[i]);
            	}
            }
            //this.select(c);
        }
    },

    /**
     * Selects the specified color in the palette (fires the {@link #select} event)
     * @param {String} color A valid 6-digit color hex code (# will be stripped if included)
     */
    select : function(color){
        //color = color.replace("#", "");
        if(color != this.value || this.allowReselect){
            var el = this.el;
            if(this.value){
                //el.child("a.color-"+this.value.name).removeClass("x-color-palette-sel");
            }
            //el.child("a.color-"+color.name).addClass("x-color-palette-sel");
            this.value = color;
            this.fireEvent("select", this, color);
            this.value = null;
        }
    }
});

// Taken from Ext.menu.ColorItem
function MathGreekItem(config) {
    MathGreekItem.superclass.constructor.call(this, new MathGreekPalette(config), config);
    /**
	 * The MathGreekPalette object
	 * 
	 * @type MathGreekPalette
	 */
    this.palette = this.component;
    this.relayEvents(this.palette, ["select"]);
    if(this.selectHandler){
        this.on('select', this.selectHandler, this.scope);
    }
};
Ext.extend(MathGreekItem, Ext.menu.Adapter);

MathGreekMenu = function(config){
    MathGreekMenu.superclass.constructor.call(this, config);
    this.plain = true;
    var ci = new MathGreekItem(config);
    this.add(ci);
    /**
	 * The {@link Ext.ColorPalette} instance for this ColorMenu
	 * 
	 * @type ColorPalette
	 */
    this.palette = ci.palette;
    /**
	 * @event select
	 * @param {ColorPalette}
	 *            palette
	 * @param {String}
	 *            color
	 */
    this.relayEvents(ci, ["select"]);
};
Ext.extend(MathGreekMenu, Ext.menu.Menu, {
    //private
    beforeDestroy: function(){
        this.palette.destroy();
    }
});