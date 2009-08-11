/*
 * Species.scala
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

import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty

import pt.cnbc.wikimodels.util.SBMLHandler

@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class Species(
    @Id override val metaid:String) extends Element(metaid){

    var id:String = null
    var name:String = null
    var speciesType:String = null  //it is not included yet.
    var compartment:String = null
    var initialAmount:java.lang.Double = null
    var initialConcentration:java.lang.Double = null
    var substanceUnits:String = null   //it is not included yet.
    var hasOnlySubstanceUnits:Boolean = false   //it is not included yet.
    var boundaryCondition:Boolean = false
    var constant:Boolean = false

    var reactantOf:java.util.Collection[Reaction] = null
    var productOf:java.util.Collection[Reaction] = null

    def this(metaid:String,
             notes:NodeSeq,
             id:String,
             name:String,
             speciesType:String,  //it is not included yet.
             compartment:String,
             initialAmount:java.lang.Double,
             initialConcentration:java.lang.Double,
             substanceUnits:String,   //it is not included yet.
             hasOnlySubstanceUnits:Boolean,   //it is not included yet.
             boundaryCondition:Boolean,
             constant:Boolean) = {
        this(metaid)
        this.setNotesFromXML(notes)
        this.id = id
        this.name = name
        this.speciesType = speciesType
        this.compartment = compartment
        this.initialAmount = initialAmount
        this.initialConcentration = initialConcentration
        this.substanceUnits = substanceUnits
        this.hasOnlySubstanceUnits = hasOnlySubstanceUnits
        this.boundaryCondition = boundaryCondition
        this.constant = constant
    }

    def this() = {
        this(null, Nil, null, null, null, null, null, null, null,
             false, false, false)
    }


    def this(xmlSpecies:Elem) = {
        this((new SBMLHandler).toStringOrNull((xmlSpecies \ "@metaid").text),
             (new SBMLHandler).checkCurrentLabelForNotes(xmlSpecies),
             (new SBMLHandler).toStringOrNull((xmlSpecies \ "@id").text),
             (new SBMLHandler).toStringOrNull((xmlSpecies \ "@name").text),
             (new SBMLHandler).toStringOrNull((xmlSpecies \ "@speciesType").text),
             (new SBMLHandler).toStringOrNull((xmlSpecies \ "@compartment").text),
             try{
                java.lang.Double.parseDouble( (xmlSpecies \ "@initialAmount").text )
            } catch {
                case _ => null
            },
             try{
                java.lang.Double.parseDouble( (xmlSpecies \ "@initialConcentration").text )
            } catch {
                case _ => null
            },
             (new SBMLHandler).toStringOrNull((xmlSpecies \ "@substanceUnits").text),
             (xmlSpecies \ "@hasOnlySubstanceUnits").text.toBoolean,
             (xmlSpecies \ "@boundaryCondition").text.toBoolean,
             (xmlSpecies \ "@constant").text.toBoolean)
    }

    override def toXML:Elem = {
        <species metaid={metaid} id={id} name={name} 
            speciesType={speciesType} compartment={compartment}
            initialAmount={
                if(initialAmount == null) null.asInstanceOf[String] else {initialAmount.toString}
            }
            initialConcentration={
                if(initialConcentration == null) null.asInstanceOf[String] else {initialConcentration.toString}
            }
            substanceUnits={substanceUnits}
            hasOnlySubstanceUnits={hasOnlySubstanceUnits.toString}
            boundaryCondition={boundaryCondition.toString}
            constant={constant.toString} >
            <!--order is important according to SBML Specifications-->
            {new SBMLHandler().genNotesFromHTML(notes)}
        </species>
    }
}
