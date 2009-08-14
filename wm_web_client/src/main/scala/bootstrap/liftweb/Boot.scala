package bootstrap.liftweb

import net.liftweb.util._
import net.liftweb.http._
import net.liftweb.sitemap._
import net.liftweb.sitemap.Loc._
import Helpers._
import net.liftweb.widgets.menu.MenuWidget
import pt.cnbc.wikimodels.snippet._

/**
  * A class that's instantiated early and run.  It allows the application
  * to modify lift's environment
  */
class Boot {
    def boot {

        LiftRules.early.append {
            _.setCharacterEncoding("UTF-8")
        }

        // where to search snippet
        LiftRules.addToPackages("pt.cnbc.wikimodels")

        val loggedIn = If(() => User.loggedIn_?, "You must be logged in")

        // SiteMap
        val entries = Menu(Loc("Home", List("index"), "Home"),
                           Menu(Loc("overview", List("overview"), "Overview")),
                           Menu(Loc("documentation", List("documentation"), "Documentation")),
                           Menu(Loc("people", List("people"), "People")),
                           Menu(Loc("contacts", List("contacts"), "Contacts")),
                           Menu(Loc("faq", List("faq"), "FAQ"))) ::
        Menu(Loc("models", List("models","index"), "Models"),
            Menu(Loc("createM", List("models","create"), "Create Model", loggedIn)),
            Menu(Loc("browseM", List("models","browse"), "Browse Model", loggedIn)),
            Menu(Loc("listM", List("models","list"), "List of Models"))) ::
        Menu(Loc("tags", List("tags"), "Tags")) ::
        Menu(Loc("administrator", List("administrator","index"), "Administrator", loggedIn)) ::
        User.sitemap
        
        // Build SiteMap
        LiftRules.setSiteMap(SiteMap(entries:_*))

        LiftRules.passNotFoundToChain = true
        
        LiftRules.rewrite.append {
           case RewriteRequest(
                   ParsePath(List("user","goncalo"),_,_,_),_,_) =>
               RewriteResponse("overview" :: Nil, Map("name" -> "goncalo"))
       }
       
        MenuWidget init;
    }
}
