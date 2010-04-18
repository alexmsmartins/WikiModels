/**
 * The Model maintains a mapping between Content MathML Nodes and MathNodes. It
 * is also in charge of all DOM Manipulations (Adding/Removing nodes)
 * 
 * @param {String}
 *            idPrefix
 * @param {Document}
 *            optXMLDocument
 * @constructor
 */
function Model(idPrefix, xmlDocument) {
	this._listeners = [];// Listeners that receive update notifications
	// this._listenersLookup = {};//For debugging, we store their id as well

	//Run type inference on the Document.
	// Also, selects the proper child if there is a mml:semantics element
	this.replaceDoc(xmlDocument);
};

Model.prototype.registerListener = function(id, listener) {
	assert(listener.notifyModelUpdated,
			"Model listeners must implement notifyModelUpdated");
	// this._listenersLookup[id] = listener;
	this._listeners.push(listener);
};

/**
 * Notifies all listeners that the Content MathML has changed.
 * 
 * @param {MathNode}
 *            mathNode The node that was changed.
 * @param {MathNode}
 *            nextChildToSelect The next MathNode the cursor should point to
 */
Model.prototype._update = function(mathNode, nextChildToSelect) {
	if (this._disableUpdates) {
		return;
	}
	// Only notify nodes that are attached to the document.
	// if (mathNode.descendantOf(this.root)) {
	// Detached nodes will not call _update because only the root node has a ref
	// to the model.
	for ( var i = 0; i < this._listeners.length; i++) {
		try {
			this._listeners[i].notifyModelUpdated(mathNode, nextChildToSelect);
		} catch (e) {
			_dpExn(e, "Model.update"); /* throw e; */
		}
	}
	//} else {
	//	getConsole().log("Updating detatched node...");
	// }
};

/**
 * Builds a new MathNode given a template name ("plus", "sum", etc) and an
 * optional Content MathML Node. The Content MathML Node is given when inferring
 * types. When the user creates a new object the optCNode is not given and is
 * constructed by this method.
 * 
 * @param {String}
 *            templateName
 * @param {Element}
 *            optCNode
 * @type MathNode
 */
Model.prototype.buildMathNode = function(templateName, optCNode) {
	var template = MathEditor_Templates[templateName];
	template.__HACK_id = templateName;
	return Model._buildMathNode(template, optCNode);
};

/**
 * Used by the parser to see which templates are available.
 * 
 * @param templateName
 * @return Boolean
 */
Model.prototype.canBuildMathNode = function(templateName) {
	return MathEditor_Templates[templateName] ? true : false;
};

/**
 * Internal method because we use this same logic to build the root in Model's
 * constructor.
 * 
 * @param {MathType}
 *            template
 * @param {Element}
 *            optCNode
 * @type MathNode
 */
Model._buildMathNode = function(template, optCNode) {
	var mathNode;

	if (optCNode) {
		mathNode = MathType.get(optCNode);
	} else {
		mathNode = new MathNode( {
			template : template
		});
		MathType.makeChildren(mathNode, /*optDomNode*/null);
	}
	return mathNode;
};

/**
 * If a parsing problem occurs, this method replaces the currently-edited node
 * with a block that has within it the exact text the user typed.
 * 
 * There are 3 possibilities: (1) The user was editing something that was not a
 * block. In that case, delete the node, and replace it with a block with their
 * input. (2) It was a block, but it's not the special "expandable block". In
 * this case, just change the block so it represents the syntax error. (3) If
 * it's the expandable block, we need to create a new node and move the
 * expandable.
 * 
 * @param {MathNode}
 *            mathNode
 * @param {String}
 *            str
 */
Model.prototype.setSyntaxError = function(mathNode, str) {
	var suppress = this._disableUpdates;
	this._disableUpdates = true;
	if (mathNode.isBlock()) {
		mathNode.text = str;
		this._disableUpdates = suppress;// restore
		this._update(mathNode, mathNode);
	} else {
		//It's not a block, so delete it. It will ALWAYS be replaced by a block
		var parent = mathNode.getParent();
		var block = parent.removeChild(mathNode);
		block.text = str;
		this._disableUpdates = suppress;// restore
		this._update(parent, block);
	}
};

/**
 * Replaces the current MathML Document with a new one. This is used by the
 * "View Source" button.
 * 
 * @param {Document}
 *            doc The new doc to use.
 */
Model.prototype.replaceDoc = function(doc) {
	if(this.root) {
		this.root.model = null;
	}
	this._disableUpdates = true;
	MathExceptions.cleanupMathML(doc.documentElement);

	var rootDom = doc.documentElement;
	// Children may be mml:semantics. If so, choose the correct child
	this.root = MathType.get(rootDom);
	if('semantics' == this.root.getChildren()[0].unknownName) {
		this.root = this.root.getChildren()[0];
	}
	if('annotation-xml' == this.root.getChildren()[0].unknownName) {
		this.root = this.root.getChildren()[0];
	}

	// Run type inference on the Document.
	this.root.model = this; // set the model so root can issue updates.
	this._disableUpdates = false;
	// notify everyone
	this._update(this.root, this.root.getChildren()[0]);
};

/**
 * Used by MathEditor to redraw once everything is loaded
 */
Model.prototype.refresh = function() {
	var root = this.root;
	this._update(root, root);
};
