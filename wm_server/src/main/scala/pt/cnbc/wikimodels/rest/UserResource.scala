/*
 * UserResource.scala
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


import scala.collection.mutable.Map
import scala.collection.mutable.HashMap


@Path("/user/{username}")//: [a-zA-Z][a-zA-Z_0-9]}")
class UserResource extends RESTResource {



    @GET
    @Produces(Array("application/xml", "application/json"))
    def get(@PathParam("username") userName:String ):String = {
        Console.print("GET verb was used in user " + userName)

        //TODO -- code to be deleted
        val hash =
        Map("alex" -> "alexp",
            "gonçalo" -> "gonçalop")
        //TODO -- end of code to be deleted

        //stub of a returned user


        hash.get(userName) match {
            case Some(x) => xmlUser(userName, x )
            case None => <user/>.toString
        }
    }

    def xmlUser(userName: String , passwd :String) =
    <users>
        <user>
            <username>
                {userName}
            </username>
            <password>
                {passwd}
            </password>
        </user>
    </users>.toString


    @POST
    @Consumes(Array("application/xml", "application/xml"))
    def create =
        println("POST verb was used")


    @PUT
    @Consumes(Array("application/xml", "application/xml"))
    def update =
        println("PUT verb was used")

    @DELETE
    @Consumes(Array("application/xml", "application/xml"))
    def delete =
        println("DELETE verb was used")




}

