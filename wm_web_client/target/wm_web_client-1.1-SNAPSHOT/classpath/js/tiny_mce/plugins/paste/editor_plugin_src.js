(function(){var A=tinymce.each;
tinymce.create("tinymce.plugins.PastePlugin",{init:function(C,D){var E=this,B;
E.editor=C;
E.url=D;
E.onPreProcess=new tinymce.util.Dispatcher(E);
E.onPostProcess=new tinymce.util.Dispatcher(E);
E.onPreProcess.add(E._preProcess);
E.onPostProcess.add(E._postProcess);
E.onPreProcess.add(function(H,I){C.execCallback("paste_preprocess",H,I)
});
E.onPostProcess.add(function(H,I){C.execCallback("paste_postprocess",H,I)
});
function G(H){var J=C.dom,I={content:H};
E.onPreProcess.dispatch(E,I);
I.node=J.create("div",0,I.content);
E.onPostProcess.dispatch(E,I);
I.content=C.serializer.serialize(I.node,{getInner:1});
if(/<(p|h[1-6]|ul|ol)/.test(I.content)){E._insertBlockContent(C,J,I.content)
}else{E._insert(I.content)
}}C.addCommand("mceInsertClipboardContent",function(I,H){G(H)
});
function F(L){var O,K,I,J=C.selection,N=C.dom,H=C.getBody(),M;
if(N.get("_mcePaste")){return 
}O=N.add(H,"div",{id:"_mcePaste"},"&nbsp;");
if(H!=C.getDoc().body){M=N.getPos(C.selection.getStart(),H).y
}else{M=H.scrollTop
}N.setStyles(O,{position:"absolute",left:-10000,top:M,width:1,height:1,overflow:"hidden"});
if(tinymce.isIE){I=N.doc.body.createTextRange();
I.moveToElementText(O);
I.execCommand("Paste");
N.remove(O);
G(O.innerHTML);
return tinymce.dom.Event.cancel(L)
}else{K=C.selection.getRng();
O=O.firstChild;
I=C.getDoc().createRange();
I.setStart(O,0);
I.setEnd(O,1);
J.setRng(I);
window.setTimeout(function(){var Q=N.get("_mcePaste"),P;
Q.id="_mceRemoved";
N.remove(Q);
Q=N.get("_mcePaste")||Q;
P=(N.select("> span.Apple-style-span div",Q)[0]||N.select("> span.Apple-style-span",Q)[0]||Q).innerHTML;
N.remove(Q);
if(K){J.setRng(K)
}G(P)
},0)
}}if(C.getParam("paste_auto_cleanup_on_paste",true)){if(tinymce.isOpera||/Firefox\/2/.test(navigator.userAgent)){C.onKeyDown.add(function(H,I){if(((tinymce.isMac?I.metaKey:I.ctrlKey)&&I.keyCode==86)||(I.shiftKey&&I.keyCode==45)){F(I)
}})
}else{C.onPaste.addToTop(function(H,I){return F(I)
})
}}E._legacySupport()
},getInfo:function(){return{longname:"Paste text/word",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/paste",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_preProcess:function(C,E){var B=E.content,D;
function D(F){A(F,function(G){if(G.constructor==RegExp){B=B.replace(G,"")
}else{B=B.replace(G[0],G[1])
}})
}D([/^\s*(&nbsp;)+/g,/(&nbsp;|<br[^>]*>)+\s*$/g]);
if(/(class=\"?Mso|style=\"[^\"]*\bmso\-|w:WordDocument)/.test(B)){E.wordContent=true;
D([/<!--[\s\S]+?-->/gi,/<\/?(img|font|meta|link|style|span|div|v:\w+)[^>]*>/gi,/<\\?\?xml[^>]*>/gi,/<\/?o:[^>]*>/gi,/ (id|name|class|language|type|on\w+|v:\w+)=\"([^\"]*)\"/gi,/ (id|name|class|language|type|on\w+|v:\w+)=(\w+)/gi,[/<(\/?)s>/gi,"<$1strike>"],/<script[^>]+>[\s\S]*?<\/script>/gi,[/&nbsp;/g,"\u00a0"]])
}E.content=B
},_postProcess:function(C,E){var B=this,D=B.editor.dom;
if(E.wordContent){A(D.select("a",E.node),function(F){if(!F.href||F.href.indexOf("#_Toc")!=-1){D.remove(F,1)
}});
if(B.editor.getParam("paste_convert_middot_lists",true)){B._convertLists(C,E)
}A(D.select("*",E.node),function(F){D.setAttrib(F,"style","")
})
}if(tinymce.isWebKit){A(D.select("*",E.node),function(F){F.removeAttribute("mce_style")
})
}},_convertLists:function(E,C){var G=E.editor.dom,F,I,B=-1,D,J=[],H;
A(G.select("p",C.node),function(P){var M,Q="",O,N,K,L;
for(M=P.firstChild;
M&&M.nodeType==3;
M=M.nextSibling){Q+=M.nodeValue
}if(/^[\u2022\u00b7\u00a7\u00d8o]\s*\u00a0\u00a0*/.test(Q)){O="ul"
}if(/^[\s\S]*\w+\.[\s\S]*\u00a0{2,}/.test(Q)){O="ol"
}if(O){D=parseFloat(P.style.marginLeft||0);
if(D>B){J.push(D)
}if(!F||O!=H){F=G.create(O);
G.insertAfter(F,P)
}else{if(D>B){F=I.appendChild(G.create(O))
}else{if(D<B){K=tinymce.inArray(J,D);
L=G.getParents(F.parentNode,O);
F=L[L.length-1-K]||F
}}}if(O=="ul"){N=P.innerHTML.replace(/^[\u2022\u00b7\u00a7\u00d8o]\s*(&nbsp;|\u00a0)+\s*/,"")
}else{N=P.innerHTML.replace(/^[\s\S]*\w+\.(&nbsp;|\u00a0)+\s*/,"")
}I=F.appendChild(G.create("li",0,N));
G.remove(P);
B=D;
H=O
}else{F=B=0
}})
},_insertBlockContent:function(H,E,I){var C,G,D=H.selection,M,J,B,K,F;
function L(O){var N;
if(tinymce.isIE){N=H.getDoc().body.createTextRange();
N.moveToElementText(O);
N.collapse(false);
N.select()
}else{D.select(O,1);
D.collapse(false)
}}this._insert('<span id="_marker">&nbsp;</span>',1);
G=E.get("_marker");
C=E.getParent(G,"p,h1,h2,h3,h4,h5,h6,ul,ol");
if(C){G=E.split(C,G);
A(E.create("div",0,I).childNodes,function(N){M=G.parentNode.insertBefore(N.cloneNode(true),G)
});
L(M)
}else{E.setOuterHTML(G,I);
D.select(H.getBody(),1);
D.collapse(0)
}E.remove("_marker");
J=D.getStart();
B=E.getViewPort(H.getWin());
K=H.dom.getPos(J).y;
F=J.clientHeight;
if(K<B.y||K+F>B.y+B.h){H.getDoc().body.scrollTop=K<B.y?K:K-B.h+25
}},_insert:function(D,B){var C=this.editor;
C.execCommand("Delete");
C.execCommand(tinymce.isGecko?"insertHTML":"mceInsertContent",false,D,{skip_undo:B})
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