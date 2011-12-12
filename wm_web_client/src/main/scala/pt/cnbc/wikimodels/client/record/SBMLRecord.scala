import net.liftweb.record.Record
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
import net.liftweb.util.BindHelpers._
import net.liftweb.http.{S, SHtml}
import net.liftweb.json.JsonAST.JValue
import org.sbml.libsbml.SBMLReader
import pt.cnbc.wikimodels.dataVisitors.SBML2BeanConverter
import visitor.SBMLRecordVisitor
import scala.collection.JavaConversions._
import alexmsmartins.log.LoggerWrapper

//Javascript handling imports
import _root_.net.liftweb.http.js.{JE,JsCmd,JsCmds}
import JsCmds._ // For implicifts
import JE.{JsRaw,Str}


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
      <div class="demo cupertino">
        <div id="accordion1" class="accordion ui-accordion ui-widget ui-helper-reset ui-accordion-icons">
          <h3 id="accord_c" class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top">
            <a href="#accord_c" >
              Compartments
              <button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Add Compartment</button>
            </a>
          </h3>
          <div class="toggle_container">
            <div id="accordion2" class="block">
              <h3 id="accord_c_c1" class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top">
                <a href="#accord_c_c1">
                  Compartment 1
                  <button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Edit</button>   <button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Delete</button>
                  <button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Add Species</button>
                </a>
              </h3>
              <div class="toggle_container">
                <div class="block">
                  <p>
                    Mauris mauris ante, blandit et, ultrices a, suscipit eget, quam. Integer
                    ut neque. Vivamus nisi metus, molestie vel, gravida in, condimentum sit
                    amet, nunc. Nam a nibh. Donec suscipit eros. Nam mi. Proin viverra leo ut
                    odio. Curabitur malesuada. Vestibulum a velit eu ante scelerisque vulputate.
                  </p>
                </div>
              </div>
              <h3 id="accord_c_c2" class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top">
                <a href="#accord_c_c2">
                  Compartment 2
                  <button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Edit</button>   <button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Delete</button>   <button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Add Species</button>
                </a>
              </h3>
              <div class="toggle_container">
                <div class="block">
                  <p>
                    Sed non urna. Donec et ante. Phasellus eu ligula. Vestibulum sit amet
                    purus. Vivamus hendrerit, dolor at aliquet laoreet, mauris turpis porttitor
                    velit, faucibus interdum tellus libero ac justo. Vivamus non quam. In
                    suscipit faucibus urna.
                  </p>
                </div>
              </div>
              <h3 id="accord_c_c3" class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top">
                <a href="#accord_c_c3">Compartment 3
                  <button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Edit</button>   <button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Delete</button>
                  <button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Add Species</button>
                </a>
              </h3>
              <div class="toggle_container">
                <div  class="block">
                  <p>
                    Nam enim risus, molestie et, porta ac, aliquam ac, risus. Quisque lobortis.
                    Phasellus pellentesque purus in massa. Aenean in pede. Phasellus ac libero
                    ac tellus pellentesque semper. Sed ac felis. Sed commodo, magna quis
                    lacinia ornare, quam ante aliquam nisi, eu iaculis leo purus venenatis dui.
                  </p>
                  <ul>
                    <li>List item one</li>
                    <li>List item two</li>
                    <li>List item three</li>
                  </ul>
                </div>
              </div>
            </div>

          </div>
          <h3 id="accord_s" class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top">
            <a href="#accord_s">
              Species
              <button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Add Species</button>
            </a>
          </h3>
          <div  class="toggle_container">
            <div id="accordion3" class="block">
              <h3 id="accord_s_s1" class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top">
                <a href="#accord_s_s1">
                  Species 1
                  <button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Edit</button>
                  <button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Delete</button>
                </a>
              </h3>
              <div class="toggle_container">
                <div class="block">
                  <p>
                    Mauris mauris ante, blandit et, ultrices a, suscipit eget, quam. Integer
                    ut neque. Vivamus nisi metus, molestie vel, gravida in, condimentum sit
                    amet, nunc. Nam a nibh. Donec suscipit eros. Nam mi. Proin viverra leo ut
                    odio. Curabitur malesuada. Vestibulum a velit eu ante scelerisque vulputate.
                  </p>
                </div>
              </div>
              <h3 id="accord_s_s2" class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top">
                <a href="#accord_s_s2">
                  Species 2
                  <button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Edit</button>
                  <button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Delete</button>
                </a>
              </h3>
              <div class="toggle_container">
                <div class="block">
                  <p>
                    Sed non urna. Donec et ante. Phasellus eu ligula. Vestibulum sit amet
                    purus. Vivamus hendrerit, dolor at aliquet laoreet, mauris turpis porttitor
                    velit, faucibus interdum tellus libero ac justo. Vivamus non quam. In
                    suscipit faucibus urna.
                  </p>
                </div>
              </div>
              <h3 id="accord_s_s3" class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top">
                <a href="#accord_s_s3">
                  Species 3
                  <button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Edit</button>
                  <button type="button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Delete</button>
                </a>
              </h3>
              <div class="toggle_container">
                <div class="block">
                  <p>
                    Nam enim risus, molestie et, porta ac, aliquam ac, risus. Quisque lobortis.
                    Phasellus pellentesque purus in massa. Aenean in pede. Phasellus ac libero
                    ac tellus pellentesque semper. Sed ac felis. Sed commodo, magna quis
                    lacinia ornare, quam ante aliquam nisi, eu iaculis leo purus venenatis dui.
                  </p>
                  <ul>
                    <li>List item one</li>
                    <li>List item two</li>
                    <li>List item three</li>
                  </ul>
                </div>
              </div>
            </div>

          </div>
        </div>
        {Script(
        JsRaw("""
          jQuery(document).ready(function(){
          //Hide (Collapse) the toggle containers on load
          $(".toggle_container").hide();

          //Switch the "Open" and "Close" state per click then slide up/down (depending on open/close state)
          $("h3.trigger").click(function(){
            $(this).toggleClass("active").next().slideToggle("slow");
            return false; //Prevent the browser jump to the link anchor
          });
          });
        """))}
      </div><!-- End demo -->
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
