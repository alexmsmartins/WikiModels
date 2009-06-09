(function(){var A=tinymce.each;
tinymce.create("tinymce.plugins.MediaPlugin",{init:function(B,F){var D=this;
D.editor=B;
D.url=F;
function C(G){return/^(mceItemFlash|mceItemShockWave|mceItemWindowsMedia|mceItemQuickTime|mceItemRealMedia)$/.test(G.className)
}B.onPreInit.add(function(){B.serializer.addRules("param[name|value|_mce_value]")
});
B.addCommand("mceMedia",function(){B.windowManager.open({file:F+"/media.htm",width:430+parseInt(B.getLang("media.delta_width",0)),height:470+parseInt(B.getLang("media.delta_height",0)),inline:1},{plugin_url:F})
});
B.addButton("media",{title:"media.desc",cmd:"mceMedia"});
B.onNodeChange.add(function(H,I,G){I.setActive("media",G.nodeName=="IMG"&&C(G))
});
B.onInit.add(function(){var G={mceItemFlash:"flash",mceItemShockWave:"shockwave",mceItemWindowsMedia:"windowsmedia",mceItemQuickTime:"quicktime",mceItemRealMedia:"realmedia"};
B.selection.onSetContent.add(function(){D._spansToImgs(B.getBody())
});
B.selection.onBeforeSetContent.add(D._objectsToSpans,D);
if(B.settings.content_css!==false){B.dom.loadCSS(F+"/css/content.css")
}if(B.theme.onResolveName){B.theme.onResolveName.add(function(I,H){if(H.name=="img"){A(G,function(J,K){if(B.dom.hasClass(H.node,K)){H.name=J;
H.title=B.dom.getAttrib(H.node,"title");
return false
}})
}})
}if(B&&B.plugins.contextmenu){B.plugins.contextmenu.onContextMenu.add(function(I,J,H){if(H.nodeName=="IMG"&&/mceItem(Flash|ShockWave|WindowsMedia|QuickTime|RealMedia)/.test(H.className)){J.add({title:"media.edit",icon:"media",cmd:"mceMedia"})
}})
}});
B.onBeforeSetContent.add(D._objectsToSpans,D);
B.onSetContent.add(function(){D._spansToImgs(B.getBody())
});
B.onPreProcess.add(function(I,G){var H=I.dom;
if(G.set){D._spansToImgs(G.node);
A(H.select("IMG",G.node),function(J){var K;
if(C(J)){K=D._parse(J.title);
H.setAttrib(J,"width",H.getAttrib(J,"width",K.width||100));
H.setAttrib(J,"height",H.getAttrib(J,"height",K.height||100))
}})
}if(G.get){A(H.select("IMG",G.node),function(J){var K,M,L;
if(I.getParam("media_use_script")){if(C(J)){J.className=J.className.replace(/mceItem/g,"mceTemp")
}return 
}switch(J.className){case"mceItemFlash":K="d27cdb6e-ae6d-11cf-96b8-444553540000";
M="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0";
L="application/x-shockwave-flash";
break;
case"mceItemShockWave":K="166b1bca-3f9c-11cf-8075-444553540000";
M="http://download.macromedia.com/pub/shockwave/cabs/director/sw.cab#version=8,5,1,0";
L="application/x-director";
break;
case"mceItemWindowsMedia":K=I.getParam("media_wmp6_compatible")?"05589fa1-c356-11ce-bf01-00aa0055595a":"6bf52a52-394a-11d3-b153-00c04f79faa6";
M="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701";
L="application/x-mplayer2";
break;
case"mceItemQuickTime":K="02bf25d5-8c17-4b23-bc80-d3488abddc6b";
M="http://www.apple.com/qtactivex/qtplugin.cab#version=6,0,2,0";
L="video/quicktime";
break;
case"mceItemRealMedia":K="cfcdaa03-8be4-11cf-b84b-0020afbbccfa";
M="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0";
L="audio/x-pn-realaudio-plugin";
break
}if(K){H.replace(D._buildObj({classid:K,codebase:M,type:L},J),J)
}})
}});
B.onPostProcess.add(function(H,G){G.content=G.content.replace(/_mce_value=/g,"value=")
});
function E(H,G){G=new RegExp(G+'="([^"]+)"',"g").exec(H);
return G?B.dom.decode(G[1]):""
}B.onPostProcess.add(function(H,G){if(H.getParam("media_use_script")){G.content=G.content.replace(/<img[^>]+>/g,function(I){var J=E(I,"class");
if(/^(mceTempFlash|mceTempShockWave|mceTempWindowsMedia|mceTempQuickTime|mceTempRealMedia)$/.test(J)){at=D._parse(E(I,"title"));
at.width=E(I,"width");
at.height=E(I,"height");
I='<script type="text/javascript">write'+J.substring(7)+"({"+D._serialize(at)+"});<\/script>"
}return I
})
}})
},getInfo:function(){return{longname:"Media",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/media",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_objectsToSpans:function(B,C){var E=this,D=C.content;
D=D.replace(/<script[^>]*>\s*write(Flash|ShockWave|WindowsMedia|QuickTime|RealMedia)\(\{([^\)]*)\}\);\s*<\/script>/gi,function(H,I,F){var G=E._parse(F);
return'<img class="mceItem'+I+'" title="'+B.dom.encode(F)+'" src="'+E.url+'/img/trans.gif" width="'+G.width+'" height="'+G.height+'" />'
});
D=D.replace(/<object([^>]*)>/gi,'<span class="mceItemObject" $1>');
D=D.replace(/<embed([^>]*)\/?>/gi,'<span class="mceItemEmbed" $1></span>');
D=D.replace(/<embed([^>]*)>/gi,'<span class="mceItemEmbed" $1>');
D=D.replace(/<\/(object)([^>]*)>/gi,"</span>");
D=D.replace(/<\/embed>/gi,"");
D=D.replace(/<param([^>]*)>/gi,function(F,G){return"<span "+G.replace(/value=/gi,"_mce_value=")+' class="mceItemParam"></span>'
});
D=D.replace(/\/ class=\"mceItemParam\"><\/span>/gi,'class="mceItemParam"></span>');
C.content=D
},_buildObj:function(D,C){var G,H=this.editor,E=H.dom,F=this._parse(C.title),B;
B=H.getParam("media_strict",true)&&D.type=="application/x-shockwave-flash";
F.width=D.width=E.getAttrib(C,"width")||100;
F.height=D.height=E.getAttrib(C,"height")||100;
if(F.src){F.src=H.convertURL(F.src,"src",C)
}if(B){G=E.create("span",{id:F.id,mce_name:"object",type:"application/x-shockwave-flash",data:F.src,style:E.getAttrib(C,"style"),width:D.width,height:D.height})
}else{G=E.create("span",{id:F.id,mce_name:"object",classid:"clsid:"+D.classid,style:E.getAttrib(C,"style"),codebase:D.codebase,width:D.width,height:D.height})
}A(F,function(I,J){if(!/^(width|height|codebase|classid|id|_cx|_cy)$/.test(J)){if(D.type=="application/x-mplayer2"&&J=="src"&&!F.url){J="url"
}if(I){E.add(G,"span",{mce_name:"param",name:J,_mce_value:I})
}}});
if(!B){E.add(G,"span",tinymce.extend({mce_name:"embed",type:D.type,style:E.getAttrib(C,"style")},F))
}return G
},_spansToImgs:function(D){var E=this,C=E.editor.dom,B,F;
A(C.select("span",D),function(G){if(C.getAttrib(G,"class")=="mceItemObject"){F=C.getAttrib(G,"classid").toLowerCase().replace(/\s+/g,"");
switch(F){case"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000":C.replace(E._createImg("mceItemFlash",G),G);
break;
case"clsid:166b1bca-3f9c-11cf-8075-444553540000":C.replace(E._createImg("mceItemShockWave",G),G);
break;
case"clsid:6bf52a52-394a-11d3-b153-00c04f79faa6":case"clsid:22d6f312-b0f6-11d0-94ab-0080c74c7e95":case"clsid:05589fa1-c356-11ce-bf01-00aa0055595a":C.replace(E._createImg("mceItemWindowsMedia",G),G);
break;
case"clsid:02bf25d5-8c17-4b23-bc80-d3488abddc6b":C.replace(E._createImg("mceItemQuickTime",G),G);
break;
case"clsid:cfcdaa03-8be4-11cf-b84b-0020afbbccfa":C.replace(E._createImg("mceItemRealMedia",G),G);
break;
default:C.replace(E._createImg("mceItemFlash",G),G)
}return 
}if(C.getAttrib(G,"class")=="mceItemEmbed"){switch(C.getAttrib(G,"type")){case"application/x-shockwave-flash":C.replace(E._createImg("mceItemFlash",G),G);
break;
case"application/x-director":C.replace(E._createImg("mceItemShockWave",G),G);
break;
case"application/x-mplayer2":C.replace(E._createImg("mceItemWindowsMedia",G),G);
break;
case"video/quicktime":C.replace(E._createImg("mceItemQuickTime",G),G);
break;
case"audio/x-pn-realaudio-plugin":C.replace(E._createImg("mceItemRealMedia",G),G);
break;
default:C.replace(E._createImg("mceItemFlash",G),G)
}}})
},_createImg:function(H,C){var B,D=this.editor.dom,E={},F="",G;
G=["id","name","width","height","bgcolor","align","flashvars","src","wmode","allowfullscreen","quality"];
B=D.create("img",{src:this.url+"/img/trans.gif",width:D.getAttrib(C,"width")||100,height:D.getAttrib(C,"height")||100,style:D.getAttrib(C,"style"),"class":H});
A(G,function(J){var I=D.getAttrib(C,J);
if(I){E[J]=I
}});
A(D.select("span",C),function(I){if(D.hasClass(I,"mceItemParam")){E[D.getAttrib(I,"name")]=D.getAttrib(I,"_mce_value")
}});
if(E.movie){E.src=E.movie;
delete E.movie
}C=D.select(".mceItemEmbed",C)[0];
if(C){A(G,function(J){var I=D.getAttrib(C,J);
if(I&&!E[J]){E[J]=I
}})
}delete E.width;
delete E.height;
B.title=this._serialize(E);
return B
},_parse:function(B){return tinymce.util.JSON.parse("{"+B+"}")
},_serialize:function(B){return tinymce.util.JSON.serialize(B).replace(/[{}]/g,"")
}});
tinymce.PluginManager.add("media",tinymce.plugins.MediaPlugin)
})();