/*
 * App.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.client

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert._

import org.apache.log4j._

import scala.xml.Elem

import pt.cnbc.wikimodels.rest.client.BasicAuth
import pt.cnbc.wikimodels.rest.client.RestfulAccess


object App {
    var ra:RestfulAccess = null
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
    def main(args:Array[String]):Unit = {
        val log  = Logger.getLogger(this.getClass)
        log.info("WikiModels access API Integration testing.")
        var testNumber = 1
        try {
            setUp
            /*log.info("======================" )
            log.info("TEST 1 - Login" )
            loginTest
            log.info("SUCCESS" )
            log.info("======================" )
            log.info("TEST 1 - CreateModel" )
            createModelTest
            log.info("SUCCESS" )*/
            log.info("======================" )
            log.info("TEST 3 - EditModel" )
            editModelTest
            log.info("SUCCESS" )
            /*log.info("======================" )
            log.info("TEST 4 - DeleteModel" )
            deleteModelTest
            log.info("SUCCESS" )*/
            log.info("======================" )
            log.info("INTEGRATION TESTING SUCCEDED")
            //basicAuthentication
            //digestAuthentication

        } catch {
            case e:Exception => {
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
    val model:Elem =
    <model metaid="metaid_0000002" id="model_0000001" name="Izhikevich2004_SpikingNeurons_Class1Excitable">
        <notes>
                <h1>SBML model of the interlocked feedback loop network
                </h1>
                <p xmlns="http://www.w3.org/1999/xhtml">
                    The model describes the circuit depicted in Fig. 4 and reproduces the simulations in Figure 5A
                    and 5B. It provides initial conditions, parameter values and rules for the production rates of the
                    following species: LHY mRNA (cLm), cytoplasmic LHY (cLc), nuclear LHY (cLn),
                    TOC1 mRNA (cTm), cytoplasmic TOC1 (cTc), nuclear TOC1 (cTn),X mRNA (cXm),
                    cytoplasmic X (cXc), nuclear X (cXn), Y mRNA (cYm), cytoplasmic Y (cYc),
                    nuclear Y (cYn), nuclear P (cPn). This model was successfully tested on MathSBML and SBML ODE Solver.
                </p>
                <p xmlns="http://www.w3.org/1999/xhtml">

                    Fig 5B is not in the right phase. However, the data is correct relative to the light/dark bars at the top of the figure.
                </p>
                <p xmlns="http://www.w3.org/1999/xhtml">This model originates from BioModels Database: A Database of Annotated Published Models. It is copyright (c) 2005-2007 The BioModels Team.<br></br> For more information see the <a href="http://www.ebi.ac.uk/biomodels/legal.html">terms of use</a>.</p>
        </notes>
    </model>

    @Test
    def loginTest = {
        ra.getRequest("/user/admin").asInstanceOf[scala.xml.Elem]
    }

    @Test
    def createModelTest = {
        ra.postRequest("/model/123", model)
    }

    @Test
    def editModelTest = {
        ra.putRequest("/model/123", model)
    }

    @Test
    def deleteModelTest = {
        ra.deleteRequest("/model/123")
    }
}
