(function(){var A=tinymce.dom.Event;
tinymce.create("tinymce.plugins.NonEditablePlugin",{init:function(E,D){var C=this,F,B;
C.editor=E;
F=E.getParam("noneditable_editable_class","mceEditable");
B=E.getParam("noneditable_noneditable_class","mceNonEditable");
E.onNodeChange.addToTop(function(J,K,G){var H,I;
H=J.dom.getParent(J.selection.getStart(),function(L){return J.dom.hasClass(L,B)
});
I=J.dom.getParent(J.selection.getEnd(),function(L){return J.dom.hasClass(L,B)
});
if(H||I){C._setDisabled(1);
return false
}else{C._setDisabled(0)
}})
},getInfo:function(){return{longname:"Non editable elements",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/noneditable",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_block:function(D,C){var B=C.keyCode;
if((B>32&&B<41)||(B>111&&B<124)){return 
}return A.cancel(C)
},_setDisabled:function(C){var D=this,B=D.editor;
tinymce.each(B.controlManager.controls,function(E){E.setDisabled(C)
});
if(C!==D.disabled){if(C){B.onKeyDown.addToTop(D._block);
B.onKeyPress.addToTop(D._block);
B.onKeyUp.addToTop(D._block);
B.onPaste.addToTop(D._block)
}else{B.onKeyDown.remove(D._block);
B.onKeyPress.remove(D._block);
B.onKeyUp.remove(D._block);
B.onPaste.remove(D._block)
}D.disabled=C
}}});
tinymce.PluginManager.add("noneditable",tinymce.plugins.NonEditablePlugin)
})();