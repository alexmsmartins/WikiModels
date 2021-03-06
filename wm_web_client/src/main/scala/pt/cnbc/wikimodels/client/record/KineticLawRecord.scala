/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.client.record

import pt.cnbc.wikimodels.dataModel._
import net.liftweb.http.S
import net.liftweb.record._
import net.liftweb.common.{Empty, Box}

/** TODO: Please document.
 *  @author Alexandre Martins
 *  Date: 16/01/12
 *  Time: 00:41 */
case class KineticLawRecord() extends SBaseRecord[KineticLawRecord] {
  override val sbmlType = "KineticLaw"

  var listOfParametersRec:List[ParameterRecord] = Nil

  override def meta = KineticLawRecord

  override protected def relativeURLasList = "model" :: S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: "reaction" :: S.param("reactionMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: "kineticlaw" :: this.metaIdO.get :: Nil
  override protected def relativeCreationURLasList = "model" :: S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: "reaction" :: S.param("reactionMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: "kineticlaw" :: Nil


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
  object notesO extends Notes(this, 1000)
  object mathO extends Math(this)
  //  ### can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ). ###

  var _parent:Box[ReactionRecord] = Empty
  //TODO isn't there a better way to override a var than THIS?!??! Fucking asInstanceOf
  override def parent:Box[ReactionRecord] = _parent.asInstanceOf[Box[ReactionRecord]]
  override def parent_=(p:Box[SBaseRecord[_]] ):Unit = {
    _parent = p.asInstanceOf[Box[ReactionRecord]]
  }
}

//TODO - DELETE IF NOT USED FOR ANYTHING
object KineticLawRecord extends KineticLawRecord with RestMetaRecord[KineticLawRecord] {
  override def fieldOrder = List(metaIdO, notesO)
  override def fields = fieldOrder
}
