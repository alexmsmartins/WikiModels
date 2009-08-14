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

class Submission {
    var prev = ""
    def form (xhtml : NodeSeq) : NodeSeq = {
        var username = ""
        var password = ""

        def authenticate () = {
            if (username.length == 0) {
                S.error("Invalid username")
            } else if (username.equals("admin") && password.equals("admin")) {
                User.UserName(Full(username))
                println("I am logged "+User.UserName.is+" ok "+User.currentUserName)
            } else {
                println("Username= "+User.UserName+" Password= "+password)
            }
        }
        bind("entry", xhtml,
             "username" -> SHtml.text("", username = _, ("id","username"), ("size", "10"), ("maxlength", "40")),
             "password" -> SHtml.password("", password = _, ("id","password"), ("size", "10"), ("maxlength", "40")),
             "submit" -> SHtml.submit("Submit", authenticate,("id","login_button")))
    }

    def createDescription (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
            var model_id = ""
            var name = ""
            var description = ""
            var function_def_id = List[String]()
            var function_def_name = List[String]()
            var function_def_math = List[String]()
            var function_def_note = List[String]()
            var compartment_id = List[String]()
            var compartment_name = List[String]()
            var compartment_sd = List[String]()
            var compartment_size = List[String]()
            var compartment_outside = List[String]()
            var compartment_constant = List[String]()
            var compartment_note = List[String]()
            var species_id = List[String]()
            var species_name = List[String]()
            var species_compartment = List[String]()
            var species_init_amount = List[String]()
            var species_init_concent = List[String]()
            var species_bound_cond = List[String]()
            var species_constant = List[String]()
            var species_note = List[String]()
            var parameter_id = List[String]()
            var parameter_name = List[String]()
            var parameter_value = List[String]()
            var parameter_constant = List[String]()
            var parameter_note = List[String]()
            var constraint_math = List[String]()
            var constraint_message = List[String]()
            var constraint_note = List[String]()
            var reaction_id = List[String]()
            var reaction_name = List[String]()
            var reaction_reversible = List[String]()
            var reaction_fast = List[String]()
            var reaction_reactant_stoi = List[String]()
            var reaction_reactant_stoi_math = List[String]()
            var reaction_product_stoi = List[String]()
            var reaction_product_stoi_math = List[String]()
            var reaction_modifier = List[String]()
            var reaction_kinetic = List[String]()
            var reaction_kinetic_param = List[String]()
            //var reaction_kinetic_param_group = List[List[String]]()
            var reaction_note = List[String]()
            val msgName: String = S.attr("id_msgs") openOr "messages"

            def createModel () = {
                if (model_id.length == 0 && name.length == 0) {
                    S.error("Invalid Data")
                } else {
                    val modelSBML = {
                    <sbml xmlns="http://www.sbml.org/sbml/level2/version4' level='2' version='4' xmlns:xhtml='http://www.w3.org/1999/xhtml">
                        <model id={model_id} name={name}>
                            <notes>
                                <body>{description}
                                </body>
                            </notes>
                            <listOfFunctionDefinitions>
                                {if(function_def_id.length == 0){
                                } else {for(val i <- 0 to (function_def_id.length-1)) yield{
                                        <functionDefinitionID>{function_def_id(i)}</functionDefinitionID>
                                        <functionDefinitionName>{function_def_name(i)}</functionDefinitionName>
                                        <functionDefinitionMath>{function_def_math(i)}</functionDefinitionMath>
                                        <notes>{function_def_note(i)}</notes>
                                    }}
                                }
                            </listOfFunctionDefinitions>
                            <listOfCompartments>
                                {if(compartment_id.length == 0){
                                } else {for(val i <- 0 to (compartment_id.length-1)) yield{
                                        <compartmentID>{compartment_id(i)}</compartmentID>
                                        <compartmentName>{compartment_name(i)}</compartmentName>
                                        <compartmentSpatialDimensions>{compartment_sd(i)}</compartmentSpatialDimensions>
                                        <compartmentSize>{compartment_size(i)}</compartmentSize>
                                        <compartmentOutside>{compartment_outside(i)}</compartmentOutside>
                                        <compartmentConstant>{compartment_constant(i)}</compartmentConstant>
                                        <notes>{compartment_note(i)}</notes>
                                    }}
                                }
                            </listOfCompartments>
                            <listOfSpecies>
                                {if(species_id.length == 0){
                                } else {for(i <- 0 to species_id.length-1) yield{
                                        <speciesID>{species_id(i)}</speciesID>
                                        <speciesName>{species_name(i)}</speciesName>
                                        <speciesCompartment>{species_compartment(i)}</speciesCompartment>
                                        <speciesInitialAmount>{species_init_amount(i)}</speciesInitialAmount>
                                        <speciesInitialConcentration>{species_init_concent(i)}</speciesInitialConcentration>
                                        <speciesBoundaryCondition>{species_bound_cond(i)}</speciesBoundaryCondition>
                                        <speciesConstant>{species_constant(i)}</speciesConstant>
                                        <notes>{species_note(i)}</notes>
                                    }}
                                }
                            </listOfSpecies>
                            <listOfParameters>
                                {if(parameter_id.length == 0){
                                } else {for(i <- 0 to parameter_id.length-1) yield{
                                        <parameterID>{parameter_id(i)}</parameterID>
                                        <parameterName>{parameter_name(i)}</parameterName>
                                        <parameterValue>{parameter_value(i)}</parameterValue>
                                        <parameterConstant>{parameter_constant(i)}</parameterConstant>
                                        <notes>{parameter_note(i)}</notes>
                                    }}
                                }
                            </listOfParameters>
                            <listOfConstraints>
                                {if(constraint_math.length == 0){
                                } else {for(i <- 0 to constraint_math.length-1) yield{
                                        <constraintMath>{constraint_math(i)}</constraintMath>
                                        <constraintMessage>{constraint_message(i)}</constraintMessage>
                                        <notes>{constraint_note(i)}</notes>
                                    }}
                                }
                            </listOfConstraints>
                            <listOfReactions>
                                {if(reaction_id.length == 0){
                                } else {for(i <- 0 to reaction_id.length-1) yield{
                                        <reactionID>{reaction_id(i)}</reactionID>
                                        <reactionName>{reaction_name(i)}</reactionName>
                                        <reactionReversible>{reaction_reversible(i)}</reactionReversible>
                                        <reactionFast>{reaction_fast(i)}</reactionFast>
                                        <reactionReactantStoi>{reaction_reactant_stoi(i)}</reactionReactantStoi>
                                        <reactionReactantStoiMath>{reaction_reactant_stoi_math(i)}</reactionReactantStoiMath>
                                        <reactionProductStoi>{reaction_product_stoi(i)}</reactionProductStoi>
                                        <reactionProductStoiMath>{reaction_product_stoi_math(i)}</reactionProductStoiMath>
                                        <reactionModifier>{reaction_modifier(i)}</reactionModifier>
                                        <reactionKinetic>{reaction_kinetic(i)}</reactionKinetic>
                                        <reactionKineticParameter>{reaction_kinetic_param(i)}</reactionKineticParameter>
                                        <notes>{reaction_note(i)}</notes>
                                    }}
                                }
                            </listOfReactions>
                        </model>
                    </sbml>}
                    XML.save("file.xml", modelSBML)
                }
            }
            val listaNova = List("","true","false")
            def selectValue(va :String){
                compartment_sd = va :: compartment_sd
            }
            def selectValueC(va :String){
                compartment_constant = va :: compartment_constant
            }
            def setSpecieBC(va :String){
                species_bound_cond = va :: species_bound_cond
            }
            def setSpecieC(va :String){
                species_constant = va :: species_constant
            }
            def setParameterC(va :String){
                parameter_constant = va :: parameter_constant
            }
            def setReactionR(va :String){
                reaction_reversible = va :: reaction_reversible
            }
            def setReactionF(va :String){
                reaction_fast = va :: reaction_fast
            }

                // build up an ajax text box
    def doText(msg: NodeSeq) =
    SHtml.ajaxText("", v => DisplayMessage(msgName,
                                     bind("text", msg, "value" -> Text(v)),
                                     null, null))
            
            bind("createDescription", xhtml,
    "id" -> SHtml.text("", model_id = _, ("id", "model_id"), ("size", "40"), ("maxlength", "120")),
    "id_verify" -> SHtml.ajaxButton(Text("Verify ID"), () => {
        JsCrVar("verifyID","") &
        (ElemById("") ~> JsFunc("appendChild", Call("verifyID", "")))
      }),
   "text" -> doText _,
    "name" -> SHtml.text("", name = _, ("id","model_name"), ("size", "40"), ("maxlength", "120")),
    "description" -> SHtml.textarea("", description = _, ("id", "descriptionArea"), ("maxlength", "20000")),
    
     "buttonFunDef" -> SHtml.ajaxButton(Text("Add Function Definition"), () => {
        JsCrVar("funcDef", Jx(<ul>
                             {
        Jx(<li>Function Definition ID <span id="required_field">*</span>:
        {SHtml.text("", v => function_def_id = v :: function_def_id,("id", "functionDefinitionId"), ("size", "40"), ("maxlength", "1000"))}</li>
        <li>Function Definition Name <span id="required_field">*</span>:
        {SHtml.text("", v => function_def_name = v :: function_def_name,("id", "functionDefinitionName"), ("size", "40"), ("maxlength", "10000"))}</li>
        <li>Function Definition Math:
             <div id="button"><a href="" value="Help on Mathematical Formulas" /><img src="/classpath/images/question.png" width="20px" height="20px" /></div><br />

             <div id="popupContact"> <a id="popupContactClose">x</a>
             <h1>Help on mathematical formulas</h1>
             <p id="contactArea">
                 <hr />
                <p>&nbsp;</p>
                <p style="text-align:justify">To insert mathematical formulas in a model, use the
                <a href="http://www1.chapman.edu/~jipsen/mathml/asciimathsyntax.html" target="_blank">ASCIIMathML syntax</a>.
                Start the formula with "amath" and end with "endamath".<br /></p>
                <p  style="text-align:justify;">Ex.:<br />
                <p style="border:1px dashed #000;"><br /><font color="#ff0000">amath</font> x^2+b/2x+c/3=0 <font color="#ff0000">endamath</font><br /><br /></p>
                </p>
                <p  style="text-align:justify">To fully understand all the mathematical expressions that may be used,
                please consult this link:
                <a href="http://www1.chapman.edu/~jipsen/mathml/asciimathsyntax.html" target="_blank">ASCIIMathML syntax</a>
                <br /><br />
                </p>
             </p>
            </div>
            <div id="backgroundPopup"></div>
            
        {SHtml.textarea("", v => function_def_math = v :: function_def_math,("id", "functionDefinitionMath"), ("rows","3"), ("cols", "120"), ("maxlength", "20000"))}</li>
        <li>Function Definition Note:<br />
        {SHtml.textarea("", v => function_def_note = v :: function_def_note,("id", "functionDefinitionNote"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)
                                           }</ul><hr /><br />).toJs) &
        (ElemById("function_def") ~> JsFunc("appendChild", Call("funcDef", "")))
      }),
    "buttonCompartment" -> SHtml.ajaxButton(Text("Add Compartment"), () => {
        JsCrVar("comp", Jx(<ul>
                             {
    Jx(<li>Compartment ID <span id="required_field">*</span>:
    {SHtml.text("", v => compartment_id = v :: compartment_id,("id", "compartmentId"), ("size", "40"), ("maxlength", "1000"))}</li>
    <li>Compartment Name: <span id="required_field">*</span>
    {SHtml.text("", v => compartment_name = v :: compartment_name,("id", "compartmentName"), ("size", "40"), ("maxlength", "10000"))}</li>
    <li>Compartment Spatial Dimension:&nbsp;
    {SHtml.select((0 to 3).toList.reverse.map(v => (v.toString, v.toString)), Empty, selectValue _)}</li>
    <li>Compartment Size: &nbsp;
    {SHtml.text("", v => compartment_size = v :: compartment_size,("id", "compartmentSize"), ("size", "10"), ("maxlength", "100"))}</li>
    <li>Compartment Outside: &nbsp;
    {SHtml.text("", v => compartment_outside = v :: compartment_outside,("id", "compartmentOutside"), ("size", "10"), ("maxlength", "100"))}</li>
    <li>Compartment Constant: &nbsp;
    {SHtml.select(listaNova.reverse.map(v => (v, v)), Empty, selectValueC _)}</li>
    <li>Compartment Note:<br />
    {SHtml.textarea("", n => compartment_note = n :: compartment_note,("id", "compartmentNoteArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)
                             }
                           </ul> <hr /><br />).toJs) &
        (ElemById("compart") ~> JsFunc("appendChild", Call("comp", "")))
      }),
    "buttonSpecies" -> SHtml.ajaxButton(Text("Add Species"), () => {
        JsCrVar("spec", Jx(<ul>
                             {
    Jx(<li>Species ID <span id="required_field">*</span>: &nbsp;
    {SHtml.text("", v => species_id = v :: species_id,("id", "speciesId"), ("size", "40"), ("maxlength", "1000"))}</li>
    <li>Species Name <span id="required_field">*</span>: &nbsp;
    {SHtml.text("", v => species_name = v :: species_name,("id", "speciesName"), ("size", "40"), ("maxlength", "10000"))}</li>
    <li>Species Compartment <span id="required_field">*</span>: &nbsp;
    {SHtml.text("", v => species_compartment = v :: species_compartment,("id", "speciesCompartment"), ("size", "40"), ("maxlength", "1000"))}</li>
    <li>Species Initial Amount: &nbsp;
    {SHtml.text("", v => species_init_amount = v :: species_init_amount,("id", "speciesInitAmount"), ("size", "10"), ("maxlength", "1000"))}</li>
    <li>Species Initial Concentration: &nbsp;
    {SHtml.text("", v => species_init_concent = v :: species_init_concent,("id", "speciesInitConc"), ("size", "10"), ("maxlength", "1000"))}</li>
    <li>Species Boundary Condition: &nbsp;
    {SHtml.select(listaNova.reverse.map(v => (v, v)), Empty, setSpecieBC _)}</li>
    <li>Species Constant: &nbsp;
    {SHtml.select(listaNova.reverse.map(v => (v, v)), Empty, setSpecieC _)}</li>
    <li>Species Note:<br />
    {SHtml.textarea("", n => species_note = n :: species_note,("id", "speciesNotesArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)
                             }
                           </ul><hr /><br />).toJs) &
        (ElemById("specie") ~> JsFunc("appendChild", Call("spec", "")))
      }),
    "buttonParam" -> SHtml.ajaxButton(Text("Add Parameter"), () => {
        JsCrVar("param", Jx(<ul>
                             {
    Jx(<li>Parameter ID <span id="required_field">*</span>: &nbsp;
    {SHtml.text("", v => parameter_id = v :: parameter_id,("id", "parameterId"), ("size", "40"), ("maxlength", "1000"))}</li>
    <li>Parameter Name <span id="required_field">*</span>: &nbsp;
    {SHtml.text("", v => parameter_name = v :: parameter_name,("id", "parameterName"), ("size", "40"), ("maxlength", "10000"))}</li>
    <li>Parameter Value : &nbsp;
    {SHtml.text("", v => parameter_value = v :: parameter_value,("id", "parameterValue"), ("size", "10"), ("maxlength", "100"))}</li>
    <li>Parameter Constant: &nbsp;
    {SHtml.select(listaNova.reverse.map(v => (v, v)), Empty, setParameterC _)}</li>
    <li>Parameter Note:<br />
    {SHtml.textarea("", n => parameter_note = n :: parameter_note,("id", "parameterNoteArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)
                             }
                           </ul><hr /><br />).toJs) &
        (ElemById("paramet") ~> JsFunc("appendChild", Call("param", "")))
      }),
    "buttonConstraint" -> SHtml.ajaxButton(Text("Add Constraint"), () => {
        JsCrVar("constr", Jx(<ul>
                             {
    Jx(<li>Constraint Math:
       <a value="Help on Mathematical Formulas" href="" onclick="window.open('/help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">
                 <img src="/classpath/images/question.png" width="20px" height="20px" /></a><br />
    {SHtml.textarea("", v => constraint_math = v :: constraint_math,("id", "constraintMath"), ("rows","3"), ("cols", "120"), ("maxlength", "10000"))}</li>
    <li>Constraint Message:<br />
    {SHtml.textarea("", v => constraint_message = v :: constraint_message,("id", "constraintMessage"), ("rows","3"), ("cols", "120"), ("maxlength", "10000"))}</li>
    <li>Constraint Note:<br />
    {SHtml.textarea("", n => constraint_note = n :: constraint_note,("id", "constraintNoteArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)
                             }
                           </ul><hr /><br />).toJs) &
        (ElemById("const") ~> JsFunc("appendChild", Call("constr", "")))
      }),
    "buttonReactions" -> SHtml.ajaxButton(Text("Add Reaction"), () => {
        JsCrVar("reactionFunc", Jx(<ul>
                             {
    Jx(<li>Reaction ID <span id="required_field">*</span>: &nbsp;
    {SHtml.text("", v => reaction_id = v :: reaction_id,("id", "reactionId"), ("size", "40"), ("maxlength", "1000"))}</li>
    <li>Reaction Name <span id="required_field">*</span>: &nbsp;
    {SHtml.text("", v => parameter_name = v :: parameter_name,("id", "reactionName"), ("size", "40"), ("maxlength", "10000"))}</li>
    <li>Reaction Reversible: &nbsp;
    {SHtml.select(listaNova.reverse.map(v => (v, v)), Empty, setReactionR _)}</li>
    <li>Reaction Fast: &nbsp;
    {SHtml.select(listaNova.reverse.map(v => (v, v)), Empty, setReactionF _)}</li>
    <li>Reaction Reactant Stoichiometry: &nbsp;
    {SHtml.text("", n => reaction_reactant_stoi = n :: reaction_reactant_stoi,("id", "reactionReactantSt"), ("size","20"), ("maxlength", "500"))}</li>
    <li>Reaction Reactant Stoichiometry Math:
         <a value="Help on Mathematical Formulas" href="" onclick="window.open('/help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">
                 <img src="/classpath/images/question.png" width="20px" height="20px" /></a><br />
    {SHtml.textarea("", n => reaction_reactant_stoi_math = n :: reaction_reactant_stoi_math,("id", "reactionReactantStMath"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
    <li>Reaction Product Stoichiometry: &nbsp;
    {SHtml.text("", n => reaction_product_stoi = n :: reaction_product_stoi,("id", "reactionProductSt"), ("size","20"), ("maxlength", "500"))}</li>
    <li>Reaction Product Stoichiometry Math:
         <a value="Help on Mathematical Formulas" href="" onclick="window.open('/help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">
                 <img src="/classpath/images/question.png" width="20px" height="20px" /></a><br />
    {SHtml.textarea("", n => reaction_product_stoi_math = n :: reaction_product_stoi_math,("id", "reactionProductStMath"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
    <li>Reaction Modifier: &nbsp;
    {SHtml.text("", n => reaction_modifier = n :: reaction_modifier,("id", "reactionModifier"), ("size","10"), ("maxlength", "500"))}</li>
    <li>Reaction Kinetic Math:
         <a value="Help on Mathematical Formulas" href="" onclick="window.open('/help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">
                 <img src="/classpath/images/question.png" width="20px" height="20px" /></a><br />
    {SHtml.textarea("", n => reaction_kinetic = n :: reaction_kinetic,("id", "reactionMath"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
    <li>Reaction Kinetic Parameters: &nbsp;
         {SHtml.text("", n => reaction_kinetic_param = n :: reaction_kinetic_param,("id", "reactionParameters"), ("size","20"), ("maxlength", "500"))}
         {/*<ul id="kinetic_param"></ul>
         {
          SHtml.ajaxButton(Text("Add Parameter"), () => {
        JsCrVar("kinetic_p", Jx(<ul>
            {Jx(<li>{SHtml.text("", n => reaction_kinetic_param = n :: reaction_kinetic_param,("id", "reactionParameters"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}
                </li>)}</ul><hr /> <br />).toJs) &
        (ElemById("kinetic_param") ~> JsFunc("appendChild", Call("kinetic_p", "")))})}
    {Console.println("Meti ="+reaction_kinetic_param.reverseMap(s => s))
        reaction_kinetic_param_group = reaction_kinetic_param :: reaction_kinetic_param_group}*/}
    </li>
    <li>Reaction Note:<br />
    {SHtml.textarea("", n => reaction_note = n :: reaction_note,("id", "reactionArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)
                             }</ul><hr /><br />).toJs) &
        (ElemById("react") ~> JsFunc("appendChild", Call("reactionFunc", "")))
      }),
    "save" -> SHtml.submit("Save Model", createModel,("id","buttonSave"), ("style","position:fixed;top:170px;right:100px;width:150px;text-align:center;border:3px solid #000;")))
        }
        case _ => Text("")
    }
}
