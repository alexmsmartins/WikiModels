tinyMCEPopup.requireLangPack();
var doc;
var defaultDocTypes='XHTML 1.0 Transitional=<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">,XHTML 1.0 Frameset=<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">,XHTML 1.0 Strict=<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">,XHTML 1.1=<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">,HTML 4.01 Transitional=<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">,HTML 4.01 Strict=<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">,HTML 4.01 Frameset=<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">';
var defaultEncodings="Western european (iso-8859-1)=iso-8859-1,Central European (iso-8859-2)=iso-8859-2,Unicode (UTF-8)=utf-8,Chinese traditional (Big5)=big5,Cyrillic (iso-8859-5)=iso-8859-5,Japanese (iso-2022-jp)=iso-2022-jp,Greek (iso-8859-7)=iso-8859-7,Korean (iso-2022-kr)=iso-2022-kr,ASCII (us-ascii)=us-ascii";
var defaultMediaTypes="all=all,screen=screen,print=print,tty=tty,tv=tv,projection=projection,handheld=handheld,braille=braille,aural=aural";
var defaultFontNames="Arial=arial,helvetica,sans-serif;Courier New=courier new,courier,monospace;Georgia=georgia,times new roman,times,serif;Tahoma=tahoma,arial,helvetica,sans-serif;Times New Roman=times new roman,times,serif;Verdana=verdana,arial,helvetica,sans-serif;Impact=impact;WingDings=wingdings";
var defaultFontSizes="10px,11px,12px,13px,14px,15px,16px";
function init(){var I=document.forms.fullpage,C=I.elements,J,F,B,D,K,G,L,H=tinyMCEPopup.editor,E=tinyMCEPopup.dom,A;
D=H.getParam("fullpage_doctypes",defaultDocTypes).split(",");
for(F=0;
F<D.length;
F++){B=D[F].split("=");
if(B.length>1){addSelectValue(I,"doctypes",B[0],B[1])
}}L=H.getParam("fullpage_fonts",defaultFontNames).split(";");
for(F=0;
F<L.length;
F++){B=L[F].split("=");
if(B.length>1){addSelectValue(I,"fontface",B[0],B[1])
}}L=H.getParam("fullpage_fontsizes",defaultFontSizes).split(",");
for(F=0;
F<L.length;
F++){addSelectValue(I,"fontsize",L[F],L[F])
}G=H.getParam("fullpage_media_types",defaultMediaTypes).split(",");
for(F=0;
F<G.length;
F++){B=G[F].split("=");
if(B.length>1){addSelectValue(I,"element_style_media",B[0],B[1]);
addSelectValue(I,"element_link_media",B[0],B[1])
}}K=H.getParam("fullpage_encodings",defaultEncodings).split(",");
for(F=0;
F<K.length;
F++){B=K[F].split("=");
if(B.length>1){addSelectValue(I,"docencoding",B[0],B[1]);
addSelectValue(I,"element_script_charset",B[0],B[1]);
addSelectValue(I,"element_link_charset",B[0],B[1])
}}document.getElementById("bgcolor_pickcontainer").innerHTML=getColorPickerHTML("bgcolor_pick","bgcolor");
document.getElementById("link_color_pickcontainer").innerHTML=getColorPickerHTML("link_color_pick","link_color");
document.getElementById("visited_color_pickcontainer").innerHTML=getColorPickerHTML("visited_color_pick","visited_color");
document.getElementById("active_color_pickcontainer").innerHTML=getColorPickerHTML("active_color_pick","active_color");
document.getElementById("textcolor_pickcontainer").innerHTML=getColorPickerHTML("textcolor_pick","textcolor");
document.getElementById("stylesheet_browsercontainer").innerHTML=getBrowserHTML("stylesheetbrowser","stylesheet","file","fullpage");
document.getElementById("link_href_pickcontainer").innerHTML=getBrowserHTML("link_href_browser","element_link_href","file","fullpage");
document.getElementById("script_src_pickcontainer").innerHTML=getBrowserHTML("script_src_browser","element_script_src","file","fullpage");
document.getElementById("bgimage_pickcontainer").innerHTML=getBrowserHTML("bgimage_browser","bgimage","image","fullpage");
if(isVisible("stylesheetbrowser")){document.getElementById("stylesheet").style.width="220px"
}if(isVisible("link_href_browser")){document.getElementById("element_link_href").style.width="230px"
}if(isVisible("bgimage_browser")){document.getElementById("bgimage").style.width="210px"
}E.add(document.body,"iframe",{id:"documentIframe",src:'javascript:""',style:{display:"none"}});
doc=E.get("documentIframe").contentWindow.document;
h=tinyMCEPopup.getWindowArg("head_html");
h=h.replace(/<script>/gi,'<script type="text/javascript">');
h=h.replace(/type=([\"\'])?/gi,"type=$1-mce-");
h=h.replace(/(src=|href=)/g,"mce_$1");
doc.write(h+"</body></html>");
doc.close();
xmlVer=getReItem(/<\?\s*?xml.*?version\s*?=\s*?"(.*?)".*?\?>/gi,h,1);
xmlEnc=getReItem(/<\?\s*?xml.*?encoding\s*?=\s*?"(.*?)".*?\?>/gi,h,1);
docType=getReItem(/<\!DOCTYPE.*?>/gi,h,0);
I.langcode.value=getReItem(/lang="(.*?)"/gi,h,1);
if(J=doc.getElementsByTagName("title")[0]){C.metatitle.value=J.textContent||J.text
}tinymce.each(doc.getElementsByTagName("meta"),function(P){var N=(P.getAttribute("name",2)||"").toLowerCase(),O=P.getAttribute("content",2),M=P.getAttribute("httpEquiv",2)||"";
J=C["meta"+N];
if(N=="robots"){selectByValue(I,"metarobots",tinymce.trim(O),true,true);
return 
}switch(M.toLowerCase()){case"content-type":tmp=getReItem(/charset\s*=\s*(.*)\s*/gi,O,1);
if(tmp!=""){xmlEnc=tmp
}return 
}if(J){J.value=O
}});
selectByValue(I,"doctypes",docType,true,true);
selectByValue(I,"docencoding",xmlEnc,true,true);
selectByValue(I,"langdir",doc.body.getAttribute("dir",2)||"",true,true);
if(xmlVer!=""){C.xml_pi.checked=true
}tinymce.each(doc.getElementsByTagName("link"),function(N){var M=N.getAttribute("media",2)||"",O=N.getAttribute("type",2)||"";
if(O=="-mce-text/css"&&(M==""||M=="screen"||M=="all")&&(N.getAttribute("rel",2)||"")=="stylesheet"){I.stylesheet.value=N.getAttribute("mce_href",2)||"";
return false
}});
tinymce.each(doc.getElementsByTagName("style"),function(M){var N=parseStyleElement(M);
for(x=0;
x<N.length;
x++){if(N[x].rule.indexOf("a:visited")!=-1&&N[x].data.color){I.visited_color.value=N[x].data.color
}if(N[x].rule.indexOf("a:link")!=-1&&N[x].data.color){I.link_color.value=N[x].data.color
}if(N[x].rule.indexOf("a:active")!=-1&&N[x].data.color){I.active_color.value=N[x].data.color
}}});
I.textcolor.value=tinyMCEPopup.dom.getAttrib(doc.body,"text");
I.active_color.value=tinyMCEPopup.dom.getAttrib(doc.body,"alink");
I.link_color.value=tinyMCEPopup.dom.getAttrib(doc.body,"link");
I.visited_color.value=tinyMCEPopup.dom.getAttrib(doc.body,"vlink");
I.bgcolor.value=tinyMCEPopup.dom.getAttrib(doc.body,"bgcolor");
I.bgimage.value=tinyMCEPopup.dom.getAttrib(doc.body,"background");
A=tinyMCEPopup.dom.parseStyle(tinyMCEPopup.dom.getAttrib(doc.body,"style"));
if(A["font-family"]){selectByValue(I,"fontface",A["font-family"],true,true)
}else{selectByValue(I,"fontface",H.getParam("fullpage_default_fontface",""),true,true)
}if(A["font-size"]){selectByValue(I,"fontsize",A["font-size"],true,true)
}else{selectByValue(I,"fontsize",H.getParam("fullpage_default_fontsize",""),true,true)
}if(A.color){I.textcolor.value=convertRGBToHex(A.color)
}if(A["background-image"]){I.bgimage.value=A["background-image"].replace(new RegExp("url\\('?([^']*)'?\\)","gi"),"$1")
}if(A["background-color"]){I.bgcolor.value=A["background-color"]
}if(A.margin){tmp=A.margin.replace(/[^0-9 ]/g,"");
tmp=tmp.split(/ +/);
I.topmargin.value=tmp.length>0?tmp[0]:"";
I.rightmargin.value=tmp.length>1?tmp[1]:tmp[0];
I.bottommargin.value=tmp.length>2?tmp[2]:tmp[0];
I.leftmargin.value=tmp.length>3?tmp[3]:tmp[0]
}if(A["margin-left"]){I.leftmargin.value=A["margin-left"].replace(/[^0-9]/g,"")
}if(A["margin-right"]){I.rightmargin.value=A["margin-right"].replace(/[^0-9]/g,"")
}if(A["margin-top"]){I.topmargin.value=A["margin-top"].replace(/[^0-9]/g,"")
}if(A["margin-bottom"]){I.bottommargin.value=A["margin-bottom"].replace(/[^0-9]/g,"")
}I.style.value=tinyMCEPopup.dom.serializeStyle(A);
updateColor("textcolor_pick","textcolor");
updateColor("bgcolor_pick","bgcolor");
updateColor("visited_color_pick","visited_color");
updateColor("active_color_pick","active_color");
updateColor("link_color_pick","link_color")
}function getReItem(C,B,A){var D=C.exec(B);
if(D&&D.length>A){return D[A]
}return""
}function updateAction(){var H=document.forms[0],A,E,F,J,L,I,G,B,D,C=true,K;
I=doc.getElementsByTagName("head")[0];
A=doc.getElementsByTagName("script");
for(E=0;
E<A.length;
E++){if(tinyMCEPopup.dom.getAttrib(A[E],"mce_type")==""){A[E].setAttribute("mce_type","text/javascript")
}}A=doc.getElementsByTagName("link");
for(E=0;
E<A.length;
E++){B=A[E];
D=tinyMCEPopup.dom.getAttrib(B,"media");
if(tinyMCEPopup.dom.getAttrib(B,"mce_type")=="text/css"&&(D==""||D=="screen"||D=="all")&&tinyMCEPopup.dom.getAttrib(B,"rel")=="stylesheet"){C=false;
if(H.stylesheet.value==""){B.parentNode.removeChild(B)
}else{B.setAttribute("mce_href",H.stylesheet.value)
}break
}}if(H.stylesheet.value!=""){B=doc.createElement("link");
B.setAttribute("type","text/css");
B.setAttribute("mce_href",H.stylesheet.value);
B.setAttribute("rel","stylesheet");
I.appendChild(B)
}setMeta(I,"keywords",H.metakeywords.value);
setMeta(I,"description",H.metadescription.value);
setMeta(I,"author",H.metaauthor.value);
setMeta(I,"copyright",H.metacopyright.value);
setMeta(I,"robots",getSelectValue(H,"metarobots"));
setMeta(I,"Content-Type",getSelectValue(H,"docencoding"));
doc.body.dir=getSelectValue(H,"langdir");
doc.body.style.cssText=H.style.value;
doc.body.setAttribute("vLink",H.visited_color.value);
doc.body.setAttribute("link",H.link_color.value);
doc.body.setAttribute("text",H.textcolor.value);
doc.body.setAttribute("aLink",H.active_color.value);
doc.body.style.fontFamily=getSelectValue(H,"fontface");
doc.body.style.fontSize=getSelectValue(H,"fontsize");
doc.body.style.backgroundColor=H.bgcolor.value;
if(H.leftmargin.value!=""){doc.body.style.marginLeft=H.leftmargin.value+"px"
}if(H.rightmargin.value!=""){doc.body.style.marginRight=H.rightmargin.value+"px"
}if(H.bottommargin.value!=""){doc.body.style.marginBottom=H.bottommargin.value+"px"
}if(H.topmargin.value!=""){doc.body.style.marginTop=H.topmargin.value+"px"
}G=doc.getElementsByTagName("html")[0];
G.setAttribute("lang",H.langcode.value);
G.setAttribute("xml:lang",H.langcode.value);
if(H.bgimage.value!=""){doc.body.style.backgroundImage="url('"+H.bgimage.value+"')"
}else{doc.body.style.backgroundImage=""
}K=tinyMCEPopup.editor.plugins.fullpage._createSerializer();
K.setRules("-title,meta[http-equiv|name|content],base[href|target],link[href|rel|type|title|media],style[type],script[type|language|src],html[lang|xml::lang|xmlns],body[style|dir|vlink|link|text|alink],head");
F=K.serialize(doc.documentElement);
F=F.substring(0,F.lastIndexOf("</body>"));
if(F.indexOf("<title>")==-1){F=F.replace(/<head.*?>/,"$&\n<title>"+tinyMCEPopup.dom.encode(H.metatitle.value)+"</title>")
}else{F=F.replace(/<title>(.*?)<\/title>/,"<title>"+tinyMCEPopup.dom.encode(H.metatitle.value)+"</title>")
}if((J=getSelectValue(H,"doctypes"))!=""){F=J+"\n"+F
}if(H.xml_pi.checked){L='<?xml version="1.0"';
if((J=getSelectValue(H,"docencoding"))!=""){L+=' encoding="'+J+'"'
}L+="?>\n";
F=L+F
}F=F.replace(/type=\"\-mce\-/gi,'type="');
tinyMCEPopup.editor.plugins.fullpage.head=F;
tinyMCEPopup.editor.plugins.fullpage._setBodyAttribs(tinyMCEPopup.editor,{});
tinyMCEPopup.close()
}function changedStyleField(A){}function setMeta(F,D,C){var B,E,A;
B=F.getElementsByTagName("meta");
for(E=0;
E<B.length;
E++){if(D=="Content-Type"&&tinyMCEPopup.dom.getAttrib(B[E],"http-equiv")==D){if(C==""){B[E].parentNode.removeChild(B[E])
}else{B[E].setAttribute("content","text/html; charset="+C)
}return 
}if(tinyMCEPopup.dom.getAttrib(B[E],"name")==D){if(C==""){B[E].parentNode.removeChild(B[E])
}else{B[E].setAttribute("content",C)
}return 
}}if(C==""){return 
}A=doc.createElement("meta");
if(D=="Content-Type"){A.httpEquiv=D
}else{A.setAttribute("name",D)
}A.setAttribute("content",C);
F.appendChild(A)
}function parseStyleElement(E){var A=E.innerHTML;
var D,B,C;
A=A.replace(/<!--/gi,"");
A=A.replace(/-->/gi,"");
A=A.replace(/[\n\r]/gi,"");
A=A.replace(/\s+/gi," ");
C=[];
D=A.split(/{|}/);
for(B=0;
B<D.length;
B+=2){if(D[B]!=""){C[C.length]={rule:tinymce.trim(D[B]),data:tinyMCEPopup.dom.parseStyle(D[B+1])}
}}return C
}function serializeStyleElement(D){var B,C,A;
C="<!--\n";
for(B=0;
B<D.length;
B++){C+=D[B].rule+" {\n";
A=tinyMCE.serializeStyle(D[B].data);
if(A!=""){A+=";"
}C+=A.replace(/;/g,";\n");
C+="}\n";
if(B!=D.length-1){C+="\n"
}}C+="\n-->";
return C
}tinyMCEPopup.onInit.add(init);