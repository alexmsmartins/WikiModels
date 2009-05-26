package pt.cnbc.wikimodels.client


import pt.cnbc.wikimodels.rest.client.RestfulAccess
import org.apache.log4j._

/**
 * Hello world!
 *
 */
object App {
    def main(args:Array[String]) = {
        var ra = new RestfulAccess("alex", "alexp")
        println( "======================" )
        ra.sendRequest("GET","http://localhost:8080/wm_server-1.0-SNAPSHOT/resources/user/alex")
        println( "======================" )
        var log  = Logger.getLogger(App.getClass)

        println( "======================" )
        println( "= Hello World!       =" )
        println( "======================" )
    }
}
