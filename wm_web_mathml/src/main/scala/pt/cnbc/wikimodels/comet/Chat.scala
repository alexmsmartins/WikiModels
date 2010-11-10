package pt.cnbc.wikimodels.comet

/**
 * User: alex
 * Date: 21/Out/2010
 * Time: 0:52:04
 */

import net.liftweb._
import http._
import actor._

class ChatServer extends LiftActor with ListenerManager {
  private var mathmlMessage = List("")

  def createUpdate = mathmlMessage

  override def lowPriority = {
    case s: String => mathmlMessage ::= s; updateListeners()
  }
}

class Chat extends CometActor with CometListener {
  private var msgs: List[String] = Nil

  val server =           new ChatServer

  def registerWith = server

  override def lowPriority = {
    case m: List[String] => msgs = m; reRender(false)
  }

  def render = {
    <div>
      <p>{msgs.firstOption.get}</p>
      <lift:form>
        {SHtml.text("", s => server ! s)}<input type="submit" value="Chat"/>
      </lift:form>
    </div>
  }
}