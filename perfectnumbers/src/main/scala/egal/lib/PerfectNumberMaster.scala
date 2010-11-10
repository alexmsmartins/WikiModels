package egal.lib

import scala.collection.mutable.HashMap

import net.liftweb.actor._
import _root_.net.liftweb._
import http._
import util._
import Helpers._
import net.liftweb.common._
import net.liftweb.mapper._

import egal.model.PerfectNumber
import egal.comet._

import egal.snippet.JobPackage

object PerfectNumberMaster extends LiftActor with Loggable{
    
  private val CALCULATIONS_PER_TICKET = 100000
  type CPackage = (Long, Int) // Number , Intervall

  
  case class Client(pns : PerfectNumberStatus) {
    var atWork : Option[CPackage] = Empty
  }
  

  var clients = new HashMap[Int, Client]() // clientId -> clientRef

  var ids = Stream.from(1)
  
  def nextClientId = {
    var id = ids.head
    ids = ids.tail
    id
  }
  
  var lastNumber : Long = {

    val allPerfectNumbers = PerfectNumber.findAll(OrderBy(PerfectNumber.MyNumber, Descending))
    
    if(allPerfectNumbers.isEmpty) {
      val firstNumber = PerfectNumber.create
      firstNumber MyNumber(6) LastCheckedNumber(6) DonatedCalculations(0) 
      
      firstNumber.save
      6  
    } else allPerfectNumbers.head.LastCheckedNumber.is
  }
  var cPackages = List[CPackage]()  
  
  var collectedFactors = new HashMap[Long, List[Long]]
  
  def createNewPackages = {
    lastNumber += 1
    val upLimit = Math.sqrt(lastNumber)
    val intervallCount = Math.ceil(upLimit / CALCULATIONS_PER_TICKET).toInt
    cPackages = (0 to intervallCount - 1).map( i => (lastNumber, i) ).toList
  }
  
     
  def getNextPackage : CPackage = {
    if(cPackages.isEmpty) createNewPackages 
    
    val ret = cPackages.head
    cPackages = cPackages.tail
      
    ret
  }
  
  def sendPackage(clientId : Int) = {
    
      def calcJobPackage(cp : CPackage) : JobPackage = {
        val intervallStart = if(cp._2 == 0) 1 else cp._2 * CALCULATIONS_PER_TICKET
        val intervallEnd = intervallStart + CALCULATIONS_PER_TICKET
        new JobPackage(clientId,intervallStart,intervallEnd,cp._1)
      }

      
    val client : Client = clients(clientId) 
    
    val p = getNextPackage
    client.atWork = Some(p)

    val jp = calcJobPackage(p)
    logger.info("New Package send for: " + clientId)
    client.pns ! DoCalculation(jp)
    
    val donCalcs = Math.min( jp.end - jp.start, jp.master).toLong  
    lastPerfect DonatedCalculations(lastPerfect.DonatedCalculations.is + donCalcs) save
  }
  
  def subscribePerfectNumberMaster(pstat: PerfectNumberStatus) = {
    val clId = nextClientId
        
    clients += ( clId -> new Client(pstat) )
    sendPackage(clId)
  }
 
  def unSubscribePerfectNumberMaster(pstat: PerfectNumberStatus) = {

    def redoWork(client: Client) =   client.atWork match {
        case Some(x) => cPackages ::= x
        case _ => logger.info("Client unsubscribed without  active process.")
    }
    
    clients.toList.find(x => x._2 == pstat) match {
      case Some(c) => redoWork(clients.removeKey(c._1) get) 
      case _       => logger.info("Client not in subscription List.")
    } 
  }
 
  
  def lastPerfect = PerfectNumber.findAll(OrderBy(PerfectNumber.MyNumber, Descending), MaxRows(1)).head
  
  def calcDone(employeId: Int, employeResult: List[Long] ) = {
    val numFromFacs = clients.get(employeId).get.atWork.get._1
    val recentResult =  collectedFactors.get(numFromFacs) getOrElse Nil 
    collectedFactors +=  ( numFromFacs -> (employeResult ::: recentResult) )  
    
     val lp = lastPerfect
     val checkComplete = lp.LastCheckedNumber.is + 1
     
     val checkA =  cPackages.filter(cp => cp._1 == checkComplete) 
     val checkB = ((clients.values.map(x => x.atWork).toList.flatten):List[CPackage]).filter(cp => cp._1 == checkComplete) 
     
     if(checkA.isEmpty && checkB.isEmpty){
       
       val facs = (collectedFactors.get(checkComplete) getOrElse Nil removeDuplicates).remove(_ == checkComplete)
       collectedFactors -= checkComplete
       if(facs.reduceLeft(_ + _) == checkComplete) {
         val newNumber = PerfectNumber.create
         newNumber MyNumber(checkComplete) LastCheckedNumber(checkComplete) DonatedCalculations(lp.DonatedCalculations.is) 
         newNumber.save
       } else {
         lp.LastCheckedNumber(checkComplete)
         lp.save
       }
     }
     
     sendPackage(employeId)
     
  }
  
  protected def messageHandler = {
    case CalcDone(employeId, employeResult) => calcDone(employeId, employeResult)
    case SubscribePerfectNumberMaster(pstat) => subscribePerfectNumberMaster(pstat)
    case UnSubscribePerfectNumberMaster(pstat) => unSubscribePerfectNumberMaster(pstat)
    case m => logger.error("Unidentified Command: " + m)
  }
    
  case class SubscribePerfectNumberMaster(pStat: PerfectNumberStatus)
  case class UnSubscribePerfectNumberMaster(pStat: PerfectNumberStatus)
  case class CalcDone(employeId: Int, factors: List[Long])
}