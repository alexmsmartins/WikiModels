/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.client.record

import pt.cnbc.wikimodels.dataModel.Compartment
import net.liftweb.common._
import net.liftweb.http.{SHtml, S}
import net.liftweb.record._
import xml.NodeSeq
import alexmsmartins.log.LoggerWrapper

/**
 * TODO: Please document.
 * @author Alexandre Martins
 * Date: 29-11-2011
 * Time: 22:35
 */
class CompartmentRecord() extends Compartment with SBaseRecord[CompartmentRecord] with LoggerWrapper {

  override def meta = CompartmentRecord

  override protected def relativeURLasList = "model" :: S.param("modelMetaId").openTheBox :: "compartment" :: metaid :: Nil
  override protected def relativeCreationURLasList = "model" :: S.param("modelMetaId").openTheBox :: "compartment" :: Nil


  //  ### can be validated with validate ###

  //  ### can be presented as XHtml, Json, or as a Form. ###

  override def toXHtml = {
    trace("Calling CompartmentRecord.toXHtml")
    <div>
      <head>
        <link type="text/css" rel="stylesheet" href="/css/sbml_present.css"></link>
      </head>
      {super.toXHtml}

    </div>
  }
  
  override def toForm(f:CompartmentRecord => Unit):NodeSeq = {
    trace("Calling CompartmentRecord.toForm( "+f+" )")
    <div class="compartment_toform">
      {super.toForm(f)}
      <!-- outside can't be a field and so I will make it a form -->
      {
        val defaultOption:(Box[CompartmentRecord], String) = (Empty, "[no compartment")
        val op = parent.openTheBox.
          listOfCompartmentsRec.
            filter(_.metaid != this.metaid).
            filter(_.spatialDimensions != 0)
        val defaultOp = parent.openTheBox.listOfCompartmentsRec.filter( _.id == outside ).headOption
        val opWithId = op.map(i => (i, i.id):(CompartmentRecord, String) )
        val options = (List((null , "no compartment")) ::: opWithId.toList).toSeq
        SHtml.selectObj(options, Box.option2Box(defaultOp),
          (choice:CompartmentRecord ) => {
            choice match {
              case null => outside = null
              case c => outside = c.id
            }
          })
      }
    </div>
  }
  
  //  ### will contain fields which can be listed with allFields. ###
  object metaIdO extends MetaId(this, 100)
  object idO extends Id(this, 100)
  object nameO extends Name(this, 100)
  object spatialDimensions0 extends SpatialDimensions(this)
  object constantO extends CConstant(this)
  object sizeO extends Size(this)
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
object CompartmentRecord extends CompartmentRecord with RestMetaRecord[CompartmentRecord] {
  def apply() = new CompartmentRecord
  override def fieldOrder = List(metaIdO, idO, nameO, spatialDimensions0, constantO, sizeO, notesO)
  override def fields = fieldOrder
}
