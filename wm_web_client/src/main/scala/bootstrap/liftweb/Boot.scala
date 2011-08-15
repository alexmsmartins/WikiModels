package bootstrap.liftweb

import _root_.net.liftweb.util._
import _root_.net.liftweb.http._
import _root_.net.liftweb.sitemap._
import _root_.net.liftweb.sitemap.Loc._
import Helpers._
import _root_.net.liftweb.widgets.menu.MenuWidget
import _root_.net.liftweb.widgets.tablesorter.TableSorter
import _root_.net.liftweb.widgets.tree.TreeView

import _root_.pt.cnbc.wikimodels.tabs.TabsView

import _root_.pt.cnbc.wikimodels.snippet._
import _root_.pt.cnbc.wikimodels.client.snippet._
import _root_.pt.cnbc.wikimodels.client.sitemapTweaks._


import net.liftweb.common.Full
import tools.nsc.Phase.Model


/**            s
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  //TODO: object Log extends Logger
  //TODO: replace log4j for SLF4J
    def boot {

      val any = ""
        // set the character enconding to UTF-8
        LiftRules.early.append {
            _.setCharacterEncoding("UTF-8")
        }

        ResourceServer.allow {
            case "css" :: "js" :: _ => true
        }

      // where to search snippet
      LiftRules.addToPackages("pt.cnbc.wikimodels")
      //Where to search for lift related files. This is the new location
      LiftRules.addToPackages("pt.cnbc.wikimodels.client")

        //TODO: LiftRules.htmlProperties.default.set((r: Req) =>new Html5Properties(r.userAgent))


        // SiteMap
        val entries = Menu(Loc("Home", List("index"), "Home"),
                           Menu(Loc("overview", List("overview"), "Overview")),
                           Menu(Loc("documentation", List("documentation"), "Documentation")),
                           Menu(Loc("people", List("people"), "People")),
                           Menu(Loc("contacts", List("contacts"), "Contacts")),
                           Menu(Loc("faq", List("faq"), "FAQ"))) ::
        Menu(Loc("models", List(""), "Models"),
             Menu(Loc("createM", List("models","create"), "Create Model", loggedIn)),
             //Menu(Loc("createEditM", List("models","createEdit","Create"), "[NEW]Create Model", loggedIn)),
             Menu(Loc("createEditMXX", List("models","createEdit"), "[NEW]Create Model", loggedIn)),
             Menu(ModelPageLoc),
             Menu(Loc("importM", List("models","import"), "[NEW]Import Model", loggedIn)),
             Menu(Loc("browseM", List("models","index"), "Browse Models", loggedIn)),
             Menu(Loc("browseMm", List("models","browse.xhtml"), "Browse Model", Hidden, loggedIn)),
             Menu(Loc("editM", List("models","editModel.xhtml"), "Edit Model", Hidden, loggedIn)),
             Menu(Loc("addM", List("models","addModel.xhtml"), "Add Model", Hidden, loggedIn)) ) ::
        //Menu(Loc("listM", List("models","list"), "List of Models"))) ::
        Menu(Loc("tags", List("tags"), "Tags")) ::
        Menu(Loc("create_user", List("create_user"), "Create User", Hidden)) ::
        Menu(Loc("profile_user", List("profile_user"), "Profile User", Hidden, loggedIn)) ::
        Menu(Loc("edit_user", List("edit_user"), "Edit User", Hidden, loggedIn)) ::
        Menu(Loc("new_comment", List("models","new_comment"), "New Comment", Hidden)) ::
        Menu(Loc("new_model_comment", List("models","new_model_comment"), "New Model Comment", Hidden)) ::
        Menu(Loc("view_comments", List("models","view_comments"), "View Comments", Hidden)) ::
        Menu(Loc("view_all_comments", List("models","view_all_comments"), "View All Comments", Hidden)) ::
        Menu(Loc("help", List("help","index"), "Help"),
             Menu(Loc("helpMath", List("help","helpMath"), "Help Math", Hidden))) ::
        Menu(Loc("administrator", List("administrator","index"), "Administrator", Hidden, loggedIn)) ::
        //entries for new brows/edit model in the same page
        Menu(Loc("browseEditM", List("modele","indexe"), "Browse/Edit Model", Hidden, loggedIn)) ::
        User.sitemap

        // Build SiteMap
        LiftRules.setSiteMap(SiteMap(entries:_*))

        // Allows the wysiwyg editor's inside pages (ie, allows the user to "insert a link")
        LiftRules.passNotFoundToChain = true

        // URL rewritess the pages, it is used for the bookmarks
        LiftRules.statelessRewrite.append {
          case RewriteRequest(ParsePath("model"::model::Nil,"",_,false),_,_) => {
            RewriteResponse(ParsePath( "modele"::"indexe"::Nil,"xhtml",true, false), Map("modelMetaId" -> model), true)
          }

          /* code written by Gonçalo
            case RewriteRequest(ParsePath(List("model",any,"edit"),_,_,_),_,_) =>
                RewriteResponse("models" :: "editModel.xhtml" :: Nil)
            case RewriteRequest(ParsePath(List("model",any,"add",some),_,_,_),_,_) =>
                RewriteResponse("models" :: "addModel.xhtml" :: Nil)
            case RewriteRequest(ParsePath(List("model",any),_,_,_),_,_) =>
                RewriteResponse("models" :: "browse.xhtml" :: Nil)
            case RewriteRequest(ParsePath(List("model",any,"parameter",some),_,_,_),_,_) =>
                RewriteResponse("models" :: "editModel.xhtml" :: Nil)
            case RewriteRequest(ParsePath(List("model",any,"species",some),_,_,_),_,_) =>
                RewriteResponse("models" :: "editModel.xhtml" :: Nil)
            case RewriteRequest(ParsePath(List("model",any,"functionDefinition",some),_,_,_),_,_) =>
                RewriteResponse("models" :: "editModel.xhtml" :: Nil)
            case RewriteRequest(ParsePath(List("model",any,"compartment",some),_,_,_),_,_) =>
                RewriteResponse("models" :: "editModel.xhtml" :: Nil)
            case RewriteRequest(ParsePath(List("model",any,"constraint",some),_,_,_),_,_) =>
                RewriteResponse("models" :: "editModel.xhtml" :: Nil)
            case RewriteRequest(ParsePath(List("model",any,"reaction",some),_,_,_),_,_) =>
                RewriteResponse("models" :: "editModel.xhtml" :: Nil)
                */
        }

      LiftRules.statelessDispatchTable.append{
        case Req("model" :: modelMetaId :: "export" :: Nil, "", GetRequest) =>
          //() => Full(PlainTextResponse("test"))
          () => {
            import _root_.pt.cnbc.wikimodels.client.lib.Export
            Export.asSBMLL2V4(modelMetaId)
          }
      }

      /**
       * The following lines are used to call object snippers that extends Dis+atchSnippet
       */
      LiftRules.snippetDispatch.append{
        Map("SBMLFormSnippet" -> new SBMLForm)
      }
    }

    MenuWidget init;
    TreeView init;
    TableSorter init;
    TabsView init;

}
