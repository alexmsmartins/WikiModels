package pt.cnbc.wikimodels.mathparser

import pt.cnbc.wikimodels.mathml.elements._
import KnownOperators._
import util.parsing.combinator.{RegexParsers, PackratParsers}

class AsciiMathParser extends RegexParsers with PackratParsers with AsciiMathParserHandlers {

  type MME = MathMLElem

  //check http://rwiki.sciviews.org/doku.php?id=wiki:asciimathml#standard_functions to handle certain cases
  lazy val LambdaExpr :PackratParser[MME]= "("~repsep(ident,",")~")" ~ "=" ~> Expr


  lazy val GenExpr    :PackratParser[MME]= Relation | Expr

  lazy val Expr       :PackratParser[MME]= Expr~"+"~Term^^{ case e~"+"~t => new Apply(Addition, List(e, t))  } |
                                           Expr~"-"~Term^^{ case e~"-"~t => new Apply(Subtraction, List(e, t)  ) } |
                                           Term

  lazy val Term       :PackratParser[MME]= Term~"*"~Factor^^{ case t~"*"~f => new Apply(Multiplication, List(t, f))  } |
                                           Term~"/"~Factor^^{ case t~"/"~f => new Apply(Division, List(t, f))  } |
                                           Factor

  lazy val Factor     :PackratParser[MME]= Power | Function | Atom | "("~>Expr<~")"

  lazy val Function   :PackratParser[MME]= ident~"("~Parameters~")"^^{ case i~"("~par~")" => {handleFunction(i, par)}}

  lazy val Parameters :PackratParser[List[MME]]= repsep(Expr,",") //no need to put anything else here

  lazy val Power      :PackratParser[MME]= Base~"^"~Exponent^^{ case b~"^"~e => new Apply(Exponentiation,  List(b, e) ) }

  lazy val Base       :PackratParser[MME]= Function | Atom | "("~>Expr<~")"

  lazy val Exponent   :PackratParser[MME]= Function |
                                           Atom |
                                           "("~>Expr<~")"

  //in this rule, parser order is important
  lazy val Atom       :PackratParser[MME]= (decimalNumber|wholeNumber)~"e"~wholeNumber^^{case x~"e"~y => new Cn(x::y::Nil, "e-notation")} |
                                           (decimalNumber|wholeNumber)~"E"~wholeNumber^^{case x~"E"~y => new Cn(x::y::Nil, "e-notation")} |
                                           decimalNumber^^(x => new Cn(x::Nil, "real")) |
                                           wholeNumber^^{x => new Cn(x::Nil, "integer")} |
                                           ident^^(x => new Ci(x))

  //relation  MathML specification section 4.4.4 Relations
  lazy val Relation   :Parser[MME]= (Expr~"=="~>Expr) | //4.4.4.1 Equals (eq)
                                           (Expr~"!="~>Expr) | //4.4.4.2 Not Equals (neq)
                                           (Expr~">"~>Expr) | //4.4.4.3 Greater than (gt)
                                           (Expr~"<"~>Expr) | //4.4.4.4 Less Than (lt)
                                           (Expr~">="~>Expr) | //4.4.4.5 Greater Than or Equal (geq)
                                           (Expr~"<="~>Expr) | //4.4.4.6 Less Than or Equal (leq)
                                           (Expr~"_="~>Expr) | //4.4.4.7 Equivalent (equivalent)
                                           (Expr~"~="~>Expr)  //4.4.4.8 Approximately (approx)

  //-- these methods where taken from JavaTokenParser and adapted to this grammars needs

  def ident: Parser[String] =
    """[a-zA-Z_]\w*""".r
  def wholeNumber: Parser[String] =
    """-?\d+""".r
  def decimalNumber: Parser[String] =
    """-?\d+\.\d+""".r

  def floatingPointNumber: Parser[String] =
    """-?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?[fFdD]?""".r



}
object AsciiMathParser{
  def apply() = new AsciiMathParser()
}

trait AsciiMathParserHandlers{
  def handleFunction(ident:String, params:List[MathMLElem] ):MathMLElem = {
    println("Operator.validOps contains " + KnownOperators.validOps.size + " predefined operators")
    new Apply(KnownOperators.validOps.getOrElse(ident, new CSymbol(ident) ), params)
  }
}
