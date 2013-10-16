/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.client.record

import pt.cnbc.wikimodels.dataModel.SpeciesReference
import xml.NodeSeq
import net.liftweb.common.Full._
import net.liftweb.http.{SHtml, S}
import net.liftweb.record._
import net.liftweb.common.{Full, Empty, Box}
import pt.cnbc.wikimodels.exceptions.NotImplementedException

/** TODO: Please document.
 *  @author Alexandre Martins
 *  Date: 29-12-2011
 *  Time: 16:49
 *  To change this template use File | Settings | File Templates. */
case class SpeciesReferenceRecord() extends SBaseRecord[SpeciesReferenceRecord]  {
  override val sbmlType = "SpeciesReference"

  var referenceType:String = "reactant" // "product" "modifier"
  override def meta = SpeciesReferenceRecord

  override protected def relativeURLasList = "model" :: S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: "reaction" :: S.param("reactionMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: referenceType :: this.metaIdO.get :: Nil
  override protected def relativeCreationURLasList = "model" :: S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") :: "species" :: Nil


  //  ### can be validated with validate ###

  //  ### can be presented as XHtml, Json, or as a Form. ###

  override def toXHtml = {
    trace("Calling SpeciesReferenceRecord.toXHtml")
    <div>
      <head>
        <link type="text/css" rel="stylesheet" href="/css/sbml_present.css"></link>
      </head>
      {super.toXHtml}

    </div>
  }

  override def toForm(f:SpeciesReferenceRecord => Unit):NodeSeq = {
    trace("Calling SpeciesReferenceRecord.toForm( "+f+" )")
    <div class="speciesreference_toform">
      {super.toForm(f)}
      <!-- outside can't be a field and so I will make it a form -->
      {
      val defaultOption:(Box[SpeciesRecord], String) = (Empty, "[no spcies")
      val op = parent.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").
        listOfSpeciesRec
      val defaultOp = parent.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").listOfSpeciesRec.filter( _.idO.is == species ).headOption
      val opWithId = op.map(i => (i, i.idO.is):(SpeciesRecord, String) )
      val options = (List((null , "no species")) ::: opWithId.toList).toSeq
      SHtml.selectObj(options, Box.option2Box(defaultOp),
        (choice:SpeciesRecord ) => {
          choice match {
            case null => species = null
            case c => species = c.idO.is
          }
        })
      }
    </div>
  }

  var species:String = throw new NotImplementedException(""" 'species' should be deleted from code!""")

  //  ### will contain fields which can be listed with allFields. ###
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
object SpeciesReferenceRecord extends SpeciesReferenceRecord with RestMetaRecord[SpeciesReferenceRecord] {
  override def fieldOrder = List(metaIdO, idO, nameO, notesO)
  override def fields = fieldOrder
}
