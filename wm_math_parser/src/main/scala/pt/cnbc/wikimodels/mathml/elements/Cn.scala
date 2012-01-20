package pt.cnbc.wikimodels.mathml.elements

/**
 * TODO: Please document.
 * @author Alexandre Martins
 * Date: 30/Set/2010
 * Time: 23:49:41
 */
case class Cn(content:List[String], //certain elemeents will be separated by <sep/> tag
              mtype:String = "real",
              base:Int = 10,
              definitionURL:String="",
              encoding:String="text") extends Token