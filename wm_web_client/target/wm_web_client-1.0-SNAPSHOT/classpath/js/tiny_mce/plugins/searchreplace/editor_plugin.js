(function(){tinymce.create("tinymce.plugins.SearchReplacePlugin",{init:function(B,C){function A(D){B.windowManager.open({file:C+"/searchreplace.htm",width:420+parseInt(B.getLang("searchreplace.delta_width",0)),height:160+parseInt(B.getLang("searchreplace.delta_height",0)),inline:1,auto_focus:0},{mode:D,search_string:B.selection.getContent({format:"text"}),plugin_url:C})
}B.addCommand("mceSearch",function(){A("search")
});
B.addCommand("mceReplace",function(){A("replace")
});
B.addButton("search",{title:"searchreplace.search_desc",cmd:"mceSearch"});
B.addButton("replace",{title:"searchreplace.replace_desc",cmd:"mceReplace"});
B.addShortcut("ctrl+f","searchreplace.search_desc","mceSearch")
},getInfo:function(){return{longname:"Search/Replace",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/searchreplace",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("searchreplace",tinymce.plugins.SearchReplacePlugin)
})();