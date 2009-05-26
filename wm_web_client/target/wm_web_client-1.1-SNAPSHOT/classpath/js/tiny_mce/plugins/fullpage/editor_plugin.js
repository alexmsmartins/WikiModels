(function(){tinymce.create("tinymce.plugins.FullPagePlugin",{init:function(B,A){var C=this;
C.editor=B;
B.addCommand("mceFullPageProperties",function(){B.windowManager.open({file:A+"/fullpage.htm",width:430+parseInt(B.getLang("fullpage.delta_width",0)),height:495+parseInt(B.getLang("fullpage.delta_height",0)),inline:1},{plugin_url:A,head_html:C.head})
});
B.addButton("fullpage",{title:"fullpage.desc",cmd:"mceFullPageProperties"});
B.onBeforeSetContent.add(C._setContent,C);
B.onSetContent.add(C._setBodyAttribs,C);
B.onGetContent.add(C._getContent,C)
},getInfo:function(){return{longname:"Fullpage",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/fullpage",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_setBodyAttribs:function(G,J){var A,H,F,D,I,C,B,E=this.head.match(/body(.*?)>/i);
if(E&&E[1]){A=E[1].match(/\s*(\w+\s*=\s*".*?"|\w+\s*=\s*'.*?'|\w+\s*=\s*\w+|\w+)\s*/g);
if(A){for(H=0,F=A.length;
H<F;
H++){D=A[H].split("=");
I=D[0].replace(/\s/,"");
C=D[1];
if(C){C=C.replace(/^\s+/,"").replace(/\s+$/,"");
B=C.match(/^["'](.*)["']$/);
if(B){C=B[1]
}}else{C=I
}G.dom.setAttrib(G.getBody(),"style",C)
}}}},_createSerializer:function(){return new tinymce.dom.Serializer({dom:this.editor.dom,apply_source_formatting:true})
},_setContent:function(G,H){var C=this,I,A,E=H.content,D,B="";
if(H.source_view&&G.getParam("fullpage_hide_in_source_view")){return 
}E=E.replace(/<(\/?)BODY/gi,"<$1body");
I=E.indexOf("<body");
if(I!=-1){I=E.indexOf(">",I);
C.head=E.substring(0,I+1);
A=E.indexOf("</body",I);
if(A==-1){A=E.indexOf("</body",A)
}H.content=E.substring(I+1,A);
C.foot=E.substring(A);
function F(J){return J.replace(/<\/?[A-Z]+/g,function(K){return K.toLowerCase()
})
}C.head=F(C.head);
C.foot=F(C.foot)
}else{C.head="";
if(G.getParam("fullpage_default_xml_pi")){C.head+='<?xml version="1.0" encoding="'+G.getParam("fullpage_default_encoding","ISO-8859-1")+'" ?>\n'
}C.head+=G.getParam("fullpage_default_doctype",'<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">');
C.head+="\n<html>\n<head>\n<title>"+G.getParam("fullpage_default_title","Untitled document")+"</title>\n";
if(D=G.getParam("fullpage_default_encoding")){C.head+='<meta http-equiv="Content-Type" content="'+D+'" />\n'
}if(D=G.getParam("fullpage_default_font_family")){B+="font-family: "+D+";"
}if(D=G.getParam("fullpage_default_font_size")){B+="font-size: "+D+";"
}if(D=G.getParam("fullpage_default_text_color")){B+="color: "+D+";"
}C.head+="</head>\n<body"+(B?' style="'+B+'"':"")+">\n";
C.foot="\n</body>\n</html>"
}},_getContent:function(B,C){var A=this;
if(!C.source_view||!B.getParam("fullpage_hide_in_source_view")){C.content=tinymce.trim(A.head)+"\n"+tinymce.trim(C.content)+"\n"+tinymce.trim(A.foot)
}}});
tinymce.PluginManager.add("fullpage",tinymce.plugins.FullPagePlugin)
})();