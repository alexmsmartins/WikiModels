package pt.cnbc.wikimodels.snippet

/**
 * TODO: Please document.
 * @author Alexandre Martins
 * Date: 12-01-2011
 * Time: 0:33
 */

import org.junit._
import Assert._
import scala.util.parsing.combinator._
import pt.cnbc.wikimodels.mathparser.{MathMLPrettyPrinter, AsciiMathMLParser}
import pt.cnbc.wikimodels.util.XMLHandler
import net.liftweb.http.DispatchSnippet

class MathMLEditTest extends MathMLEdit {

  @Before
  def setUp: Unit = {
  }

  @After
  def tearDown: Unit = {
  }

  @Test
  def processTextAreaGivesExceptionInInitializerError() {
    val parser = AsciiMathMLParser()
    var formula = "2+2"
    Console.println("MathMLEdit with asciiFormula = " + asciiFormula.is)
    Console.println("MathMLEdit with formula = " + formula)
    Console.println("MathMLEdit.render() processTextArea() with MathML = " + mathmlFormula.is)
    val result = parser.parseAll(parser.Expr, formula)
    result match {
      case parser.Success(_,_) => {
        mathmlFormulaToSave.set(MathMLPrettyPrinter.toXML(result.get))
        //add necessary parameters for javascript binding
        mathmlFormula.set(XMLHandler.addAttributes(
          mathmlFormulaToSave.is,
          "id" -> "formula2", "mode" -> "display"))
        successfulPerse.set(true)
      }
      case parser.Failure(_,_) => {
        fail("Parser failed!")
      }
      case parser.Error(_,_) => {
        Console.println("Error in parser -> \n" + result)
        fail("Parser failed with strange error!")
      }
    }

  }
}
