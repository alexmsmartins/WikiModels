/*
 * Model.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel
import pt.cnbc.wikimodels.util.SBMLHandler

import java.util.Date

import scala.reflect.BeanInfo
import scala.xml.Elem
import scala.xml.Group
import scala.xml.MetaData
import scala.xml.Node
import scala.xml.NodeSeq
import scala.xml.UnprefixedAttribute


import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty
import thewebsemantic.RdfType

/**
 * Represents a user of WikiModels
 */

@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
@RdfType("Model")
case class SBMLModel(
    @Id
    override val metaid:String,
      override val notes:NodeSeq,
      id:String,
      name:String) extends Element(metaid, notes){

    def this() = {
        this("", null, "", "")
    }

    /**
     * generates a XML representation of the SBML Model
     * it does not include the DOCTYPE declaration or the sbml top tag
     * Those should be added by a proper export funtion when it is created
     * @return the XML representing the user
     */
    override def toXML():Elem =
    <model metaid="metaid_0000002" id="model_0000001" name="Izhikevich2004_SpikingNeurons_Class1Excitable">
        {new SBMLHandler().spitNotes(Group(notes))}
        <!--order is important -->
        if(false)
        <listOfFunctionDefinitions>
        </listOfFunctionDefinitions>
        if(false)
        <listOfUnitDefinitions>
        </listOfUnitDefinitions>
        if(false)
        <listOfCompartmentTypes>
        </listOfCompartmentTypes>
        if(false)
        <listOfSpeciesTypes>
        </listOfSpeciesTypes>
        if(false)
        <listOfCompartments>
        </listOfCompartments>
        if(false)
        <listOfSpecies>
        </listOfSpecies>
        if(false)
        <listOfParameters>
        </listOfParameters>
        if(false)
        <listOfInitialAssignments>
        </listOfInitialAssignments>
        if(false)
        <listOfRules>
        </listOfRules>
        if(false)
        <listOfConstraints>
        </listOfConstraints>
        if(false)
        <listOfReactions>
        </listOfReactions>
        if(false)
        <listOfEvents>
        </listOfEvents>
    </model>
}
