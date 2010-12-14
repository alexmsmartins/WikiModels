package pt.cnbc.wikimodels.mathparser

import pt.cnbc.wikimodels.mathml.elements._
import util.parsing.combinator.{JavaTokenParsers, PackratParsers}

/**
 */

class MathParserBack extends JavaTokenParsers with PackratParsers with MathParserHandlers {
/*
  type MME = MathMLElem

  //check http://rwiki.sciviews.org/doku.php?id=wiki:asciimathml#standard_functions to handle certain cases
  lazy val LambdaExpr  :PackratParser[Any]= Function~"="~Expr

  //TODO def SimpleExpr  :Parser[Any]= Atom |   
  lazy val Expr        :PackratParser[MME]= Expr~("+"~>Term)^^( case e~(t) => new Apply(Adition, List(e, t)  )) |
                                            Expr~("-"~>Term)^^( case e~(t) => new Apply(Subtraction, List(e, t)  )) |
                                            Term^^(x => new MathMLStub(x))

  lazy val Term        :PackratParser[MME]= Term~"*"~>Factor | Term~"/"~>Factor | Factor

  lazy val Factor      :PackratParser[MME]= Power | Function | Atom | "("~Expr~")"

  lazy val Function    :PackratParser[MME]= ident~"("~Parameters~")"^^{ case i~"("~par~")" => {handleFunction(i, par)}}

  lazy val Parameters  :PackratParser[List]= repsep(Expr,",")^^{ List("1"."2","TODO") }

  lazy val Power       :PackratParser[MME]= Base~"^"~Exponent^^( case b~"^"~e => new Apply(Exponent :: b :: e))

  lazy val Base        :PackratParser[MME]= Function^^(x => new MathMLStub(x)) |
                                            Atom^^(x => new MathMLStub(x)) |
                                             "("~Expr~")"^^(case "("~e~")" => new MathMLStub(e) )

  lazy val Exponent    :PackratParser[MME]= Function^^(x => new MathMLStub(x)) |
                                            Atom^^(x => new MathMLStub(x)) |
                                             "("~Expr~")"^^{case "("~e~")" => new MathMLStub(e) }

  lazy val Atom        :PackratParser[MME]= decimalNumber^^{x => new Cn(x::Nil)} |
                                            wholeNumber^^(x => new Cn(x::Nil)) |
                                            ident^^(x => new Ci(x))                   */
}

trait MathParserHandlers{
  //def handleFunction(ident:String, params:List[String] ):MathMLElem = new MathMLStub()
}