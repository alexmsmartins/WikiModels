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
      val sbml:Elem = XML.loadString(removeXMLHeader( xmlDoc  ))
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

  /**
   * Removes the lines before the first xml tag since they cant be parsed by <code>scala.xml.XML</code> classes
   */
  def removeXMLHeader(str:String):String = {
    var tempStr = str
    //remove first line until content becomes parsable xml
    while ( "^<[^!?]".r.findFirstMatchIn(tempStr) == None){
      Console.println("""Removing line """" + tempStr.split("\n")(0) + """" From file""")
      tempStr = stripFirstLine(tempStr)
    }
    tempStr
  }


  private def stripFirstLine(str:String):String  =    {
    str.split("\n").toList.tail.mkString("\n")
  }
}
