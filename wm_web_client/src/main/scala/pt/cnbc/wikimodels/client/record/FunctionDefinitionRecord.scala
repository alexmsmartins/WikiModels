/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.client.record

import pt.cnbc.wikimodels.dataModel.FunctionDefinition
import net.liftweb.http.S
import net.liftweb.record.{Notes, Name, Id, MetaId,Math}
import net.liftweb.common.{Empty, Box}
import xml.NodeSeq

/** TODO: Please document.
 *  @author: alex
 *  Date: 29-12-2011
 *  Time: 17:11 */
class FunctionDefinitionRecord() extends FunctionDefinition with SBaseRecord[FunctionDefinitionRecord]  {

  override def meta = FunctionDefinitionRecord

  override protected def relativeURLasList = "model" :: S.param("modelMetaId").openTheBox :: "functiondefinition" :: metaid :: Nil
  override protected def relativeCreationURLasList = "model" :: S.param("modelMetaId").openTheBox :: "functiondefinition" :: Nil


  //  ### can be validated with validate ###

  //  ### can be presented as XHtml, Json, or as a Form. ###

  override def toXHtml = {
    trace("Calling FunctionDefinitionRecord.toXHtml")
    <div>
      <head>
        <link type="text/css" rel="stylesheet" href="/css/sbml_present.css"></link>
      </head>
      {super.toXHtml}

    </div>
  }

  override def toForm(f:FunctionDefinitionRecord => Unit):NodeSeq = {
    trace("Calling FunctionDefinitionRecord.toForm( "+f+" )")
    <div class="species_toform">
      {super.toForm(f)}
      <!-- outside can't be a field and so I will make it a form -->
      <div>
        <lift:MathMLEdit form="POST">
          <head>
            <script id="xslttransform" type="text/javascript">
              /* <![CDATA[ */
                        function loadXMLDoc(dname)
                        {
              $.log("loadXMLDoc(dname) is executing")
              if (window.XMLHttpRequest)
              {
                xhttp=new XMLHttpRequest();
              }
              else
              {
                xhttp=new ActiveXObject("Microsoft.XMLHTTP");
              }
              xhttp.open("GET",dname,false);
              xhttp.send("");
              return xhttp.responseXML;
              }
                        function displayResult()
                        {
              $.log("displayResult is executing");

              var xml = null;
              // code for IE
              if (window.ActiveXObject)
              {
                xml = $("#formula").xml;
              }
              else if (document.implementation && document.implementation.createDocument)
              {
                var node = $("#formula");
                xml = node.children()[0];
              }

              var xsl=loadXMLDoc("/xslt/mathmlc2p.xsl");

              // code for IE
              if (window.ActiveXObject)
              {
                ex=xml.transformNode(xsl);
                document.getElementById("formula").innerHTML=ex;
              }
              // code for Mozilla, Firefox, Opera, etc.
              else if (document.implementation && document.implementation.createDocument)
              {
                var xsltProcessor=new XSLTProcessor();
                xsltProcessor.importStylesheet(xsl);
                var resultDocument = xsltProcessor.transformToFragment(xml,document);
                var div = document.getElementById("formula");
                div.replaceChild(resultDocument, div.firstChild );
              }
              }
                        /* ]]> */
    </script>
    </head>
    <script type="text/x-mathjax-config">
      /* <![CDATA[ */
                    $.log("MathJax is being configured.");
                    MathJax.Hub.Config({
      config: ["MMLorHTML.js"],
    jax: ["input/TeX","input/MathML","output/HTML-CSS","output/NativeMML"],
    extensions: ["tex2jax.js","mml2jax.js","MathMenu.js","MathZoom.js"],
    TeX: {
      extensions: ["AMSmath.js","AMSsymbols.js","noErrors.js","noUndefined.js"]
    },
    NativeMML: { showMathMenuMSIE: false },
    menuSettings: { zoom: "Double-Click" },
    errorSettings: { message: ["[Math Error]"] }
    });

                    /* ]]> */
    </script>

    <script type="text/javascript"
            src="http://cdn.mathjax.org/mathjax/latest/MathJax.js">
      /* <![CDATA[ */
                    $(document).ready(function() {
      displayResult();
      $.log("MathJax is executing");
      });
                    /* ]]> */
    </script>
    <lift:ignore>
      <!-- TODO LEARN error messages from the framework -->
    </lift:ignore>
        <lift:msgs id="parsing_error"/>

        <link rel="stylesheet" type="text/css" href="css/mathml_editor.css"/>
      <a href="http://www1.chapman.edu/~jipsen/asciimath.html">To get help in ASCIIMathML syntax click here.</a>

      <error:box>
        <span id="box_error_message"></span>
      </error:box>

      <div>
        <visualizer:formulaViz>
          <span id="form_viz"></span>
        </visualizer:formulaViz>
          <editor:formula/>
      </div>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <editor:submit />
    </lift:MathMLEdit>
    </div>
    </div>
  }

  //  ### will contain fields which can be listed with allFields. ###
  object metaIdO extends MetaId(this, 100)
  object idO extends Id(this, 100)
  object nameO extends Name(this, 100)
  object notesO extends Notes(this, 1000)
  object matho extends Math(this)
  //  ### can be created directly from a Request containing params with names that match the fields on a Record ( see fromReq ). ###

  var _parent:Box[SBMLModelRecord] = Empty
  //TODO isn't there a better way to override a var than THIS?!??! Fucking asInstanceOf
  override def parent:Box[SBMLModelRecord] = _parent.asInstanceOf[Box[SBMLModelRecord]]
  override def parent_=(p:Box[SBaseRecord[_]] ):Unit = {
    _parent = p.asInstanceOf[Box[SBMLModelRecord]]
  }

}



//TODO - DELETE IF NOT USED FOR ANYTHING
object FunctionDefinitionRecord extends FunctionDefinitionRecord with RestMetaRecord[FunctionDefinitionRecord] {
  def apply() = new FunctionDefinitionRecord
  override def fieldOrder = List(metaIdO, idO, nameO, /*sizeO,*/ notesO)
  override def fields = fieldOrder
}
