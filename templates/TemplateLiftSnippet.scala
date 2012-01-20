package pt.cnbc.wikimodels.snippet

import net.liftweb.common._

import net.liftweb.http._
import S._

import net.liftweb.util._
import Helpers._

import scala.xml._

/** TODO Please document this snippet
 *  @author ${USER}
 *  Date: ${DATE}
 *  Time: ${TIME}  */
class TemplateSnippet extends DispatchSnippet {

  def dispatch: DispatchIt = {
    case "title" => render _
    case "content" => content _
  }

  def render(ns:NodeSeq):NodeSeq =
    <h1>Title</h1>

  def content(ns:NodeSeq):NodeSeq =
    <p>This is the content.</p>

}
