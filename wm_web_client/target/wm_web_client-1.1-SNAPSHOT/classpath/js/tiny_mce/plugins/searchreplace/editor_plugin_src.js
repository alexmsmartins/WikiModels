(function(){tinymce.create("tinymce.plugins.SearchReplacePlugin",{init:function(A,C){function B(D){A.windowManager.open({file:C+"/searchreplace.htm",width:420+parseInt(A.getLang("searchreplace.delta_width",0)),height:160+parseInt(A.getLang("searchreplace.delta_height",0)),inline:1,auto_focus:0},{mode:D,search_string:A.selection.getContent({format:"text"}),plugin_url:C})
}A.addCommand("mceSearch",function(){B("search")
});
A.addCommand("mceReplace",function(){B("replace")
});
A.addButton("search",{title:"searchreplace.search_desc",cmd:"mceSearch"});
A.addButton("replace",{title:"searchreplace.replace_desc",cmd:"mceReplace"});
A.addShortcut("ctrl+f","searchreplace.search_desc","mceSearch")
},getInfo:function(){return{longname:"Search/Replace",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/searchreplace",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("searchreplace",tinymce.plugins.SearchReplacePlugin)
})();