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
        var serializer = new XMLSerializer();
        alert(serializer.serializeToString(resultDocument) )

        var div = document.getElementById("formula");
        div.replaceChild(resultDocument, div.firstChild );
    }
}
