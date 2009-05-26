(function(){var A=tinymce.each;
tinymce.create("tinymce.plugins.TemplatePlugin",{init:function(B,D){var C=this;
C.editor=B;
B.addCommand("mceTemplate",function(E){B.windowManager.open({file:D+"/template.htm",width:B.getParam("template_popup_width",750),height:B.getParam("template_popup_height",600),inline:1},{plugin_url:D})
});
B.addCommand("mceInsertTemplate",C._insertTemplate,C);
B.addButton("template",{title:"template.desc",cmd:"mceTemplate"});
B.onPreProcess.add(function(G,E){var F=G.dom;
A(F.select("div",E.node),function(H){if(F.hasClass(H,"mceTmpl")){A(F.select("*",H),function(I){if(F.hasClass(I,G.getParam("template_mdate_classes","mdate").replace(/\s+/g,"|"))){I.innerHTML=C._getDateTime(new Date(),G.getParam("template_mdate_format",G.getLang("template.mdate_format")))
}});
C._replaceVals(H)
}})
})
},getInfo:function(){return{longname:"Template plugin",author:"Moxiecode Systems AB",authorurl:"http://www.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/template",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_insertTemplate:function(D,C){var B=this,E=B.editor,F,I,H=E.dom,J=E.selection.getContent();
F=C.content;
A(B.editor.getParam("template_replace_values"),function(K,L){if(typeof (K)!="function"){F=F.replace(new RegExp("\\{\\$"+L+"\\}","g"),K)
}});
I=H.create("div",null,F);
n=H.select(".mceTmpl",I);
if(n&&n.length>0){I=H.create("div",null);
I.appendChild(n[0].cloneNode(true))
}function G(K,L){return new RegExp("\\b"+L+"\\b","g").test(K.className)
}A(H.select("*",I),function(K){if(G(K,E.getParam("template_cdate_classes","cdate").replace(/\s+/g,"|"))){K.innerHTML=B._getDateTime(new Date(),E.getParam("template_cdate_format",E.getLang("template.cdate_format")))
}if(G(K,E.getParam("template_mdate_classes","mdate").replace(/\s+/g,"|"))){K.innerHTML=B._getDateTime(new Date(),E.getParam("template_mdate_format",E.getLang("template.mdate_format")))
}if(G(K,E.getParam("template_selected_content_classes","selcontent").replace(/\s+/g,"|"))){K.innerHTML=J
}});
B._replaceVals(I);
E.execCommand("mceInsertContent",false,I.innerHTML);
E.addVisual()
},_replaceVals:function(D){var C=this.editor.dom,B=this.editor.getParam("template_replace_values");
A(C.select("*",D),function(E){A(B,function(F,G){if(C.hasClass(E,G)){if(typeof (B[G])=="function"){B[G](E)
}}})
})
},_getDateTime:function(C,B){if(!B){return""
}function D(E,G){var F;
E=""+E;
if(E.length<G){for(F=0;
F<(G-E.length);
F++){E="0"+E
}}return E
}B=B.replace("%D","%m/%d/%y");
B=B.replace("%r","%I:%M:%S %p");
B=B.replace("%Y",""+C.getFullYear());
B=B.replace("%y",""+C.getYear());
B=B.replace("%m",D(C.getMonth()+1,2));
B=B.replace("%d",D(C.getDate(),2));
B=B.replace("%H",""+D(C.getHours(),2));
B=B.replace("%M",""+D(C.getMinutes(),2));
B=B.replace("%S",""+D(C.getSeconds(),2));
B=B.replace("%I",""+((C.getHours()+11)%12+1));
B=B.replace("%p",""+(C.getHours()<12?"AM":"PM"));
B=B.replace("%B",""+tinyMCE.getLang("template_months_long").split(",")[C.getMonth()]);
B=B.replace("%b",""+tinyMCE.getLang("template_months_short").split(",")[C.getMonth()]);
B=B.replace("%A",""+tinyMCE.getLang("template_day_long").split(",")[C.getDay()]);
B=B.replace("%a",""+tinyMCE.getLang("template_day_short").split(",")[C.getDay()]);
B=B.replace("%%","%");
return B
}});
tinymce.PluginManager.add("template",tinymce.plugins.TemplatePlugin)
})();