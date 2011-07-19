package pt.cnbc.wikimodels.client.model

import net.liftweb.record._
import xml.NodeSeq
import net.liftweb.http.StringField
import java.net.URI






/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 03-07-2011
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
/*class SBMLModelRecord extends RestRecord[SBMLModelRecord]{
  def meta:SBMLModelMetaRecord[SBMLModelRecord]

  object metaId extends KeyField[StringField, SBMLModelRecord]
  object metaId2 extends StringField(default = "defaultMetaId")
  object id extends StringField(this, "defaultId")
  object name extends StringField(this, "defaultName")
  object description extends StringField(this, "")



  //  can be created
  def create( ) = {
    pt.cnbc.wikimodels.snippet.User.restfulConnection.postRequest("/model/"+ modelId, <model></model>)
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


object SBMLModelRecord extends SBMLModelRecord with MetaRecord[SBMLModelRecord]{
  protected val rootClass: Class[Any] = _
}

class SBMLModelMetaRecord[SBMLModelRecord] extends MetaRecord[SBMLModelRecord]{

}*/



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
