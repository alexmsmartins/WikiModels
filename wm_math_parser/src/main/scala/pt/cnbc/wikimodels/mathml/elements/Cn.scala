package pt.cnbc.wikimodels.mathml.elements

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 30/Set/2010
 * Time: 23:49:41
 * To change this template use File | Settings | File Templates.
 */

case class Cn(override val content:String,
              mtype:String = "real",
              base:Int = 10,
              definitionURL:String="",
              encoding:String="text") extends Token