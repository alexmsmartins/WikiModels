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
  def meta:SBMLModelMetaRecord = new SBMLModelMetaRecord()

  val metaIdd:String = "THIS IS WRONG!!!"
  val idd:String = "THIS IS ALSO WRONG!!!"

  object metaId extends MetaId(metaIdd, this)
  object id extends Id(this)
  //object name extends StringField
  //object description extends StringField(this, "No description")

  //  can be created
  def create( ) = {
    pt.cnbc.wikimodels.snippet.User.restfulConnection.postRequest("/model/"+ metaId.valueBox.openTheBox, <model></model>)
  }
  //  can be validated with validate
  //  can be presented as XHtml, Json, or as a Form.


  def toXHTML():NodeSeq = null
  def toForm():NodeSeq = <form method="post" action="/models/createEdit">
    <div id="tab-1">
          <h4><span id="required_field">     * required fields</span></h4><br />
          <ul class="treeview-gray" id="model_tree">
              <li><span><h3>Model ID: <span id="required_field">*</span><div id="messages"></div></h3><br /></span></li>
              <li><h3>Name of the model:
                      <input onkeypress="liftUtils.lift_blurIfReturn(event)" size="40" type="text" value="" id="name_model" maxlength="1000" onblur="liftAjax.lift_ajaxHandler('F425848098740IJ2PSK=' + encodeURIComponent(this.value), null, null, null)" /></h3><br /></li>
              <li><span><h3>Description of the model:</h3></span>
                  <br />
                  <ul>
                      <li><span><textarea id="descriptionArea" name="F425848098731ZADFZH" maxlength="20000"></textarea></span><br /></li>
                  </ul>
              </li>
          </ul>
      </div>
    </form>

//  will contain fields which can be listed with allFields.

//  can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ).
}

//TODO - DELETE IF NOT USED FOR ANYTHING
object SBMLModelRecord extends SBMLModelRecord with RestMetaRecord[SBMLModelRecord]

class SBMLModelMetaRecord extends RestMetaRecord[SBMLModelMetaRecord]


class MetaId(pMetaId:String ,own:SBMLModelRecord) extends KeyField[String, SBMLModelRecord] with RestRecord[MetaId] {
  type ValueType = String

  //TODO DELETE THIS AND PUT EVERY object field in  IN owner
  var _metaId:ValueType = owner.metaIdd

  def get: MetaId#ValueType = _metaId

  def setFromJValue(jvalue: JValue): Box[MetaId#ValueType] = throw new UnsupportedOperationException

  def defaultValueBox: Box[MetaId#MyType] = throw new UnsupportedOperationException

  protected def toValueType(in: Box[MetaId#MyType]): MetaId#ValueType = throw new UnsupportedOperationException

  protected def toBoxMyType(in: MetaId#ValueType): Box[MetaId#MyType] = throw new UnsupportedOperationException

  protected def liftSetFilterToBox(in: Box[MetaId#MyType]): Box[MetaId#MyType] = throw new UnsupportedOperationException

  def setFromAny(in: Any): Box[MetaId#MyType] = throw new UnsupportedOperationException

  def setFromString(s: String): Box[MetaId#MyType] = {
    if(s == null){
      Empty
    } else {
      Full(s)
    }
  }

  def meta: RestMetaRecord[MetaId] = throw new UnsupportedOperationException

  def toForm: Box[NodeSeq] = null

  def asJs: JsExp = throw new UnsupportedOperationException

  def set(in: MetaId#ValueType): MetaId#ValueType = {
    _metaId = in;_metaId
  }

  def owner: SBMLModelRecord = own
}

class Id(rec: SBMLModelRecord) extends StringField[SBMLModelRecord](rec,-1) with RestRecord[Id] {
  type ValueType = String

  var _id:ValueType = SBMLModelRecord.idd

  def get: MetaId#ValueType = _id

  def setFromJValue(jvalue: JValue): Box[Id#ValueType] = throw new UnsupportedOperationException

  def defaultValueBox: Box[MetaId#MyType] = throw new UnsupportedOperationException

  protected def toValueType(in: Box[MetaId#MyType]): MetaId#ValueType = throw new UnsupportedOperationException

  protected def toBoxMyType(in: MetaId#ValueType): Box[MetaId#MyType] = throw new UnsupportedOperationException

  protected def liftSetFilterToBox(in: Box[MetaId#MyType]): Box[MetaId#MyType] = throw new UnsupportedOperationException

  def setFromAny(in: Any): Box[MetaId#MyType] = throw new UnsupportedOperationException

  def setFromString(s: String): Box[MetaId#MyType] = {
    if(s == null){
      Empty
    } else {
      Full(s)
    }
  }

  def meta: RestMetaRecord[MetaId] = throw new UnsupportedOperationException

  //def owner: OwnerType = null

  def toForm: Box[NodeSeq] = null

  def asJs: JsExp = throw new UnsupportedOperationException

  def set(in: MetaId#ValueType): MetaId#ValueType = {
    _id = in;_id
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
