package pt.cnbc.wikimodels.mathparser

import pt.cnbc.wikimodels.mathml.elements._
import pt.cnbc.wikimodels.mathml.elements.KnownOperators._

/**
 * AsciiMath syntax loosely based on http://www1.chapman.edu/~jipsen/mathml/asciimathsyntax.html
 * This is a very simplified syntax that satisfies the SBML restrictions.
 * @author Alexandre Martins
 * Date: 30-12-2010
 * Time: 18:31
 */
object AsciiMathPrettyPrinter{

  def toAsciiMathML(elem:MathMLElem):String = {
    elem match{
      case x:Apply => handleApply(x)
      case Ci(x,"real",_) => x
      case Cn(content, "real", 10, definitionURL, encoding ) => content(0)
      case Cn(content, "integer", 10, definitionURL, encoding ) => content(0)
      case Cn(x::y, "e-notation",10, definitionURL, encoding ) => x+"e"+y
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

      case Apply(Eq, params)  => separateWith( params.map(toAsciiMathML(_)), " == " )
      case Apply(Neq, params)  => separateWith( params.map(toAsciiMathML(_)), " != " )
      case Apply(Gt, params)  => separateWith( params.map(toAsciiMathML(_)), " > " )
      case Apply(Lt, params)  => separateWith( params.map(toAsciiMathML(_)), " < " )
      case Apply(Geq, params)  => separateWith( params.map(toAsciiMathML(_)), " >= " )
      case Apply(Leq, params)  => separateWith( params.map(toAsciiMathML(_)), " <= " )

      case Apply(And, params)  => separateWith( params.map(toAsciiMathML(_)), """ /\ """ )
      case Apply(Or, params)  => separateWith( params.map(toAsciiMathML(_)), """ \/ """ )
      //xor will be represented as xor(x1, x2)
      case Apply(Not, params)  => " ~" + toAsciiMathML(params.head)


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
