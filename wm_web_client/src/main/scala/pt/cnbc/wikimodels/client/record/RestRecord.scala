package pt.cnbc.wikimodels.client.record

import java.util.prefs.BackingStoreException
import java.lang.reflect.Method
import scala.xml._
import net.liftweb.record._
import net.liftweb.record.FieldHelpers._
import net.liftweb.http.{LiftRules, LiftResponse, Req, SHtml}
import net.liftweb.http.js.{JsExp, JsObj}
import net.liftweb.common._
import pt.cnbc.wikimodels.snippet.User


//  because of WriteConcern

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 19-07-2011
 * Time: 16:49
 */

trait RestRecord[MyType <: RestRecord[MyType]] extends Record[MyType] {
  self : MyType =>

  def createRestRec():Box[MyType]

  def readRestRec(url:String):Box[MyType]

  def updateRestRec():Box[MyType]

  def deleteRestRec():Box[MyType]

  /**
   * The protocol, host, port and path of the RESTful service were already defined when making the connection.
   * This only represents the relative URL from the roomt
   */
  def relativeURL = relativeURLasList mkString("/","/","")

  /**
   * Lift friendly representation of a URL.
   */
  protected def relativeURLasList:List[String]

  /**
   * returns Failure boxes for all types of umpredicted status codes that come within a response to WikiModels Server
   */
  protected def handleStatusCodes(status:Int, printableAction:String) :Box[MyType] = {
    status match{
      case x if(x >= 200 && x <300) => ParamFailure("Error " + printableAction + " . The status code "+ status + " should never have appeared.", this)
      case 404 => Failure("Authorization error you don't have permissions to " + printableAction + ": " + User.restfulConnection.getReasonPhrase)
      case _ => ParamFailure("Error " + printableAction + " . It failed with status code "+ status + "", this)
    }
  }
}


trait RestMetaRecord[BaseRecord <: RestRecord[BaseRecord]] extends MetaRecord[BaseRecord] {
  self: BaseRecord =>

  /**
   * Creates a new RestRecord in the RESTful service
   */
  def createRestRec():Box[BaseRecord]

  /**
   * Loads an existant RestRecord from the RESTful service
   */
  def readRestRec(url:String):Box[BaseRecord]

  /***
   * Updates an existent RestRecord in the RESTful service with new information
   */
  def updateRestRec():Box[BaseRecord]

  /**
   * Erases an existent RestRecord from the RESTful service
   */
  def deleteRestRec():Box[BaseRecord]


}
