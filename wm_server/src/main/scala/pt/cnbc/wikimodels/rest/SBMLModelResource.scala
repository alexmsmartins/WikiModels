/*
 * ModelResource.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.rest

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.DELETE
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.Consumes
import javax.ws.rs.HeaderParam
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status
import javax.ws.rs.core.Response.Status._
import java.lang.annotation._
import javax.ws.rs.WebApplicationException

import scala.collection.mutable.Map
import scala.collection.mutable.HashMap

import pt.cnbc.wikimodels.dataModel.SBMLModel
import pt.cnbc.wikimodels.dataAccess.SBMLModelsDAO
import pt.cnbc.wikimodels.security.SimpleSecurityContext
import pt.cnbc.wikimodels.security.SecurityContextFactory

import javax.ws.rs.core.Context
import javax.ws.rs.core.SecurityContext



@Path("/model/{modeid}")//: [a-zA-Z][a-zA-Z_0-9]}")
class SBMLModelResource extends RESTResource {

    




    @Context
    var security:SecurityContext = null
    val secContext = SecurityContextFactory.createSecurityContext

    @GET
    @Produces(Array("application/xml"))
    @Consumes(Array("application/xml"))
    def get(@PathParam("modelresource") sbmlModelResource:String
            ):String = {
        val username = security.getUserPrincipal().getName()

        var ret = ""
        Console.print("GET verb was used in model " + sbmlModelResource)
        if(secContext.isAuthorizedTo(username,
                                     "GET", "model/" + sbmlModelResource ) ){
            val sbmlModel = SBMLModelsDAO.loadSBMLModel(sbmlModelResource)
            sbmlModel match{
                case SBMLModel(sbmlModelResource,_,_,_) => {
                        """<?xml version="1.0" encoding="UTF-8"?>
""" + sbmlModel.toXML.toString
                }
                case _ =>
                    throw new WebApplicationException(Response.Status.NOT_FOUND)
            }
        } else {
         /*<html>
            <body>
                <h1>sbmlModelResource = {sbmlModelResource}</h1>
                sbmlmodelname = {sbmlModelname}
            </body>
        </html>.toString*/
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }
        //ret
    }

    @POST
    @Consumes(Array("application/xml"))
    def post =
    println("POST verb was used")


    @PUT
    @Consumes(Array("application/xml"))
    def put =
    println("PUT verb was used")

    @DELETE
    @Consumes(Array("application/xml", "application/xml"))
    def delete =
    println("DELETE verb was used")
}
