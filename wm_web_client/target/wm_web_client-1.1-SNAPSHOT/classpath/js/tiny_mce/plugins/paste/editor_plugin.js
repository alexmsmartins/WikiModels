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
}}E._legacySupport()
},getInfo:function(){return{longname:"Paste text/word",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/paste",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_preProcess:function(E,C){var B=C.content,D;
function D(F){A(F,function(G){if(G.constructor==RegExp){B=B.replace(G,"")
}else{B=B.replace(G[0],G[1])
}})
}D([/^\s*(&nbsp;)+/g,/(&nbsp;|<br[^>]*>)+\s*$/g]);
if(/(class=\"?Mso|style=\"[^\"]*\bmso\-|w:WordDocument)/.test(B)){C.wordContent=true;
D([/<!--[\s\S]+?-->/gi,/<\/?(img|font|meta|link|style|span|div|v:\w+)[^>]*>/gi,/<\\?\?xml[^>]*>/gi,/<\/?o:[^>]*>/gi,/ (id|name|class|language|type|on\w+|v:\w+)=\"([^\"]*)\"/gi,/ (id|name|class|language|type|on\w+|v:\w+)=(\w+)/gi,[/<(\/?)s>/gi,"<$1strike>"],/<script[^>]+>[\s\S]*?<\/script>/gi,[/&nbsp;/g,"\u00a0"]])
}C.content=B
},_postProcess:function(E,C){var B=this,D=B.editor.dom;
if(C.wordContent){A(D.select("a",C.node),function(F){if(!F.href||F.href.indexOf("#_Toc")!=-1){D.remove(F,1)
}});
if(B.editor.getParam("paste_convert_middot_lists",true)){B._convertLists(E,C)
}A(D.select("*",C.node),function(F){D.setAttrib(F,"style","")
})
}if(tinymce.isWebKit){A(D.select("*",C.node),function(F){F.removeAttribute("mce_style")
})
}},_convertLists:function(G,I){var E=G.editor.dom,F,C,J=-1,H,B=[],D;
A(E.select("p",I.node),function(O){var K,N="",P,Q,M,L;
for(K=O.firstChild;
K&&K.nodeType==3;
K=K.nextSibling){N+=K.nodeValue
}if(/^[\u2022\u00b7\u00a7\u00d8o]\s*\u00a0\u00a0*/.test(N)){P="ul"
}if(/^[\s\S]*\w+\.[\s\S]*\u00a0{2,}/.test(N)){P="ol"
}if(P){H=parseFloat(O.style.marginLeft||0);
if(H>J){B.push(H)
}if(!F||P!=D){F=E.create(P);
E.insertAfter(F,O)
}else{if(H>J){F=C.appendChild(E.create(P))
}else{if(H<J){M=tinymce.inArray(B,H);
L=E.getParents(F.parentNode,P);
F=L[L.length-1-M]||F
}}}if(P=="ul"){Q=O.innerHTML.replace(/^[\u2022\u00b7\u00a7\u00d8o]\s*(&nbsp;|\u00a0)+\s*/,"")
}else{Q=O.innerHTML.replace(/^[\s\S]*\w+\.(&nbsp;|\u00a0)+\s*/,"")
}C=F.appendChild(E.create("li",0,Q));
E.remove(O);
J=H;
D=P
}else{F=J=0
}})
},_insertBlockContent:function(G,J,F){var L,H,K=G.selection,B,E,M,D,I;
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
D.execCommand("Delete");
D.execCommand(tinymce.isGecko?"insertHTML":"mceInsertContent",false,C,{skip_undo:B})
},_legacySupport:function(){var C=this,B=C.editor;
A(["mcePasteText","mcePasteWord"],function(D){B.addCommand(D,function(){B.windowManager.open({file:C.url+(D=="mcePasteText"?"/pastetext.htm":"/pasteword.htm"),width:450,height:400,inline:1})
})
});
B.addButton("pastetext",{title:"paste.paste_text_desc",cmd:"mcePasteText"});
B.addButton("pasteword",{title:"paste.paste_word_desc",cmd:"mcePasteWord"});
B.addButton("selectall",{title:"paste.selectall_desc",cmd:"selectall"})
}});
tinymce.PluginManager.add("paste",tinymce.plugins.PastePlugin)
})();