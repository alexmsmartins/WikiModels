/*
 * ProfileUser.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.snippet

import net.liftweb.util.Helpers._
import net.liftweb.sitemap.Loc.If
import scala.xml.{ XML, Elem, Group, Node, NodeSeq, Null, Text, TopScope }
import net.liftweb.http.S
import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JsCmds.JsCrVar
import net.liftweb.http.js.Jx
import net.liftweb.http.js.JE._
import net.liftweb.common._
import pt.cnbc.wikimodels.model._

class ProfileUser {

  def viewUser (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
    case Full(user) => {
      //TODO replace the reference to userNew.xml by a proper call to WM Server

      var userRef = "userNew.xml"

      val userXML = XML.load(userRef)


      var username = (userXML \\ "user" \ "userName").text
      var firstName = (userXML \\ "user" \ "firstName").text
      var surName = (userXML \\ "user" \ "surName").text
      var title = ""
      if((userXML \\ "user" \ "title").text != null) {
          title = (userXML \\ "user" \ "title").text
      }
      var firstEmail = (userXML \\ "user" \ "email").text
      var alternativeEmail = ""
      if((userXML \\ "user" \ "alternativeEmail").text != null) {
          alternativeEmail = (userXML \\ "user" \ "alternativeEmail").text
      } else {
          alternativeEmail = "Not defined"
      }
      var institution = ""
      if((userXML \\ "user" \ "institution").text != null) {
          institution = (userXML \\ "user" \ "institution").text
      } else {
          institution = "Not defined"
      }
      var country = ""
      if((userXML \\ "user" \ "country").text != null) {
          country = (userXML \\ "user" \ "country").text
      } else {
          country = "Not defined"
      }
      var contact = ""
      if((userXML \\ "user" \\ "listOfContacts" \ "contact").text != null) {
          country = (userXML \\ "user" \ "country").text
      } else {
          contact = "Not defined"
      }

      bind("userInformation", xhtml,
           "username" -> username,
           "firstName" -> firstName,
           "surName" -> surName,
           "title" -> title,
           "firstEmail" -> firstEmail,
           "alternativeEmail" -> alternativeEmail,
           "institution" -> institution,
           "country" -> country
      )
    }
    case _ => {
            Text("")
    }
  }
}
