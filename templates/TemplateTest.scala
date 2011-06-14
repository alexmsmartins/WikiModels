package pt.cnbc.wikimodels.mypackage

import org.junit._
import Assert._

class ExampleTest {

  @Before
  def setUp: Unit = {
    Console.println(this.getClass+".setUp() is running")
  }

  @After
  def tearDown: Unit = {
    Console.println(this.getClass+".tearDown() is running")
  }

  @Test
  def example = {
    assertTrue(false)
  }

}

