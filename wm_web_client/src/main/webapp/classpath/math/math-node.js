/**
 * 
 * @param {MathModel}
 *            model
 * @param {String}
 *            id
 * @param {MathType}
 *            template
 * @return
 */
function MathNode(config) {
	this.childMap = {};
	this.unmatched = [];
	this.attributes = {};
	Ext.apply(this, config);
	if (!this.template.args) {
		this.template.args = [];
	}
};

MathNode.BLOCK_TEMPLATE_NAME = "block";

/**
 * Creates a deep clone of the current MathNode AND the associated DOM Node.
 * This is used by Undo/Redo.
 * 
 * @param {MathNode}
 *            optNewParent Mostly for recursive calls to clone, this is the new
 *            parent to set the clone to
 * @return A MathNode which is detached from a Model and can be re-cloned.
 * @type MathNode
 */
MathNode.prototype.clone = function(optNewParent) {
	var childMap = {};
	var unmatched = [];
	var clone = new MathNode(this);
	clone.parent = optNewParent;
	clone.childMap = childMap;
	clone.unmatched = unmatched;
	clone.model = null; // Let the model manage this.

	// Delete the id and _debug (if exists)
	delete clone.id;
	delete clone._debug;
	
	// Build a new childMap
	for ( var id in this.childMap) {
		var arr = [];
		childMap[id] = arr;
		this.childMap[id].forEach( function(c) {
			arr.push(c.clone(clone));
		});
	}
	this.unmatched.forEach( function(c) {
		unmatched.push(c.clone(clone));
	});
	return clone;
};

/**
 * Returns the parent MathNode or null if none exist.
 */
MathNode.prototype.getParent = function() {
	if (this.parent) {
		assert(this.parent._getRawTypeOfChild(this),
				"Child should REALLY be a child...");
	}
	return this.parent;
};

MathNode.prototype._update = function(nextChildToSelect, origMathNode) {
	if (this.model) {
		this.model._update(origMathNode || this, nextChildToSelect);
	} else if (this.getParent()) {
		this.getParent()._update(nextChildToSelect, origMathNode || this);
	}
};

/**
 * 
 * @return true if this node will disappear if it is replaced by a block or
 *         deleted.
 */
MathNode.prototype.isOptional = function() {
	return this.optional;
};

/**
 * TODO: test me, use me, or delete me All optional elements are an only child.
 * Possible parents: mml:otherwise, mml:condition, mml:degree, mml:logbase,
 * mml:bvar
 * 
 * Therefore, if someone asks their child if they are optional, we need to check
 * up to the grandparent to know
 * 
 * @return true if this node (and implicitly its parent) will disappear if it is
 *         replaced by a block or deleted.
 */
MathNode.prototype.isExpandable = function() {
	return this.expandable;
};

/**
 * TODO: test me, use me, or delete me
 * 
 * @return true if this node is a block
 * @type Boolean
 */
MathNode.prototype.isBlock = function() {
	return this.blockish;
};

/**
 * @return all the current children of this node.
 * @type MathNode[]
 */
MathNode.prototype.getChildren = function() {
	var ret = [];
	for ( var type in this.childMap) {
		var children = this.childMap[type];
		for ( var childId = 0; childId < children.length; childId++) {
			ret.push(children[childId]);
		}
	}
	this.unmatched.forEach( function(child) {
		ret.push(child);
	});

	var testParent = this;
	ret.forEach( function(child) {
		assert(testParent == child.getParent(),
				"Woah! some child doesn't have a parent. Bug?");
	});
	return ret;
};

/**
 * Used to decide when to replace a node with a block (if it's the only template
 * that fits then don't remove it)
 * 
 * @param {MathNode}
 *            child
 * @type Boolean
 */
MathNode.prototype._isOnlyTemplateThatFits = function(child) {
	var type = this.getTypeOfChild(child).base;
	var retTypes = MathType.getRetTypes();
	var templates = retTypes[type] || [];
	return templates.length <= 1;
};

/**
 * Removes the child if it exists, notifies the model, and puts in a block in
 * the DOM if necessary.
 * 
 * Note: If the child is not a block then a block is ALWAYS returned. Otherwise,
 * if the child is a block and is part of an arg with a minimum (like mml:plus)
 * and this would violate the minimum invariant then this returns a block.
 * 
 * If the child is a block, the minimum is 2, and there are no other children
 * (except maybe an expandable), unwrap the other child (removing this object
 * and the child argument) and attach the other child to this node's parent.
 * 
 * Otherwise, it returns null.
 * 
 * @param {MathNode}
 *            child
 * @return a block if the child was something other than a block (this is for
 *         the UI), a block if we tried to delete an expandable or a child that
 *         has a minimum number of children, or null.
 * @type MathNode
 */
MathNode.prototype.removeChild = function(child) {
	assert(child != null && "object" == typeof (child),
			"removeChild takes a MathNode");
	assert(this == child.getParent());
	// Now come the cases.
	// If child is not a block, then replace it with a block.
	var onlyFit = this._isOnlyTemplateThatFits(child);
	// Elements get treated as blocks if they (1) are a block, (2) are the only
	// thing that fits AND are not one of the exceptions (bvar)
	var isBlock = MathNode.BLOCK_TEMPLATE_NAME == child.template.name
			|| onlyFit;
	if (!isBlock) {
		getConsole().log("  -- replacing node with block");
		var type = this.getTypeOfChild(child).base;
		assert(type, "BUG: could not find child");
		var block = MathType.newBlock(null, type);
		this.replaceChildWith(child, block);
		assert(this == block.getParent(), "New block must be attached");

		return block;
		// } else if (MathNode.BLOCK_TEMPLATE_NAME != child.template.name) {
		// We're removing the expandable mml:piece,mml:otherwise,mml:bvar,or
		// mml:matrixrow

	} else {
		// It's a block. Now, either it's:
		// (1) Neither expandable nor optional
		// (2) Optional
		// (3) The expandable block. Just return it (can't be removed)
		// (4) Part of expandable arg, but the min invariant would be broken
		// (5) Part of expandable arg, but the min invariant would still hold
		// (6) Part of the unwrapping case (min=2, and no other children)
		//
		// Cases 1-4 result in a block being returned
		if (this.isChildDeletable(child)) {
			// Case (5)
			getConsole().log("  -- removing block");
			// Remove the child from the children array
			var next = child.nextSibling() || this;
			this._detachChild(child);
			this._update(next);
			return next;
		} else if (this._isUnwrappable(child)) {
			//Case (6)
			getConsole().log("  -- unwrapping");
			var unwrapped = this._isUnwrappable(child);
			// replace "this" with newNode.
			this.getParent().replaceChildWith(this, unwrapped);
			assert(!this.parent, "Should no longer have a parent");
			return unwrapped;
		} else {
			// Case (1-4)
			getConsole().log("  -- leaving block alone");
			return child;
		}
	}
};

/**
 * Returns whether calling removeChild on child will result in the node being
 * removed (it's an expandable type and does not violate the minimum
 * requirements)
 * 
 * @param {MathNode}
 *            child
 * @type true
 */
MathNode.prototype.isChildDeletable = function(child) {
	// It's a block. Now, either it's:
	// (1) Neither expandable nor optional
	// (2) Optional
	// (3) The expandable block. Just return it (can't be removed)
	// (4) Part of expandable arg, but the min invariant would be broken
	// (5) Part of expandable arg, but the min invariant would still hold
	//
	// Cases 1-4 result in a block being returned
	assert(child != null && "object" == typeof (child),
			"removeChild takes a MathNode");
	assert(this == child.getParent());
	if (!child.isBlock() && !this._isOnlyTemplateThatFits(child))
		return false;
	var info = this.getTypeOfChild(child);
	var type = info.base;
	var children = this.childMap[type] || this.unmatched;
	var i = children.indexOf(child);
	var numChildren = children.length; // 1 of which may be expandable
	return (info.max == -1 // part of expandable arg
			&& numChildren - 1 > info.min // min invariant holds (1 is for
	// the expandable)
	&& i != children.length - 1// NOT the expandable block
	);
};

/**
 * Detaches a MathNode from its parents without calling model.removeChild (which
 * removed id's and unlinks the Content MathML).
 * 
 * This is used in removeChild as well as in replaceChildWith (when the new node
 * is already attached somewhere)
 * 
 * @param {MathNode}
 *            child
 */
MathNode.prototype._detachChild = function(child, optNewChild) {
	assert(child, "Must always pass in a child argument.");
	var removed = false;
	var type = this.getTypeOfChild(child).base;
	assert(type, "BUG: could not find child");
	var children = this.childMap[type] || this.unmatched;
	var i; // start index to splice from
	for (i = 0; i < children.length; i++) {
		if (child == children[i]) {
			if (optNewChild) {
				children[i] = optNewChild;
				this._attachChild(optNewChild);
			} else {
				children.splice(i, 1);
			}
			removed = true;
			break;
		}
	}
	assert(removed, "BUG: Could not find child");
	child.parent = null;
};

MathNode.prototype._attachChild = function(child) {
	if (child.parent) {
		child.parent._detachChild(child);
	}
	child.parent = this;
};

/**
 * Also, if the node has args = [ "...2" ] and is the same type as the child, we
 * can combine them (no nested mml:plus, mml:times, mml:eq, mml:or, etc)
 * 
 * @param {MathNode}
 *            child
 * @param {MathNode}
 *            newNode
 * @return true if newNode still exists in the DOM (in the case of this=mml:plus
 *         and newNode=mml:plus, newNode's children will become this's children)
 * @type boolean
 */
MathNode.prototype.replaceChildWith = function(child, newNode) {
	assert(child, "child must be a MathNode");
	assert(newNode, "child must be a MathNode");
	assert(this == child.getParent(), "child must have a parent!");

	// newNode may be in the document. If so, delete it.
	if (newNode.getParent()) {
		newNode.getParent()._detachChild(newNode);
	}

	// There are several cases:
	// (1) child is the expandable node. It must NEVER be removed
	// (1.1) child is the child of the expandable node (if the expandable is a
	// - - - mml:bvar, mml:piece, mml:matrixrow)
	// (2) newNode has the same type as the this and is something like mml:plus
	// - - In that case, congeal (move newNode's children into this) so we don't
	// - - have nested mml:plus
	// (3) replace the node.

	// Now, if the node has args = [ "...2" ] and is the same type as the child,
	// Combine the child's children into this node at the splice point (congeal)
	var newTemplate = newNode.template;
	// grab whether this is expandable because things will change later on
	if (this.template == newTemplate && this.template.args.length == 1
			&& (new MathTypeInfo(this.template.args[0])).min == 2) {
		//NewNode will be descarded since we are congealing
		if (newNode.getParent()) {
			newNode.getParent()._detachChild(newNode);
		}
		this._congealChild(child, newNode);
		// Don't remove the child if it's the expandable
		// Since congealChild did not call update, we must call it manually.
		this._update(this/* nextChildToSelect */);
		assert(!newNode.getParent(), "Forgot to add the parent to newNode");
		return true;
	} else {
		var type = this.getTypeOfChild(child).base;
		var children = this.childMap[type] || this.unmatched;
		var index = children.indexOf(child);
		var parent = this.getParent();

		// mml:mtable/mml:mtr/mml:mtd (2 levels of expandables, not just 1)
		if (parent && parent.getParent() && parent.isExpandable()) {
			newExpandable = Model._buildMathNode(parent.template);
			newExpandable.parent = parent.getParent();
			rawType = parent.getParent()._getRawTypeOfChild(parent);
			var type = new MathTypeInfo(rawType).base;
			if (MathType.order.indexOf(type) >= 0) {
				parent.getParent().childMap[type].push(newExpandable);
			} else {
				parent.getParent().unmatched.push(newExpandable);
			}
		}

		if (child.isExpandable()) {
			children[index] = newNode;
			children[index + 1] = child; // mode the expandable over (since
			// we know it's the expandable, we
			// know there is nothing to the
			// right of index)
			newNode.parent = this;
			this._update(newNode);

		} else if (parent && this.isExpandable()) {
			//Case (1.1)
			// Detach the parent mml:piece/bvar/matrixrow and re-replace it.
			// This will trigger recreating the expandable
			// parent.getParent()._detachChild(parent);
			var newExpandable = Model._buildMathNode(this.template);
			newExpandable.parent = this.getParent();
			newExpandable.expandable = true;
			this.expandable = false;
			var rawType = parent._getRawTypeOfChild(this);
			var type = new MathTypeInfo(rawType).base;
			if (MathType.order.indexOf(type) >= 0) {
				parent.childMap[type].push(newExpandable);
			} else {
				parent.unmatched.push(newExpandable);
			}
			// Attach to the DOM but attach after... grrr

			// Also, we need to do the else below, so I'm copying it up here
			this._detachChild(child, newNode);
			this._update(newNode);

		} else {
			this._detachChild(child, newNode);
			this._update(newNode);
		}
		assert(this == newNode.getParent(),
				"Forgot to add the parent to newNode");

		return false;
	}
};
/**
 * Used for constructing mml:ci 's with a text child (the common case)
 * 
 * @param str
 *            The text to insert
 * @return
 */
MathNode.prototype.replaceChildWithText = function(child, str) {
	assert(this.template.isToken, "This method only works on mml:ci");
	if (child) {
		this._detachChild(child);
	}
	this.text = str;
	this._update(this);
};
MathNode.prototype.setChildAsText = function(str) {
	assert(this.template.isToken, "This method only works on mml:ci");
	var self = this; // used for Array.forEach
	this.getChildren().forEach( function(c) {
		self._detachChild(c);
	});
	this.text = str;
	this._update(this);
};

/**
 * Internal method that takes the children of newNode and adds them to "this"
 * before the insertPoint The insertPoint is then removed.
 * 
 * @param {MathNode}
 *            insertPoint
 * @param {MathNode}
 *            newNode
 */
MathNode.prototype._congealChild = function(insertPoint, newNode) {
	var info = new MathTypeInfo(this.template.args[0]);
	var type = info.base;
	var children = this.childMap[type] || this.unmatched;

	// The insertion point will ALWAYS be before the old child.
	// All we need to do is fix the array (and then reinsert the Content
	// MathML nodes)

	var newNodeChildren = newNode.childMap[type] || newNode.unmatched;
	var index = children.indexOf(insertPoint);
	var newArray = [];
	// Add all the children before "child"
	for ( var i = 0; i < index; i++) {
		newArray.push(children[i]);
	}
	//Add all the new children
	// Do length-1 because we KNOW the last one is an expandable.
	for ( var i = 0; i < newNodeChildren.length - 1; i++) {
		newArray.push(newNodeChildren[i]);
		newNodeChildren[i].parent = this;
	}
	//Add the rest of the original children
	for ( var i = index + 1/* skip the block we're inserting to */; i < children.length; i++) {
		newArray.push(children[i]);
	}
	if (this.childMap[type]) {
		this.childMap[type] = newArray;
	} else {
		this.unmatched = newArray;
	}
};

/**
 * Check whether this node can be unwrapped (it's a binary or binary+ node).
 * 
 * Should just be able to check if all but one node is blockish and that there
 * at least 3 nodes.
 * 
 * @param child
 * @return
 */
MathNode.prototype._isUnwrappable = function(child) {
	//assert(child.isBlock(), "By this point chlid should be a block");
	// if the child is a special (bvar, etc) then it DEFINITELY can't affect
	// unwrapping
	if (this.unmatched.indexOf(child) < 0 || child.isExpandable()) {
		return null;
	}
	var children = this.unmatched.length;
	// loop and see if all but 1 are blocks
	var nonBlockChildren = 0;
	var nonBlockChild;
	var hasExpandable = 0; // used for counting later
	this.unmatched.forEach( function(c) {
		if (!c.isBlock()) {
			nonBlockChildren++;
			nonBlockChild = c;
		} else if (c.isExpandable()) {
			hasExpandable = 1;
		}
	});
	// Elements like mml:interval have 2 children but are NOT unwrappable.
	if (!child.getParent().id) return false;
	// If we match the criteria (at least 2 children) and all are blocks (except
	// maybe one), then return either the non-block child, or a non-expandable
	// block.
	if (children - hasExpandable == 2 && nonBlockChildren <= 1) {
		return nonBlockChild || this.unmatched[0];
	}
};

/** Convenience function. Calls parent.removeChild. Used by math-input */
MathNode.prototype.deleteMe = function() {
	//Special case for deleting bvars (since the UI has no way of selecting them.
	return MathExceptions.deleteMe(this);
};

/**
 * Returns which argument this child fills in the form of a MathTypeInfo object,
 * null if none, and throws an exception if the parameter is not a child.
 * 
 * @param {MathNode}
 *            child
 * @type MathTypeInfo
 */
MathNode.prototype.getTypeOfChild = function(child) {
	var type = this._getRawTypeOfChild(child);
	if (type) {
		return new MathTypeInfo(type);
	} else {
		return null;
	}
};

/**
 * Internal convenience method.
 * 
 * @param {MathNode}
 *            child
 * @returns the raw type (with "?*+2" at the end)
 * @type {String}
 */
MathNode.prototype._getRawTypeOfChild = function(child) {
	for ( var type in this.childMap) {
		var children = this.childMap[type];
		assert(children, "Should have found children for each type");
		// Array.contains()
		if (children.indexOf(child) >= 0) {
			//now look up the raw type in template.args
			this.template.args.forEach( function(arg) {
				if (new MathTypeInfo(arg).base == type) {
					type = arg;
				}
			});
			return type;
		}
	}
	//Check the unmatched
	if (this.unmatched.indexOf(child) >= 0) {
		//find the unmatched arg (if any)
		var type = "any*";
		this.template.args.forEach( function(arg) {
			var info = new MathTypeInfo(arg);
			if (MathType.order.indexOf(info.base) < 0) {
				type = arg;
			}
		});
		return type;
	}
	assert(false, "BUG: Child type not found! child="
			+ (child ? (child.dom ? MathUtil.localName(child.dom) : "no-dom")
					: "null") + " parent="
			+ (this.dom ? MathUtil.localName(this.dom) : "no-dom"));
};

/**
 * A test to see if this MathNode is attached to the main document. Used by
 * MathPosition to see if it needs to be updated.
 * 
 * @param {MathNode}
 *            root
 * @type Boolean
 */
MathNode.prototype.descendantOf = function(root) {
	return (this == root) || (this.parent && this.parent.descendantOf(root));
};

/* * * * * * DOM Navigation-style function (Used by NodeIterator) * * * * * */
MathNode.prototype.firstChild = function() {
	return this.getChildren()[0];
};
MathNode.prototype.nextSibling = function() {
	if (!this.getParent()) {
		return null;
	}
	var i = this.getParent().getChildren().indexOf(this);
	return this.getParent().getChildren()[i + 1];
};
MathNode.prototype.previousSibling = function() {
	if (!this.getParent()) {
		return null;
	}
	var i = this.getParent().getChildren().indexOf(this);
	if (i == 0)
		return null;
	return this.getParent().getChildren()[i - 1];
};
MathNode.prototype.lastChild = function() {
	var children = this.getChildren();
	return children[children.length - 1]
};
