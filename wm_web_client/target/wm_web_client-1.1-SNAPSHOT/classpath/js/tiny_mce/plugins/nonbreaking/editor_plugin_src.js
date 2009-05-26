(function(){tinymce.create("tinymce.plugins.Nonbreaking",{init:function(A,B){var C=this;
C.editor=A;
A.addCommand("mceNonBreaking",function(){A.execCommand("mceInsertContent",false,(A.plugins.visualchars&&A.plugins.visualchars.state)?'<span class="mceItemHidden mceVisualNbsp">&middot;</span>':"&nbsp;")
});
A.addButton("nonbreaking",{title:"nonbreaking.nonbreaking_desc",cmd:"mceNonBreaking"});
if(A.getParam("nonbreaking_force_tab")){A.onKeyDown.add(function(D,E){if(tinymce.isIE&&E.keyCode==9){D.execCommand("mceNonBreaking");
D.execCommand("mceNonBreaking");
D.execCommand("mceNonBreaking");
tinymce.dom.Event.cancel(E)
}})
}},getInfo:function(){return{longname:"Nonbreaking space",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/nonbreaking",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("nonbreaking",tinymce.plugins.Nonbreaking)
})();