(function(){var A=tinymce.dom.Event,C=tinymce.each,B=tinymce.DOM;
tinymce.create("tinymce.plugins.ContextMenu",{init:function(D){var F=this;
F.editor=D;
F.onContextMenu=new tinymce.util.Dispatcher(this);
D.onContextMenu.add(function(G,H){if(!H.ctrlKey){F._getMenu(G).showMenu(H.clientX,H.clientY);
A.add(G.getDoc(),"click",E);
A.cancel(H)
}});
function E(){if(F._menu){F._menu.removeAll();
F._menu.destroy();
A.remove(D.getDoc(),"click",E)
}}D.onMouseDown.add(E);
D.onKeyDown.add(E)
},getInfo:function(){return{longname:"Contextmenu",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/contextmenu",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_getMenu:function(H){var L=this,F=L._menu,I=H.selection,E=I.isCollapsed(),D=I.getNode()||H.getBody(),G,K,J;
if(F){F.removeAll();
F.destroy()
}K=B.getPos(H.getContentAreaContainer());
J=B.getPos(H.getContainer());
F=H.controlManager.createDropMenu("contextmenu",{offset_x:K.x+H.getParam("contextmenu_offset_x",0),offset_y:K.y+H.getParam("contextmenu_offset_y",0),constrain:1});
L._menu=F;
F.add({title:"advanced.cut_desc",icon:"cut",cmd:"Cut"}).setDisabled(E);
F.add({title:"advanced.copy_desc",icon:"copy",cmd:"Copy"}).setDisabled(E);
F.add({title:"advanced.paste_desc",icon:"paste",cmd:"Paste"});
if((D.nodeName=="A"&&!H.dom.getAttrib(D,"name"))||!E){F.addSeparator();
F.add({title:"advanced.link_desc",icon:"link",cmd:H.plugins.advlink?"mceAdvLink":"mceLink",ui:true});
F.add({title:"advanced.unlink_desc",icon:"unlink",cmd:"UnLink"})
}F.addSeparator();
F.add({title:"advanced.image_desc",icon:"image",cmd:H.plugins.advimage?"mceAdvImage":"mceImage",ui:true});
F.addSeparator();
G=F.addMenu({title:"contextmenu.align"});
G.add({title:"contextmenu.left",icon:"justifyleft",cmd:"JustifyLeft"});
G.add({title:"contextmenu.center",icon:"justifycenter",cmd:"JustifyCenter"});
G.add({title:"contextmenu.right",icon:"justifyright",cmd:"JustifyRight"});
G.add({title:"contextmenu.full",icon:"justifyfull",cmd:"JustifyFull"});
L.onContextMenu.dispatch(L,F,D,E);
return F
}});
tinymce.PluginManager.add("contextmenu",tinymce.plugins.ContextMenu)
})();