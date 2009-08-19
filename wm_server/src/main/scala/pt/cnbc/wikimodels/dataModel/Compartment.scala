/*
 * Compartment.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel

import scala.reflect.BeanInfo
import scala.xml.Elem
import scala.xml.Group
import scala.xml.Node
import scala.xml.NodeSeq
import scala.xml.XML

import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty

import pt.cnbc.wikimodels.exceptions.BadFormatException
import pt.cnbc.wikimodels.util.SBMLHandler

@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class Compartment extends Element{

    var id:String = null
    var name:String = null
    var compartmentType:String = null //not implemented yet
    var spacialDimensions:Int = 0
    var size:Double = 0
    var units:String = null  //not implemented yet
    var outside:String = null
    var constant:Boolean = false



    def this(metaid:String,
             notes:NodeSeq,
             id:String,
             name:String,
             compartmentType:String, //not implemented yet
             spacialDimensions:Int,
             size:Double,
             units:String,  //not implemented yet
             outside:String,
             constant:Boolean) = {
        this()
        this.metaid = metaid
        this.setNotesFromXML(notes)
        this.name = name
        this.compartmentType = compartmentType
        this.spacialDimensions = spacialDimensions
        this.size = size
        this.units = units
        this.outside = outside
        this.constant = constant
    }

    def this(xmlCompartment:Elem) = {
        this((new SBMLHandler).toStringOrNull((xmlCompartment \ "@metaid").text),
             (new SBMLHandler).checkCurrentLabelForNotes(xmlCompartment),
             (new SBMLHandler).toStringOrNull((xmlCompartment \ "@id").text),
             (new SBMLHandler).toStringOrNull((xmlCompartment \ "@name").text),
             (xmlCompartment \ "@compartmentType").text,
             (xmlCompartment \ "@spacialDimensions").text.toInt,
             (xmlCompartment \ "@size").text.toDouble,
             (xmlCompartment \ "@units").text,
             (xmlCompartment \ "@outside").text,
             (xmlCompartment \ "@constant").text.toBoolean)
    }

    override def toXML:Elem = {
        <compartment metaid={metaid} id={id} name={name}
            compartmentType={compartmentType}
            spacialDimensions={spacialDimensions.toString}
            size={size.toString} units={units} outside={outside}
            constant={constant.toString}>
            {new SBMLHandler().genNotesFromHTML(notes)}
        </compartment>
    }
    override def theId = this.id
    override def theName = this.name

}
