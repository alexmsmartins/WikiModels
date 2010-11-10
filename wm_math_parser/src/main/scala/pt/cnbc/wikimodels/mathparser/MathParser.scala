package pt.cnbc.wikimodels.mathparser

import pt.cnbc.wikimodels.mathml.elements._
import scala.util.parsing.combinator._
/**
 */

class MathParserBack extends JavaTokenParsers
{
  //check http://rwiki.sciviews.org/doku.php?id=wiki:asciimathml#standard_functions to handle certain cases
  def LambdaExpr  :Parser[Any]= Function~"="~Expr

  //TODO def SimpleExpr  :Parser[Any]= Atom |   

  def Expr        :Parser[Any]=
          Term~rep( "+"~Term) |/*^^ {new Apply("plus", Term :: Term :: Nil) }*/
          Term~rep( "-"~Term ) |/*^^ { new Apply("minus", _1 :: _2 :: Nil) }*/
          Term/*^^
                    {List(_)}  */

  def Term        :Parser[Any]= Factor~rep( "*"~Factor | "/"~Factor )

  def Factor      :Parser[Any]= Power | Function | Atom | "("~Expr~")"

  def Function    :Parser[Any]= ident~"("~Parameters~")"

  def Parameters  :Parser[Any]= Expr~rep(","~Expr)

  def Power       :Parser[Any]= Base~"^"~Exponent

  def Base        :Parser[Any]= Function | Atom | "("~Expr~")"

  def Exponent    :Parser[Any]= Function | Atom | "("~Expr~")"

  def Atom        :Parser[Any]= decimalNumber | wholeNumber | ident
  
}
