tinyMCEPopup.requireLangPack();
var action,orgTableWidth,orgTableHeight,dom=tinyMCEPopup.editor.dom;
function insertTable(){var I=document.forms[0];
var E=tinyMCEPopup.editor,U=E.dom;
var M=2,K=2,S=0,V=-1,B=-1,T,Q,P,D,W,O,L;
var N="",R,F;
var X,Y,C;
tinyMCEPopup.restoreSelection();
if(!AutoValidator.validate(I)){tinyMCEPopup.alert(E.getLang("invalid_data"));
return false
}F=U.getParent(E.selection.getNode(),"table");
M=I.elements.cols.value;
K=I.elements.rows.value;
S=I.elements.border.value!=""?I.elements.border.value:0;
V=I.elements.cellpadding.value!=""?I.elements.cellpadding.value:"";
B=I.elements.cellspacing.value!=""?I.elements.cellspacing.value:"";
T=I.elements.align.options[I.elements.align.selectedIndex].value;
O=I.elements.frame.options[I.elements.frame.selectedIndex].value;
L=I.elements.rules.options[I.elements.rules.selectedIndex].value;
Q=I.elements.width.value;
P=I.elements.height.value;
bordercolor=I.elements.bordercolor.value;
bgcolor=I.elements.bgcolor.value;
D=I.elements["class"].options[I.elements["class"].selectedIndex].value;
id=I.elements.id.value;
summary=I.elements.summary.value;
style=I.elements.style.value;
dir=I.elements.dir.value;
lang=I.elements.lang.value;
background=I.elements.backgroundimage.value;
W=I.elements.caption.checked;
X=tinyMCEPopup.getParam("table_cell_limit",false);
Y=tinyMCEPopup.getParam("table_row_limit",false);
C=tinyMCEPopup.getParam("table_col_limit",false);
if(C&&M>C){tinyMCEPopup.alert(E.getLang("table_dlg.col_limit").replace(/\{\$cols\}/g,C));
return false
}else{if(Y&&K>Y){tinyMCEPopup.alert(E.getLang("table_dlg.row_limit").replace(/\{\$rows\}/g,Y));
return false
}else{if(X&&M*K>X){tinyMCEPopup.alert(E.getLang("table_dlg.cell_limit").replace(/\{\$cells\}/g,X));
return false
}}}if(action=="update"){E.execCommand("mceBeginUndoLevel");
U.setAttrib(F,"cellPadding",V,true);
U.setAttrib(F,"cellSpacing",B,true);
U.setAttrib(F,"border",S);
U.setAttrib(F,"align",T);
U.setAttrib(F,"frame",O);
U.setAttrib(F,"rules",L);
U.setAttrib(F,"class",D);
U.setAttrib(F,"style",style);
U.setAttrib(F,"id",id);
U.setAttrib(F,"summary",summary);
U.setAttrib(F,"dir",dir);
U.setAttrib(F,"lang",lang);
R=E.dom.select("caption",F)[0];
if(R&&!W){R.parentNode.removeChild(R)
}if(!R&&W){R=F.ownerDocument.createElement("caption");
if(!tinymce.isIE){R.innerHTML='<br mce_bogus="1"/>'
}F.insertBefore(R,F.firstChild)
}if(Q&&E.settings.inline_styles){U.setStyle(F,"width",Q);
U.setAttrib(F,"width","")
}else{U.setAttrib(F,"width",Q,true);
U.setStyle(F,"width","")
}U.setAttrib(F,"borderColor","");
U.setAttrib(F,"bgColor","");
U.setAttrib(F,"background","");
if(P&&E.settings.inline_styles){U.setStyle(F,"height",P);
U.setAttrib(F,"height","")
}else{U.setAttrib(F,"height",P,true);
U.setStyle(F,"height","")
}if(background!=""){F.style.backgroundImage="url('"+background+"')"
}else{F.style.backgroundImage=""
}if(bordercolor!=""){F.style.borderColor=bordercolor;
F.style.borderStyle=F.style.borderStyle==""?"solid":F.style.borderStyle;
F.style.borderWidth=S==""?"1px":S
}else{F.style.borderColor=""
}F.style.backgroundColor=bgcolor;
F.style.height=getCSSSize(P);
E.addVisual();
E.nodeChanged();
E.execCommand("mceEndUndoLevel");
if(I.width.value!=orgTableWidth||I.height.value!=orgTableHeight){E.execCommand("mceRepaint")
}tinyMCEPopup.close();
return true
}N+="<table";
N+=makeAttrib("id",id);
N+=makeAttrib("border",S);
N+=makeAttrib("cellpadding",V);
N+=makeAttrib("cellspacing",B);
if(Q&&E.settings.inline_styles){if(style){style+="; "
}if(/[0-9\.]+/.test(Q)){Q+="px"
}style+="width: "+Q
}else{N+=makeAttrib("width",Q)
}N+=makeAttrib("align",T);
N+=makeAttrib("frame",O);
N+=makeAttrib("rules",L);
N+=makeAttrib("class",D);
N+=makeAttrib("style",style);
N+=makeAttrib("summary",summary);
N+=makeAttrib("dir",dir);
N+=makeAttrib("lang",lang);
N+=">";
if(W){if(!tinymce.isIE){N+='<caption><br mce_bogus="1"/></caption>'
}else{N+="<caption></caption>"
}}for(var H=0;
H<K;
H++){N+="<tr>";
for(var J=0;
J<M;
J++){if(!tinymce.isIE){N+='<td><br mce_bogus="1"/></td>'
}else{N+="<td></td>"
}}N+="</tr>"
}N+="</table>";
E.execCommand("mceBeginUndoLevel");
if(E.settings.fix_table_elements){var A=E.selection.getBookmark(),G="";
E.execCommand("mceInsertContent",false,'<br class="_mce_marker" />');
tinymce.each("h1,h2,h3,h4,h5,h6,p".split(","),function(Z){if(G){G+=","
}G+=Z+" ._mce_marker"
});
tinymce.each(E.dom.select(G),function(Z){E.dom.split(E.dom.getParent(Z,"h1,h2,h3,h4,h5,h6,p"),Z)
});
U.setOuterHTML(U.select("._mce_marker")[0],N);
E.selection.moveToBookmark(A)
}else{E.execCommand("mceInsertContent",false,N)
}E.addVisual();
E.execCommand("mceEndUndoLevel");
tinyMCEPopup.close()
}function makeAttrib(D,C){var B=document.forms[0];
var A=B.elements[D];
if(typeof (C)=="undefined"||C==null){C="";
if(A){C=A.value
}}if(C==""){return""
}C=C.replace(/&/g,"&amp;");
C=C.replace(/\"/g,"&quot;");
C=C.replace(/</g,"&lt;");
C=C.replace(/>/g,"&gt;");
return" "+D+'="'+C+'"'
}function init(){tinyMCEPopup.resizeToInnerSize();
document.getElementById("backgroundimagebrowsercontainer").innerHTML=getBrowserHTML("backgroundimagebrowser","backgroundimage","image","table");
document.getElementById("backgroundimagebrowsercontainer").innerHTML=getBrowserHTML("backgroundimagebrowser","backgroundimage","image","table");
document.getElementById("bordercolor_pickcontainer").innerHTML=getColorPickerHTML("bordercolor_pick","bordercolor");
document.getElementById("bgcolor_pickcontainer").innerHTML=getColorPickerHTML("bgcolor_pick","bgcolor");
var J=2,I=2,Q=tinyMCEPopup.getParam("table_default_border","0"),X=tinyMCEPopup.getParam("table_default_cellpadding",""),B=tinyMCEPopup.getParam("table_default_cellspacing","");
var R="",P="",M="",U="",A="",C="";
var N="",F="",V="",O="",Y="",W="",A="",U="",H,L;
var D=tinyMCEPopup.editor,T=D.dom;
var G=document.forms[0];
var E=T.getParent(D.selection.getNode(),"table");
action=tinyMCEPopup.getWindowArg("action");
if(!action){action=E?"update":"insert"
}if(E&&action!="insert"){var K=E.rows;
var J=0;
for(var S=0;
S<K.length;
S++){if(K[S].cells.length>J){J=K[S].cells.length
}}J=J;
I=K.length;
st=T.parseStyle(T.getAttrib(E,"style"));
Q=trimSize(getStyle(E,"border","borderWidth"));
X=T.getAttrib(E,"cellpadding","");
B=T.getAttrib(E,"cellspacing","");
P=trimSize(getStyle(E,"width","width"));
M=trimSize(getStyle(E,"height","height"));
U=convertRGBToHex(getStyle(E,"bordercolor","borderLeftColor"));
A=convertRGBToHex(getStyle(E,"bgcolor","backgroundColor"));
R=T.getAttrib(E,"align",R);
L=T.getAttrib(E,"frame");
H=T.getAttrib(E,"rules");
C=tinymce.trim(T.getAttrib(E,"class").replace(/mceItem.+/g,""));
N=T.getAttrib(E,"id");
F=T.getAttrib(E,"summary");
V=T.serializeStyle(st);
O=T.getAttrib(E,"dir");
Y=T.getAttrib(E,"lang");
W=getStyle(E,"background","backgroundImage").replace(new RegExp("url\\('?([^']*)'?\\)","gi"),"$1");
G.caption.checked=E.getElementsByTagName("caption").length>0;
orgTableWidth=P;
orgTableHeight=M;
action="update";
G.insert.value=D.getLang("update")
}addClassesToList("class","table_styles");
TinyMCE_EditableSelects.init();
selectByValue(G,"align",R);
selectByValue(G,"frame",L);
selectByValue(G,"rules",H);
selectByValue(G,"class",C,true,true);
G.cols.value=J;
G.rows.value=I;
G.border.value=Q;
G.cellpadding.value=X;
G.cellspacing.value=B;
G.width.value=P;
G.height.value=M;
G.bordercolor.value=U;
G.bgcolor.value=A;
G.id.value=N;
G.summary.value=F;
G.style.value=V;
G.dir.value=O;
G.lang.value=Y;
G.backgroundimage.value=W;
updateColor("bordercolor_pick","bordercolor");
updateColor("bgcolor_pick","bgcolor");
if(isVisible("backgroundimagebrowser")){document.getElementById("backgroundimage").style.width="180px"
}if(action=="update"){G.cols.disabled=true;
G.rows.disabled=true
}}function changedSize(){var B=document.forms[0];
var C=dom.parseStyle(B.style.value);
var A=B.height.value;
if(A!=""){C.height=getCSSSize(A)
}else{C.height=""
}B.style.value=dom.serializeStyle(C)
}function changedBackgroundImage(){var A=document.forms[0];
var B=dom.parseStyle(A.style.value);
B["background-image"]="url('"+A.backgroundimage.value+"')";
A.style.value=dom.serializeStyle(B)
}function changedBorder(){var A=document.forms[0];
var B=dom.parseStyle(A.style.value);
if(A.border.value!=""&&A.bordercolor.value!=""){B["border-width"]=A.border.value+"px"
}A.style.value=dom.serializeStyle(B)
}function changedColor(){var A=document.forms[0];
var B=dom.parseStyle(A.style.value);
B["background-color"]=A.bgcolor.value;
if(A.bordercolor.value!=""){B["border-color"]=A.bordercolor.value;
if(!B["border-width"]){B["border-width"]=A.border.value==""?"1px":A.border.value+"px"
}}A.style.value=dom.serializeStyle(B)
}function changedStyle(){var A=document.forms[0];
var B=dom.parseStyle(A.style.value);
if(B["background-image"]){A.backgroundimage.value=B["background-image"].replace(new RegExp("url\\('?([^']*)'?\\)","gi"),"$1")
}else{A.backgroundimage.value=""
}if(B.width){A.width.value=trimSize(B.width)
}if(B.height){A.height.value=trimSize(B.height)
}if(B["background-color"]){A.bgcolor.value=B["background-color"];
updateColor("bgcolor_pick","bgcolor")
}if(B["border-color"]){A.bordercolor.value=B["border-color"];
updateColor("bordercolor_pick","bordercolor")
}}tinyMCEPopup.onInit.add(init);