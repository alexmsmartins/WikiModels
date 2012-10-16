/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.client.record.visitir

import org.junit._
import Assert._
import pt.cnbc.wikimodels.sbmlVisitors.SBML2BeanConverter
import pt.cnbc.wikimodels.client.record.visitor.{SBMLFromRecord, RecordFromSBML}

/**TODO: Please document.
 * @author Alexandre Martins
 *         Date: 9/5/12
 *         Time: 8:52 AM */
class SBMLRecordVisitorTest {

  @Before
  def setUp: Unit = {
    Console.println(this.getClass + ".setUp() is running ")
  }

  @After
  def tearDown: Unit = {
    Console.println(this.getClass + ".tearDown() is running ")
  }

  @Test
  def checkCompartmentOutsideRoundTrip = {
    val cXml =
      <compartment  constant="true" metaid="c1" name="c1"  compartmentType="" spatialDimensions="3"  id="c1" outside="cell_membrane">
        <notes>
          <p xmlns="http://www.w3.org/1999/xhtml">
            Compartment 1
          </p>
        </notes>
      </compartment>
    //XML to Bean
    val bean = SBML2BeanConverter.visitCompartment(cXml)
    //Bean to Record
    val rec = RecordFromSBML.createCompartmentRecordFrom(bean)
    //Record to Bean
    val beanComp = SBMLFromRecord.createCompartmentFrom(rec)
    //Bean to XML
    val XmlComp = beanComp.toXML
    //check outside content
    assertEquals("cell_membrane", XmlComp \ "@outside" text)
  }

}

