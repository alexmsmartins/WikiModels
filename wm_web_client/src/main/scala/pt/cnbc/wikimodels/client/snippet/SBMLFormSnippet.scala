package pt.cnbc.wikimodels.client.snippet

import xml.NodeSeq
import javax.mail.Session
import pt.cnbc.wikimodels.client.model.SBMLModelRecord
import net.liftweb.http._

// This is the common usage case of StatefulSnippet

/**
 * This snippet contains the forms and html to handle all the actions a user can perform on a SBMLModel
 * This includes creating a new model, visualizing an existing one, updating its associated information and deelting it
 * when it is no longer needed.
 * User: alex
 * Date: 01-08-2011
 * Time: 16:28
 */
class SBMLForm extends DispatchSnippet {

  object modelState extends SessionVar[CreateEditPageState](EnterPage)

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

  var somevar: Int = 0


  def dispatch: DispatchIt = {
    case "createModel" => createModele
  }

  def createModele(ns: NodeSeq): NodeSeq = {
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
}


