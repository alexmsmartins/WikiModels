(function(){var A=tinymce.dom.Event;
tinymce.create("tinymce.plugins.NonEditablePlugin",{init:function(D,E){var F=this,C,B;
F.editor=D;
C=D.getParam("noneditable_editable_class","mceEditable");
B=D.getParam("noneditable_noneditable_class","mceNonEditable");
D.onNodeChange.addToTop(function(H,G,K){var J,I;
J=H.dom.getParent(H.selection.getStart(),function(L){return H.dom.hasClass(L,B)
});
I=H.dom.getParent(H.selection.getEnd(),function(L){return H.dom.hasClass(L,B)
});
if(J||I){F._setDisabled(1);
return false
}else{F._setDisabled(0)
}})
},getInfo:function(){return{longname:"Non editable elements",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/noneditable",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_block:function(C,D){var B=D.keyCode;
if((B>32&&B<41)||(B>111&&B<124)){return 
}return A.cancel(D)
},_setDisabled:function(D){var C=this,B=C.editor;
tinymce.each(B.controlManager.controls,function(E){E.setDisabled(D)
});
if(D!==C.disabled){if(D){B.onKeyDown.addToTop(C._block);
B.onKeyPress.addToTop(C._block);
B.onKeyUp.addToTop(C._block);
B.onPaste.addToTop(C._block)
}else{B.onKeyDown.remove(C._block);
B.onKeyPress.remove(C._block);
B.onKeyUp.remove(C._block);
B.onPaste.remove(C._block)
}C.disabled=D
}}});
tinymce.PluginManager.add("noneditable",tinymce.plugins.NonEditablePlugin)
})();