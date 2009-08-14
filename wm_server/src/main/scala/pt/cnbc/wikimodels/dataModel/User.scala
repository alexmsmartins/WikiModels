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
import thewebsemantic.RdfType
import scala.reflect.BeanProperty


/**
 * Represents a user of WikiModels
 */
@BeanInfo
@Namespace("http://wikimodels.cnbc.pt/ontologies/users.owl#")
@RdfType("User")
case class User(
    @Id
    var userName:String,
    var password:String,
    @RdfProperty("http://xmlns.com/foaf/0.1/firstName")
    var firstName:String,
    @RdfProperty("http://xmlns.com/foaf/0.1/surname")
    var surName:String,
    @RdfProperty("http://www.w3.org/2000/10/swap/pim/contact#emailAddress")
    var emailAddress:String
) extends DataModel {



    def this() = {
        this(null, null, null, null, null)
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

