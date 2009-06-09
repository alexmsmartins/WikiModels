(function(){var A=tinymce.each;
tinymce.create("tinymce.plugins.PastePlugin",{init:function(G,F){var E=this,B;
E.editor=G;
E.url=F;
E.onPreProcess=new tinymce.util.Dispatcher(E);
E.onPostProcess=new tinymce.util.Dispatcher(E);
E.onPreProcess.add(E._preProcess);
E.onPostProcess.add(E._postProcess);
E.onPreProcess.add(function(I,H){G.execCallback("paste_preprocess",I,H)
});
E.onPostProcess.add(function(I,H){G.execCallback("paste_postprocess",I,H)
});
function C(J){var H=G.dom,I={content:J};
E.onPreProcess.dispatch(E,I);
I.node=H.create("div",0,I.content);
E.onPostProcess.dispatch(E,I);
I.content=G.serializer.serialize(I.node,{getInner:1});
if(/<(p|h[1-6]|ul|ol)/.test(I.content)){E._insertBlockContent(G,H,I.content)
}else{E._insert(I.content)
}}G.addCommand("mceInsertClipboardContent",function(H,I){C(I)
});
function D(I){var N,J,L,K=G.selection,O=G.dom,M=G.getBody(),H;
if(O.get("_mcePaste")){return 
}N=O.add(M,"div",{id:"_mcePaste"},"&nbsp;");
if(M!=G.getDoc().body){H=O.getPos(G.selection.getStart(),M).y
}else{H=M.scrollTop
}O.setStyles(N,{position:"absolute",left:-10000,top:H,width:1,height:1,overflow:"hidden"});
if(tinymce.isIE){L=O.doc.body.createTextRange();
L.moveToElementText(N);
L.execCommand("Paste");
O.remove(N);
C(N.innerHTML);
return tinymce.dom.Event.cancel(I)
}else{J=G.selection.getRng();
N=N.firstChild;
L=G.getDoc().createRange();
L.setStart(N,0);
L.setEnd(N,1);
K.setRng(L);
window.setTimeout(function(){var P=O.get("_mcePaste"),Q;
P.id="_mceRemoved";
O.remove(P);
P=O.get("_mcePaste")||P;
Q=(O.select("> span.Apple-style-span div",P)[0]||O.select("> span.Apple-style-span",P)[0]||P).innerHTML;
O.remove(P);
if(J){K.setRng(J)
}C(Q)
},0)
}}if(G.getParam("paste_auto_cleanup_on_paste",true)){if(tinymce.isOpera||/Firefox\/2/.test(navigator.userAgent)){G.onKeyDown.add(function(I,H){if(((tinymce.isMac?H.metaKey:H.ctrlKey)&&H.keyCode==86)||(H.shiftKey&&H.keyCode==45)){D(H)
}})
}else{G.onPaste.addToTop(function(I,H){return D(H)
})
}}if(G.getParam("paste_block_drop")){G.onInit.add(function(){G.dom.bind(G.getBody(),["dragend","dragover","draggesture","dragdrop","drop","drag"],function(H){H.preventDefault();
H.stopPropagation();
return false
})
})
}E._legacySupport()
},getInfo:function(){return{longname:"Paste text/word",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/paste",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_preProcess:function(F,C){var B=this.editor,G=C.content,D,E;
function D(H){A(H,function(I){if(I.constructor==RegExp){G=G.replace(I,"")
}else{G=G.replace(I[0],I[1])
}})
}D([/^\s*(&nbsp;)+/g,/(&nbsp;|<br[^>]*>)+\s*$/g]);
if(/(class=\"?Mso|style=\"[^\"]*\bmso\-|w:WordDocument)/.test(G)){C.wordContent=true;
if(B.getParam("paste_convert_middot_lists",true)){D([[/<!--\[if !supportLists\]-->/gi,"$&__MCE_ITEM__"],[/(<span[^>]+:\s*symbol[^>]+>)/gi,"$1__MCE_ITEM__"],[/(<span[^>]+mso-list:[^>]+>)/gi,"$1__MCE_ITEM__"]])
}D([/<!--[\s\S]+?-->/gi,/<\/?(img|font|meta|link|style|div|v:\w+)[^>]*>/gi,/<\\?\?xml[^>]*>/gi,/<\/?o:[^>]*>/gi,/ (id|name|language|type|on\w+|v:\w+)=\"([^\"]*)\"/gi,/ (id|name|language|type|on\w+|v:\w+)=(\w+)/gi,[/<(\/?)s>/gi,"<$1strike>"],/<script[^>]+>[\s\S]*?<\/script>/gi,[/&nbsp;/g,"\u00a0"]]);
if(!B.getParam("paste_retain_style_properties")){D([/<\/?(span)[^>]*>/gi])
}}E=B.getParam("paste_strip_class_attributes","all");
if(E!="none"){if(E=="all"){D([/ class=\"([^\"]*)\"/gi,/ class=(\w+)/gi])
}else{D([/ class=\"(mso[^\"]*)\"/gi,/ class=(mso\w+)/gi])
}}if(B.getParam("paste_remove_spans")){D([/<\/?(span)[^>]*>/gi])
}C.content=G
},_postProcess:function(E,C){var F=this,G=F.editor,D=G.dom,B;
if(C.wordContent){A(D.select("a",C.node),function(H){if(!H.href||H.href.indexOf("#_Toc")!=-1){D.remove(H,1)
}});
if(F.editor.getParam("paste_convert_middot_lists",true)){F._convertLists(E,C)
}B=G.getParam("paste_retain_style_properties");
if(tinymce.is(B,"string")){B=tinymce.explode(B)
}A(D.select("*",C.node),function(I){var H={},K=0,J,M,L;
if(B){for(J=0;
J<B.length;
J++){M=B[J];
L=D.getStyle(I,M);
if(L){H[M]=L;
K++
}}}D.setAttrib(I,"style","");
if(B&&K>0){D.setStyles(I,H)
}else{if(I.nodeName=="SPAN"&&!I.className){D.remove(I,true)
}}})
}if(G.getParam("paste_remove_styles")||(G.getParam("paste_remove_styles_if_webkit")&&tinymce.isWebKit)){A(D.select("*[style]",C.node),function(H){H.removeAttribute("style");
H.removeAttribute("mce_style")
})
}else{if(tinymce.isWebKit){A(D.select("*",C.node),function(H){H.removeAttribute("mce_style")
})
}}},_convertLists:function(H,J){var F=H.editor.dom,G,C,K=-1,I,B=[],D,E;
A(F.select("p",J.node),function(O){var R,N="",P,Q,M,L;
for(R=O.firstChild;
R&&R.nodeType==3;
R=R.nextSibling){N+=R.nodeValue
}N=O.innerHTML.replace(/<\/?\w+[^>]*>/gi,"").replace(/&nbsp;/g,"\u00a0");
if(/^(__MCE_ITEM__)+[\u2022\u00b7\u00a7\u00d8o]\s*\u00a0*/.test(N)){P="ul"
}if(/^__MCE_ITEM__\s*\w+\.\s*\u00a0{2,}/.test(N)){P="ol"
}if(P){I=parseFloat(O.style.marginLeft||0);
if(I>K){B.push(I)
}if(!G||P!=D){G=F.create(P);
F.insertAfter(G,O)
}else{if(I>K){G=C.appendChild(F.create(P))
}else{if(I<K){M=tinymce.inArray(B,I);
L=F.getParents(G.parentNode,P);
G=L[L.length-1-M]||G
}}}A(F.select("span",O),function(S){var T=S.innerHTML.replace(/<\/?\w+[^>]*>/gi,"");
if(P=="ul"&&/^[\u2022\u00b7\u00a7\u00d8o]/.test(T)){F.remove(S)
}else{if(/^[\s\S]*\w+\.(&nbsp;|\u00a0)*\s*/.test(T)){F.remove(S)
}}});
Q=O.innerHTML;
if(P=="ul"){Q=O.innerHTML.replace(/__MCE_ITEM__/g,"").replace(/^[\u2022\u00b7\u00a7\u00d8o]\s*(&nbsp;|\u00a0)+\s*/,"")
}else{Q=O.innerHTML.replace(/__MCE_ITEM__/g,"").replace(/^\s*\w+\.(&nbsp;|\u00a0)+\s*/,"")
}C=G.appendChild(F.create("li",0,Q));
F.remove(O);
K=I;
D=P
}else{G=K=0
}});
E=J.node.innerHTML;
if(E.indexOf("__MCE_ITEM__")!=-1){J.node.innerHTML=E.replace(/__MCE_ITEM__/g,"")
}},_insertBlockContent:function(G,J,F){var L,H,K=G.selection,B,E,M,D,I;
function C(N){var O;
if(tinymce.isIE){O=G.getDoc().body.createTextRange();
O.moveToElementText(N);
O.collapse(false);
O.select()
}else{K.select(N,1);
K.collapse(false)
}}this._insert('<span id="_marker">&nbsp;</span>',1);
H=J.get("_marker");
L=J.getParent(H,"p,h1,h2,h3,h4,h5,h6,ul,ol");
if(L){H=J.split(L,H);
A(J.create("div",0,F).childNodes,function(N){B=H.parentNode.insertBefore(N.cloneNode(true),H)
});
C(B)
}else{J.setOuterHTML(H,F);
K.select(G.getBody(),1);
K.collapse(0)
}J.remove("_marker");
E=K.getStart();
M=J.getViewPort(G.getWin());
D=G.dom.getPos(E).y;
I=E.clientHeight;
if(D<M.y||D+I>M.y+M.h){G.getDoc().body.scrollTop=D<M.y?D:D-M.h+25
}},_insert:function(C,B){var D=this.editor;
if(!D.selection.isCollapsed()){D.getDoc().execCommand("Delete",false,null)
}D.execCommand(tinymce.isGecko?"insertHTML":"mceInsertContent",false,C,{skip_undo:B})
},_legacySupport:function(){var C=this,B=C.editor;
A(["mcePasteText","mcePasteWord"],function(D){B.addCommand(D,function(){B.windowManager.open({file:C.url+(D=="mcePasteText"?"/pastetext.htm":"/pasteword.htm"),width:parseInt(B.getParam("paste_dialog_width","450")),height:parseInt(B.getParam("paste_dialog_height","400")),inline:1})
})
});
B.addButton("pastetext",{title:"paste.paste_text_desc",cmd:"mcePasteText"});
B.addButton("pasteword",{title:"paste.paste_word_desc",cmd:"mcePasteWord"});
B.addButton("selectall",{title:"paste.selectall_desc",cmd:"selectall"})
}});
tinymce.PluginManager.add("paste",tinymce.plugins.PastePlugin)
})();