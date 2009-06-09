tinyMCEPopup.requireLangPack();
tinyMCEPopup.onInit.add(onLoadInit);
function saveContent(){tinyMCEPopup.editor.setContent(document.getElementById("htmlSource").value,{source_view:true});
tinyMCEPopup.close()
}function onLoadInit(){tinyMCEPopup.resizeToInnerSize();
if(tinymce.isGecko){document.body.spellcheck=tinyMCEPopup.editor.getParam("gecko_spellcheck")
}document.getElementById("htmlSource").value=tinyMCEPopup.editor.getContent({source_view:true});
if(tinyMCEPopup.editor.getParam("theme_advanced_source_editor_wrap",true)){setWrap("soft");
document.getElementById("wraped").checked=true
}resizeInputs()
}function setWrap(C){var A,D,B=document.getElementById("htmlSource");
B.wrap=C;
if(!tinymce.isIE){A=B.value;
D=B.cloneNode(false);
D.setAttribute("wrap",C);
B.parentNode.replaceChild(D,B);
D.value=A
}}function toggleWordWrap(A){if(A.checked){setWrap("soft")
}else{setWrap("off")
}}var wHeight=0,wWidth=0,owHeight=0,owWidth=0;
function resizeInputs(){var A=document.getElementById("htmlSource");
if(!tinymce.isIE){wHeight=self.innerHeight-65;
wWidth=self.innerWidth-16
}else{wHeight=document.body.clientHeight-70;
wWidth=document.body.clientWidth-16
}A.style.height=Math.abs(wHeight)+"px";
A.style.width=Math.abs(wWidth)+"px"
};