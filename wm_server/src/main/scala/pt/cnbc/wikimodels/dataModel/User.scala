/*
 * User.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel

import scala.xml.Node
import scala.xml.Elem
import scala.reflect.BeanInfo
import java.util.Date

import thewebsemantic.Id
import thewebsemantic.Namespace
import thewebsemantic.RdfProperty


/**
 * Represents a user of WikiModels
 */
@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/users.owl#")
case class User(
    @Id
    @RdfProperty("http://wikimodels.cnbc.pt/ontologies/users.owl#userName")
    var userName:String,
    @RdfProperty("http://wikimodels.cnbc.pt/ontologies/users.owl#password")
    var password:String,
    @RdfProperty("http://xmlns.com/foaf/0.1/firstName")
    var firstName:String,
    @RdfProperty("http://xmlns.com/foaf/0.1/surname")
    var surName:String,
    @RdfProperty("http://www.w3.org/2000/10/swap/pim/contact#emailAddress")
    var emailAddress:String) extends DataModel {
    
    def this() = {
        this("","","","","")
    }


    def this(xmlUser:Elem) = {
        this((xmlUser \ "userName").text,
             null,
             (xmlUser \ "firstName").text,
             (xmlUser \ "surName").text,
             (xmlUser \ "emailAddress").text)
    }
    
    /**
     * generates a XML representation of the user, excluding password
     * @return the XML representing the user
     */
    def toXML:Elem =
        <user>
            <userName>{this.userName}</userName>
            <!--<password>{password}</password>-->
            <firstName>{this.firstName}</firstName>
            <surName>{this.surName}</surName>
            <emailAddress>{this.emailAddress}</emailAddress>
        </user>
}

