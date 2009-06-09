(function(){var A=tinymce.DOM;
tinymce.create("tinymce.plugins.FullScreenPlugin",{init:function(C,D){var E=this,F={},B;
E.editor=C;
C.addCommand("mceFullScreen",function(){var H,I=A.doc.documentElement;
if(C.getParam("fullscreen_is_enabled")){if(C.getParam("fullscreen_new_window")){closeFullscreen()
}else{A.win.setTimeout(function(){tinymce.dom.Event.remove(A.win,"resize",E.resizeFunc);
tinyMCE.get(C.getParam("fullscreen_editor_id")).setContent(C.getContent({format:"raw"}),{format:"raw"});
tinyMCE.remove(C);
A.remove("mce_fullscreen_container");
I.style.overflow=C.getParam("fullscreen_html_overflow");
A.setStyle(A.doc.body,"overflow",C.getParam("fullscreen_overflow"));
A.win.scrollTo(C.getParam("fullscreen_scrollx"),C.getParam("fullscreen_scrolly"));
tinyMCE.settings=tinyMCE.oldSettings
},10)
}return 
}if(C.getParam("fullscreen_new_window")){H=A.win.open(D+"/fullscreen.htm","mceFullScreenPopup","fullscreen=yes,menubar=no,toolbar=no,scrollbars=no,resizable=yes,left=0,top=0,width="+screen.availWidth+",height="+screen.availHeight);
try{H.resizeTo(screen.availWidth,screen.availHeight)
}catch(G){}}else{tinyMCE.oldSettings=tinyMCE.settings;
F.fullscreen_overflow=A.getStyle(A.doc.body,"overflow",1)||"auto";
F.fullscreen_html_overflow=A.getStyle(I,"overflow",1);
B=A.getViewPort();
F.fullscreen_scrollx=B.x;
F.fullscreen_scrolly=B.y;
if(tinymce.isOpera&&F.fullscreen_overflow=="visible"){F.fullscreen_overflow="auto"
}if(tinymce.isIE&&F.fullscreen_overflow=="scroll"){F.fullscreen_overflow="auto"
}if(tinymce.isIE&&(F.fullscreen_html_overflow=="visible"||F.fullscreen_html_overflow=="scroll")){F.fullscreen_html_overflow="auto"
}if(F.fullscreen_overflow=="0px"){F.fullscreen_overflow=""
}A.setStyle(A.doc.body,"overflow","hidden");
I.style.overflow="hidden";
B=A.getViewPort();
A.win.scrollTo(0,0);
if(tinymce.isIE){B.h-=1
}n=A.add(A.doc.body,"div",{id:"mce_fullscreen_container",style:"position:"+(tinymce.isIE6||(tinymce.isIE&&!A.boxModel)?"absolute":"fixed")+";top:0;left:0;width:"+B.w+"px;height:"+B.h+"px;z-index:200000;"});
A.add(n,"div",{id:"mce_fullscreen"});
tinymce.each(C.settings,function(J,K){F[K]=J
});
F.id="mce_fullscreen";
F.width=n.clientWidth;
F.height=n.clientHeight-15;
F.fullscreen_is_enabled=true;
F.fullscreen_editor_id=C.id;
F.theme_advanced_resizing=false;
F.save_onsavecallback=function(){C.setContent(tinyMCE.get(F.id).getContent({format:"raw"}),{format:"raw"});
C.execCommand("mceSave")
};
tinymce.each(C.getParam("fullscreen_settings"),function(K,J){F[J]=K
});
if(F.theme_advanced_toolbar_location==="external"){F.theme_advanced_toolbar_location="top"
}E.fullscreenEditor=new tinymce.Editor("mce_fullscreen",F);
E.fullscreenEditor.onInit.add(function(){E.fullscreenEditor.setContent(C.getContent());
E.fullscreenEditor.focus()
});
E.fullscreenEditor.render();
tinyMCE.add(E.fullscreenEditor);
E.fullscreenElement=new tinymce.dom.Element("mce_fullscreen_container");
E.fullscreenElement.update();
E.resizeFunc=tinymce.dom.Event.add(A.win,"resize",function(){var J=tinymce.DOM.getViewPort();
E.fullscreenEditor.theme.resizeTo(J.w,J.h)
})
}});
C.addButton("fullscreen",{title:"fullscreen.desc",cmd:"mceFullScreen"});
C.onNodeChange.add(function(H,G){G.setActive("fullscreen",H.getParam("fullscreen_is_enabled"))
})
},getInfo:function(){return{longname:"Fullscreen",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/fullscreen",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("fullscreen",tinymce.plugins.FullScreenPlugin)
})();