(function(){var A=tinymce.each;
tinymce.create("tinymce.plugins.TemplatePlugin",{init:function(B,C){var D=this;
D.editor=B;
B.addCommand("mceTemplate",function(E){B.windowManager.open({file:C+"/template.htm",width:B.getParam("template_popup_width",750),height:B.getParam("template_popup_height",600),inline:1},{plugin_url:C})
});
B.addCommand("mceInsertTemplate",D._insertTemplate,D);
B.addButton("template",{title:"template.desc",cmd:"mceTemplate"});
B.onPreProcess.add(function(E,G){var F=E.dom;
A(F.select("div",G.node),function(H){if(F.hasClass(H,"mceTmpl")){A(F.select("*",H),function(I){if(F.hasClass(I,E.getParam("template_mdate_classes","mdate").replace(/\s+/g,"|"))){I.innerHTML=D._getDateTime(new Date(),E.getParam("template_mdate_format",E.getLang("template.mdate_format")))
}});
D._replaceVals(H)
}})
})
},getInfo:function(){return{longname:"Template plugin",author:"Moxiecode Systems AB",authorurl:"http://www.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/template",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_insertTemplate:function(H,I){var J=this,G=J.editor,F,C,D=G.dom,B=G.selection.getContent();
F=I.content;
A(J.editor.getParam("template_replace_values"),function(L,K){if(typeof (L)!="function"){F=F.replace(new RegExp("\\{\\$"+K+"\\}","g"),L)
}});
C=D.create("div",null,F);
n=D.select(".mceTmpl",C);
if(n&&n.length>0){C=D.create("div",null);
C.appendChild(n[0].cloneNode(true))
}function E(L,K){return new RegExp("\\b"+K+"\\b","g").test(L.className)
}A(D.select("*",C),function(K){if(E(K,G.getParam("template_cdate_classes","cdate").replace(/\s+/g,"|"))){K.innerHTML=J._getDateTime(new Date(),G.getParam("template_cdate_format",G.getLang("template.cdate_format")))
}if(E(K,G.getParam("template_mdate_classes","mdate").replace(/\s+/g,"|"))){K.innerHTML=J._getDateTime(new Date(),G.getParam("template_mdate_format",G.getLang("template.mdate_format")))
}if(E(K,G.getParam("template_selected_content_classes","selcontent").replace(/\s+/g,"|"))){K.innerHTML=B
}});
J._replaceVals(C);
G.execCommand("mceInsertContent",false,C.innerHTML);
G.addVisual()
},_replaceVals:function(C){var D=this.editor.dom,B=this.editor.getParam("template_replace_values");
A(D.select("*",C),function(E){A(B,function(G,F){if(D.hasClass(E,F)){if(typeof (B[F])=="function"){B[F](E)
}}})
})
},_getDateTime:function(D,B){if(!B){return""
}function C(G,E){var F;
G=""+G;
if(G.length<E){for(F=0;
F<(E-G.length);
F++){G="0"+G
}}return G
}B=B.replace("%D","%m/%d/%y");
B=B.replace("%r","%I:%M:%S %p");
B=B.replace("%Y",""+D.getFullYear());
B=B.replace("%y",""+D.getYear());
B=B.replace("%m",C(D.getMonth()+1,2));
B=B.replace("%d",C(D.getDate(),2));
B=B.replace("%H",""+C(D.getHours(),2));
B=B.replace("%M",""+C(D.getMinutes(),2));
B=B.replace("%S",""+C(D.getSeconds(),2));
B=B.replace("%I",""+((D.getHours()+11)%12+1));
B=B.replace("%p",""+(D.getHours()<12?"AM":"PM"));
B=B.replace("%B",""+tinyMCE.getLang("template_months_long").split(",")[D.getMonth()]);
B=B.replace("%b",""+tinyMCE.getLang("template_months_short").split(",")[D.getMonth()]);
B=B.replace("%A",""+tinyMCE.getLang("template_day_long").split(",")[D.getDay()]);
B=B.replace("%a",""+tinyMCE.getLang("template_day_short").split(",")[D.getDay()]);
B=B.replace("%%","%");
return B
}});
tinymce.PluginManager.add("template",tinymce.plugins.TemplatePlugin)
})();