(function(){var D=tinymce.DOM,B=tinymce.dom.Event,C=tinymce.each,A=tinymce.is;
tinymce.create("tinymce.plugins.Compat2x",{getInfo:function(){return{longname:"Compat2x",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/compat2x",version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion}
}});
(function(){tinymce.extend(tinyMCE,{addToLang:function(E,F){C(F,function(G,H){tinyMCE.i18n[(tinyMCE.settings.language||"en")+"."+(E?E+"_":"")+H]=G
})
},getInstanceById:function(E){return this.get(E)
}})
})();
(function(){var E=tinymce.EditorManager;
tinyMCE.instances={};
tinyMCE.plugins={};
tinymce.PluginManager.onAdd.add(function(H,F,G){tinyMCE.plugins[F]=G
});
tinyMCE.majorVersion=tinymce.majorVersion;
tinyMCE.minorVersion=tinymce.minorVersion;
tinyMCE.releaseDate=tinymce.releaseDate;
tinyMCE.baseURL=tinymce.baseURL;
tinyMCE.isIE=tinyMCE.isMSIE=tinymce.isIE||tinymce.isOpera;
tinyMCE.isMSIE5=tinymce.isIE;
tinyMCE.isMSIE5_0=tinymce.isIE;
tinyMCE.isMSIE7=tinymce.isIE;
tinyMCE.isGecko=tinymce.isGecko;
tinyMCE.isSafari=tinymce.isWebKit;
tinyMCE.isOpera=tinymce.isOpera;
tinyMCE.isMac=false;
tinyMCE.isNS7=false;
tinyMCE.isNS71=false;
tinyMCE.compat=true;
TinyMCE_Engine=tinyMCE;
tinymce.extend(tinyMCE,{getParam:function(F,G){return this.activeEditor.getParam(F,G)
},addEvent:function(G,I,H,F){tinymce.dom.Event.add(G,I,H,F||this)
},getControlHTML:function(F){return E.activeEditor.controlManager.createControl(F)
},loadCSS:function(F){tinymce.DOM.loadCSS(F)
},importCSS:function(F,G){if(F==document){this.loadCSS(G)
}else{new tinymce.dom.DOMUtils(F).loadCSS(G)
}},log:function(){console.debug.apply(console,arguments)
},getLang:function(F,G){var H=E.activeEditor.getLang(F.replace(/^lang_/g,""),G);
if(/^[0-9\-.]+$/g.test(H)){return parseInt(H)
}return H
},isInstance:function(F){return F!=null&&typeof (F)=="object"&&F.execCommand
},triggerNodeChange:function(){E.activeEditor.nodeChanged()
},regexpReplace:function(F,J,H,G){var I;
if(F==null){return F
}if(typeof (G)=="undefined"){G="g"
}I=new RegExp(J,G);
return F.replace(I,H)
},trim:function(F){return tinymce.trim(F)
},xmlEncode:function(F){return tinymce.DOM.encode(F)
},explode:function(H,F){var G=[];
tinymce.each(H.split(F),function(I){if(I!=""){G.push(I)
}});
return G
},switchClass:function(F,G){var H;
if(/^mceButton/.test(G)){H=E.activeEditor.controlManager.get(F);
if(!H){return 
}switch(G){case"mceButtonNormal":H.setDisabled(false);
H.setActive(false);
return ;
case"mceButtonDisabled":H.setDisabled(true);
return ;
case"mceButtonSelected":H.setActive(true);
H.setDisabled(false);
return 
}}},addCSSClass:function(G,F,H){return tinymce.DOM.addClass(G,F,H)
},hasCSSClass:function(G,F){return tinymce.DOM.hasClass(G,F)
},removeCSSClass:function(G,F){return tinymce.DOM.removeClass(G,F)
},getCSSClasses:function(){var G=E.activeEditor.dom.getClasses(),F=[];
C(G,function(H){F.push(H["class"])
});
return F
},setWindowArg:function(F,G){E.activeEditor.windowManager.params[F]=G
},getWindowArg:function(F,H){var G=E.activeEditor.windowManager,I;
I=G.getParam(F);
if(I===""){return""
}return I||G.getFeature(F)||H
},getParentNode:function(F,G){return this._getDOM().getParent(F,G)
},selectElements:function(L,H,F){var G,I=[],J,K;
for(K=0,H=H.split(",");
K<H.length;
K++){for(G=0,J=L.getElementsByTagName(H[K]);
G<J.length;
G++){(!F||F(J[G]))&&I.push(J[G])
}}return I
},getNodeTree:function(F,I,H,G){return this.selectNodes(F,function(J){return(!H||J.nodeType==H)&&(!G||J.nodeName==G)
},I?I:[])
},getAttrib:function(G,F,H){return this._getDOM().getAttrib(G,F,H)
},setAttrib:function(G,F,H){return this._getDOM().setAttrib(G,F,H)
},getElementsByAttributeValue:function(F,H,K,J){var I,L=F.getElementsByTagName(H),G=[];
for(I=0;
I<L.length;
I++){if(tinyMCE.getAttrib(L[I],K).indexOf(J)!=-1){G[G.length]=L[I]
}}return G
},selectNodes:function(F,G,I){var H;
if(!I){I=[]
}if(G(F)){I[I.length]=F
}if(F.hasChildNodes()){for(H=0;
H<F.childNodes.length;
H++){tinyMCE.selectNodes(F.childNodes[H],G,I)
}}return I
},getContent:function(){return E.activeEditor.getContent()
},getParentElement:function(F,H,G){if(H){H=new RegExp("^("+H.toUpperCase().replace(/,/g,"|")+")$","g")
}return this._getDOM().getParent(F,function(I){return I.nodeType==1&&(!H||H.test(I.nodeName))&&(!G||G(I))
},this.activeEditor.getBody())
},importPluginLanguagePack:function(F){tinymce.PluginManager.requireLangPack(F)
},getButtonHTML:function(F,H,J,G,I,K){var L=E.activeEditor;
J=J.replace(/\{\$pluginurl\}/g,tinyMCE.pluginURL);
J=J.replace(/\{\$themeurl\}/g,tinyMCE.themeURL);
H=H.replace(/^lang_/g,"");
return L.controlManager.createButton(F,{title:H,command:G,ui:I,value:K,scope:this,"class":"compat",image:J})
},addSelectAccessibility:function(F,G,H){if(!G._isAccessible){G.onkeydown=tinyMCE.accessibleEventHandler;
G.onblur=tinyMCE.accessibleEventHandler;
G._isAccessible=true;
G._win=H
}return false
},accessibleEventHandler:function(G){var F,H=this._win;
G=tinymce.isIE?H.event:G;
F=tinymce.isIE?G.srcElement:G.target;
if(G.type=="blur"){if(F.oldonchange){F.onchange=F.oldonchange;
F.oldonchange=null
}return true
}if(F.nodeName=="SELECT"&&!F.oldonchange){F.oldonchange=F.onchange;
F.onchange=null
}if(G.keyCode==13||G.keyCode==32){F.onchange=F.oldonchange;
F.onchange();
F.oldonchange=null;
tinyMCE.cancelEvent(G);
return false
}return true
},cancelEvent:function(F){return tinymce.dom.Event.cancel(F)
},handleVisualAid:function(F){E.activeEditor.addVisual(F)
},getAbsPosition:function(F,G){return tinymce.DOM.getPos(F,G)
},cleanupEventStr:function(F){F=""+F;
F=F.replace("function anonymous()\n{\n","");
F=F.replace("\n}","");
F=F.replace(/^return true;/gi,"");
return F
},getVisualAidClass:function(F){return F
},parseStyle:function(F){return this._getDOM().parseStyle(F)
},serializeStyle:function(F){return this._getDOM().serializeStyle(F)
},openWindow:function(H,I){var J=E.activeEditor,G={},F;
for(F in H){G[F]=H[F]
}H=G;
I=I||{};
H.url=new tinymce.util.URI(tinymce.ThemeManager.themeURLs[J.settings.theme]).toAbsolute(H.file);
H.inline=H.inline||I.inline;
J.windowManager.open(H,I)
},closeWindow:function(F){E.activeEditor.windowManager.close(F)
},getOuterHTML:function(F){return tinymce.DOM.getOuterHTML(F)
},setOuterHTML:function(G,H,F){return tinymce.DOM.setOuterHTML(G,H,F)
},hasPlugin:function(F){return tinymce.PluginManager.get(F)!=null
},_setEventsEnabled:function(){},addPlugin:function(I,G){var H=this;
function F(J){tinyMCE.selectedInstance=J;
J.onInit.add(function(){H.settings=J.settings;
H.settings.base_href=tinyMCE.documentBasePath;
tinyMCE.settings=H.settings;
tinyMCE.documentBasePath=J.documentBasePath;
if(G.initInstance){G.initInstance(J)
}J.contentDocument=J.getDoc();
J.contentWindow=J.getWin();
J.undoRedo=J.undoManager;
J.startContent=J.getContent({format:"raw"});
tinyMCE.instances[J.id]=J;
tinyMCE.loadedFiles=[]
});
J.onActivate.add(function(){tinyMCE.settings=J.settings;
tinyMCE.selectedInstance=J
});
if(G.handleNodeChange){J.onNodeChange.add(function(L,M,K){G.handleNodeChange(L.id,K,0,0,false,!L.selection.isCollapsed())
})
}if(G.onChange){J.onChange.add(function(L,K){return G.onChange(L)
})
}if(G.cleanup){J.onGetContent.add(function(){})
}this.getInfo=function(){return G.getInfo()
};
this.createControl=function(K){tinyMCE.pluginURL=tinymce.baseURL+"/plugins/"+I;
tinyMCE.themeURL=tinymce.baseURL+"/themes/"+tinyMCE.activeEditor.settings.theme;
if(G.getControlHTML){return G.getControlHTML(K)
}return null
};
this.execCommand=function(L,M,K){if(G.execCommand){return G.execCommand(J.id,J.getBody(),L,M,K)
}return false
}
}tinymce.PluginManager.add(I,F)
},_getDOM:function(){return tinyMCE.activeEditor?tinyMCE.activeEditor.dom:tinymce.DOM
},convertRelativeToAbsoluteURL:function(G,F){return new tinymce.util.URI(G).toAbsolute(F)
},convertAbsoluteURLToRelativeURL:function(G,F){return new tinymce.util.URI(G).toRelative(F)
}});
tinymce.extend(tinymce.Editor.prototype,{getFocusElement:function(){return this.selection.getNode()
},getData:function(F){if(!this.data){this.data=[]
}if(!this.data[F]){this.data[F]=[]
}return this.data[F]
},hasPlugin:function(F){return this.plugins[F]!=null
},getContainerWin:function(){return window
},getHTML:function(F){return this.getContent({format:F?"raw":"html"})
},setHTML:function(F){this.setContent(F)
},getSel:function(){return this.selection.getSel()
},getRng:function(){return this.selection.getRng()
},isHidden:function(){var F;
if(!tinymce.isGecko){return false
}F=this.getSel();
return(!F||!F.rangeCount||F.rangeCount==0)
},translate:function(H){var F=this.settings.language,G;
if(!H){return H
}G=tinymce.EditorManager.i18n[F+"."+H]||H.replace(/{\#([^}]+)\}/g,function(I,J){return tinymce.EditorManager.i18n[F+"."+J]||"{#"+J+"}"
});
G=G.replace(/{\$lang_([^}]+)\}/g,function(I,J){return tinymce.EditorManager.i18n[F+"."+J]||"{$lang_"+J+"}"
});
return G
},repaint:function(){this.execCommand("mceRepaint")
}});
tinymce.extend(tinymce.dom.Selection.prototype,{getSelectedText:function(){return this.getContent({format:"text"})
},getSelectedHTML:function(){return this.getContent({format:"html"})
},getFocusElement:function(){return this.getNode()
},selectNode:function(G,F,I,J){var H=this;
H.select(G,I||0);
if(!A(F)){F=true
}if(F){if(!A(J)){J=true
}H.collapse(J)
}}})
}).call(this);
tinymce.PluginManager.add("compat2x",tinymce.plugins.Compat2x)
})();