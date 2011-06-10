/*f
 * UserResource.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
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
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status
import javax.ws.rs.core.Response.Status._
import javax.ws.rs.core.SecurityContext
import java.lang.annotation._
import javax.ws.rs.WebApplicationException

import scala.collection.mutable.Map
import scala.collection.mutable.HashMap

import pt.cnbc.wikimodels.dataModel.User
import pt.cnbc.wikimodels.dataAccess.UsersDAO
import pt.cnbc.wikimodels.security.SecurityContextFactory

@Path("/user/{userresource}")//: [a-zA-Z][a-zA-Z_0-9]}")
class UserResource extends RESTResource {

    @Context
    var security:SecurityContext = null


    /**
     * 
     */
    @GET
    @Produces(Array("application/xml"))
    def get(@PathParam("userresource") userResource:String
    ):String = {
        val username = security.getUserPrincipal().getName()

        var ret = ""
        logger.debug("GET verb was used in user " + userResource)
        if(secContext.isAuthorizedTo(username,
                                     "GET", "user/" + userResource ) ){
            val user = UsersDAO.loadUser(userResource)
            user match{
                case User(userResource,_,_,_,_) => user.toXML.toString
                case null =>
                    /*<html>
                     <body>
                     <h1>userResource = {userResource}</h1>
                     <h2>With null User</h2>
                     username = {username}
                     </body>
                     </html>.toString*/

                    throw new WebApplicationException(Response.Status.NOT_FOUND)
            }
        } else {
            /*<html>
             <body>
             <h1>userResource = {userResource}</h1>
             <h2>Without authorization</h2>
             username = {username}
             </body>
             </html>.toString*/


            //user is trying to access a resource for which
            //it does not have permissions
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }
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
