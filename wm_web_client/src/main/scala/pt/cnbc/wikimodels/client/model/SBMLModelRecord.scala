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
  def create( ) = {
    pt.cnbc.wikimodels.snippet.User.restfulConnection.postRequest("/model/"+ metaId.valueBox.openTheBox, <model></model>)
  }
  //  can be validated with validate
  //  can be presented as XHtml, Json, or as a Form.


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
