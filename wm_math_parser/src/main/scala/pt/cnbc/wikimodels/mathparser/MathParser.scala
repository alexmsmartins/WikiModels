package pt.cnbc.wikimodels.mathparser

import pt.cnbc.wikimodels.mathml.elements._
import KnownOperators._
import util.parsing.combinator.{RegexParsers, PackratParsers, JavaTokenParsers}

/**
 */

class MathParser extends RegexParsers with PackratParsers with MathParserHandlers {

  type MME = MathMLElem

  //check http://rwiki.sciviews.org/doku.php?id=wiki:asciimathml#standard_functions to handle certain cases
  lazy val LambdaExpr :PackratParser[Any]= Function ~ "=" ~ Expr

  //TODO def SimpleExpr  :Parser[Any]= Atom |   
  lazy val Expr       :PackratParser[MathMLElem]= Expr~"+"~Term^^{ case e~"+"~t => new Apply(Addition, List(e, t))  } |
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

  //in this rule, parser order is importatnt
  lazy val Atom       :PackratParser[MME]= decimalNumber~"e"~decimalNumber^^{case x~"E"~y => new Cn(x::y::Nil, "e-notation")} |
                                           decimalNumber^^(x => new Cn(x::Nil, "real")) |
                                           wholeNumber^^{x => new Cn(x::Nil, "integer")} |
                                           ident^^(x => new Ci(x))

  //-- these methods where taken from JAvaTokenParser and adapted to this grammars needs

  def ident: Parser[String] =
    """[a-zA-Z_]\w*""".r
  def wholeNumber: Parser[String] =
    """-?\d+""".r
  def decimalNumber: Parser[String] =
    """-?\d+\.\d+""".r

  def floatingPointNumber: Parser[String] =
    """-?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?[fFdD]?""".r



}
object MathParser{
  def apply() = new MathParser()
}

trait MathParserHandlers{
  def handleFunction(ident:String, params:List[MathMLElem] ):MathMLElem = {
    println("Operator.validOps contains " + KnownOperators.validOps.size + " predefined operators")
    KnownOperators.validOps.getOrElse(ident, new Apply(new CSymbol(ident), params ))
  }
}
