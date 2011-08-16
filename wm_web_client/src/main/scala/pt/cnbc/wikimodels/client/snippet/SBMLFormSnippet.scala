package pt.cnbc.wikimodels.client.snippet

import xml.NodeSeq

import _root_.net.liftweb._
import http._
import mapper._
import S._
import SHtml._

import common._
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

  object modelState extends SessionVar[SBMLForm.CreateEditPageState](SBMLForm.EnterPage)

  var somevar: Int = 0

  def dispatch: DispatchIt = {
    case "createModel" => createNewModel
  }

  def saveModel(model:SBMLModelRecord):Unit = {
    model.validate match {
      case Nil => model.createRestRec(); redirectTo("/model/" + model.metaid) //TODO: maybe (hope not) this can be executed in parallel
      case x => error(x); selectedModel(Full(model))

    }
  }

  def createNewModel(ns:NodeSeq) =
    selectedModel.is.openOr(SBMLModelRecord()).toForm(saveModel _) ++ <tr>
      <td><a href="/models">Cancel</a></td>
      <td><input type="submit" value="Create"/></td>
      </tr>

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
        {this.somevar}
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
  sealed class CreateEditPageState(state: String)

  case object EnterPage extends CreateEditPageState("EnterPage")

  case object Create extends CreateEditPageState("Create")

  case object CreateWithErrors extends CreateEditPageState("CreateWithErrors")

  case object Edit extends CreateEditPageState("Edit")

  case object EditWithErrors extends CreateEditPageState("EditWithErros")

  case object Visualize extends CreateEditPageState("Visualize")

  case object GoToBrowseModelsPage extends CreateEditPageState("GoToBrowseModelsPage")

  case class AnotherState(_state: String) extends CreateEditPageState(_state)
}


