(function(){tinymce.create("tinymce.plugins.EmotionsPlugin",{init:function(B,A){B.addCommand("mceEmotion",function(){B.windowManager.open({file:A+"/emotions.htm",width:250+parseInt(B.getLang("emotions.delta_width",0)),height:160+parseInt(B.getLang("emotions.delta_height",0)),inline:1},{plugin_url:A})
});
B.addButton("emotions",{title:"emotions.emotions_desc",cmd:"mceEmotion"})
},getInfo:function(){return{longname:"Emotions",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/emotions",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("emotions",tinymce.plugins.EmotionsPlugin)
})();