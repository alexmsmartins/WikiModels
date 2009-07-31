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
    @Id override val metaid:String,
      override val notes:NodeSeq,
      id:String,
      name:String) extends Element(metaid, notes){

    //listOf definitions
    var listOfFunctionDefinitions:List[FunctionDefinition] = Nil
    //var listOfUnitDefinitions:List[ÛnitDefinition] = Nil
    //var listOfCompartmentTypes:List[CompartmentType] = Nil
    //var listOfSpeciesTypes:List[SpeciesType] = Nil
    var listOfCompartments:List[Compartment] = Nil
    var listOfSpecies:List[Species] = Nil
    var listOfParameters:List[Parameter] = Nil
    //var listOfInitialAssignments:List[InitialAssignment] = Nil
    //var listOfRules:List[Rule] = Nil
    var listOfConstraints:List[Constraint] = Nil
    var listOfReactions:List[Reaction] = Nil
    //var listOfEvents:List[Event] = Nil



    def this() = {
        this(null, null, null, null)
    }

    def this(xmlModel:Elem) = {
        this((xmlModel \ "metaid").text,
             (xmlModel \ "notes"),
             (xmlModel \ "id").text,
             (xmlModel \ "name").text)
        this.listOfFunctionDefinitions = Nil
        //this.listOfUnitDefinitions = Nil
        //this.listOfCompartmentTypes = Nil
        //this.listOfSpeciesTypes = Nil
        this.listOfCompartments = Nil
        this.listOfSpecies = Nil
        this.listOfParameters = Nil
        //this.listOfInitialAssignments = Nil
        //this.listOfRules = Nil
        this.listOfConstraints = Nil
        this.listOfReactions = Nil
        //this.listOfEvents = Nil
    }

    /**
     * generates a XML representation of the SBML Model
     * it does not include the DOCTYPE declaration or the sbml top tag
     * Those should be added by a proper export funtion when it is created
     * @return the XML representing the user
     */
    override def toXML():Elem = {
        <model metaid={metaid} id={id} name={name}>
            {new SBMLHandler().spitNotes(Group(notes))}
            <!--order is important according to SBML Specifications-->
            {if(listOfFunctionDefinitions != Nil)
             <listOfFunctionDefinitions>
                    {listOfFunctionDefinitions.map(i => i.toXML)}
             </listOfFunctionDefinitions> else scala.xml.Null
            }
            {if(false)
             <listOfUnitDefinitions>
             </listOfUnitDefinitions> else scala.xml.Null
            }
            {if(false)
             <listOfCompartmentTypes>
             </listOfCompartmentTypes> else scala.xml.Null
            }
            {if(false)
             <listOfSpeciesTypes>
             </listOfSpeciesTypes> else scala.xml.Null
            }
            {if(listOfCompartments != Nil)
             <listOfCompartments>
                    {listOfCompartments.map(i => i.toXML)}
             </listOfCompartments> else scala.xml.Null
            }
            {   if(listOfSpecies != Nil)
             <listOfSpecies>
                    {listOfSpecies.map(i => i.toXML)}
             </listOfSpecies> else scala.xml.Null
            }
            {      if(listOfParameters != Nil)
             <listOfParameters>
                    {listOfParameters.map(i => i.toXML)}
             </listOfParameters> else scala.xml.Null
            }
            {     if(false)
             <listOfInitialAssignments>
             </listOfInitialAssignments> else scala.xml.Null
            }
            {   if(false)
             <listOfRules>
             </listOfRules> else scala.xml.Null
            }
            {   if(listOfConstraints != Nil)
             <listOfConstraints>
                    {listOfConstraints.map(i => i.toXML)}
             </listOfConstraints> else scala.xml.Null
            }
            {   if(listOfReactions != Nil)
             <listOfReactions>
                    {listOfReactions.map(i => i.toXML)}
             </listOfReactions> else scala.xml.Null
            }
            {   if(false)
             <listOfEvents>
             </listOfEvents> else scala.xml.Null
            }
        </model>
    }
}
