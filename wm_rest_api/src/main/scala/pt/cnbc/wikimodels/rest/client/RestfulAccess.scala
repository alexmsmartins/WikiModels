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

import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPut
import org.apache.http.client.methods.HttpDelete
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.impl.auth.BasicScheme
import org.apache.http.client.HttpClient
import org.apache.http.client.protocol.ClientContext
import org.apache.http.auth.{AuthScope, Credentials, UsernamePasswordCredentials}
import scala.xml.Elem
import org.apache.http.protocol.HttpContext
import org.apache.http._
import org.slf4j.{LoggerFactory, Logger}

/**
 * Class which wraps the api to connect to WikiModels or even to another
 * restful Web Service
 * the parameter startFunc receives a function that starts the client wiht the
 * intended authentication scheme.
 * IT IS IMPORTANT TO NOT CHANGE THIS CODE TO A MORE IDIOMATIC SCALA since it is supposed to be used by any JVM language.
 * This means NO scala.Option, net.liftweb.common.Box or even tuples
 */
class RestfulAccess(val host: String,
                    val port: Int,
                    val contextRoot: String,
                    val username: String,
                    val password: String,
                    val startFunc: (RestfulAccess) => Unit) {
  //Even though this looks easy to use, the last paramenter might cause problems for other JVM languages.
  // TODO: So, it shoul
  var lastStatusLine: StatusLine = null
  val log = LoggerFactory.getLogger(this.getClass)

  //checks if everything is OK with the supplied host,  port and contextRoot
  val uri: URI = new URI(
    "http://" + host + ":" + port + "/" + contextRoot)
  log.debug(this.getClass + " as URI " + uri.toString)

  var httpclient: DefaultHttpClient = null
  var authScope: AuthScope = null
  var credentials: Credentials = null
  var targetHost: HttpHost = null

  /**
   * null inplies the use of the default Httpcontext
   */
  var localContext: HttpContext = null

  this.startFunc(this)


  /**
   *  The anonymous user will be known throughout every WikiModels Wiki
   * He will get the minimum permissions every user will ever get
   */
  def this(host: String,
           port: Int,
           contextRoot: String,
           startFunc: (RestfulAccess) => Unit) = {
    this (host, port, contextRoot, "anonymous", "anonymous", startFunc)
  }

  def getRequest(url: String): Elem = {
    log.debug("Starting GET request to " + url)
    val uri = new URI("http://" + host + ":" + port +
            contextRoot + url)
    val meth = new HttpGet(uri)
    //try {
    val response: HttpResponse = httpclient.execute(meth, this.localContext)
    log.debug("==========After response============")
    lastStatusLine = response.getStatusLine
    log.debug("Response's status code was {}", response.getStatusLine())
    log.debug(response.toString)
    log.debug("======================")
    var xmldoc: Elem = null
    if (lastStatusLine.getStatusCode == 200) {
      xmldoc = XML.load(response.getEntity.getContent)
      log.debug("Response's content was {} ", xmldoc)
      log.debug("======================")

    }
    /*} finally
      {
        meth
      }*/
    xmldoc
  }

  def postRequest(url: String, content: Elem): URI = {
    log.debug("Starting POST request to " + url)
    val uri = new URI("http://" + host + ":" + port +
            contextRoot + url)
    val meth = new HttpPost(uri)
    this.setRequestEntity(content, meth)
    val response: HttpResponse = httpclient.execute(meth, this.localContext)
    log.debug("==========After response============")
    lastStatusLine = response.getStatusLine
    log.debug("Response's status code was {}", response.getStatusLine())
    log.debug(response.toString)
    // If entity was CREATED(201)
    if (lastStatusLine.getStatusCode == 201) {
      val is = response.getEntity.getContent
      val uriFinal = new URI(response.getFirstHeader("Location").getValue)
      log.debug("======================")
      uriFinal
    } else {
      Console.println("POST request returns null");null
    }
  }


  def putRequest(url: String, content: Elem) = {
    log.debug("Starting PUT request to " + url)
    val uri = new URI("http://" + host + ":" + port +
            contextRoot + url)
    val meth = new HttpPut(uri)
    this.setRequestEntity(content, meth)
    val response: HttpResponse = httpclient.execute(meth, this.localContext)
    log.debug("==========After response============")
    lastStatusLine = response.getStatusLine
    log.debug("Response's status code was {}", response.getStatusLine())
    log.debug(response.toString)
    log.debug("======================")
  }

  def deleteRequest(url: String) = {
    log.debug("Starting DELETE request to " + url)
    val uri = new URI("http://" + host + ":" + port +
            contextRoot + url)
    val meth = new HttpDelete(uri)
    val response: HttpResponse = httpclient.execute(meth, this.localContext)
    log.debug("==========After response============")
    lastStatusLine = response.getStatusLine
    log.debug("Response's status code was {}", response.getStatusLine())
    log.debug(response.toString)
    log.debug("======================")
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

  protected def setRequestEntity(elem: Elem, method: HttpEntityEnclosingRequest) = {
    if (elem != null) {
      val ent = new StringEntity(elem.toString)
      ent.setContentType("application/xml")
      method.setEntity(ent)
    }
    method
  }

  protected def getResponseEntiy(response: HttpResponse) = {
    // Get hold of the response entity
    val entity: HttpEntity = response.getEntity();

    // If the response does not enclose an entity, there is no need
    // to worry about connection release
    if (entity != null) {
      val instream: InputStream = entity.getContent();
      try {

        val reader: BufferedReader = new BufferedReader(
          new InputStreamReader(instream));
        // do something useful with the response
        System.out.println(reader.readLine());

      } catch {
        case ex: IOException => {

          // In case of an IOException the connection will be released
          // back to the connection manager automatically
          throw ex;
        }
        case ex: RuntimeException => {

          // In case of an unexpected exception you may want to abort
          // the HTTP request in order to shut down the underlying
          // connection and release it back to the connection manager.
          this.restart
          throw ex;
        }
      } finally {

        // Closing the input stream will trigger connection release
        if (instream != null) {
          try {
            instream.close();
          } catch {
            case ex: IOException =>
            // ignored
          }
        }
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
 * RestfulAccess to Basic Authentication
 *
 */
object BasicAuth {
  import org.apache.http.auth.{AuthScope, Credentials, AuthScheme, AuthState}
  import org.apache.http.protocol.{HttpContext, ExecutionContext}
  import org.apache.http.client.CredentialsProvider
  import org.apache.http.protocol.BasicHttpContext

  /**
   * Initializes httpclient with its respective authetiation scheme and credentials
   * It should be entered into
   */
  def startWithBasicAuth(ra: RestfulAccess) = {
    ra.httpclient = new DefaultHttpClient
    ra.authScope = new AuthScope(ra.host, ra.port)
    ra.credentials = new UsernamePasswordCredentials(ra.username, ra.password)
    ra.httpclient.asInstanceOf[DefaultHttpClient]
            .getCredentialsProvider.setCredentials(ra.authScope,
      ra.credentials)

    ra.localContext = new BasicHttpContext();

    // Generate BASIC scheme object and stick it to the local
    // execution context
    var basicAuth:BasicScheme = new BasicScheme();
    ra.localContext.setAttribute("preemptive-auth", basicAuth);

    // Add as the first request interceptor
    ra.httpclient.addRequestInterceptor(new PreemptiveAuth(), 0);

    ra.targetHost = new HttpHost(ra.host, ra.port, "http");
  }
    class PreemptiveAuth extends HttpRequestInterceptor {
      def process(
              request: HttpRequest,
              context: HttpContext) = {

        var authState: AuthState = context.getAttribute(
          ClientContext.TARGET_AUTH_STATE).asInstanceOf[AuthState]

        // If no auth scheme avaialble yet, try to initialize it preemptively
        if (authState.getAuthScheme() == null) {
          var authScheme: AuthScheme = context.getAttribute(
            "preemptive-auth").asInstanceOf[AuthScheme]
          var credsProvider: CredentialsProvider = context.getAttribute(
            ClientContext.CREDS_PROVIDER).asInstanceOf[CredentialsProvider]
          var targetHost: HttpHost = context.getAttribute(
            ExecutionContext.HTTP_TARGET_HOST).asInstanceOf[HttpHost]
          if (authScheme != null) {
            var creds: Credentials = credsProvider.getCredentials(
              new AuthScope(
                targetHost.getHostName(),
                targetHost.getPort()));
            if (creds == null) {
              throw new HttpException("No credentials for preemptive authentication")
            }
            authState.setAuthScheme(authScheme);
            authState.setCredentials(creds);
          }
        }

      }

  }
}
