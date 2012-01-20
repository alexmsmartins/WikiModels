package pt.cnbc.wikimodels.mypackage

import _root_.scala.Console
import org.junit._
import Assert._
import java.io.Console

/** TODO Please document this JUnit test
 *  @author ${USER}
 * Date: ${DATE}
 * Time: ${TIME}  */
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
    assertTrue(true)
  }

}
