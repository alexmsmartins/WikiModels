/*
 * RestFulAccess.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package pt.cnbc.wikimodels.rest.client

import scala.xml.XML
import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.charset.Charset
import org.apache.http.HttpResponse
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPut
import org.apache.http.client.methods.HttpDelete
import org.apache.http.client.methods.HttpRequestBase
import org.apache.http.impl.client.DefaultHttpClient;



/**
 * It might not be an object (aka Singleton) forever
 */
class RestfulAccess(username:String, password:String) {


    def sendRequest(method:String ,url:String):String = {
        val httpclient:DefaultHttpClient  = new DefaultHttpClient

        
        val meth = method match {
            case "GET" => new HttpGet(url)
            case "PUT" => new HttpGet(url)
            case "POST" => new HttpGet(url)
            case "DELETE" => new HttpGet(url)
            case _ => throw new Throwable
        }

        meth.setHeader("string", "strings")
        val response:HttpResponse  = httpclient.execute(meth)
        println( "======================" )

        //Console.println(response.getStatusLine())
        //Console.println(response.toString)
        val xmldoc = XML.load(response.getEntity.getContent)
        Console.println( xmldoc )
        println( "======================" )
        ""
    }

    protected def generateHash(username:String, password:String) = {
        username + password
        ""
    }
}
