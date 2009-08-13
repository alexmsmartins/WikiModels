/*
 * HomePage.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.snippet

import scala.xml._
import net.liftweb.http._
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.JE._
import net.liftweb.util._
import S._
import SHtml._
import Helpers._
import scala.xml._

class HomePage {
    def formatter = {
        val dateFormat = new java.text.SimpleDateFormat("dd MMM yyyy");
        val date = new java.util.Date();

        Text(dateFormat.format(date))
    }
    
    def logInfo (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
            bind("logged", xhtml,
                 "user" -> user)
        }
        case _ => {
            <div id="login">
                <lift:Submission.form form="post" >
                    <h1>Username <entry:username /> Password <entry:password /> <entry:submit /></h1>
                    <h1><a title="!Under Construction!" onclick="return false" href="createUser.html">Create User</a></h1>
                </lift:Submission.form>
            </div>
        }
    }

    def mainPage (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
            <div id='content'>
                <h1>Welcome to WikiModels Webpage</h1>
                <hr />
                <p>&nbsp;&nbsp;&nbsp;Welcome <b>{user}</b> to the WikiModels
            Webpage. Please, feel free to navigate through kinetic models and join
            this great biology community to create your own models or participate in
            a development team.</p>
            </div>
        }
        case _ => {
            <div id='content'>
                <h1>Welcome to WikiModels Webpage</h1>
                <hr />
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
