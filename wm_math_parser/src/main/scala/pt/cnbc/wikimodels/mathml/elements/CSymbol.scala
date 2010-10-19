package pt.cnbc.wikimodels.mathml.elements

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 1/Out/2010
 * Time: 0:43:47
 * To change this template use File | Settings | File Templates.
 */

case class CSymbol(content:String,
                   definitionURL:String="",
                   encoding:String="text") extends Container( new MathMLString(content) :: Nil)