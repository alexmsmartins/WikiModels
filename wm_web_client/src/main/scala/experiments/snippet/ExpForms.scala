package experiments.snippet

import net.liftweb.common._

import net.liftweb.http._
import S._

import net.liftweb.util._
import Helpers._

import scala.xml._
import net.liftweb.record.Record

/**TODO: Please document.
 * @author Alexandre Martins
 * Date: 9/4/12
 * Time: 12:10 PM
 * Experiences with Lift snippets*/
class ExpForms extends DispatchSnippet {


  def dispatch: DispatchIt = {
    case "title" => render _
    case "content" => content _
    case "selectBox" => selectBox _
  }

  def render(ns: NodeSeq): NodeSeq =
    <h1>Title</h1>



  def content(ns: NodeSeq): NodeSeq =
    <p>This is the content.</p>


  val listOfOptions:List[(Box[Int], String)]=
    List((Empty, "nome"), (Full(1), "one"), (Full(2), "two"), (Full(3), "three"))



  object IntOption extends SessionVar[Box[Int]](Empty)

  def selectBox(ns: NodeSeq):NodeSeq ={
    import scala.xml._


    SHtml.selectObj(
      listOfOptions,
      Full(IntOption.get),
      (i:Box[Int]) => IntOption.set(i ),
      ("xxx" -> "yyy"))
  }
}
