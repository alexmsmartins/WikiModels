package pt.cnbc.wikimodels.client.model

import scala.xml._
import net.liftweb.common._
import net.liftweb.record._
import field.{OptionalTextareaField, StringField}
import pt.cnbc.wikimodels.client.record._
import pt.cnbc.wikimodels.dataModel.SBMLModel
import net.liftweb.json.JsonAST.JValue

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 03-07-2011
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
class SBMLModelRecord extends SBMLModel with RestRecord[SBMLModelRecord] {
  type MyType = SBMLModelRecord
  import pt.cnbc.wikimodels.snippet.User

  override def meta = SBMLModelRecord

  //  ### can be created ###
  override def createRestRec():Box[MyType] = {
    User.restfulConnection.postRequest(relativeURL, this.toXML)
    User.restfulConnection.getStatusCode match {
      case 201 => Full(this)//delete went ok
      case 404 => ParamFailure("Error reading " + this.metaid + ". This element does not exist.", this)
      case status => handleStatusCodes(status, "creating model with " + this.metaid)
    }
  }

  override def readRestRec(url:String):Box[MyType] = {
    User.restfulConnection.getRequest(relativeURL)
    User.restfulConnection.getStatusCode match {
      case 200 => Full(this)//delete went ok
      case 404 => ParamFailure("Error reading " + this.metaid + ". This element does not exist.", this)
      case status => handleStatusCodes(status, "reading model with " + this.metaid)
    }
  }

  override def updateRestRec():Box[MyType] = {
    User.restfulConnection.putRequest(relativeURL, this.toXML)
    User.restfulConnection.getStatusCode match {
      case 200 => Full(this)//delete went ok
      case 404 => ParamFailure("Error updateing " + this.metaid + ". This element does not exist.", this)
      case status => handleStatusCodes(status, "updataing model with " + this.metaid)
    }
  }

  override def deleteRestRec():Box[MyType] = {
    User.restfulConnection.deleteRequest(relativeURL)
    User.restfulConnection.getStatusCode match {
      case 204 => Full(this)//delete went ok
      case 404 => ParamFailure("Error deleting " + this.metaid + ". This element does not exist.", this)
      case status => handleStatusCodes(status, "deleting model with " + this.metaid)
    }
  }

  override protected def relativeURLasList = "model" :: metaid :: Nil


  //  ### can be validated with validate ###

  //  ### can be presented as XHtml, Json, or as a Form. ###

  //  ### will contain fields which can be listed with allFields. ###
  object metaIdO extends MetaId(this, metaid)
  object idO extends Id(this, id)
  object nameO extends Name(this, name)
  object notesO extends Notes(this, 1000)
  //object description extends StringField(this, "No description")

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
  override def toForm() = Full(Text("the MetaId will be generated automatically."))
}

class Id(own:SBMLModelRecord, pId:String) extends StringField[SBMLModelRecord](own, pId) {
  override type ValueType = String

  override def get: ValueType = owner.id

  override def set(in: ValueType): ValueType = {
    owner.id = in;in
  }
}

class Name(own:SBMLModelRecord, pName:String) extends StringField[SBMLModelRecord](own, pName){
  override type ValueType = String

  override def get:ValueType = owner.name

  override def set(in: ValueType): ValueType = {
    owner.name = in; in
  }
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
}
