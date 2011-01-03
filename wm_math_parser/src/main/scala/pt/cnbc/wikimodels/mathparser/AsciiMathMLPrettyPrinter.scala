package pt.cnbc.wikimodels.mathparser

import pt.cnbc.wikimodels.mathml.elements.{Operator, Apply, Ci, Cn, CSymbol, MathMLElem}
import Operator._

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 30-12-2010
 * Time: 18:31
 * To change this template use File | Settings | File Templates.
 */

object AsciiMathMLPrettyPrinter {

  def toAsciiMathML(elem:MathMLElem):String = {
    elem match{
      case x:Apply => handleApply(x)
      case Ci(x,"real",_) => x
      case Cn(content, "real", 10, definitionURL, encoding ) => content(0)
      case Cn(content, "integer", 10, definitionURL, encoding ) => content(0)
      case Operator(name, _, _, definitionURL, encoding) => name
      case CSymbol(content, definitionURL, encoding) => content
      case default => throw new RuntimeException( "AsciiMathML conversion of " + default + " is not implemented")
    }
  }

  protected def handleApply(apply:Apply):String = {
    apply match {
      case Apply(Addition, params)  => separateWith( params.map(toAsciiMathML(_)), " + " )
      case Apply(Subtraction, params) => separateWith( params.map(toAsciiMathML(_)), " - " )
      case Apply(Multiplication, params) => separateWith( params.map(toAsciiMathML(_)), " * " )
      case Apply(Division, params) => separateWith( params.map(toAsciiMathML(_)), " / " )
      case Apply(Exponentiation, params) =>  toAsciiMathML( params(0) ) + "^" + toAsciiMathML( params(1) )
      case Apply(o,params) => toAsciiMathML(o) + "(" + separateWith( params.map(toAsciiMathML(_)), ", " ) +")"
    }
  }

  protected def separateWith(params:List[String], separator:String ):String = {
    params match {
      case Nil      => ""
      case x :: Nil => x
      case x :: xs  => (x /: xs) (_ + separator + _ )
    }
  }
}
