(function(){tinymce.create("tinymce.plugins.PageBreakPlugin",{init:function(B,D){var F='<img src="'+D+'/img/trans.gif" class="mcePageBreak mceItemNoResize" />',A="mcePageBreak",C=B.getParam("pagebreak_separator","<!-- pagebreak -->"),E;
E=new RegExp(C.replace(/[\?\.\*\[\]\(\)\{\}\+\^\$\:]/g,function(G){return"\\"+G
}),"g");
B.addCommand("mcePageBreak",function(){B.execCommand("mceInsertContent",0,F)
});
B.addButton("pagebreak",{title:"pagebreak.desc",cmd:A});
B.onInit.add(function(){if(B.settings.content_css!==false){B.dom.loadCSS(D+"/css/content.css")
}if(B.theme.onResolveName){B.theme.onResolveName.add(function(G,H){if(H.node.nodeName=="IMG"&&B.dom.hasClass(H.node,A)){H.name="pagebreak"
}})
}});
B.onClick.add(function(G,H){H=H.target;
if(H.nodeName==="IMG"&&G.dom.hasClass(H,A)){G.selection.select(H)
}});
B.onNodeChange.add(function(H,G,I){G.setActive("pagebreak",I.nodeName==="IMG"&&H.dom.hasClass(I,A))
});
B.onBeforeSetContent.add(function(G,H){H.content=H.content.replace(E,F)
});
B.onPostProcess.add(function(G,H){if(H.get){H.content=H.content.replace(/<img[^>]+>/g,function(I){if(I.indexOf('class="mcePageBreak')!==-1){I=C
}return I
})
}})
},getInfo:function(){return{longname:"PageBreak",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/pagebreak",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("pagebreak",tinymce.plugins.PageBreakPlugin)
})();