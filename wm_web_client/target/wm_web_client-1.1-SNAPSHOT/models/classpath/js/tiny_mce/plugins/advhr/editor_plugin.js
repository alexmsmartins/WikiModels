(function(){tinymce.create("tinymce.plugins.AdvancedHRPlugin",{init:function(B,A){B.addCommand("mceAdvancedHr",function(){B.windowManager.open({file:A+"/rule.htm",width:250+parseInt(B.getLang("advhr.delta_width",0)),height:160+parseInt(B.getLang("advhr.delta_height",0)),inline:1},{plugin_url:A})
});
B.addButton("advhr",{title:"advhr.advhr_desc",cmd:"mceAdvancedHr"});
B.onNodeChange.add(function(D,E,C){E.setActive("advhr",C.nodeName=="HR")
});
B.onClick.add(function(D,C){C=C.target;
if(C.nodeName==="HR"){D.selection.select(C)
}})
},getInfo:function(){return{longname:"Advanced HR",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/advhr",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("advhr",tinymce.plugins.AdvancedHRPlugin)
})();