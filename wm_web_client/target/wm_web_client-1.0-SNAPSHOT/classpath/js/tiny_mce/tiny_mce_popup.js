var tinymce=null,tinyMCEPopup,tinyMCE;
tinyMCEPopup={init:function(){var A=this,B,C;
B=A.getWin();
tinymce=B.tinymce;
tinyMCE=B.tinyMCE;
A.editor=tinymce.EditorManager.activeEditor;
A.params=A.editor.windowManager.params;
A.features=A.editor.windowManager.features;
A.dom=A.editor.windowManager.createInstance("tinymce.dom.DOMUtils",document);
if(A.features.popup_css!==false){A.dom.loadCSS(A.features.popup_css||A.editor.settings.popup_css)
}A.listeners=[];
A.onInit={add:function(D,E){A.listeners.push({func:D,scope:E})
}};
A.isWindow=!A.getWindowArg("mce_inline");
A.id=A.getWindowArg("mce_window_id");
A.editor.windowManager.onOpen.dispatch(A.editor.windowManager,window)
},getWin:function(){return window.dialogArguments||opener||parent||top
},getWindowArg:function(C,A){var B=this.params[C];
return tinymce.is(B)?B:A
},getParam:function(A,B){return this.editor.getParam(A,B)
},getLang:function(A,B){return this.editor.getLang(A,B)
},execCommand:function(C,D,B,A){A=A||{};
A.skip_focus=1;
this.restoreSelection();
return this.editor.execCommand(C,D,B,A)
},resizeToInnerSize:function(){var D=this,B,A=document.body,F=D.dom.getViewPort(window),E,C;
E=D.getWindowArg("mce_width")-F.w;
C=D.getWindowArg("mce_height")-F.h;
if(D.isWindow){window.resizeBy(E,C)
}else{D.editor.windowManager.resizeBy(E,C,D.id)
}},executeOnLoad:function(s){this.onInit.add(function(){eval(s)
})
},storeSelection:function(){this.editor.windowManager.bookmark=tinyMCEPopup.editor.selection.getBookmark("simple")
},restoreSelection:function(){var A=tinyMCEPopup;
if(!A.isWindow&&tinymce.isIE){A.editor.selection.moveToBookmark(A.editor.windowManager.bookmark)
}},requireLangPack:function(){var A=this,B=A.getWindowArg("plugin_url")||A.getWindowArg("theme_url");
if(B&&A.editor.settings.language&&A.features.translate_i18n!==false){B+="/langs/"+A.editor.settings.language+"_dlg.js";
if(!tinymce.ScriptLoader.isDone(B)){document.write('<script type="text/javascript" src="'+tinymce._addVer(B)+'"><\/script>');
tinymce.ScriptLoader.markDone(B)
}}},pickColor:function(A,B){this.execCommand("mceColorPicker",true,{color:document.getElementById(B).value,func:function(C){document.getElementById(B).value=C;
try{document.getElementById(B).onchange()
}catch(D){}}})
},openBrowser:function(B,C,A){tinyMCEPopup.restoreSelection();
this.editor.execCallback("file_browser_callback",B,document.getElementById(B).value,C,window)
},confirm:function(A,B,C){this.editor.windowManager.confirm(A,B,C,window)
},alert:function(A,B,C){this.editor.windowManager.alert(A,B,C,window)
},close:function(){var B=this;
function A(){B.editor.windowManager.close(window);
tinymce=tinyMCE=B.editor=B.params=B.dom=B.dom.doc=null
}if(tinymce.isOpera){B.getWin().setTimeout(A,0)
}else{A()
}},_restoreSelection:function(){var A=window.event.srcElement;
if(A.nodeName=="INPUT"&&(A.type=="submit"||A.type=="button")){tinyMCEPopup.restoreSelection()
}},_onDOMLoaded:function(){var A=tinyMCEPopup,D=document.title,C,E,B;
if(A.domLoaded){return 
}A.domLoaded=1;
if(A.features.translate_i18n!==false){E=document.body.innerHTML;
if(tinymce.isIE){E=E.replace(/ (value|title|alt)=([^"][^\s>]+)/gi,' $1="$2"')
}document.dir=A.editor.getParam("directionality","");
if((B=A.editor.translate(E))&&B!=E){document.body.innerHTML=B
}if((B=A.editor.translate(D))&&B!=D){document.title=D=B
}}document.body.style.display="";
if(tinymce.isIE){document.attachEvent("onmouseup",tinyMCEPopup._restoreSelection);
A.dom.add(A.dom.select("head")[0],"base",{target:"_self"})
}A.restoreSelection();
A.resizeToInnerSize();
if(!A.isWindow){A.editor.windowManager.setTitle(window,D)
}else{window.focus()
}if(!tinymce.isIE&&!A.isWindow){tinymce.dom.Event._add(document,"focus",function(){A.editor.windowManager.focus(A.id)
})
}tinymce.each(A.dom.select("select"),function(F){F.onkeydown=tinyMCEPopup._accessHandler
});
tinymce.each(A.listeners,function(F){F.func.call(F.scope,A.editor)
});
if(A.getWindowArg("mce_auto_focus",true)){window.focus();
tinymce.each(document.forms,function(F){tinymce.each(F.elements,function(G){if(A.dom.hasClass(G,"mceFocus")&&!G.disabled){G.focus();
return false
}})
})
}document.onkeyup=tinyMCEPopup._closeWinKeyHandler
},_accessHandler:function(A){A=A||window.event;
if(A.keyCode==13||A.keyCode==32){A=A.target||A.srcElement;
if(A.onchange){A.onchange()
}return tinymce.dom.Event.cancel(A)
}},_closeWinKeyHandler:function(A){A=A||window.event;
if(A.keyCode==27){tinyMCEPopup.close()
}},_wait:function(){if(document.attachEvent){document.attachEvent("onreadystatechange",function(){if(document.readyState==="complete"){document.detachEvent("onreadystatechange",arguments.callee);
tinyMCEPopup._onDOMLoaded()
}});
if(document.documentElement.doScroll&&window==window.top){(function(){if(tinyMCEPopup.domLoaded){return 
}try{document.documentElement.doScroll("left")
}catch(A){setTimeout(arguments.callee,0);
return 
}tinyMCEPopup._onDOMLoaded()
})()
}document.attachEvent("onload",tinyMCEPopup._onDOMLoaded)
}else{if(document.addEventListener){window.addEventListener("DOMContentLoaded",tinyMCEPopup._onDOMLoaded,false);
window.addEventListener("load",tinyMCEPopup._onDOMLoaded,false)
}}}};
tinyMCEPopup.init();
tinyMCEPopup._wait();