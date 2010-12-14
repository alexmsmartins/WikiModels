package pt.cnbc.wikimodels.mathparser

import util.parsing.combinator.{JavaTokenParsers, PackratParsers}
import util.parsing.combinator.syntactical.StandardTokenParsers

/**
 */

class MathParser extends JavaTokenParsers with PackratParsers {
  lazy val LambdaExpr  :PackratParser[Any]= Function~"="~Expr

  lazy val Expr        :PackratParser[Any]= Expr~"+"~Term | Expr~"-"~Term | Term

  lazy val Term        :PackratParser[Any]= Term~"*"~Factor | Term~"/"~Factor | Factor

  lazy val Factor      :PackratParser[Any]= Power | Function | Atom | "("~Expr~")"

  lazy val Function    :PackratParser[Any]= ident~"("~Parameters~")"

  lazy val Parameters  :PackratParser[Any]= repsep(Expr,",") //*arameters~","~Expr

  lazy val Power       :PackratParser[Any]= Base~"^"~Exponent

  lazy val Base        :PackratParser[Any]= Function | Atom | "("~Expr~")"

  lazy val Exponent    :PackratParser[Any]= Function | Atom | "("~Expr~")"

  lazy val Atom        :PackratParser[Any]= decimalNumber | wholeNumber | ident
}