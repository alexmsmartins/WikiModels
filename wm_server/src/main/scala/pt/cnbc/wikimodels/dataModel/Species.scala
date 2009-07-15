/*
 * Species.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel
import pt.cnbc.wikimodels.util.SBMLHandler

import scala.xml.Group
import scala.xml.Node
import scala.xml.NodeSeq

import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty
import scala.reflect.BeanInfo
import scala.xml.Elem


@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class Species(
    @Id
    override val metaid:String,
      override val notes:NodeSeq,
      id:String,
      name:String,
      speciesType:String,  //it is not included yet.
      compartment:String,
      initialAmount:Double,
      initialConcentration:Double,
      substanceUnits:String,   //it is not included yet.
      hasOnlySubstanceUnits:Boolean,   //it is not included yet.
      boundaryCondition:Boolean,
      constant:Boolean) extends Element(metaid, notes){

    var reactantOf:Seq[Reaction] = Nil
    var productOf:Seq[Reaction] = Nil



    override def toXML:Elem = {
        <species id="Glucose_6_P" compartment="cell" initialConcentration="0.75">
            {new SBMLHandler().spitNotes(Group(notes))}
        </species>
    }
}
