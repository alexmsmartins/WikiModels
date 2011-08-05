package pt.cnbc.wikimodels.client.snippet

import xml.NodeSeq
import javax.mail.Session
import pt.cnbc.wikimodels.client.model.SBMLModelRecord
import net.liftweb.http._
import org.sbml.libsbml.SBMLNamespaces

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


  var somevar: Int = 0


  def dispatch: DispatchIt = {
    case "createModel" => createModele _
  }

  def createModele(ns: NodeSeq): NodeSeq = {
    {
      <h1>CreateModel</h1>
      <p>State of page: {SBMLFormOp.stateOfPage.is}</p>
    }
  }
}
object SBMLFormOp{
  object stateOfPage extends RequestVar[SBMLFormState](Create)

  /**
   * Defines teh possible states of the CreateEdit page in WikiModels
   */
  sealed class SBMLFormState(state: String)

  case object EnterPage extends SBMLFormState("EnterPage")

  case object Create extends SBMLFormState("Create")

  case object CreateWithErrors extends SBMLFormState("CreateWithErrors")

  case object Edit extends SBMLFormState("Edit")

  case object EditWithErrors extends SBMLFormState("EditWithErros")

  case object Visualize extends SBMLFormState("Visualize")

  case object GoToBrowseModelsPage extends SBMLFormState("GoToBrowseModelsPage")
}
