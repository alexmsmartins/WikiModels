(function(){tinymce.create("tinymce.plugins.Preview",{init:function(B,A){var C=this,D=tinymce.explode(B.settings.content_css);
C.editor=B;
tinymce.each(D,function(E,F){D[F]=B.documentBaseURI.toAbsolute(E)
});
B.addCommand("mcePreview",function(){B.windowManager.open({file:B.getParam("plugin_preview_pageurl",A+"/preview.html"),width:parseInt(B.getParam("plugin_preview_width","550")),height:parseInt(B.getParam("plugin_preview_height","600")),resizable:"yes",scrollbars:"yes",popup_css:D?D.join(","):B.baseURI.toAbsolute("themes/"+B.settings.theme+"/skins/"+B.settings.skin+"/content.css"),inline:B.getParam("plugin_preview_inline",1)},{base:B.documentBaseURI.getURI()})
});
B.addButton("preview",{title:"preview.preview_desc",cmd:"mcePreview"})
},getInfo:function(){return{longname:"Preview",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/preview",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("preview",tinymce.plugins.Preview)
})();