package pt.cnbc.wikimodels.util

import net.liftweb.common.{Failure, Full, Empty, Box}
import xml.{Node, XML, Elem}
import scala.util.matching._
import scala.None
import javax.xml.soap.SOAPElementFactory
import org.slf4j._

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 29-05-2011
 * Time: 19:34
 * To change this template use File | Settings | File Templates.
 */

object SBMLDocHandler {
  self  =>

  val logger = LoggerFactory.getLogger(SBMLDocHandler.getClass)

  def surroundModelTagWithSBML(model:Elem):String = {
    """<?xml version="1.0" encoding="UTF-8"?>""" + System.getProperty("line.separator") +
<sbml xmlns="http://www.sbml.org/sbml/level2/version4" level="2" version="4">
  {model}
</sbml>
  }

  /**
   * Extracts the <model/> tag along with its content  from a valid SBML file so that it can be handled by scala.xml.XML
   */
  def extractModelTagfromSBML(xmlDoc:String):Box[Elem] = {
    logger.warn("Entering method extractModelTagfromSBML()")
    try{
      //the next expression matches <sbml> even if it has atributes. Scala 2.8.1 aND 2.9
      val removedHeader = removeXMLHeader( xmlDoc  ).trim
      Console.println("SBMLHandler.extractModelTagfromSBML() after removing header ->" + removedHeader)
      val sbml:Elem = XML.loadString( removedHeader )
      val sbml2 = xml.Utility.trim(sbml)
      Console.println("XML.loadString return soemthing of type " + sbml.getClass )
      sbml2 match {
        case <sbml>{y}</sbml> => {
          Full(y.asInstanceOf[Elem])
        }
        case y =>{
          Console.println("Invalid sbml file: <sbml> is not the root tag. the xml is " + sbml)
          Failure("Invalid sbml file: <sbml> is not the root tag.\n The content is " + sbml)
        }
      }
    } catch {
      case e =>{
        Console.println(e.printStackTrace())
        new Failure("Badly formed xml document", Full(e), Empty)
      }
    }
  }


  private val xmlTegRegExo = """^(\s)*<[^!?]""".r

  /**
   * Removes the lines before the first xml tag since they cant be parsed by <code>scala.xml.XML</code> classes
   * It is assumed that the XML Decalaration is separated from the rest of the xml document  by a \n
   */
  def removeXMLHeader(str:String):String = {
    var tempStr = str
    //remove first line until content becomes parsable xml
    while ("""^(\s)*<[^!?]""".r.findFirstMatchIn(tempStr) == None){
      Console.println("""Removing line '""" + tempStr.split("\n").toList.head + """' from file""")
      tempStr = stripFirstLine(tempStr)
    }
    tempStr
  }

  /**
   * Removes whitespaces from XML that come from code formatting tools.
   * In the XML
   */
  //def removeXMLWhitespace(str:String)


  private def stripFirstLine(str:String):String  =    {
    val a = str.split("\n")
    a.toList.tail.mkString("\n")
  }
}