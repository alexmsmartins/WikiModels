package pt.cnbc.wikimodels.snippet

import _root_.net.liftweb.http._
import _root_.net.liftweb.http.SHtml._
import _root_.net.liftweb.http.js.jquery._
import _root_.net.liftweb.http.js.jquery.JqJsCmds._
import _root_.net.liftweb.http.js.JsCommands._
import _root_.net.liftweb.common._
import _root_.scala.xml.{Text, NodeSeq}
import _root_.net.liftweb.util.Helpers._

class Ajax{

  def sample(xhtml: NodeSeq): NodeSeq = {
    // local state for the counter
    var cnt = 0

    // get the id of some elements to update
    val spanName: String = S.attr("id_name") openOr "cnt_id"
    val msgName: String = S.attr("id_msgs") openOr "messages"


    // create an ajax select box
    def doSelect(msg: NodeSeq) =
    ajaxSelect((1 to 50).toList.map(i => (i.toString, i.toString)),
               Full(1.toString),
               v => DisplayMessage(msgName,
                                   bind("sel", msg, "number" -> Text(v)),
                                   5 seconds, 1 second))

    // build up an ajax text box
    def doText(msg: NodeSeq) =
    ajaxText("", v => DisplayMessage(msgName,
                                     bind("text", msg, "value" -> Text(v)),
                                     4 seconds, 1 second))



    // bind the view to the functionality
    bind("ajax", xhtml,
         "select" -> doSelect _,
         "text" -> doText _)
  }

  private def buildQuery(current: String, limit: Int): Seq[String] = {
    (1 to limit).map(n => current+""+n)
  }

  def time = Text(timeNow.toString)
}