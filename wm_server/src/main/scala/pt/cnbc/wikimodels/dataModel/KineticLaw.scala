/*
 * KineticLaw.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel

//import org.scala_tools.javautils.Imports._

import scala.reflect.BeanInfo
import scala.xml.Elem
import scala.xml.Group
import scala.xml.Node
import scala.xml.NodeSeq
import scala.xml.XML

import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty

import pt.cnbc.wikimodels.util.SBMLHandler

@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class KineticLaw extends Element{

    var math:String = null

    @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#hasParameter")
    var listOfParameters:Collection[Parameter] = null

    def this(metaid:String,
             notes:NodeSeq,
             math:NodeSeq) = {
        this()
        this.metaid = metaid
        this.setNotesFromXML(notes)
        this.math = math.toString
    }

    def this(xmlKineticLaw:Elem) = {
        this((new SBMLHandler).toStringOrNull((xmlKineticLaw \ "@metaid").text),
             (new SBMLHandler).checkCurrentLabelForNotes(xmlKineticLaw),
             (xmlKineticLaw \ "math"))
    }

    override def toXML:Elem = {
        <kineticLaw metaid={metaid}>
            <!--order is important according to SBML Specifications-->
            {new SBMLHandler().genNotesFromHTML(notes)}
            {XML.loadString(this.math)}
            {if(listOfParameters != null && listOfParameters.size != 0)
            <listOfParameters>
                    {listOfParameters.map(i => i.toXML)}
             </listOfParameters> else scala.xml.Null
            }
        </kineticLaw>
    }
}
