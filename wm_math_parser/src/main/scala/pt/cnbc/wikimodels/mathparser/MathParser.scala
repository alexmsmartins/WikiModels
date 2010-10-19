package pt.cnbc.wikimodels.mathparser

import scala.util.parsing.combinator._
import scala.util.parsing.combinator.lexical._
import scala.util.parsing.combinator.syntactical._
import scala.util.parsing.syntax._

/**
 */

class MathParser extends JavaTokenParsers
{
  //check http://rwiki.sciviews.org/doku.php?id=wiki:asciimathml#standard_functions to handle certain cases
  def LambdaExpr  :Parser[Any]= Function~"="~Expr

  //TODO def SimpleExpr  :Parser[Any]= Atom |   

  def Expr        :Parser[Any]= Term~rep( "+"~Term | "-"~Term ) | Term 

  def Term        :Parser[Any]={
    val parse = Factor~rep( "*"~Factor | "/"~Factor )
    parse
  }

  def Factor      :Parser[Any]= Power | Function | Atom | "("~Expr~")"

  def Function    :Parser[Any]= ident~"("~Parameters~")"

  def Parameters  :Parser[Any]= Expr~rep(","~Expr)

  def Power       :Parser[Any]= Base~"^"~Exponent

  def Base        :Parser[Any]= Function | Atom | "("~Expr~")"

  def Exponent    :Parser[Any]= Function | Atom | "("~Expr~")"

  def Atom        :Parser[Any]= decimalNumber | wholeNumber | ident
  
}
