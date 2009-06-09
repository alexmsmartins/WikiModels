(function(){tinymce.create("tinymce.plugins.AdvancedLinkPlugin",{init:function(B,A){this.editor=B;
B.addCommand("mceAdvLink",function(){var C=B.selection;
if(C.isCollapsed()&&!B.dom.getParent(C.getNode(),"A")){return 
}B.windowManager.open({file:A+"/link.htm",width:480+parseInt(B.getLang("advlink.delta_width",0)),height:400+parseInt(B.getLang("advlink.delta_height",0)),inline:1},{plugin_url:A})
});
B.addButton("link",{title:"advlink.link_desc",cmd:"mceAdvLink"});
B.addShortcut("ctrl+k","advlink.advlink_desc","mceAdvLink");
B.onNodeChange.add(function(E,F,C,D){F.setDisabled("link",D&&C.nodeName!="A");
F.setActive("link",C.nodeName=="A"&&!C.name)
})
},getInfo:function(){return{longname:"Advanced link",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/advlink",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("advlink",tinymce.plugins.AdvancedLinkPlugin)
})();