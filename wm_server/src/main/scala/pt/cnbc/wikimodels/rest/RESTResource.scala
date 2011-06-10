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
import org.slf4j.LoggerFactory

trait RESTResource {
  val logger = LoggerFactory.getLogger(getClass)

  val secContext = SecurityContextFactory.createSecurityContext
}
