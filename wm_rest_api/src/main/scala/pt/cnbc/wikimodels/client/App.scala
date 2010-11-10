/*
 * App.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.client

import java.net.URI
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert._

import org.apache.log4j._

import scala.xml.Elem
import scala.xml.XML

import pt.cnbc.wikimodels.rest.client.BasicAuth
import pt.cnbc.wikimodels.rest.client.RestfulAccess


object App {
  var ra: RestfulAccess = null
  var log: Logger = Logger.getLogger(this.getClass)

  /**
   * Assumng wikimodels is working with the followning configuration
   *  - host: localhost
   *  - port: 8080
   *  - contextRoot: resources
   *  - User: admin
   *  - Password: admin
   * this method can be used to do the integration testing
   * of the Wikimodels server.
   */
  @Before
  def main(args: Array[String]): Unit = {
    log.info("WikiModels access API Integration testing.")
    var testNumber = 0
    try {
      val startTime = 

      setUp
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - Login")
      loginTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - LoginFailed")
      loginFailedTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - deleteNonExistantModel")
      deleteNonExistantModelTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - editModelBeforeCreation")
      editModelBeforeCreationTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - CreateModel")
      createModelTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - CreateAnotherModel")
      createAnotherModelTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - BrowseModel")
      browseModelTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - BrowseNonExistantModel")
      browseNonExistantModelTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - EditModel")
      editModelTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - GetListOfModels")
      getListOfModelsTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - DeleteModel2")
      deleteModelTest
      log.info("SUCCESS")

      //------------- PArameter integration tests -------------

      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - deleteNonExistantParameter")
      deleteNonExistantParameterTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - editParameterBeforeCreation")
      editParameterBeforeCreationTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - CreateParameter")
      createParameterTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - CreateAnotherParameter")
      createAnotherParameterTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - BrowseParameter")
      browseParameterTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - BrowseNonExistantParameter")
      browseNonExistantParameterTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - EditParameter")
      editParameterTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - DeleteParameter2")
      deleteParameterTest
      log.info("SUCCESS")

      //------------- Species integration tests -------------

      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - deleteNonExistantSpecies")
      deleteNonExistantSpeciesTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - editSpeciesBeforeCreation")
      editSpeciesBeforeCreationTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - CreateSpecies")
      createSpeciesTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - CreateAnotherSpecies")
      createAnotherSpeciesTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - BrowseSpecies")
      browseSpeciesTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - BrowseNonExistantSpecies")
      browseNonExistantSpeciesTest
              log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - EditSpecies")
      editSpeciesTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - DeleteSpecies2")
      deleteSpeciesTest
      log.info("SUCCESS")

      //------------- Compartment integration tests -------------

      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - deleteNonExistantCompartment")
      deleteNonExistantCompartmentTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - editCompartmentBeforeCreation")
      editCompartmentBeforeCreationTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - CreateCompartment")
      createCompartmentTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - CreateAnotherCompartment")
      createAnotherCompartmentTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - BrowseCompartment")
      browseCompartmentTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - BrowseNonExistantCompartment")
      browseNonExistantCompartmentTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - EditCompartment")
      editCompartmentTest
      log.info("SUCCESS")
      log.info("======================")
      testNumber += 1
      log.info("TEST " + testNumber + " - DeleteCompartment2")
      deleteCompartmentTest
      log.info("SUCCESS")

      //------------- Constraint integration tests -------------
      //TODO Write integration tests for the reamining elements of a Model

      /*log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - deleteNonExistantConstraint" )
      deleteNonExistantConstraintTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - editConstraintBeforeCreation" )
      editConstraintBeforeCreationTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - CreateConstraint" )
      createConstraintTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - CreateAnotherConstraint" )
      createAnotherConstraintTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - BrowseConstraint" )
      browseConstraintTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - BrowseNonExistantConstraint" )
      browseNonExistantConstraintTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - EditConstraint" )
      editConstraintTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - DeleteConstraint2" )
      deleteConstraintTest
      log.info("SUCCESS" )*/

      //------------- FunctionDefinition integration tests -------------

      /*log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - deleteNonExistantFunctionDefinition" )
      deleteNonExistantFunctionDefinitionTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - editFunctionDefinitionBeforeCreation" )
      editFunctionDefinitionBeforeCreationTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - CreateFunctionDefinition" )
      createFunctionDefinitionTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - CreateAnotherFunctionDefinition" )
      createAnotherFunctionDefinitionTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - BrowseFunctionDefinition" )
      browseFunctionDefinitionTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - BrowseNonExistantFunctionDefinition" )
      browseNonExistantFunctionDefinitionTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - EditFunctionDefinition" )
      editFunctionDefinitionTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - DeleteFunctionDefinition2" )
      deleteFunctionDefinitionTest
      log.info("SUCCESS" )

      //------------- Reaction integration tests -------------

      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - deleteNonExistantCompartment" )
      deleteNonExistantCompartmentTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - editCompartmentBeforeCreation" )
      editCompartmentBeforeCreationTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - CreateCompartment" )
      createCompartmentTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - CreateAnotherCompartment" )
      createAnotherCompartmentTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - BrowseCompartment" )
      browseCompartmentTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - BrowseNonExistantCompartment" )
      browseNonExistantCompartmentTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - EditCompartment" )
      editCompartmentTest
      log.info("SUCCESS" )
      log.info("======================" )
      testNumber+=1
      log.info("TEST " + testNumber + " - DeleteCompartment2" )
      deleteCompartmentTest
      log.info("SUCCESS" )*/

      log.info("=========== INTEGRATION TESTING SUCCEDED ===========")
      log.info("= It took " + 20 + " seconds to run the tests              =")
      log.info("====================================================")
      //basicAuthentication
      //digestAuthentication
    } catch {
      case e: Exception => {
        log.info("INTEGRATION TESTING FAILED")
        log.error("", e)
      }
    } finally {
      tearDown
    }
  }

  @Before
  def setUp: Unit = {
    this.ra = new RestfulAccess("localhost",
      8080,
      "/wm_server-1.0-SNAPSHOT/resources",
      "admin",
      "admin",
      BasicAuth.startWithBasicAuth)
  }

  @After
  def tearDown: Unit = {
    ra.close
  }

  val model: Elem =
  <model metaid="metaid_0000002" id="model_0000001" name="Izhikevich2004_SpikingNeurons_Class1Excitable">
    <notes>
      <h1>SBML model of the interlocked feedback loop network
      </h1>
      <p xmlns="http://www.w3.org/1999/xhtml">
        The model describes the circuit depicted in Fig.4 and reproduces the simulations in Figure 5A
        and 5B.It provides initial conditions, parameter values and rules for the production rates of the
        following species: LHY mRNA (cLm), cytoplasmic LHY (cLc), nuclear LHY (cLn),
        TOC1 mRNA (cTm), cytoplasmic TOC1 (cTc), nuclear TOC1 (cTn),X mRNA (cXm),
        cytoplasmic X (cXc), nuclear X (cXn), Y mRNA (cYm), cytoplasmic Y (cYc),
        nuclear Y (cYn), nuclear P (cPn).This model was successfully tested on MathSBML and SBML ODE Solver.
      </p>
      <p xmlns="http://www.w3.org/1999/xhtml">

        Fig 5B is not in the right phase.However, the data is correct relative to the light/dark bars at the top of the figure.
      </p>
      <p xmlns="http://www.w3.org/1999/xhtml">This model originates from BioModels Database: A Database of Annotated Published Models.It is copyright (c) 2005-2007 The BioModels Team.<br></br>
        For more information see the
        <a href="http://www.ebi.ac.uk/biomodels/legal.html">terms of use</a>.</p>
    </notes>
  </model>

  @Test
  def loginTest = {
    val xml: Elem = ra.getRequest("/user/admin")
    if (ra.getStatusCode == 200) {
      if ((xml \ "userName").text == "admin") {

      } else throw new Exception("Wrong user")
    } else throw new Exception("Wrong StatusCode. Should be 200 OK")
  }

  @Test
  def loginFailedTest = {
    val raFailed = new RestfulAccess("localhost",
      8080,
      "/wm_server-1.0-SNAPSHOT/resources",
      "admin",
      "adminwrong",
      BasicAuth.startWithBasicAuth)
    raFailed.getRequest("/user/admin").asInstanceOf[scala.xml.Elem]
    if (raFailed.getStatusCode == 401) {
    } else throw new Exception("Wrong StatusCode. Should be 401 Unauthorized")
  }

  //---------------Model-------------------------

  @Test
  def createModelTest = {
    val uriModel: URI = ra.postRequest("/model", model)
    log.debug("Created model with URI in " + uriModel.toString)
    uriModel
  }

  @Test
  def createAnotherModelTest = {
    val uriModel: URI = ra.postRequest("/model", model2)
    log.debug("Created model with URI in " + uriModel.toString)
    uriModel
  }

  @Test
  def deleteModelTest = {
    ra.deleteRequest("/model/metaid_0000002")
    if (ra.getStatusCode == 204) {
    } else throw new Exception("Wrong StatusCode. Should be 204 No Content")
    val xml = ra.getRequest("/model/metaid_0000002")
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")
  }

  @Test
  def deleteNonExistantModelTest = {
    ra.deleteRequest("/model/metaid_xxx")
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")
  }

  @Test
  def editModelTest = {
    ra.putRequest("/model/metaid_0000002", model)
    if (ra.getStatusCode == 200) {
    } else throw new Exception("Wrong StatusCode. Should be 200 Ok")
  }

  @Test
  def editModelBeforeCreationTest = {
    ra.putRequest("/model/metaid_0000002", model)
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")
  }

  @Test
  def browseNonExistantModelTest = {
    val xml = ra.getRequest("/model/xxx")
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")
  }

  @Test
  def browseModelTest = {
    val xml = ra.getRequest("/model/metaid_0000002")
    if (ra.getStatusCode == 200) {
    } else throw new Exception("Wrong StatusCode. Should be 200 Ok")
    assertTrue((xml \ "notes").size > 0)


    val xml2 = ra.getRequest("/model/metaid_00000020")
    if (ra.getStatusCode == 200) {
    } else throw new Exception("Wrong StatusCode. Should be 200 Ok")
    assertTrue((xml2 \ "listOfParameters" \ "parameter").size > 8)
  }


  @Test
  def getListOfModelsTest = {
    val xml = ra.getRequest("/models")
    if (ra.getStatusCode == 200) {
    } else throw new Exception("Wrong StatusCode. Should be 200 Ok")
    assertTrue((xml \ "listOfModels" \ "model").size == 2)
  }


  val model2 =
  <model metaid="metaid_0000002" id="model_0000001" name="Izhikevich2004_SpikingNeurons_Class1Excitable">
    <annotation>
      <rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:dcterms="http://purl.org/dc/terms/" xmlns:vCard="http://www.w3.org/2001/vcard-rdf/3.0#" xmlns:bqbiol="http://biomodels.net/biology-qualifiers/" xmlns:bqmodel="http://biomodels.net/model-qualifiers/">
        <rdf:Description rdf:about="#metaid_0000002">
          <dc:creator>
            <rdf:Bag>
              <rdf:li rdf:parseType="Resource">
                <vCard:N rdf:parseType="Resource">
                  <vCard:Family>He</vCard:Family>
                  <vCard:Given>Enuo</vCard:Given>
                </vCard:N>
                <vCard:EMAIL>enuo@caltech.edu</vCard:EMAIL>
                <vCard:ORG rdf:parseType="Resource">
                  <vCard:Orgname>BNMC</vCard:Orgname>
                </vCard:ORG>
              </rdf:li>
            </rdf:Bag>
          </dc:creator>
          <dcterms:created rdf:parseType="Resource">
            <dcterms:W3CDTF>2007-07-16T09:41:14Z</dcterms:W3CDTF>
          </dcterms:created>
          <dcterms:modified rdf:parseType="Resource">
            <dcterms:W3CDTF>2007-09-25T10:29:37Z</dcterms:W3CDTF>
          </dcterms:modified>
          <bqmodel:is>
            <rdf:Bag>
                <rdf:li rdf:resource="urn:miriam:biomodels.db:BIOMD0000000141"/>
            </rdf:Bag>
          </bqmodel:is>
          <bqmodel:isDescribedBy>
            <rdf:Bag>
                <rdf:li rdf:resource="urn:miriam:pubmed:15484883"/>
            </rdf:Bag>
          </bqmodel:isDescribedBy>
          <bqbiol:isVersionOf>
            <rdf:Bag>
                <rdf:li rdf:resource="urn:miriam:obo.go:GO%3A0042391"/>
                <rdf:li rdf:resource="urn:miriam:obo.go:GO%3A0001508"/>
                <rdf:li rdf:resource="urn:miriam:obo.go:GO%3A0019228"/>
            </rdf:Bag>
          </bqbiol:isVersionOf>
          <bqbiol:is>
            <rdf:Bag>
                <rdf:li rdf:resource="urn:miriam:taxonomy:40674"/>
            </rdf:Bag>
          </bqbiol:is>
        </rdf:Description>
      </rdf:RDF>
    </annotation>
    <listOfCompartments>
      <compartment metaid="metaid_0000003" id="cell" size="1">
        <annotation>
          <rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:dcterms="http://purl.org/dc/terms/" xmlns:vCard="http://www.w3.org/2001/vcard-rdf/3.0#" xmlns:bqbiol="http://biomodels.net/biology-qualifiers/" xmlns:bqmodel="http://biomodels.net/model-qualifiers/">
            <rdf:Description rdf:about="#metaid_0000003">
              <bqbiol:isVersionOf>
                <rdf:Bag>
                    <rdf:li rdf:resource="urn:miriam:obo.go:GO%3A0005623"/>
                </rdf:Bag>
              </bqbiol:isVersionOf>
            </rdf:Description>
          </rdf:RDF>
        </annotation>
      </compartment>
    </listOfCompartments>
    <listOfParameters>
        <parameter metaid="metaid_0000004" id="a" value="0.02"/>
        <parameter metaid="metaid_0000005" id="b" value="-0.1"/>
        <parameter metaid="metaid_0000006" id="c" value="-55"/>
        <parameter metaid="metaid_0000007" id="d" value="6"/>
        <parameter metaid="metaid_0000011" id="Vthresh" value="30"/>
        <parameter metaid="metaid_0000016" id="i" value="0" constant="false"/>
        <parameter metaid="metaid_0000017" id="flag" value="0" constant="false"/>
        <parameter metaid="metaid_0000020" id="v" value="-60" constant="false"/>
        <parameter metaid="metaid_0000023" id="u" value="6" constant="false"/>
    </listOfParameters>
    <listOfRules>
      <assignmentRule metaid="metaid_0000021" variable="i">
        <math xmlns="http://www.w3.org/1998/Math/MathML">
          <apply>
              <times/>
            <ci>flag</ci>
            <cn>0.075</cn>
            <apply>
                <minus/>
              <csymbol encoding="text" definitionURL="http://www.sbml.org/sbml/symbols/time">time</csymbol>
              <cn type="integer">30</cn>
            </apply>
          </apply>
        </math>
      </assignmentRule>
      <rateRule metaid="metaid_0000024" variable="v">
        <math xmlns="http://www.w3.org/1998/Math/MathML">
          <apply>
              <plus/>
            <apply>
                <minus/>
              <apply>
                  <plus/>
                <apply>
                    <times/>
                  <cn>0.04</cn>
                  <apply>
                      <power/>
                    <ci>v</ci>
                    <cn type="integer">2</cn>
                  </apply>
                </apply>
                <apply>
                    <times/>
                  <cn>4.1</cn>
                  <ci>v</ci>
                </apply>
                <cn type="integer">108</cn>
              </apply>
              <ci>u</ci>
            </apply>
            <ci>i</ci>
          </apply>
        </math>
      </rateRule>
      <rateRule metaid="metaid_0000025" variable="u">
        <math xmlns="http://www.w3.org/1998/Math/MathML">
          <apply>
              <times/>
            <ci>a</ci>
            <apply>
                <minus/>
              <apply>
                  <times/>
                <ci>b</ci>
                <ci>v</ci>
              </apply>
              <ci>u</ci>
            </apply>
          </apply>
        </math>
      </rateRule>
    </listOfRules>
    <listOfEvents>
      <event metaid="metaid_0000012" id="event_0000001">
        <trigger>
          <math xmlns="http://www.w3.org/1998/Math/MathML">
            <apply>
                <gt/>
              <ci>v</ci>
              <ci>Vthresh</ci>
            </apply>
          </math>
        </trigger>
        <listOfEventAssignments>
          <eventAssignment variable="v">
            <math xmlns="http://www.w3.org/1998/Math/MathML">
              <ci>c</ci>
            </math>
          </eventAssignment>
          <eventAssignment variable="u">
            <math xmlns="http://www.w3.org/1998/Math/MathML">
              <apply>
                  <plus/>
                <ci>u</ci>
                <ci>d</ci>
              </apply>
            </math>
          </eventAssignment>
        </listOfEventAssignments>
      </event>
      <event metaid="metaid_0000018" id="event_0000002">
        <trigger>
          <math xmlns="http://www.w3.org/1998/Math/MathML">
            <apply>
                <gt/>
              <csymbol encoding="text" definitionURL="http://www.sbml.org/sbml/symbols/time">time</csymbol>
              <cn type="integer">30</cn>
            </apply>
          </math>
        </trigger>
        <listOfEventAssignments>
          <eventAssignment variable="flag">
            <math xmlns="http://www.w3.org/1998/Math/MathML">
              <cn type="integer">1</cn>
            </math>
          </eventAssignment>
        </listOfEventAssignments>
      </event>
    </listOfEvents>
  </model>

  val model3 =
  <model metaid="metaid_01">
    <listOfUnitDefinitions>
      <unitDefinition id="per_concent_per_time">
        <listOfUnits>
            <unit kind="litre"/>
            <unit kind="mole" exponent="-1"/>
            <unit kind="second" exponent="-1"/>
        </listOfUnits>
      </unitDefinition>
    </listOfUnitDefinitions>
    <listOfSpecies>
        <species id="S1" compartment="c1" initialConcentration="2.0"/>
        <species id="S2" compartment="c1" initialConcentration="0.5"/>
        <species id="X0" compartment="c1" initialConcentration="1.0"/>
    </listOfSpecies>
    <listOfReactions>
      <reaction id="J1">
        <listOfReactants>
            <speciesReference species="X0"/>
        </listOfReactants>
        <listOfProducts>
            <speciesReference species="S1"/>
        </listOfProducts>
        <listOfModifiers>
            <modifierSpeciesReference species="S2"/>
        </listOfModifiers>
        <kineticLaw>
          <math xmlns="http://www.w3.org/1998/Math/MathML">
            <apply>
                <times/> <ci>k</ci> <ci>S2</ci> <ci>X0</ci> <ci>c1</ci>
            </apply>
          </math>
          <listOfParameters>
              <parameter id="k" value="0.1" units="per_concent_per_time"/>
          </listOfParameters>
        </kineticLaw>
      </reaction>
    </listOfReactions>
  </model>


  //---------------Parameter-------------------------

  val parameter = <parameter metaid="metaid_0000002" id="k11" value="0.02"/>
  val parameter2 = <parameter metaid="metaid_0000002" id="k2" value="0.02"/>

  @Test
  def createParameterTest = {
    val uriParameter: URI = ra.postRequest("/model/metaid_00000020/parameter", parameter)
    log.debug("Created parameter with URI in " + uriParameter.toString)
    uriParameter
  }

  @Test
  def createAnotherParameterTest = {
    val uriParameter: URI = ra.postRequest("/model/metaid_00000020/parameter", parameter)
    log.debug("Created parameter with URI in " + uriParameter.toString)
    uriParameter
  }

  @Test
  def deleteParameterTest = {
    ra.deleteRequest("/model/metaid_00000020/parameter/metaid_0000002")
    if (ra.getStatusCode == 204) {
    } else throw new Exception("Wrong StatusCode. Should be 204 No Content")
    val xml = ra.getRequest("/model/metaid_00000020/parameter/metaid_0000002")
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")

  }

  @Test
  def deleteNonExistantParameterTest = {
    ra.deleteRequest("/model/metaid_0000002/parameter/metaid_xxx")
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")
  }

  @Test
  def editParameterTest = {
    val xml = ra.getRequest("/model/metaid_0000002/parameter/metaid_0000002")
    if (ra.getStatusCode == 200) {
      assertTrue((xml \ "@metaid") == "metaid_0000002")
      assertTrue((xml \ "@id") == "k11")
    } else throw new Exception("Wrong StatusCode. Should be 200 Ok")



    ra.putRequest("/model/metaid_0000002/parameter/metaid_000002", parameter2)
    if (ra.getStatusCode == 200) {
    } else throw new Exception("Wrong StatusCode. Should be 200 Ok")

    val xmlAfter = ra.getRequest("/model/metaid_0000002/parameter/metaid_0000002")
    if (ra.getStatusCode == 200) {
      assertTrue((xmlAfter \ "@metaid") == "metaid_0000002")
      assertTrue((xmlAfter \ "@id") == "k2")
    } else throw new Exception("Wrong StatusCode. Should be 200 Ok")
  }

  @Test
  def editParameterBeforeCreationTest = {
    ra.putRequest("/model/metaid_0000002/parameter/metaid_0000023", parameter)
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")
  }

  @Test
  def browseNonExistantParameterTest = {
    val xml = ra.getRequest("/model/metaid_0000002/parameter/xxx")
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")
  }

  @Test
  def browseParameterTest = {
    val xml = ra.getRequest("/model/metaid_00000020/parameter/metaid_00000021")
    if (ra.getStatusCode == 200) {
      assertTrue((xml \ "@metaid") == "metaid_00000021")
    } else throw new Exception("Wrong StatusCode. Should be 200 Ok")
  }

  //---------------Species-------------------------

  val species = <species metaid="species_0000003" id="Glucose" compartment="cell" initialConcentration="4"/>
  val species2 = <species metaid="species_0000003" id="Glucose_6_P" compartment="cell" initialConcentration="0.75"/>

  @Test
  def createSpeciesTest = {
    val uriSpecies: URI = ra.postRequest("/model/metaid_00000020/species", species)
    log.debug("Created species with URI in " + uriSpecies.toString)
    uriSpecies
  }

  @Test
  def createAnotherSpeciesTest = {
    val uriSpecies: URI = ra.postRequest("/model/metaid_00000020/species", species)
    log.debug("Created species with URI in " + uriSpecies.toString)
    uriSpecies
  }

  @Test
  def deleteSpeciesTest = {
    ra.deleteRequest("/model/metaid_00000020/species/species_0000003")
    if (ra.getStatusCode == 204) {
    } else throw new Exception("Wrong StatusCode. Should be 204 No Content")
    val xml = ra.getRequest("/model/metaid_00000020/species/species_0000003")
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")

  }

  @Test
  def deleteNonExistantSpeciesTest = {
    ra.deleteRequest("/model/metaid_0000002/species/metaid_xxx")
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")
  }

  @Test
  def editSpeciesTest = {
    ra.putRequest("/model/metaid_0000002/species/species_0000003", species2)
    if (ra.getStatusCode == 200) {
    } else throw new Exception("Wrong StatusCode. Should be 200 Ok")
  }

  @Test
  def editSpeciesBeforeCreationTest = {
    ra.putRequest("/model/metaid_0000002/species/metaid_0000023", species)
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")
  }

  @Test
  def browseNonExistantSpeciesTest = {
    val xml = ra.getRequest("/model/metaid_0000002/species/xxx")
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")
  }

  @Test
  def browseSpeciesTest = {
    val xml = ra.getRequest("/model/metaid_00000020/species/species_00000030")
    if (ra.getStatusCode == 200) {
      assertTrue((xml \ "@metaid") == "species_00000030")
    } else throw new Exception("Wrong StatusCode. Should be 200 Ok")
  }

  //---------------Compartment-------------------------

  val compartment = <compartment metaid="compartment_0000004" id="cell" size="1"></compartment>
  val compartment2 = <compartment metaid="compartment_0000004" id="cellWall" size="1" spatialDimensions="2"></compartment>

  @Test
  def createCompartmentTest = {
    val uriCompartment: URI = ra.postRequest("/model/metaid_00000020/compartment", compartment)
    log.debug("Created compartment with URI in " + uriCompartment.toString)
    uriCompartment
  }

  @Test
  def createAnotherCompartmentTest = {
    val uriCompartment: URI = ra.postRequest("/model/metaid_00000020/compartment", compartment)
    log.debug("Created compartment with URI in " + uriCompartment.toString)
    uriCompartment
  }

  @Test
  def deleteCompartmentTest = {
    ra.deleteRequest("/model/metaid_00000020/compartment/compartment_0000004")
    if (ra.getStatusCode == 204) {
    } else throw new Exception("Wrong StatusCode. Should be 204 No Content")
    val xml = ra.getRequest("/model/metaid_00000020/compartment/compartment_0000004")
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")
  }

  @Test
  def deleteNonExistantCompartmentTest = {
    ra.deleteRequest("/model/metaid_0000002/compartment/metaid_xxx")
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")
  }

  @Test
  def editCompartmentTest = {
    ra.putRequest("/model/metaid_0000002/compartment/compartment_0000004", compartment2)
    if (ra.getStatusCode == 200) {
    } else throw new Exception("Wrong StatusCode. Should be 200 Ok")
  }

  @Test
  def editCompartmentBeforeCreationTest = {
    ra.putRequest("/model/metaid_0000002/compartment/metaid_0000023", compartment)
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")
  }

  @Test
  def browseNonExistantCompartmentTest = {
    val xml = ra.getRequest("/model/metaid_0000002/compartment/xxx")
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")
  }

  @Test
  def browseCompartmentTest = {
    val xml = ra.getRequest("/model/metaid_00000020/compartment/compartment_00000040")
    if (ra.getStatusCode == 200) {
      assertTrue((xml \ "@metaid") == "compartment_00000040")
    } else throw new Exception("Wrong StatusCode. Should be 200 Ok")
  }

  //---------------Constraint-------------------------


  //---------------Functiondefinition-------------------------

  val functionDefinition = <functionDefinition metaid="functionDefinition_0000006" id="cell" size="1"></functionDefinition>

  val functionDefinition2 = <functionDefinition metaid="functionDefinition_0000006" id="cellWall" size="1" spatialDimensions="2"></functionDefinition>


  @Test
  def createFunctionDefinitionTest = {
    val uriFunctionDefinition: URI = ra.postRequest("/model/metaid_00000020/functionDefinition", functionDefinition)
    log.debug("Created functionDefinition with URI in " + uriFunctionDefinition.toString)
    uriFunctionDefinition
  }

  @Test
  def createAnotherFunctionDefinitionTest = {
    val uriFunctionDefinition: URI = ra.postRequest("/model/metaid_00000020/functionDefinition", functionDefinition)
    log.debug("Created functionDefinition with URI in " + uriFunctionDefinition.toString)
    uriFunctionDefinition
  }

  @Test
  def deleteFunctionDefinitionTest = {
    ra.deleteRequest("/model/metaid_00000020/functionDefinition/functionDefinition_0000006")
    if (ra.getStatusCode == 204) {
    } else throw new Exception("Wrong StatusCode. Should be 204 No Content")
    val xml = ra.getRequest("/model/metaid_00000020/functionDefinition/functionDefinition_0000006")
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")
  }

  @Test
  def deleteNonExistantFunctionDefinitionTest = {
    ra.deleteRequest("/model/metaid_0000002/functionDefinition/metaid_xxx")
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")
  }

  @Test
  def editFunctionDefinitionTest = {
    ra.putRequest("/model/metaid_0000002/functionDefinition/functionDefinition_0000006", functionDefinition2)
    if (ra.getStatusCode == 200) {
    } else throw new Exception("Wrong StatusCode. Should be 200 Ok")
  }

  @Test
  def editFunctionDefinitionBeforeCreationTest = {
    ra.putRequest("/model/metaid_0000002/functionDefinition/metaid_0000023", functionDefinition)
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")
  }

  @Test
  def browseNonExistantFunctionDefinitionTest = {
    val xml = ra.getRequest("/model/metaid_0000002/functiondefinition/xxx")
    if (ra.getStatusCode == 404) {
    } else throw new Exception("Wrong StatusCode. Should be 404 Not Found")
  }

  @Test
  def browseFunctionDefinitionTest = {
    val xml = ra.getRequest("/model/metaid_00000020/functiondefinition/functiondefinition_00000060")
    if (ra.getStatusCode == 200) {
      assertTrue((xml \ "@metaid") == "functiondefinition_00000060")
    } else throw new Exception("Wrong StatusCode. Should be 200 Ok")
  }
}
