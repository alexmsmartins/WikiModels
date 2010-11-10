package egal {
package snippet {

import scala.xml.NodeSeq

import net.liftweb._
import http._
import common._
import util._
import js._
import JsCmds._
import JE._
import egal.lib.PerfectNumberMaster

import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonDSL._


case class JobPackage(ticket: Int, 
                   start: Long, 
                   end: Long, 
                   master: Long) {
  import net.liftweb.json._
  import net.liftweb.json.JsonDSL._
  def toJson = {
    val json = ("ticket" -> ticket) ~ 
      ("start" -> start) ~ 
      ("end"-> end) ~
      ("master" -> master)
    
    compact(JsonAST.render(json)) 
  }
  
  def toJsCall = "$('#some-div').html($('#clientEngine').get(0).doCalc("+ ticket +", "+ start +", "+ end +", "+ master +"));"
  
}

object PassCrunch extends Loggable{
  
  implicit val formats = net.liftweb.json.DefaultFormats
  
  def PMCall(s: String) = { 
    
    logger.info("PMCall "  + s)
    
    val cResJs = net.liftweb.json.JsonParser.parse(s) 
    val ticket  = (cResJs \\ "ticket").extract[Int]

    // TODO: 2.8 -> facs.extract[List[Long]]
    val facs = for{
      JInt(fac) <- cResJs \\ "factors"
    } yield fac.extract[Long]
    
    PerfectNumberMaster ! PerfectNumberMaster.CalcDone(ticket , facs)
    Empty
  }
  
  def unapply(in: Any): Option[String] = in match {
    case s: String => PMCall(s)
    case _ => None
  }
}

object PerfectNumberHandler extends SessionVar[JsonHandler](
  new JsonHandler {
    def apply(in: Any): JsCmd =  in match {
      	case JsonCmd("replyResult", resp, PassCrunch(s), _) => Call(resp, s)
        case _ => Noop
      }
  }
)

object PerfectNumberCom extends DispatchSnippet { 
  val dispatch = Map("render" -> buildFuncs _)
  
  def buildFuncs(in: NodeSeq): NodeSeq = {
    Script(PerfectNumberHandler.is.jsCmd &
      Function("replyResult", 
               List("callback", "str"), 
               PerfectNumberHandler.is.call("replyResult", 
                                            JsVar("callback"),
                                            JsVar("str")))
    )
  }
}

}}