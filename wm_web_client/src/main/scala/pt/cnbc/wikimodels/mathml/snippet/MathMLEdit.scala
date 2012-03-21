package pt.cnbc.wikimodels.mathml.snippet

/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

//--Standard imports --

import _root_.pt.cnbc.wikimodels.mathparser._
import pt.cnbc.wikimodels.util.{XSLTTransform, HTMLHandler, XMLHandler}

//--Standard imports --

import _root_.net.liftweb.http._
import _root_.scala.xml.{NodeSeq, Elem}
import _root_.net.liftweb.util.Helpers._
import _root_.net.liftweb.util.BindPlus._

/**
 * TODO: Please document.
 * @author Alexandre Martins
 * Date: 04-01-2011
 * Time: 14:37
 */


class MathMLEdit extends MathMLEditT


trait MathMLEditT extends DispatchSnippet {

  /**
   * Contains default values for Session Variables.
   * Those are used in a particular context.
   */
  object Default{
    val mathmlFormula = <math xmlns="http://www.w3.org/1998/Math/MathML"/>
    val editMessHtml = <p id="parse_success" >Please insert new formula</p>
    val errorMess = "No error"
    val errorHtml = <p id="parse_success" >To start working please edit the AsciiMathML formula below</p>
    val asciiFormula = ""
  }
  object asciiFormula extends RequestVar[String](Default.asciiFormula)

  object mathmlFormula extends SessionVar[Elem](Default.mathmlFormula)

  object mathmlFormulaToSave extends SessionVar[Elem](Default.mathmlFormula)


  val xsltTransform = new XSLTTransform(
     this.getClass.getClassLoader.getResourceAsStream("mathmlc2p.xsl")
  )

  //val log = Logger(this getClass)

  def dispatch: DispatchIt = {
    case "render" =>{
      //S.notice("parsing_error", errorHtml)
      render _
    }
    case _ => defaultMethodCall _
  }

  def render(xhtml: NodeSeq): NodeSeq = {
    def checkTextArea() {
      import net.liftweb.common.{Failure => _}
      val parser = AsciiMathParser()
      Console.println("MathMLEdit.render().processTextArea() with formula = " + asciiFormula.is)
      Console.println("MathMLEdit.render() processTextArea() with MathML = " + mathmlFormula.is)
      val result = parser.parseAll(parser.NumExpr, asciiFormula.is)
      result match {
        case parser.Success(_,_) => {

          val presentationMathMLAsString = xsltTransform.execute( MathMLPrettyPrinter.toXML(result.get).toString )
          val presentationMathML = scala.xml.XML.loadString( presentationMathMLAsString)
          mathmlFormulaToSave.set( presentationMathML)
          S.notice("parsing_error", <p id="parse_success" class="success">Parsing succeded</p>)
          //save
          mathmlFormulaToSave.set( presentationMathML)
          //add necessary parameters for javascript binding
          mathmlFormula.set( XMLHandler.addAttributes(
            mathmlFormulaToSave.is,
            "mode" -> "display") )
        }
        case parser.Failure(_,_) => {
          Console.println("Error parsing " + asciiFormula.is + "\n" + result)
          def errorBeautifier(f:parser.Failure) = {
            ("Line "+f.next.pos.line+" column "+f.next.pos.column+" failure: \n"
              +f.msg+"\n\n"+f.next.pos.longString)
              .replace("string matching regex `\\z'", "End of expression").replace("\n\n","\n")
          }
          S.error("parsing_error",
            <span id="parse_failed"> <p  class="error"> Error parsing AsciiMathML.<div class="monospaced">
              {
                HTMLHandler.string2html {
                  errorBeautifier {
                    result.asInstanceOf[parser.Failure]
                  }
                }
              }
            </div> </p></span>)

        }
        case parser.Error(_,_) => {
          Console.println("Error in Parser -> \n" + result)
          S.error("parsing_error", "There was a fatal error. No useful error messages are available.")
        }
      }
    }
    def saveTextArea() = {

    }


    Console.println("MathMLEdit.render() before bind() with formula = " + asciiFormula.is)
    Console.println("MathMLEdit.render() before bind() with MathML = " + mathmlFormula.is)

    xhtml.bind("editor",
      "formula" -> SHtml.textarea(asciiFormula.is, {asciiFormula set _}, "class" -> "asciimath_input" ),
      "check" -> SHtml.button("Check Formula", checkTextArea, "class" -> "left_aligned"),
      "save" -> SHtml.submit("Save Formula", () => {}, "class" -> "left_aligned"))
      .bind("visualizer",
      "formulaViz" -> <div class="mathml_output" id="formula"  >{mathmlFormula.is}</div> )
  }

  def defaultMethodCall(node: NodeSeq): NodeSeq = {
    //TODO replace this with a proper error handling routines
    <p class="error"> Error calling {node} in MathML Editor</p>
  }
}
