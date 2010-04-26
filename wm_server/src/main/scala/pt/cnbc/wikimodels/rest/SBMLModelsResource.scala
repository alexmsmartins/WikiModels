/*
 * SBMLModelsResource.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
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
import pt.cnbc.wikimodels.dataAccess.SBMLModelsDAO

@Path("/models/")
class SBMLModelsResource extends RESTResource{

    @Context
    var security:SecurityContext = null
    @Context
    var uriInfo:UriInfo =null;

    @GET
    @Produces(Array("application/xml"))
    def get(@PathParam("modelid") sbmlModelResource:String
    ):String = {
        val username:String = security.getUserPrincipal().getName()

        Console.print("GET verb was used in model " + sbmlModelResource)
        if(secContext.isAuthorizedTo(username,
                                     "GET", "model/" + sbmlModelResource ) ){
            try{
                val dao = new SBMLModelsDAO()
                val sbmlModels = dao.loadSBMLModels
                if(sbmlModels != null &&
                   sbmlModels.listOfModels.size > 0){
                    sbmlModels.toXML.toString
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
}
