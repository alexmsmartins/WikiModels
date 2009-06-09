tinyMCEPopup.requireLangPack();
function initCommonAttributes(C){var A=document.forms[0],B=tinyMCEPopup.editor.dom;
setFormValue("title",B.getAttrib(C,"title"));
setFormValue("id",B.getAttrib(C,"id"));
selectByValue(A,"class",B.getAttrib(C,"class"),true);
setFormValue("style",B.getAttrib(C,"style"));
selectByValue(A,"dir",B.getAttrib(C,"dir"));
setFormValue("lang",B.getAttrib(C,"lang"));
setFormValue("onfocus",B.getAttrib(C,"onfocus"));
setFormValue("onblur",B.getAttrib(C,"onblur"));
setFormValue("onclick",B.getAttrib(C,"onclick"));
setFormValue("ondblclick",B.getAttrib(C,"ondblclick"));
setFormValue("onmousedown",B.getAttrib(C,"onmousedown"));
setFormValue("onmouseup",B.getAttrib(C,"onmouseup"));
setFormValue("onmouseover",B.getAttrib(C,"onmouseover"));
setFormValue("onmousemove",B.getAttrib(C,"onmousemove"));
setFormValue("onmouseout",B.getAttrib(C,"onmouseout"));
setFormValue("onkeypress",B.getAttrib(C,"onkeypress"));
setFormValue("onkeydown",B.getAttrib(C,"onkeydown"));
setFormValue("onkeyup",B.getAttrib(C,"onkeyup"))
}function setFormValue(A,B){if(document.forms[0].elements[A]){document.forms[0].elements[A].value=B
}}function insertDateTime(A){document.getElementById(A).value=getDateTime(new Date(),"%Y-%m-%dT%H:%M:%S")
}function getDateTime(B,A){A=A.replace("%D","%m/%d/%y");
A=A.replace("%r","%I:%M:%S %p");
A=A.replace("%Y",""+B.getFullYear());
A=A.replace("%y",""+B.getYear());
A=A.replace("%m",addZeros(B.getMonth()+1,2));
A=A.replace("%d",addZeros(B.getDate(),2));
A=A.replace("%H",""+addZeros(B.getHours(),2));
A=A.replace("%M",""+addZeros(B.getMinutes(),2));
A=A.replace("%S",""+addZeros(B.getSeconds(),2));
A=A.replace("%I",""+((B.getHours()+11)%12+1));
A=A.replace("%p",""+(B.getHours()<12?"AM":"PM"));
A=A.replace("%%","%");
return A
}function addZeros(C,A){var B;
C=""+C;
if(C.length<A){for(B=0;
B<(A-C.length);
B++){C="0"+C
}}return C
}function selectByValue(A,C,G,F,H){if(!A||!A.elements[C]){return 
}var B=A.elements[C];
var I=false;
for(var D=0;
D<B.options.length;
D++){var E=B.options[D];
if(E.value==G||(H&&E.value.toLowerCase()==G.toLowerCase())){E.selected=true;
I=true
}else{E.selected=false
}}if(!I&&F&&G!=""){var E=new Option("Value: "+G,G);
E.selected=true;
B.options[B.options.length]=E
}return I
}function setAttrib(E,D,C){var B=document.forms[0];
var A=B.elements[D.toLowerCase()];
tinyMCEPopup.editor.dom.setAttrib(E,D,C||A.value)
}function setAllCommonAttribs(A){setAttrib(A,"title");
setAttrib(A,"id");
setAttrib(A,"class");
setAttrib(A,"style");
setAttrib(A,"dir");
setAttrib(A,"lang")
}SXE={currentAction:"insert",inst:tinyMCEPopup.editor,updateElement:null};
SXE.focusElement=SXE.inst.selection.getNode();
SXE.initElementDialog=function(A){addClassesToList("class","xhtmlxtras_styles");
TinyMCE_EditableSelects.init();
A=A.toLowerCase();
var B=SXE.inst.dom.getParent(SXE.focusElement,A.toUpperCase());
if(B!=null&&B.nodeName.toUpperCase()==A.toUpperCase()){SXE.currentAction="update"
}if(SXE.currentAction=="update"){initCommonAttributes(B);
SXE.updateElement=B
}document.forms[0].insert.value=tinyMCEPopup.getLang(SXE.currentAction,"Insert",true)
};
SXE.insertElement=function(F){var G=SXE.inst.dom.getParent(SXE.focusElement,F.toUpperCase()),E,C;
tinyMCEPopup.execCommand("mceBeginUndoLevel");
if(G==null){var D=SXE.inst.selection.getContent();
if(D.length>0){C=F;
if(tinymce.isIE&&F.indexOf("html:")==0){F=F.substring(5).toLowerCase()
}insertInlineElement(F);
var A=tinymce.grep(SXE.inst.dom.select(F));
for(var B=0;
B<A.length;
B++){var G=A[B];
if(SXE.inst.dom.getAttrib(G,"_mce_new")){G.id="";
G.setAttribute("id","");
G.removeAttribute("id");
G.removeAttribute("_mce_new");
setAllCommonAttribs(G)
}}}}else{setAllCommonAttribs(G)
}SXE.inst.nodeChanged();
tinyMCEPopup.execCommand("mceEndUndoLevel")
};
SXE.removeElement=function(A){A=A.toLowerCase();
elm=SXE.inst.dom.getParent(SXE.focusElement,A.toUpperCase());
if(elm&&elm.nodeName.toUpperCase()==A.toUpperCase()){tinyMCEPopup.execCommand("mceBeginUndoLevel");
tinyMCE.execCommand("mceRemoveNode",false,elm);
SXE.inst.nodeChanged();
tinyMCEPopup.execCommand("mceEndUndoLevel")
}};
SXE.showRemoveButton=function(){document.getElementById("remove").style.display="block"
};
SXE.containsClass=function(B,A){return(B.className.indexOf(A)>-1)?true:false
};
SXE.removeClass=function(F,C){if(F.className==null||F.className==""||!SXE.containsClass(F,C)){return true
}var E=F.className.split(" ");
var D="";
for(var A=0,B=E.length;
A<B;
A++){if(E[A]!=C){D+=(E[A]+" ")
}}F.className=D.substring(0,D.length-1)
};
SXE.addClass=function(B,A){if(!SXE.containsClass(B,A)){B.className?B.className+=" "+A:B.className=A
}return true
};
function insertInlineElement(B){var A=tinyMCEPopup.editor,C=A.dom;
A.getDoc().execCommand("FontName",false,"mceinline");
tinymce.each(C.select("span,font"),function(D){if(D.style.fontFamily=="mceinline"||D.face=="mceinline"){C.replace(C.create(B,{_mce_new:1}),D,1)
}})
};