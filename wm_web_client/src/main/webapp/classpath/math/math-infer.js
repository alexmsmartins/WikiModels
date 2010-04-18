function MathType() {
	this.name = "";
	this.returnType = "";
	this.elementType = "";
	this.args = [ "" ];
	this.attributes = {
		woo : []
	};
};

MathType.UNKNOWN_TYPE = {
	name : "unknown",
	elementType : "this-is-not-used-and-just-needs-to-not-match",
	returnType : "any",
	args : [ "any*" ]
};

/**
 * Contains a global lookup from element name to array of templates that match
 */
MathType.templateLookup = {};
( function() {
	for (id in MathEditor_Templates) {
		var template = MathEditor_Templates[id];
		var name = template.name;
		var arr;
		if (MathType.templateLookup[name]) {
			arr = MathType.templateLookup[name];
		} else {
			arr = [];
			MathType.templateLookup[name] = arr;
		}
		arr.push(template);
	}
})();

MathType.TEXT_TYPE = {
	name : "TEXT_TYPE"
};

MathType.order = [ "bvar", "lowlimit", "uplimit", "interval", "condition",
		"domainofapplication", "degree", "momentabout", "logbase", "piece",
		"otherwise" ];

/**
 * Returns a list of candidate templates for a given domNode. Used to create a
 * MathNode from a DOM Element
 * 
 * @param {Element}
 *            domNode
 * @type Array
 */
MathType._getCandidates = function(domNode) {
	var candidates;

	// Several cases:
	// (0.1) node type is TEXT_NODE
	// (0.2) node type is something other than ELEMENT_NODE
	// (1) node name matches at least 1 template's name
	// (2) node name is apply
	// (2.1) first child is a csymbol
	// (2.1.1) We recognize the @definitionURL
	// (2.1.2) We don't recognize the @definitionURL
	// (2.2) first child matches at least 1 template
	// (3) node name is "block"
	// (4) Unknown template

	// Case (0.1)
	if (Node.TEXT_NODE == domNode.nodeType) {
		if ("" != domNode.nodeValue.strip()) {
			return [ MathType.TEXT_TYPE ];
		} else {
			return [];
		}
	} else
	// Case (0.2)
	if (Node.ELEMENT_NODE != domNode.nodeType) {
		//Do nothing (return null)
		return [];
	} else {
		var name = MathUtil.localName(domNode);
		candidates = MathType.templateLookup[name] || [];
		// Case (2)
		if ('apply' == name) {
			var firstChild = MathUtil.xpathNode("*[1]", domNode);
			if (firstChild) {
				//
				// Update the name and candidates since apply doesn't really
				// have any
				name = MathUtil.localName(firstChild);
				candidates = MathType.templateLookup[name] || [];
				// filter and only provide 'operator's
				var filteredCandidates = [];
				candidates.forEach( function(c) {
					if ('operator' == c.elementType) {
						filteredCandidates.push(c);
					}
				});
				candidates = filteredCandidates;

				// Case (2.1)
				if ('csymbol' == name) {
					//
					// Again, update the candidates (only ones w/ same
					// @definitionURL)
					var definitionURL = firstChild
							.getAttribute("definitionURL");
					var newCandidates = [];// templates w/ matching defURL
					var noDefinitionUrl = []; // csymbol templates w/o
					candidates.forEach( function(c, i, arr) {
						if (definitionURL == c.definitionURL) {
							newCandidates.push(c);
						} else if (!c.definitionURL
								&& 'operator' == c.elementType) {
							noDefinitionUrl.push(c);
						}
					});
					// Case (2.1.1)
					if (newCandidates.length > 0) {
						candidates = newCandidates;
					} else
					// Case (2.1.2)
					{
						candidates = noDefinitionUrl;
					}
				} else {
					//
					// Case (2.2)
					// Special sub-case. If the first child is something that is
					// constructed (like mml:ci) we can't just replace the
					// candidates. We can only replace the candidates if we find
					// at least 1 'operator'
					var possibleCands = candidates;
					candidates = [];
					possibleCands.forEach( function(c) {
						if ('operator' == c.elementType
								&& !(candidates.indexOf(c) >= 0)) {
							candidates.push(c);
						}
					});
					// If we couldn't find anything better, then just use the
					// mml:apply constructor
					if (candidates.length == 0) {
						var name = MathUtil.localName(domNode);
						candidates = MathType.templateLookup[name] || [];
					}
				}

			}
		} else
		// Case (1)
		if (candidates.length > 0) {
			// do nothing
		} else if ('block' == name) {
			candidates = [ {
				name : MathNode.BLOCK_TEMPLATE_NAME
			} ];
		} else {
			//			if ('uplimit' == name){
			//				|| 'semantics' == name
			// || 'annotation' == name || 'annotation-xml' == name
			// || 'reln' == name || 'fn' == name) {
			// } else
			getConsole().log(
					"Couldn't find candidates for Node(using UNKNOWN_TYPE):"
							+ name);
		}
	}

	if (candidates == null || candidates.length == 0) {
		candidates = [ MathType.UNKNOWN_TYPE ];
	}
	return candidates;
};

/**
 * From an array of candidates, choose the "best fit" one. "best fit" is the one
 * that matches the most arguments that can only have 1 element associated with
 * them.
 * 
 * @param {Element}
 *            domNode
 * @param {Array}
 *            candidates
 * @type Template
 */
MathType._getBestCandidate = function(domNode, candidates) {
	if (0 == candidates.length) {
		return null;
	} else if (1 == candidates.length) {
		return candidates[0];
	}

	//
	var maxMatches = -1;
	var best = null;
	candidates.forEach( function(c, i, arr) {
		//
			// For each template, go through the args and build up an array of
			// unique element names to find
			var uniques = [];
			c.args = c.args ? c.args : [];// args may be undefined
			c.args.forEach( function(arg, i, arr) {
				var type = new MathTypeInfo(arg).base;
				var matches = MathType.getRetTypes()[type] || [];
				if (matches.length == 1) {
					uniques.push(matches[0].name);
				}
			});

			// Loop through the uniques and see if this domNode has those
			// children (Use XPath for this)
			var children = 0;
			if (uniques.length > 0) {
				var xpath = "count(mml:";
				uniques.forEach( function(arg, i, arr) {
					if (i > 0) {
						xpath = xpath + ") + count(mml:";
					}
					xpath = xpath + arg;
				});
				children = MathUtil.xpathNumber(xpath + ")", domNode);
			}

			// If we found a better candidate, replace it
			// Since we have 2 "bvar" templates (bvar and bvardeg, favor bvar
			// when matches are the same)
			if (children > maxMatches || c.name == c.returnType) {
				maxMatches = children;
				best = c;
			}

		});
	return best;
};

/**
 * Returns a MathNode given a model and a DOM Node or null if the domNode is not
 * an element.
 * 
 * @param {Node}
 *            domNode
 * @type MathNode
 */
MathType.get = function(domNode) {
	assert(domNode, "MathType.get domNode is NULL");
	var template = MathType._getBestCandidate(domNode, MathType
			._getCandidates(domNode));
	if (!template) {
		return null;
	}

	if (MathType.TEXT_TYPE == template) {
		return new MathNode( {
			template : template,
			text : domNode.nodeValue.strip()
		});
	}

	//TODO Enable me
	// MathExceptions.cleanupMathML(domNode)

	var attributes = {};
	var ret = new MathNode( {
		template : template,
		attributes : attributes
	});

	// If the best candidate is the UNKNOWN_TYPE, then make sure we store the
	// element name for serialization later on.
	if (MathType.UNKNOWN_TYPE == template) {
		ret.unknownName = MathUtil.localName(domNode);
	}

	// Set the attributes of the MathNode
	if (domNode.attributes) {
		for ( var i = 0; i < domNode.attributes.length; i++) {
			var attr = domNode.attributes[i];
			// Store the fact that interval used to be a lowlimit/upimit pair
			// for serializing later
			if ("__WAS_LOWLIMIT_UPLIMIT" == attr.name) {
				ret.__WAS_LOWLIMIT_UPLIMIT = true;
			} else
			// Don't add namespace attribute
			if (0 != attr.name.indexOf('xmlns')) {
				attributes[attr.name] = attr.nodeValue;
			}
		}
	}

	MathType.makeChildren(ret, domNode);
	return ret;
};

/**
 * Creates a new Empty block.
 * 
 * @param {MathNode}
 *            parent
 * @param {String}
 *            returnType
 * @type MathNode
 */
MathType.newBlock = function(parent, returnType) {
	return new MathNode( {
		blockish : true,
		parent : parent,
		template : {
			name : MathNode.BLOCK_TEMPLATE_NAME,
			returnType : returnType ? returnType : 'any'
		}
	});
};

/**
 * Used to create the children of a MathNode using an optional domNode
 * 
 * @param {MathNode}
 *            mathNode
 * @param {Element}
 *            optDomNode
 */
MathType.makeChildren = function(mathNode, optDomNode) {
	var template = mathNode.template;
	var children = [];
	// For each child, get the Math Node as well
	// but be sure to skip the 1st child of a mml:apply (if it's an operator)
	// We do this instead of Xpath because mml:cn may have 2 text() children
	// (separated by a mml:sep)
	if (optDomNode && optDomNode.childNodes) {
		var skipChild = ('operator' == template.elementType) ? MathUtil
				.xpathNode("*[1]", optDomNode) : null;
		for ( var i = 0; i < optDomNode.childNodes.length; i++) {
			var childDom = optDomNode.childNodes.item(i);
			assert(childDom, "Woah! ChildDOM is null. i=" + i + " length="
					+ optDomNode.childNodes.length);
			// Skip the 1st child of mml:apply
			if (skipChild == childDom) {
				continue;
			}
			var childNode = MathType.get(childDom);
			// Special exception for TEXT_TYPE nodes
			if (childNode && MathType.TEXT_TYPE == childNode.template) {
				// Concatenate child.text using commas
				mathNode.text = mathNode.text ? mathNode.text + ', '
						+ childNode.text : childNode.text;
			} else if (childNode) {
				children.push(childNode);
				mathNode._attachChild(childNode);
			}
		}
	}

	//Sort the children:
	var childTypes = MathType._organizeChildren(children);
	var childMap = childTypes.childMap;
	var unmatched = childTypes.unmatched;

	MathType._populateChildMap(mathNode, template.args, childMap,
			unmatched);

	// childMap contains many empty entries []
	for ( var id in childMap) {
		if (childMap[id].length == 0) {
			delete childMap[id];
		}
	}

	mathNode.childMap = childMap;
	mathNode.unmatched = unmatched;
}

MathType._populateChildMap = function(parent, args, childMap, unmatched) {
	args = args || []; // just in case
	//
	// Attach the children and add optional/expandables as needed
	// Creates a "block" which can be a mmled:block, mml:matrixrow, mml:piece,
	// mml:bvar...
	function _newBlock(type) {
		var block;
		var onlyOneTemplate = (MathType.templateLookup[type] || []).length == 1;
		if (MathType.order.indexOf(type) >= 0 || onlyOneTemplate) {
			//Create the correct child
			type = MathExceptions.defaultDomainofapplication(type);
			var template = MathEditor_Templates[type];
			var childMap2 = {};
			var unmatched2 = [];
			block = new MathNode( {
				parent : parent,
				template : template,
				childMap : childMap2,
				unmatched : unmatched2
			});
			MathType._populateChildMap(block, template.args, childMap2,
					unmatched2);
		} else {
			//Create a mmled:block
			block = MathType.newBlock(parent, new MathTypeInfo(type).base);
		}
		block.blockish = true;
		return block;
	}

	//First, count up the number of unmatched children.
	// The reason is that we'd like to make blocks to fill them up.
	var minUnmatched = 0;
	args.forEach( function(arg) {
		var info = new MathTypeInfo(arg);
		if (MathType.order.indexOf(info.base) < 0) {
			minUnmatched += info.min == 0 ? 1 : info.min;
		}
	});

	args.forEach( function(arg) {
		var info = new MathTypeInfo(arg);

		/*
		 * If it's a 'special' ordered child but we don't have an entry in the
		 * childMap, add one
		 */
		var arr = childMap[info.base];
		if (!arr && MathType.order.indexOf(info.base) >= 0) {
			arr = [];
			childMap[info.base] = arr;
		} else if (!arr) {
			arr = unmatched; // we can still add blocks to it
		}

		/* For things that have a min, or optionals, create blocks to fill it */
		while (arr.length < info.min || arr.length == 0 && info.optional) {
			arr.push(_newBlock(info.base));
		}

		/* Add an expandable block */
		if (-1 == info.max) {
			//TODO Add an optional block
			var block = _newBlock(info.base);
			block.expandable = true;
			arr.push(block);
		}

		/* Set the optional if needed */
		// (lastChild is guaranteed to be something)
		assert(arr.length > 0, "Should ALWAYS have a child by now");
		var lastChild = arr[arr.length - 1];
		lastChild.optional = info.optional;

	});

	// Make sure unmatched is at least minUnmatched (safe because either we have
	// 1 expandable unmatched, or multiple NON-expandable unmatcheds)
	while (unmatched.length < minUnmatched) {
		var block = _newBlock('any');
		unmatched.splice(0, 0, block);
	}
};

/**
 * Splits the children up into a map of special qualifiers (like mml:bvar,
 * mml:domainofapplication, etc) and an array of the rest of arguments. Used for
 * creating a MathNode
 * 
 * @param {Array}
 *            children
 * @type Object containing a childMap and an unmatched array
 */
MathType._organizeChildren = function(children) {
	var dict = {};
	// build up the dictionary
	var catchAll = []; // catchall
	MathType.order.forEach( function(o) {
		dict[o] = [];
	});

	// Push each child into the dictionary (or the otherTypes)
	children.forEach( function(c) {
		var name = c.template.returnType;
		var arr = dict[name] || catchAll;
		arr.push(c);
	});

	// Used for determining optional/expandable nodes
	return {
		childMap : dict,
		unmatched : catchAll
	};
};

/**
 * Converts a MathNode back into a DOM Node. Additionally takes a filter
 * function to decide whether to output the descendant into the DOM.
 * 
 * Also, takes a wysiwygFunc that adds additional attributes needed by the
 * WYSIWYG XSLT (like id, optional, expandable).
 * 
 * MathRender uses filter for the Preview/Edit-Source modes and wysiwygFunc for
 * the edit mode
 * 
 * @param {Document}
 *            doc
 * @param {MathNode}
 *            mathNode
 * @param {Function}
 *            filter takes a mathNode and decides whether to include it in the
 *            output DOM
 * @param {Function}
 *            wysiwygFunc true if we want optional nodes and id's (for rendering
 *            the WYSIWYG)
 * @type {Node}
 */
MathType.toDom = function(doc, mathNode, filter, wysiwygFunc) {
	//Text Nodes are MathNodes.
	if (MathType.TEXT_TYPE == mathNode.template) {
		return doc.createTextNode(mathNode.text);
	}

	assert(mathNode.template, "Every mathNode sould have a template");
	var tagName = mathNode.template.name;
	var ns = NS_MATHML;
	if ('block' == tagName) {
		ns = NS_MMLED;
	}

	if ('unknown' == tagName) {
		tagName = mathNode.unknownName;
	}
	var el = doc.createElementNS(ns, tagName);

	if (mathNode.text) {
		el.appendChild(doc.createTextNode(mathNode.text));
	}

	// Set all of the attributes of the node 1st
	if (mathNode.template.definitionURL) {
		el.setAttribute('definitionURL', mathNode.template.definitionURL);
	}
	for ( var attr in mathNode.attributes) {
		el.setAttribute(attr, mathNode.attributes[attr]);
	}

	//If the node represents a mml:apply then wrap the element up in one.
	if ('operator' == mathNode.template.elementType) {
		var apply = doc.createElementNS(NS_MATHML, 'apply');
		apply.appendChild(el);
		el = apply;
	}

	// Set the @id
	if (wysiwygFunc) {
		wysiwygFunc(mathNode, el);
	}

	//Get childMap as an array (ordered)
	var children = [];
	MathType.order.forEach( function(o) {
		if (mathNode.childMap[o]) {
			mathNode.childMap[o].forEach( function(c) {
				children.push(c);
			});
		}
	});
	mathNode.unmatched.forEach( function(c) {
		children.push(c);
	});

	// Never include children of mathNode's with text
	if (!mathNode.text)

		// For each child, attach it.
		children.forEach( function(child, i, arr) {
			// Only include nodes that match the filter
				if (!filter || filter(child)) {

					//Special case for mml:interval that used to be lowlimit/uplimit
					if (MathExceptions.shouldBeLimits(child)) {
						var lowlimit = doc.createElementNS(NS_MATHML,
								'lowlimit');
						var uplimit = doc.createElementNS(NS_MATHML, 'uplimit');
						var lowChild = child.getChildren()[0];
						var upChild = child.getChildren()[1];
						if (lowChild)
							lowlimit.appendChild(MathType.toDom(doc, lowChild,
									filter, wysiwygFunc));
						if (upChild)
							uplimit.appendChild(MathType.toDom(doc, upChild,
									filter, wysiwygFunc));
						el.appendChild(lowlimit);
						el.appendChild(uplimit);
					} else {
						el.appendChild(MathType.toDom(doc, child, filter,
								wysiwygFunc));
					}
				} else {
					//getConsole().log("filter. Skipping " + child.template.name);
				}
			});

	return el;
};

/**
 * Utility function that checks whether a template accepts an array of
 * returnTypes
 * 
 * @param {String[]}
 *            args
 * @param {String[]}
 *            childReturnTypes
 * @return either 0 (does not accept), 1 (accepts partially), or 2 (accepts
 *         perfectly)
 * @type Number
 */
MathType.accepts = function(args, childReturnTypes) {
	args = args || [];
	var argId = 0;
	var alreadyCounted = 0; // used for things that have a minimum number of
	// children (like plus)

	// can't do "for(... in ...) syntax because sometimes we subtract the
	// childId
	for ( var childId = 0; childId < childReturnTypes.length; childId++) {
		//alert("argId=" + argId + " childId=" + childId);
		// If we run out of argId's before we run out of children, then fail
		if (argId == args.length) {
			return false;
		}
		var childType = childReturnTypes[childId];
		// Extract the type information from the type string
		var info = new MathTypeInfo(args[argId]);
		var match = (info.base == childType) || (info.base == "any")
				|| (childType == "any");
		// If we found a match, then proceed to the next arg
		// Otherwise, one of several things may have happened
		if (match) {
			alreadyCounted++;
			// If we have a variable number of arguments don't increment argId
			// yet (only once we get a mismatch).
			if (info.max != -1) {
				argId++;
			}
		} else {
			//Could have mismatched because the arg was optional
			// or, could have mismatched becuase arg was a variable number (*+2)
			// - In either case move to the next arg, and re-check the child.
			// Otherwise, we have a potential mismatch.
			if (info.optional) {
				childId--;
				argId++;
			} else if (info.max == -1 && alreadyCounted >= info.min) {
				alreadyCounted = 0;
				childId--;
				argId++;
			} else {
				return false;
			}
		}
	}

	//Only return true if we ran out of args as well
	// Note: the last arg might be an optional.
	// In some cases argId was incremented to args.length and in some it wasn't
	// (it was a variable-length argument).
	// So, if it wasn't incremented to args.length check if the last arg was
	// optional

	// If the last arg checked was a variable type check to make sure we found
	// the minimum number of children

	if (args.length <= argId) {
		return true;
	}

	var lastArgChecked = new MathTypeInfo(args[argId]);
	if (lastArgChecked.max == -1 && alreadyCounted >= lastArgChecked.min) {
		argId++;
	}

	//Check again, because we could have checked all the args
	if (args.length <= argId) {
		return true;
	}

	if (argId < args.length - 1) {
		return false; // didn't reach the end of the args
	} else {
		var lastArg = new MathTypeInfo(args[argId]);
		return lastArg.optional;
	}
};

MathType._retTypesCache = null;
MathType.getRetTypes = function() {
	var retTypes = MathType._retTypesCache;
	if (!retTypes) {
		retTypes = {};
		for (id in MathEditor_Templates) {
			var template = MathEditor_Templates[id];
			var retType = template.returnType;
			if (!retTypes[retType]) {
				retTypes[retType] = [];
			}
			retTypes[retType].push(template);
		}
	}
	return retTypes;
};

/**
 * Takes a string of the form "number", "number?", "number*", "number+",
 * "number2" and returns extracted information from it.
 * 
 * @param {String}
 *            type
 * @return
 */
function MathTypeInfo(type) {
	this.base = type;
	this.min = 0;
	this.max = 0;
	this.optional = false;
	switch (type.charAt(type.length - 1)) {
	case "2":
		this.min++; // fall through
	case "+":
		this.min++;
	case "*":
		this.max = -1;
		this.base = type.substring(0, type.length - 1);
		break;
	case "?":
		this.max = 1;
		this.base = type.substring(0, type.length - 1);
		this.optional = true;
		break;
	default:
		this.min = 1;
		this.max = 1;
	}
};
