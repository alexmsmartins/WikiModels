/*
 * ModifierSpeciesReference.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel

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
class ModifierSpeciesReference(
        @Id override val metaid:String) extends Element(metaid) {
        var id:String = null
        var name:String = null
        
        var species:Species = null

        def this(metaid:String,
             notes:NodeSeq,
             id:String,
             name:String,
             species:Species) = {
        this(metaid)
        this.setNotesFromXML(notes)
        this.id = id
        this.name = name
        this.species = species
     }

    def this() = {
        this(null, Nil, null, null, null)
    }

    def this(xmlReaction:Elem) = {
        this((new SBMLHandler).toStringOrNull((xmlReaction \ "@metaid").text),
             (new SBMLHandler).checkCurrentLabelForNotes(xmlReaction),
             (new SBMLHandler).toStringOrNull((xmlReaction \ "@id").text),
             (new SBMLHandler).toStringOrNull((xmlReaction \ "@name").text),
             new Species(null, Nil, (xmlReaction \ "@species").text, null,
                         null, null, null, null, null, false, false, false))
    }

    override def toXML:Elem = {
        <modifierSpeciesReference metaid={metaid} id={id} name={name}
            species={species.id}>
            {new SBMLHandler().genNotesFromHTML(notes)}
        </modifierSpeciesReference>
    }
}
