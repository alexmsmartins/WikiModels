var ImageDialog={preInit:function(){var A;
tinyMCEPopup.requireLangPack();
if(A=tinyMCEPopup.getParam("external_image_list_url")){document.write('<script language="javascript" type="text/javascript" src="'+tinyMCEPopup.editor.documentBaseURI.toAbsolute(A)+'"><\/script>')
}},init:function(B){var C=document.forms[0],A=C.elements,B=tinyMCEPopup.editor,D=B.dom,E=B.selection.getNode();
tinyMCEPopup.resizeToInnerSize();
this.fillClassList("class_list");
this.fillFileList("src_list","tinyMCEImageList");
this.fillFileList("over_list","tinyMCEImageList");
this.fillFileList("out_list","tinyMCEImageList");
TinyMCE_EditableSelects.init();
if(E.nodeName=="IMG"){A.src.value=D.getAttrib(E,"src");
A.width.value=D.getAttrib(E,"width");
A.height.value=D.getAttrib(E,"height");
A.alt.value=D.getAttrib(E,"alt");
A.title.value=D.getAttrib(E,"title");
A.vspace.value=this.getAttrib(E,"vspace");
A.hspace.value=this.getAttrib(E,"hspace");
A.border.value=this.getAttrib(E,"border");
selectByValue(C,"align",this.getAttrib(E,"align"));
selectByValue(C,"class_list",D.getAttrib(E,"class"),true,true);
A.style.value=D.getAttrib(E,"style");
A.id.value=D.getAttrib(E,"id");
A.dir.value=D.getAttrib(E,"dir");
A.lang.value=D.getAttrib(E,"lang");
A.usemap.value=D.getAttrib(E,"usemap");
A.longdesc.value=D.getAttrib(E,"longdesc");
A.insert.value=B.getLang("update");
if(/^\s*this.src\s*=\s*\'([^\']+)\';?\s*$/.test(D.getAttrib(E,"onmouseover"))){A.onmouseoversrc.value=D.getAttrib(E,"onmouseover").replace(/^\s*this.src\s*=\s*\'([^\']+)\';?\s*$/,"$1")
}if(/^\s*this.src\s*=\s*\'([^\']+)\';?\s*$/.test(D.getAttrib(E,"onmouseout"))){A.onmouseoutsrc.value=D.getAttrib(E,"onmouseout").replace(/^\s*this.src\s*=\s*\'([^\']+)\';?\s*$/,"$1")
}if(B.settings.inline_styles){if(D.getAttrib(E,"align")){this.updateStyle("align")
}if(D.getAttrib(E,"hspace")){this.updateStyle("hspace")
}if(D.getAttrib(E,"border")){this.updateStyle("border")
}if(D.getAttrib(E,"vspace")){this.updateStyle("vspace")
}}}document.getElementById("srcbrowsercontainer").innerHTML=getBrowserHTML("srcbrowser","src","image","theme_advanced_image");
if(isVisible("srcbrowser")){document.getElementById("src").style.width="260px"
}document.getElementById("onmouseoversrccontainer").innerHTML=getBrowserHTML("overbrowser","onmouseoversrc","image","theme_advanced_image");
if(isVisible("overbrowser")){document.getElementById("onmouseoversrc").style.width="260px"
}document.getElementById("onmouseoutsrccontainer").innerHTML=getBrowserHTML("outbrowser","onmouseoutsrc","image","theme_advanced_image");
if(isVisible("outbrowser")){document.getElementById("onmouseoutsrc").style.width="260px"
}if(B.getParam("advimage_constrain_proportions",true)){C.constrain.checked=true
}if(A.onmouseoversrc.value||A.onmouseoutsrc.value){this.setSwapImage(true)
}else{this.setSwapImage(false)
}this.changeAppearance();
this.showPreviewImage(A.src.value,1)
},insert:function(C,E){var A=tinyMCEPopup.editor,B=this,D=document.forms[0];
if(D.src.value===""){if(A.selection.getNode().nodeName=="IMG"){A.dom.remove(A.selection.getNode());
A.execCommand("mceRepaint")
}tinyMCEPopup.close();
return 
}if(tinyMCEPopup.getParam("accessibility_warnings",1)){if(!D.alt.value){tinyMCEPopup.confirm(tinyMCEPopup.getLang("advimage_dlg.missing_alt"),function(F){if(F){B.insertAndClose()
}});
return 
}}B.insertAndClose()
},insertAndClose:function(){var C=tinyMCEPopup.editor,F=document.forms[0],A=F.elements,B,D={},E;
tinyMCEPopup.restoreSelection();
if(tinymce.isWebKit){C.getWin().focus()
}if(!C.settings.inline_styles){D={vspace:A.vspace.value,hspace:A.hspace.value,border:A.border.value,align:getSelectValue(F,"align")}
}else{D={vspace:"",hspace:"",border:"",align:""}
}tinymce.extend(D,{src:A.src.value,width:A.width.value,height:A.height.value,alt:A.alt.value,title:A.title.value,"class":getSelectValue(F,"class_list"),style:A.style.value,id:A.id.value,dir:A.dir.value,lang:A.lang.value,usemap:A.usemap.value,longdesc:A.longdesc.value});
D.onmouseover=D.onmouseout="";
if(F.onmousemovecheck.checked){if(A.onmouseoversrc.value){D.onmouseover="this.src='"+A.onmouseoversrc.value+"';"
}if(A.onmouseoutsrc.value){D.onmouseout="this.src='"+A.onmouseoutsrc.value+"';"
}}E=C.selection.getNode();
if(E&&E.nodeName=="IMG"){C.dom.setAttribs(E,D)
}else{C.execCommand("mceInsertContent",false,'<img id="__mce_tmp" />',{skip_undo:1});
C.dom.setAttribs("__mce_tmp",D);
C.dom.setAttrib("__mce_tmp","id","");
C.undoManager.add()
}tinyMCEPopup.close()
},getAttrib:function(D,A){var C=tinyMCEPopup.editor,F=C.dom,B,E;
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
},setSwapImage:function(A){var B=document.forms[0];
B.onmousemovecheck.checked=A;
setBrowserDisabled("overbrowser",!A);
setBrowserDisabled("outbrowser",!A);
if(B.over_list){B.over_list.disabled=!A
}if(B.out_list){B.out_list.disabled=!A
}B.onmouseoversrc.disabled=!A;
B.onmouseoutsrc.disabled=!A
},fillClassList:function(E){var D=tinyMCEPopup.dom,A=D.get(E),C,B;
if(C=tinyMCEPopup.getParam("theme_advanced_styles")){B=[];
tinymce.each(C.split(";"),function(F){var G=F.split("=");
B.push({title:G[0],"class":G[1]})
})
}else{B=tinyMCEPopup.editor.dom.getClasses()
}if(B.length>0){A.options.length=0;
A.options[A.options.length]=new Option(tinyMCEPopup.getLang("not_set"),"");
tinymce.each(B,function(F){A.options[A.options.length]=new Option(F.title||F["class"],F["class"])
})
}else{D.remove(D.getParent(E,"tr"))
}},fillFileList:function(F,C){var E=tinyMCEPopup.dom,A=E.get(F),D,B;
C=window[C];
A.options.length=0;
if(C&&C.length>0){A.options[A.options.length]=new Option("","");
tinymce.each(C,function(G){A.options[A.options.length]=new Option(G[0],G[1])
})
}else{E.remove(E.getParent(F,"tr"))
}},resetImageData:function(){var A=document.forms[0];
A.elements.width.value=A.elements.height.value=""
},updateImageData:function(A,B){var C=document.forms[0];
if(!B){C.elements.width.value=A.width;
C.elements.height.value=A.height
}this.preloadImg=A
},changeAppearance:function(){var B=tinyMCEPopup.editor,C=document.forms[0],A=document.getElementById("alignSampleImg");
if(A){if(B.getParam("inline_styles")){B.dom.setAttrib(A,"style",C.style.value)
}else{A.align=C.align.value;
A.border=C.border.value;
A.hspace=C.hspace.value;
A.vspace=C.vspace.value
}}},changeHeight:function(){var B=document.forms[0],C,A=this;
if(!B.constrain.checked||!A.preloadImg){return 
}if(B.width.value==""||B.height.value==""){return 
}C=(parseInt(B.width.value)/parseInt(A.preloadImg.width))*A.preloadImg.height;
B.height.value=C.toFixed(0)
},changeWidth:function(){var B=document.forms[0],C,A=this;
if(!B.constrain.checked||!A.preloadImg){return 
}if(B.width.value==""||B.height.value==""){return 
}C=(parseInt(B.height.value)/parseInt(A.preloadImg.height))*A.preloadImg.width;
B.width.value=C.toFixed(0)
},updateStyle:function(A){var F=tinyMCEPopup.dom,D,C,E=document.forms[0],B=F.create("img",{style:F.get("style").value});
if(tinyMCEPopup.editor.settings.inline_styles){if(A=="align"){F.setStyle(B,"float","");
F.setStyle(B,"vertical-align","");
C=getSelectValue(E,"align");
if(C){if(C=="left"||C=="right"){F.setStyle(B,"float",C)
}else{B.style.verticalAlign=C
}}}if(A=="border"){F.setStyle(B,"border","");
C=E.border.value;
if(C||C=="0"){if(C=="0"){B.style.border="0"
}else{B.style.border=C+"px solid black"
}}}if(A=="hspace"){F.setStyle(B,"marginLeft","");
F.setStyle(B,"marginRight","");
C=E.hspace.value;
if(C){B.style.marginLeft=C+"px";
B.style.marginRight=C+"px"
}}if(A=="vspace"){F.setStyle(B,"marginTop","");
F.setStyle(B,"marginBottom","");
C=E.vspace.value;
if(C){B.style.marginTop=C+"px";
B.style.marginBottom=C+"px"
}}F.get("style").value=F.serializeStyle(F.parseStyle(B.style.cssText))
}},changeMouseMove:function(){},showPreviewImage:function(B,A){if(!B){tinyMCEPopup.dom.setHTML("prev","");
return 
}if(!A&&tinyMCEPopup.getParam("advimage_update_dimensions_onchange",true)){this.resetImageData()
}B=tinyMCEPopup.editor.documentBaseURI.toAbsolute(B);
if(!A){tinyMCEPopup.dom.setHTML("prev",'<img id="previewImg" src="'+B+'" border="0" onload="ImageDialog.updateImageData(this);" onerror="ImageDialog.resetImageData();" />')
}else{tinyMCEPopup.dom.setHTML("prev",'<img id="previewImg" src="'+B+'" border="0" onload="ImageDialog.updateImageData(this, 1);" />')
}}};
ImageDialog.preInit();
tinyMCEPopup.onInit.add(ImageDialog.init,ImageDialog);