/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.client.snippet

import net.liftweb.common._

import net.liftweb.http._
import S._
import js._
import js.JE._ //for implicit conversions
import js.JsCmds._

import net.liftweb.util._
import Helpers._

import scala.xml._

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 10/10/12
  *         Time: 2:35 PM */
class ValidateMathSnip {

  private def addString(s:String):JsCmd = {
    println("alert('" + s + "= 4')")
    JsRaw("alert('" + s + "= 4')")
  }

  def functionDefinition =
      "#valid_funct_def [onclick]" #> {
        SHtml.ajaxCall(JsRaw("2+2"), addString )
      }
}
