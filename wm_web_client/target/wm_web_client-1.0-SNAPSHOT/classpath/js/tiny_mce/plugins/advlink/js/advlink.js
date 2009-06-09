tinyMCEPopup.requireLangPack();
var templates={"window.open":"window.open('${url}','${target}','${options}')"};
function preinit(){var A;
if(A=tinyMCEPopup.getParam("external_link_list_url")){document.write('<script language="javascript" type="text/javascript" src="'+tinyMCEPopup.editor.documentBaseURI.toAbsolute(A)+'"><\/script>')
}}function changeClass(){var A=document.forms[0];
A.classes.value=getSelectValue(A,"classlist")
}function init(){tinyMCEPopup.resizeToInnerSize();
var A=document.forms[0];
var F=tinyMCEPopup.editor;
var G=F.selection.getNode();
var E="insert";
var D;
document.getElementById("hrefbrowsercontainer").innerHTML=getBrowserHTML("hrefbrowser","href","file","advlink");
document.getElementById("popupurlbrowsercontainer").innerHTML=getBrowserHTML("popupurlbrowser","popupurl","file","advlink");
document.getElementById("linklisthrefcontainer").innerHTML=getLinkListHTML("linklisthref","href");
document.getElementById("anchorlistcontainer").innerHTML=getAnchorListHTML("anchorlist","href");
document.getElementById("targetlistcontainer").innerHTML=getTargetListHTML("targetlist","target");
D=getLinkListHTML("linklisthref","href");
if(D==""){document.getElementById("linklisthrefrow").style.display="none"
}else{document.getElementById("linklisthrefcontainer").innerHTML=D
}if(isVisible("hrefbrowser")){document.getElementById("href").style.width="260px"
}if(isVisible("popupurlbrowser")){document.getElementById("popupurl").style.width="180px"
}G=F.dom.getParent(G,"A");
if(G!=null&&G.nodeName=="A"){E="update"
}A.insert.value=tinyMCEPopup.getLang(E,"Insert",true);
setPopupControlsDisabled(true);
if(E=="update"){var B=F.dom.getAttrib(G,"href");
var C=F.dom.getAttrib(G,"onclick");
setFormValue("href",B);
setFormValue("title",F.dom.getAttrib(G,"title"));
setFormValue("id",F.dom.getAttrib(G,"id"));
setFormValue("style",F.dom.getAttrib(G,"style"));
setFormValue("rel",F.dom.getAttrib(G,"rel"));
setFormValue("rev",F.dom.getAttrib(G,"rev"));
setFormValue("charset",F.dom.getAttrib(G,"charset"));
setFormValue("hreflang",F.dom.getAttrib(G,"hreflang"));
setFormValue("dir",F.dom.getAttrib(G,"dir"));
setFormValue("lang",F.dom.getAttrib(G,"lang"));
setFormValue("tabindex",F.dom.getAttrib(G,"tabindex",typeof (G.tabindex)!="undefined"?G.tabindex:""));
setFormValue("accesskey",F.dom.getAttrib(G,"accesskey",typeof (G.accesskey)!="undefined"?G.accesskey:""));
setFormValue("type",F.dom.getAttrib(G,"type"));
setFormValue("onfocus",F.dom.getAttrib(G,"onfocus"));
setFormValue("onblur",F.dom.getAttrib(G,"onblur"));
setFormValue("onclick",C);
setFormValue("ondblclick",F.dom.getAttrib(G,"ondblclick"));
setFormValue("onmousedown",F.dom.getAttrib(G,"onmousedown"));
setFormValue("onmouseup",F.dom.getAttrib(G,"onmouseup"));
setFormValue("onmouseover",F.dom.getAttrib(G,"onmouseover"));
setFormValue("onmousemove",F.dom.getAttrib(G,"onmousemove"));
setFormValue("onmouseout",F.dom.getAttrib(G,"onmouseout"));
setFormValue("onkeypress",F.dom.getAttrib(G,"onkeypress"));
setFormValue("onkeydown",F.dom.getAttrib(G,"onkeydown"));
setFormValue("onkeyup",F.dom.getAttrib(G,"onkeyup"));
setFormValue("target",F.dom.getAttrib(G,"target"));
setFormValue("classes",F.dom.getAttrib(G,"class"));
if(C!=null&&C.indexOf("window.open")!=-1){parseWindowOpen(C)
}else{parseFunction(C)
}selectByValue(A,"dir",F.dom.getAttrib(G,"dir"));
selectByValue(A,"rel",F.dom.getAttrib(G,"rel"));
selectByValue(A,"rev",F.dom.getAttrib(G,"rev"));
selectByValue(A,"linklisthref",B);
if(B.charAt(0)=="#"){selectByValue(A,"anchorlist",B)
}addClassesToList("classlist","advlink_styles");
selectByValue(A,"classlist",F.dom.getAttrib(G,"class"),true);
selectByValue(A,"targetlist",F.dom.getAttrib(G,"target"),true)
}else{addClassesToList("classlist","advlink_styles")
}}function checkPrefix(A){if(A.value&&Validator.isEmail(A)&&!/^\s*mailto:/i.test(A.value)&&confirm(tinyMCEPopup.getLang("advlink_dlg.is_email"))){A.value="mailto:"+A.value
}if(/^\s*www\./i.test(A.value)&&confirm(tinyMCEPopup.getLang("advlink_dlg.is_external"))){A.value="http://"+A.value
}}function setFormValue(A,B){document.forms[0].elements[A].value=B
}function parseWindowOpen(C){var A=document.forms[0];
if(C.indexOf("return false;")!=-1){A.popupreturn.checked=true;
C=C.replace("return false;","")
}else{A.popupreturn.checked=false
}var E=parseLink(C);
if(E!=null){A.ispopup.checked=true;
setPopupControlsDisabled(false);
var D=parseOptions(E.options);
var B=E.url;
A.popupname.value=E.target;
A.popupurl.value=B;
A.popupwidth.value=getOption(D,"width");
A.popupheight.value=getOption(D,"height");
A.popupleft.value=getOption(D,"left");
A.popuptop.value=getOption(D,"top");
if(A.popupleft.value.indexOf("screen")!=-1){A.popupleft.value="c"
}if(A.popuptop.value.indexOf("screen")!=-1){A.popuptop.value="c"
}A.popuplocation.checked=getOption(D,"location")=="yes";
A.popupscrollbars.checked=getOption(D,"scrollbars")=="yes";
A.popupmenubar.checked=getOption(D,"menubar")=="yes";
A.popupresizable.checked=getOption(D,"resizable")=="yes";
A.popuptoolbar.checked=getOption(D,"toolbar")=="yes";
A.popupstatus.checked=getOption(D,"status")=="yes";
A.popupdependent.checked=getOption(D,"dependent")=="yes";
buildOnClick()
}}function parseFunction(B){var A=document.forms[0];
var C=parseLink(B)
}function getOption(B,A){return typeof (B[A])=="undefined"?"":B[A]
}function setPopupControlsDisabled(B){var A=document.forms[0];
A.popupname.disabled=B;
A.popupurl.disabled=B;
A.popupwidth.disabled=B;
A.popupheight.disabled=B;
A.popupleft.disabled=B;
A.popuptop.disabled=B;
A.popuplocation.disabled=B;
A.popupscrollbars.disabled=B;
A.popupmenubar.disabled=B;
A.popupresizable.disabled=B;
A.popuptoolbar.disabled=B;
A.popupstatus.disabled=B;
A.popupreturn.disabled=B;
A.popupdependent.disabled=B;
setBrowserDisabled("popupurlbrowser",B)
}function parseLink(F){F=F.replace(new RegExp("&#39;","g"),"'");
var A=F.replace(new RegExp("\\s*([A-Za-z0-9.]*)\\s*\\(.*","gi"),"$1");
var H=templates[A];
if(H){var G=H.match(new RegExp("'?\\$\\{[A-Za-z0-9.]*\\}'?","gi"));
var E="\\s*[A-Za-z0-9.]*\\s*\\(";
var D="";
for(var C=0;
C<G.length;
C++){if(G[C].indexOf("'${")!=-1){E+="'(.*)'"
}else{E+="([0-9]*)"
}D+="$"+(C+1);
G[C]=G[C].replace(new RegExp("[^A-Za-z0-9]","gi"),"");
if(C!=G.length-1){E+="\\s*,\\s*";
D+="<delim>"
}else{E+=".*"
}}E+="\\);?";
var I=[];
I._function=A;
var B=F.replace(new RegExp(E,"gi"),D).split("<delim>");
for(var C=0;
C<G.length;
C++){I[G[C]]=B[C]
}return I
}return null
}function parseOptions(C){if(C==null||C==""){return[]
}C=C.toLowerCase();
C=C.replace(/;/g,",");
C=C.replace(/[^0-9a-z=,]/g,"");
var E=C.split(",");
var A=[];
for(var B=0;
B<E.length;
B++){var D=E[B].split("=");
if(D.length==2){A[D[0]]=D[1]
}}return A
}function buildOnClick(){var A=document.forms[0];
if(!A.ispopup.checked){A.onclick.value="";
return 
}var C="window.open('";
var B=A.popupurl.value;
C+=B+"','";
C+=A.popupname.value+"','";
if(A.popuplocation.checked){C+="location=yes,"
}if(A.popupscrollbars.checked){C+="scrollbars=yes,"
}if(A.popupmenubar.checked){C+="menubar=yes,"
}if(A.popupresizable.checked){C+="resizable=yes,"
}if(A.popuptoolbar.checked){C+="toolbar=yes,"
}if(A.popupstatus.checked){C+="status=yes,"
}if(A.popupdependent.checked){C+="dependent=yes,"
}if(A.popupwidth.value!=""){C+="width="+A.popupwidth.value+","
}if(A.popupheight.value!=""){C+="height="+A.popupheight.value+","
}if(A.popupleft.value!=""){if(A.popupleft.value!="c"){C+="left="+A.popupleft.value+","
}else{C+="left='+(screen.availWidth/2-"+(A.popupwidth.value/2)+")+',"
}}if(A.popuptop.value!=""){if(A.popuptop.value!="c"){C+="top="+A.popuptop.value+","
}else{C+="top='+(screen.availHeight/2-"+(A.popupheight.value/2)+")+',"
}}if(C.charAt(C.length-1)==","){C=C.substring(0,C.length-1)
}C+="');";
if(A.popupreturn.checked){C+="return false;"
}A.onclick.value=C;
if(A.href.value==""){A.href.value=B
}}function setAttrib(F,D,C){var B=document.forms[0];
var A=B.elements[D.toLowerCase()];
var E=tinyMCEPopup.editor.dom;
if(typeof (C)=="undefined"||C==null){C="";
if(A){C=A.value
}}if(D=="style"){C=E.serializeStyle(E.parseStyle(C))
}E.setAttrib(F,D,C)
}function getAnchorListHTML(G,F){var E=tinyMCEPopup.editor;
var A=E.dom.select("a.mceItemAnchor,img.mceItemAnchor"),B,D;
var C="";
C+='<select id="'+G+'" name="'+G+'" class="mceAnchorList" o2nfocus="tinyMCE.addSelectAccessibility(event, this, window);" onchange="this.form.'+F+".value=";
C+='this.options[this.selectedIndex].value;">';
C+='<option value="">---</option>';
for(D=0;
D<A.length;
D++){if((B=E.dom.getAttrib(A[D],"name"))!=""){C+='<option value="#'+B+'">'+B+"</option>"
}}C+="</select>";
return C
}function insertAction(){var C=tinyMCEPopup.editor;
var D,A,B;
D=C.selection.getNode();
checkPrefix(document.forms[0].href);
D=C.dom.getParent(D,"A");
if(!document.forms[0].href.value){tinyMCEPopup.execCommand("mceBeginUndoLevel");
B=C.selection.getBookmark();
C.dom.remove(D,1);
C.selection.moveToBookmark(B);
tinyMCEPopup.execCommand("mceEndUndoLevel");
tinyMCEPopup.close();
return 
}tinyMCEPopup.execCommand("mceBeginUndoLevel");
if(D==null){C.getDoc().execCommand("unlink",false,null);
tinyMCEPopup.execCommand("CreateLink",false,"#mce_temp_url#",{skip_undo:1});
A=tinymce.grep(C.dom.select("a"),function(E){return C.dom.getAttrib(E,"href")=="#mce_temp_url#"
});
for(B=0;
B<A.length;
B++){setAllAttribs(D=A[B])
}}else{setAllAttribs(D)
}if(D.childNodes.length!=1||D.firstChild.nodeName!="IMG"){C.focus();
C.selection.select(D);
C.selection.collapse(0);
tinyMCEPopup.storeSelection()
}tinyMCEPopup.execCommand("mceEndUndoLevel");
tinyMCEPopup.close()
}function setAllAttribs(D){var A=document.forms[0];
var B=A.href.value;
var C=getSelectValue(A,"targetlist");
setAttrib(D,"href",B);
setAttrib(D,"title");
setAttrib(D,"target",C=="_self"?"":C);
setAttrib(D,"id");
setAttrib(D,"style");
setAttrib(D,"class",getSelectValue(A,"classlist"));
setAttrib(D,"rel");
setAttrib(D,"rev");
setAttrib(D,"charset");
setAttrib(D,"hreflang");
setAttrib(D,"dir");
setAttrib(D,"lang");
setAttrib(D,"tabindex");
setAttrib(D,"accesskey");
setAttrib(D,"type");
setAttrib(D,"onfocus");
setAttrib(D,"onblur");
setAttrib(D,"onclick");
setAttrib(D,"ondblclick");
setAttrib(D,"onmousedown");
setAttrib(D,"onmouseup");
setAttrib(D,"onmouseover");
setAttrib(D,"onmousemove");
setAttrib(D,"onmouseout");
setAttrib(D,"onkeypress");
setAttrib(D,"onkeydown");
setAttrib(D,"onkeyup");
if(tinyMCE.isMSIE5){D.outerHTML=D.outerHTML
}}function getSelectValue(A,B){var C=A.elements[B];
if(!C||C.options==null||C.selectedIndex==-1){return""
}return C.options[C.selectedIndex].value
}function getLinkListHTML(D,C,E){if(typeof (tinyMCELinkList)=="undefined"||tinyMCELinkList.length==0){return""
}var B="";
B+='<select id="'+D+'" name="'+D+'"';
B+=' class="mceLinkList" onfoc2us="tinyMCE.addSelectAccessibility(event, this, window);" onchange="this.form.'+C+".value=";
B+="this.options[this.selectedIndex].value;";
if(typeof (E)!="undefined"){B+=E+"('"+C+"',this.options[this.selectedIndex].text,this.options[this.selectedIndex].value);"
}B+='"><option value="">---</option>';
for(var A=0;
A<tinyMCELinkList.length;
A++){B+='<option value="'+tinyMCELinkList[A][1]+'">'+tinyMCELinkList[A][0]+"</option>"
}B+="</select>";
return B
}function getTargetListHTML(F,E){var A=tinyMCEPopup.getParam("theme_advanced_link_targets","").split(";");
var D="";
D+='<select id="'+F+'" name="'+F+'" onf2ocus="tinyMCE.addSelectAccessibility(event, this, window);" onchange="this.form.'+E+".value=";
D+='this.options[this.selectedIndex].value;">';
D+='<option value="_self">'+tinyMCEPopup.getLang("advlink_dlg.target_same")+"</option>";
D+='<option value="_blank">'+tinyMCEPopup.getLang("advlink_dlg.target_blank")+" (_blank)</option>";
D+='<option value="_parent">'+tinyMCEPopup.getLang("advlink_dlg.target_parent")+" (_parent)</option>";
D+='<option value="_top">'+tinyMCEPopup.getLang("advlink_dlg.target_top")+" (_top)</option>";
for(var C=0;
C<A.length;
C++){var B,G;
if(A[C]==""){continue
}B=A[C].split("=")[0];
G=A[C].split("=")[1];
D+='<option value="'+B+'">'+G+" ("+B+")</option>"
}D+="</select>";
return D
}preinit();
tinyMCEPopup.onInit.add(init);