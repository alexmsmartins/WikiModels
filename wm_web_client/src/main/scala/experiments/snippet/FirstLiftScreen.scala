/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

package experiments.snippet

import net.liftweb.common._

import net.liftweb.http._
import S._

import net.liftweb.util._
import Helpers._

import net.liftweb.http.{LiftScreen,S}

import scala.xml._

/** TODO: Please document.
 *  @author Alexandre Martins
 *  Date: 10/23/12
 *  Time: 4:18 PM */
class FirstLiftScreen extends LiftScreen {

  val flavor = field("aaaa",1)

  protected def finish() {
    S.redirectTo("/")
  }
}
