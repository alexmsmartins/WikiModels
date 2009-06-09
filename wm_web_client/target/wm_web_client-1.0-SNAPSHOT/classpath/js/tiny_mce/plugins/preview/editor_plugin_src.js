(function(){tinymce.create("tinymce.plugins.Preview",{init:function(A,B){var D=this,C=tinymce.explode(A.settings.content_css);
D.editor=A;
tinymce.each(C,function(F,E){C[E]=A.documentBaseURI.toAbsolute(F)
});
A.addCommand("mcePreview",function(){A.windowManager.open({file:A.getParam("plugin_preview_pageurl",B+"/preview.html"),width:parseInt(A.getParam("plugin_preview_width","550")),height:parseInt(A.getParam("plugin_preview_height","600")),resizable:"yes",scrollbars:"yes",popup_css:C?C.join(","):A.baseURI.toAbsolute("themes/"+A.settings.theme+"/skins/"+A.settings.skin+"/content.css"),inline:A.getParam("plugin_preview_inline",1)},{base:A.documentBaseURI.getURI()})
});
A.addButton("preview",{title:"preview.preview_desc",cmd:"mcePreview"})
},getInfo:function(){return{longname:"Preview",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/preview",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("preview",tinymce.plugins.Preview)
})();