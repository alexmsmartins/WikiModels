package pt.cnbc.wikimodels.util

import net.liftweb.common.{Failure, Full, Empty, Box}
import xml.{Node, XML, Elem}
import scala.util.matching._
import scala.None

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 29-05-2011
 * Time: 19:34
 * To change this template use File | Settings | File Templates.
 */

object SBMLDocHandler {

  def surroundModelTagWithSBML(model:Elem):String = {
    """<?xml version="1.0" encoding="UTF-8"?>
    """ +
<sbml xmlns="http://www.sbml.org/sbml/level2/version4" level="2" version="4">
  {model}
</sbml>
  }

  /**
   * Extracts the <model/> tag along with its content  from a valid SBML file so that it can be handled by scala.xml.XML
   */
  def extractModelTagfromSBML(xmlDoc:String):Box[Elem] = {
    try{
      //the next expression matches <sbml> even if it has atributes. Scala 2.8.1 aND 2.9
      val <sbml>{ret}</sbml> = XML.loadString( removeXMLHeader( xmlDoc  ) )
      Full(ret.asInstanceOf[Elem])
    } catch {
      case _ => Failure("Badly formed xml document")
    }
  }

  /**
   * Removes the lines before the first xml tag since they cant be parsed by <code>scala.xml.XML</code> classes
   */
  private def removeXMLHeader(str:String):String = {
    var tempStr = str
    //remove first line until content becomes parsable xml
    while ( "^<[^!?]".r.findFirstMatchIn(tempStr) == None){
      tempStr = stripFirstLine(tempStr)
    }
    tempStr
  }


  private def stripFirstLine(str:String):String  =    {
    str.split("\n").toList.tail.mkString("\n")
  }
}
