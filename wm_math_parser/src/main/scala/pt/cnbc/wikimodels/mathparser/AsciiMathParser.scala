package pt.cnbc.wikimodels.mathparser

import pt.cnbc.wikimodels.mathml.elements._
import KnownOperators._
import util.parsing.combinator.{RegexParsers, PackratParsers}

class AsciiMathParser extends RegexParsers with PackratParsers with AsciiMathParserHandlers {

  type MME = MathMLElem

  //check http://rwiki.sciviews.org/doku.php?id=wiki:asciimathml#standard_functions to handle certain cases
  lazy val LambdaExpr :PackratParser[MME]= "("~repsep(ident,",")~")" ~ "=" ~> GenExpr

  lazy val GenExpr    :PackratParser[MME]= NumExpr | TruthValue

  lazy val NumExpr       :PackratParser[MME]= NumExpr~"+"~Term^^{ case e~"+"~t => new Apply(Addition, List(e, t))  } |
                                           NumExpr~"-"~Term^^{ case e~"-"~t => new Apply(Subtraction, List(e, t)  ) } |
                                           Term

  lazy val Term       :PackratParser[MME]= Term~"*"~Factor^^{ case t~"*"~f => new Apply(Multiplication, List(t, f))  } |
                                           Term~"/"~Factor^^{ case t~"/"~f => new Apply(Division, List(t, f))  } |
                                           Factor

  lazy val Factor     :PackratParser[MME]= Power | Function | Atom | "("~>NumExpr<~")"

  lazy val Function   :PackratParser[MME]= ident~"("~Parameters~")"^^{ case i~"("~par~")" => {handleFunction(i, par)}}

  lazy val Parameters :PackratParser[List[MME]]= repsep(NumExpr,",") //no need to put anything else here

  lazy val Power      :PackratParser[MME]= Base~"^"~Exponent^^{ case b~"^"~e => new Apply(Exponentiation,  List(b, e) ) }

  lazy val Base       :PackratParser[MME]= Function | Atom | "("~>NumExpr<~")"

  lazy val Exponent   :PackratParser[MME]= Function |
                                           Atom |
                                           "("~>NumExpr<~")"

  //in this rule, parser order is important
  lazy val Atom       :PackratParser[MME]= (decimalNumber|wholeNumber)~"e"~wholeNumber^^{case x~"e"~y => new Cn(x::y::Nil, "e-notation")} |
                                           (decimalNumber|wholeNumber)~"E"~wholeNumber^^{case x~"E"~y => new Cn(x::y::Nil, "e-notation")} |
                                           decimalNumber^^(x => new Cn(x::Nil, "real")) |
                                           wholeNumber^^{x => new Cn(x::Nil, "integer")} |
                                           ident^^(x => new Ci(x))

  lazy val TruthValue:PackratParser[MME]= Logic | Relation
  //relation  MathML specification section 4.4.4 Relations
  lazy val Relation :PackratParser[MME]=   GenExpr~"=="~GenExpr^^{case e1~"=="~e2 => Apply(Eq, e1 :: e2 :: Nil)} | //4.4.4.1 Equals (eq)
    GenExpr~"!="~GenExpr^^{case e1~"!="~e2 => Apply(Neq, e1 :: e2 :: Nil)} | //4.4.4.2 Not Equals (neq)
    GenExpr~">"~GenExpr^^{case e1~">"~e2 => Apply(Gt, e1 :: e2 :: Nil)}  | //4.4.4.3 Greater than (gt)
    GenExpr~"<"~GenExpr^^{case e1~"<"~e2 => Apply(Lt, e1 :: e2 :: Nil)}  | //4.4.4.4 Less Than (lt)
    GenExpr~">="~GenExpr^^{case e1~">="~e2 => Apply(Geq, e1 :: e2 :: Nil)}  | //4.4.4.5 Greater Than or Equal (geq)
    GenExpr~"<="~GenExpr^^{case e1~"<="~e2 => Apply(Leq, e1 :: e2 :: Nil)}   //4.4.4.6 Less Than or Equal (leq)


  lazy val Logic:PackratParser[MME]= TruthValue~"""/\"""~TruthValue^^{case e1~"""/\"""~e2 => Apply(And , e1 :: e2 :: Nil)} |
                                     TruthValue~"""\/"""~TruthValue^^{case e1~"""\/"""~e2 => Apply(Or , e1 :: e2 :: Nil)} |
                                       "~"~TruthValue^^{case "~"~e => Apply(Not , e :: Nil)}


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
