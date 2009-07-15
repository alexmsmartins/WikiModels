/*
 * SimpleSecurityContextTest.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.security

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert._

class SimpleSecurityContextTest {

    val ssc = new SimpleSecurityContext

    @Before
    def setUp: Unit = {
    }

    @After
    def tearDown: Unit = {
    }

    /**
     * tests if "admin" is authorized to do everything to every resource
     */
    @Test
    def checkAdminAuthorization =
    assertTrue(""""admin" should be authorized to do everything.""",
        ssc.isAuthorizedTo(
            "admin", "GET" ,""
        ) && ssc.isAuthorizedTo(
            "admin", "PUT", ""
        ) && ssc.isAuthorizedTo(
            "admin", "POST", ""
        ) && ssc.isAuthorizedTo(
            "admin", "DELETE", ""
        ))

    @Test
    def checkAnonymousAuthorization =
    assertTrue(""""anonymous" should be authorized to read and nothing more""",
        ssc.isAuthorizedTo(
            "anonymous", "GET", "model/BM00001"
        ) && ssc.isAuthorizedTo(
            "anonymous", "GET",  "model/"
        ) && ssc.isAuthorizedTo(
            "anonymous", "GET", "models/"
        ) && ssc.isAuthorizedTo(
            "anonymous", "GET", "models/xxx"
        ) && ! ssc.isAuthorizedTo(
            "anonymous", "PUT", "model/BM00001"
        ) && ! ssc.isAuthorizedTo(
            "anonymous", "POST", "model/"
        ) && ! ssc.isAuthorizedTo(
            "anonymous", "DELETE", "models/"
        ) && ! ssc.isAuthorizedTo(
            "anonymous", "POST", "models/xxx"
        ))

    @Test
    def normalUSersAuthorization =
    assertTrue("""Regular user should be authorized to read and change their information and all the models""",
        ssc.isAuthorizedTo(
             "alex", "GET", "model/BM00001"
        ) && ssc.isAuthorizedTo(
             "alex", "GET", "model/"
        ) && ssc.isAuthorizedTo(
            "Gonçalo", "GET", "models/"
        ) && ssc.isAuthorizedTo(
            "Gonçalo", "GET", "models/xxx"
        ) && ssc.isAuthorizedTo(
            "Armindo", "PUT", "model/BM00001"
        ) && ssc.isAuthorizedTo(
            "Armindo", "POST", "model/"
        ) && ssc.isAuthorizedTo(
            "xxx", "DELETE", "models/"
        ) && ssc.isAuthorizedTo(
            "xxx", "POST", "models/xxx"
        ) && ssc.isAuthorizedTo(
            "xxx", "POST", "user/xxx"
        ) && ! ssc.isAuthorizedTo(
            "xxx", "POST", "user/xxy"
        ))

}
