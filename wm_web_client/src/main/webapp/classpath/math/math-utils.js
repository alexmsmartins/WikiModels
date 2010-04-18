function MathUtil() {
};

var DISABLE_ASSERTIONS = false; // DISABLE_ASSERTIONS;
var DEBUG_IS_IE = DEBUG_IS_IE; // let IE set it to true, but by default it's
								// false.
var DEBUG_IS_SAFARI = DEBUG_IS_SAFARI; // let SAFARI set it to true, but by
										// default it's false.
// FF2 doesn't like CSS on MathML, so we need to set these manually.
MathUtil.FF2_HIGHLIGHTED = '#ccccff';
MathUtil.FF2_CURSOR = '#f9f9f9';

// -------------------------------------
// Constants used throughout the editor
// -------------------------------------
var NS_XHTML = "http://www.w3.org/1999/xhtml";
var NS_XSLT = "http://www.w3.org/1999/XSL/Transform";
var NS_MATHML = "http://www.w3.org/1998/Math/MathML";
var NS_MMLED = "http://cnx.rice.edu/mmled";

// ----------------------------------
// Helper functions (for debugging)
// ----------------------------------

function getConsole() {
	if ((!window.console && !window.firebug)
			|| (window.console && !window.console.dirxml)/* safari */) {
		function assert(b, msg) {
			if (!b) {
				//alert("Assertion Failed:" + msg);
			}
		}
		function nothing() {
		}
		;
		// function alertIt(s) { alert("console.log/debug:"+s) };
		var alertIt = nothing;
		return {
			assert : assert,
			log : alertIt,
			debug : alertIt,
			dir : nothing,
			dirxml : nothing
		};
	}

	try { //Firebug may be undefined
		// Safari (already has a console. can't override
		return firebug.d.console.cmd;
	} catch (e) {
	}
	return window.console;
}

/** @param {boolean} b
 * @param {String} msg */
var assert = function(b, msg) {
	if (!b) {
		getConsole().assert(b, msg);
		if (!MathUtil.alreadyAsserted) {
			MathUtil.alreadyAsserted = true;
			_dpExn( {
				name : "Matheditor.assert",
				message : msg
			}, "Alerting before throwing: " + msg);
		}// throw {name: "assertion failed", message: msg}; 
	}
};
var _dpExn = function(e, sOpt) {
	var s = (sOpt ? sOpt : "") + (e && sOpt ? " error=" : "")
			+ (e ? e.name + ":" + e.message : "");
	if (!s)
		s = "No error and no string. weird";
	getConsole().log("EXN:" + s);
	if (e) {
		//if(window.console) window.console.dir(e);
		getConsole().dir(e);
		throw e;
	}
}

//From kupuhelpers.js
/**
 * @param {Node}
 *            node
 * @param {boolean}
 *            continueatnextsibling
 * @param {function}
 *            filter
 */
function NodeIterator(node, continueatnextsibling, filter, extraArg, goingUp) {
	/* simple node iterator

	    can be used to recursively walk through all children of a node,
	    the next() method will return the next node until either the next
	    sibling of the startnode is reached (when continueatnextsibling is 
	    false, the default) or when there's no node left (when 
	    continueatnextsibling is true)

	    returns false if no nodes are left
	 */
	assert(node, "NodeIterator was passed a null node argument");
	this.node = node;
	this.current = node;
	this.terminator = continueatnextsibling ? null : node;
	// Wrap filter in a function so eclipse type-checking works
	this.filter = function(a) {
		return filter(a);
	}; // Note: Could be null

	this.extraArg = extraArg;
	this.goingUp = goingUp; // only used when extraArg = true

	this.next = function() {
		/* return the next node */
		if (!this.current) {
			// restart
			this.current = this.node;
		}
		;
		if (this.extraArg) {
			//dp("Iterator: goingUp="+this.goingUp);
		}
		var current = this.current;
		if (current.firstChild() && !this.goingUp) {
			//if(this.extraArg) dp("Iterator: current.firstChild()");
			this.current = current.firstChild();
		} else if (this.extraArg && current.nextSibling() && this.goingUp) {
			//if(this.extraArg) dp("Iterator: current.nextSibling() (goingup=false)");
			this.current = current.nextSibling();
			this.goingUp = false;
		} else {
			if (this.extraArg) {
				//Since this is a before-and-after traversal,
				//When we hit a leaf going down, hit the same leaf going up
				if (!this.goingUp) {
					this.goingUp = true;
					// keep this.current
				} else {
					assert(this.goingUp, "Should already be going up...");
					// walk up parents until we finish or find one with a
					// nextSibling
					if (current != this.terminator) {
						current = current.getParent();
					}
					//if(this.extraArg) dp("Iterator: switching to goingUp=true (nosibling)");
					// if(this.extraArg) dp("Iterator:
					// current="+MathUtil.localName(current));
					this.current = current;
					this.goingUp = true;
				}
			} else {
				while (current != this.terminator && !current.nextSibling()) {
					current = current.getParent();
				}
				;
				if (current == this.terminator) {
					this.current = false;
				} else {
					this.current = current.nextSibling();
				}
				;
			}
		}
		;
		if (this.extraArg) {
			return {
				goingUp : this.goingUp,
				node : this.current
			};
		}
		return this.current;
	};
	this.previous = function() {
		try {
			/* return the previous node */
			if (!this.current) {
				// restart
				this.current = this.node;
			}
			;
			var current = this.current;
			if (this.extraArg) {
				//dp("Iterator.previous goingUp="+this.goingUp + " prevSibling="+current.previousSibling()+" lastChild="+current.lastChild()+" parent="+current.getParent())
				if (this.goingUp) {
					if (current.previousSibling()) {
						this.current = current.previousSibling();
						this.goingUp = false;
					} else {
						this.current = current.getParent();
					}
				} else {
					if (current.lastChild()) {
						this.current = current.lastChild();
					} else {
						//Base case
						this.goingUp = true;
					}
				}
			} else if (current.previousSibling()) {
				current = current.previousSibling();
				while (current.lastChild()) {
					current = current.lastChild();
				}
				this.current = current;
			} else if (current.getParent() == this.terminator) {
				this.current = false;
			} else {
				this.current = current.getParent();
			}
			;
			if (this.extraArg) {
				return {
					goingUp : this.goingUp,
					node : this.current
				};
			}
			return this.current;
		} catch (e) {
			_dpExn(e, "Iterator.previous:");
		}//catch
		return null;/* Just to pacify the type-checker */
	};

	this.reset = function() {
		/* reset the iterator so it starts at the first node */
		this.current = this.node;
	};

	this.setCurrent = function(node) {
		/* change the current node
		    
		    can be really useful for specific hacks, the user must take
		    care that the node is inside the iterator's scope or it will
		    go wild
		 */
		this.current = node;
	};
};

// IE: Used to get the localName
MathUtil.filterPrefix = function(path) {
	path = path.replace(/mml:/g, "");
	path = path.replace(/xsl:/g, "");
	path = path.replace(/mmled:/g, "");
	return path;
}
//IE: Used to get the localName
MathUtil.localName = function(node) {
	assert(node, "localName arg is NULL");
	assert(Node.ELEMENT_NODE == node.nodeType,
			"localName arg is not an element type=" + node.nodeType);
	try {
		if (node.localName)
			return node.localName;
		if (node.tagName) {
			return MathUtil.filterPrefix(node.tagName).toLowerCase();
		}
		return undefined;
	} catch (e) {
		_dpExn(e, "throwing from localName");
		throw e;
	}
};
// IE hack: events are passed via the window, or via srcElement, not via target
MathUtil.target = function(evt) {
	if (!evt)
		return null; // IE can send a null event... weird
	return (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement
			: null);
};
// IE: parsing hack to use xpath and have the namespaces set.
MathUtil.parseFromString = function(str, useDTD) {
	if (useDTD)
		str = "<!DOCTYPE math PUBLIC '-//W3C//DTD MathML 2.0//EN' 'http://www.w3.org/Math/DTD/mathml2/mathml2.dtd'>"
				+ str;
	var doc = new DOMParser().parseFromString(str, "text/xml");
	if (DEBUG_IS_IE && doc != null) {
		doc.setProperty("SelectionLanguage", "XPath");
		var ns = "xmlns:xsl='http://www.w3.org/1999/XSL/Transform' xmlns:mml='"
				+ NS_MATHML + "' xmlns:mmled='" + NS_MMLED + "' xmlns='"
				+ NS_MATHML + "'";
		doc.setProperty("SelectionNamespaces", ns);
	}
	return doc;
}
var xmlSerializer = new XMLSerializer(); // global serializer
MathUtil.xmlToString = function(node) {
	//pretty-print the XML
	var str = xmlSerializer.serializeToString(node);
	try { //try because safari/IE don't have an XML formatter
		return XML(str).toXMLString();
	} catch (e) {
		return str;
	}
}
// ------------------------
// XPath-related helpers
// ------------------------
MathUtil.nsResolver = function(prefix) {
	var ns = {
		'xhtml' : NS_XHTML,
		'mml' : NS_MATHML,
		'xsl' : NS_XSLT,
		'mmled' : NS_MMLED
	};
	return ns[prefix] || null;
};
MathUtil.xpathNode = function(path, parent) {
	assert(parent, "xpathNode null parent");
	if (DEBUG_IS_IE) {
		if ("mml:*|mmled:block" == path) {
			path = "*";
		}
		if (parent.ownerDocument != document) {
			return parent.selectSingleNode(path);
		} else {
			assert(false,
					"Don't know how to call xpath on a non-xmldom node in IE");
		}
	}
	var res = parent.ownerDocument.evaluate(path, parent, MathUtil.nsResolver,
			XPathResult.FIRST_ORDERED_NODE_TYPE, null); // should reuse res
	if (DEBUG_IS_IE) {
		return res.getSingleNodeValue();
	}
	return res.singleNodeValue;
};
// Used to get the number of columns when inserting a matrixrow
MathUtil.xpathNumber = function(path, parent) {
	var res = parent.ownerDocument.evaluate(path, parent, MathUtil.nsResolver,
			XPathResult.NUMBER_TYPE, null); // should reuse res
	return res.numberValue;
};

// ------------------------
// Misc helpers
// ------------------------

// From Prototype.js
// TODO: can't use prototype since MathML isn't HTML, so no .className field
String.prototype.strip = function() {
	return this.replace(/^\s*/, "").replace(/\s*$/, "");
};
MathUtil.hasClassName = function(element, className) {
	assert(element, "Element is null");
	var elementClassName = element.getAttribute("class");
	if (!elementClassName)
		return false;
	// return (elementClassName.length > 0 && (elementClassName == className ||
	// new RegExp("(^|\\s)" + className + "(\\s|$)").test(elementClassName)));
	// For some reason hasClassName didn't work.
	var ary = elementClassName.split(" ");
	for ( var i = 0; i < ary.length; i++) {
		if (className == ary[i])
			return true;
	}
	return false;
};
MathUtil.addClassName = function(element, className) {
	if (!MathUtil.hasClassName(element, className)) {
		element.setAttribute("class", (element.getAttribute("class") ? element
				.getAttribute("class") + ' ' : '')
				+ className);
	}

	if (MathUtil.hasClassName(element, 'highlighted')) {
		element.setAttribute('mathbackground', MathUtil.FF2_HIGHLIGHTED);
	} else if(MathUtil.hasClassName(element, 'cursor')) {
		element.setAttribute('mathbackground', MathUtil.FF2_CURSOR);
	}
	return element;
};
MathUtil.removeClassName = function(element, className) {
	if (!MathUtil.hasClassName(element, className)) {
		return element;
	}
	element.setAttribute("class", element.getAttribute("class").replace(
			new RegExp("(^|\\s+)" + className + "(\\s+|$)"), ' ').strip());
	
	if (MathUtil.hasClassName(element, 'highlighted')) {
		element.setAttribute('mathbackground', MathUtil.FF2_HIGHLIGHTED);
	} else if(MathUtil.hasClassName(element, 'cursor')) {
		element.setAttribute('mathbackground', MathUtil.FF2_CURSOR);
	} else {
		element.removeAttribute('mathbackground');
	}

	return element;
};

MathUtil.getXsl = function(relativePath) {
	assert(MathUtil.rootPath != undefined,
			"MathEditor.rootPath MUST be set to something (in the html file)!");
	// Build an XSLT that just imports. That way the path for subsequent imports
	// is correct.
	var str = '<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">'
			+ '<xsl:import href="'
			+ MathUtil.rootPath
			+ relativePath
			+ '"/>'
			+ '</xsl:stylesheet>';
	var xsl = MathUtil.parseFromString(str, /*ignoreDTD*/true);

	return xsl;
};

MathUtil.forEachElement = function(node, f) {
	if (f(node)) {
		return true;
	}
	for ( var i = 0; i < node.childNodes.length; i++) {
		var child = node.childNodes.item(i);
		if (!Node.ELEMENT_NODE == child.nodeType)
			continue;
		if (MathUtil.forEachElement(child, f)) {
			return true;
		}
	}
	return false;
};