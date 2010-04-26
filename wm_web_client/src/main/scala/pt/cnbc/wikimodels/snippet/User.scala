/*
* User.scala
*
* Object User registers the user in a session var and
* also removes the session var
*/

package pt.cnbc.wikimodels.snippet

import net.liftweb.mapper._
import net.liftweb.http._
import net.liftweb.http.provider.HTTPRequest
import _root_.scala.xml.{NodeSeq, Node, Elem}
import _root_.scala.xml.transform._
import net.liftweb.sitemap.Loc._
import net.liftweb.sitemap._
import net.liftweb.util._
import net.liftweb.common._

import pt.cnbc.wikimodels.rest.client.RestfulAccess

object User {
    object UserName extends SessionVar[Box[String]](Empty)

    var re:RestfulAccess = null

    def setRestful(ra: RestfulAccess) = {
        re = ra
    }

    def getRestful:RestfulAccess = {
        re
    }

    def screenWrap: Box[Node] = Full(<lift:surround with="default" at="content">
			       <lift:bind /></lift:surround>)

    def logoutSuffix = "logout"
    lazy val logoutPath = thePath(logoutSuffix)

    def homePage = "/"

    def thePath(end: String): List[String] = List(end)

    lazy val testLogginIn = If(loggedIn_? _, S.??("must.be.logged.in")) ;

    def loggedIn_? : Boolean = currentUserName.isDefined

    def notloggedIn_? = !loggedIn_?

    var onLogOut: List[Box[String] => Unit] = Nil

    def logoutUser() {
        onLogOut.foreach(_(currentUserName))
        UserName.remove()
        //S.request.foreach(_.request.getSession.invalidate)
    }

    def currentUserName: Box[String] = UserName.is

    def logout = {
        logoutUser
        S.redirectTo(homePage)
    }

    /**
     * The menu item for logout (make this "Empty" to disable)
     */
    def logoutMenuLoc: Box[Menu] =
    Full(Menu(Loc("Logout", logoutPath, S.??("logout"), Hidden,
                  Template(() => wrapIt(logout)),
                  testLogginIn)))

    lazy val sitemap: List[Menu] = {
        List(logoutMenuLoc).flatten(a => a)
    }

    protected def wrapIt(in: NodeSeq): NodeSeq =
    screenWrap.map(new RuleTransformer(new RewriteRule {
        override def transform(n: Node) = n match {
          case e: Elem if "bind" == e.label && "lift" == e.prefix => in
          case _ => n
        }
      })) openOr in
}
