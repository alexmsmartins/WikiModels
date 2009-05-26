
package pt.cnbc.wikimodels.snippet

import net.liftweb.http.SHtml
import net.liftweb.util.Helpers._
import net.liftweb.http._
import net.liftweb.sitemap.Loc.If
import scala.xml._
import S._

import pt.cnbc.wikimodels.controller._

object User extends SessionVar("Anonymous")

class Submission {
    def form (xhtml : NodeSeq) : NodeSeq = {
        var login = ""
        var password = ""

        def authenticate () = {
            if (User.size == 0) {
                S.error("Invalid username")
            } else if (User.get.equals("admin") && password.equals("admin")) {
                println("I am logged")
                isLogged.set(true)
                S.redirectTo("welcome_user")
            } else {
                isLogged.set(false)
                println("Username= "+User.get+" Password= "+password)
            }
        }
        bind("entry", xhtml,
             "username" -> SHtml.text(User, login =>{
                    User(login)}, ("size", "10"), ("maxlength", "40")),
             "password" -> SHtml.password("", password = _,  ("size", "10")),
             "submit" -> SHtml.submit("Submit", authenticate))
    }
    def create (xhtml : NodeSeq) : NodeSeq = {
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
             "name" -> SHtml.text("", name = _, ("size", "20"), ("maxlength", "120")),
             "description" -> SHtml.textarea("", description = _, ("maxlength", "2000")),
             "save" -> SHtml.submit("Submit", accept))
    }
}

object isLogged {
    var logged = false
    def set (log: Boolean) = {
        logged = log
    }

    def get () = {
        logged
    }
}
