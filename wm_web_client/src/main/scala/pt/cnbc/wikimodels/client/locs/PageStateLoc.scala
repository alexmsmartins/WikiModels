/* Copyright (c) 2011. Alexandre Martins. All rights reserved. */
package pt.cnbc.wikimodels.client


/**
 * Sitemap URL magic gets defined here
 */
package object sitemapTweaks {
  import net.liftweb.common.Full
  import net.liftweb.http.{RewriteResponse, ParsePath, RewriteRequest}
  import net.liftweb.sitemap.Loc
  import net.liftweb.common.Box
  import net.liftweb.sitemap.Loc.{
  Link, LinkText, LocParam, Hidden
  }
  import net.liftweb.sitemap.Loc.If._
  import pt.cnbc.wikimodels.client.snippet.SBMLForm._
  import net.liftweb.sitemap.Loc._
  import pt.cnbc.wikimodels.client.snippet.User

  // verification if the user is logged
  val loggedIn = If(() => User.loggedIn_?, "You must be logged in.")



  /**
   * Created by IntelliJ IDEA. User: alex Date: 11-08-2011 Time: 21:19 To change this template use File | Settings | File Templates.
   */
  object ModelPageLoc extends Loc[CreateEditPageState] {
    var state:CreateEditPageState = defaultValue.openOrThrowException("Failed creating edit page state")
    def name: String = "CreateEDitWithState"

    def link = new Link[CreateEditPageState]("models" :: "createEdit" :: "Create" :: Nil)

    def text = "[NEW]Create Model"

    def params: List[LocParam[CreateEditPageState]] = List(loggedIn)

    override def rewrite = Full({
      case RewriteRequest(ParsePath(List("models", "createEdit", _state), _, _, _), _, _) => {
        _state match {
          case "Create" => {
            state = Create
            (RewriteResponse(List("models", "createEdit")), Create)
          }
          case "edit" => {
            state = Edit
            (RewriteResponse(List("models", "createEdit")), Edit)
          }
          case _ => {
            state = Visualize
            (RewriteResponse(List("models", "createEdit")), Visualize)
          }
        }
      }
    })
    def defaultValue: Box[CreateEditPageState] = Full(Create)
  }


}