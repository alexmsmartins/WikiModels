/*
 * Constraint.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel
import scala.xml.Node
import scala.xml.NodeSeq

import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty
import scala.reflect.BeanInfo
import scala.xml.Elem


@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class Constraint(
    @Id
    override val metaid:String,
    override val notes:NodeSeq,
    id:String,
    name:String,
    math:Elem,
    message:Elem) extends Element(metaid, notes){

    override def toXML:Elem = {
        <constraint metaid={metaid} id={id}>
            <notes>
                <p xmlns="http://www.w3.org/1999/xhtml"> Notes about this Constraint. </p>
            </notes>
            <math xmlns="http://www.w3.org/1998/Math/MathML">
                <apply>
                    <and/>
                    <apply> <lt/> <cn> 1 </cn> <ci> S1 </ci> </apply>
                    <apply> <lt/> <ci> S1 </ci> <cn> 100 </cn> </apply>
                </apply>
            </math>
            <message>
                <p xmlns="http://www.w3.org/1999/xhtml"> Species S1 is out of range. </p>
            </message>
        </constraint>
    }
}
