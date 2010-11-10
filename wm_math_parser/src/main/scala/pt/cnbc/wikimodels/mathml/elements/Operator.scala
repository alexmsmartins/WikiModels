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
case class Operator(op:String, minArgs:InfInt, maxArgs:InfInt) extends Token(op)

case object Cos extends Operator("cos",1,1)

case object Plus extends Operator("plus", 2, PositiveInfiniteInt)

case object Minus extends Operator("minus", 1, PositiveInfiniteInt)

/*case class Quotient extends Operator("quotient", PositiveInfiniteInt)
case class Minus extends Operator("plus", PositiveInfiniteInt)
case class Minus extends Operator("plus", 1, PositiveInfiniteInt)
case class Quotient extends Pquotient)
            4.4.3.2 Factorial (factorial)
            4.4.3.3 Division (divide)
            4.4.3.4 Maximum and minimum (max, min)
            4.4.3.5 Subtraction (minus)
            4.4.3.6 Addition (plus)
            4.4.3.7 Exponentiation (power)
            4.4.3.8 Remainder (rem)
            4.4.3.9 Multiplication (times)
            4.4.3.10 Root (root)*/


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
