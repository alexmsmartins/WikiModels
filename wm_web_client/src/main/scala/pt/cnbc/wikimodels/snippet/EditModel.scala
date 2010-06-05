package pt.cnbc.wikimodels.snippet

import net.liftweb._
import http._
import util._
import Helpers._
import sitemap.Loc.If
import scala.xml.{ XML, Elem, Group, Node, NodeSeq, Null, Text, TopScope }
import S._
import java.net.URI
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

    def script = {
        val uriLink = S.uri
        if(uriLink.contains("/edit")){
            <script src="../../classpath/js/fckeditor/fckeditor.js" type="text/javascript"></script>
        } else {
            <script src="../../../classpath/js/fckeditor/fckeditor.js" type="text/javascript"></script>
        }
    }

    def editWhat (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
                val uriLink = S.uri
                var restful:RestfulAccess = User.getRestful
                var modelAtribute:Elem = null
                if(uriLink.contains("/edit")){
                    var uriIndex = uriLink.indexOf("/edit")
                    var uriNew = uriLink.substring(0, uriIndex)
                    modelAtribute = restful.getRequest(uriNew)
                } else {
                    modelAtribute = restful.getRequest(uriLink)
                }
                if(restful.getStatusCode == 200){
                    bind("editSome", xhtml,
                         "editWhat" -> <div>{
                                if(uriLink.contains("parameter")){
                                    <div>Parameter</div>
                                } else if(uriLink.contains("species")){
                                    <div>Species</div>
                                } else if(uriLink.contains("compartment")){
                                    <div>Compartment</div>
                                } else if(uriLink.contains("constraint")){
                                    <div>Constraint</div>
                                } else if(uriLink.contains("functiondefinition")){
                                    <div>Function Definition</div>
                                } else if(uriLink.contains("reaction")){
                                    <div>Reaction</div>
                                } else {
                                    <div>Model</div>
                                }
                            } </div>)
                } else {
                    S.error("SBML model does not exist")
                    S.redirectTo(uriLink)
                    bind("editModel", xhtml)
                }}
        case _ => Text("")
    }

    def edit (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
                var model_bool:Boolean = false
                var model_id:String = null
                var model_name:String = null
                var model_notes:String = null
                var model_metaid:String = null

                //parameter variables
                var param_bool:Boolean = false
                var param_id:String = null
                var param_name:String = null
                var param_value:String = null
                var param_constant:String = null
                var param_notes:String = null
                var param_metaid:String = null

                var spec_bool:Boolean = false
                var spec_id:String = null
                var spec_name:String = null
                var spec_compartment:String = null
                var spec_init_amount:String = null
                var spec_init_concent:String = null
                var spec_bc:String = null
                var spec_c:String = null
                var spec_notes:String = null
                var spec_metaid:String = null

                var comp_bool:Boolean = false
                var comp_id:String = null
                var comp_name:String = null
                var comp_sd:String = null
                var comp_size:String = null
                var comp_outside:String = null
                var comp_c:String = null
                var comp_notes:String = null
                var comp_metaid:String = null

                var const_bool:Boolean = false
                var const_math:String = null
                var const_message:String = null
                var const_notes:String = null
                var const_metaid:String = null

                var funDef_bool:Boolean = false
                var funDef_id:String = null
                var funDef_name:String = null
                var funDef_math:String = null
                var funDef_notes:String = null
                var funDef_metaid:String = null

                var reac_bool:Boolean = false
                var reac_id:String = null
                var reac_name:String = null
                var reac_r:String = null
                var reac_f:String = null
                var reac_constant:String = null
                var reac_notes:String = null
                var reac_metaid:String = null


                
                val msgName: String = S.attr("id_msgs") openOr "messages"

                val uriLink = S.uri
                var restful:RestfulAccess = User.getRestful
                var modelAtribute:Elem = null
                if(uriLink.contains("/edit")){
                    var uriIndex = uriLink.indexOf("/edit")
                    var uriNew = uriLink.substring(0, uriIndex)
                    modelAtribute = restful.getRequest(uriNew)
                } else {
                    modelAtribute = restful.getRequest(uriLink)
                }
                if(restful.getStatusCode == 200){
                    val listaNova = List("true","false")

                    val param_metaid_num = uriLink.indexOf("/parameter/metaid")
                    param_metaid = uriLink.substring(param_metaid_num+11)

                    val spec_metaid_num = uriLink.indexOf("/species/metaid")
                    spec_metaid = uriLink.substring(spec_metaid_num+9)

                    val comp_metaid_num = uriLink.indexOf("/compartment/metaid")
                    comp_metaid = uriLink.substring(comp_metaid_num+13)

                    val const_metaid_num = uriLink.indexOf("/constant/metaid")
                    const_metaid = uriLink.substring(const_metaid_num+10)

                    val funDef_metaid_num = uriLink.indexOf("/functiondefinition/metaid")
                    funDef_metaid = uriLink.substring(funDef_metaid_num+20)

                    val model_metaid_num = uriLink.indexOf("/model/metaid")
                    model_metaid = uriLink.substring(model_metaid_num+7)

                    def cancelSection() ={
                        var linkIndex = 0
                        var newLink = ""
                        if(uriLink.contains("parameter")){
                            linkIndex = uriLink.indexOf("/parameter")
                            newLink = uriLink.substring(0, linkIndex)
                        } else if(uriLink.contains("species")){
                            linkIndex = uriLink.indexOf("/species")
                            newLink = uriLink.substring(0, linkIndex)
                        } else if(uriLink.contains("compartment")){
                            linkIndex = uriLink.indexOf("/compartment")
                            newLink = uriLink.substring(0, linkIndex)
                        } else if(uriLink.contains("constraint")){
                            linkIndex = uriLink.indexOf("/constraint")
                            newLink = uriLink.substring(0, linkIndex)
                        } else if(uriLink.contains("functiondefinition")){
                            linkIndex = uriLink.indexOf("/functiondefinition")
                            newLink = uriLink.substring(0, linkIndex)
                        } else if(uriLink.contains("reaction")){
                            linkIndex = uriLink.indexOf("/reaction")
                            newLink = uriLink.substring(0, linkIndex)
                        } else {
                            linkIndex = uriLink.indexOf("/edit")
                            newLink = uriLink.substring(0, linkIndex)
                        }
                        S.redirectTo(newLink)
                    }
                    //if(uriLink.contains("parameter")){
                    def updateSection() ={
                        if(param_bool){
                            if (param_id.length == 0 && param_name.length == 0) {
                                S.error("Invalid Data")
                            } else {
                                val modelParameterUpdate = {
                                    <parameter id={param_id} name={param_name} value={param_value} constant={param_constant} metaid={param_metaid}>
                                        {if(param_notes.length > 0){
                                                <notes>
                                                    <body>{XML.loadString(param_notes.toString)}
                                                    </body>
                                                </notes>
                                            }}
                                    </parameter>
                                }
                                restful.putRequest(uriLink, modelParameterUpdate)

                                if((restful.getStatusCode >= 200) || (restful.getStatusCode < 300)){
                                    var num = uriLink.indexOf("/parameter")
                                    var newLink = uriLink.substring(0, num)
                                    S.redirectTo(newLink)
                                } else {
                                    S.error("Invalid content")
                                }
                            }
                            param_bool = false
                        } else if(spec_bool){
                            if (spec_id.length == 0 && spec_name.length == 0) {
                                S.error("Invalid Data")
                            } else {
                                val modelSpecUpdate = {
                                    <species id={spec_id} name={spec_name} compartment={spec_compartment} initialAmount={spec_init_amount}
                                        initialConcentration={spec_init_concent} boundaryCondition={spec_bc} constant={spec_c} metaid={spec_metaid}>
                                        {if(spec_notes.length > 0){
                                                <notes>
                                                    <body>{XML.loadString(spec_notes.toString)}
                                                    </body>
                                                </notes>
                                            }}
                                    </species>
                                }
                                
                                restful.putRequest(uriLink, modelSpecUpdate)

                                if((restful.getStatusCode >= 200) || (restful.getStatusCode < 300)){
                                    var num = uriLink.indexOf("/species")
                                    var newLink = uriLink.substring(0, num)
                                    S.redirectTo(newLink)
                                } else {
                                    S.error("Invalid content")
                                }

                            }
                            spec_bool = false
                        } else if(comp_bool){
                            if (comp_id.length == 0 && comp_name.length == 0) {
                                S.error("Invalid Data")
                            } else {
                                val modelCompUpdate = {
                                    <compartment outside={comp_outside} spatialDimensions={comp_sd} size={comp_size}
                                        name={comp_name} id={comp_id} constant={comp_c} metaid={comp_metaid}>
                                        {if(comp_notes.length > 0){
                                                <notes>
                                                    <body>{XML.loadString(comp_notes.toString)}
                                                    </body>
                                                </notes>
                                            }
                                        }
                                    </compartment>
                                        
                                }

                                restful.putRequest(uriLink, modelCompUpdate)

                                if((restful.getStatusCode >= 200) || (restful.getStatusCode < 300)){
                                    var num = uriLink.indexOf("/compartment")
                                    var newLink = uriLink.substring(0, num)
                                    S.redirectTo(newLink)
                                } else {
                                    S.error("Invalid content")
                                }
                            }
                            comp_bool = false
                        } else if(const_bool){
                            
                            val modelConstUpdate = {
                                <constraint metaid={const_metaid}>{XML.loadString(const_math.toString)}
                                    <message>{const_message}</message>
                                    <notes>{XML.loadString(const_notes.toString)}</notes>
                                </constraint>
                            }

                            restful.putRequest(uriLink, modelConstUpdate)

                            if((restful.getStatusCode >= 200) || (restful.getStatusCode < 300)){
                                var num = uriLink.indexOf("/constraint")
                                var newLink = uriLink.substring(0, num)
                                S.redirectTo(newLink)
                            } else {
                                S.error("Invalid content")
                            }
                            
                            const_bool = false
                        } else if(funDef_bool){
                            if (funDef_id.length == 0 && funDef_name.length == 0) {
                                S.error("Invalid Data")
                            } else {
                                val modelFunDefUpdate = {
                                    <functionDefinition name={funDef_name} id={funDef_id} metaid={funDef_metaid}>
                                        {
                                            if((funDef_math.length > 0) && (funDef_math.contains("<math"))){
                                                XML.loadString(funDef_math.toString)
                                            }
                                        }
                                        <notes>{XML.loadString(funDef_notes.toString)}</notes>
                                    </functionDefinition>

                                }

                                restful.putRequest(uriLink, modelFunDefUpdate)

                                if((restful.getStatusCode >= 200) || (restful.getStatusCode < 300)){
                                    var num = uriLink.indexOf("/functiondefinition")
                                    var newLink = uriLink.substring(0, num)
                                    S.redirectTo(newLink)
                                } else {
                                    S.error("Invalid content")
                                }
                            }
                            funDef_bool = false
                        } else if(model_bool){
                            if (model_id.length == 0 && model_name.length == 0) {
                                S.error("Invalid Data")
                            } else {
                                val modelUpdate = {
                                    <model name={model_name} id={model_id} metaid={model_metaid}>
                                        <notes>{XML.loadString(model_notes.toString)}</notes>
                                    </model>

                                }

                                restful.putRequest(uriLink, modelUpdate)

                                if((restful.getStatusCode >= 200) || (restful.getStatusCode < 300)){
                                    var num = uriLink.indexOf("/edit")
                                    var newLink = uriLink.substring(0, num)
                                    S.redirectTo(newLink)
                                } else {
                                    S.error("Invalid content")
                                }
                            }
                            funDef_bool = false
                        }
                    }
                    // Attribution of the names in the text fields

                    var parameter_name = (modelAtribute \\ "@name").text
                    if(parameter_name.length == 0){
                        parameter_name = (modelAtribute \\ "@id").text
                    }
                    var specie_name = (modelAtribute \\ "@name").text
                    if(specie_name.length == 0){
                        specie_name = (modelAtribute \\ "@id").text
                    }
                    var compartment_name = (modelAtribute \\ "@name").text
                    if(compartment_name.length == 0){
                        compartment_name = (modelAtribute \\ "@id").text
                    }
                    var functionDef_name = (modelAtribute \\ "@name").text
                    if(functionDef_name.length == 0){
                        functionDef_name = (modelAtribute \\ "@id").text
                    }
                    var reaction_name = (modelAtribute \\ "@name").text
                    if(reaction_name.length == 0){
                        reaction_name = (modelAtribute \\ "@id").text
                    }

                    comp_notes = "<div>"
                    param_notes = "<div>"
                    const_notes = "<div>"
                    spec_notes = "<div>"
                    funDef_notes = "<div>"
                    model_notes = "<div>"
                        
                    def setParameterC(va :String){
                        param_constant = va
                    }
                    def setSpecieBC(va :String){
                        spec_bc = va
                    }
                    def setSpecieC(va :String){
                        spec_c = va
                    }
                    def setCompartmentSD(va :String){
                        comp_sd = va
                    }
                    def setCompartmentC(va :String){
                        comp_c = va
                    }
                    def setReactionR(va :String){
                        reac_r = va
                    }
                    def setReactionF(va :String){
                        reac_f = va
                    }


                    //Console.println("EDIT...----->>>> "+modelAtribute)
                    bind("editModel", xhtml,
                         "editAttribute" -> {
                            <ul style="list-style-type: none;">{
                                    if(uriLink.contains("parameter")){
                                        param_bool = true
                                        <li>Parameter ID <span id="required_field">*</span>: &nbsp;
                                            {
                                                SHtml.text((modelAtribute \\ "@id").text, v => {param_id = (v.replace(" ", "").toLowerCase)},("id", "parameterID"), ("size", "40"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Parameter Name <span id="required_field">*</span>: &nbsp;
                                            {
                                                SHtml.text(parameter_name, v => {param_name = v},("id", "parameterName"), ("size", "40"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Parameter Value : &nbsp;
                                            {
                                                SHtml.text((modelAtribute \\ "@value").text, v => param_value = v,("id", "parameterValue"), ("size", "10"), ("maxlength", "100"))
                                            }
                                        </li>
                                        <li>Parameter Constant: &nbsp;<i>(default="true")</i> &nbsp;
                                            {
                                                SHtml.select(listaNova.map(v => (v, v)), Full((modelAtribute \\ "@constant").text), setParameterC _)
                                            }
                                        </li>
                                        <li>Parameter Notes:<br />
                                            <ul style="list-style:none;">
                                                <li>
                                                    <span>{
                                                            SHtml.textarea((modelAtribute \\ "notes").toString, n => param_notes = param_notes + n + "</div>",("id", "editNotesArea"), ("maxlength", "20000"))
                                                        }
                                                    </span>
                                                </li>
                                            </ul>
                                        </li>
                                    } else if(uriLink.contains("species")){
                                        spec_bool = true
                                        <li>Species ID <span id="required_field">*</span>: &nbsp;
                                            {
                                                SHtml.text((modelAtribute \\ "@id").text, v => {spec_id = (v.replace(" ", "").toLowerCase)},("id", "speciesID"), ("size", "40"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Species Name <span id="required_field">*</span>: &nbsp;
                                            {
                                                SHtml.text(specie_name, v => {spec_name = v},("id", "speciesName"), ("size", "40"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Species Compartment <span id="required_field">*</span>: &nbsp;
                                            {
                                                SHtml.text((modelAtribute \\ "@compartment").text, v => spec_compartment = v,("id", "speciesCompartment"), ("size", "40"), ("maxlength", "1000"))
                                            }
                                        </li>
                                        <li>Species Initial Amount: &nbsp;
                                            {
                                                SHtml.text((modelAtribute \\ "@initialAmount").text, v => spec_init_amount = v,("id", "speciesInitAmount"), ("size", "10"), ("maxlength", "1000"))
                                            }
                                        </li>
                                        <li>Species Initial Concentration: &nbsp;
                                            {
                                                SHtml.text((modelAtribute \\ "@initialConcentration").text, v => spec_init_concent = v,("id", "speciesInitConc"), ("size", "10"), ("maxlength", "1000"))
                                            }
                                        </li>
                                        <li>Species Boundary Condition: &nbsp;<i>(default="false")</i> &nbsp;
                                            {
                                                SHtml.select(listaNova.map(v => (v, v)), Empty, setSpecieBC _)
                                            }
                                        </li>
                                        <li>Species Constant: &nbsp;<i>(default="false")</i> &nbsp;
                                            {
                                                SHtml.select(listaNova.reverse.map(v => (v, v)), Empty, setSpecieC _)
                                            }
                                        </li>
                                        <li>Species Notes:<br />
                                            <ul style="list-style:none;">
                                                <li>
                                                    <span>{
                                                            SHtml.textarea((modelAtribute \\ "notes").toString, n => spec_notes = spec_notes + n + "</div>",("id", "editNotesArea"), ("maxlength", "20000"))
                                                        }
                                                    </span>
                                                </li>
                                            </ul>
                                        </li>
                                    } else if(uriLink.contains("compartment")){
                                        comp_bool = true
                                        <li>Compartment ID <span id="required_field">*</span>: &nbsp;
                                            {
                                                SHtml.text((modelAtribute \\ "@id").text, v => {comp_id = (v.replace(" ", "").toLowerCase)},("id", "compartmentID"), ("size", "40"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Compartment Name: <span id="required_field">*</span>&nbsp;
                                            {
                                                SHtml.text(compartment_name, v => {comp_name = v},("id", "compartmentName"), ("size", "40"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Compartment Spatial Dimensions:&nbsp;
                                            {
                                                SHtml.select((0 to 3).toList.reverse.map(v => (v.toString, v.toString)), Full((modelAtribute \\ "@spatialDimensions").text), setCompartmentSD _)
                                            }
                                        </li>
                                        <li>Compartment Size: &nbsp;
                                            {
                                                SHtml.text((modelAtribute \\ "@size").text, v => comp_size = v,("id", "compartmentSize"), ("size", "10"), ("maxlength", "100"))
                                            }
                                        </li>
                                        <li>Compartment Outside: &nbsp;
                                            {
                                                SHtml.text((modelAtribute \\ "@outside").text, v => comp_outside = v,("id", "compartmentOutside"), ("size", "10"), ("maxlength", "100"))
                                            }
                                        </li>
                                        <li>Compartment Constant: &nbsp;<i>(default="true")</i> &nbsp;
                                            {
                                                SHtml.select(listaNova.map(v => (v, v)), Full((modelAtribute \\ "@constant").text), setCompartmentC _)
                                            }
                                        </li>
                                        <li>Compartment Notes:<br />
                                            <ul style="list-style:none;">
                                                <li>
                                                    <span>{
                                                            SHtml.textarea((modelAtribute \\ "notes").toString, n => comp_notes = comp_notes + n + "</div>",("id", "editNotesArea"), ("maxlength", "20000"))
                                                        }
                                                    </span>
                                                </li>
                                            </ul>
                                        </li>
                                    } else if(uriLink.contains("constraint")){
                                        const_bool = true
                                        <li>Constraint Math: <i><span id="required_field">(Format: Content MathML [Mathematical Markup Language])</span></i><img src="../classpath/images/question.png" width="20px" height="20px" /><br />
                                            <br /><a style="color:blue" name="Copy and paste the Content MathML code to the box down" href="" onclick="window.open('http://cnx.org/math-editor/popup','Help','width=900,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=200,top=200,screenX=200,screenY=300');"><b>MathML Editor</b> (only for Mozilla Firefox users)</a><br /><br />
                                            {
                                                SHtml.textarea((modelAtribute \\ "constraint").text, v => const_math = v,("id", "constraintMath"), ("rows","10"), ("cols", "120"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Constraint Message:<br />
                                            {
                                                SHtml.textarea((modelAtribute \\ "constraint" \\ "message").text, v => const_message = v,("id", "constraintMessage"), ("rows","3"), ("cols", "120"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Constraint Note:<br />
                                            <ul style="list-style:none;">
                                                <li>
                                                    <span>{
                                                            SHtml.textarea((modelAtribute \\ "notes").toString, n => const_notes = const_notes + n + "</div>",("id", "editNotesArea"), ("maxlength", "20000"))
                                                        }
                                                    </span>
                                                </li>
                                            </ul>
                                        </li>
                                    } else if(uriLink.contains("functiondefinition")){
                                        funDef_bool = true
                                        <li>Function Definition ID <span id="required_field">*</span>:
                                            {
                                                SHtml.text((modelAtribute \\ "@id").text, v => {funDef_id = v.replace(" ", "").toLowerCase},("id", "functionDefinitionID"), ("size", "40"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Function Definition Name <span id="required_field">*</span>:
                                            {
                                                SHtml.text(functionDef_name, v => {funDef_name = v},("id", "functionDefinitionName"), ("size", "40"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Function Definition Math: <i><span id="required_field">(Format: Content MathML [Mathematical Markup Language])</span></i><img src="../classpath/images/question.png" width="20px" height="20px" /><br />
                                            <br /><a style="color:blue" name="Copy and paste the Content MathML code to the box down" href="" onclick="window.open('http://cnx.org/math-editor/popup','Help','width=900,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=200,top=200,screenX=200,screenY=300');"><b>MathML Editor</b> (only for Mozilla Firefox users)</a><br /><br />
                                            {
                                                SHtml.textarea((modelAtribute \\ "functionDefinition").text, v => funDef_math = v,("id", "functionDefinitionMath"), ("rows","10"), ("cols", "120"), ("maxlength", "200000"))
                                            }
                                        </li>
                                        <li><span><h3>Function Definition Note:</h3></span><br />
                                            <ul style="list-style:none;">
                                                <li>
                                                    <span>
                                                        {
                                                            SHtml.textarea((modelAtribute \\ "notes").toString, v => funDef_notes = funDef_notes + v + "</div>",("id", "editNotesArea"), ("maxlength", "20000"))
                                                        }
                                                    </span>
                                                </li>
                                            </ul>
                                        </li>
                                    } else if(uriLink.contains("reaction")){
                                        /*reac_bool = true
                                         <li>Reaction ID <span id="required_field">*</span>:
                                         {
                                         SHtml.text((modelAtribute \\ "@id").text, v => {reac_id = v.replace(" ", "").toLowerCase},("id", "reactionID"), ("size", "40"), ("maxlength", "10000"))
                                         }
                                         </li>
                                         <li>Reaction Name <span id="required_field">*</span>:
                                         {
                                         SHtml.text(reaction_name, v => {reac_name = v},("id", "reactionName"), ("size", "40"), ("maxlength", "10000"))
                                         }
                                         </li>
                                         <li>Reaction Reversible:  <i>(default="false")</i>
                                         {
                                         SHtml.select(listaNova.map(v => (v, v)), Full((modelAtribute \\ "@reversible").text), setReactionR _)
                                         }
                                         </li>
                                         <li>Reaction Fast:  <i>(default="false")</i>
                                         {
                                         SHtml.select(listaNova.reverse.map(v => (v, v)), Full((modelAtribute \\ "@fast").text), setReactionF _)
                                         }
                                         </li>
                                         {var t = (modelAtribute \\ "listOfReactants" \\ "speciesReference").size
                                            
                                         for(k <- 1 to t) yield{
                                         <li>Reactant ID <span id="required_field">*</span>:
                                         {
                                         SHtml.text("", v => {reactant.set_reactant_id(v.replace(" ","").toLowerCase)},("id", "reactantID"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Reactant Name <span id="required_field">*</span>:
                                         {SHtml.text("", v => {reactant.set_reactant_name(v)},("id", "reactantName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Reactant Specie <span id="required_field">*</span>:
                                         {SHtml.text("", v => {reactant.set_reactant_specie(v)
                                         },("id", "reactantSpecie"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Reactant Stoichiometry:  <i>(default="1")</i>
                                         {SHtml.text("1", v => {reactant.set_reactant_stoic(v)
                                         },("id", "reactantStoic"), ("size", "10"), ("maxlength", "10000"))}</li>
                                         <li>Reactant Stoichiometry Math: <i><span id="required_field">(Format: Content MathML [Mathematical Markup Language])</span></i><img src="../classpath/images/question.png" width="20px" height="20px" /><br />
                                         <!--<a value="Help on Mathematical Formulas" href="" onclick="window.open('../help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">Help on Mathematical Formula for ASCIIMathML-->
                                         <!--</a>-->
                                         <br /><a style="color:blue" name="Copy and paste the Content MathML code to the box down" href="" onclick="window.open('http://cnx.org/math-editor/popup','Help','width=900,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=200,top=200,screenX=200,screenY=300');"><b>MathML Editor</b> (only for Mozilla Firefox users)</a><br /><br />
                                         {SHtml.textarea("", v => {reactant.set_reactant_stoic_math(v)
                                         },("id", "reactantStoicMath"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
                                         <li>Reactant Note:<br />
                                         {SHtml.textarea("", v => {reactant.set_reactant_note(v)
                                         },("id", "reactantNote"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))
                                         }</li>
                                         }
                                         }
                                         </li>
                                         <br />
                                         <ul id={product_ul} style="list-style:none;"></ul>
                                         <li>{SHtml.ajaxButton(Text("Add Product"), () => {
                                         product_count = product_count+1
                                         product_key = product_ul+product_count
                                         var product = new Product();
                                         JsCrVar("reactionNewProduct", Jx(<hr /><ul id="productStyle" style="list-style:none;">{
                                         Jx(<li><font style="font-weight:bold; font-size:110%;">New Product</font>
                                         </li><br />
                                         <li>Product ID <span id="required_field">*</span>:
                                         {SHtml.text("", v => {product.set_product_id(v.replace(" ","").toLowerCase)},("id", "productID"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Product Name <span id="required_field">*</span>:
                                         {SHtml.text("", v => {product.set_product_name(v)}
                                         ,("id", "productName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Product Specie <span id="required_field">*</span>:
                                         {SHtml.text("", v => {product.set_product_specie(v)
                                         },("id", "productSpecie"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Product Stoichiometry:  <i>(default="1")</i>
                                         {SHtml.text("1", v => {product.set_product_stoic(v)
                                         },("id", "productStoic"), ("size", "10"), ("maxlength", "10000"))}</li>
                                         <li>Product Stoichiometry Math: <i><span id="required_field">(Format: Content MathML [Mathematical Markup Language])</span></i><img src="../classpath/images/question.png" width="20px" height="20px" /><br />
                                         <!--<a value="Help on Mathematical Formulas" href="" onclick="window.open('../help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">Help on Mathematical Formula for ASCIIMathML-->
                                         <!--</a>-->
                                         <br /><a style="color:blue" name="Copy and paste the Content MathML code to the box down" href="" onclick="window.open('http://cnx.org/math-editor/popup','Help','width=900,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=200,top=200,screenX=200,screenY=300');"><b>MathML Editor</b> (only for Mozilla Firefox users)</a><br /><br />
                                         {SHtml.textarea("", v => {product.set_product_stoic_math(v)
                                         },("id", "productStoicMath"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
                                         <li>Product Note:<br />
                                         {SHtml.textarea("", v => {product.set_product_note(v)
                                         },("id", "productNote"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)}
                                         <br />{product_hash.put(product_key, product)}</ul>
                                         ).toJs) &
                                         (ElemById(product_ul) ~> (JsFunc("appendChild", Call("reactionNewProduct", ""))))})}
                                         </li>
                                         <br />
                                         <ul id={modifier_ul} style="list-style:none;"></ul>
                                         <li>{SHtml.ajaxButton(Text("Add Modifier"), () => {
                                         modifier_count = modifier_count+1
                                         modifier_key = modifier_ul+modifier_count
                                         var modifier = new Modifier();
                                         JsCrVar("reactionNewModifier", Jx(<hr /><ul id="modifierStyle" style="list-style:none;">{
                                         Jx(<li><font style="font-weight:bold; font-size:110%;">New Modifier</font>
                                         </li><br />
                                         <li>Modifier ID <span id="required_field">*</span>:
                                         {SHtml.text("", v => {modifier.set_modifier_id(v.replace(" ","").toLowerCase)},("id", "modifierID"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Modifier Name <span id="required_field">*</span>:
                                         {SHtml.text("", v => {modifier.set_modifier_name(v)
                                         },("id", "modifierName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Modifier Specie <span id="required_field">*</span>:
                                         {SHtml.text("", v => {modifier.set_modifier_specie(v)
                                         },("id", "modifierSpecie"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Modifier Stoichiometry:  <i>(default="1")</i>
                                         {SHtml.text("1", v => {modifier.set_modifier_stoic(v)
                                         },("id", "modifierStoic"), ("size", "10"), ("maxlength", "10000"))}</li>
                                         <li>Modifier Stoichiometry Math: <i><span id="required_field">(Format: Content MathML [Mathematical Markup Language])</span></i><img src="../classpath/images/question.png" width="20px" height="20px" /><br />
                                         <!--<a value="Help on Mathematical Formulas" href="" onclick="window.open('../help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">Help on Mathematical Formula for ASCIIMathML-->
                                         <!--</a>-->
                                         <br /><a style="color:blue" name="Copy and paste the Content MathML code to the box down" href="" onclick="window.open('http://cnx.org/math-editor/popup','Help','width=900,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=200,top=200,screenX=200,screenY=300');"><b>MathML Editor</b> (only for Mozilla Firefox users)</a><br /><br />
                                         {SHtml.textarea("", v => {modifier.set_modifier_stoic_math(v)
                                         },("id", "modifierStoicMath"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
                                         <li>Modifier Note:<br />
                                         {SHtml.textarea("", v => {modifier.set_modifier_note(v)
                                         },("id", "productNote"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)}
                                         <br />{modifier_hash.put(modifier_key, modifier)}</ul>
                                         ).toJs) &
                                         (ElemById(modifier_ul) ~> (JsFunc("appendChild", Call("reactionNewModifier", ""))))})}
                                         </li>
                                         <br />
                                         <li>Kinetic Law: <i><span id="required_field">(Format: Content MathML [Mathematical Markup Language])</span></i><img src="../classpath/images/question.png" width="20px" height="20px" /><br />
                                         <!--<a value="Help on Mathematical Formulas" href="" onclick="window.open('../help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">Help on Mathematical Formula for ASCIIMathML-->
                                         <!--</a>-->
                                         <br /><a style="color:blue" name="Copy and paste the Content MathML code to the box down" href="" onclick="window.open('http://cnx.org/math-editor/popup','Help','width=900,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=200,top=200,screenX=200,screenY=300');"><b>MathML Editor</b> (only for Mozilla Firefox users)</a><br /><br />
                                         {SHtml.textarea("", v => {reaction_kinetic.add(v)
                                         },("id", "reactionKineticLaw"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
                                         <br />
                                         <ul id={parameter_ul} style="list-style:none;"></ul>
                                         <li>{SHtml.ajaxButton(Text("Add Kinetic Law Parameter"), () => {
                                         parameter_count = parameter_count+1
                                         parameter_key = parameter_ul+parameter_count

                                         JsCrVar("reactionNewKineticParameter", Jx(<hr /><ul id="productStyle" style="list-style:none;">{
                                         Jx(<li><font style="font-weight:bold; font-size:110%;">New Parameter</font>
                                         </li><br /><li>Parameter Name or ID<span id="required_field">*</span>: &nbsp;
                                         {SHtml.text("", v => {kineticParameter.set_parameter_name(v)
                                         kineticParameter.set_parameter_id(v.replace(" ", "").toLowerCase)},("id", "parameterName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Parameter Value : &nbsp;
                                         {SHtml.text("", v => kineticParameter.set_parameter_value(v),("id", "parameterValue"), ("size", "10"), ("maxlength", "100"))}</li>
                                         <li>Parameter Constant: &nbsp;<i>(default="true")</i> &nbsp;
                                         {SHtml.select(listaNova.map(v => (v, v)), Empty, setKineticParameterC _)}</li>
                                         <li>Parameter Note:<br />
                                         {SHtml.textarea("", n => kineticParameter.set_parameter_note(n),("id", "parameterNoteArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)}
                                         <br />{parameter_hash.put(parameter_key, kineticParameter)}</ul>
                                         ).toJs) &
                                         (ElemById(parameter_ul) ~> (JsFunc("appendChild", Call("reactionNewKineticParameter", ""))))})}
                                         </li>
                                         <br />
                                         <li>Reaction Note:<br />
                                         {SHtml.textarea("", n => reaction_note.add(n),("id", "reactionArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
                                    */} else {
                                        model_bool = true
                                        <li>Model ID: <span id="required_field">*</span>
                                            {
                                                SHtml.text((modelAtribute \\ "@id").text, refer2 => {model_id = refer2}, ("id", "model_id"), ("name", "model_id"), ("size", "40"))
                                            }
                                        </li>
                                        <li>Name of the model:
                                            {
                                                SHtml.text((modelAtribute \\ "@name").text, ref => {model_name = ref}, ("id", "name_model"), ("size", "40"), ("maxlength", "1000"))
                                            }
                                        </li>
                                        <li><span><h3>Description of the model:</h3></span><br />
                                            <ul style="list-style:none;">
                                                <li>
                                                    <span>
                                                        {
                                                            SHtml.textarea((modelAtribute \ "notes").toString, vv => {model_notes = model_notes + vv + "</div>"}, ("id", "editNotesArea"), ("maxlength", "20000"))
                                                        }
                                                    </span>
                                                </li>
                                            </ul>
                                        </li>
                                    }
                                }
                            </ul>
                        },
                         "cancel" -> SHtml.submit("Cancel Changes", cancelSection, ("id","buttonSaveChange")),
                         "save" -> SHtml.submit("Save Changes", updateSection, ("id","buttonSaveChange"))
                    )
                    
                } else {
                    S.error("SBML model does not exist")
                    S.redirectTo(uriLink)
                    bind("editModel", xhtml)
                }

                
            }
        case _ => Text("")
    }

    def add (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
                var model_bool:Boolean = false
                var model_id:String = null
                var model_name:String = null
                var model_notes:String = null
                var model_metaid:String = null

                //parameter variables
                var param_bool:Boolean = false
                var param_id:String = null
                var param_name:String = null
                var param_value:String = null
                var param_constant:String = null
                var param_notes:String = null
                var param_metaid:String = null

                var spec_bool:Boolean = false
                var spec_id:String = null
                var spec_name:String = null
                var spec_compartment:String = null
                var spec_init_amount:String = null
                var spec_init_concent:String = null
                var spec_bc:String = null
                var spec_c:String = null
                var spec_notes:String = null
                var spec_metaid:String = null

                var comp_bool:Boolean = false
                var comp_id:String = null
                var comp_name:String = null
                var comp_sd:String = null
                var comp_size:String = null
                var comp_outside:String = null
                var comp_c:String = null
                var comp_notes:String = null
                var comp_metaid:String = null

                var const_bool:Boolean = false
                var const_id:String = null
                var const_math:String = null
                var const_message:String = null
                var const_notes:String = null
                var const_metaid:String = null

                var funDef_bool:Boolean = false
                var funDef_id:String = null
                var funDef_name:String = null
                var funDef_math:String = null
                var funDef_notes:String = null
                var funDef_metaid:String = null

                var reac_bool:Boolean = false
                var reac_id:String = null
                var reac_name:String = null
                var reac_r:String = null
                var reac_f:String = null
                var reac_constant:String = null
                var reac_notes:String = null
                var reac_metaid:String = null



                val msgName: String = S.attr("id_msgs") openOr "messages"

                val uriLink = S.uri
                var restful:RestfulAccess = User.getRestful
                
                if(restful.getStatusCode == 200){
                    val listaNova = List("true","false")

                    

                    def cancelSection() ={
                        var linkIndex = 0
                        var newLink = ""
                        if(uriLink.contains("parameter")){
                            linkIndex = uriLink.indexOf("/add/parameter")
                            newLink = uriLink.substring(0, linkIndex)
                        } else if(uriLink.contains("species")){
                            linkIndex = uriLink.indexOf("/add/species")
                            newLink = uriLink.substring(0, linkIndex)
                        } else if(uriLink.contains("compartment")){
                            linkIndex = uriLink.indexOf("/add/compartment")
                            newLink = uriLink.substring(0, linkIndex)
                        } else if(uriLink.contains("constraint")){
                            linkIndex = uriLink.indexOf("/add/constraint")
                            newLink = uriLink.substring(0, linkIndex)
                        } else if(uriLink.contains("functiondefinition")){
                            linkIndex = uriLink.indexOf("/add/functiondefinition")
                            newLink = uriLink.substring(0, linkIndex)
                        } else if(uriLink.contains("reaction")){
                            linkIndex = uriLink.indexOf("/add/reaction")
                            newLink = uriLink.substring(0, linkIndex)
                        } else {
                            linkIndex = uriLink.indexOf("/add")
                            newLink = uriLink.substring(0, linkIndex)
                        }
                        S.redirectTo(newLink)
                    }
                    //if(uriLink.contains("parameter")){
                    def updateSection() ={
                        var linkIndex = 0
                        var newLink = ""
                        if(uriLink.contains("parameter")){
                            linkIndex = uriLink.indexOf("/add/parameter")
                            newLink = uriLink.substring(0, linkIndex) + "/parameter"
                        } else if(uriLink.contains("species")){
                            linkIndex = uriLink.indexOf("/add/species")
                            newLink = uriLink.substring(0, linkIndex) + "/species"
                        } else if(uriLink.contains("compartment")){
                            linkIndex = uriLink.indexOf("/add/compartment")
                            newLink = uriLink.substring(0, linkIndex) + "/compartment"
                        } else if(uriLink.contains("constraint")){
                            linkIndex = uriLink.indexOf("/add/constraint")
                            newLink = uriLink.substring(0, linkIndex) + "/constraint"
                        } else if(uriLink.contains("functiondefinition")){
                            linkIndex = uriLink.indexOf("/add/functiondefinition")
                            newLink = uriLink.substring(0, linkIndex) + "/functiondefinition"
                        } else if(uriLink.contains("reaction")){
                            linkIndex = uriLink.indexOf("/add/reaction")
                            newLink = uriLink.substring(0, linkIndex) + "/reaction"
                        } else {
                        }
                        if(param_bool){
                            if (param_id.length == 0 && param_name.length == 0) {
                                S.error("Invalid Data")
                            } else {
                                val modelParameterUpdate = {
                                    <parameter id={param_id} name={param_name} value={param_value} constant={param_constant} metaid="metaid_0000001">
                                        {if(param_notes.length > 0){
                                                <notes>
                                                    <body>{XML.loadString(param_notes.toString)}
                                                    </body>
                                                </notes>
                                            }}
                                    </parameter>
                                }
                                restful.postRequest(newLink, modelParameterUpdate)

                                if((restful.getStatusCode >= 200) || (restful.getStatusCode < 300)){
                                    var num = uriLink.indexOf("/add/parameter")
                                    var newLink = uriLink.substring(0, num)
                                    S.redirectTo(newLink)
                                } else {
                                    S.error("Invalid content")
                                }
                            }
                            param_bool = false
                        } else if(spec_bool){
                            if (spec_id.length == 0 && spec_name.length == 0) {
                                S.error("Invalid Data")
                            } else {
                                val modelSpecUpdate = {
                                    <species id={spec_id} name={spec_name} compartment={spec_compartment} initialAmount={spec_init_amount}
                                        initialConcentration={spec_init_concent} boundaryCondition={spec_bc} constant={spec_c} metaid="metaid_0000001">
                                        {if(spec_notes.length > 0){
                                                <notes>
                                                    <body>{XML.loadString(spec_notes.toString)}
                                                    </body>
                                                </notes>
                                            }}
                                    </species>
                                }

                                restful.postRequest(newLink, modelSpecUpdate)

                                if((restful.getStatusCode >= 200) || (restful.getStatusCode < 300)){
                                    var num = uriLink.indexOf("/add/species")
                                    var newLink = uriLink.substring(0, num)
                                    S.redirectTo(newLink)
                                } else {
                                    S.error("Invalid content")
                                }

                            }
                            spec_bool = false
                        } else if(comp_bool){
                            if (comp_id.length == 0 && comp_name.length == 0) {
                                S.error("Invalid Data")
                            } else {
                                val modelCompUpdate = {
                                    <compartment outside={comp_outside} spatialDimensions={comp_sd} size={comp_size}
                                        name={comp_name} id={comp_id} constant={comp_c} metaid="metaid_0000001">
                                        {if(comp_notes.length > 0){
                                                <notes>
                                                    <body>{XML.loadString(comp_notes.toString)}
                                                    </body>
                                                </notes>
                                            }
                                        }
                                    </compartment>

                                }

                                restful.postRequest(newLink, modelCompUpdate)

                                if((restful.getStatusCode >= 200) || (restful.getStatusCode < 300)){
                                    var num = uriLink.indexOf("/add/compartment")
                                    var newLink = uriLink.substring(0, num)
                                    S.redirectTo(newLink)
                                } else {
                                    S.error("Invalid content")
                                }
                            }
                            comp_bool = false
                        } else if(const_bool){

                            val modelConstUpdate = {
                                <constraint metaid="metaid_0000001">{XML.loadString(const_math.toString)}
                                    <message>{const_message}</message>
                                    <notes>{XML.loadString(const_notes.toString)}</notes>
                                </constraint>
                            }

                            restful.postRequest(newLink, modelConstUpdate)

                            if((restful.getStatusCode >= 200) || (restful.getStatusCode < 300)){
                                var num = uriLink.indexOf("/add/constraint")
                                var newLink = uriLink.substring(0, num)
                                S.redirectTo(newLink)
                            } else {
                                S.error("Invalid content")
                            }

                            const_bool = false
                        } else if(funDef_bool){
                            if (funDef_id.length == 0 && funDef_name.length == 0) {
                                S.error("Invalid Data")
                            } else {
                                val modelFunDefUpdate = {
                                    <functionDefinition name={funDef_name} id={funDef_id} metaid="metaid_0000001">
                                        {
                                            if((funDef_math.length > 0) && (funDef_math.contains("<math"))){
                                                XML.loadString(funDef_math.toString)
                                            }
                                        }
                                        <notes>{XML.loadString(funDef_notes.toString)}</notes>
                                    </functionDefinition>

                                }

                                restful.postRequest(newLink, modelFunDefUpdate)

                                if((restful.getStatusCode >= 200) || (restful.getStatusCode < 300)){
                                    var num = uriLink.indexOf("/add/functiondefinition")
                                    var newLink = uriLink.substring(0, num)
                                    S.redirectTo(newLink)
                                } else {
                                    S.error("Invalid content")
                                }
                            }
                            funDef_bool = false
                        } else {
                            var num = uriLink.indexOf("/add")
                            var newLink = uriLink.substring(0, num)
                            S.redirectTo(newLink)
                                
                        }
                    }
                    

                    comp_notes = "<div>"
                    param_notes = "<div>"
                    const_notes = "<div>"
                    spec_notes = "<div>"
                    funDef_notes = "<div>"
                    model_notes = "<div>"

                    def setParameterC(va :String){
                        param_constant = va
                    }
                    def setSpecieBC(va :String){
                        spec_bc = va
                    }
                    def setSpecieC(va :String){
                        spec_c = va
                    }
                    def setCompartmentSD(va :String){
                        comp_sd = va
                    }
                    def setCompartmentC(va :String){
                        comp_c = va
                    }
                    def setReactionR(va :String){
                        reac_r = va
                    }
                    def setReactionF(va :String){
                        reac_f = va
                    }
                    //Console.println("EDIT...----->>>> "+modelAtribute)
                    bind("addModel", xhtml,
                         "addAttribute" -> {
                            <ul style="list-style-type: none;">{
                                    if(uriLink.contains("parameter")){
                                        param_bool = true
                                        <li>Parameter ID <span id="required_field">*</span>: &nbsp;
                                            {
                                                SHtml.text("", v => {param_id = (v.replace(" ", "").toLowerCase)},("id", "parameterID"), ("size", "40"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Parameter Name <span id="required_field">*</span>: &nbsp;
                                            {
                                                SHtml.text("", v => {param_name = v},("id", "parameterName"), ("size", "40"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Parameter Value : &nbsp;
                                            {
                                                SHtml.text("", v => param_value = v,("id", "parameterValue"), ("size", "10"), ("maxlength", "100"))
                                            }
                                        </li>
                                        <li>Parameter Constant: &nbsp;<i>(default="true")</i> &nbsp;
                                            {
                                                SHtml.select(listaNova.map(v => (v, v)), Empty, setParameterC _)
                                            }
                                        </li>
                                        <li>Parameter Notes:<br />
                                            <ul style="list-style:none;">
                                                <li>
                                                    <span>{
                                                            SHtml.textarea("", n => param_notes = param_notes + n + "</div>",("id", "editNotesArea"), ("maxlength", "20000"))
                                                        }
                                                    </span>
                                                </li>
                                            </ul>
                                        </li>
                                    } else if(uriLink.contains("species")){
                                        spec_bool = true
                                        <li>Species ID <span id="required_field">*</span>: &nbsp;
                                            {
                                                SHtml.text("", v => {spec_id = (v.replace(" ", "").toLowerCase)},("id", "speciesID"), ("size", "40"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Species Name <span id="required_field">*</span>: &nbsp;
                                            {
                                                SHtml.text("", v => {spec_name = v},("id", "speciesName"), ("size", "40"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Species Compartment <span id="required_field">*</span>: &nbsp;
                                            {
                                                SHtml.text("", v => spec_compartment = v,("id", "speciesCompartment"), ("size", "40"), ("maxlength", "1000"))
                                            }
                                        </li>
                                        <li>Species Initial Amount: &nbsp;
                                            {
                                                SHtml.text("", v => spec_init_amount = v,("id", "speciesInitAmount"), ("size", "10"), ("maxlength", "1000"))
                                            }
                                        </li>
                                        <li>Species Initial Concentration: &nbsp;
                                            {
                                                SHtml.text("", v => spec_init_concent = v,("id", "speciesInitConc"), ("size", "10"), ("maxlength", "1000"))
                                            }
                                        </li>
                                        <li>Species Boundary Condition: &nbsp;<i>(default="false")</i> &nbsp;
                                            {
                                                SHtml.select(listaNova.map(v => (v, v)), Empty, setSpecieBC _)
                                            }
                                        </li>
                                        <li>Species Constant: &nbsp;<i>(default="false")</i> &nbsp;
                                            {
                                                SHtml.select(listaNova.reverse.map(v => (v, v)), Empty, setSpecieC _)
                                            }
                                        </li>
                                        <li>Species Notes:<br />
                                            <ul style="list-style:none;">
                                                <li>
                                                    <span>{
                                                            SHtml.textarea("", n => spec_notes = spec_notes + n + "</div>",("id", "editNotesArea"), ("maxlength", "20000"))
                                                        }
                                                    </span>
                                                </li>
                                            </ul>
                                        </li>
                                    } else if(uriLink.contains("compartment")){
                                        comp_bool = true
                                        <li>Compartment ID <span id="required_field">*</span>: &nbsp;
                                            {
                                                SHtml.text("", v => {comp_id = (v.replace(" ", "").toLowerCase)},("id", "compartmentID"), ("size", "40"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Compartment Name: <span id="required_field">*</span>&nbsp;
                                            {
                                                SHtml.text("", v => {comp_name = v},("id", "compartmentName"), ("size", "40"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Compartment Spatial Dimensions:&nbsp;
                                            {
                                                SHtml.select((0 to 3).toList.reverse.map(v => (v.toString, v.toString)), Empty, setCompartmentSD _)
                                            }
                                        </li>
                                        <li>Compartment Size: &nbsp;
                                            {
                                                SHtml.text("", v => comp_size = v,("id", "compartmentSize"), ("size", "10"), ("maxlength", "100"))
                                            }
                                        </li>
                                        <li>Compartment Outside: &nbsp;
                                            {
                                                SHtml.text("", v => comp_outside = v,("id", "compartmentOutside"), ("size", "10"), ("maxlength", "100"))
                                            }
                                        </li>
                                        <li>Compartment Constant: &nbsp;<i>(default="true")</i> &nbsp;
                                            {
                                                SHtml.select(listaNova.map(v => (v, v)), Empty, setCompartmentC _)
                                            }
                                        </li>
                                        <li>Compartment Notes:<br />
                                            <ul style="list-style:none;">
                                                <li>
                                                    <span>{
                                                            SHtml.textarea("", n => comp_notes = comp_notes + n + "</div>",("id", "editNotesArea"), ("maxlength", "20000"))
                                                        }
                                                    </span>
                                                </li>
                                            </ul>
                                        </li>
                                    } else if(uriLink.contains("constraint")){
                                        const_bool = true
                                        <li>Constraint ID <span id="required_field">*</span>:
                                           {
                                               SHtml.text("", v => {const_id = (v.replace(" ", "").toLowerCase)},("id", "constraintID"), ("size", "40"), ("maxlength", "10000"))
                                           }
                                       </li>
                                        <li>Constraint Math: <i><span id="required_field">(Format: Content MathML [Mathematical Markup Language])</span></i><img src="../classpath/images/question.png" width="20px" height="20px" /><br />
                                            <br /><a style="color:blue" name="Copy and paste the Content MathML code to the box down" href="" onclick="window.open('http://cnx.org/math-editor/popup','Help','width=900,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=200,top=200,screenX=200,screenY=300');"><b>MathML Editor</b> (only for Mozilla Firefox users)</a><br /><br />
                                            {
                                                SHtml.textarea("", v => const_math = v,("id", "constraintMath"), ("rows","10"), ("cols", "120"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Constraint Message:<br />
                                            {
                                                SHtml.textarea("", v => const_message = v,("id", "constraintMessage"), ("rows","3"), ("cols", "120"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Constraint Note:<br />
                                            <ul style="list-style:none;">
                                                <li>
                                                    <span>{
                                                            SHtml.textarea("", n => const_notes = const_notes + n + "</div>",("id", "editNotesArea"), ("maxlength", "20000"))
                                                        }
                                                    </span>
                                                </li>
                                            </ul>
                                        </li>
                                    } else if(uriLink.contains("functiondefinition")){
                                        funDef_bool = true
                                        <li>Function Definition ID <span id="required_field">*</span>:
                                            {
                                                SHtml.text("", v => {funDef_id = v.replace(" ", "").toLowerCase},("id", "functionDefinitionID"), ("size", "40"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Function Definition Name <span id="required_field">*</span>:
                                            {
                                                SHtml.text("", v => {funDef_name = v},("id", "functionDefinitionName"), ("size", "40"), ("maxlength", "10000"))
                                            }
                                        </li>
                                        <li>Function Definition Math: <i><span id="required_field">(Format: Content MathML [Mathematical Markup Language])</span></i><img src="../classpath/images/question.png" width="20px" height="20px" /><br />
                                            <br /><a style="color:blue" name="Copy and paste the Content MathML code to the box down" href="" onclick="window.open('http://cnx.org/math-editor/popup','Help','width=900,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=200,top=200,screenX=200,screenY=300');"><b>MathML Editor</b> (only for Mozilla Firefox users)</a><br /><br />
                                            {
                                                SHtml.textarea("", v => funDef_math = v,("id", "functionDefinitionMath"), ("rows","10"), ("cols", "120"), ("maxlength", "200000"))
                                            }
                                        </li>
                                        <li><span><h3>Function Definition Note:</h3></span><br />
                                            <ul style="list-style:none;">
                                                <li>
                                                    <span>
                                                        {
                                                            SHtml.textarea("", v => funDef_notes = funDef_notes + v + "</div>",("id", "editNotesArea"), ("maxlength", "20000"))
                                                        }
                                                    </span>
                                                </li>
                                            </ul>
                                        </li>
                                    } else if(uriLink.contains("reaction")){
                                        /*reac_bool = true
                                         <li>Reaction ID <span id="required_field">*</span>:
                                         {
                                         SHtml.text((modelAtribute \\ "@id").text, v => {reac_id = v.replace(" ", "").toLowerCase},("id", "reactionID"), ("size", "40"), ("maxlength", "10000"))
                                         }
                                         </li>
                                         <li>Reaction Name <span id="required_field">*</span>:
                                         {
                                         SHtml.text(reaction_name, v => {reac_name = v},("id", "reactionName"), ("size", "40"), ("maxlength", "10000"))
                                         }
                                         </li>
                                         <li>Reaction Reversible:  <i>(default="false")</i>
                                         {
                                         SHtml.select(listaNova.map(v => (v, v)), Full((modelAtribute \\ "@reversible").text), setReactionR _)
                                         }
                                         </li>
                                         <li>Reaction Fast:  <i>(default="false")</i>
                                         {
                                         SHtml.select(listaNova.reverse.map(v => (v, v)), Full((modelAtribute \\ "@fast").text), setReactionF _)
                                         }
                                         </li>
                                         {var t = (modelAtribute \\ "listOfReactants" \\ "speciesReference").size

                                         for(k <- 1 to t) yield{
                                         <li>Reactant ID <span id="required_field">*</span>:
                                         {
                                         SHtml.text("", v => {reactant.set_reactant_id(v.replace(" ","").toLowerCase)},("id", "reactantID"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Reactant Name <span id="required_field">*</span>:
                                         {SHtml.text("", v => {reactant.set_reactant_name(v)},("id", "reactantName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Reactant Specie <span id="required_field">*</span>:
                                         {SHtml.text("", v => {reactant.set_reactant_specie(v)
                                         },("id", "reactantSpecie"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Reactant Stoichiometry:  <i>(default="1")</i>
                                         {SHtml.text("1", v => {reactant.set_reactant_stoic(v)
                                         },("id", "reactantStoic"), ("size", "10"), ("maxlength", "10000"))}</li>
                                         <li>Reactant Stoichiometry Math: <i><span id="required_field">(Format: Content MathML [Mathematical Markup Language])</span></i><img src="../classpath/images/question.png" width="20px" height="20px" /><br />
                                         <!--<a value="Help on Mathematical Formulas" href="" onclick="window.open('../help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">Help on Mathematical Formula for ASCIIMathML-->
                                         <!--</a>-->
                                         <br /><a style="color:blue" name="Copy and paste the Content MathML code to the box down" href="" onclick="window.open('http://cnx.org/math-editor/popup','Help','width=900,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=200,top=200,screenX=200,screenY=300');"><b>MathML Editor</b> (only for Mozilla Firefox users)</a><br /><br />
                                         {SHtml.textarea("", v => {reactant.set_reactant_stoic_math(v)
                                         },("id", "reactantStoicMath"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
                                         <li>Reactant Note:<br />
                                         {SHtml.textarea("", v => {reactant.set_reactant_note(v)
                                         },("id", "reactantNote"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))
                                         }</li>
                                         }
                                         }
                                         </li>
                                         <br />
                                         <ul id={product_ul} style="list-style:none;"></ul>
                                         <li>{SHtml.ajaxButton(Text("Add Product"), () => {
                                         product_count = product_count+1
                                         product_key = product_ul+product_count
                                         var product = new Product();
                                         JsCrVar("reactionNewProduct", Jx(<hr /><ul id="productStyle" style="list-style:none;">{
                                         Jx(<li><font style="font-weight:bold; font-size:110%;">New Product</font>
                                         </li><br />
                                         <li>Product ID <span id="required_field">*</span>:
                                         {SHtml.text("", v => {product.set_product_id(v.replace(" ","").toLowerCase)},("id", "productID"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Product Name <span id="required_field">*</span>:
                                         {SHtml.text("", v => {product.set_product_name(v)}
                                         ,("id", "productName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Product Specie <span id="required_field">*</span>:
                                         {SHtml.text("", v => {product.set_product_specie(v)
                                         },("id", "productSpecie"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Product Stoichiometry:  <i>(default="1")</i>
                                         {SHtml.text("1", v => {product.set_product_stoic(v)
                                         },("id", "productStoic"), ("size", "10"), ("maxlength", "10000"))}</li>
                                         <li>Product Stoichiometry Math: <i><span id="required_field">(Format: Content MathML [Mathematical Markup Language])</span></i><img src="../classpath/images/question.png" width="20px" height="20px" /><br />
                                         <!--<a value="Help on Mathematical Formulas" href="" onclick="window.open('../help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">Help on Mathematical Formula for ASCIIMathML-->
                                         <!--</a>-->
                                         <br /><a style="color:blue" name="Copy and paste the Content MathML code to the box down" href="" onclick="window.open('http://cnx.org/math-editor/popup','Help','width=900,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=200,top=200,screenX=200,screenY=300');"><b>MathML Editor</b> (only for Mozilla Firefox users)</a><br /><br />
                                         {SHtml.textarea("", v => {product.set_product_stoic_math(v)
                                         },("id", "productStoicMath"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
                                         <li>Product Note:<br />
                                         {SHtml.textarea("", v => {product.set_product_note(v)
                                         },("id", "productNote"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)}
                                         <br />{product_hash.put(product_key, product)}</ul>
                                         ).toJs) &
                                         (ElemById(product_ul) ~> (JsFunc("appendChild", Call("reactionNewProduct", ""))))})}
                                         </li>
                                         <br />
                                         <ul id={modifier_ul} style="list-style:none;"></ul>
                                         <li>{SHtml.ajaxButton(Text("Add Modifier"), () => {
                                         modifier_count = modifier_count+1
                                         modifier_key = modifier_ul+modifier_count
                                         var modifier = new Modifier();
                                         JsCrVar("reactionNewModifier", Jx(<hr /><ul id="modifierStyle" style="list-style:none;">{
                                         Jx(<li><font style="font-weight:bold; font-size:110%;">New Modifier</font>
                                         </li><br />
                                         <li>Modifier ID <span id="required_field">*</span>:
                                         {SHtml.text("", v => {modifier.set_modifier_id(v.replace(" ","").toLowerCase)},("id", "modifierID"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Modifier Name <span id="required_field">*</span>:
                                         {SHtml.text("", v => {modifier.set_modifier_name(v)
                                         },("id", "modifierName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Modifier Specie <span id="required_field">*</span>:
                                         {SHtml.text("", v => {modifier.set_modifier_specie(v)
                                         },("id", "modifierSpecie"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Modifier Stoichiometry:  <i>(default="1")</i>
                                         {SHtml.text("1", v => {modifier.set_modifier_stoic(v)
                                         },("id", "modifierStoic"), ("size", "10"), ("maxlength", "10000"))}</li>
                                         <li>Modifier Stoichiometry Math: <i><span id="required_field">(Format: Content MathML [Mathematical Markup Language])</span></i><img src="../classpath/images/question.png" width="20px" height="20px" /><br />
                                         <!--<a value="Help on Mathematical Formulas" href="" onclick="window.open('../help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">Help on Mathematical Formula for ASCIIMathML-->
                                         <!--</a>-->
                                         <br /><a style="color:blue" name="Copy and paste the Content MathML code to the box down" href="" onclick="window.open('http://cnx.org/math-editor/popup','Help','width=900,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=200,top=200,screenX=200,screenY=300');"><b>MathML Editor</b> (only for Mozilla Firefox users)</a><br /><br />
                                         {SHtml.textarea("", v => {modifier.set_modifier_stoic_math(v)
                                         },("id", "modifierStoicMath"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
                                         <li>Modifier Note:<br />
                                         {SHtml.textarea("", v => {modifier.set_modifier_note(v)
                                         },("id", "productNote"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)}
                                         <br />{modifier_hash.put(modifier_key, modifier)}</ul>
                                         ).toJs) &
                                         (ElemById(modifier_ul) ~> (JsFunc("appendChild", Call("reactionNewModifier", ""))))})}
                                         </li>
                                         <br />
                                         <li>Kinetic Law: <i><span id="required_field">(Format: Content MathML [Mathematical Markup Language])</span></i><img src="../classpath/images/question.png" width="20px" height="20px" /><br />
                                         <!--<a value="Help on Mathematical Formulas" href="" onclick="window.open('../help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">Help on Mathematical Formula for ASCIIMathML-->
                                         <!--</a>-->
                                         <br /><a style="color:blue" name="Copy and paste the Content MathML code to the box down" href="" onclick="window.open('http://cnx.org/math-editor/popup','Help','width=900,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=200,top=200,screenX=200,screenY=300');"><b>MathML Editor</b> (only for Mozilla Firefox users)</a><br /><br />
                                         {SHtml.textarea("", v => {reaction_kinetic.add(v)
                                         },("id", "reactionKineticLaw"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
                                         <br />
                                         <ul id={parameter_ul} style="list-style:none;"></ul>
                                         <li>{SHtml.ajaxButton(Text("Add Kinetic Law Parameter"), () => {
                                         parameter_count = parameter_count+1
                                         parameter_key = parameter_ul+parameter_count

                                         JsCrVar("reactionNewKineticParameter", Jx(<hr /><ul id="productStyle" style="list-style:none;">{
                                         Jx(<li><font style="font-weight:bold; font-size:110%;">New Parameter</font>
                                         </li><br /><li>Parameter Name or ID<span id="required_field">*</span>: &nbsp;
                                         {SHtml.text("", v => {kineticParameter.set_parameter_name(v)
                                         kineticParameter.set_parameter_id(v.replace(" ", "").toLowerCase)},("id", "parameterName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                         <li>Parameter Value : &nbsp;
                                         {SHtml.text("", v => kineticParameter.set_parameter_value(v),("id", "parameterValue"), ("size", "10"), ("maxlength", "100"))}</li>
                                         <li>Parameter Constant: &nbsp;<i>(default="true")</i> &nbsp;
                                         {SHtml.select(listaNova.map(v => (v, v)), Empty, setKineticParameterC _)}</li>
                                         <li>Parameter Note:<br />
                                         {SHtml.textarea("", n => kineticParameter.set_parameter_note(n),("id", "parameterNoteArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)}
                                         <br />{parameter_hash.put(parameter_key, kineticParameter)}</ul>
                                         ).toJs) &
                                         (ElemById(parameter_ul) ~> (JsFunc("appendChild", Call("reactionNewKineticParameter", ""))))})}
                                         </li>
                                         <br />
                                         <li>Reaction Note:<br />
                                         {SHtml.textarea("", n => reaction_note.add(n),("id", "reactionArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
                                    */} else {
                                        
                                    }
                                }
                            </ul>
                        },
                         "cancel" -> SHtml.submit("Cancel Changes", cancelSection, ("id","buttonSaveChange")),
                         "save" -> SHtml.submit("Save Changes", updateSection, ("id","buttonSaveChange"))
                    )

                } else {
                    S.error("SBML model does not exist")
                    S.redirectTo(uriLink)
                    bind("addModel", xhtml)
                }


            }
        case _ => Text("")
    }
}
