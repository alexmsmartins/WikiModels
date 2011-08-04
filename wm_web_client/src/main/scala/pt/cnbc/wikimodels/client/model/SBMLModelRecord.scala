package pt.cnbc.wikimodels.client.model

import net.liftweb.record._
import field.StringField
import xml.NodeSeq
import java.net.URI
import pt.cnbc.wikimodels.client.record.{RestRecord, RestMetaRecord}
import net.liftweb.http.js.JsExp
import pt.cnbc.wikimodels.client.record.{RestMetaRecord, RestRecord}
import net.liftweb.json.JsonAST.JValue
import net.liftweb.util.ElemSelector
import net.liftweb.common.{Empty, Full, Box}
import java.security.acl.Owner
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
  override def meta = SBMLModelRecord

  object metaIdO extends MetaId(this,metaid)
  object idO extends Id(this,id)
  //object name extends StringField
  //object description extends StringField(this, "No description")

  //  can be created
  override def createRestRec():MyType = {
    pt.cnbc.wikimodels.snippet.User.restfulConnection.postRequest("/model/"+ metaIdO.valueBox.openTheBox, <model>this</model>)
    this
  }

  override def readRestRec(url:String):MyType = {
    pt.cnbc.wikimodels.snippet.User.restfulConnection.getRequest("/model/"+ metaIdO.valueBox.openTheBox)
    this
  }

  override def updateRestRec():MyType = {
    pt.cnbc.wikimodels.snippet.User.restfulConnection.putRequest("/model/"+ metaIdO.valueBox.openTheBox, <model>this</model>)
    this
  }

  override def deleteRestRec():MyType = {
    pt.cnbc.wikimodels.snippet.User.restfulConnection.deleteRequest("/model/"+ metaIdO.valueBox.openTheBox)
    this
  }

  //  can be validated with validate
  //  can be presented as XHtml, Json, or as a Form.
  //  will contain fields which can be listed with allFields.
  //  can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ).
}

//TODO - DELETE IF NOT USED FOR ANYTHING
object SBMLModelRecord extends SBMLModelRecord with RestMetaRecord[SBMLModelRecord] {
  def apply() = new SBMLModelRecord
}


class MetaId(own:SBMLModelRecord, pMetaId:String) extends StringField[SBMLModelRecord](own,pMetaId) {
  override type ValueType = String

  override def is:MetaId#ValueType = owner.metaid

  override def get: MetaId#ValueType = owner.metaid

  override def set(in: MetaId#ValueType): MetaId#ValueType = {
    owner.metaid  = in;in
  }
}

class Id(own:SBMLModelRecord, pId:String) extends StringField[SBMLModelRecord](own, pId) {
  override type ValueType = String

  override def is:MetaId#ValueType = owner.id

  override def get: MetaId#ValueType = owner.id

  override def set(in: MetaId#ValueType): MetaId#ValueType = {
    owner.id = in;in
  }
}

