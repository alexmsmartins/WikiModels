package pt.cnbc.wikimodels.snippet

import net.liftweb.http.SHtml
import net.liftweb.util.Helpers._
import net.liftweb.http._
import net.liftweb.util._
import net.liftweb.sitemap.Loc.If
import scala.xml._
import S._
import pt.cnbc.wikimodels.snippet._

import pt.cnbc.wikimodels.controller._

class Submission {
    def form (xhtml : NodeSeq) : NodeSeq = {
        var username = ""
        var password = ""

        def authenticate () = {
            println("Received "+username+" and password "+password)
            if (username.length == 0) {
                S.error("Invalid username")
            } else if (username.equals("admin") && password.equals("admin")) {
                User.UserName(Full(username))
                println("I am logged "+User.UserName.is+" ok "+User.currentUserName)
            } else {
                println("Username= "+User.UserName+" Password= "+password)
            }
        }
        bind("entry", xhtml,
             "username" -> SHtml.text("", username = _, ("size", "10"), ("maxlength", "40")),
             "password" -> SHtml.password("", password = _,  ("size", "10")),
             "submit" -> SHtml.submit("Submit", authenticate))
    }

    def create (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
            var name = ""
            var description = ""

            def accept () = {
                if (name.size == 0 && description.size == 0) {
                    S.error("Invalid username")
                } else {
                    println("Model name= "+name+" Description= "+description)
                }
            }
            bind("createEntry", xhtml,
             "name" -> SHtml.text("", name = _, ("size", "40"), ("maxlength", "120")),
             "description" -> SHtml.textarea("", description = _, ("maxlength", "2000")),
             "save" -> SHtml.submit("Submit", accept))
        }
        case _ => Text("")
    }
}
