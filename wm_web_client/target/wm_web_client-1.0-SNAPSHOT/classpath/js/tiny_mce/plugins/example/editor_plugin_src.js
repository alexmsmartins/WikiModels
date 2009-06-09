(function(){tinymce.PluginManager.requireLangPack("example");
tinymce.create("tinymce.plugins.ExamplePlugin",{init:function(A,B){A.addCommand("mceExample",function(){A.windowManager.open({file:B+"/dialog.htm",width:320+parseInt(A.getLang("example.delta_width",0)),height:120+parseInt(A.getLang("example.delta_height",0)),inline:1},{plugin_url:B,some_custom_arg:"custom arg"})
});
A.addButton("example",{title:"example.desc",cmd:"mceExample",image:B+"/img/example.gif"});
A.onNodeChange.add(function(D,C,E){C.setActive("example",E.nodeName=="IMG")
})
},createControl:function(B,A){return null
},getInfo:function(){return{longname:"Example plugin",author:"Some author",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/example",version:"1.0"}
}});
tinymce.PluginManager.add("example",tinymce.plugins.ExamplePlugin)
})();