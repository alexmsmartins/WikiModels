/*
 * SimpleSecurityContext.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.security

class SimpleSecurityContext extends SecurityContext{


  def isAuthorizedTo(resource:String, method:String, user:String): boolean = {
    //the called resource is checked with regular expressions
    user match {
      case "alex" => true
      case "gonçalo" => true
      case _ => false
    }
    //TODO - implemnt an RBAC lke permission system.

  }
}
