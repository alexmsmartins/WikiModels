package pt.cnbc.wikimodels.snippet

import net.liftweb._
import http._
import util._
import Helpers._
import sitemap.Loc.If
import scala.xml.{ XML, Elem, Group, Node, NodeSeq, Null, Text, TopScope }
import S._
import java.net.URI
import net.liftweb.http.js._
import js.JsCmds
import js.JsCmds._
import js.Jx
import js.JE
import js.JE._
import js.jquery._
import JqJsCmds._
import java.util.Hashtable
import java.util.Enumeration
import net.liftweb.common._

class DeleteModel {
    def button(in: NodeSeq) =
    SHtml.ajaxButton(in,
                     () => S.runTemplate(List("_jsdialog_confirm")).
                     map(ns => ModalDialog(ns)) openOr
                     Alert("Couldn't find _jsdialog_confirm template"))

    // the template needs to bind to either server-side behavior
    // and unblock the UI
    def confirm(in: NodeSeq) =
    bind("confirm", in,
         "yes" -> ((b: NodeSeq) => SHtml.ajaxButton(b, () =>
                {println("Rhode Island Destroyed")
                 Unblock & Alert("Rhode Island Destroyed")})),
         "no" -> ((b: NodeSeq) => <button onclick={Unblock.toJsCmd}>{b}</button>))
}
