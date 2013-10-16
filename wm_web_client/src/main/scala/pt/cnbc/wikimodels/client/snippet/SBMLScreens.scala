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
import pt.cnbc.wikimodels.dataModel.SBMLModel
import visitor.SBMLFromRecord

//implicits
import pt.cnbc.wikimodels.client.record.visitor.SBMLFromRecord._


package object screenUtil extends LoggerWrapper{
  def genWarnsForSBMLModel() {
    val mr = screenUtil.loadSBMLModelFromPathParam
    SBMLStrictValidator.visitModel(mr).map(err => S.warning(err))
  }

  def loadSBMLModelFromPathParam:SBMLModelRecord = {
    var mm:Box[SBMLModelRecord] = Empty
    tryo(
      SBMLModelRecord.readRestRec(debug("The modelMetaId in session after calling /model/modemetaid is: {}", S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")))
    ) match {
      case Full(m) => {mm = m}
      case Failure(msg,_,_) => {
        S.error(Text(msg))
        S.redirectTo("/")
      }
      case _ => {
        S.error("This should not have happened!")
        error("This should not have happened!")
        S.redirectTo("/")
      }
    }
    mm.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")
  }


  def loadCompartmentFromPathParam:CompartmentRecord = {
    var mm:Box[CompartmentRecord] = Empty
    tryo(
      CompartmentRecord.readRestRec(debug("The compartmentMetaId in session after calling /model/modemetaid/compartment/compartmentMetaId is: {}", S.param("compartmentMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")))
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
    mm.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")
  }

  def loadSpeciesFromPathParam:SpeciesRecord = {
    var mm:Box[SpeciesRecord] = Empty
    tryo(
      SpeciesRecord.readRestRec(debug("The speciesMetaId in session after calling /model/modemetaid/species/speciesMetaId is: {}", S.param("speciesMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")))
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
    mm.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")
  }

  def loadParameterFromPathParam:ParameterRecord = {
    var mm:Box[ParameterRecord] = Empty
    tryo(
      ParameterRecord.readRestRec(debug("The parameterMetaId in session after calling /model/modemetaid/parameter/speciesMetaId is: {}", S.param("parameterMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")))
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
    mm.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")
  }

  def loadFunctionDefinitionFromPathParam:FunctionDefinitionRecord = {
    var mm:Box[FunctionDefinitionRecord] = Empty
    tryo(
      FunctionDefinitionRecord.readRestRec(debug("The functionDefinitionMetaId in session after calling /model/modemetaid/functionDefinition/functionDMetaId is: {}", S.param("functionDefinitionMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")))
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
    mm.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")
  }
}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 20/11/12
  *         Time: 5:01 PM */
class CreateModelScreen extends LiftScreen with LoggerWrapper {
  object model extends ScreenVar(SBMLModelRecord.createRecord)

  addFields(() => model.get.idO)
  addFields(() => model.get.nameO)
  addFields(() => model.get.notesO)


  protected def finish() = {
    trace("CreateModelScreen.finish() started executing!")
    model.get.createRestRec()
    S.notice("Model " + model.metaIdO.get + " was created successfully!")
    for(warnings <- SBMLStrictValidator.visitModel(model.get))
      S.warning(warnings)
  }
}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 26/11/12
  *         Time: 5:01 PM */
class EditModelScreen extends LiftScreen with LoggerWrapper {
  object model extends ScreenVar(screenUtil.loadSBMLModelFromPathParam)
  addFields(() => model.get.idO)
  addFields(() => model.get.nameO)
  addFields(() => model.get.notesO)

  protected def finish() {
    trace("EditModelScreen.finish() started executing!")
    model.get.updateRestRec()
    S.notice("Model " + model.get.metaIdO.get + " was saved successfully!")
    for(warnings <- SBMLStrictValidator.visitModel(model.get))
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
  addFields(() => compartment.get.idO)
  addFields(() => compartment.get.nameO)
  addFields(() => compartment.get.spatialDimensions0)
  addFields(() => compartment.get.constantO)
  addFields(() => compartment.get.sizeO )
  addFields(() => compartment.get.outsideO)
  addFields(() => compartment.get.notesO)

  protected def finish() = {
    trace("CreateCompartmentScreen.finish() started executing!")
    compartment.get.createRestRec()
    S.notice("Compartment " + compartment.get.metaIdO.get + " was created successfully!")
    for(warnings <- SBMLStrictValidator.visitCompartment(compartment.get))
      S.warning(warnings)
    screenUtil.genWarnsForSBMLModel()
  }
}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 27/11/12
  *         Time: 14:37 PM */
class EditCompartmentScreen extends LiftScreen with LoggerWrapper {
  object compartment extends ScreenVar(screenUtil.loadCompartmentFromPathParam)
  compartment.parent = Full(screenUtil.loadSBMLModelFromPathParam)
  addFields(() => compartment.get.idO)
  addFields(() => compartment.get.nameO)
  addFields(() => compartment.get.spatialDimensions0)
  addFields(() => compartment.get.constantO)
  addFields(() => compartment.get.sizeO )
  addFields(() => compartment.get.outsideO)
  addFields(() => compartment.get.notesO)

  protected def finish() {
    trace("EditCompartmentScreen.finish() started executing!")
    debug("Compartment from screen is " + compartment.get.toXML)
    compartment.get.updateRestRec()
    S.notice("Compartment " + compartment.get.metaIdO.get + " was saved successfully!")
    for(warnings <- SBMLStrictValidator.visitCompartment(compartment.get))
      S.warning(warnings)
    screenUtil.genWarnsForSBMLModel()
  }

}


/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 20/11/12
  *         Time: 5:01 PM */
class CreateSpeciesScreen extends LiftScreen with LoggerWrapper {
  object species extends ScreenVar(SpeciesRecord.createRecord)
  species.parent = Full(screenUtil.loadSBMLModelFromPathParam)
  addFields(() => species.get.idO)
  addFields(() => species.get.nameO)
  addFields(() => species.get.compartmentO)
  addFields(() => species.get.constantO)
  addFields(() => species.get.initialAmountO )
  addFields(() => species.get.initialConcentrationO)
  addFields(() => species.get.boundaryConditionO)
  addFields(() => species.get.notesO)

  protected def finish() = {
    trace("CreateSpeciesScreen.finish() started executing!")
    species.get.createRestRec()
    S.notice("Species " + species.get.metaIdO.get + " was created successfully!")
    for(warnings <- SBMLStrictValidator.visitSpecies(species.get))
      S.warning(warnings)
    screenUtil.genWarnsForSBMLModel()
  }
}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 27/11/12
  *         Time: 14:37 PM */
class EditSpeciesScreen extends LiftScreen with LoggerWrapper {
  object species extends ScreenVar(screenUtil.loadSpeciesFromPathParam)
  species.parent = Full(screenUtil.loadSBMLModelFromPathParam)
  addFields(() => species.get.idO)
  addFields(() => species.get.nameO)
  addFields(() => species.get.compartmentO)
  addFields(() => species.get.constantO)
  addFields(() => species.get.initialAmountO )
  addFields(() => species.get.initialConcentrationO)
  addFields(() => species.get.boundaryConditionO)
  addFields(() => species.get.notesO)

  protected def finish() {
    trace("EditSpeciesScreen.finish() started executing!")
    species.get.updateRestRec()
    S.notice("Species " + species.get.metaIdO.get + " was saved successfully!")
    for(warnings <- SBMLStrictValidator.visitSpecies(species.get))
      S.warning(warnings)
    screenUtil.genWarnsForSBMLModel()
  }
}



/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 20/11/12
  *         Time: 5:01 PM */
class CreateParameterScreen extends LiftScreen with LoggerWrapper {
  object parameter extends ScreenVar(ParameterRecord.createRecord)
  parameter.parent = Full(screenUtil.loadSBMLModelFromPathParam)
  addFields(() => parameter.get.idO)
  addFields(() => parameter.get.nameO)
  addFields(() => parameter.get.valueO)
  addFields(() => parameter.get.constantO)
  addFields(() => parameter.get.notesO)

  protected def finish() = {
    trace("CreateParameterScreen.finish() started executing!")
    parameter.get.createRestRec()
    S.notice("Parameter " + parameter.get.metaIdO.get + " was created successfully!")
    for(warnings <- SBMLStrictValidator.visitParameter(parameter.get))
      S.warning(warnings)
    S.redirectTo( parameter.relativeURL )
  }
}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 27/11/12
  *         Time: 14:37 PM */
class EditParameterScreen extends LiftScreen with LoggerWrapper {
  object parameter extends ScreenVar(screenUtil.loadParameterFromPathParam)
  parameter.parent = Full(screenUtil.loadSBMLModelFromPathParam)
  addFields(() => parameter.get.idO)
  addFields(() => parameter.get.nameO)
  addFields(() => parameter.get.valueO)
  addFields(() => parameter.get.constantO)
  addFields(() => parameter.get.notesO)

  protected def finish() {
    trace("EditParameterScreen.finish() started executing!")
    parameter.get.updateRestRec()
    S.notice("Parameter " + parameter.get.metaIdO.get + " was saved successfully!")
    for(warnings <- SBMLStrictValidator.visitParameter(parameter.get))
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
  addFields(() => function.get.idO)
  addFields(() => function.get.nameO)
  addFields(() => function.get.mathO)
  addFields(() => function.get.notesO)

  def finish() = {
    trace("CreateFunctionDefinitionScreen.finish() started executing!")
    function.get.createRestRec()
    S.notice("Function definition " + function.get.metaIdO.get + " was created successfully!")
    for(warnings <- SBMLStrictValidator.visitFunctionDefinition(function.get))
      S.warning(warnings)
  }
}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 20/11/12
  *         Time: 4:45 PM */
class EditFunctionDefinitionScreen extends LiftScreen with LoggerWrapper{
  object function extends ScreenVar(screenUtil.loadFunctionDefinitionFromPathParam)
  function.parent = Full(screenUtil.loadSBMLModelFromPathParam)

  //override def screenTop =  <b>A single screen with some input validation</b>
  addFields(() => function.get.idO)
  addFields(() => function.get.nameO)
  addFields(() => function.get.mathO)
  addFields(() => function.get.notesO)

  protected def finish() {
    trace("EditParameterScreen.finish() started executing!")
    function.get.updateRestRec()
    S.notice("Function definition " + function.get.metaIdO.get + " was saved successfully!")
    for(warnings <- SBMLStrictValidator.visitFunctionDefinition(function.get))
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
  addFields(() => reaction.get.idO)
  addFields(() => reaction.get.nameO)
  addFields(() => reaction.get.notesO)

  def finish() = {
    trace("CreateReactionScreen.finish() started executing!")
    reaction.get.createRestRec()
    S.notice("Reaction " + reaction.get.metaIdO.get + " was created successfully!")
    for(warnings <- SBMLStrictValidator.visitReaction(reaction.get))
      S.warning(warnings)
  }
}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 20/11/12
  *         Time: 4:45 PM */
class EditReactionScreen extends LiftScreen with LoggerWrapper{
  object reaction extends ScreenVar(
    ReactionRecord.readRestRec(S.param("reactionMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")).openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")
  )

  //override def screenTop =  <b>A single screen with some input validation</b>
  addFields(() => reaction.get.idO)
  addFields(() => reaction.get.nameO)
  addFields(() => reaction.get.notesO)

  protected def finish() {
    trace("EditReactionScreen.finish() started executing!")
    reaction.get.updateRestRec()
    S.notice("Reaction " + reaction.get.metaIdO.get + " was saved successfully!")
    for(warnings <- SBMLStrictValidator.visitReaction(reaction.get))
      S.warning(warnings)
  }
}

