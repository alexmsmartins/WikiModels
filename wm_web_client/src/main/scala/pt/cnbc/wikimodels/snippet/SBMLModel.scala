package pt.cnbc.wikimodels.snippet

import _root_.scala.xml.{NodeSeq, Text}
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import _root_.net.liftweb.http.S
import _root_.java.util.Date
import Helpers._


/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 25-04-2011
 * Time: 3:59
 * To change this template use File | Settings | File Templates.
 */

class SBMLModel{
  def modelMetaId = "#mMetaId *" #> S.param("modelId").openOr("<<handling this is a TODO>>")
}