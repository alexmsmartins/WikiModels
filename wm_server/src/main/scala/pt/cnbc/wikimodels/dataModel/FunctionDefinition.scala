/*
 * FunctionDefinition.scala
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
import scala.xml.XML

import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty
import scala.reflect.BeanInfo
import scala.xml.Elem


@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class FunctionDefinition(
    @Id override val metaid:String) extends Element(metaid){

    var id:String = null
    var name:String = null
    var math:String = null


    def this(metaid:String,
             notes:NodeSeq,
             id:String,
             name:String,
             math:NodeSeq) = {
        this(metaid)
        this.setNotesFromXML(notes)
        this.id = id
        this.name = name
        this.math = math.toString
    }

    def this() = {
        this(null, Nil, null, null, Nil)
    }

    def this(xmlFunctionDefinition:Elem) = {
        this((new SBMLHandler).toStringOrNull((xmlFunctionDefinition \ "@metaid").text),
             (new SBMLHandler).checkCurrentLabelForNotes(xmlFunctionDefinition),
             (new SBMLHandler).toStringOrNull((xmlFunctionDefinition \ "@id").text),
             (new SBMLHandler).toStringOrNull((xmlFunctionDefinition \ "@name").text),
             (xmlFunctionDefinition \ "math"))
    }

    override def toXML:Elem = {
        <functionDefinition metaid={this.metaid} id={id} name={name}>
            {new SBMLHandler().genNotesFromHTML(notes)}
            {XML.loadString(this.math)}
        </functionDefinition>
    }
}
