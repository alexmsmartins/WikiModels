import net.liftweb.record.Record
import alexmsmartins.log.LoggerWrapper
import net.liftweb.common._
import scala.xml.XML
import scala.xml.XML._
import scala.xml.NodeSeq
import scala.xml._
import net.liftweb.common._
import net.liftweb.record._
import field.{OptionalTextareaField, StringField}
import pt.cnbc.wikimodels.client.record._
import pt.cnbc.wikimodels.dataModel._
import thewebsemantic.vocabulary.Foaf.Person
import net.liftweb.http.js.jquery.JqJsCmds.DisplayMessage._
import net.liftweb.util.BindHelpers._
import net.liftweb.http.{S, SHtml}
import net.liftweb.http.js.jquery.JqJsCmds.DisplayMessage
import net.liftweb.json.JsonAST.JValue
import org.sbml.libsbml.SBMLReader
import pt.cnbc.wikimodels.dataVisitors.SBML2BeanConverter
import visitor.SBMLRecordVisitor
import scala.collection.JavaConversions._

package pt.cnbc.wikimodels.client.record{

/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */



trait SBaseRecord[MyType <: SBaseRecord[MyType]] extends Element with RestRecord[MyType] {
  self : MyType =>
  import pt.cnbc.wikimodels.snippet.User
  /**
   * informs if the fields of this record have information or not
   */
  var clean_? = true

  /**
   * Informs if this RestRecord is already saved in the WikiModels Knowledgebase.
   * This is true if an existing element is loaded from the RESTful service or after
   * a new element gets successfully saved into it.
   */
  var saved_? = false
  //  ### can be created ###

  /**
   * CRUD operation for creating a REST [record]Record
   */
  override def createRestRec():Box[MyType] = {
    connection.postRequest("/" + this.sbmlType.toLowerCase , this.toXML)
    connection.getStatusCode match {
      case 201 => saved_? = true;Full(this)//create went ok
      case 404 => ParamFailure("Error reading " + this.metaid + ". This element does not exist.", this)
      case status => handleStatusCodes(status, "creating " + sbmlType + " with " + this.metaid)
    }
  }

  /**
   * CRUD operation for reading a REST Record
   */
  override def readRestRec(_metaid:String):Box[MyType] = {
    this.metaid = _metaid
    val content = User.restfulConnection.getRequest(relativeURL)
    User.restfulConnection.getStatusCode match {
      case 200 =>{
        clean_? = false
        //FIXME this should be replaced by a call to a XML to SBMLElement converter
        Full ((SBMLRecordVisitor.createSBMLModelRecordFrom(  SBML2BeanConverter.visitModel( content )  )).asInstanceOf[MyType] )//read went ok
      }
      case 404 => ParamFailure("Error reading " + this.metaid + ". This element does not exist.", this)
      case status => handleStatusCodes(status, "reading " + sbmlType + " with " + this.metaid)
    }
  }

  /**
   * CRUD operation for updating a REST Record
   */
  override def updateRestRec():Box[MyType] = {
    User.restfulConnection.putRequest(relativeURL, this.toXML)
    User.restfulConnection.getStatusCode match {
      case 200 => saved_? = true;Full(this)//update went ok
      case 404 => ParamFailure("Error updateing " + this.metaid + ". This element does not exist.", this)
      case status => handleStatusCodes(status, "updataing " + sbmlType + " with " + this.metaid)
    }
  }

  /**
   * CRUD operation for deleting a REST Record
   */
  override def deleteRestRec():Box[MyType] = {
    User.restfulConnection.deleteRequest(relativeURL)
    User.restfulConnection.getStatusCode match {
      case 204 => saved_? = false;Full(this)//delete went ok
      case 404 => ParamFailure("Error de[record]leting " + this.metaid + ". This element does not exist.", this)
      case status => handleStatusCodes(status, "deleting "+ sbmlType + " with " + this.metaid)
    }
  }
}

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 03-07-2011       
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
class SBMLModelRecord() extends SBMLModel with SBaseRecord[SBMLModelRecord]  {
  type MyType = SBMLModelRecord

  override def meta = SBMLModelRecord

  override protected def relativeURLasList = "model" :: metaid :: Nil

  //  ### can be validated with validate ###

  //  ### can be presented as XHtml, Json, or as a Form. ###

    /**
   * Present the model as a form and execute the function on submission of the form
   *
   * @param f - the function to execute on form submission
   *
   * @return the form
   */
  //override def toForm(f: MyType => Unit): NodeSeq = meta.toForm(this) ++ (SHtml.hidden(() => f(this)))
    
    override def toXHtml = {
    <div>
      <header>
        <link type="text/css" rel="stylesheet" href="/css/sbml_present.css"></link>
      </header>
      {super.toXHtml}
      <!--{super.listOfCompartments.map(
        i => scala.xml.Text("")
      )}-->
    </div>
  }

  //  ### will contain fields which can be listed with allFields. ###
  object metaIdO extends MetaId(this, 100) 
  object idO extends Id(this, 100)
  object nameO extends Name(this, 100)
  object notesO extends Notes(this, 1000)
  //  ### can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ). ###
}



//TODO - DELETE IF NOT USED FOR ANYTHING
object SBMLModelRecord extends SBMLModelRecord with RestMetaRecord[SBMLModelRecord] {
  def apply() = new SBMLModelRecord
  override def fieldOrder = List(metaIdO, idO, nameO, notesO)
  override def fields = fieldOrder
}

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 29-11-2011   
 * Time: 22:35
 * To change this template use File | Settings | File Templates.
 */
class CompartmentRecord() extends Compartment with SBaseRecord[CompartmentRecord]  {
  type MyType = CompartmentRecord

  override def meta = CompartmentRecord

  override protected def relativeURLasList = "model" :: metaid :: "Compartment" :: metaid :: Nil

  //  ### can be validated with validate ###

  //  ### can be presented as XHtml, Json, or as a Form. ###

  override def toXHtml = {
    <div>
      <header>
        <link type="text/css" rel="stylesheet" href="/css/sbml_present.css"></link>
      </header>Nil
      {super.toXHtml}
    </div>
  }

  //  ### will contain fields which can be listed with allFields. ###
  object metaIdO extends MetaId(this, 100)
  object idO extends Id(this, 100)
  object nameO extends Name(this, 100)
  object notesO extends Notes(this, 1000)
  object spatialDimensions0 extends SpatialDimensions(this)
  //  ### can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ). ###
}



//TODO - DELETE IF NOT USED FOR ANYTHING
object CompartmentRecord extends CompartmentRecord with RestMetaRecord[CompartmentRecord] {
  def apply() = new CompartmentRecord
  override def fieldOrder = List(metaIdO, idO, nameO, notesO, spatialDimensions0)
  override def fields = fieldOrder
}

}



package net.liftweb.record{

}
