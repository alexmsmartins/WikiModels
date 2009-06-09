tinyMCEPopup.requireLangPack();
var LinkDialog={preInit:function(){var A;
if(A=tinyMCEPopup.getParam("external_link_list_url")){document.write('<script language="javascript" type="text/javascript" src="'+tinyMCEPopup.editor.documentBaseURI.toAbsolute(A)+'"><\/script>')
}},init:function(){var B=document.forms[0],A=tinyMCEPopup.editor;
document.getElementById("hrefbrowsercontainer").innerHTML=getBrowserHTML("hrefbrowser","href","file","theme_advanced_link");
if(isVisible("hrefbrowser")){document.getElementById("href").style.width="180px"
}this.fillClassList("class_list");
this.fillFileList("link_list","tinyMCELinkList");
this.fillTargetList("target_list");
if(e=A.dom.getParent(A.selection.getNode(),"A")){B.href.value=A.dom.getAttrib(e,"href");
B.linktitle.value=A.dom.getAttrib(e,"title");
B.insert.value=A.getLang("update");
selectByValue(B,"link_list",B.href.value);
selectByValue(B,"target_list",A.dom.getAttrib(e,"target"));
selectByValue(B,"class_list",A.dom.getAttrib(e,"class"))
}},update:function(){var C=document.forms[0],B=tinyMCEPopup.editor,D,A;
tinyMCEPopup.restoreSelection();
D=B.dom.getParent(B.selection.getNode(),"A");
if(!C.href.value){if(D){tinyMCEPopup.execCommand("mceBeginUndoLevel");
A=B.selection.getBookmark();
B.dom.remove(D,1);
B.selection.moveToBookmark(A);
tinyMCEPopup.execCommand("mceEndUndoLevel");
tinyMCEPopup.close();
return 
}}tinyMCEPopup.execCommand("mceBeginUndoLevel");
if(D==null){B.getDoc().execCommand("unlink",false,null);
tinyMCEPopup.execCommand("CreateLink",false,"#mce_temp_url#",{skip_undo:1});
tinymce.each(B.dom.select("a"),function(E){if(B.dom.getAttrib(E,"href")=="#mce_temp_url#"){D=E;
B.dom.setAttribs(D,{href:C.href.value,title:C.linktitle.value,target:C.target_list?C.target_list.options[C.target_list.selectedIndex].value:null,"class":C.class_list?C.class_list.options[C.class_list.selectedIndex].value:null})
}})
}else{B.dom.setAttribs(D,{href:C.href.value,title:C.linktitle.value,target:C.target_list?C.target_list.options[C.target_list.selectedIndex].value:null,"class":C.class_list?C.class_list.options[C.class_list.selectedIndex].value:null})
}if(D.childNodes.length!=1||D.firstChild.nodeName!="IMG"){B.focus();
B.selection.select(D);
B.selection.collapse(0);
tinyMCEPopup.storeSelection()
}tinyMCEPopup.execCommand("mceEndUndoLevel");
tinyMCEPopup.close()
},checkPrefix:function(A){if(A.value&&Validator.isEmail(A)&&!/^\s*mailto:/i.test(A.value)&&confirm(tinyMCEPopup.getLang("advanced_dlg.link_is_email"))){A.value="mailto:"+A.value
}if(/^\s*www\./i.test(A.value)&&confirm(tinyMCEPopup.getLang("advanced_dlg.link_is_external"))){A.value="http://"+A.value
}},fillFileList:function(F,C){var E=tinyMCEPopup.dom,A=E.get(F),D,B;
C=window[C];
if(C&&C.length>0){A.options[A.options.length]=new Option("","");
tinymce.each(C,function(G){A.options[A.options.length]=new Option(G[0],G[1])
})
}else{E.remove(E.getParent(F,"tr"))
}},fillClassList:function(E){var D=tinyMCEPopup.dom,A=D.get(E),C,B;
if(C=tinyMCEPopup.getParam("theme_advanced_styles")){B=[];
tinymce.each(C.split(";"),function(F){var G=F.split("=");
B.push({title:G[0],"class":G[1]})
})
}else{B=tinyMCEPopup.editor.dom.getClasses()
}if(B.length>0){A.options[A.options.length]=new Option(tinyMCEPopup.getLang("not_set"),"");
tinymce.each(B,function(F){A.options[A.options.length]=new Option(F.title||F["class"],F["class"])
})
}else{D.remove(D.getParent(E,"tr"))
}},fillTargetList:function(D){var C=tinyMCEPopup.dom,A=C.get(D),B;
A.options[A.options.length]=new Option(tinyMCEPopup.getLang("not_set"),"");
A.options[A.options.length]=new Option(tinyMCEPopup.getLang("advanced_dlg.link_target_same"),"_self");
A.options[A.options.length]=new Option(tinyMCEPopup.getLang("advanced_dlg.link_target_blank"),"_blank");
if(B=tinyMCEPopup.getParam("theme_advanced_link_targets")){tinymce.each(B.split(","),function(E){E=E.split("=");
A.options[A.options.length]=new Option(E[0],E[1])
})
}}};
LinkDialog.preInit();
tinyMCEPopup.onInit.add(LinkDialog.init,LinkDialog);