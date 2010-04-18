/**
 * Constructs a new renderer that updates when the model changes.
 * 
 * @param {MathModel}
 *            model
 * @param {Element}
 *            c2pXslt
 * @param {Element}
 *            divDestination
 * @constructor
 * @type MathRender
 */
function MathRender(model, c2pXslt, divDestination, previewXslt, editor, xmlDoc) {
	this._prefix = "math-editor-" + (MathRender._counter++) + "-";
	this._counter = 0; // for id's

	this.doc = xmlDoc; //used for calling MathType.toDom
	
	//Set the status bar
	this.editor = editor;
	
	// Create the transformer
	this._c2pTransformer = new XSLTProcessor();
	this._c2pTransformer.importStylesheet(c2pXslt);
	if (previewXslt) {
		this._previewTransformer = new XSLTProcessor();
		this._previewTransformer.importStylesheet(previewXslt);
	}
	this._dest = divDestination;
	this._dest.className = "math-editor-main";
	this._dest._mathRender = this; // Attach a MathRender for callbacks from
	// Input.

	this.idLookup = {};

	// Register a listener for structural updates.
	model.registerListener("renderer", this);

	// Call notifyModelUpdated once so we render the screen
	this.notifyModelUpdated(model.root, model.root.getChildren()[0]);
};

/**
 * Callback function defined in the Model. This notifies the renderer when the
 * model (Content MathML) changed.
 * 
 * @param {MathNode}
 *            mathNode
 */
MathRender.prototype.notifyModelUpdated = function(xxx, mathNode) {
	assert(mathNode, "Bug! Can't have a null mathNode");
	//Clear the idLookup
	this.idLookup = {};
	this.skipStaleCheck = true;
	
	//For now, just blindly refresh everything
	while(mathNode.getParent()) {
		mathNode = mathNode.getParent();
	}
	var domNode = MathType.toDom(this.doc, mathNode,
			MathRender.toDomWysiwygFilter, this.toDomWysiwyg.createDelegate(this));

	this.skipStaleCheck = false;
	try {
		var newMath = this.transform(domNode);
		this.setDiv(newMath);
	} catch (e) {
		_dpExn(e, "math-render transform error:");
	}//catch
};

/**
 * Used internally and by the toolbar generator to convert Content MathML to
 * Presentation MathML
 * 
 * @param {Element}
 *            domNode
 * @type {Element}
 */
MathRender.prototype.transform = function(domNode, usePreview) {
	//Clone the node and clean it up.
	var clone = domNode;

	if(this.editor)
		this.editor.statusBar.showBusy("Updating Main Editing Area...");
	// run the transform:
	if (usePreview && this._previewTransformer) {
		var fragment = this._previewTransformer.transformToFragment(clone,
				document);
	} else {
		var fragment = this._c2pTransformer
				.transformToFragment(clone, document);
	}
	var newMath = document.createElementNS(NS_MATHML, "math");
	newMath.appendChild(fragment);

	if(this.editor)
		this.editor.statusBar.clearStatus();
	return newMath;
};

/**
 * Method used by "View Source" that just hides the rendered HTML
 */
MathRender.prototype.hide = function() {
	while (this._dest.firstChild) {
		this._dest.removeChild(this._dest.firstChild);
	}
};

MathRender.prototype.setDiv = function(newMath) {
	// Clear out the destination DIV
	this.hide();
	// Append the transformed node
	this._dest.appendChild(newMath);
};

/**
 * Filter removes expandables and optionals
 */
MathRender.toDomFilter = function(mathNode) {
	var allChildrenAreBlocks = mathNode.getChildren().length > 0;
	mathNode.getChildren().forEach(function(child){
		if(!child.blockish) {
			allChildrenAreBlocks = false;
		}
	});
	var optional = mathNode.isOptional() || (mathNode.getParent() && mathNode.getParent().isOptional());
	var expandable = mathNode.isExpandable() || (mathNode.getParent() && mathNode.getParent().isExpandable());
	
	var ret = !(expandable || (optional && allChildrenAreBlocks && !mathNode.text));
	return ret;
};
MathRender.toDomWysiwygFilter = function(mathNode) {
	var hideExpandable = mathNode.isExpandable() && MathExceptions.validToContract.indexOf(mathNode.getParent().template.name) >= 0;
	return !hideExpandable;
};

MathRender.prototype.toDomWysiwyg = function(mathNode, domNode) {
	if('math' != mathNode.template.name && 'semantics' != mathNode.unknownName && 'annotation-xml' != mathNode.unknownName) {
		assert(mathNode.getParent(), "Should ALWAYS have a parent");
		assert(mathNode.getParent().__WAS_LOWLIMIT_UPLIMIT || this.idLookup[MathExceptions.getParent(mathNode).id], "Should ALWAYS have already registered the parent");
	}
	var id = this.id(mathNode); // keep the id cached (for debugging)
	domNode.setAttribute("id", id);

	if (mathNode.isOptional() || (mathNode.getParent() && mathNode.getParent().isOptional())) {
		domNode.setAttribute("__optional", "true");
	}
	if (mathNode.isExpandable() || (mathNode.getParent() && mathNode.getParent().isExpandable())) {
		domNode.setAttribute("extra", "expandable");
	}
	
	mathNode._debug = domNode;
};


/**
* A static counter to find bugs in swapping out models (when reparsing a
* document)
*/
MathRender._counter = 0;

/**
* Creates a new id (should be in MathRender)
* 
* @param prefix
*            optional prefix for the id for debugging-ish use
* @return A document-unique id
* @type String
*/
MathRender.prototype._freshId = function(prefix) {
	return this._prefix + (prefix ? prefix + "-" : "") + (this._counter++);
};


MathRender.prototype.id = function(mathNode) {
	var id = mathNode.id;
	if (!id) {
		id = this._freshId(mathNode.template.name);
		mathNode.id = id;
		this.idLookup[id] = mathNode;
	}
	if(!this.skipStaleCheck && !this.idLookup[id]) {
		getConsole().log("Woah! stale id!");
	}
	this.idLookup[id] = mathNode; // just to be safe
	return id;
}