package pt.cnbc.wikimodels.mathparser

import scala.util.parsing.combinator._
/**
 */

class MathParser extends JavaTokenParsers
{
  //check http://rwiki.sciviews.org/doku.php?id=wiki:asciimathml#standard_functions to handle certain cases
  def LambdaExpr  :Parser[Any]= Function~"="~Expr

  //TODO def SimpleExpr  :Parser[Any]= Atom |   

  def Expr        :Parser[Any]= Term~rep( "+"~Term) | rep( "-"~Term ) | Term 

  def Term        :Parser[Any]= Factor~rep( "*"~Factor | "/"~Factor )

  def Factor      :Parser[Any]= Power | Function | Atom | "("~Expr~")"

  def Function    :Parser[Any]= ident~"("~Parameters~")"

  def Parameters  :Parser[Any]= Expr~rep(","~Expr)

  def Power       :Parser[Any]= Base~"^"~Exponent

  def Base        :Parser[Any]= Function | Atom | "("~Expr~")"

  def Exponent    :Parser[Any]= Function | Atom | "("~Expr~")"

  def Atom        :Parser[Any]= decimalNumber | wholeNumber | ident
  
}