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

  private def loadSBMLModelFromPathParam() = {
    trace("calling SBMLForm.loadSBMLModelFromPathParam")
    debug("""Parameter "modelMetaId" « {} """, S.param("modelMetaId").openTheBox)

    selectedModel {
      SBMLModelRecord.readRestRec(debug("The modelMetaId in session after calling /model/modemetaid is: {}", S.param("modelMetaId").openTheBox))
    }
    debug("Loaded model into session: {}", selectedModel.get.openTheBox.toXML )
    debug("Loaded model into session with metaid {}, id {} and name {}",
      selectedModel.get.openTheBox.metaid,
      selectedModel.get.openTheBox.id,
      selectedModel.get.openTheBox.name)
    selectedModel.is
  }

  private def loadCompartmentFromPathParam() = {
    trace("Calling SBMLForm.loadCompartmentFromPathParam")
    debug("""Parameter "compartmentMetaId" « {} """, S.param("compartmentMetaId").openTheBox)

    selectedCompartment {
      CompartmentRecord.readRestRec(debug("The compartmentMetaId in session after calling /model/modemetaid/compartment/compartmentMetaId is: {}", S.param("compartmentMetaId").openTheBox))
    }

    //TODO this might be optimizable by turning the parent field into a lazy field and the interface buttons that need it into ajax buttons
    selectedModel {
      SBMLModelRecord.readRestRec(debug("The modelMetaId in session after calling /model/modemetaid is: {}", S.param("modelMetaId").openTheBox))
    }
    selectedCompartment.get.openTheBox.parent = selectedModel.get
    debug("Loaded compartment into session: {}", selectedCompartment.get.openTheBox.toXML )
    debug("Loaded model into session with metaid {}, id {} and name {}",
      selectedCompartment.get.openTheBox.metaid,
      selectedCompartment.get.openTheBox.id,
      selectedCompartment.get.openTheBox.name)
    selectedCompartment.is
  }

  private def loadSpeciesFromPathParam() = {
    trace("Calling SBMLForm.loadSpeciesFromPathParam")
    debug("""Parameter "speciesMetaId" « {} """, S.param("speciesMetaId").openTheBox)

    selectedSpecies {
      SpeciesRecord.readRestRec(debug("The speciesMetaId in session after calling /model/modemetaid/species/speciesMetaId is: {}", S.param("speciesMetaId").openTheBox))
    }

    //TODO this might be optimizable by turning the parent field into a lazy field and the interface buttons that need it into ajax buttons
    selectedModel {
      SBMLModelRecord.readRestRec(debug("The modelMetaId in session after calling /model/modemetaid is: {}", S.param("modelMetaId").openTheBox))
    }
    selectedSpecies.get.openTheBox.parent = selectedModel.get
    debug("Loaded species into session: {}", selectedSpecies.get.openTheBox.toXML )
    debug("Loaded model into session with metaid {}, id {} and name {}",
      selectedSpecies.get.openTheBox.metaid,
      selectedSpecies.get.openTheBox.id,
      selectedSpecies.get.openTheBox.name)
    selectedSpecies.is
  }

  def dispatch: DispatchIt = {
    case "createModel" => createNewModel
    case "editModel" => editSelectedModel
    case "visualizeModel" => visualizeSelectedModel
    case "deleteModel" => confirmAndDeleteSelectedModel
    case "createCompartment" => createNewCompartment
    case "editCompartment" => editSelectedCompartment
    case "visualizeCompartment" => visualizeSelectedCompartment
    case "deleteCompartment" => confirmAndDeleteSelectedCompartment
    case "createSpecies" => createNewSpecies
    case "editSpecies" => editSelectedSpecies
    case "visualizeSpecies" => visualizeSelectedSpecies
    case "deleteSpecies" => confirmAndDeleteSelectedSpecies

  }

  //### Create state

  def saveNewModel(model:SBMLModelRecord):Unit = {
    info("SAVE NEW MODEL with xml {}", model.toXML())
    //metaid is never presented and, by default, we give it the same value as id
    model.metaIdO.set(model.id)
    model.validate match {
      case Nil => { //no validation errors
        model.metaid = model.id
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
    compartment.metaIdO.set(compartment.id)
    compartment.validate match {
      case Nil => { //no validation errors
        compartment.metaid = compartment.id
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
    species.metaIdO.set(species.id)
    species.validate match {
      case Nil => { //no validation errors
        species.metaid = species.id
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

  //### Edit state

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

  //### Visualize state
   case class ParamModelMetaIDInfo(theMetaId:String)
   //TODO val menu = Menu.param[ParamModelMetaIDInfo]("model", "model", s => Full(s),encoder: T => String)

  def visualizeSelectedModel(ns:NodeSeq):NodeSeq = {
    debug("VISUALIZE SELECTED MODEL")
    loadSBMLModelFromPathParam
    debug("Loaded selectedModel = " + selectedModel.openTheBox.toXML )
    selectedModel.map(_.toXHtml) openOr {S.error(mainMsgId,"Model not found"); redirectTo("/models/")}
  }

  def visualizeSelectedCompartment(ns:NodeSeq):NodeSeq = {
    debug("VISUALIZE SELECTED COMPARTMENT")
    loadCompartmentFromPathParam
    debug("Loaded selectedCompartment = " + selectedCompartment.openTheBox.toXML )
    selectedCompartment.map(_.toXHtml) openOr {S.error(mainMsgId,"Compartment not found"); redirectTo("/models/")}
  }

  def visualizeSelectedSpecies(ns:NodeSeq):NodeSeq = {
    debug("VISUALIZE SELECTED SPECIES")
    loadSpeciesFromPathParam()
    debug("Loaded selectedSpecies= " + selectedSpecies.openTheBox.toXML )
    selectedSpecies.map(_.toXHtml) openOr {S.error(mainMsgId,"Species not found"); redirectTo("/models/")}
  }


  //### delete state

  def confirmAndDeleteSelectedModel(ns:NodeSeq):NodeSeq = {
    debug("CONFIRM DELETE SELECTED MODEL")
    val model = new SBMLModelRecord
    model.metaid = S.param("modelMetaId").get
    def deleteModel():NodeSeq = {
        model.deleteRestRec() match {
          case full:Full[_] => S.notice(mainMsgId, "Model "+ model.metaid +" deleted")
          case fail:Failure => S.error(mainMsgId, fail.msg)
          case Empty => S.error(mainMsgId, "THIS IS A BUG")
        }
        redirectTo("/models/")
    }
    // bind the incoming XHTML to a "delete" button.
    // when the delete button is pressed, call the "deleteUser"
    // function (which is a closure and bound the "user" object
    // in the current content)
    bind("xmp", ns, "modelname " -> (model.id),
    "delete" -> submit("Delete", deleteModel ))

    // if the was no ID or the user couldn't be found,
    // display an error and redirect
  }

  def confirmAndDeleteSelectedCompartment(ns:NodeSeq):NodeSeq = {
    debug("CONFIRM DELETE SELECTED COMPARTMENT")
    val compartment = new CompartmentRecord
    compartment.metaid = S.param("compartmentMetaId").get
    def deleteCompartment():NodeSeq = {
      compartment.deleteRestRec() match {
        case full:Full[_] => S.notice(mainMsgId, "Compartment "+ compartment.metaid +" deleted")
        case fail:Failure => S.error(mainMsgId, fail.msg)
        case Empty => S.error(mainMsgId, "THIS IS A BUG")
      }
      redirectTo("/model/" + S.param("modelMetaId").openTheBox )
    }
    // bind the incoming XHTML to a "delete" button.
    // when the delete button is pressed, call the "deleteUser"
    // function (which is a closure and bound the "user" object
    // in the current content)
    bind("xmp", ns, "compartmentname" -> (compartment.id),
      "delete" -> submit("Delete", deleteCompartment ))

    // if the was no ID or the user couldn't be found,
    // display an error and redirect
  }

  def confirmAndDeleteSelectedSpecies(ns:NodeSeq):NodeSeq = {
    debug("CONFIRM DELETE SELECTED SPECIES")
    val species = new SpeciesRecord
    species.metaid = S.param("speciesMetaId").get
    def deleteSpecies():NodeSeq = {
      species.deleteRestRec() match {
        case full:Full[_] => S.notice(mainMsgId, "Species "+ species.metaid +" deleted")
        case fail:Failure => S.error(mainMsgId, fail.msg)
        case Empty => S.error(mainMsgId, "THIS IS A BUG")
      }
      redirectTo("/model/" + S.param("modelMetaId").openTheBox )
    }
    // bind the incoming XHTML to a "delete" button.
    // when the delete button is pressed, call the "deleteUser"
    // function (which is a closure and bound the "user" object
    // in the current content)
    bind("xmp", ns, "speciesname" -> (species.id),
      "delete" -> submit("Delete", deleteSpecies ))

    // if the was no ID or the user couldn't be found,
    // display an error and redirect
  }

  var somevar: Int = 0

  def createModele(ns: NodeSeq): NodeSeq = {
    //TODO: delete this code when no longer necessary
    S.setHeader("aaaHeader", "valHeader")
    S.set("set","set")
    S.setSessionAttribute("sessionAttribute", "valSessionAttribute")
    S.getHeader("aaa").map(_.toInt).openOr("aaa")

    var rv = SBMLForm.reqvar.is
    SBMLForm.reqvar(rv + 1)
    var sv = SBMLForm.sessionvar.is
    SBMLForm.sessionvar(sv + 1)
    somevar = somevar + 1;
    {
      <h1>CreateModel</h1>
      <p>Paragraph of trial snippet</p>
      <p>object reqvar extends RequestVar(0)⁼= {SBMLForm.reqvar}
      </p>
      <p>object sessionvar extends SessionVar = {SBMLForm.sessionvar}
      </p>
      <p>var somevar =
        {somevar}
      </p>
    }
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


