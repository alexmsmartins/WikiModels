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
object MathMLPrettyPrinter extends AsciiMathParser{

  def exprToMathML(expr:String) = toXML(parseAll(NumExpr, expr).get)

  def toXML(elem:MathMLElem):scala.xml.Elem = {
    <math xmlns="http://www.w3.org/1998/Math/MathML">
      {toXMLMatch(elem)}
    </math>
  }


  def toXMLMatch(elem:MathMLElem):scala.xml.Elem = {
      elem match{
        case Apply(op,params) => <apply>{(op :: params).map(toXMLMatch(_))}</apply>
        case Lambda(params, expr) => <lambda>{params.map(p => <bvar>{toXMLMatch(p)}</bvar>)}{toXMLMatch(expr)}</lambda>
        case Ci(x,_) => <ci>{x}</ci>
        case Cn(content, "real", 10, encoding ) => <cn type="real" base="10">{content}</cn>
        case Cn(content, "integer", 10, encoding ) => <cn type="integer" base="10">{content}</cn>
        case Cn(x::y, "e-notation",10, encoding ) => <cn type="e-notation" base="10">{x}<sep/>{y}</cn>
        case Operator(name, _, _, encoding) =>
          Elem(null, name,
            new UnprefixedAttribute("encoding", Text(encoding), Null),
            TopScope, true)
        case CSymbol(content, definitionURL, encoding) => <csymbol encoding={encoding} definitionURL={definitionURL} >{content}</csymbol>
        case Sep() => <sep/>
        case default => throw new RuntimeException( "XML conversion of " + default + " is not implemented")
      }
  }
}


