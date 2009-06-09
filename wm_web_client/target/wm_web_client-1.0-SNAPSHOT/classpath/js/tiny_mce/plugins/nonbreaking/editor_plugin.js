(function(){tinymce.create("tinymce.plugins.Nonbreaking",{init:function(B,A){var C=this;
C.editor=B;
B.addCommand("mceNonBreaking",function(){B.execCommand("mceInsertContent",false,(B.plugins.visualchars&&B.plugins.visualchars.state)?'<span class="mceItemHidden mceVisualNbsp">&middot;</span>':"&nbsp;")
});
B.addButton("nonbreaking",{title:"nonbreaking.nonbreaking_desc",cmd:"mceNonBreaking"});
if(B.getParam("nonbreaking_force_tab")){B.onKeyDown.add(function(E,D){if(tinymce.isIE&&D.keyCode==9){E.execCommand("mceNonBreaking");
E.execCommand("mceNonBreaking");
E.execCommand("mceNonBreaking");
tinymce.dom.Event.cancel(D)
}})
}},getInfo:function(){return{longname:"Nonbreaking space",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/nonbreaking",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("nonbreaking",tinymce.plugins.Nonbreaking)
})();