/*
 * Compartment.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel
import pt.cnbc.wikimodels.exceptions.BadFormatException
import pt.cnbc.wikimodels.util.SBMLHandler
import scala.xml.Node
import scala.xml.NodeSeq
import scala.xml.XML

import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty
import scala.reflect.BeanInfo
import scala.xml.Group
import scala.xml.Elem


@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/sbml.owl#")
case class Compartment(
    @Id
    override val metaid:String,
      override val notes:NodeSeq,
      id:String,
      name:String,
      compartmentType:String, //not implemented yet
      spacialDimensions:Int,
      size:Double,
      units:String,  //not implemented yet
      outside:String,
      constant:Boolean) extends Element(metaid, notes){

    if(id == null){
        throw new BadFormatException(
            "Compartment constructor should not receive a null id "+
            "since it is mandatory in SBML.")
    }

    def this() = this("",Nil,"","","",0,0,"","",false)

    def this(xmlCompartment:Elem) = {
        this(
            (xmlCompartment \ "@metaid").toString,
              (xmlCompartment \ "notes"),
              (xmlCompartment \ "@id" ).toString,
              (xmlCompartment \ "@name").toString,
              (xmlCompartment \ "@compartmentType").toString,
              (xmlCompartment \ "@spacialDimensions").toString.toInt,
              (xmlCompartment \ "@size").toString.toDouble,
              (xmlCompartment \ "@units").toString,
              (xmlCompartment \ "@outside").toString,
              (xmlCompartment \ "@constant").toString.toBoolean)
    }

    override def toXML:Elem = {
        <compartment metaid={metaid} id={id} name={name}
            compartmentType={compartmentType}
            spacialDimensions={spacialDimensions.toString}
            size={size.toString} units={units} outside={outside}
            constant={constant.toString}>
            {new SBMLHandler().spitNotes(Group(notes))}
        </compartment>
    }
}
