/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.client.lib

import _root_.net.liftweb.common._
import _root_.net.liftweb.http._
import S._
import _root_.net.liftweb.util._
import Helpers._
import _root_.scala.xml._

import _root_.pt.cnbc.wikimodels.rest.client.RestfulAccess
import _root_.pt.cnbc.wikimodels.snippet.User
import collection.immutable.List._

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 26-04-2011
 * Time: 20:51
 * To change this template use File | Settings | File Templates.
 */

object Export {

  def asSBMLL2V4(modelMetaId:String):Box[LiftResponse] = {
    val model = User.restfulConnection.getRequest("/model/" + modelMetaId )
    val completeModel:Elem = <sbml xmlns="http://www.sbml.org/sbml/level2/version4" level="2" version="4">{
        model
       }</sbml>
    val arrayByte = ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+completeModel.toString).getBytes("UTF-8")

    //Content-type and Content-disposition are both important to force the appearance of the Save as dialog
    Full(InMemoryResponse(arrayByte,
                          ("Content-Length", arrayByte.length.toString) ::
                          ("content-disposition","attachment; filename=" + modelMetaId + ".xml") ::
                          ("Content-Type", "application/x-download; charset=utf-8") :: Nil,
                          Nil,
                          200))
  }
}

