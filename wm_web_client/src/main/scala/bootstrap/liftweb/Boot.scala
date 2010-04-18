package bootstrap.liftweb

import net.liftweb.util._
import net.liftweb.http._
import net.liftweb.sitemap._
import net.liftweb.sitemap.Loc._
import Helpers._
import net.liftweb.widgets.menu.MenuWidget
import net.liftweb.widgets.tablesorter.TableSorter
import net.liftweb.widgets.tree.TreeView
import pt.cnbc.wikimodels.tabs.TabsView

import pt.cnbc.wikimodels.snippet._

/**
  * A class that's instantiated early and run.  It allows the application
  * to modify lift's environment
  */
class Boot {
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

        // verification if the user is logged
        val loggedIn = If(() => User.loggedIn_?, "You must be logged in.")

        // SiteMap
        val entries = Menu(Loc("Home", List("index"), "Home"),
                           Menu(Loc("overview", List("overview"), "Overview")),
                           Menu(Loc("documentation", List("documentation"), "Documentation")),
                           Menu(Loc("people", List("people"), "People")),
                           Menu(Loc("contacts", List("contacts"), "Contacts")),
                           Menu(Loc("faq", List("faq"), "FAQ"))) ::
        Menu(Loc("models", List(""), "Models"),
            Menu(Loc("createM", List("models","create"), "Create Model", loggedIn)),
            Menu(Loc("browseM", List("models","index"), "Browse Model", loggedIn)),
            Menu(Loc("browseMm", List("models","browse.xhtml"), "Browse Model", Hidden, loggedIn)),
            Menu(Loc("editM", List("models","editModel.xhtml"), "Edit Model", Hidden, loggedIn))) ::
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
        User.sitemap
        
        // Build SiteMap
        LiftRules.setSiteMap(SiteMap(entries:_*))

        // Allows the wysiwyg editor's inside pages (ie, allows the user to "insert a link")
        LiftRules.passNotFoundToChain = true

        // redirects the pages, it is used for the bookmarks
        /*LiftRules.rewrite.append {
            case RewriteRequest(
                    ParsePath(List("models","browse",modelID),_,_,_),_,_) =>
                RewriteResponse("models" :: "browse" :: Nil, Map("modelID" -> modelID))
        }*/

        /*LiftRules.rewrite.append {
           case RewriteRequest(
                   ParsePath(List("models",any),_,_,_),_,_) => any match {
                    case "model001" => {
                            RewriteResponse("models" :: "browse" :: Nil, Map("modelID" -> any))
                    }
                    case _ => {
                            RewriteResponse("models" :: any :: Nil, Map("name" -> any))
                    }
                   }
               
        }*/
        
        MenuWidget init;
        TreeView init;
        TableSorter init;
        TabsView init;
    }
}
