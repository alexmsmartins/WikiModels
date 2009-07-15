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
class RestfulAccess(host:String,
                    port:Int,
                    contextRoot:String,
                    username:String,
                    password:String) {

    val httpclient:DefaultHttpClient  = new DefaultHttpClient
    var authScheme =  new AuthScope(host, 8080)
    var credentials = new UsernamePasswordCredentials(username, password)
    httpclient.getCredentialsProvider()
              .setCredentials(authScheme,
                              credentials)

    /**
     * The anonymous user will be known throughout every WikiModels Wiki
     * He will get the minimum permissions every user will ever get
     */
    def this(host:String, port:Int, contextRoot:String) = {
        this(host, port, contextRoot,"anonymous","anonymous")
    }

    def getRequest(url:String) = {
        val meth =  new HttpGet(url)
        val response:HttpResponse  = httpclient.execute(meth)
        println( "======================" )

        //Console.println(response.getStatusLine())
        //Console.println(response.toString)
        val xmldoc = XML.load(response.getEntity.getContent)
        Console.println( xmldoc )
        println( "======================" )
        ""

    }

    def postRequest(url:String) = {
        val meth = new HttpPut(url)
        val response:HttpResponse  = httpclient.execute(meth)
        println( "======================" )

        //Console.println(response.getStatusLine())
        //Console.println(response.toString)
        val xmldoc = XML.load(response.getEntity.getContent)
        Console.println( xmldoc )
        println( "======================" )
        ""
    }
    def putRequest(url:String) = {
        val meth = new HttpPost(url)
        val response:HttpResponse  = httpclient.execute(meth)
        println( "======================" )

        //Console.println(response.getStatusLine())
        //Console.println(response.toString)
        val xmldoc = XML.load(response.getEntity.getContent)
        Console.println( xmldoc )
        println( "======================" )
        ""

    }

    def deleteRequest(url:String) = {
        val meth = new HttpDelete(url)
        val response:HttpResponse  = httpclient.execute(meth)
        println( "======================" )

        //Console.println(response.getStatusLine())
        //Console.println(response.toString)
        val xmldoc = XML.load(response.getEntity.getContent)
        Console.println( xmldoc )
        println( "======================" )
        ""
    }

    private def sendRequest(method:String ,url:String):String = {
        
        val meth = method match {
            case "GET" => new HttpGet(url)
            case "PUT" => new HttpPost(url)
            case "POST" => new HttpPut(url)
            case "DELETE" => new HttpDelete(url)
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
}
