(function(){var C=tinymce.DOM,A=tinymce.dom.Event,D=tinymce.each,B=tinymce.explode;
tinymce.create("tinymce.plugins.TabFocusPlugin",{init:function(F,G){function E(I,J){if(J.keyCode===9){return A.cancel(J)
}}function H(K,O){var I,L,N,M,J;
function P(Q){N=C.getParent(K.id,"form");
M=N.elements;
if(N){D(M,function(S,R){if(S.id==K.id){I=R;
return false
}});
if(Q>0){for(L=I+1;
L<M.length;
L++){if(M[L].type!="hidden"){return M[L]
}}}else{for(L=I-1;
L>=0;
L--){if(M[L].type!="hidden"){return M[L]
}}}}return null
}if(O.keyCode===9){J=B(K.getParam("tab_focus",K.getParam("tabfocus_elements",":prev,:next")));
if(J.length==1){J[1]=J[0];
J[0]=":prev"
}if(O.shiftKey){if(J[0]==":prev"){M=P(-1)
}else{M=C.get(J[0])
}}else{if(J[1]==":next"){M=P(1)
}else{M=C.get(J[1])
}}if(M){if(K=tinymce.EditorManager.get(M.id||M.name)){K.focus()
}else{window.setTimeout(function(){window.focus();
M.focus()
},10)
}return A.cancel(O)
}}}F.onKeyUp.add(E);
if(tinymce.isGecko){F.onKeyPress.add(H);
F.onKeyDown.add(E)
}else{F.onKeyDown.add(H)
}F.onInit.add(function(){D(C.select("a:first,a:last",F.getContainer()),function(I){A.add(I,"focus",function(){F.focus()
})
})
})
},getInfo:function(){return{longname:"Tabfocus",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/tabfocus",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("tabfocus",tinymce.plugins.TabFocusPlugin)
})();