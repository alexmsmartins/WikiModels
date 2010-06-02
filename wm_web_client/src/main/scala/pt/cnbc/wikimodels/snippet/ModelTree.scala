/*
 * ModelTree.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.snippet

import scala.xml._
import net.liftweb.http.js._
import JE._
import JsCmds._
import net.liftweb.widgets.tree._

class ModelTree {

  def render(xhtml: Group): NodeSeq = {
    TreeView("model_tree", JsObj(("animated" -> 150)))
  }
  def render2(xhtml: Group): NodeSeq = {
    TreeView("model_tree_model", JsObj(("animated" -> 150)))
  }
  def render3(xhtml: Group): NodeSeq = {
    TreeView("model_tree_overview", JsObj(("animated" -> 150)))
  }
  def render4(xhtml: Group): NodeSeq = {
    TreeView("model_tree_math", JsObj(("animated" -> 150)))
  }
  def render5(xhtml: Group): NodeSeq = {
    TreeView("model_tree_physical", JsObj(("animated" -> 150)))
  }
  def render6(xhtml: Group): NodeSeq = {
    TreeView("model_tree_parameter", JsObj(("animated" -> 150)))
  }
  def renderC1(xhtml: Group): NodeSeq = {
    TreeView("model_tree_Reac", JsObj(("animated" -> 150)))
  }
  def renderC2(xhtml: Group): NodeSeq = {
    TreeView("model_tree_Comp", JsObj(("animated" -> 150)))
  }
  def renderC3(xhtml: Group): NodeSeq = {
    TreeView("model_tree_Spec", JsObj(("animated" -> 150)))
  }
  def renderC4(xhtml: Group): NodeSeq = {
    TreeView("model_tree_funDef", JsObj(("animated" -> 150)))
  }
  def renderC5(xhtml: Group): NodeSeq = {
    TreeView("model_tree_Par", JsObj(("animated" -> 150)))
  }
  def renderC6(xhtml: Group): NodeSeq = {
    TreeView("model_tree_Cons", JsObj(("animated" -> 150)))
  }

  def renderComment(xhtml: Group): NodeSeq = {
    TreeView("model_tree_comment", JsObj(("animated" -> 150)))
  }
}
