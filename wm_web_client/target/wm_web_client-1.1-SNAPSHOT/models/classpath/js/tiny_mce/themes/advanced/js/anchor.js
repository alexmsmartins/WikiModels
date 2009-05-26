tinyMCEPopup.requireLangPack();
var AnchorDialog={init:function(A){var C,D,B=document.forms[0];
this.editor=A;
D=A.dom.getParent(A.selection.getNode(),"A,IMG");
v=A.dom.getAttrib(D,"name");
if(v){this.action="update";
B.anchorName.value=v
}B.insert.value=A.getLang(D?"update":"insert")
},update:function(){var A=this.editor;
tinyMCEPopup.restoreSelection();
if(this.action!="update"){A.selection.collapse(1)
}if(tinymce.isWebKit){A.execCommand("mceInsertContent",0,A.dom.createHTML("img",{mce_name:"a",name:document.forms[0].anchorName.value,"class":"mceItemAnchor"}))
}else{A.execCommand("mceInsertContent",0,A.dom.createHTML("a",{name:document.forms[0].anchorName.value,"class":"mceItemAnchor"},""))
}tinyMCEPopup.close()
}};
tinyMCEPopup.onInit.add(AnchorDialog.init,AnchorDialog);