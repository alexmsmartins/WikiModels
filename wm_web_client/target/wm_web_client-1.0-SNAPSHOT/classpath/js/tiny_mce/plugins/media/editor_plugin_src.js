(function(){var A=tinymce.each;
tinymce.create("tinymce.plugins.MediaPlugin",{init:function(B,C){var E=this;
E.editor=B;
E.url=C;
function F(G){return/^(mceItemFlash|mceItemShockWave|mceItemWindowsMedia|mceItemQuickTime|mceItemRealMedia)$/.test(G.className)
}B.onPreInit.add(function(){B.serializer.addRules("param[name|value|_mce_value]")
});
B.addCommand("mceMedia",function(){B.windowManager.open({file:C+"/media.htm",width:430+parseInt(B.getLang("media.delta_width",0)),height:470+parseInt(B.getLang("media.delta_height",0)),inline:1},{plugin_url:C})
});
B.addButton("media",{title:"media.desc",cmd:"mceMedia"});
B.onNodeChange.add(function(H,G,I){G.setActive("media",I.nodeName=="IMG"&&F(I))
});
B.onInit.add(function(){var G={mceItemFlash:"flash",mceItemShockWave:"shockwave",mceItemWindowsMedia:"windowsmedia",mceItemQuickTime:"quicktime",mceItemRealMedia:"realmedia"};
B.selection.onSetContent.add(function(){E._spansToImgs(B.getBody())
});
B.selection.onBeforeSetContent.add(E._objectsToSpans,E);
if(B.settings.content_css!==false){B.dom.loadCSS(C+"/css/content.css")
}if(B.theme.onResolveName){B.theme.onResolveName.add(function(H,I){if(I.name=="img"){A(G,function(K,J){if(B.dom.hasClass(I.node,J)){I.name=K;
I.title=B.dom.getAttrib(I.node,"title");
return false
}})
}})
}if(B&&B.plugins.contextmenu){B.plugins.contextmenu.onContextMenu.add(function(I,H,J){if(J.nodeName=="IMG"&&/mceItem(Flash|ShockWave|WindowsMedia|QuickTime|RealMedia)/.test(J.className)){H.add({title:"media.edit",icon:"media",cmd:"mceMedia"})
}})
}});
B.onBeforeSetContent.add(E._objectsToSpans,E);
B.onSetContent.add(function(){E._spansToImgs(B.getBody())
});
B.onPreProcess.add(function(G,I){var H=G.dom;
if(I.set){E._spansToImgs(I.node);
A(H.select("IMG",I.node),function(K){var J;
if(F(K)){J=E._parse(K.title);
H.setAttrib(K,"width",H.getAttrib(K,"width",J.width||100));
H.setAttrib(K,"height",H.getAttrib(K,"height",J.height||100))
}})
}if(I.get){A(H.select("IMG",I.node),function(M){var L,J,K;
if(G.getParam("media_use_script")){if(F(M)){M.className=M.className.replace(/mceItem/g,"mceTemp")
}return 
}switch(M.className){case"mceItemFlash":L="d27cdb6e-ae6d-11cf-96b8-444553540000";
J="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0";
K="application/x-shockwave-flash";
break;
case"mceItemShockWave":L="166b1bca-3f9c-11cf-8075-444553540000";
J="http://download.macromedia.com/pub/shockwave/cabs/director/sw.cab#version=8,5,1,0";
K="application/x-director";
break;
case"mceItemWindowsMedia":L=G.getParam("media_wmp6_compatible")?"05589fa1-c356-11ce-bf01-00aa0055595a":"6bf52a52-394a-11d3-b153-00c04f79faa6";
J="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701";
K="application/x-mplayer2";
break;
case"mceItemQuickTime":L="02bf25d5-8c17-4b23-bc80-d3488abddc6b";
J="http://www.apple.com/qtactivex/qtplugin.cab#version=6,0,2,0";
K="video/quicktime";
break;
case"mceItemRealMedia":L="cfcdaa03-8be4-11cf-b84b-0020afbbccfa";
J="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0";
K="audio/x-pn-realaudio-plugin";
break
}if(L){H.replace(E._buildObj({classid:L,codebase:J,type:K},M),M)
}})
}});
B.onPostProcess.add(function(G,H){H.content=H.content.replace(/_mce_value=/g,"value=")
});
function D(G,H){H=new RegExp(H+'="([^"]+)"',"g").exec(G);
return H?B.dom.decode(H[1]):""
}B.onPostProcess.add(function(G,H){if(G.getParam("media_use_script")){H.content=H.content.replace(/<img[^>]+>/g,function(J){var I=D(J,"class");
if(/^(mceTempFlash|mceTempShockWave|mceTempWindowsMedia|mceTempQuickTime|mceTempRealMedia)$/.test(I)){at=E._parse(D(J,"title"));
at.width=D(J,"width");
at.height=D(J,"height");
J='<script type="text/javascript">write'+I.substring(7)+"({"+E._serialize(at)+"});<\/script>"
}return J
})
}})
},getInfo:function(){return{longname:"Media",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/media",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_objectsToSpans:function(B,E){var C=this,D=E.content;
D=D.replace(/<script[^>]*>\s*write(Flash|ShockWave|WindowsMedia|QuickTime|RealMedia)\(\{([^\)]*)\}\);\s*<\/script>/gi,function(G,F,I){var H=C._parse(I);
return'<img class="mceItem'+F+'" title="'+B.dom.encode(I)+'" src="'+C.url+'/img/trans.gif" width="'+H.width+'" height="'+H.height+'" />'
});
D=D.replace(/<object([^>]*)>/gi,'<span class="mceItemObject" $1>');
D=D.replace(/<embed([^>]*)\/?>/gi,'<span class="mceItemEmbed" $1></span>');
D=D.replace(/<embed([^>]*)>/gi,'<span class="mceItemEmbed" $1>');
D=D.replace(/<\/(object)([^>]*)>/gi,"</span>");
D=D.replace(/<\/embed>/gi,"");
D=D.replace(/<param([^>]*)>/gi,function(G,F){return"<span "+F.replace(/value=/gi,"_mce_value=")+' class="mceItemParam"></span>'
});
D=D.replace(/\/ class=\"mceItemParam\"><\/span>/gi,'class="mceItemParam"></span>');
E.content=D
},_buildObj:function(G,H){var D,C=this.editor,F=C.dom,E=this._parse(H.title),B;
B=C.getParam("media_strict",true)&&G.type=="application/x-shockwave-flash";
E.width=G.width=F.getAttrib(H,"width")||100;
E.height=G.height=F.getAttrib(H,"height")||100;
if(E.src){E.src=C.convertURL(E.src,"src",H)
}if(B){D=F.create("span",{id:E.id,mce_name:"object",type:"application/x-shockwave-flash",data:E.src,style:F.getAttrib(H,"style"),width:G.width,height:G.height})
}else{D=F.create("span",{id:E.id,mce_name:"object",classid:"clsid:"+G.classid,style:F.getAttrib(H,"style"),codebase:G.codebase,width:G.width,height:G.height})
}A(E,function(J,I){if(!/^(width|height|codebase|classid|id|_cx|_cy)$/.test(I)){if(G.type=="application/x-mplayer2"&&I=="src"&&!E.url){I="url"
}if(J){F.add(D,"span",{mce_name:"param",name:I,_mce_value:J})
}}});
if(!B){F.add(D,"span",tinymce.extend({mce_name:"embed",type:G.type,style:F.getAttrib(H,"style")},E))
}return D
},_spansToImgs:function(E){var D=this,F=D.editor.dom,B,C;
A(F.select("span",E),function(G){if(F.getAttrib(G,"class")=="mceItemObject"){C=F.getAttrib(G,"classid").toLowerCase().replace(/\s+/g,"");
switch(C){case"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000":F.replace(D._createImg("mceItemFlash",G),G);
break;
case"clsid:166b1bca-3f9c-11cf-8075-444553540000":F.replace(D._createImg("mceItemShockWave",G),G);
break;
case"clsid:6bf52a52-394a-11d3-b153-00c04f79faa6":case"clsid:22d6f312-b0f6-11d0-94ab-0080c74c7e95":case"clsid:05589fa1-c356-11ce-bf01-00aa0055595a":F.replace(D._createImg("mceItemWindowsMedia",G),G);
break;
case"clsid:02bf25d5-8c17-4b23-bc80-d3488abddc6b":F.replace(D._createImg("mceItemQuickTime",G),G);
break;
case"clsid:cfcdaa03-8be4-11cf-b84b-0020afbbccfa":F.replace(D._createImg("mceItemRealMedia",G),G);
break;
default:F.replace(D._createImg("mceItemFlash",G),G)
}return 
}if(F.getAttrib(G,"class")=="mceItemEmbed"){switch(F.getAttrib(G,"type")){case"application/x-shockwave-flash":F.replace(D._createImg("mceItemFlash",G),G);
break;
case"application/x-director":F.replace(D._createImg("mceItemShockWave",G),G);
break;
case"application/x-mplayer2":F.replace(D._createImg("mceItemWindowsMedia",G),G);
break;
case"video/quicktime":F.replace(D._createImg("mceItemQuickTime",G),G);
break;
case"audio/x-pn-realaudio-plugin":F.replace(D._createImg("mceItemRealMedia",G),G);
break;
default:F.replace(D._createImg("mceItemFlash",G),G)
}}})
},_createImg:function(C,H){var B,G=this.editor.dom,F={},E="",D;
D=["id","name","width","height","bgcolor","align","flashvars","src","wmode","allowfullscreen","quality"];
B=G.create("img",{src:this.url+"/img/trans.gif",width:G.getAttrib(H,"width")||100,height:G.getAttrib(H,"height")||100,style:G.getAttrib(H,"style"),"class":C});
A(D,function(I){var J=G.getAttrib(H,I);
if(J){F[I]=J
}});
A(G.select("span",H),function(I){if(G.hasClass(I,"mceItemParam")){F[G.getAttrib(I,"name")]=G.getAttrib(I,"_mce_value")
}});
if(F.movie){F.src=F.movie;
delete F.movie
}H=G.select(".mceItemEmbed",H)[0];
if(H){A(D,function(I){var J=G.getAttrib(H,I);
if(J&&!F[I]){F[I]=J
}})
}delete F.width;
delete F.height;
B.title=this._serialize(F);
return B
},_parse:function(B){return tinymce.util.JSON.parse("{"+B+"}")
},_serialize:function(B){return tinymce.util.JSON.serialize(B).replace(/[{}]/g,"")
}});
tinymce.PluginManager.add("media",tinymce.plugins.MediaPlugin)
})();