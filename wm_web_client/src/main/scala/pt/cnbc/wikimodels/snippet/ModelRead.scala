/*
 * ModelRead.scala
 *
 */

package pt.cnbc.wikimodels.snippet

import net.liftweb._
import http._
import util._
import Helpers._
import sitemap.Loc.If
import scala.xml.{ MetaData, XML, Elem, Group, Node, NodeSeq, Null, Text, TopScope, PrettyPrinter }
import scala.xml.transform._
import S._
import js._
import js.JsCmds
import js.JsCmds._
import js.Jx
import js.JE
import js.JE._

class ModelRead {

    def listOfModels (xhtml : NodeSeq) : NodeSeq = {
        val model001 = XML.load("BIOMD0000000055.xml")
        val model002 = XML.load("BIOMD0000000103.xml")
        val model003 = XML.load("BIOMD0000000117.xml")
        val model004 = XML.load("BIOMD0000000168.xml")
        val model005 = XML.load("BIOMD0000000206.xml")
        val model006 = XML.load("BIOMD0000000212.xml")

        bind("listModels", xhtml,
             "list" -> {
                <table id="myTable" class="tablesorter">
                    <thead>
                        <tr>
                            <th>Model Name</th>
                            <th>Author</th>
                            <th>Last Modification</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>{for(val data <- (model001 \ "model")) yield {
                                    <td title="Model Name"><a href="/models/browse/model001">{data \ "@name"}</a></td>
                                    <td title="Author"><a href="../user/admin"></a></td>
                                    <td title="Last Modification"></td>}}
                        </tr>
                        <tr>
                            {for(val data <- (model002 \ "model")) yield {
                                    <td title="Model Name"><a href="/models/browse/model002">{data \ "@name"}</a></td>
                                    <td title="Author"><a href="../user/admin"></a></td>
                                    <td title="Last Modification"></td>}}
                        </tr>
                        <tr>
                            {for(val data <- (model003 \ "model")) yield {
                                    <td title="Model Name"><a href="/models/browse/model003">{data \ "@name"}</a></td>
                                    <td title="Author"><a href="../user/admin"></a></td>
                                    <td title="Last Modification"></td>}}
                        </tr>
                        <tr>
                            {for(val data <- (model004 \ "model")) yield {
                                    <td title="Model Name"><a href="/models/browse/model004">{data \ "@name"}</a></td>
                                    <td title="Author"><a href="../user/admin"></a></td>
                                    <td title="Last Modification"></td>}}
                        </tr>
                        <tr>
                            {for(val data <- (model005 \ "model")) yield {
                                    <td title="Model Name"><a href="/models/browse/model005">{data \ "@name"}</a></td>
                                    <td title="Author"><a href="../user/admin"></a></td>
                                    <td title="Last Modification"></td>}}
                        </tr>
                        <tr>
                            {for(val data <- (model006 \ "model")) yield {
                                    <td title="Model Name"><a href="/models/browse/model006">{data \ "@name"}</a></td>
                                    <td title="Author"><a href="../user/admin"></a></td>
                                    <td title="Last Modification"></td>}}
                        </tr>
                    </tbody>
                </table>})
    }

    def browseModel (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
            
                var countR = 0
                var countF = 0
                val modelID = S.param("modelID")
                var modelRef = "BIOMD0000000055.xml"
                modelID match {
                    case Full("model001") => {modelRef = "BIOMD0000000055.xml"
                                              Text("")}
                    case Full("model002") => {modelRef = "BIOMD0000000103.xml"
                                              Text("")}
                    case Full("model003") => {modelRef = "BIOMD0000000117.xml"
                                              Text("")}
                    case Full("model004") => {modelRef = "BIOMD0000000168.xml"
                                              Text("")}
                    case Full("model005") => {modelRef = "BIOMD0000000206.xml"
                                              Text("")}
                    case Full("model006") => {modelRef = "BIOMD0000000212.xml"
                                              Text("")}
                    case _ => {Text("")}

                }
                val modelSBML = XML.load(modelRef)
                //val model_qq = (modelSBML \ "sbml" \ "@xmlns").text
                val model_id= (modelSBML \ "model" \ "@id").text
                val model_name = (modelSBML \ "model" \ "@name").text
                val model_notes = (modelSBML \ "model" \ "notes").toString
                var v1 = ""

                for(val spec <- modelSBML \ "model" \ "listOfReactions" \ "reaction" ;
                    val addSpec <- spec \\ "@name"  ) { countR = countR+1}
                for(val func <- modelSBML \ "model" \ "listOfFunctionDefinitions" \ "functionDefinition" ;
                    val addSpec <- func \\ "@id"  ) { countF = countF+1}

                // Search for the <body> and <notes> and replaces for the <div> tag
                if(model_notes.contains("body")){
                    var var2 = ""
                    var2 = model_notes.replaceAll("body", "div")
                    v1 = var2.replaceAll("notes", "div")
                } else {
                    v1 = model_notes.replaceAll("notes", "div")
                }
                val model_notes_p = XML.loadString(v1)

                def generateTableFromXML(nodes:NodeSeq, metid:NodeSeq, columnNum:Int):Elem = {
                    var pos = 0
                    val relElems:NodeSeq = nodes
                    val metElems:NodeSeq = metid
                    val nodeNum = relElems.size

                    val rowNum = ( nodeNum + columnNum -1 )  / columnNum
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
                            }}</table>
                }
            
                bind("modelInformation", xhtml,
                     "id" -> model_id,
                     "model_name" -> model_name,
                     "model_notes" -> model_notes_p,
                     "listOfReactions" -> {
                        val reac = modelSBML \ "model" \ "listOfReactions" \\ "reaction"
                        if((reac \\ "@name").isEmpty){
                            generateTableFromXML(reac \\ "@id",reac \\ "@metaid",4)
                        } else {
                            generateTableFromXML(reac \\ "@name",reac \\ "@metaid",4)
                        }
                            
                    },
                     "numberReactions" -> {<span>{countR}</span>},
                     "numberFunctions" -> {<span>{countF}</span>},
                     "listOfCompartments" -> {
                        val comp = modelSBML \ "model" \ "listOfCompartments" \\ "compartment"
                        if((comp \\ "@name").isEmpty){
                            generateTableFromXML(comp \\ "@id",comp \\ "@metaid",1)
                        } else {
                            generateTableFromXML(comp \\ "@name",comp \\ "@metaid",1)
                        }
                    },
                     "listOfSpecies" -> {
                        val spec = modelSBML \ "model" \ "listOfSpecies" \\ "species"
                        if((spec \\ "@name").isEmpty){
                            generateTableFromXML(spec \\ "@id",spec \\ "@metaid",4)
                        } else {
                            generateTableFromXML(spec \\ "@name",spec \\ "@metaid",4)
                        }
                    },
                     "downloadSBML" -> {<a href={"../"+modelRef} target="_blank"><input type="submit" class="buttonExportModel" title="Export this model in SBML" value="Export SBML Model" /></a>},
                     "listOfParameters" -> {
                        val param = modelSBML \ "model" \ "listOfParameters" \\ "parameter"
                        if((param \\ "@name").isEmpty){
                            generateTableFromXML(param \\ "@id",param \\ "@metaid",5)
                        } else {
                            generateTableFromXML(param \\ "@name",param \\ "@metaid",5)
                        }
                    },
                     "listFunctionsMath" -> {
                        for(val fun <- modelSBML \ "model" \ "listOfFunctionDefinitions" \\ "functionDefinition";
                            val addFun <- fun \\ "@id" ) yield{
                            <ul><h8><a onclick="window.open('../models/editModel.html','Edit Model','location=no,resizable=no,scrollbars=no,width=800,height=500');">Edit</a>
                                    <a onclick="window.open('../models/new_comment.html','New Comment','location=no,resizable=no,scrollbars=no,width=600,height=300');">Make Comment</a>
                                    <a onclick="window.open('../models/view_comments.html','View Comments','location=no, resizable=no,scrollbars=yes,width=500,height=600');">View Comments</a></h8>
                                <li class="closed"><span><tr id={fun \\ "@metaid"}>
                                            {if((fun \\ "@name").isEmpty){
                                                    <td class="main">{addFun}</td>} else{
                                                    <td class="main">{fun \\ "@name"}</td>}
                                            }<td class="sub_main">&nbsp;</td>
                                                         </tr></span>
                                    <ul><li><tr><td class="main_under">Math:</td>
                                                <td class="sub_main_under">
                                                    {for(val x <- fun\\"math") yield {
                                                            if((x \"lambda").isEmpty){
                                                                <b style="color:red;">MathML with errors!</b>
                                                            } else{
                                                                val te = x.toString
                                                                val va = te.indexOf("/version")
                                                                val v1 = te.substring(0,(va-39))
                                                                val v2 = te.substring(va+10)
                                                                val fi = XML.loadString(v1.concat(v2))
                                                                <b>{fi}</b>}}}</td></tr></li></ul><ul>
                                        <li>{if((fun\\"notes").isEmpty){} else{
                                                    <tr><td class="main_under">Notes:</td>
                                                        <td class="sub_main_under">
                                                            {for(val x <- fun\\"notes") yield {
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
                    },
                     "listReactionsMath" -> {
                        for(val react <- modelSBML \ "model" \ "listOfReactions" \\ "reaction";
                            val addRe <- react \\ "@id" ) yield{
                            <ul><h8><a onclick="window.open('../models/editModel.html','Edit Model','location=no, resizable=no, scrollbars=no,width=800,height=500');">Edit</a>
                                    <a onclick="window.open('../models/new_comment.html','New Comment','location=no, resizable=no, scrollbars=no,width=600,height=300');">Make Comment</a>
                                    <a onclick="window.open('../models/view_comments.html','View Comments','location=no, resizable=no, scrollbars=yes, width=500,height=600');">View Comments</a></h8>
                                <li class="closed"><span><tr id={react \\ "@metaid"}>{
                                                if((react \\ "@name").isEmpty){
                                                    <td class="main">{addRe}</td>} else{
                                                    <td class="main">{react \\ "@name"}</td>}}
                                            <td class="sub_main"><br /><b>[{for(val x <- react\\"listOfReactants"\\"speciesReference"\\"@species") yield x}]</b>
                                                {if(react \\"@reversible" != "false"){<b>&harr;</b>} else {<b>&rarr;</b>}}
                                                <b>[{for(val y <- react\\"listOfProducts"\\"speciesReference"\\"@species") yield y}];&nbsp;&nbsp;</b>
                                                {for(val z <- react\\"listOfModifiers"\\"modifierSpeciesReference"\\"@species") yield {
                                                        <b>&#123;{z}&#125;&nbsp;</b>}}</td></tr></span>
                                    <ul><li><tr><td class="main_under">Math:</td>
                                                <td class="sub_main_under">
                                                    {for(val x <- react\\"kineticLaw"\"math") yield {
                                                            val te = x.toString
                                                            val va = te.indexOf("/version")
                                                            val v1 = te.substring(0,(va-39))
                                                            val v2 = te.substring(va+10)
                                                            val fi = XML.loadString(v1.concat(v2))
                                                            /*S.setDocType(Full("""<?xml-stylesheet type="text/xsl" href="../classpath/mathml.xsl"?>
                                                             <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">"""))*/
                                                            <b>{fi}</b>}}</td></tr></li></ul>
                                    <ul>
                                        <li>{if((react\\"notes").isEmpty){} else{
                                                    <tr><td class="main_under">Notes:</td>
                                                        <td class="sub_main_under">
                                                            {for(val x <- react\\"notes") yield {
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
                    },
                     "compartmentData" ->{
                        for(val compartData <- modelSBML \ "model" \ "listOfCompartments" \\ "compartment";
                            val compartDataId <- compartData \\ "@id") yield {
                            <ul><h8><a onclick="window.open('../models/editModel.html','Edit Model','location=no, resizable=no, scrollbars=no,width=800,height=500');">Edit</a>
                                    <a onclick="window.open('../models/new_comment.html','New Comment','location=no, resizable=no, scrollbars=no,width=600,height=300');">Make Comment</a>
                                    <a onclick="window.open('../models/view_comments.html','View Comments','location=no, resizable=no, scrollbars=yes, width=500,height=600');">View Comments</a></h8>
                                <li class="closed"><span><tr id={compartData \\ "@metaid"}>{
                                                if((compartData \\ "@name").isEmpty){
                                                    <td class="main">{compartDataId}</td>} else{
                                                    <td class="main">{compartData \\ "@name"}</td>}}
                                            <td class="sub_main" style="width:100%;"><br />{
                                                    if((compartData \\ "@spatialDimensions").isEmpty){
                                                        <b>Spatial dimensions: </b><i>3;  </i>
                                                    } else {
                                                        <b>Spatial dimensions: </b><i>{compartData \\ "@spatialDimensions"};  </i>
                                                    }}{
                                                    if((compartData \\ "@size").isEmpty){
                                                    } else {
                                                        <b>Size: </b><i>{compartData \\ "@size"};  </i>
                                                    }}{
                                                    if((compartData \\ "@outside").isEmpty){
                                                    } else {
                                                        <b>Outside: </b><i>{compartData \\ "@outside"};  </i>
                                                    }}{
                                                    if((compartData \\ "@constant").isEmpty){
                                                        <b>Constant: </b><i>true;</i>
                                                    } else{
                                                        <b>Constant: </b><i>false;</i>
                                                    }}
                                            </td></tr></span>
                                    <ul>
                                        <li>{if((compartData\\"notes").isEmpty){} else{
                                                    <tr><td class="main_under" style="width:80%;">Notes:</td>
                                                        <td class="sub_main_under" style="width:100%;">
                                                            {for(val x <- compartData\\"notes") yield {
                                                                    val react_notes = x.toString
                                                                    if(react_notes.contains("body")){
                                                                        val var2 = react_notes.replaceAll("body", "div")
                                                                        v1 = var2.replaceAll("notes", "div")
                                                                    } else {
                                                                        v1 = react_notes.replaceAll("notes", "div")
                                                                    }
                                                                    val reaction_notes_p = XML.loadString(v1)
                                                                    <b>{reaction_notes_p}</b>}}</td></tr>}}
                                        </li></ul></li></ul>}
                    },
                     "speciesData" ->{
                        for(val specData <- modelSBML \ "model" \ "listOfSpecies" \\ "species";
                            val specDataId <- specData \\ "@id") yield {
                            <ul><h8><a onclick="window.open('../models/editModel.html','Edit Model','location=no, resizable=no, scrollbars=no,width=800,height=500');">Edit</a>
                                    <a onclick="window.open('../models/new_comment.html','New Comment','location=no, resizable=no, scrollbars=no,width=600,height=300');">Make Comment</a>
                                    <a onclick="window.open('../models/view_comments.html','View Comments','location=no, resizable=no, scrollbars=yes, width=500,height=600');">View Comments</a></h8>
                                <li class="closed"><span><tr>{
                                                if((specData \\ "@name").isEmpty){
                                                    <td class="main">{specDataId}<br /><hr />Compartment: <i>{specData \\ "@compartment"}</i></td>} else{
                                                    <td class="main">{specData \\ "@name"}<br /><hr />Compartment: <i>{specData \\ "@compartment"}</i></td>}}
                                            <td class="sub_main" style="width:100%;"><br />{
                                                    if((specData \\ "@initialAmount").isEmpty){
                                                    } else {
                                                        <b>Initial amount: </b><i>{specData \\ "@initialAmount"};  </i>
                                                    }}{
                                                    if((specData \\ "@initialConcentration").isEmpty){
                                                    } else {
                                                        <b>Initial concentration: </b><i>{specData \\ "@initialConcentration"};  </i>
                                                    }}{
                                                    if((specData \\ "@boundaryCondition").isEmpty){
                                                        <b>Boundary condition: </b><i>false;  </i>
                                                    } else {
                                                        <b>Boundary condition: </b><i>true;  </i>
                                                    }}{
                                                    if((specData \\ "@constant").isEmpty){
                                                        <b>Constant: </b> <i>true;</i>
                                                    } else{
                                                        <b>Constant: </b> <i>false;</i>
                                                    }}
                                            </td></tr></span>
                                    <ul>
                                        <li>{if((specData\\"notes").isEmpty){} else{
                                                    <tr><td class="main_under" style="width:80%;">Notes:</td>
                                                        <td class="sub_main_under" style="width:100%;">
                                                            {for(val x <- specData\\"notes") yield {
                                                                    val react_notes = x.toString
                                                                    if(react_notes.contains("body")){
                                                                        val var2 = react_notes.replaceAll("body", "div")
                                                                        v1 = var2.replaceAll("notes", "div")
                                                                    } else {
                                                                        v1 = react_notes.replaceAll("notes", "div")
                                                                    }
                                                                    val reaction_notes_p = XML.loadString(v1)
                                                                    <b>{reaction_notes_p}</b>}}</td></tr>}}
                                        </li></ul></li></ul>}
                    },
                     "parametersData" ->{
                        for(val paramData <- modelSBML \ "model" \ "listOfParameters" \\ "parameter";
                            val paramDataId <- paramData \\ "@id") yield {
                            <ul><h8><a onclick="window.open('../models/editModel.html','Edit Model','location=no, resizable=no, scrollbars=no,width=800,height=500');">Edit</a>
                                    <a onclick="window.open('../models/new_comment.html','New Comment','location=no, resizable=no, scrollbars=no,width=600,height=300');">Make Comment</a>
                                    <a onclick="window.open('../models/view_comments.html','View Comments','location=no, resizable=no, scrollbars=yes, width=500,height=600');">View Comments</a></h8>
                                <li class="closed"><span><tr>{
                                                if((paramData \\ "@name").isEmpty){
                                                    <td class="main">{paramDataId}</td>} else{
                                                    <td class="main">{paramData \\ "@name"}</td>}}
                                            <td class="sub_main" style="width:100%;"><br />{
                                                    if((paramData \\ "@value").isEmpty){
                                                    } else {
                                                        <b>Value: </b><i>{paramData \\ "@value"};  </i>
                                                    }}{
                                                    if((paramData \\ "@constant").isEmpty){
                                                        <b>Constant: </b> <i>true;</i>
                                                    } else{
                                                        <b>Constant: </b> <i>false;</i>
                                                    }}
                                            </td></tr></span>
                                    <ul>
                                        <li>{if((paramData\\"notes").isEmpty){} else{
                                                    <tr><td class="main_under" style="width:80%;">Notes:</td>
                                                        <td class="sub_main_under" style="width:100%;">
                                                            {for(val x <- paramData\\"notes") yield {
                                                                    val react_notes = x.toString
                                                                    if(react_notes.contains("body")){
                                                                        val var2 = react_notes.replaceAll("body", "div")
                                                                        v1 = var2.replaceAll("notes", "div")
                                                                    } else {
                                                                        v1 = react_notes.replaceAll("notes", "div")
                                                                    }
                                                                    val reaction_notes_p = XML.loadString(v1)
                                                                    <b>{reaction_notes_p}</b>}}</td></tr>}}
                                        </li></ul></li></ul>}
                    },
                     "extraParametersData" -> {
                        for(val extraParamData <- modelSBML \ "model" \ "listOfReactions" \ "reaction";
                            val extraParamDataId <- extraParamData \ "@id") yield {
                            <li>{val extra = extraParamData \ "kineticLaw" \ "listOfParameters" \ "parameter"
                                 if( extra.isEmpty){
                                    } else{
                                        <span><h2>&nbsp;&nbsp;&nbsp;{extraParamData \\ "@name"}</h2></span>
                                        <ul><li class="closed">
                                                <table class="browseModelTable" id="modelTable_Overview">
                                                    <tbody>
                                                        <ul><h8><a onclick="window.open('../models/editModel.html','Edit Model','location=no, resizable=no, scrollbars=no,width=800,height=500');">Edit</a>
                                                                <a onclick="window.open('../models/new_comment.html','New Comment','location=no, resizable=no, scrollbars=no,width=600,height=300');">Make Comment</a>
                                                                <a onclick="window.open('../models/view_comments.html','View Comments','location=no, resizable=no, scrollbars=yes, width=500,height=600');">View Comments</a></h8>
                                                            <li class="closed"><span><tr>{
                                                                            if((extra \\ "@name").isEmpty){
                                                                                <td class="main">{extra \\ "@id"}</td>} else{
                                                                                <td class="main">{extra \\ "@name"}</td>}}
                                                                        <td class="sub_main" style="width:100%;"><br />{
                                                                                if((extra \\ "@value").isEmpty){
                                                                                } else {
                                                                                    <b>Value: </b><i>{extra \\ "@value"};  </i>
                                                                                }}{
                                                                                if((extra \\ "@constant").isEmpty){
                                                                                    <b>Constant: </b> <i>true;</i>
                                                                                } else{
                                                                                    <b>Constant: </b> <i>false;</i>
                                                                                }}
                                                                        </td></tr></span>
                                                                <ul>
                                                                    <li>{if((extra\\"notes").isEmpty){} else{
                                                                                <tr><td class="main_under" style="width:80%;">Notes:</td>
                                                                                    <td class="sub_main_under" style="width:100%;">
                                                                                        {for(val x <- extra \\"notes") yield {
                                                                                                val react_notes = x.toString
                                                                                                if(react_notes.contains("body")){
                                                                                                    val var2 = react_notes.replaceAll("body", "div")
                                                                                                    v1 = var2.replaceAll("notes", "div")
                                                                                                } else {
                                                                                                    v1 = react_notes.replaceAll("notes", "div")
                                                                                                }
                                                                                                val reaction_notes_p = XML.loadString(v1)
                                                                                                <b>{reaction_notes_p}</b>}}</td></tr>}}
                                                                    </li></ul></li></ul>
                                                    </tbody>
                                                </table>
                                            </li>
                                        </ul>
                
                                    }}</li>
                        }}
                )
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
