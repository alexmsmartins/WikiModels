package pt.cnbc.wikimodels.client.snippet

import _root_.net.liftweb.common._
import _root_.net.liftweb.http._
import S._
import _root_.net.liftweb.util._
import Helpers._
import scala.xml._


import _root_.pt.cnbc.wikimodels.rest.client.RestfulAccess
import xml.{Elem, NodeSeq, Text}
import alexmsmartins.log.LoggerWrapper

/**
 * TODO: Please document.
 * @author Alexandre Martins
 * Date: 25-04-2011
 * Time: 3:59
 */
class SBMLSnip extends LoggerWrapper{
  //TODO DELETE THIS asap

  def modelMetaId = "#mMetaId *" #> S.param("modelMetaId").openOr("<<handling this is a TODO>>")    //TODO HANDLE ERROR HERE

  def compartmentMetaId = "#cMetaId *" #> S.param("compartmentMetaId").openOr("<<handling this is a TODO>>")     //TODO HANDLE ERROR HERE

  def speciesMetaId = "#sMetaId *" #> S.param("speciesMetaId").openOr("<<handling this is a TODO>>")     //TODO HANDLE ERROR HERE

  def parameterMetaId = "#pMetaId *" #> S.param("parameterMetaId").openOr("<<handling this is a TODO>>")  //TODO HANDLE ERROR HERE

  def functionDefinitionMetaId = "#fdMetaId *" #> S.param("functionDefinitionMetaId").openOr("<<handling this is a TODO>>")      //TODO HANDLE ERROR HERE

  def constraintMetaId = "#ctMetaId *" #> S.param("constraintMetaId").openOr("<<handling this is a TODO>>")       //TODO HANDLE ERROR HERE

  def reactionMetaId = "#rMetaId *" #> S.param("reactionMetaId").openOr("<<handling this is a TODO>>")        //TODO HANDLE ERROR HERE


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
