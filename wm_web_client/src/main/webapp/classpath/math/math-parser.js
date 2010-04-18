/**
 * Utility class that parses a string into Content MathML with type annotations.
 * It uses Model to create Content MathML nodes and returns a MathNode.
 * 
 * Requires JSON and tdop.js
 * 
 * @param {Model}
 *            model
 * @param {String}
 *            stringToParse
 * @return
 */
function MathParser(model, stringToParse, optContext, holeOnLeft) {
	this.model = model;
	var ret;
	if (optContext) {
		var withHole = holeOnLeft ? "(" + MathParser._PARSE_EXPRESSION_HOLE
				+ ")" + stringToParse : stringToParse + "("
				+ MathParser._PARSE_EXPRESSION_HOLE + ")";
		ret = this._parse(withHole, optContext);
	} else {
		// Try to parse it without contextual information
		ret = this._parse(stringToParse);
	}
	this._math = ret.node;
	this._error = ret.error;
	this._usedContext = ret.usedContext;
};

MathParser._PARSE_EXPRESSION_HOLE = "thisisaholeidentifier";

MathParser.prototype._parse = function(stringToParse, optContext) {
	try {
		var parse = make_parse();
		var tree = parse("var make_parse = " + stringToParse + ";");
		if (tree) {
			if ("SyntaxError" != tree.name) {
				var ret = this._convertTreeToMath(tree.second, optContext);
				return ret;
			}
		}
	} catch (e) {
		getConsole().dir(e);
	}
	return {
		error : "Could not parse"
	};
};

MathParser.prototype.getMathNode = function() {
	return this._math;
};
MathParser.prototype.getError = function() {
	return this._error;
};
MathParser.prototype.hasError = function() {
	return this.getError() ? true : false;
};
MathParser.prototype.usedContext = function() {
	return this._usedContext ? true : false;
}

/**
 * Converts a parseTree (from tdop.js) into a MathNode (with children)
 * 
 * @param {Object}
 *            tree
 * @param {MathNode}
 *            optConext an optional context to include for strings like "+2"
 * @return the parsed MathNode
 * @type {node:MathNode,err:String}
 */
MathParser.prototype._convertTreeToMath = function(tree, optContext) {
	var ret;
	var canCollapse = false; // changed in the cases
	var op;
	var usedContext = false;
	switch (tree.arity) {
	case "name":
		//If it's the PARSE_EXPRESSION_HOLE then just create a block
		if (MathParser._PARSE_EXPRESSION_HOLE == tree.value) {
			if (optContext) {
				ret = optContext;
				usedContext = true;
			} else {
				return {
					error : "Context Problem"
				};
			}
		}
		//either it's a variable, or a template
		else if (this.model.canBuildMathNode(tree.value)) {
			ret = this.model.buildMathNode(tree.value);
		} else {
			//It's a variable, so create a <ci/>
			ret = this.model.buildMathNode("ci");
			// The variable MAY have a subscript, like k_2. If so, create
			// <mml:msub>
			if (tree.value.match("_")) {
				var msub = this.model.buildMathNode("msub");
				var top = this.model.buildMathNode("mi");
				var bot = this.model.buildMathNode("mi");
				var beforeUnderscore = tree.value.substring(0, tree.value
						.indexOf("_"));
				var afterUnderscore = tree.value.substring(tree.value
						.indexOf("_") + 1);
				top.setChildAsText(beforeUnderscore);
				bot.setChildAsText(afterUnderscore);
				var msubBlocks = msub.getChildren();
				msub.replaceChildWith(msubBlocks[0], top);
				msub.replaceChildWith(msubBlocks[1], bot);
				ret.replaceChildWith(ret.getChildren()[0], msub);
			} else {
				ret.replaceChildWithText(ret.getChildren()[0], tree.value);
			}
		}
		break;
	case "literal":
		//It's a number, so create a <cn/>
		ret = this.model.buildMathNode("cn");
		var text = tree.value + ''; // need to convert 0's to a string.
									// otherwise treated as null
		ret.text = text;
		break;

	case "unary":
		switch (tree.value) {
		case "-":
			op = "negate";
			break;
		case "!":
			op = "not";
			break;
		// Classical functions
		case "sin": //fall through
		case "cos": //fall through
		case "tan": //fall through
		case "sec": //fall through
		case "csc": //fall through
		case "cot": //fall through
		case "sinh": //fall through
		case "cosh": //fall through
		case "tanh": //fall through
		case "sech": //fall through
		case "csch": //fall through
		case "coth": //fall through
		case "arcsin": //fall through
		case "arccos": //fall through
		case "arctan": //fall through
		case "arccosh": //fall through
		case "arccot": //fall through
		case "arccoth": //fall through
		case "arccsc": //fall through
		case "arccsch": //fall through
		case "arcsec": //fall through
		case "arcsech": //fall through
		case "arcsinh": //fall through
		case "arctanh": //fall through
		case "exp": //fall through 
		case "ln": //fall through
		case "log":
			op = tree.value;
			break;
		default:
			return {
				error : "unary op not supported:" + tree.value
			};
		}
		ret = this.model.buildMathNode(op);
		var arg = this._convertTreeToMath(tree.first, optContext);
		// propagate the error, or continue
		if (arg.error) {
			return arg;
		} else {
			arg = arg.node;
		}
		// Append the child
		ret.replaceChildWith(ret.getChildren()[0], arg);
		break;
	case "binary":
		switch (tree.value) {
		case "+":
			op = "plus";
			canCollapse = true;
			break;
		case "-":
			op = "minus";
			break;
		case "*":
			op = "times";
			canCollapse = true;
			break;
		case "/":
			op = "divide";
			break;
		case "^":
			op = "power";
			break;
		// Algebra
		case "%":
			op = "rem";
			break;
		// Relations
		case "<":
			op = "lt";
			canCollapse = true;
			break;
		case ">":
			op = "gt";
			canCollapse = true;
			break;
		case "<=":
			op = "leq";
			canCollapse = true;
			break;
		case ">=":
			op = "geq";
			canCollapse = true;
			break;
		case "=":
			op = "eq";
			canCollapse = true;
			break;
		case "==":
			op = "equivalent";
			canCollapse = true;
			break;
		case "!=":
			op = "neq";
			canCollapse = true;
			break;
		case "~":
			op = "approx";
			canCollapse = true;
			break;
		case "&&":
			op = "and";
			canCollapse = true;
			break;
		case "||":
			op = "or";
			canCollapse = true;
			break;
		case "|":
			op = "factorof";
			break;
		case "=>":
			op = "implies";
			break;
		// Set stuff
		case "union":
			op = tree.value;
			break;
		case "intersect":
			op = tree.value;
			break;
		case "in":
			op = tree.value;
			break;
		// case "!in": //fall through
		case "notin":
			op = tree.value;
			break;
		case "subset":
			op = tree.value;
			break;
		case "prsubset":
			op = tree.value;
			break;
		// case "!subset": //fall through
		case "notsubset":
			op = tree.value;
			break;
		// case "!prsubset": //fall through
		case "notprsubset":
			op = tree.value;
			break;
		case "\\":
			op = "setdiff";
			break;
		// Sequences and Series
		case "->":
			op = "tendsto";
			break;
		default:
			return {
				error : "binary op not supported:" + tree.value
			};
		}
		ret = this.model.buildMathNode(op);
		var block1 = ret.getChildren()[0];
		var block2 = ret.getChildren()[1];
		var ret1 = this._convertTreeToMath(tree.first, optContext);
		var ret2 = this._convertTreeToMath(tree.second, optContext);
		// propagate the error, or continue
		if (ret1.error) {
			return ret1;
		} else {
			var arg1 = ret1.node;
		}
		// propagate the error, or continue
		if (ret2.error) {
			return ret2;
		} else {
			var arg2 = ret2.node;
		}
		usedContext = ret1.usedContext || ret2.usedContext;
		// Add the children
		// As a precaution, we add these backwards because of congealing (adding
		// a mml:plus as the 1st child of a mml:plus will cause the second block
		// to move)
		ret.replaceChildWith(block2, arg2);
		ret.replaceChildWith(block1, arg1);
		break;
	default:
		return {
			error : "case in _convertTreeToMath not found for:" + tree.arity
		};
	}
	assert(ret, "should have defined ret to be something");
	return {
		node : ret,
		usedContext : usedContext
	};
};