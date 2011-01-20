package pt.cnbc.wikimodels.tabs

import _root_.scala.xml._
import _root_.net.liftweb.util.Helpers._
import _root_.net.liftweb.http.S._
import _root_.net.liftweb.http.LiftRules
import _root_.net.liftweb.http.{LiftResponse, JsonResponse}
import _root_.net.liftweb.http.js._
import _root_.net.liftweb.http.js.jquery._
import JsCmds._
import JE._
import JqJsCmds._
import JqJE._
import net.liftweb.common._

object TabsView {

  def apply(id: String, jsObj: JsObj) = new TabsView().onLoad(id, jsObj)

  /**
   * Call this function typically in boot
   */
  def init() {
    import _root_.net.liftweb.http.ResourceServer
    ResourceServer.allow({
      case "tabs" :: _ => true
    })

  }
}

class TabsView {

  /**
   * Makes a static tree out of the <ul><li> lists. The tree is buid when page loads.
   *
   * @param id - the id of the empty <ul> element that will be populated with the tree
   * @param jsObj - the JSON object passed to the treeview function
   *
   */
  def onLoad(id: String, jsObj: JsObj) : NodeSeq = {
    <head>
      <link rel="stylesheet" href={"/" + LiftRules.resourceServerPath + "/tabs/css/ui-lightness/jquery-ui-1.7.2.custom.css"} type="text/css"/>
      <script type="text/javascript" src={"/" + LiftRules.resourceServerPath + "/tabs/js/jquery-ui-1.7.2.custom.min.js"}/>
       <script type="text/javascript" charset="utf-8">{
         OnLoad(JqId(id) >> new JsExp with JsMember {
           def toJsCmd = "tabs(" + jsObj.toJsCmd + ")"
         }) toJsCmd
       }
       </script>
    </head>

  }

}

object Tab {
  def apply(text:String) = new Tab(text, Empty, Empty, false, false, Nil)
  def apply(text:String, id: String, hasChildren: Boolean) = new Tab(text, Full(id), Empty, false, true, Nil)
  def apply(text:String, classes: String) = new Tab(text, Empty, Full(classes), false, false, Nil)
  def apply(text:String, children: List[Tab]) = new Tab(text, Empty, Empty, false, false, children)
  def apply(text:String, classes: String, children: List[Tab]) = new Tab(text, Empty, Full(classes), false, false, children)
  def apply(text:String, classes: String, expanded: Boolean, hasChildren: Boolean, children: List[Tab]) =
    new Tab(text, Empty, Full(classes), expanded, hasChildren, children)
  def apply(text:String, id: String, classes: String, expanded: Boolean, hasChildren: Boolean, children: List[Tab]) =
    new Tab(text, Full(id), Full(classes), expanded, hasChildren, children)

  def toJSON(nodes: List[Tab]): String = nodes.map(_ toJSON).mkString("[", ", ", "]")
}

/**
 * Server side representation of a node of the tab widget
 */
case class Tab(text:String,
                id: Box[String],
                classes: Box[String],
                expanded: Boolean,
                hasChildren: Boolean,
                children: List[Tab]) {

  def toJSON: String = {

      "{ \"text\": \"" + text + "\"" +
        id.map(id => ", \"id\": \"" + id + "\"").openOr("") +
        classes.map(cls => ", \"classes\": \"" + cls + "\"").openOr("") +
        (hasChildren match { case true => ", \"hasChildren\": true" case _ =>  ""}) +
        (expanded match { case true => ", \"expanded\": true" case _ =>  ""}) +
        (children match {
          case Nil => ""
          case childs => ", \"children\": " + childs.map(_ toJSON).mkString("[", ", ", "]")
        }) +
        " }"
  }

}

