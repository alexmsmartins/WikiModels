package pt.cnbc.wikimodels.client.model

import scala.xml._
import net.liftweb.common._
import net.liftweb.record._
import field.StringField
import pt.cnbc.wikimodels.client.record._
import pt.cnbc.wikimodels.dataModel.SBMLModel

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
  override def createRestRec():MyType = {
    User.restfulConnection.postRequest(relativeURL, this.toXML)
    this
  }

  override def readRestRec(url:String):MyType = {
    User.restfulConnection.getRequest(relativeURL)
    this
  }

  override def updateRestRec():MyType = {
    User.restfulConnection.putRequest(relativeURL, this.toXML)
    this
  }

  override def deleteRestRec():MyType = {
    User.restfulConnection.deleteRequest(relativeURL)
    this
  }

  override protected def relativeURLasList = "model" :: metaid :: Nil


  //  ### can be validated with validate ###

  //  ### can be presented as XHtml, Json, or as a Form. ###

  //  ### will contain fields which can be listed with allFields. ###
  object metaIdO extends MetaId(this, metaid)
  object idO extends Id(this, id)
  object nameO extends Name(this, name)
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

  override def toForm() = Full(Text("the MetaId will be generated autimatically from concatenating the ids of any parent entities with '_' in between."))
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

