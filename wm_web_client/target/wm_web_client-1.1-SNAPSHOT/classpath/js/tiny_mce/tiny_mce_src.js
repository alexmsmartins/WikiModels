var tinymce={majorVersion:"3",minorVersion:"2.3",releaseDate:"2009-04-23",_init:function(){var L=this,I=document,J=window,H=navigator,B=H.userAgent,G,A,F,E,D,K;
L.isOpera=J.opera&&opera.buildNumber;
L.isWebKit=/WebKit/.test(B);
L.isIE=!L.isWebKit&&!L.isOpera&&(/MSIE/gi).test(B)&&(/Explorer/gi).test(H.appName);
L.isIE6=L.isIE&&/MSIE [56]/.test(B);
L.isGecko=!L.isWebKit&&/Gecko/.test(B);
L.isMac=B.indexOf("Mac")!=-1;
L.isAir=/adobeair/i.test(B);
if(J.tinyMCEPreInit){L.suffix=tinyMCEPreInit.suffix;
L.baseURL=tinyMCEPreInit.base;
L.query=tinyMCEPreInit.query;
return 
}L.suffix="";
A=I.getElementsByTagName("base");
for(G=0;
G<A.length;
G++){if(K=A[G].href){if(/^https?:\/\/[^\/]+$/.test(K)){K+="/"
}E=K?K.match(/.*\//)[0]:""
}}function C(M){if(M.src&&/tiny_mce(|_dev|_src|_gzip|_jquery|_prototype).js/.test(M.src)){if(/_(src|dev)\.js/g.test(M.src)){L.suffix="_src"
}if((D=M.src.indexOf("?"))!=-1){L.query=M.src.substring(D+1)
}L.baseURL=M.src.substring(0,M.src.lastIndexOf("/"));
if(E&&L.baseURL.indexOf("://")==-1){L.baseURL=E+L.baseURL
}return L.baseURL
}return null
}A=I.getElementsByTagName("script");
for(G=0;
G<A.length;
G++){if(C(A[G])){return 
}}F=I.getElementsByTagName("head")[0];
if(F){A=F.getElementsByTagName("script");
for(G=0;
G<A.length;
G++){if(C(A[G])){return 
}}}return 
},is:function(B,A){var C=typeof (B);
if(!A){return C!="undefined"
}if(A=="array"&&(B.hasOwnProperty&&B instanceof Array)){return true
}return C==A
},each:function(D,A,C){var E,B;
if(!D){return 0
}C=C||D;
if(typeof (D.length)!="undefined"){for(E=0,B=D.length;
E<B;
E++){if(A.call(C,D[E],E,D)===false){return 0
}}}else{for(E in D){if(D.hasOwnProperty(E)){if(A.call(C,D[E],E,D)===false){return 0
}}}}return 1
},map:function(A,B){var C=[];
tinymce.each(A,function(D){C.push(B(D))
});
return C
},grep:function(A,B){var C=[];
tinymce.each(A,function(D){if(!B||B(D)){C.push(D)
}});
return C
},inArray:function(B,C){var D,A;
if(B){for(D=0,A=B.length;
D<A;
D++){if(B[D]===C){return D
}}}return -1
},extend:function(D,C){var B,A=arguments;
for(B=1;
B<A.length;
B++){C=A[B];
tinymce.each(C,function(E,F){if(typeof (E)!=="undefined"){D[F]=E
}})
}return D
},trim:function(A){return(A?""+A:"").replace(/^\s*|\s*$/g,"")
},create:function(I,A){var H=this,B,D,E,F,C,G=0;
I=/^((static) )?([\w.]+)(:([\w.]+))?/.exec(I);
E=I[3].match(/(^|\.)(\w+)$/i)[2];
D=H.createNS(I[3].replace(/\.\w+$/,""));
if(D[E]){return 
}if(I[2]=="static"){D[E]=A;
if(this.onCreate){this.onCreate(I[2],I[3],D[E])
}return 
}if(!A[E]){A[E]=function(){};
G=1
}D[E]=A[E];
H.extend(D[E].prototype,A);
if(I[5]){B=H.resolve(I[5]).prototype;
F=I[5].match(/\.(\w+)$/i)[1];
C=D[E];
if(G){D[E]=function(){return B[F].apply(this,arguments)
}
}else{D[E]=function(){this.parent=B[F];
return C.apply(this,arguments)
}
}D[E].prototype[E]=D[E];
H.each(B,function(J,K){D[E].prototype[K]=B[K]
});
H.each(A,function(J,K){if(B[K]){D[E].prototype[K]=function(){this.parent=B[K];
return J.apply(this,arguments)
}
}else{if(K!=E){D[E].prototype[K]=J
}}})
}H.each(A["static"],function(J,K){D[E][K]=J
});
if(this.onCreate){this.onCreate(I[2],I[3],D[E].prototype)
}},walk:function(C,B,D,A){A=A||this;
if(C){if(D){C=C[D]
}tinymce.each(C,function(F,E){if(B.call(A,F,E,D)===false){return false
}tinymce.walk(F,B,D,A)
})
}},createNS:function(D,C){var B,A;
C=C||window;
D=D.split(".");
for(B=0;
B<D.length;
B++){A=D[B];
if(!C[A]){C[A]={}
}C=C[A]
}return C
},resolve:function(D,C){var B,A;
C=C||window;
D=D.split(".");
for(B=0,A=D.length;
B<A;
B++){C=C[D[B]];
if(!C){break
}}return C
},addUnload:function(E,D){var C=this,A=window;
E={func:E,scope:D||this};
if(!C.unloads){function B(){var G=C.unloads,H,I;
if(G){for(I in G){H=G[I];
if(H&&H.func){H.func.call(H.scope,1)
}}if(A.detachEvent){A.detachEvent("onbeforeunload",F);
A.detachEvent("onunload",B)
}else{if(A.removeEventListener){A.removeEventListener("unload",B,false)
}}C.unloads=H=G=A=B=0;
if(window.CollectGarbage){window.CollectGarbage()
}}}function F(){var H=document;
if(H.readyState=="interactive"){function G(){H.detachEvent("onstop",G);
if(B){B()
}H=0
}if(H){H.attachEvent("onstop",G)
}window.setTimeout(function(){if(H){H.detachEvent("onstop",G)
}},0)
}}if(A.attachEvent){A.attachEvent("onunload",B);
A.attachEvent("onbeforeunload",F)
}else{if(A.addEventListener){A.addEventListener("unload",B,false)
}}C.unloads=[E]
}else{C.unloads.push(E)
}return E
},removeUnload:function(C){var A=this.unloads,B=null;
tinymce.each(A,function(E,D){if(E&&E.func==C){A.splice(D,1);
B=C;
return false
}});
return B
},explode:function(A,B){return A?tinymce.map(A.split(B||","),tinymce.trim):A
},_addVer:function(B){var A;
if(!this.query){return B
}A=(B.indexOf("?")==-1?"?":"&")+this.query;
if(B.indexOf("#")==-1){return B+A
}return B.replace("#",A+"#")
}};
window.tinymce=tinymce;
tinymce._init();
tinymce.create("tinymce.util.Dispatcher",{scope:null,listeners:null,Dispatcher:function(A){this.scope=A||this;
this.listeners=[]
},add:function(A,B){this.listeners.push({cb:A,scope:B||this.scope});
return A
},addToTop:function(A,B){this.listeners.unshift({cb:A,scope:B||this.scope});
return A
},remove:function(A){var B=this.listeners,C=null;
tinymce.each(B,function(E,D){if(A==E.cb){C=A;
B.splice(D,1);
return false
}});
return C
},dispatch:function(){var D,B=arguments,C,A=this.listeners,E;
for(C=0;
C<A.length;
C++){E=A[C];
D=E.cb.apply(E.scope,B);
if(D===false){break
}}return D
}});
(function(){var A=tinymce.each;
tinymce.create("tinymce.util.URI",{URI:function(D,F){var E=this,G,C,B;
F=E.settings=F||{};
if(/^(mailto|tel|news|javascript|about):/i.test(D)||/^\s*#/.test(D)){E.source=D;
return 
}if(D.indexOf("/")===0&&D.indexOf("//")!==0){D=(F.base_uri?F.base_uri.protocol||"http":"http")+"://mce_host"+D
}if(D.indexOf(":/")===-1&&D.indexOf("//")!==0){D=(F.base_uri.protocol||"http")+"://mce_host"+E.toAbsPath(F.base_uri.path,D)
}D=D.replace(/@@/g,"(mce_at)");
D=/^(?:(?![^:@]+:[^:@\/]*@)([^:\/?#.]+):)?(?:\/\/)?((?:(([^:@]*):?([^:@]*))?@)?([^:\/?#]*)(?::(\d*))?)(((\/(?:[^?#](?![^?#\/]*\.[^?#\/.]+(?:[?#]|$)))*\/?)?([^?#\/]*))(?:\?([^#]*))?(?:#(.*))?)/.exec(D);
A(["source","protocol","authority","userInfo","user","password","host","port","relative","path","directory","file","query","anchor"],function(H,I){var J=D[I];
if(J){J=J.replace(/\(mce_at\)/g,"@@")
}E[H]=J
});
if(B=F.base_uri){if(!E.protocol){E.protocol=B.protocol
}if(!E.userInfo){E.userInfo=B.userInfo
}if(!E.port&&E.host=="mce_host"){E.port=B.port
}if(!E.host||E.host=="mce_host"){E.host=B.host
}E.source=""
}},setPath:function(C){var B=this;
C=/^(.*?)\/?(\w+)?$/.exec(C);
B.path=C[0];
B.directory=C[1];
B.file=C[2];
B.source="";
B.getURI()
},toRelative:function(B){var C=this,D;
if(B==="./"){return B
}B=new tinymce.util.URI(B,{base_uri:C});
if((B.host!="mce_host"&&C.host!=B.host&&B.host)||C.port!=B.port||C.protocol!=B.protocol){return B.getURI()
}D=C.toRelPath(C.path,B.path);
if(B.query){D+="?"+B.query
}if(B.anchor){D+="#"+B.anchor
}return D
},toAbsolute:function(B,C){var B=new tinymce.util.URI(B,{base_uri:this});
return B.getURI(this.host==B.host?C:0)
},toRelPath:function(G,H){var C,F=0,D="",E,B;
G=G.substring(0,G.lastIndexOf("/"));
G=G.split("/");
C=H.split("/");
if(G.length>=C.length){for(E=0,B=G.length;
E<B;
E++){if(E>=C.length||G[E]!=C[E]){F=E+1;
break
}}}if(G.length<C.length){for(E=0,B=C.length;
E<B;
E++){if(E>=G.length||G[E]!=C[E]){F=E+1;
break
}}}if(F==1){return H
}for(E=0,B=G.length-(F-1);
E<B;
E++){D+="../"
}for(E=F-1,B=C.length;
E<B;
E++){if(E!=F-1){D+="/"+C[E]
}else{D+=C[E]
}}return D
},toAbsPath:function(E,F){var C,B=0,G=[],D;
D=/\/$/.test(F)?"/":"";
E=E.split("/");
F=F.split("/");
A(E,function(H){if(H){G.push(H)
}});
E=G;
for(C=F.length-1,G=[];
C>=0;
C--){if(F[C].length==0||F[C]=="."){continue
}if(F[C]==".."){B++;
continue
}if(B>0){B--;
continue
}G.push(F[C])
}C=E.length-B;
if(C<=0){return"/"+G.reverse().join("/")+D
}return"/"+E.slice(0,C).join("/")+"/"+G.reverse().join("/")+D
},getURI:function(D){var C,B=this;
if(!B.source||D){C="";
if(!D){if(B.protocol){C+=B.protocol+"://"
}if(B.userInfo){C+=B.userInfo+"@"
}if(B.host){C+=B.host
}if(B.port){C+=":"+B.port
}}if(B.path){C+=B.path
}if(B.query){C+="?"+B.query
}if(B.anchor){C+="#"+B.anchor
}B.source=C
}return B.source
}})
})();
(function(){var A=tinymce.each;
tinymce.create("static tinymce.util.Cookie",{getHash:function(D){var B=this.get(D),C;
if(B){A(B.split("&"),function(E){E=E.split("=");
C=C||{};
C[unescape(E[0])]=unescape(E[1])
})
}return C
},setHash:function(H,B,E,D,G,C){var F="";
A(B,function(J,I){F+=(!F?"":"&")+escape(I)+"="+escape(J)
});
this.set(H,F,E,D,G,C)
},get:function(F){var E=document.cookie,D,C=F+"=",B;
if(!E){return 
}B=E.indexOf("; "+C);
if(B==-1){B=E.indexOf(C);
if(B!=0){return null
}}else{B+=2
}D=E.indexOf(";",B);
if(D==-1){D=E.length
}return unescape(E.substring(B+C.length,D))
},set:function(G,B,E,D,F,C){document.cookie=G+"="+escape(B)+((E)?"; expires="+E.toGMTString():"")+((D)?"; path="+escape(D):"")+((F)?"; domain="+F:"")+((C)?"; secure":"")
},remove:function(D,B){var C=new Date();
C.setTime(C.getTime()-1000);
this.set(D,"",C,B,C)
}})
})();
tinymce.create("static tinymce.util.JSON",{serialize:function(E){var C,A,D=tinymce.util.JSON.serialize,B;
if(E==null){return"null"
}B=typeof E;
if(B=="string"){A="\bb\tt\nn\ff\rr\"\"''\\\\";
return'"'+E.replace(/([\u0080-\uFFFF\x00-\x1f\"])/g,function(G,F){C=A.indexOf(F);
if(C+1){return"\\"+A.charAt(C+1)
}G=F.charCodeAt().toString(16);
return"\\u"+"0000".substring(G.length)+G
})+'"'
}if(B=="object"){if(E.hasOwnProperty&&E instanceof Array){for(C=0,A="[";
C<E.length;
C++){A+=(C>0?",":"")+D(E[C])
}return A+"]"
}A="{";
for(C in E){A+=typeof E[C]!="function"?(A.length>1?',"':'"')+C+'":'+D(E[C]):""
}return A+"}"
}return""+E
},parse:function(s){try{return eval("("+s+")")
}catch(ex){}}});
tinymce.create("static tinymce.util.XHR",{send:function(F){var A,D,B=window,G=0;
F.scope=F.scope||this;
F.success_scope=F.success_scope||F.scope;
F.error_scope=F.error_scope||F.scope;
F.async=F.async===false?false:true;
F.data=F.data||"";
function C(I){A=0;
try{A=new ActiveXObject(I)
}catch(H){}return A
}A=B.XMLHttpRequest?new XMLHttpRequest():C("Microsoft.XMLHTTP")||C("Msxml2.XMLHTTP");
if(A){if(A.overrideMimeType){A.overrideMimeType(F.content_type)
}A.open(F.type||(F.data?"POST":"GET"),F.url,F.async);
if(F.content_type){A.setRequestHeader("Content-Type",F.content_type)
}A.send(F.data);
function E(){if(!F.async||A.readyState==4||G++>10000){if(F.success&&G<10000&&A.status==200){F.success.call(F.success_scope,""+A.responseText,A,F)
}else{if(F.error){F.error.call(F.error_scope,G>10000?"TIMED_OUT":"GENERAL",A,F)
}}A=null
}else{B.setTimeout(E,10)
}}if(!F.async){return E()
}D=B.setTimeout(E,10)
}}});
(function(){var C=tinymce.extend,B=tinymce.util.JSON,A=tinymce.util.XHR;
tinymce.create("tinymce.util.JSONRequest",{JSONRequest:function(D){this.settings=C({},D);
this.count=0
},send:function(F){var E=F.error,D=F.success;
F=C(this.settings,F);
F.success=function(H,G){H=B.parse(H);
if(typeof (H)=="undefined"){H={error:"JSON Parse error."}
}if(H.error){E.call(F.error_scope||F.scope,H.error,G)
}else{D.call(F.success_scope||F.scope,H.result)
}};
F.error=function(H,G){E.call(F.error_scope||F.scope,H,G)
};
F.data=B.serialize({id:F.id||"c"+(this.count++),method:F.method,params:F.params});
F.content_type="application/json";
A.send(F)
},"static":{sendRPC:function(D){return new tinymce.util.JSONRequest().send(D)
}}})
}());
(function(C){var E=C.each,B=C.is;
var D=C.isWebKit,A=C.isIE;
C.create("tinymce.dom.DOMUtils",{doc:null,root:null,files:null,pixelStyles:/^(top|left|bottom|right|width|height|borderWidth)$/,props:{"for":"htmlFor","class":"className",className:"className",checked:"checked",disabled:"disabled",maxlength:"maxLength",readonly:"readOnly",selected:"selected",value:"value",id:"id",name:"name",type:"type"},DOMUtils:function(I,G){var F=this;
F.doc=I;
F.win=window;
F.files={};
F.cssFlicker=false;
F.counter=0;
F.boxModel=!C.isIE||I.compatMode=="CSS1Compat";
F.stdMode=I.documentMode===8;
this.settings=G=C.extend({keep_values:false,hex_colors:1,process_html:1},G);
if(C.isIE6){try{I.execCommand("BackgroundImageCache",false,true)
}catch(H){F.cssFlicker=true
}}C.addUnload(F.destroy,F)
},getRoot:function(){var F=this,G=F.settings;
return(G&&F.get(G.root_element))||F.doc.body
},getViewPort:function(G){var H,F;
G=!G?this.win:G;
H=G.document;
F=this.boxModel?H.documentElement:H.body;
return{x:G.pageXOffset||F.scrollLeft,y:G.pageYOffset||F.scrollTop,w:G.innerWidth||F.clientWidth,h:G.innerHeight||F.clientHeight}
},getRect:function(I){var H,F=this,G;
I=F.get(I);
H=F.getPos(I);
G=F.getSize(I);
return{x:H.x,y:H.y,w:G.w,h:G.h}
},getSize:function(I){var G=this,F,H;
I=G.get(I);
F=G.getStyle(I,"width");
H=G.getStyle(I,"height");
if(F.indexOf("px")===-1){F=0
}if(H.indexOf("px")===-1){H=0
}return{w:parseInt(F)||I.offsetWidth||I.clientWidth,h:parseInt(H)||I.offsetHeight||I.clientHeight}
},is:function(G,F){return C.dom.Sizzle.matches(F,G.nodeType?[G]:G).length>0
},getParent:function(H,G,F){return this.getParents(H,G,F,false)
},getParents:function(M,J,H,L){var G=this,F,I=G.settings,K=[];
M=G.get(M);
L=L===undefined;
if(I.strict_root){H=H||G.getRoot()
}if(B(J,"string")){F=J;
if(J==="*"){J=function(N){return N.nodeType==1
}
}else{J=function(N){return G.is(N,F)
}
}}while(M){if(M==H||!M.nodeType||M.nodeType===9){break
}if(!J||J(M)){if(L){K.push(M)
}else{return M
}}M=M.parentNode
}return L?K:null
},get:function(F){var G;
if(F&&this.doc&&typeof (F)=="string"){G=F;
F=this.doc.getElementById(F);
if(F&&F.id!==G){return this.doc.getElementsByName(G)[1]
}}return F
},select:function(H,G){var F=this;
return C.dom.Sizzle(H,F.get(G)||F.get(F.settings.root_element)||F.doc,[])
},add:function(I,K,F,H,J){var G=this;
return this.run(I,function(N){var M,L;
M=B(K,"string")?G.doc.createElement(K):K;
G.setAttribs(M,F);
if(H){if(H.nodeType){M.appendChild(H)
}else{G.setHTML(M,H)
}}return !J?N.appendChild(M):M
})
},create:function(H,F,G){return this.add(this.doc.createElement(H),H,F,G,1)
},createHTML:function(K,F,I){var J="",H=this,G;
J+="<"+K;
for(G in F){if(F.hasOwnProperty(G)){J+=" "+G+'="'+H.encode(F[G])+'"'
}}if(C.is(I)){return J+">"+I+"</"+K+">"
}return J+" />"
},remove:function(H,F){var G=this;
return this.run(H,function(L){var K,J,I;
K=L.parentNode;
if(!K){return null
}if(F){for(I=L.childNodes.length-1;
I>=0;
I--){G.insertAfter(L.childNodes[I],L)
}}if(G.fixPsuedoLeaks){K=L.cloneNode(true);
F="IELeakGarbageBin";
J=G.get(F)||G.add(G.doc.body,"div",{id:F,style:"display:none"});
J.appendChild(L);
J.innerHTML="";
return K
}return K.removeChild(L)
})
},setStyle:function(I,F,G){var H=this;
return H.run(I,function(L){var K,J;
K=L.style;
F=F.replace(/-(\D)/g,function(N,M){return M.toUpperCase()
});
if(H.pixelStyles.test(F)&&(C.is(G,"number")||/^[\-0-9\.]+$/.test(G))){G+="px"
}switch(F){case"opacity":if(A){K.filter=G===""?"":"alpha(opacity="+(G*100)+")";
if(!I.currentStyle||!I.currentStyle.hasLayout){K.display="inline-block"
}}K[F]=K["-moz-opacity"]=K["-khtml-opacity"]=G||"";
break;
case"float":A?K.styleFloat=G:K.cssFloat=G;
break;
default:K[F]=G||""
}if(H.settings.update_styles){H.setAttrib(L,"mce_style")
}})
},getStyle:function(I,F,H){I=this.get(I);
if(!I){return false
}if(this.doc.defaultView&&H){F=F.replace(/[A-Z]/g,function(J){return"-"+J
});
try{return this.doc.defaultView.getComputedStyle(I,null).getPropertyValue(F)
}catch(G){return null
}}F=F.replace(/-(\D)/g,function(K,J){return J.toUpperCase()
});
if(F=="float"){F=A?"styleFloat":"cssFloat"
}if(I.currentStyle&&H){return I.currentStyle[F]
}return I.style[F]
},setStyles:function(I,J){var G=this,H=G.settings,F;
F=H.update_styles;
H.update_styles=0;
E(J,function(K,L){G.setStyle(I,L,K)
});
H.update_styles=F;
if(H.update_styles){G.setAttrib(I,H.cssText)
}},setAttrib:function(H,I,F){var G=this;
if(!H||!I){return 
}if(G.settings.strict){I=I.toLowerCase()
}return this.run(H,function(K){var J=G.settings;
switch(I){case"style":if(!B(F,"string")){E(F,function(L,M){G.setStyle(K,M,L)
});
return 
}if(J.keep_values){if(F&&!G._isRes(F)){K.setAttribute("mce_style",F,2)
}else{K.removeAttribute("mce_style",2)
}}K.style.cssText=F;
break;
case"class":K.className=F||"";
break;
case"src":case"href":if(J.keep_values){if(J.url_converter){F=J.url_converter.call(J.url_converter_scope||G,F,I,K)
}G.setAttrib(K,"mce_"+I,F,2)
}break;
case"shape":K.setAttribute("mce_style",F);
break
}if(B(F)&&F!==null&&F.length!==0){K.setAttribute(I,""+F,2)
}else{K.removeAttribute(I,2)
}})
},setAttribs:function(G,H){var F=this;
return this.run(G,function(I){E(H,function(J,K){F.setAttrib(I,K,J)
})
})
},getAttrib:function(I,J,H){var F,G=this;
I=G.get(I);
if(!I||I.nodeType!==1){return false
}if(!B(H)){H=""
}if(/^(src|href|style|coords|shape)$/.test(J)){F=I.getAttribute("mce_"+J);
if(F){return F
}}if(A&&G.props[J]){F=I[G.props[J]];
F=F&&F.nodeValue?F.nodeValue:F
}if(!F){F=I.getAttribute(J,2)
}if(J==="style"){F=F||I.style.cssText;
if(F){F=G.serializeStyle(G.parseStyle(F));
if(G.settings.keep_values&&!G._isRes(F)){I.setAttribute("mce_style",F)
}}}if(D&&J==="class"&&F){F=F.replace(/(apple|webkit)\-[a-z\-]+/gi,"")
}if(A){switch(J){case"rowspan":case"colspan":if(F===1){F=""
}break;
case"size":if(F==="+0"||F===20||F===0){F=""
}break;
case"width":case"height":case"vspace":case"checked":case"disabled":case"readonly":if(F===0){F=""
}break;
case"hspace":if(F===-1){F=""
}break;
case"maxlength":case"tabindex":if(F===32768||F===2147483647||F==="32768"){F=""
}break;
case"multiple":case"compact":case"noshade":case"nowrap":if(F===65535){return J
}return H;
case"shape":F=F.toLowerCase();
break;
default:if(J.indexOf("on")===0&&F){F=(""+F).replace(/^function\s+anonymous\(\)\s+\{\s+(.*)\s+\}$/,"$1")
}}}return(F!==undefined&&F!==null&&F!=="")?""+F:H
},getPos:function(M,I){var G=this,F=0,L=0,J,K=G.doc,H;
M=G.get(M);
I=I||K.body;
if(M){if(A&&!G.stdMode){M=M.getBoundingClientRect();
J=G.boxModel?K.documentElement:K.body;
F=G.getStyle(G.select("html")[0],"borderWidth");
F=(F=="medium"||G.boxModel&&!G.isIE6)&&2||F;
M.top+=G.win.self!=G.win.top?2:0;
return{x:M.left+J.scrollLeft-F,y:M.top+J.scrollTop-F}
}H=M;
while(H&&H!=I&&H.nodeType){F+=H.offsetLeft||0;
L+=H.offsetTop||0;
H=H.offsetParent
}H=M.parentNode;
while(H&&H!=I&&H.nodeType){F-=H.scrollLeft||0;
L-=H.scrollTop||0;
H=H.parentNode
}}return{x:F,y:L}
},parseStyle:function(H){var I=this,J=I.settings,K={};
if(!H){return K
}function F(R,O,Q){var N,P,L,M;
N=K[R+"-top"+O];
if(!N){return 
}P=K[R+"-right"+O];
if(N!=P){return 
}L=K[R+"-bottom"+O];
if(P!=L){return 
}M=K[R+"-left"+O];
if(L!=M){return 
}K[Q]=M;
delete K[R+"-top"+O];
delete K[R+"-right"+O];
delete K[R+"-bottom"+O];
delete K[R+"-left"+O]
}function G(N,M,L,P){var O;
O=K[M];
if(!O){return 
}O=K[L];
if(!O){return 
}O=K[P];
if(!O){return 
}K[N]=K[M]+" "+K[L]+" "+K[P];
delete K[M];
delete K[L];
delete K[P]
}H=H.replace(/&(#?[a-z0-9]+);/g,"&$1_MCE_SEMI_");
E(H.split(";"),function(M){var L,N=[];
if(M){M=M.replace(/_MCE_SEMI_/g,";");
M=M.replace(/url\([^\)]+\)/g,function(O){N.push(O);
return"url("+N.length+")"
});
M=M.split(":");
L=C.trim(M[1]);
L=L.replace(/url\(([^\)]+)\)/g,function(P,O){return N[parseInt(O)-1]
});
L=L.replace(/rgb\([^\)]+\)/g,function(O){return I.toHex(O)
});
if(J.url_converter){L=L.replace(/url\([\'\"]?([^\)\'\"]+)[\'\"]?\)/g,function(O,P){return"url("+J.url_converter.call(J.url_converter_scope||I,I.decode(P),"style",null)+")"
})
}K[C.trim(M[0]).toLowerCase()]=L
}});
F("border","","border");
F("border","-width","border-width");
F("border","-color","border-color");
F("border","-style","border-style");
F("padding","","padding");
F("margin","","margin");
G("border","border-width","border-style","border-color");
if(A){if(K.border=="medium none"){K.border=""
}}return K
},serializeStyle:function(G){var F="";
E(G,function(I,H){if(H&&I){if(C.isGecko&&H.indexOf("-moz-")===0){return 
}switch(H){case"color":case"background-color":I=I.toLowerCase();
break
}F+=(F?" ":"")+H+": "+I+";"
}});
return F
},loadCSS:function(F){var G=this,H=G.doc;
if(!F){F=""
}E(F.split(","),function(I){if(G.files[I]){return 
}G.files[I]=true;
G.add(G.select("head")[0],"link",{rel:"stylesheet",href:C._addVer(I)})
})
},addClass:function(F,G){return this.run(F,function(H){var I;
if(!G){return 0
}if(this.hasClass(H,G)){return H.className
}I=this.removeClass(H,G);
return H.className=(I!=""?(I+" "):"")+G
})
},removeClass:function(H,I){var F=this,G;
return F.run(H,function(K){var J;
if(F.hasClass(K,I)){if(!G){G=new RegExp("(^|\\s+)"+I+"(\\s+|$)","g")
}J=K.className.replace(G," ");
return K.className=C.trim(J!=" "?J:"")
}return K.className
})
},hasClass:function(G,F){G=this.get(G);
if(!G||!F){return false
}return(" "+G.className+" ").indexOf(" "+F+" ")!==-1
},show:function(F){return this.setStyle(F,"display","block")
},hide:function(F){return this.setStyle(F,"display","none")
},isHidden:function(F){F=this.get(F);
return !F||F.style.display=="none"||this.getStyle(F,"display")=="none"
},uniqueId:function(F){return(!F?"mce_":F)+(this.counter++)
},setHTML:function(H,G){var F=this;
return this.run(H,function(M){var I,K,J,O,L,I;
G=F.processHTML(G);
if(A){function N(){try{M.innerHTML="<br />"+G;
M.removeChild(M.firstChild)
}catch(P){while(M.firstChild){M.firstChild.removeNode()
}I=F.create("div");
I.innerHTML="<br />"+G;
E(I.childNodes,function(R,Q){if(Q){M.appendChild(R)
}})
}}if(F.settings.fix_ie_paragraphs){G=G.replace(/<p><\/p>|<p([^>]+)><\/p>|<p[^\/+]\/>/gi,'<p$1 mce_keep="true">&nbsp;</p>')
}N();
if(F.settings.fix_ie_paragraphs){J=M.getElementsByTagName("p");
for(K=J.length-1,I=0;
K>=0;
K--){O=J[K];
if(!O.hasChildNodes()){if(!O.mce_keep){I=1;
break
}O.removeAttribute("mce_keep")
}}}if(I){G=G.replace(/<p ([^>]+)>|<p>/g,'<div $1 mce_tmp="1">');
G=G.replace(/<\/p>/g,"</div>");
N();
if(F.settings.fix_ie_paragraphs){J=M.getElementsByTagName("DIV");
for(K=J.length-1;
K>=0;
K--){O=J[K];
if(O.mce_tmp){L=F.doc.createElement("p");
O.cloneNode(false).outerHTML.replace(/([a-z0-9\-_]+)=/gi,function(Q,P){var R;
if(P!=="mce_tmp"){R=O.getAttribute(P);
if(!R&&P==="class"){R=O.className
}L.setAttribute(P,R)
}});
for(I=0;
I<O.childNodes.length;
I++){L.appendChild(O.childNodes[I].cloneNode(true))
}O.swapNode(L)
}}}}}else{M.innerHTML=G
}return G
})
},processHTML:function(I){var G=this,H=G.settings;
if(!H.process_html){return I
}if(C.isGecko){I=I.replace(/<(\/?)strong>|<strong( [^>]+)>/gi,"<$1b$2>");
I=I.replace(/<(\/?)em>|<em( [^>]+)>/gi,"<$1i$2>")
}else{if(A){I=I.replace(/&apos;/g,"&#39;");
I=I.replace(/\s+(disabled|checked|readonly|selected)\s*=\s*[\"\']?(false|0)[\"\']?/gi,"")
}}I=I.replace(/<a( )([^>]+)\/>|<a\/>/gi,"<a$1$2></a>");
if(H.keep_values){if(/<script|style/.test(I)){function F(J){J=J.replace(/(<!--\[CDATA\[|\]\]-->)/g,"\n");
J=J.replace(/^[\r\n]*|[\r\n]*$/g,"");
J=J.replace(/^\s*(\/\/\s*<!--|\/\/\s*<!\[CDATA\[|<!--|<!\[CDATA\[)[\r\n]*/g,"");
J=J.replace(/\s*(\/\/\s*\]\]>|\/\/\s*-->|\]\]>|-->|\]\]-->)\s*$/g,"");
return J
}I=I.replace(/<script([^>]+|)>([\s\S]*?)<\/script>/g,function(L,K,J){J=F(J);
if(!K){K=' type="text/javascript"'
}if(J){J="<!--\n"+J+"\n// -->"
}return"<mce:script"+K+">"+J+"</mce:script>"
});
I=I.replace(/<style([^>]+|)>([\s\S]*?)<\/style>/g,function(L,K,J){J=F(J);
return"<mce:style"+K+"><!--\n"+J+"\n--></mce:style><style"+K+' mce_bogus="1">'+J+"</style>"
})
}I=I.replace(/<!\[CDATA\[([\s\S]+)\]\]>/g,"<!--[CDATA[$1]]-->");
I=I.replace(/<([\w:]+) [^>]*(src|href|style|shape|coords)[^>]*>/gi,function(J,L){function K(N,M,P){var O=P;
if(J.indexOf("mce_"+M)!=-1){return N
}if(M=="style"){if(G._isRes(P)){return N
}if(H.hex_colors){O=O.replace(/rgb\([^\)]+\)/g,function(Q){return G.toHex(Q)
})
}if(H.url_converter){O=O.replace(/url\([\'\"]?([^\)\'\"]+)\)/g,function(Q,R){return"url("+G.encode(H.url_converter.call(H.url_converter_scope||G,G.decode(R),M,L))+")"
})
}}else{if(M!="coords"&&M!="shape"){if(H.url_converter){O=G.encode(H.url_converter.call(H.url_converter_scope||G,G.decode(P),M,L))
}}}return" "+M+'="'+P+'" mce_'+M+'="'+O+'"'
}J=J.replace(/ (src|href|style|coords|shape)=[\"]([^\"]+)[\"]/gi,K);
J=J.replace(/ (src|href|style|coords|shape)=[\']([^\']+)[\']/gi,K);
return J.replace(/ (src|href|style|coords|shape)=([^\s\"\'>]+)/gi,K)
})
}return I
},getOuterHTML:function(F){var G;
F=this.get(F);
if(!F){return null
}if(F.outerHTML!==undefined){return F.outerHTML
}G=(F.ownerDocument||this.doc).createElement("body");
G.appendChild(F.cloneNode(true));
return G.innerHTML
},setOuterHTML:function(H,G,I){var F=this;
return this.run(H,function(J){var L,K;
J=F.get(J);
I=I||J.ownerDocument||F.doc;
if(A&&J.nodeType==1){J.outerHTML=G
}else{K=I.createElement("body");
K.innerHTML=G;
L=K.lastChild;
while(L){F.insertAfter(L.cloneNode(true),J);
L=L.previousSibling
}F.remove(J)
}})
},decode:function(G){var H,I,F;
if(/&[^;]+;/.test(G)){H=this.doc.createElement("div");
H.innerHTML=G;
I=H.firstChild;
F="";
if(I){do{F+=I.nodeValue
}while(I.nextSibling)
}return F||G
}return G
},encode:function(F){return F?(""+F).replace(/[<>&\"]/g,function(H,G){switch(H){case"&":return"&amp;";
case'"':return"&quot;";
case"<":return"&lt;";
case">":return"&gt;"
}return H
}):F
},insertAfter:function(H,G){var F=this;
G=F.get(G);
return this.run(H,function(K){var J,I;
J=G.parentNode;
I=G.nextSibling;
if(I){J.insertBefore(K,I)
}else{J.appendChild(K)
}return K
})
},isBlock:function(F){if(F.nodeType&&F.nodeType!==1){return false
}F=F.nodeName||F;
return/^(H[1-6]|HR|P|DIV|ADDRESS|PRE|FORM|TABLE|LI|OL|UL|TR|TD|CAPTION|BLOCKQUOTE|CENTER|DL|DT|DD|DIR|FIELDSET|NOSCRIPT|NOFRAMES|MENU|ISINDEX|SAMP)$/.test(F)
},replace:function(I,H,F){var G=this;
if(B(H,"array")){I=I.cloneNode(true)
}return G.run(H,function(J){if(F){E(J.childNodes,function(K){I.appendChild(K.cloneNode(true))
})
}if(G.fixPsuedoLeaks&&J.nodeType===1){J.parentNode.insertBefore(I,J);
G.remove(J);
return I
}return J.parentNode.replaceChild(I,J)
})
},findCommonAncestor:function(H,F){var I=H,G;
while(I){G=F;
while(G&&I!=G){G=G.parentNode
}if(I==G){break
}I=I.parentNode
}if(!I&&H.ownerDocument){return H.ownerDocument.documentElement
}return I
},toHex:function(F){var H=/^\s*rgb\s*?\(\s*?([0-9]+)\s*?,\s*?([0-9]+)\s*?,\s*?([0-9]+)\s*?\)\s*$/i.exec(F);
function G(I){I=parseInt(I).toString(16);
return I.length>1?I:"0"+I
}if(H){F="#"+G(H[1])+G(H[2])+G(H[3]);
return F
}return F
},getClasses:function(){var J=this,F=[],I,K={},L=J.settings.class_filter,H;
if(J.classes){return J.classes
}function M(N){E(N.imports,function(O){M(O)
});
E(N.cssRules||N.rules,function(O){switch(O.type||1){case 1:if(O.selectorText){E(O.selectorText.split(","),function(P){P=P.replace(/^\s*|\s*$|^\s\./g,"");
if(/\.mce/.test(P)||!/\.[\w\-]+$/.test(P)){return 
}H=P;
P=P.replace(/.*\.([a-z0-9_\-]+).*/i,"$1");
if(L&&!(P=L(P,H))){return 
}if(!K[P]){F.push({"class":P});
K[P]=1
}})
}break;
case 3:M(O.styleSheet);
break
}})
}try{E(J.doc.styleSheets,M)
}catch(G){}if(F.length>0){J.classes=F
}return F
},run:function(I,H,G){var F=this,J;
if(F.doc&&typeof (I)==="string"){I=F.get(I)
}if(!I){return false
}G=G||this;
if(!I.nodeType&&(I.length||I.length===0)){J=[];
E(I,function(L,K){if(L){if(typeof (L)=="string"){L=F.doc.getElementById(L)
}J.push(H.call(G,L,K))
}});
return J
}return H.call(G,I)
},getAttribs:function(G){var F;
G=this.get(G);
if(!G){return[]
}if(A){F=[];
if(G.nodeName=="OBJECT"){return G.attributes
}G.cloneNode(false).outerHTML.replace(/([a-z0-9\:\-_]+)=/gi,function(I,H){F.push({specified:1,nodeName:H})
});
return F
}return G.attributes
},destroy:function(G){var F=this;
F.win=F.doc=F.root=null;
if(!G){C.removeUnload(F.destroy)
}},createRng:function(){var F=this.doc;
return F.createRange?F.createRange():new C.dom.Range(this)
},split:function(K,J,N){var O=this,F=O.createRng(),L,I,M;
function G(Q,P){Q=Q[P];
if(Q&&Q[P]&&Q[P].nodeType==1&&H(Q[P])){O.remove(Q[P])
}}function H(P){P=O.getOuterHTML(P);
P=P.replace(/<(img|hr|table)/gi,"-");
P=P.replace(/<[^>]+>/g,"");
return P.replace(/[ \t\r\n]+|&nbsp;|&#160;/g,"")==""
}if(K&&J){F.setStartBefore(K);
F.setEndBefore(J);
L=F.extractContents();
F=O.createRng();
F.setStartAfter(J);
F.setEndAfter(K);
I=F.extractContents();
M=K.parentNode;
G(L,"lastChild");
if(!H(L)){M.insertBefore(L,K)
}if(N){M.replaceChild(N,J)
}else{M.insertBefore(J,K)
}G(I,"firstChild");
if(!H(I)){M.insertBefore(I,K)
}O.remove(K);
return N||J
}},_isRes:function(F){return/^(top|left|bottom|right|width|height)/i.test(F)||/;\s*(top|left|bottom|right|width|height)/i.test(F)
}});
C.DOM=new C.dom.DOMUtils(document,{process_html:0})
})(tinymce);
(function(F){var H=0,C=1,E=2,D=tinymce.extend;
function G(M,K){var J,L;
if(M.parentNode!=K){return -1
}for(L=K.firstChild,J=0;
L!=M;
L=L.nextSibling){J++
}return J
}function B(K){var J=0;
while(K.previousSibling){J++;
K=K.previousSibling
}return J
}function I(J,K){var L;
if(J.nodeType==3){return J
}if(K<0){return J
}L=J.firstChild;
while(L!=null&&K>0){--K;
L=L.nextSibling
}if(L!=null){return L
}return J
}function A(K){var J=K.doc;
D(this,{dom:K,startContainer:J,startOffset:0,endContainer:J,endOffset:0,collapsed:true,commonAncestorContainer:J,START_TO_START:0,START_TO_END:1,END_TO_END:2,END_TO_START:3})
}D(A.prototype,{setStart:function(K,J){this._setEndPoint(true,K,J)
},setEnd:function(K,J){this._setEndPoint(false,K,J)
},setStartBefore:function(J){this.setStart(J.parentNode,B(J))
},setStartAfter:function(J){this.setStart(J.parentNode,B(J)+1)
},setEndBefore:function(J){this.setEnd(J.parentNode,B(J))
},setEndAfter:function(J){this.setEnd(J.parentNode,B(J)+1)
},collapse:function(K){var J=this;
if(K){J.endContainer=J.startContainer;
J.endOffset=J.startOffset
}else{J.startContainer=J.endContainer;
J.startOffset=J.endOffset
}J.collapsed=true
},selectNode:function(J){this.setStartBefore(J);
this.setEndAfter(J)
},selectNodeContents:function(J){this.setStart(J,0);
this.setEnd(J,J.nodeType===1?J.childNodes.length:J.nodeValue.length)
},compareBoundaryPoints:function(M,N){var L=this,P=L.startContainer,O=L.startOffset,K=L.endContainer,J=L.endOffset;
if(M===0){return L._compareBoundaryPoints(P,O,P,O)
}if(M===1){return L._compareBoundaryPoints(P,O,K,J)
}if(M===2){return L._compareBoundaryPoints(K,J,K,J)
}if(M===3){return L._compareBoundaryPoints(K,J,P,O)
}},deleteContents:function(){this._traverse(E)
},extractContents:function(){return this._traverse(H)
},cloneContents:function(){return this._traverse(C)
},insertNode:function(M){var J=this,L,K;
if(M.nodeType===3||M.nodeType===4){L=J.startContainer.splitText(J.startOffset);
J.startContainer.parentNode.insertBefore(M,L)
}else{if(J.startContainer.childNodes.length>0){K=J.startContainer.childNodes[J.startOffset]
}J.startContainer.insertBefore(M,K)
}},surroundContents:function(L){var J=this,K=J.extractContents();
J.insertNode(L);
L.appendChild(K);
J.selectNode(L)
},cloneRange:function(){var J=this;
return D(new A(J.dom),{startContainer:J.startContainer,startOffset:J.startOffset,endContainer:J.endContainer,endOffset:J.endOffset,collapsed:J.collapsed,commonAncestorContainer:J.commonAncestorContainer})
},_isCollapsed:function(){return(this.startContainer==this.endContainer&&this.startOffset==this.endOffset)
},_compareBoundaryPoints:function(M,O,K,N){var P,L,J,Q,S,R;
if(M==K){if(O==N){return 0
}else{if(O<N){return -1
}else{return 1
}}}P=K;
while(P&&P.parentNode!=M){P=P.parentNode
}if(P){L=0;
J=M.firstChild;
while(J!=P&&L<O){L++;
J=J.nextSibling
}if(O<=L){return -1
}else{return 1
}}P=M;
while(P&&P.parentNode!=K){P=P.parentNode
}if(P){L=0;
J=K.firstChild;
while(J!=P&&L<N){L++;
J=J.nextSibling
}if(L<N){return -1
}else{return 1
}}Q=this.dom.findCommonAncestor(M,K);
S=M;
while(S&&S.parentNode!=Q){S=S.parentNode
}if(!S){S=Q
}R=K;
while(R&&R.parentNode!=Q){R=R.parentNode
}if(!R){R=Q
}if(S==R){return 0
}J=Q.firstChild;
while(J){if(J==S){return -1
}if(J==R){return 1
}J=J.nextSibling
}},_setEndPoint:function(K,O,N){var L=this,J,M;
if(K){L.startContainer=O;
L.startOffset=N
}else{L.endContainer=O;
L.endOffset=N
}J=L.endContainer;
while(J.parentNode){J=J.parentNode
}M=L.startContainer;
while(M.parentNode){M=M.parentNode
}if(M!=J){L.collapse(K)
}else{if(L._compareBoundaryPoints(L.startContainer,L.startOffset,L.endContainer,L.endOffset)>0){L.collapse(K)
}}L.collapsed=L._isCollapsed();
L.commonAncestorContainer=L.dom.findCommonAncestor(L.startContainer,L.endContainer)
},_traverse:function(Q){var R=this,P,M=0,T=0,K,O,L,N,J,S;
if(R.startContainer==R.endContainer){return R._traverseSameContainer(Q)
}for(P=R.endContainer,K=P.parentNode;
K!=null;
P=K,K=K.parentNode){if(K==R.startContainer){return R._traverseCommonStartContainer(P,Q)
}++M
}for(P=R.startContainer,K=P.parentNode;
K!=null;
P=K,K=K.parentNode){if(K==R.endContainer){return R._traverseCommonEndContainer(P,Q)
}++T
}O=T-M;
L=R.startContainer;
while(O>0){L=L.parentNode;
O--
}N=R.endContainer;
while(O<0){N=N.parentNode;
O++
}for(J=L.parentNode,S=N.parentNode;
J!=S;
J=J.parentNode,S=S.parentNode){L=J;
N=S
}return R._traverseCommonAncestors(L,N,Q)
},_traverseSameContainer:function(N){var Q=this,P,R,J,K,L,O,M;
if(N!=E){P=Q.dom.doc.createDocumentFragment()
}if(Q.startOffset==Q.endOffset){return P
}if(Q.startContainer.nodeType==3){R=Q.startContainer.nodeValue;
J=R.substring(Q.startOffset,Q.endOffset);
if(N!=C){Q.startContainer.deleteData(Q.startOffset,Q.endOffset-Q.startOffset);
Q.collapse(true)
}if(N==E){return null
}P.appendChild(Q.dom.doc.createTextNode(J));
return P
}K=I(Q.startContainer,Q.startOffset);
L=Q.endOffset-Q.startOffset;
while(L>0){O=K.nextSibling;
M=Q._traverseFullySelected(K,N);
if(P){P.appendChild(M)
}--L;
K=O
}if(N!=C){Q.collapse(true)
}return P
},_traverseCommonStartContainer:function(J,O){var R=this,Q,K,L,M,P,N;
if(O!=E){Q=R.dom.doc.createDocumentFragment()
}K=R._traverseRightBoundary(J,O);
if(Q){Q.appendChild(K)
}L=G(J,R.startContainer);
M=L-R.startOffset;
if(M<=0){if(O!=C){R.setEndBefore(J);
R.collapse(false)
}return Q
}K=J.previousSibling;
while(M>0){P=K.previousSibling;
N=R._traverseFullySelected(K,O);
if(Q){Q.insertBefore(N,Q.firstChild)
}--M;
K=P
}if(O!=C){R.setEndBefore(J);
R.collapse(false)
}return Q
},_traverseCommonEndContainer:function(M,O){var R=this,Q,N,J,K,P,L;
if(O!=E){Q=R.dom.doc.createDocumentFragment()
}J=R._traverseLeftBoundary(M,O);
if(Q){Q.appendChild(J)
}N=G(M,R.endContainer);
++N;
K=R.endOffset-N;
J=M.nextSibling;
while(K>0){P=J.nextSibling;
L=R._traverseFullySelected(J,O);
if(Q){Q.appendChild(L)
}--K;
J=P
}if(O!=C){R.setStartAfter(M);
R.collapse(true)
}return Q
},_traverseCommonAncestors:function(O,J,R){var U=this,L,T,N,P,Q,K,S,M;
if(R!=E){T=U.dom.doc.createDocumentFragment()
}L=U._traverseLeftBoundary(O,R);
if(T){T.appendChild(L)
}N=O.parentNode;
P=G(O,N);
Q=G(J,N);
++P;
K=Q-P;
S=O.nextSibling;
while(K>0){M=S.nextSibling;
L=U._traverseFullySelected(S,R);
if(T){T.appendChild(L)
}S=M;
--K
}L=U._traverseRightBoundary(J,R);
if(T){T.appendChild(L)
}if(R!=C){U.setStartAfter(O);
U.collapse(true)
}return T
},_traverseRightBoundary:function(P,Q){var S=this,L=I(S.endContainer,S.endOffset-1),R,O,N,J,K;
var M=L!=S.endContainer;
if(L==P){return S._traverseNode(L,M,false,Q)
}R=L.parentNode;
O=S._traverseNode(R,false,false,Q);
while(R!=null){while(L!=null){N=L.previousSibling;
J=S._traverseNode(L,M,false,Q);
if(Q!=E){O.insertBefore(J,O.firstChild)
}M=true;
L=N
}if(R==P){return O
}L=R.previousSibling;
R=R.parentNode;
K=S._traverseNode(R,false,false,Q);
if(Q!=E){K.appendChild(O)
}O=K
}return null
},_traverseLeftBoundary:function(P,Q){var S=this,M=I(S.startContainer,S.startOffset);
var N=M!=S.startContainer,R,O,L,J,K;
if(M==P){return S._traverseNode(M,N,true,Q)
}R=M.parentNode;
O=S._traverseNode(R,false,true,Q);
while(R!=null){while(M!=null){L=M.nextSibling;
J=S._traverseNode(M,N,true,Q);
if(Q!=E){O.appendChild(J)
}N=true;
M=L
}if(R==P){return O
}M=R.nextSibling;
R=R.parentNode;
K=S._traverseNode(R,false,true,Q);
if(Q!=E){K.appendChild(O)
}O=K
}return null
},_traverseNode:function(J,N,Q,R){var S=this,M,L,O,K,P;
if(N){return S._traverseFullySelected(J,R)
}if(J.nodeType==3){M=J.nodeValue;
if(Q){K=S.startOffset;
L=M.substring(K);
O=M.substring(0,K)
}else{K=S.endOffset;
L=M.substring(0,K);
O=M.substring(K)
}if(R!=C){J.nodeValue=O
}if(R==E){return null
}P=J.cloneNode(false);
P.nodeValue=L;
return P
}if(R==E){return null
}return J.cloneNode(false)
},_traverseFullySelected:function(L,K){var J=this;
if(K!=E){return K==C?L.cloneNode(true):L
}L.parentNode.removeChild(L);
return null
}});
F.Range=A
})(tinymce.dom);
(function(){function A(E){var D=this,H="\uFEFF",B,G;
function C(J,I){if(J&&I){if(J.item&&I.item&&J.item(0)===I.item(0)){return 1
}if(J.isEqual&&I.isEqual&&I.isEqual(J)){return 1
}}return 0
}function F(){var M=E.dom,J=E.getRng(),S=M.createRng(),P,K,N,Q,O,L;
function I(V){var T=V.parentNode.childNodes,U;
for(U=T.length-1;
U>=0;
U--){if(T[U]==V){return U
}}return -1
}function R(V){var T=J.duplicate(),b,Y,U,W,X=0,Z=0,a,c;
T.collapse(V);
b=T.parentElement();
T.pasteHTML(H);
U=b.childNodes;
for(Y=0;
Y<U.length;
Y++){W=U[Y];
if(Y>0&&(W.nodeType!==3||U[Y-1].nodeType!==3)){Z++
}if(W.nodeType===3){a=W.nodeValue.indexOf(H);
if(a!==-1){X+=a;
break
}X+=W.nodeValue.length
}else{X=0
}}T.moveStart("character",-1);
T.text="";
return{index:Z,offset:X,parent:b}
}N=J.item?J.item(0):J.parentElement();
if(N.ownerDocument!=M.doc){return S
}if(J.item||!N.hasChildNodes()){S.setStart(N.parentNode,I(N));
S.setEnd(S.startContainer,S.startOffset+1);
return S
}L=E.isCollapsed();
P=R(true);
K=R(false);
P.parent.normalize();
K.parent.normalize();
Q=P.parent.childNodes[Math.min(P.index,P.parent.childNodes.length-1)];
if(Q.nodeType!=3){S.setStart(P.parent,P.index)
}else{S.setStart(P.parent.childNodes[P.index],P.offset)
}O=K.parent.childNodes[Math.min(K.index,K.parent.childNodes.length-1)];
if(O.nodeType!=3){if(!L){K.index++
}S.setEnd(K.parent,K.index)
}else{S.setEnd(K.parent.childNodes[K.index],K.offset)
}if(!L){Q=S.startContainer;
if(Q.nodeType==1){S.setStart(Q,Math.min(S.startOffset,Q.childNodes.length))
}O=S.endContainer;
if(O.nodeType==1){S.setEnd(O,Math.min(S.endOffset,O.childNodes.length))
}}D.addRange(S);
return S
}this.addRange=function(J){var O,M=E.dom.doc.body,P,K,Q,L,N,I;
Q=J.startContainer;
L=J.startOffset;
N=J.endContainer;
I=J.endOffset;
O=M.createTextRange();
Q=Q.nodeType==1?Q.childNodes[Math.min(L,Q.childNodes.length-1)]:Q;
N=N.nodeType==1?N.childNodes[Math.min(L==I?I:I-1,N.childNodes.length-1)]:N;
if(Q==N&&Q.nodeType==1){if(/^(IMG|TABLE)$/.test(Q.nodeName)&&L!=I){O=M.createControlRange();
O.addElement(Q)
}else{O=M.createTextRange();
if(!Q.hasChildNodes()&&Q.canHaveHTML){Q.innerHTML=H
}O.moveToElementText(Q);
if(Q.innerHTML==H){O.collapse(true);
Q.removeChild(Q.firstChild)
}}if(L==I){O.collapse(I<=J.endContainer.childNodes.length-1)
}O.select();
return 
}function R(T,V){var U,S,W;
if(T.nodeType!=3){return -1
}U=T.nodeValue;
S=M.createTextRange();
T.nodeValue=U.substring(0,V)+H+U.substring(V);
S.moveToElementText(T.parentNode);
S.findText(H);
W=Math.abs(S.moveStart("character",-1048575));
T.nodeValue=U;
return W
}if(J.collapsed){pos=R(Q,L);
O=M.createTextRange();
O.move("character",pos);
O.select();
return 
}else{if(Q==N&&Q.nodeType==3){P=R(Q,L);
O.move("character",P);
O.moveEnd("character",I-L);
O.select();
return 
}P=R(Q,L);
K=R(N,I);
O=M.createTextRange();
if(P==-1){O.moveToElementText(Q);
P=0
}else{O.move("character",P)
}tmpRng=M.createTextRange();
if(K==-1){tmpRng.moveToElementText(N)
}else{tmpRng.move("character",K)
}O.setEndPoint("EndToEnd",tmpRng);
O.select();
return 
}};
this.getRangeAt=function(){if(!B||!C(G,E.getRng())){B=F();
G=E.getRng()
}return B
};
this.destroy=function(){G=B=null
}
}tinymce.dom.TridentSelection=A
})();
(function(){var O=/((?:\((?:\([^()]+\)|[^()]+)+\)|\[(?:\[[^[\]]*\]|['"][^'"]*['"]|[^[\]'"]+)+\]|\\.|[^ >+~,(\[\\]+)+|[>+~])(\s*,\s*)?/g,H=0,D=Object.prototype.toString,M=false;
var B=function(d,T,a,V){a=a||[];
var Q=T=T||document;
if(T.nodeType!==1&&T.nodeType!==9){return[]
}if(!d||typeof d!=="string"){return a
}var b=[],c,Y,g,f,Z,S,R=true,W=N(T);
O.lastIndex=0;
while((c=O.exec(d))!==null){b.push(c[1]);
if(c[2]){S=RegExp.rightContext;
break
}}if(b.length>1&&I.exec(d)){if(b.length===2&&E.relative[b[0]]){Y=F(b[0]+b[1],T)
}else{Y=E.relative[b[0]]?[T]:B(b.shift(),T);
while(b.length){d=b.shift();
if(E.relative[d]){d+=b.shift()
}Y=F(d,Y)
}}}else{if(!V&&b.length>1&&T.nodeType===9&&!W&&E.match.ID.test(b[0])&&!E.match.ID.test(b[b.length-1])){var h=B.find(b.shift(),T,W);
T=h.expr?B.filter(h.expr,h.set)[0]:h.set[0]
}if(T){var h=V?{expr:b.pop(),set:A(V)}:B.find(b.pop(),b.length===1&&(b[0]==="~"||b[0]==="+")&&T.parentNode?T.parentNode:T,W);
Y=h.expr?B.filter(h.expr,h.set):h.set;
if(b.length>0){g=A(Y)
}else{R=false
}while(b.length){var U=b.pop(),X=U;
if(!E.relative[U]){U=""
}else{X=b.pop()
}if(X==null){X=T
}E.relative[U](g,X,W)
}}else{g=b=[]
}}if(!g){g=Y
}if(!g){throw"Syntax error, unrecognized expression: "+(U||d)
}if(D.call(g)==="[object Array]"){if(!R){a.push.apply(a,g)
}else{if(T&&T.nodeType===1){for(var e=0;
g[e]!=null;
e++){if(g[e]&&(g[e]===true||g[e].nodeType===1&&G(T,g[e]))){a.push(Y[e])
}}}else{for(var e=0;
g[e]!=null;
e++){if(g[e]&&g[e].nodeType===1){a.push(Y[e])
}}}}}else{A(g,a)
}if(S){B(S,Q,a,V);
B.uniqueSort(a)
}return a
};
B.uniqueSort=function(R){if(C){M=false;
R.sort(C);
if(M){for(var Q=1;
Q<R.length;
Q++){if(R[Q]===R[Q-1]){R.splice(Q--,1)
}}}}};
B.matches=function(Q,R){return B(Q,null,null,R)
};
B.find=function(X,Q,Y){var W,U;
if(!X){return[]
}for(var T=0,S=E.order.length;
T<S;
T++){var V=E.order[T],U;
if((U=E.match[V].exec(X))){var R=RegExp.leftContext;
if(R.substr(R.length-1)!=="\\"){U[1]=(U[1]||"").replace(/\\/g,"");
W=E.find[V](U,Q,Y);
if(W!=null){X=X.replace(E.match[V],"");
break
}}}}if(!W){W=Q.getElementsByTagName("*")
}return{set:W,expr:X}
};
B.filter=function(a,Z,d,T){var S=a,f=[],X=Z,V,Q,W=Z&&Z[0]&&N(Z[0]);
while(a&&Z.length){for(var Y in E.filter){if((V=E.match[Y].exec(a))!=null){var R=E.filter[Y],e,c;
Q=false;
if(X==f){f=[]
}if(E.preFilter[Y]){V=E.preFilter[Y](V,X,d,f,T,W);
if(!V){Q=e=true
}else{if(V===true){continue
}}}if(V){for(var U=0;
(c=X[U])!=null;
U++){if(c){e=R(c,V,U,X);
var b=T^!!e;
if(d&&e!=null){if(b){Q=true
}else{X[U]=false
}}else{if(b){f.push(c);
Q=true
}}}}}if(e!==undefined){if(!d){X=f
}a=a.replace(E.match[Y],"");
if(!Q){return[]
}break
}}}if(a==S){if(Q==null){throw"Syntax error, unrecognized expression: "+a
}else{break
}}S=a
}return X
};
var E=B.selectors={order:["ID","NAME","TAG"],match:{ID:/#((?:[\w\u00c0-\uFFFF_-]|\\.)+)/,CLASS:/\.((?:[\w\u00c0-\uFFFF_-]|\\.)+)/,NAME:/\[name=['"]*((?:[\w\u00c0-\uFFFF_-]|\\.)+)['"]*\]/,ATTR:/\[\s*((?:[\w\u00c0-\uFFFF_-]|\\.)+)\s*(?:(\S?=)\s*(['"]*)(.*?)\3|)\s*\]/,TAG:/^((?:[\w\u00c0-\uFFFF\*_-]|\\.)+)/,CHILD:/:(only|nth|last|first)-child(?:\((even|odd|[\dn+-]*)\))?/,POS:/:(nth|eq|gt|lt|first|last|even|odd)(?:\((\d*)\))?(?=[^-]|$)/,PSEUDO:/:((?:[\w\u00c0-\uFFFF_-]|\\.)+)(?:\((['"]*)((?:\([^\)]+\)|[^\2\(\)]*)+)\2\))?/},attrMap:{"class":"className","for":"htmlFor"},attrHandle:{href:function(Q){return Q.getAttribute("href")
}},relative:{"+":function(X,Q,W){var U=typeof Q==="string",Y=U&&!/\W/.test(Q),V=U&&!Y;
if(Y&&!W){Q=Q.toUpperCase()
}for(var T=0,S=X.length,R;
T<S;
T++){if((R=X[T])){while((R=R.previousSibling)&&R.nodeType!==1){}X[T]=V||R&&R.nodeName===Q?R||false:R===Q
}}if(V){B.filter(Q,X,true)
}},">":function(W,R,X){var U=typeof R==="string";
if(U&&!/\W/.test(R)){R=X?R:R.toUpperCase();
for(var S=0,Q=W.length;
S<Q;
S++){var V=W[S];
if(V){var T=V.parentNode;
W[S]=T.nodeName===R?T:false
}}}else{for(var S=0,Q=W.length;
S<Q;
S++){var V=W[S];
if(V){W[S]=U?V.parentNode:V.parentNode===R
}}if(U){B.filter(R,W,true)
}}},"":function(T,R,V){var S=H++,Q=P;
if(!R.match(/\W/)){var U=R=V?R:R.toUpperCase();
Q=L
}Q("parentNode",R,S,T,U,V)
},"~":function(T,R,V){var S=H++,Q=P;
if(typeof R==="string"&&!R.match(/\W/)){var U=R=V?R:R.toUpperCase();
Q=L
}Q("previousSibling",R,S,T,U,V)
}},find:{ID:function(R,S,T){if(typeof S.getElementById!=="undefined"&&!T){var Q=S.getElementById(R[1]);
return Q?[Q]:[]
}},NAME:function(S,V,W){if(typeof V.getElementsByName!=="undefined"){var R=[],U=V.getElementsByName(S[1]);
for(var T=0,Q=U.length;
T<Q;
T++){if(U[T].getAttribute("name")===S[1]){R.push(U[T])
}}return R.length===0?null:R
}},TAG:function(Q,R){return R.getElementsByTagName(Q[1])
}},preFilter:{CLASS:function(T,R,S,Q,W,X){T=" "+T[1].replace(/\\/g,"")+" ";
if(X){return T
}for(var U=0,V;
(V=R[U])!=null;
U++){if(V){if(W^(V.className&&(" "+V.className+" ").indexOf(T)>=0)){if(!S){Q.push(V)
}}else{if(S){R[U]=false
}}}}return false
},ID:function(Q){return Q[1].replace(/\\/g,"")
},TAG:function(R,Q){for(var S=0;
Q[S]===false;
S++){}return Q[S]&&N(Q[S])?R[1]:R[1].toUpperCase()
},CHILD:function(Q){if(Q[1]=="nth"){var R=/(-?)(\d*)n((?:\+|-)?\d*)/.exec(Q[2]=="even"&&"2n"||Q[2]=="odd"&&"2n+1"||!/\D/.test(Q[2])&&"0n+"+Q[2]||Q[2]);
Q[2]=(R[1]+(R[2]||1))-0;
Q[3]=R[3]-0
}Q[0]=H++;
return Q
},ATTR:function(U,R,S,Q,V,W){var T=U[1].replace(/\\/g,"");
if(!W&&E.attrMap[T]){U[1]=E.attrMap[T]
}if(U[2]==="~="){U[4]=" "+U[4]+" "
}return U
},PSEUDO:function(U,R,S,Q,V){if(U[1]==="not"){if(U[3].match(O).length>1||/^\w/.test(U[3])){U[3]=B(U[3],null,null,R)
}else{var T=B.filter(U[3],R,S,true^V);
if(!S){Q.push.apply(Q,T)
}return false
}}else{if(E.match.POS.test(U[0])||E.match.CHILD.test(U[0])){return true
}}return U
},POS:function(Q){Q.unshift(true);
return Q
}},filters:{enabled:function(Q){return Q.disabled===false&&Q.type!=="hidden"
},disabled:function(Q){return Q.disabled===true
},checked:function(Q){return Q.checked===true
},selected:function(Q){Q.parentNode.selectedIndex;
return Q.selected===true
},parent:function(Q){return !!Q.firstChild
},empty:function(Q){return !Q.firstChild
},has:function(S,R,Q){return !!B(Q[3],S).length
},header:function(Q){return/h\d/i.test(Q.nodeName)
},text:function(Q){return"text"===Q.type
},radio:function(Q){return"radio"===Q.type
},checkbox:function(Q){return"checkbox"===Q.type
},file:function(Q){return"file"===Q.type
},password:function(Q){return"password"===Q.type
},submit:function(Q){return"submit"===Q.type
},image:function(Q){return"image"===Q.type
},reset:function(Q){return"reset"===Q.type
},button:function(Q){return"button"===Q.type||Q.nodeName.toUpperCase()==="BUTTON"
},input:function(Q){return/input|select|textarea|button/i.test(Q.nodeName)
}},setFilters:{first:function(R,Q){return Q===0
},last:function(S,R,Q,T){return R===T.length-1
},even:function(R,Q){return Q%2===0
},odd:function(R,Q){return Q%2===1
},lt:function(S,R,Q){return R<Q[3]-0
},gt:function(S,R,Q){return R>Q[3]-0
},nth:function(S,R,Q){return Q[3]-0==R
},eq:function(S,R,Q){return Q[3]-0==R
}},filter:{PSEUDO:function(W,S,T,X){var R=S[1],U=E.filters[R];
if(U){return U(W,T,S,X)
}else{if(R==="contains"){return(W.textContent||W.innerText||"").indexOf(S[3])>=0
}else{if(R==="not"){var V=S[3];
for(var T=0,Q=V.length;
T<Q;
T++){if(V[T]===W){return false
}}return true
}}}},CHILD:function(Q,T){var W=T[1],R=Q;
switch(W){case"only":case"first":while(R=R.previousSibling){if(R.nodeType===1){return false
}}if(W=="first"){return true
}R=Q;
case"last":while(R=R.nextSibling){if(R.nodeType===1){return false
}}return true;
case"nth":var S=T[2],Z=T[3];
if(S==1&&Z==0){return true
}var V=T[0],Y=Q.parentNode;
if(Y&&(Y.sizcache!==V||!Q.nodeIndex)){var U=0;
for(R=Y.firstChild;
R;
R=R.nextSibling){if(R.nodeType===1){R.nodeIndex=++U
}}Y.sizcache=V
}var X=Q.nodeIndex-Z;
if(S==0){return X==0
}else{return(X%S==0&&X/S>=0)
}}},ID:function(R,Q){return R.nodeType===1&&R.getAttribute("id")===Q
},TAG:function(R,Q){return(Q==="*"&&R.nodeType===1)||R.nodeName===Q
},CLASS:function(R,Q){return(" "+(R.className||R.getAttribute("class"))+" ").indexOf(Q)>-1
},ATTR:function(V,T){var S=T[1],Q=E.attrHandle[S]?E.attrHandle[S](V):V[S]!=null?V[S]:V.getAttribute(S),W=Q+"",U=T[2],R=T[4];
return Q==null?U==="!=":U==="="?W===R:U==="*="?W.indexOf(R)>=0:U==="~="?(" "+W+" ").indexOf(R)>=0:!R?W&&Q!==false:U==="!="?W!=R:U==="^="?W.indexOf(R)===0:U==="$="?W.substr(W.length-R.length)===R:U==="|="?W===R||W.substr(0,R.length+1)===R+"-":false
},POS:function(U,R,S,V){var Q=R[2],T=E.setFilters[Q];
if(T){return T(U,S,R,V)
}}}};
var I=E.match.POS;
for(var K in E.match){E.match[K]=new RegExp(E.match[K].source+/(?![^\[]*\])(?![^\(]*\))/.source)
}var A=function(R,Q){R=Array.prototype.slice.call(R);
if(Q){Q.push.apply(Q,R);
return Q
}return R
};
try{Array.prototype.slice.call(document.documentElement.childNodes)
}catch(J){A=function(U,T){var R=T||[];
if(D.call(U)==="[object Array]"){Array.prototype.push.apply(R,U)
}else{if(typeof U.length==="number"){for(var S=0,Q=U.length;
S<Q;
S++){R.push(U[S])
}}else{for(var S=0;
U[S];
S++){R.push(U[S])
}}}return R
}
}var C;
if(document.documentElement.compareDocumentPosition){C=function(R,Q){var S=R.compareDocumentPosition(Q)&4?-1:R===Q?0:1;
if(S===0){M=true
}return S
}
}else{if("sourceIndex" in document.documentElement){C=function(R,Q){var S=R.sourceIndex-Q.sourceIndex;
if(S===0){M=true
}return S
}
}else{if(document.createRange){C=function(T,R){var S=T.ownerDocument.createRange(),Q=R.ownerDocument.createRange();
S.setStart(T,0);
S.setEnd(T,0);
Q.setStart(R,0);
Q.setEnd(R,0);
var U=S.compareBoundaryPoints(Range.START_TO_END,Q);
if(U===0){M=true
}return U
}
}}}(function(){var R=document.createElement("div"),S="script"+(new Date).getTime();
R.innerHTML="<a name='"+S+"'/>";
var Q=document.documentElement;
Q.insertBefore(R,Q.firstChild);
if(!!document.getElementById(S)){E.find.ID=function(U,V,W){if(typeof V.getElementById!=="undefined"&&!W){var T=V.getElementById(U[1]);
return T?T.id===U[1]||typeof T.getAttributeNode!=="undefined"&&T.getAttributeNode("id").nodeValue===U[1]?[T]:undefined:[]
}};
E.filter.ID=function(V,T){var U=typeof V.getAttributeNode!=="undefined"&&V.getAttributeNode("id");
return V.nodeType===1&&U&&U.nodeValue===T
}
}Q.removeChild(R)
})();
(function(){var Q=document.createElement("div");
Q.appendChild(document.createComment(""));
if(Q.getElementsByTagName("*").length>0){E.find.TAG=function(R,V){var U=V.getElementsByTagName(R[1]);
if(R[1]==="*"){var T=[];
for(var S=0;
U[S];
S++){if(U[S].nodeType===1){T.push(U[S])
}}U=T
}return U
}
}Q.innerHTML="<a href='#'></a>";
if(Q.firstChild&&typeof Q.firstChild.getAttribute!=="undefined"&&Q.firstChild.getAttribute("href")!=="#"){E.attrHandle.href=function(R){return R.getAttribute("href",2)
}
}})();
if(document.querySelectorAll){(function(){var Q=B,S=document.createElement("div");
S.innerHTML="<p class='TEST'></p>";
if(S.querySelectorAll&&S.querySelectorAll(".TEST").length===0){return 
}B=function(W,V,T,U){V=V||document;
if(!U&&V.nodeType===9&&!N(V)){try{return A(V.querySelectorAll(W),T)
}catch(X){}}return Q(W,V,T,U)
};
for(var R in Q){B[R]=Q[R]
}})()
}if(document.getElementsByClassName&&document.documentElement.getElementsByClassName){(function(){var Q=document.createElement("div");
Q.innerHTML="<div class='test e'></div><div class='test'></div>";
if(Q.getElementsByClassName("e").length===0){return 
}Q.lastChild.className="e";
if(Q.getElementsByClassName("e").length===1){return 
}E.order.splice(1,0,"CLASS");
E.find.CLASS=function(R,S,T){if(typeof S.getElementsByClassName!=="undefined"&&!T){return S.getElementsByClassName(R[1])
}}
})()
}function L(R,W,V,a,X,Z){var Y=R=="previousSibling"&&!Z;
for(var T=0,S=a.length;
T<S;
T++){var Q=a[T];
if(Q){if(Y&&Q.nodeType===1){Q.sizcache=V;
Q.sizset=T
}Q=Q[R];
var U=false;
while(Q){if(Q.sizcache===V){U=a[Q.sizset];
break
}if(Q.nodeType===1&&!Z){Q.sizcache=V;
Q.sizset=T
}if(Q.nodeName===W){U=Q;
break
}Q=Q[R]
}a[T]=U
}}}function P(R,W,V,a,X,Z){var Y=R=="previousSibling"&&!Z;
for(var T=0,S=a.length;
T<S;
T++){var Q=a[T];
if(Q){if(Y&&Q.nodeType===1){Q.sizcache=V;
Q.sizset=T
}Q=Q[R];
var U=false;
while(Q){if(Q.sizcache===V){U=a[Q.sizset];
break
}if(Q.nodeType===1){if(!Z){Q.sizcache=V;
Q.sizset=T
}if(typeof W!=="string"){if(Q===W){U=true;
break
}}else{if(B.filter(W,[Q]).length>0){U=Q;
break
}}}Q=Q[R]
}a[T]=U
}}}var G=document.compareDocumentPosition?function(R,Q){return R.compareDocumentPosition(Q)&16
}:function(R,Q){return R!==Q&&(R.contains?R.contains(Q):true)
};
var N=function(Q){return Q.nodeType===9&&Q.documentElement.nodeName!=="HTML"||!!Q.ownerDocument&&Q.ownerDocument.documentElement.nodeName!=="HTML"
};
var F=function(Q,X){var T=[],U="",V,S=X.nodeType?[X]:X;
while((V=E.match.PSEUDO.exec(Q))){U+=V[0];
Q=Q.replace(E.match.PSEUDO,"")
}Q=E.relative[Q]?Q+"*":Q;
for(var W=0,R=S.length;
W<R;
W++){B(Q,S[W],T)
}return B.filter(U,T)
};
window.tinymce.dom.Sizzle=B
})();
(function(D){var F=D.each,C=D.DOM,B=D.isIE,E=D.isWebKit,A;
D.create("static tinymce.dom.Event",{inits:[],events:[],add:function(M,N,L,J){var G,H=this,I=H.events,K;
if(M&&M.hasOwnProperty&&M instanceof Array){K=[];
F(M,function(O){O=C.get(O);
K.push(H.add(O,N,L,J))
});
return K
}M=C.get(M);
if(!M){return 
}G=function(O){O=O||window.event;
if(O&&!O.target&&B){O.target=O.srcElement
}if(!J){return L(O)
}return L.call(J,O)
};
if(N=="unload"){D.unloads.unshift({func:G});
return G
}if(N=="init"){if(H.domLoaded){G()
}else{H.inits.push(G)
}return G
}I.push({obj:M,name:N,func:L,cfunc:G,scope:J});
H._add(M,N,G);
return L
},remove:function(L,M,K){var H=this,G=H.events,I=false,J;
if(L&&L.hasOwnProperty&&L instanceof Array){J=[];
F(L,function(N){N=C.get(N);
J.push(H.remove(N,M,K))
});
return J
}L=C.get(L);
F(G,function(O,N){if(O.obj==L&&O.name==M&&(!K||(O.func==K||O.cfunc==K))){G.splice(N,1);
H._remove(L,M,O.cfunc);
I=true;
return false
}});
return I
},clear:function(K){var I=this,G=I.events,H,J;
if(K){K=C.get(K);
for(H=G.length-1;
H>=0;
H--){J=G[H];
if(J.obj===K){I._remove(J.obj,J.name,J.cfunc);
J.obj=J.cfunc=null;
G.splice(H,1)
}}}},cancel:function(G){if(!G){return false
}this.stop(G);
return this.prevent(G)
},stop:function(G){if(G.stopPropagation){G.stopPropagation()
}else{G.cancelBubble=true
}return false
},prevent:function(G){if(G.preventDefault){G.preventDefault()
}else{G.returnValue=false
}return false
},_unload:function(){var G=A;
F(G.events,function(I,H){G._remove(I.obj,I.name,I.cfunc);
I.obj=I.cfunc=null
});
G.events=[];
G=null
},_add:function(H,I,G){if(H.attachEvent){H.attachEvent("on"+I,G)
}else{if(H.addEventListener){H.addEventListener(I,G,false)
}else{H["on"+I]=G
}}},_remove:function(I,J,H){if(I){try{if(I.detachEvent){I.detachEvent("on"+J,H)
}else{if(I.removeEventListener){I.removeEventListener(J,H,false)
}else{I["on"+J]=null
}}}catch(G){}}},_pageInit:function(){var G=A;
if(G.domLoaded){return 
}G._remove(window,"DOMContentLoaded",G._pageInit);
G.domLoaded=true;
F(G.inits,function(H){H()
});
G.inits=[]
},_wait:function(){if(window.tinyMCE_GZ&&tinyMCE_GZ.loaded){A.domLoaded=1;
return 
}if(document.attachEvent){document.attachEvent("onreadystatechange",function(){if(document.readyState==="complete"){document.detachEvent("onreadystatechange",arguments.callee);
A._pageInit()
}});
if(document.documentElement.doScroll&&window==window.top){(function(){if(A.domLoaded){return 
}try{document.documentElement.doScroll("left")
}catch(G){setTimeout(arguments.callee,0);
return 
}A._pageInit()
})()
}}else{if(document.addEventListener){A._add(window,"DOMContentLoaded",A._pageInit,A)
}}A._add(window,"load",A._pageInit,A)
}});
A=D.dom.Event;
A._wait();
D.addUnload(A._unload)
})(tinymce);
(function(A){var B=A.each;
A.create("tinymce.dom.Element",{Element:function(G,E){var C=this,F,D;
E=E||{};
C.id=G;
C.dom=F=E.dom||A.DOM;
C.settings=E;
if(!A.isIE){D=C.dom.get(C.id)
}B(["getPos","getRect","getParent","add","setStyle","getStyle","setStyles","setAttrib","setAttribs","getAttrib","addClass","removeClass","hasClass","getOuterHTML","setOuterHTML","remove","show","hide","isHidden","setHTML","get"],function(H){C[H]=function(){var I=[G],J;
for(J=0;
J<arguments.length;
J++){I.push(arguments[J])
}I=F[H].apply(F,I);
C.update(H);
return I
}
})
},on:function(E,D,C){return A.dom.Event.add(this.id,E,D,C)
},getXY:function(){return{x:parseInt(this.getStyle("left")),y:parseInt(this.getStyle("top"))}
},getSize:function(){var C=this.dom.get(this.id);
return{w:parseInt(this.getStyle("width")||C.clientWidth),h:parseInt(this.getStyle("height")||C.clientHeight)}
},moveTo:function(C,D){this.setStyles({left:C,top:D})
},moveBy:function(C,E){var D=this.getXY();
this.moveTo(D.x+C,D.y+E)
},resizeTo:function(C,D){this.setStyles({width:C,height:D})
},resizeBy:function(C,E){var D=this.getSize();
this.resizeTo(D.w+C,D.h+E)
},update:function(D){var E=this,C,F=E.dom;
if(A.isIE6&&E.settings.blocker){D=D||"";
if(D.indexOf("get")===0||D.indexOf("has")===0||D.indexOf("is")===0){return 
}if(D=="remove"){F.remove(E.blocker);
return 
}if(!E.blocker){E.blocker=F.uniqueId();
C=F.add(E.settings.container||F.getRoot(),"iframe",{id:E.blocker,style:"position:absolute;",frameBorder:0,src:'javascript:""'});
F.setStyle(C,"opacity",0)
}else{C=F.get(E.blocker)
}F.setStyle(C,"left",E.getStyle("left",1));
F.setStyle(C,"top",E.getStyle("top",1));
F.setStyle(C,"width",E.getStyle("width",1));
F.setStyle(C,"height",E.getStyle("height",1));
F.setStyle(C,"display",E.getStyle("display",1));
F.setStyle(C,"zIndex",parseInt(E.getStyle("zIndex",1)||0)-1)
}}})
})(tinymce);
(function(C){function E(F){return F.replace(/[\n\r]+/g,"")
}var B=C.is,A=C.isIE,D=C.each;
C.create("tinymce.dom.Selection",{Selection:function(I,H,G){var F=this;
F.dom=I;
F.win=H;
F.serializer=G;
D(["onBeforeSetContent","onBeforeGetContent","onSetContent","onGetContent"],function(J){F[J]=new C.util.Dispatcher(F)
});
if(!F.win.getSelection){F.tridentSel=new C.dom.TridentSelection(F)
}C.addUnload(F.destroy,F)
},getContent:function(G){var F=this,H=F.getRng(),L=F.dom.create("body"),J=F.getSel(),I,K,M;
G=G||{};
I=K="";
G.get=true;
G.format=G.format||"html";
F.onBeforeGetContent.dispatch(F,G);
if(G.format=="text"){return F.isCollapsed()?"":(H.text||(J.toString?J.toString():""))
}if(H.cloneContents){M=H.cloneContents();
if(M){L.appendChild(M)
}}else{if(B(H.item)||B(H.htmlText)){L.innerHTML=H.item?H.item(0).outerHTML:H.htmlText
}else{L.innerHTML=H.toString()
}}if(/^\s/.test(L.innerHTML)){I=" "
}if(/\s+$/.test(L.innerHTML)){K=" "
}G.getInner=true;
G.content=F.isCollapsed()?"":I+F.serializer.serialize(L,G)+K;
F.onGetContent.dispatch(F,G);
return G.content
},setContent:function(H,G){var F=this,I=F.getRng(),K,J=F.win.document;
G=G||{format:"html"};
G.set=true;
H=G.content=F.dom.processHTML(H);
F.onBeforeSetContent.dispatch(F,G);
H=G.content;
if(I.insertNode){H+='<span id="__caret">_</span>';
I.deleteContents();
I.insertNode(F.getRng().createContextualFragment(H));
K=F.dom.get("__caret");
I=J.createRange();
I.setStartBefore(K);
I.setEndAfter(K);
F.setRng(I);
F.dom.remove("__caret")
}else{if(I.item){J.execCommand("Delete",false,null);
I=F.getRng()
}I.pasteHTML(H)
}F.onSetContent.dispatch(F,G)
},getStart:function(){var F=this,G=F.getRng(),H;
if(A){if(G.item){return G.item(0)
}G=G.duplicate();
G.collapse(1);
H=G.parentElement();
if(H&&H.nodeName=="BODY"){return H.firstChild
}return H
}else{H=G.startContainer;
if(H.nodeName=="BODY"){return H.firstChild
}return F.dom.getParent(H,"*")
}},getEnd:function(){var F=this,G=F.getRng(),H;
if(A){if(G.item){return G.item(0)
}G=G.duplicate();
G.collapse(0);
H=G.parentElement();
if(H&&H.nodeName=="BODY"){return H.lastChild
}return H
}else{H=G.endContainer;
if(H.nodeName=="BODY"){return H.lastChild
}return F.dom.getParent(H,"*")
}},getBookmark:function(U){var J=this,M=J.getRng(),F,N,L,R=J.dom.getViewPort(J.win),S,P,W,O,T=-16777215,K,H=J.dom.getRoot(),G=0,I=0,V;
N=R.x;
L=R.y;
if(U=="simple"){return{rng:M,scrollX:N,scrollY:L}
}if(A){if(M.item){S=M.item(0);
D(J.dom.select(S.nodeName),function(Y,X){if(S==Y){P=X;
return false
}});
return{tag:S.nodeName,index:P,scrollX:N,scrollY:L}
}F=J.dom.doc.body.createTextRange();
F.moveToElementText(H);
F.collapse(true);
W=Math.abs(F.move("character",T));
F=M.duplicate();
F.collapse(true);
P=Math.abs(F.move("character",T));
F=M.duplicate();
F.collapse(false);
O=Math.abs(F.move("character",T))-P;
return{start:P-W,length:O,scrollX:N,scrollY:L}
}S=J.getNode();
K=J.getSel();
if(!K){return null
}if(S&&S.nodeName=="IMG"){return{scrollX:N,scrollY:L}
}function Q(Z,c,Y){var X=J.dom.doc.createTreeWalker(Z,NodeFilter.SHOW_TEXT,null,false),e,a=0,b={};
while((e=X.nextNode())!=null){if(e==c){b.start=a
}if(e==Y){b.end=a;
return b
}a+=E(e.nodeValue||"").length
}return null
}if(K.anchorNode==K.focusNode&&K.anchorOffset==K.focusOffset){S=Q(H,K.anchorNode,K.focusNode);
if(!S){return{scrollX:N,scrollY:L}
}E(K.anchorNode.nodeValue||"").replace(/^\s+/,function(X){G=X.length
});
return{start:Math.max(S.start+K.anchorOffset-G,0),end:Math.max(S.end+K.focusOffset-G,0),scrollX:N,scrollY:L,beg:K.anchorOffset-G==0}
}else{S=Q(H,M.startContainer,M.endContainer);
if(!S){return{scrollX:N,scrollY:L}
}return{start:Math.max(S.start+M.startOffset-G,0),end:Math.max(S.end+M.endOffset-I,0),scrollX:N,scrollY:L,beg:M.startOffset-G==0}
}},moveToBookmark:function(N){var O=this,G=O.getRng(),P=O.getSel(),J=O.dom.getRoot(),M,H,K;
function I(Q,S,a){var Y=O.dom.doc.createTreeWalker(Q,NodeFilter.SHOW_TEXT,null,false),U,R=0,X={},T,Z,W,V;
while((U=Y.nextNode())!=null){W=V=0;
K=U.nodeValue||"";
H=E(K).length;
R+=H;
if(R>=S&&!X.startNode){T=S-(R-H);
if(N.beg&&T>=H){continue
}X.startNode=U;
X.startOffset=T+V
}if(R>=a){X.endNode=U;
X.endOffset=a-(R-H)+V;
return X
}}return null
}if(!N){return false
}O.win.scrollTo(N.scrollX,N.scrollY);
if(A){if(G=N.rng){try{G.select()
}catch(L){}return true
}O.win.focus();
if(N.tag){G=J.createControlRange();
D(O.dom.select(N.tag),function(R,Q){if(Q==N.index){G.addElement(R)
}})
}else{try{if(N.start<0){return true
}G=P.createRange();
G.moveToElementText(J);
G.collapse(true);
G.moveStart("character",N.start);
G.moveEnd("character",N.length)
}catch(F){return true
}}try{G.select()
}catch(L){}return true
}if(!P){return false
}if(N.rng){P.removeAllRanges();
P.addRange(N.rng)
}else{if(B(N.start)&&B(N.end)){try{M=I(J,N.start,N.end);
if(M){G=O.dom.doc.createRange();
G.setStart(M.startNode,M.startOffset);
G.setEnd(M.endNode,M.endOffset);
P.removeAllRanges();
P.addRange(G)
}if(!C.isOpera){O.win.focus()
}}catch(L){}}}},select:function(G,L){var O=this,F=O.getRng(),P=O.getSel(),N,M,K,J=O.win.document;
function H(T,S){var R,Q;
if(T){R=J.createTreeWalker(T,NodeFilter.SHOW_TEXT,null,false);
while(T=R.nextNode()){Q=T;
if(C.trim(T.nodeValue).length!=0){if(S){return T
}else{Q=T
}}}}return Q
}if(A){try{N=J.body;
if(/^(IMG|TABLE)$/.test(G.nodeName)){F=N.createControlRange();
F.addElement(G)
}else{F=N.createTextRange();
F.moveToElementText(G)
}F.select()
}catch(I){}}else{if(L){M=H(G,1)||O.dom.select("br:first",G)[0];
K=H(G,0)||O.dom.select("br:last",G)[0];
if(M&&K){F=J.createRange();
if(M.nodeName=="BR"){F.setStartBefore(M)
}else{F.setStart(M,0)
}if(K.nodeName=="BR"){F.setEndBefore(K)
}else{F.setEnd(K,K.nodeValue.length)
}}else{F.selectNode(G)
}}else{F.selectNode(G)
}O.setRng(F)
}return G
},isCollapsed:function(){var F=this,H=F.getRng(),G=F.getSel();
if(!H||H.item){return false
}return !G||H.boundingWidth==0||H.collapsed
},collapse:function(F){var G=this,H=G.getRng(),I;
if(H.item){I=H.item(0);
H=this.win.document.body.createTextRange();
H.moveToElementText(I)
}H.collapse(!!F);
G.setRng(H)
},getSel:function(){var G=this,F=this.win;
return F.getSelection?F.getSelection():F.document.selection
},getRng:function(J){var G=this,H,I;
if(J&&G.tridentSel){return G.tridentSel.getRangeAt(0)
}try{if(H=G.getSel()){I=H.rangeCount>0?H.getRangeAt(0):(H.createRange?H.createRange():G.win.document.createRange())
}}catch(F){}if(!I){I=A?G.win.document.body.createTextRange():G.win.document.createRange()
}return I
},setRng:function(I){var H,G=this;
if(!G.tridentSel){H=G.getSel();
if(H){H.removeAllRanges();
H.addRange(I)
}}else{if(I.cloneRange){G.tridentSel.addRange(I);
return 
}try{I.select()
}catch(F){}}},setNode:function(G){var F=this;
F.setContent(F.dom.getOuterHTML(G));
return G
},getNode:function(){var F=this,H=F.getRng(),G=F.getSel(),I;
if(!A){if(!H){return F.dom.getRoot()
}I=H.commonAncestorContainer;
if(!H.collapsed){if(C.isWebKit&&G.anchorNode&&G.anchorNode.nodeType==1){return G.anchorNode.childNodes[G.anchorOffset]
}if(H.startContainer==H.endContainer){if(H.startOffset-H.endOffset<2){if(H.startContainer.hasChildNodes()){I=H.startContainer.childNodes[H.startOffset]
}}}}return F.dom.getParent(I,"*")
}return H.item?H.item(0):H.parentElement()
},getSelectedBlocks:function(G,F){var I=this,J=I.dom,M,H,L,K=[];
M=J.getParent(G||I.getStart(),J.isBlock);
H=J.getParent(F||I.getEnd(),J.isBlock);
if(M){K.push(M)
}if(M&&H&&M!=H){L=M;
while((L=L.nextSibling)&&L!=H){if(J.isBlock(L)){K.push(L)
}}}if(H&&M!=H){K.push(H)
}return K
},destroy:function(G){var F=this;
F.win=null;
if(F.tridentSel){F.tridentSel.destroy()
}if(!G){C.removeUnload(F.destroy)
}}})
})(tinymce);
(function(A){A.create("tinymce.dom.XMLWriter",{node:null,XMLWriter:function(C){function B(){var E=document.implementation;
if(!E||!E.createDocument){try{return new ActiveXObject("MSXML2.DOMDocument")
}catch(D){}try{return new ActiveXObject("Microsoft.XmlDom")
}catch(D){}}else{return E.createDocument("","",null)
}}this.doc=B();
this.valid=A.isOpera||A.isWebKit;
this.reset()
},reset:function(){var B=this,C=B.doc;
if(C.firstChild){C.removeChild(C.firstChild)
}B.node=C.appendChild(C.createElement("html"))
},writeStartElement:function(C){var B=this;
B.node=B.node.appendChild(B.doc.createElement(C))
},writeAttribute:function(C,B){if(this.valid){B=B.replace(/>/g,"%MCGT%")
}this.node.setAttribute(C,B)
},writeEndElement:function(){this.node=this.node.parentNode
},writeFullEndElement:function(){var B=this,C=B.node;
C.appendChild(B.doc.createTextNode(""));
B.node=C.parentNode
},writeText:function(B){if(this.valid){B=B.replace(/>/g,"%MCGT%")
}this.node.appendChild(this.doc.createTextNode(B))
},writeCDATA:function(B){this.node.appendChild(this.doc.createCDATA(B))
},writeComment:function(B){if(A.isIE){B=B.replace(/^\-|\-$/g," ")
}this.node.appendChild(this.doc.createComment(B.replace(/\-\-/g," ")))
},getContent:function(){var B;
B=this.doc.xml||new XMLSerializer().serializeToString(this.doc);
B=B.replace(/<\?[^?]+\?>|<html>|<\/html>|<html\/>|<!DOCTYPE[^>]+>/g,"");
B=B.replace(/ ?\/>/g," />");
if(this.valid){B=B.replace(/\%MCGT%/g,"&gt;")
}return B
}})
})(tinymce);
(function(A){A.create("tinymce.dom.StringWriter",{str:null,tags:null,count:0,settings:null,indent:null,StringWriter:function(B){this.settings=A.extend({indent_char:" ",indentation:1},B);
this.reset()
},reset:function(){this.indent="";
this.str="";
this.tags=[];
this.count=0
},writeStartElement:function(B){this._writeAttributesEnd();
this.writeRaw("<"+B);
this.tags.push(B);
this.inAttr=true;
this.count++;
this.elementCount=this.count
},writeAttribute:function(D,B){var C=this;
C.writeRaw(" "+C.encode(D)+'="'+C.encode(B)+'"')
},writeEndElement:function(){var B;
if(this.tags.length>0){B=this.tags.pop();
if(this._writeAttributesEnd(1)){this.writeRaw("</"+B+">")
}if(this.settings.indentation>0){this.writeRaw("\n")
}}},writeFullEndElement:function(){if(this.tags.length>0){this._writeAttributesEnd();
this.writeRaw("</"+this.tags.pop()+">");
if(this.settings.indentation>0){this.writeRaw("\n")
}}},writeText:function(B){this._writeAttributesEnd();
this.writeRaw(this.encode(B));
this.count++
},writeCDATA:function(B){this._writeAttributesEnd();
this.writeRaw("<![CDATA["+B+"]]>");
this.count++
},writeComment:function(B){this._writeAttributesEnd();
this.writeRaw("<!-- "+B+"-->");
this.count++
},writeRaw:function(B){this.str+=B
},encode:function(B){return B.replace(/[<>&"]/g,function(C){switch(C){case"<":return"&lt;";
case">":return"&gt;";
case"&":return"&amp;";
case'"':return"&quot;"
}return C
})
},getContent:function(){return this.str
},_writeAttributesEnd:function(B){if(!this.inAttr){return 
}this.inAttr=false;
if(B&&this.elementCount==this.count){this.writeRaw(" />");
return false
}this.writeRaw(">");
return true
}})
})(tinymce);
(function(E){var G=E.extend,F=E.each,B=E.util.Dispatcher,D=E.isIE,A=E.isGecko;
function C(H){return H.replace(/([?+*])/g,".$1")
}E.create("tinymce.dom.Serializer",{Serializer:function(J){var I=this;
I.key=0;
I.onPreProcess=new B(I);
I.onPostProcess=new B(I);
try{I.writer=new E.dom.XMLWriter()
}catch(H){I.writer=new E.dom.StringWriter()
}I.settings=J=G({dom:E.DOM,valid_nodes:0,node_filter:0,attr_filter:0,invalid_attrs:/^(mce_|_moz_)/,closed:/^(br|hr|input|meta|img|link|param|area)$/,entity_encoding:"named",entities:"160,nbsp,161,iexcl,162,cent,163,pound,164,curren,165,yen,166,brvbar,167,sect,168,uml,169,copy,170,ordf,171,laquo,172,not,173,shy,174,reg,175,macr,176,deg,177,plusmn,178,sup2,179,sup3,180,acute,181,micro,182,para,183,middot,184,cedil,185,sup1,186,ordm,187,raquo,188,frac14,189,frac12,190,frac34,191,iquest,192,Agrave,193,Aacute,194,Acirc,195,Atilde,196,Auml,197,Aring,198,AElig,199,Ccedil,200,Egrave,201,Eacute,202,Ecirc,203,Euml,204,Igrave,205,Iacute,206,Icirc,207,Iuml,208,ETH,209,Ntilde,210,Ograve,211,Oacute,212,Ocirc,213,Otilde,214,Ouml,215,times,216,Oslash,217,Ugrave,218,Uacute,219,Ucirc,220,Uuml,221,Yacute,222,THORN,223,szlig,224,agrave,225,aacute,226,acirc,227,atilde,228,auml,229,aring,230,aelig,231,ccedil,232,egrave,233,eacute,234,ecirc,235,euml,236,igrave,237,iacute,238,icirc,239,iuml,240,eth,241,ntilde,242,ograve,243,oacute,244,ocirc,245,otilde,246,ouml,247,divide,248,oslash,249,ugrave,250,uacute,251,ucirc,252,uuml,253,yacute,254,thorn,255,yuml,402,fnof,913,Alpha,914,Beta,915,Gamma,916,Delta,917,Epsilon,918,Zeta,919,Eta,920,Theta,921,Iota,922,Kappa,923,Lambda,924,Mu,925,Nu,926,Xi,927,Omicron,928,Pi,929,Rho,931,Sigma,932,Tau,933,Upsilon,934,Phi,935,Chi,936,Psi,937,Omega,945,alpha,946,beta,947,gamma,948,delta,949,epsilon,950,zeta,951,eta,952,theta,953,iota,954,kappa,955,lambda,956,mu,957,nu,958,xi,959,omicron,960,pi,961,rho,962,sigmaf,963,sigma,964,tau,965,upsilon,966,phi,967,chi,968,psi,969,omega,977,thetasym,978,upsih,982,piv,8226,bull,8230,hellip,8242,prime,8243,Prime,8254,oline,8260,frasl,8472,weierp,8465,image,8476,real,8482,trade,8501,alefsym,8592,larr,8593,uarr,8594,rarr,8595,darr,8596,harr,8629,crarr,8656,lArr,8657,uArr,8658,rArr,8659,dArr,8660,hArr,8704,forall,8706,part,8707,exist,8709,empty,8711,nabla,8712,isin,8713,notin,8715,ni,8719,prod,8721,sum,8722,minus,8727,lowast,8730,radic,8733,prop,8734,infin,8736,ang,8743,and,8744,or,8745,cap,8746,cup,8747,int,8756,there4,8764,sim,8773,cong,8776,asymp,8800,ne,8801,equiv,8804,le,8805,ge,8834,sub,8835,sup,8836,nsub,8838,sube,8839,supe,8853,oplus,8855,otimes,8869,perp,8901,sdot,8968,lceil,8969,rceil,8970,lfloor,8971,rfloor,9001,lang,9002,rang,9674,loz,9824,spades,9827,clubs,9829,hearts,9830,diams,338,OElig,339,oelig,352,Scaron,353,scaron,376,Yuml,710,circ,732,tilde,8194,ensp,8195,emsp,8201,thinsp,8204,zwnj,8205,zwj,8206,lrm,8207,rlm,8211,ndash,8212,mdash,8216,lsquo,8217,rsquo,8218,sbquo,8220,ldquo,8221,rdquo,8222,bdquo,8224,dagger,8225,Dagger,8240,permil,8249,lsaquo,8250,rsaquo,8364,euro",bool_attrs:/(checked|disabled|readonly|selected|nowrap)/,valid_elements:"*[*]",extended_valid_elements:0,valid_child_elements:0,invalid_elements:0,fix_table_elements:1,fix_list_elements:true,fix_content_duplication:true,convert_fonts_to_spans:false,font_size_classes:0,font_size_style_values:0,apply_source_formatting:0,indent_mode:"simple",indent_char:"\t",indent_levels:1,remove_linebreaks:1,remove_redundant_brs:1,element_format:"xhtml"},J);
I.dom=J.dom;
if(J.remove_redundant_brs){I.onPostProcess.add(function(K,L){L.content=L.content.replace(/(<br \/>\s*)+<\/(p|h[1-6]|div|li)>/gi,function(N,M,O){if(/^<br \/>\s*<\//.test(N)){return"</"+O+">"
}return N
})
})
}if(J.element_format=="html"){I.onPostProcess.add(function(K,L){L.content=L.content.replace(/<([^>]+) \/>/g,"<$1>")
})
}if(J.fix_list_elements){I.onPreProcess.add(function(R,O){var L,T,S=["ol","ul"],Q,P,N,K=/^(OL|UL)$/,U;
function M(X,Y){var V=Y.split(","),W;
while((X=X.previousSibling)!=null){for(W=0;
W<V.length;
W++){if(X.nodeName==V[W]){return X
}}}return null
}for(T=0;
T<S.length;
T++){L=I.dom.select(S[T],O.node);
for(Q=0;
Q<L.length;
Q++){P=L[Q];
N=P.parentNode;
if(K.test(N.nodeName)){U=M(P,"LI");
if(!U){U=I.dom.create("li");
U.innerHTML="&nbsp;";
U.appendChild(P);
N.insertBefore(U,N.firstChild)
}else{U.appendChild(P)
}}}}})
}if(J.fix_table_elements){I.onPreProcess.add(function(K,L){F(I.dom.select("p table",L.node),function(M){I.dom.split(I.dom.getParent(M,"p"),M)
})
})
}},setEntities:function(N){var L=this,I,K,H={},M="",J;
if(L.entityLookup){return 
}I=N.split(",");
for(K=0;
K<I.length;
K+=2){J=I[K];
if(J==34||J==38||J==60||J==62){continue
}H[String.fromCharCode(I[K])]=I[K+1];
J=parseInt(I[K]).toString(16);
M+="\\u"+"0000".substring(J.length)+J
}if(!M){L.settings.entity_encoding="raw";
return 
}L.entitiesRE=new RegExp("["+M+"]","g");
L.entityLookup=H
},setValidChildRules:function(H){this.childRules=null;
this.addValidChildRules(H)
},addValidChildRules:function(K){var J=this,L,H,I;
if(!K){return 
}L="A|BR|SPAN|BDO|MAP|OBJECT|IMG|TT|I|B|BIG|SMALL|EM|STRONG|DFN|CODE|Q|SAMP|KBD|VAR|CITE|ABBR|ACRONYM|SUB|SUP|#text|#comment";
H="A|BR|SPAN|BDO|OBJECT|APPLET|IMG|MAP|IFRAME|TT|I|B|U|S|STRIKE|BIG|SMALL|FONT|BASEFONT|EM|STRONG|DFN|CODE|Q|SAMP|KBD|VAR|CITE|ABBR|ACRONYM|SUB|SUP|INPUT|SELECT|TEXTAREA|LABEL|BUTTON|#text|#comment";
I="H[1-6]|P|DIV|ADDRESS|PRE|FORM|TABLE|LI|OL|UL|TD|CAPTION|BLOCKQUOTE|CENTER|DL|DT|DD|DIR|FIELDSET|FORM|NOSCRIPT|NOFRAMES|MENU|ISINDEX|SAMP";
F(K.split(","),function(N){var O=N.split(/\[|\]/),M;
N="";
F(O[1].split("|"),function(P){if(N){N+="|"
}switch(P){case"%itrans":P=H;
break;
case"%itrans_na":P=H.substring(2);
break;
case"%istrict":P=L;
break;
case"%istrict_na":P=L.substring(2);
break;
case"%btrans":P=I;
break;
case"%bstrict":P=I;
break
}N+=P
});
M=new RegExp("^("+N.toLowerCase()+")$","i");
F(O[0].split("/"),function(P){J.childRules=J.childRules||{};
J.childRules[P]=M
})
});
K="";
F(J.childRules,function(N,M){if(K){K+="|"
}K+=M
});
J.parentElementsRE=new RegExp("^("+K.toLowerCase()+")$","i")
},setRules:function(I){var H=this;
H._setup();
H.rules={};
H.wildRules=[];
H.validElements={};
return H.addRules(I)
},addRules:function(I){var H=this,J;
if(!I){return 
}H._setup();
F(I.split(","),function(M){var P=M.split(/\[|\]/),L=P[0].split("/"),Q,K,O,N=[];
if(J){K=E.extend([],J.attribs)
}if(P.length>1){F(P[1].split("|"),function(T){var R={},S;
K=K||[];
T=T.replace(/::/g,"~");
T=/^([!\-])?([\w*.?~_\-]+|)([=:<])?(.+)?$/.exec(T);
T[2]=T[2].replace(/~/g,":");
if(T[1]=="!"){Q=Q||[];
Q.push(T[2])
}if(T[1]=="-"){for(S=0;
S<K.length;
S++){if(K[S].name==T[2]){K.splice(S,1);
return 
}}}switch(T[3]){case"=":R.defaultVal=T[4]||"";
break;
case":":R.forcedVal=T[4];
break;
case"<":R.validVals=T[4].split("?");
break
}if(/[*.?]/.test(T[2])){O=O||[];
R.nameRE=new RegExp("^"+C(T[2])+"$");
O.push(R)
}else{R.name=T[2];
K.push(R)
}N.push(T[2])
})
}F(L,function(U,T){var V=U.charAt(0),S=1,R={};
if(J){if(J.noEmpty){R.noEmpty=J.noEmpty
}if(J.fullEnd){R.fullEnd=J.fullEnd
}if(J.padd){R.padd=J.padd
}}switch(V){case"-":R.noEmpty=true;
break;
case"+":R.fullEnd=true;
break;
case"#":R.padd=true;
break;
default:S=0
}L[T]=U=U.substring(S);
H.validElements[U]=1;
if(/[*.?]/.test(L[0])){R.nameRE=new RegExp("^"+C(L[0])+"$");
H.wildRules=H.wildRules||{};
H.wildRules.push(R)
}else{R.name=L[0];
if(L[0]=="@"){J=R
}H.rules[U]=R
}R.attribs=K;
if(Q){R.requiredAttribs=Q
}if(O){U="";
F(N,function(W){if(U){U+="|"
}U+="("+C(W)+")"
});
R.validAttribsRE=new RegExp("^"+U.toLowerCase()+"$");
R.wildAttribs=O
}})
});
I="";
F(H.validElements,function(L,K){if(I){I+="|"
}if(K!="@"){I+=K
}});
H.validElementsRE=new RegExp("^("+C(I.toLowerCase())+")$")
},findRule:function(L){var I=this,K=I.rules,H,J;
I._setup();
J=K[L];
if(J){return J
}K=I.wildRules;
for(H=0;
H<K.length;
H++){if(K[H].nameRE.test(L)){return K[H]
}}return null
},findAttribRule:function(H,K){var I,J=H.wildAttribs;
for(I=0;
I<J.length;
I++){if(J[I].nameRE.test(K)){return J[I]
}}return null
},serialize:function(K,J){var I,H=this;
H._setup();
J=J||{};
J.format=J.format||"html";
H.processObj=J;
K=K.cloneNode(true);
H.key=""+(parseInt(H.key)+1);
if(!J.no_events){J.node=K;
H.onPreProcess.dispatch(H,J)
}H.writer.reset();
H._serializeNode(K,J.getInner);
J.content=H.writer.getContent();
if(!J.no_events){H.onPostProcess.dispatch(H,J)
}H._postProcess(J);
J.node=null;
return E.trim(J.content)
},_postProcess:function(M){var H=this,J=H.settings,I=M.content,L=[],K;
if(M.format=="html"){K=H._protect({content:I,patterns:[{pattern:/(<script[^>]*>)(.*?)(<\/script>)/g},{pattern:/(<style[^>]*>)(.*?)(<\/style>)/g},{pattern:/(<pre[^>]*>)(.*?)(<\/pre>)/g,encode:1},{pattern:/(<!--\[CDATA\[)(.*?)(\]\]-->)/g}]});
I=K.content;
if(J.entity_encoding!=="raw"){I=H._encode(I)
}if(!M.set){I=I.replace(/<p>\s+<\/p>|<p([^>]+)>\s+<\/p>/g,J.entity_encoding=="numeric"?"<p$1>&#160;</p>":"<p$1>&nbsp;</p>");
if(J.remove_linebreaks){I=I.replace(/\r?\n|\r/g," ");
I=I.replace(/(<[^>]+>)\s+/g,"$1 ");
I=I.replace(/\s+(<\/[^>]+>)/g," $1");
I=I.replace(/<(p|h[1-6]|blockquote|hr|div|table|tbody|tr|td|body|head|html|title|meta|style|pre|script|link|object) ([^>]+)>\s+/g,"<$1 $2>");
I=I.replace(/<(p|h[1-6]|blockquote|hr|div|table|tbody|tr|td|body|head|html|title|meta|style|pre|script|link|object)>\s+/g,"<$1>");
I=I.replace(/\s+<\/(p|h[1-6]|blockquote|hr|div|table|tbody|tr|td|body|head|html|title|meta|style|pre|script|link|object)>/g,"</$1>")
}if(J.apply_source_formatting&&J.indent_mode=="simple"){I=I.replace(/<(\/?)(ul|hr|table|meta|link|tbody|tr|object|body|head|html|map)(|[^>]+)>\s*/g,"\n<$1$2$3>\n");
I=I.replace(/\s*<(p|h[1-6]|blockquote|div|title|style|pre|script|td|li|area)(|[^>]+)>/g,"\n<$1$2>");
I=I.replace(/<\/(p|h[1-6]|blockquote|div|title|style|pre|script|td|li)>\s*/g,"</$1>\n");
I=I.replace(/\n\n/g,"\n")
}}I=H._unprotect(I,K);
I=I.replace(/<!--\[CDATA\[([\s\S]+)\]\]-->/g,"<![CDATA[$1]]>");
if(J.entity_encoding=="raw"){I=I.replace(/<p>&nbsp;<\/p>|<p([^>]+)>&nbsp;<\/p>/g,"<p$1>\u00a0</p>")
}}M.content=I
},_serializeNode:function(V,K){var R=this,S=R.settings,P=R.writer,M,I,O,X,W,Y,T,H,Q,J,N,U,L;
if(!S.node_filter||S.node_filter(V)){switch(V.nodeType){case 1:if(V.hasAttribute?V.hasAttribute("mce_bogus"):V.getAttribute("mce_bogus")){return 
}L=false;
M=V.hasChildNodes();
J=V.getAttribute("mce_name")||V.nodeName.toLowerCase();
if(D){if(V.scopeName!=="HTML"&&V.scopeName!=="html"){J=V.scopeName+":"+J
}}if(J.indexOf("mce:")===0){J=J.substring(4)
}if(!R.validElementsRE.test(J)||(R.invalidElementsRE&&R.invalidElementsRE.test(J))||K){L=true;
break
}if(D){if(S.fix_content_duplication){if(V.mce_serialized==R.key){return 
}V.mce_serialized=R.key
}if(J.charAt(0)=="/"){J=J.substring(1)
}}else{if(A){if(V.nodeName==="BR"&&V.getAttribute("type")=="_moz"){return 
}}}if(R.childRules){if(R.parentElementsRE.test(R.elementName)){if(!R.childRules[R.elementName].test(J)){L=true;
break
}}R.elementName=J
}N=R.findRule(J);
J=N.name||J;
if((!M&&N.noEmpty)||(D&&!J)){L=true;
break
}if(N.requiredAttribs){Y=N.requiredAttribs;
for(X=Y.length-1;
X>=0;
X--){if(this.dom.getAttrib(V,Y[X])!==""){break
}}if(X==-1){L=true;
break
}}P.writeStartElement(J);
if(N.attribs){for(X=0,T=N.attribs,W=T.length;
X<W;
X++){Y=T[X];
Q=R._getAttrib(V,Y);
if(Q!==null){P.writeAttribute(Y.name,Q)
}}}if(N.validAttribsRE){T=R.dom.getAttribs(V);
for(X=T.length-1;
X>-1;
X--){H=T[X];
if(H.specified){Y=H.nodeName.toLowerCase();
if(S.invalid_attrs.test(Y)||!N.validAttribsRE.test(Y)){continue
}U=R.findAttribRule(N,Y);
Q=R._getAttrib(V,U,Y);
if(Q!==null){P.writeAttribute(Y,Q)
}}}}if(N.padd){if(M&&(O=V.firstChild)&&O.nodeType===1&&V.childNodes.length===1){if(O.hasAttribute?O.hasAttribute("mce_bogus"):O.getAttribute("mce_bogus")){P.writeText("\u00a0")
}}else{if(!M){P.writeText("\u00a0")
}}}break;
case 3:if(R.childRules&&R.parentElementsRE.test(R.elementName)){if(!R.childRules[R.elementName].test(V.nodeName)){return 
}}return P.writeText(V.nodeValue);
case 4:return P.writeCDATA(V.nodeValue);
case 8:return P.writeComment(V.nodeValue)
}}else{if(V.nodeType==1){M=V.hasChildNodes()
}}if(M){O=V.firstChild;
while(O){R._serializeNode(O);
R.elementName=J;
O=O.nextSibling
}}if(!L){if(M||!S.closed.test(J)){P.writeFullEndElement()
}else{P.writeEndElement()
}}},_protect:function(J){var I=this;
J.items=J.items||[];
function H(L){return L.replace(/[\r\n\\]/g,function(M){if(M==="\n"){return"\\n"
}else{if(M==="\\"){return"\\\\"
}}return"\\r"
})
}function K(L){return L.replace(/\\[\\rn]/g,function(M){if(M==="\\n"){return"\n"
}else{if(M==="\\\\"){return"\\"
}}return"\r"
})
}F(J.patterns,function(L){J.content=K(H(J.content).replace(L.pattern,function(N,O,M,P){M=K(M);
if(L.encode){M=I._encode(M)
}J.items.push(M);
return O+"<!--mce:"+(J.items.length-1)+"-->"+P
}))
});
return J
},_unprotect:function(H,I){H=H.replace(/\<!--mce:([0-9]+)--\>/g,function(K,J){return I.items[parseInt(J)]
});
I.items=[];
return H
},_encode:function(K){var I=this,J=I.settings,H;
if(J.entity_encoding!=="raw"){if(J.entity_encoding.indexOf("named")!=-1){I.setEntities(J.entities);
H=I.entityLookup;
K=K.replace(I.entitiesRE,function(L){var M;
if(M=H[L]){L="&"+M+";"
}return L
})
}if(J.entity_encoding.indexOf("numeric")!=-1){K=K.replace(/[\u007E-\uFFFF]/g,function(L){return"&#"+L.charCodeAt(0)+";"
})
}}return K
},_setup:function(){var H=this,I=this.settings;
if(H.done){return 
}H.done=1;
H.setRules(I.valid_elements);
H.addRules(I.extended_valid_elements);
H.addValidChildRules(I.valid_child_elements);
if(I.invalid_elements){H.invalidElementsRE=new RegExp("^("+C(I.invalid_elements.replace(/,/g,"|").toLowerCase())+")$")
}if(I.attrib_value_filter){H.attribValueFilter=I.attribValueFilter
}},_getAttrib:function(L,I,H){var K,J;
H=H||I.name;
if(I.forcedVal&&(J=I.forcedVal)){if(J==="{$uid}"){return this.dom.uniqueId()
}return J
}J=this.dom.getAttrib(L,H);
if(this.settings.bool_attrs.test(H)&&J){J=(""+J).toLowerCase();
if(J==="false"||J==="0"){return null
}J=H
}switch(H){case"rowspan":case"colspan":if(J=="1"){J=""
}break
}if(this.attribValueFilter){J=this.attribValueFilter(H,J,L)
}if(I.validVals){for(K=I.validVals.length-1;
K>=0;
K--){if(J==I.validVals[K]){break
}}if(K==-1){return null
}}if(J===""&&typeof (I.defaultVal)!="undefined"){J=I.defaultVal;
if(J==="{$uid}"){return this.dom.uniqueId()
}return J
}else{if(H=="class"&&this.processObj.get){J=J.replace(/\s?mceItem\w+\s?/g,"")
}}if(J===""){return null
}return J
}})
})(tinymce);
(function(tinymce){var each=tinymce.each,Event=tinymce.dom.Event;
tinymce.create("tinymce.dom.ScriptLoader",{ScriptLoader:function(s){this.settings=s||{};
this.queue=[];
this.lookup={}
},isDone:function(u){return this.lookup[u]?this.lookup[u].state==2:0
},markDone:function(u){this.lookup[u]={state:2,url:u}
},add:function(u,cb,s,pr){var t=this,lo=t.lookup,o;
if(o=lo[u]){if(cb&&o.state==2){cb.call(s||this)
}return o
}o={state:0,url:u,func:cb,scope:s||this};
if(pr){t.queue.unshift(o)
}else{t.queue.push(o)
}lo[u]=o;
return o
},load:function(u,cb,s){var t=this,o;
if(o=t.lookup[u]){if(cb&&o.state==2){cb.call(s||t)
}return o
}function loadScript(u){if(Event.domLoaded||t.settings.strict_mode){tinymce.util.XHR.send({url:tinymce._addVer(u),error:t.settings.error,async:false,success:function(co){t.eval(co)
}})
}else{document.write('<script type="text/javascript" src="'+tinymce._addVer(u)+'"><\/script>')
}}if(!tinymce.is(u,"string")){each(u,function(u){loadScript(u)
});
if(cb){cb.call(s||t)
}}else{loadScript(u);
if(cb){cb.call(s||t)
}}},loadQueue:function(cb,s){var t=this;
if(!t.queueLoading){t.queueLoading=1;
t.queueCallbacks=[];
t.loadScripts(t.queue,function(){t.queueLoading=0;
if(cb){cb.call(s||t)
}each(t.queueCallbacks,function(o){o.func.call(o.scope)
})
})
}else{if(cb){t.queueCallbacks.push({func:cb,scope:s||t})
}}},eval:function(co){var w=window;
if(!w.execScript){try{eval.call(w,co)
}catch(ex){eval(co,w)
}}else{w.execScript(co)
}},loadScripts:function(sc,cb,s){var t=this,lo=t.lookup;
function done(o){o.state=2;
if(o.func){o.func.call(o.scope||t)
}}function allDone(){var l;
l=sc.length;
each(sc,function(o){o=lo[o.url];
if(o.state===2){done(o);
l--
}else{load(o)
}});
if(l===0&&cb){cb.call(s||t);
cb=0
}}function load(o){if(o.state>0){return 
}o.state=1;
tinymce.dom.ScriptLoader.loadScript(o.url,function(){done(o);
allDone()
})
}each(sc,function(o){var u=o.url;
if(!lo[u]){lo[u]=o;
t.queue.push(o)
}else{o=lo[u]
}if(o.state>0){return 
}if(!Event.domLoaded&&!t.settings.strict_mode){var ix,ol="";
if(cb||o.func){o.state=1;
ix=tinymce.dom.ScriptLoader._addOnLoad(function(){done(o);
allDone()
});
if(tinymce.isIE){ol=' onreadystatechange="'
}else{ol=' onload="'
}ol+="tinymce.dom.ScriptLoader._onLoad(this,'"+u+"',"+ix+');"'
}document.write('<script type="text/javascript" src="'+tinymce._addVer(u)+'"'+ol+"><\/script>");
if(!o.func){done(o)
}}else{load(o)
}});
allDone()
},"static":{_addOnLoad:function(f){var t=this;
t._funcs=t._funcs||[];
t._funcs.push(f);
return t._funcs.length-1
},_onLoad:function(e,u,ix){if(!tinymce.isIE||e.readyState=="complete"){this._funcs[ix].call(this)
}},loadScript:function(u,cb){var id=tinymce.DOM.uniqueId(),e;
function done(){Event.clear(id);
tinymce.DOM.remove(id);
if(cb){cb.call(document,u);
cb=0
}}if(tinymce.isIE){tinymce.util.XHR.send({url:tinymce._addVer(u),async:false,success:function(co){window.execScript(co);
done()
}})
}else{e=tinymce.DOM.create("script",{id:id,type:"text/javascript",src:tinymce._addVer(u)});
Event.add(e,"load",done);
(document.getElementsByTagName("head")[0]||document.body).appendChild(e)
}}}});
tinymce.ScriptLoader=new tinymce.dom.ScriptLoader()
})(tinymce);
(function(C){var B=C.DOM,A=C.is;
C.create("tinymce.ui.Control",{Control:function(E,D){this.id=E;
this.settings=D=D||{};
this.rendered=false;
this.onRender=new C.util.Dispatcher(this);
this.classPrefix="";
this.scope=D.scope||this;
this.disabled=0;
this.active=0
},setDisabled:function(D){var E;
if(D!=this.disabled){E=B.get(this.id);
if(E&&this.settings.unavailable_prefix){if(D){this.prevTitle=E.title;
E.title=this.settings.unavailable_prefix+": "+E.title
}else{E.title=this.prevTitle
}}this.setState("Disabled",D);
this.setState("Enabled",!D);
this.disabled=D
}},isDisabled:function(){return this.disabled
},setActive:function(D){if(D!=this.active){this.setState("Active",D);
this.active=D
}},isActive:function(){return this.active
},setState:function(F,D){var E=B.get(this.id);
F=this.classPrefix+F;
if(D){B.addClass(E,F)
}else{B.removeClass(E,F)
}},isRendered:function(){return this.rendered
},renderHTML:function(){},renderTo:function(D){B.setHTML(D,this.renderHTML())
},postRender:function(){var E=this,D;
if(A(E.disabled)){D=E.disabled;
E.disabled=-1;
E.setDisabled(D)
}if(A(E.active)){D=E.active;
E.active=-1;
E.setActive(D)
}},remove:function(){B.remove(this.id);
this.destroy()
},destroy:function(){C.dom.Event.clear(this.id)
}})
})(tinymce);
tinymce.create("tinymce.ui.Container:tinymce.ui.Control",{Container:function(B,A){this.parent(B,A);
this.controls=[];
this.lookup={}
},add:function(A){this.lookup[A.id]=A;
this.controls.push(A);
return A
},get:function(A){return this.lookup[A]
}});
tinymce.create("tinymce.ui.Separator:tinymce.ui.Control",{Separator:function(B,A){this.parent(B,A);
this.classPrefix="mceSeparator"
},renderHTML:function(){return tinymce.DOM.createHTML("span",{"class":this.classPrefix})
}});
(function(D){var C=D.is,B=D.DOM,E=D.each,A=D.walk;
D.create("tinymce.ui.MenuItem:tinymce.ui.Control",{MenuItem:function(G,F){this.parent(G,F);
this.classPrefix="mceMenuItem"
},setSelected:function(F){this.setState("Selected",F);
this.selected=F
},isSelected:function(){return this.selected
},postRender:function(){var F=this;
F.parent();
if(C(F.selected)){F.setSelected(F.selected)
}}})
})(tinymce);
(function(D){var C=D.is,B=D.DOM,E=D.each,A=D.walk;
D.create("tinymce.ui.Menu:tinymce.ui.MenuItem",{Menu:function(H,G){var F=this;
F.parent(H,G);
F.items={};
F.collapsed=false;
F.menuCount=0;
F.onAddItem=new D.util.Dispatcher(this)
},expand:function(G){var F=this;
if(G){A(F,function(H){if(H.expand){H.expand()
}},"items",F)
}F.collapsed=false
},collapse:function(G){var F=this;
if(G){A(F,function(H){if(H.collapse){H.collapse()
}},"items",F)
}F.collapsed=true
},isCollapsed:function(){return this.collapsed
},add:function(F){if(!F.settings){F=new D.ui.MenuItem(F.id||B.uniqueId(),F)
}this.onAddItem.dispatch(this,F);
return this.items[F.id]=F
},addSeparator:function(){return this.add({separator:true})
},addMenu:function(F){if(!F.collapse){F=this.createMenu(F)
}this.menuCount++;
return this.add(F)
},hasMenus:function(){return this.menuCount!==0
},remove:function(F){delete this.items[F.id]
},removeAll:function(){var F=this;
A(F,function(G){if(G.removeAll){G.removeAll()
}else{G.remove()
}G.destroy()
},"items",F);
F.items={}
},createMenu:function(G){var F=new D.ui.Menu(G.id||B.uniqueId(),G);
F.onAddItem.add(this.onAddItem.dispatch,this.onAddItem);
return F
}})
})(tinymce);
(function(E){var D=E.is,C=E.DOM,F=E.each,A=E.dom.Event,B=E.dom.Element;
E.create("tinymce.ui.DropMenu:tinymce.ui.Menu",{DropMenu:function(H,G){G=G||{};
G.container=G.container||C.doc.body;
G.offset_x=G.offset_x||0;
G.offset_y=G.offset_y||0;
G.vp_offset_x=G.vp_offset_x||0;
G.vp_offset_y=G.vp_offset_y||0;
if(D(G.icons)&&!G.icons){G["class"]+=" mceNoIcons"
}this.parent(H,G);
this.onShowMenu=new E.util.Dispatcher(this);
this.onHideMenu=new E.util.Dispatcher(this);
this.classPrefix="mceMenu"
},createMenu:function(J){var H=this,I=H.settings,G;
J.container=J.container||I.container;
J.parent=H;
J.constrain=J.constrain||I.constrain;
J["class"]=J["class"]||I["class"];
J.vp_offset_x=J.vp_offset_x||I.vp_offset_x;
J.vp_offset_y=J.vp_offset_y||I.vp_offset_y;
G=new E.ui.DropMenu(J.id||C.uniqueId(),J);
G.onAddItem.add(H.onAddItem.dispatch,H.onAddItem);
return G
},update:function(){var I=this,J=I.settings,G=C.get("menu_"+I.id+"_tbl"),L=C.get("menu_"+I.id+"_co"),H,K;
H=J.max_width?Math.min(G.clientWidth,J.max_width):G.clientWidth;
K=J.max_height?Math.min(G.clientHeight,J.max_height):G.clientHeight;
if(!C.boxModel){I.element.setStyles({width:H+2,height:K+2})
}else{I.element.setStyles({width:H,height:K})
}if(J.max_width){C.setStyle(L,"width",H)
}if(J.max_height){C.setStyle(L,"height",K);
if(G.clientHeight<J.max_height){C.setStyle(L,"overflow","hidden")
}}},showMenu:function(O,M,Q){var T=this,U=T.settings,N,G=C.getViewPort(),R,K,S,P,H=2,J,I,L=T.classPrefix;
T.collapse(1);
if(T.isMenuVisible){return 
}if(!T.rendered){N=C.add(T.settings.container,T.renderNode());
F(T.items,function(V){V.postRender()
});
T.element=new B("menu_"+T.id,{blocker:1,container:U.container})
}else{N=C.get("menu_"+T.id)
}if(!E.isOpera){C.setStyles(N,{left:-65535,top:-65535})
}C.show(N);
T.update();
O+=U.offset_x||0;
M+=U.offset_y||0;
G.w-=4;
G.h-=4;
if(U.constrain){R=N.clientWidth-H;
K=N.clientHeight-H;
S=G.x+G.w;
P=G.y+G.h;
if((O+U.vp_offset_x+R)>S){O=Q?Q-R:Math.max(0,(S-U.vp_offset_x)-R)
}if((M+U.vp_offset_y+K)>P){M=Math.max(0,(P-U.vp_offset_y)-K)
}}C.setStyles(N,{left:O,top:M});
T.element.update();
T.isMenuVisible=1;
T.mouseClickFunc=A.add(N,"click",function(W){var V;
W=W.target;
if(W&&(W=C.getParent(W,"tr"))&&!C.hasClass(W,L+"ItemSub")){V=T.items[W.id];
if(V.isDisabled()){return 
}J=T;
while(J){if(J.hideMenu){J.hideMenu()
}J=J.settings.parent
}if(V.settings.onclick){V.settings.onclick(W)
}return A.cancel(W)
}});
if(T.hasMenus()){T.mouseOverFunc=A.add(N,"mouseover",function(Y){var V,X,W;
Y=Y.target;
if(Y&&(Y=C.getParent(Y,"tr"))){V=T.items[Y.id];
if(T.lastMenu){T.lastMenu.collapse(1)
}if(V.isDisabled()){return 
}if(Y&&C.hasClass(Y,L+"ItemSub")){X=C.getRect(Y);
V.showMenu((X.x+X.w-H),X.y-H,X.x);
T.lastMenu=V;
C.addClass(C.get(V.id).firstChild,L+"ItemActive")
}}})
}T.onShowMenu.dispatch(T);
if(U.keyboard_focus){A.add(N,"keydown",T._keyHandler,T);
C.select("a","menu_"+T.id)[0].focus();
T._focusIdx=0
}},hideMenu:function(J){var G=this,I=C.get("menu_"+G.id),H;
if(!G.isMenuVisible){return 
}A.remove(I,"mouseover",G.mouseOverFunc);
A.remove(I,"click",G.mouseClickFunc);
A.remove(I,"keydown",G._keyHandler);
C.hide(I);
G.isMenuVisible=0;
if(!J){G.collapse(1)
}if(G.element){G.element.hide()
}if(H=C.get(G.id)){C.removeClass(H.firstChild,G.classPrefix+"ItemActive")
}G.onHideMenu.dispatch(G)
},add:function(I){var G=this,H;
I=G.parent(I);
if(G.isRendered&&(H=C.get("menu_"+G.id))){G._add(C.select("tbody",H)[0],I)
}return I
},collapse:function(G){this.parent(G);
this.hideMenu(1)
},remove:function(G){C.remove(G.id);
this.destroy();
return this.parent(G)
},destroy:function(){var G=this,H=C.get("menu_"+G.id);
A.remove(H,"mouseover",G.mouseOverFunc);
A.remove(H,"click",G.mouseClickFunc);
if(G.element){G.element.remove()
}C.remove(H)
},renderNode:function(){var I=this,J=I.settings,L,H,K,G;
G=C.create("div",{id:"menu_"+I.id,"class":J["class"],style:"position:absolute;left:0;top:0;z-index:200000"});
K=C.add(G,"div",{id:"menu_"+I.id+"_co","class":I.classPrefix+(J["class"]?" "+J["class"]:"")});
I.element=new B("menu_"+I.id,{blocker:1,container:J.container});
if(J.menu_line){C.add(K,"span",{"class":I.classPrefix+"Line"})
}L=C.add(K,"table",{id:"menu_"+I.id+"_tbl",border:0,cellPadding:0,cellSpacing:0});
H=C.add(L,"tbody");
F(I.items,function(M){I._add(H,M)
});
I.rendered=true;
return G
},_keyHandler:function(J){var I=this,H=J.keyCode;
function G(M){var K=I._focusIdx+M,L=C.select("a","menu_"+I.id)[K];
if(L){I._focusIdx=K;
L.focus()
}}switch(H){case 38:G(-1);
return ;
case 40:G(1);
return ;
case 13:return ;
case 27:return this.hideMenu()
}},_add:function(J,H){var I,O=H.settings,N,L,K,M=this.classPrefix,G;
if(O.separator){L=C.add(J,"tr",{id:H.id,"class":M+"ItemSeparator"});
C.add(L,"td",{"class":M+"ItemSeparator"});
if(I=L.previousSibling){C.addClass(I,"mceLast")
}return 
}I=L=C.add(J,"tr",{id:H.id,"class":M+"Item "+M+"ItemEnabled"});
I=K=C.add(I,"td");
I=N=C.add(I,"a",{href:"javascript:;",onclick:"return false;",onmousedown:"return false;"});
C.addClass(K,O["class"]);
G=C.add(I,"span",{"class":"mceIcon"+(O.icon?" mce_"+O.icon:"")});
if(O.icon_src){C.add(G,"img",{src:O.icon_src})
}I=C.add(I,O.element||"span",{"class":"mceText",title:H.settings.title},H.settings.title);
if(H.settings.style){C.setAttrib(I,"style",H.settings.style)
}if(J.childNodes.length==1){C.addClass(L,"mceFirst")
}if((I=L.previousSibling)&&C.hasClass(I,M+"ItemSeparator")){C.addClass(L,"mceFirst")
}if(H.collapse){C.addClass(L,M+"ItemSub")
}if(I=L.previousSibling){C.removeClass(I,"mceLast")
}C.addClass(L,"mceLast")
}})
})(tinymce);
(function(B){var A=B.DOM;
B.create("tinymce.ui.Button:tinymce.ui.Control",{Button:function(D,C){this.parent(D,C);
this.classPrefix="mceButton"
},renderHTML:function(){var F=this.classPrefix,E=this.settings,D,C;
C=A.encode(E.label||"");
D='<a id="'+this.id+'" href="javascript:;" class="'+F+" "+F+"Enabled "+E["class"]+(C?" "+F+"Labeled":"")+'" onmousedown="return false;" onclick="return false;" title="'+A.encode(E.title)+'">';
if(E.image){D+='<img class="mceIcon" src="'+E.image+'" />'+C+"</a>"
}else{D+='<span class="mceIcon '+E["class"]+'"></span>'+(C?'<span class="'+F+'Label">'+C+"</span>":"")+"</a>"
}return D
},postRender:function(){var C=this,D=C.settings;
B.dom.Event.add(C.id,"click",function(E){if(!C.isDisabled()){return D.onclick.call(D.scope,E)
}})
}})
})(tinymce);
(function(D){var C=D.DOM,B=D.dom.Event,E=D.each,A=D.util.Dispatcher;
D.create("tinymce.ui.ListBox:tinymce.ui.Control",{ListBox:function(H,G){var F=this;
F.parent(H,G);
F.items=[];
F.onChange=new A(F);
F.onPostRender=new A(F);
F.onAdd=new A(F);
F.onRenderMenu=new D.util.Dispatcher(this);
F.classPrefix="mceListBox"
},select:function(G){var F=this,I,H;
if(G==undefined){return F.selectByIndex(-1)
}if(G&&G.call){H=G
}else{H=function(J){return J==G
}
}if(G!=F.selectedValue){E(F.items,function(K,J){if(H(K.value)){I=1;
F.selectByIndex(J);
return false
}});
if(!I){F.selectByIndex(-1)
}}},selectByIndex:function(F){var G=this,H,I;
if(F!=G.selectedIndex){H=C.get(G.id+"_text");
I=G.items[F];
if(I){G.selectedValue=I.value;
G.selectedIndex=F;
C.setHTML(H,C.encode(I.title));
C.removeClass(H,"mceTitle")
}else{C.setHTML(H,C.encode(G.settings.title));
C.addClass(H,"mceTitle");
G.selectedValue=G.selectedIndex=null
}H=0
}},add:function(I,F,H){var G=this;
H=H||{};
H=D.extend(H,{title:I,value:F});
G.items.push(H);
G.onAdd.dispatch(G,H)
},getLength:function(){return this.items.length
},renderHTML:function(){var H="",F=this,G=F.settings,I=F.classPrefix;
H='<table id="'+F.id+'" cellpadding="0" cellspacing="0" class="'+I+" "+I+"Enabled"+(G["class"]?(" "+G["class"]):"")+'"><tbody><tr>';
H+="<td>"+C.createHTML("a",{id:F.id+"_text",href:"javascript:;","class":"mceText",onclick:"return false;",onmousedown:"return false;"},C.encode(F.settings.title))+"</td>";
H+="<td>"+C.createHTML("a",{id:F.id+"_open",tabindex:-1,href:"javascript:;","class":"mceOpen",onclick:"return false;",onmousedown:"return false;"},"<span></span>")+"</td>";
H+="</tr></tbody></table>";
return H
},showMenu:function(){var G=this,J,I,H=C.get(this.id),F;
if(G.isDisabled()||G.items.length==0){return 
}if(G.menu&&G.menu.isMenuVisible){return G.hideMenu()
}if(!G.isMenuRendered){G.renderMenu();
G.isMenuRendered=true
}J=C.getPos(this.settings.menu_container);
I=C.getPos(H);
F=G.menu;
F.settings.offset_x=I.x;
F.settings.offset_y=I.y;
F.settings.keyboard_focus=!D.isOpera;
if(G.oldID){F.items[G.oldID].setSelected(0)
}E(G.items,function(K){if(K.value===G.selectedValue){F.items[K.id].setSelected(1);
G.oldID=K.id
}});
F.showMenu(0,H.clientHeight);
B.add(C.doc,"mousedown",G.hideMenu,G);
C.addClass(G.id,G.classPrefix+"Selected")
},hideMenu:function(G){var F=this;
if(G&&G.type=="mousedown"&&(G.target.id==F.id+"_text"||G.target.id==F.id+"_open")){return 
}if(!G||!C.getParent(G.target,".mceMenu")){C.removeClass(F.id,F.classPrefix+"Selected");
B.remove(C.doc,"mousedown",F.hideMenu,F);
if(F.menu){F.menu.hideMenu()
}}},renderMenu:function(){var G=this,F;
F=G.settings.control_manager.createDropMenu(G.id+"_menu",{menu_line:1,"class":G.classPrefix+"Menu mceNoIcons",max_width:150,max_height:150});
F.onHideMenu.add(G.hideMenu,G);
F.add({title:G.settings.title,"class":"mceMenuItemTitle",onclick:function(){if(G.settings.onselect("")!==false){G.select("")
}}});
E(G.items,function(H){H.id=C.uniqueId();
H.onclick=function(){if(G.settings.onselect(H.value)!==false){G.select(H.value)
}};
F.add(H)
});
G.onRenderMenu.dispatch(G,F);
G.menu=F
},postRender:function(){var F=this,G=F.classPrefix;
B.add(F.id,"click",F.showMenu,F);
B.add(F.id+"_text","focus",function(H){if(!F._focused){F.keyDownHandler=B.add(F.id+"_text","keydown",function(L){var I=-1,J,K=L.keyCode;
E(F.items,function(M,N){if(F.selectedValue==M.value){I=N
}});
if(K==38){J=F.items[I-1]
}else{if(K==40){J=F.items[I+1]
}else{if(K==13){J=F.selectedValue;
F.selectedValue=null;
F.settings.onselect(J);
return B.cancel(L)
}}}if(J){F.hideMenu();
F.select(J.value)
}})
}F._focused=1
});
B.add(F.id+"_text","blur",function(){B.remove(F.id+"_text","keydown",F.keyDownHandler);
F._focused=0
});
if(D.isIE6||!C.boxModel){B.add(F.id,"mouseover",function(){if(!C.hasClass(F.id,G+"Disabled")){C.addClass(F.id,G+"Hover")
}});
B.add(F.id,"mouseout",function(){if(!C.hasClass(F.id,G+"Disabled")){C.removeClass(F.id,G+"Hover")
}})
}F.onPostRender.dispatch(F,C.get(F.id))
},destroy:function(){this.parent();
B.clear(this.id+"_text")
}})
})(tinymce);
(function(D){var C=D.DOM,B=D.dom.Event,E=D.each,A=D.util.Dispatcher;
D.create("tinymce.ui.NativeListBox:tinymce.ui.ListBox",{NativeListBox:function(G,F){this.parent(G,F);
this.classPrefix="mceNativeListBox"
},setDisabled:function(F){C.get(this.id).disabled=F
},isDisabled:function(){return C.get(this.id).disabled
},select:function(G){var F=this,I,H;
if(G==undefined){return F.selectByIndex(-1)
}if(G&&G.call){H=G
}else{H=function(J){return J==G
}
}if(G!=F.selectedValue){E(F.items,function(K,J){if(H(K.value)){I=1;
F.selectByIndex(J);
return false
}});
if(!I){F.selectByIndex(-1)
}}},selectByIndex:function(F){C.get(this.id).selectedIndex=F+1;
this.selectedValue=this.items[F]?this.items[F].value:null
},add:function(J,G,F){var I,H=this;
F=F||{};
F.value=G;
if(H.isRendered()){C.add(C.get(this.id),"option",F,J)
}I={title:J,value:G,attribs:F};
H.items.push(I);
H.onAdd.dispatch(H,I)
},getLength:function(){return C.get(this.id).options.length-1
},renderHTML:function(){var G,F=this;
G=C.createHTML("option",{value:""},"-- "+F.settings.title+" --");
E(F.items,function(H){G+=C.createHTML("option",{value:H.value},H.title)
});
G=C.createHTML("select",{id:F.id,"class":"mceNativeListBox"},G);
return G
},postRender:function(){var G=this,H;
G.rendered=true;
function F(J){var I=G.items[J.target.selectedIndex-1];
if(I&&(I=I.value)){G.onChange.dispatch(G,I);
if(G.settings.onselect){G.settings.onselect(I)
}}}B.add(G.id,"change",F);
B.add(G.id,"keydown",function(J){var I;
B.remove(G.id,"change",H);
I=B.add(G.id,"blur",function(){B.add(G.id,"change",F);
B.remove(G.id,"blur",I)
});
if(J.keyCode==13||J.keyCode==32){F(J);
return B.cancel(J)
}});
G.onPostRender.dispatch(G,C.get(G.id))
}})
})(tinymce);
(function(C){var B=C.DOM,A=C.dom.Event,D=C.each;
C.create("tinymce.ui.MenuButton:tinymce.ui.Button",{MenuButton:function(F,E){this.parent(F,E);
this.onRenderMenu=new C.util.Dispatcher(this);
E.menu_container=E.menu_container||B.doc.body
},showMenu:function(){var F=this,I,H,G=B.get(F.id),E;
if(F.isDisabled()){return 
}if(!F.isMenuRendered){F.renderMenu();
F.isMenuRendered=true
}if(F.isMenuVisible){return F.hideMenu()
}I=B.getPos(F.settings.menu_container);
H=B.getPos(G);
E=F.menu;
E.settings.offset_x=H.x;
E.settings.offset_y=H.y;
E.settings.vp_offset_x=H.x;
E.settings.vp_offset_y=H.y;
E.settings.keyboard_focus=F._focused;
E.showMenu(0,G.clientHeight);
A.add(B.doc,"mousedown",F.hideMenu,F);
F.setState("Selected",1);
F.isMenuVisible=1
},renderMenu:function(){var F=this,E;
E=F.settings.control_manager.createDropMenu(F.id+"_menu",{menu_line:1,"class":this.classPrefix+"Menu",icons:F.settings.icons});
E.onHideMenu.add(F.hideMenu,F);
F.onRenderMenu.dispatch(F,E);
F.menu=E
},hideMenu:function(F){var E=this;
if(F&&F.type=="mousedown"&&B.getParent(F.target,function(G){return G.id===E.id||G.id===E.id+"_open"
})){return 
}if(!F||!B.getParent(F.target,".mceMenu")){E.setState("Selected",0);
A.remove(B.doc,"mousedown",E.hideMenu,E);
if(E.menu){E.menu.hideMenu()
}}E.isMenuVisible=0
},postRender:function(){var E=this,F=E.settings;
A.add(E.id,"click",function(){if(!E.isDisabled()){if(F.onclick){F.onclick(E.value)
}E.showMenu()
}})
}})
})(tinymce);
(function(C){var B=C.DOM,A=C.dom.Event,D=C.each;
C.create("tinymce.ui.SplitButton:tinymce.ui.MenuButton",{SplitButton:function(F,E){this.parent(F,E);
this.classPrefix="mceSplitButton"
},renderHTML:function(){var H,F=this,G=F.settings,E;
H="<tbody><tr>";
if(G.image){E=B.createHTML("img ",{src:G.image,"class":"mceAction "+G["class"]})
}else{E=B.createHTML("span",{"class":"mceAction "+G["class"]},"")
}H+="<td>"+B.createHTML("a",{id:F.id+"_action",href:"javascript:;","class":"mceAction "+G["class"],onclick:"return false;",onmousedown:"return false;",title:G.title},E)+"</td>";
E=B.createHTML("span",{"class":"mceOpen "+G["class"]});
H+="<td>"+B.createHTML("a",{id:F.id+"_open",href:"javascript:;","class":"mceOpen "+G["class"],onclick:"return false;",onmousedown:"return false;",title:G.title},E)+"</td>";
H+="</tr></tbody>";
return B.createHTML("table",{id:F.id,"class":"mceSplitButton mceSplitButtonEnabled "+G["class"],cellpadding:"0",cellspacing:"0",onmousedown:"return false;",title:G.title},H)
},postRender:function(){var E=this,F=E.settings;
if(F.onclick){A.add(E.id+"_action","click",function(){if(!E.isDisabled()){F.onclick(E.value)
}})
}A.add(E.id+"_open","click",E.showMenu,E);
A.add(E.id+"_open","focus",function(){E._focused=1
});
A.add(E.id+"_open","blur",function(){E._focused=0
});
if(C.isIE6||!B.boxModel){A.add(E.id,"mouseover",function(){if(!B.hasClass(E.id,"mceSplitButtonDisabled")){B.addClass(E.id,"mceSplitButtonHover")
}});
A.add(E.id,"mouseout",function(){if(!B.hasClass(E.id,"mceSplitButtonDisabled")){B.removeClass(E.id,"mceSplitButtonHover")
}})
}},destroy:function(){this.parent();
A.clear(this.id+"_action");
A.clear(this.id+"_open")
}})
})(tinymce);
(function(D){var C=D.DOM,A=D.dom.Event,B=D.is,E=D.each;
D.create("tinymce.ui.ColorSplitButton:tinymce.ui.SplitButton",{ColorSplitButton:function(H,G){var F=this;
F.parent(H,G);
F.settings=G=D.extend({colors:"000000,993300,333300,003300,003366,000080,333399,333333,800000,FF6600,808000,008000,008080,0000FF,666699,808080,FF0000,FF9900,99CC00,339966,33CCCC,3366FF,800080,999999,FF00FF,FFCC00,FFFF00,00FF00,00FFFF,00CCFF,993366,C0C0C0,FF99CC,FFCC99,FFFF99,CCFFCC,CCFFFF,99CCFF,CC99FF,FFFFFF",grid_width:8,default_color:"#888888"},F.settings);
F.onShowMenu=new D.util.Dispatcher(F);
F.onHideMenu=new D.util.Dispatcher(F);
F.value=G.default_color
},showMenu:function(){var F=this,G,J,I,H;
if(F.isDisabled()){return 
}if(!F.isMenuRendered){F.renderMenu();
F.isMenuRendered=true
}if(F.isMenuVisible){return F.hideMenu()
}I=C.get(F.id);
C.show(F.id+"_menu");
C.addClass(I,"mceSplitButtonSelected");
H=C.getPos(I);
C.setStyles(F.id+"_menu",{left:H.x,top:H.y+I.clientHeight,zIndex:200000});
I=0;
A.add(C.doc,"mousedown",F.hideMenu,F);
if(F._focused){F._keyHandler=A.add(F.id+"_menu","keydown",function(K){if(K.keyCode==27){F.hideMenu()
}});
C.select("a",F.id+"_menu")[0].focus()
}F.onShowMenu.dispatch(F);
F.isMenuVisible=1
},hideMenu:function(G){var F=this;
if(G&&G.type=="mousedown"&&C.getParent(G.target,function(H){return H.id===F.id+"_open"
})){return 
}if(!G||!C.getParent(G.target,".mceSplitButtonMenu")){C.removeClass(F.id,"mceSplitButtonSelected");
A.remove(C.doc,"mousedown",F.hideMenu,F);
A.remove(F.id+"_menu","keydown",F._keyHandler);
C.hide(F.id+"_menu")
}F.onHideMenu.dispatch(F);
F.isMenuVisible=0
},renderMenu:function(){var J=this,F,I=0,K=J.settings,M,H,L,G;
G=C.add(K.menu_container,"div",{id:J.id+"_menu","class":K.menu_class+" "+K["class"],style:"position:absolute;left:0;top:-1000px;"});
F=C.add(G,"div",{"class":K["class"]+" mceSplitButtonMenu"});
C.add(F,"span",{"class":"mceMenuLine"});
M=C.add(F,"table",{"class":"mceColorSplitMenu"});
H=C.add(M,"tbody");
I=0;
E(B(K.colors,"array")?K.colors:K.colors.split(","),function(N){N=N.replace(/^#/,"");
if(!I--){L=C.add(H,"tr");
I=K.grid_width-1
}M=C.add(L,"td");
M=C.add(M,"a",{href:"javascript:;",style:{backgroundColor:"#"+N},mce_color:"#"+N})
});
if(K.more_colors_func){M=C.add(H,"tr");
M=C.add(M,"td",{colspan:K.grid_width,"class":"mceMoreColors"});
M=C.add(M,"a",{id:J.id+"_more",href:"javascript:;",onclick:"return false;","class":"mceMoreColors"},K.more_colors_title);
A.add(M,"click",function(N){K.more_colors_func.call(K.more_colors_scope||this);
return A.cancel(N)
})
}C.addClass(F,"mceColorSplitMenu");
A.add(J.id+"_menu","click",function(N){var O;
N=N.target;
if(N.nodeName=="A"&&(O=N.getAttribute("mce_color"))){J.setColor(O)
}return A.cancel(N)
});
return G
},setColor:function(G){var F=this;
C.setStyle(F.id+"_preview","backgroundColor",G);
F.value=G;
F.hideMenu();
F.settings.onselect(G)
},postRender:function(){var F=this,G=F.id;
F.parent();
C.add(G+"_action","div",{id:G+"_preview","class":"mceColorPreview"});
C.setStyle(F.id+"_preview","backgroundColor",F.value)
},destroy:function(){this.parent();
A.clear(this.id+"_menu");
A.clear(this.id+"_more");
C.remove(this.id+"_menu")
}})
})(tinymce);
tinymce.create("tinymce.ui.Toolbar:tinymce.ui.Container",{renderHTML:function(){var I=this,D="",F,G,B=tinymce.DOM,J=I.settings,C,A,E,H;
H=I.controls;
for(C=0;
C<H.length;
C++){G=H[C];
A=H[C-1];
E=H[C+1];
if(C===0){F="mceToolbarStart";
if(G.Button){F+=" mceToolbarStartButton"
}else{if(G.SplitButton){F+=" mceToolbarStartSplitButton"
}else{if(G.ListBox){F+=" mceToolbarStartListBox"
}}}D+=B.createHTML("td",{"class":F},B.createHTML("span",null,"<!-- IE -->"))
}if(A&&G.ListBox){if(A.Button||A.SplitButton){D+=B.createHTML("td",{"class":"mceToolbarEnd"},B.createHTML("span",null,"<!-- IE -->"))
}}if(B.stdMode){D+='<td style="position: relative">'+G.renderHTML()+"</td>"
}else{D+="<td>"+G.renderHTML()+"</td>"
}if(E&&G.ListBox){if(E.Button||E.SplitButton){D+=B.createHTML("td",{"class":"mceToolbarStart"},B.createHTML("span",null,"<!-- IE -->"))
}}}F="mceToolbarEnd";
if(G.Button){F+=" mceToolbarEndButton"
}else{if(G.SplitButton){F+=" mceToolbarEndSplitButton"
}else{if(G.ListBox){F+=" mceToolbarEndListBox"
}}}D+=B.createHTML("td",{"class":F},B.createHTML("span",null,"<!-- IE -->"));
return B.createHTML("table",{id:I.id,"class":"mceToolbar"+(J["class"]?" "+J["class"]:""),cellpadding:"0",cellspacing:"0",align:I.settings.align||""},"<tbody><tr>"+D+"</tr></tbody>")
}});
(function(B){var A=B.util.Dispatcher,C=B.each;
B.create("tinymce.AddOnManager",{items:[],urls:{},lookup:{},onAdd:new A(this),get:function(D){return this.lookup[D]
},requireLangPack:function(F){var D,E=B.EditorManager.settings;
if(E&&E.language){D=this.urls[F]+"/langs/"+E.language+".js";
if(!B.dom.Event.domLoaded&&!E.strict_mode){B.ScriptLoader.load(D)
}else{B.ScriptLoader.add(D)
}}},add:function(E,D){this.items.push(D);
this.lookup[E]=D;
this.onAdd.dispatch(this,E,D);
return D
},load:function(H,E,D,G){var F=this;
if(F.urls[H]){return 
}if(E.indexOf("/")!=0&&E.indexOf("://")==-1){E=B.baseURL+"/"+E
}F.urls[H]=E.substring(0,E.lastIndexOf("/"));
B.ScriptLoader.add(E,D,G)
}});
B.PluginManager=new B.AddOnManager();
B.ThemeManager=new B.AddOnManager()
}(tinymce));
(function(F){var G=F.each,H=F.extend,E=F.DOM,A=F.dom.Event,C=F.ThemeManager,B=F.PluginManager,D=F.explode;
F.create("static tinymce.EditorManager",{editors:{},i18n:{},activeEditor:null,preInit:function(){var I=this,J=window.location;
F.documentBaseURL=J.href.replace(/[\?#].*$/,"").replace(/[\/\\][^\/]+$/,"");
if(!/[\/\\]$/.test(F.documentBaseURL)){F.documentBaseURL+="/"
}F.baseURL=new F.util.URI(F.documentBaseURL).toAbsolute(F.baseURL);
F.EditorManager.baseURI=new F.util.URI(F.baseURL);
if(document.domain&&J.hostname!=document.domain){F.relaxedDomain=document.domain
}I.onBeforeUnload=new F.util.Dispatcher(I);
A.add(window,"beforeunload",function(K){I.onBeforeUnload.dispatch(I,K)
})
},init:function(Q){var P=this,L,K=F.ScriptLoader,O,N,I=[],M;
function J(T,U,R){var S=T[U];
if(!S){return 
}if(F.is(S,"string")){R=S.replace(/\.\w+$/,"");
R=R?F.resolve(R):0;
S=F.resolve(S)
}return S.apply(R||this,Array.prototype.slice.call(arguments,2))
}Q=H({theme:"simple",language:"en",strict_loading_mode:document.contentType=="application/xhtml+xml"},Q);
P.settings=Q;
if(!A.domLoaded&&!Q.strict_loading_mode){if(Q.language){K.add(F.baseURL+"/langs/"+Q.language+".js")
}if(Q.theme&&Q.theme.charAt(0)!="-"&&!C.urls[Q.theme]){C.load(Q.theme,"themes/"+Q.theme+"/editor_template"+F.suffix+".js")
}if(Q.plugins){L=D(Q.plugins);
if(F.inArray(L,"compat2x")!=-1){B.load("compat2x","plugins/compat2x/editor_plugin"+F.suffix+".js")
}G(L,function(R){if(R&&R.charAt(0)!="-"&&!B.urls[R]){if(!F.isWebKit&&R=="safari"){return 
}B.load(R,"plugins/"+R+"/editor_plugin"+F.suffix+".js")
}})
}K.loadQueue()
}A.add(document,"init",function(){var R,T;
J(Q,"onpageload");
if(Q.browsers){R=false;
G(D(Q.browsers),function(U){switch(U){case"ie":case"msie":if(F.isIE){R=true
}break;
case"gecko":if(F.isGecko){R=true
}break;
case"safari":case"webkit":if(F.isWebKit){R=true
}break;
case"opera":if(F.isOpera){R=true
}break
}});
if(!R){return 
}}switch(Q.mode){case"exact":R=Q.elements||"";
if(R.length>0){G(D(R),function(U){if(E.get(U)){M=new F.Editor(U,Q);
I.push(M);
M.render(1)
}else{O=0;
G(document.forms,function(V){G(V.elements,function(W){if(W.name===U){U="mce_editor_"+O;
E.setAttrib(W,"id",U);
M=new F.Editor(U,Q);
I.push(M);
M.render(1)
}})
})
}})
}break;
case"textareas":case"specific_textareas":function S(V,U){return U.constructor===RegExp?U.test(V.className):E.hasClass(V,U)
}G(E.select("textarea"),function(U){if(Q.editor_deselector&&S(U,Q.editor_deselector)){return 
}if(!Q.editor_selector||S(U,Q.editor_selector)){N=E.get(U.name);
if(!U.id&&!N){U.id=U.name
}if(!U.id||P.get(U.id)){U.id=E.uniqueId()
}M=new F.Editor(U.id,Q);
I.push(M);
M.render(1)
}});
break
}if(Q.oninit){R=T=0;
G(I,function(U){T++;
if(!U.initialized){U.onInit.add(function(){R++;
if(R==T){J(Q,"oninit")
}})
}else{R++
}if(R==T){J(Q,"oninit")
}})
}})
},get:function(I){return this.editors[I]
},getInstanceById:function(I){return this.get(I)
},add:function(I){this.editors[I.id]=I;
this._setActive(I);
return I
},remove:function(J){var I=this;
if(!I.editors[J.id]){return null
}delete I.editors[J.id];
if(I.activeEditor==J){G(I.editors,function(K){I._setActive(K);
return false
})
}J.destroy();
return J
},execCommand:function(O,M,L){var N=this,K=N.get(L),I;
switch(O){case"mceFocus":K.focus();
return true;
case"mceAddEditor":case"mceAddControl":if(!N.get(L)){new F.Editor(L,N.settings).render()
}return true;
case"mceAddFrameControl":I=L.window;
I.tinyMCE=tinyMCE;
I.tinymce=F;
F.DOM.doc=I.document;
F.DOM.win=I;
K=new F.Editor(L.element_id,L);
K.render();
if(F.isIE){function J(){K.destroy();
I.detachEvent("onunload",J);
I=I.tinyMCE=I.tinymce=null
}I.attachEvent("onunload",J)
}L.page_window=null;
return true;
case"mceRemoveEditor":case"mceRemoveControl":if(K){K.remove()
}return true;
case"mceToggleEditor":if(!K){N.execCommand("mceAddControl",0,L);
return true
}if(K.isHidden()){K.show()
}else{K.hide()
}return true
}if(N.activeEditor){return N.activeEditor.execCommand(O,M,L)
}return false
},execInstanceCommand:function(M,L,K,J){var I=this.get(M);
if(I){return I.execCommand(L,K,J)
}return false
},triggerSave:function(){G(this.editors,function(I){I.save()
})
},addI18n:function(K,L){var I,J=this.i18n;
if(!F.is(K,"string")){G(K,function(N,M){G(N,function(P,O){G(P,function(R,Q){if(O==="common"){J[M+"."+Q]=R
}else{J[M+"."+O+"."+Q]=R
}})
})
})
}else{G(L,function(N,M){J[K+"."+M]=N
})
}},_setActive:function(I){this.selectedInstance=this.activeEditor=I
}});
F.EditorManager.preInit()
})(tinymce);
var tinyMCE=window.tinyMCE=tinymce.EditorManager;
(function(N){var O=N.DOM,K=N.dom.Event,F=N.extend,L=N.util.Dispatcher;
var J=N.each,A=N.isGecko,B=N.isIE,E=N.isWebKit;
var D=N.is,H=N.ThemeManager,C=N.PluginManager,I=N.EditorManager;
var P=N.inArray,M=N.grep,G=N.explode;
N.create("tinymce.Editor",{Editor:function(S,R){var Q=this;
Q.id=Q.editorId=S;
Q.execCommands={};
Q.queryStateCommands={};
Q.queryValueCommands={};
Q.plugins={};
J(["onPreInit","onBeforeRenderUI","onPostRender","onInit","onRemove","onActivate","onDeactivate","onClick","onEvent","onMouseUp","onMouseDown","onDblClick","onKeyDown","onKeyUp","onKeyPress","onContextMenu","onSubmit","onReset","onPaste","onPreProcess","onPostProcess","onBeforeSetContent","onBeforeGetContent","onSetContent","onGetContent","onLoadContent","onSaveContent","onNodeChange","onChange","onBeforeExecCommand","onExecCommand","onUndo","onRedo","onVisualAid","onSetProgressState"],function(T){Q[T]=new L(Q)
});
Q.settings=R=F({id:S,language:"en",docs_language:"en",theme:"simple",skin:"default",delta_width:0,delta_height:0,popup_css:"",plugins:"",document_base_url:N.documentBaseURL,add_form_submit_trigger:1,submit_patch:1,add_unload_trigger:1,convert_urls:1,relative_urls:1,remove_script_host:1,table_inline_editing:0,object_resizing:1,cleanup:1,accessibility_focus:1,custom_shortcuts:1,custom_undo_redo_keyboard_shortcuts:1,custom_undo_redo_restore_selection:1,custom_undo_redo:1,doctype:'<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">',visual_table_class:"mceItemTable",visual:1,inline_styles:true,convert_fonts_to_spans:true,font_size_style_values:"xx-small,x-small,small,medium,large,x-large,xx-large",apply_source_formatting:1,directionality:"ltr",forced_root_block:"p",valid_elements:"@[id|class|style|title|dir<ltr?rtl|lang|xml::lang|onclick|ondblclick|onmousedown|onmouseup|onmouseover|onmousemove|onmouseout|onkeypress|onkeydown|onkeyup],a[rel|rev|charset|hreflang|tabindex|accesskey|type|name|href|target|title|class|onfocus|onblur],strong/b,em/i,strike,u,#p[align],-ol[type|compact],-ul[type|compact],-li,br,img[longdesc|usemap|src|border|alt=|title|hspace|vspace|width|height|align],-sub,-sup,-blockquote[cite],-table[border=0|cellspacing|cellpadding|width|frame|rules|height|align|summary|bgcolor|background|bordercolor],-tr[rowspan|width|height|align|valign|bgcolor|background|bordercolor],tbody,thead,tfoot,#td[colspan|rowspan|width|height|align|valign|bgcolor|background|bordercolor|scope],#th[colspan|rowspan|width|height|align|valign|scope],caption,-div,-span,-code,-pre,address,-h1,-h2,-h3,-h4,-h5,-h6,hr[size|noshade],-font[face|size|color],dd,dl,dt,cite,abbr,acronym,del[datetime|cite],ins[datetime|cite],object[classid|width|height|codebase|*],param[name|value],embed[type|width|height|src|*],script[src|type],map[name],area[shape|coords|href|alt|target],bdo,button,col[align|char|charoff|span|valign|width],colgroup[align|char|charoff|span|valign|width],dfn,fieldset,form[action|accept|accept-charset|enctype|method],input[accept|alt|checked|disabled|maxlength|name|readonly|size|src|type|value|tabindex|accesskey],kbd,label[for],legend,noscript,optgroup[label|disabled],option[disabled|label|selected|value],q[cite],samp,select[disabled|multiple|name|size],small,textarea[cols|rows|disabled|name|readonly],tt,var,big",hidden_input:1,padd_empty_editor:1,render_ui:1,init_theme:1,force_p_newlines:1,indentation:"30px",keep_styles:1,fix_table_elements:1,removeformat_selector:"span,b,strong,em,i,font,u,strike"},R);
Q.documentBaseURI=new N.util.URI(R.document_base_url||N.documentBaseURL,{base_uri:tinyMCE.baseURI});
Q.baseURI=I.baseURI;
Q.execCallback("setup",Q)
},render:function(S){var T=this,U=T.settings,V=T.id,Q=N.ScriptLoader;
if(!K.domLoaded){K.add(document,"init",function(){T.render()
});
return 
}if(!S){U.strict_loading_mode=1;
tinyMCE.settings=U
}if(!T.getElement()){return 
}if(U.strict_loading_mode){Q.settings.strict_mode=U.strict_loading_mode;
N.DOM.settings.strict=1
}if(!/TEXTAREA|INPUT/i.test(T.getElement().nodeName)&&U.hidden_input&&O.getParent(V,"form")){O.insertAfter(O.create("input",{type:"hidden",name:V}),V)
}if(N.WindowManager){T.windowManager=new N.WindowManager(T)
}if(U.encoding=="xml"){T.onGetContent.add(function(W,X){if(X.save){X.content=O.encode(X.content)
}})
}if(U.add_form_submit_trigger){T.onSubmit.addToTop(function(){if(T.initialized){T.save();
T.isNotDirty=1
}})
}if(U.add_unload_trigger){T._beforeUnload=tinyMCE.onBeforeUnload.add(function(){if(T.initialized&&!T.destroyed&&!T.isHidden()){T.save({format:"raw",no_events:true})
}})
}N.addUnload(T.destroy,T);
if(U.submit_patch){T.onBeforeRenderUI.add(function(){var W=T.getElement().form;
if(!W){return 
}if(W._mceOldSubmit){return 
}if(!W.submit.nodeType&&!W.submit.length){T.formElement=W;
W._mceOldSubmit=W.submit;
W.submit=function(){I.triggerSave();
T.isNotDirty=1;
return T.formElement._mceOldSubmit(T.formElement)
}
}W=null
})
}function R(){if(U.language){Q.add(N.baseURL+"/langs/"+U.language+".js")
}if(U.theme&&U.theme.charAt(0)!="-"&&!H.urls[U.theme]){H.load(U.theme,"themes/"+U.theme+"/editor_template"+N.suffix+".js")
}J(G(U.plugins),function(W){if(W&&W.charAt(0)!="-"&&!C.urls[W]){if(!E&&W=="safari"){return 
}C.load(W,"plugins/"+W+"/editor_plugin"+N.suffix+".js")
}});
Q.loadQueue(function(){if(!T.removed){T.init()
}})
}if(U.plugins.indexOf("compat2x")!=-1){C.load("compat2x","plugins/compat2x/editor_plugin"+N.suffix+".js");
Q.loadQueue(R)
}else{R()
}},init:function(){var S,b=this,c=b.settings,Y,V,X=b.getElement(),R,Q,Z,U,W,a;
I.add(b);
if(c.theme){c.theme=c.theme.replace(/-/,"");
R=H.get(c.theme);
b.theme=new R();
if(b.theme.init&&c.init_theme){b.theme.init(b,H.urls[c.theme]||N.documentBaseURL.replace(/\/$/,""))
}}J(G(c.plugins.replace(/\-/g,"")),function(f){var g=C.get(f),e=C.urls[f]||N.documentBaseURL.replace(/\/$/,""),d;
if(g){d=new g(b,e);
b.plugins[f]=d;
if(d.init){d.init(b,e)
}}});
if(c.popup_css!==false){if(c.popup_css){c.popup_css=b.documentBaseURI.toAbsolute(c.popup_css)
}else{c.popup_css=b.baseURI.toAbsolute("themes/"+c.theme+"/skins/"+c.skin+"/dialog.css")
}}if(c.popup_css_add){c.popup_css+=","+b.documentBaseURI.toAbsolute(c.popup_css_add)
}b.controlManager=new N.ControlManager(b);
b.undoManager=new N.UndoManager(b);
b.undoManager.onAdd.add(function(e,d){if(!d.initial){return b.onChange.dispatch(b,d,e)
}});
b.undoManager.onUndo.add(function(e,d){return b.onUndo.dispatch(b,d,e)
});
b.undoManager.onRedo.add(function(e,d){return b.onRedo.dispatch(b,d,e)
});
if(c.custom_undo_redo){b.onExecCommand.add(function(e,g,f,h,d){if(g!="Undo"&&g!="Redo"&&g!="mceRepaint"&&(!d||!d.skip_undo)){b.undoManager.add()
}})
}b.onExecCommand.add(function(d,e){if(!/^(FontName|FontSize)$/.test(e)){b.nodeChanged()
}});
if(A){function T(d,e){if(!e||!e.initial){b.execCommand("mceRepaint")
}}b.onUndo.add(T);
b.onRedo.add(T);
b.onSetContent.add(T)
}b.onBeforeRenderUI.dispatch(b,b.controlManager);
if(c.render_ui){Y=c.width||X.style.width||X.offsetWidth;
V=c.height||X.style.height||X.offsetHeight;
b.orgDisplay=X.style.display;
a=/^[0-9\.]+(|px)$/i;
if(a.test(""+Y)){Y=Math.max(parseInt(Y)+(R.deltaWidth||0),100)
}if(a.test(""+V)){V=Math.max(parseInt(V)+(R.deltaHeight||0),100)
}R=b.theme.renderUI({targetNode:X,width:Y,height:V,deltaWidth:c.delta_width,deltaHeight:c.delta_height});
b.editorContainer=R.editorContainer
}O.setStyles(R.sizeContainer||R.editorContainer,{width:Y,height:V});
V=(R.iframeHeight||V)+(typeof (V)=="number"?(R.deltaHeight||0):"");
if(V<100){V=100
}b.iframeHTML=c.doctype+'<html><head xmlns="http://www.w3.org/1999/xhtml"><base href="'+b.documentBaseURI.getURI()+'" />';
b.iframeHTML+='<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />';
if(N.relaxedDomain){b.iframeHTML+='<script type="text/javascript">document.domain = "'+N.relaxedDomain+'";<\/script>'
}U=c.body_id||"tinymce";
if(U.indexOf("=")!=-1){U=b.getParam("body_id","","hash");
U=U[b.id]||U
}W=c.body_class||"";
if(W.indexOf("=")!=-1){W=b.getParam("body_class","","hash");
W=W[b.id]||""
}b.iframeHTML+='</head><body id="'+U+'" class="mceContentBody '+W+'"></body></html>';
if(N.relaxedDomain){if(B||(N.isOpera&&parseFloat(opera.version())>=9.5)){Z='javascript:(function(){document.open();document.domain="'+document.domain+'";var ed = window.parent.tinyMCE.get("'+b.id+'");document.write(ed.iframeHTML);document.close();ed.setupIframe();})()'
}else{if(N.isOpera){Z='javascript:(function(){document.open();document.domain="'+document.domain+'";document.close();ed.setupIframe();})()'
}}}S=O.add(R.iframeContainer,"iframe",{id:b.id+"_ifr",src:Z||'javascript:""',frameBorder:"0",style:{width:"100%",height:V}});
b.contentAreaContainer=R.iframeContainer;
O.get(R.editorContainer).style.display=b.orgDisplay;
O.get(b.id).style.display="none";
if(!B||!N.relaxedDomain){b.setupIframe()
}X=S=R=null
},setupIframe:function(){var X=this,Y=X.settings,S=O.get(X.id),T=X.getDoc(),R,V;
if(!B||!N.relaxedDomain){T.open();
T.write(X.iframeHTML);
T.close()
}if(!B){try{if(!Y.readonly){T.designMode="On"
}}catch(U){}}if(B){V=X.getBody();
O.hide(V);
if(!Y.readonly){V.contentEditable=true
}O.show(V)
}X.dom=new N.DOM.DOMUtils(X.getDoc(),{keep_values:true,url_converter:X.convertURL,url_converter_scope:X,hex_colors:Y.force_hex_style_colors,class_filter:Y.class_filter,update_styles:1,fix_ie_paragraphs:1});
X.serializer=new N.dom.Serializer({entity_encoding:Y.entity_encoding,entities:Y.entities,valid_elements:Y.verify_html===false?"*[*]":Y.valid_elements,extended_valid_elements:Y.extended_valid_elements,valid_child_elements:Y.valid_child_elements,invalid_elements:Y.invalid_elements,fix_table_elements:Y.fix_table_elements,fix_list_elements:Y.fix_list_elements,fix_content_duplication:Y.fix_content_duplication,convert_fonts_to_spans:Y.convert_fonts_to_spans,font_size_classes:Y.font_size_classes,font_size_style_values:Y.font_size_style_values,apply_source_formatting:Y.apply_source_formatting,remove_linebreaks:Y.remove_linebreaks,element_format:Y.element_format,dom:X.dom});
X.selection=new N.dom.Selection(X.dom,X.getWin(),X.serializer);
X.forceBlocks=new N.ForceBlocks(X,{forced_root_block:Y.forced_root_block});
X.editorCommands=new N.EditorCommands(X);
X.serializer.onPreProcess.add(function(Z,a){return X.onPreProcess.dispatch(X,a,Z)
});
X.serializer.onPostProcess.add(function(Z,a){return X.onPostProcess.dispatch(X,a,Z)
});
X.onPreInit.dispatch(X);
if(!Y.gecko_spellcheck){X.getBody().spellcheck=0
}if(!Y.readonly){X._addEvents()
}X.controlManager.onPostRender.dispatch(X,X.controlManager);
X.onPostRender.dispatch(X);
if(Y.directionality){X.getBody().dir=Y.directionality
}if(Y.nowrap){X.getBody().style.whiteSpace="nowrap"
}if(Y.auto_resize){X.onNodeChange.add(X.resizeToContent,X)
}if(Y.custom_elements){function W(Z,a){J(G(Y.custom_elements),function(b){var c;
if(b.indexOf("~")===0){b=b.substring(1);
c="span"
}else{c="div"
}a.content=a.content.replace(new RegExp("<("+b+")([^>]*)>","g"),"<"+c+' mce_name="$1"$2>');
a.content=a.content.replace(new RegExp("</("+b+")>","g"),"</"+c+">")
})
}X.onBeforeSetContent.add(W);
X.onPostProcess.add(function(Z,a){if(a.set){W(Z,a)
}})
}if(Y.handle_node_change_callback){X.onNodeChange.add(function(a,Z,b){X.execCallback("handle_node_change_callback",X.id,b,-1,-1,true,X.selection.isCollapsed())
})
}if(Y.save_callback){X.onSaveContent.add(function(Z,b){var a=X.execCallback("save_callback",X.id,b.content,X.getBody());
if(a){b.content=a
}})
}if(Y.onchange_callback){X.onChange.add(function(a,Z){X.execCallback("onchange_callback",X,Z)
})
}if(Y.convert_newlines_to_brs){X.onBeforeSetContent.add(function(Z,a){if(a.initial){a.content=a.content.replace(/\r?\n/g,"<br />")
}})
}if(Y.fix_nesting&&B){X.onBeforeSetContent.add(function(Z,a){a.content=X._fixNesting(a.content)
})
}if(Y.preformatted){X.onPostProcess.add(function(Z,a){a.content=a.content.replace(/^\s*<pre.*?>/,"");
a.content=a.content.replace(/<\/pre>\s*$/,"");
if(a.set){a.content='<pre class="mceItemHidden">'+a.content+"</pre>"
}})
}if(Y.verify_css_classes){X.serializer.attribValueFilter=function(c,a){var b,Z;
if(c=="class"){if(!X.classesRE){Z=X.dom.getClasses();
if(Z.length>0){b="";
J(Z,function(d){b+=(b?"|":"")+d["class"]
});
X.classesRE=new RegExp("("+b+")","gi")
}}return !X.classesRE||/(\bmceItem\w+\b|\bmceTemp\w+\b)/g.test(a)||X.classesRE.test(a)?a:""
}return a
}
}if(Y.convert_fonts_to_spans){X._convertFonts()
}if(Y.inline_styles){X._convertInlineElements()
}if(Y.cleanup_callback){X.onBeforeSetContent.add(function(Z,a){a.content=X.execCallback("cleanup_callback","insert_to_editor",a.content,a)
});
X.onPreProcess.add(function(Z,a){if(a.set){X.execCallback("cleanup_callback","insert_to_editor_dom",a.node,a)
}if(a.get){X.execCallback("cleanup_callback","get_from_editor_dom",a.node,a)
}});
X.onPostProcess.add(function(Z,a){if(a.set){a.content=X.execCallback("cleanup_callback","insert_to_editor",a.content,a)
}if(a.get){a.content=X.execCallback("cleanup_callback","get_from_editor",a.content,a)
}})
}if(Y.save_callback){X.onGetContent.add(function(Z,a){if(a.save){a.content=X.execCallback("save_callback",X.id,a.content,X.getBody())
}})
}if(Y.handle_event_callback){X.onEvent.add(function(Z,a,b){if(X.execCallback("handle_event_callback",a,Z,b)===false){K.cancel(a)
}})
}X.onSetContent.add(function(){X.addVisual(X.getBody())
});
if(Y.padd_empty_editor){X.onPostProcess.add(function(Z,a){a.content=a.content.replace(/^(<p[^>]*>(&nbsp;|&#160;|\s|\u00a0|)<\/p>[\r\n]*|<br \/>[\r\n]*)$/,"")
})
}if(A){function Q(Z,a){J(Z.dom.select("a"),function(c){var b=c.parentNode;
if(Z.dom.isBlock(b)&&b.lastChild===c){Z.dom.add(b,"br",{mce_bogus:1})
}})
}X.onExecCommand.add(function(Z,a){if(a==="CreateLink"){Q(Z)
}});
X.onSetContent.add(X.selection.onSetContent.add(Q));
if(!Y.readonly){try{T.designMode="Off";
T.designMode="On"
}catch(U){}}}setTimeout(function(){if(X.removed){return 
}X.load({initial:true,format:(Y.cleanup_on_startup?"html":"raw")});
X.startContent=X.getContent({format:"raw"});
X.undoManager.add({initial:true});
X.initialized=true;
X.onInit.dispatch(X);
X.execCallback("setupcontent_callback",X.id,X.getBody(),X.getDoc());
X.execCallback("init_instance_callback",X);
X.focus(true);
X.nodeChanged({initial:1});
if(Y.content_css){N.each(G(Y.content_css),function(Z){X.dom.loadCSS(X.documentBaseURI.toAbsolute(Z))
})
}if(Y.auto_focus){setTimeout(function(){var Z=I.get(Y.auto_focus);
Z.selection.select(Z.getBody(),1);
Z.selection.collapse(1);
Z.getWin().focus()
},100)
}},1);
S=null
},focus:function(R){var T,Q=this,S=Q.settings.content_editable;
if(!R){if(!S&&(!B||Q.selection.getNode().ownerDocument!=Q.getDoc())){Q.getWin().focus()
}}if(I.activeEditor!=Q){if((T=I.activeEditor)!=null){T.onDeactivate.dispatch(T,Q)
}Q.onActivate.dispatch(Q,T)
}I._setActive(Q)
},execCallback:function(T){var Q=this,S=Q.settings[T],R;
if(!S){return 
}if(Q.callbackLookup&&(R=Q.callbackLookup[T])){S=R.func;
R=R.scope
}if(D(S,"string")){R=S.replace(/\.\w+$/,"");
R=R?N.resolve(R):0;
S=N.resolve(S);
Q.callbackLookup=Q.callbackLookup||{};
Q.callbackLookup[T]={func:S,scope:R}
}return S.apply(R||Q,Array.prototype.slice.call(arguments,1))
},translate:function(Q){var S=this.settings.language||"en",R=I.i18n;
if(!Q){return""
}return R[S+"."+Q]||Q.replace(/{\#([^}]+)\}/g,function(U,T){return R[S+"."+T]||"{#"+T+"}"
})
},getLang:function(R,Q){return I.i18n[(this.settings.language||"en")+"."+R]||(D(Q)?Q:"{#"+R+"}")
},getParam:function(V,S,Q){var T=N.trim,R=D(this.settings[V])?this.settings[V]:S,U;
if(Q==="hash"){U={};
if(D(R,"string")){J(R.indexOf("=")>0?R.split(/[;,](?![^=;,]*(?:[;,]|$))/):R.split(","),function(W){W=W.split("=");
if(W.length>1){U[T(W[0])]=T(W[1])
}else{U[T(W[0])]=T(W)
}})
}else{U=R
}return U
}return R
},nodeChanged:function(S){var Q=this,R=Q.selection,T=R.getNode()||Q.getBody();
if(Q.initialized){Q.onNodeChange.dispatch(Q,S?S.controlManager||Q.controlManager:Q.controlManager,B&&T.ownerDocument!=Q.getDoc()?Q.getBody():T,R.isCollapsed(),S)
}},addButton:function(S,R){var Q=this;
Q.buttons=Q.buttons||{};
Q.buttons[S]=R
},addCommand:function(S,R,Q){this.execCommands[S]={func:R,scope:Q||this}
},addQueryStateHandler:function(S,R,Q){this.queryStateCommands[S]={func:R,scope:Q||this}
},addQueryValueHandler:function(S,R,Q){this.queryValueCommands[S]={func:R,scope:Q||this}
},addShortcut:function(S,U,Q,T){var R=this,V;
if(!R.settings.custom_shortcuts){return false
}R.shortcuts=R.shortcuts||{};
if(D(Q,"string")){V=Q;
Q=function(){R.execCommand(V,false,null)
}
}if(D(Q,"object")){V=Q;
Q=function(){R.execCommand(V[0],V[1],V[2])
}
}J(G(S),function(W){var X={func:Q,scope:T||this,desc:U,alt:false,ctrl:false,shift:false};
J(G(W,"+"),function(Y){switch(Y){case"alt":case"ctrl":case"shift":X[Y]=true;
break;
default:X.charCode=Y.charCodeAt(0);
X.keyCode=Y.toUpperCase().charCodeAt(0)
}});
R.shortcuts[(X.ctrl?"ctrl":"")+","+(X.alt?"alt":"")+","+(X.shift?"shift":"")+","+X.keyCode]=X
});
return true
},execCommand:function(V,U,X,Q){var S=this,T=0,W,R;
if(!/^(mceAddUndoLevel|mceEndUndoLevel|mceBeginUndoLevel|mceRepaint|SelectAll)$/.test(V)&&(!Q||!Q.skip_focus)){S.focus()
}W={};
S.onBeforeExecCommand.dispatch(S,V,U,X,W);
if(W.terminate){return false
}if(S.execCallback("execcommand_callback",S.id,S.selection.getNode(),V,U,X)){S.onExecCommand.dispatch(S,V,U,X,Q);
return true
}if(W=S.execCommands[V]){R=W.func.call(W.scope,U,X);
if(R!==true){S.onExecCommand.dispatch(S,V,U,X,Q);
return R
}}J(S.plugins,function(Y){if(Y.execCommand&&Y.execCommand(V,U,X)){S.onExecCommand.dispatch(S,V,U,X,Q);
T=1;
return false
}});
if(T){return true
}if(S.theme&&S.theme.execCommand&&S.theme.execCommand(V,U,X)){S.onExecCommand.dispatch(S,V,U,X,Q);
return true
}if(N.GlobalCommands.execCommand(S,V,U,X)){S.onExecCommand.dispatch(S,V,U,X,Q);
return true
}if(S.editorCommands.execCommand(V,U,X)){S.onExecCommand.dispatch(S,V,U,X,Q);
return true
}S.getDoc().execCommand(V,U,X);
S.onExecCommand.dispatch(S,V,U,X,Q)
},queryCommandState:function(U){var R=this,T,S;
if(R._isHidden()){return 
}if(T=R.queryStateCommands[U]){S=T.func.call(T.scope);
if(S!==true){return S
}}T=R.editorCommands.queryCommandState(U);
if(T!==-1){return T
}try{return this.getDoc().queryCommandState(U)
}catch(Q){}},queryCommandValue:function(U){var R=this,T,S;
if(R._isHidden()){return 
}if(T=R.queryValueCommands[U]){S=T.func.call(T.scope);
if(S!==true){return S
}}T=R.editorCommands.queryCommandValue(U);
if(D(T)){return T
}try{return this.getDoc().queryCommandValue(U)
}catch(Q){}},show:function(){var Q=this;
O.show(Q.getContainer());
O.hide(Q.id);
Q.load()
},hide:function(){var Q=this,R=Q.getDoc();
if(B&&R){R.execCommand("SelectAll")
}Q.save();
O.hide(Q.getContainer());
O.setStyle(Q.id,"display",Q.orgDisplay)
},isHidden:function(){return !O.isHidden(this.id)
},setProgressState:function(Q,R,S){this.onSetProgressState.dispatch(this,Q,R,S);
return Q
},resizeToContent:function(){var Q=this;
O.setStyle(Q.id+"_ifr","height",Q.getBody().scrollHeight)
},load:function(T){var Q=this,S=Q.getElement(),R;
if(S){T=T||{};
T.load=true;
R=Q.setContent(D(S.value)?S.value:S.innerHTML,T);
T.element=S;
if(!T.no_events){Q.onLoadContent.dispatch(Q,T)
}T.element=S=null;
return R
}},save:function(U){var Q=this,T=Q.getElement(),R,S;
if(!T||!Q.initialized){return 
}U=U||{};
U.save=true;
if(!U.no_events){Q.undoManager.typing=0;
Q.undoManager.add()
}U.element=T;
R=U.content=Q.getContent(U);
if(!U.no_events){Q.onSaveContent.dispatch(Q,U)
}R=U.content;
if(!/TEXTAREA|INPUT/i.test(T.nodeName)){T.innerHTML=R;
if(S=O.getParent(Q.id,"form")){J(S.elements,function(V){if(V.name==Q.id){V.value=R;
return false
}})
}}else{T.value=R
}U.element=T=null;
return R
},setContent:function(R,S){var Q=this;
S=S||{};
S.format=S.format||"html";
S.set=true;
S.content=R;
if(!S.no_events){Q.onBeforeSetContent.dispatch(Q,S)
}if(!N.isIE&&(R.length===0||/^\s+$/.test(R))){S.content=Q.dom.setHTML(Q.getBody(),'<br mce_bogus="1" />');
S.format="raw"
}S.content=Q.dom.setHTML(Q.getBody(),N.trim(S.content));
if(S.format!="raw"&&Q.settings.cleanup){S.getInner=true;
S.content=Q.dom.setHTML(Q.getBody(),Q.serializer.serialize(Q.getBody(),S))
}if(!S.no_events){Q.onSetContent.dispatch(Q,S)
}return S.content
},getContent:function(S){var Q=this,R;
S=S||{};
S.format=S.format||"html";
S.get=true;
if(!S.no_events){Q.onBeforeGetContent.dispatch(Q,S)
}if(S.format!="raw"&&Q.settings.cleanup){S.getInner=true;
R=Q.serializer.serialize(Q.getBody(),S)
}else{R=Q.getBody().innerHTML
}R=R.replace(/^\s*|\s*$/g,"");
S.content=R;
if(!S.no_events){Q.onGetContent.dispatch(Q,S)
}return S.content
},isDirty:function(){var Q=this;
return N.trim(Q.startContent)!=N.trim(Q.getContent({format:"raw",no_events:1}))&&!Q.isNotDirty
},getContainer:function(){var Q=this;
if(!Q.container){Q.container=O.get(Q.editorContainer||Q.id+"_parent")
}return Q.container
},getContentAreaContainer:function(){return this.contentAreaContainer
},getElement:function(){return O.get(this.settings.content_element||this.id)
},getWin:function(){var Q=this,R;
if(!Q.contentWindow){R=O.get(Q.id+"_ifr");
if(R){Q.contentWindow=R.contentWindow
}}return Q.contentWindow
},getDoc:function(){var R=this,Q;
if(!R.contentDocument){Q=R.getWin();
if(Q){R.contentDocument=Q.document
}}return R.contentDocument
},getBody:function(){return this.bodyElement||this.getDoc().body
},convertURL:function(Q,U,T){var R=this,S=R.settings;
if(S.urlconverter_callback){return R.execCallback("urlconverter_callback",Q,T,true,U)
}if(!S.convert_urls||(T&&T.nodeName=="LINK")||Q.indexOf("file:")===0){return Q
}if(S.relative_urls){return R.documentBaseURI.toRelative(Q)
}Q=R.documentBaseURI.toAbsolute(Q,S.remove_script_host);
return Q
},addVisual:function(S){var Q=this,R=Q.settings;
S=S||Q.getBody();
if(!D(Q.hasVisual)){Q.hasVisual=R.visual
}J(Q.dom.select("table,a",S),function(U){var T;
switch(U.nodeName){case"TABLE":T=Q.dom.getAttrib(U,"border");
if(!T||T=="0"){if(Q.hasVisual){Q.dom.addClass(U,R.visual_table_class)
}else{Q.dom.removeClass(U,R.visual_table_class)
}}return ;
case"A":T=Q.dom.getAttrib(U,"name");
if(T){if(Q.hasVisual){Q.dom.addClass(U,"mceItemAnchor")
}else{Q.dom.removeClass(U,"mceItemAnchor")
}}return 
}});
Q.onVisualAid.dispatch(Q,S,Q.hasVisual)
},remove:function(){var Q=this,R=Q.getContainer();
Q.removed=1;
Q.hide();
Q.execCallback("remove_instance_callback",Q);
Q.onRemove.dispatch(Q);
Q.onExecCommand.listeners=[];
I.remove(Q);
O.remove(R)
},destroy:function(R){var Q=this;
if(Q.destroyed){return 
}if(!R){N.removeUnload(Q.destroy);
tinyMCE.onBeforeUnload.remove(Q._beforeUnload);
if(Q.theme&&Q.theme.destroy){Q.theme.destroy()
}Q.controlManager.destroy();
Q.selection.destroy();
Q.dom.destroy();
if(!Q.settings.content_editable){K.clear(Q.getWin());
K.clear(Q.getDoc())
}K.clear(Q.getBody());
K.clear(Q.formElement)
}if(Q.formElement){Q.formElement.submit=Q.formElement._mceOldSubmit;
Q.formElement._mceOldSubmit=null
}Q.contentAreaContainer=Q.formElement=Q.container=Q.settings.content_element=Q.bodyElement=Q.contentDocument=Q.contentWindow=null;
if(Q.selection){Q.selection=Q.selection.win=Q.selection.dom=Q.selection.dom.doc=null
}Q.destroyed=1
},_addEvents:function(){var U=this,T,W=U.settings,V={mouseup:"onMouseUp",mousedown:"onMouseDown",click:"onClick",keyup:"onKeyUp",keydown:"onKeyDown",keypress:"onKeyPress",submit:"onSubmit",reset:"onReset",contextmenu:"onContextMenu",dblclick:"onDblClick",paste:"onPaste"};
function S(Z,a){var Y=Z.type;
if(U.removed){return 
}if(U.onEvent.dispatch(U,Z,a)!==false){U[V[Z.fakeType||Z.type]].dispatch(U,Z,a)
}}J(V,function(Z,Y){switch(Y){case"contextmenu":if(N.isOpera){K.add(U.getBody(),"mousedown",function(a){if(a.ctrlKey){a.fakeType="contextmenu";
S(a)
}})
}else{K.add(U.getBody(),Y,S)
}break;
case"paste":K.add(U.getBody(),Y,function(a){S(a)
});
break;
case"submit":case"reset":K.add(U.getElement().form||O.getParent(U.id,"form"),Y,S);
break;
default:K.add(W.content_editable?U.getBody():U.getDoc(),Y,S)
}});
K.add(W.content_editable?U.getBody():(A?U.getDoc():U.getWin()),"focus",function(Y){U.focus(true)
});
if(N.isGecko){K.add(U.getDoc(),"DOMNodeInserted",function(Z){var Y;
Z=Z.target;
if(Z.nodeType===1&&Z.nodeName==="IMG"&&(Y=Z.getAttribute("mce_src"))){Z.src=U.documentBaseURI.toAbsolute(Y)
}})
}if(A){function Q(){var Z=this,b=Z.getDoc(),a=Z.settings;
if(A&&!a.readonly){if(Z._isHidden()){try{if(!a.content_editable){b.designMode="On"
}}catch(Y){}}try{b.execCommand("styleWithCSS",0,false)
}catch(Y){if(!Z._isHidden()){try{b.execCommand("useCSS",0,true)
}catch(Y){}}}if(!a.table_inline_editing){try{b.execCommand("enableInlineTableEditing",false,false)
}catch(Y){}}if(!a.object_resizing){try{b.execCommand("enableObjectResizing",false,false)
}catch(Y){}}}}U.onBeforeExecCommand.add(Q);
U.onMouseDown.add(Q)
}U.onMouseUp.add(U.nodeChanged);
U.onClick.add(U.nodeChanged);
U.onKeyUp.add(function(Y,Z){var a=Z.keyCode;
if((a>=33&&a<=36)||(a>=37&&a<=40)||a==13||a==45||a==46||a==8||(N.isMac&&(a==91||a==93))||Z.ctrlKey){U.nodeChanged()
}});
U.onReset.add(function(){U.setContent(U.startContent,{format:"raw"})
});
if(W.custom_shortcuts){if(W.custom_undo_redo_keyboard_shortcuts){U.addShortcut("ctrl+z",U.getLang("undo_desc"),"Undo");
U.addShortcut("ctrl+y",U.getLang("redo_desc"),"Redo")
}if(A){U.addShortcut("ctrl+b",U.getLang("bold_desc"),"Bold");
U.addShortcut("ctrl+i",U.getLang("italic_desc"),"Italic");
U.addShortcut("ctrl+u",U.getLang("underline_desc"),"Underline")
}for(T=1;
T<=6;
T++){U.addShortcut("ctrl+"+T,"",["FormatBlock",false,"<h"+T+">"])
}U.addShortcut("ctrl+7","",["FormatBlock",false,"<p>"]);
U.addShortcut("ctrl+8","",["FormatBlock",false,"<div>"]);
U.addShortcut("ctrl+9","",["FormatBlock",false,"<address>"]);
function X(Z){var Y=null;
if(!Z.altKey&&!Z.ctrlKey&&!Z.metaKey){return Y
}J(U.shortcuts,function(a){if(N.isMac&&a.ctrl!=Z.metaKey){return 
}else{if(!N.isMac&&a.ctrl!=Z.ctrlKey){return 
}}if(a.alt!=Z.altKey){return 
}if(a.shift!=Z.shiftKey){return 
}if(Z.keyCode==a.keyCode||(Z.charCode&&Z.charCode==a.charCode)){Y=a;
return false
}});
return Y
}U.onKeyUp.add(function(Y,Z){var a=X(Z);
if(a){return K.cancel(Z)
}});
U.onKeyPress.add(function(Y,Z){var a=X(Z);
if(a){return K.cancel(Z)
}});
U.onKeyDown.add(function(Y,Z){var a=X(Z);
if(a){a.func.call(a.scope);
return K.cancel(Z)
}})
}if(N.isIE){K.add(U.getDoc(),"controlselect",function(a){var Z=U.resizeInfo,Y;
a=a.target;
if(a.nodeName!=="IMG"){return 
}if(Z){K.remove(Z.node,Z.ev,Z.cb)
}if(!U.dom.hasClass(a,"mceItemNoResize")){ev="resizeend";
Y=K.add(a,ev,function(c){var b;
c=c.target;
if(b=U.dom.getStyle(c,"width")){U.dom.setAttrib(c,"width",b.replace(/[^0-9%]+/g,""));
U.dom.setStyle(c,"width","")
}if(b=U.dom.getStyle(c,"height")){U.dom.setAttrib(c,"height",b.replace(/[^0-9%]+/g,""));
U.dom.setStyle(c,"height","")
}})
}else{ev="resizestart";
Y=K.add(a,"resizestart",K.cancel,K)
}Z=U.resizeInfo={node:a,ev:ev,cb:Y}
});
U.onKeyDown.add(function(Y,Z){switch(Z.keyCode){case 8:if(U.selection.getRng().item){U.selection.getRng().item(0).removeNode();
return K.cancel(Z)
}}})
}if(N.isOpera){U.onClick.add(function(Y,Z){K.prevent(Z)
})
}if(W.custom_undo_redo){function R(){U.undoManager.typing=0;
U.undoManager.add()
}if(N.isIE){K.add(U.getWin(),"blur",function(Y){var Z;
if(U.selection){Z=U.selection.getNode();
if(!U.removed&&Z.ownerDocument&&Z.ownerDocument!=U.getDoc()){R()
}}})
}else{K.add(U.getDoc(),"blur",function(){if(U.selection&&!U.removed){R()
}})
}U.onMouseDown.add(R);
U.onKeyUp.add(function(Y,Z){if((Z.keyCode>=33&&Z.keyCode<=36)||(Z.keyCode>=37&&Z.keyCode<=40)||Z.keyCode==13||Z.keyCode==45||Z.ctrlKey){U.undoManager.typing=0;
U.undoManager.add()
}});
U.onKeyDown.add(function(Y,Z){if((Z.keyCode>=33&&Z.keyCode<=36)||(Z.keyCode>=37&&Z.keyCode<=40)||Z.keyCode==13||Z.keyCode==45){if(U.undoManager.typing){U.undoManager.add();
U.undoManager.typing=0
}return 
}if(!U.undoManager.typing){U.undoManager.add();
U.undoManager.typing=1
}})
}},_convertInlineElements:function(){var W=this,Y=W.settings,R=W.dom,V,T,S,X,Q;
function U(Z,a){if(!Y.inline_styles){return 
}if(a.get){J(W.dom.select("table,u,strike",a.node),function(b){switch(b.nodeName){case"TABLE":if(V=R.getAttrib(b,"height")){R.setStyle(b,"height",V);
R.setAttrib(b,"height","")
}break;
case"U":case"STRIKE":b.style.textDecoration=b.nodeName=="U"?"underline":"line-through";
R.setAttrib(b,"mce_style","");
R.setAttrib(b,"mce_name","span");
break
}})
}else{if(a.set){J(W.dom.select("table,span",a.node).reverse(),function(b){if(b.nodeName=="TABLE"){if(V=R.getStyle(b,"height")){R.setAttrib(b,"height",V.replace(/[^0-9%]+/g,""))
}}else{if(b.style.textDecoration=="underline"){S="u"
}else{if(b.style.textDecoration=="line-through"){S="strike"
}else{S=""
}}if(S){b.style.textDecoration="";
R.setAttrib(b,"mce_style","");
T=R.create(S,{style:R.getAttrib(b,"style")});
R.replace(T,b,1)
}}})
}}}W.onPreProcess.add(U);
if(!Y.cleanup_on_startup){W.onSetContent.add(function(Z,a){if(a.initial){U(W,{node:W.getBody(),set:1})
}})
}},_convertFonts:function(){var U=this,V=U.settings,X=U.dom,T,R,Q,S;
if(!V.inline_styles){return 
}T=[8,10,12,14,18,24,36];
R=["xx-small","x-small","small","medium","large","x-large","xx-large"];
if(Q=V.font_size_style_values){Q=G(Q)
}if(S=V.font_size_classes){S=G(S)
}function W(b){var c,a,Z,Y;
if(!V.inline_styles){return 
}Z=U.dom.select("font",b);
for(Y=Z.length-1;
Y>=0;
Y--){c=Z[Y];
a=X.create("span",{style:X.getAttrib(c,"style"),"class":X.getAttrib(c,"class")});
X.setStyles(a,{fontFamily:X.getAttrib(c,"face"),color:X.getAttrib(c,"color"),backgroundColor:c.style.backgroundColor});
if(c.size){if(Q){X.setStyle(a,"fontSize",Q[parseInt(c.size)-1])
}else{X.setAttrib(a,"class",S[parseInt(c.size)-1])
}}X.setAttrib(a,"mce_style","");
X.replace(a,c,1)
}}U.onPreProcess.add(function(Y,Z){if(Z.get){W(Z.node)
}});
U.onSetContent.add(function(Y,Z){if(Z.initial){W(Z.node)
}})
},_isHidden:function(){var Q;
if(!A){return 0
}Q=this.selection.getSel();
return(!Q||!Q.rangeCount||Q.rangeCount==0)
},_fixNesting:function(R){var S=[],Q;
R=R.replace(/<(\/)?([^\s>]+)[^>]*?>/g,function(U,T,W){var V;
if(T==="/"){if(!S.length){return""
}if(W!==S[S.length-1].tag){for(Q=S.length-1;
Q>=0;
Q--){if(S[Q].tag===W){S[Q].close=1;
break
}}return""
}else{S.pop();
if(S.length&&S[S.length-1].close){U=U+"</"+S[S.length-1].tag+">";
S.pop()
}}}else{if(/^(br|hr|input|meta|img|link|param)$/i.test(W)){return U
}if(/\/>$/.test(U)){return U
}S.push({tag:W})
}return U
});
for(Q=S.length-1;
Q>=0;
Q--){R+="</"+S[Q].tag+">"
}return R
}})
})(tinymce);
(function(D){var F=D.each,C=D.isIE,A=D.isGecko,B=D.isOpera,E=D.isWebKit;
D.create("tinymce.EditorCommands",{EditorCommands:function(G){this.editor=G
},execCommand:function(K,J,L){var H=this,G=H.editor,I;
switch(K){case"mceResetDesignMode":case"mceBeginUndoLevel":return true;
case"unlink":H.UnLink();
return true;
case"JustifyLeft":case"JustifyCenter":case"JustifyRight":case"JustifyFull":H.mceJustify(K,K.substring(7).toLowerCase());
return true;
default:I=this[K];
if(I){I.call(this,J,L);
return true
}}return false
},Indent:function(){var G=this.editor,L=G.dom,J=G.selection,K,H,I;
H=G.settings.indentation;
I=/[a-z%]+$/i.exec(H);
H=parseInt(H);
if(G.settings.inline_styles&&(!this.queryStateInsertUnorderedList()&&!this.queryStateInsertOrderedList())){F(J.getSelectedBlocks(),function(M){L.setStyle(M,"paddingLeft",(parseInt(M.style.paddingLeft||0)+H)+I)
});
return 
}G.getDoc().execCommand("Indent",false,null);
if(C){L.getParent(J.getNode(),function(M){if(M.nodeName=="BLOCKQUOTE"){M.dir=M.style.cssText=""
}})
}},Outdent:function(){var H=this.editor,M=H.dom,K=H.selection,L,G,I,J;
I=H.settings.indentation;
J=/[a-z%]+$/i.exec(I);
I=parseInt(I);
if(H.settings.inline_styles&&(!this.queryStateInsertUnorderedList()&&!this.queryStateInsertOrderedList())){F(K.getSelectedBlocks(),function(N){G=Math.max(0,parseInt(N.style.paddingLeft||0)-I);
M.setStyle(N,"paddingLeft",G?G+J:"")
});
return 
}H.getDoc().execCommand("Outdent",false,null)
},mceSetContent:function(H,G){this.editor.setContent(G)
},mceToggleVisualAid:function(){var G=this.editor;
G.hasVisual=!G.hasVisual;
G.addVisual()
},mceReplaceContent:function(H,G){var I=this.editor.selection;
I.setContent(G.replace(/\{\$selection\}/g,I.getContent({format:"text"})))
},mceInsertLink:function(I,H){var G=this.editor,J=G.selection,K=G.dom.getParent(J.getNode(),"a");
if(D.is(H,"string")){H={href:H}
}function L(M){F(H,function(O,N){G.dom.setAttrib(M,N,O)
})
}if(!K){G.execCommand("CreateLink",false,"javascript:mctmp(0);");
F(G.dom.select("a[href=javascript:mctmp(0);]"),function(M){L(M)
})
}else{if(H.href){L(K)
}else{G.dom.remove(K,1)
}}},UnLink:function(){var G=this.editor,H=G.selection;
if(H.isCollapsed()){H.select(H.getNode())
}G.getDoc().execCommand("unlink",false,null);
H.collapse(0)
},FontName:function(I,H){var J=this,G=J.editor,K=G.selection,L;
if(!H){if(K.isCollapsed()){K.select(K.getNode())
}}else{if(G.settings.convert_fonts_to_spans){J._applyInlineStyle("span",{style:{fontFamily:H}})
}else{G.getDoc().execCommand("FontName",false,H)
}}},FontSize:function(J,I){var H=this.editor,L=H.settings,K,G;
if(L.convert_fonts_to_spans&&I>=1&&I<=7){G=D.explode(L.font_size_style_values);
K=D.explode(L.font_size_classes);
if(K){I=K[I-1]||I
}else{I=G[I-1]||I
}}if(I>=1&&I<=7){H.getDoc().execCommand("FontSize",false,I)
}else{this._applyInlineStyle("span",{style:{fontSize:I}})
}},queryCommandValue:function(H){var G=this["queryValue"+H];
if(G){return G.call(this,H)
}return false
},queryCommandState:function(H){var G;
switch(H){case"JustifyLeft":case"JustifyCenter":case"JustifyRight":case"JustifyFull":return this.queryStateJustify(H,H.substring(7).toLowerCase());
default:if(G=this["queryState"+H]){return G.call(this,H)
}}return -1
},_queryState:function(H){try{return this.editor.getDoc().queryCommandState(H)
}catch(G){}},_queryVal:function(H){try{return this.editor.getDoc().queryCommandValue(H)
}catch(G){}},queryValueFontSize:function(){var H=this.editor,G=0,I;
if(I=H.dom.getParent(H.selection.getNode(),"span")){G=I.style.fontSize
}if(!G&&(B||E)){if(I=H.dom.getParent(H.selection.getNode(),"font")){G=I.size
}return G
}return G||this._queryVal("FontSize")
},queryValueFontName:function(){var H=this.editor,G=0,I;
if(I=H.dom.getParent(H.selection.getNode(),"font")){G=I.face
}if(I=H.dom.getParent(H.selection.getNode(),"span")){G=I.style.fontFamily.replace(/, /g,",").replace(/[\'\"]/g,"").toLowerCase()
}if(!G){G=this._queryVal("FontName")
}return G
},mceJustify:function(N,O){var K=this.editor,M=K.selection,G=M.getNode(),P=G.nodeName,H,J,I=K.dom,L;
if(K.settings.inline_styles&&this.queryStateJustify(N,O)){L=1
}H=I.getParent(G,K.dom.isBlock);
if(P=="IMG"){if(O=="full"){return 
}if(L){if(O=="center"){I.setStyle(H||G.parentNode,"textAlign","")
}I.setStyle(G,"float","");
this.mceRepaint();
return 
}if(O=="center"){if(H&&/^(TD|TH)$/.test(H.nodeName)){H=0
}if(!H||H.childNodes.length>1){J=I.create("p");
J.appendChild(G.cloneNode(false));
if(H){I.insertAfter(J,H)
}else{I.insertAfter(J,G)
}I.remove(G);
G=J.firstChild;
H=J
}I.setStyle(H,"textAlign",O);
I.setStyle(G,"float","")
}else{I.setStyle(G,"float",O);
I.setStyle(H||G.parentNode,"textAlign","")
}this.mceRepaint();
return 
}if(K.settings.inline_styles&&K.settings.forced_root_block){if(L){O=""
}F(M.getSelectedBlocks(I.getParent(M.getStart(),I.isBlock),I.getParent(M.getEnd(),I.isBlock)),function(Q){I.setAttrib(Q,"align","");
I.setStyle(Q,"textAlign",O=="full"?"justify":O)
});
return 
}else{if(!L){K.getDoc().execCommand(N,false,null)
}}if(K.settings.inline_styles){if(L){I.getParent(K.selection.getNode(),function(Q){if(Q.style&&Q.style.textAlign){I.setStyle(Q,"textAlign","")
}});
return 
}F(I.select("*"),function(R){var Q=R.align;
if(Q){if(Q=="full"){Q="justify"
}I.setStyle(R,"textAlign",Q);
I.setAttrib(R,"align","")
}})
}},mceSetCSSClass:function(H,G){this.mceSetStyleInfo(0,{command:"setattrib",name:"class",value:G})
},getSelectedElement:function(){var U=this,O=U.editor,N=O.dom,R=O.selection,H=R.getRng(),L,K,S,P,J,G,Q,I,V,T;
if(R.isCollapsed()||H.item){return R.getNode()
}T=O.settings.merge_styles_invalid_parents;
if(D.is(T,"string")){T=new RegExp(T,"i")
}if(C){L=H.duplicate();
L.collapse(true);
S=L.parentElement();
K=H.duplicate();
K.collapse(false);
P=K.parentElement();
if(S!=P){L.move("character",1);
S=L.parentElement()
}if(S==P){L=H.duplicate();
L.moveToElementText(S);
if(L.compareEndPoints("StartToStart",H)==0&&L.compareEndPoints("EndToEnd",H)==0){return T&&T.test(S.nodeName)?null:S
}}}else{function M(W){return N.getParent(W,"*")
}S=H.startContainer;
P=H.endContainer;
J=H.startOffset;
G=H.endOffset;
if(!H.collapsed){if(S==P){if(J-G<2){if(S.hasChildNodes()){I=S.childNodes[J];
return T&&T.test(I.nodeName)?null:I
}}}}if(S.nodeType!=3||P.nodeType!=3){return null
}if(J==0){I=M(S);
if(I&&I.firstChild!=S){I=null
}}if(J==S.nodeValue.length){Q=S.nextSibling;
if(Q&&Q.nodeType==1){I=S.nextSibling
}}if(G==0){Q=P.previousSibling;
if(Q&&Q.nodeType==1){V=Q
}}if(G==P.nodeValue.length){V=M(P);
if(V&&V.lastChild!=P){V=null
}}if(I==V){return T&&I&&T.test(I.nodeName)?null:I
}}return null
},mceSetStyleInfo:function(N,M){var Q=this,H=Q.editor,J=H.getDoc(),G=H.dom,I,K,R=H.selection,P=M.wrapper||"span",K=R.getBookmark(),O;
function L(T,S){if(T.nodeType==1){switch(M.command){case"setattrib":return G.setAttrib(T,M.name,M.value);
case"setstyle":return G.setStyle(T,M.name,M.value);
case"removeformat":return G.setAttrib(T,"class","")
}}}O=H.settings.merge_styles_invalid_parents;
if(D.is(O,"string")){O=new RegExp(O,"i")
}if((I=Q.getSelectedElement())&&!H.settings.force_span_wrappers){L(I,1)
}else{J.execCommand("FontName",false,"__");
F(G.select("span,font"),function(U){var S,T;
if(G.getAttrib(U,"face")=="__"||U.style.fontFamily==="__"){S=G.create(P,{mce_new:"1"});
L(S);
F(U.childNodes,function(V){S.appendChild(V.cloneNode(true))
});
G.replace(S,U)
}})
}F(G.select(P).reverse(),function(T){var S=T.parentNode;
if(!G.getAttrib(T,"mce_new")){S=G.getParent(T,"*[mce_new]");
if(S){G.remove(T,1)
}}});
F(G.select(P).reverse(),function(T){var S=T.parentNode;
if(!S||!G.getAttrib(T,"mce_new")){return 
}if(H.settings.force_span_wrappers&&S.nodeName!="SPAN"){return 
}if(S.nodeName==P.toUpperCase()&&S.childNodes.length==1){return G.remove(S,1)
}if(T.nodeType==1&&(!O||!O.test(S.nodeName))&&S.childNodes.length==1){L(S);
G.setAttrib(T,"class","")
}});
F(G.select(P).reverse(),function(S){if(G.getAttrib(S,"mce_new")||(G.getAttribs(S).length<=1&&S.className==="")){if(!G.getAttrib(S,"class")&&!G.getAttrib(S,"style")){return G.remove(S,1)
}G.setAttrib(S,"mce_new","")
}});
R.moveToBookmark(K)
},queryStateJustify:function(K,H){var G=this.editor,J=G.selection.getNode(),I=G.dom;
if(J&&J.nodeName=="IMG"){if(I.getStyle(J,"float")==H){return 1
}return J.parentNode.style.textAlign==H
}J=I.getParent(G.selection.getStart(),function(L){return L.nodeType==1&&L.style.textAlign
});
if(H=="full"){H="justify"
}if(G.settings.inline_styles){return(J&&J.style.textAlign==H)
}return this._queryState(K)
},ForeColor:function(I,H){var G=this.editor;
if(G.settings.convert_fonts_to_spans){this._applyInlineStyle("span",{style:{color:H}});
return 
}else{G.getDoc().execCommand("ForeColor",false,H)
}},HiliteColor:function(I,K){var H=this,G=H.editor,J=G.getDoc();
if(G.settings.convert_fonts_to_spans){this._applyInlineStyle("span",{style:{backgroundColor:K}});
return 
}function L(N){if(!A){return 
}try{J.execCommand("styleWithCSS",0,N)
}catch(M){J.execCommand("useCSS",0,!N)
}}if(A||B){L(true);
J.execCommand("hilitecolor",false,K);
L(false)
}else{J.execCommand("BackColor",false,K)
}},FormatBlock:function(N,H){var O=this,L=O.editor,P=L.selection,J=L.dom,G,K,M;
function I(Q){return/^(P|DIV|H[1-6]|ADDRESS|BLOCKQUOTE|PRE)$/.test(Q.nodeName)
}G=J.getParent(P.getNode(),function(Q){return I(Q)
});
if(G){if((C&&I(G.parentNode))||G.nodeName=="DIV"){K=L.dom.create(H);
F(J.getAttribs(G),function(Q){J.setAttrib(K,Q.nodeName,J.getAttrib(G,Q.nodeName))
});
M=P.getBookmark();
J.replace(K,G,1);
P.moveToBookmark(M);
L.nodeChanged();
return 
}}H=L.settings.forced_root_block?(H||"<p>"):H;
if(H.indexOf("<")==-1){H="<"+H+">"
}if(D.isGecko){H=H.replace(/<(div|blockquote|code|dt|dd|dl|samp)>/gi,"$1")
}L.getDoc().execCommand("FormatBlock",false,H)
},mceCleanup:function(){var H=this.editor,I=H.selection,G=I.getBookmark();
H.setContent(H.getContent());
I.moveToBookmark(G)
},mceRemoveNode:function(J,K){var H=this.editor,I=H.selection,G,L=K||I.getNode();
if(L==H.getBody()){return 
}G=I.getBookmark();
H.dom.remove(L,1);
I.moveToBookmark(G);
H.nodeChanged()
},mceSelectNodeDepth:function(I,J){var G=this.editor,H=G.selection,K=0;
G.dom.getParent(H.getNode(),function(L){if(L.nodeType==1&&K++==J){H.select(L);
G.nodeChanged();
return false
}},G.getBody())
},mceSelectNode:function(H,G){this.editor.selection.select(G)
},mceInsertContent:function(G,H){this.editor.selection.setContent(H)
},mceInsertRawHTML:function(H,I){var G=this.editor;
G.selection.setContent("tiny_mce_marker");
G.setContent(G.getContent().replace(/tiny_mce_marker/g,I))
},mceRepaint:function(){var I,G,J=this.editor;
if(D.isGecko){try{I=J.selection;
G=I.getBookmark(true);
if(I.getSel()){I.getSel().selectAllChildren(J.getBody())
}I.collapse(true);
I.moveToBookmark(G)
}catch(H){}}},queryStateUnderline:function(){var G=this.editor,H=G.selection.getNode();
if(H&&H.nodeName=="A"){return false
}return this._queryState("Underline")
},queryStateOutdent:function(){var G=this.editor,H;
if(G.settings.inline_styles){if((H=G.dom.getParent(G.selection.getStart(),G.dom.isBlock))&&parseInt(H.style.paddingLeft)>0){return true
}if((H=G.dom.getParent(G.selection.getEnd(),G.dom.isBlock))&&parseInt(H.style.paddingLeft)>0){return true
}}return this.queryStateInsertUnorderedList()||this.queryStateInsertOrderedList()||(!G.settings.inline_styles&&!!G.dom.getParent(G.selection.getNode(),"BLOCKQUOTE"))
},queryStateInsertUnorderedList:function(){return this.editor.dom.getParent(this.editor.selection.getNode(),"UL")
},queryStateInsertOrderedList:function(){return this.editor.dom.getParent(this.editor.selection.getNode(),"OL")
},queryStatemceBlockQuote:function(){return !!this.editor.dom.getParent(this.editor.selection.getStart(),function(G){return G.nodeName==="BLOCKQUOTE"
})
},_applyInlineStyle:function(O,J,M){var Q=this,N=Q.editor,L=N.dom,I,P={},K,R;
O=O.toUpperCase();
if(M&&M.check_classes&&J["class"]){M.check_classes.push(J["class"])
}function H(){F(L.select(O).reverse(),function(T){var S=0;
F(L.getAttribs(T),function(U){if(U.nodeName.substring(0,1)!="_"&&L.getAttrib(T,U.nodeName)!=""){S++
}});
if(S==0){L.remove(T,1)
}})
}function G(){var S;
F(L.select("span,font"),function(T){if(T.style.fontFamily=="mceinline"||T.face=="mceinline"){if(!S){S=N.selection.getBookmark()
}J._mce_new="1";
L.replace(L.create(O,J),T,1)
}});
F(L.select(O+"[_mce_new]"),function(U){function T(V){if(V.nodeType==1){F(J.style,function(X,W){L.setStyle(V,W,"")
});
if(J["class"]&&V.className&&M){F(M.check_classes,function(W){if(L.hasClass(V,W)){L.removeClass(V,W)
}})
}}}F(L.select(O,U),T);
if(U.parentNode&&U.parentNode.nodeType==1&&U.parentNode.childNodes.length==1){T(U.parentNode)
}L.getParent(U.parentNode,function(V){if(V.nodeType==1){if(J.style){F(J.style,function(Y,X){var W;
if(!P[X]&&(W=L.getStyle(V,X))){if(W===Y){L.setStyle(U,X,"")
}P[X]=1
}})
}if(J["class"]&&V.className&&M){F(M.check_classes,function(W){if(L.hasClass(V,W)){L.removeClass(U,W)
}})
}}return false
});
U.removeAttribute("_mce_new")
});
H();
N.selection.moveToBookmark(S);
return !!S
}N.focus();
N.getDoc().execCommand("FontName",false,"mceinline");
G();
if(K=Q._applyInlineStyle.keyhandler){N.onKeyUp.remove(K);
N.onKeyPress.remove(K);
N.onKeyDown.remove(K);
N.onSetContent.remove(Q._applyInlineStyle.chandler)
}if(N.selection.isCollapsed()){if(!C){F(L.getParents(N.selection.getNode(),"span"),function(S){F(J.style,function(U,T){var V;
if(V=L.getStyle(S,T)){if(V==U){L.setStyle(S,T,"");
R=2;
return false
}R=1;
return false
}});
if(R){return false
}});
if(R==2){I=N.selection.getBookmark();
H();
N.selection.moveToBookmark(I);
window.setTimeout(function(){N.nodeChanged()
},1);
return 
}}Q._pendingStyles=D.extend(Q._pendingStyles||{},J.style);
Q._applyInlineStyle.chandler=N.onSetContent.add(function(){delete Q._pendingStyles
});
Q._applyInlineStyle.keyhandler=K=function(S){if(Q._pendingStyles){J.style=Q._pendingStyles;
delete Q._pendingStyles
}if(G()){N.onKeyDown.remove(Q._applyInlineStyle.keyhandler);
N.onKeyPress.remove(Q._applyInlineStyle.keyhandler)
}if(S.type=="keyup"){N.onKeyUp.remove(Q._applyInlineStyle.keyhandler)
}};
N.onKeyDown.add(K);
N.onKeyPress.add(K);
N.onKeyUp.add(K)
}else{Q._pendingStyles=0
}}})
})(tinymce);
(function(A){A.create("tinymce.UndoManager",{index:0,data:null,typing:0,UndoManager:function(C){var D=this,B=A.util.Dispatcher;
D.editor=C;
D.data=[];
D.onAdd=new B(this);
D.onUndo=new B(this);
D.onRedo=new B(this)
},add:function(C){var F=this,E,D=F.editor,B,G=D.settings,H;
C=C||{};
C.content=C.content||D.getContent({format:"raw",no_events:1});
C.content=C.content.replace(/^\s*|\s*$/g,"");
H=F.data[F.index>0&&(F.index==0||F.index==F.data.length)?F.index-1:F.index];
if(!C.initial&&H&&C.content==H.content){return null
}if(G.custom_undo_redo_levels){if(F.data.length>G.custom_undo_redo_levels){for(E=0;
E<F.data.length-1;
E++){F.data[E]=F.data[E+1]
}F.data.length--;
F.index=F.data.length
}}if(G.custom_undo_redo_restore_selection&&!C.initial){C.bookmark=B=C.bookmark||D.selection.getBookmark()
}if(F.index<F.data.length){F.index++
}if(F.data.length===0&&!C.initial){return null
}F.data.length=F.index+1;
F.data[F.index++]=C;
if(C.initial){F.index=0
}if(F.data.length==2&&F.data[0].initial){F.data[0].bookmark=B
}F.onAdd.dispatch(F,C);
D.isNotDirty=0;
return C
},undo:function(){var E=this,C=E.editor,B=B,D;
if(E.typing){E.add();
E.typing=0
}if(E.index>0){if(E.index==E.data.length&&E.index>1){D=E.index;
E.typing=0;
if(!E.add()){E.index=D
}--E.index
}B=E.data[--E.index];
C.setContent(B.content,{format:"raw"});
C.selection.moveToBookmark(B.bookmark);
E.onUndo.dispatch(E,B)
}return B
},redo:function(){var D=this,C=D.editor,B=null;
if(D.index<D.data.length-1){B=D.data[++D.index];
C.setContent(B.content,{format:"raw"});
C.selection.moveToBookmark(B.bookmark);
D.onRedo.dispatch(D,B)
}return B
},clear:function(){var B=this;
B.data=[];
B.index=0;
B.typing=0;
B.add({initial:true})
},hasUndo:function(){return this.index!=0||this.typing
},hasRedo:function(){return this.index<this.data.length-1
}})
})(tinymce);
(function(E){var B,D,A,C,F,H;
B=E.dom.Event;
D=E.isIE;
A=E.isGecko;
C=E.isOpera;
F=E.each;
H=E.extend;
function G(I){I=I.innerHTML;
I=I.replace(/<\w+ .*?mce_\w+\"?=.*?>/gi,"-");
I=I.replace(/<(img|hr|table)/gi,"-");
I=I.replace(/<[^>]+>/g,"");
return I.replace(/[ \t\r\n]+/g,"")==""
}E.create("tinymce.ForceBlocks",{ForceBlocks:function(J){var K=this,L=J.settings,M;
K.editor=J;
K.dom=J.dom;
M=(L.forced_root_block||"p").toLowerCase();
L.element=M.toUpperCase();
J.onPreInit.add(K.setup,K);
K.reOpera=new RegExp("(\\u00a0|&#160;|&nbsp;)</"+M+">","gi");
K.rePadd=new RegExp("<p( )([^>]+)><\\/p>|<p( )([^>]+)\\/>|<p( )([^>]+)>\\s+<\\/p>|<p><\\/p>|<p\\/>|<p>\\s+<\\/p>".replace(/p/g,M),"gi");
K.reNbsp2BR1=new RegExp("<p( )([^>]+)>[\\s\\u00a0]+<\\/p>|<p>[\\s\\u00a0]+<\\/p>".replace(/p/g,M),"gi");
K.reNbsp2BR2=new RegExp("<%p()([^>]+)>(&nbsp;|&#160;)<\\/%p>|<%p>(&nbsp;|&#160;)<\\/%p>".replace(/%p/g,M),"gi");
K.reBR2Nbsp=new RegExp("<p( )([^>]+)>\\s*<br \\/>\\s*<\\/p>|<p>\\s*<br \\/>\\s*<\\/p>".replace(/p/g,M),"gi");
function I(N,O){if(C){O.content=O.content.replace(K.reOpera,"</"+M+">")
}O.content=O.content.replace(K.rePadd,"<"+M+"$1$2$3$4$5$6>\u00a0</"+M+">");
if(!D&&!C&&O.set){O.content=O.content.replace(K.reNbsp2BR1,"<"+M+"$1$2><br /></"+M+">");
O.content=O.content.replace(K.reNbsp2BR2,"<"+M+"$1$2><br /></"+M+">")
}else{O.content=O.content.replace(K.reBR2Nbsp,"<"+M+"$1$2>\u00a0</"+M+">")
}}J.onBeforeSetContent.add(I);
J.onPostProcess.add(I);
if(L.forced_root_block){J.onInit.add(K.forceRoots,K);
J.onSetContent.add(K.forceRoots,K);
J.onBeforeGetContent.add(K.forceRoots,K)
}},setup:function(){var J=this,I=J.editor,K=I.settings;
if(K.forced_root_block){I.onKeyUp.add(J.forceRoots,J);
I.onPreProcess.add(J.forceRoots,J)
}if(K.force_br_newlines){if(D){I.onKeyPress.add(function(M,O){var P,N=M.selection;
if(O.keyCode==13&&N.getNode().nodeName!="LI"){N.setContent('<br id="__" /> ',{format:"raw"});
P=M.dom.get("__");
P.removeAttribute("id");
N.select(P);
N.collapse();
return B.cancel(O)
}})
}return 
}if(!D&&K.force_p_newlines){I.onKeyPress.add(function(M,N){if(N.keyCode==13&&!N.shiftKey){if(!J.insertPara(N)){B.cancel(N)
}}});
if(A){I.onKeyDown.add(function(M,N){if((N.keyCode==8||N.keyCode==46)&&!N.shiftKey){J.backspaceDelete(N,N.keyCode==8)
}})
}}function L(N,M){var O=I.dom.create(M);
F(N.attributes,function(P){if(P.specified&&P.nodeValue){O.setAttribute(P.nodeName.toLowerCase(),P.nodeValue)
}});
F(N.childNodes,function(P){O.appendChild(P.cloneNode(true))
});
N.parentNode.replaceChild(O,N);
return O
}if(D){I.onPreProcess.add(function(M,N){F(M.dom.select("p,h1,h2,h3,h4,h5,h6,div",N.node),function(O){if(G(O)){O.innerHTML=""
}})
});
if(K.element!="P"){I.onKeyPress.add(function(M,N){J.lastElm=M.selection.getNode().nodeName
});
I.onKeyUp.add(function(N,P){var R,O=N.selection,Q=O.getNode(),M=N.getBody();
if(M.childNodes.length===1&&Q.nodeName=="P"){Q=L(Q,K.element);
O.select(Q);
O.collapse();
N.nodeChanged()
}else{if(P.keyCode==13&&!P.shiftKey&&J.lastElm!="P"){R=N.dom.getParent(Q,"p");
if(R){L(R,K.element);
N.nodeChanged()
}}}})
}}},find:function(N,K,L){var J=this.editor,I=J.getDoc().createTreeWalker(N,4,null,false),M=-1;
while(N=I.nextNode()){M++;
if(K==0&&N==L){return M
}if(K==1&&M==L){return N
}}return -1
},forceRoots:function(N,Y){var P=this,N=P.editor,g=N.getBody(),Z=N.getDoc(),k=N.selection,Q=k.getSel(),R=k.getRng(),h=-2,M,W,I,J,a=-16777215;
var f,K,j,V,S,L=g.childNodes,U,T,O;
for(U=L.length-1;
U>=0;
U--){f=L[U];
if(f.nodeType==3||(!P.dom.isBlock(f)&&f.nodeType!=8)){if(!K){if(f.nodeType!=3||/[^\s]/g.test(f.nodeValue)){if(h==-2&&R){if(!D){if(R.startContainer.nodeType==1&&(T=R.startContainer.childNodes[R.startOffset])&&T.nodeType==1){O=T.getAttribute("id");
T.setAttribute("id","__mce")
}else{if(N.dom.getParent(R.startContainer,function(b){return b===g
})){W=R.startOffset;
I=R.endOffset;
h=P.find(g,0,R.startContainer);
M=P.find(g,0,R.endContainer)
}}}else{J=Z.body.createTextRange();
J.moveToElementText(g);
J.collapse(1);
j=J.move("character",a)*-1;
J=R.duplicate();
J.collapse(1);
V=J.move("character",a)*-1;
J=R.duplicate();
J.collapse(0);
S=(J.move("character",a)*-1)-V;
h=V-j;
M=S
}}K=N.dom.create(N.settings.forced_root_block);
K.appendChild(f.cloneNode(1));
f.parentNode.replaceChild(K,f)
}}else{if(K.hasChildNodes()){K.insertBefore(f,K.firstChild)
}else{K.appendChild(f)
}}}else{K=null
}}if(h!=-2){if(!D){K=g.getElementsByTagName(N.settings.element)[0];
R=Z.createRange();
if(h!=-1){R.setStart(P.find(g,1,h),W)
}else{R.setStart(K,0)
}if(M!=-1){R.setEnd(P.find(g,1,M),I)
}else{R.setEnd(K,0)
}if(Q){Q.removeAllRanges();
Q.addRange(R)
}}else{try{R=Q.createRange();
R.moveToElementText(g);
R.collapse(1);
R.moveStart("character",h);
R.moveEnd("character",M);
R.select()
}catch(X){}}}else{if(!D&&(T=N.dom.get("__mce"))){if(O){T.setAttribute("id",O)
}else{T.removeAttribute("id")
}R=Z.createRange();
R.setStartBefore(T);
R.setEndBefore(T);
k.setRng(R)
}}},getParentBlock:function(J){var I=this.dom;
return I.getParent(J,I.isBlock)
},insertPara:function(k){var V=this,N=V.editor,g=N.dom,l=N.getDoc(),q=N.settings,W=N.selection.getSel(),X=W.getRangeAt(0),p=l.body;
var a,c,Y,i,h,L,J,M,P,I,T,o,K,O,Z,f=g.getViewPort(N.getWin()),S,U,R;
a=l.createRange();
a.setStart(W.anchorNode,W.anchorOffset);
a.collapse(true);
c=l.createRange();
c.setStart(W.focusNode,W.focusOffset);
c.collapse(true);
Y=a.compareBoundaryPoints(a.START_TO_END,c)<0;
i=Y?W.anchorNode:W.focusNode;
h=Y?W.anchorOffset:W.focusOffset;
L=Y?W.focusNode:W.anchorNode;
J=Y?W.focusOffset:W.anchorOffset;
if(i===L&&/^(TD|TH)$/.test(i.nodeName)){g.remove(i.firstChild);
N.dom.add(i,q.element,null,"<br />");
o=N.dom.add(i,q.element,null,"<br />");
X=l.createRange();
X.selectNodeContents(o);
X.collapse(1);
N.selection.setRng(X);
return false
}if(i==p&&L==p&&p.firstChild&&N.dom.isBlock(p.firstChild)){i=L=i.firstChild;
h=J=0;
a=l.createRange();
a.setStart(i,0);
c=l.createRange();
c.setStart(L,0)
}i=i.nodeName=="HTML"?l.body:i;
i=i.nodeName=="BODY"?i.firstChild:i;
L=L.nodeName=="HTML"?l.body:L;
L=L.nodeName=="BODY"?L.firstChild:L;
M=V.getParentBlock(i);
P=V.getParentBlock(L);
I=M?M.nodeName:q.element;
if(V.dom.getParent(M,"ol,ul,pre")){return true
}if(M&&(M.nodeName=="CAPTION"||/absolute|relative|fixed/gi.test(g.getStyle(M,"position",1)))){I=q.element;
M=null
}if(P&&(P.nodeName=="CAPTION"||/absolute|relative|fixed/gi.test(g.getStyle(M,"position",1)))){I=q.element;
P=null
}if(/(TD|TABLE|TH|CAPTION)/.test(I)||(M&&I=="DIV"&&/left|right/gi.test(g.getStyle(M,"float",1)))){I=q.element;
M=P=null
}T=(M&&M.nodeName==I)?M.cloneNode(0):N.dom.create(I);
o=(P&&P.nodeName==I)?P.cloneNode(0):N.dom.create(I);
o.removeAttribute("id");
if(/^(H[1-6])$/.test(I)&&i.nodeValue&&h==i.nodeValue.length){o=N.dom.create(q.element)
}Z=K=i;
do{if(Z==p||Z.nodeType==9||V.dom.isBlock(Z)||/(TD|TABLE|TH|CAPTION)/.test(Z.nodeName)){break
}K=Z
}while((Z=Z.previousSibling?Z.previousSibling:Z.parentNode));
Z=O=L;
do{if(Z==p||Z.nodeType==9||V.dom.isBlock(Z)||/(TD|TABLE|TH|CAPTION)/.test(Z.nodeName)){break
}O=Z
}while((Z=Z.nextSibling?Z.nextSibling:Z.parentNode));
if(K.nodeName==I){a.setStart(K,0)
}else{a.setStartBefore(K)
}a.setEnd(i,h);
T.appendChild(a.cloneContents()||l.createTextNode(""));
try{c.setEndAfter(O)
}catch(j){}c.setStart(L,J);
o.appendChild(c.cloneContents()||l.createTextNode(""));
X=l.createRange();
if(!K.previousSibling&&K.parentNode.nodeName==I){X.setStartBefore(K.parentNode)
}else{if(a.startContainer.nodeName==I&&a.startOffset==0){X.setStartBefore(a.startContainer)
}else{X.setStart(a.startContainer,a.startOffset)
}}if(!O.nextSibling&&O.parentNode.nodeName==I){X.setEndAfter(O.parentNode)
}else{X.setEnd(c.endContainer,c.endOffset)
}X.deleteContents();
if(C){N.getWin().scrollTo(0,f.y)
}if(T.firstChild&&T.firstChild.nodeName==I){T.innerHTML=T.firstChild.innerHTML
}if(o.firstChild&&o.firstChild.nodeName==I){o.innerHTML=o.firstChild.innerHTML
}if(G(T)){T.innerHTML="<br />"
}function m(s,d){var b=[],u,t,r;
s.innerHTML="";
if(q.keep_styles){t=d;
do{if(/^(SPAN|STRONG|B|EM|I|FONT|STRIKE|U)$/.test(t.nodeName)){u=t.cloneNode(false);
g.setAttrib(u,"id","");
b.push(u)
}}while(t=t.parentNode)
}if(b.length>0){for(r=b.length-1,u=s;
r>=0;
r--){u=u.appendChild(b[r])
}b[0].innerHTML=C?"&nbsp;":"<br />";
return b[0]
}else{s.innerHTML=C?"&nbsp;":"<br />"
}}if(G(o)){R=m(o,L)
}if(C&&parseFloat(opera.version())<9.5){X.insertNode(T);
X.insertNode(o)
}else{X.insertNode(o);
X.insertNode(T)
}o.normalize();
T.normalize();
function Q(b){return l.createTreeWalker(b,NodeFilter.SHOW_TEXT,null,false).nextNode()||b
}X=l.createRange();
X.selectNodeContents(A?Q(R||o):R||o);
X.collapse(1);
W.removeAllRanges();
W.addRange(X);
S=N.dom.getPos(o).y;
U=o.clientHeight;
if(S<f.y||S+U>f.y+f.h){N.getWin().scrollTo(0,S<f.y?S:S-f.h+25)
}return false
},backspaceDelete:function(L,R){var T=this,K=T.editor,O=K.getBody(),J,M=K.selection,I=M.getRng(),N=I.startContainer,J,P,Q;
if(N&&K.dom.isBlock(N)&&!/^(TD|TH)$/.test(N.nodeName)&&R){if(N.childNodes.length==0||(N.childNodes.length==1&&N.firstChild.nodeName=="BR")){J=N;
while((J=J.previousSibling)&&!K.dom.isBlock(J)){}if(J){if(N!=O.firstChild){P=K.dom.doc.createTreeWalker(J,NodeFilter.SHOW_TEXT,null,false);
while(Q=P.nextNode()){J=Q
}I=K.getDoc().createRange();
I.setStart(J,J.nodeValue?J.nodeValue.length:0);
I.setEnd(J,J.nodeValue?J.nodeValue.length:0);
M.setRng(I);
K.dom.remove(N)
}return B.cancel(L)
}}}function S(U){var V;
U=U.target;
if(U&&U.parentNode&&U.nodeName=="BR"&&(J=T.getParentBlock(U))){V=U.previousSibling;
B.remove(O,"DOMNodeInserted",S);
if(V&&V.nodeType==3&&/\s+$/.test(V.nodeValue)){return 
}if(U.previousSibling||U.nextSibling){K.dom.remove(U)
}}}B._add(O,"DOMNodeInserted",S);
window.setTimeout(function(){B._remove(O,"DOMNodeInserted",S)
},1)
}})
})(tinymce);
(function(C){var B=C.DOM,A=C.dom.Event,D=C.each,E=C.extend;
C.create("tinymce.ControlManager",{ControlManager:function(F,I){var H=this,G;
I=I||{};
H.editor=F;
H.controls={};
H.onAdd=new C.util.Dispatcher(H);
H.onPostRender=new C.util.Dispatcher(H);
H.prefix=I.prefix||F.id+"_";
H._cls={};
H.onPostRender.add(function(){D(H.controls,function(J){J.postRender()
})
})
},get:function(F){return this.controls[this.prefix+F]||this.controls[F]
},setActive:function(H,F){var G=null;
if(G=this.get(H)){G.setActive(F)
}return G
},setDisabled:function(H,F){var G=null;
if(G=this.get(H)){G.setDisabled(F)
}return G
},add:function(G){var F=this;
if(G){F.controls[G.id]=G;
F.onAdd.dispatch(G,F)
}return G
},createControl:function(I){var H,G=this,F=G.editor;
D(F.plugins,function(J){if(J.createControl){H=J.createControl(I,G);
if(H){return false
}}});
switch(I){case"|":case"separator":return G.createSeparator()
}if(!H&&F.buttons&&(H=F.buttons[I])){return G.createButton(I,H)
}return G.add(H)
},createDropMenu:function(F,N,H){var M=this,I=M.editor,J,G,K,L;
N=E({"class":"mceDropDown",constrain:I.settings.constrain_menus},N);
N["class"]=N["class"]+" "+I.getParam("skin")+"Skin";
if(K=I.getParam("skin_variant")){N["class"]+=" "+I.getParam("skin")+"Skin"+K.substring(0,1).toUpperCase()+K.substring(1)
}F=M.prefix+F;
L=H||M._cls.dropmenu||C.ui.DropMenu;
J=M.controls[F]=new L(F,N);
J.onAddItem.add(function(Q,P){var O=P.settings;
O.title=I.getLang(O.title,O.title);
if(!O.onclick){O.onclick=function(R){I.execCommand(O.cmd,O.ui||false,O.value)
}
}});
I.onRemove.add(function(){J.destroy()
});
if(C.isIE){J.onShowMenu.add(function(){I.focus();
G=I.selection.getBookmark(1)
});
J.onHideMenu.add(function(){if(G){I.selection.moveToBookmark(G);
G=0
}})
}return M.add(J)
},createListBox:function(M,I,L){var H=this,G=H.editor,J,K,F;
if(H.get(M)){return null
}I.title=G.translate(I.title);
I.scope=I.scope||G;
if(!I.onselect){I.onselect=function(N){G.execCommand(I.cmd,I.ui||false,N||I.value)
}
}I=E({title:I.title,"class":"mce_"+M,scope:I.scope,control_manager:H},I);
M=H.prefix+M;
if(G.settings.use_native_selects){K=new C.ui.NativeListBox(M,I)
}else{F=L||H._cls.listbox||C.ui.ListBox;
K=new F(M,I)
}H.controls[M]=K;
if(C.isWebKit){K.onPostRender.add(function(O,N){A.add(N,"mousedown",function(){G.bookmark=G.selection.getBookmark("simple")
});
A.add(N,"focus",function(){G.selection.moveToBookmark(G.bookmark);
G.bookmark=null
})
})
}if(K.hideMenu){G.onMouseDown.add(K.hideMenu,K)
}return H.add(K)
},createButton:function(M,I,L){var H=this,G=H.editor,J,K,F;
if(H.get(M)){return null
}I.title=G.translate(I.title);
I.label=G.translate(I.label);
I.scope=I.scope||G;
if(!I.onclick&&!I.menu_button){I.onclick=function(){G.execCommand(I.cmd,I.ui||false,I.value)
}
}I=E({title:I.title,"class":"mce_"+M,unavailable_prefix:G.getLang("unavailable",""),scope:I.scope,control_manager:H},I);
M=H.prefix+M;
if(I.menu_button){F=L||H._cls.menubutton||C.ui.MenuButton;
K=new F(M,I);
G.onMouseDown.add(K.hideMenu,K)
}else{F=H._cls.button||C.ui.Button;
K=new F(M,I)
}return H.add(K)
},createMenuButton:function(H,F,G){F=F||{};
F.menu_button=1;
return this.createButton(H,F,G)
},createSplitButton:function(M,I,L){var H=this,G=H.editor,J,K,F;
if(H.get(M)){return null
}I.title=G.translate(I.title);
I.scope=I.scope||G;
if(!I.onclick){I.onclick=function(N){G.execCommand(I.cmd,I.ui||false,N||I.value)
}
}if(!I.onselect){I.onselect=function(N){G.execCommand(I.cmd,I.ui||false,N||I.value)
}
}I=E({title:I.title,"class":"mce_"+M,scope:I.scope,control_manager:H},I);
M=H.prefix+M;
F=L||H._cls.splitbutton||C.ui.SplitButton;
K=H.add(new F(M,I));
G.onMouseDown.add(K.hideMenu,K);
return K
},createColorSplitButton:function(F,N,H){var L=this,J=L.editor,I,K,M,G;
if(L.get(F)){return null
}N.title=J.translate(N.title);
N.scope=N.scope||J;
if(!N.onclick){N.onclick=function(O){if(C.isIE){G=J.selection.getBookmark(1)
}J.execCommand(N.cmd,N.ui||false,O||N.value)
}
}if(!N.onselect){N.onselect=function(O){J.execCommand(N.cmd,N.ui||false,O||N.value)
}
}N=E({title:N.title,"class":"mce_"+F,menu_class:J.getParam("skin")+"Skin",scope:N.scope,more_colors_title:J.getLang("more_colors")},N);
F=L.prefix+F;
M=H||L._cls.colorsplitbutton||C.ui.ColorSplitButton;
K=new M(F,N);
J.onMouseDown.add(K.hideMenu,K);
J.onRemove.add(function(){K.destroy()
});
if(C.isIE){K.onHideMenu.add(function(){if(G){J.selection.moveToBookmark(G);
G=0
}})
}return L.add(K)
},createToolbar:function(K,H,J){var I,G=this,F;
K=G.prefix+K;
F=J||G._cls.toolbar||C.ui.Toolbar;
I=new F(K,H);
if(G.get(K)){return null
}return G.add(I)
},createSeparator:function(G){var F=G||this._cls.separator||C.ui.Separator;
return new F()
},setControlType:function(G,F){return this._cls[G.toLowerCase()]=F
},destroy:function(){D(this.controls,function(F){F.destroy()
});
this.controls=null
}})
})(tinymce);
(function(D){var A=D.util.Dispatcher,E=D.each,C=D.isIE,B=D.isOpera;
D.create("tinymce.WindowManager",{WindowManager:function(F){var G=this;
G.editor=F;
G.onOpen=new A(G);
G.onClose=new A(G);
G.params={};
G.features={}
},open:function(R,G){var Q=this,J="",M,L,H=Q.editor.settings.dialog_type=="modal",O,N,I,F=D.DOM.getViewPort(),P;
R=R||{};
G=G||{};
N=B?F.w:screen.width;
I=B?F.h:screen.height;
R.name=R.name||"mc_"+new Date().getTime();
R.width=parseInt(R.width||320);
R.height=parseInt(R.height||240);
R.resizable=true;
R.left=R.left||parseInt(N/2)-(R.width/2);
R.top=R.top||parseInt(I/2)-(R.height/2);
G.inline=false;
G.mce_width=R.width;
G.mce_height=R.height;
G.mce_auto_focus=R.auto_focus;
if(H){if(C){R.center=true;
R.help=false;
R.dialogWidth=R.width+"px";
R.dialogHeight=R.height+"px";
R.scroll=R.scrollbars||false
}}E(R,function(T,S){if(D.is(T,"boolean")){T=T?"yes":"no"
}if(!/^(name|url)$/.test(S)){if(C&&H){J+=(J?";":"")+S+":"+T
}else{J+=(J?",":"")+S+"="+T
}}});
Q.features=R;
Q.params=G;
Q.onOpen.dispatch(Q,R,G);
P=R.url||R.file;
P=D._addVer(P);
try{if(C&&H){O=1;
window.showModalDialog(P,window,J)
}else{O=window.open(P,R.name,J)
}}catch(K){}if(!O){alert(Q.editor.getLang("popup_blocked"))
}},close:function(F){F.close();
this.onClose.dispatch(this)
},createInstance:function(H,G,F,L,K,J){var I=D.resolve(H);
return new I(G,F,L,K,J)
},confirm:function(H,F,I,G){G=G||window;
F.call(I||this,G.confirm(this._decode(this.editor.getLang(H,H))))
},alert:function(H,F,J,G){var I=this;
G=G||window;
G.alert(I._decode(I.editor.getLang(H,H)));
if(F){F.call(J||I)
}},_decode:function(F){return D.DOM.decode(F).replace(/\\n/g,"\n")
}})
}(tinymce));
(function(A){A.CommandManager=function(){var C={},B={},D={};
function E(I,H,G,F){if(typeof (H)=="string"){H=[H]
}A.each(H,function(J){I[J.toLowerCase()]={func:G,scope:F}
})
}A.extend(this,{add:function(H,G,F){E(C,H,G,F)
},addQueryStateHandler:function(H,G,F){E(B,H,G,F)
},addQueryValueHandler:function(H,G,F){E(D,H,G,F)
},execCommand:function(G,J,I,H,F){if(J=C[J.toLowerCase()]){if(J.func.call(G||J.scope,I,H,F)!==false){return true
}}},queryCommandValue:function(){if(cmd=D[cmd.toLowerCase()]){return cmd.func.call(scope||cmd.scope,ui,value,args)
}},queryCommandState:function(){if(cmd=B[cmd.toLowerCase()]){return cmd.func.call(scope||cmd.scope,ui,value,args)
}}})
};
A.GlobalCommands=new A.CommandManager()
})(tinymce);
(function(B){function A(I,D,H,M){var J,G,E,L,F;
function K(O,N){do{if(O.parentNode==N){return O
}O=O.parentNode
}while(O)
}function C(N){M(N);
B.walk(N,M,"childNodes")
}J=I.findCommonAncestor(D,H);
E=K(D,J)||D;
L=K(H,J)||H;
for(G=D;
G&&G!=E;
G=G.parentNode){for(F=G.nextSibling;
F;
F=F.nextSibling){C(F)
}}if(E!=L){for(G=E.nextSibling;
G&&G!=L;
G=G.nextSibling){C(G)
}}else{C(E)
}for(G=H;
G&&G!=L;
G=G.parentNode){for(F=G.previousSibling;
F;
F=F.previousSibling){C(F)
}}}B.GlobalCommands.add("RemoveFormat",function(){var M=this,L=M.dom,R=M.selection,D=R.getRng(1),E=[],H,F,J,P,G,N,C,I;
function K(T){var S;
L.getParent(T,function(U){if(L.is(U,M.getParam("removeformat_selector"))){S=U
}return L.isBlock(U)
},M.getBody());
return S
}function O(S){if(L.is(S,M.getParam("removeformat_selector"))){E.push(S)
}}function Q(S){O(S);
B.walk(S,O,"childNodes")
}H=R.getBookmark();
P=D.startContainer;
N=D.endContainer;
G=D.startOffset;
C=D.endOffset;
P=P.nodeType==1?P.childNodes[Math.min(G,P.childNodes.length-1)]:P;
N=N.nodeType==1?N.childNodes[Math.min(G==C?C:C-1,N.childNodes.length-1)]:N;
if(P==N){F=K(P);
if(P.nodeType==3){if(F&&F.nodeType==1){I=P.splitText(G);
I.splitText(C-G);
L.split(F,I);
R.moveToBookmark(H)
}return 
}Q(L.split(F,P)||P)
}else{F=K(P);
J=K(N);
if(F){if(P.nodeType==3){if(G==P.nodeValue.length){P.nodeValue+="\uFEFF"
}P=P.splitText(G)
}}if(J){if(N.nodeType==3){N.splitText(C)
}}if(F&&F==J){L.replace(L.create("span",{id:"__end"},N.cloneNode(true)),N)
}if(F){F=L.split(F,P)
}else{F=P
}if(I=L.get("__end")){N=I;
J=K(N)
}if(J){J=L.split(J,N)
}else{J=N
}A(L,F,J,O);
if(P.nodeValue=="\uFEFF"){P.nodeValue=""
}Q(N);
Q(P)
}B.each(E,function(S){L.remove(S,1)
});
L.remove("__end",1);
R.moveToBookmark(H)
})
})(tinymce);
(function(A){A.GlobalCommands.add("mceBlockQuote",function(){var I=this,M=I.selection,F=I.dom,K,J,E,D,N,C,L,H,B;
function G(O){return F.getParent(O,function(P){return P.nodeName==="BLOCKQUOTE"
})
}K=F.getParent(M.getStart(),F.isBlock);
J=F.getParent(M.getEnd(),F.isBlock);
if(N=G(K)){if(K!=J||K.childNodes.length>1||(K.childNodes.length==1&&K.firstChild.nodeName!="BR")){D=M.getBookmark()
}if(G(J)){L=N.cloneNode(false);
while(E=J.nextSibling){L.appendChild(E.parentNode.removeChild(E))
}}if(L){F.insertAfter(L,N)
}B=M.getSelectedBlocks(K,J);
for(H=B.length-1;
H>=0;
H--){F.insertAfter(B[H],N)
}if(/^\s*$/.test(N.innerHTML)){F.remove(N,1)
}if(L&&/^\s*$/.test(L.innerHTML)){F.remove(L,1)
}if(!D){if(!A.isIE){C=I.getDoc().createRange();
C.setStart(K,0);
C.setEnd(K,0);
M.setRng(C)
}else{M.select(K);
M.collapse(0);
if(F.getParent(M.getStart(),F.isBlock)!=K){C=M.getRng();
C.move("character",-1);
C.select()
}}}else{I.selection.moveToBookmark(D)
}return 
}if(A.isIE&&!K&&!J){I.getDoc().execCommand("Indent");
E=G(M.getNode());
E.style.margin=E.dir="";
return 
}if(!K||!J){return 
}if(K!=J||K.childNodes.length>1||(K.childNodes.length==1&&K.firstChild.nodeName!="BR")){D=M.getBookmark()
}A.each(M.getSelectedBlocks(G(M.getStart()),G(M.getEnd())),function(O){if(O.nodeName=="BLOCKQUOTE"&&!N){N=O;
return 
}if(!N){N=F.create("blockquote");
O.parentNode.insertBefore(N,O)
}if(O.nodeName=="BLOCKQUOTE"&&N){E=O.firstChild;
while(E){N.appendChild(E.cloneNode(true));
E=E.nextSibling
}F.remove(O);
return 
}N.appendChild(F.remove(O))
});
if(!D){if(!A.isIE){C=I.getDoc().createRange();
C.setStart(K,0);
C.setEnd(K,0);
M.setRng(C)
}else{M.select(K);
M.collapse(1)
}}else{M.moveToBookmark(D)
}})
})(tinymce);
(function(A){A.each(["Cut","Copy","Paste"],function(B){A.GlobalCommands.add(B,function(){var C=this,E=C.getDoc();
try{E.execCommand(B,false,null);
if(!E.queryCommandSupported(B)){throw"Error"
}}catch(D){C.windowManager.alert(C.getLang("clipboard_no_support"))
}})
})
})(tinymce);
(function(A){A.GlobalCommands.add("InsertHorizontalRule",function(){if(A.isOpera){return this.getDoc().execCommand("InsertHorizontalRule",false,"")
}this.selection.setContent("<hr />")
})
})(tinymce);
(function(){var A=tinymce.GlobalCommands;
A.add(["mceEndUndoLevel","mceAddUndoLevel"],function(){this.undoManager.add()
});
A.add("Undo",function(){var B=this;
if(B.settings.custom_undo_redo){B.undoManager.undo();
B.nodeChanged();
return true
}return false
});
A.add("Redo",function(){var B=this;
if(B.settings.custom_undo_redo){B.undoManager.redo();
B.nodeChanged();
return true
}return false
})
})();