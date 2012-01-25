package pt.cnbc.wikimodels.mathparser

import pt.cnbc.wikimodels.mathml.elements._
import KnownOperators._
import util.parsing.combinator.{RegexParsers, PackratParsers}

class AsciiMathParser extends RegexParsers with PackratParsers with AsciiMathParserHandlers {

  type MME = MathMLElem

  //check http://rwiki.sciviews.org/doku.php?id=wiki:asciimathml#standard_functions to handle certain cases
  lazy val LambdaExpr :PackratParser[MME]= "("~repsep(ident,",")~")" ~ "=" ~> Expr

  lazy val GenExpr    :PackratParser[MME]= Relation | Expr //| Logical

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
  lazy val Relation :PackratParser[MME]=   Expr~"=="~Expr^^{case e1~"=="~e2 => Apply(Eq, e1 :: e2 :: Nil)} | //4.4.4.1 Equals (eq)
                                           Expr~"!="~Expr^^{case e1~"!="~e2 => Apply(Neq, e1 :: e2 :: Nil)} | //4.4.4.2 Not Equals (neq)
                                           Expr~">"~Expr^^{case e1~">"~e2 => Apply(Gt, e1 :: e2 :: Nil)}  | //4.4.4.3 Greater than (gt)
                                           Expr~"<"~Expr^^{case e1~"<"~e2 => Apply(Lt, e1 :: e2 :: Nil)}  | //4.4.4.4 Less Than (lt)
                                           Expr~">="~Expr^^{case e1~">="~e2 => Apply(Geq, e1 :: e2 :: Nil)}  | //4.4.4.5 Greater Than or Equal (geq)
                                           Expr~"<="~Expr^^{case e1~"<="~e2 => Apply(Leq, e1 :: e2 :: Nil)}   //4.4.4.6 Less Than or Equal (leq)


  //lazy val Logical :

  //-- these methods where taken from JavaTokenParser and adapted to this grammars needs

  def ident: Parser[String] =
    """[a-zA-Z_]\w*""".r
  def wholeNumber: Parser[String] =
    """-?\d+""".r
  def decimalNumber: Parser[String] =
    """-?\d+\.\d+""".r

  def floatingPointNumber: Parser[String] =
    """-?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?[fFdD]?""".r

  //TODO exclude words in this list from the "ident" regular expression
  val reservedWords = "true" :: "false" :: Nil

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
