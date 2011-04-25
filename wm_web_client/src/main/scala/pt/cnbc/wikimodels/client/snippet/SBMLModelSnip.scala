package pt.cnbc.wikimodels.client.snippet

import _root_.scala.xml.{NodeSeq, Text}
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import _root_.net.liftweb.http.S
import _root_.java.util.Date
import Helpers._

import _root_.pt.cnbc.wikimodels.rest.client.RestfulAccess
import _root_.pt.cnbc.wikimodels.snippet.User

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 25-04-2011
 * Time: 3:59
 * To change this template use File | Settings | File Templates.
 */

class SBMLModelSnip{
  def modelMetaId = "#mMetaId *" #> S.param("modelId").openOr("<<handling this is a TODO>>")

  def entireModelInXML ={
    val rest = User.getRestful
    val model = rest.getRequest("/model/" + S.param("modelId").openOr("TODO: Error") )
    "#xmlmodel *" #> model.toString()
  }
}