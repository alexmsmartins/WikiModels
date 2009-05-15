
package pt.cnbc.wikimodels.snippet

import net.liftweb.http.SHtml
import net.liftweb.util.Helpers._
import net.liftweb.http._
import net.liftweb.sitemap.Loc.If
import scala.xml._

import pt.cnbc.wikimodels.controller._

class Submission {
    def form (xhtml : NodeSeq) : NodeSeq = {
        var login = ""
        var password = ""

        def authenticate () = {
            if (login.size == 0) {
                S.error("Invalid username")
            } else {
                println("Username= "+login+" Password= "+password)
            }
        }
        /*<h3>
            Login { SHtml.text("", l => login = l, ("size", "10"), ("maxlength", "40")) }
            Password { SHtml.password("", p => password = p, ("size", "10"), ("maxlength", "40")) }
            { SHtml.submit("Submit", () => UsersControl ! UserLogin(login, password)) }
        </h3>*/
        bind("entry", xhtml,
             "username" -> SHtml.text("", login = _, ("size", "10"), ("maxlength", "40")),
             "password" -> SHtml.password("", password = _,  ("size", "10")),
             "submit" -> SHtml.submit("Submit", authenticate))
    }
}
