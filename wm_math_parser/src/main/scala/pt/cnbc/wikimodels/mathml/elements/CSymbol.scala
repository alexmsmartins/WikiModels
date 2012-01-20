package pt.cnbc.wikimodels.mathml.elements

/**
 * TODO: Please document.
 * @author Alexandre Martins
 * Date: 1/Out/2010
 * Time: 0:43:47
 */
case class CSymbol(content:String,
                   definitionURL:String="",
                   encoding:String="text") extends Container( new MathMLString(content) :: Nil)