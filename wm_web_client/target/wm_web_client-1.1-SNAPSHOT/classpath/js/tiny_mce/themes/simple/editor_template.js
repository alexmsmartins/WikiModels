(function(){var A=tinymce.DOM;
tinymce.ThemeManager.requireLangPack("simple");
tinymce.create("tinymce.themes.SimpleTheme",{init:function(F,E){var D=this,B=["Bold","Italic","Underline","Strikethrough","InsertUnorderedList","InsertOrderedList"],C=F.settings;
D.editor=F;
F.onInit.add(function(){F.onNodeChange.add(function(G,H){tinymce.each(B,function(I){H.get(I.toLowerCase()).setActive(G.queryCommandState(I))
})
});
F.dom.loadCSS(E+"/skins/"+C.skin+"/content.css")
});
A.loadCSS((C.editor_css?F.documentBaseURI.toAbsolute(C.editor_css):"")||E+"/skins/"+C.skin+"/ui.css")
},renderUI:function(D){var G=this,C=D.targetNode,B,I,H=G.editor,F=H.controlManager,E;
C=A.insertAfter(A.create("span",{id:H.id+"_container","class":"mceEditor "+H.settings.skin+"SimpleSkin"}),C);
C=E=A.add(C,"table",{cellPadding:0,cellSpacing:0,"class":"mceLayout"});
C=I=A.add(C,"tbody");
C=A.add(I,"tr");
C=B=A.add(A.add(C,"td"),"div",{"class":"mceIframeContainer"});
C=A.add(A.add(I,"tr",{"class":"last"}),"td",{"class":"mceToolbar mceLast",align:"center"});
I=G.toolbar=F.createToolbar("tools1");
I.add(F.createButton("bold",{title:"simple.bold_desc",cmd:"Bold"}));
I.add(F.createButton("italic",{title:"simple.italic_desc",cmd:"Italic"}));
I.add(F.createButton("underline",{title:"simple.underline_desc",cmd:"Underline"}));
I.add(F.createButton("strikethrough",{title:"simple.striketrough_desc",cmd:"Strikethrough"}));
I.add(F.createSeparator());
I.add(F.createButton("undo",{title:"simple.undo_desc",cmd:"Undo"}));
I.add(F.createButton("redo",{title:"simple.redo_desc",cmd:"Redo"}));
I.add(F.createSeparator());
I.add(F.createButton("cleanup",{title:"simple.cleanup_desc",cmd:"mceCleanup"}));
I.add(F.createSeparator());
I.add(F.createButton("insertunorderedlist",{title:"simple.bullist_desc",cmd:"InsertUnorderedList"}));
I.add(F.createButton("insertorderedlist",{title:"simple.numlist_desc",cmd:"InsertOrderedList"}));
I.renderTo(C);
return{iframeContainer:B,editorContainer:H.id+"_container",sizeContainer:E,deltaHeight:-20}
},getInfo:function(){return{longname:"Simple theme",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.ThemeManager.add("simple",tinymce.themes.SimpleTheme)
})();