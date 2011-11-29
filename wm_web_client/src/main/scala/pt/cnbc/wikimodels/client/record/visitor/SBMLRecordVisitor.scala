package pt.cnbc.wikimodels.client.record.visitor

import scala.collection.JavaConversions._

import pt.cnbc.wikimodels.client.record._
import pt.cnbc.wikimodels.dataModel._

/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 15-11-2011
 * Time: 22:51
 * To change this template use File | Settings | File Templates.
 */

object SBMLRecordVisitor {


  def createSBMLModelRecordFrom(m:SBMLModel):SBMLModelRecord = {
    val mr = new SBMLModelRecord()
    mr.metaid = m.metaid
    mr.id = m.id
    mr.name = m.name
    mr.notes = m.notes
    if(mr.listOfCompartments != null)
      mr.listOfCompartments = m.listOfCompartments.map(createSBMLCompartmentRecordFrom(_))
      //TODO - write code for the remaining lists
    mr
  }

  def createSBMLCompartmentRecordFrom(c: Compartment):CompartmentRecord = {
    val cr = new CompartmentRecord()
    cr.metaid = c.metaid
    cr.id = c.id
    cr.name = c.name
    cr.notes = c.notes
    cr.compartmentType = c.compartmentType
    cr.spatialDimensions = c.spatialDimensions
    cr.size = c.size
    cr.units = c.units
    cr.outside = c.outside
    cr.constant = c.constant
    cr
  }

}