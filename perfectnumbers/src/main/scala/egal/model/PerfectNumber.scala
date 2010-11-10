package egal.model

import net.liftweb._
import mapper._
import http._
import SHtml._
import util._

class PerfectNumber extends LongKeyedMapper[PerfectNumber] with IdPK {
  def getSingleton = PerfectNumber
  
  object MyNumber extends MappedLong(this)
  object LastCheckedNumber extends MappedLong(this)
  object DonatedCalculations extends MappedLong(this)
}

object PerfectNumber extends PerfectNumber with LongKeyedMetaMapper[PerfectNumber]{
  override def fieldOrder = List(MyNumber,LastCheckedNumber)
}