(function(F){var G=F.DOM,A=F.dom.Event,C=F.extend,E=F.each,B=F.util.Cookie,D,H=F.explode;
F.ThemeManager.requireLangPack("advanced");
F.create("tinymce.themes.AdvancedTheme",{sizes:[8,10,12,14,18,24,36],controls:{bold:["bold_desc","Bold"],italic:["italic_desc","Italic"],underline:["underline_desc","Underline"],strikethrough:["striketrough_desc","Strikethrough"],justifyleft:["justifyleft_desc","JustifyLeft"],justifycenter:["justifycenter_desc","JustifyCenter"],justifyright:["justifyright_desc","JustifyRight"],justifyfull:["justifyfull_desc","JustifyFull"],bullist:["bullist_desc","InsertUnorderedList"],numlist:["numlist_desc","InsertOrderedList"],outdent:["outdent_desc","Outdent"],indent:["indent_desc","Indent"],cut:["cut_desc","Cut"],copy:["copy_desc","Copy"],paste:["paste_desc","Paste"],undo:["undo_desc","Undo"],redo:["redo_desc","Redo"],link:["link_desc","mceLink"],unlink:["unlink_desc","unlink"],image:["image_desc","mceImage"],cleanup:["cleanup_desc","mceCleanup"],help:["help_desc","mceHelp"],code:["code_desc","mceCodeEditor"],hr:["hr_desc","InsertHorizontalRule"],removeformat:["removeformat_desc","RemoveFormat"],sub:["sub_desc","subscript"],sup:["sup_desc","superscript"],forecolor:["forecolor_desc","ForeColor"],forecolorpicker:["forecolor_desc","mceForeColor"],backcolor:["backcolor_desc","HiliteColor"],backcolorpicker:["backcolor_desc","mceBackColor"],charmap:["charmap_desc","mceCharMap"],visualaid:["visualaid_desc","mceToggleVisualAid"],anchor:["anchor_desc","mceInsertAnchor"],newdocument:["newdocument_desc","mceNewDocument"],blockquote:["blockquote_desc","mceBlockQuote"]},stateControls:["bold","italic","underline","strikethrough","bullist","numlist","justifyleft","justifycenter","justifyright","justifyfull","sub","sup","blockquote"],init:function(L,K){var J=this,I,M,N;
J.editor=L;
J.url=K;
J.onResolveName=new F.util.Dispatcher(this);
J.settings=I=C({theme_advanced_path:true,theme_advanced_toolbar_location:"bottom",theme_advanced_buttons1:"bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect",theme_advanced_buttons2:"bullist,numlist,|,outdent,indent,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code",theme_advanced_buttons3:"hr,removeformat,visualaid,|,sub,sup,|,charmap",theme_advanced_blockformats:"p,address,pre,h1,h2,h3,h4,h5,h6",theme_advanced_toolbar_align:"center",theme_advanced_fonts:"Andale Mono=andale mono,times;Arial=arial,helvetica,sans-serif;Arial Black=arial black,avant garde;Book Antiqua=book antiqua,palatino;Comic Sans MS=comic sans ms,sans-serif;Courier New=courier new,courier;Georgia=georgia,palatino;Helvetica=helvetica;Impact=impact,chicago;Symbol=symbol;Tahoma=tahoma,arial,helvetica,sans-serif;Terminal=terminal,monaco;Times New Roman=times new roman,times;Trebuchet MS=trebuchet ms,geneva;Verdana=verdana,geneva;Webdings=webdings;Wingdings=wingdings,zapf dingbats",theme_advanced_more_colors:1,theme_advanced_row_height:23,theme_advanced_resize_horizontal:1,theme_advanced_resizing_use_cookie:1,theme_advanced_font_sizes:"1,2,3,4,5,6,7",readonly:L.settings.readonly},L.settings);
if(!I.font_size_style_values){I.font_size_style_values="8pt,10pt,12pt,14pt,18pt,24pt,36pt"
}if(F.is(I.theme_advanced_font_sizes,"string")){I.font_size_style_values=F.explode(I.font_size_style_values);
I.font_size_classes=F.explode(I.font_size_classes||"");
N={};
L.settings.theme_advanced_font_sizes=I.theme_advanced_font_sizes;
E(L.getParam("theme_advanced_font_sizes","","hash"),function(O,P){var Q;
if(P==O&&O>=1&&O<=7){P=O+" ("+J.sizes[O-1]+"pt)";
if(L.settings.convert_fonts_to_spans){Q=I.font_size_classes[O-1];
O=I.font_size_style_values[O-1]||(J.sizes[O-1]+"pt")
}}if(/^\s*\./.test(O)){Q=O.replace(/\./g,"")
}N[P]=Q?{"class":Q}:{fontSize:O}
});
I.theme_advanced_font_sizes=N
}if((M=I.theme_advanced_path_location)&&M!="none"){I.theme_advanced_statusbar_location=I.theme_advanced_path_location
}if(I.theme_advanced_statusbar_location=="none"){I.theme_advanced_statusbar_location=0
}L.onInit.add(function(){L.onNodeChange.add(J._nodeChanged,J);
if(L.settings.content_css!==false){L.dom.loadCSS(L.baseURI.toAbsolute("themes/advanced/skins/"+L.settings.skin+"/content.css"))
}});
L.onSetProgressState.add(function(R,T,Q){var P,O=R.id,S;
if(T){J.progressTimer=setTimeout(function(){P=R.getContainer();
P=P.insertBefore(G.create("DIV",{style:"position:relative"}),P.firstChild);
S=G.get(R.id+"_tbl");
G.add(P,"div",{id:O+"_blocker","class":"mceBlocker",style:{width:S.clientWidth+2,height:S.clientHeight+2}});
G.add(P,"div",{id:O+"_progress","class":"mceProgress",style:{left:S.clientWidth/2,top:S.clientHeight/2}})
},Q||0)
}else{G.remove(O+"_blocker");
G.remove(O+"_progress");
clearTimeout(J.progressTimer)
}});
G.loadCSS(I.editor_css?L.documentBaseURI.toAbsolute(I.editor_css):K+"/skins/"+L.settings.skin+"/ui.css");
if(I.skin_variant){G.loadCSS(K+"/skins/"+L.settings.skin+"/ui_"+I.skin_variant+".css")
}},createControl:function(I,L){var K,J;
if(J=L.createControl(I)){return J
}switch(I){case"styleselect":return this._createStyleSelect();
case"formatselect":return this._createBlockFormats();
case"fontselect":return this._createFontSelect();
case"fontsizeselect":return this._createFontSizeSelect();
case"forecolor":return this._createForeColorMenu();
case"backcolor":return this._createBackColorMenu()
}if((K=this.controls[I])){return L.createButton(I,{title:"advanced."+K[0],cmd:K[1],ui:K[2],value:K[3]})
}},execCommand:function(J,K,I){var L=this["_"+J];
if(L){L.call(this,K,I);
return true
}return false
},_importClasses:function(J){var K=this.editor,I=K.controlManager.get("styleselect");
if(I.getLength()==0){E(K.dom.getClasses(),function(L){I.add(L["class"],L["class"])
})
}},_createStyleSelect:function(I){var L=this,M=L.editor,K=M.controlManager,J=K.createListBox("styleselect",{title:"advanced.style_select",onselect:function(N){if(J.selectedValue===N){M.execCommand("mceSetStyleInfo",0,{command:"removeformat"});
J.select();
return false
}else{M.execCommand("mceSetCSSClass",0,N)
}}});
if(J){E(M.getParam("theme_advanced_styles","","hash"),function(N,O){if(N){J.add(L.editor.translate(O),N)
}});
J.onPostRender.add(function(O,N){if(!J.NativeListBox){A.add(N.id+"_text","focus",L._importClasses,L);
A.add(N.id+"_text","mousedown",L._importClasses,L);
A.add(N.id+"_open","focus",L._importClasses,L);
A.add(N.id+"_open","mousedown",L._importClasses,L)
}else{A.add(N.id,"focus",L._importClasses,L)
}})
}return J
},_createFontSelect:function(){var I,J=this,K=J.editor;
I=K.controlManager.createListBox("fontselect",{title:"advanced.fontdefault",cmd:"FontName"});
if(I){E(K.getParam("theme_advanced_fonts",J.settings.theme_advanced_fonts,"hash"),function(L,M){I.add(K.translate(M),L,{style:L.indexOf("dings")==-1?"font-family:"+L:""})
})
}return I
},_createFontSizeSelect:function(){var I=this,K=I.editor,M,J=0,L=[];
M=K.controlManager.createListBox("fontsizeselect",{title:"advanced.font_size",onselect:function(N){if(N.fontSize){K.execCommand("FontSize",false,N.fontSize)
}else{E(I.settings.theme_advanced_font_sizes,function(O,P){if(O["class"]){L.push(O["class"])
}});
K.editorCommands._applyInlineStyle("span",{"class":N["class"]},{check_classes:L})
}}});
if(M){E(I.settings.theme_advanced_font_sizes,function(P,N){var O=P.fontSize;
if(O>=1&&O<=7){O=I.sizes[parseInt(O)-1]+"pt"
}M.add(N,P,{style:"font-size:"+O,"class":"mceFontSize"+(J++)+(" "+(P["class"]||""))})
})
}return M
},_createBlockFormats:function(){var I,K={p:"advanced.paragraph",address:"advanced.address",pre:"advanced.pre",h1:"advanced.h1",h2:"advanced.h2",h3:"advanced.h3",h4:"advanced.h4",h5:"advanced.h5",h6:"advanced.h6",div:"advanced.div",blockquote:"advanced.blockquote",code:"advanced.code",dt:"advanced.dt",dd:"advanced.dd",samp:"advanced.samp"},J=this;
I=J.editor.controlManager.createListBox("formatselect",{title:"advanced.block",cmd:"FormatBlock"});
if(I){E(J.editor.getParam("theme_advanced_blockformats",J.settings.theme_advanced_blockformats,"hash"),function(L,M){I.add(J.editor.translate(M!=L?M:K[L]),L,{"class":"mce_formatPreview mce_"+L})
})
}return I
},_createForeColorMenu:function(){var I,L=this,K=L.settings,J={},M;
if(K.theme_advanced_more_colors){J.more_colors_func=function(){L._mceColorPicker(0,{color:I.value,func:function(N){I.setColor(N)
}})
}
}if(M=K.theme_advanced_text_colors){J.colors=M
}if(K.theme_advanced_default_foreground_color){J.default_color=K.theme_advanced_default_foreground_color
}J.title="advanced.forecolor_desc";
J.cmd="ForeColor";
J.scope=this;
I=L.editor.controlManager.createColorSplitButton("forecolor",J);
return I
},_createBackColorMenu:function(){var I,L=this,K=L.settings,J={},M;
if(K.theme_advanced_more_colors){J.more_colors_func=function(){L._mceColorPicker(0,{color:I.value,func:function(N){I.setColor(N)
}})
}
}if(M=K.theme_advanced_background_colors){J.colors=M
}if(K.theme_advanced_default_background_color){J.default_color=K.theme_advanced_default_background_color
}J.title="advanced.backcolor_desc";
J.cmd="HiliteColor";
J.scope=this;
I=L.editor.controlManager.createColorSplitButton("backcolor",J);
return I
},renderUI:function(M){var K,L,J,Q=this,I=Q.editor,P=Q.settings,R,N,O;
K=N=G.create("span",{id:I.id+"_parent","class":"mceEditor "+I.settings.skin+"Skin"+(P.skin_variant?" "+I.settings.skin+"Skin"+Q._ufirst(P.skin_variant):"")});
if(!G.boxModel){K=G.add(K,"div",{"class":"mceOldBoxModel"})
}K=R=G.add(K,"table",{id:I.id+"_tbl","class":"mceLayout",cellSpacing:0,cellPadding:0});
K=J=G.add(K,"tbody");
switch((P.theme_advanced_layout_manager||"").toLowerCase()){case"rowlayout":L=Q._rowLayout(P,J,M);
break;
case"customlayout":L=I.execCallback("theme_advanced_custom_layout",P,J,M,N);
break;
default:L=Q._simpleLayout(P,J,M,N)
}K=M.targetNode;
O=G.stdMode?R.getElementsByTagName("tr"):R.rows;
G.addClass(O[0],"mceFirst");
G.addClass(O[O.length-1],"mceLast");
E(G.select("tr",J),function(S){G.addClass(S.firstChild,"mceFirst");
G.addClass(S.childNodes[S.childNodes.length-1],"mceLast")
});
if(G.get(P.theme_advanced_toolbar_container)){G.get(P.theme_advanced_toolbar_container).appendChild(N)
}else{G.insertAfter(N,K)
}A.add(I.id+"_path_row","click",function(S){S=S.target;
if(S.nodeName=="A"){Q._sel(S.className.replace(/^.*mcePath_([0-9]+).*$/,"$1"));
return A.cancel(S)
}});
if(!I.getParam("accessibility_focus")){A.add(G.add(N,"a",{href:"#"},"<!-- IE -->"),"focus",function(){tinyMCE.get(I.id).focus()
})
}if(P.theme_advanced_toolbar_location=="external"){M.deltaHeight=0
}Q.deltaHeight=M.deltaHeight;
M.targetNode=null;
return{iframeContainer:L,editorContainer:I.id+"_parent",sizeContainer:R,deltaHeight:M.deltaHeight}
},getInfo:function(){return{longname:"Advanced theme",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",version:F.majorVersion+"."+F.minorVersion}
},resizeBy:function(K,J){var I=G.get(this.editor.id+"_tbl");
this.resizeTo(I.clientWidth+K,I.clientHeight+J)
},resizeTo:function(M,J){var L=this.editor,K=L.settings,O=G.get(L.id+"_tbl"),N=G.get(L.id+"_ifr"),I;
M=Math.max(K.theme_advanced_resizing_min_width||100,M);
J=Math.max(K.theme_advanced_resizing_min_height||100,J);
M=Math.min(K.theme_advanced_resizing_max_width||65535,M);
J=Math.min(K.theme_advanced_resizing_max_height||65535,J);
I=O.clientHeight-N.clientHeight;
G.setStyle(N,"height",J-I);
G.setStyles(O,{width:M,height:J})
},destroy:function(){var I=this.editor.id;
A.clear(I+"_resize");
A.clear(I+"_path_row");
A.clear(I+"_external_close")
},_simpleLayout:function(P,I,M,O){var Q=this,T=Q.editor,S=P.theme_advanced_toolbar_location,K=P.theme_advanced_statusbar_location,L,N,J,R;
if(P.readonly){L=G.add(I,"tr");
L=N=G.add(L,"td",{"class":"mceIframeContainer"});
return N
}if(S=="top"){Q._addToolbars(I,M)
}if(S=="external"){L=R=G.create("div",{style:"position:relative"});
L=G.add(L,"div",{id:T.id+"_external","class":"mceExternalToolbar"});
G.add(L,"a",{id:T.id+"_external_close",href:"javascript:;","class":"mceExternalClose"});
L=G.add(L,"table",{id:T.id+"_tblext",cellSpacing:0,cellPadding:0});
J=G.add(L,"tbody");
if(O.firstChild.className=="mceOldBoxModel"){O.firstChild.appendChild(R)
}else{O.insertBefore(R,O.firstChild)
}Q._addToolbars(J,M);
T.onMouseUp.add(function(){var U=G.get(T.id+"_external");
G.show(U);
G.hide(D);
var V=A.add(T.id+"_external_close","click",function(){G.hide(T.id+"_external");
A.remove(T.id+"_external_close","click",V)
});
G.show(U);
G.setStyle(U,"top",0-G.getRect(T.id+"_tblext").h-1);
G.hide(U);
G.show(U);
U.style.filter="";
D=T.id+"_external";
U=null
})
}if(K=="top"){Q._addStatusBar(I,M)
}if(!P.theme_advanced_toolbar_container){L=G.add(I,"tr");
L=N=G.add(L,"td",{"class":"mceIframeContainer"})
}if(S=="bottom"){Q._addToolbars(I,M)
}if(K=="bottom"){Q._addStatusBar(I,M)
}return N
},_rowLayout:function(R,L,N){var S=this,K=S.editor,T,Q,P=K.controlManager,M,O,I,J;
T=R.theme_advanced_containers_default_class||"";
Q=R.theme_advanced_containers_default_align||"center";
E(H(R.theme_advanced_containers||""),function(U,V){var W=R["theme_advanced_container_"+U]||"";
switch(W.toLowerCase()){case"mceeditor":M=G.add(L,"tr");
M=O=G.add(M,"td",{"class":"mceIframeContainer"});
break;
case"mceelementpath":S._addStatusBar(L,N);
break;
default:J=(R["theme_advanced_container_"+U+"_align"]||Q).toLowerCase();
J="mce"+S._ufirst(J);
M=G.add(G.add(L,"tr"),"td",{"class":"mceToolbar "+(R["theme_advanced_container_"+U+"_class"]||T)+" "+J||Q});
I=P.createToolbar("toolbar"+V);
S._addControls(W,I);
G.setHTML(M,I.renderHTML());
N.deltaHeight-=R.theme_advanced_row_height
}});
return O
},_addControls:function(L,M){var K=this,J=K.settings,I,N=K.editor.controlManager;
if(J.theme_advanced_disable&&!K._disabled){I={};
E(H(J.theme_advanced_disable),function(O){I[O]=1
});
K._disabled=I
}else{I=K._disabled
}E(H(L),function(O){var P;
if(I&&I[O]){return 
}if(O=="tablecontrols"){E(["table","|","row_props","cell_props","|","row_before","row_after","delete_row","|","col_before","col_after","delete_col","|","split_cells","merge_cells"],function(Q){Q=K.createControl(Q,N);
if(Q){M.add(Q)
}});
return 
}P=K.createControl(O,N);
if(P){M.add(P)
}})
},_addToolbars:function(T,O){var Q=this,K,M,I=Q.editor,L=Q.settings,R,P=I.controlManager,U,N,J=[],S;
S=L.theme_advanced_toolbar_align.toLowerCase();
S="mce"+Q._ufirst(S);
N=G.add(G.add(T,"tr"),"td",{"class":"mceToolbar "+S});
if(!I.getParam("accessibility_focus")){J.push(G.createHTML("a",{href:"#",onfocus:"tinyMCE.get('"+I.id+"').focus();"},"<!-- IE -->"))
}J.push(G.createHTML("a",{href:"#",accesskey:"q",title:I.getLang("advanced.toolbar_focus")},"<!-- IE -->"));
for(K=1;
(R=L["theme_advanced_buttons"+K]);
K++){M=P.createToolbar("toolbar"+K,{"class":"mceToolbarRow"+K});
if(L["theme_advanced_buttons"+K+"_add"]){R+=","+L["theme_advanced_buttons"+K+"_add"]
}if(L["theme_advanced_buttons"+K+"_add_before"]){R=L["theme_advanced_buttons"+K+"_add_before"]+","+R
}Q._addControls(R,M);
J.push(M.renderHTML());
O.deltaHeight-=L.theme_advanced_row_height
}J.push(G.createHTML("a",{href:"#",accesskey:"z",title:I.getLang("advanced.toolbar_focus"),onfocus:"tinyMCE.getInstanceById('"+I.id+"').focus();"},"<!-- IE -->"));
G.setHTML(N,J.join(""))
},_addStatusBar:function(K,N){var M,Q=this,J=Q.editor,P=Q.settings,O,I,R,L;
M=G.add(K,"tr");
M=L=G.add(M,"td",{"class":"mceStatusbar"});
M=G.add(M,"div",{id:J.id+"_path_row"},P.theme_advanced_path?J.translate("advanced.path")+": ":"&#160;");
G.add(M,"a",{href:"#",accesskey:"x"});
if(P.theme_advanced_resizing){G.add(L,"a",{id:J.id+"_resize",href:"javascript:;",onclick:"return false;","class":"mceResize"});
if(P.theme_advanced_resizing_use_cookie){J.onPostRender.add(function(){var T=B.getHash("TinyMCE_"+J.id+"_size"),S=G.get(J.id+"_tbl");
if(!T){return 
}if(P.theme_advanced_resize_horizontal){S.style.width=Math.max(10,T.cw)+"px"
}S.style.height=Math.max(10,T.ch)+"px";
G.get(J.id+"_ifr").style.height=Math.max(10,parseInt(T.ch)+Q.deltaHeight)+"px"
})
}J.onPostRender.add(function(){A.add(J.id+"_resize","mousedown",function(S){var X,T,W,U,Y,V;
X=G.get(J.id+"_tbl");
W=X.clientWidth;
U=X.clientHeight;
miw=P.theme_advanced_resizing_min_width||100;
mih=P.theme_advanced_resizing_min_height||100;
maw=P.theme_advanced_resizing_max_width||65535;
mah=P.theme_advanced_resizing_max_height||65535;
T=G.add(G.get(J.id+"_parent"),"div",{"class":"mcePlaceHolder"});
G.setStyles(T,{width:W,height:U});
G.hide(X);
G.show(T);
O={x:S.screenX,y:S.screenY,w:W,h:U,dx:null,dy:null};
I=A.add(G.doc,"mousemove",function(b){var a,Z;
O.dx=b.screenX-O.x;
O.dy=b.screenY-O.y;
a=Math.max(miw,O.w+O.dx);
Z=Math.max(mih,O.h+O.dy);
a=Math.min(maw,a);
Z=Math.min(mah,Z);
if(P.theme_advanced_resize_horizontal){T.style.width=a+"px"
}T.style.height=Z+"px";
return A.cancel(b)
});
R=A.add(G.doc,"mouseup",function(a){var Z;
A.remove(G.doc,"mousemove",I);
A.remove(G.doc,"mouseup",R);
X.style.display="";
G.remove(T);
if(O.dx===null){return 
}Z=G.get(J.id+"_ifr");
if(P.theme_advanced_resize_horizontal){X.style.width=Math.max(10,O.w+O.dx)+"px"
}X.style.height=Math.max(10,O.h+O.dy)+"px";
Z.style.height=Math.max(10,Z.clientHeight+O.dy)+"px";
if(P.theme_advanced_resizing_use_cookie){B.setHash("TinyMCE_"+J.id+"_size",{cw:O.w+O.dx,ch:O.h+O.dy})
}});
return A.cancel(S)
})
})
}N.deltaHeight-=21;
M=K=null
},_nodeChanged:function(M,U,N,J){var R=this,P,I=0,S,L,Q=R.settings,T,O,K;
if(Q.readonly){return 
}F.each(R.stateControls,function(V){U.setActive(V,M.queryCommandState(R.controls[V][1]))
});
U.setActive("visualaid",M.hasVisual);
U.setDisabled("undo",!M.undoManager.hasUndo()&&!M.typing);
U.setDisabled("redo",!M.undoManager.hasRedo());
U.setDisabled("outdent",!M.queryCommandState("Outdent"));
P=G.getParent(N,"A");
if(L=U.get("link")){if(!P||!P.name){L.setDisabled(!P&&J);
L.setActive(!!P)
}}if(L=U.get("unlink")){L.setDisabled(!P&&J);
L.setActive(!!P&&!P.name)
}if(L=U.get("anchor")){L.setActive(!!P&&P.name);
if(F.isWebKit){P=G.getParent(N,"IMG");
L.setActive(!!P&&G.getAttrib(P,"mce_name")=="a")
}}P=G.getParent(N,"IMG");
if(L=U.get("image")){L.setActive(!!P&&N.className.indexOf("mceItem")==-1)
}if(L=U.get("styleselect")){if(N.className){R._importClasses();
L.select(N.className)
}else{L.select()
}}if(L=U.get("formatselect")){P=G.getParent(N,G.isBlock);
if(P){L.select(P.nodeName.toLowerCase())
}}if(M.settings.convert_fonts_to_spans){M.dom.getParent(N,function(V){if(V.nodeName==="SPAN"){if(!T&&V.className){T=V.className
}if(!O&&V.style.fontSize){O=V.style.fontSize
}if(!K&&V.style.fontFamily){K=V.style.fontFamily.replace(/[\"\']+/g,"").replace(/^([^,]+).*/,"$1").toLowerCase()
}}return false
});
if(L=U.get("fontselect")){L.select(function(V){return V.replace(/^([^,]+).*/,"$1").toLowerCase()==K
})
}if(L=U.get("fontsizeselect")){L.select(function(V){if(V.fontSize&&V.fontSize===O){return true
}if(V["class"]&&V["class"]===T){return true
}})
}}else{if(L=U.get("fontselect")){L.select(M.queryCommandValue("FontName"))
}if(L=U.get("fontsizeselect")){S=M.queryCommandValue("FontSize");
L.select(function(V){return V.fontSize==S
})
}}if(Q.theme_advanced_path&&Q.theme_advanced_statusbar_location){P=G.get(M.id+"_path")||G.add(M.id+"_path_row","span",{id:M.id+"_path"});
G.setHTML(P,"");
M.dom.getParent(N,function(V){var Z=V.nodeName.toLowerCase(),Y,W,X="";
if(V.nodeType!=1||V.nodeName==="BR"||(G.hasClass(V,"mceItemHidden")||G.hasClass(V,"mceItemRemoved"))){return 
}if(S=G.getAttrib(V,"mce_name")){Z=S
}if(F.isIE&&V.scopeName!=="HTML"){Z=V.scopeName+":"+Z
}Z=Z.replace(/mce\:/g,"");
switch(Z){case"b":Z="strong";
break;
case"i":Z="em";
break;
case"img":if(S=G.getAttrib(V,"src")){X+="src: "+S+" "
}break;
case"a":if(S=G.getAttrib(V,"name")){X+="name: "+S+" ";
Z+="#"+S
}if(S=G.getAttrib(V,"href")){X+="href: "+S+" "
}break;
case"font":if(Q.convert_fonts_to_spans){Z="span"
}if(S=G.getAttrib(V,"face")){X+="font: "+S+" "
}if(S=G.getAttrib(V,"size")){X+="size: "+S+" "
}if(S=G.getAttrib(V,"color")){X+="color: "+S+" "
}break;
case"span":if(S=G.getAttrib(V,"style")){X+="style: "+S+" "
}break
}if(S=G.getAttrib(V,"id")){X+="id: "+S+" "
}if(S=V.className){S=S.replace(/(webkit-[\w\-]+|Apple-[\w\-]+|mceItem\w+|mceVisualAid)/g,"");
if(S&&S.indexOf("mceItem")==-1){X+="class: "+S+" ";
if(G.isBlock(V)||Z=="img"||Z=="span"){Z+="."+S
}}}Z=Z.replace(/(html:)/g,"");
Z={name:Z,node:V,title:X};
R.onResolveName.dispatch(R,Z);
X=Z.title;
Z=Z.name;
W=G.create("a",{href:"javascript:;",onmousedown:"return false;",title:X,"class":"mcePath_"+(I++)},Z);
if(P.hasChildNodes()){P.insertBefore(G.doc.createTextNode(" \u00bb "),P.firstChild);
P.insertBefore(W,P.firstChild)
}else{P.appendChild(W)
}},M.getBody())
}},_sel:function(I){this.editor.execCommand("mceSelectNodeDepth",false,I)
},_mceInsertAnchor:function(I,J){var K=this.editor;
K.windowManager.open({url:F.baseURL+"/themes/advanced/anchor.htm",width:320+parseInt(K.getLang("advanced.anchor_delta_width",0)),height:90+parseInt(K.getLang("advanced.anchor_delta_height",0)),inline:true},{theme_url:this.url})
},_mceCharMap:function(){var I=this.editor;
I.windowManager.open({url:F.baseURL+"/themes/advanced/charmap.htm",width:550+parseInt(I.getLang("advanced.charmap_delta_width",0)),height:250+parseInt(I.getLang("advanced.charmap_delta_height",0)),inline:true},{theme_url:this.url})
},_mceHelp:function(){var I=this.editor;
I.windowManager.open({url:F.baseURL+"/themes/advanced/about.htm",width:480,height:380,inline:true},{theme_url:this.url})
},_mceColorPicker:function(I,J){var K=this.editor;
J=J||{};
K.windowManager.open({url:F.baseURL+"/themes/advanced/color_picker.htm",width:375+parseInt(K.getLang("advanced.colorpicker_delta_width",0)),height:250+parseInt(K.getLang("advanced.colorpicker_delta_height",0)),close_previous:false,inline:true},{input_color:J.color,func:J.func,theme_url:this.url})
},_mceCodeEditor:function(J,I){var K=this.editor;
K.windowManager.open({url:F.baseURL+"/themes/advanced/source_editor.htm",width:parseInt(K.getParam("theme_advanced_source_editor_width",720)),height:parseInt(K.getParam("theme_advanced_source_editor_height",580)),inline:true,resizable:true,maximizable:true},{theme_url:this.url})
},_mceImage:function(J,I){var K=this.editor;
if(K.dom.getAttrib(K.selection.getNode(),"class").indexOf("mceItem")!=-1){return 
}K.windowManager.open({url:F.baseURL+"/themes/advanced/image.htm",width:355+parseInt(K.getLang("advanced.image_delta_width",0)),height:275+parseInt(K.getLang("advanced.image_delta_height",0)),inline:true},{theme_url:this.url})
},_mceLink:function(J,I){var K=this.editor;
K.windowManager.open({url:F.baseURL+"/themes/advanced/link.htm",width:310+parseInt(K.getLang("advanced.link_delta_width",0)),height:200+parseInt(K.getLang("advanced.link_delta_height",0)),inline:true},{theme_url:this.url})
},_mceNewDocument:function(){var I=this.editor;
I.windowManager.confirm("advanced.newdocument",function(J){if(J){I.execCommand("mceSetContent",false,"")
}})
},_mceForeColor:function(){var I=this;
this._mceColorPicker(0,{color:I.fgColor,func:function(J){I.fgColor=J;
I.editor.execCommand("ForeColor",false,J)
}})
},_mceBackColor:function(){var I=this;
this._mceColorPicker(0,{color:I.bgColor,func:function(J){I.bgColor=J;
I.editor.execCommand("HiliteColor",false,J)
}})
},_ufirst:function(I){return I.substring(0,1).toUpperCase()+I.substring(1)
}});
F.ThemeManager.add("advanced",F.themes.AdvancedTheme)
}(tinymce));