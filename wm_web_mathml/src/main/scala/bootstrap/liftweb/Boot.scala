package bootstrap.liftweb

import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import _root_.net.liftweb.http._
import _root_.net.liftweb.http.provider._
import _root_.net.liftweb.sitemap._
import _root_.net.liftweb.sitemap.Loc._
import Helpers._
import _root_.net.liftweb.mapper.{DB, ConnectionManager, Schemifier, DefaultConnectionIdentifier, StandardDBVendor}
import _root_.java.sql.{Connection, DriverManager}
import _root_.pt.cnbc.wikimodels.model._


/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    if (!DB.jndiJdbcConnAvailable_?) {
      val vendor = 
	new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
			     Props.get("db.url") openOr 
			     "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
			     Props.get("db.user"), Props.get("db.password"))

      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)

      DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)
    }

    // where to search snippet
    LiftRules.addToPackages("pt.cnbc.wikimodels")
    Schemifier.schemify(true, Schemifier.infoF _, User)

    // Build SiteMap
    def sitemap() = SiteMap(
      Menu("Home") / "index" :: // Simple menu form
      // Menu with special Link
      Menu(Loc("Static", Link(List("static"), true, "/static/index"), 
	       "Static Content")) ::
      Menu(Loc("MathMLApplet", new Link(List("applet"), true),
        "MathML Applet" , Hidden) ) ::
      // Menu entries for the User management stuff
      User.sitemap :_*)

    LiftRules.setSiteMapFunc(sitemap)

    /*
     * Show the spinny image when an Ajax call starts
     */
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    /*
     * Make the spinny image go away when it ends
     */
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    LiftRules.early.append(makeUtf8)

    LiftRules.loggedInTest = Full(() => User.loggedIn_?)

    S.addAround(DB.buildLoanWrapper)

    /// the following lines make content in static/ really static
    /// taken from http://scala-programming-language.1934581.n4.nabble.com/How-to-make-static-a-really-static-content-td1979339.html

    /**
     * pass a request that's not associated with Lift on to the
     * next filter (and ultimately the Servlet container),
     */
    LiftRules.passNotFoundToChain = true

    /**
     * identify any request that starts with /static/... as something not handled by Lift.
     */
    /*LiftRules.liftRequest.append{
      case Req("static" :: _, _, _) => false
    } */
  }

  /**
   *   Force the request to be UTF-8  
   */
  private def makeUtf8(req: HTTPRequest) {
    req.setCharacterEncoding("UTF-8")
  }
}
