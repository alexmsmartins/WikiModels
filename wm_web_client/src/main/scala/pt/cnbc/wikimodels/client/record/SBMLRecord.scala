import scala.xml._
import net.liftweb.common._
import net.liftweb.record._
import field.{OptionalTextareaField, StringField}
import pt.cnbc.wikimodels.client.record._
import pt.cnbc.wikimodels.dataModel._
import net.liftweb.util.BindHelpers._
import net.liftweb.http.{S, SHtml}
import alexmsmartins.log.LoggerWrapper

//Javascript handling imports
import _root_.net.liftweb.http.js.{JE,JsCmd,JsCmds}
import JsCmds._ // For implicifts
import JE.{JsRaw,Str}


package pt.cnbc.wikimodels.client.record{

import pt.cnbc.wikimodels.client.snippet.CommentSnip
import net.liftweb.common.Box
import pt.cnbc.wikimodels.sbmlVisitors.dataVisitors.SBML2BeanConverter
import visitor.SBMLRecordVisitor


/*
* Copyright (c) 2011. Alexandre Martins. All rights reserved.
*/



trait SBaseRecord[MyType <: SBaseRecord[MyType]] extends Element with RestRecord[MyType] with CommentSnip {
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
    val url = connection.postRequest(relativeCreationURL, this.toXML)
    connection.getStatusCode match {
      case 201 => {
        saved_? = true
        //the server may modify the metaid to avoid clashes
        this.metaid = url.toString.split("/").last
        Full(this)//create went ok
      }
      case 404 => ParamFailure("Error creating " + this.sbmlType + " with metaid " + this.metaid + ".", this)
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
        val loadedRecord:Box[MyType] =  Full ((SBMLRecordVisitor.createModelRecordFrom(  SBML2BeanConverter.visitModel( content )  )).asInstanceOf[MyType] )//read went ok
        debug("Read of " + loadedRecord + "aaa" )
        loadedRecord
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

  def comments:NodeSeq = {
    disqusFromMetaId(this.metaid)
  }

  var parent:Box[SBaseRecord[_]]
}

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 03-07-2011       
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
class SBMLModelRecord() extends SBMLModel with SBaseRecord[SBMLModelRecord] with LoggerWrapper {
  type MyType = SBMLModelRecord


  //listOf definitions for record
  //TODO - find a better solution to this. Parents' listOfXXX definitions is a big problem
  var listOfFunctionDefinitionsRec:List[FunctionDefinitionRecord] = Nil
  //var listOfUnitDefinitions:List[ÛnitDefinition] = List()
  //var listOfCompartmentTypes:List[CompartmentType] = List()
  //var listOfSpeciesTypes:List[SpeciesType] = List()
  var listOfCompartmentsRec: List[CompartmentRecord] = Nil
  var listOfSpeciesRec: List[SpeciesRecord] = Nil
  var listOfParametersRec: List[ParameterRecord] = Nil
  //var listOfInitialAssignments:List[InitialAssignment] = List()
  //TODO listOfRules is missing
  //var listOfRules: java.util.Collection[Rules] = null
  var listOfConstraintsRec: List[ConstraintRecord] = Nil
  //var listOfReactionsRec: List[ReactionRecord] = Nil
  //var listOfEvents:List[Event] = List()


  override def meta = SBMLModelRecord

  override protected def relativeURLasList = "model" :: metaid :: Nil
  override protected def relativeCreationURLasList = "model" :: Nil

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
      <head>
        <link type="text/css" rel="stylesheet" href="/css/sbml_present.css"></link>
      </head>
      {super.toXHtml}
      <div class="demo cupertino changeline">
        <div id="accordion1" class="accordion ui-accordion ui-widget ui-helper-reset ui-accordion-icons">
          <h3 id="accord_c" class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top changeline">
            <a href="#accord_c">
              {this.listOfCompartmentsRec.size} Compartments
              <form style='display:inline;' >{SHtml.button(
                Text("Add Compartment"),
                () => {
                   debug("Button to add compartment, pressed.")
                   S.redirectTo(this.relativeURL + "/createcompartment" )
                },
                "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
              )}</form>
            </a>
          </h3>
          <div class="toggle_container">
            <div id="accordion_c" class="block">{
              this.listOfCompartmentsRec.map(i => {
              <h3 id={"accord_c_"+i.metaid} class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top">
                <a href={"#accord_c_"+i.metaid}>
                  {i.id}
                  <form style='display:inline;' >{SHtml.button(Text("Edit"),
                    () => {
                      debug("Button to edit compartment, pressed.")
                      S.redirectTo(i.relativeURL + "/edit" )
                    },
                    "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                  )}</form>
                  <form style='display:inline;' >{SHtml.button(Text("Delete"),
                    () => {
                      debug("Button to delete compartment, pressed.")
                      S.redirectTo(i.relativeURL )
                    },
                    "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                  )}</form>
                </a>
              </h3>
              <div class="toggle_container">
                <div class="block">{
                    i.toXHtml
                  }
                </div>
              </div>})
              }
            </div>
          </div>
          <h3 id="accord_s" class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top changeline">
            <a href="#accord_s">
              {this.listOfSpeciesRec.size} Species
              <form style='display:inline;' >{SHtml.button(Text("Add Species"),
                () => {
                  debug("Button to add species pressed.")
                  S.redirectTo(this.relativeURL + "/createspecies" )
                },
                "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
              )}</form>

            </a>
          </h3>
          <div  class="toggle_container">
            <div id="accordion_s" class="block">{
              this.listOfSpeciesRec.map(i => {
                <h3 id={"accord_s_"+i.metaid} class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top">
                  <a href={"#accord_s_"+i.metaid}>
                    {i.id}
                    <form style='display:inline;' >{SHtml.button(Text("Edit"),
                      () => {
                        debug("Button to edit species, pressed.")
                        S.redirectTo(i.relativeURL + "/edit" )
                      },
                      "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                    )}</form>
                    <form style='display:inline;' >{SHtml.button(Text("Delete"),
                      () => {
                        debug("Button to delete species, pressed.")
                        S.redirectTo(i.relativeURL )
                      },
                      "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                    )}</form>
                  </a>
                </h3>
                  <div class="toggle_container">
                    <div class="block">{
                      i.toXHtml
                      }
                    </div>
                  </div>})
              }
            </div>
          </div>

          <h3 id="accord_p" class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top changeline">
            <a href="#accord_p">
              {this.listOfParametersRec.size} Parameters
              <form style='display:inline;' >{SHtml.button(Text("Add Parameters"),
                () => {
                  debug("Button to add parameters pressed.")
                  S.redirectTo(this.relativeURL + "/createparameter" )
                },
                "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
              )}</form>

            </a>
          </h3>
          <div  class="toggle_container">
            <div id="accordion_p" class="block">{
              this.listOfParametersRec.map(i => {
                <h3 id={"accord_p_"+i.metaid} class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top">
                  <a href={"#accord_p_"+i.metaid}>
                    {i.id}
                    <form style='display:inline;' >{SHtml.button(Text("Edit"),
                      () => {
                        debug("Button to edit parameter, pressed.")
                        S.redirectTo(i.relativeURL + "/edit" )
                      },
                      "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                    )}</form>
                    <form style='display:inline;' >{SHtml.button(Text("Delete"),
                      () => {
                        debug("Button to delete parameter, pressed.")
                        S.redirectTo(i.relativeURL )
                      },
                      "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                    )}</form>
                  </a>
                </h3>
                  <div class="toggle_container">
                    <div class="block">{
                      i.toXHtml
                      }
                    </div>
                  </div>})
              }
            </div>
          </div>

          <h3 id="accord_fd" class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top changeline">
            <a href="#accord_fd">
              {this.listOfFunctionDefinitionsRec.size} Function Definition
              <form style='display:inline;' >{SHtml.button(Text("Add Function definition"),
                () => {
                  debug("Button to add function definition pressed.")
                  S.redirectTo(this.relativeURL + "/createfunctiondefinition" )
                },
                "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
              )}</form>

            </a>
          </h3>
          <div  class="toggle_container">
            <div id="accordion_fd" class="block">{
              this.listOfFunctionDefinitionsRec.map(i => {
                <h3 id={"accord_fd_"+i.metaid} class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top">
                  <a href={"#accord_fd_"+i.metaid}>
                    {i.id}
                    <form style='display:inline;' >{SHtml.button(Text("Edit"),
                      () => {
                        debug("Button to edit function definition, pressed.")
                        S.redirectTo(i.relativeURL + "/edit" )
                      },
                      "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                    )}</form>
                    <form style='display:inline;' >{SHtml.button(Text("Delete"),
                      () => {
                        debug("Button to delete function definition, pressed.")
                        S.redirectTo(i.relativeURL )
                      },
                      "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                    )}</form>
                  </a>
                </h3>
                  <div class="toggle_container">
                    <div class="block">{
                      i.toXHtml
                      }
                    </div>
                  </div>})
              }
            </div>
          </div>

          <h3 id="accord_c" class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top changeline">
            <a href="#accord_c">
              {this.listOfConstraintsRec.size} Constraints
              <form style='display:inline;' >{SHtml.button(Text("Add Constraints"),
                () => {
                  debug("Button to add constraints pressed.")
                  S.redirectTo(this.relativeURL + "/createconstraints" )
                },
                "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
              )}</form>

            </a>
          </h3>
          <div  class="toggle_container">
            <div id="accordion3" class="block">{
              this.listOfConstraintsRec.map(i => {
                <h3 id={"accord_c_"+i.metaid} class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top">
                  <a href={"#accord_c_"+i.metaid}>
                    {i.id}
                    <form style='display:inline;' >{SHtml.button(Text("Edit"),
                      () => {
                        debug("Button to edit constraints, pressed.")
                        S.redirectTo(i.relativeURL + "/edit" )
                      },
                      "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                    )}</form>
                    <form style='display:inline;' >{SHtml.button(Text("Delete"),
                      () => {
                        debug("Button to delete constraints, pressed.")
                        S.redirectTo(i.relativeURL )
                      },
                      "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                    )}</form>
                  </a>
                </h3>
                  <div class="toggle_container">
                    <div class="block">{
                      i.toXHtml
                    }
                    </div>
                  </div>})
              }
            </div>
          </div>

          <!--<h3 id="accord_r" class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top changeline">
            <a href="#accord_r">
              {this.listOfReactionsRec.size} Species
              <form style='display:inline;' >{SHtml.button(Text("Add Species"),
                () => {
                  debug("Button to add reaction pressed.")
                  S.redirectTo(this.relativeURL + "/createreaction" )
                },
                "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
              )}</form>

            </a>
          </h3>
          <div  class="toggle_container">
            <div id="accordion_r" class="block">{
              this.listOfReactionsRec.map(i => {
                <h3 id={"accord_r_"+i.metaid} class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top">
                  <a href={"#accord_r_"+i.metaid}>
                    {i.id}
                    <form style='display:inline;' >{SHtml.button(Text("Edit"),
                      () => {
                        debug("Button to edit reaction, pressed.")
                        S.redirectTo(i.relativeURL + "/edit" )
                      },
                      "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                    )}</form>
                    <form style='display:inline;' >{SHtml.button(Text("Delete"),
                      () => {
                        debug("Button to delete reaction, pressed.")
                        S.redirectTo(i.relativeURL )
                      },
                      "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                    )}</form>
                  </a>
                </h3>
                  <div class="toggle_container">
                    <div class="block">{
                      i.toXHtml
                      }
                    </div>
                  </div>})
              }
            </div>
          </div>-->

          <h3 id="accord_s" class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top changeline">
            <a href="#accord_s">
              Reactions
              <form style='display:inline;' >{SHtml.button(Text("Add reaction"),
                () => {
                  debug("Button to add reactions pressed.")
                  S.redirectTo(this.relativeURL + "/createreaction" )
                },
                "class" ->"ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
              )}</form>

            </a>
          </h3>
          <div  class="toggle_container">
            <div id="accordion3" class="block">
              <h3 id="accord_s_s1" class="trigger ui-accordion-header ui-helper-reset ui-state-default ui-corner-top">
                <a href="#accord_s_s1">
                  Reaction 1
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
                <a href="#acctoggle_containerord_s_s2">
                  Reaction 2
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
                  Reaction 3
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
            $("h3.trigger :button").click(function(e){
              e.stopPropagation();
            });
          });
        """))}
      </div><!-- End demo -->
      <div class="changeline">{this.comments}</div>
    </div>
  }

  //  ### will contain fields which can be listed with allFields. ###
  object metaIdO extends MetaId(this, 100) 
  object idO extends Id(this, 100)
  object nameO extends Name(this, 100)
  object notesO extends Notes(this, 1000)
  //  ### can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ). ###
  override def parent:Box[SBaseRecord[_]] = Empty
  override def parent_=(p:Box[SBaseRecord[_]] ) { debug("Empty def parent SHOULD NOT be called!!!")}
}



//TODO - DELETE IF NOT USED FOR ANYTHING
object SBMLModelRecord extends SBMLModelRecord with RestMetaRecord[SBMLModelRecord] {
  def apply() = new SBMLModelRecord
  override def fieldOrder = List(metaIdO, idO, nameO, notesO)
  override def fields = fieldOrder
}


}


package net.liftweb.record{

}
