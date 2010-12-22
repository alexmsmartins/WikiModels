package pt.cnbc.wikimodels.mathml.elements

import scala.xml.Node
import scala.xml.Elem

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 30/Set/2010
 * Time: 23:52:15
 * To change this template use File | Settings | File Templates.
 */
trait MathMLElem

/**
 * DO NOT USE THIS IN PRODUCTION CODE
 */
class MathMLElemForTests extends MathMLElem

class MathMLString(valu:String) extends MathMLElem{
  val value:String = valu
}

object MathMLString{
  implicit def str2MathStr(str:String): MathMLString = new MathMLString(str)
  implicit def mathStr2Str(str:MathMLString): String = str.value
}

/**
 * Exits to fill the AST for MAthMLAscii when no new MathML entities should be created.
 * It is not meant to serve as a leaf but only as an intermediate node of the AST.
 */
class MathMLASTStub(sub:MathMLElem) extends MathMLElem
