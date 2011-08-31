/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.client.model

import scala.xml._
import net.liftweb.common._
import net.liftweb.record._
import field.{OptionalTextareaField, StringField}
import pt.cnbc.wikimodels.client.record._
import net.liftweb.json.JsonAST.JValue
import pt.cnbc.wikimodels.dataModel.{Element, SBMLModel}
import thewebsemantic.vocabulary.Foaf.Person
import net.liftweb.http.SHtml

//Javascript handling imports
import _root_.net.liftweb.http.js.{JE,JsCmd,JsCmds}
import JsCmds._ // For implicits
import JE.{JsRaw,Str}


trait SBMLElement[MyType <: SBMLElement[MyType]] extends Element with RestRecord[MyType]{
  self : MyType =>

}

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 03-07-2011
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
class SBMLModelRecord extends SBMLModel with SBMLElement[SBMLModelRecord] {
  type MyType = SBMLModelRecord
  import pt.cnbc.wikimodels.snippet.User

  /**
   * informs if the fields of this record have information or not
   */
  var isFilled = false

  /**
   * Informs if this RestRecord is already saved in the WikiModels Knowledgebase.
   * This is true if an existing element is loaded from the RESTful service or after
   * a new element gets successfully saved into it.
   */
  var isSaved = false


  override def meta = SBMLModelRecord

  //  ### can be created ###
  override def createRestRec():Box[MyType] = {
    User.restfulConnection.postRequest(relativeURL, this.toXML)
    User.restfulConnection.getStatusCode match {
      case 201 => isSaved = true;Full(this)//delete went ok
      case 404 => ParamFailure("Error reading " + this.metaid + ". This element does not exist.", this)
      case status => handleStatusCodes(status, "creating model with " + this.metaid)
    }
  }

  override def readRestRec(url:String):Box[MyType] = {
    User.restfulConnection.getRequest(relativeURL)
    User.restfulConnection.getStatusCode match {
      case 200 => isFilled = true;Full(this)//delete went ok
      case 404 => ParamFailure("Error reading " + this.metaid + ". This element does not exist.", this)
      case status => handleStatusCodes(status, "reading model with " + this.metaid)
    }
  }

  override def updateRestRec():Box[MyType] = {
    User.restfulConnection.putRequest(relativeURL, this.toXML)
    User.restfulConnection.getStatusCode match {
      case 200 => isSaved = true;Full(this)//delete went ok
      case 404 => ParamFailure("Error updateing " + this.metaid + ". This element does not exist.", this)
      case status => handleStatusCodes(status, "updataing model with " + this.metaid)
    }
  }

  override def deleteRestRec():Box[MyType] = {
    User.restfulConnection.deleteRequest(relativeURL)
    User.restfulConnection.getStatusCode match {
      case 204 => isSaved = false;Full(this)//delete went ok
      case 404 => ParamFailure("Error deleting " + this.metaid + ". This element does not exist.", this)
      case status => handleStatusCodes(status, "deleting model with " + this.metaid)
    }
  }

  override protected def relativeURLasList = "model" :: metaid :: Nil


  //  ### can be validated with validate ###

  //  ### can be presented as XHtml, Json, or as a Form. ###

  override def toForm(button:Box[String])(f:MyType => Unit) = {
    meta.toForm(this) ++
    (SHtml.hidden(() => f(this))) ++
    ((button.map(b => (<input type="submit" value={b}/>)) openOr scala.xml.Text("")))
  }

    <h4><span id="required_field">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;* required fields</span></h4><br/>
    <lift:ModelTree.render/>
    <ul id="model_tree" class="treeview-gray">
      <li><span>
        <h3>Model ID: <span id="required_field">*</span>
          <div id="messages"></div>
        </h3><br/>
      </span></li>
      <li>
        <h3>Name of the model:
          <createDescription:name>
              <text:model_id/>
          </createDescription:name>
        </h3>
          <br/></li>
      <li><span><h3>Description of the model:</h3></span>
          <br/>

        <ul>
          <li><span><createDescription:description/></span><br/></li>
        </ul>
      </li>
    </ul>


  //  ### will contain fields which can be listed with allFields. ###
  object metaIdO extends MetaId(this, metaid)
  object idO extends Id(this, id)
  object nameO extends Name(this, name)
  object notesO extends Notes(this, 1000)
  //override def fieldList = metaIdO :: idO :: nameO :: notesO :: Nil
  //  ### can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ). ###
}

//TODO - DELETE IF NOT USED FOR ANYTHING
object SBMLModelRecord extends SBMLModelRecord with RestMetaRecord[SBMLModelRecord] {
  def apply() = new SBMLModelRecord
}

class MetaId(own:SBMLModelRecord, pMetaId:String) extends StringField[SBMLModelRecord](own,pMetaId) {
  override type ValueType = String

  override def get: ValueType = owner.metaid

  override def set(in: ValueType): ValueType = {
    owner.metaid  = in;in
  }

  //the MetaId will be generated autimatically from concatenating the ids of any parent entities with '_' in between.
  override def toForm() = Full(Text("The MetaId will be generated automatically.") ++ <br/>)
}

class Id(own:SBMLModelRecord, pId:String) extends StringField[SBMLModelRecord](own, pId) {
  override type ValueType = String

  override def get: ValueType = owner.id

  override def set(in: ValueType): ValueType = {
    owner.id = in;in
  }

  //Appers when rendering the form or the vizualization
  override def displayName: String = "Model id"

  override def toForm = Full(Text(displayName) ++ super.toForm.openTheBox)
}

class Name(own:SBMLModelRecord, pName:String) extends StringField[SBMLModelRecord](own, pName){
  override type ValueType = String

  override def get:ValueType = owner.name

  override def set(in: ValueType): ValueType = {
    owner.name = in; in
  }

  //Appers when rendering the form or the vizualization
  override def displayName = "Model name"

  override def toForm = Full(Text(displayName) ++ super.toForm.openTheBox)
}

class Notes(own:SBMLModelRecord, size:Int) extends OptionalTextareaField[SBMLModelRecord](own, size){
  override def get: ValueType =
    if ((owner.notes != null) ||
      (owner.notes.trim() != "")) Some(owner.notes)
    else None

  override def set(in: ValueType): ValueType = {
    owner.notes = in.toString; in
  }

  override protected def valueTypeToBoxString(in: ValueType): Box[String] = in
  override protected def boxStrToValType(in: Box[String]): ValueType = {
    in.toOption
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
                  ['Cut','Copy','Paste','PasteText','PasteWord'],['Undo','Redo','-','Find','Replace'],
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
