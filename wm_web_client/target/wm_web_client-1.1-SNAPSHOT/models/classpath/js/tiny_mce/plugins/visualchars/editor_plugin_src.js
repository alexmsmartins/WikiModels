(function(){tinymce.create("tinymce.plugins.VisualChars",{init:function(A,B){var C=this;
C.editor=A;
A.addCommand("mceVisualChars",C._toggleVisualChars,C);
A.addButton("visualchars",{title:"visualchars.desc",cmd:"mceVisualChars"});
A.onBeforeGetContent.add(function(D,E){if(C.state){C.state=true;
C._toggleVisualChars()
}})
},getInfo:function(){return{longname:"Visual characters",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/visualchars",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_toggleVisualChars:function(){var I=this,E=I.editor,A,C,D,G=E.getDoc(),H=E.getBody(),F,J=E.selection,B;
I.state=!I.state;
E.controlManager.setActive("visualchars",I.state);
if(I.state){A=[];
tinymce.walk(H,function(K){if(K.nodeType==3&&K.nodeValue&&K.nodeValue.indexOf("\u00a0")!=-1){A.push(K)
}},"childNodes");
for(C=0;
C<A.length;
C++){F=A[C].nodeValue;
F=F.replace(/(\u00a0+)/g,'<span class="mceItemHidden mceVisualNbsp">$1</span>');
F=F.replace(/\u00a0/g,"\u00b7");
E.dom.setOuterHTML(A[C],F,G)
}}else{A=tinymce.grep(E.dom.select("span",H),function(K){return E.dom.hasClass(K,"mceVisualNbsp")
});
for(C=0;
C<A.length;
C++){E.dom.setOuterHTML(A[C],A[C].innerHTML.replace(/(&middot;|\u00b7)/g,"&nbsp;"),G)
}}}});
tinymce.PluginManager.add("visualchars",tinymce.plugins.VisualChars)
})();