/*
 * Copyright (c) 2012. Alexandre Martins. All rights reserved.
 */

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 01/02/12
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
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
    return xhttp.responseXML.documentElement;
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
        var serializer = new XMLSerializer();
        //alert(xsl);
        //alert(serializer.serializeToString(xsl));
        //var xsltProcessor=new XSLTProcessor();
        //alert(xsltProcessor);


        //xsltProcessor.importStylesheet(xsl);

        xsltProcessor = getProcessor(xsl); //fixes webkit's issues
        //alert(xsltProcessor);
        var resultDocument = xsltProcessor.transformToFragment(xml,document);

        //if(resultDocument == null)
            //alert("resutlDocuent is null!!!!")
        //alert(resultDocument);
        //alert(serializer.serializeToString(resultDocument) );

        var div = document.getElementById("formula");
        div.replaceChild(resultDocument, div.firstChild );
    }
}


//funtion taken from http://www.sitepoint.com/forums/showthread.php?769873-transformToFragment-on-webkit-browsers
//they

function fixWebKitStylesheet(doc)
{
    // ensure output method = html:
    var node = doc.selectSingleNode("/xsl:stylesheet/xsl:output");
    if (node && node.getAttribute("method")=="xml")
        node.setAttribute("method", "html");

    // gut out document node template to deal with Webkit bug
    // Google: webkit bug 28744 "root one"
    if ((node=doc.selectSingleNode("/xsl:stylesheet/xsl:template[@match='/']")))
    {
        var child;
        while ((child=node.lastChild))
            node.removeChild(child);
        child = node.appendChild(doc.createElement("xsl:apply-templates"));
        child.setAttribute("select", "*");
    }
}

function getProcessor(doc)
{
    // detect WebKit implementation (incomplete example pulled out
    // of other code that handles cross-browser XMLDOM differences):
    var d = document.implementation.createDocument("","",null);
    var isWebKit = !(d.load);

    var proc = new XSLTProcessor();
    if (isWebKit)
        fixWebKitStylesheet(doc);

    proc.importStylesheet(doc);

    return proc;
}
