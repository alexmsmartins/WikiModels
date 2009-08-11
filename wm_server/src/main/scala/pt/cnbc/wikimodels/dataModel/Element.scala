/*
 * Element.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel

import scala.xml.Node
import scala.xml.NodeSeq
import scala.xml.Elem
import scala.reflect.BeanInfo
import java.util.Date

import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty

import scala.reflect.BeanInfo
import scala.xml.Group

import pt.cnbc.wikimodels.util.SBMLHandler
import pt.cnbc.wikimodels.exceptions.BadFormatException

/**
 * This can be considered the equivalent of SBase in SBML 2.4 specification
 * Yet there is no sboTerm as up now
 */
@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class Element(
    @Id
    @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#metaid")
    metaid:String) extends DataModel {


    //these two lines have to be repeated in any descendant of this class
    //since annotations are not inherited
    @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#notes")
    var notes:String = null

    def setNotesFromXML(notes:NodeSeq) = {
        this.notes = Group((new SBMLHandler).addNamespaceToXHTML(notes)).toString()
    }


    //metaid stays mandatory for now
    if(metaid == null){
        //Note: It might be deleted from here to make the data model
        //Note: independent frm the data validation -> we 'll see.
        throw new BadFormatException(
            "Element constructor should never receive a null metaid.")
    }

    def this(xmlModel:Elem) = {
        this((xmlModel \ "@metaid").text)
        this.setNotesFromXML(xmlModel \ "notes")
    }


    override def toXML:Elem =
    throw new pt.cnbc.wikimodels.exceptions
    .NotImplementedException("toXML in class " + this.getClass +
                             "is not implemente")
}
