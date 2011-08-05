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

import net.liftweb.common.Full


/**            s
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {






  //TODO: object Log extends Logger
  //TODO: replace log4j for SLF4J
  def boot {
    //1. Execute early functions: this is a mechanism that allows a user function to be called on the
    //   HttpServletRequest before it enters the normal processing chain. This can be used for, for
    //   example, to set the XHTML output to UTF-8. This is controlled through LiftRules.early
    // set the character enconding to UTF-8
    LiftRules.early.append {
      _.setCharacterEncoding("UTF-8")
    }

    //2. Perform URL Rewriting, which we already covered in detail in section 3.7. Controlled via
    //   LiftRules.rewrite, this is useful for creating user-friendly URLs, among other things.
    //   The result of the transformation will be checked for possible rewrites until there are no more
    //   matches or it is explicitly stopped by setting the stopRewriting val in ReqwriteResponse
    //   to true. It is relevant to know that you can have rewriter functions per-session hence you
    //   can have different rewriter in different contexts. These session rewriters are prended to the
    //   LiftRules rewriters before their application.
    // URL rewritess the pages, it is used for the bookmarks
    LiftRules.statelessRewrite.append {
      case RewriteRequest(ParsePath("model" :: model :: Nil, "", _, false), _, _) => {
        RewriteResponse(ParsePath("modele" :: "indexe" :: Nil, "xhtml", true, false), Map("modelMetaId" -> model), true)
      }
      case RewriteRequest(ParsePath("models" :: "CreateEdit" :: state :: Nil, "", _, false), _, _) => {
        RewriteResponse(ParsePath("models" :: "createEdit" :: Nil, "xhtml", true, false), Map("state" -> state), true)
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

    //3. Call LiftRules.onBeginServicing hooks. This is a mechanism that allows you to add
    //   your own hook functions that will be called when Lift is starting to process the request. You
    //   could set up logging here, for instance.

    //4. Check for user-defined stateless dispatch in LiftRules.statelessDispatchTable. If
    //   the partial functions defined in this table match the request then they are used to create a
    //   LiftResponse that is sent to the user, bypassing any further processing. These are very
    //   useful for building things like REST APIs. The term stateless refers to the fact that at the
    //   time the dispatch function is called, the stateful object, called S, is not available and the
    //   LiftSession is not created yet. Custom dispatch is covered in section 3.8
    LiftRules.statelessDispatchTable.append {
      case Req("model" :: modelMetaId :: "export" :: Nil, "", GetRequest) =>
        //() => Full(PlainTextResponse("test"))
        () => {
          import _root_.pt.cnbc.wikimodels.client.lib.Export
          Export.asSBMLL2V4(modelMetaId)
        }
    }
    //5. Create a LiftSession. The LiftSession holds various bits of state for the request, and
    //   is covered in more detail in section 9.5.

    //6. Call LiftSession.onSetupSession. This is a mechanism for adding hook functions that
    //   will be called when the LiftSession is created. We’ll get into more details when we discuss
    //   Lift’s session management in section 9.5.
    //7. Initialize the S object (section 3.4.1). The S object represents the current state of the Request
    //   and Response.
    //8. Call any LoanWrapper instances that you’ve added through S.addAround. A LoanWrap-
    //   per is a way to insert your own processing into the render pipeline, similar to how Fil-
    //   ter works in the Servlet API. This means that when your LoanWrapper implementation is
    //   called, Lift passes you a function allowing you to chain the processing of the request. With
    //   this functionality you can execute your own pre- and post-condition code. A simple exam-
    //   ple of this would be if you need to make sure that something is configured at the start of
    //   processing and cleanly shut down when processing terminates. LoanWrappers are covered
    //   in section 9.6.1
    //9. Process the stateful request
    //     (a) Check the stateful dispatch functions defined in LiftRules.dispatch. This is sim-
    //         ilar to the stateless dispatch in step #4 except that these functions are executed in the
    //         context of a LiftSession and an S object (section 3.4.1). The first matching partial func-
    //         tion is used to generate a LiftResponse that is returned to the client. If none of the
    //         dispatch functions match then processing continues. Dispatch functions are covered
    //         in section 3.8. This flow is wrapped by LiftSession.onBeginServicing/onEndServicing
    //         calls
    //     (b) If this is a Comet request, then process it and return the response. Comet is a method
    //         for performing asynchronous updates of the user’s page without a reload. We cover
    //         Comet techniques in chapter 11
    //     (c) If this is an Ajax request, execute the user’s callback function; the specific function
    //         is mapped via a request parameter (essentially a token). The result of the callback is
    //         returned as the response to the user. The response can be a JavaScript snippet, an XML
    //         construct or virtually any LiftResponse. For an overview of LiftResponse please
    //         see section 9.4. This flow is wrapped by LiftSession.onBeginServicing/onEndServicing
    //         calls.
    //    (d) If this is a regular HTTP request, then:
    //           i. Call LiftSession.onBeginServicing hooks. Mostly “onBegin”/”onEnd”
    //              functions are used for logging. Note that the LiftRules object also has onBe-
    //              ginServicing and onEndServicing functions but these are “wrapping” more
    //              Lift processing and not just statefull processing.
    //          ii. Check the user-defined dispatch functions that are set per-session
    //              (see S.addHighLevelSessionDispatcher).
    //              This is similar to LiftRules.dispatch except that you can have different functions set up
    //              for a different session depending on your application logic. If there is a function
    //              applicable, execute it and return its response. If there is no per-session dispatch
    //              function, process the request by executing the Scala function that user set up for
    //              specific events (such as when clicking a link, or pressing the submit button, or a
    //              function that will be executed when a form field is set etc.). Please see SHtml obejct
    //              3.4.2.
    //         iii. Check the SiteMap and Loc functions. We cover SiteMap extensively in chapter 7.

    // verification if the user is logged
    val loggedIn = If(() => User.loggedIn_?, "You must be logged in.")

    // SiteMap
    val entries =
      Menu(Loc("Home", List("index"), "Home"),
        Menu(Loc("overview", List("overview"), "Overview")),
        Menu(Loc("documentation", List("documentation"), "Documentation")),
        Menu(Loc("people", List("people"), "People")),
        Menu(Loc("contacts", List("contacts"), "Contacts")),
        Menu(Loc("faq", List("faq"), "FAQ"))) ::
      Menu(Loc("models", List(""), "Models"),
        Menu(Loc("createM", List("models", "create"), "Create Model", loggedIn)),
        Menu(Loc("createEditM", List("models", "createEdit"), "[NEW]Create Model", loggedIn)),
        Menu(Loc("importM", List("models", "import"), "[NEW]Import Model", loggedIn)),
        Menu(Loc("browseM", List("models", "index"), "Browse Models", loggedIn)),
        Menu(Loc("browseMm", List("models", "browse.xhtml"), "Browse Model", Hidden, loggedIn)),
        Menu(Loc("editM", List("models", "editModel.xhtml"), "Edit Model", Hidden, loggedIn)),
        Menu(Loc("addM", List("models", "addModel.xhtml"), "Add Model", Hidden, loggedIn))) ::
      //Menu(Loc("listM", List("models","list"), "List of Models"))) ::
      Menu(Loc("tags", List("tags"), "Tags")) ::
      Menu(Loc("create_user", List("create_user"), "Create User", Hidden)) ::
      Menu(Loc("profile_user", List("profile_user"), "Profile User", Hidden, loggedIn)) ::
      Menu(Loc("edit_user", List("edit_user"), "Edit User", Hidden, loggedIn)) ::
      Menu(Loc("new_comment", List("models", "new_comment"), "New Comment", Hidden)) ::
      Menu(Loc("new_model_comment", List("models", "new_model_comment"), "New Model Comment", Hidden)) ::
      Menu(Loc("view_comments", List("models", "view_comments"), "View Comments", Hidden)) ::
      Menu(Loc("view_all_comments", List("models", "view_all_comments"), "View All Comments", Hidden)) ::
      Menu(Loc("help", List("help", "index"), "Help"),
        Menu(Loc("helpMath", List("help", "helpMath"), "Help Math", Hidden))) ::
      Menu(Loc("administrator", List("administrator", "index"), "Administrator", Hidden, loggedIn)) ::
      //entries for new brows/edit model in the same page
      Menu(Loc("browseEditM", List("modele", "indexe"), "Browse/Edit Model", Hidden, loggedIn)) ::
      User.sitemap

    // Build SiteMap
    LiftRules.setSiteMap(SiteMap(entries: _*))


    //         iv. Lookup the template based on the request path. Lift will locate the templates using
    //              various approaches:
    //               A. Check the partial functions defined in LiftRules.viewDispatch. If there
    //                   is a function defined for this path invoke it and return an Either[() ⇒
    //                   Can[ NodeSeq],LiftView]. This allows you to either return the function for han-
    //                   dling the view directly, or delegate to a LiftView subclass. LiftView is cov-
    //                   ered in section 4.4
    //               B. If no viewDispatch functions match, then look for the template using the
    //                   ServletContext’s getResourceAsStream.
    //               C. If Lift still can’t find any templates, it will attempt to locate a View class whose
    //                   name matches the first component of the request path under the view folder of
    //                   any packages defined by LiftRules.addToPackages method. If an Inse-
    //                   cureLiftView class is found, it will attempt to invoke a function on the class
    //                   corresponding to the second component of the request path. If a LiftView
    //                   class is found, it will invoke the dispatch method on the second component
    //                   of the request path.
    // where to search snippet
    LiftRules.addToPackages("pt.cnbc.wikimodels")
    //Where to search for lift related files. This is the new location
    LiftRules.addToPackages("pt.cnbc.wikimodels.client")

    //          v. Process the templates by executing snippets, combining templates etc.
    LiftRules.snippetDispatch.append {
      Map("SBMLFormSnippet" -> new SBMLForm)
    }
    //               A. Merge <head> elements, as described in section e??
    //               B. Update the internal functions map. Basically this associates the user’s Scala
    //                   functions with tokens that are passed around in subsequent requests using
    //                   HTTP query parameters. We cover this mechanism in detail in section 9.3
    //               C. Clean up notices (see S.error, S.warning, S.notice) since they were already ren-
    //                   dered they are no longer needed. Notices are covered in section B.
    //               D. Call LiftRules.convertResponse. Basically this glues together different
    //                   pieces if information such as the actual markup, the response headers, cookies,
    //                   etc into a LiftResponse instance.
    //               E. Check to see if Lift needs to send HTTP redirect. For an overview please see 3.9
    //         vi. Call LiftSession.onEndServicing hooks, the counterparts to LiftSes-
    //              sion.onBeginServicing
    //    (e) Call LiftRules.performTransform.
    //         This is actually configured via the LiftRules.responseTransformers RulesSeq. This is a list of functions on
    //         LiftResponse ⇒ LiftResponse that allows the user to modify the response before it’s
    //         sent to the client
    //10. Call LiftRules.onEndServicing hooks. These are the stateless end-servicing hooks,
    //    called after the S object context is destroyed.
    //11. Call any functions defined in LiftRules.beforeSend. This is the last place where you
    //    can modify the response before it’s sent to the user
    //12. Convert the LiftResponse to a raw byte stream and send it to client as an HTTP response.
    //13. Call any functions defined in LiftRules.afterSend. Typically these would be used for
    //    cleanup.

    val any = ""


    ResourceServer.allow {
      case "css" :: "js" :: _ => true
    }

    //TODO: LiftRules.htmlProperties.default.set((r: Req) =>new Html5Properties(r.userAgent))

    // Allows the wysiwyg editor's inside pages (ie, allows the user to "insert a link")
    LiftRules.passNotFoundToChain = true





  }

  MenuWidget init;
  TreeView init;
  TableSorter init;
  TabsView init;
}
