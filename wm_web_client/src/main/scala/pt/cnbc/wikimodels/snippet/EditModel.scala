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

import pt.cnbc.wikimodels.rest.client.RestfulAccess

class EditModel {
    var global:String = null

    def edit (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
                var model_id:String = null
                //parameter variables
                var param_id:String = null
                var param_name:String = null
                var param_value:String = null
                var param_constant:String = null
                var param_notes:String = null


                var description:String = null
                val msgName: String = S.attr("id_msgs") openOr "messages"

                val teste = S.uri
                var restful:RestfulAccess = User.getRestful
                //Console.println("aqui..->"+teste)
                val modelAtribute = restful.getRequest(teste)
                if(restful.getStatusCode == 200){
                    val listaNova = List("true","false")
                    if(teste.contains("parameter")){
                        def updateParameter() ={
                            Console.println("-----DADOS A ACTUALIZAR-----")
                            Console.println("id-"+param_id+"- name-"+param_name+"- value-"+param_value+"- constant-"+param_constant+"-")
                            Console.println("teste------->>>>>"+teste)
                            if (param_id.length == 0 && param_name.length == 0) {
                                S.error("Invalid Data")
                            } else {
                                val modelAtributeUpdate = {
                                    <parameter id={param_id} name={param_name} value={param_value} constant={param_constant}>
                                        <notes>
                                            <body>{param_notes}
                                            </body>
                                        </notes>
                                    </parameter>}
                                restful.putRequest(teste, modelAtributeUpdate)
                                Console.println("STATUS RECEBIDO--------"+restful.getStatusCode)
                                if(restful.getStatusCode == 200){
                                    var num = teste.indexOf("/parameter")
                                    var newLink = teste.substring(0, num)
                                    S.redirectTo(newLink)
                                } else {
                                    S.error("Invalid content")
                                }
                            }
                        }
                        def setParameterC(va :String){
                            param_constant = va
                        }
                        Console.println("EDIT...----->>>> "+modelAtribute)
                        bind("editModel", xhtml,
                             "editParameter" -> {<ul style="list-style-type: none;">

                                                 <li>Parameter Name <span id="required_field">*</span>: &nbsp;
                                    {SHtml.text((modelAtribute \\ "@name").text, v => {param_name = v
                                                                                       param_id = (v.replace(" ", "").toLowerCase)},("id", "parameterName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                                 <li>Parameter Value : &nbsp;
                                    {SHtml.text((modelAtribute \\ "@value").text, v => param_value = v,("id", "parameterValue"), ("size", "10"), ("maxlength", "100"))}</li>
                                                 <li>Parameter Constant: &nbsp;<i>(default="true")</i> &nbsp;
                                    {SHtml.select(listaNova.map(v => (v, v)), Empty, setParameterC _)}</li>
                                                 <li>Parameter Note:<br />
                                    {SHtml.textarea((modelAtribute \ "notes").toString, n => param_notes = n,("id", "editAreas"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
                                                 </ul>},
                             "editSpecies" -> "",
                             "save" -> SHtml.submit("Save Changes", updateParameter,("id","buttonSaveChange"))
                        )
                    } else {
                        bind("editModel", xhtml)
                    }
                } else {
                    S.error("SBML model does not exist")
                    S.redirectTo(teste)
                    bind("editModel", xhtml)
                }

                /**
                 * generates a XML representation of the SBML Model
                 * it does not include the DOCTYPE declaration or the sbml top tag
                 * Those should be added by a proper export funtion when it is created
                 * @return the XML representing the user
                 */
                /*def updateModel () = {
                 /*model_id = name.replace(" ", "").toLowerCase
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
                  }*/
                 }*/

                // This method creates the id of the model
                /*def createNameAndId(nameml: NodeSeq) = {
                 SHtml.ajaxText("", ref => { name = ref;
                 DisplayMessage(msgName,
                 bind("text", nameml, "model_id" ->
                 SHtml.text(Text(ref.replace(" ", "").toLowerCase).toString, refer2 => model_id = refer2, ("id", "model_id"), ("size", "40"), ("disabled", "disabled"))),
                 300000 seconds, 1 second)}, ("id", "name_model"), ("size", "40"), ("maxlength", "1000"))
                 }*/
                /*module match {
                 case Full("001") => {
                            
                 }
                 case Full("002") => {
                 bind("editModel", xhtml,
                 "name" -> createNameAndId _,
                 "description" -> SHtml.textarea("", description = _, ("id", "editArea"), ("maxlength", "20000")),
                 "save" -> SHtml.submit("Save Changes", updateModel,("id","buttonSaveChange"))
                 )
                 }
                 case _ => Text("")
                 }*/
            }
        case _ => Text("")
    }
}
