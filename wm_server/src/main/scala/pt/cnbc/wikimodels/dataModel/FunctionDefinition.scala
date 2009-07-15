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

import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty
import scala.reflect.BeanInfo
import scala.xml.Elem


@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class FunctionDefinition(
    @Id
    @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#metaid")
    override val metaid:String,
      @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#notes")
    override val notes:NodeSeq,
      @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#id")
    id:String,
      @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#notes")
    name:String) extends Element(metaid, notes){

    override def toXML:Elem = {
        <functionDefinition id="pow3">
            {new SBMLHandler().spitNotes(Group(notes))}
            <math xmlns="http://www.w3.org/1998/Math/MathML">
                <lambda>
                    <bvar><ci> x </ci></bvar>
                    <apply>
                        <power/>
                        <ci> x </ci>
                        <cn> 3 </cn>
                    </apply>
                </lambda>
            </math>
        </functionDefinition>
    }
}
