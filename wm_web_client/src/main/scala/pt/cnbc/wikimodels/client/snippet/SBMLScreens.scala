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
import pt.cnbc.wikimodels.client.record.{SBMLModelRecord, FunctionDefinitionRecord}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 20/11/12
  *         Time: 5:01 PM */
class CreateModelScreen extends LiftScreen {
  object model extends ScreenVar(SBMLModelRecord.createRecord)

  addFields(() => model.is)

  def finish() = {
    S.notice("Model was created successfully!")
  }
}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 25/10/12
  *         Time: 7:01 PM */
class CreateFunctionDefinitionScreen extends LiftScreen {
  object function extends ScreenVar(FunctionDefinitionRecord.createRecord)

  //override def screenTop =  <b>A single screen with some input validation</b>
  addFields(() => function.is)

  def finish() = {
    S.notice(<div>Validation is going in here <br/> Second line of validation goes here!</div>)
  }
}

/** TODO: Please document.
  * @author Alexandre Martins
  *         Date: 20/11/12
  *         Time: 4:45 PM */
class EditFunctionDefinitionScreen extends LiftScreen {
  object function extends ScreenVar(
    FunctionDefinitionRecord.readRestRec(S.param("functionDefinitioMetaId").openTheBox).openTheBox
  )

  //override def screenTop =  <b>A single screen with some input validation</b>
  addFields(() => function.is)

  def finish() = {
    S.notice(<div>Validation is going in here <br/> Second line of validation goes here!</div>)
  }
}
