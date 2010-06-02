/*
 * CommentResource.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.rest

import javax.ws.rs.core.{UriInfo, SecurityContext, Context}

class CommentsResource {

  @Context
  var security:SecurityContext = null
  @Context
  var uriInfo:UriInfo =null;

}
