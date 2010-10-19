package pt.cnbc.wikimodels.mathml.elements

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 30/Set/2010
 * Time: 23:52:15
 * To change this template use File | Settings | File Templates.
 */
class MathMLElem

class MathMLString(valu:String) extends MathMLElem{
  val value:String = valu
}

object MathMLString{
  implicit def str2MathStr(str:String): MathMLString ={
    return new MathMLString(str)
  }
  implicit def mathStr2Str(str:MathMLString): String = str.value
}

