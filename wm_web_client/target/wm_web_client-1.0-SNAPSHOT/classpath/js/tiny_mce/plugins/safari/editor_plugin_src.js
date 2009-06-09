(function(){var A=tinymce.dom.Event,C=tinymce.grep,D=tinymce.each,B=tinymce.inArray;
function E(I,H,G){var F,J;
F=I.createTreeWalker(H,NodeFilter.SHOW_ALL,null,false);
while(J=F.nextNode()){if(G){if(!G(J)){return false
}}if(J.nodeType==3&&J.nodeValue&&/[^\s\u00a0]+/.test(J.nodeValue)){return false
}if(J.nodeType==1&&/^(HR|IMG|TABLE)$/.test(J.nodeName)){return false
}}return true
}tinymce.create("tinymce.plugins.Safari",{init:function(F){var G=this,H;
if(!tinymce.isWebKit){return 
}G.editor=F;
G.webKitFontSizes=["x-small","small","medium","large","x-large","xx-large","-webkit-xxx-large"];
G.namedFontSizes=["xx-small","x-small","small","medium","large","x-large","xx-large"];
F.addCommand("CreateLink",function(K,J){var M=F.selection.getNode(),L=F.dom,I;
if(M&&(/^(left|right)$/i.test(L.getStyle(M,"float",1))||/^(left|right)$/i.test(L.getAttrib(M,"align")))){I=L.create("a",{href:J},M.cloneNode());
M.parentNode.replaceChild(I,M);
F.selection.select(I)
}else{F.getDoc().execCommand("CreateLink",false,J)
}});
F.onKeyUp.add(function(J,N){var L,I,M,O,K;
if(N.keyCode==46||N.keyCode==8){I=J.getBody();
L=I.innerHTML;
K=J.selection;
if(I.childNodes.length==1&&!/<(img|hr)/.test(L)&&tinymce.trim(L.replace(/<[^>]+>/g,"")).length==0){J.setContent('<p><br mce_bogus="1" /></p>',{format:"raw"});
O=I.firstChild;
M=K.getRng();
M.setStart(O,0);
M.setEnd(O,0);
K.setRng(M)
}}});
F.addCommand("FormatBlock",function(J,I){var L=F.dom,K=L.getParent(F.selection.getNode(),L.isBlock);
if(K){L.replace(L.create(I),K,1)
}else{F.getDoc().execCommand("FormatBlock",false,I)
}});
F.addCommand("mceInsertContent",function(J,I){F.getDoc().execCommand("InsertText",false,"mce_marker");
F.getBody().innerHTML=F.getBody().innerHTML.replace(/mce_marker/g,F.dom.processHTML(I)+'<span id="_mce_tmp">XX</span>');
F.selection.select(F.dom.get("_mce_tmp"));
F.getDoc().execCommand("Delete",false," ")
});
F.onKeyPress.add(function(N,O){var P,U,Q,L,J,K,I,T,M,S,R;
if(O.keyCode==13){I=N.selection;
P=I.getNode();
if(O.shiftKey||N.settings.force_br_newlines&&P.nodeName!="LI"){G._insertBR(N);
A.cancel(O)
}if(U=H.getParent(P,"LI")){Q=H.getParent(U,"OL,UL");
T=N.getDoc();
R=H.create("p");
H.add(R,"br",{mce_bogus:"1"});
if(E(T,U)){if(K=H.getParent(Q.parentNode,"LI,OL,UL")){return 
}K=H.getParent(Q,"p,h1,h2,h3,h4,h5,h6,div")||Q;
L=T.createRange();
L.setStartBefore(K);
L.setEndBefore(U);
J=T.createRange();
J.setStartAfter(U);
J.setEndAfter(K);
M=L.cloneContents();
S=J.cloneContents();
if(!E(T,S)){H.insertAfter(S,K)
}H.insertAfter(R,K);
if(!E(T,M)){H.insertAfter(M,K)
}H.remove(K);
K=R.firstChild;
L=T.createRange();
L.setStartBefore(K);
L.setEndBefore(K);
I.setRng(L);
return A.cancel(O)
}}}});
F.onExecCommand.add(function(I,K){var J,M,N,L;
if(K=="InsertUnorderedList"||K=="InsertOrderedList"){J=I.selection;
M=I.dom;
if(N=M.getParent(J.getNode(),function(O){return/^(H[1-6]|P|ADDRESS|PRE)$/.test(O.nodeName)
})){L=J.getBookmark();
M.remove(N,1);
J.moveToBookmark(L)
}}});
F.onClick.add(function(I,J){J=J.target;
if(J.nodeName=="IMG"){G.selElm=J;
I.selection.select(J)
}else{G.selElm=null
}});
F.onInit.add(function(){G._fixWebKitSpans()
});
F.onSetContent.add(function(){H=F.dom;
D(["strong","b","em","u","strike","sub","sup","a"],function(I){D(C(H.select(I)).reverse(),function(L){var K=L.nodeName.toLowerCase(),J;
if(K=="a"){if(L.name){H.replace(H.create("img",{mce_name:"a",name:L.name,"class":"mceItemAnchor"}),L)
}return 
}switch(K){case"b":case"strong":if(K=="b"){K="strong"
}J="font-weight: bold;";
break;
case"em":J="font-style: italic;";
break;
case"u":J="text-decoration: underline;";
break;
case"sub":J="vertical-align: sub;";
break;
case"sup":J="vertical-align: super;";
break;
case"strike":J="text-decoration: line-through;";
break
}H.replace(H.create("span",{mce_name:K,style:J,"class":"Apple-style-span"}),L,1)
})
})
});
F.onPreProcess.add(function(I,J){H=I.dom;
D(C(J.node.getElementsByTagName("span")).reverse(),function(M){var K,L;
if(J.get){if(H.hasClass(M,"Apple-style-span")){L=M.style.backgroundColor;
switch(H.getAttrib(M,"mce_name")){case"font":if(!I.settings.convert_fonts_to_spans){H.setAttrib(M,"style","")
}break;
case"strong":case"em":case"sub":case"sup":H.setAttrib(M,"style","");
break;
case"strike":case"u":if(!I.settings.inline_styles){H.setAttrib(M,"style","")
}else{H.setAttrib(M,"mce_name","")
}break;
default:if(!I.settings.inline_styles){H.setAttrib(M,"style","")
}}if(L){M.style.backgroundColor=L
}}}if(H.hasClass(M,"mceItemRemoved")){H.remove(M,1)
}})
});
F.onPostProcess.add(function(I,J){J.content=J.content.replace(/<br \/><\/(h[1-6]|div|p|address|pre)>/g,"</$1>");
J.content=J.content.replace(/ id=\"undefined\"/g,"")
})
},getInfo:function(){return{longname:"Safari compatibility",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/safari",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_fixWebKitSpans:function(){var G=this,F=G.editor;
A.add(F.getDoc(),"DOMNodeInserted",function(H){H=H.target;
if(H&&H.nodeType==1){G._fixAppleSpan(H)
}})
},_fixAppleSpan:function(L){var G=this.editor,M=G.dom,I=this.webKitFontSizes,F=this.namedFontSizes,J=G.settings,H,K;
if(M.getAttrib(L,"mce_fixed")){return 
}if(L.nodeName=="SPAN"&&L.className=="Apple-style-span"){H=L.style;
if(!J.convert_fonts_to_spans){if(H.fontSize){M.setAttrib(L,"mce_name","font");
M.setAttrib(L,"size",B(I,H.fontSize)+1)
}if(H.fontFamily){M.setAttrib(L,"mce_name","font");
M.setAttrib(L,"face",H.fontFamily)
}if(H.color){M.setAttrib(L,"mce_name","font");
M.setAttrib(L,"color",M.toHex(H.color))
}if(H.backgroundColor){M.setAttrib(L,"mce_name","font");
M.setStyle(L,"background-color",H.backgroundColor)
}}else{if(H.fontSize){M.setStyle(L,"fontSize",F[B(I,H.fontSize)])
}}if(H.fontWeight=="bold"){M.setAttrib(L,"mce_name","strong")
}if(H.fontStyle=="italic"){M.setAttrib(L,"mce_name","em")
}if(H.textDecoration=="underline"){M.setAttrib(L,"mce_name","u")
}if(H.textDecoration=="line-through"){M.setAttrib(L,"mce_name","strike")
}if(H.verticalAlign=="super"){M.setAttrib(L,"mce_name","sup")
}if(H.verticalAlign=="sub"){M.setAttrib(L,"mce_name","sub")
}M.setAttrib(L,"mce_fixed","1")
}},_insertBR:function(F){var J=F.dom,H=F.selection,I=H.getRng(),G;
I.insertNode(G=J.create("br"));
I.setStartAfter(G);
I.setEndAfter(G);
H.setRng(I);
if(H.getSel().focusNode==G.previousSibling){H.select(J.insertAfter(J.doc.createTextNode("\u00a0"),G));
H.collapse(1)
}F.getWin().scrollTo(0,J.getPos(H.getRng().startContainer).y)
}});
tinymce.PluginManager.add("safari",tinymce.plugins.Safari)
})();