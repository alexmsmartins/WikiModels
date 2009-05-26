(function(){tinymce.create("tinymce.plugins.AdvancedLinkPlugin",{init:function(A,B){this.editor=A;
A.addCommand("mceAdvLink",function(){var C=A.selection;
if(C.isCollapsed()&&!A.dom.getParent(C.getNode(),"A")){return 
}A.windowManager.open({file:B+"/link.htm",width:480+parseInt(A.getLang("advlink.delta_width",0)),height:400+parseInt(A.getLang("advlink.delta_height",0)),inline:1},{plugin_url:B})
});
A.addButton("link",{title:"advlink.link_desc",cmd:"mceAdvLink"});
A.addShortcut("ctrl+k","advlink.advlink_desc","mceAdvLink");
A.onNodeChange.add(function(D,C,F,E){C.setDisabled("link",E&&F.nodeName!="A");
C.setActive("link",F.nodeName=="A"&&!F.name)
})
},getInfo:function(){return{longname:"Advanced link",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/advlink",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("advlink",tinymce.plugins.AdvancedLinkPlugin)
})();