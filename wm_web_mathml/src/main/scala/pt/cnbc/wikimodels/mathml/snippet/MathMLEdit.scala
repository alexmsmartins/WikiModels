package pt.cnbc.wikimodels.mathml.snippet

//--Standard imports --

import _root_.net.liftweb.http._
import S._
import _root_.net.liftweb.util._
import Helpers._
import _root_.net.liftweb.common._
import _root_.scala.xml._
import _root_.pt.cnbc.wikimodels.mathparser._
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
 * TODO: Please document.
 * @author Alexandre Martins
 * Date: 04-01-2011
 * Time: 14:37
 */
/**
 * Contains default values for Session Variables.
 * Those are used in a particular context.
 */
object Default{
  val mathmlFormula = <math xmlns="http://www.w3.org/1998/Math/MathML"/>
  val editMessHtml = <p id="parse_success" >Please insert new formula</p>
  val errorMess = "No error"
  val errorHtml = <p id="parse_success" >To start working please edit the AsciiMathML formula below</p>
  val asciiFormula = "x1+(x2^2/(3*x3))"
}

object asciiFormula extends SessionVar[String](Default.asciiFormula)

object mathmlFormula extends SessionVar[Elem](Default.mathmlFormula)

object mathmlFormulaToSave extends SessionVar[Elem](Default.mathmlFormula)

object successfulPerse extends SessionVar[Boolean](true)

object errorMessage extends SessionVar[String](Default.errorMess)

object errorHtml extends SessionVar[scala.xml.Elem](Default.errorHtml)

class MathMLEdit extends DispatchSnippet {
  import _root_.scala.util.parsing.combinator.Parsers
  import pt.cnbc.wikimodels.mathml.elements.{Cn, Ci, Apply, MathMLElem, Operator, CSymbol, Sep}
  import pt.cnbc.wikimodels.mathml.elements.Operator._

  //val log = Logger(this getClass)

  def dispatch: DispatchIt = {
    case "render" => render _
    case _ => defaultMethodCall _
  }

  def render(xhtml: NodeSeq): NodeSeq = {
    def processTextArea() {
      import scala.util.parsing.combinator._
      import net.liftweb.common.{Failure => _}
      val parser = AsciiMathParser()
      Console.println("MathMLEdit.render().processTextArea() with formula = " + asciiFormula.is)
      Console.println("MathMLEdit.render() processTextArea() with MathML = " + mathmlFormula.is)
      val result = parser.parseAll(parser.NumExpr, asciiFormula.is)
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
          Console.println("Error parsing " + asciiFormula.is + "\n" + result)
          errorMessage.set(result.toString)
          successfulPerse.set(false)
          def errorBeautifier(f:parser.Failure) = {
            ("Line "+f.next.pos.line+" column "+f.next.pos.column+" failure: \n"
              +f.msg+"\n\n"+f.next.pos.longString)
              .replace("string matching regex `\\z'", "End of expression").replace("\n\n","\n")
          }
          errorHtml.set(<p id="parse_failed" class="error"> Error parsing AsciiMathML.<div class="monospaced">{
            HTMLHandler.string2html(errorBeautifier(result.asInstanceOf[parser.Failure]))
          }</div> </p>)
        }
        case parser.Error(_,_) => {
          Console.println("Error in Parser -> \n" + result)
          errorMessage.set("There was a fatal error. No useful error messages are available.")
          successfulPerse.set(false)
        }
      }
    }
    Console.println("MathMLEdit.render() before bind() with formula = " + asciiFormula.is)
    Console.println("MathMLEdit.render() before bind() with MathML = " + mathmlFormula.is)
    Console.println("MathMLEdit.render() before bind() with ErroBoxL = " + errorHtml.is)

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
    <p class="error"> Error calling {node} in MathML Editor</p>
  }
}
