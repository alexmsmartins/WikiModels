(function(){tinymce.create("tinymce.plugins.Directionality",{init:function(A,B){var C=this;
C.editor=A;
A.addCommand("mceDirectionLTR",function(){var D=A.dom.getParent(A.selection.getNode(),A.dom.isBlock);
if(D){if(A.dom.getAttrib(D,"dir")!="ltr"){A.dom.setAttrib(D,"dir","ltr")
}else{A.dom.setAttrib(D,"dir","")
}}A.nodeChanged()
});
A.addCommand("mceDirectionRTL",function(){var D=A.dom.getParent(A.selection.getNode(),A.dom.isBlock);
if(D){if(A.dom.getAttrib(D,"dir")!="rtl"){A.dom.setAttrib(D,"dir","rtl")
}else{A.dom.setAttrib(D,"dir","")
}}A.nodeChanged()
});
A.addButton("ltr",{title:"directionality.ltr_desc",cmd:"mceDirectionLTR"});
A.addButton("rtl",{title:"directionality.rtl_desc",cmd:"mceDirectionRTL"});
A.onNodeChange.add(C._nodeChange,C)
},getInfo:function(){return{longname:"Directionality",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/directionality",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_nodeChange:function(B,A,E){var D=B.dom,C;
E=D.getParent(E,D.isBlock);
if(!E){A.setDisabled("ltr",1);
A.setDisabled("rtl",1);
return 
}C=D.getAttrib(E,"dir");
A.setActive("ltr",C=="ltr");
A.setDisabled("ltr",0);
A.setActive("rtl",C=="rtl");
A.setDisabled("rtl",0)
}});
tinymce.PluginManager.add("directionality",tinymce.plugins.Directionality)
})();