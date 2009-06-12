/*
 * User.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.dataModel

import scala.xml.Node

case class User(userName:String , password:String, fullName:String, email:String ) {
    def toXML:Node =
        <users>
            <user>
                <username>
                    {userName}
                </username>
                <password>
                    {password}
                </password>
                <fullName>
                    {fullName}
                </fullName>
                <email>
                    {email}
                </email>
            </user>
        </users>

}

