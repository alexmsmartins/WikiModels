(function(E){var D=E.DOM,B=E.dom.Event,H=E.extend,F=E.each,A=E.util.Cookie,G,C=E.explode;
E.ThemeManager.requireLangPack("advanced");
E.create("tinymce.themes.AdvancedTheme",{sizes:[8,10,12,14,18,24,36],controls:{bold:["bold_desc","Bold"],italic:["italic_desc","Italic"],underline:["underline_desc","Underline"],strikethrough:["striketrough_desc","Strikethrough"],justifyleft:["justifyleft_desc","JustifyLeft"],justifycenter:["justifycenter_desc","JustifyCenter"],justifyright:["justifyright_desc","JustifyRight"],justifyfull:["justifyfull_desc","JustifyFull"],bullist:["bullist_desc","InsertUnorderedList"],numlist:["numlist_desc","InsertOrderedList"],outdent:["outdent_desc","Outdent"],indent:["indent_desc","Indent"],cut:["cut_desc","Cut"],copy:["copy_desc","Copy"],paste:["paste_desc","Paste"],undo:["undo_desc","Undo"],redo:["redo_desc","Redo"],link:["link_desc","mceLink"],unlink:["unlink_desc","unlink"],image:["image_desc","mceImage"],cleanup:["cleanup_desc","mceCleanup"],help:["help_desc","mceHelp"],code:["code_desc","mceCodeEditor"],hr:["hr_desc","InsertHorizontalRule"],removeformat:["removeformat_desc","RemoveFormat"],sub:["sub_desc","subscript"],sup:["sup_desc","superscript"],forecolor:["forecolor_desc","ForeColor"],forecolorpicker:["forecolor_desc","mceForeColor"],backcolor:["backcolor_desc","HiliteColor"],backcolorpicker:["backcolor_desc","mceBackColor"],charmap:["charmap_desc","mceCharMap"],visualaid:["visualaid_desc","mceToggleVisualAid"],anchor:["anchor_desc","mceInsertAnchor"],newdocument:["newdocument_desc","mceNewDocument"],blockquote:["blockquote_desc","mceBlockQuote"]},stateControls:["bold","italic","underline","strikethrough","bullist","numlist","justifyleft","justifycenter","justifyright","justifyfull","sub","sup","blockquote"],init:function(J,K){var L=this,M,I,N;
L.editor=J;
L.url=K;
L.onResolveName=new E.util.Dispatcher(this);
L.settings=M=H({theme_advanced_path:true,theme_advanced_toolbar_location:"bottom",theme_advanced_buttons1:"bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect",theme_advanced_buttons2:"bullist,numlist,|,outdent,indent,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code",theme_advanced_buttons3:"hr,removeformat,visualaid,|,sub,sup,|,charmap",theme_advanced_blockformats:"p,address,pre,h1,h2,h3,h4,h5,h6",theme_advanced_toolbar_align:"center",theme_advanced_fonts:"Andale Mono=andale mono,times;Arial=arial,helvetica,sans-serif;Arial Black=arial black,avant garde;Book Antiqua=book antiqua,palatino;Comic Sans MS=comic sans ms,sans-serif;Courier New=courier new,courier;Georgia=georgia,palatino;Helvetica=helvetica;Impact=impact,chicago;Symbol=symbol;Tahoma=tahoma,arial,helvetica,sans-serif;Terminal=terminal,monaco;Times New Roman=times new roman,times;Trebuchet MS=trebuchet ms,geneva;Verdana=verdana,geneva;Webdings=webdings;Wingdings=wingdings,zapf dingbats",theme_advanced_more_colors:1,theme_advanced_row_height:23,theme_advanced_resize_horizontal:1,theme_advanced_resizing_use_cookie:1,theme_advanced_font_sizes:"1,2,3,4,5,6,7",readonly:J.settings.readonly},J.settings);
if(!M.font_size_style_values){M.font_size_style_values="8pt,10pt,12pt,14pt,18pt,24pt,36pt"
}if(E.is(M.theme_advanced_font_sizes,"string")){M.font_size_style_values=E.explode(M.font_size_style_values);
M.font_size_classes=E.explode(M.font_size_classes||"");
N={};
J.settings.theme_advanced_font_sizes=M.theme_advanced_font_sizes;
F(J.getParam("theme_advanced_font_sizes","","hash"),function(Q,P){var O;
if(P==Q&&Q>=1&&Q<=7){P=Q+" ("+L.sizes[Q-1]+"pt)";
if(J.settings.convert_fonts_to_spans){O=M.font_size_classes[Q-1];
Q=M.font_size_style_values[Q-1]||(L.sizes[Q-1]+"pt")
}}if(/^\s*\./.test(Q)){O=Q.replace(/\./g,"")
}N[P]=O?{"class":O}:{fontSize:Q}
});
M.theme_advanced_font_sizes=N
}if((I=M.theme_advanced_path_location)&&I!="none"){M.theme_advanced_statusbar_location=M.theme_advanced_path_location
}if(M.theme_advanced_statusbar_location=="none"){M.theme_advanced_statusbar_location=0
}J.onInit.add(function(){J.onNodeChange.add(L._nodeChanged,L);
if(J.settings.content_css!==false){J.dom.loadCSS(J.baseURI.toAbsolute("themes/advanced/skins/"+J.settings.skin+"/content.css"))
}});
J.onSetProgressState.add(function(Q,O,R){var S,T=Q.id,P;
if(O){L.progressTimer=setTimeout(function(){S=Q.getContainer();
S=S.insertBefore(D.create("DIV",{style:"position:relative"}),S.firstChild);
P=D.get(Q.id+"_tbl");
D.add(S,"div",{id:T+"_blocker","class":"mceBlocker",style:{width:P.clientWidth+2,height:P.clientHeight+2}});
D.add(S,"div",{id:T+"_progress","class":"mceProgress",style:{left:P.clientWidth/2,top:P.clientHeight/2}})
},R||0)
}else{D.remove(T+"_blocker");
D.remove(T+"_progress");
clearTimeout(L.progressTimer)
}});
D.loadCSS(M.editor_css?J.documentBaseURI.toAbsolute(M.editor_css):K+"/skins/"+J.settings.skin+"/ui.css");
if(M.skin_variant){D.loadCSS(K+"/skins/"+J.settings.skin+"/ui_"+M.skin_variant+".css")
}},createControl:function(L,I){var J,K;
if(K=I.createControl(L)){return K
}switch(L){case"styleselect":return this._createStyleSelect();
case"formatselect":return this._createBlockFormats();
case"fontselect":return this._createFontSelect();
case"fontsizeselect":return this._createFontSizeSelect();
case"forecolor":return this._createForeColorMenu();
case"backcolor":return this._createBackColorMenu()
}if((J=this.controls[L])){return I.createButton(L,{title:"advanced."+J[0],cmd:J[1],ui:J[2],value:J[3]})
}},execCommand:function(K,J,L){var I=this["_"+K];
if(I){I.call(this,J,L);
return true
}return false
},_importClasses:function(J){var I=this.editor,K=I.controlManager.get("styleselect");
if(K.getLength()==0){F(I.dom.getClasses(),function(L){K.add(L["class"],L["class"])
})
}},_createStyleSelect:function(M){var J=this,I=J.editor,K=I.controlManager,L=K.createListBox("styleselect",{title:"advanced.style_select",onselect:function(N){if(L.selectedValue===N){I.execCommand("mceSetStyleInfo",0,{command:"removeformat"});
L.select();
return false
}else{I.execCommand("mceSetCSSClass",0,N)
}}});
if(L){F(I.getParam("theme_advanced_styles","","hash"),function(O,N){if(O){L.add(J.editor.translate(N),O)
}});
L.onPostRender.add(function(N,O){if(!L.NativeListBox){B.add(O.id+"_text","focus",J._importClasses,J);
B.add(O.id+"_text","mousedown",J._importClasses,J);
B.add(O.id+"_open","focus",J._importClasses,J);
B.add(O.id+"_open","mousedown",J._importClasses,J)
}else{B.add(O.id,"focus",J._importClasses,J)
}})
}return L
},_createFontSelect:function(){var K,J=this,I=J.editor;
K=I.controlManager.createListBox("fontselect",{title:"advanced.fontdefault",cmd:"FontName"});
if(K){F(I.getParam("theme_advanced_fonts",J.settings.theme_advanced_fonts,"hash"),function(M,L){K.add(I.translate(L),M,{style:M.indexOf("dings")==-1?"font-family:"+M:""})
})
}return K
},_createFontSizeSelect:function(){var L=this,J=L.editor,M,K=0,I=[];
M=J.controlManager.createListBox("fontsizeselect",{title:"advanced.font_size",onselect:function(N){if(N.fontSize){J.execCommand("FontSize",false,N.fontSize)
}else{F(L.settings.theme_advanced_font_sizes,function(P,O){if(P["class"]){I.push(P["class"])
}});
J.editorCommands._applyInlineStyle("span",{"class":N["class"]},{check_classes:I})
}}});
if(M){F(L.settings.theme_advanced_font_sizes,function(O,N){var P=O.fontSize;
if(P>=1&&P<=7){P=L.sizes[parseInt(P)-1]+"pt"
}M.add(N,O,{style:"font-size:"+P,"class":"mceFontSize"+(K++)+(" "+(O["class"]||""))})
})
}return M
},_createBlockFormats:function(){var K,I={p:"advanced.paragraph",address:"advanced.address",pre:"advanced.pre",h1:"advanced.h1",h2:"advanced.h2",h3:"advanced.h3",h4:"advanced.h4",h5:"advanced.h5",h6:"advanced.h6",div:"advanced.div",blockquote:"advanced.blockquote",code:"advanced.code",dt:"advanced.dt",dd:"advanced.dd",samp:"advanced.samp"},J=this;
K=J.editor.controlManager.createListBox("formatselect",{title:"advanced.block",cmd:"FormatBlock"});
if(K){F(J.editor.getParam("theme_advanced_blockformats",J.settings.theme_advanced_blockformats,"hash"),function(M,L){K.add(J.editor.translate(L!=M?L:I[M]),M,{"class":"mce_formatPreview mce_"+M})
})
}return K
},_createForeColorMenu:function(){var M,J=this,K=J.settings,L={},I;
if(K.theme_advanced_more_colors){L.more_colors_func=function(){J._mceColorPicker(0,{color:M.value,func:function(N){M.setColor(N)
}})
}
}if(I=K.theme_advanced_text_colors){L.colors=I
}if(K.theme_advanced_default_foreground_color){L.default_color=K.theme_advanced_default_foreground_color
}L.title="advanced.forecolor_desc";
L.cmd="ForeColor";
L.scope=this;
M=J.editor.controlManager.createColorSplitButton("forecolor",L);
return M
},_createBackColorMenu:function(){var M,J=this,K=J.settings,L={},I;
if(K.theme_advanced_more_colors){L.more_colors_func=function(){J._mceColorPicker(0,{color:M.value,func:function(N){M.setColor(N)
}})
}
}if(I=K.theme_advanced_background_colors){L.colors=I
}if(K.theme_advanced_default_background_color){L.default_color=K.theme_advanced_default_background_color
}L.title="advanced.backcolor_desc";
L.cmd="HiliteColor";
L.scope=this;
M=J.editor.controlManager.createColorSplitButton("backcolor",L);
return M
},renderUI:function(K){var M,L,N,Q=this,O=Q.editor,R=Q.settings,P,J,I;
M=J=D.create("span",{id:O.id+"_parent","class":"mceEditor "+O.settings.skin+"Skin"+(R.skin_variant?" "+O.settings.skin+"Skin"+Q._ufirst(R.skin_variant):"")});
if(!D.boxModel){M=D.add(M,"div",{"class":"mceOldBoxModel"})
}M=P=D.add(M,"table",{id:O.id+"_tbl","class":"mceLayout",cellSpacing:0,cellPadding:0});
M=N=D.add(M,"tbody");
switch((R.theme_advanced_layout_manager||"").toLowerCase()){case"rowlayout":L=Q._rowLayout(R,N,K);
break;
case"customlayout":L=O.execCallback("theme_advanced_custom_layout",R,N,K,J);
break;
default:L=Q._simpleLayout(R,N,K,J)
}M=K.targetNode;
I=D.stdMode?P.getElementsByTagName("tr"):P.rows;
D.addClass(I[0],"mceFirst");
D.addClass(I[I.length-1],"mceLast");
F(D.select("tr",N),function(S){D.addClass(S.firstChild,"mceFirst");
D.addClass(S.childNodes[S.childNodes.length-1],"mceLast")
});
if(D.get(R.theme_advanced_toolbar_container)){D.get(R.theme_advanced_toolbar_container).appendChild(J)
}else{D.insertAfter(J,M)
}B.add(O.id+"_path_row","click",function(S){S=S.target;
if(S.nodeName=="A"){Q._sel(S.className.replace(/^.*mcePath_([0-9]+).*$/,"$1"));
return B.cancel(S)
}});
if(!O.getParam("accessibility_focus")){B.add(D.add(J,"a",{href:"#"},"<!-- IE -->"),"focus",function(){tinyMCE.get(O.id).focus()
})
}if(R.theme_advanced_toolbar_location=="external"){K.deltaHeight=0
}Q.deltaHeight=K.deltaHeight;
K.targetNode=null;
return{iframeContainer:L,editorContainer:O.id+"_parent",sizeContainer:P,deltaHeight:K.deltaHeight}
},getInfo:function(){return{longname:"Advanced theme",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",version:E.majorVersion+"."+E.minorVersion}
},resizeBy:function(I,J){var K=D.get(this.editor.id+"_tbl");
this.resizeTo(K.clientWidth+I,K.clientHeight+J)
},resizeTo:function(I,L){var J=this.editor,K=J.settings,N=D.get(J.id+"_tbl"),O=D.get(J.id+"_ifr"),M;
I=Math.max(K.theme_advanced_resizing_min_width||100,I);
L=Math.max(K.theme_advanced_resizing_min_height||100,L);
I=Math.min(K.theme_advanced_resizing_max_width||65535,I);
L=Math.min(K.theme_advanced_resizing_max_height||65535,L);
M=N.clientHeight-O.clientHeight;
D.setStyle(O,"height",L-M);
D.setStyles(N,{width:I,height:L})
},destroy:function(){var I=this.editor.id;
B.clear(I+"_resize");
B.clear(I+"_path_row");
B.clear(I+"_external_close")
},_simpleLayout:function(T,O,K,I){var S=this,P=S.editor,Q=T.theme_advanced_toolbar_location,M=T.theme_advanced_statusbar_location,L,J,N,R;
if(T.readonly){L=D.add(O,"tr");
L=J=D.add(L,"td",{"class":"mceIframeContainer"});
return J
}if(Q=="top"){S._addToolbars(O,K)
}if(Q=="external"){L=R=D.create("div",{style:"position:relative"});
L=D.add(L,"div",{id:P.id+"_external","class":"mceExternalToolbar"});
D.add(L,"a",{id:P.id+"_external_close",href:"javascript:;","class":"mceExternalClose"});
L=D.add(L,"table",{id:P.id+"_tblext",cellSpacing:0,cellPadding:0});
N=D.add(L,"tbody");
if(I.firstChild.className=="mceOldBoxModel"){I.firstChild.appendChild(R)
}else{I.insertBefore(R,I.firstChild)
}S._addToolbars(N,K);
P.onMouseUp.add(function(){var V=D.get(P.id+"_external");
D.show(V);
D.hide(G);
var U=B.add(P.id+"_external_close","click",function(){D.hide(P.id+"_external");
B.remove(P.id+"_external_close","click",U)
});
D.show(V);
D.setStyle(V,"top",0-D.getRect(P.id+"_tblext").h-1);
D.hide(V);
D.show(V);
V.style.filter="";
G=P.id+"_external";
V=null
})
}if(M=="top"){S._addStatusBar(O,K)
}if(!T.theme_advanced_toolbar_container){L=D.add(O,"tr");
L=J=D.add(L,"td",{"class":"mceIframeContainer"})
}if(Q=="bottom"){S._addToolbars(O,K)
}if(M=="bottom"){S._addStatusBar(O,K)
}return J
},_rowLayout:function(S,M,K){var R=this,N=R.editor,Q,T,I=N.controlManager,L,J,P,O;
Q=S.theme_advanced_containers_default_class||"";
T=S.theme_advanced_containers_default_align||"center";
F(C(S.theme_advanced_containers||""),function(W,V){var U=S["theme_advanced_container_"+W]||"";
switch(U.toLowerCase()){case"mceeditor":L=D.add(M,"tr");
L=J=D.add(L,"td",{"class":"mceIframeContainer"});
break;
case"mceelementpath":R._addStatusBar(M,K);
break;
default:O=(S["theme_advanced_container_"+W+"_align"]||T).toLowerCase();
O="mce"+R._ufirst(O);
L=D.add(D.add(M,"tr"),"td",{"class":"mceToolbar "+(S["theme_advanced_container_"+W+"_class"]||Q)+" "+O||T});
P=I.createToolbar("toolbar"+V);
R._addControls(U,P);
D.setHTML(L,P.renderHTML());
K.deltaHeight-=S.theme_advanced_row_height
}});
return J
},_addControls:function(J,I){var K=this,L=K.settings,M,N=K.editor.controlManager;
if(L.theme_advanced_disable&&!K._disabled){M={};
F(C(L.theme_advanced_disable),function(O){M[O]=1
});
K._disabled=M
}else{M=K._disabled
}F(C(J),function(P){var O;
if(M&&M[P]){return 
}if(P=="tablecontrols"){F(["table","|","row_props","cell_props","|","row_before","row_after","delete_row","|","col_before","col_after","delete_col","|","split_cells","merge_cells"],function(Q){Q=K.createControl(Q,N);
if(Q){I.add(Q)
}});
return 
}O=K.createControl(P,N);
if(O){I.add(O)
}})
},_addToolbars:function(Q,J){var T=this,M,L,O=T.editor,U=T.settings,S,I=O.controlManager,P,K,N=[],R;
R=U.theme_advanced_toolbar_align.toLowerCase();
R="mce"+T._ufirst(R);
K=D.add(D.add(Q,"tr"),"td",{"class":"mceToolbar "+R});
if(!O.getParam("accessibility_focus")){N.push(D.createHTML("a",{href:"#",onfocus:"tinyMCE.get('"+O.id+"').focus();"},"<!-- IE -->"))
}N.push(D.createHTML("a",{href:"#",accesskey:"q",title:O.getLang("advanced.toolbar_focus")},"<!-- IE -->"));
for(M=1;
(S=U["theme_advanced_buttons"+M]);
M++){L=I.createToolbar("toolbar"+M,{"class":"mceToolbarRow"+M});
if(U["theme_advanced_buttons"+M+"_add"]){S+=","+U["theme_advanced_buttons"+M+"_add"]
}if(U["theme_advanced_buttons"+M+"_add_before"]){S=U["theme_advanced_buttons"+M+"_add_before"]+","+S
}T._addControls(S,L);
N.push(L.renderHTML());
J.deltaHeight-=U.theme_advanced_row_height
}N.push(D.createHTML("a",{href:"#",accesskey:"z",title:O.getLang("advanced.toolbar_focus"),onfocus:"tinyMCE.getInstanceById('"+O.id+"').focus();"},"<!-- IE -->"));
D.setHTML(K,N.join(""))
},_addStatusBar:function(M,J){var K,Q=this,N=Q.editor,R=Q.settings,I,O,P,L;
K=D.add(M,"tr");
K=L=D.add(K,"td",{"class":"mceStatusbar"});
K=D.add(K,"div",{id:N.id+"_path_row"},R.theme_advanced_path?N.translate("advanced.path")+": ":"&#160;");
D.add(K,"a",{href:"#",accesskey:"x"});
if(R.theme_advanced_resizing){D.add(L,"a",{id:N.id+"_resize",href:"javascript:;",onclick:"return false;","class":"mceResize"});
if(R.theme_advanced_resizing_use_cookie){N.onPostRender.add(function(){var S=A.getHash("TinyMCE_"+N.id+"_size"),T=D.get(N.id+"_tbl");
if(!S){return 
}if(R.theme_advanced_resize_horizontal){T.style.width=Math.max(10,S.cw)+"px"
}T.style.height=Math.max(10,S.ch)+"px";
D.get(N.id+"_ifr").style.height=Math.max(10,parseInt(S.ch)+Q.deltaHeight)+"px"
})
}N.onPostRender.add(function(){B.add(N.id+"_resize","mousedown",function(W){var Y,V,S,U,X,T;
Y=D.get(N.id+"_tbl");
S=Y.clientWidth;
U=Y.clientHeight;
miw=R.theme_advanced_resizing_min_width||100;
mih=R.theme_advanced_resizing_min_height||100;
maw=R.theme_advanced_resizing_max_width||65535;
mah=R.theme_advanced_resizing_max_height||65535;
V=D.add(D.get(N.id+"_parent"),"div",{"class":"mcePlaceHolder"});
D.setStyles(V,{width:S,height:U});
D.hide(Y);
D.show(V);
I={x:W.screenX,y:W.screenY,w:S,h:U,dx:null,dy:null};
O=B.add(D.doc,"mousemove",function(b){var Z,a;
I.dx=b.screenX-I.x;
I.dy=b.screenY-I.y;
Z=Math.max(miw,I.w+I.dx);
a=Math.max(mih,I.h+I.dy);
Z=Math.min(maw,Z);
a=Math.min(mah,a);
if(R.theme_advanced_resize_horizontal){V.style.width=Z+"px"
}V.style.height=a+"px";
return B.cancel(b)
});
P=B.add(D.doc,"mouseup",function(Z){var a;
B.remove(D.doc,"mousemove",O);
B.remove(D.doc,"mouseup",P);
Y.style.display="";
D.remove(V);
if(I.dx===null){return 
}a=D.get(N.id+"_ifr");
if(R.theme_advanced_resize_horizontal){Y.style.width=Math.max(10,I.w+I.dx)+"px"
}Y.style.height=Math.max(10,I.h+I.dy)+"px";
a.style.height=Math.max(10,a.clientHeight+I.dy)+"px";
if(R.theme_advanced_resizing_use_cookie){A.setHash("TinyMCE_"+N.id+"_size",{cw:I.w+I.dx,ch:I.h+I.dy})
}});
return B.cancel(W)
})
})
}J.deltaHeight-=21;
K=M=null
},_nodeChanged:function(L,Q,K,O){var T=this,I,P=0,S,M,U=T.settings,R,J,N;
if(U.readonly){return 
}E.each(T.stateControls,function(V){Q.setActive(V,L.queryCommandState(T.controls[V][1]))
});
Q.setActive("visualaid",L.hasVisual);
Q.setDisabled("undo",!L.undoManager.hasUndo()&&!L.typing);
Q.setDisabled("redo",!L.undoManager.hasRedo());
Q.setDisabled("outdent",!L.queryCommandState("Outdent"));
I=D.getParent(K,"A");
if(M=Q.get("link")){if(!I||!I.name){M.setDisabled(!I&&O);
M.setActive(!!I)
}}if(M=Q.get("unlink")){M.setDisabled(!I&&O);
M.setActive(!!I&&!I.name)
}if(M=Q.get("anchor")){M.setActive(!!I&&I.name);
if(E.isWebKit){I=D.getParent(K,"IMG");
M.setActive(!!I&&D.getAttrib(I,"mce_name")=="a")
}}I=D.getParent(K,"IMG");
if(M=Q.get("image")){M.setActive(!!I&&K.className.indexOf("mceItem")==-1)
}if(M=Q.get("styleselect")){if(K.className){T._importClasses();
M.select(K.className)
}else{M.select()
}}if(M=Q.get("formatselect")){I=D.getParent(K,D.isBlock);
if(I){M.select(I.nodeName.toLowerCase())
}}if(L.settings.convert_fonts_to_spans){L.dom.getParent(K,function(V){if(V.nodeName==="SPAN"){if(!R&&V.className){R=V.className
}if(!J&&V.style.fontSize){J=V.style.fontSize
}if(!N&&V.style.fontFamily){N=V.style.fontFamily.replace(/[\"\']+/g,"").replace(/^([^,]+).*/,"$1").toLowerCase()
}}return false
});
if(M=Q.get("fontselect")){M.select(function(V){return V.replace(/^([^,]+).*/,"$1").toLowerCase()==N
})
}if(M=Q.get("fontsizeselect")){M.select(function(V){if(V.fontSize&&V.fontSize===J){return true
}if(V["class"]&&V["class"]===R){return true
}})
}}else{if(M=Q.get("fontselect")){M.select(L.queryCommandValue("FontName"))
}if(M=Q.get("fontsizeselect")){S=L.queryCommandValue("FontSize");
M.select(function(V){return V.fontSize==S
})
}}if(U.theme_advanced_path&&U.theme_advanced_statusbar_location){I=D.get(L.id+"_path")||D.add(L.id+"_path_row","span",{id:L.id+"_path"});
D.setHTML(I,"");
L.dom.getParent(K,function(Z){var V=Z.nodeName.toLowerCase(),W,Y,X="";
if(Z.nodeType!=1||Z.nodeName==="BR"||(D.hasClass(Z,"mceItemHidden")||D.hasClass(Z,"mceItemRemoved"))){return 
}if(S=D.getAttrib(Z,"mce_name")){V=S
}if(E.isIE&&Z.scopeName!=="HTML"){V=Z.scopeName+":"+V
}V=V.replace(/mce\:/g,"");
switch(V){case"b":V="strong";
break;
case"i":V="em";
break;
case"img":if(S=D.getAttrib(Z,"src")){X+="src: "+S+" "
}break;
case"a":if(S=D.getAttrib(Z,"name")){X+="name: "+S+" ";
V+="#"+S
}if(S=D.getAttrib(Z,"href")){X+="href: "+S+" "
}break;
case"font":if(U.convert_fonts_to_spans){V="span"
}if(S=D.getAttrib(Z,"face")){X+="font: "+S+" "
}if(S=D.getAttrib(Z,"size")){X+="size: "+S+" "
}if(S=D.getAttrib(Z,"color")){X+="color: "+S+" "
}break;
case"span":if(S=D.getAttrib(Z,"style")){X+="style: "+S+" "
}break
}if(S=D.getAttrib(Z,"id")){X+="id: "+S+" "
}if(S=Z.className){S=S.replace(/(webkit-[\w\-]+|Apple-[\w\-]+|mceItem\w+|mceVisualAid)/g,"");
if(S&&S.indexOf("mceItem")==-1){X+="class: "+S+" ";
if(D.isBlock(Z)||V=="img"||V=="span"){V+="."+S
}}}V=V.replace(/(html:)/g,"");
V={name:V,node:Z,title:X};
T.onResolveName.dispatch(T,V);
X=V.title;
V=V.name;
Y=D.create("a",{href:"javascript:;",onmousedown:"return false;",title:X,"class":"mcePath_"+(P++)},V);
if(I.hasChildNodes()){I.insertBefore(D.doc.createTextNode(" \u00bb "),I.firstChild);
I.insertBefore(Y,I.firstChild)
}else{I.appendChild(Y)
}},L.getBody())
}},_sel:function(I){this.editor.execCommand("mceSelectNodeDepth",false,I)
},_mceInsertAnchor:function(K,J){var I=this.editor;
I.windowManager.open({url:E.baseURL+"/themes/advanced/anchor.htm",width:320+parseInt(I.getLang("advanced.anchor_delta_width",0)),height:90+parseInt(I.getLang("advanced.anchor_delta_height",0)),inline:true},{theme_url:this.url})
},_mceCharMap:function(){var I=this.editor;
I.windowManager.open({url:E.baseURL+"/themes/advanced/charmap.htm",width:550+parseInt(I.getLang("advanced.charmap_delta_width",0)),height:250+parseInt(I.getLang("advanced.charmap_delta_height",0)),inline:true},{theme_url:this.url})
},_mceHelp:function(){var I=this.editor;
I.windowManager.open({url:E.baseURL+"/themes/advanced/about.htm",width:480,height:380,inline:true},{theme_url:this.url})
},_mceColorPicker:function(K,J){var I=this.editor;
J=J||{};
I.windowManager.open({url:E.baseURL+"/themes/advanced/color_picker.htm",width:375+parseInt(I.getLang("advanced.colorpicker_delta_width",0)),height:250+parseInt(I.getLang("advanced.colorpicker_delta_height",0)),close_previous:false,inline:true},{input_color:J.color,func:J.func,theme_url:this.url})
},_mceCodeEditor:function(J,K){var I=this.editor;
I.windowManager.open({url:E.baseURL+"/themes/advanced/source_editor.htm",width:parseInt(I.getParam("theme_advanced_source_editor_width",720)),height:parseInt(I.getParam("theme_advanced_source_editor_height",580)),inline:true,resizable:true,maximizable:true},{theme_url:this.url})
},_mceImage:function(J,K){var I=this.editor;
if(I.dom.getAttrib(I.selection.getNode(),"class").indexOf("mceItem")!=-1){return 
}I.windowManager.open({url:E.baseURL+"/themes/advanced/image.htm",width:355+parseInt(I.getLang("advanced.image_delta_width",0)),height:275+parseInt(I.getLang("advanced.image_delta_height",0)),inline:true},{theme_url:this.url})
},_mceLink:function(J,K){var I=this.editor;
I.windowManager.open({url:E.baseURL+"/themes/advanced/link.htm",width:310+parseInt(I.getLang("advanced.link_delta_width",0)),height:200+parseInt(I.getLang("advanced.link_delta_height",0)),inline:true},{theme_url:this.url})
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
E.ThemeManager.add("advanced",E.themes.AdvancedTheme)
}(tinymce));