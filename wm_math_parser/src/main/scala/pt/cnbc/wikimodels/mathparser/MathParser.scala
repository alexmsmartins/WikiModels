package pt.cnbc.wikimodels.mathparser

import scala.util.parsing.combinator.{PackratParsers, JavaTokenParsers}
import pt.cnbc.wikimodels.mathml.elements._
import pt.cnbc.wikimodels.mathml.elements.Operator._

/**
 */

class MathParser extends JavaTokenParsers with PackratParsers with MathParserHandlers {

  type MME = MathMLElem

  //check http://rwiki.sciviews.org/doku.php?id=wiki:asciimathml#standard_functions to handle certain cases
  lazy val LambdaExpr :PackratParser[Any]= Function ~ "=" ~ Expr

  //TODO def SimpleExpr  :Parser[Any]= Atom |   
  lazy val Expr       :PackratParser[MME]= Expr~"+"~Term^^{ case e~"+"~t => new Apply(Operator.Addition, List(e, t))  } |
                                           Expr~"-"~Term^^{ case e~"-"~t => new Apply(Subtraction, List(e, t)  ) } |
                                           Term

  lazy val Term       :PackratParser[MME]= Term~"*"~Factor^^{ case t~"*"~f => new Apply(Operator.Multiplication, List(t, f))  } |
                                           Term~"/"~Factor^^{ case t~"/"~f => new Apply(Operator.Division, List(t, f))  } |
                                           Factor

  lazy val Factor     :PackratParser[MME]= Power | Function | Atom | "("~>Expr<~")"

  lazy val Function   :PackratParser[MME]= ident~"("~Parameters~")"^^{ case i~"("~par~")" => {handleFunction(i, par)}}

  lazy val Parameters :PackratParser[List[MME]]= repsep(Expr,",") //no need to put anythin else here

  lazy val Power      :PackratParser[MME]= Base~"^"~Exponent^^{ case b~"^"~e => new Apply(Exponentiation,  List(b, e) ) }

  lazy val Base       :PackratParser[MME]= Function | Atom | "("~>Expr<~")"

  lazy val Exponent   :PackratParser[MME]= Function |
                                           Atom |
                                           "("~>Expr<~")"

  lazy val Atom       :PackratParser[MME]= wholeNumber^^(x => new Cn(x::Nil, "integer")) |
                                           decimalNumber^^{x => new Cn(x::Nil, "real")} |
                                           ident^^(x => new Ci(x))


}

trait MathParserHandlers{
  def handleFunction(ident:String, params:List[MathMLElem] ):MathMLElem = {
    println("Operator.validOps contains " + Operator.validOps.size + " predefined operators")
    Operator.validOps.getOrElse(ident, new Apply(new CSymbol(ident), params ))
  }
}
