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
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import javax.ws.rs.HeaderParam
import javax.ws.rs.core.Context
import javax.ws.rs.core.SecurityContext
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo
import javax.ws.rs.WebApplicationException

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
            val sm = SBMLModel(sbmlModelResource,
                      <html>
                    <body>
                        <h1>Title</h1>
                    </body>
                      </html>
                      ,
                      "id0001", "name0001")
            sm.toXML
                .toString
            
            /*
             val sbmlModel = SBMLModelsDAO.loadSBMLModel(sbmlModelResource)
             sbmlModel match{
             case SBMLModel(sbmlModelResource,_,_,_) => {
             sbmlModel.toXML.toString
             }
             case _ =>
             throw new WebApplicationException(Response.Status.NOT_FOUND)
             }*/
        } else {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }
    }



    /**
     * Creates a new resource (model in this case) with its metaid generated
     * automatically by wikiModels server
     */
    @POST
    @Produces(Array("application/xml"))
    @Consumes(Array("application/xml"))
    def post(requestContent:InputStream) = {
        val username = security.getUserPrincipal().getName()
        Console.print("POST verb was used in user " + username)

        var ret = ""
        if(secContext.isAuthorizedTo(username,
                                     "POST", "model/") ){
            try{
                val dao = new SBMLModelsDAO
                dao.trytoCreateSBMLModel(
                    new SBMLModel(
                        scala.xml.XML.load(requestContent)))
            } catch {
                case e:Exception => {
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
     * Udaptes an already created resource
     * According to the REST style architecture the PUT request can be used
     * to create new reesources. Yet this is only allowed as long as the request
     * remains  idempotent.
     * Yet, creating a new model is not an idempotent request since it is
     * sbuject to verifications and may not result in exactly the sent entity
     * being created. Ids and other infromation may be modified.
     */
    @PUT
    @Consumes(Array("application/xml"))
    def put(requestContent:String) = {
        println("PUT verb was used")

    }

    @DELETE
    def delete =
    println("DELETE verb was used")
}
