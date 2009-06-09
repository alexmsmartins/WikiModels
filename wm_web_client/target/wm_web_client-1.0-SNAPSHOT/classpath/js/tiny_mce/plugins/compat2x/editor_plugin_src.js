(function(){var C=tinymce.DOM,A=tinymce.dom.Event,D=tinymce.each,B=tinymce.is;
tinymce.create("tinymce.plugins.Compat2x",{getInfo:function(){return{longname:"Compat2x",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/compat2x",version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion}
}});
(function(){tinymce.extend(tinyMCE,{addToLang:function(F,E){D(E,function(H,G){tinyMCE.i18n[(tinyMCE.settings.language||"en")+"."+(F?F+"_":"")+G]=H
})
},getInstanceById:function(E){return this.get(E)
}})
})();
(function(){var E=tinymce.EditorManager;
tinyMCE.instances={};
tinyMCE.plugins={};
tinymce.PluginManager.onAdd.add(function(F,H,G){tinyMCE.plugins[H]=G
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
tinymce.extend(tinyMCE,{getParam:function(G,F){return this.activeEditor.getParam(G,F)
},addEvent:function(H,F,G,I){tinymce.dom.Event.add(H,F,G,I||this)
},getControlHTML:function(F){return E.activeEditor.controlManager.createControl(F)
},loadCSS:function(F){tinymce.DOM.loadCSS(F)
},importCSS:function(G,F){if(G==document){this.loadCSS(F)
}else{new tinymce.dom.DOMUtils(G).loadCSS(F)
}},log:function(){console.debug.apply(console,arguments)
},getLang:function(H,G){var F=E.activeEditor.getLang(H.replace(/^lang_/g,""),G);
if(/^[0-9\-.]+$/g.test(F)){return parseInt(F)
}return F
},isInstance:function(F){return F!=null&&typeof (F)=="object"&&F.execCommand
},triggerNodeChange:function(){E.activeEditor.nodeChanged()
},regexpReplace:function(J,F,H,I){var G;
if(J==null){return J
}if(typeof (I)=="undefined"){I="g"
}G=new RegExp(F,I);
return J.replace(G,H)
},trim:function(F){return tinymce.trim(F)
},xmlEncode:function(F){return tinymce.DOM.encode(F)
},explode:function(F,H){var G=[];
tinymce.each(F.split(H),function(I){if(I!=""){G.push(I)
}});
return G
},switchClass:function(H,G){var F;
if(/^mceButton/.test(G)){F=E.activeEditor.controlManager.get(H);
if(!F){return 
}switch(G){case"mceButtonNormal":F.setDisabled(false);
F.setActive(false);
return ;
case"mceButtonDisabled":F.setDisabled(true);
return ;
case"mceButtonSelected":F.setActive(true);
F.setDisabled(false);
return 
}}},addCSSClass:function(G,H,F){return tinymce.DOM.addClass(G,H,F)
},hasCSSClass:function(F,G){return tinymce.DOM.hasClass(F,G)
},removeCSSClass:function(F,G){return tinymce.DOM.removeClass(F,G)
},getCSSClasses:function(){var F=E.activeEditor.dom.getClasses(),G=[];
D(F,function(H){G.push(H["class"])
});
return G
},setWindowArg:function(G,F){E.activeEditor.windowManager.params[G]=F
},getWindowArg:function(I,G){var H=E.activeEditor.windowManager,F;
F=H.getParam(I);
if(F===""){return""
}return F||H.getFeature(I)||G
},getParentNode:function(G,F){return this._getDOM().getParent(G,F)
},selectElements:function(L,I,K){var J,H=[],G,F;
for(F=0,I=I.split(",");
F<I.length;
F++){for(J=0,G=L.getElementsByTagName(I[F]);
J<G.length;
J++){(!K||K(G[J]))&&H.push(G[J])
}}return H
},getNodeTree:function(I,F,G,H){return this.selectNodes(I,function(J){return(!G||J.nodeType==G)&&(!H||J.nodeName==H)
},F?F:[])
},getAttrib:function(G,H,F){return this._getDOM().getAttrib(G,H,F)
},setAttrib:function(G,H,F){return this._getDOM().setAttrib(G,H,F)
},getElementsByAttributeValue:function(L,J,G,H){var I,F=L.getElementsByTagName(J),K=[];
for(I=0;
I<F.length;
I++){if(tinyMCE.getAttrib(F[I],G).indexOf(H)!=-1){K[K.length]=F[I]
}}return K
},selectNodes:function(I,H,F){var G;
if(!F){F=[]
}if(H(I)){F[F.length]=I
}if(I.hasChildNodes()){for(G=0;
G<I.childNodes.length;
G++){tinyMCE.selectNodes(I.childNodes[G],H,F)
}}return F
},getContent:function(){return E.activeEditor.getContent()
},getParentElement:function(H,F,G){if(F){F=new RegExp("^("+F.toUpperCase().replace(/,/g,"|")+")$","g")
}return this._getDOM().getParent(H,function(I){return I.nodeType==1&&(!F||F.test(I.nodeName))&&(!G||G(I))
},this.activeEditor.getBody())
},importPluginLanguagePack:function(F){tinymce.PluginManager.requireLangPack(F)
},getButtonHTML:function(L,J,H,K,I,G){var F=E.activeEditor;
H=H.replace(/\{\$pluginurl\}/g,tinyMCE.pluginURL);
H=H.replace(/\{\$themeurl\}/g,tinyMCE.themeURL);
J=J.replace(/^lang_/g,"");
return F.controlManager.createButton(L,{title:J,command:K,ui:I,value:G,scope:this,"class":"compat",image:H})
},addSelectAccessibility:function(H,G,F){if(!G._isAccessible){G.onkeydown=tinyMCE.accessibleEventHandler;
G.onblur=tinyMCE.accessibleEventHandler;
G._isAccessible=true;
G._win=F
}return false
},accessibleEventHandler:function(G){var H,F=this._win;
G=tinymce.isIE?F.event:G;
H=tinymce.isIE?G.srcElement:G.target;
if(G.type=="blur"){if(H.oldonchange){H.onchange=H.oldonchange;
H.oldonchange=null
}return true
}if(H.nodeName=="SELECT"&&!H.oldonchange){H.oldonchange=H.onchange;
H.onchange=null
}if(G.keyCode==13||G.keyCode==32){H.onchange=H.oldonchange;
H.onchange();
H.oldonchange=null;
tinyMCE.cancelEvent(G);
return false
}return true
},cancelEvent:function(F){return tinymce.dom.Event.cancel(F)
},handleVisualAid:function(F){E.activeEditor.addVisual(F)
},getAbsPosition:function(G,F){return tinymce.DOM.getPos(G,F)
},cleanupEventStr:function(F){F=""+F;
F=F.replace("function anonymous()\n{\n","");
F=F.replace("\n}","");
F=F.replace(/^return true;/gi,"");
return F
},getVisualAidClass:function(F){return F
},parseStyle:function(F){return this._getDOM().parseStyle(F)
},serializeStyle:function(F){return this._getDOM().serializeStyle(F)
},openWindow:function(H,G){var F=E.activeEditor,I={},J;
for(J in H){I[J]=H[J]
}H=I;
G=G||{};
H.url=new tinymce.util.URI(tinymce.ThemeManager.themeURLs[F.settings.theme]).toAbsolute(H.file);
H.inline=H.inline||G.inline;
F.windowManager.open(H,G)
},closeWindow:function(F){E.activeEditor.windowManager.close(F)
},getOuterHTML:function(F){return tinymce.DOM.getOuterHTML(F)
},setOuterHTML:function(G,F,H){return tinymce.DOM.setOuterHTML(G,F,H)
},hasPlugin:function(F){return tinymce.PluginManager.get(F)!=null
},_setEventsEnabled:function(){},addPlugin:function(F,H){var G=this;
function I(J){tinyMCE.selectedInstance=J;
J.onInit.add(function(){G.settings=J.settings;
G.settings.base_href=tinyMCE.documentBasePath;
tinyMCE.settings=G.settings;
tinyMCE.documentBasePath=J.documentBasePath;
if(H.initInstance){H.initInstance(J)
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
if(H.handleNodeChange){J.onNodeChange.add(function(L,K,M){H.handleNodeChange(L.id,M,0,0,false,!L.selection.isCollapsed())
})
}if(H.onChange){J.onChange.add(function(K,L){return H.onChange(K)
})
}if(H.cleanup){J.onGetContent.add(function(){})
}this.getInfo=function(){return H.getInfo()
};
this.createControl=function(K){tinyMCE.pluginURL=tinymce.baseURL+"/plugins/"+F;
tinyMCE.themeURL=tinymce.baseURL+"/themes/"+tinyMCE.activeEditor.settings.theme;
if(H.getControlHTML){return H.getControlHTML(K)
}return null
};
this.execCommand=function(L,K,M){if(H.execCommand){return H.execCommand(J.id,J.getBody(),L,K,M)
}return false
}
}tinymce.PluginManager.add(F,I)
},_getDOM:function(){return tinyMCE.activeEditor?tinyMCE.activeEditor.dom:tinymce.DOM
},convertRelativeToAbsoluteURL:function(F,G){return new tinymce.util.URI(F).toAbsolute(G)
},convertAbsoluteURLToRelativeURL:function(F,G){return new tinymce.util.URI(F).toRelative(G)
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
},translate:function(F){var H=this.settings.language,G;
if(!F){return F
}G=tinymce.EditorManager.i18n[H+"."+F]||F.replace(/{\#([^}]+)\}/g,function(J,I){return tinymce.EditorManager.i18n[H+"."+I]||"{#"+I+"}"
});
G=G.replace(/{\$lang_([^}]+)\}/g,function(J,I){return tinymce.EditorManager.i18n[H+"."+I]||"{$lang_"+I+"}"
});
return G
},repaint:function(){this.execCommand("mceRepaint")
}});
tinymce.extend(tinymce.dom.Selection.prototype,{getSelectedText:function(){return this.getContent({format:"text"})
},getSelectedHTML:function(){return this.getContent({format:"html"})
},getFocusElement:function(){return this.getNode()
},selectNode:function(I,J,G,F){var H=this;
H.select(I,G||0);
if(!B(J)){J=true
}if(J){if(!B(F)){F=true
}H.collapse(F)
}}})
}).call(this);
tinymce.PluginManager.add("compat2x",tinymce.plugins.Compat2x)
})();