package pt.cnbc.wikimodels.mathml.elements


case class InfInt(value:Int, infinite:Boolean = false)
object InfInt{
  implicit def int2InfInt(value:Int) = new InfInt(value)
}

case object PositiveInfiniteInt extends InfInt(1,true)

case object NegativeInfiniteInt extends InfInt(-1,true)

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 10/Out/2010
 * Time: 22:17:34
 * To change this template use File | Settings | File Templates.
 */
case class Operator(op:String, maxArgs:InfInt) extends Token(op)

case class Cos extends Operator("cos", 1)

/*
token: cn, ci, csymbol, sep
8
• general : apply, piecewise, piece, otherwise, lambda (the last is restricted to use in FunctionDefinition)
9
• relational operators: eq, neq, gt, lt, geq, leq
 10
• arithmetic operators: plus, minus, times, divide, power, root, abs, exp, ln, log, floor, ceiling,
 11
factorial
 12
• logical operators: and, or, xor, not
 13
• qualifiers: degree, bvar, logbase
 14
• trigonometric operators: sin, cos, tan, sec, csc, cot, sinh, cosh, tanh, sech, csch, coth, arcsin,
 15
arccos, arctan, arcsec, arccsc, arccot, arcsinh, arccosh, arctanh, arcsech, arccsch, arccoth
 16
• constants: true, false, notanumber, pi, infinity, exponentiale
 17
• annotation: semantics, annotation, annotation-xml
 1*/


/**
 * This object represent the concept of an Positive Infinite Integer in Scala
 */
