(function(){tinymce.create("tinymce.plugins.AdvancedImagePlugin",{init:function(A,B){A.addCommand("mceAdvImage",function(){if(A.dom.getAttrib(A.selection.getNode(),"class").indexOf("mceItem")!=-1){return 
}A.windowManager.open({file:B+"/image.htm",width:480+parseInt(A.getLang("advimage.delta_width",0)),height:385+parseInt(A.getLang("advimage.delta_height",0)),inline:1},{plugin_url:B})
});
A.addButton("image",{title:"advimage.image_desc",cmd:"mceAdvImage"})
},getInfo:function(){return{longname:"Advanced image",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/advimage",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("advimage",tinymce.plugins.AdvancedImagePlugin)
})();