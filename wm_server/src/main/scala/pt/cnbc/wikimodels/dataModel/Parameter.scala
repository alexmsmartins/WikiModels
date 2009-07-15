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
    @Id
    override val metaid:String,
      override val notes:NodeSeq,
      id:String,
      name:String,
      value: Double,
      units:String,   //UnitSId { use=”optional” } - not implemented yet
      constant: Boolean) extends Element(metaid, notes){


    def this() =
        this("",Nil, "", "", 0, null, true)

    override def toXML:Elem = {
        <parameter  metaid={metaid} id={id}
                    value={value.toString} units={units} >
            {new SBMLHandler().spitNotes(Group(notes))}
        </parameter>
    }
}
