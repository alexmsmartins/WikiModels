/*
 * Reaction.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel

import org.scala_tools.javautils.Imports._

import scala.xml.Group
import scala.xml.Node
import scala.xml.NodeSeq

import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty
import scala.reflect.BeanInfo
import scala.xml.Elem

import pt.cnbc.wikimodels.util.SBMLHandler

@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class Reaction extends Element{

    var id:String = null
    var name:String = null
    var reversible:Boolean = true
    var fast:boolean = false

    //TODO - MATH wiil be done in the DAO since it is very complicated to do it
    //all here
    var listOfReactants:java.util.Collection[SpeciesReference] = null
    var listOfProducts:java.util.Collection[SpeciesReference] = null
    var listOfModifiers:java.util.Collection[ModifierSpeciesReference] = null

    var kineticLaw:KineticLaw = null //optional

    def this(metaid:String,
             notes:NodeSeq,
             id:String,
             name:String,
             reversible:Boolean,
             fast:boolean) = {
        this()
        this.metaid = metaid
        this.setNotesFromXML(notes)
        this.id = id
        this.name = name
        this.reversible = reversible
        this.fast = fast
     }

    def this(xmlReaction:Elem) = {
        this((new SBMLHandler).toStringOrNull((xmlReaction \ "@metaid").text),
             (new SBMLHandler).checkCurrentLabelForNotes(xmlReaction),
             (new SBMLHandler).toStringOrNull((xmlReaction \ "@id").text),
             (new SBMLHandler).toStringOrNull((xmlReaction \ "@name").text),
             (xmlReaction \ "@reversible").text.toBoolean ,
             (xmlReaction \ "@fast").text.toBoolean)
    }

    override def toXML:Elem = {
        <reaction metaid={metaid} id={id} name={name} >
            {new SBMLHandler().genNotesFromHTML(notes)}
            {if(listOfReactants != null)
            <listOfReactants>
                    {listOfReactants.asScala.map(i => i.toXML)}
             </listOfReactants> else scala.xml.Null
            }
            {if(listOfProducts != null)
            <listOfProducts>
                    {listOfProducts.asScala.map(i => i.toXML)}
             </listOfProducts> else scala.xml.Null
            }
            {if(listOfModifiers != null)
            <listOfModifiers>
                    {listOfModifiers.asScala.map(i => i.toXML)}
             </listOfModifiers> else scala.xml.Null
            }
            {if(this.kineticLaw != null) this.kineticLaw.toXML else null.asInstanceOf[Elem]}
        </reaction>
    }
    override def theId = this.id
    override def theName = this.name
}
