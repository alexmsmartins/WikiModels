(function(){tinymce.create("tinymce.plugins.AutoSavePlugin",{init:function(B,A){var C=this;
C.editor=B;
window.onbeforeunload=tinymce.plugins.AutoSavePlugin._beforeUnloadHandler
},getInfo:function(){return{longname:"Auto save",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/autosave",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},"static":{_beforeUnloadHandler:function(){var A;
tinymce.each(tinyMCE.editors,function(B){if(B.getParam("fullscreen_is_enabled")){return 
}if(B.isDirty()){A=B.getLang("autosave.unload_msg");
return false
}});
return A
}}});
tinymce.PluginManager.add("autosave",tinymce.plugins.AutoSavePlugin)
})();