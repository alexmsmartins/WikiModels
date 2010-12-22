package pt.cnbc.wikimodels.mathml.elements

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 30/Set/2010
 * Time: 23:49:41
 * To change this template use File | Settings | File Templates.
 */
case class Cn(content:List[String], //certain elemeents will be separated by <sep/> tag
              mtype:String = "real",
              base:Int = 10,
              definitionURL:String="",
              encoding:String="text") extends Token