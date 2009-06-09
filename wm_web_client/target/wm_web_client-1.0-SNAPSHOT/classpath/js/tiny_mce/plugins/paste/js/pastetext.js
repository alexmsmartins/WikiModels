tinyMCEPopup.requireLangPack();
var PasteTextDialog={init:function(){this.resize()
},insert:function(){var B=tinyMCEPopup.dom.encode(document.getElementById("content").value),A;
if(document.getElementById("linebreaks").checked){A=B.split(/\r?\n/);
if(A.length>1){B="";
tinymce.each(A,function(C){B+="<p>"+C+"</p>"
})
}}tinyMCEPopup.editor.execCommand("mceInsertClipboardContent",false,B);
tinyMCEPopup.close()
},resize:function(){var A=tinyMCEPopup.dom.getViewPort(window),B;
B=document.getElementById("content");
B.style.width=(A.w-20)+"px";
B.style.height=(A.h-90)+"px"
}};
tinyMCEPopup.onInit.add(PasteTextDialog.init,PasteTextDialog);