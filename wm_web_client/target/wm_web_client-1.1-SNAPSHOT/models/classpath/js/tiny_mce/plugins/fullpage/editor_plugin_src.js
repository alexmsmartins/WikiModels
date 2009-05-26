(function(){tinymce.create("tinymce.plugins.FullPagePlugin",{init:function(A,B){var C=this;
C.editor=A;
A.addCommand("mceFullPageProperties",function(){A.windowManager.open({file:B+"/fullpage.htm",width:430+parseInt(A.getLang("fullpage.delta_width",0)),height:495+parseInt(A.getLang("fullpage.delta_height",0)),inline:1},{plugin_url:B,head_html:C.head})
});
A.addButton("fullpage",{title:"fullpage.desc",cmd:"mceFullPageProperties"});
A.onBeforeSetContent.add(C._setContent,C);
A.onSetContent.add(C._setBodyAttribs,C);
A.onGetContent.add(C._getContent,C)
},getInfo:function(){return{longname:"Fullpage",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/fullpage",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_setBodyAttribs:function(D,A){var J,C,E,G,B,H,I,F=this.head.match(/body(.*?)>/i);
if(F&&F[1]){J=F[1].match(/\s*(\w+\s*=\s*".*?"|\w+\s*=\s*'.*?'|\w+\s*=\s*\w+|\w+)\s*/g);
if(J){for(C=0,E=J.length;
C<E;
C++){G=J[C].split("=");
B=G[0].replace(/\s/,"");
H=G[1];
if(H){H=H.replace(/^\s+/,"").replace(/\s+$/,"");
I=H.match(/^["'](.*)["']$/);
if(I){H=I[1]
}}else{H=B
}D.dom.setAttrib(D.getBody(),"style",H)
}}}},_createSerializer:function(){return new tinymce.dom.Serializer({dom:this.editor.dom,apply_source_formatting:true})
},_setContent:function(C,B){var G=this,A,I,E=B.content,F,H="";
if(B.source_view&&C.getParam("fullpage_hide_in_source_view")){return 
}E=E.replace(/<(\/?)BODY/gi,"<$1body");
A=E.indexOf("<body");
if(A!=-1){A=E.indexOf(">",A);
G.head=E.substring(0,A+1);
I=E.indexOf("</body",A);
if(I==-1){I=E.indexOf("</body",I)
}B.content=E.substring(A+1,I);
G.foot=E.substring(I);
function D(J){return J.replace(/<\/?[A-Z]+/g,function(K){return K.toLowerCase()
})
}G.head=D(G.head);
G.foot=D(G.foot)
}else{G.head="";
if(C.getParam("fullpage_default_xml_pi")){G.head+='<?xml version="1.0" encoding="'+C.getParam("fullpage_default_encoding","ISO-8859-1")+'" ?>\n'
}G.head+=C.getParam("fullpage_default_doctype",'<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">');
G.head+="\n<html>\n<head>\n<title>"+C.getParam("fullpage_default_title","Untitled document")+"</title>\n";
if(F=C.getParam("fullpage_default_encoding")){G.head+='<meta http-equiv="Content-Type" content="'+F+'" />\n'
}if(F=C.getParam("fullpage_default_font_family")){H+="font-family: "+F+";"
}if(F=C.getParam("fullpage_default_font_size")){H+="font-size: "+F+";"
}if(F=C.getParam("fullpage_default_text_color")){H+="color: "+F+";"
}G.head+="</head>\n<body"+(H?' style="'+H+'"':"")+">\n";
G.foot="\n</body>\n</html>"
}},_getContent:function(A,C){var B=this;
if(!C.source_view||!A.getParam("fullpage_hide_in_source_view")){C.content=tinymce.trim(B.head)+"\n"+tinymce.trim(C.content)+"\n"+tinymce.trim(B.foot)
}}});
tinymce.PluginManager.add("fullpage",tinymce.plugins.FullPagePlugin)
})();