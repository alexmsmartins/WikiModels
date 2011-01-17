package pt.cnbc.wikimodels.snippet

//--Standard imports --
import _root_.net.liftweb.http._
import S._
import _root_.net.liftweb.util._
import Helpers._
import _root_.net.liftweb.common._
import _root_.scala.xml._
import _root_.pt.cnbc.wikimodels.mathparser.{MathParser, MathMLPrettyPrinter}
import pt.cnbc.wikimodels.util.{HTMLHandler, XMLHandler}

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

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 04-01-2011
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */

/**
 * Contains default values for Session Variables.
 * Those are used in a particular context.
 */
object Default{
  val xml = <math xmlns="http://www.w3.org/1998/Math/MathML"/>
  val editMessHtml = <p id="parse_success" >Please insert new formula</p>
  val errorMess = "No error"
  val errorHtml = <p id="parse_success" >Please insert new formula</p>
}

object asciiFormula extends SessionVar[String]("")
object mathmlFormula extends SessionVar[Elem](Default.xml)
object mathmlFormulaToSave extends SessionVar[Elem](Default.xml)
object successfulPerse extends SessionVar[Boolean](true)
object errorMessage extends SessionVar[String](Default.errorMess)
object errorHtml extends SessionVar[scala.xml.Elem](Default.errorHtml)

class MathMLEdit extends DispatchSnippet {
  import _root_.scala.util.parsing.combinator.Parsers
  import pt.cnbc.wikimodels.mathml.elements.{Cn, Ci, Apply, MathMLElem, Operator, CSymbol, Sep}
  import pt.cnbc.wikimodels.mathml.elements.Operator._

  val log = Logger(this getClass)

  def dispatch: DispatchIt = {
    case "render" => render _
    case _ => defaultMethodCall _
  }

  def render(xhtml: NodeSeq): NodeSeq = {
    def processTextArea() {
      import scala.util.parsing.combinator._
      import net.liftweb.common.{Failure => _}
      val parser = MathParser()
      log.info("MathMLEdit.render().processTextArea() with formula = " + asciiFormula.is)
      log.info("MathMLEdit.render() processTextArea() with MathML = " + mathmlFormula.is)
      val result = parser.parseAll(parser.Expr, asciiFormula.is)
      result match {
        case parser.Success(_,_) => {
          mathmlFormulaToSave.set( MathMLPrettyPrinter.toXML(result.get))
          successfulPerse.set(true)
          errorHtml.set(<p id="parse_success" class="success">Parsing succeded</p>)
          //save
          mathmlFormulaToSave.set( MathMLPrettyPrinter.toXML(result.get))
          //add necessary parameters for javascript binding
          mathmlFormula.set( XMLHandler.addAttributes(
            mathmlFormulaToSave.is,
            "id" -> "formula2", "mode" -> "display") )
        }
        case parser.Failure(_,_) => {
          log.info("Error parsing " + asciiFormula.is + "\n" + result)
          errorMessage.set(result.toString)
          successfulPerse.set(false)
          errorHtml.set(<p id="parse_failed" class="error"> Error parsing AsciiMathML due to {HTMLHandler.string2html(result.toString)} </p>)
        }
        case parser.Error(_,_) => {
          log.error(result)
          errorMessage.set("There was a fatal error. No useful error messages are available.")
          successfulPerse.set(false)
        }
      }
    }
    log.info("MathMLEdit.render() before bind() with formula = " + asciiFormula.is)
    log.info("MathMLEdit.render() before bind() with MathML = " + mathmlFormula.is)
    log.info("MathMLEdit.render() before bind() with ErroBoxL = " + errorHtml.is)

    xhtml.bind("editor",
      "formula" -> SHtml.textarea(asciiFormula.is, {asciiFormula set _}, "class" -> "asciimath_input" ),
      "submit" -> SHtml.submit("Send Formula", processTextArea, "class" -> "left_aligned"))
      .bind("error",
      "box" -> errorHtml.is )
      .bind("visualizer",
      "formulaViz" -> <div class="mathml_output" id="formula"  >{mathmlFormula.is}</div> )
  }

  def defaultMethodCall(node: NodeSeq): NodeSeq = {
    //TODO replace this with a proper error handling routines
    <p class="error"> Error calling {node}in MathML Editor</p>
  }
}
