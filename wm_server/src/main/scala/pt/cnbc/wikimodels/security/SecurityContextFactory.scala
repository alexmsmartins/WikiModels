/*
 * SecurityContextFactory.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.security

//note that this import replaces the need for various Child factories
//To change SecurityContext just replace the package and class before symbol =>
import pt.cnbc.wikimodels.security.{SimpleSecurityContext => SecurityContextImpl}


/**
 * Creates a SecurityContext that will tell WikiModels Server if the requests
 * done by the user are to be authorized or not
 */
object SecurityContextFactory {

    /**
     * Creates the SecurityContext for this application
     */
    def createSecurityContext:SecurityContext = {
        new SecurityContextImpl
    }
}
