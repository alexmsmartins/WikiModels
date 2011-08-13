/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.client


import org.apache.log4j._
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import scala.xml.XML
import scala.xml.Xhtml

import pt.cnbc.wikimodels.rest.client.BasicAuth
import pt.cnbc.wikimodels.rest.client.RestfulAccess

/**
 * Even though not all code is used in the initial protoype
 * this object should be kept so as to use it in the future
 *
 */
object AppDigest {

    /**
     * Assumng wikimodels is working with the followning configuration
     *  - host: localhost
     *  - port: 8080
     *  - contextRoot: resources
     *  - User: admin
     *  - Password: admin
     * this method can be used to do the integration testing
     * of the Wikimodels server.
     */
    def main(args:Array[String]) = {
        try {
            var ra = new RestfulAccess("localhost",
                                       8080,
                                       "resources/",
                                       "admin",
                                       "admin",
                                       BasicAuth.startWithBasicAuth)
            println( "======================" )
            ra.getRequest("/user/admin")
            println( "======================" )
            var log  = Logger.getLogger(this.getClass)

            println( "======================" )
            println( "= Hello World!       =" )
            println( "======================" )
            //basicAuthentication
            //digestAuthentication

        } catch {
            case e:Exception => {
                    Console.println("INTEGRATION TESTING FAILED")
                    e.printStackTrace
                }
        }
    }

    def digestAuthentication:Unit = {
        //TODO Complete the implementation of digest authentication.
        //Now the project is using Basic authentication
        var httpclient = new DefaultHttpClient
        // Create the necessary credetials to do BASIC authetication
        var authScheme =  new AuthScope("localhost", 8080)
        var credentials = new UsernamePasswordCredentials("admin", "admin")
        httpclient.getCredentialsProvider().setCredentials(
            authScheme,
              credentials);


        var localcontext = new BasicHttpContext();
        // Generate DIGEST scheme object, initialize it and stick it to
        // the local execution context
        //var digestAuth = new DigestScheme();
        // Suppose we already know the realm name
        //        digestAuth.overrideParamter("realm", "userauth");
        // Suppose we already know the expected nonce value
        //digestAuth.overrideParamter("nonce", "whatever");
        //        localcontext.setAttribute("preemptive-auth", digestAuth);

        // Add as the first request interceptor
        //        httpclient.addRequestInterceptor(new PreemptiveAuth(), 0);
        // Add as the last response interceptor
        //        httpclient.addResponseInterceptor(new PersistentDigest());

        var targetHost = new HttpHost("localhost", 8080, "http");

        var httpget = new HttpGet("/ServletStateless-war/servlet");

        System.out.println("executing request: " + httpget.getRequestLine());
        System.out.println("to target: " + targetHost);

        var responseBody = httpclient.execute(targetHost, httpget/*, localcontext*/);
        var entity = responseBody.getEntity();

        Console.println("------------------CONTENT----------------------")
        val dataInputStream = new java.io.DataInputStream(responseBody.getEntity.getContent)
        var str = ""
        var tmpstr = ""
        while(tmpstr != null){
            str += tmpstr
            tmpstr = dataInputStream.readLine
        }
        Console.println(str)
        Console.println("---------------END OF CONTENT------------------")
        Console.println("----------------------------------------")

        // When HttpClient instance is no longer needed,
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown()
    }

    def basicAuthentication:Unit = {
        var httpclient = new DefaultHttpClient
        // Create the necessary credetials to do BASIC authetication
        var authScheme =  new AuthScope("localhost", 8080)
        var credentials = new UsernamePasswordCredentials("admin", "admin")
        httpclient.getCredentialsProvider().setCredentials(
            authScheme,
              credentials);

        var httpget = new HttpGet("http://localhost:8080/ServletStateless-war/servlet")

        Console.println("executing request " + httpget.getURI());

        // Create a response handler
        var responseBody = httpclient.execute(httpget)
        Console.println("------------------CONTENT----------------------")
        val dataInputStream = new java.io.DataInputStream(responseBody.getEntity.getContent)
        var str = ""
        var tmpstr = ""
        while(tmpstr != null){
            str += tmpstr
            tmpstr = dataInputStream.readLine
        }
        Console.println(str)
        Console.println("---------------END OF CONTENT------------------")
        Console.println("----------------------------------------")

        // When HttpClient instance is no longer needed,
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown()
    }
}

class PreemptiveAuth extends HttpRequestInterceptor {

    /*
     * This method can throw
     * HttpException
     * IOException*/
    def process (request:HttpRequest, context:HttpContext) ={
        var authState:AuthState = context.getAttribute(
            ClientContext.TARGET_AUTH_STATE).asInstanceOf[AuthState]
        // If no auth scheme avaialble yet, try to initialize it preemptively
        if (authState.getAuthScheme() == null) {
            var authScheme = context.getAttribute(
                "preemptive-auth").asInstanceOf[AuthScheme];
            var credsProvider = context.getAttribute(
                ClientContext.CREDS_PROVIDER).asInstanceOf[CredentialsProvider ];
            var targetHost = context.getAttribute(
                ExecutionContext.HTTP_TARGET_HOST).asInstanceOf[HttpHost];
            if (authScheme != null) {
                var creds = credsProvider.getCredentials(
                    new AuthScope(
                        targetHost.getHostName(),
                          targetHost.getPort()));
                if (creds == null) {
                    throw new HttpException("No credentials for preemptive authentication");
                }
                authState.setAuthScheme(authScheme);
                authState.setCredentials(creds);
            }
        }

    }

}

class PersistentDigest extends HttpResponseInterceptor {

    /*
     * This method can throw
     * HttpException
     * IOException*/
    def process (response:HttpResponse, context:HttpContext) ={
        var authState = context.getAttribute(
            ClientContext.TARGET_AUTH_STATE).asInstanceOf[AuthState];
        if (authState != null) {
            var authScheme = authState.getAuthScheme();
            // Stick the auth scheme to the local context, so
            // we could try to authenticate subsequent requests
            // preemptively
            if (authScheme.isInstanceOf[DigestScheme]) {
                context.setAttribute("preemptive-auth", authScheme);
            }
        }
    }
}
