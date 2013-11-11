package pt.cnbc.wikimodels.mathparser

import pt.cnbc.wikimodels.mathml._
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
      case Ci(x,_) => x
      case Ci(x,_) => x
      case Cn(content, "real", 10, definitionURL, encoding ) => content(0)
      case Cn(content, "integer", 10, definitionURL, encoding ) => content(0)
      case Cn(x::y, "e-notation",10, definitionURL, encoding ) => x+"e"+y
      case o:Operator => handleOperator(o)
      case CSymbol(content, definitionURL, encoding) => content
      case x:Lambda => handleLambda(x)
      case default => throw new RuntimeException( "AsciiMathML conversion of " + default + " is not implemented")
    }
  }


  /**
   * The prefix operators always have the form <code>op(listofparameters)</code> so their precedence is clear in AsciiMathML rendering.
   *
   * For infix operators, the use of parenthesis depends on the precedence of the operators.
   *
   * For this reason, they are included here.
   */
  private val infixOps:Map[Operator,Int] = Map(
    (Exponentiation -> 100),
    (Multiplication -> 90), (Division -> 90),
    (Addition -> 80), (Subtraction -> 80),
    (Eq -> 70), (Neq -> 70), (Gt -> 70), (Lt -> 70), (Geq -> 70), (Leq -> 70),
    (And -> 70), (Or -> 70)//, (Not -> 70)
  )

  private def operatorPrecedence(ap:Apply): Int =
    ap.op match {
      case o:Operator =>  infixOps.get(o).get
      case _ => 0 // the operators or functions not in infixOps have lower precedence
    }

  /**
   * If given a MathML operator object, it returns the ASCIIMathML symbol to be used for that MathML operator
   * @param op AST operator
   * @return the ASCIIMathML representation of this operator
   */
  protected def handleOperator(op:Operator):String ={
    op match {
      case Addition => " + "
      case Subtraction => " - "
      case Multiplication => " * "
      case Division => " / "
      case Eq => " == "
      case Neq => " != "
      case Gt => " > "
      case Lt => " < "
      case Geq => " >= "
      case Leq => " <= "
      case And => """ /\ """
      case Or => """ \/ """
      //xor will be represented as xor(x1, x2)
      case Not => " ~"
      case Exponentiation => "^"
      case Factorial => "! "
      case o:Operator => o.name
    }
  }


  /**
   *
   * @param apply
   * @return
   */
  protected def handleApply(apply:Apply):String = {
    //FIXME OPERATOR precedence must be improved since IT DOES NOT RESPECT all precedence rules and the final result is not easy to read
    //for example (temp_in_fahrenheit + 459.67) / (1.8) should be written as (temp_in_fahrenheit + 459.67) / 1.8


    apply match {
      case Apply(op,params) => {
        op match { // infix operators
          case Factorial => toAsciiMathML(params.head) + toAsciiMathML(op) // x!
          case Not  => toAsciiMathML(op) + toAsciiMathML(params.head)  //~x
          case o:Operator if infixOps.contains(o)=> { // x1 + x2 + x3
            handleApplyForInfixOperators(apply)
          }
          case o => { //funct(x)
            toAsciiMathML(o) + "(" + separateWith( params.map(toAsciiMathML(_)), ", " ) +")"
          }
        }
      }
    }
  }

  /**
   * Pretty prints an apply with an infix operator.
   * This takes in account the precedence rules and only adds parenthesis when necessary
   * @param ap
   */
  protected def handleApplyForInfixOperators(ap:Apply):String = {
    //TODO precedence rules go here
    val leftApply = ap.parameters.head
    val rightApply:List[MathMLElem] = ap.parameters.tail

    val left:String = leftApply match {
      case la:Apply if operatorPrecedence(la) < operatorPrecedence(ap) => "("+toAsciiMathML(la)+")"
      case x => toAsciiMathML(x) // no parenthesis are necessary
    }

    val right:List[String] = rightApply.map(
      _ match {
        case ra: Apply if operatorPrecedence(ra) <= operatorPrecedence(ap) => "(" + toAsciiMathML(ra) + ")"
        case x => toAsciiMathML(x)
      })

    separateWith(
      left :: right,
      toAsciiMathML(ap.op) )
  }

  protected def handleLambda(lambda:Lambda):String = {
    "(" + separateWith(lambda.params.map(toAsciiMathML(_)),", ") + ") = " + toAsciiMathML(lambda.expr)
  }

  protected def separateWith(params:List[String], separator:String ):String = {
    params match {
      case Nil      => ""
      case x :: Nil => x
      case x :: xs  => (x /: xs) (_ + separator + _ )
    }
  }

  //protected def parenthesis()
}
