/**
 * Location of special cases. These should all be added here.
 */

function MathExceptions() {
};

/**
 * Internal function that cleans up the MathML. Currently, it replaces
 * lowlimit/uplimit pairs with an interval and reorders the children of
 * mml:apply (bvar, lowlimit, uplimit, interval, condition, domainofapplication,
 * degree, momentabout, logbase)
 * 
 * @param {Element}
 *            root
 */
MathExceptions.cleanupMathML = function(root) {

	//replace mml:reln with mml:apply (mml:reln is deprecated)
	var reln;
	while (reln = MathUtil.xpathNode("//mml:reln", root)) {
		var apply = root.ownerDocument.createElementNS(NS_MATHML, "apply");
		while (reln.firstChild) {
			apply.appendChild(reln.firstChild);
		}
		reln.parentNode.replaceChild(apply, reln);
	}

	//Replace lowlimit/uplimit with interval
	while (parent = MathUtil.xpathNode(
			"//mml:apply[mml:uplimit/* and mml:lowlimit/*]", root)) {
		var uplimitChild = MathUtil.xpathNode("mml:uplimit/*", parent);
		var lowlimitChild = MathUtil.xpathNode("mml:lowlimit/*", parent);
		var interval = root.ownerDocument
				.createElementNS(NS_MATHML, "interval");
		// Remember this when rendering the DOM (MathNode will pick up this)
		interval.setAttribute("__WAS_LOWLIMIT_UPLIMIT", "true");
		parent.replaceChild(interval, lowlimitChild.parentNode);
		parent.removeChild(uplimitChild.parentNode);
		interval.appendChild(lowlimitChild);
		interval.appendChild(uplimitChild);
	}

	// Per m0033
	// If a mml:math node has 2 children and neither is a mml:semantics, then
	// wrap it up in a mml:mrow (it can't be content mathML)
	if ("math" == MathUtil.localName(root)
			&& 1 < MathUtil.xpathNumber("count(*)", root)) {
		var mrow = root.ownerDocument.createElementNS(NS_MATHML, "mrow");
		root.appendChild(mrow);
		var child;
		while ((child = MathUtil.xpathNode("*[1]", root)) != mrow) {
			mrow.appendChild(child);
		}
	}
};

MathExceptions.defaultDomainofapplication = function(type) {
	if ('domainofapplication' == type) {
		type = 'interval';
	}
	return type;
};

/**
 * Qualifiers can't be selected, so we must move to the grandparent
 */
MathExceptions.getParent = function(mathNode) {
	if (mathNode.getParent()) {
		switch (mathNode.getParent().template.name) {
		case "bvar":
		case "condition":
		case "interval":
		case "logbase":
		case "degree":
			return mathNode.getParent().getParent();
		default:
			return mathNode.getParent();
		}
	} else {
		return null;
	}
};

/**
 * true if the MathNode will be rendered as a single input box. This is the case
 * when the node is a number, variable, block.
 * 
 * @param {MathNode}
 *            mathNode
 * @type Boolean
 */
MathExceptions.becomesAnInput = function(mathNode) {
	var ret = (mathNode.text)
			|| mathNode.template.name == MathNode.BLOCK_TEMPLATE_NAME
			// || mathNode.template.name == "csymbol"
			|| (mathNode.template.isToken && (mathNode.getChildren().length == 0));
	return ret;
};

MathExceptions.deleteMe = function(mathNode) {
	var parent = mathNode.getParent();
	if (parent) {
		if ("bvar" == parent.template.name && mathNode.isBlock()) {
			return parent.deleteMe();
		} else {
			return parent.removeChild(mathNode);
		}
	} else {
		return mathNode;
	}

};

/**
 * Only remove the expandable in cases were there is a keyboard method of adding
 * them in
 */
MathExceptions.validToContract = [ "plus", "times", "eq", "lt", "gt", "leq",
		"geq", "and", "or", "union", "intersect", "subset", "prsubset" ];

/**
 * Skip over things that aren't selectable in the presentation (like mml:bvar,
 * mml:condition, mml:interval)
 */
MathExceptions.smartNextChildToSelect = function(nextChildToSelect) {
	switch (nextChildToSelect.template.name) {
	case "bvar":
	case "condition":
	case "interval":
	case "domainofapplication":
	case "logbase":
	case "degree":
		return nextChildToSelect.getChildren()[0];
	}

	//Could be an expandable node. In that case just select the parent.
	if (nextChildToSelect.isExpandable()) {
		var parentName = nextChildToSelect.getParent().template.name;
		if (MathExceptions.validToContract.indexOf(parentName) >= 0) {
			return nextChildToSelect.getParent();
		}
	}
	return nextChildToSelect;
};

/**
 * Used to display text in the input box to help the user (disabled)
 * 
 * @param {MathNode}
 *            mathNode
 * @type String
 */
MathExceptions.emptyTextFor = function(mathNode) {
	var parentName = MathUtil.localName(mathNode.dom.parentNode);
	switch (parentName) {
	case "bvar":
		return "Var";
	case "interval":
		return mathNode.dom == MathUtil.xpathNode("*[1]",
				mathNode.dom.parentNode) ? "Lower" : "Upper";
	}
	//Now, do it based on the parent's type
	var type = mathNode.getParent().getTypeOfChild(mathNode);
	switch (type.base) {
	case "any":
		return "Expr";
	default:
		return type.base;
	}
};

MathExceptions.shouldBeLimits = function(child) {
	if (child.__WAS_LOWLIMIT_UPLIMIT)
		return true;

	var limitOps = [ "sum", "product", "int" ];

	return ("interval" == child.template.name && limitOps.indexOf(child
			.getParent().template.name) >= 0);
};