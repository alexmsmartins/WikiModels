/*
 * RestFulAccess.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package pt.cnbc.wikimodels.rest.client


import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.WebResource

/**
 * It might not be an object (aka Singleton) forever
 */
object RestfulAccess {
    def checkURL(url:String, userHash:String) = {
        var c = Client.create
        //var r = c.resource("http://")
        ""
    }

    protected def generateHash(username:String, password:String) = {
        val c:Client = Client.create
        username + password
        ""
    }
}

