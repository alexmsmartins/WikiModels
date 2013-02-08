package pt.cnbc.wikimodels.mathml.elements


class InfInt(value:Int, infinite:Boolean = false)
object InfInt{
  implicit def int2InfInt(value:Int) = new InfInt(value)
}
case object PositiveInfiniteInt extends InfInt(1,true)
case object NegativeInfiniteInt extends InfInt(-1,true)

/**
 * TODO: Please document.
 * @author Alexandre Martins
 * Date: 10/Out/2010
 * Time: 22:17:34
 */
class Operator(val name:String, val minArgs:InfInt, val maxArgs:InfInt, val definitionURL:Option[String]=None, val encoding:String="real") extends Token(name)

object Operator{
  def unapply(oscm: Operator):Option[(String, InfInt, InfInt, Option[String], String)] = Some((oscm.name, oscm.maxArgs, oscm.maxArgs, oscm.definitionURL, oscm.encoding))

  def apply(name:String, minArgs:InfInt, maxArgs:InfInt, definitionURL:Option[String]=None, encoding:String="real"):Operator = {
    new Operator(name, minArgs, maxArgs, definitionURL, encoding)
  }
}

object KnownOperators{
  /**
   * Map that contains the valid MathML operators that can appear io an SBML document
   */
  var validOps = Map.empty[String, Operator]
  var validOpsList = List.empty[Operator]


  //------------ relational operators: ------------//
  case object Eq extends Operator("eq",2,PositiveInfiniteInt)
  case object Neq  extends Operator("neq",2,2)
  case object Gt extends Operator("gt",2,PositiveInfiniteInt)
  case object Lt extends Operator("lt",2,PositiveInfiniteInt)
  case object Geq extends Operator("geq",2,PositiveInfiniteInt)
  case object Leq extends Operator("leq",2,PositiveInfiniteInt)

  validOpsList :::= List(Eq, Neq, Gt, Lt, Geq, Leq)

  //------------ trugonometric operators ------------//
  case object Sin extends Operator("sin",1,1)
  case object Cos extends Operator("cos",1,1)
  case object Tan extends Operator("tan",1,1)
  case object Sec extends Operator("sec",1,1)
  case object Csc extends Operator("csc",1,1)
  case object Cot extends Operator("cot",1,1)
  case object Sinh extends Operator("sinh",1,1)
  case object Cosh extends Operator("cosh",1,1)
  case object Tanh extends Operator("tanh",1,1)
  case object Sech extends Operator("sech",1,1)
  case object Csch extends Operator("csch",1,1)
  case object Coth extends Operator("coth",1,1)

  validOpsList :::= List(Sin, Cos, Tan, Sec, Csc, Cot, Sinh, Cosh, Tanh, Sech, Csch, Coth)

  //arccos, arctan, arcsec, arccsc, arccot, arcsinh, arccosh, arctanh, arcsech, arccsch, arccoth
  case object ArcSin extends Operator("arcsin",1,1)
  case object ArcCos extends Operator("arccos",1,1)
  case object ArcTan extends Operator("arctan",1,1)
  case object ArcSec extends Operator("arcsec",1,1)
  case object ArcCsc extends Operator("arccsc",1,1)
  case object ArcCot extends Operator("arccot",1,1)
  case object ArcSinh extends Operator("arcsinh",1,1)
  case object ArcCosh extends Operator("arccosh",1,1)
  case object ArcTanh extends Operator("arctanh",1,1)
  case object ArcSech extends Operator("arcsech",1,1)
  case object ArcCsch extends Operator("arccsch",1,1)
  case object ArcCoth extends Operator("arccoth",1,1)

  validOpsList :::= List(ArcSin, ArcCos, ArcTan, ArcSec, ArcCsc, ArcCot, ArcSinh, ArcCosh, ArcTanh, ArcSech, ArcCsch, ArcCoth)

  //------------ Arithmetic operators ------------//

  //plus, minus, times, divide, power, root, abs, exp, ln, log, floor, ceiling, factorial
  case object Addition extends Operator("plus", 2, PositiveInfiniteInt)
  case object Subtraction extends Operator("minus",1,2)
  case object Multiplication extends Operator("times", 2, PositiveInfiniteInt)
  case object Division  extends Operator("divide", 2, 2)
  case object Exponentiation extends Operator("power",2,2)
  case object Root extends Operator("root",1,1)
  case object Abs extends Operator("abs",1,1)
  case object Exp extends Operator("exp",1,1)
  case object Ln extends Operator("ln",1,1)
  case object Log extends Operator("log",1,2)
  case object Floor extends Operator("floor",1,1)
  case object Ceiling extends Operator("ceiling",1,1)
  case object Factorial extends Operator("factorial",1,1)

  validOpsList :::= List(Addition, Subtraction, Multiplication, Division, Exponentiation, Root, Abs, Exp, Ln, Log, Floor, Ceiling, Factorial)

  //------------ Logical operators ------------//

  //and, or, xor, not
  case object And extends Operator("and", 2, PositiveInfiniteInt)
  case object Or extends Operator("or",2, PositiveInfiniteInt)
  case object Xor extends Operator("xor", 2, PositiveInfiniteInt)
  case object Not extends Operator("not", 1, 1)

  validOpsList :::= List(And, Or , Xor, Not)

  validOps ++= validOpsList.map(op => (op.name, op))


  override def toString() = {
    validOps.map(op => {
        if(op._1 == null){
          "op is null\n"
        }else{
          "op is "+op._1+"\n"
        }
      }
    ).fold("")((s1,s2) => s1 + s2)
  }
}
