/*
 * ModelResource.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package pt.cnbc.wikimodels.rest

import java.io.InputStream
import java.lang.annotation._
import java.net.URI

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.DELETE
import javax.ws.rs.Consumes
import javax.ws.rs.HeaderParam
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Context
import javax.ws.rs.core.SecurityContext
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

import scala.collection.mutable.Map
import scala.collection.mutable.HashMap

import pt.cnbc.wikimodels.dataModel.SBMLModel
import pt.cnbc.wikimodels.dataAccess.SBMLModelsDAO
import pt.cnbc.wikimodels.exceptions.BadFormatException
import pt.cnbc.wikimodels.security.SecurityContextFactory

@Path("/model/")
class SBMLModelResource extends RESTResource {

    @Context
    var security:SecurityContext = null
    @Context
    var uriInfo:UriInfo =null;

    @GET
    @Path("{modelid}")//: [a-zA-Z][a-zA-Z_0-9]}")
    @Produces(Array("application/xml"))
    def get(@PathParam("modelid") sbmlModelResource:String
    ):String = {
        val username:String = security.getUserPrincipal().getName()

        Console.print("GET verb was used in model " + sbmlModelResource)
        if(secContext.isAuthorizedTo(username,
                                     "GET", "model/" + sbmlModelResource ) ){
            try{
                val dao = new SBMLModelsDAO
                val sbmlModel = dao.loadSBMLModel(sbmlModelResource)
                if(sbmlModel != null &&
                   sbmlModel.metaid == sbmlModelResource){
                    sbmlModel.toXML.toString
                } else {
                    throw new WebApplicationException(Response.Status.NOT_FOUND)
                }
            } catch {
                case e:WebApplicationException => throw e
                case e:Exception => {
                        e.printStackTrace
                        throw throw new WebApplicationException(
                            Response.Status.BAD_REQUEST)
                    }
            }
        } else {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }
    }
    
    /**
     * Creates a new resource (model in this case) with its metaid generated
     * automatically by wikiModels server.
     * the metaid can be suggested and, for that to happen, the XML that
     * represents the model should come with the metaid attribute filled
     */
    @POST
    @Consumes(Array("application/xml"))
    def post(requestContent:InputStream) = {
        val username = security.getUserPrincipal().getName()
        Console.print("POST verb was used in user " + username)

        var ret = ""
        if(secContext.isAuthorizedTo(username,
                                     "POST", "model/") ){
            val modelMetaId =
            try{
                val dao = new SBMLModelsDAO
                dao.trytoCreateSBMLModel(
                    new SBMLModel(
                        scala.xml.XML.load(requestContent)))
            } catch {
                case e:Exception => {
                        e.printStackTrace
                        throw throw new WebApplicationException(
                            Response.Status.BAD_REQUEST)
                    }
            }
            if(modelMetaId == null){
                throw new BadFormatException("Creating model did not went according to plan.");
            }else {
                val uri:URI = uriInfo.getAbsolutePathBuilder()
                .path(modelMetaId)
                .build();
                Response.created( uri ).build()
            }
        } else {
            //user is trying to access a resource for which
            //it does not have permissions
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

    }

    /**
     * Updates an already created resource
     * According to the REST style architecture the PUT request can be used
     * to create new reesources. Yet this is only allowed as long as the request
     * remains  idempotent.
     * Yet, creating a new model is not an idempotent request since it is
     * sbuject to verifications and may not result in exactly the sent entity
     * being created. Ids and other infromation may be modified.
     */
    @PUT
    @Path("{modelid}")//: [a-zA-Z][a-zA-Z_0-9]}")
    @Consumes(Array("application/xml"))
    def put(@PathParam("modelid") sbmlModelResource:String,
            requestContent:String):Response = {
        val username = security.getUserPrincipal().getName()
        Console.print("PUT verb was used in user " + username)
        Console.print("modelid = " + sbmlModelResource)
        Console.print("Content of request = " + requestContent)
        Console.print("--------------------------------------")
        var ret = ""
        if(secContext.isAuthorizedTo(username,
                                     "PUT", "model/") ){
            try{
                val dao = new SBMLModelsDAO
                //XXX if there are performance problems in this part replace:
                // - requstcontent:String -> requastcontont:InputStream
                // - scala.xml.XML.loadString -> scala.xml.XML.load
                if( dao.updateSBMLModel(
                        new SBMLModel(
                            scala.xml.XML.loadString(requestContent))) ){
                    Response.ok.build
                } else {
                    throw new WebApplicationException(
                        Response.Status.NOT_FOUND)
                }
            } catch {
                case e:WebApplicationException => throw e
                case e:Exception => {
                        e.printStackTrace
                        throw new WebApplicationException(
                            Response.Status.BAD_REQUEST)
                    }
            }
        } else {
            //user is trying to access a resource for which
            //it does not have permissions
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }
    }

    @DELETE
    @Path("{modelid}")//: [a-zA-Z][a-zA-Z_0-9]}")
    def delete(@PathParam("modelid") sbmlModelResource:String
    ):Unit = {
        val username = security.getUserPrincipal().getName()
        Console.print("DELETE verb was used in user " + username)

        var ret = ""
        if(secContext.isAuthorizedTo(username,
                                     "DELETE", "model/") ){
            try{
                val dao = new SBMLModelsDAO
                if(dao.deleteSBMLModel(
                        new SBMLModel(sbmlModelResource, Nil, null , null))){
                      System.out.print("XXXXXXXXXXXXXXXXXXXXXX")
                } else {
                    throw new WebApplicationException(
                        Response.Status.NOT_FOUND)
                }
                
            } catch {
                case e:WebApplicationException => throw e
                case e:Exception => {
                        e.printStackTrace
                        throw new WebApplicationException(
                            Response.Status.BAD_REQUEST)
                    }
            }
        } else {
            //user is trying to access a resource for which
            //it does not have permissions
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }
    }


  @Path("{modelid}/compartment/")
   def compartmentResource(@PathParam("modelid") sbmlModelResource:String) = {
      /*val username = security.getUserPrincipal().getName()
       Console.print("compartment resource was used in user " + username)*/
      val resource:CompartmentResource = new CompartmentResource(sbmlModelResource)
      //TODO find out why the annotation @Context does not work on subresources
      resource.security = this.security //ugly hack
      resource.uriInfo = this.uriInfo //ugly hack
      resource
   }

   @Path("{modelid}/constraint/")
   def constraintResource(@PathParam("modelid") sbmlModelResource:String) = {
      /*val username = security.getUserPrincipal().getName()
       Console.print("constraint resource was used in user " + username)*/
      val resource:ConstraintResource = new ConstraintResource(sbmlModelResource)
      //TODO find out why the annotation @Context does not work on subresources
      resource.security = this.security //ugly hack
      resource.uriInfo = this.uriInfo //ugly hack
      resource
   }

   @Path("{modelid}/functionDefinition/")
   def functionDefinitionResource(@PathParam("modelid") sbmlModelResource:String) = {
      /*val username = security.getUserPrincipal().getName()
       Console.print("functionDefinition resource was used in user " + username)*/
      val resource:FunctionDefinitionResource = new FunctionDefinitionResource(sbmlModelResource)
      //TODO find out why the annotation @Context does not work on subresources
      resource.security = this.security //ugly hack
      resource.uriInfo = this.uriInfo //ugly hack
      resource
    }

  @Path("{modelid}/parameter/")
  def parameterResource(@PathParam("modelid") sbmlModelResource:String):ParameterResource = {
    /*val username = security.getUserPrincipal().getName()
     Console.print("parameter resource was used in user " + username)*/
    val resource:ParameterResource = new ParameterResource(sbmlModelResource)
    //TODO find out why the annotation @Context does not work on subresources
    resource.security = this.security //ugly hack
    resource.uriInfo = this.uriInfo //ugly hack
    resource
  }

  @Path("{modelid}/reaction/")
  def reactionResource(@PathParam("modelid") sbmlModelResource:String) = {
    /*val username = security.getUserPrincipal().getName()
     Console.print("reaction resource was used in user " + username)*/
    val resource:ReactionResource = new ReactionResource(sbmlModelResource)
    //TODO find out why the annotation @Context does not work on subresources
    resource.security = this.security //ugly hack
    resource.uriInfo = this.uriInfo //ugly hack
    resource
  }

  @Path("{modelid}/species/")
  def speciesResource(@PathParam("modelid") sbmlModelResource:String) = {
    /*val username = security.getUserPrincipal().getName()
     Console.print("species resource was used in user " + username)*/
    val resource:SpeciesResource = new SpeciesResource(sbmlModelResource)
    //TODO find out why the annotation @Context does not work on subresources
    resource.security = this.security //ugly hack
    resource.uriInfo = this.uriInfo //ugly hack
    resource
  }

  @Path("{modelid}/comments/")
  def commentsResource(@PathParam("modelid") sbmlModelResource:String) = {
    val username = security.getUserPrincipal().getName()
    Console.print("comments resource was used in user " + username)
    new CommentsResource()
  }
}
