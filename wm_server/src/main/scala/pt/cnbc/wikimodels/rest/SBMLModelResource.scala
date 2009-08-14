/*
 * ModelResource.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.rest

import java.io.InputStream
import java.lang.annotation._

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
import pt.cnbc.wikimodels.security.SecurityContextFactory

@Path("/model/{modelid}")//: [a-zA-Z][a-zA-Z_0-9]}")
class SBMLModelResource extends RESTResource {

    @Context
    var security:SecurityContext = null
    @Context
    var uriInfo:UriInfo =null;
    val secContext = SecurityContextFactory.createSecurityContext

    @GET
    @Produces(Array("application/xml"))
    def get(@PathParam("modelid") sbmlModelResource:String
    ):String = {
        val username:String = security.getUserPrincipal().getName()

        Console.print("GET verb was used in model " + sbmlModelResource)
        if(secContext.isAuthorizedTo(username,
                                     "GET", "model/" + sbmlModelResource ) ){
            try{
                val dao = new SBMLModelsDAO
                val sm = new SBMLModel(sbmlModelResource,
                                       <html>
                        <body>
                            <h1>Title</h1>
                        </body>
                                       </html>
                                       ,
                                       "id0001", "name0001")
                sm.toXML
                .toString
            
            
                val sbmlModel = dao.loadSBMLModel(sbmlModelResource)
                sbmlModel match{
                    case SBMLModel(sbmlModelResource) => {
                            sbmlModel.toXML.toString
                        }
                    case _ =>
                        throw new WebApplicationException(Response.Status.NOT_FOUND)
                }
            } catch {
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
    @Produces(Array("application/xml"))
    @Consumes(Array("application/xml"))
    def post(@PathParam("modelid") sbmlModelResource:String,
             requestContent:InputStream) = {
        val username = security.getUserPrincipal().getName()
        Console.print("POST verb was used in user " + username)

        var ret = ""
        //TODO TURN THE POST scheleton in SBMLModelResource into
        if(secContext.isAuthorizedTo(username,
                                     "POST", "model/") ){
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
                dao.createSBMLModel(
                    new SBMLModel(
                        scala.xml.XML.loadString(requestContent)))
                //TODO insert the right URL
                return Response.created(uriInfo.getAbsolutePath()).build()
            } catch {
                case e:Exception => {
                        e.printStackTrace
                        throw throw new WebApplicationException(
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
    def delete(@PathParam("modelid") sbmlModelResource:String
    ) = {
        val username = security.getUserPrincipal().getName()
        Console.print("DELETE verb was used in user " + username)

        var ret = ""
        if(secContext.isAuthorizedTo(username,
                                     "DELETE", "model/") ){
            try{
                val dao = new SBMLModelsDAO
                //TODO DELETE SOMETHING
                ""
            } catch {
                case e:Exception => {
                        e.printStackTrace
                        throw throw new WebApplicationException(
                            Response.Status.BAD_REQUEST)
                    }
            }
        } else {
            //user is trying to access a resource for which
            //it does not have permissions
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }
    }


    /*@Path("compartment/")
    def compartmentResource() = {
        val username = security.getUserPrincipal().getName()
        Console.print("compartment resource was used in user " + username)        
    }

    @Path("constraint/")
    def constraintResource() = {
        val username = security.getUserPrincipal().getName()
        Console.print("constraint resource was used in user " + username)
    }

    @Path("functionDefinition/")
    def functionDefinitionResource() = {
        val username = security.getUserPrincipal().getName()
        Console.print("functionDefinition resource was used in user " + username)
    }

    @Path("parameter/")
    def parameterResource() = {
        val username = security.getUserPrincipal().getName()
        Console.print("parameter resource was used in user " + username)
    }

    @Path("reaction/")
    def reactionResource() = {
        val username = security.getUserPrincipal().getName()
        Console.print("reaction resource was used in user " + username)
    }

    @Path("species/")
    def speciesResource() = {
        val username = security.getUserPrincipal().getName()
        Console.print("species resource was used in user " + username)
    }

    @Path("comments/")
    def commentsResource() = {
        val username = security.getUserPrincipal().getName()
        Console.print("comments resource was used in user " + username)
        new CommentsResource()
    }*/
}
