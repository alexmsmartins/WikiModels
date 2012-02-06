/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.dataAccess

import org.junit._
import Assert._
import com.hp.hpl.jena.rdf.model.ModelFactory
import pt.cnbc.wikimodels.setup.Setup
import pt.cnbc.wikimodels.sbmlVisitors.SBML2BeanConverter

/**TODO: Please document.
 *  @author Alexandre Martins
 *  Date: 10-01-2012
 *  Time: 7:21 */
class CompartmentsDAOTest {

  @Before
  def setUp: Unit = {
    Console.println(this.getClass+".setUp() is running ")
  }

  @After
  def tearDown: Unit = {
    Console.println(this.getClass+".tearDown() is running ")
  }

  @Test
  def checkIfCompartmentSizeRemainsEmpty = {
    var model = ModelFactory.createDefaultModel
    model = Setup.saveOntologiesOn(model)


    val cXml =
      <compartment  constant="true" metaid="c1" name="c1"  compartmentType="" spatialDimensions="3"  id="c1">
        <notes>
          <p xmlns="http://www.w3.org/1999/xhtml">
            Compartment 1
          </p>
        </notes>
      </compartment>

    //create reaction
    val daoComp = new CompartmentsDAO()
      daoComp.tryToCreateCompartment(SBML2BeanConverter.visitCompartment(cXml),
      model)

    //load reaction
    val newComp = daoComp.loadCompartment("c1", model)
    val cXMLFinal =newComp.toXML

    //check XML
    Console.println("Old compartment is " + cXml )
    Console.println("New model is " + newComp.toXML )

    //check local parameter list
    assertEquals(newComp.metaid, "c1")
    assertEquals(newComp.size, null)
    assertEquals(cXMLFinal \ "@size" text, "" )

  }

}