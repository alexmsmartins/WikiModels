(function(){var D=tinymce.DOM,A=tinymce.dom.Element,B=tinymce.dom.Event,C=tinymce.each,E=tinymce.is;
tinymce.create("tinymce.plugins.InlinePopups",{init:function(G,F){G.onBeforeRenderUI.add(function(){G.windowManager=new tinymce.InlineWindowManager(G);
D.loadCSS(F+"/skins/"+(G.settings.inlinepopups_skin||"clearlooks2")+"/window.css")
})
},getInfo:function(){return{longname:"InlinePopups",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/inlinepopups",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.create("tinymce.InlineWindowManager:tinymce.WindowManager",{InlineWindowManager:function(G){var F=this;
F.parent(G);
F.zIndex=300000;
F.count=0;
F.windows={}
},open:function(F,M){var Q=this,N,L="",G=Q.editor,P=0,T=0,O,J,I,H,K,S,R;
F=F||{};
M=M||{};
if(!F.inline){return Q.parent(F,M)
}if(!F.type){Q.bookmark=G.selection.getBookmark("simple")
}N=D.uniqueId();
O=D.getViewPort();
F.width=parseInt(F.width||320);
F.height=parseInt(F.height||240)+(tinymce.isIE?8:0);
F.min_width=parseInt(F.min_width||150);
F.min_height=parseInt(F.min_height||100);
F.max_width=parseInt(F.max_width||2000);
F.max_height=parseInt(F.max_height||2000);
F.left=F.left||Math.round(Math.max(O.x,O.x+(O.w/2)-(F.width/2)));
F.top=F.top||Math.round(Math.max(O.y,O.y+(O.h/2)-(F.height/2)));
F.movable=F.resizable=true;
M.mce_width=F.width;
M.mce_height=F.height;
M.mce_inline=true;
M.mce_window_id=N;
M.mce_auto_focus=F.auto_focus;
Q.features=F;
Q.params=M;
Q.onOpen.dispatch(Q,F,M);
if(F.type){L+=" mceModal";
if(F.type){L+=" mce"+F.type.substring(0,1).toUpperCase()+F.type.substring(1)
}F.resizable=false
}if(F.statusbar){L+=" mceStatusbar"
}if(F.resizable){L+=" mceResizable"
}if(F.minimizable){L+=" mceMinimizable"
}if(F.maximizable){L+=" mceMaximizable"
}if(F.movable){L+=" mceMovable"
}Q._addAll(D.doc.body,["div",{id:N,"class":G.settings.inlinepopups_skin||"clearlooks2",style:"width:100px;height:100px"},["div",{id:N+"_wrapper","class":"mceWrapper"+L},["div",{id:N+"_top","class":"mceTop"},["div",{"class":"mceLeft"}],["div",{"class":"mceCenter"}],["div",{"class":"mceRight"}],["span",{id:N+"_title"},F.title||""]],["div",{id:N+"_middle","class":"mceMiddle"},["div",{id:N+"_left","class":"mceLeft"}],["span",{id:N+"_content"}],["div",{id:N+"_right","class":"mceRight"}]],["div",{id:N+"_bottom","class":"mceBottom"},["div",{"class":"mceLeft"}],["div",{"class":"mceCenter"}],["div",{"class":"mceRight"}],["span",{id:N+"_status"},"Content"]],["a",{"class":"mceMove",tabindex:"-1",href:"javascript:;"}],["a",{"class":"mceMin",tabindex:"-1",href:"javascript:;",onmousedown:"return false;"}],["a",{"class":"mceMax",tabindex:"-1",href:"javascript:;",onmousedown:"return false;"}],["a",{"class":"mceMed",tabindex:"-1",href:"javascript:;",onmousedown:"return false;"}],["a",{"class":"mceClose",tabindex:"-1",href:"javascript:;",onmousedown:"return false;"}],["a",{id:N+"_resize_n","class":"mceResize mceResizeN",tabindex:"-1",href:"javascript:;"}],["a",{id:N+"_resize_s","class":"mceResize mceResizeS",tabindex:"-1",href:"javascript:;"}],["a",{id:N+"_resize_w","class":"mceResize mceResizeW",tabindex:"-1",href:"javascript:;"}],["a",{id:N+"_resize_e","class":"mceResize mceResizeE",tabindex:"-1",href:"javascript:;"}],["a",{id:N+"_resize_nw","class":"mceResize mceResizeNW",tabindex:"-1",href:"javascript:;"}],["a",{id:N+"_resize_ne","class":"mceResize mceResizeNE",tabindex:"-1",href:"javascript:;"}],["a",{id:N+"_resize_sw","class":"mceResize mceResizeSW",tabindex:"-1",href:"javascript:;"}],["a",{id:N+"_resize_se","class":"mceResize mceResizeSE",tabindex:"-1",href:"javascript:;"}]]]);
D.setStyles(N,{top:-10000,left:-10000});
if(tinymce.isGecko){D.setStyle(N,"overflow","auto")
}if(!F.type){P+=D.get(N+"_left").clientWidth;
P+=D.get(N+"_right").clientWidth;
T+=D.get(N+"_top").clientHeight;
T+=D.get(N+"_bottom").clientHeight
}D.setStyles(N,{top:F.top,left:F.left,width:F.width+P,height:F.height+T});
R=F.url||F.file;
if(R){if(tinymce.relaxedDomain){R+=(R.indexOf("?")==-1?"?":"&")+"mce_rdomain="+tinymce.relaxedDomain
}R=tinymce._addVer(R)
}if(!F.type){D.add(N+"_content","iframe",{id:N+"_ifr",src:'javascript:""',frameBorder:0,style:"border:0;width:10px;height:10px"});
D.setStyles(N+"_ifr",{width:F.width,height:F.height});
D.setAttrib(N+"_ifr","src",R)
}else{D.add(N+"_wrapper","a",{id:N+"_ok","class":"mceButton mceOk",href:"javascript:;",onmousedown:"return false;"},"Ok");
if(F.type=="confirm"){D.add(N+"_wrapper","a",{"class":"mceButton mceCancel",href:"javascript:;",onmousedown:"return false;"},"Cancel")
}D.add(N+"_middle","div",{"class":"mceIcon"});
D.setHTML(N+"_content",F.content.replace("\n","<br />"))
}I=B.add(N,"mousedown",function(V){var U=V.target,W,X;
W=Q.windows[N];
Q.focus(N);
if(U.nodeName=="A"||U.nodeName=="a"){if(U.className=="mceMax"){W.oldPos=W.element.getXY();
W.oldSize=W.element.getSize();
X=D.getViewPort();
X.w-=2;
X.h-=2;
W.element.moveTo(X.x,X.y);
W.element.resizeTo(X.w,X.h);
D.setStyles(N+"_ifr",{width:X.w-W.deltaWidth,height:X.h-W.deltaHeight});
D.addClass(N+"_wrapper","mceMaximized")
}else{if(U.className=="mceMed"){W.element.moveTo(W.oldPos.x,W.oldPos.y);
W.element.resizeTo(W.oldSize.w,W.oldSize.h);
W.iframeElement.resizeTo(W.oldSize.w-W.deltaWidth,W.oldSize.h-W.deltaHeight);
D.removeClass(N+"_wrapper","mceMaximized")
}else{if(U.className=="mceMove"){return Q._startDrag(N,V,U.className)
}else{if(D.hasClass(U,"mceResize")){return Q._startDrag(N,V,U.className.substring(13))
}}}}}});
H=B.add(N,"click",function(U){var V=U.target;
Q.focus(N);
if(V.nodeName=="A"||V.nodeName=="a"){switch(V.className){case"mceClose":Q.close(null,N);
return B.cancel(U);
case"mceButton mceOk":case"mceButton mceCancel":F.button_func(V.className=="mceButton mceOk");
return B.cancel(U)
}}});
S=Q.windows[N]={id:N,mousedown_func:I,click_func:H,element:new A(N,{blocker:1,container:G.getContainer()}),iframeElement:new A(N+"_ifr"),features:F,deltaWidth:P,deltaHeight:T};
S.iframeElement.on("focus",function(){Q.focus(N)
});
if(Q.count==0&&Q.editor.getParam("dialog_type","modal")=="modal"){D.add(D.doc.body,"div",{id:"mceModalBlocker","class":(Q.editor.settings.inlinepopups_skin||"clearlooks2")+"_modalBlocker",style:{zIndex:Q.zIndex-1}});
D.show("mceModalBlocker")
}else{D.setStyle("mceModalBlocker","z-index",Q.zIndex-1)
}if(tinymce.isIE6||/Firefox\/2\./.test(navigator.userAgent)||(tinymce.isIE&&!D.boxModel)){D.setStyles("mceModalBlocker",{position:"absolute",left:O.x,top:O.y,width:O.w-2,height:O.h-2})
}Q.focus(N);
Q._fixIELayout(N,1);
if(D.get(N+"_ok")){D.get(N+"_ok").focus()
}Q.count++;
return S
},focus:function(F){var G=this,H;
if(H=G.windows[F]){H.zIndex=this.zIndex++;
H.element.setStyle("zIndex",H.zIndex);
H.element.update();
F=F+"_wrapper";
D.removeClass(G.lastId,"mceFocus");
D.addClass(F,"mceFocus");
G.lastId=F
}},_addAll:function(G,I){var J,F,K=this,H=tinymce.DOM;
if(E(I,"string")){G.appendChild(H.doc.createTextNode(I))
}else{if(I.length){G=G.appendChild(H.create(I[0],I[1]));
for(J=2;
J<I.length;
J++){K._addAll(G,I[J])
}}}},_startDrag:function(L,K,O){var W=this,N,H,S=D.doc,p,Z=W.windows[L],d=Z.element,I=d.getXY(),J,U,M,e,V,Q,R,b,c,Y,a,X,T;
e={x:0,y:0};
V=D.getViewPort();
V.w-=2;
V.h-=2;
b=K.screenX;
c=K.screenY;
Y=a=X=T=0;
N=B.add(S,"mouseup",function(F){B.remove(S,"mouseup",N);
B.remove(S,"mousemove",H);
if(p){p.remove()
}d.moveBy(Y,a);
d.resizeBy(X,T);
U=d.getSize();
D.setStyles(L+"_ifr",{width:U.w-Z.deltaWidth,height:U.h-Z.deltaHeight});
W._fixIELayout(L,1);
return B.cancel(F)
});
if(O!="Move"){P()
}function P(){if(p){return 
}W._fixIELayout(L,0);
D.add(S.body,"div",{id:"mceEventBlocker","class":"mceEventBlocker "+(W.editor.settings.inlinepopups_skin||"clearlooks2"),style:{zIndex:W.zIndex+1}});
if(tinymce.isIE6||(tinymce.isIE&&!D.boxModel)){D.setStyles("mceEventBlocker",{position:"absolute",left:V.x,top:V.y,width:V.w-2,height:V.h-2})
}p=new A("mceEventBlocker");
p.update();
J=d.getXY();
U=d.getSize();
Q=e.x+J.x-V.x;
R=e.y+J.y-V.y;
D.add(p.get(),"div",{id:"mcePlaceHolder","class":"mcePlaceHolder",style:{left:Q,top:R,width:U.w,height:U.h}});
M=new A("mcePlaceHolder")
}H=B.add(S,"mousemove",function(F){var g,f,G;
P();
g=F.screenX-b;
f=F.screenY-c;
switch(O){case"ResizeW":Y=g;
X=0-g;
break;
case"ResizeE":X=g;
break;
case"ResizeN":case"ResizeNW":case"ResizeNE":if(O=="ResizeNW"){Y=g;
X=0-g
}else{if(O=="ResizeNE"){X=g
}}a=f;
T=0-f;
break;
case"ResizeS":case"ResizeSW":case"ResizeSE":if(O=="ResizeSW"){Y=g;
X=0-g
}else{if(O=="ResizeSE"){X=g
}}T=f;
break;
case"mceMove":Y=g;
a=f;
break
}if(X<(G=Z.features.min_width-U.w)){if(Y!==0){Y+=X-G
}X=G
}if(T<(G=Z.features.min_height-U.h)){if(a!==0){a+=T-G
}T=G
}X=Math.min(X,Z.features.max_width-U.w);
T=Math.min(T,Z.features.max_height-U.h);
Y=Math.max(Y,V.x-(Q+V.x));
a=Math.max(a,V.y-(R+V.y));
Y=Math.min(Y,(V.w+V.x)-(Q+U.w+V.x));
a=Math.min(a,(V.h+V.y)-(R+U.h+V.y));
if(Y+a!==0){if(Q+Y<0){Y=0
}if(R+a<0){a=0
}M.moveTo(Q+Y,R+a)
}if(X+T!==0){M.resizeTo(U.w+X,U.h+T)
}return B.cancel(F)
});
return B.cancel(K)
},resizeBy:function(H,G,F){var I=this.windows[F];
if(I){I.element.resizeBy(H,G);
I.iframeElement.resizeBy(H,G)
}},close:function(H,F){var J=this,K,G=D.doc,L=0,I,F;
F=J._findId(F||H);
if(!J.windows[F]){J.parent(H);
return 
}J.count--;
if(J.count==0){D.remove("mceModalBlocker")
}if(K=J.windows[F]){J.onClose.dispatch(J);
B.remove(G,"mousedown",K.mousedownFunc);
B.remove(G,"click",K.clickFunc);
B.clear(F);
B.clear(F+"_ifr");
D.setAttrib(F+"_ifr","src",'javascript:""');
K.element.remove();
delete J.windows[F];
C(J.windows,function(M){if(M.zIndex>L){I=M;
L=M.zIndex
}});
if(I){J.focus(I.id)
}}},setTitle:function(H,G){var F;
H=this._findId(H);
if(F=D.get(H+"_title")){F.innerHTML=D.encode(G)
}},alert:function(I,J,F){var G=this,H;
H=G.open({title:G,type:"alert",button_func:function(K){if(J){J.call(K||G,K)
}G.close(null,H.id)
},content:D.encode(G.editor.getLang(I,I)),inline:1,width:400,height:130})
},confirm:function(I,J,F){var G=this,H;
H=G.open({title:G,type:"confirm",button_func:function(K){if(J){J.call(K||G,K)
}G.close(null,H.id)
},content:D.encode(G.editor.getLang(I,I)),inline:1,width:400,height:130})
},_findId:function(G){var F=this;
if(typeof (G)=="string"){return G
}C(F.windows,function(I){var H=D.get(I.id+"_ifr");
if(H&&G==H.contentWindow){G=I.id;
return false
}});
return G
},_fixIELayout:function(F,G){var I,H;
if(!tinymce.isIE6){return 
}C(["n","s","w","e","nw","ne","sw","se"],function(K){var J=D.get(F+"_resize_"+K);
D.setStyles(J,{width:G?J.clientWidth:"",height:G?J.clientHeight:"",cursor:D.getStyle(J,"cursor",1)});
D.setStyle(F+"_bottom","bottom","-1px");
J=0
});
if(I=this.windows[F]){I.element.hide();
I.element.show();
C(D.select("div,a",F),function(J,K){if(J.currentStyle.backgroundImage!="none"){H=new Image();
H.src=J.currentStyle.backgroundImage.replace(/url\(\"(.+)\"\)/,"$1")
}});
D.get(F).style.filter=""
}}});
tinymce.PluginManager.add("inlinepopups",tinymce.plugins.InlinePopups)
})();