package pt.cnbc.wikimodels.mathparser

/**
 * TODO: Please document.
 * @author Alexandre Martins
 * Date: 21/Set/2010
 * Time: 19:13:31
 */
import org.junit._
import Assert._
import scala.util.parsing.combinator._

class AsciiMathParserTest extends AsciiMathParser {

  @Before
  def setUp: Unit = {
    }

  @After
  def tearDown: Unit = {
    }

  def parsingWasSuccessful(parseResult:ParseResult[Any]):Boolean = {
    parseResult match {
      case Success(_,_) => true
      case Failure(_,_) => false
      case _ => {
        fail("Something is wrong with this test")
        false
      }
    }
  }

  @Test
  def wholeNumberTest {
    val expr = "1"
    val result = parseAll(NumExpr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def decimalNumberTest {
    val expr = "-1.03"
    val result = parseAll(NumExpr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def scientificNotationTest {
    //TODO add scientific notaiton options to the grammar
    val expr = "1.03e-23"
    val result = parseAll(NumExpr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
    val expr2 = "1.03E-23"
    val result2 = parseAll(NumExpr, expr2)
    assertTrue(parsingWasSuccessful(result2))
    println(result2)

  }

  @Test
  def variable {
    val expr = "teta"
    val result = parseAll(NumExpr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def simpleSum {
    val expr = "1+2+3+4"
    val result = this.parseAll(NumExpr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def simpleSum2 {
    val expr = "2+2"
    val result = this.parseAll(NumExpr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

    @Test
    def severalNotationsSum {
      //TODO COMPLETE SCIENTIFIC NOTAITON
      val expr = "1+2.1+3.1"//+4e2"
      val result = this.parseAll(NumExpr, expr)
      assertTrue(parsingWasSuccessful(result))
      println(result)
    }

  @Test
  def sum {
    val expr = "1+2+x"
    val result = this.parseAll(NumExpr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def simpleSubtraction {
    val expr = "1-2-3-4"
    val result = this.parseAll(NumExpr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def simpleSumAndSubtraction {
    val expr = "1+2+3-4"
    val result = this.parseAll(NumExpr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def sumAndSubtraction {
    val expr = "1+2-x-(x-y+4)"
    val result = this.parseAll(NumExpr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def simpleMultiplication {
    val expr = "2*x*2+3"
    val result = this.parseAll(NumExpr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def multiplication {
    val expr = "4-2*x+2*x+3"//"4-2*x+2x+3"
    val result = this.parseAll(NumExpr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def simpleFraction {
    val expr = "1/(x*y)"
    val result = this.parseAll(NumExpr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def fraction {
    val expr = "1/(2+x)" //"1/(2x)"
    val result = this.parseAll(NumExpr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def sqrt {
    val expr = "sqrt(x)"
    val result = parseAll (NumExpr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
    }

  @Test
  def sqrtInNumerator {
    val expr = "sqrt(2)/2"
    val result = parseAll (NumExpr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
    }

  @Test
  def exponent {
    val expr = "x^2 + x^(5*y)" //x^2 + x^(5*y)
    val result = parseAll(NumExpr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def power {
    //val expr = "(pi)r^2"
    //val result = parseAll(NumExpr, expr)
    //println(result)
    val expr2 = "pi*r^2"
    val result2 = parseAll(NumExpr, expr2)
    assertTrue(parsingWasSuccessful(result2))
    println(result2)
    val xml = MathMLPrettyPrinter.toXML(result2.get)
    println("Expression " + expr2 + " in mathml: " + xml)
  }

  @Test
  def sin {
    val expr2 = "sin(x)"
    val result2 = parseAll(NumExpr, expr2)
    assertTrue(parsingWasSuccessful(result2))
    println(result2)
    val xml = MathMLPrettyPrinter.toXML(result2.get)
    println("Expression " + expr2 + " in mathml: " + xml)
  }

  @Test
  def lambda {
    val expr = "(x,y) = x+y+2"
    val result = parseAll(LambdaExpr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
    val xml = MathMLPrettyPrinter.toXML(result.get)
    println("Expression " + expr + " in mathml: " + xml)
    val expr2 = "f(x) = x+2"
    val result2 = parseAll(LambdaExpr, expr2)
    assertFalse(parsingWasSuccessful(result2))
    println(result2)
    println("Expression " + expr2 + " failed")
  }

  @Test
  def eq {
    val expr2 = "2 == 2"
    val result2 = parseAll(Relation, expr2)
    assertTrue(parsingWasSuccessful(result2))
    println(result2)
    val xml = MathMLPrettyPrinter.toXML(result2.get)
    println("Expression " + expr2 + " in mathml: " + xml)
  }

  @Test
  def neq {
    val expr2 = "2!=x"
    val result2 = parseAll(Relation, expr2)
    assertTrue(parsingWasSuccessful(result2))
    println(result2)
    val xml = MathMLPrettyPrinter.toXML(result2.get)
    println("Expression " + expr2 + " in mathml: " + xml)
  }

  @Test
  def gteq {
    val expr2 = "2E-2 >= 2"
    val result2 = parseAll(Relation, expr2)
    assertTrue(parsingWasSuccessful(result2))
    println(result2)
    val xml = MathMLPrettyPrinter.toXML(result2.get)
    println("Expression " + expr2 + " in mathml: " + xml)
  }

  @Test
  def lteq {
    val expr2 = "2 <= 24+5"
    val result2 = parseAll(Relation, expr2)
    assertTrue(parsingWasSuccessful(result2))
    println(result2)
    val xml = MathMLPrettyPrinter.toXML(result2.get)
    println("Expression " + expr2 + " in mathml: " + xml)
  }

  //TODO - test to "and" in wm_math_parser is failing
  //@Test
  def and{
    val expr2 = """x /\ y"""
    val result2 = parseAll(GenExpr, expr2)
    assertTrue(parsingWasSuccessful(result2))
    println(result2)
    val xml = MathMLPrettyPrinter.toXML(result2.get)
    println("Expression " + expr2 + " in mathml: " + xml)
  }
}
