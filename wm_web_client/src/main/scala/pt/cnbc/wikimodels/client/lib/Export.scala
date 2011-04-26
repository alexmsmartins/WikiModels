package pt.cnbc.wikimodels.client.lib

import _root_.net.liftweb.common._
import _root_.net.liftweb.http._
import S._
import _root_.net.liftweb.util._
import Helpers._
import _root_.scala.xml._

import _root_.pt.cnbc.wikimodels.rest.client.RestfulAccess
import _root_.pt.cnbc.wikimodels.snippet.User

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 26-04-2011
 * Time: 20:51
 * To change this template use File | Settings | File Templates.
 */

object Export {
  def asSBMLL2V4(modelMetaId:String):Box[LiftResponse] = {
    val model = User.getRestful.getRequest("/model/" + modelMetaId )
    val completeModel:Elem = <sbml xmlns="http://www.sbml.org/sbml/level2/version4" level="2" version="4">{
        model
       }</sbml>
    Full(XmlResponse(completeModel))
  }
}