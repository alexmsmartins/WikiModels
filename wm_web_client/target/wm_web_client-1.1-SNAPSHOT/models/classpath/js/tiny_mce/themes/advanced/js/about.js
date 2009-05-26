tinyMCEPopup.requireLangPack();
function init(){var A,B;
tinyMCEPopup.resizeToInnerSize();
A=tinyMCEPopup.editor;
window.setTimeout(insertHelpIFrame,10);
B=document.getElementById("plugintablecontainer");
document.getElementById("plugins_tab").style.display="none";
var C="";
C+='<table id="plugintable">';
C+="<thead>";
C+="<tr>";
C+="<td>"+A.getLang("advanced_dlg.about_plugin")+"</td>";
C+="<td>"+A.getLang("advanced_dlg.about_author")+"</td>";
C+="<td>"+A.getLang("advanced_dlg.about_version")+"</td>";
C+="</tr>";
C+="</thead>";
C+="<tbody>";
tinymce.each(A.plugins,function(E,F){var D;
if(!E.getInfo){return 
}C+="<tr>";
D=E.getInfo();
if(D.infourl!=null&&D.infourl!=""){C+='<td width="50%" title="'+F+'"><a href="'+D.infourl+'" target="_blank">'+D.longname+"</a></td>"
}else{C+='<td width="50%" title="'+F+'">'+D.longname+"</td>"
}if(D.authorurl!=null&&D.authorurl!=""){C+='<td width="35%"><a href="'+D.authorurl+'" target="_blank">'+D.author+"</a></td>"
}else{C+='<td width="35%">'+D.author+"</td>"
}C+='<td width="15%">'+D.version+"</td>";
C+="</tr>";
document.getElementById("plugins_tab").style.display=""
});
C+="</tbody>";
C+="</table>";
B.innerHTML=C;
tinyMCEPopup.dom.get("version").innerHTML=tinymce.majorVersion+"."+tinymce.minorVersion;
tinyMCEPopup.dom.get("date").innerHTML=tinymce.releaseDate
}function insertHelpIFrame(){var A;
if(tinyMCEPopup.getParam("docs_url")){A='<iframe width="100%" height="300" src="'+tinyMCEPopup.editor.baseURI.toAbsolute(tinyMCEPopup.getParam("docs_url"))+'"></iframe>';
document.getElementById("iframecontainer").innerHTML=A;
document.getElementById("help_tab").style.display="block"
}}tinyMCEPopup.onInit.add(init);