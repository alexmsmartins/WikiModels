tinyMCEPopup.requireLangPack();
var ed;
function init(){ed=tinyMCEPopup.editor;
tinyMCEPopup.resizeToInnerSize();
document.getElementById("backgroundimagebrowsercontainer").innerHTML=getBrowserHTML("backgroundimagebrowser","backgroundimage","image","table");
document.getElementById("bordercolor_pickcontainer").innerHTML=getColorPickerHTML("bordercolor_pick","bordercolor");
document.getElementById("bgcolor_pickcontainer").innerHTML=getColorPickerHTML("bgcolor_pick","bgcolor");
var G=ed;
var I=ed.dom.getParent(ed.selection.getNode(),"td,th");
var H=document.forms[0];
var O=ed.dom.parseStyle(ed.dom.getAttrib(I,"style"));
var E=I.nodeName.toLowerCase();
var J=ed.dom.getAttrib(I,"align");
var L=ed.dom.getAttrib(I,"valign");
var B=trimSize(getStyle(I,"width","width"));
var M=trimSize(getStyle(I,"height","height"));
var F=convertRGBToHex(getStyle(I,"bordercolor","borderLeftColor"));
var Q=convertRGBToHex(getStyle(I,"bgcolor","backgroundColor"));
var K=ed.dom.getAttrib(I,"class");
var P=getStyle(I,"background","backgroundImage").replace(new RegExp("url\\('?([^']*)'?\\)","gi"),"$1");
var A=ed.dom.getAttrib(I,"id");
var C=ed.dom.getAttrib(I,"lang");
var D=ed.dom.getAttrib(I,"dir");
var N=ed.dom.getAttrib(I,"scope");
addClassesToList("class","table_cell_styles");
TinyMCE_EditableSelects.init();
H.bordercolor.value=F;
H.bgcolor.value=Q;
H.backgroundimage.value=P;
H.width.value=B;
H.height.value=M;
H.id.value=A;
H.lang.value=C;
H.style.value=ed.dom.serializeStyle(O);
selectByValue(H,"align",J);
selectByValue(H,"valign",L);
selectByValue(H,"class",K,true,true);
selectByValue(H,"celltype",E);
selectByValue(H,"dir",D);
selectByValue(H,"scope",N);
if(isVisible("backgroundimagebrowser")){document.getElementById("backgroundimage").style.width="180px"
}updateColor("bordercolor_pick","bordercolor");
updateColor("bgcolor_pick","bgcolor")
}function updateAction(){var A,D=ed,F,I,G,E=document.forms[0];
tinyMCEPopup.restoreSelection();
A=ed.selection.getNode();
F=ed.dom.getParent(A,"td,th");
I=ed.dom.getParent(A,"tr");
G=ed.dom.getParent(A,"table");
ed.execCommand("mceBeginUndoLevel");
switch(getSelectValue(E,"action")){case"cell":var C=getSelectValue(E,"celltype");
var K=getSelectValue(E,"scope");
function J(M){if(M){updateCell(F);
ed.addVisual();
ed.nodeChanged();
D.execCommand("mceEndUndoLevel");
tinyMCEPopup.close()
}}if(ed.getParam("accessibility_warnings",1)){if(C=="th"&&K==""){tinyMCEPopup.confirm(ed.getLang("table_dlg.missing_scope","",true),J)
}else{J(1)
}return 
}updateCell(F);
break;
case"row":var H=I.firstChild;
if(H.nodeName!="TD"&&H.nodeName!="TH"){H=nextCell(H)
}do{H=updateCell(H,true)
}while((H=nextCell(H))!=null);
break;
case"all":var L=G.getElementsByTagName("tr");
for(var B=0;
B<L.length;
B++){var H=L[B].firstChild;
if(H.nodeName!="TD"&&H.nodeName!="TH"){H=nextCell(H)
}do{H=updateCell(H,true)
}while((H=nextCell(H))!=null)
}break
}ed.addVisual();
ed.nodeChanged();
D.execCommand("mceEndUndoLevel");
tinyMCEPopup.close()
}function nextCell(A){while((A=A.nextSibling)!=null){if(A.nodeName=="TD"||A.nodeName=="TH"){return A
}}return null
}function updateCell(B,H){var F=ed;
var G=document.forms[0];
var A=B.nodeName.toLowerCase();
var E=getSelectValue(G,"celltype");
var K=F.getDoc();
var C=ed.dom;
if(!H){B.setAttribute("id",G.id.value)
}B.setAttribute("align",G.align.value);
B.setAttribute("vAlign",G.valign.value);
B.setAttribute("lang",G.lang.value);
B.setAttribute("dir",getSelectValue(G,"dir"));
B.setAttribute("style",ed.dom.serializeStyle(ed.dom.parseStyle(G.style.value)));
B.setAttribute("scope",G.scope.value);
ed.dom.setAttrib(B,"class",getSelectValue(G,"class"));
ed.dom.setAttrib(B,"width","");
ed.dom.setAttrib(B,"height","");
ed.dom.setAttrib(B,"bgColor","");
ed.dom.setAttrib(B,"borderColor","");
ed.dom.setAttrib(B,"background","");
B.style.width=getCSSSize(G.width.value);
B.style.height=getCSSSize(G.height.value);
if(G.bordercolor.value!=""){B.style.borderColor=G.bordercolor.value;
B.style.borderStyle=B.style.borderStyle==""?"solid":B.style.borderStyle;
B.style.borderWidth=B.style.borderWidth==""?"1px":B.style.borderWidth
}else{B.style.borderColor=""
}B.style.backgroundColor=G.bgcolor.value;
if(G.backgroundimage.value!=""){B.style.backgroundImage="url('"+G.backgroundimage.value+"')"
}else{B.style.backgroundImage=""
}if(A!=E){var D=K.createElement(E);
for(var I=0;
I<B.childNodes.length;
I++){D.appendChild(B.childNodes[I].cloneNode(1))
}for(var J=0;
J<B.attributes.length;
J++){ed.dom.setAttrib(D,B.attributes[J].name,ed.dom.getAttrib(B,B.attributes[J].name))
}B.parentNode.replaceChild(D,B);
B=D
}C.setAttrib(B,"style",C.serializeStyle(C.parseStyle(B.style.cssText)));
return B
}function changedBackgroundImage(){var A=document.forms[0];
var B=ed.dom.parseStyle(A.style.value);
B["background-image"]="url('"+A.backgroundimage.value+"')";
A.style.value=ed.dom.serializeStyle(B)
}function changedSize(){var B=document.forms[0];
var C=ed.dom.parseStyle(B.style.value);
var D=B.width.value;
if(D!=""){C.width=getCSSSize(D)
}else{C.width=""
}var A=B.height.value;
if(A!=""){C.height=getCSSSize(A)
}else{C.height=""
}B.style.value=ed.dom.serializeStyle(C)
}function changedColor(){var A=document.forms[0];
var B=ed.dom.parseStyle(A.style.value);
B["background-color"]=A.bgcolor.value;
B["border-color"]=A.bordercolor.value;
A.style.value=ed.dom.serializeStyle(B)
}function changedStyle(){var A=document.forms[0];
var B=ed.dom.parseStyle(A.style.value);
if(B["background-image"]){A.backgroundimage.value=B["background-image"].replace(new RegExp("url\\('?([^']*)'?\\)","gi"),"$1")
}else{A.backgroundimage.value=""
}if(B.width){A.width.value=trimSize(B.width)
}if(B.height){A.height.value=trimSize(B.height)
}if(B["background-color"]){A.bgcolor.value=B["background-color"];
updateColor("bgcolor_pick","bgcolor")
}if(B["border-color"]){A.bordercolor.value=B["border-color"];
updateColor("bordercolor_pick","bordercolor")
}}tinyMCEPopup.onInit.add(init);