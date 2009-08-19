/*
 * RestFulAccess.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package pt.cnbc.wikimodels.rest.client

import scala.xml.XML

import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.io.IOException
import java.net.URI
import java.nio.charset.Charset

import org.apache.http.HttpEntity
import org.apache.http.HttpEntityEnclosingRequest
import org.apache.http.HttpResponse
import org.apache.http.StatusLine
import org.apache.http.auth.AuthScope
import org.apache.http.auth.Credentials
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPut
import org.apache.http.client.methods.HttpDelete
import org.apache.http.client.methods.HttpRequestBase
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.log4j._

import scala.xml.Elem

/**
 * Class which wraps the api to connect to WikiModels or even to another
 * restful Web Service
 * the parameter startFunc receives a function that starts the client wiht the
 * intended authentication scheme.
 */
class RestfulAccess(val host:String,
                    val port:Int,
                    val contextRoot:String,
                    val username:String,
                    val password:String,
                    val startFunc:(RestfulAccess) => Unit) {
    var lastStatusLine:StatusLine = null
    val log  = Logger.getLogger(this.getClass)

    //checks if everything is OK with the supplied host,  port and contextRoot
    val uri:URI = new URI(
        "http://" + host + ":" + port + "/" + contextRoot)
    log.debug(this.getClass + " as URI " + uri.toString)
    
    var httpclient:HttpClient  = null
    var authScope:AuthScope =  null
    var credentials:Credentials = null
    this.startFunc(this)

    /**
     * The anonymous user will be known throughout every WikiModels Wiki
     * He will get the minimum permissions every user will ever get
     */
    def this(host:String,
             port:Int,
             contextRoot:String,
             startFunc:(RestfulAccess) => Unit) = {
        this(host, port, contextRoot,"anonymous","anonymous", startFunc)
    }
    
    def getRequest(url:String):Elem = {
        log.debug("Starting GET request to " + url)
        val uri = new URI("http://" + host + ":" + port +
                          contextRoot + url)
        val meth =  new HttpGet(uri)
        val response:HttpResponse  = httpclient.execute(meth)
        log.debug( "==========After response============" )
        lastStatusLine = response.getStatusLine
        log.debug( response.getStatusLine())
        log.debug( response.toString)
        log.debug( "======================" )
        var xmldoc:Elem = null
        if( lastStatusLine.getStatusCode == 200  ){
            log.debug( xmldoc )
            log.debug( "======================" )
            xmldoc = XML.load(response.getEntity.getContent)
        }
        xmldoc

    }

    def postRequest(url:String, content:Elem):URI = {
        log.debug("Starting POST request to " + url)
        val uri = new URI("http://" + host + ":" + port +
                          contextRoot + url)
        val meth = new HttpPost(uri)
        this.setRequestEntity(content, meth)
        val response:HttpResponse  = httpclient.execute(meth)
        log.debug( "==========After response============" )
        lastStatusLine = response.getStatusLine
        log.debug( response.getStatusLine())
        log.debug( response.toString)
        val is = response.getEntity.getContent

        val uriFinal = new URI( response.getFirstHeader("Location").getValue )
        log.debug( "======================" )
        uriFinal
    }


    def putRequest(url:String, content:Elem) = {
        log.debug("Starting PUT request to " + url)
        val uri = new URI("http://" + host + ":" + port +
                          contextRoot + url)
        val meth = new HttpPut(uri)
        this.setRequestEntity(content, meth)
        val response:HttpResponse  = httpclient.execute(meth)
        log.debug( "==========After response============" )
        lastStatusLine = response.getStatusLine
        log.debug( response.getStatusLine())
        log.debug( response.toString)
        log.debug( "======================" )
    }

    def deleteRequest(url:String) = {
        log.debug("Starting DELETE request to " + url)
        val uri = new URI("http://" + host + ":" + port +
                          contextRoot + url)
        val meth = new HttpDelete(uri)
        val response:HttpResponse  = httpclient.execute(meth)
        log.debug( "==========After response============" )
        lastStatusLine = response.getStatusLine
        log.debug( response.getStatusLine())
        log.debug( response.toString)
        log.debug( "======================" )
    }

    //---StautsLine aaccessors --

    def getProtocolVersion = {
        lastStatusLine.getProtocolVersion
    }
    def getStatusCode = {
        lastStatusLine.getStatusCode
    }
    def getReasonPhrase = {
        lastStatusLine.getReasonPhrase
    }


    protected def setRequestEntity(elem:Elem, method:HttpEntityEnclosingRequest) = {
        if(elem != null) {
            val ent = new StringEntity( elem.toString )
            ent.setContentType("application/xml")
            method.setEntity(ent)
        }
        method
    }

    protected def getResponseEntiy(response:HttpResponse) = {
        // Get hold of the response entity
        val entity:HttpEntity = response.getEntity();

        // If the response does not enclose an entity, there is no need
        // to worry about connection release
        if (entity != null) {
            val instream:InputStream = entity.getContent();
            try {

                val reader:BufferedReader  = new BufferedReader(
                    new InputStreamReader(instream));
                // do something useful with the response
                System.out.println(reader.readLine());

            } catch {
                case ex:IOException => {

                        // In case of an IOException the connection will be released
                        // back to the connection manager automatically
                        throw ex;
                    }
                case ex:RuntimeException => {

                        // In case of an unexpected exception you may want to abort
                        // the HTTP request in order to shut down the underlying
                        // connection and release it back to the connection manager.
                        this.restart
                        throw ex;
                    }
            } finally {

                // Closing the input stream will trigger connection release
                instream.close();

            }
        }
    }

    def restart = {
        this.close
        this.startFunc
    }

    /**
     * Closes the HttpConnection established by RestfulAccess
     * This method should be called when the class is no longer needed
     * since it frees Operating system resurces that the GC cannot free.
     */
    def close = {
        log.debug("Closing Resources for client connection in " + this.getClass)
        // When HttpClient instance is no longer needed,
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown()
    }
}


/**
 * An object that contains the function that initializes
 * RestfulAccess to Basic Authetication
 *
 */
object BasicAuth {

    /**
     * Initializes httpclient with its respective authetiation scheme and credentials
     * It should be entered into
     */
    def startWithBasicAuth(ra:RestfulAccess) = {
        ra.httpclient = new DefaultHttpClient
        ra.authScope =  new AuthScope(ra.host, ra.port)
        ra.credentials = new UsernamePasswordCredentials(ra.username, ra.password)
        ra.httpclient.asInstanceOf[DefaultHttpClient]
        .getCredentialsProvider.setCredentials(ra.authScope,
                                               ra.credentials)
    }
}
