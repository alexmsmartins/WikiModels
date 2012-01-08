/*
 * LogUser.scala
 *
 * Class to submit the log of the user
 */

package pt.cnbc.wikimodels.snippet

import net.liftweb._
import http._
import util._
import Helpers._
import sitemap.Loc.If
import scala.xml.{ XML, Elem, Group, Node, NodeSeq, Null, Text, TopScope }
import S._
import net.liftweb.http.js._
import js.JsCmds
import js.JsCmds._
import js.Jx
import js.JE
import js.JE._
import js.jquery._
import JqJsCmds._
import net.liftweb.common._

import pt.cnbc.wikimodels.rest.client.BasicAuth
import pt.cnbc.wikimodels.rest.client.RestfulAccess
import alexmsmartins.log.LoggerWrapper
import pt.cnbc.wikimodels.controller.SMsg


class LogUser extends SMsg with LoggerWrapper {
    def form (xhtml : NodeSeq) : NodeSeq = {

        var username = ""
        var password = ""

        def authenticate () = {
            var ra:RestfulAccess = null
            if (username.trim.length == 0) {
                S.error(loginMsgId, "Username missing")
            } else if (password.length == 0) {
                S.error(loginMsgId, "Password missing")
            } else {
                //TODO: these constants related to the server URL should be refactored to a configuration file
                ra = new RestfulAccess("localhost", 8080, "/wm_server/resources", username, password, BasicAuth.startWithBasicAuth)
                val userXML = ra.getRequest("/user/"+username).asInstanceOf[scala.xml.Elem]
                debug("STATUS MESSAGE => "+ra.getStatusCode)
                if(ra.getStatusCode == 200){
                    User.UserName(Full(username))
                    User.restfulConnection = ra
                } else {
                    S.error(loginMsgId, "Error in username or password")
                }
            }
        }
        bind("entry", xhtml,
             "username" -> SHtml.text("", username = _, ("id","username"), ("size", "10"), ("maxlength", "40")),
             "password" -> SHtml.password("", password = _, ("id","password"), ("size", "10"), ("maxlength", "40")),
             "submit" -> SHtml.submit("Submit", authenticate,("id","login_button")))
    }
}
