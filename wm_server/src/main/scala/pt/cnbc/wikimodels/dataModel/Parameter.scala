/*
 * Parameter.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel

import pt.cnbc.wikimodels.util.SBMLHandler
import scala.xml.Node
import scala.xml.NodeSeq

import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty
import scala.reflect.BeanInfo
import scala.xml.Group
import scala.xml.Elem

@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class Parameter(
    @Id override val metaid:String) extends Element(metaid){

    var id:String = null
    var name:String = null

    /**
     * The optional attribute value determines the value (of type double)
     * assigned to the identifier. A missing value implies that the value either
     * is unknown, or to be obtained from an external source, or determined by
     * an initial assignment (Section 4.10) or a rule (Section 4.11) elsewhere
     * in the model.
     */
    var value:java.lang.Double = null
    var units:String = null   //UnitSId { use=”optional” } - not implemented yet

    /**
     * The Parameter object has an optional boolean attribute named constant
     * which indicates whether the parameter’s value can vary during a simulation.
     * The attribute’s default value is “true”. A value of “false” indicates the
     * parameter’s value can be changed by rules (see Section 4.11) and that the
     * value is actually intended to be the initial value of the parameter.
     */
    var constant:Boolean = true

    def this(metaid:String,
             notes:NodeSeq,
             id:String,
             name:String,
             value:java.lang.Double,
             units:String,
             constant:Boolean) = {
        this(metaid)
        this.setNotesFromXML(notes)
        this.id = id
        this.name = name
        this.value = value
        this.units = units
        this.constant = constant
    }

    def this() =
        this(null,Nil, null, null, null, null, true)


    def this(xmlParameter:Elem) = {
        this((new SBMLHandler).toStringOrNull((xmlParameter \ "@metaid").text),
             (new SBMLHandler).checkCurrentLabelForNotes(xmlParameter),
             (new SBMLHandler).toStringOrNull((xmlParameter \ "@id").text),
             (new SBMLHandler).toStringOrNull((xmlParameter \ "@name").text),
             try{
                java.lang.Double.parseDouble( (xmlParameter \ "@value").text )
            } catch {
                case _ => null
            },
            (new SBMLHandler).toStringOrNull((xmlParameter \ "@units").text),
            (xmlParameter \ "@constant").text.toBoolean
        )
    }



    override def toXML:Elem = {
        <parameter metaid={metaid} id={id} name={name} value={
                if(value == null) null.asInstanceOf[String] else {value.toString}
            } units={units} constant={constant.toString}>
            {new SBMLHandler().genNotesFromHTML(notes)}
        </parameter>
    }
}
