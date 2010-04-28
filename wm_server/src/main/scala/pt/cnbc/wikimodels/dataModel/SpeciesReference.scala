/*
 * SpeciesReference.scala
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
case class SpeciesReference extends Element{
  var id:String = null
  var name:String = null

  //stoichiometry and
  var stoichiometry:java.lang.Double = 1 //{ use="optional" default="1" }

  /**
   * In SBML this is a class
   * Yet that class is only a container for MathML.
   * taking this in account it was decided to turn this into a property
   * both in the WikiModels class hierarchy as weel as in the ontology
   */
  var stoichiometryMath:String = null //{ use="optional" }

  var species:String = null //mandatory

  def this(metaid:String,
           notes:NodeSeq,
           id:String,
           name:String,
           species:String,
           stoichiometry:Double,
           stoichiometryMath:NodeSeq) = {
    this()
    this.metaid = metaid
    this.setNotesFromXML(notes)
    this.id = id
    this.name = name
    this.species = species
    this.stoichiometry = stoichiometry
    this.stoichiometryMath = stoichiometryMath.toString
  }

  def this(xmlSpeciesRef:Elem) = {
    this((new SBMLHandler).toStringOrNull((xmlSpeciesRef \ "@metaid").text),
         (new SBMLHandler).checkCurrentLabelForNotes(xmlSpeciesRef),
         (new SBMLHandler).toStringOrNull((xmlSpeciesRef \ "@id").text),
         (new SBMLHandler).toStringOrNull((xmlSpeciesRef \ "@name").text),
         (xmlSpeciesRef \ "@species").text,
         try{
          (xmlSpeciesRef \ "@stoichiometry").text.toDouble
        } catch {case _ => 1 },
         (xmlSpeciesRef \ "stoichiometryMath" \ "math"))
    //if there is a stoichiometryMath 
    if( (xmlSpeciesRef \ "@speciesReference" \ "stoichiometryMath" ).size == 0 ){
    } else {
      this.stoichiometry = null
    }
  }

  override def toXML:Elem = {
    val mathExists = this.stoichiometryMath.size == 0
    
    <speciesReference metaid={metaid} id={id} name={name}
      species={species} stoichiometry=
      {
        if(mathExists) null.asInstanceOf[String] else {this.stoichiometry.toString}
      }>
      <!--order is important according to SBML Specifications-->
      {new SBMLHandler().genNotesFromHTML(notes)}
      {
        if(!mathExists){
          null
        } else {
          <stoichiometryMath>
            {this.stoichiometryMath.elements.next}
          </stoichiometryMath>
        }
      }
    </speciesReference>
  }
  override def theId = this.id
  override def theName = this.name
}
