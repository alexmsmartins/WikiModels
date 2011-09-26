package pt.cnbc.wikimodels.snippet

import net.liftweb.util.Helpers._
import net.liftweb.sitemap.Loc.If
import scala.xml.{ XML, Elem, Group, Node, NodeSeq, Null, Text, TopScope }
import net.liftweb.http.S
import java.net.URI
import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.JsCmds.JsHideId
import net.liftweb.http.js.Jx
import net.liftweb.http.js.JE._
import net.liftweb.http.js.jquery._
import net.liftweb.http.js.jquery.JqJsCmds._
import java.util.TreeMap
import java.util.LinkedList
import java.util.Enumeration
import net.liftweb.common._
import pt.cnbc.wikimodels.model._

import pt.cnbc.wikimodels.rest.client.RestfulAccess
import alexmsmartins.log.LoggerWrapper


class CreateModel extends LoggerWrapper {
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
                var constraint_id = new LinkedList[String]()
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
                val msgParamName: String = S.attr("id_msgs") openOr "paramMessage"
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

                    if(reaction_id.size > 0 && reaction_name.size > 0){
                        for(i <- 0 to reaction_id.size){
                            if(reaction_id.get(i).equals("") && !reaction_name.get(i).equals("")){
                                reaction_id.add(i, reaction_name.get(i))
                            }
                        }
                    }
                    debug("aqui------>"+XML.loadString(description))
                    model_id = model_name.replace(" ", "").toLowerCase
                    if (model_id.length == 0 && model_name.length == 0) {
                        S.error("Invalid Content in the model")
                    } else {
                        val modelSBML = {
                            <model id={model_id} name={model_name} metaid="metaid_0000001">
                                <notes>
                                    <body>{XML.loadString(description.toString)}
                                    </body>
                                </notes>
                                {
                                    if(function_def_id.peekFirst != ""){
                                        <listOfFunctionDefinitions>
                                            {
                                                for( i <- 0 to (function_def_id.size-1)) yield{
                                                    <functionDefinition name={function_def_name.get(i)} id={function_def_id.get(i)} metaid="metaid_0000001">
                                                        {
                                                            if((function_def_math.peek != "") && (function_def_math.get(i).contains("<math"))){
                                                                    XML.loadString(function_def_math.get(i).toString)
                                                            } else scala.xml.Null
                                                        }
                                                        <notes>{function_def_note.get(i)}</notes>
                                                    </functionDefinition>
                                                }
                                            }
                                        </listOfFunctionDefinitions>
                                    } else scala.xml.Null 
                                }
                                {
                                    if(compartment_id.peekFirst != ""){
                                        <listOfCompartments>
                                            {for( i <- 0 to (compartment_id.size-1)) yield{
                                                    <compartment outside={compartment_outside.get(i)} spatialDimensions={compartment_sd.get(i)} size={compartment_size.get(i)}
                                                        name={compartment_name.get(i)} id={compartment_id.get(i)} constant={compartment_constant.get(i)} metaid="metaid_0000001">
                                                        <notes>{compartment_note.get(i)}</notes>
                                                    </compartment>
                                                }
                                            }
                                        </listOfCompartments>
                                    } else scala.xml.Null
                                }
                                {
                                    if(species_id.peekFirst != ""){
                                        <listOfSpecies>
                                            {
                                                for(i <- 0 to species_id.size-1) yield{
                                                    <species id={species_id.get(i)} name={species_name.get(i)} compartment={species_compartment.get(i)}
                                                        initialAmount={species_init_amount.get(i)} initialConcentration={species_init_concent.get(i)}
                                                        boundaryCondition={species_bound_cond.get(i)} constant={species_constant.get(i)} metaid="metaid_0000001">
                                                        <notes>{species_note.get(i)}</notes>
                                                    </species>
                                                }
                                            }
                                        </listOfSpecies>
                                    } else scala.xml.Null
                                }
                                {
                                    if(parameter_id.peekFirst != ""){
                                        <listOfParameters>
                                            {
                                                for(i <- 0 to parameter_id.size-1) yield{
                                                    <parameter id={parameter_id.get(i)} name={parameter_name.get(i)} value={parameter_value.get(i)}
                                                        constant={parameter_constant.get(i)} metaid="metaid_0000001">
                                                        <notes>{parameter_note.get(i)}</notes>
                                                    </parameter>
                                                }
                                            }
                                        </listOfParameters>
                                    } else scala.xml.Null
                                }
                                {
                                    if(constraint_math.peekFirst != ""){
                                        <listOfConstraints>
                                            {
                                                
                                                for(i <- 0 to constraint_math.size-1) yield{
                                                    if((constraint_math.peek != "") && (constraint_math.get(i).contains("<math"))){
                                                        <constraint id={constraint_id.get(i)} metaid="metaid_0000001">{XML.loadString(constraint_math.get(i).toString)}
                                                                                        
                                                            <message>{constraint_message.get(i)}</message>
                                                            <notes>{constraint_note.get(i)}</notes>
                                                        </constraint>
                                                    } else scala.xml.Null
                                                }
                                                    
                                            }
                                        </listOfConstraints>
                                    } else scala.xml.Null
                                }
                                {
                                    
                                    if(reaction_id.peekFirst != ""){
                                        <listOfReactions>{
                                                for(i <- 1 to reaction_id.size) yield{
                                                    <reaction reversible={reaction_reversible.get(i-1)} name={reaction_name.get(i-1)} id={reaction_id.get(i-1)} fast={reaction_fast.get(i-1)} metaid="metaid_0000001">
                                                        <notes>{reaction_note.get(i-1)}</notes>
                                                        {var o1 = reactant_hash.entrySet
                                                         var o2 = o1.iterator
                                                         for(k <- 1 to reactant_hash.size) yield{
                                                                //for ( l <- o2.hasNext) yield {
                                                                    var o3 = o2.next
                                                                    var id1 = o3.getValue
                                                                    //debug("VALORES----------"+id1.get_reactant_id)
                                                                    if(id1.get_reactant_id != ""){

                                                                        <listOfReactants>{
                                                                    
                                                                                contadorReactant = contadorReactant+1
                                                                                var ent = reactant_hash.entrySet
                                                                                var enum = ent.iterator
                                                                                for(k <- 1 to reactant_hash.size) yield{
                                                                                    //for ( l <- enum.hasNext) yield {
                                                                                        var entry = enum.next
                                                                                        var teste = "reactant_"+i+"_"+k
                                                                                        if((entry.getKey.equals(teste)) && (contadorReactant == i)){
                                                                                            var valor = entry.getValue
                                                                                            <speciesReference id={valor.get_reactant_id} name={valor.get_reactant_name} species={valor.get_reactant_specie} stoichiometry={valor.get_reactant_stoic} metaid="metaid_0000001">
                                                                                                <notes>{valor.get_reactant_note}</notes>
                                                                                                {
                                                                                                    if((valor.get_reactant_stoic_math.length > 0) && (valor.get_reactant_stoic_math.contains("<math"))){
                                                                                                        <stoichiometryMath>
                                                                                                            {XML.loadString(valor.get_reactant_stoic_math.toString)}
                                                                                                        </stoichiometryMath>
                                                                                                    } else scala.xml.Null}
                                                                                            </speciesReference>
                                                                                        } else scala.xml.Null

                                                                                }
                                                                            }
                                                                        </listOfReactants>
                                                                    } else scala.xml.Null}}
                                                        {var o1 = product_hash.entrySet
                                                         var o2 = o1.iterator
                                                         for(k <- 1 to product_hash.size) yield{
                                                                //for ( l <- o2.hasNext) yield {
                                                                    var o3 = o2.next
                                                                    var id1 = o3.getValue
                                                                    //debug("VALORES----------"+id1.get_reactant_id)
                                                                    if(id1.get_product_id != ""){
                                                                        <listOfProducts>{
                                                                    
                                                                                contadorProduct = contadorProduct+1
                                                                                var ent = product_hash.entrySet
                                                                                var enum = ent.iterator
                                                                                for(k <- 1 to product_hash.size) yield{
                                                                                    //for ( l <- enum.hasNext) yield {
                                                                                        var entry = enum.next
                                                                                        var teste = "product_"+i+"_"+k
                                                                                        if((entry.getKey.equals(teste)) && (contadorProduct == i)){
                                                                                            var valor = entry.getValue
                                                                                            <speciesReference id={valor.get_product_name} name={valor.get_product_name} species={valor.get_product_specie} stoichiometry={valor.get_product_stoic} metaid="metaid_0000001">
                                                                                                <notes>{valor.get_product_note}</notes>
                                                                                                {
                                                                                                    if((valor.get_product_stoic_math.length > 0) && (valor.get_product_stoic_math.contains("<math"))){
                                                                                                        <stoichiometryMath>
                                                                                                            {XML.loadString(valor.get_product_stoic_math.toString)}
                                                                                                        </stoichiometryMath>
                                                                                                    } else scala.xml.Null}
                                                                                            </speciesReference>
                                                                                        } else scala.xml.Null

                                                                                }
                                                                            }
                                                                        </listOfProducts>
                                                                    } else scala.xml.Null}}
                                                        {var o1 = modifier_hash.entrySet
                                                         var o2 = o1.iterator
                                                         for(k <- 1 to modifier_hash.size) yield{
                                                                //for ( l <- o2.hasNext) yield {
                                                                    var o3 = o2.next
                                                                    var id1 = o3.getValue
                                                                    //debug("VALORES----------"+id1.get_reactant_id)
                                                                    if(id1.get_modifier_id != ""){
                                                                        <listOfModifiers>{
                                                                                contadorModifier = contadorModifier+1
                                                                                var ent = modifier_hash.entrySet
                                                                                var enum = ent.iterator
                                                                                for(k <- 1 to modifier_hash.size) yield{
                                                                                    //for ( l <- enum.hasNext) yield {
                                                                                        var entry = enum.next
                                                                                        var teste = "modifier_"+i+"_"+k
                                                                                        if((entry.getKey.equals(teste)) && (contadorModifier == i)){
                                                                                            var valor = entry.getValue
                                                                                            <speciesReference id={valor.get_modifier_id} name={valor.get_modifier_name} species={valor.get_modifier_specie} stoichiometry={valor.get_modifier_stoic} metaid="metaid_0000001">
                                                                                                <notes>{valor.get_modifier_note}</notes>
                                                                                                {
                                                                                                    if((valor.get_modifier_stoic_math.length > 0) && (valor.get_modifier_stoic_math.contains("<math"))){
                                                                                                        <stoichiometryMath>
                                                                                                            {XML.loadString(valor.get_modifier_stoic_math.toString)}
                                                                                                        </stoichiometryMath>
                                                                                                    } else scala.xml.Null}
                                                                                            </speciesReference>
                                                                                        } else scala.xml.Null

                                                                                }
                                                                            }
                                                                        </listOfModifiers>
                                                                    } else scala.xml.Null}}
                                                        <kineticLaw>{
                                                                for(k <- 0 to reaction_kinetic.size-1) yield{
                                                                    if(((reaction_kinetic.size > 0)) && (reaction_kinetic.get(k).contains("<math"))){
                                                                        {XML.loadString(reaction_kinetic.get(k).toString)}
                                                                
                                                                    } else scala.xml.Null}}
                                                            {
                                                                if(!parameter_hash.isEmpty){
                                                                    <listOfParameters>{
                                                                            contadorParameter = contadorParameter+1
                                                                            var ent = parameter_hash.entrySet
                                                                            var enum = ent.iterator
                                                                            for(k <- 1 to parameter_hash.size) yield{
                                                                                //for ( l <- enum.hasNext) yield {
                                                                                    var entry = enum.next
                                                                                    var teste = "parameter_"+i+"_"+k
                                                                                    if((entry.getKey.equals(teste)) && (contadorParameter == i)){
                                                                                        var valor = entry.getValue
                                                                                        <parameter id={valor.get_parameter_id} name={valor.get_parameter_name} value={valor.get_parameter_value}
                                                                                            constant={valor.get_parameter_constant} metaid="metaid_0000001">
                                                                                            <notes>{valor.get_parameter_note}</notes>
                                                                                        </parameter>
                                                                                    } else scala.xml.Null

                                                                            }
                                                                        }
                                                                    </listOfParameters>
                                                                } else scala.xml.Null
                                                            }</kineticLaw>
                                                    </reaction>}}
                                        </listOfReactions>
                                    } else scala.xml.Null
                                }
                            </model>
                        }
                        debug("Creating model {}", modelSBML)

                        var restful:RestfulAccess = User.restfulConnection
                        try {
                            val uriModel:URI = restful.postRequest("/model", modelSBML)
                            if((restful.getStatusCode >= 200) || (restful.getStatusCode < 300)){
                                var link = uriModel.getPath
                                var loc = link.indexOf("/metaid")
                                var final_link = link.substring(loc)
                                S.redirectTo("/model"+final_link)
                            } else {
                                S.error("Error creating model!")
                            }
                        } catch {
                            case e: pt.cnbc.wikimodels.exceptions.BadFormatException => S.error("Bad format error in creating the model on the server side!")
                            case nullPoint: NullPointerException => S.error("Null - Error in creating the model on the server side!")
                        }
                        
                        
                        //XML.save("file.xml", modelSBML)
                        
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
                                                                   SHtml.text(Text(ref.replace(" ", "").toLowerCase).toString, refer2 => model_id = refer2, ("id", "model_id"), ("name", "model_id"), ("size", "40"))),
                                                              300000 seconds, 1 second)}, ("id", "name_model"), ("size", "40"), ("maxlength", "1000"))
                }

                def createNameAndIdCompartment(nameml: NodeSeq) = {
                    SHtml.ajaxText("", ref => { parameter_name.add(ref);
                                               DisplayMessage(msgName,
                                                              bind("text", nameml, "param_id" ->
                                                                   SHtml.text(Text(ref.replace(" ", "").toLowerCase).toString, refer2 => parameter_id.add(refer2), ("id", "model_id"), ("name", "model_id"), ("size", "40"))),
                                                              300000 seconds, 1 second)}, ("id", "parameter_name"), ("size", "40"), ("maxlength", "1000"))
                }

                description = "<div>"

                bind("createDescription", xhtml,

                     "name" -> createNameAndId _,
    
                     "description" -> SHtml.textarea("", vv => {description = description + vv + "</div>"}, ("id", "descriptionArea"), ("maxlength", "20000")),

                     "buttonReactions" -> SHtml.ajaxButton(Text("Add Reaction"), () => {
                            count = count+1
                            reactant_ul = "reactant_"+count+"_"
                            product_ul = "product_"+count+"_"
                            modifier_ul = "modifier_"+count+"_"
                            parameter_ul = "parameter_"+count+"_"

                            JsCrVar("reactionFunc", Jx(
                                    <ul id="lateralSlice">
                                        {
                                            Jx(<li>Reaction ID <span id="required_field">*</span>:
                                               {SHtml.text("", v => {reaction_id.add(v.replace(" ", "").toLowerCase)},("id", "reactionID"), ("size", "40"), ("maxlength", "10000"))}</li>
                                               <li>Reaction Name <span id="required_field">*</span>:
                                                    {SHtml.text("", v => {reaction_name.add(v)},("id", "reactionName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                               <li>Reaction Reversible:  <i>(default="false")</i>
                                                    {SHtml.select(listaNova.map(v => (v, v)), Empty, setReactionR _)}</li>
                                               <li>Reaction Fast:  <i>(default="false")</i>
                                                    {SHtml.select(listaNova.reverse.map(v => (v, v)), Empty, setReactionF _)}</li>
                                               <br />
                                               <ul id={reactant_ul} style="list-style:none;"></ul>
                                               <li>{SHtml.ajaxButton(Text("Add Reactant"), () => {
                                                                reactant_count = reactant_count+1
                                                                reactant_key = reactant_ul+reactant_count
                                                                var reactant = new Reactant();
                                                                JsCrVar("reactionNewReactant", Jx(<hr /><ul id="reactantStyle" style="list-style:none;">{
                                                                            Jx(<li><font style="font-weight:bold; font-size:110%;">New Reactant</font></li><br />
                                                                               <li>Reactant ID <span id="required_field">*</span>:
                                                                                    {SHtml.text("", v => {reactant.set_reactant_id(v.replace(" ","").toLowerCase)},("id", "reactantID"), ("size", "40"), ("maxlength", "10000"))}</li>
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
                                                                                    }</li>)}<br />{reactant_hash.put(reactant_key, reactant)}</ul>
                                                                    ).toJs) &
                                                                (ElemById(reactant_ul) ~> (JsFunc("appendChild", Call("reactionNewReactant", ""))))})}
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
                                            )}
                                    </ul>
                                    <hr /><br /><br />).toJs) &
                            (ElemById("react") ~> JsFunc("appendChild", Call("reactionFunc", "")))
                        }),
    
                     "buttonFunDef" -> SHtml.ajaxButton(Text("Add Function Definition"), () => {

                            JsCrVar("funcDef", Jx(
                                    <ul style="list-style:none;">
                                        {
                                            Jx(<li>Function Definition ID <span id="required_field">*</span>:
                                               {SHtml.text("", v => {function_def_id.add(v.replace(" ", "").toLowerCase)},("id", "functionDefinitionID"), ("size", "40"), ("maxlength", "10000"))}</li>
                                               <li>Function Definition Name <span id="required_field">*</span>:
                                                    {SHtml.text("", v => {function_def_name.add(v)},("id", "functionDefinitionName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                               <li>Function Definition Math: <i><span id="required_field">(Format: Content MathML [Mathematical Markup Language])</span></i><img src="../classpath/images/question.png" width="20px" height="20px" /><br />
                                                    <!--<a value="Help on Mathematical Formulas" href="" onclick="window.open('../help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">Help on Mathematical Formula for ASCIIMathML-->
                                                    <!--</a>-->
                                                    <br /><a style="color:blue" name="Copy and paste the Content MathML code to the box down" href="" onclick="window.open('http://cnx.org/math-editor/popup','Help','width=900,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=200,top=200,screenX=200,screenY=300');"><b>MathML Editor</b> (only for Mozilla Firefox users)</a><br /><br />
                                                    {SHtml.textarea("", v => function_def_math.add(v),("id", "functionDefinitionMath"), ("rows","10"), ("cols", "120"), ("maxlength", "200000"))}</li>
                                               <li>Function Definition Note:<br />
                                                    {SHtml.textarea("", v => function_def_note.add(v),("id", "functionDefinitionNote"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))
                                                    }</li>)
                                        }</ul><hr /><br /><br />
                                    /*<div onclick={Call("deleteFunDef", "")}>aqui</div>*/
                                ).toJs) &
                            (ElemById("function_def") ~> JsFunc("appendChild", Call("funcDef", "")))}),
                     "buttonCompartment" -> SHtml.ajaxButton(Text("Add Compartment"), () => {
                            JsCrVar("comp", Jx(<ul style="list-style:none;">
                                               {
                                        Jx(<li>Compartment ID <span id="required_field">*</span>:
                                           {SHtml.text("", v => {compartment_id.add(v.replace(" ", "").toLowerCase)},("id", "compartmentID"), ("size", "40"), ("maxlength", "10000"))}</li>
                                           <li>Compartment Name: <span id="required_field">*</span>&nbsp;
                                                {SHtml.text("", v => {compartment_name.add(v)},("id", "compartmentName"), ("size", "40"), ("maxlength", "10000"))}</li>
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
                                               </ul> <hr /><br /><br />).toJs) &
                            (ElemById("compart") ~> JsFunc("appendChild", Call("comp", "")))
                        }),
                     "buttonSpecies" -> SHtml.ajaxButton(Text("Add Species"), () => {
                            JsCrVar("spec", Jx(<ul style="list-style:none;">
                                               {
                                        Jx(<li>Species ID <span id="required_field">*</span>:
                                           {SHtml.text("", v => {species_id.add(v.replace(" ", "").toLowerCase)},("id", "speciesID"), ("size", "40"), ("maxlength", "10000"))}</li>
                                           <li>Species Name <span id="required_field">*</span>: &nbsp;
                                                {SHtml.text("", v => {species_name.add(v)},("id", "speciesName"), ("size", "40"), ("maxlength", "10000"))}</li>
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
                                               </ul><hr /><br /><br />).toJs) &
                            (ElemById("specie") ~> JsFunc("appendChild", Call("spec", "")))
                        }),
                     "buttonParam" -> SHtml.ajaxButton(Text("Add Parameter"), () => {
                            JsCrVar("param", Jx(<ul style="list-style:none;">
                                                {
                                        Jx( <li>Parameter ID <span id="required_field">*</span>:
                                           {SHtml.text("", v => {parameter_id.add(v.replace(" ", "").toLowerCase)},("id", "parameterID"), ("size", "40"), ("maxlength", "10000"))}</li>
                                           <li>Parameter Name <span id="required_field">*</span>: &nbsp;
                                                {SHtml.text("", v => {parameter_name.add(v)},("id", "parameterName"), ("size", "40"), ("maxlength", "10000"))}</li>
                                           
                                           <li>Parameter Value : &nbsp;
                                                {SHtml.text("", v => parameter_value.add(v),("id", "parameterValue"), ("size", "10"), ("maxlength", "100"))}</li>
                                           <li>Parameter Constant: &nbsp;<i>(default="true")</i> &nbsp;
                                                {SHtml.select(listaNova.map(v => (v, v)), Empty, setParameterC _)}</li>
                                           <li>Parameter Note:<br />
                                                {SHtml.textarea("", n => parameter_note.add(n),("id", "parameterNoteArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)
                                    }
                                                </ul><hr /><br /><br />).toJs) &
                            (ElemById("paramet") ~> JsFunc("appendChild", Call("param", "")))
                        }),
                     /*<li>Parameter Name: <span id="required_field">*</span> &nbsp;{SHtml.ajaxText("", ref => { parameter_name.add(ref);
                                               DisplayMessage(msgParamName,
                                                              bind("text", xhtml, "param_id" ->
                                                                   SHtml.text(Text(ref.replace(" ", "").toLowerCase).toString, refer2 => parameter_id.add(refer2), ("id", "model_id"), ("name", "model_id"), ("size", "40"))),
                                                              300000 seconds, 1 second)}, ("id", "parameter_name"), ("size", "40"), ("maxlength", "1000"))}</li>*/
                     "buttonConstraint" -> SHtml.ajaxButton(Text("Add Constraint"), () => {
                            JsCrVar("constr", Jx(<ul style="list-style:none;">
                                                 {
                                        Jx(<li>Constraint ID <span id="required_field">*</span>:
                                           {SHtml.text("", v => {constraint_id.add(v.replace(" ", "").toLowerCase)},("id", "constraintID"), ("size", "40"), ("maxlength", "10000"))}</li>
                                            <li>Constraint Math: <i><span id="required_field">(Format: Content MathML [Mathematical Markup Language])</span></i><img src="../classpath/images/question.png" width="20px" height="20px" /><br />
                                           <!--<a value="Help on Mathematical Formulas" href="" onclick="window.open('../help/helpMath','Help','width=500,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=400,top=300,screenX=400,screenY=300');">Help on Mathematical Formula for ASCIIMathML-->
                                           <!--</a>-->
                                           <br /><a style="color:blue" name="Copy and paste the Content MathML code to the box down" href="" onclick="window.open('http://cnx.org/math-editor/popup','Help','width=900,height=300,resizable=no,toolbar=no,location=no,directories=no,status=no,menubar=no,left=200,top=200,screenX=200,screenY=300');"><b>MathML Editor</b> (only for Mozilla Firefox users)</a><br /><br />
                                           {SHtml.textarea("", v => constraint_math.add(v),("id", "constraintMath"), ("rows","10"), ("cols", "120"), ("maxlength", "10000"))}</li>
                                           <li>Constraint Message:<br />
                                                {SHtml.textarea("", v => constraint_message.add(v),("id", "constraintMessage"), ("rows","3"), ("cols", "120"), ("maxlength", "10000"))}</li>
                                           <li>Constraint Note:<br />
                                                {SHtml.textarea("", n => constraint_note.add(n),("id", "constraintNoteArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000"))}</li>)
                                    }
                                                 </ul><hr /><br /><br />).toJs) &
                            (ElemById("const") ~> JsFunc("appendChild", Call("constr", "")))
                        }),
                     "save" -> SHtml.submit("Save Model", createNewModel,("id","buttonSave"),("onclick" -> {
                                JsIf(JsEq(ValById("name_model"), ""), Alert("You must provide a name to the model!") & JsReturn(false)).toJsCmd
                                /*JsIf(!ValById("reactantStoicMath").contains("<math"), Alert("Error in the mathematical formula in: 'Reactant Stoichiometry Math'.") & JsReturn(false))
                            JsIf(!ValById("functionDefinitionMath").contains("<math"), Alert("Error in the mathematical formula in: 'Function Definition Math'.") & JsReturn(false))*/})))
            }
        case _ => Text("")
    }
}
