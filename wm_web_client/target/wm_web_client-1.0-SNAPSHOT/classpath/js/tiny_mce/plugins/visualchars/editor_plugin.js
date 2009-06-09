(function(){tinymce.create("tinymce.plugins.VisualChars",{init:function(B,A){var C=this;
C.editor=B;
B.addCommand("mceVisualChars",C._toggleVisualChars,C);
B.addButton("visualchars",{title:"visualchars.desc",cmd:"mceVisualChars"});
B.onBeforeGetContent.add(function(E,D){if(C.state){C.state=true;
C._toggleVisualChars()
}})
},getInfo:function(){return{longname:"Visual characters",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/visualchars",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_toggleVisualChars:function(){var B=this,F=B.editor,J,H,G,D=F.getDoc(),C=F.getBody(),E,A=F.selection,I;
B.state=!B.state;
F.controlManager.setActive("visualchars",B.state);
if(B.state){J=[];
tinymce.walk(C,function(K){if(K.nodeType==3&&K.nodeValue&&K.nodeValue.indexOf("\u00a0")!=-1){J.push(K)
}},"childNodes");
for(H=0;
H<J.length;
H++){E=J[H].nodeValue;
E=E.replace(/(\u00a0+)/g,'<span class="mceItemHidden mceVisualNbsp">$1</span>');
E=E.replace(/\u00a0/g,"\u00b7");
F.dom.setOuterHTML(J[H],E,D)
}}else{J=tinymce.grep(F.dom.select("span",C),function(K){return F.dom.hasClass(K,"mceVisualNbsp")
});
for(H=0;
H<J.length;
H++){F.dom.setOuterHTML(J[H],J[H].innerHTML.replace(/(&middot;|\u00b7)/g,"&nbsp;"),D)
}}}});
tinymce.PluginManager.add("visualchars",tinymce.plugins.VisualChars)
})();