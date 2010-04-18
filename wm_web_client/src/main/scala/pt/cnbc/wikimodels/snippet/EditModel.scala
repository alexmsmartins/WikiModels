package pt.cnbc.wikimodels.snippet

import net.liftweb._
import http._
import util._
import Helpers._
import sitemap.Loc.If
import scala.xml.{ XML, Elem, Group, Node, NodeSeq, Null, Text, TopScope }
import S._
import net.liftweb.http.js._
import js.JsCmds
import js.JsCmds._
import js.Jx
import js.JE
import js.JE._
import js.jquery._
import JqJsCmds._
import java.util.Hashtable
import java.util.Enumeration
import net.liftweb.common._

class EditModel {
    var global:String = null

    def edit (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
                var id:String = null
                var model_id:String = null
                var name:String = null
                var description:String = null
                val msgName: String = S.attr("id_msgs") openOr "messages"

                val module = S.param("module")
                Console.println("Valor ---> "+module)

                /**
                 * generates a XML representation of the SBML Model
                 * it does not include the DOCTYPE declaration or the sbml top tag
                 * Those should be added by a proper export funtion when it is created
                 * @return the XML representing the user
                 */
                def updateModel () = {
                    model_id = name.replace(" ", "").toLowerCase
                    if (model_id.length == 0 && name.length == 0) {
                        S.error("Invalid Data")
                    } else {
                        val modelSBML = {
                            <model id={model_id} name={name}>
                                <notes>
                                    <body>{XML.loadString(description)}
                                    </body>
                                </notes>
                            </model>}
                        XML.save("file.xml", modelSBML)
                    }
                }

                // This method creates the id of the model
                def createNameAndId(nameml: NodeSeq) = {
                    SHtml.ajaxText("", ref => { name = ref;
                                               DisplayMessage(msgName,
                                                              bind("text", nameml, "model_id" ->
                                                                   SHtml.text(Text(ref.replace(" ", "").toLowerCase).toString, refer2 => model_id = refer2, ("id", "model_id"), ("size", "40"), ("disabled", "disabled"))),
                                                              300000 seconds, 1 second)}, ("id", "name_model"), ("size", "40"), ("maxlength", "1000"))
                }
                module match {
                    case Full("001") => {
                            bind("editModel", xhtml,
                                 "name" -> createNameAndId _,
                                 "description" -> SHtml.textarea("", description = _, ("id", "editArea"), ("maxlength", "20000")),
                                 "something" -> SHtml.textarea("", description = _, ("id", "editArea"), ("maxlength", "20000")),
                                 "save" -> SHtml.submit("Save Changes", updateModel,("id","buttonSaveChange"))
                            )
                        }
                    case Full("002") => {
                            bind("editModel", xhtml,
                                 "name" -> createNameAndId _,
                                 "description" -> SHtml.textarea("", description = _, ("id", "editArea"), ("maxlength", "20000")),
                                 "save" -> SHtml.submit("Save Changes", updateModel,("id","buttonSaveChange"))
                            )
                        }
                    case _ => Text("")
                }
            }
        case _ => Text("")
    }
}
