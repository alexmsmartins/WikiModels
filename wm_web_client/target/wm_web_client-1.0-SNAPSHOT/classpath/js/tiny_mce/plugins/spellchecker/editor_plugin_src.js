(function(){var A=tinymce.util.JSONRequest,C=tinymce.each,B=tinymce.DOM;
tinymce.create("tinymce.plugins.SpellcheckerPlugin",{getInfo:function(){return{longname:"Spellchecker",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/spellchecker",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},init:function(E,F){var G=this,D;
G.url=F;
G.editor=E;
E.addCommand("mceSpellCheck",function(){if(!G.active){E.setProgressState(1);
G._sendRPC("checkWords",[G.selectedLang,G._getWords()],function(H){if(H.length>0){G.active=1;
G._markWords(H);
E.setProgressState(0);
E.nodeChanged()
}else{E.setProgressState(0);
E.windowManager.alert("spellchecker.no_mpell")
}})
}else{G._done()
}});
E.onInit.add(function(){if(E.settings.content_css!==false){E.dom.loadCSS(F+"/css/content.css")
}});
E.onClick.add(G._showMenu,G);
E.onContextMenu.add(G._showMenu,G);
E.onBeforeGetContent.add(function(){if(G.active){G._removeWords()
}});
E.onNodeChange.add(function(I,H){H.setActive("spellchecker",G.active)
});
E.onSetContent.add(function(){G._done()
});
E.onBeforeGetContent.add(function(){G._done()
});
E.onBeforeExecCommand.add(function(H,I){if(I=="mceFullScreen"){G._done()
}});
G.languages={};
C(E.getParam("spellchecker_languages","+English=en,Danish=da,Dutch=nl,Finnish=fi,French=fr,German=de,Italian=it,Polish=pl,Portuguese=pt,Spanish=es,Swedish=sv","hash"),function(I,H){if(H.indexOf("+")===0){H=H.substring(1);
G.selectedLang=I
}G.languages[H]=I
})
},createControl:function(H,D){var F=this,G,E=F.editor;
if(H=="spellchecker"){G=D.createSplitButton(H,{title:"spellchecker.desc",cmd:"mceSpellCheck",scope:F});
G.onRenderMenu.add(function(J,I){I.add({title:"spellchecker.langs","class":"mceMenuItemTitle"}).setDisabled(1);
C(F.languages,function(M,L){var N={icon:1},K;
N.onclick=function(){K.setSelected(1);
F.selectedItem.setSelected(0);
F.selectedItem=K;
F.selectedLang=M
};
N.title=L;
K=I.add(N);
K.setSelected(M==F.selectedLang);
if(M==F.selectedLang){F.selectedItem=K
}})
});
return G
}},_walk:function(G,E){var F=this.editor.getDoc(),D;
if(F.createTreeWalker){D=F.createTreeWalker(G,NodeFilter.SHOW_TEXT,null,false);
while((G=D.nextNode())!=null){E.call(this,G)
}}else{tinymce.walk(G,E,"childNodes")
}},_getSeparators:function(){var E="",D,F=this.editor.getParam("spellchecker_word_separator_chars",'\\s!"#$%&()*+,-./:;<=>?@[]^_{|}����������������\u201d\u201c');
for(D=0;
D<F.length;
D++){E+="\\"+F.charAt(D)
}return E
},_getWords:function(){var E=this.editor,G=[],D="",F={};
this._walk(E.getBody(),function(H){if(H.nodeType==3){D+=H.nodeValue+" "
}});
D=D.replace(new RegExp("([0-9]|["+this._getSeparators()+"])","g")," ");
D=tinymce.trim(D.replace(/(\s+)/g," "));
C(D.split(" "),function(H){if(!F[H]){G.push(H);
F[H]=1
}});
return G
},_removeWords:function(E){var F=this.editor,H=F.dom,G=F.selection,D=G.getBookmark();
C(H.select("span").reverse(),function(I){if(I&&(H.hasClass(I,"mceItemHiddenSpellWord")||H.hasClass(I,"mceItemHidden"))){if(!E||H.decode(I.innerHTML)==E){H.remove(I,1)
}}});
G.moveToBookmark(D)
},_markWords:function(O){var I,H,G,F,E,N="",K=this.editor,P=this._getSeparators(),J=K.dom,D=[];
var L=K.selection,M=L.getBookmark();
C(O,function(Q){N+=(N?"|":"")+Q
});
I=new RegExp("(["+P+"])("+N+")(["+P+"])","g");
H=new RegExp("^("+N+")","g");
G=new RegExp("("+N+")(["+P+"]?)$","g");
F=new RegExp("^("+N+")(["+P+"]?)$","g");
E=new RegExp("("+N+")(["+P+"])","g");
this._walk(this.editor.getBody(),function(Q){if(Q.nodeType==3){D.push(Q)
}});
C(D,function(R){var Q;
if(R.nodeType==3){Q=R.nodeValue;
if(I.test(Q)||H.test(Q)||G.test(Q)||F.test(Q)){Q=J.encode(Q);
Q=Q.replace(E,'<span class="mceItemHiddenSpellWord">$1</span>$2');
Q=Q.replace(G,'<span class="mceItemHiddenSpellWord">$1</span>$2');
J.replace(J.create("span",{"class":"mceItemHidden"},Q),R)
}}});
L.moveToBookmark(M)
},_showMenu:function(F,H){var G=this,F=G.editor,D=G._menu,J,I=F.dom,E=I.getViewPort(F.getWin());
if(!D){J=B.getPos(F.getContentAreaContainer());
D=F.controlManager.createDropMenu("spellcheckermenu",{offset_x:J.x,offset_y:J.y,"class":"mceNoIcons"});
G._menu=D
}if(I.hasClass(H.target,"mceItemHiddenSpellWord")){D.removeAll();
D.add({title:"spellchecker.wait","class":"mceMenuItemTitle"}).setDisabled(1);
G._sendRPC("getSuggestions",[G.selectedLang,I.decode(H.target.innerHTML)],function(K){D.removeAll();
if(K.length>0){D.add({title:"spellchecker.sug","class":"mceMenuItemTitle"}).setDisabled(1);
C(K,function(L){D.add({title:L,onclick:function(){I.replace(F.getDoc().createTextNode(L),H.target);
G._checkDone()
}})
});
D.addSeparator()
}else{D.add({title:"spellchecker.no_sug","class":"mceMenuItemTitle"}).setDisabled(1)
}D.add({title:"spellchecker.ignore_word",onclick:function(){I.remove(H.target,1);
G._checkDone()
}});
D.add({title:"spellchecker.ignore_words",onclick:function(){G._removeWords(I.decode(H.target.innerHTML));
G._checkDone()
}});
D.update()
});
F.selection.select(H.target);
J=I.getPos(H.target);
D.showMenu(J.x,J.y+H.target.offsetHeight-E.y);
return tinymce.dom.Event.cancel(H)
}else{D.hideMenu()
}},_checkDone:function(){var E=this,D=E.editor,G=D.dom,F;
C(G.select("span"),function(H){if(H&&G.hasClass(H,"mceItemHiddenSpellWord")){F=true;
return false
}});
if(!F){E._done()
}},_done:function(){var D=this,E=D.active;
if(D.active){D.active=0;
D._removeWords();
if(D._menu){D._menu.hideMenu()
}if(E){D.editor.nodeChanged()
}}},_sendRPC:function(E,H,D){var G=this,F=G.editor.getParam("spellchecker_rpc_url","{backend}");
if(F=="{backend}"){G.editor.setProgressState(0);
alert("Please specify: spellchecker_rpc_url");
return 
}A.sendRPC({url:F,method:E,params:H,success:D,error:function(J,I){G.editor.setProgressState(0);
G.editor.windowManager.alert(J.errstr||("Error response: "+I.responseText))
}})
}});
tinymce.PluginManager.add("spellchecker",tinymce.plugins.SpellcheckerPlugin)
})();