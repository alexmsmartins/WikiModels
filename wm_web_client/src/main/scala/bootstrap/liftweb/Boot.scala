package bootstrap.liftweb

import _root_.net.liftweb.util._
import _root_.net.liftweb.http._
import _root_.net.liftweb.sitemap._
import _root_.net.liftweb.sitemap.Loc._
import pt.cnbc.wikimodels.model._
import Helpers._

/**
  * A class that's instantiated early and run.  It allows the application
  * to modify lift's environment
  */

object MenuInfo {
    import Loc._
    
    def menu: List[Menu] =  Menu(Loc("home", List("index"), " Home ", Hidden)) ::
    Menu(Loc("logout", List("menu","logout"), " Logout ", Hidden)) ::
    Menu(Loc("profile", List("menu","profile"), " Profile ", Hidden)) ::
    Menu(Loc("recover", List("menu","recover"), " Recover Password ")) ::
    Menu(Loc("create", List("menu","create"), " Register ")) :: Nil

}

class Boot {
  def boot {
    // where to search snippet
    LiftRules.addToPackages("pt.cnbc.wikimodels")

    // Build SiteMap
    LiftRules.setSiteMap(SiteMap(MenuInfo.menu:_*))
  }
}

