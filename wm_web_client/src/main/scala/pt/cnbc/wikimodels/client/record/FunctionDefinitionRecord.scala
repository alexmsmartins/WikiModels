/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.client.record

import pt.cnbc.wikimodels.dataModel.FunctionDefinition
import net.liftweb.http.S
import net.liftweb.record.{Notes, Name, Id, MetaId}
import net.liftweb.common.{Empty, Box}

/** TODO: Please document.
 *  @author: alex
 *  Date: 29-12-2011
 *  Time: 17:11 */
class FunctionDefinitionRecord() extends FunctionDefinition with SBaseRecord[FunctionDefinitionRecord]  {

  override def meta = FunctionDefinitionRecord

  override protected def relativeURLasList = "model" :: S.param("modelMetaId").openTheBox :: "functiondefinition" :: metaid :: Nil
  override protected def relativeCreationURLasList = "model" :: S.param("modelMetaId").openTheBox :: "functiondefinition" :: Nil


  //  ### can be validated with validate ###

  //  ### can be presented as XHtml, Json, or as a Form. ###

  override def toXHtml = {
    <div>
      <head>
        <link type="text/css" rel="stylesheet" href="/css/sbml_present.css"></link>
      </head>
      {super.toXHtml}
    </div>
  }

  //  ### will contain fields which can be listed with allFields. ###
  object metaIdO extends MetaId(this, 100)
  object idO extends Id(this, 100)
  object nameO extends Name(this, 100)
  object notesO extends Notes(this, 1000)
  //  ### can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ). ###

  var _parent:Box[SBMLModelRecord] = Empty
  //TODO isn't there a better way to override a var than THIS?!??! Fucking asInstanceOf
  override def parent:Box[SBMLModelRecord] = _parent.asInstanceOf[Box[SBMLModelRecord]]
  override def parent_=(p:Box[SBaseRecord[_]] ):Unit = {
    _parent = p.asInstanceOf[Box[SBMLModelRecord]]
  }

}



//TODO - DELETE IF NOT USED FOR ANYTHING
object FunctionDefinitionRecord extends FunctionDefinitionRecord with RestMetaRecord[FunctionDefinitionRecord] {
  def apply() = new FunctionDefinitionRecord
  override def fieldOrder = List(metaIdO, idO, nameO, /*sizeO,*/ notesO)
  override def fields = fieldOrder
}
