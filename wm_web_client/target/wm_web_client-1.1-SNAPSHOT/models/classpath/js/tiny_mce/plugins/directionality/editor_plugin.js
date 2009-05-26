(function(){tinymce.create("tinymce.plugins.Directionality",{init:function(B,A){var C=this;
C.editor=B;
B.addCommand("mceDirectionLTR",function(){var D=B.dom.getParent(B.selection.getNode(),B.dom.isBlock);
if(D){if(B.dom.getAttrib(D,"dir")!="ltr"){B.dom.setAttrib(D,"dir","ltr")
}else{B.dom.setAttrib(D,"dir","")
}}B.nodeChanged()
});
B.addCommand("mceDirectionRTL",function(){var D=B.dom.getParent(B.selection.getNode(),B.dom.isBlock);
if(D){if(B.dom.getAttrib(D,"dir")!="rtl"){B.dom.setAttrib(D,"dir","rtl")
}else{B.dom.setAttrib(D,"dir","")
}}B.nodeChanged()
});
B.addButton("ltr",{title:"directionality.ltr_desc",cmd:"mceDirectionLTR"});
B.addButton("rtl",{title:"directionality.rtl_desc",cmd:"mceDirectionRTL"});
B.onNodeChange.add(C._nodeChange,C)
},getInfo:function(){return{longname:"Directionality",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/directionality",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_nodeChange:function(A,B,C){var D=A.dom,E;
C=D.getParent(C,D.isBlock);
if(!C){B.setDisabled("ltr",1);
B.setDisabled("rtl",1);
return 
}E=D.getAttrib(C,"dir");
B.setActive("ltr",E=="ltr");
B.setDisabled("ltr",0);
B.setActive("rtl",E=="rtl");
B.setDisabled("rtl",0)
}});
tinymce.PluginManager.add("directionality",tinymce.plugins.Directionality)
})();