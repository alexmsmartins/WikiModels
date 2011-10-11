package pt.cnbc.wikimodels.client.record{

/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

//package pt.cnbc.wikimodels.client.model

import scala.xml._
import net.liftweb.common._
import net.liftweb.record._
import field.{OptionalTextareaField, StringField}
import pt.cnbc.wikimodels.client.record._
import pt.cnbc.wikimodels.dataModel.{Element, SBMLModel}
import thewebsemantic.vocabulary.Foaf.Person
import net.liftweb.http.js.jquery.JqJsCmds.DisplayMessage._
import net.liftweb.util.BindHelpers._
import net.liftweb.http.{S, SHtml}
import net.liftweb.http.js.jquery.JqJsCmds.DisplayMessage
import net.liftweb.json.JsonAST.JValue
import org.sbml.libsbml.SBMLReader


trait SBMLElement[MyType <: SBMLElement[MyType]] extends Element with RestRecord[MyType] {
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
        Full ((new SBMLModelRecord).asInstanceOf[MyType] )//read went ok
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
class SBMLModelRecord() extends SBMLModel with SBMLElement[SBMLModelRecord]  {
  type MyType = SBMLModelRecord

  override def meta = SBMLModelRecord

  override protected def relativeURLasList = "model" :: metaid :: Nil

  //  ### can be validated with validate ###

  //  ### can be presented as XHtml, Json, or as a Form. ###


    <h4><span id="required_field">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;* required fields</span></h4><br/>
    <lift:ModelTree.render/>
    <ul id="model_tree" class="treeview-gray">
      <li><span>
        <h3>Model ID: <span id="required_field">*</span>
          <div id="messages"></div>
        </h3><br/>
      </span></li>                       [record]
      <li>
        <h3>Name of the model:
          <createDescription:name>
              <text:model_id/>
          </createDescription:name>
        </h3>
          <br/></li>
      <li><span><h3>Description of the model:</h3></span>
          <br/>
         What Makes Your Wish List Universal?
You can add items from any website to your Amazon Wish List with just a few clicks, making it easy to keep track of all the gifts you're wishing for, all in one place.
        <ul>
          <li><span><createDescription:description/></span><br/></li>
        </ul>
      </li>
    </ul>


  //  ### will contain fields which can be listed with allFields. ###
  object metaIdO extends MetaId(this, 100)
  object idO extends Id(this, 100)
  object nameO extends Name(this, 100)
  object notesO extends Notes(this, 1000)
  //override def fieldList = metaIdO :: idO :: nameO :: notesO :: Nil
  //  ### can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ). ###
}

//TODO - DELETE IF NOT USED FOR ANYTHING
object SBMLModelRecord extends SBMLModelRecord with RestMetaRecord[SBMLModelRecord] {
  def apply() = new SBMLModelRecord
  override def fieldOrder = List(metaIdO, idO, nameO, notesO)
  override def fields = fieldOrder
}

}

package net.liftweb.record {

import scala.xml._
import net.liftweb.common._
import net.liftweb.http.{S, SHtml}
import net.liftweb.record.field._

//Javascript handling imports
import _root_.net.liftweb.http.js.{JE,JsCmd,JsCmds}
import JsCmds._ // For implicits
import JE.{JsRaw,Str}

import pt.cnbc.wikimodels.client.record.SBMLModelRecord

class MetaId(own:SBMLModelRecord, maxLength: Int) extends StringField[SBMLModelRecord](own, maxLength) with DisplayHTMLWithLabelInOneLine[SBMLModelRecord]{

  override def setBox(in: Box[MyType]): Box[MyType] = {
    super.setBox(in) match {
      case full:Full[MyType] =>{
        owner.metaid=full.openTheBox
        full
      }
      case notfull  => notfull
    }
  }

  //the MetaId will be generated autimatically from concatenating the ids of any parent entities with '_' in between.
  override def toForm() = Empty
}

class Id(own:SBMLModelRecord, maxLength: Int) extends StringField[SBMLModelRecord](own, maxLength)
with DisplayFormWithLabelInOneLine[SBMLModelRecord] with DisplayHTMLWithLabelInOneLine[SBMLModelRecord]{

  override def setBox(in: Box[MyType]): Box[MyType] = {
    super.setBox(in) match {
      case full:Full[MyType] =>{
        owner.id=full.openTheBox
        full
      }
      case notFull  => notFull
    }
  }

  //Appears when rendering the form or the visualization
  override def displayName: String = "Model id"
  override def displayNameHtml = Full(<h3>Id</h3>)

  override def toForm = super.toForm
}

/**
 *
 */
class Name(own:SBMLModelRecord, maxLength: Int) extends StringField[SBMLModelRecord](own, maxLength)
with DisplayFormWithLabelInOneLine[SBMLModelRecord] with DisplayHTMLWithLabelInOneLine[SBMLModelRecord]
with GetSetOnwerField[String, SBMLModelRecord]{
  var _data:Box[MyType] = Empty

  override def theData_=(in:Box[MyType]) {
    _data = in
    _data match{
      //if a valid value is set then update the owner class
      case Full(x) => owner.name = x.asInstanceOf[String]
      case _ => owner.name = null //just to make sure the owner does not have valid values when errors occur
    }
  }

  override def theData:Box[MyType] = {
    //if the owner has valid data that was obtained from the wikimodels Server
    if(owner.name != null) {
      _data = Full(owner.name)
    } else {
      _data match {
        case Empty => _data = defaultValueBox;if (! this.optional_?) owner.name = defaultValue
        case Full(x) => owner.name = x
      }
    }
    _data
  }



  //Appears when rendering the form or the visualization
  override def displayName = "Model name"
  override def displayNameHtml = Full(<h3>Name</h3>)

  val msgName: String = S.attr("id_msgs") openOr "messages"
}

class Notes(own:SBMLModelRecord, size:Int) extends OptionalTextareaField[SBMLModelRecord](own, size){

  override def setBox(in: Box[MyType]): Box[MyType] = {
    super.setBox(in) match {
      case full:Full[MyType] =>{
        owner.notes=full.openTheBox
        full
      }
      case notFull  => notFull
    }
  }

  override def toForm() = Full(
  <ul class="treeview-gray" id="model_tree">
    <li><span><h3>Description of the model:</h3></span>
      <br />
      <ul>
          <li><span><textarea id="descriptionArea" maxlength="20000"></textarea></span><br /></li>
      </ul>
      <!--<script src="../classpath/js/wymeditor/wymeditor/jquery.wymeditor.pack.js" type="text/javascript" />-->


      <head>
        <!-- TODO: MAKE sure this doesn't get repeated more than once in a page -->
        <script type="text/javascript" src="/classpath/js/fckeditor/fckeditor.js"></script>
        {
          Script(
            JsRaw("""
              window.onload = function() {
                  var oFCKeditor01 = new FCKeditor( 'descriptionArea' );
                  oFCKeditor01.BasePath = '/classpath/js/fckeditor/' ;
                  oFCKeditor01.ToolbarSet = 'MyTool' ;
                  /*oFCKeditor01.ToolbarSets[&quot;Default&quot;] = [
                  /**
   * setBox overriden
   */  ['Cut','Copy','Paste','PasteText','PasteWord'],['Undo','Redo','-','Find','Replace'],
                  ['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
                  ['OrderedList','UnorderedList','-','Outdent','Indent','Blockquote'],
                  ['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],['Link','Unlink'],
                  ['Image','Table','Rule','SpecialChar'],['FontSize','TextColor','BGColor'],['Source','FitWindow']
                  ] ;*/
                  oFCKeditor01.ReplaceTextarea();
              };
            """)
          )
        }
      </head>
    </li>
    </ul>
  )
}

//#### Aux Record traits

/**
 * Mix in to a field to change its form display to    for (id <- uniqueFieldId; control <- super.toXHtml) yield
 be formatted with the label aside.
 *
 * E.g.
 *   <div id={ id + "_holder" }>
 *     <div><label for={ id }>{ displayName }</label></div>
 *     { control }
 *   </div>
 */
trait DisplayFormWithLabelInOneLine[OwnerType <: Record[OwnerType]] extends OwnedField[OwnerType] {
  override abstract def toForm:Box[NodeSeq] =
    for (id <- uniqueFieldId; control <- super.toForm)
    yield
      <span id={id + "_holder"}>
        <label for={ id }>{ displayHtml }</label>
        {control}
        <lift:msg id={id}  errorClass="lift_error"/>
      </span>
}

  trait DisplayHTMLWithLabelInOneLine[OwnerType <: Record[OwnerType]] extends OwnedField[OwnerType] {
    override abstract  def toXHtml: NodeSeq =
    //BIG ERRORS HERE... JUST CHECK http://localhost:9999/model/f
        <div id={uniqueFieldId + "_holder"}>
          <span for={ uniqueFieldId.openTheBox }>{ displayHtml }:   {this.asString}</span>
          {super.toXHtml}
          <lift:msg id={uniqueFieldId.openTheBox}  errorClass="lift_error"/>
        </div>

  }

  /**
   * Convert the field to a String... usually of the form "displayName=value"
   */

/**
 *
 */
  trait GetSetOnwerField[ThisType, OwnerType <: Record[OwnerType]] extends OwnedField[OwnerType] with TypedField[ThisType] {

    private[record] def theData_=(in:Box[MyType]):Unit
    private[record] def theData:Box[MyType]

    override def setBox(in: Box[MyType]): Box[MyType] = synchronized {
      needsDefault = false
      theData = in match {
        case _ if !canWrite_?      => Failure(noValueErrorMessage)
        case Full(_)               => set_!(in)
        case _ if optional_?       => set_!(in)
        case (f: Failure)          => set_!(f) // preserve failures set in
        case _                     => Failure(notOptionalErrorMessage)
      }
      dirty_?(true)
      theData
    }

    override def valueBox: Box[MyType] = synchronized {
      if (needsDefault) {
        needsDefault = false
        theData = defaultValueBox
      }

      if (canRead_?) theData
      else theData.flatMap(obscure)
    }

    override def asString = displayName + "=" + theData.openTheBox


  }
}