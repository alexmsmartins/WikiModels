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
  def create():MyType
  def read(url:String):MyType
  def update():MyType
  def delete():MyType
}


trait RestMetaRecord[BaseRecord <: RestRecord[BaseRecord]] extends MetaRecord[BaseRecord] {
  self: BaseRecord =>

  def create():BaseRecord
  def read(url:String):BaseRecord
  def update():BaseRecord
  def delete():BaseRecord
}
