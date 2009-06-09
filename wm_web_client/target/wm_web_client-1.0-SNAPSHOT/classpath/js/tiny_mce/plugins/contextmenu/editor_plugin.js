(function(){var B=tinymce.dom.Event,C=tinymce.each,A=tinymce.DOM;
tinymce.create("tinymce.plugins.ContextMenu",{init:function(F){var D=this;
D.editor=F;
D.onContextMenu=new tinymce.util.Dispatcher(this);
F.onContextMenu.add(function(H,G){if(!G.ctrlKey){D._getMenu(H).showMenu(G.clientX,G.clientY);
B.add(H.getDoc(),"click",E);
B.cancel(G)
}});
function E(){if(D._menu){D._menu.removeAll();
D._menu.destroy();
B.remove(F.getDoc(),"click",E)
}}F.onMouseDown.add(E);
F.onKeyDown.add(E)
},getInfo:function(){return{longname:"Contextmenu",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/contextmenu",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_getMenu:function(H){var D=this,J=D._menu,G=H.selection,K=G.isCollapsed(),L=G.getNode()||H.getBody(),I,E,F;
if(J){J.removeAll();
J.destroy()
}E=A.getPos(H.getContentAreaContainer());
F=A.getPos(H.getContainer());
J=H.controlManager.createDropMenu("contextmenu",{offset_x:E.x+H.getParam("contextmenu_offset_x",0),offset_y:E.y+H.getParam("contextmenu_offset_y",0),constrain:1});
D._menu=J;
J.add({title:"advanced.cut_desc",icon:"cut",cmd:"Cut"}).setDisabled(K);
J.add({title:"advanced.copy_desc",icon:"copy",cmd:"Copy"}).setDisabled(K);
J.add({title:"advanced.paste_desc",icon:"paste",cmd:"Paste"});
if((L.nodeName=="A"&&!H.dom.getAttrib(L,"name"))||!K){J.addSeparator();
J.add({title:"advanced.link_desc",icon:"link",cmd:H.plugins.advlink?"mceAdvLink":"mceLink",ui:true});
J.add({title:"advanced.unlink_desc",icon:"unlink",cmd:"UnLink"})
}J.addSeparator();
J.add({title:"advanced.image_desc",icon:"image",cmd:H.plugins.advimage?"mceAdvImage":"mceImage",ui:true});
J.addSeparator();
I=J.addMenu({title:"contextmenu.align"});
I.add({title:"contextmenu.left",icon:"justifyleft",cmd:"JustifyLeft"});
I.add({title:"contextmenu.center",icon:"justifycenter",cmd:"JustifyCenter"});
I.add({title:"contextmenu.right",icon:"justifyright",cmd:"JustifyRight"});
I.add({title:"contextmenu.full",icon:"justifyfull",cmd:"JustifyFull"});
D.onContextMenu.dispatch(D,J,L,K);
return J
}});
tinymce.PluginManager.add("contextmenu",tinymce.plugins.ContextMenu)
})();