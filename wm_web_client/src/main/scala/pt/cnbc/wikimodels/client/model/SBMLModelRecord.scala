package pt.cnbc.wikimodels.client.model

import net.liftweb.record._
import field.StringField
import xml.NodeSeq
import java.net.URI
import pt.cnbc.wikimodels.client.record.{RestRecord, RestMetaRecord}
import net.liftweb.http.js.JsExp
import pt.cnbc.wikimodels.client.record.{RestMetaRecord, RestRecord}
import net.liftweb.json.JsonAST.JValue
import org.h2.value.ValueTime
import net.liftweb.util.ElemSelector
import net.liftweb.common.{Empty, Full, Box}
import java.security.acl.Owner

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 03-07-2011
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
class SBMLModelRecord extends RestRecord[SBMLModelRecord] {
  type MyType = SBMLModelRecord
  override def meta = SBMLModelRecord

  var metaIdd:String = "THIS IS WRONG!!!"
  var idd:String = "THIS IS ALSO WRONG!!!"

  object metaId extends MetaId(this,metaIdd)
  object id extends Id(this,idd)
  //object name extends StringField
  //object description extends StringField(this, "No description")

  //  can be created
  override def create():MyType = {
    pt.cnbc.wikimodels.snippet.User.restfulConnection.postRequest("/model/"+ metaId.valueBox.openTheBox, <model>this</model>)
    this
  }

  override def read(url:String):MyType = {
    pt.cnbc.wikimodels.snippet.User.restfulConnection.getRequest("/model/"+ metaId.valueBox.openTheBox)
    this
  }

  override def update():MyType = {
    pt.cnbc.wikimodels.snippet.User.restfulConnection.putRequest("/model/"+ metaId.valueBox.openTheBox, <model>this</model>)
    this
  }

  override def delete = {
    pt.cnbc.wikimodels.snippet.User.restfulConnection.deleteRequest("/model/"+ metaId.valueBox.openTheBox)
    this
  }

  //  can be validated with validate
  //  can be presented as XHtml, Json, or as a Form.
  //  will contain fields which can be listed with allFields.
  //  can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ).
}

//TODO - DELETE IF NOT USED FOR ANYTHING
object SBMLModelRecord extends SBMLModelRecord with RestMetaRecord[SBMLModelRecord]


class MetaId(own:SBMLModelRecord, pMetaId:String) extends StringField[SBMLModelRecord](own,pMetaId) {
  override type ValueType = String

  override def is:MetaId#ValueType = owner.metaIdd

  override def get: MetaId#ValueType = owner.metaIdd

  override def set(in: MetaId#ValueType): MetaId#ValueType = {
    owner.metaIdd  = in;in
  }
}

class Id(own:SBMLModelRecord, pId:String) extends StringField[SBMLModelRecord](own, pId) {
  override type ValueType = String

  override def is:MetaId#ValueType = owner.idd

  override def get: MetaId#ValueType = owner.idd

  override def set(in: MetaId#ValueType): MetaId#ValueType = {
    owner.idd = in;in
  }
}


sealed class ManipModelState( stateName:String){
  case object EnterPage extends ManipModelState("EnterPage")
  case object Create extends ManipModelState("Create")
  case object CreateWithErrors extends ManipModelState("CreateWithErrors")
  case object Edit extends ManipModelState("Edit")
  case object EditWithErrors extends ManipModelState("EditWithErros")
  case object Visualize extends ManipModelState("Visualize")
  case object GoToBrowseModelsPage extends ManipModelState("GoToBrowseModelsPage")
  case class AnotherState(_state:String) extends ManipModelState(_state)
}
