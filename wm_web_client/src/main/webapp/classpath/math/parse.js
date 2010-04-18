// parse.js
// Parser for Simplified JavaScript written in Simplified JavaScript
// From Top Down Operator Precedence
// http://javascript.crockford.com/tdop/index.html
// Douglas Crockford
// 2008-02-19

/*members create, error, message, name, prototype, stringify, toSource,
    toString, write
*/

/*global JSON, make_parse, parse, source, tree */

// Make a new object that inherits members from an existing object.

function create(o) {
        function F() {}
        F.prototype = o;
        return new F();
}

var make_parse = function () {
    var scope;
    var symbol_table = {};
    var token;
    var tokens;
    var token_nr;

    var itself = function () {
        return this;
    };

    // Transform a token object into an exception object and throw it.
    var error = function(t, msg) {
      t.name = "SyntaxError";
      t.message = msg;
      throw t;
    };

    var original_scope = {
        define: function (n) {
            var t = this.def[n.value];
            if (typeof t === "object") {
                error(n, t.reserved ? "Already reserved." : "Already defined.");
            }
            this.def[n.value] = n;
            n.reserved = false;
            n.nud      = itself;
            n.led      = null;
            n.std      = null;
            n.lbp      = 0;
            n.scope    = scope;
            return n;
        },
        find: function (n) {
            var e = this, o;
            while (true) {
                o = e.def[n];
                if (o) {
                    return o;
                }
                e = e.parent;
                if (!e) {
                    return symbol_table[symbol_table.hasOwnProperty(n) ?
                        n : "(name)"];
                }
            }
        },
        pop: function () {
            scope = this.parent;
        },
        reserve: function (n) {
            if (n.arity !== "name" || n.reserved) {
                return;
            }
            var t = this.def[n.value];
            if (t) {
                if (t.reserved) {
                    return;
                }
                if (t.arity === "name") {
                    error(t, "Already defined.");
                }
            }
            this.def[n.value] = n;
            n.reserved = true;
        }
    };

    var new_scope = function () {
        var s = scope;
        scope = create(original_scope);
        scope.def = {};
        scope.parent = s;
        return scope;
    };

    var advance = function (id) {
        var a, o, t, v;
        if (id && token.id !== id) {
            error(token, "Expected '" + id + "'.");
        }
        if (token_nr >= tokens.length) {
            token = symbol_table["(end)"];
            return;
        }
        t = tokens[token_nr];
        token_nr += 1;
        v = t.value;
        a = t.type;
        if (a === "name") {
            o = scope.find(v);
        } else if (a === "operator") {
            o = symbol_table[v];
            if (!o) {
                error(t, "Unknown operator.");
            }
        } else if (a === "string" || a ===  "number") {
            o = symbol_table["(literal)"];
            a = "literal";
        } else {
            error(t, "Unexpected token.");
        }
        token = create(o);
        token.from  = t.from;
        token.to    = t.to;
        token.value = v;
        token.arity = a;
        return token;
    };

    var expression = function (rbp) {
        var left;
        var t = token;
        advance();
        left = t.nud();
        while (rbp < token.lbp) {
            t = token;
            advance();
            left = t.led(left);
        }
        return left;
    };

    var statement = function () {
        var n = token, v;

        if (n.std) {
            advance();
            scope.reserve(n);
            return n.std();
        }
        v = expression(0);
        if (!v.assignment && v.id !== "(") {
            error(v, "Bad expression statement.");
        }
        advance(";");
        return v;
    };

    var statements = function () {
        var a = [], s;
        while (true) {
            if (token.id === "}" || token.id === "(end)") {
                break;
            }
            s = statement();
            if (s) {
                a.push(s);
            }
        }
        return a.length === 0 ? null : a.length === 1 ? a[0] : a;
    };

    var block = function () {
        var t = token;
        advance("{");
        return t.std();
    };

    var original_symbol = {
        nud: function () {
            //this.error("Undefined.");
            return this;
        },
        led: function (left) {
            error(this, "Missing operator.");
        }
    };

    var symbol = function (id, bp) {
        var s = symbol_table[id];
        bp = bp || 0;
        if (s) {
            if (bp >= s.lbp) {
                s.lbp = bp;
            }
        } else {
            s = create(original_symbol);
            s.id = s.value = id;
            s.lbp = bp;
            symbol_table[id] = s;
        }
        return s;
    };

    var constant = function (s, v) {
        var x = symbol(s);
        x.nud = function () {
            scope.reserve(this);
            this.value = symbol_table[this.id].value;
            this.arity = "literal";
            return this;
        };
        x.value = v;
        return x;
    };

    var infix = function (id, bp, led) {
        var s = symbol(id, bp);
        s.led = led || function (left) {
            this.first = left;
            this.second = expression(bp);
            this.arity = "binary";
            return this;
        };
        return s;
    };

    var infixr = function (id, bp, led) {
        var s = symbol(id, bp);
        s.led = led || function (left) {
            this.first = left;
            this.second = expression(bp - 1);
            this.arity = "binary";
            return this;
        };
        return s;
    };

    var assignment = function (id) {
        return infixr(id, 10, function (left) {
            if (left.id !== "." && left.id !== "[" && left.arity !== "name") {
                error(left, "Bad lvalue.");
            }
            this.first = left;
            this.second = expression(9);
            this.assignment = true;
            this.arity = "binary";
            return this;
        });
    };

    var prefix = function (id, nud) {
        var s = symbol(id);
        s.nud = nud || function () {
            scope.reserve(this);
            this.first = expression(70);
            this.arity = "unary";
            return this;
        };
        return s;
    };

    var stmt = function (s, f) {
        var x = symbol(s);
        x.std = f;
        return x;
    };

    symbol("(end)");
    symbol("(name)");
    symbol(";");
    symbol(")");
    symbol(",");

    symbol("(literal)").nud = itself;

    symbol("this").nud = function () {
        scope.reserve(this);
        this.arity = "this";
        return this;
    };

    //assignment("=");
    infixr("=", 10);

    infixr("&&", 30);
    infixr("||", 30);
    infixr("|", 30); //factorof
    infix("=>", 30); //implies

    infixr("==", 10);
    infixr("!=", 10);
    infixr("~", 10);
    infixr("<", 40);
    infixr("<=", 40);
    infixr(">", 40);
    infixr(">=", 40);
    infix("%", 40);//modulus correct precedence?

    //Sets
    infixr("union", 40);
    infixr("intersect", 40);
    infixr("in", 40);
    infixr("notin", 40);
    infixr("subset", 40);
    infixr("prsubset", 40);
    infixr("notsubset", 40);
    infixr("notprsubset", 40);
    infix("\\", 40); //setdiff
    //infixr("!in", 40); //Not supported (because of "!" token)
    //infixr("!subset", 40); //Not supported (because of "!" token)
    //infixr("!prsubset", 40); //Not supported (because of "!" token)

    //Sequences and Series
    infix("->", 30); //tendsto

    //Classical functions
    prefix("sin");
    prefix("cos");
    prefix("tan");
    prefix("sec");
    prefix("csc");
    prefix("cot");
    prefix("sinh");
    prefix("cosh");
    prefix("tanh");
    prefix("sech");
    prefix("csch");
    prefix("coth");
    prefix("arcsin");
    prefix("arccos");
    prefix("arctan");
    prefix("arccosh");
    prefix("sin");
    prefix("cos");
    prefix("tan");
    prefix("sec");
    prefix("csc");
    prefix("cot");
    prefix("sinh");
    prefix("cosh");
    prefix("tanh");
    prefix("sech");
    prefix("csch");
    prefix("coth");
    prefix("arcsin");
    prefix("arccos");
    prefix("arctan");
    prefix("arccosh");
    prefix("arccot");
    prefix("arccoth");
    prefix("arccsc");
    prefix("arccsch");
    prefix("arcsec");
    prefix("arcsech");
    prefix("arcsinh");
    prefix("arctanh");
    prefix("exp");
    prefix("ln");
    prefix("log");
    
    infix("+", 50);
    infix("-", 50);

    infix("*", 60);
    infix("/", 60);
    infix("^", 65);

    infix("(", 80, function (left) {
        var a = [];
        if (left.id === "." || left.id === "[") {
            this.arity = "ternary";
            this.first = left.first;
            this.second = left.second;
            this.third = a;
        } else {
            this.arity = "binary";
            this.first = left;
            this.second = a;
            if ((left.arity !== "unary" || left.id !== "function") &&
                    left.arity !== "name" && left.id !== "(" &&
                    left.id !== "&&" && left.id !== "||" && left.id !== "?") {
                error(left, "Expected a variable name.");
            }
        }
        if (token.id !== ")") {
            while (true)  {
                a.push(expression(0));
                if (token.id !== ",") {
                    break;
                }
                advance(",");
            }
        }
        advance(")");
        return this;
    });

    prefix("-");
    prefix("!"); //not

    prefix("(", function () {
        var e = expression(0);
        advance(")");
        return e;
    });

    stmt("var", function () {
        var a = [], n, t;
        while (true) {
            n = token;
            if (n.arity !== "name") {
                error(n, "Expected a new variable name.");
            }
            scope.define(n);
            advance();
            if (token.id === "=") {
                t = token;
                advance("=");
                t.first = n;
                t.second = expression(0);
                t.arity = "binary";
                a.push(t);
            }
            if (token.id !== ",") {
                break;
            }
            advance(",");
        }
        advance(";");
        return a.length === 0 ? null : a.length === 1 ? a[0] : a;
    });

    return function (source) {
        tokens = source.tokens('=<>!+-*&|/%^', '=<>&|');
        token_nr = 0;
        new_scope();
        advance();
        var s = statements();
        advance("(end)");
        scope.pop();
        return s;
    };
};
