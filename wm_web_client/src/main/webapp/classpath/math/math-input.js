/**
 * Handles everything about the cursor. MathRender creates clickable math, and
 * this class handles the clicks and data entry.
 * 
 * @param {Model}
 *            model
 * @param {Element}
 *            div The div that encompasses the editor (we attach a dangler)
 */
function MathInput(editor, render, div) {
	this.editor = editor; // Used for parseXML
	this.render = render;
	this.model = editor.model;
	this.model.registerListener("math-input", this);

	// Add a dangler on the div
	div._mathInput = this;
	this._div = div;
	// Since Math Nodes can't have onclicks, we add a large on on the div

	var el = Ext.get(div);
	
	// Use textarea for multiline Cut/Copy
	this._cursor = document.createElement('textarea');
	this.cursor = new Ext.form.TextField({applyTo:this._cursor,autoHeight:true, autoWidth:true,grow:true,preventScrollbars:true,selectOnFocus:true,enableKeyEvents:true
	, fieldClass:'c-cursor-nonexistant-fieldclass', focusClass:'c-cursor-nonexistant-focusClass'
	});
	
	this.cursor.on('keyup', this.onkeyup, this);
	this.cursor.on('blur', this.onblur, this);
	new Ext.KeyNav(this.cursor.getEl(), {
		'left':function(e) {
			var shift = e.shiftKey;
			var start = this.cursor.getEl().dom.selectionStart;
			var end = this.cursor.getEl().dom.selectionEnd;
			var hitBoundary = start == 0;
			if (hitBoundary || this._cursorInvisible /* Cut/Copy */) {
				if (!shift && (end == start || this._cursorInvisible)) {
					e.stopEvent();
					this.saveIfNeeded();
					// If we're working with a block/mml:ci
					// Then move the cursor to the left of the ci
					// before moving it to the previous node
					if (this._cursorCache) {
						this._position.moveTo(this._position
								.getCurrentNode(), /*isAfter*/false);
					}
					this._ignoreBlur = true;
					this._position.moveLeft();
					// Hack to preventDefault
					this.cursor.focus(/*selectText*/true, 10);
				} else if (shift) {
					e.stopEvent();
					this.saveIfNeeded();
					this._position.selectLeft();
				}
			}
		},
		'right':function(e) {
			var shift = e.shiftKey;
			var start = this.cursor.getEl().dom.selectionStart;
			var end = this.cursor.getEl().dom.selectionEnd;
			var hitBoundary = end == this.cursor.getValue().length;
			if (hitBoundary || this._cursorInvisible /* Cut/Copy */) {
				if (!shift && (end == start || this._cursorInvisible)) {
					e.stopEvent();
					this.saveIfNeeded();
					// If we're working with a block/mml:ci
					// Then move the cursor to the left of the ci
					// before moving it to the previous node
					if (this._cursorCache) {
						this._position.moveTo(this._position
								.getCurrentNode(), /*isAfter*/true);
					}
					this._ignoreBlur = true;
					this._position.moveRight();
					// Hack to preventDefault
					this.cursor.focus(/*selectText*/true,10);
				} else if (shift) {
					e.stopEvent();
					this.saveIfNeeded();
					this._position.selectRight();
				}
			}
		},
		//'del':this._keyBackspace, Handled by KeyMap
		'enter': function(e) {e.stopEvent(); this.handleReturn(e);},
		defaultEventAction:'stopPropagation',
		scope:this
	});
	new Ext.KeyMap(this.cursor.getEl(),{
		key:[Ext.EventObject.BACKSPACE, Ext.EventObject.DELETE],
		handler:this._keyBackspace,
		stopEvent:false,
		scope:this
	});
	function autoHeightSetup() {
		//this.growPad = 0;
		
		// taken from Ext.form.TextArea's onRender
        if(this.grow){
            this.textSizeEl = Ext.DomHelper.append(document.body, {
                tag: "pre", cls: "x-form-grow-sizer-matheditor"
            });
            if(this.preventScrollbars){
                this.el.setStyle("overflow", "hidden");
            }
            //this.el.setHeight(this.growMin);
        }
	};
	function autoHeightFunc() {
        if(!this.grow || !this.textSizeEl){
            return;
        }
        var el = this.el;
        var v = el.dom.value;
        var ts = this.textSizeEl;
        ts.innerHTML = '';
        ts.appendChild(document.createTextNode(v));
        v = ts.innerHTML;

        Ext.fly(ts).setWidth(this.el.getWidth());
        if(v.length < 1){
            v = "&#160;&#160;";
        }else{
            if(Ext.isIE){
                v = v.replace(/\n/g, '<p>&#160;</p>');
            }
            v += this.growAppend;
        }
        ts.innerHTML = v;
        // var h = Math.min(this.growMax, Math.max(ts.offsetHeight,
		// this.growMin)+this.growPad);
        var h = ts.offsetHeight;// + this.growPad;
        if(h != this.lastHeight){
            this.lastHeight = h;
            this.el.setHeight(h + 8);
            // this.fireEvent("autosize", this, h);
        }
	}
	autoHeightSetup.createDelegate(this.cursor) ();
	autoHeightFunc.createDelegate(this.cursor) ();
	this.cursor.on('render', autoHeightSetup);
	this.cursor.on('autosize', autoHeightFunc);
	MathUtil.addClassName(this._cursor, "cursor");

	this._cursorNode = null; // used to know if we need to restore the cursor
	this._cursorCache = null; // The cached presentation node
	this.setCursorValue('');

	// "math" is the root so pick the child
	this._cursorNode = this.model.root.getChildren()[0];
	// Store the position object
	this._position = new MathPosition(this.model, this._cursorNode);
	// MUST be done AFTER we initialize the cursor
	this._position.moveTo(this.model.root.getChildren()[0], /*isAfter*/
	true);
	this._position.registerListener("math-input", this);
};

/**
 * Both DELETE and BACKSPACE do the same thing
 */
MathInput.prototype._keyBackspace = function(key, e) {
	var start = this.cursor.getEl().dom.selectionStart;
	var end = this.cursor.getEl().dom.selectionEnd;
	var hitBoundary = (start == 0 && start == end);
	if (hitBoundary || (this._cursorInvisible && '' == this.cursor.getValue()) /* Cut/Copy */) {
		e.stopEvent();
		// this.saveIfNeeded();
		this.handleBackspace();
		getConsole().log("Stopping backspace");
	} else {
		getConsole().log("NOT Stopping backspace");
		this.cursor.grow = true;
	}
}

/**
 * Searches up the tree until it encounters an Element with a _mathInput
 * dangling off it.
 * 
 * @param {Element}
 *            domNode
 * @return MathInput or null if none could be found
 * @type MathInput
 */
MathInput.findNearestMathInput = function(domNode) {
	var ret;
	if (!domNode) {
		ret = null;
	} else if (domNode._mathInput) {
		ret = domNode._mathInput;
	} else if (domNode.ownerDocument == domNode) {
		//Give up
		ret = null;
	} else if ("div" == MathUtil.localName(domNode)
			|| "body" == MathUtil.localName(domNode)) {
		//Give up
		ret = null;
	} else {
		ret = MathInput.findNearestMathInput(domNode.parentNode);
	}
	assert(ret, "BUG: Must ALWAYS be able to find a _mathInput");
	// Cache the results for later
	domNode._mathInput = ret;
	return ret;
};

MathInput.prototype.findNearestId = function(domNode) {
	var ret;
	if (!domNode) {
		ret = null;
	} else if (Node.ELEMENT_NODE == domNode.nodeType
			&& domNode.getAttribute("id")) {
		ret = domNode.getAttribute("id");
	} else if (this._div == domNode) {
		//Give up
		ret = null;
	} else {
		ret = this.findNearestId(domNode.parentNode);
	}
	return ret;
};

MathInput.prototype._xmlSame = function() {
	var value = this._cursor.value || '';
	return (this._cursorOrigValue && value.valueOf() == this._cursorOrigValue
			.valueOf());
};

MathInput.prototype.setPositionById = function(id, isAfter, select) {
	//First save if the cursor has unsaved chages
	this.saveIfNeeded();

	// select this node
	var mathNode;
	if (!id) {
		mathNode = this.model.root.getChildren()[0];
	} else {
		mathNode = this.render.idLookup[id]
		// something else with an id
				|| this.model.root.getChildren()[0];
	}
	this._position.moveTo(mathNode, isAfter);
	// Should ALWAYS select when the cursor is an input.
	this._position.select(!this._cursorInvisible || select);
};

MathInput.prototype.notifyModelUpdated = function(mathNode, nextChildToSelect) {
	//Discard the cache because a refresh occurred
	this._cursorCache = null;
};

MathInput.prototype.notifySelectionChanged = function(newSelection, forBlurring) {
	//window.console.log("New selection = " + newSelection);
	if (this._oldSelection) {
		var pNodeOld = this._getPresentationNode(this._oldSelection);
		if (pNodeOld) {//it could have been refreshed
			MathUtil.removeClassName(pNodeOld, "highlighted");
			// pNodeOld.removeAttribute('mathbackground');
		}
	}
	//this._cursorOrigValue = "";
	if (!this._cursorCache) {
		var pNode = this._getPresentationNode(this._cursorNode);
		getConsole().assert(forBlurring || pNode,
				"Should always find a pNode. id=" + this._cursorNode.id);
		if (pNode && newSelection) {
			assert(newSelection == this._cursorNode,
					"newSelection and cursorMathnode should match");
			MathUtil.removeClassName(pNode, "cursor"); // can't have cursor and
			// highlighted
			// pNode.removeAttribute('mathbackground');

			MathUtil.addClassName(pNode, "highlighted");
			// pNode.setAttribute('mathbackground', MathUtil.FF2_HIGHLIGHT);

			// Because we're using native Copy/Paste, we need to inject the XML
			// into the cursor.
			try {
				var clone = MathType.toDom(this.editor.render.doc, newSelection,
						MathRender.toDomFilter);
				var cursorXML = '<math xmlns="' + NS_MATHML + '">\n'
						+ MathUtil.xmlToString(clone) + '\n</math>';
				this.setCursorValue(cursorXML);
				MathInput.focus(this._cursor);
				this._cursor.selectionStart = 0;
				this._cursor.selectionEnd = cursorXML.length + 1;
			} catch (e) {
				this.setCursorValue('');
			}

		} else if (pNode) {
			MathUtil.removeClassName(pNode, "highlighted");
			// pNode.removeAttribute('mathbackground');
		}
	}
	this._oldSelection = newSelection;
};

MathInput.prototype.notifyCursorMoved = function(mathNode, isAfter) {
	if (this._cursorNode) {
		var pNode = this._getPresentationNode(this._cursorNode);
		if (pNode) {
			MathUtil.removeClassName(pNode, "cursor");
			// pNode.removeAttribute('mathbackground');
		}
	}

	this._restoreCursorCache();
	this._attachCursorTo(mathNode, isAfter);

	var pNode = this._getPresentationNode(mathNode);
	if (pNode) {
		MathUtil.addClassName(pNode, "cursor");
		// pNode.setAttribute('mathbackground', MathUtil.FF2_CURSOR);
	}
};

/** Internal method to (un)blur the cursor */
MathInput.prototype.notifySelectionChanged2 = function(newSelection) {
	this.notifySelectionChanged(newSelection, true);
};

MathInput.prototype._restoreCursorCache = function() {
	if (this._cursorCache) {
		this._cursor.parentNode.replaceChild(this._cursorCache, this._cursor);
		this._cursorCache = null;
	}
};

MathInput.prototype.saveIfNeeded = function() {
	var str = this._cursor.value;
	// If it's just the original XML (For Cut/Copy) then ignore
	if (this._xmlSame()) {
		return;
	}
	// If there is unfinished work, now is the time to save it
	if (str != this._cursorOrigValue) {

		//Change the orig value before we forget
		this._cursorOrigValue = str;

		// Delete the node
		if ("" == str) {
			this._cursorNode.deleteMe();
		} else {

			//We only have a context if we're NOT a full-sized input box
			var optContext = !this._cursorCache && !this._position.isSelected() ? this._cursorNode
					: null;
			var placeholder = null;
			// Need to do some ugly threading...
			// First, save if cursor was before or after the node (it will be
			// replaced by a block)
			var isAfter = this._position.isAfter();
			if (optContext && !optContext.isBlock()) {
				placeholder = optContext.getParent().removeChild(optContext);
			}
			var parser = new MathParser(this.model, str, optContext, /*holeOnLeft*/
			isAfter);
			if(optContext && !parser._usedContext) {
				//Don't parse it. Just put the node back
				placeholder.getParent().replaceChildWith(placeholder, optContext);
				// Move the cursor back to either the beginning or end.
				this._position.moveTo(optContext, isAfter);
			} else
			// If there is a parse error, replace this node with a block with
			// the error in it
			if (parser.hasError() && "mtext" == this._cursorNode.template.name) {
				// If there is a parse error, and the node is a token,
				// then no actual parse error occured. Just update the text.
				this._cursorNode.setChildAsText(str);
			} else if (parser.hasError() && str.length > 1) {
				//Try and see if it's XML and parse it....
				var doc = this.editor.guiParseXML(str, /*hidePopup*/true).doc;
				var addedMathChild;
				if (doc
						&& (addedMathChild = MathUtil.xpathNode("*",
								doc.documentElement))) {
					getConsole().log("Added Math Child:");
					getConsole().dirxml(addedMathChild);
					var mathNode = MathType.get(addedMathChild);
					this._cursorNode.getParent().replaceChildWith(
							this._cursorNode, mathNode);
				} else {
					this.model.setSyntaxError(this._cursorNode, str);
					this.setCursorValue(str);
					getConsole().log("Parse error: " + parser._error);
				}
			} else if (parser.hasError() && str.length == 1) { //it was a unicode char
				var mathNode = this.model.buildMathNode("ci");
				mathNode.replaceChildWithText(mathNode.getChildren()[0], str);
				this._cursorNode.getParent().replaceChildWith(this._cursorNode,
						mathNode);
			} else if (placeholder != null) {
				//We used the context and need to replace placeholder w/ parsed stuff
				placeholder.getParent().replaceChildWith(placeholder,
						parser.getMathNode());
			} else {
				var mathNode = parser.getMathNode();
				// Even if no parse error occured, but we parse a simple number
				// or word, if the current node is a token, set the text to it.
				// That way mml:mtext isn't replaced with a mml:ci.
				// Just replace the text if it's a token node and we just parsed
				// text.
				if (this._cursorNode.template.isToken && mathNode.text) {
					//remove all the children and plop in the new one(s)
					this._cursorNode.setChildAsText(mathNode.text);
				} else if(this._cursorNode.getParent().getParent() && this._cursorNode.getParent().template.isToken) {
					this._cursorNode.getParent().getParent().replaceChildWith(
							this._cursorNode.getParent(), mathNode);
					this._position.moveTo(mathNode, isAfter);
				} else {
					this._cursorNode.getParent().replaceChildWith(
							this._cursorNode, mathNode);
				}
			}
		}
	}
};

MathInput.prototype._attachCursorTo = function(mathNode, isAfter) {
	assert(!this._cursorCache, "Should NOT have a cache at this point");
	var pNode = this._getPresentationNode(mathNode);
	assert(pNode, "Should always find a pNode to attach the cursor to "
			+ mathNode.template.name
			+ (mathNode.unknownName ? " unknownName=" + mathNode.unknownName
					: ""));
	if (!pNode) { //In case this bug shows up on the live site.
		// mathNode = this.model.root.getChildren()[0];
		// pNode = this._getPresentationNode(mathNode);
		return;
	}
	// If it's a block, ci, cn
	if (MathExceptions.becomesAnInput(mathNode)) {
		if(this._cursor.parentNode == pNode.parentNode) {
			this._cursor.parentNode.removeChild(this._cursor);
		}
		pNode.parentNode.replaceChild(this._cursor, pNode);
		this._cursorCache = pNode;
		this.setCursorValue(mathNode.text ? mathNode.text : '');
		if (mathNode.isBlock()) {
			// A block may contain unparsed text (as the firstNode)
			this._position.select(true);
		}
		if (!isAfter) {
			this._cursor.selectionStart = 0;
			this._cursor.selectionEnd = 0;
		}
		this._cursorInvisible = false;
		this.cursor.grow = true;
		this.cursor.autoHeight = true;

	} else {
		//Otherwise, just inject the cursor either before or after the node.
		// Actually, we'll need to inject either as the last child or the first
		// Special case if the cursor will be in a table (needs to go IN a
		// mml:mtd)
		if ("mtr" == MathUtil.localName(pNode)) {
			if (isAfter) {
				MathUtil.xpathNode("*[position()=last()]", pNode).appendChild(
						this._cursor);
			} else {
				MathUtil.xpathNode("*[1]", pNode).appendChild(this._cursor);
			}
		} else if (isAfter || !pNode.firstChild) {
			pNode.appendChild(this._cursor);
		} else {
			pNode.insertBefore(this._cursor, pNode.firstChild);
		}
		this.setCursorValue('');
		this._cursorInvisible = true;
		this.cursor.grow = false;
		this.cursor.autoHeight = false;
	}
	MathUtil.removeClassName(this._cursor, "cursor");
	MathUtil.removeClassName(this._cursor, "cursor-invisible");
	MathUtil.addClassName(this._cursor,
			this._cursorInvisible ? "cursor-invisible" : "cursor");
	this._cursorNode = mathNode;

	// Expand the cursor
	this.cursor.autoSize();
	var width = this._cursorInvisible ? 0 : (this._cursor.value ? this._cursor.value.length:1);
	// FF2 needs a non-zero width to show the cursor
	this._cursor.style.width = (!width) ? '2px' : (width + 'em');
	// When losing focus we re-render the MathML, but don't want to refocus on
	// the input.
	if(!this.ignoreFocus)
		MathInput.focus(this._cursor);
};

/**
 * @param {String}
 *            id
 * @type Element
 */
MathInput.prototype._getPresentationNode = function(mathNode) {
	var id = this.render.id(mathNode);
	return document.getElementById(id);
};

MathInput.onfocusBlock = function(event, input, id) {
	var mathInput = MathInput.findNearestMathInput(input);
	assert(mathInput, "Should always be able to find a MathRender");

	// Use model to look up by ID
	mathInput.setPositionById(id, /*isAfter?*/false, /*forceSelect?*/true);
};

/*
 * ----------------------------------------------------------------------------------------------------
 * Mouse handlers go here
 * ----------------------------------------------------------------------------------------------------
 */
MathInput.prototype.onmousedown = function(evt) {
	try {
		var node = evt.getTarget();
		if (!this._mousedownTarget) {
			this._mousedownTarget = node;
		}
	} catch (e) {
		_dpExn(e, "onmousedownHandler:");
	}//catch
};

MathInput.prototype.onmouseup = function(evt) {
	if(!this._mousedownTarget) {
		return;
	}
	try {
		var consumed = false;
		var node1 = this._mousedownTarget;
		var node2 = evt.getTarget();
		assert(node1, "Node1 must not be null");
		assert(node2, "Node2 must not be null");

		// Clear the target for invariants
		this._mousedownTarget = null;

		if (node1 == node2) {
			getConsole().log("Ignoring click on cursor");
		} else {
			// Find the common parent node (to select)
			var ary1 = [];
			var ary2 = [];

			do {
				ary1.push(node1);
				node1 = node1.parentNode;
			} while (node1 && Node.ELEMENT_NODE == node1.nodeType
					&& "div" != MathUtil.localName(node1));

			do {
				ary2.push(node2);
				node2 = node2.parentNode;
			} while (node2 && Node.ELEMENT_NODE == node2.nodeType
					&& "div" != MathUtil.localName(node2));

			if (!node1 || !node2) {
				//at least one was out of an editor. so do nothing
			} else if (node1 != this._div || node2 != this._div) {
				//selected between 2 different editors, so do nothing
			} else {
				//Find the common parent
				var common = this._div;
				assert(common, "Should ALWAYS be defined.");
				var maxDepth = Math.min(ary1.length, ary2.length);
				for ( var i = 1; i <= maxDepth; i++) {
					var n1 = ary1.pop();
					var n2 = ary2.pop();
					if (n1 == n2) {
						common = n1;
						assert(common, "Should ALWAYS be defined." + i);
					} else {
						break;
					}
				}

				var nearestId = (common != this._div) ? this
						.findNearestId(common) : this.model.root.id;
				this.setPositionById(nearestId, /*isAfter*/true, /*select?*/true);
				consumed = true;
			}
		}
		return !consumed;

	} catch (e) {
		_dpExn(e, "onmouseupHandler:");
		throw e;
	}//catch
};

MathInput.prototype.onclick = function(event, target, options, doubleClick) {
try{
	/*
	 * We could be clicking on one of the following: - Something in our editor -
	 * a pNode - a button - the oBody - another text box (input or search) -
	 * Somethine else (another editor, another link, etc)
	 * 
	 * First we update the input box (if there is one) The we find out if we
	 * clicked outside of the active editor (and set the active editor to null)
	 * Then, we let the handler continue
	 */
	// Now see if the click was within this editor.
	var node = target;
	// loop up until we hit an element
	if (Node.ELEMENT_NODE != node.nodeType) {
		node = node.parentNode;
	}
	assert(Node.ELEMENT_NODE == node.nodeType,
			"Should ALWAYS find a nearest element");

	// Ignore if the user clicked on an <input>
	var tagName = MathUtil.localName(node);
	if ("input" != tagName && "button" != tagName && "textarea" != tagName) {
		var id = this.findNearestId(node);
		if (id) {
			// 2 cases: if doubleclicked, select the entire node
			// Otherwise, move the cursor to the left of the node.
			if (doubleClick) {
				// dp("DOUBLECLICKING!!!!!");
				this.setPositionById(id, /*isAfter*/true, /*select?*/
				true);
			} else {
				// dp("SINGLECLICKING!!!");
				this.setPositionById(id, /*isAfter*/false, /*select?*/
				false);
			}
		} else {
			//The user clicked int the space to the right of existing math.
			// Move the cursor to the right of the whole equation
			this.setPositionById(/*id*/null, /*isAfter*/true, /*select?*/
			false);
		}
	}

	return true; // propagate to the next onclick handler
}catch(e){_dpExn(e,"onclick"); throw e; }
};

MathInput.prototype.ondoubleclick = function(event, target, options) {
	this.onclick(event, target, options, /*dblclick*/true);
};

/*
 * --------------------------------------------------------------------------
 * Handle the keypresses here
 * --------------------------------------------------------------------------
 */

/**
 * When a key is released, we care if a Cut/Copy/Paste was done
 */
MathInput.prototype.onkeyup = function(input, event) {
try{
	var keyCode = event.getKey();
	var shift = event.shiftKey;
	var mathInput = this;

	// If a delete or a cut occured, do the same as a backspace
	if (this._cursorInvisible && "" == input.getValue()
			&& "" != this._cursorOrigValue) {
		//this.saveIfNeeded();
		this.handleBackspace();
		return false;
	}

	// Before resizing, see if we pasted in MathML. If so, parse it and change
	// it.
	var statusBefore = this.editor.statusBar.getText();
	var trimmed = input.getValue().strip();
	if ('<' == trimmed.charAt(0)
			&& '>' == trimmed.charAt(trimmed.length - 1)) {
		//And it's not EXACTLY what the underlying XML is (for Cut/Copy)
		if (!this._xmlSame()) {
			this.editor.statusBar.showBusy("Parsing XML");
			getConsole().log("Parsing XML")
			// Try and parse it using the GUIParser, ignoring modal boxes
			var doc = this.editor.guiParseXML(trimmed, /*hidePopup*/true).doc;
			if (doc) {
				var addedMathChild = MathUtil.xpathNode("*",
						doc.documentElement);
				this.editor.statusBar.showBusy("Inferring types on XML");
				var mathNode = MathType.get(addedMathChild);
				this.editor.statusBar.clearStatus();
				this._cursorNode.getParent().replaceChildWith(
						this._cursorNode, mathNode);
				// Be sure to clear the input box afterwards
				this.setCursorValue('');
			}
			this.editor.statusBar.setText(statusBefore);
		} else {
			//ignore. don't parse, so return
			this.editor.statusBar.clearStatus();
			this.editor.statusBar.setText(statusBefore);
			return;
		}
	}
	this.editor.statusBar.clearStatus();
	this.editor.statusBar.setText(statusBefore);
	// See if we need to auto-resize the input box (when contents changed
	// but not XML)
	if (input.getValue() != this._cursorOrigValue) {
		this.cursor.grow = true;
		if (this._position.isSelected()) {
			//Hide the selected node since we are replacing it
			var pNode = this._getPresentationNode(this._cursorNode);
			if (pNode) {
				pNode.parentNode.replaceChild(this._cursor, pNode);
				this.cursor.focus();
				// Make sure we didn't highlight
				this.cursor.getEl().dom.selectionStart = this.cursor.getEl().dom.selectionEnd;
			}
		}
	}
}catch(e){_dpExn(e,"onkeyup"); throw e; }
};

/**
 * When the blinking cursor disappears (user clicked elsewhere) de-highlight the
 * currently selected node. Don't actually de-select because clicking the
 * toolbar (to insert something) would cause onblur to be called and disable
 * (since nothing was selected)
 * 
 * Also, since the invisible cursor stores the MathML for Copy/Paste,
 * de-highlighting is a visual clue for the user that pressing Ctrl+X/C/V will
 * have no effect.
 */
MathInput.prototype.onblur = function(input) {
	try{
	var mathInput = this;
	// Hack. Call the notify to hide the node instead of really removing the
	// selection. This way clicking the toolbar will still work
	if (mathInput._ignoreBlur) {
		mathInput._ignoreBlur = false;
		return;
	}
	// If the user clicks elsewhere we don't want to put focus back on the input box
	this.ignoreFocus = true;
	this.saveIfNeeded();
	this.ignoreFocus = false;
	// mathInput.notifySelectionChanged2(null);
	}catch(e){_dpExn(e,"onblur"); throw e; }
};

/**
 * If the node is selected and we're after it, then delete it
 */
MathInput.prototype.handleBackspace = function() {
	if (this._position.isSelected()) {
		var curr = this._position.getCurrentNode();
		var newCurr = curr.deleteMe();
		if(curr == newCurr) {
			//Put the original xml back...
			this.setCursorValue(this._cursorOrigValue);
		}
	} else {
		this._position.select(true);
	}
};

MathInput.prototype.handleReturn = function() {
	this.saveIfNeeded();
};

/**
 * Ensures the invariant that only the user can cause the new value at the
 * cursor location to be different from the original value
 */
MathInput.prototype.setCursorValue = function(str) {
	this._cursor.value = str;
	// Setting the value of an input may change the string a bit.
	this._cursorOrigValue = this._cursor.value;
}

/**
 * Helper that attempts to focus on the input element and silently fails
 */
MathInput.focus = function(input) {
	try {
		input.focus();
	} catch (e) {
	}
}