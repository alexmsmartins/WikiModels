package pt.cnbc.wikimodels.util

import org.junit._
import Assert._
import org.slf4j.LoggerFactory
import net.liftweb.common.{Logger, Failure, Full}

class SBMLHandlerTest {

  @Before
  def setUp: Unit = {
    logger.debug("{}.setUp is running",this.getClass)
  }

  @After
  def tearDown: Unit = {
    logger.debug("{}.tearDown is running",this.getClass)
  }

  val xmlHeader = """<?xml version="1.0" encoding="UTF-8"?>"""

  val modelTag =
  <model metaid="metaid_0000002" id="Holzhutter2004_Erythrocyte_Metabolism" name="Holzhutter2004_Erythrocyte_Metabolism">
    <notes>
      <body xmlns="http://www.w3.org/1999/xhtml">
        <p>This model is automatically generated from the Model BIOMD0000000070 by using <a href="http://sbml.org/Software/libSBML" target="_blank">libsbml</a>. According to the <a href="http://www.ebi.ac.uk/biomodels//legal.html">terms of use</a>, this generated model is not related with Model BIOMD0000000070 any more. <br/>To retrieve the curated model, please visit <a href="http://www.ebi.ac.uk/biomodels/">BioModels Database</a>.</p>
      </body>
    </notes>
  </model>

  val sbmlFileContent:String = xmlHeader + System.getProperty("line.separator") +
    <sbml xmlns="http://www.sbml.org/sbml/level2/version4" metaid="metaid_0000001" level="2" version="4">
      {modelTag}
    </sbml>


  val sbmlFileContent1:String = xmlHeader + System.getProperty("line.separator") +
    <sbml xmlns="http://www.sbml.org/sbml/level2/version4" metaid="metaid_0000001" level="2" version="4">
      {modelTag}
    </sbml>

  val logger = LoggerFactory.getLogger(getClass)

  @Test
  def extractModelTagfromSBML = {
    logger.debug("[Test extractModelTagfromSBML]")
    val model = SBMLDocHandler.extractModelTagfromSBML(sbmlFileContent1)
    val scala.xml.Elem(_,a,_,_,_*) = model.get
    assertTrue(a == "model")
  }

  val sbmlFileContent2:String = xmlHeader + System.getProperty("line.separator") +
    <sbml xmlns="http://www.sbml.org/sbml/level2/version4" metaid="metaid_0000001" level="2" version="4">{modelTag}
    </sbml>


    @Test
  def extractModelTagfromSBMLWithLine2 = {
    logger.debug("[Test extractModelTagfromSBMLWithLine2]")
    val model = SBMLDocHandler.extractModelTagfromSBML(sbmlFileContent2)
    Console.println("Model tag is ->" + model.head)
    val scala.xml.Elem(_,a,_,_,_*) = model.get
    assertTrue(a == "model")
  }

  @Test
  def doNotExtractModelFromBadlyFormedSBML = {
    logger.debug("[Test doNotExtractModelFromBadlyFormedSBML]")
    val model = SBMLDocHandler.extractModelTagfromSBML(modelTag.toString())
    model match {
      case Failure(mesg,_,_) => assertTrue(true)
      case _ => fail("XML file without header and sbml tag should not have been successfully handled")
    }
  }
}
