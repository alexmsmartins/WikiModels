/*
 * Reaction.scala
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
case class Reaction(
    @Id
    override val metaid:String,
      override val notes:NodeSeq,
      id:String,
      name:String,
      reversible:Boolean,
      fast:boolean

) extends Element(metaid, notes){

    var lisOfReactants:Seq[Species] = Nil
    var listOfProducts:Seq[Species] = Nil

    override def toXML:Elem = {
        <reaction id="J1">
            {new SBMLHandler().spitNotes(Group(notes))}
            <listOfReactants>
                <speciesReference species="X0"/>
            </listOfReactants>
            <listOfProducts>
                <speciesReference species="S1"/>
            </listOfProducts>
            <listOfModifiers>
                <modifierSpeciesReference species="S2"/>
            </listOfModifiers>
            <kineticLaw>
                <math xmlns="http://www.w3.org/1998/Math/MathML">
                    <apply>
                        <times/> <ci> k </ci> <ci> S2 </ci> <ci> X0 </ci> <ci> c1 </ci>
                    </apply>
                </math>
                <listOfParameters>
                    <parameter id="k" value="0.1" units="per_concent_per_time"/>
                </listOfParameters>
            </kineticLaw>
        </reaction>
    }
}
