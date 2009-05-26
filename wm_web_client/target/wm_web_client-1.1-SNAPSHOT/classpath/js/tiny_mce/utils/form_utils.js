var themeBaseURL=tinyMCEPopup.editor.baseURI.toAbsolute("themes/"+tinyMCEPopup.getParam("theme"));
function getColorPickerHTML(C,B){var A="";
A+='<a id="'+C+'_link" href="javascript:;" onclick="tinyMCEPopup.pickColor(event,\''+B+'\');" onmousedown="return false;" class="pickcolor">';
A+='<span id="'+C+'" title="'+tinyMCEPopup.getLang("browse")+'">&nbsp;</span></a>';
return A
}function updateColor(B,A){document.getElementById(B).style.backgroundColor=document.forms[0].elements[A].value
}function setBrowserDisabled(D,B){var A=document.getElementById(D);
var C=document.getElementById(D+"_link");
if(C){if(B){C.setAttribute("realhref",C.getAttribute("href"));
C.removeAttribute("href");
tinyMCEPopup.dom.addClass(A,"disabled")
}else{if(C.getAttribute("realhref")){C.setAttribute("href",C.getAttribute("realhref"))
}tinyMCEPopup.dom.removeClass(A,"disabled")
}}}function getBrowserHTML(G,E,D,F){var C=F+"_"+D+"_browser_callback",A,B;
A=tinyMCEPopup.getParam(C,tinyMCEPopup.getParam("file_browser_callback"));
if(!A){return""
}B="";
B+='<a id="'+G+'_link" href="javascript:openBrowser(\''+G+"','"+E+"', '"+D+"','"+C+'\');" onmousedown="return false;" class="browse">';
B+='<span id="'+G+'" title="'+tinyMCEPopup.getLang("browse")+'">&nbsp;</span></a>';
return B
}function openBrowser(B,E,D,C){var A=document.getElementById(B);
if(A.className!="mceButtonDisabled"){tinyMCEPopup.openBrowser(E,D,C)
}}function selectByValue(A,C,G,F,H){if(!A||!A.elements[C]){return 
}var B=A.elements[C];
var I=false;
for(var D=0;
D<B.options.length;
D++){var E=B.options[D];
if(E.value==G||(H&&E.value.toLowerCase()==G.toLowerCase())){E.selected=true;
I=true
}else{E.selected=false
}}if(!I&&F&&G!=""){var E=new Option(G,G);
E.selected=true;
B.options[B.options.length]=E;
B.selectedIndex=B.options.length-1
}return I
}function getSelectValue(A,B){var C=A.elements[B];
if(C==null||C.options==null){return""
}return C.options[C.selectedIndex].value
}function addSelectValue(A,D,B,E){var C=A.elements[D];
var F=new Option(B,E);
C.options[C.options.length]=F
}function addClassesToList(H,F){var C=document.getElementById(H);
var D=tinyMCEPopup.getParam("theme_advanced_styles",false);
D=tinyMCEPopup.getParam(F,D);
if(D){var G=D.split(";");
for(var B=0;
B<G.length;
B++){if(G!=""){var A,E;
A=G[B].split("=")[0];
E=G[B].split("=")[1];
C.options[C.length]=new Option(A,E)
}}}else{tinymce.each(tinyMCEPopup.editor.dom.getClasses(),function(I){C.options[C.length]=new Option(I.title||I["class"],I["class"])
})
}}function isVisible(A){var B=document.getElementById(A);
return B&&B.style.display!="none"
}function convertRGBToHex(B){var C=new RegExp("rgb\\s*\\(\\s*([0-9]+).*,\\s*([0-9]+).*,\\s*([0-9]+).*\\)","gi");
var A=B.replace(C,"$1,$2,$3").split(",");
if(A.length==3){r=parseInt(A[0]).toString(16);
g=parseInt(A[1]).toString(16);
b=parseInt(A[2]).toString(16);
r=r.length==1?"0"+r:r;
g=g.length==1?"0"+g:g;
b=b.length==1?"0"+b:b;
return"#"+r+g+b
}return B
}function convertHexToRGB(A){if(A.indexOf("#")!=-1){A=A.replace(new RegExp("[^0-9A-F]","gi"),"");
r=parseInt(A.substring(0,2),16);
g=parseInt(A.substring(2,4),16);
b=parseInt(A.substring(4,6),16);
return"rgb("+r+","+g+","+b+")"
}return A
}function trimSize(A){return A.replace(/([0-9\.]+)px|(%|in|cm|mm|em|ex|pt|pc)/,"$1$2")
}function getCSSSize(A){A=trimSize(A);
if(A==""){return""
}if(/^[0-9]+$/.test(A)){A+="px"
}return A
}function getStyle(D,B,A){var C=tinyMCEPopup.dom.getAttrib(D,B);
if(C!=""){return""+C
}if(typeof (A)=="undefined"){A=B
}return tinyMCEPopup.dom.getStyle(D,A)
};