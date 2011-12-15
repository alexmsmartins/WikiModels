package pt.cnbc.wikimodels.client.record.visitor

import scala.collection.JavaConversions._

import pt.cnbc.wikimodels.client.record._
import pt.cnbc.wikimodels.dataModel._
import net.liftweb.common.Full

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

  def createModelFrom(mr:SBMLModelRecord) = {
    val m = new SBMLModel()
    m.metaid = mr.metaid
    m.id = mr.id
    m.name = mr.name
    m.notes = mr.notes
    m.listOfCompartments = Set.empty ++ mr.listOfCompartmentsRec.map(createCompartmentRecordFrom(_))
    //TODO - write code for the remaining lists
    mr
  }

  def createCompartmentFrom(cr: CompartmentRecord):Compartment = {
    val c = new Compartment()
    c.metaid = cr.metaid
    c.id = cr.id
    c.name = cr.name
    c.notes = cr.notes
    c.compartmentType = cr.compartmentType
    c.spatialDimensions = cr.spatialDimensions
    c.size = cr.size
    c.units = cr.units
    c.outside = cr.outside
    c.constant = cr.constant
    c
  }

  def createModelRecordFrom(m:SBMLModel):SBMLModelRecord = {
    val mr = new SBMLModelRecord()
    mr.metaid = m.metaid
    mr.id = m.id
    mr.name = m.name
    mr.notes = m.notes
    if(mr.listOfCompartments != null)
      mr.listOfCompartmentsRec = Set.empty ++ m.listOfCompartments.map(createCompartmentRecordFrom(_))
        .map(i => {
          i.parent = Full(mr) //to build complete URLs
          i
        }
      )
      //TODO - write code for the remaining lists
    mr
  }

  def createCompartmentRecordFrom(c: Compartment):CompartmentRecord = {
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