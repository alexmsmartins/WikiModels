package pt.cnbc.wikimodels.util

import net.liftweb.common.{Failure, Full, Empty, Box}
import xml._
import scala.util.matching._
import scala.None
import javax.xml.soap.SOAPElementFactory
import org.slf4j._
import alexmsmartins.log.LoggerWrapper

/**
 * TODO: Please document.
 * @author Alexandre Martins
 * Date: 29-05-2011
 * Time: 19:34
 */
object SBMLDocHandler extends LoggerWrapper {
  self  =>

  def surroundModelTagWithSBML(model:Elem):String = {
    """<?xml version="1.0" encoding="UTF-8"?>""" + System.getProperty("line.separator") +
<sbml xmlns="http://www.sbml.org/sbml/level2/version4" level="2" version="4">
  {model}
</sbml>
  }

  /**
   * Extracts the <model/> tag along with its content  from a valid SBML file so that it can be handled by scala.xml.XML
   */
  def extractModelTagfromSBML(xmlDoc:String):Box[scala.xml.Elem] = {
    trace("Entering method extractModelTagfromSBML() with parameter\n" + xmlDoc)
    try{
      //the next expression matches <sbml> even if it has atributes. Scala 2.8.1 and 2.9
      val removedHeader = removeXMLHeader( xmlDoc  ).trim
      debug("SBMLHandler.extractModelTagfromSBML() after removing header -> " + removedHeader)
      val sbml:Elem = XML.loadString( removedHeader )
      val sbml2:Elem = xml.Utility.trim(sbml).head.asInstanceOf[Elem]
      debug("XML.loadString return soemthing of type " + sbml.getClass )
      sbml2 match {
        case y if(y.label == "sbml") => {
          debug("SBML root label was extracted and the content is " + (y \ "model").head.asInstanceOf[Elem])
          Full((y \ "model").head.asInstanceOf[Elem])
        }
        case y => {
          debug("Invalid sbml file: <sbml> is not the root tag.\n The root tag is " + sbml.label)
          Failure("Invalid sbml file: <sbml> is not the root tag.\n The root tag is " + sbml.label)
        }
      }
    } catch {
      case e:Throwable =>{
        debug("Exception extracting <model/> tag from {} with exception {}", xmlDoc, e.printStackTrace())
        new Failure("Badly formed xml document", Full(e), Empty)
      }
    }
  }


  private val xmlTegRegExo = """^(\s)*<[^!?]""".r

  /**
   * Removes the lines before the first xml tag since they cant be parsed by <code>scala.xml.XML</code> classes
   * It is assumed that the XML Declaration is separated from the rest of the xml document  by a \n
   */
  def removeXMLHeader(str:String):String = {
    var tempStr = str
    //remove first line until content becomes parsable xml
    while ("""^(\s)*<[^!?]""".r.findFirstMatchIn(tempStr) == None){
      debug("""Removing line '""" + tempStr.split("\n").toList.head + """' from file""")
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
