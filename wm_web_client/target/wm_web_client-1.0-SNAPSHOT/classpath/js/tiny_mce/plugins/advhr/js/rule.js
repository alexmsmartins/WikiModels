var AdvHRDialog={init:function(B){var D=B.dom,C=document.forms[0],E=B.selection.getNode(),A;
A=D.getAttrib(E,"width");
C.width.value=A?parseInt(A):(D.getStyle("width")||"");
C.size.value=D.getAttrib(E,"size")||parseInt(D.getStyle("height"))||"";
C.noshade.checked=!!D.getAttrib(E,"noshade")||!!D.getStyle("border-width");
selectByValue(C,"width2",A.indexOf("%")!=-1?"%":"px")
},update:function(){var A=tinyMCEPopup.editor,C,D=document.forms[0],B="";
C="<hr";
if(D.size.value){C+=' size="'+D.size.value+'"';
B+=" height:"+D.size.value+"px;"
}if(D.width.value){C+=' width="'+D.width.value+(D.width2.value=="%"?"%":"")+'"';
B+=" width:"+D.width.value+(D.width2.value=="%"?"%":"px")+";"
}if(D.noshade.checked){C+=' noshade="noshade"';
B+=" border-width: 1px; border-style: solid; border-color: #CCCCCC; color: #ffffff;"
}if(A.settings.inline_styles){C+=' style="'+tinymce.trim(B)+'"'
}C+=" />";
A.execCommand("mceInsertContent",false,C);
tinyMCEPopup.close()
}};
tinyMCEPopup.requireLangPack();
tinyMCEPopup.onInit.add(AdvHRDialog.init,AdvHRDialog);