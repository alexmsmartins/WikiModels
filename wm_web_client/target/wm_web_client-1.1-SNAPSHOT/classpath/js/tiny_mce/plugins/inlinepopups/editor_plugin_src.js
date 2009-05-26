(function(){var D=tinymce.DOM,B=tinymce.dom.Element,A=tinymce.dom.Event,E=tinymce.each,C=tinymce.is;
tinymce.create("tinymce.plugins.InlinePopups",{init:function(F,G){F.onBeforeRenderUI.add(function(){F.windowManager=new tinymce.InlineWindowManager(F);
D.loadCSS(G+"/skins/"+(F.settings.inlinepopups_skin||"clearlooks2")+"/window.css")
})
},getInfo:function(){return{longname:"InlinePopups",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/inlinepopups",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.create("tinymce.InlineWindowManager:tinymce.WindowManager",{InlineWindowManager:function(F){var G=this;
G.parent(F);
G.zIndex=300000;
G.count=0;
G.windows={}
},open:function(P,I){var T=this,H,J="",O=T.editor,F=0,Q=0,G,L,M,N,K,R,S;
P=P||{};
I=I||{};
if(!P.inline){return T.parent(P,I)
}if(!P.type){T.bookmark=O.selection.getBookmark("simple")
}H=D.uniqueId();
G=D.getViewPort();
P.width=parseInt(P.width||320);
P.height=parseInt(P.height||240)+(tinymce.isIE?8:0);
P.min_width=parseInt(P.min_width||150);
P.min_height=parseInt(P.min_height||100);
P.max_width=parseInt(P.max_width||2000);
P.max_height=parseInt(P.max_height||2000);
P.left=P.left||Math.round(Math.max(G.x,G.x+(G.w/2)-(P.width/2)));
P.top=P.top||Math.round(Math.max(G.y,G.y+(G.h/2)-(P.height/2)));
P.movable=P.resizable=true;
I.mce_width=P.width;
I.mce_height=P.height;
I.mce_inline=true;
I.mce_window_id=H;
I.mce_auto_focus=P.auto_focus;
T.features=P;
T.params=I;
T.onOpen.dispatch(T,P,I);
if(P.type){J+=" mceModal";
if(P.type){J+=" mce"+P.type.substring(0,1).toUpperCase()+P.type.substring(1)
}P.resizable=false
}if(P.statusbar){J+=" mceStatusbar"
}if(P.resizable){J+=" mceResizable"
}if(P.minimizable){J+=" mceMinimizable"
}if(P.maximizable){J+=" mceMaximizable"
}if(P.movable){J+=" mceMovable"
}T._addAll(D.doc.body,["div",{id:H,"class":O.settings.inlinepopups_skin||"clearlooks2",style:"width:100px;height:100px"},["div",{id:H+"_wrapper","class":"mceWrapper"+J},["div",{id:H+"_top","class":"mceTop"},["div",{"class":"mceLeft"}],["div",{"class":"mceCenter"}],["div",{"class":"mceRight"}],["span",{id:H+"_title"},P.title||""]],["div",{id:H+"_middle","class":"mceMiddle"},["div",{id:H+"_left","class":"mceLeft"}],["span",{id:H+"_content"}],["div",{id:H+"_right","class":"mceRight"}]],["div",{id:H+"_bottom","class":"mceBottom"},["div",{"class":"mceLeft"}],["div",{"class":"mceCenter"}],["div",{"class":"mceRight"}],["span",{id:H+"_status"},"Content"]],["a",{"class":"mceMove",tabindex:"-1",href:"javascript:;"}],["a",{"class":"mceMin",tabindex:"-1",href:"javascript:;",onmousedown:"return false;"}],["a",{"class":"mceMax",tabindex:"-1",href:"javascript:;",onmousedown:"return false;"}],["a",{"class":"mceMed",tabindex:"-1",href:"javascript:;",onmousedown:"return false;"}],["a",{"class":"mceClose",tabindex:"-1",href:"javascript:;",onmousedown:"return false;"}],["a",{id:H+"_resize_n","class":"mceResize mceResizeN",tabindex:"-1",href:"javascript:;"}],["a",{id:H+"_resize_s","class":"mceResize mceResizeS",tabindex:"-1",href:"javascript:;"}],["a",{id:H+"_resize_w","class":"mceResize mceResizeW",tabindex:"-1",href:"javascript:;"}],["a",{id:H+"_resize_e","class":"mceResize mceResizeE",tabindex:"-1",href:"javascript:;"}],["a",{id:H+"_resize_nw","class":"mceResize mceResizeNW",tabindex:"-1",href:"javascript:;"}],["a",{id:H+"_resize_ne","class":"mceResize mceResizeNE",tabindex:"-1",href:"javascript:;"}],["a",{id:H+"_resize_sw","class":"mceResize mceResizeSW",tabindex:"-1",href:"javascript:;"}],["a",{id:H+"_resize_se","class":"mceResize mceResizeSE",tabindex:"-1",href:"javascript:;"}]]]);
D.setStyles(H,{top:-10000,left:-10000});
if(tinymce.isGecko){D.setStyle(H,"overflow","auto")
}if(!P.type){F+=D.get(H+"_left").clientWidth;
F+=D.get(H+"_right").clientWidth;
Q+=D.get(H+"_top").clientHeight;
Q+=D.get(H+"_bottom").clientHeight
}D.setStyles(H,{top:P.top,left:P.left,width:P.width+F,height:P.height+Q});
S=P.url||P.file;
if(S){if(tinymce.relaxedDomain){S+=(S.indexOf("?")==-1?"?":"&")+"mce_rdomain="+tinymce.relaxedDomain
}S=tinymce._addVer(S)
}if(!P.type){D.add(H+"_content","iframe",{id:H+"_ifr",src:'javascript:""',frameBorder:0,style:"border:0;width:10px;height:10px"});
D.setStyles(H+"_ifr",{width:P.width,height:P.height});
D.setAttrib(H+"_ifr","src",S)
}else{D.add(H+"_wrapper","a",{id:H+"_ok","class":"mceButton mceOk",href:"javascript:;",onmousedown:"return false;"},"Ok");
if(P.type=="confirm"){D.add(H+"_wrapper","a",{"class":"mceButton mceCancel",href:"javascript:;",onmousedown:"return false;"},"Cancel")
}D.add(H+"_middle","div",{"class":"mceIcon"});
D.setHTML(H+"_content",P.content.replace("\n","<br />"))
}M=A.add(H,"mousedown",function(W){var X=W.target,U,V;
U=T.windows[H];
T.focus(H);
if(X.nodeName=="A"||X.nodeName=="a"){if(X.className=="mceMax"){U.oldPos=U.element.getXY();
U.oldSize=U.element.getSize();
V=D.getViewPort();
V.w-=2;
V.h-=2;
U.element.moveTo(V.x,V.y);
U.element.resizeTo(V.w,V.h);
D.setStyles(H+"_ifr",{width:V.w-U.deltaWidth,height:V.h-U.deltaHeight});
D.addClass(H+"_wrapper","mceMaximized")
}else{if(X.className=="mceMed"){U.element.moveTo(U.oldPos.x,U.oldPos.y);
U.element.resizeTo(U.oldSize.w,U.oldSize.h);
U.iframeElement.resizeTo(U.oldSize.w-U.deltaWidth,U.oldSize.h-U.deltaHeight);
D.removeClass(H+"_wrapper","mceMaximized")
}else{if(X.className=="mceMove"){return T._startDrag(H,W,X.className)
}else{if(D.hasClass(X,"mceResize")){return T._startDrag(H,W,X.className.substring(13))
}}}}}});
N=A.add(H,"click",function(U){var V=U.target;
T.focus(H);
if(V.nodeName=="A"||V.nodeName=="a"){switch(V.className){case"mceClose":T.close(null,H);
return A.cancel(U);
case"mceButton mceOk":case"mceButton mceCancel":P.button_func(V.className=="mceButton mceOk");
return A.cancel(U)
}}});
R=T.windows[H]={id:H,mousedown_func:M,click_func:N,element:new B(H,{blocker:1,container:O.getContainer()}),iframeElement:new B(H+"_ifr"),features:P,deltaWidth:F,deltaHeight:Q};
R.iframeElement.on("focus",function(){T.focus(H)
});
if(T.count==0&&T.editor.getParam("dialog_type","modal")=="modal"){D.add(D.doc.body,"div",{id:"mceModalBlocker","class":(T.editor.settings.inlinepopups_skin||"clearlooks2")+"_modalBlocker",style:{zIndex:T.zIndex-1}});
D.show("mceModalBlocker")
}else{D.setStyle("mceModalBlocker","z-index",T.zIndex-1)
}if(tinymce.isIE6||/Firefox\/2\./.test(navigator.userAgent)||(tinymce.isIE&&!D.boxModel)){D.setStyles("mceModalBlocker",{position:"absolute",left:G.x,top:G.y,width:G.w-2,height:G.h-2})
}T.focus(H);
T._fixIELayout(H,1);
if(D.get(H+"_ok")){D.get(H+"_ok").focus()
}T.count++;
return R
},focus:function(H){var G=this,F;
if(F=G.windows[H]){F.zIndex=this.zIndex++;
F.element.setStyle("zIndex",F.zIndex);
F.element.update();
H=H+"_wrapper";
D.removeClass(G.lastId,"mceFocus");
D.addClass(H,"mceFocus");
G.lastId=H
}},_addAll:function(J,H){var G,K,F=this,I=tinymce.DOM;
if(C(H,"string")){J.appendChild(I.doc.createTextNode(H))
}else{if(H.length){J=J.appendChild(I.create(H[0],H[1]));
for(G=2;
G<H.length;
G++){F._addAll(J,H[G])
}}}},_startDrag:function(T,e,b){var O=this,S,W,Z=D.doc,F,L=O.windows[T],H=L.element,V=H.getXY(),U,P,c,G,X,R,Q,J,I,M,K,N,Y;
G={x:0,y:0};
X=D.getViewPort();
X.w-=2;
X.h-=2;
J=e.screenX;
I=e.screenY;
M=K=N=Y=0;
S=A.add(Z,"mouseup",function(d){A.remove(Z,"mouseup",S);
A.remove(Z,"mousemove",W);
if(F){F.remove()
}H.moveBy(M,K);
H.resizeBy(N,Y);
P=H.getSize();
D.setStyles(T+"_ifr",{width:P.w-L.deltaWidth,height:P.h-L.deltaHeight});
O._fixIELayout(T,1);
return A.cancel(d)
});
if(b!="Move"){a()
}function a(){if(F){return 
}O._fixIELayout(T,0);
D.add(Z.body,"div",{id:"mceEventBlocker","class":"mceEventBlocker "+(O.editor.settings.inlinepopups_skin||"clearlooks2"),style:{zIndex:O.zIndex+1}});
if(tinymce.isIE6||(tinymce.isIE&&!D.boxModel)){D.setStyles("mceEventBlocker",{position:"absolute",left:X.x,top:X.y,width:X.w-2,height:X.h-2})
}F=new B("mceEventBlocker");
F.update();
U=H.getXY();
P=H.getSize();
R=G.x+U.x-X.x;
Q=G.y+U.y-X.y;
D.add(F.get(),"div",{id:"mcePlaceHolder","class":"mcePlaceHolder",style:{left:R,top:Q,width:P.w,height:P.h}});
c=new B("mcePlaceHolder")
}W=A.add(Z,"mousemove",function(g){var d,h,f;
a();
d=g.screenX-J;
h=g.screenY-I;
switch(b){case"ResizeW":M=d;
N=0-d;
break;
case"ResizeE":N=d;
break;
case"ResizeN":case"ResizeNW":case"ResizeNE":if(b=="ResizeNW"){M=d;
N=0-d
}else{if(b=="ResizeNE"){N=d
}}K=h;
Y=0-h;
break;
case"ResizeS":case"ResizeSW":case"ResizeSE":if(b=="ResizeSW"){M=d;
N=0-d
}else{if(b=="ResizeSE"){N=d
}}Y=h;
break;
case"mceMove":M=d;
K=h;
break
}if(N<(f=L.features.min_width-P.w)){if(M!==0){M+=N-f
}N=f
}if(Y<(f=L.features.min_height-P.h)){if(K!==0){K+=Y-f
}Y=f
}N=Math.min(N,L.features.max_width-P.w);
Y=Math.min(Y,L.features.max_height-P.h);
M=Math.max(M,X.x-(R+X.x));
K=Math.max(K,X.y-(Q+X.y));
M=Math.min(M,(X.w+X.x)-(R+P.w+X.x));
K=Math.min(K,(X.h+X.y)-(Q+P.h+X.y));
if(M+K!==0){if(R+M<0){M=0
}if(Q+K<0){K=0
}c.moveTo(R+M,Q+K)
}if(N+Y!==0){c.resizeTo(P.w+N,P.h+Y)
}return A.cancel(g)
});
return A.cancel(e)
},resizeBy:function(G,H,I){var F=this.windows[I];
if(F){F.element.resizeBy(G,H);
F.iframeElement.resizeBy(G,H)
}},close:function(J,L){var H=this,G,K=D.doc,F=0,I,L;
L=H._findId(L||J);
if(!H.windows[L]){H.parent(J);
return 
}H.count--;
if(H.count==0){D.remove("mceModalBlocker")
}if(G=H.windows[L]){H.onClose.dispatch(H);
A.remove(K,"mousedown",G.mousedownFunc);
A.remove(K,"click",G.clickFunc);
A.clear(L);
A.clear(L+"_ifr");
D.setAttrib(L+"_ifr","src",'javascript:""');
G.element.remove();
delete H.windows[L];
E(H.windows,function(M){if(M.zIndex>F){I=M;
F=M.zIndex
}});
if(I){H.focus(I.id)
}}},setTitle:function(F,G){var H;
F=this._findId(F);
if(H=D.get(F+"_title")){H.innerHTML=D.encode(G)
}},alert:function(G,F,J){var I=this,H;
H=I.open({title:I,type:"alert",button_func:function(K){if(F){F.call(K||I,K)
}I.close(null,H.id)
},content:D.encode(I.editor.getLang(G,G)),inline:1,width:400,height:130})
},confirm:function(G,F,J){var I=this,H;
H=I.open({title:I,type:"confirm",button_func:function(K){if(F){F.call(K||I,K)
}I.close(null,H.id)
},content:D.encode(I.editor.getLang(G,G)),inline:1,width:400,height:130})
},_findId:function(F){var G=this;
if(typeof (F)=="string"){return F
}E(G.windows,function(H){var I=D.get(H.id+"_ifr");
if(I&&F==I.contentWindow){F=H.id;
return false
}});
return F
},_fixIELayout:function(I,H){var F,G;
if(!tinymce.isIE6){return 
}E(["n","s","w","e","nw","ne","sw","se"],function(J){var K=D.get(I+"_resize_"+J);
D.setStyles(K,{width:H?K.clientWidth:"",height:H?K.clientHeight:"",cursor:D.getStyle(K,"cursor",1)});
D.setStyle(I+"_bottom","bottom","-1px");
K=0
});
if(F=this.windows[I]){F.element.hide();
F.element.show();
E(D.select("div,a",I),function(K,J){if(K.currentStyle.backgroundImage!="none"){G=new Image();
G.src=K.currentStyle.backgroundImage.replace(/url\(\"(.+)\"\)/,"$1")
}});
D.get(I).style.filter=""
}}});
tinymce.PluginManager.add("inlinepopups",tinymce.plugins.InlinePopups)
})();