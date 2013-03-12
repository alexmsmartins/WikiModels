/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.mathparser

import org.junit._
import Assert._
import xml.{Utility, Elem}

/**TODO: Please document.
 *   @author Alexandre Martins
 *  Date: 20/01/12
 *  Time: 16:39 */
class MathMLMatchParserTest {

  @Before
  def setUp: Unit = {
    Console.println(this.getClass+".setUp() is running ")
  }

  @After
  def tearDown: Unit = {
    Console.println(this.getClass+".tearDown() is running ")
  }

  @Test
  def roundtripSin = {
    val mathml =
      <math xmlns="http://www.w3.org/1998/Math/MathML">
        <apply>
          <sin/>
          <ci type="real">x</ci>
        </apply>
      </math>
    Console.println("Beginning with xml " + mathml)
    val <math>{apply}</math> = Utility.trim(mathml)
    val p = MathMLMatchParser()
    val mathMLaST = p.parse(apply.asInstanceOf[Elem])
    Console.println("AST generated from mathml is " + mathMLaST)
    val asciiMath = AsciiMathPrettyPrinter.toAsciiMathML(mathMLaST)
    Console.println("AsciiMath generated from AST is " + asciiMath)
    val asciiParser = AsciiMathParser()
    val roundAST = asciiParser.parseAll(asciiParser.NumExpr, asciiMath)
    Console.println("AST generated from asciimath is " + roundAST)
    val roundMathML = scala.xml.Utility.trim(MathMLPrettyPrinter.toXML(roundAST.get))
    Console.println("MathML generated from roundtripped AST is " + roundMathML)
    assertEquals((roundMathML \ "apply" \ "sin" ).head.label, "sin")
    assertEquals((roundMathML \ "apply").head.child.size , 2) //tests for the number of elements inside the <apply> tag
    assertEquals(roundMathML.child(0).child(1).head.text , "x") //tests for 'x' in <ci>
  }

  @Test
  def roundtripLambda = {
    val mathml =  // taken from sbml l2v4r1 spec page 40
      <math xmlns="http://www.w3.org/1998/Math/MathML">
        <lambda>
          <bvar><ci> temp_in_fahrenheit </ci></bvar>
          <bvar><ci> temp_in_fahrenheit_for_testing </ci></bvar>
          <apply>
            <divide/>
            <apply>
              <plus/>
              <ci> temp_in_fahrenheit </ci>
              <cn> 459.67 </cn>
            </apply>
            <cn> 1.8 </cn>
          </apply>
        </lambda>
      </math>
    Console.println("Beginning with xml " + mathml)
    val <math>{lambda}</math> = Utility.trim(mathml)
    val p = MathMLMatchParser()
    val mathMLaST = p.parse(lambda.asInstanceOf[Elem])
    Console.println("AST generated from mathml is " + mathMLaST)
    val asciiMath = AsciiMathPrettyPrinter.toAsciiMathML(mathMLaST)
    Console.println("AsciiMath generated from AST is " + asciiMath)
    val asciiParser = AsciiMathParser()
    val roundAST = asciiParser.parseAll(asciiParser.LambdaExpr, asciiMath)
    Console.println("AST generated from asciimath is " + roundAST.get)
    val roundMathML = scala.xml.Utility.trim(MathMLPrettyPrinter.toXML(roundAST.get))
    Console.println("MathML generated from roundtripped AST is " + roundMathML)
    assertEquals((roundMathML \ "lambda" \ "bvar" \ "ci" ).head.text.trim, "temp_in_fahrenheit")
    assertEquals((roundMathML \ "lambda").head.child.size , 3) //tests for the number of elements inside the <lambda> tag
    assertEquals((roundMathML \ "lambda" \ "apply" \ "apply" \ "ci").text, "temp_in_fahrenheit") //tests for 'temp_in_fahrenheit' in <ci>
  }

  @Test
  def roundtripLambdaWithPower = {
    val mathml =  // taken from sbml l2v4r1 spec page 40
      <math xmlns="http://www.w3.org/1998/Math/MathML">
        <lambda>
          <bvar><ci> x </ci></bvar>
          <apply>
            <power/>
            <apply>
              <divide/>
              <apply>
                <plus/>
                <ci> x </ci>
                <cn> 459.67 </cn>
              </apply>
              <cn> 1.8 </cn>
            </apply>
            <cn> 3 </cn>
          </apply>
        </lambda>
      </math>
    Console.println("Beginning with xml " + mathml)
    val <math>{lambda}</math> = Utility.trim(mathml)
    val p = MathMLMatchParser()
    val mathMLaST = p.parse(lambda.asInstanceOf[Elem])
    Console.println("AST generated from mathml is " + mathMLaST)
    val asciiMath = AsciiMathPrettyPrinter.toAsciiMathML(mathMLaST)
    Console.println("AsciiMath generated from AST is " + asciiMath)
    val asciiParser = AsciiMathParser()
    val roundAST = asciiParser.parseAll(asciiParser.LambdaExpr, asciiMath)
    Console.println("AST generated from asciimath is " + roundAST.get)
    val roundMathML = scala.xml.Utility.trim(MathMLPrettyPrinter.toXML(roundAST.get))
    Console.println("MathML generated from roundtripped AST is " + roundMathML)
    assertEquals((roundMathML \ "lambda" \ "bvar" \ "ci" ).head.text.trim, "x")
    assertEquals((roundMathML \ "lambda").head.child.size , 2) //tests for the number of elements inside the <lambda> tag
    assertEquals((roundMathML \ "lambda" \ "apply" \"apply" \"apply" \ "ci").text, "x") //tests for 'x' in <ci>
    assertEquals((roundMathML \ "lambda" \ "apply" \ "cn").text, "3") //tests for '3' in <cn>
  }

}

