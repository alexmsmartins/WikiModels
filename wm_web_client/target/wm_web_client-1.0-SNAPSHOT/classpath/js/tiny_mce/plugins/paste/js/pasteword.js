tinyMCEPopup.requireLangPack();
var PasteWordDialog={init:function(){var A=tinyMCEPopup.editor,C=document.getElementById("iframecontainer"),F,E,B,D="";
C.innerHTML='<iframe id="iframe" src="javascript:\'\';" frameBorder="0" style="border: 1px solid gray"></iframe>';
F=document.getElementById("iframe");
E=F.contentWindow.document;
B=[A.baseURI.toAbsolute("themes/"+A.settings.theme+"/skins/"+A.settings.skin+"/content.css")];
B=B.concat(tinymce.explode(A.settings.content_css)||[]);
tinymce.each(B,function(G){D+='<link href="'+A.documentBaseURI.toAbsolute(G)+'" rel="stylesheet" type="text/css" />'
});
E.open();
E.write("<html><head>"+D+'</head><body class="mceContentBody" spellcheck="false"></body></html>');
E.close();
E.designMode="on";
this.resize();
window.setTimeout(function(){F.contentWindow.focus()
},10)
},insert:function(){var A=document.getElementById("iframe").contentWindow.document.body.innerHTML;
tinyMCEPopup.editor.execCommand("mceInsertClipboardContent",false,A);
tinyMCEPopup.close()
},resize:function(){var A=tinyMCEPopup.dom.getViewPort(window),B;
B=document.getElementById("iframe");
if(B){B.style.width=(A.w-20)+"px";
B.style.height=(A.h-90)+"px"
}}};
tinyMCEPopup.onInit.add(PasteWordDialog.init,PasteWordDialog);