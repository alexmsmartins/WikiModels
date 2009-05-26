tinyMCEPopup.requireLangPack();
function init(){tinyMCEPopup.resizeToInnerSize();
document.getElementById("backgroundimagebrowsercontainer").innerHTML=getBrowserHTML("backgroundimagebrowser","backgroundimage","image","table");
document.getElementById("bgcolor_pickcontainer").innerHTML=getColorPickerHTML("bgcolor_pick","bgcolor");
var F=tinyMCEPopup.editor;
var E=F.dom;
var K=E.getParent(F.selection.getNode(),"tr");
var G=document.forms[0];
var M=E.parseStyle(E.getAttrib(K,"style"));
var C=K.parentNode.nodeName.toLowerCase();
var H=E.getAttrib(K,"align");
var J=E.getAttrib(K,"valign");
var L=trimSize(getStyle(K,"height","height"));
var I=E.getAttrib(K,"class");
var O=convertRGBToHex(getStyle(K,"bgcolor","backgroundColor"));
var N=getStyle(K,"background","backgroundImage").replace(new RegExp("url\\('?([^']*)'?\\)","gi"),"$1");
var A=E.getAttrib(K,"id");
var B=E.getAttrib(K,"lang");
var D=E.getAttrib(K,"dir");
addClassesToList("class","table_row_styles");
TinyMCE_EditableSelects.init();
G.bgcolor.value=O;
G.backgroundimage.value=N;
G.height.value=L;
G.id.value=A;
G.lang.value=B;
G.style.value=E.serializeStyle(M);
selectByValue(G,"align",H);
selectByValue(G,"valign",J);
selectByValue(G,"class",I,true,true);
selectByValue(G,"rowtype",C);
selectByValue(G,"dir",D);
if(isVisible("backgroundimagebrowser")){document.getElementById("backgroundimage").style.width="180px"
}updateColor("bgcolor_pick","bgcolor")
}function updateAction(){var F=tinyMCEPopup.editor,H=F.dom,G,B,A=document.forms[0];
var E=getSelectValue(A,"action");
tinyMCEPopup.restoreSelection();
G=H.getParent(F.selection.getNode(),"tr");
B=H.getParent(F.selection.getNode(),"table");
F.execCommand("mceBeginUndoLevel");
switch(E){case"row":updateRow(G);
break;
case"all":var D=B.getElementsByTagName("tr");
for(var C=0;
C<D.length;
C++){updateRow(D[C],true)
}break;
case"odd":case"even":var D=B.getElementsByTagName("tr");
for(var C=0;
C<D.length;
C++){if((C%2==0&&E=="odd")||(C%2!=0&&E=="even")){updateRow(D[C],true,true)
}}break
}F.addVisual();
F.nodeChanged();
F.execCommand("mceEndUndoLevel");
tinyMCEPopup.close()
}function updateRow(H,I,J){var F=tinyMCEPopup.editor;
var G=document.forms[0];
var D=F.dom;
var A=H.parentNode.nodeName.toLowerCase();
var B=getSelectValue(G,"rowtype");
var L=F.getDoc();
if(!I){H.setAttribute("id",G.id.value)
}H.setAttribute("align",getSelectValue(G,"align"));
H.setAttribute("vAlign",getSelectValue(G,"valign"));
H.setAttribute("lang",G.lang.value);
H.setAttribute("dir",getSelectValue(G,"dir"));
H.setAttribute("style",D.serializeStyle(D.parseStyle(G.style.value)));
D.setAttrib(H,"class",getSelectValue(G,"class"));
H.setAttribute("background","");
H.setAttribute("bgColor","");
H.setAttribute("height","");
H.style.height=getCSSSize(G.height.value);
H.style.backgroundColor=G.bgcolor.value;
if(G.backgroundimage.value!=""){H.style.backgroundImage="url('"+G.backgroundimage.value+"')"
}else{H.style.backgroundImage=""
}if(A!=B&&!J){var N=H.cloneNode(1);
var C=D.getParent(H,"table");
var K=B;
var M=null;
for(var E=0;
E<C.childNodes.length;
E++){if(C.childNodes[E].nodeName.toLowerCase()==K){M=C.childNodes[E]
}}if(M==null){M=L.createElement(K);
if(K=="thead"){if(C.firstChild.nodeName=="CAPTION"){F.dom.insertAfter(M,C.firstChild)
}else{C.insertBefore(M,C.firstChild)
}}else{C.appendChild(M)
}}M.appendChild(N);
H.parentNode.removeChild(H);
H=N
}D.setAttrib(H,"style",D.serializeStyle(D.parseStyle(H.style.cssText)))
}function changedBackgroundImage(){var A=document.forms[0],C=tinyMCEPopup.editor.dom;
var B=C.parseStyle(A.style.value);
B["background-image"]="url('"+A.backgroundimage.value+"')";
A.style.value=C.serializeStyle(B)
}function changedStyle(){var A=document.forms[0],C=tinyMCEPopup.editor.dom;
var B=C.parseStyle(A.style.value);
if(B["background-image"]){A.backgroundimage.value=B["background-image"].replace(new RegExp("url\\('?([^']*)'?\\)","gi"),"$1")
}else{A.backgroundimage.value=""
}if(B.height){A.height.value=trimSize(B.height)
}if(B["background-color"]){A.bgcolor.value=B["background-color"];
updateColor("bgcolor_pick","bgcolor")
}}function changedSize(){var B=document.forms[0],D=tinyMCEPopup.editor.dom;
var C=D.parseStyle(B.style.value);
var A=B.height.value;
if(A!=""){C.height=getCSSSize(A)
}else{C.height=""
}B.style.value=D.serializeStyle(C)
}function changedColor(){var A=document.forms[0],C=tinyMCEPopup.editor.dom;
var B=C.parseStyle(A.style.value);
B["background-color"]=A.bgcolor.value;
A.style.value=C.serializeStyle(B)
}tinyMCEPopup.onInit.add(init);