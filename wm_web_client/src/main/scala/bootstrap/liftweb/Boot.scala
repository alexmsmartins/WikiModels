package bootstrap.liftweb

import _root_.net.liftweb.util._
import _root_.net.liftweb.http._
import _root_.net.liftweb.sitemap._
import _root_.net.liftweb.sitemap.Loc._
import _root_.net.liftmodules.FoBo
import Helpers._
import net.liftweb.common._
import _root_.net.liftmodules.widgets.menu.MenuWidget
import _root_.net.liftmodules.widgets.tablesorter.TableSorter
import _root_.net.liftmodules.widgets.tree.TreeView

import _root_.pt.cnbc.wikimodels.client.snippet._
import _root_.pt.cnbc.wikimodels.client.sitemapTweaks._
import alexmsmartins.log.LoggerWrapper

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot extends LoggerWrapper{
    def boot {

      FoBo.InitParam.JQuery=FoBo.JQuery191
      FoBo.InitParam.ToolKit=FoBo.Bootstrap231
      FoBo.InitParam.ToolKit=FoBo.AngularJS106
      FoBo.init()

      // set the character enconding to UTF-8
      LiftRules.early.append {
          _.setCharacterEncoding("UTF-8")
      }

      LiftRules.jsArtifacts

      ResourceServer.allow {
          case "css" :: "js" :: _ => true
      }

      // where to search snippet
      LiftRules.addToPackages("pt.cnbc.wikimodels.client")

      //TODO - solve java.lang.ClassCastException: net.liftweb.http.RewriteRequest cannot be cast to net.liftweb.http.Req before uncommenting the following line
      //LiftRules.docType.default.set((r:Req) => Full(DocType.html5))

      LiftRules.htmlProperties.default.set((r: Req) =>
        new XHtmlInHtml5OutProperties(r.userAgent))

        // SiteMap
        val entries = Menu(Loc("Home", List("index"), "Home"),
                           Menu(Loc("overview", List("overview"), "Overview")),
                           Menu(Loc("documentation", List("documentation"), "Documentation")),
                           Menu(Loc("people", List("people"), "People")),
                           Menu(Loc("contacts", List("contacts"), "Contacts")),
                           Menu(Loc("faq", List("faq"), "FAQ"))) ::
        Menu(Loc("models", List(""), "Models"),
             //Menu(Loc("createM", List("models","create"), "Create Model", loggedIn)),
             //Menu(Loc("createEditM", List("models","createEdit","Create"), "[NEW]Create Model", loggedIn)),
             //Menu(Loc("createEditMXX", List("models","createEdit"), "[NEW]Create Model", Hidden, loggedIn)),
             Menu(Loc("createModel", List("modele","model_create"), "Create Model", loggedIn)),
             Menu(Loc("importM", List("models","import"), "Import Model", loggedIn)),
             Menu(Loc("browseM", List("models","index"), "Browse Models", loggedIn)) ) ::
        //Menu(Loc("listM", List("models","list"), "List of Models"))) ::
        Menu(Loc("tags", List("tags"), "Tags")) ::
        Menu(Loc("create_user", List("create_user"), "Create User", Hidden)) ::
        Menu(Loc("profile_user", List("profile_user"), "Profile User", Hidden, loggedIn)) ::
        Menu(Loc("edit_user", List("edit_user"), "Edit User", Hidden, loggedIn)) ::
        Menu(Loc("help", List("help","index"), "Help"),
             Menu(Loc("helpMath", List("help","helpMath"), "Help Math", Hidden))) ::
        Menu(Loc("administrator", List("administrator","index"), "Administrator", Hidden, loggedIn)) ::
        //entries for new brows/edit model
        Menu(Loc("listMModels", List("modele","indexe"), "List Models", Hidden, loggedIn)) ::
        //Menu(Loc("createModel", List("modele","model_create"), "Create Model", Hidden, loggedIn)) ::
        Menu(Loc("editModel", List("modele","model_edit"), "Edit Model", Hidden, loggedIn)) ::
        Menu(Loc("viewModel", List("modele","model_view"), "View Model", Hidden, loggedIn)) ::
        Menu(Loc("deleteModel", List("modele","model_delete"), "Delete Model", Hidden, loggedIn)) ::
        //entries for new brows/edit compartment
        Menu(Loc("createCompartment", List("modele","compartment_create"), "Create Compartment", Hidden, loggedIn)) ::
        Menu(Loc("editCompartment", List("modele","compartment_edit"), "Edit Comaprtment", Hidden, loggedIn)) ::
        Menu(Loc("viewCompartment", List("modele","compartment_view"), "View Compartment", Hidden, loggedIn)) ::
        Menu(Loc("deleteCompartment", List("modele","compartment_delete"), "Delete Compartment", Hidden, loggedIn)) ::
        //entries for new brows/edit species
        Menu(Loc("createSpecies", List("modele","species_create"), "Create Species", Hidden, loggedIn)) ::
        Menu(Loc("editSpecies", List("modele","species_edit"), "Edit Species", Hidden, loggedIn)) ::
        Menu(Loc("viewSpecies", List("modele","species_view"), "View Species", Hidden, loggedIn)) ::
        Menu(Loc("deleteSpecies", List("modele","species_delete"), "Delete Species", Hidden, loggedIn)) ::
        //entries for new brows/edit paramenter
        Menu(Loc("createParameter", List("modele","parameter_create"), "Create Parameter", Hidden, loggedIn)) ::
        Menu(Loc("editParameter", List("modele","parameter_edit"), "Edit Parameter", Hidden, loggedIn)) ::
        Menu(Loc("viewParameter", List("modele","parameter_view"), "View Parameter", Hidden, loggedIn)) ::
        Menu(Loc("deleteParameter", List("modele","parameter_delete"), "Delete Parameter", Hidden, loggedIn)) ::
        //entries for new brows/edit function definition
        Menu(Loc("createFunctionDefinition", List("modele","function_definition_create"), "Create Function Definition", Hidden, loggedIn)) ::
        Menu(Loc("editFunctionDefinition", List("modele","function_definition_edit"), "Edit Function Definition", Hidden, loggedIn)) ::
        Menu(Loc("viewFunctionDefinition", List("modele","function_definition_view"), "View Function Definition", Hidden, loggedIn)) ::
        Menu(Loc("deleteFunctionDefinition", List("modele","function_definition_delete"), "Delete Function Definition", Hidden, loggedIn)) ::
        //entries for new brows/edit constraint
        Menu(Loc("createConstraint", List("modele","constraint_create"), "Create Constraint", Hidden, loggedIn)) ::
        Menu(Loc("editConstraint", List("modele","constraint_edit"), "Edit Constraint", Hidden, loggedIn)) ::
        Menu(Loc("viewConstraint", List("modele","constraint_view"), "View Constraint", Hidden, loggedIn)) ::
        Menu(Loc("deleteConstraint", List("modele","constraint_delete"), "Delete Constraint", Hidden, loggedIn)) ::
        //entries for new brows/edit reaction
        Menu(Loc("createReaction", List("modele","reaction_create"), "Create Reaction", Hidden, loggedIn)) ::
        Menu(Loc("editReaction", List("modele","reaction_edit"), "Edit Reaction", Hidden, loggedIn)) ::
        Menu(Loc("viewReaction", List("modele","reaction_view"), "View Reaction", Hidden, loggedIn)) ::
        Menu(Loc("deleteReaction", List("modele","reaction_delete"), "Delete Reaction", Hidden, loggedIn)) ::
        //entries for new brows/edit reactant
        Menu(Loc("createReactant", List("modele","reaction","reactant_create"), "Create Reactant", Hidden, loggedIn)) ::
        Menu(Loc("editReactant", List("modele","reaction","reactant_edit"), "Edit Reactant", Hidden, loggedIn)) ::
        Menu(Loc("viewReactant", List("modele","reaction","reactant_view"), "View Reactant", Hidden, loggedIn)) ::
        Menu(Loc("deleteReactant", List("modele","reaction","reactant_delete"), "Delete Reactant", Hidden, loggedIn)) ::
        //entries for new brows/edit product
        Menu(Loc("createProduct", List("modele","reaction","product_create"), "Create Product", Hidden, loggedIn)) ::
        Menu(Loc("editProduct", List("modele","reaction","product_edit"), "Edit Product", Hidden, loggedIn)) ::
        Menu(Loc("viewProduct", List("modele","reaction","product_view"), "View Product", Hidden, loggedIn)) ::
        Menu(Loc("deleteProduct", List("modele","reaction","product_delete"), "Delete Product", Hidden, loggedIn)) ::
        //entries for new brows/edit modifier
        Menu(Loc("createModifier", List("modele","reaction","modifier_create"), "Create Modifier", Hidden, loggedIn)) ::
        Menu(Loc("editModifier", List("modele","reaction","modifier_edit"), "Edit Modifier", Hidden, loggedIn)) ::
        Menu(Loc("viewModifier", List("modele","reaction","modifier_view"), "View Modifier", Hidden, loggedIn)) ::
        Menu(Loc("deleteModifier", List("modele","reaction","modifier_delete"), "Delete Modifier", Hidden, loggedIn)) ::
        //TODO - experiences - please delete before release
        Menu(Loc("selectbox_exp", List("selectbox_exp"), "Select Box experiment", Hidden, loggedIn)) ::
        Menu(Loc("firstliftscreen", List("experiments","firstliftscreen"), "LfitScren experiment", Hidden, loggedIn)) ::
        User.sitemap

      // Build SiteMap
      LiftRules.setSiteMap(SiteMap(entries:_*))

      // Allows the wysiwyg editor's inside pages (ie, allows the user to "insert a link")
      LiftRules.passNotFoundToChain = true


      // URL rewritess the pages, it is used for the bookmarks
      LiftRules.statelessRewrite.append {
        //NOTE1: these redirects make the model metaId available to SBMLForm snippets.
        //NOTE2: Lift automagically redirects a path terminated with a slash to "/index". And this is why, to match urls
        //of the form "xxx" and "xxx/" we have to use the pattern "xxx"::(Nil|"index"::Nil). For this to work "endslash"
        //in ParsePath must also be defined as "_" or the path won't even be matched to begin with.
        case RewriteRequest(ParsePath("createmodel"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /createmodel to /modele/model_create.html" )
          RewriteResponse(ParsePath( "modele"::"model_create"::Nil, "html", true, false),
            Map(), true )
        }
        case RewriteRequest(ParsePath("model"::model::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+" to /modele/model_view.html" )
          RewriteResponse(ParsePath( "modele"::"model_view"::Nil,"html",true, false),
            Map("modelMetaId" -> model ), true)
        }
        case RewriteRequest(ParsePath("model"::model::"edit"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/edit to /modele/model_edit.html" )
          RewriteResponse(ParsePath( "modele"::"model_edit"::Nil, "html", true, false),
            Map("modelMetaId" -> model), true )
        }
        case RewriteRequest(ParsePath("model"::model::"delete"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/delete to /modele/model_delete.html" )
          RewriteResponse(ParsePath( "modele"::"model_delete"::Nil, "html", true, false),
            Map("modelMetaId" -> model), true )
        }
        //redirects for compartment
        case RewriteRequest(ParsePath("model"::model::"createcompartment"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"
            +model+"/createcompartment to /modele/compartment_create.html" )
          RewriteResponse(ParsePath( "modele"::"compartment_create"::Nil, "html", true, false),
            Map("modelMetaId" -> model), true )
        }
        case RewriteRequest(ParsePath("model"::model::"compartment"::compartment::(Nil | "index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/compartment/"+compartment+" to /modele/compartment_view.html" )
          RewriteResponse(ParsePath( "modele"::"compartment_view"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "compartmentMetaId" -> compartment), true )
        }
        case RewriteRequest(ParsePath("model"::model::"compartment"::compartment::"edit"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/compartment/"+compartment+"/edit to /modele/compartment_edit.html" )
          RewriteResponse(ParsePath( "modele"::"compartment_edit"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "compartmentMetaId" -> compartment), true )
        }
        case RewriteRequest(ParsePath("model"::model::"compartment"::compartment::"delete"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/compartment/"+compartment+"/delete to /modele/compartment_delete.html" )
          RewriteResponse(ParsePath( "modele"::"compartment_delete"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "compartmentMetaId" -> compartment), true )
        }
        //redirects for species
        case RewriteRequest(ParsePath("model"::model::"createspecies"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"
            +model+"/createspecies to /modele/species_create.html" )
          RewriteResponse(ParsePath( "modele"::"species_create"::Nil, "html", true, false),
            Map("modelMetaId" -> model), true )
        }
        case RewriteRequest(ParsePath("model"::model::"species"::species::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/species/"+species+" to /modele/species_view.html" )
          RewriteResponse(ParsePath( "modele"::"species_view"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "speciesMetaId" -> species), true )
        }
        case RewriteRequest(ParsePath("model"::model::"species"::species::"edit"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/species/"+species+"/edit to /modele/species_edit.html" )
          RewriteResponse(ParsePath( "modele"::"species_edit"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "speciesMetaId" -> species), true )
        }
        case RewriteRequest(ParsePath("model"::model::"species"::species::"delete"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/species/"+species+"/delete to /modele/species_delete.html" )
          RewriteResponse(ParsePath( "modele"::"species_delete"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "speciesMetaId" -> species), true )
        }
        //redirects for parameter
        case RewriteRequest(ParsePath("model"::model::"createparameter"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"
            +model+"/createparameter to /modele/parameter_create.html" )
          RewriteResponse(ParsePath( "modele"::"parameter_create"::Nil, "html", true, false),
            Map("modelMetaId" -> model), true )
        }
        case RewriteRequest(ParsePath("model"::model::"parameter"::parameter::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/parameter/"+parameter+" to /modele/parameter_view.html" )
          RewriteResponse(ParsePath( "modele"::"parameter_view"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "parameterMetaId" -> parameter), true )
        }
        case RewriteRequest(ParsePath("model"::model::"parameter"::parameter::"edit"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/parameter/"+parameter+"/edit to /modele/parameter_edit.html" )
          RewriteResponse(ParsePath( "modele"::"parameter_edit"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "parameterMetaId" -> parameter), true )
        }
        case RewriteRequest(ParsePath("model"::model::"parameter"::parameter::"delete"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/parameter/"+parameter+"/delete to /modele/parameter_delete.html" )
          RewriteResponse(ParsePath( "modele"::"parameter_delete"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "parameterMetaId" -> parameter), true )
        }
        //redirects for function definition
        case RewriteRequest(ParsePath("model"::model::"createfunctiondefinition"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"
            +model+"/createfunctionDefinition to /modele/function_definition_create.html" )
          RewriteResponse(ParsePath("modele"::"function_definition_create"::Nil, "html",true,false),
            Map("modelMetaId"-> model), true)
        }
        case RewriteRequest(ParsePath("model"::model::"functiondefinition"::functionDefinition::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/functiondefinition/"+functionDefinition+" to /modele/functiondefinition_view.html" )
          RewriteResponse(ParsePath( "modele"::"function_definition_view"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "functionDefinitionMetaId" -> functionDefinition), true )
        }
        case RewriteRequest(ParsePath("model"::model::"functiondefinition"::functionDefinition::"edit"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/functiondefinition/"+functionDefinition+"/edit to /modele/functiondefinition_edit.html" )
          RewriteResponse(ParsePath( "modele"::"function_definition_edit"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "functionDefinitionMetaId" -> functionDefinition), true )
        }
        case RewriteRequest(ParsePath("model"::model::"functiondefinition"::functionDefinition::"delete"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/functiondefinition/"+functionDefinition+"/delete to /modele/functiondefinition_delete.html" )
          RewriteResponse(ParsePath( "modele"::"function_definition_delete"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "functionDefinitionMetaId" -> functionDefinition), true )
        }
        //redirects for constraint
        case RewriteRequest(ParsePath("model"::model::"createconstraint"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"
            +model+"/createconstraint to /modele/constraint_create.html" )
          RewriteResponse(ParsePath( "modele"::"constraint_create"::Nil, "html", true, false),
            Map("modelMetaId" -> model), true )
        }
        case RewriteRequest(ParsePath("model"::model::"constraint"::constraint::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/constraint/"+constraint+" to /modele/constraint_view.html" )
          RewriteResponse(ParsePath( "modele"::"constraint_view"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "constraintMetaId" -> constraint), true )
        }
        case RewriteRequest(ParsePath("model"::model::"constraint"::constraint::"edit"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/constraint/"+constraint+"/edit to /modele/constraint_edit.html" )
          RewriteResponse(ParsePath( "modele"::"constraint_edit"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "constraintMetaId" -> constraint), true )
        }
        case RewriteRequest(ParsePath("model"::model::"constraint"::constraint::"delete"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/constraint/"+constraint+"/delete to /modele/constraint_delete.html" )
          RewriteResponse(ParsePath( "modele"::"constraint_delete"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "constraintMetaId" -> constraint), true )
        }
        //redirects for reaction
        case RewriteRequest(ParsePath("model"::model::"createreaction"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"
            +model+"/createreaction to /modele/reaction_create.html" )
          RewriteResponse(ParsePath( "modele"::"reaction_create"::Nil, "html", true, false),
            Map("modelMetaId" -> model), true )
        }
        case RewriteRequest(ParsePath("model"::model::"reaction"::reaction::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/reaction/"+reaction+" to /modele/reaction_view.html" )
          RewriteResponse(ParsePath( "modele"::"reaction_view"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "reactionMetaId" -> reaction), true )
        }
        case RewriteRequest(ParsePath("model"::model::"reaction"::reaction::"edit"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/reaction/"+reaction+"/edit to /modele/reaction_edit.html" )
          RewriteResponse(ParsePath( "modele"::"reaction_edit"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "reactionMetaId" -> reaction), true )
        }
        case RewriteRequest(ParsePath("model"::model::"reaction"::reaction::"delete"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/reaction/"+reaction+"/delete to /modele/reaction_delete.html" )
          RewriteResponse(ParsePath( "modele"::"reaction_delete"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "reactionMetaId" -> reaction), true )
        }


        //redirects for reactant
        case RewriteRequest(ParsePath("model"::model::"reaction"::reaction::"createreactant"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"
            +model+"/createreactant to /modele/reaction/reactionreaction/reactant_create.html" )
          RewriteResponse(ParsePath( "modele"::"reaction"::"reactant_create"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "reactionMetaId" -> reaction), true )
        }
        case RewriteRequest(ParsePath("model"::model::"reaction"::reaction::"reactant"::reactant::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/reactant/"+reactant+" to /modele/reaction/reactionreactant_view.html" )
          RewriteResponse(ParsePath( "modele"::"reaction"::"reactant_view"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "reactionMetaId" -> reaction, "reactantMetaId" -> reactant), true )
        }
        case RewriteRequest(ParsePath("model"::model::"reaction"::reaction::"reactant"::reactant::"edit"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/reactant/"+reactant+"/edit to /modele/reaction/reactant_edit.html" )
          RewriteResponse(ParsePath( "modele"::"reaction"::"reactant_edit"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "reactionMetaId" -> reaction, "reactantMetaId" -> reactant), true )
        }
        case RewriteRequest(ParsePath("model"::model::"reaction"::reaction::"reactant"::reactant::"delete"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/reactant/"+reactant+"/delete to /modele/reaction/reactant_delete.html" )
          RewriteResponse(ParsePath( "modele"::"reaction"::"reactant_delete"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "reactionMetaId" -> reaction, "reactantMetaId" -> reactant), true )
        }

        //redirects for product
        case RewriteRequest(ParsePath("model"::model::"reaction"::reaction::"createproduct"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"
            +model+"/createproduct to /modele/reaction/product_create.html" )
          RewriteResponse(ParsePath( "modele"::"reaction"::"product_create"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "reactionMetaId" -> reaction), true )
        }
        case RewriteRequest(ParsePath("model"::model::"reaction"::reaction::"product"::product::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/product/"+product+" to /modele/reaction/product_view.html" )
          RewriteResponse(ParsePath( "modele"::"reaction"::"reaction"::"product_view"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "reactionMetaId" -> reaction, "productMetaId" -> product), true )
        }
        case RewriteRequest(ParsePath("model"::model::"reaction"::reaction::"product"::product::"edit"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/product/"+product+"/edit to /modele/reaction/product_edit.html" )
          RewriteResponse(ParsePath( "modele"::"reaction"::"reaction"::"product_edit"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "reactionMetaId" -> reaction, "productMetaId" -> product), true )
        }
        case RewriteRequest(ParsePath("model"::model::"reaction"::reaction::"product"::product::"delete"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/product/"+product+"/delete to /modele/reaction/product_delete.html" )
          RewriteResponse(ParsePath( "modele"::"reaction"::"reaction"::"product_delete"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "reactionMetaId" -> reaction, "productMetaId" -> product), true )
        }

        //redirects for modifier
        case RewriteRequest(ParsePath("model"::model::"reaction"::reaction::"createmodifier"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"
            +model+"/createmodifier to /modele/reaction/modifier_create.html" )
          RewriteResponse(ParsePath( "modele"::"reaction"::"modifier_create"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "reactionMetaId" -> reaction), true )
        }
        case RewriteRequest(ParsePath("model"::model::"reaction"::reaction::"modifier"::modifier::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/modifier/"+modifier+" to /modele/reaction/modifier_view.html" )
          RewriteResponse(ParsePath( "modele"::"reaction"::"modifier_view"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "reactionMetaId" -> reaction, "modifierMetaId" -> modifier), true )
        }
        case RewriteRequest(ParsePath("model"::model::"reaction"::reaction::"modifier"::modifier::"edit"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/modifier/"+modifier+"/edit to /modele/reaction/modifier_edit.html" )
          RewriteResponse(ParsePath( "modele"::"reaction"::"modifier_edit"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "reactionMetaId" -> reaction, "modifierMetaId" -> modifier), true )
        }
        case RewriteRequest(ParsePath("model"::model::"reaction"::reaction::"modifier"::modifier::"delete"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/modifier/"+modifier+"/delete to /modele/reaction/modifier_delete.html" )
          RewriteResponse(ParsePath( "modele"::"reaction"::"modifier_delete"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "reactionMetaId" -> reaction, "modifierMetaId" -> modifier), true )
        }

        //These redirects make the comments available to everyone
        //They detect a path that ends with /comments
        case RewriteRequest(ParsePath("model"::commented::"comments"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+commented+"/comments to /modele/model_comments.html" )
          RewriteResponse(ParsePath( "modele"::"model_comments"::Nil, "html", true, false),
            Map("commentedMetaId" -> commented), true )
        }
        case RewriteRequest(ParsePath("model"::model::childType::commented::"comments"::(Nil|"index"::Nil),"",_,_),_,_) => {
          trace("RewriteRequest from /model/"+model+"/"+childType+"/"+commented+"/delete to /modele/child_comments.html" )
          RewriteResponse(ParsePath( "modele"::"child_comments"::Nil, "html", true, false),
            Map("modelMetaId" -> model, "childType" -> childType, "commentedMetaId" -> commented), true )
        }
      }

      LiftRules.statelessDispatch.append{
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

    MenuWidget.init;
    TreeView.init;
    TableSorter.init;
}
