/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.client.snippet

import net.liftweb.common._

import net.liftweb.http._
import S._

import net.liftweb.util._
import Helpers._

import scala.xml._
import pt.cnbc.wikimodels.client.record._
import alexmsmartins.log.LoggerWrapper
import pt.cnbc.wikimodels.sbmlVisitors.SBMLStrictValidator


package object screenUtil extends LoggerWrapper{

  def loadSBMLModelFromPathParam:SBMLModelRecord = {
    var mm:Box[SBMLModelRecord] = Empty
    tryo(
      SBMLModelRecord.readRestRec(debug("The modelMetaId in session after calling /model/modemetaid is: {}", S.param("modelMetaId").openTheBox))
    ) match {
      case Full(m) => {mm = m}
      case Failure(msg,_,_) => {
        S.error(msg)
        S.redirectTo("/")
      }
      case _ => {
        S.error("This should not have happened!")
        error("This should not have happened!")
        S.redirectTo("/")
      }
    }
    mm.openTheBox
  }


  def loadCompartmentFromPathParam:CompartmentRecord = {
    var mm:Box[CompartmentRecord] = Empty
    tryo(
      CompartmentRecord.readRestRec(debug("The compartmentMetaId in session after calling /model/modemetaid/compartment/compartmentMetaId is: {}", S.param("compartmentMetaId").openTheBox))
    ) match {
      case Full(m) => {mm = m}
      case Failure(msg,_,_) => {
        S.error(msg)
        S.redirectTo("/")
      }
      case _ => {
        S.error("This should not have happened!")
        error("This should not have happened!")
        S.redirectTo("/")
      }
    }
    mm.openTheBox
  }
}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 20/11/12
  *         Time: 5:01 PM */
class CreateModelScreen extends LiftScreen with LoggerWrapper {
  object model extends ScreenVar(SBMLModelRecord.createRecord)

  addFields(() => model.is.idO)
  addFields(() => model.is.nameO)
  addFields(() => model.is.notesO)


  protected def finish() = {
    trace("CreateModelScreen.finish() started executing!")
    model.is.createRestRec()
    S.notice("Model " + model.is.metaid + " was created successfully!")
    for(warnings <- SBMLStrictValidator.visit(model))
      S.warning(warnings)
  }
}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 26/11/12
  *         Time: 5:01 PM */
class EditModelScreen extends LiftScreen with LoggerWrapper {

  object model extends ScreenVar(screenUtil.loadSBMLModelFromPathParam)

  addFields(() => model.is.idO)
  addFields(() => model.is.nameO)
  addFields(() => model.is.notesO)

  protected def finish() {
    trace("EditModelScreen.finish() started executing!")
    model.is.updateRestRec()
    S.notice("Model " + model.is.metaid + " was saved successfully!")
    for(warnings <- SBMLStrictValidator.visit(model))
      S.warning(warnings)
  }
}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 20/11/12
  *         Time: 5:01 PM */
class CreateCompartmentScreen extends LiftScreen with LoggerWrapper {
  object compartment extends ScreenVar(CompartmentRecord.createRecord)
  compartment.parent = Full(screenUtil.loadSBMLModelFromPathParam)
  addFields(() => compartment.is.idO)
  addFields(() => compartment.is.nameO)
  addFields(() => compartment.is.spatialDimensions0)
  addFields(() => compartment.is.constantO)
  addFields(() => compartment.is.sizeO )
  addFields(() => compartment.is.outsideO)
  addFields(() => compartment.is.notesO)

  protected def finish() = {
    trace("CreateCompartmentScreen.finish() started executing!")
    compartment.is.createRestRec()
    S.notice("Compartment " + compartment.is.metaid + " was created successfully!")
    for(warnings <- SBMLStrictValidator.visit(compartment))
      S.warning(warnings)

  }
}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 27/11/12
  *         Time: 14:37 PM */
class EditCompartmentScreen extends LiftScreen with LoggerWrapper {
  object compartment extends ScreenVar(screenUtil.loadCompartmentFromPathParam)
  compartment.parent = Full(screenUtil.loadSBMLModelFromPathParam)
  addFields(() => compartment.is.idO)
  addFields(() => compartment.is.nameO)
  addFields(() => compartment.is.spatialDimensions0)
  addFields(() => compartment.is.constantO)
  addFields(() => compartment.is.sizeO )
  addFields(() => compartment.is.outsideO)
  addFields(() => compartment.is.notesO)

  protected def finish() {
    trace("EditCompartmentScreen.finish() started executing!")
    compartment.is.updateRestRec()
    S.notice("Compartment " + compartment.is.metaid + " was saved successfully!")
    for(warnings <- SBMLStrictValidator.visit(compartment))
      S.warning(warnings)
  }
}




/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 25/10/12
  *         Time: 7:01 PM */
class CreateFunctionDefinitionScreen extends LiftScreen {
  object function extends ScreenVar(FunctionDefinitionRecord.createRecord)

  //override def screenTop =  <b>A single screen with some input validation</b>
  addFields(() => function.is)

  def finish() = {
    S.notice(<div>Validation is going in here <br/> Second line of validation goes here!</div>)
  }
}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 20/11/12
  *         Time: 4:45 PM */
class EditFunctionDefinitionScreen extends LiftScreen {
  object function extends ScreenVar(
    FunctionDefinitionRecord.readRestRec(S.param("functionDefinitioMetaId").openTheBox).openTheBox
  )

  //override def screenTop =  <b>A single screen with some input validation</b>
  addFields(() => function.is)

  def finish() = {
    S.notice(<div>Validation is going in here <br/> Second line of validation goes here!</div>)
  }
}
