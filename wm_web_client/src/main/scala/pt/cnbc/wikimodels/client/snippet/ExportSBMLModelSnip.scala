package pt.cnbc.wikimodels.client.snippet

import _root_.net.liftweb.common._
import _root_.net.liftweb.http._
import S._
import _root_.net.liftweb.util._
import Helpers._
import _root_.scala.xml._

import _root_.pt.cnbc.wikimodels.rest.client.RestfulAccess

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 26-04-2011
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */

class ExportSBMLModelSnip {

  def doIt = {
    val model = User.restfulConnection.getRequest("/model/" + S.param("modelMetaId").openOr("TODO: Error export model") )
    val completeModel =
      <sbml xmlns="http://www.sbml.org/sbml/level2/version4" level="2" version="4">{
        model
       }</sbml>

  }

}