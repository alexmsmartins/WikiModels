(function(){tinymce.create("tinymce.plugins.AdvancedHRPlugin",{init:function(A,B){A.addCommand("mceAdvancedHr",function(){A.windowManager.open({file:B+"/rule.htm",width:250+parseInt(A.getLang("advhr.delta_width",0)),height:160+parseInt(A.getLang("advhr.delta_height",0)),inline:1},{plugin_url:B})
});
A.addButton("advhr",{title:"advhr.advhr_desc",cmd:"mceAdvancedHr"});
A.onNodeChange.add(function(D,C,E){C.setActive("advhr",E.nodeName=="HR")
});
A.onClick.add(function(C,D){D=D.target;
if(D.nodeName==="HR"){C.selection.select(D)
}})
},getInfo:function(){return{longname:"Advanced HR",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/advhr",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("advhr",tinymce.plugins.AdvancedHRPlugin)
})();