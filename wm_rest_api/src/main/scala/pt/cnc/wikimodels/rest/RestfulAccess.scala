/*
 * RestFulAccess.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package pt.cnc.wikimodels.rest


trait RestfulAccess {
    def checkURL(url:String, userHash:String):String ;
    def generateHash(username:String, password:String) = {
        username + password
    }
}

class

