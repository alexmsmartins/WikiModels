package pt.cnbc.wikimodels.client.record

import java.util.prefs.BackingStoreException
import java.lang.reflect.Method
import net.liftweb.common.{Empty, Full, Box}
import net.liftweb.record.{LifecycleCallbacks, Field, MetaRecord, Record}
import collection.mutable.ListBuffer
import xml.Text._
import net.liftweb.http.js.JE.JsObj._
import net.liftweb.http.js.{JsExp, JsObj}
import net.liftweb.util.{JSONParser, FieldError}
import net.liftweb.json.JsonAST.JObject._
import net.liftweb.json.JsonAST.JField._
import net.liftweb.json.JsonAST._
import net.liftweb.record.FieldHelpers._
import net.liftweb.json.{JsonParser, Printer}
import xml.Elem._
import net.liftweb.http.{LiftRules, LiftResponse, Req, SHtml}
import net.liftweb.http.js.{JsExp, JsObj}
import net.liftweb.http.js.JE._
import xml.{Text, Elem, NodeSeq}
import javax.xml.soap.SOAPElementFactory


//  because of WriteConcern

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 19-07-2011
 * Time: 16:49
 */

trait RestRecord[MyType <: RestRecord[MyType]] extends Record[MyType] {
  self : MyType =>

  def createRestRec():MyType

  def readRestRec(url:String):MyType

  def updateRestRec():MyType

  def deleteRestRec():MyType

  /**
   * The protocol, host, port and path of the RESTful service were already defined when making the connection.
   * This only represents the relative URL from the roomt
   */
  def relativeURL = relativeURLasList mkString("/","/","")

  /**
   * Lift friendly representation of a URL.
   */
  protected def relativeURLasList:List[String]
}


trait RestMetaRecord[BaseRecord <: RestRecord[BaseRecord]] extends MetaRecord[BaseRecord] {
  self: BaseRecord =>

  /**
   * Creates a new RestRecord in the RESTful service
   */
  def createRestRec():BaseRecord

  /**
   * Loads an existant RestRecord from the RESTful service
   */
  def readRestRec(url:String):BaseRecord

  /***
   * Updates an existent RestRecord in the RESTful service with new information
   */
  def updateRestRec():BaseRecord

  /**
   * Erases an existent RestRecord from the RESTful service
   */
  def deleteRestRec():BaseRecord


}
