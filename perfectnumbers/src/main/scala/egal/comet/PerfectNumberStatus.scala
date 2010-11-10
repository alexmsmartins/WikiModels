package egal {
package comet {

import scala.xml._

import net.liftweb.common._
import net.liftweb.http._
import net.liftweb.http.js._
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.JE._

import net.liftweb.mapper._

import egal.model.PerfectNumber
import egal.lib.PerfectNumberMaster
import egal.snippet.JobPackage

class PerfectNumberStatus extends CometActor with Loggable{
  
  var donations : Long = 0
   
  override def defaultPrefix = Full("pfn")
  
  override def render = bind(
    "lastPerfect" -> <span id="lastPerfect">{lastPefect}</span>,
    "myDonation" -> <span id="myDonation">{myDonation}</span>,
    "perfectNumbers" -> <span id="perfectNumbers">{perfectNumbers}</span>
  )
  
  def perfectNumbers : NodeSeq = {PerfectNumber.findAll(OrderBy(PerfectNumber.MyNumber, Ascending)).map(pn => pn.MyNumber.is).toString }
  def lastPefect : NodeSeq = {PerfectNumber.findAll(OrderBy(PerfectNumber.MyNumber, Descending), MaxRows(1)).head.toHtml}
  def myDonation : NodeSeq = {donations} 
    
  override def lowPriority : PartialFunction[Any, Unit] = {
    case DoCalculation(c) => {
      donations += (Math.min(c.end,c.master) - c.start).toLong      
      partialUpdate(
        SetHtml("lastPerfect", <span id="lastPefect">{lastPefect}</span>) &
        SetHtml("myDonation", <span id="myDonation">{myDonation}</span>) &
        SetHtml("perfectNumbers", <span id="perfectNumbers">{perfectNumbers}</span>) &
        //OnLoad(JsRaw("findFactors('"+ c.toJson +"');"))
        OnLoad(JsRaw(c.toJsCall))  
      )
    }
    case m => logger.error("Unidentified Command: " + m)
  }
     
  override def localSetup {
     logger.info("PerfectNumberStatus client setup")
     PerfectNumberMaster ! new PerfectNumberMaster.SubscribePerfectNumberMaster(this)
     super.localSetup
  }
  
   override def localShutdown {
     logger.info("PerfectNumberStatus client shutdown")
    PerfectNumberMaster ! new PerfectNumberMaster.UnSubscribePerfectNumberMaster(this)
    super.localShutdown
  }

}

case class DoCalculation(pack: JobPackage)

}}