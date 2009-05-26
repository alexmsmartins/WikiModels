var ImageDialog={preInit:function(){var A;
tinyMCEPopup.requireLangPack();
if(A=tinyMCEPopup.getParam("external_image_list_url")){document.write('<script language="javascript" type="text/javascript" src="'+tinyMCEPopup.editor.documentBaseURI.toAbsolute(A)+'"><\/script>')
}},init:function(){var B=document.forms[0],A=tinyMCEPopup.editor;
document.getElementById("srcbrowsercontainer").innerHTML=getBrowserHTML("srcbrowser","src","image","theme_advanced_image");
if(isVisible("srcbrowser")){document.getElementById("src").style.width="180px"
}e=A.selection.getNode();
this.fillFileList("image_list","tinyMCEImageList");
if(e.nodeName=="IMG"){B.src.value=A.dom.getAttrib(e,"src");
B.alt.value=A.dom.getAttrib(e,"alt");
B.border.value=this.getAttrib(e,"border");
B.vspace.value=this.getAttrib(e,"vspace");
B.hspace.value=this.getAttrib(e,"hspace");
B.width.value=A.dom.getAttrib(e,"width");
B.height.value=A.dom.getAttrib(e,"height");
B.insert.value=A.getLang("update");
this.styleVal=A.dom.getAttrib(e,"style");
selectByValue(B,"image_list",B.src.value);
selectByValue(B,"align",this.getAttrib(e,"align"));
this.updateStyle()
}},fillFileList:function(F,C){var E=tinyMCEPopup.dom,A=E.get(F),D,B;
C=window[C];
if(C&&C.length>0){A.options[A.options.length]=new Option("","");
tinymce.each(C,function(G){A.options[A.options.length]=new Option(G[0],G[1])
})
}else{E.remove(E.getParent(F,"tr"))
}},update:function(){var E=document.forms[0],A=E.elements,B=tinyMCEPopup.editor,C={},D;
tinyMCEPopup.restoreSelection();
if(E.src.value===""){if(B.selection.getNode().nodeName=="IMG"){B.dom.remove(B.selection.getNode());
B.execCommand("mceRepaint")
}tinyMCEPopup.close();
return 
}if(!B.settings.inline_styles){C=tinymce.extend(C,{vspace:A.vspace.value,hspace:A.hspace.value,border:A.border.value,align:getSelectValue(E,"align")})
}else{C.style=this.styleVal
}tinymce.extend(C,{src:E.src.value,alt:E.alt.value,width:E.width.value,height:E.height.value});
D=B.selection.getNode();
if(D&&D.nodeName=="IMG"){B.dom.setAttribs(D,C)
}else{B.execCommand("mceInsertContent",false,'<img id="__mce_tmp" />',{skip_undo:1});
B.dom.setAttribs("__mce_tmp",C);
B.dom.setAttrib("__mce_tmp","id","");
B.undoManager.add()
}tinyMCEPopup.close()
},updateStyle:function(){var D=tinyMCEPopup.dom,B,A,C=document.forms[0];
if(tinyMCEPopup.editor.settings.inline_styles){B=tinyMCEPopup.dom.parseStyle(this.styleVal);
A=getSelectValue(C,"align");
if(A){if(A=="left"||A=="right"){B["float"]=A;
delete B["vertical-align"]
}else{B["vertical-align"]=A;
delete B["float"]
}}else{delete B["float"];
delete B["vertical-align"]
}A=C.border.value;
if(A||A=="0"){if(A=="0"){B.border="0"
}else{B.border=A+"px solid black"
}}else{delete B.border
}A=C.hspace.value;
if(A){delete B.margin;
B["margin-left"]=A+"px";
B["margin-right"]=A+"px"
}else{delete B["margin-left"];
delete B["margin-right"]
}A=C.vspace.value;
if(A){delete B.margin;
B["margin-top"]=A+"px";
B["margin-bottom"]=A+"px"
}else{delete B["margin-top"];
delete B["margin-bottom"]
}B=tinyMCEPopup.dom.parseStyle(D.serializeStyle(B));
this.styleVal=D.serializeStyle(B)
}},getAttrib:function(D,A){var C=tinyMCEPopup.editor,F=C.dom,B,E;
if(C.settings.inline_styles){switch(A){case"align":if(B=F.getStyle(D,"float")){return B
}if(B=F.getStyle(D,"vertical-align")){return B
}break;
case"hspace":B=F.getStyle(D,"margin-left");
E=F.getStyle(D,"margin-right");
if(B&&B==E){return parseInt(B.replace(/[^0-9]/g,""))
}break;
case"vspace":B=F.getStyle(D,"margin-top");
E=F.getStyle(D,"margin-bottom");
if(B&&B==E){return parseInt(B.replace(/[^0-9]/g,""))
}break;
case"border":B=0;
tinymce.each(["top","right","bottom","left"],function(G){G=F.getStyle(D,"border-"+G+"-width");
if(!G||(G!=B&&B!==0)){B=0;
return false
}if(G){B=G
}});
if(B){return parseInt(B.replace(/[^0-9]/g,""))
}break
}}if(B=F.getAttrib(D,A)){return B
}return""
},resetImageData:function(){var A=document.forms[0];
A.width.value=A.height.value=""
},updateImageData:function(){var B=document.forms[0],A=ImageDialog;
if(B.width.value==""){B.width.value=A.preloadImg.width
}if(B.height.value==""){B.height.value=A.preloadImg.height
}},getImageData:function(){var A=document.forms[0];
this.preloadImg=new Image();
this.preloadImg.onload=this.updateImageData;
this.preloadImg.onerror=this.resetImageData;
this.preloadImg.src=tinyMCEPopup.editor.documentBaseURI.toAbsolute(A.src.value)
}};
ImageDialog.preInit();
tinyMCEPopup.onInit.add(ImageDialog.init,ImageDialog);