(function(){var B=tinymce.util.JSONRequest,C=tinymce.each,A=tinymce.DOM;
tinymce.create("tinymce.plugins.SpellcheckerPlugin",{getInfo:function(){return{longname:"Spellchecker",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/spellchecker",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},init:function(F,E){var D=this,G;
D.url=E;
D.editor=F;
F.addCommand("mceSpellCheck",function(){if(!D.active){F.setProgressState(1);
D._sendRPC("checkWords",[D.selectedLang,D._getWords()],function(H){if(H.length>0){D.active=1;
D._markWords(H);
F.setProgressState(0);
F.nodeChanged()
}else{F.setProgressState(0);
F.windowManager.alert("spellchecker.no_mpell")
}})
}else{D._done()
}});
F.onInit.add(function(){if(F.settings.content_css!==false){F.dom.loadCSS(E+"/css/content.css")
}});
F.onClick.add(D._showMenu,D);
F.onContextMenu.add(D._showMenu,D);
F.onBeforeGetContent.add(function(){if(D.active){D._removeWords()
}});
F.onNodeChange.add(function(H,I){I.setActive("spellchecker",D.active)
});
F.onSetContent.add(function(){D._done()
});
F.onBeforeGetContent.add(function(){D._done()
});
F.onBeforeExecCommand.add(function(I,H){if(H=="mceFullScreen"){D._done()
}});
D.languages={};
C(F.getParam("spellchecker_languages","+English=en,Danish=da,Dutch=nl,Finnish=fi,French=fr,German=de,Italian=it,Polish=pl,Portuguese=pt,Spanish=es,Swedish=sv","hash"),function(H,I){if(I.indexOf("+")===0){I=I.substring(1);
D.selectedLang=H
}D.languages[I]=H
})
},createControl:function(D,H){var F=this,E,G=F.editor;
if(D=="spellchecker"){E=H.createSplitButton(D,{title:"spellchecker.desc",cmd:"mceSpellCheck",scope:F});
E.onRenderMenu.add(function(I,J){J.add({title:"spellchecker.langs","class":"mceMenuItemTitle"}).setDisabled(1);
C(F.languages,function(N,K){var M={icon:1},L;
M.onclick=function(){L.setSelected(1);
F.selectedItem.setSelected(0);
F.selectedItem=L;
F.selectedLang=N
};
M.title=K;
L=J.add(M);
L.setSelected(N==F.selectedLang);
if(N==F.selectedLang){F.selectedItem=L
}})
});
return E
}},_walk:function(D,F){var E=this.editor.getDoc(),G;
if(E.createTreeWalker){G=E.createTreeWalker(D,NodeFilter.SHOW_TEXT,null,false);
while((D=G.nextNode())!=null){F.call(this,D)
}}else{tinymce.walk(D,F,"childNodes")
}},_getSeparators:function(){var E="",F,D=this.editor.getParam("spellchecker_word_separator_chars",'\\s!"#$%&()*+,-./:;<=>?@[]^_{|}����������������\u201d\u201c');
for(F=0;
F<D.length;
F++){E+="\\"+D.charAt(F)
}return E
},_getWords:function(){var F=this.editor,D=[],G="",E={};
this._walk(F.getBody(),function(H){if(H.nodeType==3){G+=H.nodeValue+" "
}});
G=G.replace(new RegExp("([0-9]|["+this._getSeparators()+"])","g")," ");
G=tinymce.trim(G.replace(/(\s+)/g," "));
C(G.split(" "),function(H){if(!E[H]){D.push(H);
E[H]=1
}});
return D
},_removeWords:function(G){var F=this.editor,D=F.dom,E=F.selection,H=E.getBookmark();
C(D.select("span").reverse(),function(I){if(I&&(D.hasClass(I,"mceItemHiddenSpellWord")||D.hasClass(I,"mceItemHidden"))){if(!G||D.decode(I.innerHTML)==G){D.remove(I,1)
}}});
E.moveToBookmark(H)
},_markWords:function(E){var K,L,M,N,O,F="",I=this.editor,D=this._getSeparators(),J=I.dom,P=[];
var H=I.selection,G=H.getBookmark();
C(E,function(Q){F+=(F?"|":"")+Q
});
K=new RegExp("(["+D+"])("+F+")(["+D+"])","g");
L=new RegExp("^("+F+")","g");
M=new RegExp("("+F+")(["+D+"]?)$","g");
N=new RegExp("^("+F+")(["+D+"]?)$","g");
O=new RegExp("("+F+")(["+D+"])","g");
this._walk(this.editor.getBody(),function(Q){if(Q.nodeType==3){P.push(Q)
}});
C(P,function(Q){var R;
if(Q.nodeType==3){R=Q.nodeValue;
if(K.test(R)||L.test(R)||M.test(R)||N.test(R)){R=J.encode(R);
R=R.replace(O,'<span class="mceItemHiddenSpellWord">$1</span>$2');
R=R.replace(M,'<span class="mceItemHiddenSpellWord">$1</span>$2');
J.replace(J.create("span",{"class":"mceItemHidden"},R),Q)
}}});
H.moveToBookmark(G)
},_showMenu:function(H,F){var G=this,H=G.editor,J=G._menu,D,E=H.dom,I=E.getViewPort(H.getWin());
if(!J){D=A.getPos(H.getContentAreaContainer());
J=H.controlManager.createDropMenu("spellcheckermenu",{offset_x:D.x,offset_y:D.y,"class":"mceNoIcons"});
G._menu=J
}if(E.hasClass(F.target,"mceItemHiddenSpellWord")){J.removeAll();
J.add({title:"spellchecker.wait","class":"mceMenuItemTitle"}).setDisabled(1);
G._sendRPC("getSuggestions",[G.selectedLang,E.decode(F.target.innerHTML)],function(K){J.removeAll();
if(K.length>0){J.add({title:"spellchecker.sug","class":"mceMenuItemTitle"}).setDisabled(1);
C(K,function(L){J.add({title:L,onclick:function(){E.replace(H.getDoc().createTextNode(L),F.target);
G._checkDone()
}})
});
J.addSeparator()
}else{J.add({title:"spellchecker.no_sug","class":"mceMenuItemTitle"}).setDisabled(1)
}J.add({title:"spellchecker.ignore_word",onclick:function(){E.remove(F.target,1);
G._checkDone()
}});
J.add({title:"spellchecker.ignore_words",onclick:function(){G._removeWords(E.decode(F.target.innerHTML));
G._checkDone()
}});
J.update()
});
H.selection.select(F.target);
D=E.getPos(F.target);
J.showMenu(D.x,D.y+F.target.offsetHeight-I.y);
return tinymce.dom.Event.cancel(F)
}else{J.hideMenu()
}},_checkDone:function(){var F=this,G=F.editor,D=G.dom,E;
C(D.select("span"),function(H){if(H&&D.hasClass(H,"mceItemHiddenSpellWord")){E=true;
return false
}});
if(!E){F._done()
}},_done:function(){var E=this,D=E.active;
if(E.active){E.active=0;
E._removeWords();
if(E._menu){E._menu.hideMenu()
}if(D){E.editor.nodeChanged()
}}},_sendRPC:function(G,D,H){var E=this,F=E.editor.getParam("spellchecker_rpc_url","{backend}");
if(F=="{backend}"){E.editor.setProgressState(0);
alert("Please specify: spellchecker_rpc_url");
return 
}B.sendRPC({url:F,method:G,params:D,success:H,error:function(I,J){E.editor.setProgressState(0);
E.editor.windowManager.alert(I.errstr||("Error response: "+J.responseText))
}})
}});
tinymce.PluginManager.add("spellchecker",tinymce.plugins.SpellcheckerPlugin)
})();