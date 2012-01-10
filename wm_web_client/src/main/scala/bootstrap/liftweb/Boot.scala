package bootstrap.liftweb

import _root_.net.liftweb.util._
import _root_.net.liftweb.http._
import _root_.net.liftweb.sitemap._
import _root_.net.liftweb.sitemap.Loc._
import Helpers._
import net.liftweb.common._
import _root_.net.liftweb.widgets.menu.MenuWidget
import _root_.net.liftweb.widgets.tablesorter.TableSorter
import _root_.net.liftweb.widgets.tree.TreeView

import _root_.pt.cnbc.wikimodels.tabs.TabsView

import _root_.pt.cnbc.wikimodels.snippet._
import _root_.pt.cnbc.wikimodels.client.snippet._
import _root_.pt.cnbc.wikimodels.client.sitemapTweaks._
import alexmsmartins.log.LoggerWrapper

/**            s
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot extends LoggerWrapper{
    def boot {


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

      //TODO - solve java.lang.ClassCastException: net.liftweb.http.RewriteRequest cannot be cast to net.liftweb.http.Req before uncommenting the following line
      //LiftRules.docType.default.set((r:Req) => Full(DocType.html5))

      LiftRules.htmlProperties.default.set((r: Req) =>new XHtmlInHtml5OutProperties(r.userAgent))

      // TODO - i STILL WANT TO FIND OUT WHY THIS DOES NOT WKRK. Something to do wiht the response not returning statuscode 302 to the browser, perhaps
      /*      LiftRules.statefulRewrite.prepend(NamedPF("CreateAndEditRewrite") {
        case RewriteRequest(
            ParsePath("models" :: "createEdit" :: state :: Nil, _, _,_), _, _) => {
          debug("Rewriting path: models/createEdit/Create to createEdit.html")
          val rewriteResp = RewriteResponse(
            ParsePath("models/createEdit" :: Nil, "html", true, false) , Map("state" -> "Create") // Use webapp/models/creteEdit.html
          )
          debug("Response refers to URL " + rewriteResp.path + " with parameters " + rewriteResp.params.toList.map(x => "" + x.key + "=" + x.value + ", "))
          rewriteResp
        }
      })*/
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
             Menu(Loc("createEditMXX", List("models","createEdit"), "[NEW]Create Model", Hidden, loggedIn)),
             Menu(ModelPageLoc),
             Menu(Loc("importM", List("models","import"), "[NEW]Import Model", loggedIn)),
             Menu(Loc("browseM", List("models","index"), "Browse Models", loggedIn)),
             Menu(Loc("browseMm", List("models","browse.html"), "Browse Model", Hidden, loggedIn)),
             Menu(Loc("editM", List("models","editModel.html"), "Edit Model", Hidden, loggedIn)),
             Menu(Loc("addM", List("models","addModel.html"), "Add Model", Hidden, loggedIn)) ) ::
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
        Menu(Loc("listMModels", List("modele","indexe"), "List Models", Hidden, loggedIn)) ::
        Menu(Loc("createModel", List("modele","model_create"), "Create Model", Hidden, loggedIn)) ::
        Menu(Loc("editModel", List("modele","model_edit"), "Edit Model", Hidden, loggedIn)) ::
        Menu(Loc("viewModel", List("modele","model_view"), "View Model", Hidden, loggedIn)) ::
        Menu(Loc("createCompartment", List("modele","compartment_create"), "Create Compartment", Hidden, loggedIn)) ::
        Menu(Loc("editCompartment", List("modele","compartment_edit"), "Edit Comaprtment", Hidden, loggedIn)) ::
        Menu(Loc("viewCompartment", List("modele","compartment_view"), "View Compartment", Hidden, loggedIn)) ::
          User.sitemap

        // Build SiteMap
        LiftRules.setSiteMap(SiteMap(entries:_*))

        // Allows the wysiwyg editor's inside pages (ie, allows the user to "insert a link")
        LiftRules.passNotFoundToChain = true

        // URL rewritess the pages, it is used for the bookmarks
        LiftRules.statelessRewrite.append {
          //NOTE: these redirects make the model metaId available to SBMLForm snippets.
          case RewriteRequest(ParsePath("createmodel"::Nil,"",_,false),_,_) => {
            trace("RewriteRequest from /createmodel to /modele/model_create.html" )
            RewriteResponse(ParsePath( "modele"::"model_create"::Nil, "html", true, false),
              Map(), true )
          }
          case RewriteRequest(ParsePath("model"::model::Nil,"",_,false),_,_) => {
            trace("RewriteRequest from /model/"+model+" to /modele/model_view.html" )
            RewriteResponse(ParsePath( "modele"::"model_view"::Nil,"html",true, false),
              Map("modelMetaId" -> model ), true)
          }
          case RewriteRequest(ParsePath("model"::model::"edit"::Nil,"",_,false),_,_) => {
            trace("RewriteRequest from /model/"+model+"/edit to /modele/model_edit.html" )
            RewriteResponse(ParsePath( "modele"::"model_edit"::Nil, "html", true, false),
              Map("modelMetaId" -> model), true )
          }
          case RewriteRequest(ParsePath("model"::model::"delete"::Nil,"",_,false),_,_) => {
            trace("RewriteRequest from /model/"+model+"/delete to /modele/model_delete.html" )
            RewriteResponse(ParsePath( "modele"::"model_delete"::Nil, "html", true, false),
              Map("modelMetaId" -> model), true )
          }
          case RewriteRequest(ParsePath("model"::model::"createcompartment"::Nil,"",_,false),_,_) => {
            trace("RewriteRequest from /model/"
              +model+"/createcompartment to /modele/compartment_create.html" )
            RewriteResponse(ParsePath( "modele"::"compartment_create"::Nil, "html", true, false),
              Map("modelMetaId" -> model), true )
          }
          case RewriteRequest(ParsePath("model"::model::"compartment"::compartment::Nil,"",_,false),_,_) => {
            trace("RewriteRequest from /model/"+model+"/compartment/"+compartment+" to /modele/compartment_view.html" )
            RewriteResponse(ParsePath( "modele"::"compartment_view"::Nil, "html", true, false),
              Map("modelMetaId" -> model, "compartmentMetaId" -> compartment), true )
          }
          case RewriteRequest(ParsePath("model"::model::"compartment"::compartment::"edit"::Nil,"",_,false),_,_) => {
            trace("RewriteRequest from /model/"+model+"/compartment/"+compartment+"/edit to /modele/compartment_edit.html" )
            RewriteResponse(ParsePath( "modele"::"compartment_edit"::Nil, "html", true, false),
              Map("modelMetaId" -> model, "compartmentMetaId" -> compartment), true )
          }
          case RewriteRequest(ParsePath("model"::model::"compartment"::compartment::"delete"::Nil,"",_,false),_,_) => {
            trace("RewriteRequest from /model/"+model+"/compartment/"+compartment+"/delete to /modele/compartment_delete.html" )
            RewriteResponse(ParsePath( "modele"::"compartment_delete"::Nil, "html", true, false),
              Map("modelMetaId" -> model, "compartmentMetaId" -> compartment), true )
          }

          //TODO redirects for species
          //TODO redirects for parameter
          //TODO redirects for function definition
          //TODO redirects for constraint
          //TODO redirects for reaction


          //These redirects make the comments available to everyone
          //They detect a path that ends with /comments
          case RewriteRequest(ParsePath("model"::commented::"comments"::Nil,"",_,false),_,_) => {
            trace("RewriteRequest from /model/"+commented+"/comments to /modele/model_comments.html" )
            RewriteResponse(ParsePath( "modele"::"model_comments"::Nil, "html", true, false),
              Map("commentedMetaId" -> commented), true )
          }
          case RewriteRequest(ParsePath("model"::model::childType::commented::"comments"::Nil,"",_,false),_,_) => {
            trace("RewriteRequest from /model/"+model+"/"+childType+"/"+commented+"/delete to /modele/child_comments.html" )
            RewriteResponse(ParsePath( "modele"::"child_comments"::Nil, "html", true, false),
              Map("modelMetaId" -> model, "childType" -> childType, "commentedMetaId" -> commented), true )
          }

          /* code written by Gonçalo
            case RewriteRequest(ParsePath(List("model",any,"edit"),_,_,_),_,_) =>
                RewriteResponse("models" :: "editModel.html" :: Nil)
            case RewriteRequest(ParsePath(List("model",any,"add",some),_,_,_),_,_) =>
                RewriteResponse("models" :: "addModel.html" :: Nil)
            case RewriteRequest(ParsePath(List("model",any),_,_,_),_,_) =>
                RewriteResponse("models" :: "browse.html" :: Nil)
            case RewriteRequest(ParsePath(List("model",any,"parameter",some),_,_,_),_,_) =>
                RewriteResponse("models" :: "editModel.html" :: Nil)
            case RewriteRequest(ParsePath(List("model",any,"species",some),_,_,_),_,_) =>
                RewriteResponse("models" :: "editModel.html" :: Nil)
            case RewriteRequest(ParsePath(List("model",any,"functionDefinition",some),_,_,_),_,_) =>
                RewriteResponse("models" :: "editModel.html" :: Nil)
            case RewriteRequest(ParsePath(List("model",any,"compartment",some),_,_,_),_,_) =>
                RewriteResponse("models" :: "editModel.html" :: Nil)
            case RewriteRequest(ParsePath(List("model",any,"constraint",some),_,_,_),_,_) =>
                RewriteResponse("models" :: "editModel.html" :: Nil)
            case RewriteRequest(ParsePath(List("model",any,"reaction",some),_,_,_),_,_) =>
                RewriteResponse("models" :: "editModel.html" :: Nil)
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
