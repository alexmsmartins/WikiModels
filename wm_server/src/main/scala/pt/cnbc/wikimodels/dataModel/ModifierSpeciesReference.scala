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
import pt.cnbc.wikimodels.exceptions.BadFormatException

@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class ModifierSpeciesReference() extends Element {
  var id: String = null
  var name: String = null

  var species: Species = null

  def this(metaid: String,
           notes: NodeSeq,
           id: String,
           name: String,
           species: Species) = {
    this ()
    this.metaid = metaid
    this.setNotesFromXML(notes)
    this.id = id
    this.name = name
    this.species = species
    (new SBMLHandler).idExistsAndIsValid(this.id)
  }

  def this(xmlReaction: Elem) = {
    this ((new SBMLHandler).toStringOrNull((xmlReaction \ "@metaid").text),
      (new SBMLHandler).checkCurrentLabelForNotes(xmlReaction),
      (new SBMLHandler).toStringOrNull((xmlReaction \ "@id").text),
      (new SBMLHandler).toStringOrNull((xmlReaction \ "@name").text),
      new Species(null, Nil, (xmlReaction \ "@species").text, null,
        null, null, null, null, null, false, false, false))
  }

  override def toXML: Elem = {
    <modifierSpeciesReference metaid={metaid} id={id} name={name}
                              species={species.id}>
      {new SBMLHandler().genNotesFromHTML(notes)}
    </modifierSpeciesReference>
  }

  override def theId = this.id

  override def theName = this.name
}
