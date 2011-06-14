package pt.cnbc.wikimodels.setup

import org.junit._
import Assert._
import com.hp.hpl.jena.rdf.model.ModelFactory
import org.slf4j.LoggerFactory

/**
 * User: Alexandre Martins
 * Date: 10-06-2011
 * Time: 18:41
 * To change this template use File | Settings | File Templates.
 */
class SetupTest {
  val logger = LoggerFactory.getLogger(this.getClass)

  @Before
  def setUp: Unit = {
    Console.println(this.getClass+".setUp() is running")
  }

  @After
  def tearDown: Unit = {
    Console.println(this.getClass+".tearDown() is running")
  }

  @Test
  def checkKBCreation = {
    var model = ModelFactory.createDefaultModel
    model = Setup.saveOntologiesOn(model)
    assertTrue(model != null)
    logger.debug("Created model has {} statements", model.size )
    assertTrue(model.size() > 1)
  }
}

