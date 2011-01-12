package pt.cnbc.wikimodels.snippet

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 12-01-2011
 * Time: 0:33
 * To change this template use File | Settings | File Templates.
 */


import org.junit._
import Assert._
import scala.util.parsing.combinator._
import pt.cnbc.wikimodels.mathparser.{MathMLPrettyPrinter, MathParser}
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
    val parser = MathParser()
    log.info("MathMLEdit.render().processTextArea() with formula = " + asciiFormula.is)
    log.info("MathMLEdit.render() processTextArea() with MathML = " + mathmlFormula.is)
    val result = parser.parseAll(parser.Expr, asciiFormula.is)
    //save
    mathmlFormulaToSave.set(MathMLPrettyPrinter.toXML(result.get))
    //add necessary parameters for javascript binding
    mathmlFormula.set(XMLHandler.addAttributes(
      mathmlFormulaToSave.is,
      "id" -> "formula2", "mode" -> "display"))
  }
}
