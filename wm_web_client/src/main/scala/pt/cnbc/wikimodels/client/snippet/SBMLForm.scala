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

/**
 * This snippet contains the forms and html to handle all the actions a user can perform on a SBMLModel
 * This includes creating a new model, visualizing an existing one, updating its associated information and deelting it
 * when it is no longer needed.
 * User: alex
 * Date: 01-08-2011
 * Time: 16:28
 */
class SBMLForm extends DispatchSnippet with LoggerWrapper {
  private object selectedModel extends RequestVar[Box[SBMLModelRecord]](Empty)
  private object selectedCompartment extends RequestVar[Box[CompartmentRecord]](Empty)

  private def loadSBMLFromPathParam() = {
    trace("Calling SBMLForm.loadSBMLFromPathParam")
    debug("""Parameter "modelMetaId" « {} """, S.param("modelMetaId").openTheBox)

    selectedModel {
      SBMLModelRecord.readRestRec(debug("The modelMetaId in session after calling /model/modemetaid is: {}", S.param("modelMetaId").openTheBox))
    }
    debug("Loaded model into session: {}", selectedModel.get.openTheBox.toXML )
    debug("Loaded model into session with metaid {}, id {} and name {}",
      selectedModel.get.openTheBox.metaid,
      selectedModel.get.openTheBox.id,
      selectedModel.get.openTheBox.name)
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
  }

  //### Create state

  def saveNewModel(model:SBMLModelRecord):Unit = {
    info("SAVE NEW MODEL with xml {}", model.toXML())
    //metaid is never presented and, by default, we give it the same value as id
    model.metaIdO.set(model.id)
    model.validate match {
      case Nil => { //no validation errors
        model.metaid = model.id
        val metaid = model.createRestRec().openTheBox.metaid
        redirectTo("/model/" + metaid) //TODO: handle failure in the server (maybe this should be general)
      }
      case x => S.error(x); selectedModel(Full(model))
    }
  }

  def createNewModel(ns:NodeSeq):NodeSeq = {
    info("CREATE SELECTED MODEL")
    selectedModel.is.openOr(SBMLModelRecord.createRecord)
      .toForm(Empty)(saveNewModel _) ++ <tr>
      <td><a href="/models">Cancel</a></td>
      <td><input type="submit" name="create" value="Create"/></td>
    </tr>
  }

  def saveNewCompartment(compartment:CompartmentRecord):Unit = {
    info("SAVE NEW COMPARTMENT with xml {}", compartment.toXML)
    //metaid is never presented and, by default, we give it the same value as id
    compartment.metaIdO.set(compartment.id)
    compartment.validate match {
      case Nil => { //no validation errors
        compartment.metaid = compartment.id
        compartment.createRestRec() match {
          case Full(x) => {
            val metaid = compartment.createRestRec().openTheBox.metaid
            redirectTo(compartment.relativeURL) //TODO: handle failure in the server (maybe this should be general)
          }
          case Empty => {
            S.error("Strange error. Report this bug please!")
            selectedCompartment(Full(compartment))
          }
          case x:ParamFailure[_] => {
            S.error(x.messageChain) //TODO check if message chain is the right thing to send to S.error
            selectedCompartment(Full(compartment))
          }
          case x:Failure => {
            S.error(x.messageChain)
           selectedCompartment(Full(compartment))
          }
        }
      }
      case x => S.error(x); selectedCompartment(Full(compartment))
    }
  }

  def createNewCompartment(ns:NodeSeq):NodeSeq = {
    info("CREATE SELECTED COMPARTMENT")
    selectedCompartment.is.openOr(CompartmentRecord.createRecord)
      .toForm(Empty)(saveNewCompartment _) ++ <tr>
      <td><a href="/models">Cancel</a></td>
      <td><input type="submit" name="create" value="Create"/></td>
    </tr>
  }

  //### Edit state

  def saveSelectedModel(model:SBMLModelRecord):Unit = {
    debug("SAVE SELECTED MODEL")
    model.validate match {
      case Nil => model.updateRestRec(); redirectTo("/model/" + model.metaid) //TODO: handle the possibility that the server does not accept the change (maybe this should be general)
      case x => S.error(x); selectedModel(Full(model))
    }
  }

  def editSelectedModel(ns:NodeSeq):NodeSeq ={
    debug("EDIT SELECTED MODEL")
    loadSBMLFromPathParam
    selectedModel.is.map( _.toForm(Empty)( saveSelectedModel _ ) ++ <tr>
                                      <td><a href="/models">Cancel</a></td>
                                      <td><input type="submit" value="Save"/></td>
                                    </tr>
    ) openOr {error("Model not found"); redirectTo("/models")}
  }

  def saveSelectedCompartment(compartment:CompartmentRecord):Unit = {
    debug("SAVE SELECTED COMPARTMENT")
    compartment.validate match {
      case Nil => compartment.updateRestRec(); redirectTo("/compartment/" + compartment.metaid) //TODO: handle the possibility that the server does not accept the change (maybe this should be general)
      case x => S.error(x); selectedCompartment(Full(compartment))
    }
  }

  def editSelectedCompartment(ns:NodeSeq):NodeSeq ={
    debug("EDIT SELECTED COMPARTMENT")
    loadSBMLFromPathParam
    selectedCompartment.is.map( _.toForm(Empty)( saveSelectedCompartment _ ) ++ <tr>
      <td><a href="/models">Cancel</a></td>
      <td><input type="submit" value="Save"/></td>
    </tr>
    ) openOr {error("Compartment not found"); redirectTo("/models")}
  }

  //### Visualize state
   case class ParamModelMetaIDInfo(theMetaId:String)
   //TODO val menu = Menu.param[ParamModelMetaIDInfo]("model", "model", s => Full(s),encoder: T => String)

  def visualizeSelectedModel(ns:NodeSeq):NodeSeq = {
    debug("VISUALIZE SELECTED MODEL")
    loadSBMLFromPathParam
    debug("Loaded selectedModel = " + selectedModel.openTheBox.toXML )
    selectedModel.map(_.toXHtml) openOr {S.error("Model not found"); redirectTo("/models/")}
  }

  def visualizeSelectedCompartment(ns:NodeSeq):NodeSeq = {
    debug("VISUALIZE SELECTED MODEL")
    loadSBMLFromPathParam
    debug("Loaded selectedModel = " + selectedCompartment.openTheBox.toXML )
    selectedCompartment.map(_.toXHtml) openOr {S.error("Compartment not found"); redirectTo("/models/")}
  }

  //### delete state

  def confirmAndDeleteSelectedModel(ns:NodeSeq):NodeSeq = {
    debug("CONFIRM DELETE SELECTED MODEL")
    (for(model <- selectedModel.is) yield {
       def deleteModel():NodeSeq = {
        notice("Model "+ model.metaid +" deleted")
        model.deleteRestRec()
        redirectTo("/models/")
      }
      // bind the incoming XHTML to a "delete" button.
      // when the delete button is pressed, call the "deleteUser"
      // function (which is a closure and bound the "user" object
      // in the current content)
      bind("xmp", ns, "Model " -> (model.metaid),
      "delete" -> submit("Delete", deleteModel ))

      // if the was no ID or the user couldn't be found,
      // display an error and redirect
    }) openOr {S.error("Model not found"); redirectTo("/models/")}
  }

  def confirmAndDeleteSelectedCompartment(ns:NodeSeq):NodeSeq = {
    debug("CONFIRM DELETE SELECTED COMPARTMENT")
    (for(compartment <- selectedCompartment.is) yield {
      def deleteCompartment():NodeSeq = {
        notice("Compartment "+ compartment.metaid +" deleted")
        compartment.deleteRestRec()
        redirectTo("/models/")
      }
      // bind the incoming XHTML to a "delete" button.
      // when the delete button is pressed, call the "deleteUser"
      // function (which is a closure and bound the "user" object
      // in the current content)
      bind("xmp", ns, "Compartment " -> (compartment.metaid),
        "delete" -> submit("Delete", deleteCompartment ))

      // if the was no ID or the user couldn't be found,
      // display an error and redirect
    }) openOr {S.error("Compartment not found"); redirectTo("/models/")}
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

