package pt.cnbc.wikimodels.mathml.elements


/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 30/Set/2010
 * Time: 23:42:50
 * To change this template use File | Settings | File Templates.
 */

case class Apply(op:MathMLElem, parameters:List[MathMLElem]) extends Container(op :: parameters)