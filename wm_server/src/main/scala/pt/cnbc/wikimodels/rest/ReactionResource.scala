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

import pt.cnbc.wikimodels.dataModel.Reaction
import pt.cnbc.wikimodels.dataModel.Reaction
import pt.cnbc.wikimodels.dataAccess.ReactionsDAO
import pt.cnbc.wikimodels.exceptions.BadFormatException
import pt.cnbc.wikimodels.security.SecurityContextFactory


class ReactionResource(sbmlModelResource:String) extends RESTResource {
    
    @Context
    var security:SecurityContext = null
    @Context
    var uriInfo:UriInfo =null;

    @GET
    @Produces(Array("application/xml"))
    @Path("{reactionid}")//: [a-zA-Z][a-zA-Z_0-9]}")
    def get(@PathParam("reactionid") reactionResource:String
    ):String = {
        val username:String = security.getUserPrincipal().getName()

        Console.print("GET verb was used in reaction " + reactionResource)
        if(secContext.isAuthorizedTo(username,
                                     "GET", "model/" + sbmlModelResource +
                                     "/reaction/" + reactionResource ) ){
            try{
                val dao = new ReactionsDAO
                val reaction = dao.loadReaction(reactionResource)
                if(reaction != null &&
                   reaction.metaid == reactionResource){
                    reaction.toXML.toString
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
     * Creates a new resource (reaction in this case) with its metaid generated
     * automatically by wikiModels server.
     * the metaid can be suggested and, for that to happen, the XML that
     * represents the reaction should come with the metaid attribute filled
     */
    @POST
    @Consumes(Array("application/xml"))
    def post(requestContent:InputStream) = {
        val username = security.getUserPrincipal().getName()
        Console.print("POST verb was used in user " + username)

        var ret = ""
        if(secContext.isAuthorizedTo(username,
                                     "POST", "model/" + sbmlModelResource +
                                     "/reaction/" ) ){
            val reactionMetaId =
            try{
                val dao = new ReactionsDAO
                dao.trytoCreateReactionInModel(sbmlModelResource,
                    new Reaction(
                        scala.xml.XML.load(requestContent)))
            } catch {
                case e:Exception => {
                        e.printStackTrace
                        throw throw new WebApplicationException(
                            Response.Status.BAD_REQUEST)
                    }
            }
            if(reactionMetaId == null){
                throw new BadFormatException("Creating reaction did not went according to plan.");
            }else {
                val uri:URI = uriInfo.getAbsolutePathBuilder()
                .path(reactionMetaId)
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
     * Yet, creating a new reaction is not an idempotent request since it is
     * sbuject to verifications and may not result in exactly the sent entity
     * being created. Ids and other infromation may be modified.
     */
    @PUT
    @Path("{reactionid}")//: [a-zA-Z][a-zA-Z_0-9]}")
    @Consumes(Array("application/xml"))
    def put(@PathParam("reactionid") reactionResource:String,
            requestContent:String):Response = {
        val username = security.getUserPrincipal().getName()
        Console.print("PUT verb was used in user " + username)
        Console.print("reactionid = " + reactionResource)
        Console.print("Content of request = " + requestContent)
        Console.print("--------------------------------------")
        var ret = ""
        if(secContext.isAuthorizedTo(username,
                                     "PUT", "model/" + sbmlModelResource +
                                     "/reaction/" + reactionResource ) ){
            try{
                val dao = new ReactionsDAO
                //XXX if there are performance problems in this part replace:
                // - requstcontent:String -> requastcontont:InputStream
                // - scala.xml.XML.loadString -> scala.xml.XML.load
                if( dao.updateReaction(
                        new Reaction(
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
    @Path("{reactionid}")//: [a-zA-Z][a-zA-Z_0-9]}")
    def delete(@PathParam("reactionid") reactionResource:String
    ):Unit = {
        val username = security.getUserPrincipal().getName()
        Console.print("DELETE verb was used with user " + username)
        Console.print("DELETE verb was used with reactionid " + reactionResource)

        var ret = ""
        if(secContext.isAuthorizedTo(username,
                                     "DELETE", "model/" + sbmlModelResource +
                                     "/reaction/" + reactionResource ) ){
            try{
                val dao = new ReactionsDAO()

                if(dao.deleteReaction(
                        new Reaction(
                            reactionResource, Nil, null, null, true, false)) ){
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
}
