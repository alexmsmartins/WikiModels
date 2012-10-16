/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.client.snippet

import net.liftweb.common._

import net.liftweb.http._
import S._
import js._

import net.liftweb.util._
import Helpers._

import scala.xml._

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 10/10/12
  *         Time: 2:35 PM */
class ValidateMathSnip {
  def functionDefinition =
      "#valid_funct_def [onclick]" #> {SHtml.ajaxInvoke( () => {
        JsCmds.Alert("Validation of math goes here")/*
      })._2.cmd.toJsCmd + "return false;"*/
   })}
}
