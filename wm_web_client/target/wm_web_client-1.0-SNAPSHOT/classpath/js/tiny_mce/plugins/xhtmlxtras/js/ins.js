function init(){SXE.initElementDialog("ins");
if(SXE.currentAction=="update"){setFormValue("datetime",tinyMCEPopup.editor.dom.getAttrib(SXE.updateElement,"datetime"));
setFormValue("cite",tinyMCEPopup.editor.dom.getAttrib(SXE.updateElement,"cite"));
SXE.showRemoveButton()
}}function setElementAttribs(A){setAllCommonAttribs(A);
setAttrib(A,"datetime");
setAttrib(A,"cite")
}function insertIns(){var D=tinyMCEPopup.editor.dom.getParent(SXE.focusElement,"INS");
tinyMCEPopup.execCommand("mceBeginUndoLevel");
if(D==null){var C=SXE.inst.selection.getContent();
if(C.length>0){insertInlineElement("INS");
var A=tinymce.grep(SXE.inst.dom.select("ins"),function(E){return E.id=="#sxe_temp_ins#"
});
for(var B=0;
B<A.length;
B++){var D=A[B];
setElementAttribs(D)
}}}else{setElementAttribs(D)
}tinyMCEPopup.editor.nodeChanged();
tinyMCEPopup.execCommand("mceEndUndoLevel");
tinyMCEPopup.close()
}function removeIns(){SXE.removeElement("ins");
tinyMCEPopup.close()
}function insertInlineElement(B){var A=tinyMCEPopup.editor,C=A.dom;
A.getDoc().execCommand("FontName",false,"mceinline");
tinymce.each(C.select(tinymce.isWebKit?"span":"font"),function(D){if(D.style.fontFamily=="mceinline"||D.face=="mceinline"){C.replace(C.create(B),D,1)
}})
}tinyMCEPopup.onInit.add(init);