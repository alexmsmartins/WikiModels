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


class CreateModel {
    var global:String = null

    def createDescription (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
                var id:String = null
                var model_id:String = null
                var model_name:String = null
                var description:String = null
                var incr:Int = 0
                var count:Int = 0
                var reactant_count:Int = 0
                var reactant_key:String = null
                var reactant_ul:String = null
                var product_ul:String = null
                var product_count:Int = 0
                var product_key:String = null
                var modifier_ul:String = null
                var modifier_count:String = null
                var modifier_key:String = null
                var parameter_ul:String = null
                var parameter_count:Int = 0
                var parameter_key:String = null
                var function_def_id = new LinkedList[String]()
                var function_def_name = new LinkedList[String]()
                var function_def_math = new LinkedList[String]()
                var function_def_note = new LinkedList[String]()
                var compartment_id = new LinkedList[String]()
                var compartment_name = new LinkedList[String]()
                var compartment_sd = new LinkedList[String]()
                var compartment_size = new LinkedList[String]()
                var compartment_outside = new LinkedList[String]()
                var compartment_constant = new LinkedList[String]()
                var compartment_note = new LinkedList[String]()
                var species_id = new LinkedList[String]()
                var species_name = new LinkedList[String]()
                var species_compartment = new LinkedList[String]()
                var species_init_amount = new LinkedList[String]()
                var species_init_concent = new LinkedList[String]()
                var species_bound_cond = new LinkedList[String]()
                var species_constant = new LinkedList[String]()
                var species_note = new LinkedList[String]()
                var parameter_id = new LinkedList[String]()
                var parameter_name = new LinkedList[String]()
                var parameter_value = new LinkedList[String]()
                var parameter_constant = new LinkedList[String]()
                var parameter_note = new LinkedList[String]()
                var constraint_math = new LinkedList[String]()
                var constraint_message = new LinkedList[String]()
                var constraint_note = new LinkedList[String]()
                var reaction_id = new LinkedList[String]()
                var reaction_number = new LinkedList[String]()
                var reaction_name = new LinkedList[String]()
                var reaction_reversible = new LinkedList[String]()
                var reaction_fast = new LinkedList[String]()
                var reaction_note = new LinkedList[String]()
                var reactant_hash = new TreeMap[String,Reactant]()
                var product_hash = new TreeMap[String,Product]()
                var modifier_hash = new TreeMap[String,Modifier]()
                var reaction_kinetic = new LinkedList[String]()
                var parameter_hash = new TreeMap[String,Parameter]()
                //var reaction_kinetic_param_group = List[List[String]]()
                var kineticParameter = new Parameter();
                
                val msgName: String = S.attr("id_msgs") openOr "messages"
                //val fdefinit = new FunctionDefinition

                /**
                 * generates a XML representation of the SBML Model
                 * it does not include the DOCTYPE declaration or the sbml top tag
                 * Those should be added by a proper export funtion when it is created
                 * @return the XML representing the user
                 */

                def createNewModel () = {
                    var contadorReactant = 0
                    var contadorProduct = 0
                    var contadorModifier = 0
                    var contadorParameter = 0
                    model_id = model_name.replace(" ", "").toLowerCase
                    if (model_id.length == 0 && model_name.length == 0) {
                        S.error("Invalid Data")
                    } else {
                        val modelSBML = {
                            <model id={model_id} name={model_name}>
                                <notes>
                                    <body>{XML.loadString(description)}
                                    </body>
                                </notes>
                                {
                                    if(function_def_id != null) {
                                        <listOfFunctionDefinitions>
                                            {
                                                for(val i <- 0 to (function_def_id.size-1)) yield{
                                                    <functionDefinition name={function_def_name.get(i)} id={function_def_id.get(i)}>
                                                        <math xmlns="http://www.w3.org/1998/Math/MathML">{function_def_math.get(i)}</math>
                                                        <notes>{function_def_note.get(i)}</notes>
                                                    </functionDefinition>
                                                }
                                            }
                                        </listOfFunctionDefinitions>
                                    } else scala.xml.Null
                                }
                                {
                                    if(compartment_id != null) {
                                        <listOfCompartments>
                                            {for(val i <- 0 to (compartment_id.size-1)) yield{
                                                    <compartment outside={compartment_outside.get(i)} spacialDimensions={compartment_sd.get(i)} size={compartment_size.get(i)}
                                                        name={compartment_name.get(i)} id={compartment_id.get(i)} constant={compartment_constant.get(i)}>
                                                        <notes>{compartment_note.get(i)}</notes>
                                                    </compartment>
                                                }
                                            }
                                        </listOfCompartments>
                                    } else scala.xml.Null
                                }
                                {
                                    if(species_id != null) {
                                        <listOfSpecies>
                                            {
                                                for(i <- 0 to species_id.size-1) yield{
                                                    <species id={species_id.get(i)} name={species_name.get(i)} compartment={species_compartment.get(i)}
                                                        initialAmount={species_init_amount.get(i)} initialConcentration={species_init_concent.get(i)}
                                                        boundaryCondition={species_bound_cond.get(i)} constant={species_constant.get(i)}>
                                                        <notes>{species_note.get(i)}</notes>
                                                    </species>
                                                }
                                            }
                                        </listOfSpecies>
                                    } else scala.xml.Null
                                }
                                {
                                    if(parameter_id != null) {
                                        <listOfParameters>
                                            {
                                                for(i <- 0 to parameter_id.size-1) yield{
                                                    <parameter id={parameter_id.get(i)} name={parameter_name.get(i)} value={parameter_value.get(i)}
                                                        constant={parameter_constant.get(i)}>
                                                        <notes>{parameter_note.get(i)}</notes>
                                                    </parameter>
                                                }
                                            }
                                        </listOfParameters>
                                    } else scala.xml.Null
                                }
                                {
                                    if(constraint_math != null) {
                                        <listOfConstraints>
                                            {
                                                for(i <- 0 to constraint_math.size-1) yield{
                                                    <constraint><math xmlns="http://www.w3.org/1998/Math/MathML">{constraint_math.get(i)}
                                                                </math>
                                                        <message>{constraint_message.get(i)}</message>
                                                        <notes>{constraint_note.get(i)}</notes>
                                                    </constraint>
                                                }
                                            }
                                        </listOfConstraints>
                                    } else scala.xml.Null
                                }
                                {if(reaction_id.size != 0){
                                        <listOfReactions>
                                            {for(i <- 1 to reaction_id.size) yield{
                                                    <reaction reversible={reaction_reversible.get(i-1)} name={reaction_name.get(i-1)} id={reaction_id.get(i-1)} fast={reaction_fast.get(i-1)}>
                                                        <notes>{reaction_note.get(i-1)}</notes>
                                                        <listOfReactants>{
                                                                if((reactant_hash.size > 0)) {
                                                                    contadorReactant = contadorReactant+1
                                                                    var ent = reactant_hash.entrySet
                                                                    var enum = ent.iterator
                                                                    for(k <- 1 to reactant_hash.size) yield{
                                                                        for ( l <- enum.hasNext) yield {
                                                                            var entry = enum.next
                                                                            var teste = "reactant_"+i+"_"+k
                                                                            if((entry.getKey.equals(teste)) && (contadorReactant == i)){
                                                                                var valor = entry.getValue
                                                                                <speciesReference id={valor.get_reactant_id} name={valor.get_reactant_name} species={valor.get_reactant_specie} stoichiometry={valor.get_reactant_stoic}>
                                                                                    <notes>{valor.get_reactant_note}</notes>
                                                                                    {
                                                                                        if(valor.get_reactant_stoic_math.length > 0){
                                                                                            <stoichiometryMath>
                                                                                                {valor.get_reactant_stoic_math}
                                                                                            </stoichiometryMath>
                                                                                        } else scala.xml.Null}
                                                                                </speciesReference>
                                                                            } else scala.xml.Null
                                                                        }
                                                                    }
                                                                } else scala.xml.Null}
                                                        </listOfReactants>
                                                        <listOfProducts>{
                                                                if((product_hash.size > 0)) {
                                                                    contadorProduct = contadorProduct+1
                                                                    var ent = product_hash.entrySet
                                                                    var enum = ent.iterator
                                                                    for(k <- 1 to product_hash.size) yield{
                                                                        for ( l <- enum.hasNext) yield {
                                                                            var entry = enum.next
                                                                            var teste = "product_"+i+"_"+k
                                                                            if((entry.getKey.equals(teste)) && (contadorProduct == i)){
                                                                                var valor = entry.getValue
                                                                                <speciesReference id={valor.get_product_name} name={valor.get_product_name} species={valor.get_product_specie} stoichiometry={valor.get_product_stoic}>
                                                                                    <notes>{valor.get_product_note}</notes>
                                                                                    {
                                                                                        if(valor.get_product_stoic_math.length > 0){
                                                                                            <stoichiometryMath>
                                                                                                {valor.get_product_stoic_math}
                                                                                            </stoichiometryMath>
                                                                                        } else scala.xml.Null}
                                                                                </speciesReference>
                                                                            } else scala.xml.Null
                                                                        }
                                                                    }
                                                                } else scala.xml.Null}
                                                        </listOfProducts>
                                                        <listOfModifiers>{
                                                                if((modifier_hash.size > 0)) {
                                                                    contadorModifier = contadorModifier+1
                                                                    var ent = modifier_hash.entrySet
                                                                    var enum = ent.iterator
                                                                    for(k <- 1 to modifier_hash.size) yield{
                                                                        for ( l <- enum.hasNext) yield {
                                                                            var entry = enum.next
                                                                            var teste = "modifier_"+i+"_"+k
                                                                            if((entry.getKey.equals(teste)) && (contadorModifier == i)){
                                                                                var valor = entry.getValue
                                                                                <speciesReference id={valor.get_modifier_id} name={valor.get_modifier_name} species={valor.get_modifier_specie} stoichiometry={valor.get_modifier_stoic}>
                                                                                    <notes>{valor.get_modifier_note}</notes>
                                                                                    {
                                                                                        if(valor.get_modifier_stoic_math.length > 0){
                                                                                            <stoichiometryMath>
                                                                                                {valor.get_modifier_stoic_math}
                                                                                            </stoichiometryMath>
                                                                                        } else scala.xml.Null}
                                                                                </speciesReference>
                                                                            } else scala.xml.Null
                                                                        }
                                                                    }
                                                                } else scala.xml.Null}
                                                        </listOfModifiers>
                                                        <kineticLaw>{
                                                                if((reaction_kinetic.size > 0)) {
                                                                    for(k <- 0 to reaction_kinetic.size-1) yield{
                                                                        <math xmlns="http://www.w3.org/1998/Math/MathML">{reaction_kinetic.get(k)}</math>
                                                                    }
                                                                } else scala.xml.Null
                                                                <listOfParameters>{
                                                                        if((parameter_hash.size > 0)) {
                                                                            contadorParameter = contadorParameter+1
                                                                            var ent = parameter_hash.entrySet
                                                                            var enum = ent.iterator
                                                                            for(k <- 1 to parameter_hash.size) yield{
                                                                                for ( l <- enum.hasNext) yield {
                                                                                    var entry = enum.next
                                                                                    var teste = "parameter_"+i+"_"+k
                                                                                    if((entry.getKey.equals(teste)) && (contadorParameter == i)){
                                                                                        var valor = entry.getValue
                                                                                        <parameter id={valor.get_parameter_id} name={valor.get_parameter_name} value={valor.get_parameter_value}
                                                                                            constant={valor.get_parameter_constant}>
                                                                                            <notes>{valor.get_parameter_note}</notes>
                                                                                        </parameter>
                                                                                    } else scala.xml.Null
                                                                                }
                                                                            }
                                                                        } else scala.xml.Null}
                                                                </listOfParameters>
                                                            }</kineticLaw>
                                                    </reaction>
                                                }
                                            }
                                        </listOfReactions>
                                    } else scala.xml.Null
                                }
                            </model>
                        }
                        Console.println(modelSBML)
                        XML.save("file.xml", modelSBML)
                        S.redirectTo("/models/browse.xhtml?modelID=file")
                    }
                }
                val listaNova = List("true","false")
                
                def selectValue(va :String){
                    compartment_sd.add(va)
                }
                def selectValueC(va :String){
                    compartment_constant.add(va)
                }
                def setSpecieBC(va :String){
                    species_bound_cond.add(va)
                }
                def setSpecieC(va :String){
                    species_constant.add(va)
                }
                def setParameterC(va :String){
                    parameter_constant.add(va)
                }
                def setKineticParameterC(va :String){
                    kineticParameter.set_parameter_constant(va)
                }
                def setReactionR(va :String){
                    reaction_reversible.add(va)
                }
                def setReactionF(va :String){
                    reaction_fast.add(va)
                }

                // This method creates the id of the model
                def createNameAndId(nameml: NodeSeq) = {
                    SHtml.ajaxText("", ref => { model_name = ref;
                                               DisplayMessage(msgName,
                                                              bind("text", nameml, "model_id" ->
                                                                   SHtml.text(Text(ref.replace(" ", "").toLowerCase).toString, refer2 => model_id = refer2, ("id", "model_id"), ("size", "40"), ("disabled", "disabled"))),
                                                              300000 seconds, 1 second)}, ("id", "name_model"), ("size", "40"), ("maxlength", "1000"))
                }

                bind("createDescription", xhtml,
                     "name" -> createNameAndId _,
    
                     "description" -> SHtml.textarea("", description = _, ("id", "descriptionArea"), ("maxlength", "20000")),
    
                     "buttonFunDef" -> SHtml.ajaxButton(Text("Add Function Definition"), () => {

                            JsCrVar("funcDef", Jx(<ul>
                                                  {
                                        Jx(<li>Function Definition Name <span id="required_field">*</span>:
                                           {SHtml.text("", v => {function_def_name.add(v)
                                                                 function_def_id.add(v.replace(" ", "").toLowerCase)},("id", "functionDefinitionName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                           <li>Function Definition Math:
                                                <a value="Help on Mathematical Formulas" href="" onclick="window.open('../help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">
                                                    <img src="../classpath/images/question.png" width="20px" height="20px" /></a><br />
                                                {SHtml.textarea("amath [insert mathematical formula here] endamath", v => function_def_math.add(v),("id", "functionDefinitionMath"), ("rows","3"), ("cols", "120"), ("maxlength", "20000"))}</li>
                                           <li>Function Definition Note:<br />
                                                {SHtml.textarea("", v => function_def_note.add(v),("id", "functionDefinitionNote"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))
                                                }</li>)
                                    }</ul> <hr /><br />
                                ).toJs) &
                            (ElemById("function_def") ~> JsFunc("appendChild", Call("funcDef", "")))
                        }),
                     "buttonCompartment" -> SHtml.ajaxButton(Text("Add Compartment"), () => {
                            JsCrVar("comp", Jx(<ul>
                                               {
                                        Jx(<li>Compartment Name: <span id="required_field">*</span>&nbsp;
                                           {SHtml.text("", v => {compartment_name.add(v)
                                                                 compartment_id.add(v.replace(" ", "").toLowerCase)},("id", "compartmentName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                           <li>Compartment Spatial Dimension:&nbsp;
                                                {SHtml.select((0 to 3).toList.reverse.map(v => (v.toString, v.toString)), Empty, selectValue _)}</li>
                                           <li>Compartment Size: &nbsp;
                                                {SHtml.text("", v => compartment_size.add(v),("id", "compartmentSize"), ("size", "10"), ("maxlength", "100"))}</li>
                                           <li>Compartment Outside: &nbsp;
                                                {SHtml.text("", v => compartment_outside.add(v),("id", "compartmentOutside"), ("size", "10"), ("maxlength", "100"))}</li>
                                           <li>Compartment Constant: &nbsp;<i>(default="true")</i> &nbsp;
                                                {SHtml.select(listaNova.map(v => (v, v)), Empty, selectValueC _)}</li>
                                           <li>Compartment Note:<br />
                                                {SHtml.textarea("", n => compartment_note.add(n),("id", "compartmentNoteArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)
                                    }
                                               </ul> <hr /><br />).toJs) &
                            (ElemById("compart") ~> JsFunc("appendChild", Call("comp", "")))
                        }),
                     "buttonSpecies" -> SHtml.ajaxButton(Text("Add Species"), () => {
                            JsCrVar("spec", Jx(<ul>
                                               {
                                        Jx(<li>Species Name <span id="required_field">*</span>: &nbsp;
                                           {SHtml.text("", v => {species_name.add(v)
                                                                 species_id.add(v.replace(" ", "").toLowerCase)},("id", "speciesName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                           <li>Species Compartment <span id="required_field">*</span>: &nbsp;
                                                {SHtml.text("", v => species_compartment.add(v),("id", "speciesCompartment"), ("size", "40"), ("maxlength", "1000"))}</li>
                                           <li>Species Initial Amount: &nbsp;
                                                {SHtml.text("", v => species_init_amount.add(v),("id", "speciesInitAmount"), ("size", "10"), ("maxlength", "1000"))}</li>
                                           <li>Species Initial Concentration: &nbsp;
                                                {SHtml.text("", v => species_init_concent.add(v),("id", "speciesInitConc"), ("size", "10"), ("maxlength", "1000"))}</li>
                                           <li>Species Boundary Condition: &nbsp;<i>(default="false")</i> &nbsp;
                                                {SHtml.select(listaNova.reverse.map(v => (v, v)), Empty, setSpecieBC _)}</li>
                                           <li>Species Constant: &nbsp;<i>(default="false")</i> &nbsp;
                                                {SHtml.select(listaNova.reverse.map(v => (v, v)), Empty, setSpecieC _)}</li>
                                           <li>Species Note:<br />
                                                {SHtml.textarea("", n => species_note.add(n),("id", "speciesNotesArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)
                                    }
                                               </ul><hr /><br />).toJs) &
                            (ElemById("specie") ~> JsFunc("appendChild", Call("spec", "")))
                        }),
                     "buttonParam" -> SHtml.ajaxButton(Text("Add Parameter"), () => {
                            JsCrVar("param", Jx(<ul>
                                                {
                                        Jx(<li>Parameter Name <span id="required_field">*</span>: &nbsp;
                                           {SHtml.text("", v => {parameter_name.add(v)
                                                                 parameter_id.add(v.replace(" ", "").toLowerCase)},("id", "parameterName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                           <li>Parameter Value : &nbsp;
                                                {SHtml.text("", v => parameter_value.add(v),("id", "parameterValue"), ("size", "10"), ("maxlength", "100"))}</li>
                                           <li>Parameter Constant: &nbsp;<i>(default="true")</i> &nbsp;
                                                {SHtml.select(listaNova.map(v => (v, v)), Empty, setParameterC _)}</li>
                                           <li>Parameter Note:<br />
                                                {SHtml.textarea("", n => parameter_note.add(n),("id", "parameterNoteArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)
                                    }
                                                </ul><hr /><br />).toJs) &
                            (ElemById("paramet") ~> JsFunc("appendChild", Call("param", "")))
                        }),
                     "buttonConstraint" -> SHtml.ajaxButton(Text("Add Constraint"), () => {
                            JsCrVar("constr", Jx(<ul>
                                                 {
                                        Jx(<li>Constraint Math:
                                           <a value="Help on Mathematical Formulas" href="" onclick="window.open('../help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">
                                                <img src="../classpath/images/question.png" width="20px" height="20px" /></a><br />
                                           {SHtml.textarea("amath [insert mathematical formula here] endamath", v => constraint_math.add(v),("id", "constraintMath"), ("rows","3"), ("cols", "120"), ("maxlength", "10000"))}</li>
                                           <li>Constraint Message:<br />
                                                {SHtml.textarea("", v => constraint_message.add(v),("id", "constraintMessage"), ("rows","3"), ("cols", "120"), ("maxlength", "10000"))}</li>
                                           <li>Constraint Note:<br />
                                                {SHtml.textarea("", n => constraint_note.add(n),("id", "constraintNoteArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)
                                    }
                                                 </ul><hr /><br />).toJs) &
                            (ElemById("const") ~> JsFunc("appendChild", Call("constr", "")))
                        }),
                     "buttonReactions" -> SHtml.ajaxButton(Text("Add Reaction"), () => {
                            count = count+1
                            reactant_ul = "reactant_"+count+"_"
                            product_ul = "product_"+count+"_"
                            modifier_ul = "modifier_"+count+"_"
                            parameter_ul = "parameter_"+count+"_"
        
                            JsCrVar("reactionFunc", Jx(<ul>
                                                       {
                                        Jx(<li>Reaction Name <span id="required_field">*</span>:
                                           {SHtml.text("", v => {reaction_name.add(v)
                                                                 reaction_id.add(v.replace(" ", "").toLowerCase)},("id", "reactionName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                           <li>Reaction Reversible:  <i>(default="false")</i>
                                                {SHtml.select(listaNova.map(v => (v, v)), Empty, setReactionR _)}</li>
                                           <li>Reaction Fast:  <i>(default="false")</i>
                                                {SHtml.select(listaNova.reverse.map(v => (v, v)), Empty, setReactionF _)}</li>
                                           <ul id={reactant_ul}></ul>
                                           <li>{SHtml.ajaxButton(Text("Add Reactant"), () => {
                                                            reactant_count = reactant_count+1
                                                            reactant_key = reactant_ul+reactant_count
                                                            var reactant = new Reactant();
                                                            JsCrVar("reactionNewReactant", Jx(<hr /><ul id="reactantStyle">{
                                                                        Jx(<li><font style="font-weight:bold; font-size:110%;">New Reactant</font>
                                                                           </li><br /><li>Reactant Name <span id="required_field">*</span>:
                                                                           {SHtml.text("", v => {reactant.set_reactant_name(v)
                                                                                                 reactant.set_reactant_id(v.replace(" ","").toLowerCase)
                                                                                    }
                                                                                       ,("id", "reactantName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                                                           <li>Reactant Specie <span id="required_field">*</span>:
                                                                                {SHtml.text("", v => {reactant.set_reactant_specie(v)
                                                                                        },("id", "reactantSpecie"), ("size", "40"), ("maxlength", "10000"))}</li>
                                                                           <li>Reactant Stoichiometry:  <i>(default="1")</i>
                                                                                {SHtml.text("1", v => {reactant.set_reactant_stoic(v)
                                                                                        },("id", "reactantStoic"), ("size", "10"), ("maxlength", "10000"))}</li>
                                                                           <li>Reactant Stoichiometry Math: <a value="Help on Mathematical Formulas" href="" onclick="window.open('../help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">
                                                                                    <img src="../classpath/images/question.png" width="20px" height="20px" /></a><br />
                                                                                {SHtml.textarea("amath [insert mathematical formula here] endamath", v => {reactant.set_reactant_stoic_math(v)
                                                                                        },("id", "reactantStoicMath"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
                                                                           <li>Reactant Note:<br />
                                                                                {SHtml.textarea("", v => {reactant.set_reactant_note(v)
                                                                                        },("id", "reactantNote"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))
                                                                                }</li>)}<br />{reactant_hash.put(reactant_key, reactant)}</ul>
                                                                ).toJs) &
                                                            (ElemById(reactant_ul) ~> (JsFunc("appendChild", Call("reactionNewReactant", ""))))})}
                                           </li>
                                           <ul id={product_ul}></ul>
                                           <li>{SHtml.ajaxButton(Text("Add Product"), () => {
                                                            product_count = product_count+1
                                                            product_key = product_ul+product_count
                                                            var product = new Product();
                                                            JsCrVar("reactionNewProduct", Jx(<hr /><ul id="productStyle">{
                                                                        Jx(<li><font style="font-weight:bold; font-size:110%;">New Product</font>
                                                                           </li><br /><li>Product Name <span id="required_field">*</span>:
                                                                           {SHtml.text("", v => {product.set_product_name(v)
                                                                                                 product.set_product_id(v.replace(" ","").toLowerCase)
                                                                                    }
                                                                                       ,("id", "productName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                                                           <li>Product Specie <span id="required_field">*</span>:
                                                                                {SHtml.text("", v => {product.set_product_specie(v)
                                                                                        },("id", "productSpecie"), ("size", "40"), ("maxlength", "10000"))}</li>
                                                                           <li>Product Stoichiometry:  <i>(default="1")</i>
                                                                                {SHtml.text("1", v => {product.set_product_stoic(v)
                                                                                        },("id", "productStoic"), ("size", "10"), ("maxlength", "10000"))}</li>
                                                                           <li>Product Stoichiometry Math: <a value="Help on Mathematical Formulas" href="" onclick="window.open('../help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">
                                                                                    <img src="../classpath/images/question.png" width="20px" height="20px" /></a><br />
                                                                                {SHtml.textarea("amath [insert mathematical formula here] endamath", v => {product.set_product_stoic_math(v)
                                                                                        },("id", "productStoicMath"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
                                                                           <li>Product Note:<br />
                                                                                {SHtml.textarea("", v => {product.set_product_note(v)
                                                                                        },("id", "productNote"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)}
                                                                                             <br />{product_hash.put(product_key, product)}</ul>
                                                                ).toJs) &
                                                            (ElemById(product_ul) ~> (JsFunc("appendChild", Call("reactionNewProduct", ""))))})}
                                           </li>
                                           <ul id={modifier_ul}></ul>
                                           <li>{SHtml.ajaxButton(Text("Add Modifier"), () => {
                                                            modifier_count = modifier_count+1
                                                            modifier_key = modifier_ul+modifier_count
                                                            var modifier = new Modifier();
                                                            JsCrVar("reactionNewModifier", Jx(<hr /><ul id="modifierStyle">{
                                                                        Jx(<li><font style="font-weight:bold; font-size:110%;">New Modifier</font>
                                                                           </li><br /><li>Modifier Name <span id="required_field">*</span>:
                                                                           {SHtml.text("", v => {modifier.set_modifier_name(v)
                                                                                                 modifier.set_modifier_id(v.replace(" ","").toLowerCase)
                                                                                    }
                                                                                       ,("id", "modifierName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                                                           <li>Modifier Specie <span id="required_field">*</span>:
                                                                                {SHtml.text("", v => {modifier.set_modifier_specie(v)
                                                                                        },("id", "modifierSpecie"), ("size", "40"), ("maxlength", "10000"))}</li>
                                                                           <li>Modifier Stoichiometry:  <i>(default="1")</i>
                                                                                {SHtml.text("1", v => {modifier.set_modifier_stoic(v)
                                                                                        },("id", "modifierStoic"), ("size", "10"), ("maxlength", "10000"))}</li>
                                                                           <li>Modifier Stoichiometry Math: <a value="Help on Mathematical Formulas" href="" onclick="window.open('../help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">
                                                                                    <img src="../classpath/images/question.png" width="20px" height="20px" /></a><br />
                                                                                {SHtml.textarea("amath [insert mathematical formula here] endamath", v => {modifier.set_modifier_stoic_math(v)
                                                                                        },("id", "modifierStoicMath"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
                                                                           <li>Modifier Note:<br />
                                                                                {SHtml.textarea("", v => {modifier.set_modifier_note(v)
                                                                                        },("id", "productNote"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)}
                                                                                              <br />{modifier_hash.put(modifier_key, modifier)}</ul>
                                                                ).toJs) &
                                                            (ElemById(modifier_ul) ~> (JsFunc("appendChild", Call("reactionNewModifier", ""))))})}
                                           </li>
                                           <li>Kinetic Law: <a value="Help on Mathematical Formulas" href="" onclick="window.open('../help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">
                                                    <img src="../classpath/images/question.png" width="20px" height="20px" /></a><br />
                                                {SHtml.textarea("amath [insert mathematical formula here] endamath", v => {reaction_kinetic.add(v)
                                                        },("id", "reactionKineticLaw"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
                                           <ul id={parameter_ul}></ul>
                                           <li>{SHtml.ajaxButton(Text("Add Kinetic Law Parameter"), () => {
                                                            parameter_count = parameter_count+1
                                                            parameter_key = parameter_ul+parameter_count
                                                            
                                                            JsCrVar("reactionNewKineticParameter", Jx(<hr /><ul id="productStyle">{
                                                                        Jx(<li><font style="font-weight:bold; font-size:110%;">New Parameter</font>
                                                                           </li><br /><li>Parameter Name <span id="required_field">*</span>: &nbsp;
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
                                           <li>Reaction Note:<br />
                                                {SHtml.textarea("", n => reaction_note.add(n),("id", "reactionArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
                                        )}</ul><hr />).toJs) &
                            (ElemById("react") ~> JsFunc("appendChild", Call("reactionFunc", "")))
                        }),
                     "save" -> SHtml.submit("Save Model", createNewModel,("id","buttonSave")))
            }
        case _ => Text("")
    }
}
