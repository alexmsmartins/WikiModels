/*
 * ModelRead.scala
 *
 */

package pt.cnbc.wikimodels.snippet

import net.liftweb.http._
import net.liftweb.util._
import net.liftweb.util.Helpers._
import net.liftweb.sitemap.Loc.If
import scala.xml.{ MetaData, XML, Elem, Group, Node, NodeSeq, Null, Text, TopScope, PrettyPrinter }
import scala.xml.transform._
import net.liftweb.http.S
import S._
import js._
import js.JsCmds
import js.JsCmds._
import js.Jx
import js.JE
import js.JE._
import net.liftweb.common._
import java.util.LinkedList

import pt.cnbc.wikimodels.rest.client.RestfulAccess

class ModelRead {

    def listOfModels (xhtml : NodeSeq) : NodeSeq = {
        var model = new LinkedList[String]()
        /*model.add("BIOMD0000000055.xml")
         model.add("BIOMD0000000070.xml")
         model.add("BIOMD0000000103.xml")
         model.add("BIOMD0000000117.xml")
         model.add("BIOMD0000000168.xml")
         model.add("BIOMD0000000206.xml")
         model.add("BIOMD0000000212.xml")
         model.add("BIOMD00000002122.xml")*/

        
        
        //val newModel = "file.xml"
        var count = 0
        var mod:String = ""

        val teste = S.uri
        var restful:RestfulAccess = User.restfulConnection
        
        val modelsSBML = restful.getRequest(teste)
        
        if(restful.getStatusCode == 200){
            bind("listModels", xhtml,
                 "list" -> {
                    if(!(modelsSBML \\ "models" \ "listOfModels").isEmpty){
                        for( fun <- modelsSBML \\ "models" \ "listOfModels" \ "model") yield{
                            <tbody>
                                <tr>
                                    <td title="Model Metaid"><a href={"/model/"+(fun \ "@metaid")}>{(fun \ "@metaid")}</a></td>
                                    <td title={(fun \ "@name")}><a href={"/model/"+(fun \ "@metaid")}>{(fun \ "@name")}</a></td>
                                    <td title="Author"><a href=""></a></td>
                                    <td title="Last Modification">-</td>
                                    <td title="Actions">
                                      <!-- TODO CHECK WHY window.location.href does not work
                                      <form>
                                        <input title="Export" type="submit" onclick={"window.location.href='/model/"+ (fun \ "@metaid") +"/export'"}></input>
                                      </form> -->
                                      <a href={"/model/"+(fun \ "@metaid")+"/export"}>Export</a>
                                    </td>
                                </tr>
                            </tbody>
                        }
                    } else {
                        Text("")
                    }
                }
            )
            /*var models_metaid = models_metaid :: (modelsSBML \\ "models" \ "listOfModels" \ "model" \ "@metaid").text
             var models_id = (modelsSBML \\ "models" \ "listOfModels" \ "model" \ "@id").text
             var models_name = (modelsSBML \\ "models" \ "listOfModels" \ "model" \ "@name").text
             if(models_id != null) {
             for( spec <- modelsSBML \\ "model" \ "listOfReactions" \\ "reaction" ;
             val addSpec <- spec \ "@name"  ) { countR = countR+1}
             for( func <- modelsSBML \\ "model" \ "listOfFunctionDefinitions" \\ "functionDefinition" ;
             val addSpec <- func \ "@id"  ) { countF = countF+1}
             }*/
        } else {
            S.error("SBML model doesn't exist")
            bind("listModels", xhtml,
                 "list" -> "")
        }

        
    }

    def browseModel (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
            
                var countR = 0
                var countF = 0
                /*val modelID = S.param("modelID")
                 var modelRef = "BIOMD0000000055.xml"
                 modelID match {
                 case Full("model001") => {modelRef = "BIOMD0000000055.xml"
                 Text("")}
                 case Full("model002") => {modelRef = "BIOMD0000000070.xml"
                 Text("")}
                 case Full("model003") => {modelRef = "BIOMD0000000103.xml"
                 Text("")}
                 case Full("model004") => {modelRef = "BIOMD0000000117.xml"
                 Text("")}
                 case Full("model005") => {modelRef = "BIOMD0000000168.xml"
                 Text("")}
                 case Full("model006") => {modelRef = "BIOMD0000000206.xml"
                 Text("")}
                 case Full("model007") => {modelRef = "BIOMD0000000212.xml"
                 Text("")}
                 case Full("model008") => {modelRef = "BIOMD00000002122.xml"
                 Text("")}
                 case Full("file") => {modelRef = "file.xml"
                 Text("")}
                 case _ => {
                 Text("")}

                 }*/
                val teste = S.uri
                var restful:RestfulAccess = User.restfulConnection
                //Console.println("aqui..->"+teste)
                val modelSBML = restful.getRequest(teste)
                if(restful.getStatusCode == 200){
                    Console.println("MODELO ENCONTRADO------->>>>>>'"+modelSBML+"'")

                    //normalize-space(modelSBML)
                    //XML.load(modelRef)
                    //XML.save("file.xml", modelSBML)
                    //var modelSBML1 = XML.loadFile("file.xml")

                    //val model_qq = (modelSBML \ "sbml" \ "@xmlns").text
                    val model_id= (modelSBML \\ "model" \ "@id").text
                    val model_metaid= (modelSBML \\ "model" \ "@metaid").text
                    val model_name = (modelSBML \\ "model" \ "@name").text
                    val model_notes = (modelSBML \\ "model" \ "notes").toString
                    var v1 = ""
                    if((!(modelSBML \\ "model" \ "listOfReactions" \\ "reaction").isEmpty) || !(modelSBML \\ "model" \ "listOfFunctionDefinitions" \\ "functionDefinition").isEmpty) {
                        for(spec <- modelSBML \\ "model" \ "listOfReactions" \\ "reaction" ;
                            addSpec <- spec \ "@name"  ) { countR = countR+1}
                        for(func <- modelSBML \\ "model" \ "listOfFunctionDefinitions" \\ "functionDefinition" ;
                            addSpec <- func \ "@id"  ) { countF = countF+1}
                    } else {
                        countR = 0
                        countF = 0
                    }
                    var model_notes_p:Elem = null
                    // Search for the <body> and <notes> and replaces for the <div> tag
                    if(model_notes.length >0){
                        if(model_notes.contains("body")){
                            var var2 = ""
                            var2 = model_notes.replaceAll("body", "div")
                            v1 = var2.replaceAll("notes", "div")
                        } else if(model_notes.contains("h1")){
                            var var2 = ""
                            var2 = model_notes.replaceAll("h1", "div")
                            v1 = var2.replaceAll("notes", "div")
                        } else {
                            v1 = model_notes.replaceAll("notes", "div")
                        }
                        model_notes_p = XML.loadString(v1)
                    } else {
                        model_notes_p = <div>-</div>
                    }

                    def generateTableFromXML(nodes:NodeSeq, metid:NodeSeq, columnNum:Int):Elem = {
                        var pos = 0
                        val relElems:NodeSeq = nodes
                        val metElems:NodeSeq = metid
                        val nodeNum = relElems.size

                        val rowNum = ( nodeNum + columnNum - 1 )  / columnNum
                        <table id="modelTable_Overview">
                            {
                                for(i <- 0 until rowNum) yield {
                                    <tr>{
                                            for(j <- 0 until columnNum) yield {
                                                pos+=1
                                                if(pos<=nodeNum){
                                                    if(columnNum==1){
                                                        <td class="sub_main2" >
                                                            <a href={'#'+metElems(pos-1).text}>{relElems(pos-1).text}</a></td>
                                                    } else if(columnNum==4){
                                                        <td class="sub_main2" >
                                                            <a href={'#'+metElems(pos-1).text}>{relElems(pos-1).text}</a></td>
                                                    } else {
                                                        <td class="sub_main2" >
                                                            <a href={'#'+metElems(pos-1).text}>{relElems(pos-1).text}</a></td>
                                                    }
                                                } else {
                                                    <td class="sub_main2">&nbsp;</td>
                                                }
                                            }
                                        }
                                    </tr>
                                }
                            }
                        </table>
                    }
                    def reDraw:JsCmd = {
                        Alert("Deleted")
                    }
                    def deleteFromDB(){
                        
                    }


                    bind("modelInformation", xhtml,
                         "id" -> model_id,
                         "delete" -> "", /*SHtml.ajaxButton(Text("Delete"), Call("dialog"), () => {deleteFromDB(); S.notice( "Deleted the question!"); reDraw;})*/
                         "addReaction" -> <a id="addR" href={"/model/"+model_metaid+"/add/reaction"}>Add</a>,
                         "addParameter" -> <a id="addP" href={"/model/"+model_metaid+"/add/parameter"}>Add</a>,
                         "addCompartment" -> <a id="addP" href={"/model/"+model_metaid+"/add/compartment"}>Add</a>,
                         "addSpecies" -> <a id="addP" href={"/model/"+model_metaid+"/add/species"}>Add</a>,
                         "addFunctions" -> <a id="addP" href={"/model/"+model_metaid+"/add/functiondefinition"}>Add</a>,
                         "edit" -> <a href={"/model/"+model_metaid+"/edit"}>Edit</a>,
                         "model_name" -> model_name,
                         "model_notes" -> model_notes_p,
                         "listOfReactions" -> {

                            val reac = modelSBML \\ "model" \ "listOfReactions" \\ "reaction"
                            if(reac.length > 0){
                                if((reac \\ "@name").isEmpty){
                                    generateTableFromXML(reac \\ "@id",reac \\ "@metaid",4)
                                } else {
                                    generateTableFromXML(reac \\ "@name",reac \\ "@metaid",4)
                                }
                            } else {
                                Text("")
                            }

                        },
                         "numberReactions" -> {<span>{countR}</span>},
                         "numberFunctions" -> {<span>{countF}</span>},
                         "listOfCompartments" -> {
                            val comp = modelSBML \\ "model" \ "listOfCompartments" \\ "compartment"
                            if(comp.length >0){
                                if((comp \\ "@name").isEmpty){
                                    if((comp \\ "@id").isEmpty){
                                        generateTableFromXML(comp \\ "@metaid",comp \\ "@metaid",1)
                                    } else {
                                        generateTableFromXML(comp \\ "@id",comp \\ "@metaid",1)
                                    }
                                } else {
                                    generateTableFromXML(comp \\ "@name",comp \\ "@metaid",1)
                                }
                            } else {
                                Text("")
                            }
                        },
                         "listOfSpecies" -> {
                            val spec = modelSBML \\ "model" \\ "listOfSpecies" \\ "species"
                            if(spec.length >0){
                                if((spec \\ "@name").isEmpty){
                                    generateTableFromXML(spec \\ "@id",spec \\ "@metaid",4)
                                } else {
                                    generateTableFromXML(spec \\ "@name",spec \\ "@metaid",4)
                                }
                            } else {
                                Text("")
                            }
                        },
                         "downloadSBML" -> {XML.save("file.xml", modelSBML)
                                            <a href="./file.xml" target="_blank"><input type="submit" class="buttonExportModel" title="Export this model in SBML" value="Export SBML Model" /></a>},
                         "listOfParameters" -> {
                            val param = modelSBML \\ "model" \ "listOfParameters" \\ "parameter"
                            if(param.length >0){
                                if((param \\ "@name").isEmpty){
                                    generateTableFromXML(param \\ "@id",param \\ "@metaid",4)
                                } else {
                                    generateTableFromXML(param \\ "@name",param \\ "@metaid",4)
                                }
                            } else {
                                Text("")
                            }
                        },
                         "listFunctionsMath" -> {
                            if(!(modelSBML \\ "model" \ "listOfFunctionDefinitions" \\ "functionDefinition").isEmpty){
                                for( fun <- modelSBML \\ "model" \ "listOfFunctionDefinitions" \\ "functionDefinition";
                                    addFun <- fun \\ "@id" ) yield{
                                    <ul><h8><a id="delete" href="">Delete</a>
                                            <a href={"/model/"+model_metaid+"/functionDefinition/"+(fun \\ "@metaid")}>Edit</a>
                                            <a onclick="window.open('../models/new_comment.html','New Comment','width=600,height=300');">Make Comment</a>
                                            <a onclick="window.open('../models/view_comments.html','View Comments','width=600,height=600');">View Comments</a></h8>
                                        <li class="closed"><span><tr id={fun \\ "@metaid"}>
                                                    {if((fun \\ "@name").isEmpty){
                                                            <td class="main">{addFun}</td>} else{
                                                            <td class="main">{fun \\ "@name"}</td>}
                                                    }<td class="sub_main">&nbsp;</td>
                                                                 </tr></span>
                                            <ul><li><tr><td class="main_under">Math:</td>
                                                        <td class="sub_main_under">
                                                            {for(x <- fun \\ "math") yield {
                                                                    /*if((x \\ "lambda").isEmpty){
                                                                     <b style="color:red;">MathML with errors!</b>
                                                                     } else{*/
                                                                    val te = x.toString
                                                                    //val va = te.indexOf("/version")
                                                                    //val v1 = te.substring(0,(va-39))
                                                                    //val v2 = te.substring(va+10)
                                                                    //val fi = XML.loadString(v1.concat(v2))
                                                                    val fi = XML.loadString(te)
                                                                    <b>{fi}</b>
                                                                    //}
                                                                }
                                                            }</td></tr></li></ul><ul>
                                                <li>{if((fun \\ "notes").isEmpty){} else{
                                                            <tr><td class="main_under">Notes:</td>
                                                                <td class="sub_main_under">
                                                                    {for( x <- fun \\ "notes") yield {
                                                                            val fun_notes = x.toString
                                                                            if(fun_notes.contains("body")){
                                                                                val var2 = fun_notes.replaceAll("body", "div")
                                                                                v1 = var2.replaceAll("notes", "div")
                                                                            } else {
                                                                                v1 = fun_notes.replaceAll("notes", "div")
                                                                            }
                                                                            val fun_notes_p = XML.loadString(v1)
                                                                            /*S.setDocType(Full("""<?xml-stylesheet type="text/xsl" href="../classpath/mathml.xsl"?>
                                                                             <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">"""))*/
                                                                            <b>{fun_notes_p}</b>}}</td></tr>}}</li></ul></li></ul>}
                            } else {
                                Text("")
                            }
                        },
                         "listReactionsMath" -> {
                            if(!(modelSBML \\ "model" \ "listOfReactions" \\ "reaction").isEmpty){
                                for( react <- modelSBML \\ "model" \ "listOfReactions" \\ "reaction") yield{
                                    <ul><h8><a id="delete" href="">Delete</a>
                                            <a href={"/model/"+model_metaid+"/reaction/"+(react \\ "@metaid")}>Edit</a>
                                            <a onclick="window.open('../models/new_comment.html','New Comment','width=600,height=300');">Make Comment</a>
                                            <a onclick="window.open('../models/view_comments.html','View Comments','width=600,height=600');">View Comments</a></h8>
                                        <li class="closed"><span><tr name={react \\ "@metaid"}>{
                                                        val addRe = react \\ "@id"
                                                        if((react \\ "@name").isEmpty){
                                                            <td class="main">{addRe}</td>} else{
                                                            <td class="main">{react \\ "@name"}</td>}}
                                                    <td class="sub_main"><br /><b>[{for( x <- react \ "listOfReactants" \\ "speciesReference" \\ "@species") yield x}]</b>
                                                        {if(react \\ "@reversible" != "false"){<b>&harr;</b>} else {<b>&rarr;</b>}}
                                                        <b>[{for( y <- react \ "listOfProducts" \\ "speciesReference" \\ "@species") yield y}];&nbsp;&nbsp;</b>
                                                        {for( z <- react \ "listOfModifiers" \\ "modifierSpeciesReference" \\ "@species") yield {
                                                                <b>&#123;{z}&#125;&nbsp;</b>}}</td></tr></span>
                                            <ul><li><tr><td class="main_under">Math:</td>
                                                        <td class="sub_main_under">
                                                            {for( x <- react \ "kineticLaw" \ "math") yield {
                                                                    val te = x.toString
                                                                    val va = te.indexOf("/version")
                                                                    //                                                                    Console.println("INDEX--->"+va+" te-"+te)
                                                                    val v1 = te.substring(0,(va-39))
                                                                    val v2 = te.substring(va+10)
                                                                    val fi = XML.loadString(v1.concat(v2))
                                                                    /*S.setDocType(Full("""<?xml-stylesheet type="text/xsl" href="../classpath/mathml.xsl"?>
                                                                     <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">"""))*/
                                                                    <b>{fi}</b>}}</td></tr></li></ul>
                                            <ul>
                                                <li>{if((react \ "notes").isEmpty){} else{
                                                            <tr><td class="main_under">Notes:</td>
                                                                <td class="sub_main_under">
                                                                    {for( x <- react \\ "notes") yield {
                                                                            val react_notes = x.toString
                                                                            if(react_notes.contains("body")){
                                                                                val var2 = react_notes.replaceAll("body", "div")
                                                                                v1 = var2.replaceAll("notes", "div")
                                                                            } else {
                                                                                v1 = react_notes.replaceAll("notes", "div")
                                                                            }
                                                                            val reaction_notes_p = XML.loadString(v1)
                                                                            <b>{reaction_notes_p}</b>}}</td></tr>}}
                                                </li></ul></li>
                                    </ul>}
                            } else {
                                Text("")
                            }
                        },
                         "compartmentData" -> {
                            if(!(modelSBML \\ "model" \ "listOfCompartments" \\ "compartment").isEmpty){
                                for( compartData <- modelSBML \\ "model" \ "listOfCompartments" \\ "compartment") yield {
                                    <ul><h8><a id="delete" href="">Delete</a>
                                            <a href={"/model/"+model_metaid+"/compartment/"+(compartData \\ "@metaid")}>Edit</a>
                                            <a onclick="window.open('../models/new_comment.html','New Comment','width=600,height=300');">Make Comment</a>
                                            <a onclick="window.open('../models/view_comments.html','View Comments','width=600,height=600');">View Comments</a></h8>
                                        <li class="closed">
                                            <span>
                                                <tr name={(compartData \\ "@metaid")} id={(compartData \\ "@metaid")}>{
                                                        if((compartData \\ "@name").isEmpty){
                                                            Console.println("=====.---...-.-..VALOR DO COMPARTMENT ID-----------"+(compartData \\ "@id"))
                                                            <td class="main">{(compartData \\ "@id")}</td>
                                                        } else {
                                                            <td class="main">{(compartData \\ "@name")}</td>
                                                        }
                                                    }
                                                    <td class="sub_main"><br />{
                                                            <table>
                                                                <tr>
                                                                    <td>{
                                                                            if(!(compartData \\ "@spatialDimensions").isEmpty){
                                                                                <div style="font-size:small"><b>Spatial dimensions: </b><i>{(compartData \\ "@spatialDimensions")} &nbsp;&nbsp;</i></div>
                                                                            } else {
                                                                                <div style="font-size:small"><b>Spatial dimensions: </b><i>3 &nbsp;&nbsp;</i></div>
                                                                            }
                                                                        }
                                                                    </td>
                                                                    <td>{
                                                                            if(!(compartData \\ "@size").isEmpty){
                                                                                <div style="font-size:small"><b>Size: </b><i>{(compartData \\ "@size")} &nbsp;&nbsp;</i></div>
                                                                            } else {
                                                                                <div style="font-size:small"><b>Size: </b><i>-.-</i></div>
                                                                            }
                                                                        }
                                                                    </td>
                                                                    <td>{
                                                                            if(!(compartData \\ "@outside").isEmpty){
                                                                                <div style="font-size:small"><b>Outside: </b><i>{(compartData \\ "@outside")} &nbsp;&nbsp;</i></div>
                                                                            } else {
                                                                                <div style="font-size:small"><b>Outside: </b><i>- &nbsp;&nbsp;</i></div>
                                                                            }
                                                                        }
                                                                    </td>
                                                                    <td>{
                                                                            if(!(compartData \\ "@constant").isEmpty){
                                                                                <div style="font-size:small"><b>Constant: </b><i>{compartData \\ "@constant"} &nbsp;&nbsp;</i></div>
                                                                            } else{
                                                                                <div style="font-size:small"><b>Constant: </b><i>true &nbsp;&nbsp;</i></div>
                                                                            }
                                                                        }
                                                                    </td></tr>
                                                            </table>
                                                        }
                                                    </td>
                                                </tr>
                                            </span>
                                            <ul>
                                                <li>{
                                                        if(!(compartData \ "notes").isEmpty){
                                                            <tr><td class="main_under">Notes:</td>
                                                                <td class="sub_main_under">
                                                                    {for( x <- compartData \\ "notes") yield {
                                                                            val react_notes = x.toString
                                                                            if(react_notes.contains("body")){
                                                                                val var2 = react_notes.replaceAll("body", "div")
                                                                                v1 = var2.replaceAll("notes", "div")
                                                                            } else {
                                                                                v1 = react_notes.replaceAll("notes", "div")
                                                                            }
                                                                            val reaction_notes_p = XML.loadString(v1)
                                                                            <b>{reaction_notes_p}</b>
                                                                        }
                                                                    }
                                                                </td>
                                                            </tr>

                                                        } else {

                                                        }
                                                    }
                                                </li>
                                            </ul>
                                        </li>
                                    </ul>}
                            } else {
                                Text("")
                            }
                        },
                         "speciesData" -> {
                            if(!(modelSBML \\ "model" \ "listOfSpecies" \\ "species").isEmpty){
                                for(specData <- modelSBML \\ "model" \ "listOfSpecies" \\ "species";
                                    specDataId <- specData \\ "@id") yield {
                                    <ul><h8><a href={"/model/"+model_metaid+"/species/"+(specData \\ "@metaid")}>Edit</a>
                                            <a onclick="window.open('../models/new_comment.html','New Comment','width=600,height=300');">Make Comment</a>
                                            <a onclick="window.open('../models/view_comments.html','View Comments','width=600,height=600');">View Comments</a></h8>
                                        <li class="closed"><span><tr>{
                                                        if((specData \\ "@name").isEmpty){
                                                            <td class="main">{specDataId}<br /><hr />Compartment: <i>{specData \\ "@compartment"}</i></td>
                                                        } else{
                                                            <td class="main">{specData \\ "@name"}<br /><hr />Compartment: <i>{specData \\ "@compartment"}</i></td>
                                                        }
                                                    }
                                                    <td class="sub_main"><br />{
                                                            <table>
                                                                <tr>
                                                                    <td>{
                                                                            if(!(specData \\ "@initialAmount").isEmpty){
                                                                                <div style="font-size:small"><b>Initial amount: </b><i>{specData \\ "@initialAmount"} &nbsp;&nbsp;</i></div>
                                                                            } else {
                                                                                <div style="font-size:small"><b>Initial amount: </b><i>- &nbsp;&nbsp;</i></div>
                                                                            }
                                                                        }
                                                                    </td>
                                                                    <td>{
                                                                            if(!(specData \\ "@initialConcentration").isEmpty){
                                                                                <div style="font-size:small"><b>Initial concentration: </b><i>{specData \\ "@initialConcentration"} &nbsp;&nbsp;</i></div>
                                                                            } else {
                                                                                <div style="font-size:small"><b>Initial concentration: </b><i>- &nbsp;&nbsp;</i></div>
                                                                            }
                                                                        }
                                                                    </td>
                                                                    <td>{
                                                                            if(!(specData \\ "@boundaryCondition").isEmpty){
                                                                                <div style="font-size:small"><b>Boundary condition: </b><i>{specData \\ "@boundaryCondition"} &nbsp;&nbsp;</i></div>
                                                                            } else {
                                                                                <div style="font-size:small"><b>Boundary condition: </b><i>false &nbsp;&nbsp;</i></div>
                                                                            }
                                                                        }</td>
                                                                    <td>{
                                                                            if(!(specData \\ "@constant").isEmpty){
                                                                                <div style="font-size:small"><b>Constant: </b><i>{specData \\ "@constant"} &nbsp;&nbsp;</i></div>
                                                                            } else{
                                                                                <div style="font-size:small"><b>Constant: </b> <i>true &nbsp;&nbsp;</i></div>
                                                                            }
                                                                        }</td></tr>
                                                            </table>
                                                        }
                                                    </td>
                                                                 </tr>
                                                           </span>
                                            <ul>
                                                <li>{if(!(specData \\ "notes").isEmpty){
                                                            <tr><td class="main_under">Notes:</td>
                                                                <td class="sub_main_under">
                                                                    {for( x <- specData \\ "notes") yield {
                                                                            val react_notes = x.toString
                                                                            if(react_notes.contains("body")){
                                                                                val var2 = react_notes.replaceAll("body", "div")
                                                                                v1 = var2.replaceAll("notes", "div")
                                                                            } else {
                                                                                v1 = react_notes.replaceAll("notes", "div")
                                                                            }
                                                                            val reaction_notes_p = XML.loadString(v1)
                                                                            <b>{reaction_notes_p}</b>}
                                                                    }
                                                                </td>
                                                            </tr>
                                                        }
                                                    }
                                                </li>
                                            </ul>
                                        </li>
                                    </ul>
                                }
                            } else {
                                Text("")
                            }
                        },
                         "parametersData" -> {
                            if(!(modelSBML \\ "model" \ "listOfParameters" \\ "parameter").isEmpty){
                                for( paramData <- modelSBML \\ "model" \ "listOfParameters" \\ "parameter";
                                    val paramDataId <- paramData \\ "@id") yield {
                                    <ul><h8><!--<a onclick={"window.open('/model/"+model_metaid+"/parameter/"+(paramData \\ "@metaid")+"','Edit Model','width=800,height=500');"}>Edit</a>-->
                                            <a id="delete" href="">Delete</a>
                                            <a href={"/model/"+model_metaid+"/parameter/"+(paramData \\ "@metaid")}>Edit</a>
                                            <a onclick="window.open('../models/new_comment.html','New Comment','width=600,height=300');">Make Comment</a>
                                            <a onclick="window.open('../models/view_comments.html','View Comments','width=600,height=600');">View Comments</a></h8>
                                        <li class="closed"><span><tr>{
                                                        if((paramData \\ "@name").isEmpty){
                                                            <td class="main">{paramDataId}</td>} else{
                                                            <td class="main">{paramData \\ "@name"}</td>}}
                                                    <td class="sub_main"><br />{
                                                            <table>
                                                                <tr>
                                                                    <td>{
                                                                            if(!(paramData \\ "@value").isEmpty){
                                                                                <div style="font-size:small"><b>Value: </b><i>{paramData \\ "@value"} &nbsp;&nbsp;</i></div>
                                                                            } else {
                                                                                <div style="font-size:small"><b>Value: </b><i>- &nbsp;&nbsp;</i></div>
                                                                            }}
                                                                    </td>
                                                                    <td>{
                                                                            if(!(paramData \\ "@constant").isEmpty){
                                                                                <div style="font-size:small"><b>Constant: </b><i>{paramData \\ "@constant"} &nbsp;&nbsp;</i></div>
                                                                            } else{
                                                                                <div style="font-size:small"><b>Constant: </b><i>true &nbsp;&nbsp;</i></div>
                                                                            }}

                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        }
                                                    </td></tr></span>
                                            <ul>
                                                <li>{if(!(paramData \\ "notes").isEmpty) {
                                                            <tr><td class="main_under">Notes:</td>
                                                                <td class="sub_main_under">
                                                                    {for( x <- paramData \\ "notes") yield {
                                                                            val react_notes = x.toString
                                                                            if(react_notes.contains("body")){
                                                                                val var2 = react_notes.replaceAll("body", "div")
                                                                                v1 = var2.replaceAll("notes", "div")
                                                                            } else {
                                                                                v1 = react_notes.replaceAll("notes", "div")
                                                                            }
                                                                            val reaction_notes_p = XML.loadString(v1)
                                                                            <b>{reaction_notes_p}</b>}
                                                                    }
                                                                </td>
                                                            </tr>
                                                        }
                                                    }
                                                </li>
                                            </ul>
                                        </li>
                                    </ul>
                                }
                            } else {
                                Text("")
                            }
                        },
                         "extraParametersData" -> {
                            if(!(modelSBML \\ "model" \ "listOfReactions" \\ "reaction").isEmpty){
                                for( extraParamData <- modelSBML \\ "model" \ "listOfReactions" \\ "reaction";
                                    val extraParamDataId <- extraParamData \\ "@id") yield {
                                    <li>{val extra = extraParamData \ "kineticLaw" \ "listOfParameters" \\ "parameter"
                                         if(!extra.isEmpty){
                                                <span><h2>&nbsp;&nbsp;&nbsp;{extraParamData \\ "@name"}</h2></span>
                                                <ul><li class="closed">
                                                        <table class="browseModelTable" id="modelTable_Overview">
                                                            <tbody>
                                                                <ul><h8><a onclick="window.open('../models/editModel.xhtml','Edit Model','width=800,height=500');">Edit</a>
                                                                        <a onclick="window.open('../models/new_comment.html','New Comment','width=600,height=300');">Make Comment</a>
                                                                        <a onclick="window.open('../models/view_comments.html','View Comments','width=600,height=600');">View Comments</a></h8>
                                                                    <li class="closed"><span><tr>{
                                                                                    if((extra \\ "@name").isEmpty){
                                                                                        <td class="main">{extra \\ "@id"}</td>} else{
                                                                                        <td class="main">{extra \\ "@name"}</td>}}
                                                                                <td class="sub_main"><br />{
                                                                                        if(!(extra \\ "@value").isEmpty){
                                                                                            <b>Value: </b><i>{extra \\ "@value"};  </i>
                                                                                        }
                                                                                        if((extra \\ "@constant").isEmpty){
                                                                                            <b>Constant: </b> <i>true;</i>
                                                                                        } else{
                                                                                            <b>Constant: </b> <i>false;</i>
                                                                                        }
                                                                                    }
                                                                                </td></tr></span>
                                                                        <ul>
                                                                            <li>{if(!(extra \\ "notes").isEmpty) {
                                                                                        <tr><td class="main_under">Notes:</td>
                                                                                            <td class="sub_main_under">
                                                                                                {for( x <- extra \\ "notes") yield {
                                                                                                        val react_notes = x.toString
                                                                                                        if(react_notes.contains("body")){
                                                                                                            val var2 = react_notes.replaceAll("body", "div")
                                                                                                            v1 = var2.replaceAll("notes", "div")
                                                                                                        } else {
                                                                                                            v1 = react_notes.replaceAll("notes", "div")
                                                                                                        }
                                                                                                        val reaction_notes_p = XML.loadString(v1)
                                                                                                        <b>{reaction_notes_p}</b>
                                                                                                    }
                                                                                                }
                                                                                            </td>
                                                                                        </tr>
                                                                                    }
                                                                                }
                                                                            </li>
                                                                        </ul>
                                                                    </li>
                                                                </ul>
                                                            </tbody>
                                                        </table>
                                                    </li>
                                                </ul>

                                            }
                                        }
                                    </li>
                                }
                            } else {
                                Text("")
                            }
                        }
                    )
                } else {
                    S.error("SBML model does not exist")
                    S.redirectTo("/models")
                    bind("modelInformation", xhtml)
                }

                
            }
        case _ => {
                Text("")
            }
    }

    def newComment () = {
        val list = List[(String,String)]()
        val novo = S.getHeaders(list)
        Console.println("Valor ="+novo.map(s => s))
    }

    def newCommentForm (xhtml : NodeSeq) : NodeSeq = {
        var new_comment = ""

        def commentMade () = {
            if (new_comment.length == 0) {
                S.error("Invalid comment")
            } else {
                println("New comment = "+new_comment)
            }
        }
        bind("makeComment", xhtml,
             "newComment" -> SHtml.textarea("", new_comment = _, ("id","commentArea"), ("rows","10"), ("cols", "120"), ("maxlength", "50000")),
             "submitComment" -> SHtml.submit("Add Comment", commentMade,("id","browseModelLI_AddComment"))
        )
    }
}
