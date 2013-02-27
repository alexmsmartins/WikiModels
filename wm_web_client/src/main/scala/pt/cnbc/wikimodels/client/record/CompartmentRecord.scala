/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.client.record

import pt.cnbc.wikimodels.dataModel._
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
case class CompartmentRecord() extends SBaseRecord[CompartmentRecord] with LoggerWrapper {

  override val sbmlType = "Compartment"

  override def meta = CompartmentRecord

  override protected def relativeURLasList = "model" :: S.param("modelMetaId").openTheBox :: "compartment" :: this.metaIdO.get :: Nil
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
    </div>
  }
  
  //  ### will contain fields which can be listed with allFields. ###
  object idO extends Id(this, 100)
  object nameO extends Name(this, 100)
  object spatialDimensions0 extends SpatialDimensions(this)
  object constantO extends CConstant(this)
  object sizeO extends Size(this)
  object notesO extends Notes(this, 1000)
  object outsideO extends COutside(this)

  //  ### can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ). ###

  var _parent:Box[SBaseRecord[_]] = Empty
  //TODO isn't there a better way to override a var than THIS?!??! Fucking asInstanceOf
  override def parent:Box[SBaseRecord[_]] = _parent
  override def parent_=(p:Box[SBaseRecord[_]] ) = {
    _parent = p
  }
}



//TODO - DELETE IF NOT USED FOR ANYTHING
object CompartmentRecord extends CompartmentRecord with RestMetaRecord[CompartmentRecord] {
  override def fieldOrder = List(metaIdO, idO, nameO, spatialDimensions0, constantO, sizeO, outsideO, notesO)
  override def fields = fieldOrder
}
