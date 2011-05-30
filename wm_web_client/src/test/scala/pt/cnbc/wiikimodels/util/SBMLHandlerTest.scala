package pt.cnbc.wikimodels.util

import org.junit._
import Assert._
import internal.runners.statements.Fail
import net.liftweb.common.{Failure, Full}

class SBMLHandlerTest {

  @Before
  def setUp: Unit = {
    Console.println("SBMLHandlerTest.setUp is running")
  }

  @After
  def tearDown: Unit = {
    Console.println("SBMLHandlerTest.tearDown is running")
  }

  val xmlHeader = """<?xml version="1.0" encoding="UTF-8"?>\n"""

  val modelTag =
  <model metaid="metaid_0000002" id="Holzhutter2004_Erythrocyte_Metabolism" name="Holzhutter2004_Erythrocyte_Metabolism">
    <notes>
      <body xmlns="http://www.w3.org/1999/xhtml">
        <p>This model is automatically generated from the Model BIOMD0000000070 by using <a href="http://sbml.org/Software/libSBML" target="_blank">libsbml</a>. According to the <a href="http://www.ebi.ac.uk/biomodels//legal.html">terms of use</a>, this generated model is not related with Model BIOMD0000000070 any more. <br/>To retrieve the curated model, please visit <a href="http://www.ebi.ac.uk/biomodels/">BioModels Database</a>.</p>
      </body>
    </notes>
  </model>

  val sbmlFileContent = xmlHeader +
    <sbml xmlns="http://www.sbml.org/sbml/level2/version4" metaid="metaid_0000001" level="2" version="4">
      {modelTag}
    </sbml>

  @Test
  def extractModelTagfromSBML = {
    val model = SBMLDocHandler.extractModelTagfromSBML(sbmlFileContent)
    model match {
      case Full(elem) => assertTrue( elem == <model metaid="metaid_0000002" id="Holzhutter2004_Erythrocyte_Metabolism" name="Holzhutter2004_Erythrocyte_Metabolism">
    <notes>
      <body xmlns="http://www.w3.org/1999/xhtml">
        <p>This model is automatically generated from the Model BIOMD0000000070 by using <a href="http://sbml.org/Software/libSBML" target="_blank">libsbml</a>. According to the <a href="http://www.ebi.ac.uk/biomodels//legal.html">terms of use</a>, this generated model is not related with Model BIOMD0000000070 any more. <br/>To retrieve the curated model, please visit <a href="http://www.ebi.ac.uk/biomodels/">BioModels Database</a>.</p>
      </body>
    </notes>
  </model>  )
      case _ => fail("Valid SBML document wasn't correctly handled.")
    }
  }

  def doNotExtractModelFromBadlyFormedSBML = {
    val model = SBMLDocHandler.extractModelTagfromSBML(modelTag.toString())
    model match {
      case Failure(mesg,_,_) => assertTrue(true)
      case _ => fail("XML file without header and sbml tag should not have been successfully handled")
    }
  }

}
