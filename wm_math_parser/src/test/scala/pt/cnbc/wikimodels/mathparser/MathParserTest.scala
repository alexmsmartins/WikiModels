package pt.cnbc.wikimodels.mathparser

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 21/Set/2010
 * Time: 19:13:31
 * To change this template use File | Settings | File Templates.
 */
import org.junit._
import Assert._
import scala.util.parsing.combinator._


class MathParserTest extends MathParser {

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
    val result = parseAll(Expr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def decimalNumberTest {
    val expr = "1.03"
    val result = parseAll(Expr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def variable {
    val expr = "teta"
    val result = parseAll(Expr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def sum {
    val expr = "1+2+x"
    val result = this.parseAll(Expr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def sumAndSubtraction {
    val expr = "1+2-x-(x-y+4)"
    val result = this.parseAll(Expr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def simpleMultiplication {
    val expr = "2*x*2+3"
    val result = this.parseAll(Expr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def multiplication {
    val expr = "4-2*x+2*x+3"//"4-2*x+2x+3"
    val result = this.parseAll(Expr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def simpleFraction {
    val expr = "1/(x*y)"
    val result = this.parseAll(Expr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def fraction {
    val expr = "1/(2+x)" //"1/(2x)"
    val result = this.parseAll(Expr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def sqrt {
    val expr = "sqrt(x)"
    val result = parseAll (Expr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
    }

  @Test
  def sqrtInNumerator {
    val expr = "sqrt(2)/2"
    val result = parseAll (Expr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
    }

  @Test
  def exponent {
    val expr = "x^2 + x^(5*y)" //x^2 + x^(5*y)
    val result = parseAll(Expr, expr)
    assertTrue(parsingWasSuccessful(result))
    println(result)
  }

  @Test
  def power {
    //val expr = "(pi)r^2"
    //val result = parseAll(Expr, expr)
    //println(result)
    val expr2 = "pi*r^2"
    val result2 = parseAll(Expr, expr2)
    assertTrue(parsingWasSuccessful(result2))
    println(result2)
  }
}
