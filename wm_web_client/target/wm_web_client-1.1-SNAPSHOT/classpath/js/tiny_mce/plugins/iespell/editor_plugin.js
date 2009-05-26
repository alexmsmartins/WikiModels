(function(){tinymce.create("tinymce.plugins.IESpell",{init:function(B,A){var D=this,C;
if(!tinymce.isIE){return 
}D.editor=B;
B.addCommand("mceIESpell",function(){try{C=new ActiveXObject("ieSpell.ieSpellExtension");
C.CheckDocumentNode(B.getDoc().documentElement)
}catch(E){if(E.number==-2146827859){B.windowManager.confirm(B.getLang("iespell.download"),function(F){if(F){window.open("http://www.iespell.com/download.php","ieSpellDownload","")
}})
}else{B.windowManager.alert("Error Loading ieSpell: Exception "+E.number)
}}});
B.addButton("iespell",{title:"iespell.iespell_desc",cmd:"mceIESpell"})
},getInfo:function(){return{longname:"IESpell (IE Only)",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/iespell",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("iespell",tinymce.plugins.IESpell)
})();