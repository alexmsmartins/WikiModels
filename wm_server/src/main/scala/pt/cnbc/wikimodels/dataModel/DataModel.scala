/*
 * DataModel.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel

import scala.xml.Node
import scala.xml.Elem
import thewebsemantic.binding.RdfBean

import pt.cnbc.wikimodels.util.SBMLHandler
import org.slf4j.LoggerFactory

/**
 * The initial bean from which every other persistence model class
 * should inherit
 * @author Alexandre Martins
 */
trait DataModel extends RdfBean[DataModel]{
  val logger = LoggerFactory.getLogger(getClass)

    val sbmlHandler = new SBMLHandler()
    /**
     * Converts this data model to xnl form
     */
    def toXML:Elem

    /**
     * Constructor that extracts the information contained in the XML
     * Should be implemented in every child class
     */
    //def this(xml:Node)
}
