/*
 * RESTResource.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.rest


import pt.cnbc.wikimodels.security.SecurityContextFactory

import javax.ws.rs.core.Context
import javax.ws.rs.core.SecurityContext

trait RESTResource {
    val secContext = SecurityContextFactory.createSecurityContext
}
