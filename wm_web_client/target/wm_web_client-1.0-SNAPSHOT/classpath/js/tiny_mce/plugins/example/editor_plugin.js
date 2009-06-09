(function(){tinymce.PluginManager.requireLangPack("example");
tinymce.create("tinymce.plugins.ExamplePlugin",{init:function(B,A){B.addCommand("mceExample",function(){B.windowManager.open({file:A+"/dialog.htm",width:320+parseInt(B.getLang("example.delta_width",0)),height:120+parseInt(B.getLang("example.delta_height",0)),inline:1},{plugin_url:A,some_custom_arg:"custom arg"})
});
B.addButton("example",{title:"example.desc",cmd:"mceExample",image:A+"/img/example.gif"});
B.onNodeChange.add(function(D,E,C){E.setActive("example",C.nodeName=="IMG")
})
},createControl:function(A,B){return null
},getInfo:function(){return{longname:"Example plugin",author:"Some author",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/example",version:"1.0"}
}});
tinymce.PluginManager.add("example",tinymce.plugins.ExamplePlugin)
})();