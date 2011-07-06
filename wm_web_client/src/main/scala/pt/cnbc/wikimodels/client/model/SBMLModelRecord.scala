package pt.cnbc.wikimodels.client.model

import net.liftweb.record._
import xml.NodeSeq
import net.liftweb.http.StringField

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 03-07-2011
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
/*class SBMLModelRecord extends KeyedRecord[SBMLModelRecord]{
  def meta = SBMLModelRecord

  object metaId extends StringField(this, "defaultMetaId")
  object id extends StringField(this, "defaultId")
  object name extends StringField(this, "defaultName")
  object description extends StringField(this, "")


  def primaryKey: KeyField[StringField, SBMLModelRecord] = metaId

//  can be created
  def create( ) = {
    pt.cnbc.wikimodels.snippet.User.restfulConnection.postRequest("/model/"+ modelId, <model></model>)
  }
//  can be validated with validate
  def validate = {
    validate
  }
//  can be presented as XHtml, Json, or as a Form.


  def toXHTML():NodeSeq
  def toForm():NodeSeq
//  will contain fields which can be listed with allFields.

//  can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ).
}


object SBMLModelRecord extends SBMLModelRecord with MetaRecord[SBMLModelRecord]{
  protected val rootClass: Class[Any] = _
}



sealed trait ManipModelState{ stateName:String){
  case object EnterPage extends ManipModelState("EnterPage")
  case object Create extends ManipModelState("Create")
  case object CreateWithErrors extends ManipModelState("CreateWithErrors")
  case object Edit extends ManipModelState("Edit")
  case object EditWithErrors extends ManipModelState("EditWithErros")
  case object Visualize extends ManipModelState("Visualize")
  case object GoToBrowseModelsPage extends ManipModelState("GoToBrowseModelsPage")
  case class AnotherState(_state:String) extends ManipModelState(_state)
}*/