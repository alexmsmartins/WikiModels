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

  def loadSpeciesFromPathParam:SpeciesRecord = {
    var mm:Box[SpeciesRecord] = Empty
    tryo(
      SpeciesRecord.readRestRec(debug("The speciesMetaId in session after calling /model/modemetaid/species/speciesMetaId is: {}", S.param("speciesMetaId").openTheBox))
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

  def loadParameterFromPathParam:ParameterRecord = {
    var mm:Box[ParameterRecord] = Empty
    tryo(
      ParameterRecord.readRestRec(debug("The parameterMetaId in session after calling /model/modemetaid/parameter/speciesMetaId is: {}", S.param("parameterMetaId").openTheBox))
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

  def loadFunctionDefinitionFromPathParam:FunctionDefinitionRecord = {
    var mm:Box[FunctionDefinitionRecord] = Empty
    tryo(
      FunctionDefinitionRecord.readRestRec(debug("The functionDefinitionMetaId in session after calling /model/modemetaid/functionDefinition/functionDMetaId is: {}", S.param("functionDefinitionMetaId").openTheBox))
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
  *         Date: 20/11/12
  *         Time: 5:01 PM */
class CreateSpeciesScreen extends LiftScreen with LoggerWrapper {
  object species extends ScreenVar(SpeciesRecord.createRecord)
  species.parent = Full(screenUtil.loadSBMLModelFromPathParam)
  addFields(() => species.is.idO)
  addFields(() => species.is.nameO)
  addFields(() => species.is.compartmentO)
  addFields(() => species.is.constantO)
  addFields(() => species.is.initialAmount0 )
  addFields(() => species.is.initialConcentration0)
  addFields(() => species.is.boundaryCondition0)
  addFields(() => species.is.notesO)

  protected def finish() = {
    trace("CreateSpeciesScreen.finish() started executing!")
    species.is.createRestRec()
    S.notice("Species " + species.is.metaid + " was created successfully!")
    for(warnings <- SBMLStrictValidator.visit(species))
      S.warning(warnings)
  }
}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 27/11/12
  *         Time: 14:37 PM */
class EditSpeciesScreen extends LiftScreen with LoggerWrapper {
  object species extends ScreenVar(screenUtil.loadSpeciesFromPathParam)
  species.parent = Full(screenUtil.loadSBMLModelFromPathParam)
  addFields(() => species.is.idO)
  addFields(() => species.is.nameO)
  addFields(() => species.is.compartmentO)
  addFields(() => species.is.constantO)
  addFields(() => species.is.initialAmount0 )
  addFields(() => species.is.initialConcentration0)
  addFields(() => species.is.boundaryCondition0)
  addFields(() => species.is.notesO)

  protected def finish() {
    trace("EditSpeciesScreen.finish() started executing!")
    species.is.updateRestRec()
    S.notice("Species " + species.is.metaid + " was saved successfully!")
    for(warnings <- SBMLStrictValidator.visit(species))
      S.warning(warnings)
  }
}



/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 20/11/12
  *         Time: 5:01 PM */
class CreateParameterScreen extends LiftScreen with LoggerWrapper {
  object parameter extends ScreenVar(ParameterRecord.createRecord)
  parameter.parent = Full(screenUtil.loadSBMLModelFromPathParam)
  addFields(() => parameter.is.idO)
  addFields(() => parameter.is.nameO)
  addFields(() => parameter.is.valueO)
  addFields(() => parameter.is.constantO)
  addFields(() => parameter.is.notesO)

  protected def finish() = {
    trace("CreateParameterScreen.finish() started executing!")
    parameter.is.createRestRec()
    S.notice("Parameter " + parameter.is.metaid + " was created successfully!")
    for(warnings <- SBMLStrictValidator.visit(parameter))
      S.warning(warnings)
  }
}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 27/11/12
  *         Time: 14:37 PM */
class EditParameterScreen extends LiftScreen with LoggerWrapper {
  object parameter extends ScreenVar(screenUtil.loadParameterFromPathParam)
  parameter.parent = Full(screenUtil.loadSBMLModelFromPathParam)
  addFields(() => parameter.is.idO)
  addFields(() => parameter.is.nameO)
  addFields(() => parameter.is.valueO)
  addFields(() => parameter.is.constantO)
  addFields(() => parameter.is.notesO)

  protected def finish() {
    trace("EditParameterScreen.finish() started executing!")
    parameter.is.updateRestRec()
    S.notice("Parameter " + parameter.is.metaid + " was saved successfully!")
    for(warnings <- SBMLStrictValidator.visit(parameter))
      S.warning(warnings)
  }
}


/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 25/10/12
  *         Time: 7:01 PM */
class CreateFunctionDefinitionScreen extends LiftScreen with LoggerWrapper{
  object function extends ScreenVar(FunctionDefinitionRecord.createRecord)
  function.parent = Full(screenUtil.loadSBMLModelFromPathParam)

  //override def screenTop =  <b>A single screen with some input validation</b>
  addFields(() => function.is.idO)
  addFields(() => function.is.nameO)
  addFields(() => function.is.mathO)
  addFields(() => function.is.notesO)

  def finish() = {
    trace("CreateFunctionDefinitionScreen.finish() started executing!")
    function.is.createRestRec()
    S.notice("Function definition " + function.is.metaid + " was created successfully!")
    for(warnings <- SBMLStrictValidator.visit(function))
      S.warning(warnings)
  }
}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 20/11/12
  *         Time: 4:45 PM */
class EditFunctionDefinitionScreen extends LiftScreen with LoggerWrapper{
  object function extends ScreenVar(
    FunctionDefinitionRecord.readRestRec(S.param("functionDefinitionMetaId").openTheBox).openTheBox
  )

  //override def screenTop =  <b>A single screen with some input validation</b>
  addFields(() => function.is.idO)
  addFields(() => function.is.nameO)
  addFields(() => function.is.mathO)
  addFields(() => function.is.notesO)

  protected def finish() {
    trace("EditParameterScreen.finish() started executing!")
    function.is.updateRestRec()
    S.notice("Function definition " + function.is.metaid + " was saved successfully!")
    for(warnings <- SBMLStrictValidator.visit(function))
      S.warning(warnings)
  }
}


/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 25/10/12
  *         Time: 7:01 PM */
class CreateReactionScreen extends LiftScreen with LoggerWrapper{
  object reaction extends ScreenVar(ReactionRecord.createRecord)
  reaction.parent = Full(screenUtil.loadSBMLModelFromPathParam)

  //override def screenTop =  <b>A single screen with some input validation</b>
  addFields(() => reaction.is.idO)
  addFields(() => reaction.is.nameO)
  addFields(() => reaction.is.notesO)

  def finish() = {
    trace("CreateReactionScreen.finish() started executing!")
    reaction.is.createRestRec()
    S.notice("Reaction " + reaction.is.metaid + " was created successfully!")
    for(warnings <- SBMLStrictValidator.visit(reaction))
      S.warning(warnings)
  }
}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 20/11/12
  *         Time: 4:45 PM */
class EditReactionScreen extends LiftScreen with LoggerWrapper{
  object reaction extends ScreenVar(
    ReactionRecord.readRestRec(S.param("reactionMetaId").openTheBox).openTheBox
  )

  //override def screenTop =  <b>A single screen with some input validation</b>
  addFields(() => reaction.is.idO)
  addFields(() => reaction.is.nameO)
  addFields(() => reaction.is.notesO)

  protected def finish() {
    trace("EditReactionScreen.finish() started executing!")
    reaction.is.updateRestRec()
    S.notice("Reaction " + reaction.is.metaid + " was saved successfully!")
    for(warnings <- SBMLStrictValidator.visit(reaction))
      S.warning(warnings)
  }
}

