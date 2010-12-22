package pt.cnbc.wikimodels.mathparser

import util.parsing.combinator.{JavaTokenParsers, PackratParsers}
import util.parsing.combinator.syntactical.StandardTokenParsers

/**
 */

class MathParserBack extends JavaTokenParsers with PackratParsers {
  type MME = Any

  //check http://rwiki.sciviews.org/doku.php?id=wiki:asciimathml#standard_functions to handle certain cases
  lazy val LambdaExpr :PackratParser[Any]= Function ~ "=" ~ Expr

  //TODO def SimpleExpr  :Parser[Any]= Atom |
  lazy val Expr       :PackratParser[MME]= Expr~"+" ~ Term |
                                           Expr~"-"~Term |
                                           Term

  lazy val Term       :PackratParser[MME]= Term~"*"~Factor | Term~"/"~Factor | Factor

  lazy val Factor     :PackratParser[MME]= Power | Function | Atom | "("~>Expr<~")"

  lazy val Function   :PackratParser[MME]= ident~"("~Parameters~")"

  lazy val Parameters :PackratParser[List[MME]]= repsep(Expr,",") //no need to put anythin else here

  lazy val Power      :PackratParser[MME]= Base~"^"~Exponent

  lazy val Base       :PackratParser[MME]= Function |
                                           Atom |
                                            "("~Expr~")"

  lazy val Exponent   :PackratParser[MME]= Function |
                                           Atom |
                                           "("~Expr~")"

  lazy val Atom       :PackratParser[MME]= decimalNumber |
                                           wholeNumber |
                                           ident


}