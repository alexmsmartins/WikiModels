/*
 * Menu.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.snippet

import _root_.scala.xml.NodeSeq
import _root_.net.liftweb.widgets.menu._

class MenuSnippet {

  def render(xhtml: NodeSeq) :NodeSeq = {
      MenuWidget()
  }
}
