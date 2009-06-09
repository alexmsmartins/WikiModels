tinyMCEPopup.requireLangPack();
var TemplateDialog={preInit:function(){var A=tinyMCEPopup.getParam("template_external_list_url");
if(A!=null){document.write('<script language="javascript" type="text/javascript" src="'+tinyMCEPopup.editor.documentBaseURI.toAbsolute(A)+'"><\/script>')
}},init:function(){var C=tinyMCEPopup.editor,B,E,A,D;
B=C.getParam("template_templates",false);
E=document.getElementById("tpath");
if(!B&&typeof (tinyMCETemplateList)!="undefined"){for(A=0,B=[];
A<tinyMCETemplateList.length;
A++){B.push({title:tinyMCETemplateList[A][0],src:tinyMCETemplateList[A][1],description:tinyMCETemplateList[A][2]})
}}for(A=0;
A<B.length;
A++){E.options[E.options.length]=new Option(B[A].title,tinyMCEPopup.editor.documentBaseURI.toAbsolute(B[A].src))
}this.resize();
this.tsrc=B
},resize:function(){var A,B,C;
if(!self.innerWidth){A=document.body.clientWidth-50;
B=document.body.clientHeight-160
}else{A=self.innerWidth-50;
B=self.innerHeight-170
}C=document.getElementById("templatesrc");
if(C){C.style.height=Math.abs(B)+"px";
C.style.width=Math.abs(A-5)+"px"
}},loadCSSFiles:function(B){var A=tinyMCEPopup.editor;
tinymce.each(A.getParam("content_css","").split(","),function(C){B.write('<link href="'+A.documentBaseURI.toAbsolute(C)+'" rel="stylesheet" type="text/css" />')
})
},selectTemplate:function(C,D){var E=window.frames.templatesrc.document,A,B=this.tsrc;
if(!C){return 
}E.body.innerHTML=this.templateHTML=this.getFileContents(C);
for(A=0;
A<B.length;
A++){if(B[A].title==D){document.getElementById("tmpldesc").innerHTML=B[A].description||""
}}},insert:function(){tinyMCEPopup.execCommand("mceInsertTemplate",false,{content:this.templateHTML,selection:tinyMCEPopup.editor.selection.getContent()});
tinyMCEPopup.close()
},getFileContents:function(B){var A,E,C="text/plain";
function D(F){A=0;
try{A=new ActiveXObject(F)
}catch(F){}return A
}A=window.ActiveXObject?D("Msxml2.XMLHTTP")||D("Microsoft.XMLHTTP"):new XMLHttpRequest();
A.overrideMimeType&&A.overrideMimeType(C);
A.open("GET",B,false);
A.send(null);
return A.responseText
}};
TemplateDialog.preInit();
tinyMCEPopup.onInit.add(TemplateDialog.init,TemplateDialog);