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


/**
 * The initial bean from which every other persistence model class
 * should inherit
 * @author Alexandre Martins
 */
trait DataModel {
    /**
     * Converts this data model to xnl form
     */
    def toXML:Elem

    /**
     * Extracts the information contained in the XML
     * and creates a Data model accordingly
     */
    //def extractXML(xml:Node):DataModel
}
