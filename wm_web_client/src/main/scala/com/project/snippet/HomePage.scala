/*
 * HomePage.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.project.snippet

import scala.xml._
import net.liftweb.http._
import net.liftweb.util._
import S._
import SHtml._
import Helpers._
import scala.xml._

import com.project.model._

import java.util.Date

class HomePage {
  val formatter = new java.text.SimpleDateFormat("yyyy/MM/dd")

  def summary (xhtml : NodeSeq) : NodeSeq = {
    <lift:embed what="welcome_msg" />
  }
}
