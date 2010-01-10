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
import java.util.Hashtable
import java.util.Enumeration
import net.liftweb.common._

import rest.client.BasicAuth
import rest.client.RestfulAccess


class CreateModel {
    var global:String = null

    def createDescription (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
                var id:String = null
                var model_id:String = null
                var model_name:String = null
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
                var description:String = null
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
                var reaction_number = List[String]()
                var reaction_name = List[String]()
                var reaction_reversible = List[String]()
                var reaction_fast = List[String]()
                var reaction_list = List[Unit]()
                var product_name = List[String]()
                var product_id = List[String]()
                var product_specie = List[String]()
                var product_stoic = List[String]()
                var product_stoic_math = List[String]()
                var product_note = List[String]()
                var modifier_name = List[String]()
                var modifier_id = List[String]()
                var modifier_specie = List[String]()
                var modifier_stoic = List[String]()
                var modifier_stoic_math = List[String]()
                var modifier_note = List[String]()
                var reaction_reactant_stoi = List[String]()
                var reaction_reactant_stoi_math = List[String]()
                var reaction_product_stoi = List[String]()
                var reaction_product_stoi_math = List[String]()
                var reaction_modifier = List[String]()
                var reaction_kinetic = List[String]()
                var reaction_kinetic_param = List[String]()
                var reactant_hash = new Hashtable[String,Reactant]()
                //var reaction_kinetic_param_group = List[List[String]]()
                var reaction_note = List[String]()
                val msgName: String = S.attr("id_msgs") openOr "messages"

                /**
                 * generates a XML representation of the SBML Model
                 * it does not include the DOCTYPE declaration or the sbml top tag
                 * Those should be added by a proper export funtion when it is created
                 * @return the XML representing the user
                 */
                def createNewModel () = {
                    model_id = model_name.replace(" ", "").toLowerCase
                    
                    if (model_id.length == 0 && model_name.length == 0) {
                        S.error("Invalid Data")
                    } else {
                        val modelSBML = {<test>This is only a test</test>}
                        val modelInitialTag = {
                            <model id={model_id} name={model_name}>
                                <notes>
                                    <body>{XML.loadString(description)}
                                    </body>
                                </notes>
                                {modelSBML.attributes}
                            </model>
                        }/*

                                {
                                    if(function_def_id != null) {
                                        <listOfFunctionDefinitions>
                                            {for(val i <- 0 to (function_def_id.length-1)) yield{
                                                    <functionDefinition name={function_def_name(i)} id={function_def_id(i)}>
                                                        <math xmlns="http://www.w3.org/1998/Math/MathML">{function_def_math(i)}</math>
                                                        <notes>{function_def_note(i)}</notes>
                                                    </functionDefinition>
                                                }
                                            }
                                        </listOfFunctionDefinitions>} else scala.xml.Null}
                                {if(compartment_id != null) {
                                        <listOfCompartments>
                                            {for(val i <- 0 to (compartment_id.length-1)) yield{
                                                    <compartment outside={compartment_outside(i)} spacialDimensions={compartment_sd(i)} size={compartment_size(i)}
                                                        name={compartment_name(i)} id={compartment_id(i)} constant={compartment_constant(i)}>
                                                        <notes>{compartment_note(i)}</notes>
                                                    </compartment>
                                                }
                                            }
                                        </listOfCompartments>} else scala.xml.Null}
                                {if(species_id != null) {
                                        <listOfSpecies>
                                            {for(i <- 0 to species_id.length-1) yield{
                                                    <species id={species_id(i)} name={species_name(i)} compartment={species_compartment(i)}
                                                        initialAmount={species_init_amount(i)} initialConcentration={species_init_concent(i)}
                                                        boundaryCondition={species_bound_cond(i)} constant={species_constant(i)}>
                                                        <notes>{species_note(i)}</notes>
                                                    </species>
                                        
                                        
                                                }
                                            }
                                        </listOfSpecies>} else scala.xml.Null}
                                {if(parameter_id != null) {
                                        <listOfParameters>
                                            {for(i <- 0 to parameter_id.length-1) yield{
                                                    <parameter id={parameter_id(i)} name={parameter_name(i)} value={parameter_value(i)}
                                                        constant={parameter_constant(i)}>
                                                        <notes>{parameter_note(i)}</notes>
                                                    </parameter>
                                                }}
                                        </listOfParameters>} else scala.xml.Null}
                                {if(constraint_math != null) {
                                        <listOfConstraints>
                                            {for(i <- 0 to constraint_math.length-1) yield{
                                                    <constraint><math xmlns="http://www.w3.org/1998/Math/MathML">{constraint_math(i)}
                                                                </math>
                                                        <message>{constraint_message(i)}</message>
                                                        <notes>{constraint_note(i)}</notes>
                                                    </constraint>
                                                }}
                                        </listOfConstraints>} else scala.xml.Null}
                                {if(reaction_id.length != 0){
                                        <listOfReactions>
                                            {for(i <- 0 to reaction_id.length-1) yield{
                                                    <reaction reversible={reaction_reversible(i)} name={reaction_name(i)} id={reaction_id(i)} fast={reaction_fast(i)}>
                                                        <notes>{reaction_note(i)}</notes>
                                                        <listOfReactants>aqui
                                                            {
                                                                var count = 0
                                                                    var va = reactant_hash.elements
                                                                    while(va.hasMoreElements){
                                                                        var elemm = va.nextElement
                                                                        count = count+1
                                                                        var vvv = ((i+1) + "_" + count).toString
                                                                        Console.println("Valor i=" + vvv + " Contem=" + reactant_hash.containsKey(vvv) + " o que tem-" + elemm)
                                                                        if(reactant_hash.containsKey(vvv)){
                                                                            Console.println("Aqui "+elemm.get_reactant_id+" "+elemm.get_reactant_name)
                                                                            /*<speciesReference id={elements.get_reactant_id} name={elements.get_reactant_name} species={elements.get_reactant_specie} stoichiometry={elements.get_reactant_stoic}>
                                                                             <notes>{elements.get_reactant_note}</notes>
                                                                             {if(elements.get_reactant_stoic_math.length != 0){
                                                                             <stoichiometryMath>
                                                                             {elements.get_reactant_stoic_math}
                                                                             </stoichiometryMath>
                                                                             } else scala.xml.Null}
                                                                             </speciesReference>*/
                                                                        } else scala.xml.Null
                                                                    }
                                                                } e aqui!
                                                        </listOfReactants>
                                                    </reaction>
                                                }
                                            }
                                        </listOfReactions>} else scala.xml.Null}
                            </model>}*/
                        Console.println(modelInitialTag)
                        XML.save("file.xml", modelInitialTag)
                    }
                }
                val listaNova = List("true","false")
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
                                           {SHtml.text("", v => {function_def_name = v :: function_def_name
                                                                 function_def_id = v.replace(" ", "").toLowerCase :: function_def_id},("id", "functionDefinitionName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                           <li>Function Definition Math:
                                                <a value="Help on Mathematical Formulas" href="" onclick="window.open('../help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">
                                                    <img src="../classpath/images/question.png" width="20px" height="20px" /></a><br />
                                                {SHtml.textarea("amath [insert mathematical formula here] endamath", v => function_def_math = v :: function_def_math,("id", "functionDefinitionMath"), ("rows","3"), ("cols", "120"), ("maxlength", "20000"))}</li>
                                           <li>Function Definition Note:<br />
                                                {SHtml.textarea("", v => function_def_note = v :: function_def_note,("id", "functionDefinitionNote"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))
                                                }</li>)}</ul><hr /><br />).toJs) &
                            (ElemById("function_def") ~> JsFunc("appendChild", Call("funcDef", "")))
                        }),
                     "buttonCompartment" -> SHtml.ajaxButton(Text("Add Compartment"), () => {
                            JsCrVar("comp", Jx(<ul>
                                               {
                                        Jx(<li>Compartment Name: <span id="required_field">*</span>&nbsp;
                                           {SHtml.text("", v => {compartment_name = v :: compartment_name
                                                                 compartment_id = v.replace(" ", "") :: compartment_id},("id", "compartmentName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                           <li>Compartment Spatial Dimension:&nbsp;
                                                {SHtml.select((0 to 3).toList.reverse.map(v => (v.toString, v.toString)), Empty, selectValue _)}</li>
                                           <li>Compartment Size: &nbsp;
                                                {SHtml.text("", v => compartment_size = v :: compartment_size,("id", "compartmentSize"), ("size", "10"), ("maxlength", "100"))}</li>
                                           <li>Compartment Outside: &nbsp;
                                                {SHtml.text("", v => compartment_outside = v :: compartment_outside,("id", "compartmentOutside"), ("size", "10"), ("maxlength", "100"))}</li>
                                           <li>Compartment Constant: &nbsp;<i>(default="true")</i> &nbsp;
                                                {SHtml.select(listaNova.map(v => (v, v)), Empty, selectValueC _)}</li>
                                           <li>Compartment Note:<br />
                                                {SHtml.textarea("", n => compartment_note = n :: compartment_note,("id", "compartmentNoteArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)
                                    }
                                               </ul> <hr /><br />).toJs) &
                            (ElemById("compart") ~> JsFunc("appendChild", Call("comp", "")))
                        }),
                     "buttonSpecies" -> SHtml.ajaxButton(Text("Add Species"), () => {
                            JsCrVar("spec", Jx(<ul>
                                               {
                                        Jx(<li>Species Name <span id="required_field">*</span>: &nbsp;
                                           {SHtml.text("", v => {species_name = v :: species_name
                                                                 species_id = v.replace(" ", "") :: species_id},("id", "speciesName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                           <li>Species Compartment <span id="required_field">*</span>: &nbsp;
                                                {SHtml.text("", v => species_compartment = v :: species_compartment,("id", "speciesCompartment"), ("size", "40"), ("maxlength", "1000"))}</li>
                                           <li>Species Initial Amount: &nbsp;
                                                {SHtml.text("", v => species_init_amount = v :: species_init_amount,("id", "speciesInitAmount"), ("size", "10"), ("maxlength", "1000"))}</li>
                                           <li>Species Initial Concentration: &nbsp;
                                                {SHtml.text("", v => species_init_concent = v :: species_init_concent,("id", "speciesInitConc"), ("size", "10"), ("maxlength", "1000"))}</li>
                                           <li>Species Boundary Condition: &nbsp;<i>(default="false")</i> &nbsp;
                                                {SHtml.select(listaNova.reverse.map(v => (v, v)), Empty, setSpecieBC _)}</li>
                                           <li>Species Constant: &nbsp;<i>(default="false")</i> &nbsp;
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
                                        Jx(<li>Parameter Name <span id="required_field">*</span>: &nbsp;
                                           {SHtml.text("", v => {parameter_name = v :: parameter_name
                                                                 parameter_id = v.replace(" ", "") :: parameter_id},("id", "parameterName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                           <li>Parameter Value : &nbsp;
                                                {SHtml.text("", v => parameter_value = v :: parameter_value,("id", "parameterValue"), ("size", "10"), ("maxlength", "100"))}</li>
                                           <li>Parameter Constant: &nbsp;<i>(default="true")</i> &nbsp;
                                                {SHtml.select(listaNova.map(v => (v, v)), Empty, setParameterC _)}</li>
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
                                           <a value="Help on Mathematical Formulas" href="" onclick="window.open('../help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">
                                                <img src="../classpath/images/question.png" width="20px" height="20px" /></a><br />
                                           {SHtml.textarea("amath [insert mathematical formula here] endamath", v => constraint_math = v :: constraint_math,("id", "constraintMath"), ("rows","3"), ("cols", "120"), ("maxlength", "10000"))}</li>
                                           <li>Constraint Message:<br />
                                                {SHtml.textarea("", v => constraint_message = v :: constraint_message,("id", "constraintMessage"), ("rows","3"), ("cols", "120"), ("maxlength", "10000"))}</li>
                                           <li>Constraint Note:<br />
                                                {SHtml.textarea("", n => constraint_note = n :: constraint_note,("id", "constraintNoteArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)
                                    }
                                                 </ul><hr /><br />).toJs) &
                            (ElemById("const") ~> JsFunc("appendChild", Call("constr", "")))
                        }),
                     "buttonReactions" -> SHtml.ajaxButton(Text("Add Reaction"), () => {
                            count = count+1
                            reactant_ul = "reactant_"+count+"_"
                            product_ul = "product_"+count+"_"
                            modifier_ul = "modifier_"+count+"_"
        
                            JsCrVar("reactionFunc", Jx(<ul>
                                                       {
                                        Jx(<li>Reaction Name <span id="required_field">*</span>:
                                           {SHtml.text("", v => {reaction_name = v :: reaction_name
                                                                 reaction_id = v.replace(" ", "") :: reaction_id},("id", "reactionName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                           <li>Reaction Reversible:  <i>(default="true")</i>
                                                {SHtml.select(listaNova.map(v => (v, v)), Empty, setReactionR _)}</li>
                                           <li>Reaction Fast:  <i>(default="false")</i>
                                                {SHtml.select(listaNova.reverse.map(v => (v, v)), Empty, setReactionF _)}</li>
                                           <ul id={reactant_ul}></ul>
                                           <li>{SHtml.ajaxButton(Text("Add Reactant"), () => {
                                                            reactant_count = reactant_count+1
                                                            reactant_key = count+"_"+reactant_count
                                                            var reactant = new Reactant();
                                                            JsCrVar("reactionNewReactant", Jx(<hr /><ul id="reactantStyle">{
                                                                        Jx(<li><font style="font-weight:bold; font-size:110%;">New Reactant</font>
                                                                           </li><br /><li>Reactant Name <span id="required_field">*</span>:
                                                                           {SHtml.text("", v => {reactant.set_reactant_name(v)
                                                                                                 reactant.set_reactant_id(v.replace(" ",""))
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
                                                            product_key = count+"_"+product_count
                                                            var product = new Product();
                                                            JsCrVar("reactionNewProduct", Jx(<hr /><ul id="productStyle">{
                                                                        Jx(<li><font style="font-weight:bold; font-size:110%;">New Product</font>
                                                                           </li><br /><li>Product Name <span id="required_field">*</span>:
                                                                           {SHtml.text("", v => {product.set_product_name(v)
                                                                                                 product.set_product_id(v.replace(" ",""))
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
                                                                                        },("id", "productNote"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)}<br /></ul>
                                                                ).toJs) &
                                                            (ElemById(product_ul) ~> (JsFunc("appendChild", Call("reactionNewProduct", ""))))})}
                                           </li>
                                           <ul id={modifier_ul}></ul>
                                           <li>{SHtml.ajaxButton(Text("Add Modifier"), () => {
                                                            modifier_count = modifier_count+1
                                                            modifier_key = count+"_"+modifier_count
                                                            var modifier = new Modifier();
                                                            JsCrVar("reactionNewModifier", Jx(<hr /><ul id="modifierStyle">{
                                                                        Jx(<li><font style="font-weight:bold; font-size:110%;">New Modifier</font>
                                                                           </li><br /><li>Modifier Name <span id="required_field">*</span>:
                                                                           {SHtml.text("", v => {modifier.set_modifier_name(v)
                                                                                                 modifier.set_modifier_id(v.replace(" ",""))
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
                                                                                        },("id", "productNote"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)}<br /></ul>
                                                                ).toJs) &
                                                            (ElemById(modifier_ul) ~> (JsFunc("appendChild", Call("reactionNewModifier", ""))))})}
                                           </li>
                                           <li>Reaction Note:<br />
                                                {SHtml.textarea("", n => reaction_note = n :: reaction_note,("id", "reactionArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>
                                        )}</ul><hr />).toJs) &
                            (ElemById("react") ~> JsFunc("appendChild", Call("reactionFunc", "")))
                        }),
                     "save" -> SHtml.submit("Save Model", createNewModel,("id","buttonSave")))
            }
        case _ => Text("")
    }
}
