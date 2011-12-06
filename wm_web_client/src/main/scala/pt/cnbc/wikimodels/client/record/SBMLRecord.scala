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
  override def fields = metaIdO :: idO :: nameO :: notesO :: Nil
  //  ### can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ). ###
}



//TODO - DELETE IF NOT USED FOR ANYTHING
object CompartmentRecord extends CompartmentRecord with RestMetaRecord[CompartmentRecord] {
  def apply() = new CompartmentRecord
  override def fieldOrder = List(metaIdO, idO, nameO, notesO)
  override def fields = fieldOrder
}

}

package net.liftweb.record {

import net.liftweb.common._
import net.liftweb.http.{S, SHtml}
import net.liftweb.record.field._
import alexmsmartins.log.LoggerWrapper
import alexmsmartins.log.LoggerWrapper._
import pt.cnbc.wikimodels.client.record.{SBaseRecord, SBMLModelRecord}

//Javascript handling imports
import _root_.net.liftweb.http.js.{JE,JsCmd,JsCmds}
import JsCmds._ // For implicifts
import JE.{JsRaw,Str}


class MetaId[T <: SBaseRecord[T]](own:T, maxLength: Int) extends StringField[T](own, maxLength) 
with DisplayFormWithLabelInOneLine[String, T] with DisplayHTMLWithLabelInOneLine[String, T]{
  var _data:Box[MyType] = Empty

  override def theData_=(in:Box[MyType]) {
    trace("Calling MetaId.theData_=" + in)
    _data = in
    _data match{
      //if a valid value is set then update the owner class
      case Full(x) => owner.metaid = x.asInstanceOf[String]
      case _ => owner.metaid = null //just to make sure the owner does not have valid values when errors occur
    }
  }

  override def theData:Box[MyType] = {
    trace("Calling MetaId.theData")
    //if the owner has valid data that was obtained from the wikimodels Server
    if(owner.metaid != null) {
      debug("theData with metaid = "+ owner.metaid + " is being copied to the record Field.")
      _data = Full(owner.metaid)
    } else {
      _data match {
        case Empty => {
          _data = defaultValueBox
          if (! this.optional_?) owner.metaid = defaultValue
        }
        case Full(x) => owner.metaid = x
      }
    }
    trace("MetaId.theData returns " + _data)
    _data
  }

  //the MetaId will be generated from concatenating the ids of any parent entities with '_' in between.
  override def toForm() = Empty
  //Appears when rendering the form or the visualization
  override def name: String = "Metaid"


  //override def toXHtml: NodeSeq = Text(this.value + "xxxxxxx")
}

class Id[T <: SBaseRecord[T]{var id:String}](own:T, maxLength: Int) extends StringField[T](own, maxLength)
with DisplayFormWithLabelInOneLine[String, T] with DisplayHTMLWithLabelInOneLine[String, T]{
  var _data:Box[MyType] = Empty

  override def theData_=(in:Box[MyType]) {
    trace("Calling Id.theData_=" + in)
    _data = in
    _data match{
      //if a valid value is set then update the owner class
      case Full(x) => owner.id = x.asInstanceOf[String]
      case _ => owner.id = null //just to make sure the owner does not have valid values when errors occur
    }
  }

  override def theData:Box[MyType] = {
    trace("Calling Id.theData")
    //if the owner has valid data that was obtained from the wikimodels Server
    if(owner.id != null) {
      debug("theData with aid = "+ owner.id + " is being copied to the record Field.")
      _data = Full(owner.id)
    } else {
      _data match {
        case Empty => {
          _data = defaultValueBox
          if (! this.optional_?) owner.id = defaultValue
        }
        case Full(x) => owner.id = x
      }
    }
    trace("Id.theData returns " + _data)
    _data
  }


  //Appears when rendering the form or the visualization
  override def name: String = "Id"
  //override def toXHtml: NodeSeq = Text(this.value)
}

/**
 *
 */
class Name[T <: SBaseRecord[T]{var name:String}](own:T, maxLength: Int) extends StringField[T](own, maxLength)
with DisplayFormWithLabelInOneLine[String, T] with DisplayHTMLWithLabelInOneLine[String, T]{
  var _data:Box[MyType] = Empty

  override def theData_=(in:Box[MyType]) {
    trace("Calling Name.theData_=" + in)
    _data = in
    _data match{
      //if a valid value is set then update the owner class
      case Full(x) => owner.name = x
      case _ => owner.name = null //just to make sure the owner does not have valid values when errors occur
    }
  }

  override def theData:Box[MyType] = {
    trace("Calling Name.theData")
    //if the owner has valid data that was obtained from the wikimodels Server
    if(owner.name != null) {
      debug("theData with name = "+ owner.name + " is being copied to the record Field.")
      _data = Full(owner.name)
    } else {
      _data match {
        case Empty => {
          _data = defaultValueBox
          if (! this.optional_?) owner.name = defaultValue
        }
        case Full(x) => owner.name = x
      }
    }
    trace("Name.theData returns " + _data)
    _data
  }



  //Appears when rendering the form or the visualization
  override def name = "Name"

  val msgName: String = S.attr("id_msgs") openOr "messages"
  //override def toXHtml: NodeSeq = Text(this.value)
}

class Notes[T <: SBaseRecord[T]](own:T, size:Int) extends OptionalTextareaField[T](own, size){
  override def setBox(in: Box[MyType]): Box[MyType] = {
    trace("Calling Notes.setBox")
    super.setBox(in) match {
      case full:Full[MyType] =>{
        owner.notes=full.openTheBox
        full
      }
      case notFull  => notFull
    }
  }

  override def toForm() = Full(
  <div>

  <link type="text/css" rel="stylesheet" href="/classpath/tree/jquery.treeview.css" />

  <script type="text/javascript" src="/classpath/tree/jquery.treeview.js"></script>
  {Script(
    JsRaw("""
      jQuery(document).ready(function() {jQuery('#'+"model_tree").treeview({"animated": 150});});
    """))
  }
  <ul class="treeview-gray" id="model_tree">
    <head>
      <!-- TODO: MAKE sure this doesn't get repeated more than once in a page -->
      <script type="text/javascript" src="/classpath/js/fckeditor/fckeditor.js"></script>
      {Script(
        JsRaw("""
          $(document).ready(function(){
            var oFCKeditor01 = new FCKeditor( 'descriptionArea' );
            oFCKeditor01.BasePath = '/classpath/js/fckeditor/' ;
            oFCKeditor01.ToolbarSet = 'MyTool' ;
            /*oFCKeditor01.ToolbarSets["Default"] = [
            ['Cut','Copy','Paste','PasteText','PasteWord'],['Undo','Redo','-','Find','Replace'],
            ['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
            ['OrderedList','UnorderedList','-','Outdent','Indent','Blockquote'],
            ['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],['Link','Unlink'],
            ['Image','Table','Rule','SpecialChar'],['FontSize','TextColor','BGColor'],['Source','FitWindow']
            ] ;*/
            oFCKeditor01.ReplaceTextarea();
          });
        """))
      }
    </head>

    <li><span><h3>Description of the model:</h3></span>
      <br />
      <ul>
          <li><span>
            {
              SHtml.textarea(
                valueBox.openOr(""),
                vv => setBox(
                  vv.trim match {
                    case "" => Empty
                    case x => Full(x)
                  }
                ),
                "id" -> "descriptionArea", "maxlength" -> "20000")
            }
          </span><br /></li>
      </ul>
      <!--<script src="../classpath/js/wymeditor/wymeditor/jquery.wymeditor.pack.js" type="text/javascript" />-->

    </li>
    </ul>
    </div>
  )
  //Appears when rendering the form or the visualization
  override def name: String = "Description"
  override def toXHtml: NodeSeq = {
    trace("Calling Notes.toXHtml")
    XML.loadString("<span>" + this.value.getOrElse("No description found!") + "</span>")
  }
}
}


package net.liftweb.record{


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
  trait DisplayFormWithLabelInOneLine[ThisType, OwnerType <: Record[OwnerType]] extends GetSetOnwerField[ThisType, OwnerType] {
    override abstract def toForm:Box[NodeSeq] = {
      trace("Calling DisplayFormWithLabelInOneLine.toForm")
      for (id <- uniqueFieldId; control <- super.toForm)
      yield
        <span id={id + "_holder"}>
          <label for={ id }> <span class="sbml_field_label">{ displayHtml }</span></label>
          {control}
          <lift:msg id={id}  errorClass="lift_error"/>
        </span>
    }
  }

  trait DisplayHTMLWithLabelInOneLine[ThisType, OwnerType <: Record[OwnerType]] extends GetSetOnwerField[ThisType, OwnerType] {
    override def toXHtml: NodeSeq = {
      trace("Calling DisplayHTMLWithLabelInOneLine.toXHtml")
    //TODO: BIG ERRORS HERE... JUST CHECK http://localhost:9999/model/f
        <div id={uniqueFieldId + "_holder"}>
          <span for={ uniqueFieldId.openTheBox }>
            <span class="sbml_field_label">{displayHtml}</span>
            <span class="sbml_field_content">  {this.valueBox.openTheBox}</span>
          </span>
          <lift:msg id={uniqueFieldId.openTheBox}  errorClass="lift_error"/>
        </div>
    }

  }

  /**
   * Convert the field to a String... usually of the form "displayName=value"
   */
  trait GetSetOnwerField[ThisType, OwnerType <: Record[OwnerType]] extends OwnedField[OwnerType] with TypedField[ThisType]
  with LoggerWrapper{

    private[record] def theData_=(in:Box[MyType]):Unit
    private[record] def theData:Box[MyType]

    /**
     * defines if a defalt value should be attributed to this field
     */
    needsDefault = false

    override def setBox(in: Box[MyType]): Box[MyType] = synchronized {
      trace("Calling GetSetOnwerField.setBox(" + in + ")")
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
      trace("Calling GetSetOnwerField.valueBox")
      if (needsDefault) { //FIXME - THIS CODE CAME FROM THE TypeField trait. Delete it
        needsDefault = false
        theData = defaultValueBox
      }
      trace("Data returned is {}",
        if (canRead_?) theData
        else theData.flatMap(debug("Data obscured by {}", obscure) )
      )
    }

    override def asString = displayName + "=" + theData.openTheBox
  }
}
