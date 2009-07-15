/*
 * SecurityContext.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.security

/**
 * Defines the interface that every  implementation of authorization in Wikimodels as to implement
 */
trait SecurityContext {
  /**
   * checks if a user as permissions to call a method to act upon a resource
   *
   */
  def isAuthorizedTo(resource:String, method:String, user:String): Boolean
}