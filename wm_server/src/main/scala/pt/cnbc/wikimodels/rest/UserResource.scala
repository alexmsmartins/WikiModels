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
import javax.ws.rs.HeaderParam
import javax.ws.rs.core.Response
import java.lang.annotation._


import scala.collection.mutable.Map
import scala.collection.mutable.HashMap


import pt.cnbc.wikimodels.dataModel.User


@Path("/user/{username}")//: [a-zA-Z][a-zA-Z_0-9]}")
@Consumes(Array("application/xml"))
@Produces(Array("application/xml"))
class UserResource extends RESTResource {



    @GET
    def get(@PathParam("username") userName:String,
            @HeaderParam("string") str:String ):String = {


        //TODO - refactor this method to separate the reading of the header
        //TODO from the resto
        Console.print("GET verb was used in user " + userName)

        //TODO -- code to be deleted
        val alex= User("alex","alexp","alexmsmartins@gmail.com","alexandre Martins")
        val goncalo = User("goncalo", "goncalop", "gneto@gmail.com", "Gonçalo Neto")

        val hash =
        Map[String,User]("alex" -> alex,
            "gonçalo" -> goncalo)
        //TODO -- end of code to be deleted

        //stub of a returned user


        hash.get(userName ) match {
            case Some(x) => x.toXML.toString
            case None => <users/>.toString
        }

                    //val r = Response.created(uri)

        
    }

    @POST
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

