/*
 * ModelTab.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.snippet

import scala.xml._
import net.liftweb.http.js._
import JE._
import JsCmds._
import pt.cnbc.wikimodels.tabs.TabsView

class ModelTab {
    def render(xhtml: Group): NodeSeq = {
        TabsView("model_tab", JsObj(("rotate" -> 2000)))
    }
}
