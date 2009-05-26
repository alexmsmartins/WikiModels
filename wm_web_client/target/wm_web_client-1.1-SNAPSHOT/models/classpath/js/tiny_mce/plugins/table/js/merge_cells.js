tinyMCEPopup.requireLangPack();
function init(){var B=document.forms[0],A;
tinyMCEPopup.resizeToInnerSize();
B.numcols.value=tinyMCEPopup.getWindowArg("numcols",1);
B.numrows.value=tinyMCEPopup.getWindowArg("numrows",1)
}function mergeCells(){var A=[],B=document.forms[0];
tinyMCEPopup.restoreSelection();
if(!AutoValidator.validate(B)){tinyMCEPopup.alert(tinyMCEPopup.getLang("invalid_data"));
return false
}A.numcols=B.numcols.value;
A.numrows=B.numrows.value;
tinyMCEPopup.execCommand("mceTableMergeCells",false,A);
tinyMCEPopup.close()
}tinyMCEPopup.onInit.add(init);