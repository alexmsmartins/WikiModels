(function(){tinymce.create("tinymce.plugins.PageBreakPlugin",{init:function(A,E){var C='<img src="'+E+'/img/trans.gif" class="mcePageBreak mceItemNoResize" />',B="mcePageBreak",F=A.getParam("pagebreak_separator","<!-- pagebreak -->"),D;
D=new RegExp(F.replace(/[\?\.\*\[\]\(\)\{\}\+\^\$\:]/g,function(G){return"\\"+G
}),"g");
A.addCommand("mcePageBreak",function(){A.execCommand("mceInsertContent",0,C)
});
A.addButton("pagebreak",{title:"pagebreak.desc",cmd:B});
A.onInit.add(function(){if(A.settings.content_css!==false){A.dom.loadCSS(E+"/css/content.css")
}if(A.theme.onResolveName){A.theme.onResolveName.add(function(H,G){if(G.node.nodeName=="IMG"&&A.dom.hasClass(G.node,B)){G.name="pagebreak"
}})
}});
A.onClick.add(function(H,G){G=G.target;
if(G.nodeName==="IMG"&&H.dom.hasClass(G,B)){H.selection.select(G)
}});
A.onNodeChange.add(function(H,I,G){I.setActive("pagebreak",G.nodeName==="IMG"&&H.dom.hasClass(G,B))
});
A.onBeforeSetContent.add(function(H,G){G.content=G.content.replace(D,C)
});
A.onPostProcess.add(function(H,G){if(G.get){G.content=G.content.replace(/<img[^>]+>/g,function(I){if(I.indexOf('class="mcePageBreak')!==-1){I=F
}return I
})
}})
},getInfo:function(){return{longname:"PageBreak",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/pagebreak",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("pagebreak",tinymce.plugins.PageBreakPlugin)
})();