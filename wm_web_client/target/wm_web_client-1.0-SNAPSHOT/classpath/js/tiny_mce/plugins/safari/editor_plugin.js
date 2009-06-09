(function(){var B=tinymce.dom.Event,E=tinymce.grep,D=tinymce.each,A=tinymce.inArray;
function C(G,H,I){var J,F;
J=G.createTreeWalker(H,NodeFilter.SHOW_ALL,null,false);
while(F=J.nextNode()){if(I){if(!I(F)){return false
}}if(F.nodeType==3&&F.nodeValue&&/[^\s\u00a0]+/.test(F.nodeValue)){return false
}if(F.nodeType==1&&/^(HR|IMG|TABLE)$/.test(F.nodeName)){return false
}}return true
}tinymce.create("tinymce.plugins.Safari",{init:function(H){var G=this,F;
if(!tinymce.isWebKit){return 
}G.editor=H;
G.webKitFontSizes=["x-small","small","medium","large","x-large","xx-large","-webkit-xxx-large"];
G.namedFontSizes=["xx-small","x-small","small","medium","large","x-large","xx-large"];
H.addCommand("CreateLink",function(K,L){var I=H.selection.getNode(),J=H.dom,M;
if(I&&(/^(left|right)$/i.test(J.getStyle(I,"float",1))||/^(left|right)$/i.test(J.getAttrib(I,"align")))){M=J.create("a",{href:L},I.cloneNode());
I.parentNode.replaceChild(M,I);
H.selection.select(M)
}else{H.getDoc().execCommand("CreateLink",false,L)
}});
H.onKeyUp.add(function(L,O){var J,M,I,N,K;
if(O.keyCode==46||O.keyCode==8){M=L.getBody();
J=M.innerHTML;
K=L.selection;
if(M.childNodes.length==1&&!/<(img|hr)/.test(J)&&tinymce.trim(J.replace(/<[^>]+>/g,"")).length==0){L.setContent('<p><br mce_bogus="1" /></p>',{format:"raw"});
N=M.firstChild;
I=K.getRng();
I.setStart(N,0);
I.setEnd(N,0);
K.setRng(I)
}}});
H.addCommand("FormatBlock",function(K,L){var I=H.dom,J=I.getParent(H.selection.getNode(),I.isBlock);
if(J){I.replace(I.create(L),J,1)
}else{H.getDoc().execCommand("FormatBlock",false,L)
}});
H.addCommand("mceInsertContent",function(I,J){H.getDoc().execCommand("InsertText",false,"mce_marker");
H.getBody().innerHTML=H.getBody().innerHTML.replace(/mce_marker/g,H.dom.processHTML(J)+'<span id="_mce_tmp">XX</span>');
H.selection.select(H.dom.get("_mce_tmp"));
H.getDoc().execCommand("Delete",false," ")
});
H.onKeyPress.add(function(L,K){var J,R,I,N,P,O,Q,S,M,T,U;
if(K.keyCode==13){Q=L.selection;
J=Q.getNode();
if(K.shiftKey||L.settings.force_br_newlines&&J.nodeName!="LI"){G._insertBR(L);
B.cancel(K)
}if(R=F.getParent(J,"LI")){I=F.getParent(R,"OL,UL");
S=L.getDoc();
U=F.create("p");
F.add(U,"br",{mce_bogus:"1"});
if(C(S,R)){if(O=F.getParent(I.parentNode,"LI,OL,UL")){return 
}O=F.getParent(I,"p,h1,h2,h3,h4,h5,h6,div")||I;
N=S.createRange();
N.setStartBefore(O);
N.setEndBefore(R);
P=S.createRange();
P.setStartAfter(R);
P.setEndAfter(O);
M=N.cloneContents();
T=P.cloneContents();
if(!C(S,T)){F.insertAfter(T,O)
}F.insertAfter(U,O);
if(!C(S,M)){F.insertAfter(M,O)
}F.remove(O);
O=U.firstChild;
N=S.createRange();
N.setStartBefore(O);
N.setEndBefore(O);
Q.setRng(N);
return B.cancel(K)
}}}});
H.onExecCommand.add(function(M,K){var L,I,N,J;
if(K=="InsertUnorderedList"||K=="InsertOrderedList"){L=M.selection;
I=M.dom;
if(N=I.getParent(L.getNode(),function(O){return/^(H[1-6]|P|ADDRESS|PRE)$/.test(O.nodeName)
})){J=L.getBookmark();
I.remove(N,1);
L.moveToBookmark(J)
}}});
H.onClick.add(function(J,I){I=I.target;
if(I.nodeName=="IMG"){G.selElm=I;
J.selection.select(I)
}else{G.selElm=null
}});
H.onInit.add(function(){G._fixWebKitSpans()
});
H.onSetContent.add(function(){F=H.dom;
D(["strong","b","em","u","strike","sub","sup","a"],function(I){D(E(F.select(I)).reverse(),function(J){var K=J.nodeName.toLowerCase(),L;
if(K=="a"){if(J.name){F.replace(F.create("img",{mce_name:"a",name:J.name,"class":"mceItemAnchor"}),J)
}return 
}switch(K){case"b":case"strong":if(K=="b"){K="strong"
}L="font-weight: bold;";
break;
case"em":L="font-style: italic;";
break;
case"u":L="text-decoration: underline;";
break;
case"sub":L="vertical-align: sub;";
break;
case"sup":L="vertical-align: super;";
break;
case"strike":L="text-decoration: line-through;";
break
}F.replace(F.create("span",{mce_name:K,style:L,"class":"Apple-style-span"}),J,1)
})
})
});
H.onPreProcess.add(function(J,I){F=J.dom;
D(E(I.node.getElementsByTagName("span")).reverse(),function(K){var M,L;
if(I.get){if(F.hasClass(K,"Apple-style-span")){L=K.style.backgroundColor;
switch(F.getAttrib(K,"mce_name")){case"font":if(!J.settings.convert_fonts_to_spans){F.setAttrib(K,"style","")
}break;
case"strong":case"em":case"sub":case"sup":F.setAttrib(K,"style","");
break;
case"strike":case"u":if(!J.settings.inline_styles){F.setAttrib(K,"style","")
}else{F.setAttrib(K,"mce_name","")
}break;
default:if(!J.settings.inline_styles){F.setAttrib(K,"style","")
}}if(L){K.style.backgroundColor=L
}}}if(F.hasClass(K,"mceItemRemoved")){F.remove(K,1)
}})
});
H.onPostProcess.add(function(J,I){I.content=I.content.replace(/<br \/><\/(h[1-6]|div|p|address|pre)>/g,"</$1>");
I.content=I.content.replace(/ id=\"undefined\"/g,"")
})
},getInfo:function(){return{longname:"Safari compatibility",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/safari",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_fixWebKitSpans:function(){var F=this,G=F.editor;
B.add(G.getDoc(),"DOMNodeInserted",function(H){H=H.target;
if(H&&H.nodeType==1){F._fixAppleSpan(H)
}})
},_fixAppleSpan:function(G){var L=this.editor,F=L.dom,J=this.webKitFontSizes,M=this.namedFontSizes,I=L.settings,K,H;
if(F.getAttrib(G,"mce_fixed")){return 
}if(G.nodeName=="SPAN"&&G.className=="Apple-style-span"){K=G.style;
if(!I.convert_fonts_to_spans){if(K.fontSize){F.setAttrib(G,"mce_name","font");
F.setAttrib(G,"size",A(J,K.fontSize)+1)
}if(K.fontFamily){F.setAttrib(G,"mce_name","font");
F.setAttrib(G,"face",K.fontFamily)
}if(K.color){F.setAttrib(G,"mce_name","font");
F.setAttrib(G,"color",F.toHex(K.color))
}if(K.backgroundColor){F.setAttrib(G,"mce_name","font");
F.setStyle(G,"background-color",K.backgroundColor)
}}else{if(K.fontSize){F.setStyle(G,"fontSize",M[A(J,K.fontSize)])
}}if(K.fontWeight=="bold"){F.setAttrib(G,"mce_name","strong")
}if(K.fontStyle=="italic"){F.setAttrib(G,"mce_name","em")
}if(K.textDecoration=="underline"){F.setAttrib(G,"mce_name","u")
}if(K.textDecoration=="line-through"){F.setAttrib(G,"mce_name","strike")
}if(K.verticalAlign=="super"){F.setAttrib(G,"mce_name","sup")
}if(K.verticalAlign=="sub"){F.setAttrib(G,"mce_name","sub")
}F.setAttrib(G,"mce_fixed","1")
}},_insertBR:function(J){var F=J.dom,H=J.selection,G=H.getRng(),I;
G.insertNode(I=F.create("br"));
G.setStartAfter(I);
G.setEndAfter(I);
H.setRng(G);
if(H.getSel().focusNode==I.previousSibling){H.select(F.insertAfter(F.doc.createTextNode("\u00a0"),I));
H.collapse(1)
}J.getWin().scrollTo(0,F.getPos(H.getRng().startContainer).y)
}});
tinymce.PluginManager.add("safari",tinymce.plugins.Safari)
})();