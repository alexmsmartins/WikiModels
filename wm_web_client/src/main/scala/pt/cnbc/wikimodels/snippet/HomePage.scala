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
import net.liftweb.http.js._
import net.liftweb.util._
import S._
import SHtml._
import Helpers._
import scala.xml._
import net.liftweb.common._

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
                    <lift:LogUser.form form="post" >
                        <h1>Username <entry:username /> Password <entry:password /> <entry:submit /></h1>
                        <h1><a title="Create a new user" href="create_user">Create User</a></h1>
                    </lift:LogUser.form>
                </div>
            }
    }
    

    def mainPage (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
                <div id='content'>
                    <h1>Welcome to WikiModels Webpage</h1>
                    <hr />
                    <p>&nbsp;&nbsp;&nbsp;<b>{user}</b>, welcome to WikiModels! A web-based platform
                        for collaborative development of kinetic models of biochemical processes.
                        Please feel free to browse and comment the available models.</p>
                    <p>&nbsp;</p>
                    <p>&nbsp;</p>
                    <p>&nbsp;</p>
                    <p>&nbsp;</p>
                    <p>&nbsp;</p>
                    <p>&nbsp;</p>
                </div>
            }
        case _ => {
                <div id='content'>
                    <h1>Welcome to WikiModels Webpage</h1>
                    <hr />
                    <p>&nbsp;&nbsp;&nbsp;<b>visitor</b>, welcome to WikiModels! A web-based platform
                        for collaborative development of kinetic models of biochemical processes.
                        Please feel free to browse and comment the available models.</p>
                    <p>&nbsp;</p>
                    <p>&nbsp;</p>
                    <p>&nbsp;</p>
                    <p>&nbsp;</p>
                    <p>&nbsp;</p>
                    <p>&nbsp;</p>
                </div>
            }
            
    }
    
    def profile = User.currentUserName match {
        case Full(user) => {<h1><a title="See profile" href="./profile_user">{user}'s profile</a></h1>}
        case _ => Nil
    }

}
