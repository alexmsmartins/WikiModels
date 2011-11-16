package pt.cnbc.wikimodels.client.record.visitor

import pt.cnbc.wikimodels.client.record.SBMLModelRecord
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
    //mr.listOfCompartments = m.listOfCompartments.map(createSBMLCompartmentRecordFrom(_))
    //TODO - write code for the remaining lists
    mr
  }

  //def createSBMLCompartmentRecordFrom(c: Compartment):SBMLCompartmentRecord =

}