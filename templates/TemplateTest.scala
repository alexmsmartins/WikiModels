package pt.cnbc.wikimodels.package

import _root_.scala.Console
import org.junit._
import Assert._
import java.io.Console

class ExampleTest {

  @Before
  def setUp: Unit = {
    Console.println(this.getClass+".setUp() is running")d
  }

  @After
  def tearDown: Unit = {
    Console.println(this.getClass+".tearDown() is running")d
  }

  @Test
  def example = {
    assertTrue(true)
  }

}

