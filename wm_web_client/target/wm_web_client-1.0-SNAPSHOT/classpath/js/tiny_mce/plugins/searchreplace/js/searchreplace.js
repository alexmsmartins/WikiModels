tinyMCEPopup.requireLangPack();
var SearchReplaceDialog={init:function(B){var C=document.forms[0],A=tinyMCEPopup.getWindowArg("mode");
this.switchMode(A);
C[A+"_panel_searchstring"].value=tinyMCEPopup.getWindowArg("search_string");
C[A+"_panel_searchstring"].focus()
},switchMode:function(A){var C,B=this.lastMode;
if(B!=A){C=document.forms[0];
if(B){C[A+"_panel_searchstring"].value=C[B+"_panel_searchstring"].value;
C[A+"_panel_backwardsu"].checked=C[B+"_panel_backwardsu"].checked;
C[A+"_panel_backwardsd"].checked=C[B+"_panel_backwardsd"].checked;
C[A+"_panel_casesensitivebox"].checked=C[B+"_panel_casesensitivebox"].checked
}mcTabs.displayTab(A+"_tab",A+"_panel");
document.getElementById("replaceBtn").style.display=(A=="replace")?"inline":"none";
document.getElementById("replaceAllBtn").style.display=(A=="replace")?"inline":"none";
this.lastMode=A
}},searchNext:function(J){var E=tinyMCEPopup.editor,G=E.selection,A=G.getRng(),F,C=this.lastMode,N,I,L=0,K=E.getWin(),M=E.windowManager,H=0;
F=document.forms[0];
N=F[C+"_panel_searchstring"].value;
I=F[C+"_panel_backwardsu"].checked;
ca=F[C+"_panel_casesensitivebox"].checked;
rs=F.replace_panel_replacestring.value;
if(N==""){return 
}function D(){A=G.getRng().cloneRange();
E.getDoc().execCommand("SelectAll",false,null);
G.setRng(A)
}function B(){if(tinymce.isIE){E.selection.getRng().duplicate().pasteHTML(rs)
}else{E.getDoc().execCommand("InsertHTML",false,rs)
}}if(ca){L=L|4
}switch(J){case"all":E.execCommand("SelectAll");
E.selection.collapse(true);
if(tinymce.isIE){while(A.findText(N,I?-1:1,L)){A.scrollIntoView();
A.select();
B();
H=1
}tinyMCEPopup.storeSelection()
}else{while(K.find(N,ca,I,false,false,false,false)){B();
H=1
}}if(H){tinyMCEPopup.alert(E.getLang("searchreplace_dlg.allreplaced"))
}else{tinyMCEPopup.alert(E.getLang("searchreplace_dlg.notfound"))
}return ;
case"current":if(!E.selection.isCollapsed()){B()
}break
}G.collapse(I);
A=G.getRng();
if(!N){return 
}if(tinymce.isIE){if(A.findText(N,I?-1:1,L)){A.scrollIntoView();
A.select()
}else{tinyMCEPopup.alert(E.getLang("searchreplace_dlg.notfound"))
}tinyMCEPopup.storeSelection()
}else{if(!K.find(N,ca,I,false,false,false,false)){tinyMCEPopup.alert(E.getLang("searchreplace_dlg.notfound"))
}else{D()
}}}};
tinyMCEPopup.onInit.add(SearchReplaceDialog.init,SearchReplaceDialog);