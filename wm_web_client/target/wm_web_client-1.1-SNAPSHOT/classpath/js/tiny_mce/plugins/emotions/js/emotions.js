tinyMCEPopup.requireLangPack();
var EmotionsDialog={init:function(A){tinyMCEPopup.resizeToInnerSize()
},insert:function(B,D){var A=tinyMCEPopup.editor,C=A.dom;
tinyMCEPopup.execCommand("mceInsertContent",false,C.createHTML("img",{src:tinyMCEPopup.getWindowArg("plugin_url")+"/img/"+B,alt:A.getLang(D),title:A.getLang(D),border:0}));
tinyMCEPopup.close()
}};
tinyMCEPopup.onInit.add(EmotionsDialog.init,EmotionsDialog);