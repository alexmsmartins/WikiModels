/*
 * MenuSnippet.scala
 *
 *
 */

package pt.cnbc.wikimodels.client.snippet

import scala.xml.NodeSeq
import net.liftmodules.widgets.menu._

import net.liftweb.http.SHtml
import net.liftweb.http.js._
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.JE._
import net.liftweb.util.Helpers._

class MenuSnippet {

  def render(xhtml: NodeSeq) :NodeSeq = {
      MenuWidget()
  }

}
