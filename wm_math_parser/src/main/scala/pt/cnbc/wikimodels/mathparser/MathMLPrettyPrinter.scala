package pt.cnbc.wikimodels.mathparser

import scala.xml._
import pt.cnbc.wikimodels.mathml.elements._
import pt.cnbc.wikimodels.mathml.elements.Operator._

/**
 * TODO: Please document.
 * @author Alexandre Martins
 * Date: 7/Dez/2010
 * Time: 18:36:24
 */
object MathMLPrettyPrinter extends AsciiMathMLParser{

  def exprToMathML(expr:String) = toXML(parseAll(Expr, expr).get)

  def toXML(elem:MathMLElem):scala.xml.Elem = {
    <math xmlns="http://www.w3.org/1998/Math/MathML">
      {toXMLMatch(elem)}
    </math>
  }

  def toXMLMatch(elem:MathMLElem):scala.xml.Elem = {
      elem match{
        case Apply(op,params) => { <apply>{(op :: params).map(toXMLMatch(_))}</apply>
        }
        case Ci(x,"real",_) => <ci type="real">{x}</ci>
        case Cn(content, "real", 10, definitionURL, encoding ) => <cn type="real" base="10">{content}</cn>
        case Cn(content, "integer", 10, definitionURL, encoding ) => <cn type="integer" base="10">{content}</cn>
        case Cn(x::y, "e-notation",10, definitionURL, encoding ) => <cn type="e-notation" base="10">{x}<sep/>{y}</cn>
        case Operator(name, _, _, definitionURL, encoding) => {
          Elem(null, name,
            new UnprefixedAttribute("definitionURL", Text(definitionURL.getOrElse("")),
              new UnprefixedAttribute("encoding", Text(encoding), Null)),
            TopScope)
        }
        case CSymbol(content, definitionURL, encoding) => <csymbol content={content} definitionURL={definitionURL} >{content}</csymbol>
        case Sep() => <sep/>
        case default => throw new RuntimeException( "XML conversion of " + default + " is not implemented")
      }
  }
}
