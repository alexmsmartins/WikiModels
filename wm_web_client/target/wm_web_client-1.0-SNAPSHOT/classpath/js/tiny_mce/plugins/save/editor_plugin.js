(function(){tinymce.create("tinymce.plugins.Save",{init:function(B,A){var C=this;
C.editor=B;
B.addCommand("mceSave",C._save,C);
B.addCommand("mceCancel",C._cancel,C);
B.addButton("save",{title:"save.save_desc",cmd:"mceSave"});
B.addButton("cancel",{title:"save.cancel_desc",cmd:"mceCancel"});
B.onNodeChange.add(C._nodeChange,C);
B.addShortcut("ctrl+s",B.getLang("save.save_desc"),"mceSave")
},getInfo:function(){return{longname:"Save",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/save",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_nodeChange:function(A,B,C){var A=this.editor;
if(A.getParam("save_enablewhendirty")){B.setDisabled("save",!A.isDirty());
B.setDisabled("cancel",!A.isDirty())
}},_save:function(){var E=this.editor,B,C,D,A;
B=tinymce.DOM.get(E.id).form||tinymce.DOM.getParent(E.id,"form");
if(E.getParam("save_enablewhendirty")&&!E.isDirty()){return 
}tinyMCE.triggerSave();
if(C=E.getParam("save_onsavecallback")){if(E.execCallback("save_onsavecallback",E)){E.startContent=tinymce.trim(E.getContent({format:"raw"}));
E.nodeChanged()
}return 
}if(B){E.isNotDirty=true;
if(B.onsubmit==null||B.onsubmit()!=false){B.submit()
}E.nodeChanged()
}else{E.windowManager.alert("Error: No form element found.")
}},_cancel:function(){var B=this.editor,C,A=tinymce.trim(B.startContent);
if(C=B.getParam("save_oncancelcallback")){B.execCallback("save_oncancelcallback",B);
return 
}B.setContent(A);
B.undoManager.clear();
B.nodeChanged()
}});
tinymce.PluginManager.add("save",tinymce.plugins.Save)
})();