/*
 * Model.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel

import java.util.Date
import java.util.{Collection => JCollection}

import org.scala_tools.javautils.Imports._

import scala.reflect.BeanInfo
import scala.xml.Elem
import scala.xml.Group
import scala.xml.MetaData
import scala.xml.Node
import scala.xml.NodeSeq
import scala.xml.UnprefixedAttribute
import scala.xml.XML


import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty
import thewebsemantic.RdfType

import pt.cnbc.wikimodels.util.SBMLHandler
import pt.cnbc.wikimodels.exceptions.BadFormatException


/**
 * Represents a user of WikiModels
 */

@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
@RdfType("Model")
case class SBMLModel extends Element{

    var id:String = null
    var name:String = null

    //listOf definitions
    var listOfFunctionDefinitions:JCollection[FunctionDefinition] = null
    //var listOfUnitDefinitions:List[ÛnitDefinition] = List()
    //var listOfCompartmentTypes:List[CompartmentType] = List()
    //var listOfSpeciesTypes:List[SpeciesType] = List()
    @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#hasPart")
    var listOfCompartments:JCollection[Compartment] = null
    @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#hasPart")
    var listOfSpecies:JCollection[Species] = null
    @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#hasParameter")
    var listOfParameters:JCollection[SBMLParameter] = null
    //var listOfInitialAssignments:List[InitialAssignment] = List()
    //var listOfRules:List[Rule] = List()
    @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#hasPart")
    var listOfConstraints:JCollection[Constraint] = null
    @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#hasPart")
    var listOfReactions:JCollection[Reaction] = null
    //var listOfEvents:List[Event] = List()

    def this(metaid:String,
             notes:NodeSeq,
             id:String,
             name:String) = {
        this()
        this.metaid = metaid
        this.setNotesFromXML(notes)
        this.id = id
        this.name = name
    }

    /**
     * Constructor that extracts the information contained in the XML
     */
    def this(xmlModel:Elem) = {
        this((new SBMLHandler).toStringOrNull((xmlModel \ "@metaid").text),
             (new SBMLHandler).checkCurrentLabelForNotes(xmlModel),
             (new SBMLHandler).toStringOrNull((xmlModel \ "@id").text),
             (new SBMLHandler).toStringOrNull((xmlModel \ "@name").text) )
    }

    /**
     * generates a XML representation of the SBML Model
     * it does not include the DOCTYPE declaration or the sbml top tag
     * Those should be added by a proper export funtion when it is created
     * @return the XML representing the user
     */
    override def toXML():Elem = {
        Console.println("HTML to generate SBML notes in toXML = "+ notes)
        <model metaid={metaid} id={id} name={name}>
            <!--order is important according to SBML Specifications-->
            {new SBMLHandler().genNotesFromHTML(notes)}
            {if(listOfFunctionDefinitions != null && listOfFunctionDefinitions.size != 0 )
             <listOfFunctionDefinitions>
                    {listOfFunctionDefinitions.asScala.map(i => i.toXML)}
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
            {if(listOfCompartments != null && listOfCompartments.size != 0)
             <listOfCompartments>
                    {listOfCompartments.asScala.map(i => i.toXML)}
             </listOfCompartments> else scala.xml.Null
            }
            {if(listOfSpecies != null && listOfSpecies.size != 0)
             <listOfSpecies>
                    {listOfSpecies.asScala.map(i => i.toXML)}
             </listOfSpecies> else scala.xml.Null
            }
            {if(listOfParameters != null && listOfParameters.size != 0)
             <listOfParameters>
                    {listOfParameters.asScala.map(i => i.toXML)}
             </listOfParameters> else scala.xml.Null
            }
            {if(false)
             <listOfInitialAssignments>
             </listOfInitialAssignments> else scala.xml.Null
            }
            {if(false)
             <listOfRules>
             </listOfRules> else scala.xml.Null
            }
            {if(listOfConstraints != null && listOfConstraints.size != 0)
             <listOfConstraints>
                    {listOfConstraints.asScala.map(i => i.toXML)}
             </listOfConstraints> else scala.xml.Null
            }
            {if(listOfReactions != null && listOfReactions.size != 0)
             <listOfReactions>
                    {listOfReactions.asScala.map(i => i.toXML)}
             </listOfReactions> else scala.xml.Null
            }
            {if(false)
             <listOfEvents>
             </listOfEvents> else scala.xml.Null
            }
        </model>
    }

    override def theId = this.id
    override def theName = this.name
}
