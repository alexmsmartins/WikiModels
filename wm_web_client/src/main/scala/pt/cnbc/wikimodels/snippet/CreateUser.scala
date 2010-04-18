package pt.cnbc.wikimodels.snippet

import net.liftweb.util.Helpers._
import net.liftweb.sitemap.Loc.If
import scala.xml.{ XML, Elem, Group, Node, NodeSeq, Null, Text, TopScope }
import net.liftweb.http.S
import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JsCmds.JsCrVar
import net.liftweb.http.js.Jx
import net.liftweb.http.js.JE._
import net.liftweb.http.js.jquery._
import net.liftweb.http.js.jquery.JqJsCmds._
import java.util.TreeMap
import java.util.LinkedList
import java.util.Enumeration
import scala.collection.jcl.Collection
import net.liftweb.common._
import pt.cnbc.wikimodels.model._

/*import pt.cnbc.wikimodels.rest.client.BasicAuth
 import pt.cnbc.wikimodels.rest.client.RestfulAccess*/


class CreateUser {

    def editUser (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
                var username:String = null
                var password:String = null
                var repeat_password:String = null
                var firstName:String = null
                var surName:String = null
                var firstEmail:String = null

                val msgName: String = S.attr("id_msgs") openOr "messages"

                /**
                 * generates a XML representation of the SBML Model
                 * it does not include the DOCTYPE declaration or the sbml top tag
                 * Those should be added by a proper export funtion when it is created
                 * @return the XML representing the user
                 */

                def updateUserXML () = {

                    if (password.length == 0) {
                        S.error("Please insert the password!")
                    } else if (repeat_password.length == 0) {
                        S.error("Please insert the password!")
                    } else if (!password.equals(repeat_password)) {
                        S.error("Passwords do not match.")
                    } else if (firstEmail.length == 0) {
                        S.error("Please insert a valid email!")
                    } else if (!firstEmail.contains("@")) {
                        S.error("Please insert a valid email!")
                    } else if ((firstName.length == 0) || (surName.length == 0)) {
                        S.error("Please insert a valid name!")
                    } else {
                        val userXML = {
                            <user>
                                <userName>{user}</userName>
                                <password>{password}</password>
                                <firstName>{firstName}</firstName>
                                <surName>{surName}</surName>
                                <email>{firstEmail}</email>
                            </user>
                        }
                        Console.println(userXML)
                        XML.save("userNew.xml", userXML)

                    }
                }

                bind("updateUser", xhtml,
                     "username" -> user,
                     "password" -> SHtml.password("", v => password = v, ("id", "passArea"), ("size", "30"), ("maxlength", "40")),
                     "verifyPassword" -> SHtml.password("", v => repeat_password = v, ("id", "repassArea"), ("size", "30"), ("maxlength", "40")),
                     "firstName" -> SHtml.text("", v => firstName = v, ("id", "nameArea"), ("size", "30"), ("maxlength", "200")),
                     "surName" -> SHtml.text("", v => surName = v, ("id", "nameArea"), ("size", "30"), ("maxlength", "200")),
                     "firstEmail" -> SHtml.text("", v => firstEmail = v, ("id", "emailArea"), ("size", "30"), ("maxlength", "300")),
                     "save" -> SHtml.submit("Update User", updateUserXML))
            }
        case _ => S.redirectTo("/index")
    }

    def createNewUser (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => S.redirectTo("/index")
        case _ => {
                var username:String = null
                var password:String = null
                var repeat_password:String = null
                var firstName:String = null
                var surName:String = null
                var firstEmail:String = null
                var optionalEmail:String = null
                var typeContacts = new LinkedList[String]()
                var contacts = new LinkedList[String]()
                var institution:String = null
                var country:String = null
                var title:String = null

                val msgName: String = S.attr("id_msgs") openOr "messages"

                /**
                 * generates a XML representation of the SBML Model
                 * it does not include the DOCTYPE declaration or the sbml top tag
                 * Those should be added by a proper export funtion when it is created
                 * @return the XML representing the user
                 */

                def createUserXML () = {

                    if (username.length == 0) {
                        S.error("Please insert a valid username!")
                    } else if (password.length == 0) {
                        S.error("Please insert the password!")
                    } else if (repeat_password.length == 0) {
                        S.error("Please insert the password!")
                    } else if (!password.equals(repeat_password)) {
                        S.error("Passwords do not match.")
                    } else if (firstEmail.length == 0) {
                        S.error("Please insert a valid email!")
                    } else if (!firstEmail.contains("@")) {
                        S.error("Please insert a valid email!")
                    } else if ((firstName.length == 0) || (surName.length == 0)) {
                        S.error("Please insert a valid name!")
                    } else {
                        val userXML = {
                            <user>
                                <userName>{username}</userName>
                                <password>{password}</password>
                                <firstName>{firstName}</firstName>
                                <surName>{surName}</surName>
                                <title>{title}</title>
                                <email>{firstEmail}</email>
                                <alternativeEmail>{optionalEmail}</alternativeEmail>
                                <listOfContacts>{
                                        for(val i <- 0 to (contacts.size-1)) yield {
                                            <contact typeContact={typeContacts.get(i)}>
                                                {
                                                    contacts.get(i)
                                                }
                                            </contact>
                                        }
                                    }
                                </listOfContacts>
                                <institution>{institution}</institution>
                                <country>{country}</country>
                            </user>
                        }
                        Console.println(userXML)
                        XML.save("userNew.xml", userXML)
                        
                    }
                }

                val listContactTypes = List("MSN", "ICQ", "AIM", "Yahoo", "SKYPE", "Personal webpage", "Other")

                val listCountries = List("","Afghanistan","Albania","Algeria","American Samoa","Andorra","Angola","Anguilla","Antarctica","Antigua and Barbuda","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan",
                                         "Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia and Herzegovina","Botswana","Bouvet Island","Brazil",
                                         "British Indian Ocean Territory","Brunei Darussalam","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Canada","Cape Verde","Cayman Islands","Central African Republic",
                                         "Chad","Chile","China","Christmas Island","Cocos (Keeling) Islands","Colombia","Comoros","Congo","Congo, The Democratic Republic of The","Cook Islands","Costa Rica","Cote D'ivoire",
                                         "Croatia","Cuba","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia",
                                         "Falkland Islands (Malvinas)","Faroe Islands","Fiji","Finland","France","French Guiana","French Polynesia","French Southern Territories","Gabon","Gambia","Georgia","Germany","Ghana",
                                         "Gibraltar","Greece","Greenland","Grenada","Guadeloupe","Guam","Guatemala","Guinea","Guinea-bissau","Guyana","Haiti","Heard Island and Mcdonald Islands","Holy See (Vatican City State)",
                                         "Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","Iran, Islamic Republic of","Iraq","Ireland","Israel","Italy","Jamaica","Japan","Jordan","Kazakhstan","Kenya","Kiribati",
                                         "Korea, Democratic People's Republic of","Korea, Republic of","Kuwait","Kyrgyzstan","Lao People's Democratic Republic","Latvia","Lebanon","Lesotho","Liberia","Libyan Arab Jamahiriya",
                                         "Liechtenstein","Lithuania","Luxembourg","Macao","Macedonia, The Former Yugoslav Republic of","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Martinique",
                                         "Mauritania","Mauritius","Mayotte","Mexico","Micronesia, Federated States of","Moldova, Republic of","Monaco","Mongolia","Montserrat","Morocco","Mozambique","Myanmar","Namibia","Nauru",
                                         "Nepal","Netherlands","Netherlands Antilles","New Caledonia","New Zealand","Nicaragua","Niger","Nigeria","Niue","Norfolk Island","Northern Mariana Islands","Norway","Oman","Pakistan",
                                         "Palau","Palestinian Territory, Occupied","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Pitcairn","Poland","Portugal","Puerto Rico","Qatar","Reunion","Romania","Russian Federation",
                                         "Rwanda","Saint Helena","Saint Kitts and Nevis","Saint Lucia","Saint Pierre and Miquelon","Saint Vincent and The Grenadines","Samoa","San Marino","Sao Tome and Principe","Saudi Arabia",
                                         "Senegal","Serbia and Montenegro","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Solomon Islands","Somalia","South Africa","South Georgia and The South Sandwich Islands",
                                         "Spain","Sri Lanka","Sudan","Suriname","Svalbard and Jan Mayen","Swaziland","Sweden","Switzerland","Syrian Arab Republic","Taiwan, Province of China","Tajikistan","Tanzania, United Republic of",
                                         "Thailand","Timor-leste","Togo","Tokelau","Tonga","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan","Turks and Caicos Islands","Tuvalu","Uganda","Ukraine","United Arab Emirates",
                                         "United Kingdom","United States of America","United States Minor Outlying Islands","Uruguay","Uzbekistan","Vanuatu","Venezuela","Vietnam","Virgin Islands, British","Virgin Islands, U.S.",
                                         "Wallis and Futuna","Western Sahara","Yemen","Zambia","Zimbabwe")

                def selectCountry(va :String){
                    country = va
                }
                def selectContactType(va :String){
                    typeContacts.add(va)
                }

                bind("createNewUser", xhtml,
                     "username" -> SHtml.text("", v => username = v, ("id", "loginArea"), ("size", "30"), ("maxlength", "40")),
                     "password" -> SHtml.password("", v => password = v, ("id", "passArea"), ("size", "30"), ("maxlength", "40")),
                     "verifyPassword" -> SHtml.password("", v => repeat_password = v, ("id", "repassArea"), ("size", "30"), ("maxlength", "40")),
                     "firstName" -> SHtml.text("", v => firstName = v, ("id", "nameArea"), ("size", "30"), ("maxlength", "200")),
                     "surName" -> SHtml.text("", v => surName = v, ("id", "nameArea"), ("size", "30"), ("maxlength", "200")),
                     "title" -> SHtml.text("", v => title = v, ("id", "titleArea"), ("size", "5"), ("maxlength", "10")),
                     "firstEmail" -> SHtml.text("", v => firstEmail = v, ("id", "emailArea"), ("size", "30"), ("maxlength", "300")),
                     "secondaryEmail" -> SHtml.text("", v => optionalEmail = v, ("id", "opEmailArea"), ("size", "30"), ("maxlength", "300")),
                     "contacts" -> SHtml.ajaxButton(Text("Add Contact"), () => {
                            JsCrVar("contactNew", Jx(<table width="100%" style="background-color:#dddddd">
                                                     <tr>
                                        <td width="50%" style="text-align:right;">Type:</td>
                                        <td width="50%" style="text-align:right;">Address:</td>
                                                     </tr>
                                                     <tr>
                                        <td width="50%">{SHtml.select(listContactTypes.map(v => (v,v)), Empty, selectContactType _,("id", "contacts"))}</td>
                                        <td width="50%">{SHtml.text("", v => {contacts.add(v)},("id", "contacts"), ("size", "30"), ("maxlength", "300"))}</td>
                                                     </tr>
                                                     </table>
                                                     <hr /><br />).toJs) &
                            (ElemById("contact_new") ~> JsFunc("appendChild", Call("contactNew", "")))
                        }),
                     "institution" -> SHtml.text("", v => institution = v, ("id", "instArea"), ("size", "30"), ("maxlength", "300")),
                     "country" -> SHtml.select(listCountries.map(v => (v, v)), Empty, selectCountry _, ("id", "countryArea")),
                     "save" -> SHtml.submit("Create User", createUserXML))
            }

    }
}
