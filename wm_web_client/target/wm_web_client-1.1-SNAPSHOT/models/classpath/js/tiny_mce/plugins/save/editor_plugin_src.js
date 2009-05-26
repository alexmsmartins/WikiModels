(function(){tinymce.create("tinymce.plugins.Save",{init:function(A,B){var C=this;
C.editor=A;
A.addCommand("mceSave",C._save,C);
A.addCommand("mceCancel",C._cancel,C);
A.addButton("save",{title:"save.save_desc",cmd:"mceSave"});
A.addButton("cancel",{title:"save.cancel_desc",cmd:"mceCancel"});
A.onNodeChange.add(C._nodeChange,C);
A.addShortcut("ctrl+s",A.getLang("save.save_desc"),"mceSave")
},getInfo:function(){return{longname:"Save",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/save",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_nodeChange:function(B,A,C){var B=this.editor;
if(B.getParam("save_enablewhendirty")){A.setDisabled("save",!B.isDirty());
A.setDisabled("cancel",!B.isDirty())
}},_save:function(){var C=this.editor,A,E,D,B;
A=tinymce.DOM.get(C.id).form||tinymce.DOM.getParent(C.id,"form");
if(C.getParam("save_enablewhendirty")&&!C.isDirty()){return 
}tinyMCE.triggerSave();
if(E=C.getParam("save_onsavecallback")){if(C.execCallback("save_onsavecallback",C)){C.startContent=tinymce.trim(C.getContent({format:"raw"}));
C.nodeChanged()
}return 
}if(A){C.isNotDirty=true;
if(A.onsubmit==null||A.onsubmit()!=false){A.submit()
}C.nodeChanged()
}else{C.windowManager.alert("Error: No form element found.")
}},_cancel:function(){var A=this.editor,C,B=tinymce.trim(A.startContent);
if(C=A.getParam("save_oncancelcallback")){A.execCallback("save_oncancelcallback",A);
return 
}A.setContent(B);
A.undoManager.clear();
A.nodeChanged()
}});
tinymce.PluginManager.add("save",tinymce.plugins.Save)
})();