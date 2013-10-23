package pt.cnbc.wikimodels.client.snippet

import scala.xml.NodeSeq
import net.liftweb._
import http._
import SHtml._
import js._
import JsCmds._
import JE._
import util._
import Helpers._

class HelloWorld {
  def button(in: NodeSeq) =
  SHtml.ajaxButton(in,
             () => JsRaw("$.popup.show('The title', 'A nice ');").cmd)
 /*"+(
             () => JsRaw("$.blockUI({ message: "+(
        <h1>
          Do you really want to destroy Rhode Island?
          {
            SHtml.ajaxButton("yes", () => {println("Rhode Island Destroyed"); JsRaw("$.unblockUI();").cmd})
          }
          <button onclick="$.unblockUI()">No</button>
        </h1>).toString.encJs+
                         " });").cmd)*/
}

