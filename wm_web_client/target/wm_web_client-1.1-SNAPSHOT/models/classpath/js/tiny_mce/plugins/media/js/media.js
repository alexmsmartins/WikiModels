tinyMCEPopup.requireLangPack();
var oldWidth,oldHeight,ed,url;
if(url=tinyMCEPopup.getParam("media_external_list_url")){document.write('<script language="javascript" type="text/javascript" src="'+tinyMCEPopup.editor.documentBaseURI.toAbsolute(url)+'"><\/script>')
}function init(){var E="",F,G;
var D="flash",A,C;
ed=tinyMCEPopup.editor;
tinyMCEPopup.resizeToInnerSize();
F=document.forms[0];
A=ed.selection.getNode();
if(/mceItem(Flash|ShockWave|WindowsMedia|QuickTime|RealMedia)/.test(ed.dom.getAttrib(A,"class"))){E=A.title;
switch(ed.dom.getAttrib(A,"class")){case"mceItemFlash":D="flash";
break;
case"mceItemFlashVideo":D="flv";
break;
case"mceItemShockWave":D="shockwave";
break;
case"mceItemWindowsMedia":D="wmp";
break;
case"mceItemQuickTime":D="qt";
break;
case"mceItemRealMedia":D="rmp";
break
}document.forms[0].insert.value=ed.getLang("update","Insert",true)
}document.getElementById("filebrowsercontainer").innerHTML=getBrowserHTML("filebrowser","src","media","media");
document.getElementById("qtsrcfilebrowsercontainer").innerHTML=getBrowserHTML("qtsrcfilebrowser","qt_qtsrc","media","media");
document.getElementById("bgcolor_pickcontainer").innerHTML=getColorPickerHTML("bgcolor_pick","bgcolor");
var B=getMediaListHTML("medialist","src","media","media");
if(B==""){document.getElementById("linklistrow").style.display="none"
}else{document.getElementById("linklistcontainer").innerHTML=B
}if(isVisible("filebrowser")){document.getElementById("src").style.width="230px"
}if(E!=""){E=tinyMCEPopup.editor.plugins.media._parse(E);
switch(D){case"flash":setBool(E,"flash","play");
setBool(E,"flash","loop");
setBool(E,"flash","menu");
setBool(E,"flash","swliveconnect");
setStr(E,"flash","quality");
setStr(E,"flash","scale");
setStr(E,"flash","salign");
setStr(E,"flash","wmode");
setStr(E,"flash","base");
setStr(E,"flash","flashvars");
break;
case"qt":setBool(E,"qt","loop");
setBool(E,"qt","autoplay");
setBool(E,"qt","cache");
setBool(E,"qt","controller");
setBool(E,"qt","correction");
setBool(E,"qt","enablejavascript");
setBool(E,"qt","kioskmode");
setBool(E,"qt","autohref");
setBool(E,"qt","playeveryframe");
setBool(E,"qt","tarsetcache");
setStr(E,"qt","scale");
setStr(E,"qt","starttime");
setStr(E,"qt","endtime");
setStr(E,"qt","tarset");
setStr(E,"qt","qtsrcchokespeed");
setStr(E,"qt","volume");
setStr(E,"qt","qtsrc");
break;
case"shockwave":setBool(E,"shockwave","sound");
setBool(E,"shockwave","progress");
setBool(E,"shockwave","autostart");
setBool(E,"shockwave","swliveconnect");
setStr(E,"shockwave","swvolume");
setStr(E,"shockwave","swstretchstyle");
setStr(E,"shockwave","swstretchhalign");
setStr(E,"shockwave","swstretchvalign");
break;
case"wmp":setBool(E,"wmp","autostart");
setBool(E,"wmp","enabled");
setBool(E,"wmp","enablecontextmenu");
setBool(E,"wmp","fullscreen");
setBool(E,"wmp","invokeurls");
setBool(E,"wmp","mute");
setBool(E,"wmp","stretchtofit");
setBool(E,"wmp","windowlessvideo");
setStr(E,"wmp","balance");
setStr(E,"wmp","baseurl");
setStr(E,"wmp","captioningid");
setStr(E,"wmp","currentmarker");
setStr(E,"wmp","currentposition");
setStr(E,"wmp","defaultframe");
setStr(E,"wmp","playcount");
setStr(E,"wmp","rate");
setStr(E,"wmp","uimode");
setStr(E,"wmp","volume");
break;
case"rmp":setBool(E,"rmp","autostart");
setBool(E,"rmp","loop");
setBool(E,"rmp","autogotourl");
setBool(E,"rmp","center");
setBool(E,"rmp","imagestatus");
setBool(E,"rmp","maintainaspect");
setBool(E,"rmp","nojava");
setBool(E,"rmp","prefetch");
setBool(E,"rmp","shuffle");
setStr(E,"rmp","console");
setStr(E,"rmp","controls");
setStr(E,"rmp","numloop");
setStr(E,"rmp","scriptcallbacks");
break
}setStr(E,null,"src");
setStr(E,null,"id");
setStr(E,null,"name");
setStr(E,null,"vspace");
setStr(E,null,"hspace");
setStr(E,null,"bgcolor");
setStr(E,null,"align");
setStr(E,null,"width");
setStr(E,null,"height");
if((G=ed.dom.getAttrib(A,"width"))!=""){E.width=F.width.value=G
}if((G=ed.dom.getAttrib(A,"height"))!=""){E.height=F.height.value=G
}oldWidth=E.width?parseInt(E.width):0;
oldHeight=E.height?parseInt(E.height):0
}else{oldWidth=oldHeight=0
}selectByValue(F,"media_type",D);
changedType(D);
updateColor("bgcolor_pick","bgcolor");
TinyMCE_EditableSelects.init();
generatePreview()
}function insertMedia(){var A,C=document.forms[0],B;
tinyMCEPopup.restoreSelection();
if(!AutoValidator.validate(C)){tinyMCEPopup.alert(ed.getLang("invalid_data"));
return false
}C.width.value=C.width.value==""?100:C.width.value;
C.height.value=C.height.value==""?100:C.height.value;
A=ed.selection.getNode();
if(A!=null&&/mceItem(Flash|ShockWave|WindowsMedia|QuickTime|RealMedia)/.test(ed.dom.getAttrib(A,"class"))){switch(C.media_type.options[C.media_type.selectedIndex].value){case"flash":A.className="mceItemFlash";
break;
case"flv":A.className="mceItemFlashVideo";
break;
case"shockwave":A.className="mceItemShockWave";
break;
case"qt":A.className="mceItemQuickTime";
break;
case"wmp":A.className="mceItemWindowsMedia";
break;
case"rmp":A.className="mceItemRealMedia";
break
}if(A.width!=C.width.value||A.height!=C.height.value){ed.execCommand("mceRepaint")
}A.title=serializeParameters();
A.width=C.width.value;
A.height=C.height.value;
A.style.width=C.width.value+(C.width.value.indexOf("%")==-1?"px":"");
A.style.height=C.height.value+(C.height.value.indexOf("%")==-1?"px":"");
A.align=C.align.options[C.align.selectedIndex].value
}else{B='<img src="'+tinyMCEPopup.getWindowArg("plugin_url")+'/img/trans.gif"';
switch(C.media_type.options[C.media_type.selectedIndex].value){case"flash":B+=' class="mceItemFlash"';
break;
case"flv":B+=' class="mceItemFlashVideo"';
break;
case"shockwave":B+=' class="mceItemShockWave"';
break;
case"qt":B+=' class="mceItemQuickTime"';
break;
case"wmp":B+=' class="mceItemWindowsMedia"';
break;
case"rmp":B+=' class="mceItemRealMedia"';
break
}B+=' title="'+serializeParameters()+'"';
B+=' width="'+C.width.value+'"';
B+=' height="'+C.height.value+'"';
B+=' align="'+C.align.options[C.align.selectedIndex].value+'"';
B+=" />";
ed.execCommand("mceInsertContent",false,B)
}tinyMCEPopup.close()
}function updatePreview(){var B=document.forms[0],A;
B.width.value=B.width.value||"320";
B.height.value=B.height.value||"240";
A=getType(B.src.value);
selectByValue(B,"media_type",A);
changedType(A);
generatePreview()
}function getMediaListHTML(){if(typeof (tinyMCEMediaList)!="undefined"&&tinyMCEMediaList.length>0){var B="";
B+='<select id="linklist" name="linklist" style="width: 250px" onchange="this.form.src.value=this.options[this.selectedIndex].value;updatePreview();">';
B+='<option value="">---</option>';
for(var A=0;
A<tinyMCEMediaList.length;
A++){B+='<option value="'+tinyMCEMediaList[A][1]+'">'+tinyMCEMediaList[A][0]+"</option>"
}B+="</select>";
return B
}return""
}function getType(B){var D,C,G,E,A,F=document.forms[0];
D=ed.getParam("media_types","flash=swf;flv=flv;shockwave=dcr;qt=mov,qt,mpg,mp3,mp4,mpeg;shockwave=dcr;wmp=avi,wmv,wm,asf,asx,wmx,wvx;rmp=rm,ra,ram").split(";");
if(B.match(/watch\?v=(.+)(.*)/)){F.width.value="425";
F.height.value="350";
F.src.value="http://www.youtube.com/v/"+B.match(/v=(.*)(.*)/)[0].split("=")[1];
return"flash"
}if(B.indexOf("http://video.google.com/videoplay?docid=")==0){F.width.value="425";
F.height.value="326";
F.src.value="http://video.google.com/googleplayer.swf?docId="+B.substring("http://video.google.com/videoplay?docid=".length)+"&hl=en";
return"flash"
}for(C=0;
C<D.length;
C++){G=D[C].split("=");
E=G[1].split(",");
for(A=0;
A<E.length;
A++){if(B.indexOf("."+E[A])!=-1){return G[0]
}}}return null
}function switchType(A){var B=getType(A),D=document,C=D.forms[0];
if(!B){return 
}selectByValue(D.forms[0],"media_type",B);
changedType(B);
if(B=="qt"&&C.src.value.toLowerCase().indexOf("rtsp://")!=-1){alert(ed.getLang("media_qt_stream_warn"));
if(C.qt_qtsrc.value==""){C.qt_qtsrc.value=C.src.value
}}}function changedType(A){var B=document;
B.getElementById("flash_options").style.display="none";
B.getElementById("flv_options").style.display="none";
B.getElementById("qt_options").style.display="none";
B.getElementById("shockwave_options").style.display="none";
B.getElementById("wmp_options").style.display="none";
B.getElementById("rmp_options").style.display="none";
if(A){B.getElementById(A+"_options").style.display="block"
}}function serializeParameters(){var C=document,B=C.forms[0],A="";
switch(B.media_type.options[B.media_type.selectedIndex].value){case"flash":A+=getBool("flash","play",true);
A+=getBool("flash","loop",true);
A+=getBool("flash","menu",true);
A+=getBool("flash","swliveconnect",false);
A+=getStr("flash","quality");
A+=getStr("flash","scale");
A+=getStr("flash","salign");
A+=getStr("flash","wmode");
A+=getStr("flash","base");
A+=getStr("flash","flashvars");
break;
case"qt":A+=getBool("qt","loop",false);
A+=getBool("qt","autoplay",true);
A+=getBool("qt","cache",false);
A+=getBool("qt","controller",true);
A+=getBool("qt","correction",false,"none","full");
A+=getBool("qt","enablejavascript",false);
A+=getBool("qt","kioskmode",false);
A+=getBool("qt","autohref",false);
A+=getBool("qt","playeveryframe",false);
A+=getBool("qt","targetcache",false);
A+=getStr("qt","scale");
A+=getStr("qt","starttime");
A+=getStr("qt","endtime");
A+=getStr("qt","target");
A+=getStr("qt","qtsrcchokespeed");
A+=getStr("qt","volume");
A+=getStr("qt","qtsrc");
break;
case"shockwave":A+=getBool("shockwave","sound");
A+=getBool("shockwave","progress");
A+=getBool("shockwave","autostart");
A+=getBool("shockwave","swliveconnect");
A+=getStr("shockwave","swvolume");
A+=getStr("shockwave","swstretchstyle");
A+=getStr("shockwave","swstretchhalign");
A+=getStr("shockwave","swstretchvalign");
break;
case"wmp":A+=getBool("wmp","autostart",true);
A+=getBool("wmp","enabled",false);
A+=getBool("wmp","enablecontextmenu",true);
A+=getBool("wmp","fullscreen",false);
A+=getBool("wmp","invokeurls",true);
A+=getBool("wmp","mute",false);
A+=getBool("wmp","stretchtofit",false);
A+=getBool("wmp","windowlessvideo",false);
A+=getStr("wmp","balance");
A+=getStr("wmp","baseurl");
A+=getStr("wmp","captioningid");
A+=getStr("wmp","currentmarker");
A+=getStr("wmp","currentposition");
A+=getStr("wmp","defaultframe");
A+=getStr("wmp","playcount");
A+=getStr("wmp","rate");
A+=getStr("wmp","uimode");
A+=getStr("wmp","volume");
break;
case"rmp":A+=getBool("rmp","autostart",false);
A+=getBool("rmp","loop",false);
A+=getBool("rmp","autogotourl",true);
A+=getBool("rmp","center",false);
A+=getBool("rmp","imagestatus",true);
A+=getBool("rmp","maintainaspect",false);
A+=getBool("rmp","nojava",false);
A+=getBool("rmp","prefetch",false);
A+=getBool("rmp","shuffle",false);
A+=getStr("rmp","console");
A+=getStr("rmp","controls");
A+=getStr("rmp","numloop");
A+=getStr("rmp","scriptcallbacks");
break
}A+=getStr(null,"id");
A+=getStr(null,"name");
A+=getStr(null,"src");
A+=getStr(null,"align");
A+=getStr(null,"bgcolor");
A+=getInt(null,"vspace");
A+=getInt(null,"hspace");
A+=getStr(null,"width");
A+=getStr(null,"height");
A=A.length>0?A.substring(0,A.length-1):A;
return A
}function setBool(A,B,C){if(typeof (A[C])=="undefined"){return 
}document.forms[0].elements[B+"_"+C].checked=A[C]!="false"
}function setStr(A,D,E){var B=document.forms[0],C=B.elements[(D!=null?D+"_":"")+E];
if(typeof (A[E])=="undefined"){return 
}if(C.type=="text"){C.value=A[E]
}else{selectByValue(B,(D!=null?D+"_":"")+E,A[E])
}}function getBool(D,F,E,B,C){var A=document.forms[0].elements[D+"_"+F].checked;
B=typeof (B)=="undefined"?"true":"'"+jsEncode(B)+"'";
C=typeof (C)=="undefined"?"false":"'"+jsEncode(C)+"'";
return(A==E)?"":F+(A?":"+B+",":":'"+C+"',")
}function getStr(C,E,D){var B=document.forms[0].elements[(C!=null?C+"_":"")+E];
var A=B.type=="text"?B.value:B.options[B.selectedIndex].value;
if(E=="src"){A=tinyMCEPopup.editor.convertURL(A,"src",null)
}return((E==D||A=="")?"":E+":'"+jsEncode(A)+"',")
}function getInt(C,E,D){var B=document.forms[0].elements[(C!=null?C+"_":"")+E];
var A=B.type=="text"?B.value:B.options[B.selectedIndex].value;
return((E==D||A=="")?"":E+":"+A.replace(/[^0-9]+/g,"")+",")
}function jsEncode(A){A=A.replace(new RegExp("\\\\","g"),"\\\\");
A=A.replace(new RegExp('"',"g"),'\\"');
A=A.replace(new RegExp("'","g"),"\\'");
return A
}function generatePreview(I){var H=document.forms[0],A=document.getElementById("prev"),F="",M,D,C,J,E,K,L,G,B;
A.innerHTML="<!-- x --->";
G=parseInt(H.width.value);
B=parseInt(H.height.value);
if(H.width.value!=""&&H.height.value!=""){if(H.constrain.checked){if(I=="width"&&oldWidth!=0){K=G/oldWidth;
B=Math.round(K*B);
H.height.value=B
}else{if(I=="height"&&oldHeight!=0){L=B/oldHeight;
G=Math.round(L*G);
H.width.value=G
}}}}if(H.width.value!=""){oldWidth=G
}if(H.height.value!=""){oldHeight=B
}D=serializeParameters();
switch(H.media_type.options[H.media_type.selectedIndex].value){case"flash":M="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000";
E="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0";
J="application/x-shockwave-flash";
break;
case"shockwave":M="clsid:166B1BCA-3F9C-11CF-8075-444553540000";
E="http://download.macromedia.com/pub/shockwave/cabs/director/sw.cab#version=8,5,1,0";
J="application/x-director";
break;
case"qt":M="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B";
E="http://www.apple.com/qtactivex/qtplugin.cab#version=6,0,2,0";
J="video/quicktime";
break;
case"wmp":M=ed.getParam("media_wmp6_compatible")?"clsid:05589FA1-C356-11CE-BF01-00AA0055595A":"clsid:6BF52A52-394A-11D3-B153-00C04F79FAA6";
E="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701";
J="application/x-mplayer2";
break;
case"rmp":M="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA";
E="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701";
J="audio/x-pn-realaudio-plugin";
break
}if(D==""){A.innerHTML="";
return 
}D=tinyMCEPopup.editor.plugins.media._parse(D);
if(!D.src){A.innerHTML="";
return 
}D.src=tinyMCEPopup.editor.documentBaseURI.toAbsolute(D.src);
D.width=!D.width?100:D.width;
D.height=!D.height?100:D.height;
D.id=!D.id?"obj":D.id;
D.name=!D.name?"eobj":D.name;
D.align=!D.align?"":D.align;
if(!tinymce.isIE||document.location.protocol!="https:"){F+='<object classid="'+M+'" codebase="'+E+'" width="'+D.width+'" height="'+D.height+'" id="'+D.id+'" name="'+D.name+'" align="'+D.align+'">';
for(C in D){F+='<param name="'+C+'" value="'+D[C]+'">';
if(C=="src"&&D[C].indexOf("://")!=-1){F+='<param name="url" value="'+D[C]+'" />'
}}}F+='<embed type="'+J+'" ';
for(C in D){F+=C+'="'+D[C]+'" '
}F+="></embed>";
if(!tinymce.isIE||document.location.protocol!="https:"){F+="</object>"
}A.innerHTML="<!-- x --->"+F
}tinyMCEPopup.onInit.add(init);