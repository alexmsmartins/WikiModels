/**
 * Handles the abstract notion of a cursor. It should NOT know about keyboard
 * events, onclicks, etc.
 * 
 * @param {Model}
 *            model
 */
function MathPosition(model, startNode) {
	assert(startNode, "Should always have a starting node");
	this.model = model;
	this._currentNode = startNode;
	this._isSelected = false;
	this._isAfter = true; // By default.
	this._listeners = []; // need to preserve order
	// this._listenersLookup = {};

	model.registerListener("math-position", this);
};

MathPosition.prototype.registerListener = function(id, listener) {
	assert(listener.notifySelectionChanged,
			"Every listener needs to implement notifySelectionChanged");
	assert(listener.notifyCursorMoved,
			"Every listener needs to implement notifyCursorMoved");
	// this._listenersLookup[id] = listener;
	this._listeners.push(listener);// need to preserve order
};

/**
 * The cursor can be either before, after, or around the selected node. Most
 * things (like the toolbar) care only if it's around (selected)
 * 
 * @return true if the entire MathNode is selected.
 * @type Boolean
 */
MathPosition.prototype.isSelected = function() {
	return this._isSelected;
};
MathPosition.prototype.isAfter = function() {
	return this._isAfter;
};
MathPosition.prototype.getCurrentNode = function() {
	return this._currentNode;
};
/**
 * Whether or not to select the current node
 * 
 * @param {Boolean}
 *            bool
 */
MathPosition.prototype.select = function(bool) {
	if (this._isSelected != bool || this._currentNode != this._lastSelectedNode) {
		this._isSelected = bool;
		this._lastSelectedNode = this._currentNode;
		var newSelection = bool ? this._currentNode : null;
		for ( var id = 0; id < this._listeners.length; id++) {
			this._listeners[id].notifySelectionChanged(newSelection);
		}
	} else {
		//getConsole().log("No change in selection. skipping");
	}
};

MathPosition.prototype.moveTo = function(mathNode, isAfter) {
	if (this._currentNode != mathNode || this._isAfter != isAfter) {
		this._currentNode = mathNode;
		this._isAfter = isAfter;
		for ( var id = 0; id < this._listeners.length; id++) {
			this._listeners[id].notifyCursorMoved(mathNode, isAfter);
		}
	} else {
		//getConsole().log("No change in cursor. skipping");
	}
	this.select(MathExceptions.becomesAnInput(mathNode));
};

/**
 * Internal function that actually moves left or right
 * 
 * @param {Boolean}
 *            isLeft is true if we are moving left, or false if moving right
 */
MathPosition.prototype._moveLeftOrRight = function(isLeft) {
	var node = this._currentNode;
	var initialGoingUp = isLeft ? !this._isAfter : this._isAfter;
	var it = new NodeIterator(node, true, null/*no filter*/,
			true/* traverse down and up again */, initialGoingUp);
	var ret; // Object that contains a content MathML DOM node
	// Use a while loop because of expandables (like plus don't have a
	do {
		ret = isLeft ? it.previous() : it.next();
	} while (ret.node 
	// Skip expandables that are not rendered (like mml:plus)
			&& !document.getElementById(ret.node.id));
	// Also, in the "while", skip over expandable nodes (because we hide them in
	// the rendering)
	if (ret.node || ret.goingUp != initialGoingUp) {
		var newMathNode = ret.node;
		if(this.model.root == newMathNode) {
			newMathNode = this.model.root.getChildren()[0];
		}
		var isAfter = isLeft ? !ret.goingUp : ret.goingUp;
		this.moveTo(newMathNode, isAfter);
	} else {
		getConsole().log("Can't find a node to move to, so staying put.");
	}
};
/**
 * @return The new node after moving left (could be the same one)
 * @type MathNode
 */
MathPosition.prototype.moveLeft = function() {
	if (this._currentNode == null) {
		return null;
	}
	this._moveLeftOrRight(/*isLeft*/true);
};
/**
 * @return The new node after moving right (could be the same one)
 * @type MathNode
 */
MathPosition.prototype.moveRight = function() {
	this._moveLeftOrRight(/*isLeft*/false);
};

/**
 * Defined in Model. The cursor listens to structural changes so it can update
 * its currentNode (if the currentNode is no longer in the tree)
 */
MathPosition.prototype.notifyModelUpdated = function(mathNode,
		nextChildToSelect) {
	if (!this._currentNode) {
		getConsole().log("No currentNode set. Skipping");
		return;
	}
	
	if (this._currentNode.descendantOf(this.model.root) && !nextChildToSelect.text) {
		getConsole().log(
				"Still in the document (replaced something else). Skipping");
		return;
	}

	// Move the cursor to the updated node
	// TODO: be smart, move to the 1st block
	nextChildToSelect = MathExceptions
			.smartNextChildToSelect(nextChildToSelect);

	this.moveTo(nextChildToSelect, /*isAfter*/true);
};

MathPosition.prototype.selectLeft = function() {
	this._selectLeftOrRight(/*isLeft*/true);
};
MathPosition.prototype.selectRight = function() {
	this._selectLeftOrRight(/*isLeft*/false);
};
MathPosition.prototype._selectLeftOrRight = function(isLeft) {
	var current = this.getCurrentNode();
	var isAfter = this._isAfter;
	if (isAfter == isLeft) {
		//Simple case, just select the node
		this.moveTo(current, !isAfter);
		this.select(true);
	} else {
		var parent = MathExceptions.getParent(current);
		if (parent != this.model.root) {
			this.moveTo(parent, /*isAfter*/!isLeft);
			this.select(true);
		}
	}
};
