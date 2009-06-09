(function(){var A=tinymce.DOM;
tinymce.ThemeManager.requireLangPack("simple");
tinymce.create("tinymce.themes.SimpleTheme",{init:function(C,D){var E=this,B=["Bold","Italic","Underline","Strikethrough","InsertUnorderedList","InsertOrderedList"],F=C.settings;
E.editor=C;
C.onInit.add(function(){C.onNodeChange.add(function(H,G){tinymce.each(B,function(I){G.get(I.toLowerCase()).setActive(H.queryCommandState(I))
})
});
C.dom.loadCSS(D+"/skins/"+F.skin+"/content.css")
});
A.loadCSS((F.editor_css?C.documentBaseURI.toAbsolute(F.editor_css):"")||D+"/skins/"+F.skin+"/ui.css")
},renderUI:function(H){var E=this,I=H.targetNode,B,C,D=E.editor,F=D.controlManager,G;
I=A.insertAfter(A.create("span",{id:D.id+"_container","class":"mceEditor "+D.settings.skin+"SimpleSkin"}),I);
I=G=A.add(I,"table",{cellPadding:0,cellSpacing:0,"class":"mceLayout"});
I=C=A.add(I,"tbody");
I=A.add(C,"tr");
I=B=A.add(A.add(I,"td"),"div",{"class":"mceIframeContainer"});
I=A.add(A.add(C,"tr",{"class":"last"}),"td",{"class":"mceToolbar mceLast",align:"center"});
C=E.toolbar=F.createToolbar("tools1");
C.add(F.createButton("bold",{title:"simple.bold_desc",cmd:"Bold"}));
C.add(F.createButton("italic",{title:"simple.italic_desc",cmd:"Italic"}));
C.add(F.createButton("underline",{title:"simple.underline_desc",cmd:"Underline"}));
C.add(F.createButton("strikethrough",{title:"simple.striketrough_desc",cmd:"Strikethrough"}));
C.add(F.createSeparator());
C.add(F.createButton("undo",{title:"simple.undo_desc",cmd:"Undo"}));
C.add(F.createButton("redo",{title:"simple.redo_desc",cmd:"Redo"}));
C.add(F.createSeparator());
C.add(F.createButton("cleanup",{title:"simple.cleanup_desc",cmd:"mceCleanup"}));
C.add(F.createSeparator());
C.add(F.createButton("insertunorderedlist",{title:"simple.bullist_desc",cmd:"InsertUnorderedList"}));
C.add(F.createButton("insertorderedlist",{title:"simple.numlist_desc",cmd:"InsertOrderedList"}));
C.renderTo(I);
return{iframeContainer:B,editorContainer:D.id+"_container",sizeContainer:G,deltaHeight:-20}
},getInfo:function(){return{longname:"Simple theme",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.ThemeManager.add("simple",tinymce.themes.SimpleTheme)
})();