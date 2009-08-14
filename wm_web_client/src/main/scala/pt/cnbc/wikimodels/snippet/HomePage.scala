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

import java.util.Date

class HomePage {
    val formatter = new java.text.SimpleDateFormat("yyyy/MM/dd")
    
    def logInfo (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
            bind("logged", xhtml,
                 "user" -> user)
        }
        case _ => {
            <div id="login1">
                <lift:Submission.form form="post" >
                    <h3>Username <entry:username /> Password <entry:password /> <entry:submit /></h3>
                    <h3><a href="createUser.html">Create User</a></h3>
                </lift:Submission.form>
            </div>
        }
    }

    def mainPage (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
            <div id='content'>
                <p>&nbsp;</p>
                <h1>Welcome to WikiModels Webpage</h1>
                <p>&nbsp;</p>
                <p>&nbsp;&nbsp;&nbsp;Welcome <b>{user}</b> to the WikiModels
            Webpage. Please, feel free to navigate through kinetic models and join
            this great biology community to create your own models or participate in
            a development team.</p>
            </div>
        }
        case _ => {
            <div id='content'>
                <p>&nbsp;</p>
                <h1>Welcome to WikiModels Webpage</h1>
                <p>&nbsp;</p>
                <p>&nbsp;&nbsp;&nbsp;Welcome <b>visitor</b> to the WikiModels
            Webpage. Please, feel free to navigate through kinetic models and join
            this great biology community to create your own models or participate in
            a development team.</p>
            </div>
        }
    }
    
    def profile = User.currentUserName match {
        case Full(user) => Text(user +"'s profile")
            case _ => Nil
    }

}
