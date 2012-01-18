package pt.cnbc.wikimodels.mathml.elements


/**
 * TODO: Please document.
 * @author Alexandre Martins
 * Date: 30/Set/2010
 * Time: 23:42:50
 */
case class Apply(op:MathMLElem, parameters:List[MathMLElem]) extends Container(op :: parameters)