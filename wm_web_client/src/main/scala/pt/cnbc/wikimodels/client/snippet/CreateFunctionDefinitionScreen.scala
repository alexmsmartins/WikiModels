/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.client.snippet

import net.liftweb.common._

import net.liftweb.http._
import S._

import net.liftweb.util._
import Helpers._

import scala.xml._
import pt.cnbc.wikimodels.client.record.FunctionDefinitionRecord

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 10/25/12
  *         Time: 7:01 PM */
class CreateFunctionDefinitionScreen extends LiftScreen {
  object function extends ScreenVar(FunctionDefinitionRecord.createRecord)

  //override def screenTop =  <b>A single screen with some input validation</b>
  addFields(() => function.is)

  def finish() = {
    S.notice(<div>Validation is going in here <br/> Second line of validation goes here!</div>)
  }
}
