function init(){tinyMCEPopup.resizeToInnerSize();
var C=tinyMCEPopup.editor;
var D=C.dom;
var E=C.selection.getNode();
var B=document.forms[0];
var A=D.getAttrib(E,"onclick");
setFormValue("title",D.getAttrib(E,"title"));
setFormValue("id",D.getAttrib(E,"id"));
setFormValue("style",D.getAttrib(E,"style"));
setFormValue("dir",D.getAttrib(E,"dir"));
setFormValue("lang",D.getAttrib(E,"lang"));
setFormValue("tabindex",D.getAttrib(E,"tabindex",typeof (E.tabindex)!="undefined"?E.tabindex:""));
setFormValue("accesskey",D.getAttrib(E,"accesskey",typeof (E.accesskey)!="undefined"?E.accesskey:""));
setFormValue("onfocus",D.getAttrib(E,"onfocus"));
setFormValue("onblur",D.getAttrib(E,"onblur"));
setFormValue("onclick",A);
setFormValue("ondblclick",D.getAttrib(E,"ondblclick"));
setFormValue("onmousedown",D.getAttrib(E,"onmousedown"));
setFormValue("onmouseup",D.getAttrib(E,"onmouseup"));
setFormValue("onmouseover",D.getAttrib(E,"onmouseover"));
setFormValue("onmousemove",D.getAttrib(E,"onmousemove"));
setFormValue("onmouseout",D.getAttrib(E,"onmouseout"));
setFormValue("onkeypress",D.getAttrib(E,"onkeypress"));
setFormValue("onkeydown",D.getAttrib(E,"onkeydown"));
setFormValue("onkeyup",D.getAttrib(E,"onkeyup"));
className=D.getAttrib(E,"class");
addClassesToList("classlist","advlink_styles");
selectByValue(B,"classlist",className,true);
TinyMCE_EditableSelects.init()
}function setFormValue(A,B){if(B&&document.forms[0].elements[A]){document.forms[0].elements[A].value=B
}}function insertAction(){var A=tinyMCEPopup.editor;
var B=A.selection.getNode();
tinyMCEPopup.execCommand("mceBeginUndoLevel");
setAllAttribs(B);
tinyMCEPopup.execCommand("mceEndUndoLevel");
tinyMCEPopup.close()
}function setAttrib(G,E,D){var B=document.forms[0];
var A=B.elements[E.toLowerCase()];
var C=tinyMCEPopup.editor;
var F=C.dom;
if(typeof (D)=="undefined"||D==null){D="";
if(A){D=A.value
}}if(D!=""){F.setAttrib(G,E.toLowerCase(),D);
if(E=="style"){E="style.cssText"
}if(E.substring(0,2)=="on"){D="return true;"+D
}if(E=="class"){E="className"
}G[E]=D
}else{G.removeAttribute(E)
}}function setAllAttribs(B){var A=document.forms[0];
setAttrib(B,"title");
setAttrib(B,"id");
setAttrib(B,"style");
setAttrib(B,"class",getSelectValue(A,"classlist"));
setAttrib(B,"dir");
setAttrib(B,"lang");
setAttrib(B,"tabindex");
setAttrib(B,"accesskey");
setAttrib(B,"onfocus");
setAttrib(B,"onblur");
setAttrib(B,"onclick");
setAttrib(B,"ondblclick");
setAttrib(B,"onmousedown");
setAttrib(B,"onmouseup");
setAttrib(B,"onmouseover");
setAttrib(B,"onmousemove");
setAttrib(B,"onmouseout");
setAttrib(B,"onkeypress");
setAttrib(B,"onkeydown");
setAttrib(B,"onkeyup")
}function insertAttribute(){tinyMCEPopup.close()
}tinyMCEPopup.onInit.add(init);
tinyMCEPopup.requireLangPack();