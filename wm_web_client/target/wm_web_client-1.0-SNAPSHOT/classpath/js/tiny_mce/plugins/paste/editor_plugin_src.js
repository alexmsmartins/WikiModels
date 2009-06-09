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
}}if(C.getParam("paste_block_drop")){C.onInit.add(function(){C.dom.bind(C.getBody(),["dragend","dragover","draggesture","dragdrop","drop","drag"],function(H){H.preventDefault();
H.stopPropagation();
return false
})
})
}E._legacySupport()
},getInfo:function(){return{longname:"Paste text/word",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/paste",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_preProcess:function(D,G){var B=this.editor,C=G.content,F,E;
function F(H){A(H,function(I){if(I.constructor==RegExp){C=C.replace(I,"")
}else{C=C.replace(I[0],I[1])
}})
}F([/^\s*(&nbsp;)+/g,/(&nbsp;|<br[^>]*>)+\s*$/g]);
if(/(class=\"?Mso|style=\"[^\"]*\bmso\-|w:WordDocument)/.test(C)){G.wordContent=true;
if(B.getParam("paste_convert_middot_lists",true)){F([[/<!--\[if !supportLists\]-->/gi,"$&__MCE_ITEM__"],[/(<span[^>]+:\s*symbol[^>]+>)/gi,"$1__MCE_ITEM__"],[/(<span[^>]+mso-list:[^>]+>)/gi,"$1__MCE_ITEM__"]])
}F([/<!--[\s\S]+?-->/gi,/<\/?(img|font|meta|link|style|div|v:\w+)[^>]*>/gi,/<\\?\?xml[^>]*>/gi,/<\/?o:[^>]*>/gi,/ (id|name|language|type|on\w+|v:\w+)=\"([^\"]*)\"/gi,/ (id|name|language|type|on\w+|v:\w+)=(\w+)/gi,[/<(\/?)s>/gi,"<$1strike>"],/<script[^>]+>[\s\S]*?<\/script>/gi,[/&nbsp;/g,"\u00a0"]]);
if(!B.getParam("paste_retain_style_properties")){F([/<\/?(span)[^>]*>/gi])
}}E=B.getParam("paste_strip_class_attributes","all");
if(E!="none"){if(E=="all"){F([/ class=\"([^\"]*)\"/gi,/ class=(\w+)/gi])
}else{F([/ class=\"(mso[^\"]*)\"/gi,/ class=(mso\w+)/gi])
}}if(B.getParam("paste_remove_spans")){F([/<\/?(span)[^>]*>/gi])
}G.content=C
},_postProcess:function(E,G){var D=this,C=D.editor,F=C.dom,B;
if(G.wordContent){A(F.select("a",G.node),function(H){if(!H.href||H.href.indexOf("#_Toc")!=-1){F.remove(H,1)
}});
if(D.editor.getParam("paste_convert_middot_lists",true)){D._convertLists(E,G)
}B=C.getParam("paste_retain_style_properties");
if(tinymce.is(B,"string")){B=tinymce.explode(B)
}A(F.select("*",G.node),function(K){var L={},I=0,J,M,H;
if(B){for(J=0;
J<B.length;
J++){M=B[J];
H=F.getStyle(K,M);
if(H){L[M]=H;
I++
}}}F.setAttrib(K,"style","");
if(B&&I>0){F.setStyles(K,L)
}else{if(K.nodeName=="SPAN"&&!K.className){F.remove(K,true)
}}})
}if(C.getParam("paste_remove_styles")||(C.getParam("paste_remove_styles_if_webkit")&&tinymce.isWebKit)){A(F.select("*[style]",G.node),function(H){H.removeAttribute("style");
H.removeAttribute("mce_style")
})
}else{if(tinymce.isWebKit){A(F.select("*",G.node),function(H){H.removeAttribute("mce_style")
})
}}},_convertLists:function(E,C){var G=E.editor.dom,F,J,B=-1,D,K=[],I,H;
A(G.select("p",C.node),function(Q){var N,R="",P,O,L,M;
for(N=Q.firstChild;
N&&N.nodeType==3;
N=N.nextSibling){R+=N.nodeValue
}R=Q.innerHTML.replace(/<\/?\w+[^>]*>/gi,"").replace(/&nbsp;/g,"\u00a0");
if(/^(__MCE_ITEM__)+[\u2022\u00b7\u00a7\u00d8o]\s*\u00a0*/.test(R)){P="ul"
}if(/^__MCE_ITEM__\s*\w+\.\s*\u00a0{2,}/.test(R)){P="ol"
}if(P){D=parseFloat(Q.style.marginLeft||0);
if(D>B){K.push(D)
}if(!F||P!=I){F=G.create(P);
G.insertAfter(F,Q)
}else{if(D>B){F=J.appendChild(G.create(P))
}else{if(D<B){L=tinymce.inArray(K,D);
M=G.getParents(F.parentNode,P);
F=M[M.length-1-L]||F
}}}A(G.select("span",Q),function(T){var S=T.innerHTML.replace(/<\/?\w+[^>]*>/gi,"");
if(P=="ul"&&/^[\u2022\u00b7\u00a7\u00d8o]/.test(S)){G.remove(T)
}else{if(/^[\s\S]*\w+\.(&nbsp;|\u00a0)*\s*/.test(S)){G.remove(T)
}}});
O=Q.innerHTML;
if(P=="ul"){O=Q.innerHTML.replace(/__MCE_ITEM__/g,"").replace(/^[\u2022\u00b7\u00a7\u00d8o]\s*(&nbsp;|\u00a0)+\s*/,"")
}else{O=Q.innerHTML.replace(/__MCE_ITEM__/g,"").replace(/^\s*\w+\.(&nbsp;|\u00a0)+\s*/,"")
}J=F.appendChild(G.create("li",0,O));
G.remove(Q);
B=D;
I=P
}else{F=B=0
}});
H=C.node.innerHTML;
if(H.indexOf("__MCE_ITEM__")!=-1){C.node.innerHTML=H.replace(/__MCE_ITEM__/g,"")
}},_insertBlockContent:function(H,E,I){var C,G,D=H.selection,M,J,B,K,F;
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
if(!C.selection.isCollapsed()){C.getDoc().execCommand("Delete",false,null)
}C.execCommand(tinymce.isGecko?"insertHTML":"mceInsertContent",false,D,{skip_undo:B})
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