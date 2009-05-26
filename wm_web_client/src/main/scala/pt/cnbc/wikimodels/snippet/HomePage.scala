/*
 * HomePage.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.snippet

import scala.xml._
import net.liftweb.http._
import net.liftweb.util._
import S._
import SHtml._
import Helpers._
import scala.xml._

import pt.cnbc.wikimodels.model._

import java.util.Date

class HomePage {
    val formatter = new java.text.SimpleDateFormat("yyyy/MM/dd")
    
    def summary (xhtml : NodeSeq) : NodeSeq = {
        <lift:embed what="welcome_msg" />
    }
    
    def howdy = Text(User.get +"'s profile")

}
