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
    val roundMathML = MathMLPrettyPrinter.toXML(roundAST.get)
    Console.println("MathML generated from roundtripped AST is " + roundMathML)
    assertEquals((roundMathML \ "apply" \ "sin" ).head.label, "sin")
    //assertEquals(roundMathML.child.size , 2)
    //assertEquals(roundMathML.child(1).text , "x")

  }

}

