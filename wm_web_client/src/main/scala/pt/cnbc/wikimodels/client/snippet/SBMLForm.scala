package pt.cnbc.wikimodels.client.snippet

import java.util.Date
import scala.xml._


import net.liftweb._
import common._
import http._
import util.Helpers._
import sitemap._
import mapper._
import S._
import SHtml._
import util._
import Helpers._

import pt.cnbc.wikimodels.client.model._
import pt.cnbc.wikimodels.client.record._

import alexmsmartins.log.LoggerWrapper
import pt.cnbc.wikimodels.controller.SMsg
import pt.cnbc.wikimodels.sbmlVisitors.SBMLStrictValidator
import pt.cnbc.wikimodels.dataModel.SBMLModel
import visitor.SBMLFromRecord

/**
 * This snippet contains the forms and html to handle all the actions a user can perform on a SBMLModel
 * This includes creating a new model, visualizing an existing one, updating its associated information and deelting it
 * when it is no longer needed.
 * User: alex
 * Date: 01-08-2011
 * Time: 16:28
 */
class SBMLForm extends DispatchSnippet with SMsg with LoggerWrapper {
  //TODO check if selectedXXX can be merged and refactor all the methods that call it. Then, replace this ->
  //TODO <- by a justification for not doing the refactoring if that is the case
  private object selectedModel extends RequestVar[Box[SBMLModelRecord]](Empty)
  private object selectedCompartment extends RequestVar[Box[CompartmentRecord]](Empty)
  private object selectedSpecies extends RequestVar[Box[SpeciesRecord]](Empty)
  private object selectedParameter extends RequestVar[Box[ParameterRecord]](Empty)
  private object selectedFunctionDefinition extends RequestVar[Box[FunctionDefinitionRecord]](Empty)
  private object selectedConstraint extends RequestVar[Box[ConstraintRecord]](Empty)
  private object selectedReaction extends RequestVar[Box[ReactionRecord]](Empty)

  private def loadSBMLModelFromPathParam() = {
    trace("calling SBMLForm.loadSBMLModelFromPathParam")
    debug("""Parameter "modelMetaId" « {} """, S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method"))

    selectedModel {
      SBMLModelRecord.readRestRec(debug("The modelMetaId in session after calling /model/modemetaid is: {}", S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")))
    }
    debug("Loaded model into session: {}", selectedModel.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").toXML )
    debug("Loaded model into session with metaid {}, id {} and name {}",
      selectedModel.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").metaIdO.get,
      selectedModel.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").idO.get,
      selectedModel.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").nameO.get)
    selectedModel.is
  }

  private def loadCompartmentFromPathParam() = {
    trace("Calling SBMLForm.loadCompartmentFromPathParam")
    debug("""Parameter "compartmentMetaId" « {} """, S.param("compartmentMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method"))

    selectedCompartment {
      CompartmentRecord.readRestRec(debug("The compartmentMetaId in session after calling /model/modemetaid/compartment/compartmentMetaId is: {}", S.param("compartmentMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")))
    }

    //TODO this might be optimizable by turning the parent field into a lazy field and the interface buttons that need it into ajax buttons
    selectedModel {
      SBMLModelRecord.readRestRec(debug("The modelMetaId in session after calling /model/modemetaid is: {}", S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")))
    }
    selectedCompartment.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").parent = selectedModel.get
    debug("Loaded compartment into session: {}", selectedCompartment.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").toXML )
    debug("Loaded model into session with metaid {}, id {} and name {}",
      selectedCompartment.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").metaIdO.get,
      selectedCompartment.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").idO.get,
      selectedCompartment.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").nameO.get)
    selectedCompartment.is
  }

  private def loadSpeciesFromPathParam() = {
    trace("Calling SBMLForm.loadSpeciesFromPathParam")
    debug("""Parameter "speciesMetaId" « {} """, S.param("speciesMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method"))

    selectedSpecies {
      SpeciesRecord.readRestRec(debug("The speciesMetaId in session after calling /model/modemetaid/species/speciesMetaId is: {}", S.param("speciesMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")))
    }

    //TODO this might be optimizable by turning the parent field into a lazy field and the interface buttons that need it into ajax buttons
    selectedModel {
      SBMLModelRecord.readRestRec(debug("The modelMetaId in session after calling /model/modemetaid is: {}", S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")))
    }
    selectedSpecies.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").parent = selectedModel.get
    debug("Loaded species into session: {}", selectedSpecies.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").toXML )
    debug("Loaded model into session with metaid {}, id {} and name {}",
      selectedSpecies.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").metaIdO.get,
      selectedSpecies.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").idO.get,
      selectedSpecies.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").idO.get)
    selectedSpecies.is
  }

  private def loadParameterFromPathParam() = {
    trace("Calling SBMLForm.loadBootParameterFromPathParam")
    debug("""Parameter "parameterMetaId" « {} """, S.param("parameterMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method"))

    selectedParameter {
      ParameterRecord.readRestRec(debug("The parameterMetaId in session after calling /model/modemetaid/parameter/parameterMetaId is: {}", S.param("parameterMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")))
    }

    //TODO this might be optimizable by turning the parent field into a lazy field and the interface buttons that need it into ajax buttons
    selectedModel {
      SBMLModelRecord.readRestRec(debug("The modelMetaId in session after calling /model/modemetaid is: {}", S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")))
    }
    selectedParameter.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").parent = selectedModel.get
    debug("Loaded parameter into session: {}", selectedParameter.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").toXML )
    debug("Loaded model into session with metaid {}, id {} and name {}",
      selectedParameter.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").metaIdO.get,
      selectedParameter.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").idO.get,
      selectedParameter.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").idO.get)
    selectedParameter.is
  }

  private def loadFunctionDefinitionFromPathParam() = {
    trace("Calling SBMLForm.loadFunctionDefinitionFromPathParam")
    debug("""Parameter "functionDefinitioMetaId" « {} """, S.param("functionDefinitioMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method"))

    selectedFunctionDefinition {
      FunctionDefinitionRecord.readRestRec(debug("The functionDefinitioMetaId in session after calling /model/modemetaid/functiondefinition/functionDefinitionMetaId is: {}", S.param("functionDefinitioMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")))
    }

    //TODO this might be optimizable by turning the parent field into a lazy field and the interface buttons that need it into ajax buttons
    selectedModel {
      SBMLModelRecord.readRestRec(debug("The modelMetaId in session after calling /model/modemetaid is: {}", S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")))
    }
    selectedFunctionDefinition.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").parent = selectedModel.get
    debug("Loaded function definition into session: {}", selectedFunctionDefinition.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").toXML )
    debug("Loaded model into session with metaid {}, id {} and name {}",
      selectedFunctionDefinition.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").metaIdO.get,
      selectedFunctionDefinition.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").idO.get,
      selectedFunctionDefinition.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").idO.get)
    selectedFunctionDefinition.is
  }

  private def loadConstraintFromPathParam() = {
    trace("Calling SBMLForm.loadConstraintFromPathParam")
    debug("""Parameter "constraintMetaId" « {} """, S.param("constraintMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method"))

    selectedConstraint{
      ConstraintRecord.readRestRec(debug("The constraintMetaId in session after calling /model/modemetaid/constraint/constraintMetaId is: {}", S.param("constraintMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")))
    }

    //TODO this might be optimizable by turning the parent field into a lazy field and the interface buttons that need it into ajax buttons
    selectedModel {
      SBMLModelRecord.readRestRec(debug("The modelMetaId in session after calling /model/modemetaid is: {}", S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")))
    }
    selectedConstraint.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").parent = selectedModel.get
    debug("Loaded constraint into session: {}", selectedConstraint.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").toXML )
    debug("Loaded model into session with metaid {}, id {} and name {}",
      selectedConstraint.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").metaIdO.get,
      selectedConstraint.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").idO.get,
      selectedConstraint.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").idO.get)
    selectedConstraint.is
  }

  private def loadReactionFromPathParam() = {
    trace("Calling SBMLForm.loadReactionFromPathParam")
    debug("""Parameter "reactionMetaId" « {} """, S.param("reactionMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method"))

    selectedReaction {
      ReactionRecord.readRestRec(debug("The reactionMetaId in session after calling /model/modemetaid/reaction/reactionMetaId is: {}", S.param("reactionMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")))
    }

    //TODO this might be optimizable by turning the parent field into a lazy field and the interface buttons that need it into ajax buttons
    selectedModel {
      SBMLModelRecord.readRestRec(debug("The modelMetaId in session after calling /model/modemetaid is: {}", S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method")))
    }
    selectedReaction.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").parent = selectedModel.get
    debug("Loaded reaction into session: {}", selectedReaction.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").toXML )
    debug("Loaded model into session with metaid {}, id {} and name {}",
      selectedReaction.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").metaIdO.get,
      selectedReaction.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").idO.get,
      selectedReaction.get.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").idO.get)
    selectedReaction.is
  }

  def dispatch: DispatchIt = {
    //TODO delete unused dispatch functions and references to them
    //model dispatching
    //case "createModel" => createNewModel
    //case "editModel" => editSelectedModel
    case "visualizeModel" => visualizeSelectedModel
    case "deleteModel" => confirmAndDeleteSelectedModel
    //compartment dispatching
    case "createCompartment" => createNewCompartment
    case "editCompartment" => editSelectedCompartment
    case "visualizeCompartment" => visualizeSelectedCompartment
    case "deleteCompartment" => confirmAndDeleteSelectedCompartment
    //species dispatching
    case "createSpecies" => createNewSpecies
    case "editSpecies" => editSelectedSpecies
    case "visualizeSpecies" => visualizeSelectedSpecies
    case "deleteSpecies" => confirmAndDeleteSelectedSpecies
    //parameter dispatching
    case "createParameter" => createNewParameter
    case "editParameter" => editSelectedParameter
    case "visualizeParameter" => visualizeSelectedParameter
    case "deleteParameter" => confirmAndDeleteSelectedParameter
    //function definition dispatching
    case "createFunctionDefinition" => createNewFunctionDefinition
    case "editFunctionDefinition" => editSelectedFunctionDefinition
    case "visualizeFunctionDefinition" => visualizeSelectedFunctionDefinition
    case "deleteFunctionDefinition" => confirmAndDeleteSelectedFunctionDefinition
    //constraint dispatching
    case "createConstraint" => createNewConstraint
    case "editConstraint" => editSelectedConstraint
    case "visualizeConstraint" => visualizeSelectedConstraint
    case "deleteConstraint" => confirmAndDeleteSelectedConstraint
    //reaction dispatching
    case "createReaction" => createNewReaction
    case "editReaction" => editSelectedReaction
    case "visualizeReaction" => visualizeSelectedReaction
    case "deleteReaction" => confirmAndDeleteSelectedReaction

  }

  //### Create state ###

  def saveNewModel(model:SBMLModelRecord):Unit = {
    info("SAVE NEW MODEL with xml {}", model.toXML)
    //metaid is never presented and, by default, we give it the same value as id
    model.metaIdO.set(model.idO.get)
    model.validate match {
      case Nil => { //no validation errors
        model.metaIdO.set(model.idO.get)
        model.createRestRec() match {
          case Full(x) => {
            redirectTo(model.relativeURL) //TODO: handle failure in the server (maybe this should be general)
          }
          case Empty => {
            S.error(mainMsgId, "Strange error when creating a new "+ model.sbmlType +". Report this bug please!")
            selectedModel(Full(model))
          }
          case x:ParamFailure[_] => {
            S.error(mainMsgId, x.messageChain) //TODO check if message chain is the right thing to send to S.error
            selectedModel(Full(model))
          }
          case x:Failure => {
            S.error(mainMsgId, x.messageChain)
            selectedModel(Full(model))
          }
        }
      }
      case x => {
        x.foreach(f => S.error(mainMsgId, "Error in " + f.field.uniqueFieldId + ": "+f.msg  ))
        selectedModel(Full(model))
      }
    }
  }

  def createNewModel(ns:NodeSeq):NodeSeq = {
    info("CREATE SELECTED MODEL")
    selectedModel.is.openOr(SBMLModelRecord.createRecord)
      .toForm(saveNewModel _) ++ <tr>
      <td><a href="/models">Cancel</a></td>
      <td><input type="submit" name="create" value="Create"/></td>
    </tr>
  }

  def saveNewCompartment(compartment:CompartmentRecord):
  Unit = {
    info("SAVE NEW COMPARTMENT with xml {}", compartment.toXML)
    //metaid is never presented and, by default, we give it the same value as id
    compartment.metaIdO.set(compartment.idO.get)
    compartment.validate match {
      case Nil => { //no validation errors
        compartment.metaIdO.set(compartment.idO.get)
        compartment.createRestRec() match {
          case Full(x) => {
            redirectTo(compartment.relativeURL) //TODO: handle failure in the server (maybe this should be general)
          }
          case Empty => {
            S.error(mainMsgId, "Strange error when creating a new compartment "+ compartment.sbmlType +". Report this bug please!")
            selectedCompartment(Full(compartment))
          }
          case x:ParamFailure[_] => {
            S.error(mainMsgId, x.messageChain) //TODO check if message chain is the right thing to send to S.error
            selectedCompartment(Full(compartment))
          }
          case x:Failure => {
            S.error(mainMsgId, x.messageChain)
            selectedCompartment(Full(compartment))
          }
        }
      }
      case x =>{
        x.foreach(f => S.error(mainMsgId, "Error in " + f.field.uniqueFieldId + ": "+f.msg  ))
        selectedCompartment(Full(compartment))
      }
    }
  }

  def createNewCompartment(ns:NodeSeq):NodeSeq = {
    info("CREATE SELECTED COMPARTMENT")
    val c = selectedCompartment.is.openOr(CompartmentRecord.createRecord)
    c.parent = loadSBMLModelFromPathParam()
    c.toForm(saveNewCompartment _) ++ <tr>
      <td><a href={"/model/" + S.param("modelMetaId").get}>Cancel</a></td>
      <td><input type="submit" name="create" value="Create"/></td>
    </tr>
  }

  def saveNewSpecies(species:SpeciesRecord):
  Unit = {
    info("SAVE NEW SPECIES with xml {}", species.toXML)
    //metaid is never presented and, by default, we give it the same value as id
    species.metaIdO.set(species.idO.get)
    species.validate match {
      case Nil => { //no validation errors
        species.metaIdO.set(species.idO.get)
        species.createRestRec() match {
          case Full(x) => {
            redirectTo(species.relativeURL) //TODO: handle failure in the server (maybe this should be general)
          }
          case Empty => {
            S.error(mainMsgId, "Strange error when creating a new species "+ species.sbmlType +". Report this bug please!")
            selectedSpecies(Full(species))
          }
          case x:ParamFailure[_] => {
            S.error(mainMsgId, x.messageChain) //TODO check if message chain is the right thing to send to S.error
            selectedSpecies(Full(species))
          }
          case x:Failure => {
            S.error(mainMsgId, x.messageChain)
            selectedSpecies(Full(species))
          }
        }
      }
      case x =>{
        x.foreach(f => S.error(mainMsgId, "Error in " + f.field.uniqueFieldId + ": "+f.msg  ))
        selectedSpecies(Full(species))
      }
    }
  }

  def createNewSpecies(ns:NodeSeq):NodeSeq = {
    info("CREATE SELECTED SPECIES")
    val s = selectedSpecies.is.openOr(SpeciesRecord.createRecord)
    s.parent = loadSBMLModelFromPathParam()
    s.toForm(saveNewSpecies _) ++ <tr>
      <td><a href={"/model/" + S.param("modelMetaId").get}>Cancel</a></td>
      <td><input type="submit" name="create" value="Create"/></td>
    </tr>
  }

  def saveNewParameter(parameter:ParameterRecord):
  Unit = {
    info("SAVE NEW Parameter with xml {}", parameter.toXML)
    //metaid is never presented and, by default, we give it the same value as id
    parameter.metaIdO.set(parameter.idO.get)
    parameter.validate match {
      case Nil => { //no validation errors
        parameter.metaIdO.set( parameter.idO.get)
        parameter.createRestRec() match {
          case Full(x) => {
            redirectTo(parameter.relativeURL) //TODO: handle failure in the server (maybe this should be general)
          }
          case Empty => {
            S.error(mainMsgId, "Strange error when creating a new parameter "+ parameter.sbmlType +". Report this bug please!")
            selectedParameter(Full(parameter))
          }
          case x:ParamFailure[_] => {
            S.error(mainMsgId, x.messageChain) //TODO check if message chain is the right thing to send to S.error
            selectedParameter(Full(parameter))
          }
          case x:Failure => {
            S.error(mainMsgId, x.messageChain)
            selectedParameter(Full(parameter))
          }
        }
      }
      case x =>{
        x.foreach(f => S.error(mainMsgId, "Error in " + f.field.uniqueFieldId + ": "+f.msg  ))
        selectedParameter(Full(parameter))
      }
    }
  }

  def createNewParameter(ns:NodeSeq):NodeSeq = {
    info("CREATE SELECTED PARAMETER")
    val s = selectedParameter.is.openOr(ParameterRecord.createRecord)
    s.parent = loadSBMLModelFromPathParam()
    s.toForm(saveNewParameter _) ++ <tr>
      <td><a href={"/model/" + S.param("modelMetaId").get}>Cancel</a></td>
      <td><input type="submit" name="create" value="Create"/></td>
    </tr>
  }

  def saveNewFunctionDefinition(functionDefinition:FunctionDefinitionRecord):
  Unit = {
    info("SAVE NEW FUNCTION DEFINITION with xml {}", functionDefinition.toXML)
    //metaid is never presented and, by default, we give it the same value as id
    functionDefinition.metaIdO.set(functionDefinition.idO.get)
    functionDefinition.validate match {
      case Nil => { //no validation errors
        functionDefinition.metaIdO.set(functionDefinition.idO.get)
        functionDefinition.createRestRec() match {
          case Full(x) => {
            redirectTo(functionDefinition.relativeURL) //TODO: handle failure in the server (maybe this should be general)
          }
          case Empty => {
            S.error(mainMsgId, "Strange error when creating a new functionDefinition "+ functionDefinition.sbmlType +". Report this bug please!")
            selectedFunctionDefinition(Full(functionDefinition))
          }
          case x:ParamFailure[_] => {
            S.error(mainMsgId, x.messageChain) //TODO check if message chain is the right thing to send to S.error
            selectedFunctionDefinition(Full(functionDefinition))
          }
          case x:Failure => {
            S.error(mainMsgId, x.messageChain)
            selectedFunctionDefinition(Full(functionDefinition))
          }
        }
      }
      case x => {
        x.foreach(f => S.error(mainMsgId, "Error in " + f.field.uniqueFieldId + ": "+f.msg  ))
        selectedFunctionDefinition(Full(functionDefinition))
      }
    }
  }

  def createNewFunctionDefinition(ns:NodeSeq):NodeSeq = {
    info("CREATE SELECTED FUNCTION DEFINITION")
    val s = selectedFunctionDefinition.is.openOr(FunctionDefinitionRecord.createRecord)
    s.parent = loadSBMLModelFromPathParam()
    s.toForm(saveNewFunctionDefinition _) ++ <tr>
      <td><a href={"/model/" + S.param("modelMetaId").get}>Cancel</a></td>
      <td><input type="submit" name="create" value="Create"/></td>
    </tr>
  }

  def saveNewConstraint(constraint:ConstraintRecord):
  Unit = {
    info("SAVE NEW Constraint with xml {}", constraint.toXML)
    //metaid is never presented and, by default, we give it the same value as id
    constraint.metaIdO.set(constraint.idO.get)
    constraint.validate match {
      case Nil => { //no validation errors
        constraint.metaIdO.set( constraint.idO.get)
        constraint.createRestRec() match {
          case Full(x) => {
            redirectTo(constraint.relativeURL) //TODO: handle failure in the server (maybe this should be general)
          }
          case Empty => {
            S.error(mainMsgId, "Strange error when creating a new constraint "+ constraint.sbmlType +". Report this bug please!")
            selectedConstraint(Full(constraint))
          }
          case x:ParamFailure[_] => {
            S.error(mainMsgId, x.messageChain) //TODO check if message chain is the right thing to send to S.error
            selectedConstraint(Full(constraint))
          }
          case x:Failure => {
            S.error(mainMsgId, x.messageChain)
            selectedConstraint(Full(constraint))
          }
        }
      }
      case x =>{
        x.foreach(f => S.error(mainMsgId, "Error in " + f.field.uniqueFieldId + ": "+f.msg  ))
        selectedConstraint(Full(constraint))
      }
    }
  }

  def createNewConstraint(ns:NodeSeq):NodeSeq = {
    info("CREATE SELECTED Constraint")
    val s = selectedConstraint.is.openOr(ConstraintRecord.createRecord)
    s.parent = loadSBMLModelFromPathParam()
    s.toForm(saveNewConstraint _) ++ <tr>
      <td><a href={"/model/" + S.param("modelMetaId").get}>Cancel</a></td>
      <td><input type="submit" name="create" value="Create"/></td>
    </tr>
  }

  def saveNewReaction(reaction:ReactionRecord):
  Unit = {
    info("SAVE NEW REACTION with xml {}", reaction.toXML)
    //metaid is never presented and, by default, we give it the same value as id
    reaction.metaIdO.set(reaction.idO.get)
    reaction.validate match {
      case Nil => { //no validation errors
        reaction.metaIdO.set(reaction.idO.get)
        reaction.createRestRec() match {
          case Full(x) => {
            redirectTo(reaction.relativeURL) //TODO: handle failure in the server (maybe this should be general)
          }
          case Empty => {
            S.error(mainMsgId, "Strange error when creating a new reaction "+ reaction.sbmlType +". Report this bug please!")
            selectedReaction(Full(reaction))
          }
          case x:ParamFailure[_] => {
            S.error(mainMsgId, x.messageChain) //TODO check if message chain is the right thing to send to S.error
            selectedReaction(Full(reaction))
          }
          case x:Failure => {
            S.error(mainMsgId, x.messageChain)
            selectedReaction(Full(reaction))
          }
        }
      }
      case x =>{
        x.foreach(f => S.error(mainMsgId, "Error in " + f.field.uniqueFieldId + ": "+f.msg  ))
        selectedReaction(Full(reaction))
      }
    }
  }

  def createNewReaction(ns:NodeSeq):NodeSeq = {
    info("CREATE SELECTED Reaction")
    val s = selectedReaction.is.openOr(ReactionRecord.createRecord)
    s.parent = loadSBMLModelFromPathParam()
    s.toForm(saveNewReaction _) ++ <tr>
      <td><a href={"/model/" + S.param("modelMetaId").get}>Cancel</a></td>
      <td><input type="submit" name="create" value="Create"/></td>
    </tr>
  }
  //### Edit state ###

  def saveSelectedModel(model:SBMLModelRecord):Unit = {
    debug("SAVE SELECTED MODEL")
    model.validate match {
      case Nil => model.updateRestRec(); redirectTo(model.relativeURL) //TODO: handle the possibility that the server does not accept the change (maybe this should be general)
      case x => {
        x.foreach(f => S.error(mainMsgId, "Error in " + f.field.uniqueFieldId + ": "+f.msg  ))
        selectedModel(Full(model))
      }
    }
  }

  def editSelectedModel(ns:NodeSeq):NodeSeq ={
    debug("EDIT SELECTED MODEL")
    loadSBMLModelFromPathParam
    selectedModel.is.map( _.toForm( saveSelectedModel _ ) ++ <tr>
                                      <td><a href={"/model/" + S.param("modelMetaId").get}>Cancel</a></td>
                                      <td><input type="submit" value="Save"/></td>
                                    </tr>
    ) openOr {S.error(mainMsgId, "Model not found"); redirectTo("/models")}
  }

  def saveSelectedCompartment(compartment:CompartmentRecord):Unit = {
    debug("SAVE SELECTED COMPARTMENT")
    debug("This compartment has an outside with value " + compartment.outsideO.get)
    compartment.validate match {
      case Nil => compartment.updateRestRec(); redirectTo(compartment.relativeURL) //TODO: handle the possibility that the server does not accept the change (maybe this should be general)
      case x =>{
        x.foreach(f => S.error(mainMsgId, "Error in " + f.field.uniqueFieldId + ": "+f.msg  ))
        selectedCompartment(Full(compartment))
      }
    }
  }

  def editSelectedCompartment(ns:NodeSeq):NodeSeq ={
    debug("EDIT SELECTED COMPARTMENT")
    loadCompartmentFromPathParam
    selectedCompartment.is.map( _.toForm( saveSelectedCompartment _ ) ++ <tr>
      <td><a href={"/model/" + S.param("modelMetaId").get}>Cancel</a></td>
      <td><input type="submit" value="Save"/></td>
    </tr>
    ) openOr {S.error(mainMsgId, "Compartment not found"); redirectTo("/models")}
  }

  def saveSelectedSpecies(species:SpeciesRecord):Unit = {
    debug("SAVE SELECTED SPECIES")
    species.validate match {
      case Nil => species.updateRestRec(); redirectTo(species.relativeURL) //TODO: handle the possibility that the server does not accept the change (maybe this should be general)
      case x =>{
        x.foreach(f => S.error(mainMsgId, "Error in " + f.field.uniqueFieldId + ": "+f.msg  ))
        selectedSpecies(Full(species))
      }
    }
  }

  def editSelectedSpecies(ns:NodeSeq):NodeSeq ={
    debug("EDIT SELECTED SPECIES")
    loadSpeciesFromPathParam
    selectedSpecies.is.map( _.toForm( saveSelectedSpecies _ ) ++ <tr>
      <td><a href={"/model/" + S.param("modelMetaId").get}>Cancel</a></td>
      <td><input type="submit" value="Save"/></td>
    </tr>
    ) openOr {S.error(mainMsgId, "Species not found"); redirectTo("/models")}
  }

  def saveSelectedParameter(parameter:ParameterRecord):Unit = {
    debug("SAVE SELECTED Parameter")
    parameter.validate match {
      case Nil => parameter.updateRestRec(); redirectTo(parameter.relativeURL) //TODO: handle the possibility that the server does not accept the change (maybe this should be general)
      case x =>{
        x.foreach(f => S.error(mainMsgId, "Error in " + f.field.uniqueFieldId + ": "+f.msg  ))
        selectedParameter(Full(parameter))
      }
    }
  }

  def editSelectedParameter(ns:NodeSeq):NodeSeq ={
    debug("EDIT SELECTED Parameter")
    loadParameterFromPathParam()
    selectedParameter.is.map( _.toForm( saveSelectedParameter _ ) ++ <tr>
      <td><a href={"/model/" + S.param("modelMetaId").get}>Cancel</a></td>
      <td><input type="submit" value="Save"/></td>
    </tr>
    ) openOr {S.error(mainMsgId, "Parameter not found"); redirectTo("/models")}
  }

  def saveSelectedFunctionDefinition(functionDefinition:FunctionDefinitionRecord):Unit = {
    debug("SAVE SELECTED FUNCTION DEFINITION")
    functionDefinition.validate match {
      case Nil => functionDefinition.updateRestRec(); redirectTo(functionDefinition.relativeURL) //TODO: handle the possibility that the server does not accept the change (maybe this should be general)
      case x =>{
        x.foreach(f => S.error(mainMsgId, "Error in " + f.field.uniqueFieldId + ": "+f.msg  ))
        selectedFunctionDefinition(Full(functionDefinition))
      }
    }
  }

  def editSelectedFunctionDefinition(ns:NodeSeq):NodeSeq ={
    debug("EDIT SELECTED FUNCTION DEFINITION")
    loadFunctionDefinitionFromPathParam()
    selectedFunctionDefinition.is.map( _.toForm( saveSelectedFunctionDefinition _ ) ++ <tr>
      <td><a href={"/model/" + S.param("modelMetaId").get}>Cancel</a></td>
      <td><input type="submit" value="Save"/></td>
    </tr>
    ) openOr {S.error(mainMsgId, "Function Definition not found"); redirectTo("/models")}
  }

  def saveSelectedConstraint(constraint:ConstraintRecord):Unit = {
    debug("SAVE SELECTED Constraint")
    constraint.validate match {
      case Nil => constraint.updateRestRec(); redirectTo(constraint.relativeURL) //TODO: handle the possibility that the server does not accept the change (maybe this should be general)
      case x =>{
        x.foreach(f => S.error(mainMsgId, "Error in " + f.field.uniqueFieldId + ": "+f.msg  ))
        selectedConstraint(Full(constraint))
      }
    }
  }

  def editSelectedConstraint(ns:NodeSeq):NodeSeq ={
    debug("EDIT SELECTED Constraint")
    loadConstraintFromPathParam
    selectedConstraint.is.map( _.toForm( saveSelectedConstraint _ ) ++ <tr>
      <td><a href={"/model/" + S.param("modelMetaId").get}>Cancel</a></td>
      <td><input type="submit" value="Save"/></td>
    </tr>
    ) openOr {S.error(mainMsgId, "Constraint not found"); redirectTo("/models")}
  }

  def saveSelectedReaction(reaction:ReactionRecord):Unit = {
    debug("SAVE SELECTED Reaction")
    reaction.validate match {
      case Nil => reaction.updateRestRec(); redirectTo(reaction.relativeURL) //TODO: handle the possibility that the server does not accept the change (maybe this should be general)
      case x =>{
        x.foreach(f => S.error(mainMsgId, "Error in " + f.field.uniqueFieldId + ": "+f.msg  ))
        selectedReaction(Full(reaction))
      }
    }
  }

  def editSelectedReaction(ns:NodeSeq):NodeSeq ={
    debug("EDIT SELECTED Reaction")
    loadReactionFromPathParam
    selectedReaction.is.map( _.toForm( saveSelectedReaction _ ) ++ <tr>
      <td><a href={"/model/" + S.param("modelMetaId").get}>Cancel</a></td>
      <td><input type="submit" value="Save"/></td>
    </tr>
    ) openOr {S.error(mainMsgId, "Reaction not found"); redirectTo("/models")}
  }

  //### Visualize state ###

  def visualizeSelectedModel(ns:NodeSeq):NodeSeq = {
    debug("VISUALIZE SELECTED MODEL")
    loadSBMLModelFromPathParam
    debug("Loaded selectedModel = " + selectedModel.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").toXML )
      selectedModel.map(mr =>{
        val m:SBMLModel = SBMLFromRecord.createModelFrom(mr)
        SBMLStrictValidator.visit(m).map(err => S.notice(err))
        mr.toXHtml
      }
    ) openOr {S.error(mainMsgId,"Model not found"); redirectTo("/models/")}
  }

  def visualizeSelectedCompartment(ns:NodeSeq):NodeSeq = {
    debug("VISUALIZE SELECTED COMPARTMENT")
    loadCompartmentFromPathParam
    debug("Loaded selectedCompartment = " + selectedCompartment.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").toXML )
    selectedCompartment.map(_.toXHtml) openOr {S.error(mainMsgId,"Compartment not found"); redirectTo("/models/")}
  }

  def visualizeSelectedSpecies(ns:NodeSeq):NodeSeq = {
    debug("VISUALIZE SELECTED SPECIES")
    loadSpeciesFromPathParam()
    debug("Loaded selectedSpecies= " + selectedSpecies.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").toXML )
    selectedSpecies.map(_.toXHtml) openOr {S.error(mainMsgId,"Species not found"); redirectTo("/models/")}
  }

  def visualizeSelectedParameter(ns:NodeSeq):NodeSeq = {
    debug("VISUALIZE SELECTED Parameter")
    loadParameterFromPathParam()
    debug("Loaded selectedParameter= " + selectedParameter.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").toXML )
    selectedParameter.map(_.toXHtml) openOr {S.error(mainMsgId,"Parameter not found"); redirectTo("/models/")}
  }

  def visualizeSelectedFunctionDefinition(ns:NodeSeq):NodeSeq = {
    debug("VISUALIZE SELECTED Function Definition")
    loadFunctionDefinitionFromPathParam()
    debug("Loaded selectedFunctionDefinition= " + selectedFunctionDefinition.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").toXML )
    selectedFunctionDefinition.map(_.toXHtml) openOr {S.error(mainMsgId,"Function Definition not found"); redirectTo("/models/")}
  }

  def visualizeSelectedConstraint(ns:NodeSeq):NodeSeq = {
    debug("VISUALIZE SELECTED Constraint")
    loadConstraintFromPathParam()
    debug("Loaded selectedConstraint= " + selectedConstraint.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").toXML )
    selectedConstraint.map(_.toXHtml) openOr {S.error(mainMsgId,"Constraint not found"); redirectTo("/models/")}
  }

  def visualizeSelectedReaction(ns:NodeSeq):NodeSeq = {
    debug("VISUALIZE SELECTED Reaction")
    loadReactionFromPathParam()
    debug("Loaded selectedReaction= " + selectedReaction.openOrThrowException("TODO: replacement for usage of deprecated openTheBox method").toXML )
    selectedReaction.map(_.toXHtml) openOr {S.error(mainMsgId,"Reaction not found"); redirectTo("/models/")}
  }


  //### delete state ###

  def confirmAndDeleteSelectedModel(ns:NodeSeq):NodeSeq = {
    debug("CONFIRM DELETE SELECTED MODEL")
    val model = new SBMLModelRecord
    model.metaIdO(S.param("modelMetaId").get)
    def deleteModel():NodeSeq = {
        model.deleteRestRec() match {
          case full:Full[_] => S.notice(mainMsgId, "Model "+ model.metaIdO.get +" deleted")
          case fail:Failure => S.error(mainMsgId, fail.msg)
          case Empty => S.error(mainMsgId, "THIS IS A BUG")
        }
        redirectTo("/models/")
    }
    // bind the incoming XHTML to a "delete" button.
    // when the delete button is pressed, call the "deleteUser"
    // function (which is a closure and bound the "user" object
    // in the current content)
    bind("xmp", ns, "modelname " -> (model.idO.get),
    "delete" -> submit("Delete", deleteModel ))

    // if the was no ID or the user couldn't be found,
    // display an error and redirect
  }

  def confirmAndDeleteSelectedCompartment(ns:NodeSeq):NodeSeq = {
    debug("CONFIRM DELETE SELECTED COMPARTMENT")
    val compartment = new CompartmentRecord
    compartment.metaIdO.set(S.param("compartmentMetaId").get)
    def deleteCompartment():NodeSeq = {
      compartment.deleteRestRec() match {
        case full:Full[_] => S.notice(mainMsgId, "Compartment "+ compartment.metaIdO.get +" deleted")
        case fail:Failure => S.error(mainMsgId, fail.msg)
        case Empty => S.error(mainMsgId, "THIS IS A BUG")
      }
      redirectTo("/model/" + S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") )
    }
    // bind the incoming XHTML to a "delete" button.
    // when the delete button is pressed, call the "deleteUser"
    // function (which is a closure and bound the "user" object
    // in the current content)
    bind("xmp", ns, "compartmentname" -> (compartment.idO.get),
      "delete" -> submit("Delete", deleteCompartment ))

    // if the was no ID or the user couldn't be found,
    // display an error and redirect
  }

  def confirmAndDeleteSelectedSpecies(ns:NodeSeq):NodeSeq = {
    debug("CONFIRM DELETE SELECTED SPECIES")
    val species = new SpeciesRecord
    species.metaIdO.set( S.param("speciesMetaId").get)
    def deleteSpecies():NodeSeq = {
      species.deleteRestRec() match {
        case full:Full[_] => S.notice(mainMsgId, "Species "+ species.metaIdO.get +" deleted")
        case fail:Failure => S.error(mainMsgId, fail.msg)
        case Empty => S.error(mainMsgId, "THIS IS A BUG")
      }
      redirectTo("/model/" + S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") )
    }
    // bind the incoming XHTML to a "delete" button.
    // when the delete button is pressed, call the "deleteUser"
    // function (which is a closure and bound the "user" object
    // in the current content)
    bind("xmp", ns, "speciesname" -> (species.idO.get),
      "delete" -> submit("Delete", deleteSpecies ))

    // if the was no ID or the user couldn't be found,
    // display an error and redirect
  }

  def confirmAndDeleteSelectedParameter(ns:NodeSeq):NodeSeq = {
    debug("CONFIRM DELETE SELECTED Parameter")
    val parameter = new ParameterRecord
    parameter.metaIdO.set(S.param("parameterMetaId").get)
    def deleteParameter():NodeSeq = {
      parameter.deleteRestRec() match {
        case full:Full[_] => S.notice(mainMsgId, "Parameter "+ parameter.metaIdO.get +" deleted")
        case fail:Failure => S.error(mainMsgId, fail.msg)
        case Empty => S.error(mainMsgId, "THIS IS A BUG")
      }
      redirectTo("/model/" + S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") )
    }
    // bind the incoming XHTML to a "delete" button.
    // when the delete button is pressed, call the "deleteUser"
    // function (which is a closure and bound the "user" object
    // in the current content)
    bind("xmp", ns, "parametername" -> (parameter.idO.get),
      "delete" -> submit("Delete", deleteParameter ))

    // if the was no ID or the user couldn't be found,
    // display an error and redirect
  }

  def confirmAndDeleteSelectedFunctionDefinition(ns:NodeSeq):NodeSeq = {
    debug("CONFIRM DELETE SELECTED FunctionDefinition")
    val functionDefinition = new FunctionDefinitionRecord
    functionDefinition.metaIdO.set(S.param("functionDefinitionMetaId").get)
    def deleteFunctionDefinition():NodeSeq = {
      functionDefinition.deleteRestRec() match {
        case full:Full[_] => S.notice(mainMsgId, "FunctionDefinition "+ functionDefinition.metaIdO.get +" deleted")
        case fail:Failure => S.error(mainMsgId, fail.msg)
        case Empty => S.error(mainMsgId, "THIS IS A BUG")
      }
      redirectTo("/model/" + S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") )
    }
    // bind the incoming XHTML to a "delete" button.
    // when the delete button is pressed, call the "deleteUser"
    // function (which is a closure and bound the "user" object
    // in the current content)
    bind("xmp", ns, "functiondefinitionname" -> (functionDefinition.idO.get),
      "delete" -> submit("Delete", deleteFunctionDefinition ))

    // if the was no ID or the user couldn't be found,
    // display an error and redirect
  }

  def confirmAndDeleteSelectedConstraint(ns:NodeSeq):NodeSeq = {
    debug("CONFIRM DELETE SELECTED Constraint")
    val constraint = new ConstraintRecord
    constraint.metaIdO.set(S.param("constraintMetaId").get)
    def deleteConstraint():NodeSeq = {
      constraint.deleteRestRec() match {
        case full:Full[_] => S.notice(mainMsgId, "Constraint "+ constraint.metaIdO.get +" deleted")
        case fail:Failure => S.error(mainMsgId, fail.msg)
        case Empty => S.error(mainMsgId, "THIS IS A BUG")
      }
      redirectTo("/model/" + S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") )
    }
    // bind the incoming XHTML to a "delete" button.
    // when the delete button is pressed, call the "deleteUser"
    // function (which is a closure and bound the "user" object
    // in the current content)
    bind("xmp", ns, "constraintname" -> (constraint.idO.get),
      "delete" -> submit("Delete", deleteConstraint ))

    // if the was no ID or the user couldn't be found,
    // display an error and redirect
  }

  def confirmAndDeleteSelectedReaction(ns:NodeSeq):NodeSeq = {
    debug("CONFIRM DELETE SELECTED Reaction")
    val reaction = new ReactionRecord
    reaction.metaIdO.set(S.param("reactionMetaId").get)
    def deleteReaction():NodeSeq = {
      reaction.deleteRestRec() match {
        case full:Full[_] => S.notice(mainMsgId, "Reaction "+ reaction.metaIdO.get +" deleted")
        case fail:Failure => S.error(mainMsgId, fail.msg)
        case Empty => S.error(mainMsgId, "THIS IS A BUG")
      }
      redirectTo("/model/" + S.param("modelMetaId").openOrThrowException("TODO: replacement for usage of deprecated openTheBox method") )
    }
    // bind the incoming XHTML to a "delete" button.
    // when the delete button is pressed, call the "deleteUser"
    // function (which is a closure and bound the "user" object
    // in the current content)
    bind("xmp", ns, "reactionname" -> (reaction.idO.get),
      "delete" -> submit("Delete", deleteReaction ))

    // if the was no ID or the user couldn't be found,
    // display an error and redirect
  }
}
object SBMLForm{
  object reqvar extends RequestVar[Int](0)
  object sessionvar extends SessionVar[Int](0)

    /**
   * Defines teh possible states of the CreateEdit page in WikiModels
   */
  sealed class CreateEditPageState(val state: String)

  case object EnterPage extends CreateEditPageState("EnterPage")

  case object Create extends CreateEditPageState("Create")

  case object CreateWithErrors extends CreateEditPageState("CreateWithErrors")

  case object Edit extends CreateEditPageState("Edit")

  case object EditWithErrors extends CreateEditPageState("EditWithErros")

  case object Visualize extends CreateEditPageState("Visualize")

  case object GoToBrowseModelsPage extends CreateEditPageState("GoToBrowseModelsPage")

  case class AnotherState(override val state: String) extends CreateEditPageState(state)
}


