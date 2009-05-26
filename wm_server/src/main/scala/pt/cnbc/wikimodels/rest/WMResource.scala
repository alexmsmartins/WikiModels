/*
 * RESTEndPoint.scala
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
import javax.ws.rs.Produces
import javax.ws.rs.Consumes

@Path("/wm")
class WMResource {

    @GET
    @Produces(Array("application/xml", "application/json"))
    def get:String = {
        println("GET verb was used")
        <html>
            <body>
                <h1>get</h1>
            </body>
        </html>.toString
    }

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
