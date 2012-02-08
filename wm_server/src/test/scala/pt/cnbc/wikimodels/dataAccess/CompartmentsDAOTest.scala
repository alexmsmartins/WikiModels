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

  @Test
  def saveLoadModelWithOneCompartment = {
    var model = ModelFactory.createDefaultModel
    model = Setup.saveOntologiesOn(model)

    val xmlModelWith1Compartment =
      <model metaid="metaid_00000020" name="Izhikevich2004_SpikingNeurons_Class1Excitable" id="model_0000001">
        <!--order is important according to SBML Specifications-->
        <notes><p xmlns="http://www.w3.org/1999/xhtml">aaa</p></notes>
        <listOfCompartments>
          <compartment id="dsfas" spatialDimensions="3" size="2.0" name="fdsaf" metaid="dsfas" constant="true">
                 <notes><p xmlns="http://www.w3.org/1999/xhtml">fdasf</p></notes>
          </compartment>
        </listOfCompartments>
      </model>

    //create reaction
    val daoModel = new SBMLModelsDAO
    daoModel.createSBMLModel(
      SBML2BeanConverter.visitModel(xmlModelWith1Compartment),
      model)

    //load reaction
    val newModel = daoModel.deepLoadSBMLModel("metaid_00000020", model)
    val mXMLFinal =newModel.toXML

    //check XML
    Console.println("Old compartment is " + xmlModelWith1Compartment )
    Console.println("New model is " + mXMLFinal )

    //check local parameter list
    assertEquals(newModel.metaid, "metaid_00000020")
    assertEquals(newModel.listOfCompartments.size, 1)
    assertEquals((mXMLFinal \ "listOfCompartments" \ "compartment" \ "@metaid") text, "dsfas" )

  }

}