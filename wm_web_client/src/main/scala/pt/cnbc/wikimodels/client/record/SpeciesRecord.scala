/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.client.record

import pt.cnbc.wikimodels.dataModel.Species
import xml.NodeSeq
import net.liftweb.common.Full._
import net.liftweb.common.{Full, Empty, Box}
import net.liftweb.http.{SHtml, S}
import net.liftweb.record._

/** TODO: Please document.
 *  @author Alexandre Martins
 *  Date: 29-12-2011
 *  Time: 16:49
 *  To change this template use File | Settings | File Templates. */
class SpeciesRecord() extends Species with SBaseRecord[SpeciesRecord]  {

  override def meta = SpeciesRecord

  override protected def relativeURLasList = "model" :: S.param("modelMetaId").openTheBox :: "species" :: metaid :: Nil
  override protected def relativeCreationURLasList = "model" :: S.param("modelMetaId").openTheBox :: "species" :: Nil


  //  ### can be validated with validate ###

  //  ### can be presented as XHtml, Json, or as a Form. ###

  override def toXHtml = {
    trace("Calling SpeciesRecord.toXHtml")
    <div>
      <head>
        <link type="text/css" rel="stylesheet" href="/css/sbml_present.css"></link>
      </head>
      {super.toXHtml}

    </div>
  }

  override def toForm(f:SpeciesRecord => Unit):NodeSeq = {
    trace("Calling SpeciesRecord.toForm( "+f+" )")
    <div class="species_toform">
      {super.toForm(f)}
      <!-- outside can't be a field and so I will make it a form -->
      {
      val defaultOption:(Box[CompartmentRecord], String) = (Empty, "[no compartment")
      val op = parent.openTheBox.
        listOfCompartmentsRec
      val defaultOp = parent.openTheBox.listOfCompartmentsRec.filter( _.id == compartment ).headOption
      val opWithId = op.map(i => (i, i.id):(CompartmentRecord, String) )
      val options = (List((null , "no compartment")) ::: opWithId.toList).toSeq
      SHtml.selectObj(options, Box.option2Box(defaultOp),
        (choice:CompartmentRecord ) => {
          choice match {
            case null => compartment = null
            case c => compartment = c.id
          }
        })
      }
    </div>
  }

  //  ### will contain fields which can be listed with allFields. ###
  object metaIdO extends MetaId(this, 100)
  object idO extends Id(this, 100)
  object nameO extends Name(this, 100)
  object constantO extends SConstant(this)
  object notesO extends Notes(this, 1000)
  object initialAmount0 extends InitialAmount(this)
  object initialConcentration0 extends InitialConcentration(this)
  object boundaryCondition0 extends BoundaryCondition(this)
  //  ### can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ). ###

  var _parent:Box[SBMLModelRecord] = Empty
  //TODO isn't there a better way to override a var than THIS?!??! Fucking asInstanceOf
  override def parent:Box[SBMLModelRecord] = _parent.asInstanceOf[Box[SBMLModelRecord]]
  override def parent_=(p:Box[SBaseRecord[_]] ):Unit = {
    _parent = p.asInstanceOf[Box[SBMLModelRecord]]
  }

}



//TODO - DELETE IF NOT USED FOR ANYTHING
object SpeciesRecord extends SpeciesRecord with RestMetaRecord[SpeciesRecord] {
  def apply() = new SpeciesRecord
  override def fieldOrder = List(metaIdO, idO, nameO, initialAmount0, initialConcentration0, boundaryCondition0, constantO, notesO)
  override def fields = fieldOrder
}
