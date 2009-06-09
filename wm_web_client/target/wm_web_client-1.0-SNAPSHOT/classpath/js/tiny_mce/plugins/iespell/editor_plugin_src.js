(function(){tinymce.create("tinymce.plugins.IESpell",{init:function(A,B){var C=this,D;
if(!tinymce.isIE){return 
}C.editor=A;
A.addCommand("mceIESpell",function(){try{D=new ActiveXObject("ieSpell.ieSpellExtension");
D.CheckDocumentNode(A.getDoc().documentElement)
}catch(E){if(E.number==-2146827859){A.windowManager.confirm(A.getLang("iespell.download"),function(F){if(F){window.open("http://www.iespell.com/download.php","ieSpellDownload","")
}})
}else{A.windowManager.alert("Error Loading ieSpell: Exception "+E.number)
}}});
A.addButton("iespell",{title:"iespell.iespell_desc",cmd:"mceIESpell"})
},getInfo:function(){return{longname:"IESpell (IE Only)",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/iespell",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("iespell",tinymce.plugins.IESpell)
})();