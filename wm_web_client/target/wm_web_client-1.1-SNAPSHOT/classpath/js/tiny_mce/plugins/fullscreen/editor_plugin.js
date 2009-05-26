(function(){var A=tinymce.DOM;
tinymce.create("tinymce.plugins.FullScreenPlugin",{init:function(F,E){var D=this,C={},B;
D.editor=F;
F.addCommand("mceFullScreen",function(){var H,G=A.doc.documentElement;
if(F.getParam("fullscreen_is_enabled")){if(F.getParam("fullscreen_new_window")){closeFullscreen()
}else{A.win.setTimeout(function(){tinymce.dom.Event.remove(A.win,"resize",D.resizeFunc);
tinyMCE.get(F.getParam("fullscreen_editor_id")).setContent(F.getContent({format:"raw"}),{format:"raw"});
tinyMCE.remove(F);
A.remove("mce_fullscreen_container");
G.style.overflow=F.getParam("fullscreen_html_overflow");
A.setStyle(A.doc.body,"overflow",F.getParam("fullscreen_overflow"));
A.win.scrollTo(F.getParam("fullscreen_scrollx"),F.getParam("fullscreen_scrolly"));
tinyMCE.settings=tinyMCE.oldSettings
},10)
}return 
}if(F.getParam("fullscreen_new_window")){H=A.win.open(E+"/fullscreen.htm","mceFullScreenPopup","fullscreen=yes,menubar=no,toolbar=no,scrollbars=no,resizable=yes,left=0,top=0,width="+screen.availWidth+",height="+screen.availHeight);
try{H.resizeTo(screen.availWidth,screen.availHeight)
}catch(I){}}else{tinyMCE.oldSettings=tinyMCE.settings;
C.fullscreen_overflow=A.getStyle(A.doc.body,"overflow",1)||"auto";
C.fullscreen_html_overflow=A.getStyle(G,"overflow",1);
B=A.getViewPort();
C.fullscreen_scrollx=B.x;
C.fullscreen_scrolly=B.y;
if(tinymce.isOpera&&C.fullscreen_overflow=="visible"){C.fullscreen_overflow="auto"
}if(tinymce.isIE&&C.fullscreen_overflow=="scroll"){C.fullscreen_overflow="auto"
}if(tinymce.isIE&&(C.fullscreen_html_overflow=="visible"||C.fullscreen_html_overflow=="scroll")){C.fullscreen_html_overflow="auto"
}if(C.fullscreen_overflow=="0px"){C.fullscreen_overflow=""
}A.setStyle(A.doc.body,"overflow","hidden");
G.style.overflow="hidden";
B=A.getViewPort();
A.win.scrollTo(0,0);
if(tinymce.isIE){B.h-=1
}n=A.add(A.doc.body,"div",{id:"mce_fullscreen_container",style:"position:"+(tinymce.isIE6||(tinymce.isIE&&!A.boxModel)?"absolute":"fixed")+";top:0;left:0;width:"+B.w+"px;height:"+B.h+"px;z-index:200000;"});
A.add(n,"div",{id:"mce_fullscreen"});
tinymce.each(F.settings,function(K,J){C[J]=K
});
C.id="mce_fullscreen";
C.width=n.clientWidth;
C.height=n.clientHeight-15;
C.fullscreen_is_enabled=true;
C.fullscreen_editor_id=F.id;
C.theme_advanced_resizing=false;
C.save_onsavecallback=function(){F.setContent(tinyMCE.get(C.id).getContent({format:"raw"}),{format:"raw"});
F.execCommand("mceSave")
};
tinymce.each(F.getParam("fullscreen_settings"),function(J,K){C[K]=J
});
if(C.theme_advanced_toolbar_location==="external"){C.theme_advanced_toolbar_location="top"
}D.fullscreenEditor=new tinymce.Editor("mce_fullscreen",C);
D.fullscreenEditor.onInit.add(function(){D.fullscreenEditor.setContent(F.getContent());
D.fullscreenEditor.focus()
});
D.fullscreenEditor.render();
tinyMCE.add(D.fullscreenEditor);
D.fullscreenElement=new tinymce.dom.Element("mce_fullscreen_container");
D.fullscreenElement.update();
D.resizeFunc=tinymce.dom.Event.add(A.win,"resize",function(){var J=tinymce.DOM.getViewPort();
D.fullscreenEditor.theme.resizeTo(J.w,J.h)
})
}});
F.addButton("fullscreen",{title:"fullscreen.desc",cmd:"mceFullScreen"});
F.onNodeChange.add(function(G,H){H.setActive("fullscreen",G.getParam("fullscreen_is_enabled"))
})
},getInfo:function(){return{longname:"Fullscreen",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/fullscreen",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("fullscreen",tinymce.plugins.FullScreenPlugin)
})();