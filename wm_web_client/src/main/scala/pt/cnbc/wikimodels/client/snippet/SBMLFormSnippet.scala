package pt.cnbc.wikimodels.client.snippet

import java.util.Date
import scala.xml._


import net.liftweb._
import common._
import util.Helpers._
import http._
import sitemap._
import mapper._
import S._
import SHtml._
import util._
import Helpers._

import pt.cnbc.wikimodels.client.model._

/**
 * This snippet contains the forms and html to handle all the actions a user can perform on a SBMLModel
 * This includes creating a new model, visualizing an existing one, updating its associated information and deelting it
 * when it is no longer needed.
 * User: alex
 * Date: 01-08-2011
 * Time: 16:28
 */
class SBMLForm extends DispatchSnippet {
  private object selectedModel extends RequestVar[Box[SBMLModelRecord]](Empty)

  def dispatch: DispatchIt = {
    case "createModel" => createNewModel
    case "editModel" => editSelectedModel
    case "visualizeModel" => visualizeSelectedModel
    case "deleteModel" => confirmAndDeleteSelectedModel
  }

  //### Create state

  def saveNewModel(model:SBMLModelRecord):Unit = {
    Console.println("SAVE NEW MODEL with xml " + model.toXML())
    model.validate match {
      case Nil => { //no validation errors
        model.metaid = model.id
        val metaid = model.createRestRec(); redirectTo("/model/" + metaid) //TODO: handle failure in the server (maybe this should be general)
      }
      case x => error(x); selectedModel(Full(model))
    }
  }

  def createNewModel(ns:NodeSeq):NodeSeq = {
    selectedModel.is.openOr(SBMLModelRecord.createRecord)
      .toForm(saveNewModel _) ++ <tr>
      <td><a href="/models">Cancel</a></td>
      <td><input type="submit" value="Create"/></td>
    </tr>
  }

  //### Edit state

  def saveSelectedModel(model:SBMLModelRecord):Unit = {
    Console.println("SAVE SELECTED MODEL")
    model.validate match {
      case Nil => model.updateRestRec(); redirectTo("/model/" + model.metaid) //TODO: handle the possibility that the server does not accept the change (maybe this should be general)
      case x => error(x); selectedModel(Full(model))
    }
  }

  def editSelectedModel(ns:NodeSeq):NodeSeq =
    selectedModel.is.openTheBox.toForm(saveSelectedModel _ ) ++ <tr>
                                      <td><a href="/simple/index.html">Cancel</a></td>
                                      <td><input type="submit" value="Save"/></td>
                                    </tr>

  //### Visualize state
   case class ParamModelMetaIDInfo(theMetaId:String)
   //TODO val menu = Menu.param[ParamModelMetaIDInfo]("model", "model", s => Full(s),encoder: T => String)

  def visualizeSelectedModel(ns:NodeSeq):NodeSeq = {
    Console.println("VISUALIZE SELECTED MODEL")
    selectedModel.map(_.toXHtml) openOr {error("Model not found"); redirectTo("/models/")}
  }

  //### delete state

  def confirmAndDeleteSelectedModel(ns:NodeSeq):NodeSeq = {
    Console.println("CONFIRM DELETE SELECTED MODEL")
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
    }) openOr {error("Model not found"); redirectTo("/models/")}
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


