package bootstrap.liftweb

import _root_.net.liftweb.util._
import _root_.net.liftweb.http._
import _root_.net.liftweb.sitemap._
import _root_.net.liftweb.sitemap.Loc._
import Helpers._
import _root_.net.liftweb.widgets.menu.MenuWidget
import pt.cnbc.wikimodels.snippet._

/**
  * A class that's instantiated early and run.  It allows the application
  * to modify lift's environment
  */
class Boot {

    def boot {
        // where to search snippet
        LiftRules.addToPackages("pt.cnbc.wikimodels")

        val loggedIn = If(() => isLogged.get, () => RedirectResponse("/login"))

        // SiteMap
        val entries = Menu(Loc("Home", List("index"), "Home"),
                           Menu(Loc("overview", List("overview"), "Overview")),
                           Menu(Loc("documentation", List("documentation"), "Documentation")),
                           Menu(Loc("investigators", List("investigators"), "Investigators")),
                           Menu(Loc("contacts", List("contacts"), "Contacts")),
                           Menu(Loc("faq", List("faq"), "FAQ"))) ::
        Menu(Loc("models", List("models","index"), "Models"),
            Menu(Loc("createM", List("models","create"), "Create Model", loggedIn)),
            Menu(Loc("browseM", List("models","browse"), "Browse Model", loggedIn)),
            Menu(Loc("listM", List("models","list"), "List of Models"))) ::
        Menu(Loc("tags", List("tags"), "Tags")) ::
        Menu(Loc("administrator", List("administrator","index"), "Administrator", loggedIn)) ::
        Menu(Loc("", List("welcome_user"), "", Hidden)) ::
        Nil
        
        // Build SiteMap
        LiftRules.setSiteMap(SiteMap(entries:_*))
        
        MenuWidget init;
    }
}
