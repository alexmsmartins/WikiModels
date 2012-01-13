package pt.cnbc.wikimodels.client.snippet

import _root_.net.liftweb.common._
import _root_.net.liftweb.http._
import S._
import _root_.net.liftweb.util._
import Helpers._
import scala.xml._


import _root_.pt.cnbc.wikimodels.rest.client.RestfulAccess
import _root_.pt.cnbc.wikimodels.snippet.User
import xml.{Elem, NodeSeq, Text}
import alexmsmartins.log.LoggerWrapper

/**
 * TODO: Please document.
 * @author Alexandre Martins
 * Date: 25-04-2011
 * Time: 3:59
 */
class SBMLModelSnip extends LoggerWrapper{
  //TODO DELETE THIS asap

  def modelMetaId = "#mMetaId *" #> S.param("modelMetaId").openOr("<<handling this is a TODO>>")

  def compartmentMetaId = "#mMetaId *" #> S.param("compartmentMetaId").openOr("<<handling this is a TODO>>")

  def speciesMetaId = "#mMetaId *" #> S.param("speciesMetaId").openOr("<<handling this is a TODO>>")


  def entireModelInXML = {
    val model = User
      .restfulConnection
      .getRequest {
      "/model/" + debug("Getting /model/ {} from server",
      S.param("modelMetaId").openOr("TODO: Error"))}
    "#xmlmodel *" #> model.toString()
  }

  def entireModelInHTML = {
    val model = entireModelInXML
    model
  }

  def modelMetaIdToAttr(html:NodeSeq):NodeSeq =
    bind("input",html, AttrBindParam("val",
                                     S.param("modelMetaId").openOr("<<handling this is a TODO>>"),
                                     "value"))

}
