package pt.cnbc.wikimodels.snippet

//--Standard imports --
import _root_.net.liftweb.http._
import S._
import _root_.net.liftweb.util._
import Helpers._
import _root_.net.liftweb.common._
import _root_.scala.xml._
import pt.cnbc.wikimodels.mathparser.{MathParser, MathMLPrettyPrinter}
import pt.cnbc.wikimodels.util.XMLHandler

//--Standard imports --

import _root_.net.liftweb.http._
import _root_.net.liftweb.http.SHtml._
import _root_.net.liftweb.http.js.jquery._
import _root_.net.liftweb.http.js.jquery.JqJsCmds._
import _root_.net.liftweb.http.js.JsCommands._
import _root_.net.liftweb.common._
import _root_.scala.xml.{Text, NodeSeq, Elem}
import _root_.net.liftweb.util.Helpers._
import _root_.net.liftweb.util.BindPlus._

import _root_.scala.util.parsing.combinator.Parsers
import pt.cnbc.wikimodels.mathml.elements.{Cn, Ci, Apply, MathMLElem, Operator, CSymbol, Sep}

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 04-01-2011
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
class MathMLEdit extends DispatchSnippet {
  import pt.cnbc.wikimodels.mathml.elements.{Cn, Ci, Apply, MathMLElem, Operator, CSymbol, Sep}
  import pt.cnbc.wikimodels.mathml.elements.Operator._

  val log = Logger(this getClass)

  object asciiFormula extends SessionVar[String]("2+3")
  object mathmlFormula extends SessionVar[Elem](<math/>)
  object mathmlFormulaToSave extends SessionVar[Elem](<math/>)


  def dispatch: DispatchIt = {
    case "render" => render _
    case "sample" => sample _
    case _ => defaultMethodCall _
  }

  def render(xhtml: NodeSeq): NodeSeq = {
    def processTextArea() {
      val parser = MathParser()
      log.info("MathMLEdit.render().processTextArea() with formula = " + asciiFormula.is)
      log.info("MathMLEdit.render() processTextArea() with MathML = " + mathmlFormula.is)
      val result = parser.parseAll(parser.Expr, asciiFormula.is)
      //save
      mathmlFormulaToSave.set( MathMLPrettyPrinter.toXML(result.get))
      //add necessary parameters for javascript binding
      mathmlFormula.set( XMLHandler.addAttributes(
        mathmlFormulaToSave.is,
        "id" -> "formula2", "mode" -> "display") )
    }
    log.info("MathMLEdit.render() before bind() with formula = " + asciiFormula.is)
    log.info("MathMLEdit.render() before bind() with MathML = " + mathmlFormula.is)

    val mathml = bind("math", mathmlFormula.is, "mode" -> "display")

    xhtml.bind("editor",
      "formula" -> SHtml.textarea(asciiFormula.is, {asciiFormula set _}, "class" -> "asciimath_input" ),
      "submit" -> SHtml.submit("Send Formula", processTextArea, "class" -> "left_aligned"))
      .bind("visualizer",
      "formulaViz" -> <div class="mathml_output" id="ondivload2"  >{mathmlFormula.is}</div> )
  }

  def defaultMethodCall(node: NodeSeq): NodeSeq = {
    //TODO replace this with a proper error handling routines
    <p class="error"> Error calling {node}in MathML Editor</p>
  }

  //-- from Ajax.scala

  // local state for the counter
  var cnt = 0

  // get the id of some elements to update
  val spanName: String = S.attr("id_name") openOr "cnt_id"
  val msgName: String = S.attr("id_msgs") openOr "messages"

  def sample(xhtml: NodeSeq): NodeSeq = {
    // local state for the counter
    var cnt = 0

    // get the id of some elements to update
    val spanName: String = S.attr("id_name") openOr "cnt_id"
    val msgName: String = S.attr("id_msgs") openOr "messages"


    // create an ajax select box
    def doSelect(msg: NodeSeq) =
      ajaxSelect((1 to 50).toList.map(i => (i.toString, i.toString)),
        Full(1.toString),
        v => DisplayMessage(msgName,
          bind("sel", msg, "number" -> Text(v)),
          5 seconds, 1 second))

    // build up an ajax text box
    def doText(msg: NodeSeq) =
      ajaxText("", v => DisplayMessage(msgName,
        bind("text", msg, "value" -> Text(v)),
        0 seconds, 1 second))



    // bind the view to the functionality
    bind("ajax", xhtml,
      "select" -> doSelect _,
      "text" -> doText _)
  }

  private def buildQuery(current: String, limit: Int): Seq[String] = {
    (1 to limit).map(n => current + "" + n)
  }

  def time = Text(timeNow.toString)
}
