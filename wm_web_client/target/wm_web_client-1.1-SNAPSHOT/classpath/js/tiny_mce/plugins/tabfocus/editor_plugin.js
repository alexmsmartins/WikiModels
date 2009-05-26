(function(){var D=tinymce.DOM,B=tinymce.dom.Event,C=tinymce.each,A=tinymce.explode;
tinymce.create("tinymce.plugins.TabFocusPlugin",{init:function(G,F){function H(J,I){if(I.keyCode===9){return B.cancel(I)
}}function E(J,N){var L,I,O,P,K;
function M(Q){O=D.getParent(J.id,"form");
P=O.elements;
if(O){C(P,function(R,S){if(R.id==J.id){L=S;
return false
}});
if(Q>0){for(I=L+1;
I<P.length;
I++){if(P[I].type!="hidden"){return P[I]
}}}else{for(I=L-1;
I>=0;
I--){if(P[I].type!="hidden"){return P[I]
}}}}return null
}if(N.keyCode===9){K=A(J.getParam("tab_focus",J.getParam("tabfocus_elements",":prev,:next")));
if(K.length==1){K[1]=K[0];
K[0]=":prev"
}if(N.shiftKey){if(K[0]==":prev"){P=M(-1)
}else{P=D.get(K[0])
}}else{if(K[1]==":next"){P=M(1)
}else{P=D.get(K[1])
}}if(P){if(J=tinymce.EditorManager.get(P.id||P.name)){J.focus()
}else{window.setTimeout(function(){window.focus();
P.focus()
},10)
}return B.cancel(N)
}}}G.onKeyUp.add(H);
if(tinymce.isGecko){G.onKeyPress.add(E);
G.onKeyDown.add(H)
}else{G.onKeyDown.add(E)
}G.onInit.add(function(){C(D.select("a:first,a:last",G.getContainer()),function(I){B.add(I,"focus",function(){G.focus()
})
})
})
},getInfo:function(){return{longname:"Tabfocus",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/tabfocus",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("tabfocus",tinymce.plugins.TabFocusPlugin)
})();