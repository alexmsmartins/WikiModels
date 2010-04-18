/**
 * This Stores information for how to create Content MathML nodes
 */
var MATH_NUMBER = "NUMBER"; // this attribute takes a number (in addition to
// other things)
var MATH_STRING = "STRING";
var MATH_COLOR = "COLOR";

var MATH_BOOL = [ "false", "true" ];
var MATH_LEFTRIGHT = [ "left", "right" ];
var MATH_LCR = [ "left", "center", "right" ];
var MATH_TBCB = [ "top | bottom | center | baseline | axis" ];
var MATH_NAMEDSPACE = [ MATH_NUMBER, "veryverythinmathspace",
		"verythinmathspace", "thinmathspace", "mediummathspace",
		"thickmathspace", "verythickmathspace", "veryverythickmathspace" ];
var MATH_HUNIT = MATH_NUMBER; // TODO fix this
var MATH_VUNIT = MATH_NUMBER; // TODO fix this
function MathPresToken(config) {
	//from MathPres TODO actually inherit
	this.mathsize = [ MATH_NUMBER, "small", "normal", "big" ];
	this.mathcolor = [ MATH_COLOR ];
	this.mathbackground = [ MATH_COLOR ];

	this.mathvariant = [ "normal", "bold", "italic", "bold-italic",
			"double-struck", "bold-fraktur", "script", "bold-script",
			"fraktur", "sans-serif", "bold-sans-serif", "sans-serif-italic",
			"sans-serif-bold-italic", "monospace" ];
	Ext.apply(this, config);
};
MathEditor_Templates = {

	//---------------------------
	// Start of presentation templates
	// ------------------------
	"mi" : {
		name : "mi",
		isToken : true,
		elementType : "private",
		returnType : "any",
		args : [],
		attributes : new MathPresToken()
	},
	"mn" : {
		name : "mn",
		isToken : true,
		elementType : "private",
		returnType : "any",
		args : [],
		attributes : new MathPresToken()
	},
	"mo" : {
		name : "mo",
		isToken : true,
		elementType : "private",
		returnType : "any",
		args : [],
		attributes : new MathPresToken( {
			form : [ "prefix | infix | postfix" ],
			fence : MATH_BOOL,
			separator : MATH_BOOL,
			lspace : MATH_NAMEDSPACE,
			rspace : MATH_NAMEDSPACE,
			stretchy : MATH_BOOL,
			symmetric : MATH_BOOL,
			//maxsize:number [ v-unit | h-unit ] | namedspace | infinity
			// minsize: number [ v-unit | h-unit ] | namedspace
			largeop : MATH_BOOL,
			movablelimits : MATH_BOOL,
			accent : MATH_BOOL
		})
	},
	"mtext" : {
		name : "mtext",
		isToken : true,
		elementType : "private",
		returnType : "any",
		args : [],
		attributes : new MathPresToken()
	},
	"mspace" : {
		name : "mspace",
		isToken : true,
		elementType : "private",
		returnType : "any",
		args : [],
		attributes : {
			width : MATH_NAMEDSPACE,
			height : MATH_VUNIT,
			depth : MATH_VUNIT,
			linebreak : [ "auto | newline | indentingnewline | nobreak | goodbreak | badbreak" ]
		}
	},
	"ms" : {
		name : "ms",
		isToken : true,
		elementType : "private",
		returnType : "any",
		args : [],
		attributes : new MathPresToken( {
			lqote : MATH_STRING,
			rquote : MATH_STRING
		})
	},
	"mglyph" : {
		name : "mglyph",
		isToken : true,
		elementType : "private",
		returnType : "any",
		args : [],
		attributes : new MathPresToken( {
			alt : MATH_STRING,
			fontfamily : MATH_STRING,
			index : MATH_NUMBER
		})
	},
	"mrow" : {
		name : "mrow",
		elementType : "private",
		returnType : "any",
		args : [ "any*" ], //Open Office import ends with empty mrow
		attributes : {}
	},
	"mfrac" : {
		name : "mfrac",
		elementType : "private",
		returnType : "any",
		args : [ "any", "any" ],
		attributes : {
			linethickness : [ MATH_NUMBER/* vunit? */, "thin | medium | thick" ],
			numalign : MATH_LCR,
			denomalign : MATH_LCR,
			bevelled : MATH_BOOL
		}
	},
	"msqrt" : {
		name : "msqrt",
		elementType : "private",
		returnType : "any",
		args : [ "any+" ], //Another mml:mrow is assumed....
		attributes : {}
	},
	"mroot" : {
		name : "mroot",
		elementType : "private",
		returnType : "any",
		args : [ "any", "any" ],
		attributes : {}
	},
	"mstyle" : {
		name : "mstyle",
		elementType : "private",
		returnType : "any",
		args : [ "any+" ],
		attributes : {
			scriptlevel : MATH_NUMBER,
			displaystyle : MATH_BOOL,
			scriptsizemultiplier : MATH_NUMBER,
			scriptminsize : MATH_VUNIT,
			background : MATH_COLOR,
			veryverythinmathspace : MATH_HUNIT,
			verythinmathspace : MATH_HUNIT,
			thinmathspace : MATH_HUNIT,
			mediummathspace : MATH_HUNIT,
			thickmathspace : MATH_HUNIT,
			verythickmathspace : MATH_HUNIT,
			veryverythickmathspace : MATH_HUNIT
		}
	},
	//	"mpadded" : {
	//		name : "mpadded",
	//		elementType : "private",
	//		returnType : "any",
	//		args : [ "any" ],
	//		attributes : {}
	//	},
	"mphantom" : {//TODO change XSLT so phantom's are shown lightly
		name : "mphantom",
		elementType : "private",
		returnType : "any",
		args : [ "any" ],
		attributes : {}
	},
	"mfenced" : {
		name : "mfenced",
		elementType : "private",
		returnType : "any",
		args : [ "any+" ],
		attributes : {
			open : MATH_STRING,
			close : MATH_STRING,
			separators : MATH_STRING
		}
	},
	"menclose" : {
		name : "menclose",
		elementType : "private",
		returnType : "any",
		args : [ "any" ],
		attributes : {
			notation : [ "longdiv | actuarial | radical | box | roundedbox | circle | left | right | top | bottom | updiagonalstrike | downdiagonalstrike | verticalstrike | horizontalstrike" ]
		}
	},
	"msub" : {
		name : "msub",
		elementType : "private",
		returnType : "any",
		args : [ "any", "any" ],
		attributes : {
			subscriptshift : MATH_VUNIT
		}
	},
	"msup" : {
		name : "msup",
		elementType : "private",
		returnType : "any",
		args : [ "any", "any" ],
		attributes : {
			superscriptshift : MATH_VUNIT
		}
	},
	"msubsup" : {
		name : "msubsup",
		elementType : "private",
		returnType : "any",
		args : [ "any", "any", "any" ],
		attributes : {
			subscriptshift : MATH_VUNIT,
			superscriptshift : MATH_VUNIT
		}
	},
	"munder" : {
		name : "munder",
		elementType : "private",
		returnType : "any",
		args : [ "any", "any" ],
		attributes : {
			"accentunder" : MATH_BOOL
		}
	},
	"mover" : {
		name : "mover",
		elementType : "private",
		returnType : "any",
		args : [ "any", "any" ],
		attributes : {
			"accent" : MATH_BOOL
		}
	},
	"munderover" : {
		name : "munderover",
		elementType : "private",
		returnType : "any",
		args : [ "any", "any", "any" ],
		attributes : {
			"accent" : MATH_BOOL,
			"accentunder" : MATH_BOOL
		}
	},
	// "mmultiscripts" : {
	// name : "mmultiscripts",
	// elementType : "private",
	// returnType : "any",
	// args : [ "any+" ],
	// attributes : {
	// subscriptshift : MATH_VUNIT,
	// superscriptshift : MATH_VUNIT
	// }
	// },
	"mtable" : {
		name : "mtable",
		elementType : "private",
		returnType : "any",
		args : [ "mtr+" ],
		attributes : {
			align : MATH_TBCB,
			rowalign : MATH_TBCB,
			//columnalign:MATH_LCR+,
			//groupalign:group-alignment-list-list
			alignmentscope : MATH_BOOL
		// columnwidth:(auto | number h-unit | namedspace | fit) +
		// TODO: The rest of the attributes
		}
	},
	"mtr" : {
		name : "mtr",
		elementType : "private",
		returnType : "mtr",
		args : [ "mtd+" ],
		attributes : {
			rowalign : MATH_TBCB,
			columnalign : MATH_LCR
		// groupalign
		}
	},
	//	"mlabeledtr" : {
	//		name : "mlabeledtr",
	//		elementType : "private",
	//		returnType : "mtr",
	//		args : [ "any", "mtd+" ],
	//		attributes : {
	//			rowalign : MATH_TBCB,
	//			columnalign : MATH_LCR
	// // groupalign
	// }
	// },
	"mtd" : {
		name : "mtd",
		elementType : "private",
		returnType : "mtd",
		args : [ "any*" ], //These CAN be empty (editor uses empty mml:mtd)
		attributes : {
			rowspan : MATH_NUMBER,
			columnspan : MATH_NUMBER,
			rowalign : MATH_TBCB,
			columnalign : MATH_LCR
		}
	},
	"malignmark" : {
		name : "malignmark",
		elementType : "private",
		returnType : "any",
		args : [],
		attributes : {
			edge : MATH_LEFTRIGHT
		}
	},
	"maligngroup" : {
		name : "maligngroup",
		elementType : "private",
		returnType : "any",
		args : [],
		attributes : {
			groupalign : MATH_LCR
		}
	},
	//TODO: Stopped here with PMathML

	// ---------------------------
	// End of presentation templates
	// ------------------------

	"math" : {
		"name" : "math",
		"elementType" : "private",
		"args" : [ "any" ]
	},
	//	"math2" : {
	//		"name" : "math",
	//		"elementType" : "private",
	//		"args" : [ "declare*", "any" ]
	// },
	"declare" : {
		"name" : "declare",
		"elementType" : "private",
		"returnType" : "declare",
		"args" : [],
		"attributes" : {
			"type" : [ "fn" ],
			"nargs" : [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ],
			"occurence" : [ "infix" ]
		}
	},
	"cn" : {
		"name" : "cn",
		isToken : true, //only presentation and csymbol can take arbitrary text
		"elementType" : "private",
		"returnType" : "number",
		//"args" : [ "text-number" ],
		"args" : [],
		"attributes" : {
			"type" : [/* optional */"", "real", "e-notation", "integer",
					"rational", "complex-polar", "complex-cartesian",
					"constant" ]
		}
	},
	// "cn2" : {// TODO handle numbers with a mml:sep
	// "name" : "cn",
	// "elementType" : "private",
	// "returnType" : "number",
	// "args" : [ "text-number", "sep", "text-number" ],
	// "attributes" : {
	// "type" : [/* optional */"", "real", "e-notation", "integer",
	// "rational", "complex-polar", "complex-cartesian",
	// "constant" ]
	// }
	// },
	"sep" : {
		"name" : "sep",
		"elementType" : "private",
		"args" : []
	},
	"ci" : {
		"name" : "ci",
		isToken : true, //only presentation and csymbol can take arbitrary text
		"elementType" : "private",
		"returnType" : "any",
		//"args" : [ "text" ],
		"args" : [ "any?" ],
		"attributes" : {
			"type" : [/* optional */"", "integer", "rational", "real",
					"complex", "complex-polar", "complex-cartesian",
					"constant", "function" ]
		// TODO or-any-content-element
		}
	// When we find a text node, we use the other template.
	},
//	"ci-child" : {
//		"name" : "ci",
//		"elementType" : "private",
//		"returnType" : "any",
//		//"args" : [ "text" ],
//		"args" : [ "any" ],
//		"attributes" : {
//			"type" : [/* optional */"", "integer", "rational", "real",
//					"complex", "complex-polar", "complex-cartesian",
//					"constant", "function", "vector", "matrix" ]
//		// TODO or-any-content-element
//		}
//	}, //HACK There is another template for mml:ci inside the math-node code. 
	/***************************************************************************
	 * The following are special qualifiers
	 **************************************************************************/
	"bvar" : {// TODO this may need special case. only mml:ci for args
		"name" : "bvar",
		"elementType" : "private",
		"returnType" : "bvar",
		"args" : [ "number" ]
	},
	"bvardeg" : {// This case is only for diff and partialdiff
		"name" : "bvar",
		"elementType" : "private",
		"returnType" : "bvardeg",
		"args" : [ "ci", "degree?" ]
	},
	"degree" : {
		"name" : "degree",
		"elementType" : "private",
		"returnType" : "degree",
		"args" : [ "number" ]
	},
	"logbase" : {
		"name" : "logbase",
		"elementType" : "private",
		"returnType" : "logbase",
		"args" : [ "text-number" ]
	},
	"piece" : {
		"name" : "piece",
		"elementType" : "private",
		"returnType" : "piece",
		"args" : [ "number", "bool" ]
	},
	"otherwise" : {
		"name" : "otherwise",
		"elementType" : "private",
		"returnType" : "otherwise",
		"args" : [ "number" ]
	},
	"matrixrow" : {
		"handleSpecial" : "matrixcol-added", // TODO be sure to handle-special
		"name" : "matrixrow",
		"elementType" : "private",
		"returnType" : "matrixrow",
		"args" : [ "number+" ]
	},
	"lowlimit" : { //used for mml:limit
		"name" : "lowlimit",
		"elementType" : "private",
		"returnType" : "lowlimit",
		"args" : [ "number" ]
	},
	/***************************************************************************
	 * The following are used for "domainofapplication"
	 **************************************************************************/
	"interval" : {
		"name" : "interval",
		"elementType" : "private",
		"returnType" : "domainofapplication",
		"args" : [ "number", "number" ],
		"attributes" : {
			"closure" : [/* optional */"", "closed", "open", "open-closed",
					"closed-open" ]
		}
	},
	"conditionDomain" : {
		"name" : "condition",
		"elementType" : "private",
		"returnType" : "domainofapplication",
		"args" : [ "bool" ]
	},
	"domainofapplication" : {
		"name" : "domainofapplication",
		"elementType" : "private",
		"returnType" : "domainofapplication",
		"args" : [ "bool" ]
	},
	"condition" : { // Only the return type is different (for things that ONLY take a condition)
		"name" : "condition",
		"elementType" : "private",
		"returnType" : "condition",
		"args" : [ "bool" ]
	},
	/***********************************************************************
	 * Begin the bulk of MathML
	 **********************************************************************/
	"inverse" : {
		"name" : "inverse",
		"elementType" : "operator",
		"returnType" : "function",
		"args" : [ "function" ]
	},
	"lambda" : {
		"name" : "lambda",
		"elementType" : "operator",
		"returnType" : "function",
		"args" : [ "bvar", "any" ]
	},
	"apply" : {
		"name" : "apply",
		"elementType" : "value",
		"returnType" : "any",
		"args" : [ "any2" ]
	},
	"reln" : { //Deprecated
		"name" : "reln",
		"elementType" : "value",
		"returnType" : "any",
		"args" : [ "any2" ]
	},
	"csymbol" : {//MathType generates this instead of mml:ci
		"name" : "csymbol", //around mml:msub
		isToken : true,
		"elementType" : "value",
		"returnType" : "csymbol",
		"args" : [ "any?" ]
	},
//	"csymbol-only" : {
//		"name" : "csymbol",
//		"elementType" : "value",
//		"returnType" : "csymbol",
//		"args" : [ "any" ]
//	},
	"compose" : {
		"name" : "compose",
		title : "Composition",
		"elementType" : "operator",
		"returnType" : "function",
		"args" : [ "function2" ]
	},
	"ident" : {
		"name" : "ident",
		title : "Identity",
		"elementType" : "value",
		"returnType" : "function"
	},
	"domain" : {
		"name" : "domain",
		"elementType" : "operator",
		"returnType" : "set",
		"args" : [ "function" ]
	},
	"codomain" : {
		"name" : "codomain",
		"elementType" : "operator",
		"returnType" : "set",
		"args" : [ "function" ]
	},
	"image" : {
		"name" : "image",
		"elementType" : "operator",
		"returnType" : "set",
		"args" : [ "function" ]
	},
	"piecewise" : {
		"name" : "piecewise",
		"elementType" : "constructor",
		"returnType" : "function",
		"args" : [ "piece+", "otherwise?" ]
	},
	"factorial" : {
		"name" : "factorial",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"divide" : {
		"name" : "divide",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number", "number" ]
	},
	"max" : {
		"name" : "max",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number+" ]
	},
	"min" : {
		"name" : "min",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number+" ]
	},
	//	"max2" : {
	//		"name" : "max",
	//		"elementType" : "operator",
	//		"returnType" : "number",
	//		"args" : [ "domainofapplication", "number" ]
	// },
	// "min2" : {
	// "name" : "min",
	// "elementType" : "operator",
	// "returnType" : "number",
	// "args" : [ "domainofapplication", "number" ]
	// },
	"max2" : {
		"name" : "max",
		title : "Max (Conditional)",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "bvar", "condition" ]
	},
	"min2" : {
		"name" : "min",
		title : "Min (Conditional)",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "bvar", "condition" ]
	},
	"max3" : {
		"name" : "max",
		title : "Max (Var)",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "bvar", "number" ]
	},
	"min3" : {
		"name" : "min",
		title : "Min (Var)",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "bvar", "number" ]
	},
	"negate" : {
		"name" : "minus",
		title : "Negate",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"minus" : {
		"name" : "minus",
		shortcut : "-",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number", "number" ]
	},
	"plus" : {
		"name" : "plus",
		shortcut : "+",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number2" ]
	},
	"power" : {
		"name" : "power",
		shortcut : "^",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number", "number" ]
	},
	"times" : {
		"name" : "times",
		shortcut : "*",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number2" ]
	},
	"root" : {
		"name" : "root",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "degree?", "number" ]
	},
	"conjugate" : {
		"name" : "conjugate",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"arg" : {
		"name" : "arg",
		title : '"Argument" of Complex',
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"real" : {
		"name" : "real",
		title : "Real Part of Complex",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"imaginary" : {
		"name" : "imaginary",
		title : "Imaginary Part of Complex",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"abs" : {
		"name" : "abs",
		title : "Absolute Value",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"floor" : {
		"name" : "floor",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"ceiling" : {
		"name" : "ceiling",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"quotient" : {
		"name" : "quotient",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number", "number" ]
	},
	"rem" : {
		"name" : "rem",
		title : "Remainder",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number", "number" ]
	},
	"gcd" : {
		"name" : "gcd",
		title : "Greatest Common Divisor",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number2" ]
	},
	"lcm" : {
		"name" : "lcm",
		title : "Least Common Multiple",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number2" ]
	},
	"and" : {
		"name" : "and",
		title : "Logical And",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "bool2" ]
	},
	"or" : {
		"name" : "or",
		title : "Logical Or",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "bool2" ]
	},
	"xor" : {
		"name" : "xor",
		title : "Exclusive Or",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "bool2" ]
	},
	"not" : {
		"name" : "not",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "bool" ]
	},
	"implies" : {
		"name" : "implies",
		shortcut : "-&gt;",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "bool", "bool" ]
	},
	"forall" : {
		"name" : "forall",
		title : "For All",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "bvar*", "condition?", "bool" ]
	// HACK * is so we can match "bvar+,condition?" as well as "condition"
	},
	"exists" : {
		"name" : "exists",
		title : "There Exists",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "bvar*", "condition?", "bool" ]
	},
	"eq" : {
		"name" : "eq",
		title : "Equal",
		shortcut : "=",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "any2" ]
	},
	"neq" : {
		"name" : "neq",
		title : "Not Equal",
		shortcut : "!=",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "any2" ]
	},
	"gt" : {
		"name" : "gt",
		title : "Greater Than",
		shortcut : "&gt;",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "number2" ]
	},
	"lt" : {
		"name" : "lt",
		title : "Less Than",
		shortcut : "&lt;",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "number2" ]
	},
	"geq" : {
		"name" : "geq",
		title : "Greater Than or Equal",
		shortcut : "&gt;=",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "number2" ]
	},
	"leq" : {
		"name" : "leq",
		title : "Less Than or Equal",
		shortcut : "&lt;=",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "number2" ]
	},
	"equivalent" : {
		"name" : "equivalent",
		shortcut : "==",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "any2" ]
	},
	"approx" : {
		"name" : "approx",
		title : "Approximately Equal",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "number2" ]
	},
	"factorof" : {
		"name" : "factorof",
		title : "Factor of",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "number" ]
	},
	"int" : {
		"name" : "int",
		title : "Integral",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "bvar?", "domainofapplication?", "number" ]
	},
	"diff" : {
		"name" : "diff",
		title : "Differentiation",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "bvardeg?", "number" ]
	},
	"diffdegree" : {
		"name" : "diff",
		title : "Differentiation",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "degree?", "number" ]
	},
	"partialdiff" : {
		"name" : "partialdiff",
		title : "Partial Differentiation",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "bvardeg+", "number" ]
	},
	"divergence" : {
		"name" : "divergence",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "function" ]
	},
	"grad" : {
		"name" : "grad",
		title : "Gradient",
		"elementType" : "operator",
		"returnType" : "function",
		"args" : [ "function" ]
	},
	"curl" : {
		"name" : "curl",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "function" ]
	},
	"laplacian" : {
		"name" : "laplacian",
		"elementType" : "operator",
		"returnType" : "any",
		"args" : [ "vector" ]
	},
	"set" : {
		"name" : "set",
		"elementType" : "constructor",
		"returnType" : "set",
		"args" : [ "any*" ],
		"attributes" : {
			"type" : [/* optional */"", "normal", "multiset" ]
		}
	},
	"set2" : {
		"name" : "set",
		title : "Set (Conditional)",
		"elementType" : "constructor",
		"returnType" : "set",
		"args" : [ "bvar", "domainofapplication?" ],
		"attributes" : {
			"type" : [/* optional */"", "normal", "multiset" ]
		}
	},
	"list" : {
		"name" : "list",
		"elementType" : "constructor",
		"returnType" : "set",
		"args" : [ "any*" ],
		"attributes" : {
			"order" : [/* optional */"", "numeric", "lexicographic" ]
		}
	},
	"list2" : {
		"name" : "list",
		title : "List (Conditional)",
		"elementType" : "constructor",
		"returnType" : "set",
		"args" : [ "bvar", "domainofapplication?" ],
		"attributes" : {
			"order" : [/* optional */"", "numeric", "lexicographic" ]
		}
	},
	"union" : {
		"name" : "union",
		"elementType" : "operator",
		"returnType" : "set",
		"args" : [ "set2" ]
	},
	"intersect" : {
		"name" : "intersect",
		title : "Intersection",
		"elementType" : "operator",
		"returnType" : "set",
		"args" : [ "set2" ]
	},
	"in" : {
		"name" : "in",
		title : "Set Inclusion",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "number", "set" ]
	},
	"notin" : {
		"name" : "notin",
		title : "Set Exclusion",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "number", "set" ]
	},
	"subset" : {
		"name" : "subset",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "set2" ]
	},
	"prsubset" : {
		"name" : "prsubset",
		title : "Proper Subset",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "set2" ]
	},
	"notsubset" : {
		"name" : "notsubset",
		title : "Not Subset",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "set", "set" ]
	},
	"notprsubset" : {
		"name" : "notprsubset",
		title : "Not Proper Subset",
		"elementType" : "operator",
		"returnType" : "bool",
		"args" : [ "set", "set" ]
	},
	"setdiff" : {
		"name" : "setdiff",
		title : "Set Difference",
		"elementType" : "operator",
		"returnType" : "set",
		"args" : [ "set", "set" ]
	},
	"card" : {
		"name" : "card",
		title : "Cardinality",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "set" ]
	},
	"cartesianproduct" : {
		"name" : "cartesianproduct",
		title : "Cartesian Product",
		"elementType" : "operator",
		"returnType" : "set",
		"args" : [ "set2" ]
	},
	"sum" : {
		"name" : "sum",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "bvar?", "domainofapplication?", "number" ]
	},
	"sum9" : {
		"name" : "sum",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "bvar2", "domainofapplication", "number" ]
	},
	"product" : {
		"name" : "product",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "bvar?", "domainofapplication?", "number" ]
	},
	"limit" : {
		"name" : "limit",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "bvar", "lowlimit", "number" ]
	},
	"limit2" : {
		"name" : "limit",
		title : "Limit (Conditional)",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "condition", "number" ]
	},
	"tendsto" : {
		"name" : "tendsto",
		title : "Tends To",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number", "number" ],
		"attributes" : {
			"type" : [ "two-sided", "above", "below" ]
		}
	},
	"sin" : {
		"name" : "sin",
		title : "Sine",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"cos" : {
		"name" : "cos",
		title : "Cosine",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"tan" : {
		"name" : "tan",
		title : "Tangent",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"sec" : {
		"name" : "sec",
		title : "Secant",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"csc" : {
		"name" : "csc",
		title : "Cosecant",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"cot" : {
		"name" : "cot",
		title : "Cotangent",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"sinh" : {
		"name" : "sinh",
		title : "Hyperbolic Sine",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"cosh" : {
		"name" : "cosh",
		title : "Hyperbolic Cosine",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"tanh" : {
		"name" : "tanh",
		title : "Hyperbolic Tangent",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"sech" : {
		"name" : "sech",
		title : "Hyperbolic Secant",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"csch" : {
		"name" : "csch",
		title : "Hyperbolic Cosecant",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"coth" : {
		"name" : "coth",
		title : "Hyperbolic Cotangent",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"arcsin" : {
		"name" : "arcsin",
		title : "Arcsine",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"arccos" : {
		"name" : "arccos",
		title : "Arccosine",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"arctan" : {
		"name" : "arctan",
		title : "Arctangent",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"arccosh" : {
		"name" : "arccosh",
		title : "Hyperbolic Arccosine",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"arccot" : {
		"name" : "arccot",
		title : "Arccotangent",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"arccoth" : {
		"name" : "arccoth",
		title : "Hyperbolic Arccotangent",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"arccsc" : {
		"name" : "arccsc",
		title : "Arccosecant",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"arccsch" : {
		"name" : "arccsch",
		title : "Hyperbolic Arccosecant",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"arcsec" : {
		"name" : "arcsec",
		title : "Arcsecant",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"arcsech" : {
		"name" : "arcsech",
		title : "Hyperbolic Arcsecant",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"arcsinh" : {
		"name" : "arcsinh",
		title : "Hyperbolic Arcsine",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"arctanh" : {
		"name" : "arctanh",
		title : "Hyperbolic Arctangent",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"exp" : {
		"name" : "exp",
		title : "Exponential",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"ln" : {
		"name" : "ln",
		title : "Natural Logarithm",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"log" : {
		"name" : "log",
		title : "Logarithm",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "logbase?", "number" ]
	},
	"probability" : {
		"name" : "csymbol",
		"definitionURL" : "http://cnx.rice.edu/cd/cnxmath.ocd#probability",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "domainofapplication?", "bool+" ]
	// used to be domainofapplication, bool+
	},
	"complement" : {
		"name" : "csymbol",
		"definitionURL" : "http://cnx.rice.edu/cd/cnxmath.ocd#complement",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"expectedvalue" : {
		"name" : "csymbol",
		title : "Expected Value",
		"definitionURL" : "http://cnx.rice.edu/cd/cnxmath.ocd#expectedvalue",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "bvar?", "condition?", "number" ]
	},
	"estimate" : {
		"name" : "csymbol",
		"definitionURL" : "http://cnx.rice.edu/cd/cnxmath.ocd#estimate",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"pdf" : {
		"name" : "csymbol",
		title : "Probability Density Function",
		"definitionURL" : "http://cnx.rice.edu/cd/cnxmath.ocd#pdf",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "bvar", "condition?", "number+" ]
	// TODO: is "number" the right type here?
	},
	"pdf2" : {
		"name" : "csymbol",
		title : "Probability Density Function (Conditional)",
		"definitionURL" : "http://cnx.rice.edu/cd/cnxmath.ocd#pdf",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "bvar?", "condition", "number+" ]
	// TODO: is "number" the right type here?
	},
	"cdf" : {
		"name" : "csymbol",
		title : "Cumulative Distribution Function",
		"definitionURL" : "http://cnx.rice.edu/cd/cnxmath.ocd#cdf",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "bvar", "condition?", "number+" ]
	// TODO: is "number" the right type here?
	},
	"cdf2" : {
		"name" : "csymbol",
		title : "Cumulative Distribution Function (Conditional)",
		"definitionURL" : "http://cnx.rice.edu/cd/cnxmath.ocd#cdf",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "bvar?", "condition", "number+" ]
	// TODO: is "number" the right type here?
	},
	"evaluateat" : {
		"name" : "csymbol",
		title : "Evaluate at",
		"definitionURL" : "http://cnx.rice.edu/cd/cnxmath.ocd#evaluateat",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "bvar?", "lowlimit?", "number" ]
	},
	"norm" : {
		"name" : "csymbol",
		"definitionURL" : "http://cnx.rice.edu/cd/cnxmath.ocd#norm",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number" ]
	},
	"norm2" : {
		"name" : "csymbol",
		"definitionURL" : "http://cnx.rice.edu/cd/cnxmath.ocd#norm",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "domainofapplication" ]
	},
	"mean" : {
		"name" : "mean",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number+" ]
	},
	"sdev" : {
		"name" : "sdev",
		title : "Standard Deviation",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number+" ]
	},
	"variance" : {
		"name" : "variance",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number+" ]
	},
	"median" : {
		"name" : "median",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number+" ]
	},
	"mode" : {
		"name" : "mode",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "number+" ]
	},
	"vector" : {
		"name" : "vector",
		"elementType" : "constructor",
		"returnType" : "vector",
		"args" : [ "number+" ]
	},
	"matrix" : {
		"name" : "matrix",
		"elementType" : "constructor",
		"returnType" : "matrix",
		"args" : [ "matrixrow+" ]
	},
	"determinant" : {
		"name" : "determinant",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "matrix" ]
	},
	"transpose" : {
		"name" : "transpose",
		"elementType" : "operator",
		"returnType" : "matrix",
		"args" : [ "matrix" ]
	},
	"selector" : {
		"name" : "selector",
		title : "Selector (Matrix to Number)",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "matrix", "number", "number" ]
	},
	"selector2" : {
		"name" : "selector",
		title : "Selector (Matrix to Vector)",
		"elementType" : "operator",
		"returnType" : "vector",
		"args" : [ "matrix", "number" ]
	},
	"selector3" : {
		"name" : "selector",
		title : "Selector (Vector)",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "vector", "number" ]
	},
	"vectorproduct" : {
		"name" : "vectorproduct",
		title : "Vector Product",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "vector", "vector" ]
	},
	"scalarproduct" : {
		"name" : "scalarproduct",
		title : "Scalar Product",
		"elementType" : "operator",
		"returnType" : "number",
		"args" : [ "vector", "vector" ]
	},
	"outerproduct" : {
		"name" : "outerproduct",
		title : "Outer Product",
		"elementType" : "operator",
		"returnType" : "matrix",
		"args" : [ "vector", "vector" ]
	},
	"integers" : {
		"name" : "integers",
		"elementType" : "value",
		"returnType" : "set"
	},
	"reals" : {
		"name" : "reals",
		"elementType" : "value",
		"returnType" : "set"
	},
	"rationals" : {
		"name" : "rationals",
		title : "Rational Numbers",
		"elementType" : "value",
		"returnType" : "set"
	},
	"naturalnumbers" : {
		"name" : "naturalnumbers",
		title : "Natural Numbers",
		"elementType" : "value",
		"returnType" : "set"
	},
	"complexes" : {
		"name" : "complexes",
		title : "Complex Numbers",
		"elementType" : "value",
		"returnType" : "set"
	},
	"primes" : {
		"name" : "primes",
		title : "Prime Numbers",
		"elementType" : "value",
		"returnType" : "set"
	},
	"exponentiale" : {
		"name" : "exponentiale",
		title : "Exponential e",
		"elementType" : "value",
		"returnType" : "number"
	},
	"imaginaryi" : {
		"name" : "imaginaryi",
		title : "Imaginary i",
		"elementType" : "value",
		"returnType" : "number"
	},
	"notanumber" : {
		"name" : "notanumber",
		title : "Not a Number",
		"elementType" : "value",
		"returnType" : "number"
	},
	"true" : {
		"name" : "true",
		"elementType" : "value",
		"returnType" : "bool"
	},
	"false" : {
		"name" : "false",
		"elementType" : "value",
		"returnType" : "bool"
	},
	"emptyset" : {
		"name" : "emptyset",
		title : "Empty Set",
		"elementType" : "value",
		"returnType" : "set"
	},
	"pi" : {
		"name" : "pi",
		"elementType" : "value",
		"returnType" : "number"
	},
	"eulergamma" : {
		"name" : "eulergamma",
		title : "Euler Gamma",
		"elementType" : "value",
		"returnType" : "number"
	},
	"infinity" : {
		"name" : "infinity",
		"elementType" : "value",
		"returnType" : "number"
	},
	"alpha" : {
		"name" : "alpha",
		"elementType" : "text",
		"symbol" : "\u03b1"
	},
	"beta" : {
		"name" : "beta",
		"elementType" : "text",
		"symbol" : "\u03b2"
	},
	"gamma" : {
		"name" : "gamma",
		"elementType" : "text",
		"symbol" : "\u03b3"
	},
	"delta" : {
		"name" : "delta",
		"elementType" : "text",
		"symbol" : "\u03b4"
	},
	"epsilon" : {
		"name" : "epsilon",
		"elementType" : "text",
		"symbol" : "\u03b5"
	},
	"zeta" : {
		"name" : "zeta",
		"elementType" : "text",
		"symbol" : "\u03b6"
	},
	"eta" : {
		"name" : "eta",
		"elementType" : "text",
		"symbol" : "\u03b7"
	},
	"theta" : {
		"name" : "theta",
		"elementType" : "text",
		"symbol" : "\u03b8"
	},
	"iota" : {
		"name" : "iota",
		"elementType" : "text",
		"symbol" : "\u03b9"
	},
	"kappa" : {
		"name" : "kappa",
		"elementType" : "text",
		"symbol" : "\u03ba"
	},
	"lambda2" : {
		"elementType" : "text",
		"symbol" : "\u03bb"
	},
	"mu" : {
		"elementType" : "text",
		"symbol" : "\u03bc"
	},
	"nu" : {
		"elementType" : "text",
		"symbol" : "\u03bd"
	},
	"xi" : {
		"elementType" : "text",
		"symbol" : "\u03be"
	},
	"omicron" : {
		"elementType" : "text",
		"symbol" : "\u03bf"
	},
	"pi2" : {
		"elementType" : "text",
		"symbol" : "\u03c0"
	},
	"rho" : {
		"elementType" : "text",
		"symbol" : "\u03c1"
	},
	"sigma" : {
		"elementType" : "text",
		"symbol" : "\u03c3"
	},
	"tau" : {
		"elementType" : "text",
		"symbol" : "\u03c4"
	},
	"upsilon" : {
		"elementType" : "text",
		"symbol" : "\u03c5"
	},
	"phi" : {
		"elementType" : "text",
		"symbol" : "\u03c6"
	},
	"chi" : {
		"elementType" : "text",
		"symbol" : "\u03c7"
	},
	"psi" : {
		"elementType" : "text",
		"symbol" : "\u03c8"
	},
	"omega" : {
		"elementType" : "text",
		"symbol" : "\u03c9"
	},
	"agr2" : {
		"elementType" : "text",
		"symbol" : "\u0391"
	},
	"bgr2" : {
		"elementType" : "text",
		"symbol" : "\u0392"
	},
	"gamma2" : {
		"elementType" : "text",
		"symbol" : "\u0393"
	},
	"delta2" : {
		"elementType" : "text",
		"symbol" : "\u0394"
	},
	"egr2" : {
		"elementType" : "text",
		"symbol" : "\u0395"
	},
	"zgr2" : {
		"elementType" : "text",
		"symbol" : "\u0396"
	},
	"eegr2" : {
		"elementType" : "text",
		"symbol" : "\u0397"
	},
	"theta2" : {
		"elementType" : "text",
		"symbol" : "\u0398"
	},
	"igr2" : {
		"elementType" : "text",
		"symbol" : "\u0399"
	},
	"kgr2" : {
		"elementType" : "text",
		"symbol" : "\u039a"
	},
	"lambda2" : {
		"elementType" : "text",
		"symbol" : "\u039b"
	},
	"mgr2" : {
		"elementType" : "text",
		"symbol" : "\u039c"
	},
	"ngr2" : {
		"elementType" : "text",
		"symbol" : "\u039d"
	},
	"xi2" : {
		"elementType" : "text",
		"symbol" : "\u039e"
	},
	"ogr2" : {
		"elementType" : "text",
		"symbol" : "\u039f"
	},
	"pi2" : {
		"elementType" : "text",
		"symbol" : "\u03a0"
	},
	"rgr2" : {
		"elementType" : "text",
		"symbol" : "\u03a1"
	},
	"sigma2" : {
		"elementType" : "text",
		"symbol" : "\u03a3"
	},
	"tgr2" : {
		"elementType" : "text",
		"symbol" : "\u03a4"
	},
	"upsi2" : {
		"elementType" : "text",
		"symbol" : "\u03a5"
	},
	"phi2" : {
		"elementType" : "text",
		"symbol" : "\u03a6"
	},
	"khgr2" : {
		"elementType" : "text",
		"symbol" : "\u03a7"
	},
	"psi2" : {
		"elementType" : "text",
		"symbol" : "\u03a8"
	},
	"omega2" : {
		"elementType" : "text",
		"symbol" : "\u03a9"
	},
	"plankv" : {
		"elementType" : "text",
		"symbol" : "\u210f"
	},
	"infin" : {
		"elementType" : "text",
		"symbol" : "\u221e"
	},
	"deg" : {
		"elementType" : "text",
		"symbol" : "\u00b0"
	}
};
