(function(){tinymce.create("tinymce.plugins.AdvancedImagePlugin",{init:function(B,A){B.addCommand("mceAdvImage",function(){if(B.dom.getAttrib(B.selection.getNode(),"class").indexOf("mceItem")!=-1){return 
}B.windowManager.open({file:A+"/image.htm",width:480+parseInt(B.getLang("advimage.delta_width",0)),height:385+parseInt(B.getLang("advimage.delta_height",0)),inline:1},{plugin_url:A})
});
B.addButton("image",{title:"advimage.image_desc",cmd:"mceAdvImage"})
},getInfo:function(){return{longname:"Advanced image",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/advimage",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("advimage",tinymce.plugins.AdvancedImagePlugin)
})();