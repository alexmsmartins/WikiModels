package pt.cnbc.wikimodels.mathparser

import xml.Elem
import pt.cnbc.wikimodels.mathml.elements.{Cn, Ci, Apply, MathMLElem}

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 7/Dez/2010
 * Time: 18:36:24
 * To change this template use File | Settings | File Templates.
 */

object MathMLPrettyPrinter{
  def toXML(elem:MathMLElem):Elem = {
    elem match{
      case Apply(op,params) => { <apply>{toXML(op)
                                        params.map(toXML(_))}</apply>
      }
      case Ci(x,"real",_) => <ci type="real">{x}</ci>
      //case Cn() => {}
      case _ => throw new RuntimeException( "XML conversion of ") 


    }

  }
}