var tinymce={majorVersion:"3",minorVersion:"2.4.1",releaseDate:"2009-05-25",_init:function(){var A=this,D=document,C=window,E=navigator,K=E.userAgent,F,L,G,H,I,B;
A.isOpera=C.opera&&opera.buildNumber;
A.isWebKit=/WebKit/.test(K);
A.isIE=!A.isWebKit&&!A.isOpera&&(/MSIE/gi).test(K)&&(/Explorer/gi).test(E.appName);
A.isIE6=A.isIE&&/MSIE [56]/.test(K);
A.isGecko=!A.isWebKit&&/Gecko/.test(K);
A.isMac=K.indexOf("Mac")!=-1;
A.isAir=/adobeair/i.test(K);
if(C.tinyMCEPreInit){A.suffix=tinyMCEPreInit.suffix;
A.baseURL=tinyMCEPreInit.base;
A.query=tinyMCEPreInit.query;
return 
}A.suffix="";
L=D.getElementsByTagName("base");
for(F=0;
F<L.length;
F++){if(B=L[F].href){if(/^https?:\/\/[^\/]+$/.test(B)){B+="/"
}H=B?B.match(/.*\//)[0]:""
}}function J(M){if(M.src&&/tiny_mce(|_dev|_src|_gzip|_jquery|_prototype).js/.test(M.src)){if(/_(src|dev)\.js/g.test(M.src)){A.suffix="_src"
}if((I=M.src.indexOf("?"))!=-1){A.query=M.src.substring(I+1)
}A.baseURL=M.src.substring(0,M.src.lastIndexOf("/"));
if(H&&A.baseURL.indexOf("://")==-1){A.baseURL=H+A.baseURL
}return A.baseURL
}return null
}L=D.getElementsByTagName("script");
for(F=0;
F<L.length;
F++){if(J(L[F])){return 
}}G=D.getElementsByTagName("head")[0];
if(G){L=G.getElementsByTagName("script");
for(F=0;
F<L.length;
F++){if(J(L[F])){return 
}}}return 
},is:function(A,B){var C=typeof (A);
if(!B){return C!="undefined"
}if(B=="array"&&(A.hasOwnProperty&&A instanceof Array)){return true
}return C==B
},each:function(D,B,E){var C,A;
if(!D){return 0
}E=E||D;
if(typeof (D.length)!="undefined"){for(C=0,A=D.length;
C<A;
C++){if(B.call(E,D[C],C,D)===false){return 0
}}}else{for(C in D){if(D.hasOwnProperty(C)){if(B.call(E,D[C],C,D)===false){return 0
}}}}return 1
},map:function(A,C){var B=[];
tinymce.each(A,function(D){B.push(C(D))
});
return B
},grep:function(A,C){var B=[];
tinymce.each(A,function(D){if(!C||C(D)){B.push(D)
}});
return B
},inArray:function(D,C){var B,A;
if(D){for(B=0,A=D.length;
B<A;
B++){if(D[B]===C){return B
}}}return -1
},extend:function(B,C){var D,A=arguments;
for(D=1;
D<A.length;
D++){C=A[D];
tinymce.each(C,function(E,F){if(typeof (E)!=="undefined"){B[F]=E
}})
}return B
},trim:function(A){return(A?""+A:"").replace(/^\s*|\s*$/g,"")
},create:function(A,I){var B=this,H,F,E,D,G,C=0;
A=/^((static) )?([\w.]+)(:([\w.]+))?/.exec(A);
E=A[3].match(/(^|\.)(\w+)$/i)[2];
F=B.createNS(A[3].replace(/\.\w+$/,""));
if(F[E]){return 
}if(A[2]=="static"){F[E]=I;
if(this.onCreate){this.onCreate(A[2],A[3],F[E])
}return 
}if(!I[E]){I[E]=function(){};
C=1
}F[E]=I[E];
B.extend(F[E].prototype,I);
if(A[5]){H=B.resolve(A[5]).prototype;
D=A[5].match(/\.(\w+)$/i)[1];
G=F[E];
if(C){F[E]=function(){return H[D].apply(this,arguments)
}
}else{F[E]=function(){this.parent=H[D];
return G.apply(this,arguments)
}
}F[E].prototype[E]=F[E];
B.each(H,function(K,J){F[E].prototype[J]=H[J]
});
B.each(I,function(K,J){if(H[J]){F[E].prototype[J]=function(){this.parent=H[J];
return K.apply(this,arguments)
}
}else{if(J!=E){F[E].prototype[J]=K
}}})
}B.each(I["static"],function(K,J){F[E][J]=K
});
if(this.onCreate){this.onCreate(A[2],A[3],F[E].prototype)
}},walk:function(D,A,C,B){B=B||this;
if(D){if(C){D=D[C]
}tinymce.each(D,function(E,F){if(A.call(B,E,F,C)===false){return false
}tinymce.walk(E,A,C,B)
})
}},createNS:function(C,D){var A,B;
D=D||window;
C=C.split(".");
for(A=0;
A<C.length;
A++){B=C[A];
if(!D[B]){D[B]={}
}D=D[B]
}return D
},resolve:function(C,D){var A,B;
D=D||window;
C=C.split(".");
for(A=0,B=C.length;
A<B;
A++){D=D[C[A]];
if(!D){break
}}return D
},addUnload:function(D,E){var F=this,B=window;
D={func:D,scope:E||this};
if(!F.unloads){function A(){var I=F.unloads,H,G;
if(I){for(G in I){H=I[G];
if(H&&H.func){H.func.call(H.scope,1)
}}if(B.detachEvent){B.detachEvent("onbeforeunload",C);
B.detachEvent("onunload",A)
}else{if(B.removeEventListener){B.removeEventListener("unload",A,false)
}}F.unloads=H=I=B=A=0;
if(window.CollectGarbage){window.CollectGarbage()
}}}function C(){var G=document;
if(G.readyState=="interactive"){function H(){G.detachEvent("onstop",H);
if(A){A()
}G=0
}if(G){G.attachEvent("onstop",H)
}window.setTimeout(function(){if(G){G.detachEvent("onstop",H)
}},0)
}}if(B.attachEvent){B.attachEvent("onunload",A);
B.attachEvent("onbeforeunload",C)
}else{if(B.addEventListener){B.addEventListener("unload",A,false)
}}F.unloads=[D]
}else{F.unloads.push(D)
}return D
},removeUnload:function(C){var B=this.unloads,A=null;
tinymce.each(B,function(D,E){if(D&&D.func==C){B.splice(E,1);
A=C;
return false
}});
return A
},explode:function(B,A){return B?tinymce.map(B.split(A||","),tinymce.trim):B
},_addVer:function(A){var B;
if(!this.query){return A
}B=(A.indexOf("?")==-1?"?":"&")+this.query;
if(A.indexOf("#")==-1){return A+B
}return A.replace("#",B+"#")
}};
window.tinymce=tinymce;
tinymce._init();
tinymce.create("tinymce.util.Dispatcher",{scope:null,listeners:null,Dispatcher:function(A){this.scope=A||this;
this.listeners=[]
},add:function(B,A){this.listeners.push({cb:B,scope:A||this.scope});
return B
},addToTop:function(B,A){this.listeners.unshift({cb:B,scope:A||this.scope});
return B
},remove:function(B){var A=this.listeners,C=null;
tinymce.each(A,function(D,E){if(B==D.cb){C=B;
A.splice(E,1);
return false
}});
return C
},dispatch:function(){var C,E=arguments,D,A=this.listeners,B;
for(D=0;
D<A.length;
D++){B=A[D];
C=B.cb.apply(B.scope,E);
if(C===false){break
}}return C
}});
(function(){var A=tinymce.each;
tinymce.create("tinymce.util.URI",{URI:function(E,C){var D=this,B,F,G;
C=D.settings=C||{};
if(/^(mailto|tel|news|javascript|about):/i.test(E)||/^\s*#/.test(E)){D.source=E;
return 
}if(E.indexOf("/")===0&&E.indexOf("//")!==0){E=(C.base_uri?C.base_uri.protocol||"http":"http")+"://mce_host"+E
}if(E.indexOf(":/")===-1&&E.indexOf("//")!==0){E=(C.base_uri.protocol||"http")+"://mce_host"+D.toAbsPath(C.base_uri.path,E)
}E=E.replace(/@@/g,"(mce_at)");
E=/^(?:(?![^:@]+:[^:@\/]*@)([^:\/?#.]+):)?(?:\/\/)?((?:(([^:@]*):?([^:@]*))?@)?([^:\/?#]*)(?::(\d*))?)(((\/(?:[^?#](?![^?#\/]*\.[^?#\/.]+(?:[?#]|$)))*\/?)?([^?#\/]*))(?:\?([^#]*))?(?:#(.*))?)/.exec(E);
A(["source","protocol","authority","userInfo","user","password","host","port","relative","path","directory","file","query","anchor"],function(H,J){var I=E[J];
if(I){I=I.replace(/\(mce_at\)/g,"@@")
}D[H]=I
});
if(G=C.base_uri){if(!D.protocol){D.protocol=G.protocol
}if(!D.userInfo){D.userInfo=G.userInfo
}if(!D.port&&D.host=="mce_host"){D.port=G.port
}if(!D.host||D.host=="mce_host"){D.host=G.host
}D.source=""
}},setPath:function(C){var B=this;
C=/^(.*?)\/?(\w+)?$/.exec(C);
B.path=C[0];
B.directory=C[1];
B.file=C[2];
B.source="";
B.getURI()
},toRelative:function(B){var D=this,C;
if(B==="./"){return B
}B=new tinymce.util.URI(B,{base_uri:D});
if((B.host!="mce_host"&&D.host!=B.host&&B.host)||D.port!=B.port||D.protocol!=B.protocol){return B.getURI()
}C=D.toRelPath(D.path,B.path);
if(B.query){C+="?"+B.query
}if(B.anchor){C+="#"+B.anchor
}return C
},toAbsolute:function(B,C){var B=new tinymce.util.URI(B,{base_uri:this});
return B.getURI(this.host==B.host?C:0)
},toRelPath:function(D,C){var H,E=0,G="",F,B;
D=D.substring(0,D.lastIndexOf("/"));
D=D.split("/");
H=C.split("/");
if(D.length>=H.length){for(F=0,B=D.length;
F<B;
F++){if(F>=H.length||D[F]!=H[F]){E=F+1;
break
}}}if(D.length<H.length){for(F=0,B=H.length;
F<B;
F++){if(F>=D.length||D[F]!=H[F]){E=F+1;
break
}}}if(E==1){return C
}for(F=0,B=D.length-(E-1);
F<B;
F++){G+="../"
}for(F=E-1,B=H.length;
F<B;
F++){if(F!=E-1){G+="/"+H[F]
}else{G+=H[F]
}}return G
},toAbsPath:function(E,D){var G,B=0,C=[],F;
F=/\/$/.test(D)?"/":"";
E=E.split("/");
D=D.split("/");
A(E,function(H){if(H){C.push(H)
}});
E=C;
for(G=D.length-1,C=[];
G>=0;
G--){if(D[G].length==0||D[G]=="."){continue
}if(D[G]==".."){B++;
continue
}if(B>0){B--;
continue
}C.push(D[G])
}G=E.length-B;
if(G<=0){return"/"+C.reverse().join("/")+F
}return"/"+E.slice(0,G).join("/")+"/"+C.reverse().join("/")+F
},getURI:function(C){var D,B=this;
if(!B.source||C){D="";
if(!C){if(B.protocol){D+=B.protocol+"://"
}if(B.userInfo){D+=B.userInfo+"@"
}if(B.host){D+=B.host
}if(B.port){D+=":"+B.port
}}if(B.path){D+=B.path
}if(B.query){D+="?"+B.query
}if(B.anchor){D+="#"+B.anchor
}B.source=D
}return B.source
}})
})();
(function(){var A=tinymce.each;
tinymce.create("static tinymce.util.Cookie",{getHash:function(C){var B=this.get(C),D;
if(B){A(B.split("&"),function(E){E=E.split("=");
D=D||{};
D[unescape(E[0])]=unescape(E[1])
})
}return D
},setHash:function(C,B,F,G,D,H){var E="";
A(B,function(I,J){E+=(!E?"":"&")+escape(J)+"="+escape(I)
});
this.set(C,E,F,G,D,H)
},get:function(B){var C=document.cookie,D,E=B+"=",F;
if(!C){return 
}F=C.indexOf("; "+E);
if(F==-1){F=C.indexOf(E);
if(F!=0){return null
}}else{F+=2
}D=C.indexOf(";",F);
if(D==-1){D=C.length
}return unescape(C.substring(F+E.length,D))
},set:function(C,B,E,F,D,G){document.cookie=C+"="+escape(B)+((E)?"; expires="+E.toGMTString():"")+((F)?"; path="+escape(F):"")+((D)?"; domain="+D:"")+((G)?"; secure":"")
},remove:function(C,B){var D=new Date();
D.setTime(D.getTime()-1000);
this.set(C,"",D,B,D)
}})
})();
tinymce.create("static tinymce.util.JSON",{serialize:function(C){var E,B,D=tinymce.util.JSON.serialize,A;
if(C==null){return"null"
}A=typeof C;
if(A=="string"){B="\bb\tt\nn\ff\rr\"\"''\\\\";
return'"'+C.replace(/([\u0080-\uFFFF\x00-\x1f\"])/g,function(F,G){E=B.indexOf(G);
if(E+1){return"\\"+B.charAt(E+1)
}F=G.charCodeAt().toString(16);
return"\\u"+"0000".substring(F.length)+F
})+'"'
}if(A=="object"){if(C.hasOwnProperty&&C instanceof Array){for(E=0,B="[";
E<C.length;
E++){B+=(E>0?",":"")+D(C[E])
}return B+"]"
}B="{";
for(E in C){B+=typeof C[E]!="function"?(B.length>1?',"':'"')+E+'":'+D(C[E]):""
}return B+"}"
}return""+C
},parse:function(s){try{return eval("("+s+")")
}catch(ex){}}});
tinymce.create("static tinymce.util.XHR",{send:function(D){var B,F,A=window,C=0;
D.scope=D.scope||this;
D.success_scope=D.success_scope||D.scope;
D.error_scope=D.error_scope||D.scope;
D.async=D.async===false?false:true;
D.data=D.data||"";
function G(H){B=0;
try{B=new ActiveXObject(H)
}catch(I){}return B
}B=A.XMLHttpRequest?new XMLHttpRequest():G("Microsoft.XMLHTTP")||G("Msxml2.XMLHTTP");
if(B){if(B.overrideMimeType){B.overrideMimeType(D.content_type)
}B.open(D.type||(D.data?"POST":"GET"),D.url,D.async);
if(D.content_type){B.setRequestHeader("Content-Type",D.content_type)
}B.send(D.data);
function E(){if(!D.async||B.readyState==4||C++>10000){if(D.success&&C<10000&&B.status==200){D.success.call(D.success_scope,""+B.responseText,B,D)
}else{if(D.error){D.error.call(D.error_scope,C>10000?"TIMED_OUT":"GENERAL",B,D)
}}B=null
}else{A.setTimeout(E,10)
}}if(!D.async){return E()
}F=A.setTimeout(E,10)
}}});
(function(){var C=tinymce.extend,A=tinymce.util.JSON,B=tinymce.util.XHR;
tinymce.create("tinymce.util.JSONRequest",{JSONRequest:function(D){this.settings=C({},D);
this.count=0
},send:function(D){var E=D.error,F=D.success;
D=C(this.settings,D);
D.success=function(G,H){G=A.parse(G);
if(typeof (G)=="undefined"){G={error:"JSON Parse error."}
}if(G.error){E.call(D.error_scope||D.scope,G.error,H)
}else{F.call(D.success_scope||D.scope,G.result)
}};
D.error=function(G,H){E.call(D.error_scope||D.scope,G,H)
};
D.data=A.serialize({id:D.id||"c"+(this.count++),method:D.method,params:D.params});
D.content_type="application/json";
B.send(D)
},"static":{sendRPC:function(D){return new tinymce.util.JSONRequest().send(D)
}}})
}());
(function(E){var C=E.each,A=E.is;
var D=E.isWebKit,B=E.isIE;
E.create("tinymce.dom.DOMUtils",{doc:null,root:null,files:null,pixelStyles:/^(top|left|bottom|right|width|height|borderWidth)$/,props:{"for":"htmlFor","class":"className",className:"className",checked:"checked",disabled:"disabled",maxlength:"maxLength",readonly:"readOnly",selected:"selected",value:"value",id:"id",name:"name",type:"type"},DOMUtils:function(F,H){var I=this;
I.doc=F;
I.win=window;
I.files={};
I.cssFlicker=false;
I.counter=0;
I.boxModel=!E.isIE||F.compatMode=="CSS1Compat";
I.stdMode=F.documentMode===8;
I.settings=H=E.extend({keep_values:false,hex_colors:1,process_html:1},H);
if(E.isIE6){try{F.execCommand("BackgroundImageCache",false,true)
}catch(G){I.cssFlicker=true
}}E.addUnload(I.destroy,I)
},getRoot:function(){var G=this,F=G.settings;
return(F&&G.get(F.root_element))||G.doc.body
},getViewPort:function(G){var F,H;
G=!G?this.win:G;
F=G.document;
H=this.boxModel?F.documentElement:F.body;
return{x:G.pageXOffset||H.scrollLeft,y:G.pageYOffset||H.scrollTop,w:G.innerWidth||H.clientWidth,h:G.innerHeight||H.clientHeight}
},getRect:function(F){var G,I=this,H;
F=I.get(F);
G=I.getPos(F);
H=I.getSize(F);
return{x:G.x,y:G.y,w:H.w,h:H.h}
},getSize:function(F){var H=this,I,G;
F=H.get(F);
I=H.getStyle(F,"width");
G=H.getStyle(F,"height");
if(I.indexOf("px")===-1){I=0
}if(G.indexOf("px")===-1){G=0
}return{w:parseInt(I)||F.offsetWidth||F.clientWidth,h:parseInt(G)||F.offsetHeight||F.clientHeight}
},is:function(F,G){return E.dom.Sizzle.matches(G,F.nodeType?[F]:F).length>0
},getParent:function(F,G,H){return this.getParents(F,G,H,false)
},getParents:function(M,H,J,F){var K=this,L,I=K.settings,G=[];
M=K.get(M);
F=F===undefined;
if(I.strict_root){J=J||K.getRoot()
}if(A(H,"string")){L=H;
if(H==="*"){H=function(N){return N.nodeType==1
}
}else{H=function(N){return K.is(N,L)
}
}}while(M){if(M==J||!M.nodeType||M.nodeType===9){break
}if(!H||H(M)){if(F){G.push(M)
}else{return M
}}M=M.parentNode
}return F?G:null
},get:function(G){var F;
if(G&&this.doc&&typeof (G)=="string"){F=G;
G=this.doc.getElementById(G);
if(G&&G.id!==F){return this.doc.getElementsByName(F)[1]
}}return G
},select:function(F,G){var H=this;
return E.dom.Sizzle(F,H.get(G)||H.get(H.settings.root_element)||H.doc,[])
},add:function(H,F,K,I,G){var J=this;
return this.run(H,function(N){var L,M;
L=A(F,"string")?J.doc.createElement(F):F;
J.setAttribs(L,K);
if(I){if(I.nodeType){L.appendChild(I)
}else{J.setHTML(L,I)
}}return !G?N.appendChild(L):L
})
},create:function(F,H,G){return this.add(this.doc.createElement(F),F,H,G,1)
},createHTML:function(F,K,H){var G="",I=this,J;
G+="<"+F;
for(J in K){if(K.hasOwnProperty(J)){G+=" "+J+'="'+I.encode(K[J])+'"'
}}if(E.is(H)){return G+">"+H+"</"+F+">"
}return G+" />"
},remove:function(F,H){var G=this;
return this.run(F,function(I){var J,K,L;
J=I.parentNode;
if(!J){return null
}if(H){for(L=I.childNodes.length-1;
L>=0;
L--){G.insertAfter(I.childNodes[L],I)
}}if(G.fixPsuedoLeaks){J=I.cloneNode(true);
H="IELeakGarbageBin";
K=G.get(H)||G.add(G.doc.body,"div",{id:H,style:"display:none"});
K.appendChild(I);
K.innerHTML="";
return J
}return J.removeChild(I)
})
},setStyle:function(F,I,H){var G=this;
return G.run(F,function(J){var K,L;
K=J.style;
I=I.replace(/-(\D)/g,function(N,M){return M.toUpperCase()
});
if(G.pixelStyles.test(I)&&(E.is(H,"number")||/^[\-0-9\.]+$/.test(H))){H+="px"
}switch(I){case"opacity":if(B){K.filter=H===""?"":"alpha(opacity="+(H*100)+")";
if(!F.currentStyle||!F.currentStyle.hasLayout){K.display="inline-block"
}}K[I]=K["-moz-opacity"]=K["-khtml-opacity"]=H||"";
break;
case"float":B?K.styleFloat=H:K.cssFloat=H;
break;
default:K[I]=H||""
}if(G.settings.update_styles){G.setAttrib(J,"mce_style")
}})
},getStyle:function(F,I,G){F=this.get(F);
if(!F){return false
}if(this.doc.defaultView&&G){I=I.replace(/[A-Z]/g,function(J){return"-"+J
});
try{return this.doc.defaultView.getComputedStyle(F,null).getPropertyValue(I)
}catch(H){return null
}}I=I.replace(/-(\D)/g,function(J,K){return K.toUpperCase()
});
if(I=="float"){I=B?"styleFloat":"cssFloat"
}if(F.currentStyle&&G){return F.currentStyle[I]
}return F.style[I]
},setStyles:function(G,F){var I=this,H=I.settings,J;
J=H.update_styles;
H.update_styles=0;
C(F,function(L,K){I.setStyle(G,K,L)
});
H.update_styles=J;
if(H.update_styles){I.setAttrib(G,H.cssText)
}},setAttrib:function(G,F,I){var H=this;
if(!G||!F){return 
}if(H.settings.strict){F=F.toLowerCase()
}return this.run(G,function(J){var K=H.settings;
switch(F){case"style":if(!A(I,"string")){C(I,function(M,L){H.setStyle(J,L,M)
});
return 
}if(K.keep_values){if(I&&!H._isRes(I)){J.setAttribute("mce_style",I,2)
}else{J.removeAttribute("mce_style",2)
}}J.style.cssText=I;
break;
case"class":J.className=I||"";
break;
case"src":case"href":if(K.keep_values){if(K.url_converter){I=K.url_converter.call(K.url_converter_scope||H,I,F,J)
}H.setAttrib(J,"mce_"+F,I,2)
}break;
case"shape":J.setAttribute("mce_style",I);
break
}if(A(I)&&I!==null&&I.length!==0){J.setAttribute(F,""+I,2)
}else{J.removeAttribute(F,2)
}})
},setAttribs:function(G,F){var H=this;
return this.run(G,function(I){C(F,function(K,J){H.setAttrib(I,J,K)
})
})
},getAttrib:function(G,F,H){var J,I=this;
G=I.get(G);
if(!G||G.nodeType!==1){return false
}if(!A(H)){H=""
}if(/^(src|href|style|coords|shape)$/.test(F)){J=G.getAttribute("mce_"+F);
if(J){return J
}}if(B&&I.props[F]){J=G[I.props[F]];
J=J&&J.nodeValue?J.nodeValue:J
}if(!J){J=G.getAttribute(F,2)
}if(F==="style"){J=J||G.style.cssText;
if(J){J=I.serializeStyle(I.parseStyle(J));
if(I.settings.keep_values&&!I._isRes(J)){G.setAttribute("mce_style",J)
}}}if(D&&F==="class"&&J){J=J.replace(/(apple|webkit)\-[a-z\-]+/gi,"")
}if(B){switch(F){case"rowspan":case"colspan":if(J===1){J=""
}break;
case"size":if(J==="+0"||J===20||J===0){J=""
}break;
case"width":case"height":case"vspace":case"checked":case"disabled":case"readonly":if(J===0){J=""
}break;
case"hspace":if(J===-1){J=""
}break;
case"maxlength":case"tabindex":if(J===32768||J===2147483647||J==="32768"){J=""
}break;
case"multiple":case"compact":case"noshade":case"nowrap":if(J===65535){return F
}return H;
case"shape":J=J.toLowerCase();
break;
default:if(F.indexOf("on")===0&&J){J=(""+J).replace(/^function\s+\w+\(\)\s+\{\s+(.*)\s+\}$/,"$1")
}}}return(J!==undefined&&J!==null&&J!=="")?""+J:H
},getPos:function(F,J){var L=this,M=0,G=0,I,H=L.doc,K;
F=L.get(F);
J=J||H.body;
if(F){if(B&&!L.stdMode){F=F.getBoundingClientRect();
I=L.boxModel?H.documentElement:H.body;
M=L.getStyle(L.select("html")[0],"borderWidth");
M=(M=="medium"||L.boxModel&&!L.isIE6)&&2||M;
F.top+=L.win.self!=L.win.top?2:0;
return{x:F.left+I.scrollLeft-M,y:F.top+I.scrollTop-M}
}K=F;
while(K&&K!=J&&K.nodeType){M+=K.offsetLeft||0;
G+=K.offsetTop||0;
K=K.offsetParent
}K=F.parentNode;
while(K&&K!=J&&K.nodeType){M-=K.scrollLeft||0;
G-=K.scrollTop||0;
K=K.parentNode
}}return{x:M,y:G}
},parseStyle:function(I){var H=this,G=H.settings,F={};
if(!I){return F
}function K(M,P,N){var Q,O,L,R;
Q=F[M+"-top"+P];
if(!Q){return 
}O=F[M+"-right"+P];
if(Q!=O){return 
}L=F[M+"-bottom"+P];
if(O!=L){return 
}R=F[M+"-left"+P];
if(L!=R){return 
}F[N]=R;
delete F[M+"-top"+P];
delete F[M+"-right"+P];
delete F[M+"-bottom"+P];
delete F[M+"-left"+P]
}function J(P,L,M,N){var O;
O=F[L];
if(!O){return 
}O=F[M];
if(!O){return 
}O=F[N];
if(!O){return 
}F[P]=F[L]+" "+F[M]+" "+F[N];
delete F[L];
delete F[M];
delete F[N]
}I=I.replace(/&(#?[a-z0-9]+);/g,"&$1_MCE_SEMI_");
C(I.split(";"),function(L){var M,N=[];
if(L){L=L.replace(/_MCE_SEMI_/g,";");
L=L.replace(/url\([^\)]+\)/g,function(O){N.push(O);
return"url("+N.length+")"
});
L=L.split(":");
M=E.trim(L[1]);
M=M.replace(/url\(([^\)]+)\)/g,function(O,P){return N[parseInt(P)-1]
});
M=M.replace(/rgb\([^\)]+\)/g,function(O){return H.toHex(O)
});
if(G.url_converter){M=M.replace(/url\([\'\"]?([^\)\'\"]+)[\'\"]?\)/g,function(P,O){return"url("+G.url_converter.call(G.url_converter_scope||H,H.decode(O),"style",null)+")"
})
}F[E.trim(L[0]).toLowerCase()]=M
}});
K("border","","border");
K("border","-width","border-width");
K("border","-color","border-color");
K("border","-style","border-style");
K("padding","","padding");
K("margin","","margin");
J("border","border-width","border-style","border-color");
if(B){if(F.border=="medium none"){F.border=""
}}return F
},serializeStyle:function(F){var G="";
C(F,function(H,I){if(I&&H){if(E.isGecko&&I.indexOf("-moz-")===0){return 
}switch(I){case"color":case"background-color":H=H.toLowerCase();
break
}G+=(G?" ":"")+I+": "+H+";"
}});
return G
},loadCSS:function(I){var G=this,F=G.doc,H;
if(!I){I=""
}H=G.select("head")[0];
C(I.split(","),function(K){var J;
if(G.files[K]){return 
}G.files[K]=true;
J=G.create("link",{rel:"stylesheet",href:E._addVer(K)});
if(B&&F.documentMode){J.onload=function(){F.recalc();
J.onload=null
}
}H.appendChild(J)
})
},addClass:function(G,F){return this.run(G,function(I){var H;
if(!F){return 0
}if(this.hasClass(I,F)){return I.className
}H=this.removeClass(I,F);
return I.className=(H!=""?(H+" "):"")+F
})
},removeClass:function(G,F){var I=this,H;
return I.run(G,function(J){var K;
if(I.hasClass(J,F)){if(!H){H=new RegExp("(^|\\s+)"+F+"(\\s+|$)","g")
}K=J.className.replace(H," ");
return J.className=E.trim(K!=" "?K:"")
}return J.className
})
},hasClass:function(F,G){F=this.get(F);
if(!F||!G){return false
}return(" "+F.className+" ").indexOf(" "+G+" ")!==-1
},show:function(F){return this.setStyle(F,"display","block")
},hide:function(F){return this.setStyle(F,"display","none")
},isHidden:function(F){F=this.get(F);
return !F||F.style.display=="none"||this.getStyle(F,"display")=="none"
},uniqueId:function(F){return(!F?"mce_":F)+(this.counter++)
},setHTML:function(F,G){var H=this;
return this.run(F,function(I){var M,K,L,N,J,M;
G=H.processHTML(G);
if(B){function O(){try{I.innerHTML="<br />"+G;
I.removeChild(I.firstChild)
}catch(P){while(I.firstChild){I.firstChild.removeNode()
}M=H.create("div");
M.innerHTML="<br />"+G;
C(M.childNodes,function(Q,R){if(R){I.appendChild(Q)
}})
}}if(H.settings.fix_ie_paragraphs){G=G.replace(/<p><\/p>|<p([^>]+)><\/p>|<p[^\/+]\/>/gi,'<p$1 mce_keep="true">&nbsp;</p>')
}O();
if(H.settings.fix_ie_paragraphs){L=I.getElementsByTagName("p");
for(K=L.length-1,M=0;
K>=0;
K--){N=L[K];
if(!N.hasChildNodes()){if(!N.mce_keep){M=1;
break
}N.removeAttribute("mce_keep")
}}}if(M){G=G.replace(/<p ([^>]+)>|<p>/g,'<div $1 mce_tmp="1">');
G=G.replace(/<\/p>/g,"</div>");
O();
if(H.settings.fix_ie_paragraphs){L=I.getElementsByTagName("DIV");
for(K=L.length-1;
K>=0;
K--){N=L[K];
if(N.mce_tmp){J=H.doc.createElement("p");
N.cloneNode(false).outerHTML.replace(/([a-z0-9\-_]+)=/gi,function(Q,R){var P;
if(R!=="mce_tmp"){P=N.getAttribute(R);
if(!P&&R==="class"){P=N.className
}J.setAttribute(R,P)
}});
for(M=0;
M<N.childNodes.length;
M++){J.appendChild(N.childNodes[M].cloneNode(true))
}N.swapNode(J)
}}}}}else{I.innerHTML=G
}return G
})
},processHTML:function(F){var H=this,G=H.settings;
if(!G.process_html){return F
}if(E.isGecko){F=F.replace(/<(\/?)strong>|<strong( [^>]+)>/gi,"<$1b$2>");
F=F.replace(/<(\/?)em>|<em( [^>]+)>/gi,"<$1i$2>")
}else{if(B){F=F.replace(/&apos;/g,"&#39;");
F=F.replace(/\s+(disabled|checked|readonly|selected)\s*=\s*[\"\']?(false|0)[\"\']?/gi,"")
}}F=F.replace(/<a( )([^>]+)\/>|<a\/>/gi,"<a$1$2></a>");
if(G.keep_values){if(/<script|style/.test(F)){function I(J){J=J.replace(/(<!--\[CDATA\[|\]\]-->)/g,"\n");
J=J.replace(/^[\r\n]*|[\r\n]*$/g,"");
J=J.replace(/^\s*(\/\/\s*<!--|\/\/\s*<!\[CDATA\[|<!--|<!\[CDATA\[)[\r\n]*/g,"");
J=J.replace(/\s*(\/\/\s*\]\]>|\/\/\s*-->|\]\]>|-->|\]\]-->)\s*$/g,"");
return J
}F=F.replace(/<script([^>]+|)>([\s\S]*?)<\/script>/g,function(L,J,K){if(!J){J=' type="text/javascript"'
}J=J.replace(/(type|language)=\"?/,"$&mce-");
J=J.replace(/src=\"([^\"]+)\"?/,function(M,N){if(G.url_converter){N=H.encode(G.url_converter.call(G.url_converter_scope||H,H.decode(N),"src","script"))
}return'mce_src="'+N+'"'
});
if(E.trim(K)){K="<!--\n"+I(K)+"\n// -->"
}return"<mce:script"+J+">"+K+"</mce:script>"
});
F=F.replace(/<style([^>]+|)>([\s\S]*?)<\/style>/g,function(L,J,K){if(K){K="<!--\n"+I(K)+"\n-->"
}return"<mce:style"+J+">"+K+"</mce:style><style "+J+' mce_bogus="1">'+K+"</style>"
})
}F=F.replace(/<!\[CDATA\[([\s\S]+)\]\]>/g,"<!--[CDATA[$1]]-->");
F=F.replace(/<([\w:]+) [^>]*(src|href|style|shape|coords)[^>]*>/gi,function(L,J){function K(O,P,M){var N=M;
if(L.indexOf("mce_"+P)!=-1){return O
}if(P=="style"){if(H._isRes(M)){return O
}if(G.hex_colors){N=N.replace(/rgb\([^\)]+\)/g,function(Q){return H.toHex(Q)
})
}if(G.url_converter){N=N.replace(/url\([\'\"]?([^\)\'\"]+)\)/g,function(Q,R){return"url("+H.encode(G.url_converter.call(G.url_converter_scope||H,H.decode(R),P,J))+")"
})
}}else{if(P!="coords"&&P!="shape"){if(G.url_converter){N=H.encode(G.url_converter.call(G.url_converter_scope||H,H.decode(M),P,J))
}}}return" "+P+'="'+M+'" mce_'+P+'="'+N+'"'
}L=L.replace(/ (src|href|style|coords|shape)=[\"]([^\"]+)[\"]/gi,K);
L=L.replace(/ (src|href|style|coords|shape)=[\']([^\']+)[\']/gi,K);
return L.replace(/ (src|href|style|coords|shape)=([^\s\"\'>]+)/gi,K)
})
}return F
},getOuterHTML:function(G){var F;
G=this.get(G);
if(!G){return null
}if(G.outerHTML!==undefined){return G.outerHTML
}F=(G.ownerDocument||this.doc).createElement("body");
F.appendChild(G.cloneNode(true));
return F.innerHTML
},setOuterHTML:function(G,H,F){var I=this;
return this.run(G,function(L){var J,K;
L=I.get(L);
F=F||L.ownerDocument||I.doc;
if(B&&L.nodeType==1){L.outerHTML=H
}else{K=F.createElement("body");
K.innerHTML=H;
J=K.lastChild;
while(J){I.insertAfter(J.cloneNode(true),L);
J=J.previousSibling
}I.remove(L)
}})
},decode:function(H){var G,F,I;
if(/&[^;]+;/.test(H)){G=this.doc.createElement("div");
G.innerHTML=H;
F=G.firstChild;
I="";
if(F){do{I+=F.nodeValue
}while(F.nextSibling)
}return I||H
}return H
},encode:function(F){return F?(""+F).replace(/[<>&\"]/g,function(G,H){switch(G){case"&":return"&amp;";
case'"':return"&quot;";
case"<":return"&lt;";
case">":return"&gt;"
}return G
}):F
},insertAfter:function(F,G){var H=this;
G=H.get(G);
return this.run(F,function(I){var J,K;
J=G.parentNode;
K=G.nextSibling;
if(K){J.insertBefore(I,K)
}else{J.appendChild(I)
}return I
})
},isBlock:function(F){if(F.nodeType&&F.nodeType!==1){return false
}F=F.nodeName||F;
return/^(H[1-6]|HR|P|DIV|ADDRESS|PRE|FORM|TABLE|LI|OL|UL|TR|TD|CAPTION|BLOCKQUOTE|CENTER|DL|DT|DD|DIR|FIELDSET|NOSCRIPT|NOFRAMES|MENU|ISINDEX|SAMP)$/.test(F)
},replace:function(F,G,I){var H=this;
if(A(G,"array")){F=F.cloneNode(true)
}return H.run(G,function(J){if(I){C(J.childNodes,function(K){F.appendChild(K.cloneNode(true))
})
}if(H.fixPsuedoLeaks&&J.nodeType===1){J.parentNode.insertBefore(F,J);
H.remove(J);
return F
}return J.parentNode.replaceChild(F,J)
})
},findCommonAncestor:function(G,I){var F=G,H;
while(F){H=I;
while(H&&F!=H){H=H.parentNode
}if(F==H){break
}F=F.parentNode
}if(!F&&G.ownerDocument){return G.ownerDocument.documentElement
}return F
},toHex:function(H){var F=/^\s*rgb\s*?\(\s*?([0-9]+)\s*?,\s*?([0-9]+)\s*?,\s*?([0-9]+)\s*?\)\s*$/i.exec(H);
function G(I){I=parseInt(I).toString(16);
return I.length>1?I:"0"+I
}if(F){H="#"+G(F[1])+G(F[2])+G(F[3]);
return H
}return H
},getClasses:function(){var G=this,K=[],H,F={},M=G.settings.class_filter,I;
if(G.classes){return G.classes
}function L(N){C(N.imports,function(O){L(O)
});
C(N.cssRules||N.rules,function(O){switch(O.type||1){case 1:if(O.selectorText){C(O.selectorText.split(","),function(P){P=P.replace(/^\s*|\s*$|^\s\./g,"");
if(/\.mce/.test(P)||!/\.[\w\-]+$/.test(P)){return 
}I=P;
P=P.replace(/.*\.([a-z0-9_\-]+).*/i,"$1");
if(M&&!(P=M(P,I))){return 
}if(!F[P]){K.push({"class":P});
F[P]=1
}})
}break;
case 3:L(O.styleSheet);
break
}})
}try{C(G.doc.styleSheets,L)
}catch(J){}if(K.length>0){G.classes=K
}return K
},run:function(G,H,I){var J=this,F;
if(J.doc&&typeof (G)==="string"){G=J.get(G)
}if(!G){return false
}I=I||this;
if(!G.nodeType&&(G.length||G.length===0)){F=[];
C(G,function(K,L){if(K){if(typeof (K)=="string"){K=J.doc.getElementById(K)
}F.push(H.call(I,K,L))
}});
return F
}return H.call(I,G)
},getAttribs:function(F){var G;
F=this.get(F);
if(!F){return[]
}if(B){G=[];
if(F.nodeName=="OBJECT"){return F.attributes
}F.cloneNode(false).outerHTML.replace(/([a-z0-9\:\-_]+)=/gi,function(H,I){G.push({specified:1,nodeName:I})
});
return G
}return F.attributes
},destroy:function(F){var G=this;
if(G.events){G.events.destroy()
}G.win=G.doc=G.root=G.events=null;
if(!F){E.removeUnload(G.destroy)
}},createRng:function(){var F=this.doc;
return F.createRange?F.createRange():new E.dom.Range(this)
},split:function(J,K,G){var F=this,P=F.createRng(),I,L,H;
function O(Q,R){Q=Q[R];
if(Q&&Q[R]&&Q[R].nodeType==1&&M(Q[R])){F.remove(Q[R])
}}function M(Q){Q=F.getOuterHTML(Q);
Q=Q.replace(/<(img|hr|table)/gi,"-");
Q=Q.replace(/<[^>]+>/g,"");
return Q.replace(/[ \t\r\n]+|&nbsp;|&#160;/g,"")==""
}function N(Q){var R=0;
while(Q.previousSibling){R++;
Q=Q.previousSibling
}return R
}if(J&&K){P.setStart(J.parentNode,N(J));
P.setEnd(K.parentNode,N(K));
I=P.extractContents();
P=F.createRng();
P.setStart(K.parentNode,N(K)+1);
P.setEnd(J.parentNode,N(J)+1);
L=P.extractContents();
H=J.parentNode;
O(I,"lastChild");
if(!M(I)){H.insertBefore(I,J)
}if(G){H.replaceChild(G,K)
}else{H.insertBefore(K,J)
}O(L,"firstChild");
if(!M(L)){H.insertBefore(L,J)
}F.remove(J);
return G||K
}},bind:function(F,J,G,H){var I=this;
if(!I.events){I.events=new E.dom.EventUtils()
}return I.events.add(F,J,G,H||this)
},unbind:function(F,I,G){var H=this;
if(!H.events){H.events=new E.dom.EventUtils()
}return H.events.remove(F,I,G)
},_isRes:function(F){return/^(top|left|bottom|right|width|height)/i.test(F)||/;\s*(top|left|bottom|right|width|height)/i.test(F)
}});
E.DOM=new E.dom.DOMUtils(document,{process_html:0})
})(tinymce);
(function(D){var B=0,G=1,E=2,F=tinymce.extend;
function C(J,L){var M,K;
if(J.parentNode!=L){return -1
}for(K=L.firstChild,M=0;
K!=J;
K=K.nextSibling){M++
}return M
}function H(J){var K=0;
while(J.previousSibling){K++;
J=J.previousSibling
}return K
}function A(L,K){var J;
if(L.nodeType==3){return L
}if(K<0){return L
}J=L.firstChild;
while(J!=null&&K>0){--K;
J=J.nextSibling
}if(J!=null){return J
}return L
}function I(J){var K=J.doc;
F(this,{dom:J,startContainer:K,startOffset:0,endContainer:K,endOffset:0,collapsed:true,commonAncestorContainer:K,START_TO_START:0,START_TO_END:1,END_TO_END:2,END_TO_START:3})
}F(I.prototype,{setStart:function(J,K){this._setEndPoint(true,J,K)
},setEnd:function(J,K){this._setEndPoint(false,J,K)
},setStartBefore:function(J){this.setStart(J.parentNode,H(J))
},setStartAfter:function(J){this.setStart(J.parentNode,H(J)+1)
},setEndBefore:function(J){this.setEnd(J.parentNode,H(J))
},setEndAfter:function(J){this.setEnd(J.parentNode,H(J)+1)
},collapse:function(J){var K=this;
if(J){K.endContainer=K.startContainer;
K.endOffset=K.startOffset
}else{K.startContainer=K.endContainer;
K.startOffset=K.endOffset
}K.collapsed=true
},selectNode:function(J){this.setStartBefore(J);
this.setEndAfter(J)
},selectNodeContents:function(J){this.setStart(J,0);
this.setEnd(J,J.nodeType===1?J.childNodes.length:J.nodeValue.length)
},compareBoundaryPoints:function(J,P){var K=this,N=K.startContainer,O=K.startOffset,L=K.endContainer,M=K.endOffset;
if(J===0){return K._compareBoundaryPoints(N,O,N,O)
}if(J===1){return K._compareBoundaryPoints(N,O,L,M)
}if(J===2){return K._compareBoundaryPoints(L,M,L,M)
}if(J===3){return K._compareBoundaryPoints(L,M,N,O)
}},deleteContents:function(){this._traverse(E)
},extractContents:function(){return this._traverse(B)
},cloneContents:function(){return this._traverse(G)
},insertNode:function(J){var M=this,K,L;
if(J.nodeType===3||J.nodeType===4){K=M.startContainer.splitText(M.startOffset);
M.startContainer.parentNode.insertBefore(J,K)
}else{if(M.startContainer.childNodes.length>0){L=M.startContainer.childNodes[M.startOffset]
}M.startContainer.insertBefore(J,L)
}},surroundContents:function(J){var L=this,K=L.extractContents();
L.insertNode(J);
J.appendChild(K);
L.selectNode(J)
},cloneRange:function(){var J=this;
return F(new I(J.dom),{startContainer:J.startContainer,startOffset:J.startOffset,endContainer:J.endContainer,endOffset:J.endOffset,collapsed:J.collapsed,commonAncestorContainer:J.commonAncestorContainer})
},_isCollapsed:function(){return(this.startContainer==this.endContainer&&this.startOffset==this.endOffset)
},_compareBoundaryPoints:function(N,L,P,M){var K,O,Q,J,R,S;
if(N==P){if(L==M){return 0
}else{if(L<M){return -1
}else{return 1
}}}K=P;
while(K&&K.parentNode!=N){K=K.parentNode
}if(K){O=0;
Q=N.firstChild;
while(Q!=K&&O<L){O++;
Q=Q.nextSibling
}if(L<=O){return -1
}else{return 1
}}K=N;
while(K&&K.parentNode!=P){K=K.parentNode
}if(K){O=0;
Q=P.firstChild;
while(Q!=K&&O<M){O++;
Q=Q.nextSibling
}if(O<M){return -1
}else{return 1
}}J=this.dom.findCommonAncestor(N,P);
R=N;
while(R&&R.parentNode!=J){R=R.parentNode
}if(!R){R=J
}S=P;
while(S&&S.parentNode!=J){S=S.parentNode
}if(!S){S=J
}if(R==S){return 0
}Q=J.firstChild;
while(Q){if(Q==R){return -1
}if(Q==S){return 1
}Q=Q.nextSibling
}},_setEndPoint:function(L,N,O){var K=this,M,J;
if(L){K.startContainer=N;
K.startOffset=O
}else{K.endContainer=N;
K.endOffset=O
}M=K.endContainer;
while(M.parentNode){M=M.parentNode
}J=K.startContainer;
while(J.parentNode){J=J.parentNode
}if(J!=M){K.collapse(L)
}else{if(K._compareBoundaryPoints(K.startContainer,K.startOffset,K.endContainer,K.endOffset)>0){K.collapse(L)
}}K.collapsed=K._isCollapsed();
K.commonAncestorContainer=K.dom.findCommonAncestor(K.startContainer,K.endContainer)
},_traverse:function(J){var T=this,K,N=0,R=0,P,L,O,M,Q,S;
if(T.startContainer==T.endContainer){return T._traverseSameContainer(J)
}for(K=T.endContainer,P=K.parentNode;
P!=null;
K=P,P=P.parentNode){if(P==T.startContainer){return T._traverseCommonStartContainer(K,J)
}++N
}for(K=T.startContainer,P=K.parentNode;
P!=null;
K=P,P=P.parentNode){if(P==T.endContainer){return T._traverseCommonEndContainer(K,J)
}++R
}L=R-N;
O=T.startContainer;
while(L>0){O=O.parentNode;
L--
}M=T.endContainer;
while(L<0){M=M.parentNode;
L++
}for(Q=O.parentNode,S=M.parentNode;
Q!=S;
Q=Q.parentNode,S=S.parentNode){O=Q;
M=S
}return T._traverseCommonAncestors(O,M,J)
},_traverseSameContainer:function(M){var J=this,K,R,Q,P,O,L,N;
if(M!=E){K=J.dom.doc.createDocumentFragment()
}if(J.startOffset==J.endOffset){return K
}if(J.startContainer.nodeType==3){R=J.startContainer.nodeValue;
Q=R.substring(J.startOffset,J.endOffset);
if(M!=G){J.startContainer.deleteData(J.startOffset,J.endOffset-J.startOffset);
J.collapse(true)
}if(M==E){return null
}K.appendChild(J.dom.doc.createTextNode(Q));
return K
}P=A(J.startContainer,J.startOffset);
O=J.endOffset-J.startOffset;
while(O>0){L=P.nextSibling;
N=J._traverseFullySelected(P,M);
if(K){K.appendChild(N)
}--O;
P=L
}if(M!=G){J.collapse(true)
}return K
},_traverseCommonStartContainer:function(Q,L){var R=this,J,P,O,N,K,M;
if(L!=E){J=R.dom.doc.createDocumentFragment()
}P=R._traverseRightBoundary(Q,L);
if(J){J.appendChild(P)
}O=C(Q,R.startContainer);
N=O-R.startOffset;
if(N<=0){if(L!=G){R.setEndBefore(Q);
R.collapse(false)
}return J
}P=Q.previousSibling;
while(N>0){K=P.previousSibling;
M=R._traverseFullySelected(P,L);
if(J){J.insertBefore(M,J.firstChild)
}--N;
P=K
}if(L!=G){R.setEndBefore(Q);
R.collapse(false)
}return J
},_traverseCommonEndContainer:function(N,L){var R=this,J,M,Q,P,K,O;
if(L!=E){J=R.dom.doc.createDocumentFragment()
}Q=R._traverseLeftBoundary(N,L);
if(J){J.appendChild(Q)
}M=C(N,R.endContainer);
++M;
P=R.endOffset-M;
Q=N.nextSibling;
while(P>0){K=Q.nextSibling;
O=R._traverseFullySelected(Q,L);
if(J){J.appendChild(O)
}--P;
Q=K
}if(L!=G){R.setStartAfter(N);
R.collapse(true)
}return J
},_traverseCommonAncestors:function(L,Q,U){var R=this,O,S,M,K,J,P,T,N;
if(U!=E){S=R.dom.doc.createDocumentFragment()
}O=R._traverseLeftBoundary(L,U);
if(S){S.appendChild(O)
}M=L.parentNode;
K=C(L,M);
J=C(Q,M);
++K;
P=J-K;
T=L.nextSibling;
while(P>0){N=T.nextSibling;
O=R._traverseFullySelected(T,U);
if(S){S.appendChild(O)
}T=N;
--P
}O=R._traverseRightBoundary(Q,U);
if(S){S.appendChild(O)
}if(U!=G){R.setStartAfter(L);
R.collapse(true)
}return S
},_traverseRightBoundary:function(L,K){var S=this,P=A(S.endContainer,S.endOffset-1),J,M,N,R,Q;
var O=P!=S.endContainer;
if(P==L){return S._traverseNode(P,O,false,K)
}J=P.parentNode;
M=S._traverseNode(J,false,false,K);
while(J!=null){while(P!=null){N=P.previousSibling;
R=S._traverseNode(P,O,false,K);
if(K!=E){M.insertBefore(R,M.firstChild)
}O=true;
P=N
}if(J==L){return M
}P=J.previousSibling;
J=J.parentNode;
Q=S._traverseNode(J,false,false,K);
if(K!=E){Q.appendChild(M)
}M=Q
}return null
},_traverseLeftBoundary:function(L,K){var S=this,O=A(S.startContainer,S.startOffset);
var N=O!=S.startContainer,J,M,P,R,Q;
if(O==L){return S._traverseNode(O,N,true,K)
}J=O.parentNode;
M=S._traverseNode(J,false,true,K);
while(J!=null){while(O!=null){P=O.nextSibling;
R=S._traverseNode(O,N,true,K);
if(K!=E){M.appendChild(R)
}N=true;
O=P
}if(J==L){return M
}O=J.nextSibling;
J=J.parentNode;
Q=S._traverseNode(J,false,true,K);
if(K!=E){Q.appendChild(M)
}M=Q
}return null
},_traverseNode:function(Q,M,J,S){var R=this,N,O,L,P,K;
if(M){return R._traverseFullySelected(Q,S)
}if(Q.nodeType==3){N=Q.nodeValue;
if(J){P=R.startOffset;
O=N.substring(P);
L=N.substring(0,P)
}else{P=R.endOffset;
O=N.substring(0,P);
L=N.substring(P)
}if(S!=G){Q.nodeValue=L
}if(S==E){return null
}K=Q.cloneNode(false);
K.nodeValue=O;
return K
}if(S==E){return null
}return Q.cloneNode(false)
},_traverseFullySelected:function(J,K){var L=this;
if(K!=E){return K==G?J.cloneNode(true):J
}J.parentNode.removeChild(J);
return null
}});
D.Range=I
})(tinymce.dom);
(function(){function A(F){var G=this,C="\uFEFF",B,D;
function H(I,J){if(I&&J){if(I.item&&J.item&&I.item(0)===J.item(0)){return 1
}if(I.isEqual&&J.isEqual&&J.isEqual(I)){return 1
}}return 0
}function E(){var N=F.dom,Q=F.getRng(),S=N.createRng(),K,P,M,J,L,O;
function R(T){var V=T.parentNode.childNodes,U;
for(U=V.length-1;
U>=0;
U--){if(V[U]==T){return U
}}return -1
}function I(a){var c=Q.duplicate(),U,X,b,Z,Y=0,W=0,V,T;
c.collapse(a);
U=c.parentElement();
c.pasteHTML(C);
b=U.childNodes;
for(X=0;
X<b.length;
X++){Z=b[X];
if(X>0&&(Z.nodeType!==3||b[X-1].nodeType!==3)){W++
}if(Z.nodeType===3){V=Z.nodeValue.indexOf(C);
if(V!==-1){Y+=V;
break
}Y+=Z.nodeValue.length
}else{Y=0
}}c.moveStart("character",-1);
c.text="";
return{index:W,offset:Y,parent:U}
}M=Q.item?Q.item(0):Q.parentElement();
if(M.ownerDocument!=N.doc){return S
}if(Q.item||!M.hasChildNodes()){S.setStart(M.parentNode,R(M));
S.setEnd(S.startContainer,S.startOffset+1);
return S
}O=F.isCollapsed();
K=I(true);
P=I(false);
K.parent.normalize();
P.parent.normalize();
J=K.parent.childNodes[Math.min(K.index,K.parent.childNodes.length-1)];
if(J.nodeType!=3){S.setStart(K.parent,K.index)
}else{S.setStart(K.parent.childNodes[K.index],K.offset)
}L=P.parent.childNodes[Math.min(P.index,P.parent.childNodes.length-1)];
if(L.nodeType!=3){if(!O){P.index++
}S.setEnd(P.parent,P.index)
}else{S.setEnd(P.parent.childNodes[P.index],P.offset)
}if(!O){J=S.startContainer;
if(J.nodeType==1){S.setStart(J,Math.min(S.startOffset,J.childNodes.length))
}L=S.endContainer;
if(L.nodeType==1){S.setEnd(L,Math.min(S.endOffset,L.childNodes.length))
}}G.addRange(S);
return S
}this.addRange=function(Q){var L,N=F.dom.doc.body,K,P,J,O,M,R;
J=Q.startContainer;
O=Q.startOffset;
M=Q.endContainer;
R=Q.endOffset;
L=N.createTextRange();
J=J.nodeType==1?J.childNodes[Math.min(O,J.childNodes.length-1)]:J;
M=M.nodeType==1?M.childNodes[Math.min(O==R?R:R-1,M.childNodes.length-1)]:M;
if(J==M&&J.nodeType==1){if(/^(IMG|TABLE)$/.test(J.nodeName)&&O!=R){L=N.createControlRange();
L.addElement(J)
}else{L=N.createTextRange();
if(!J.hasChildNodes()&&J.canHaveHTML){J.innerHTML=C
}L.moveToElementText(J);
if(J.innerHTML==C){L.collapse(true);
J.removeChild(J.firstChild)
}}if(O==R){L.collapse(R<=Q.endContainer.childNodes.length-1)
}L.select();
return 
}function I(V,T){var U,W,S;
if(V.nodeType!=3){return -1
}U=V.nodeValue;
W=N.createTextRange();
V.nodeValue=U.substring(0,T)+C+U.substring(T);
W.moveToElementText(V.parentNode);
W.findText(C);
S=Math.abs(W.moveStart("character",-1048575));
V.nodeValue=U;
return S
}if(Q.collapsed){pos=I(J,O);
L=N.createTextRange();
L.move("character",pos);
L.select();
return 
}else{if(J==M&&J.nodeType==3){K=I(J,O);
L.move("character",K);
L.moveEnd("character",R-O);
L.select();
return 
}K=I(J,O);
P=I(M,R);
L=N.createTextRange();
if(K==-1){L.moveToElementText(J);
K=0
}else{L.move("character",K)
}tmpRng=N.createTextRange();
if(P==-1){tmpRng.moveToElementText(M)
}else{tmpRng.move("character",P)
}L.setEndPoint("EndToEnd",tmpRng);
L.select();
return 
}};
this.getRangeAt=function(){if(!B||!H(D,F.getRng())){B=E();
D=F.getRng()
}return B
};
this.destroy=function(){D=B=null
}
}tinymce.dom.TridentSelection=A
})();
(function(){var B=/((?:\((?:\([^()]+\)|[^()]+)+\)|\[(?:\[[^[\]]*\]|['"][^'"]*['"]|[^[\]'"]+)+\]|\\.|[^ >+~,(\[\\]+)+|[>+~])(\s*,\s*)?/g,I=0,M=Object.prototype.toString,D=false;
var O=function(c,a,h,W){h=h||[];
var i=a=a||document;
if(a.nodeType!==1&&a.nodeType!==9){return[]
}if(!c||typeof c!=="string"){return h
}var g=[],d,R,V,X,Q,b,f=true,U=C(a);
B.lastIndex=0;
while((d=B.exec(c))!==null){g.push(d[1]);
if(d[2]){b=RegExp.rightContext;
break
}}if(g.length>1&&H.exec(c)){if(g.length===2&&L.relative[g[0]]){R=K(g[0]+g[1],a)
}else{R=L.relative[g[0]]?[a]:O(g.shift(),a);
while(g.length){c=g.shift();
if(L.relative[c]){c+=g.shift()
}R=K(c,R)
}}}else{if(!W&&g.length>1&&a.nodeType===9&&!U&&L.match.ID.test(g[0])&&!L.match.ID.test(g[g.length-1])){var T=O.find(g.shift(),a,U);
a=T.expr?O.filter(T.expr,T.set)[0]:T.set[0]
}if(a){var T=W?{expr:g.pop(),set:P(W)}:O.find(g.pop(),g.length===1&&(g[0]==="~"||g[0]==="+")&&a.parentNode?a.parentNode:a,U);
R=T.expr?O.filter(T.expr,T.set):T.set;
if(g.length>0){V=P(R)
}else{f=false
}while(g.length){var Y=g.pop(),S=Y;
if(!L.relative[Y]){Y=""
}else{S=g.pop()
}if(S==null){S=a
}L.relative[Y](V,S,U)
}}else{V=g=[]
}}if(!V){V=R
}if(!V){throw"Syntax error, unrecognized expression: "+(Y||c)
}if(M.call(V)==="[object Array]"){if(!f){h.push.apply(h,V)
}else{if(a&&a.nodeType===1){for(var Z=0;
V[Z]!=null;
Z++){if(V[Z]&&(V[Z]===true||V[Z].nodeType===1&&J(a,V[Z]))){h.push(R[Z])
}}}else{for(var Z=0;
V[Z]!=null;
Z++){if(V[Z]&&V[Z].nodeType===1){h.push(R[Z])
}}}}}else{P(V,h)
}if(b){O(b,i,h,W);
O.uniqueSort(h)
}return h
};
O.uniqueSort=function(Q){if(N){D=false;
Q.sort(N);
if(D){for(var R=1;
R<Q.length;
R++){if(Q[R]===Q[R-1]){Q.splice(R--,1)
}}}}};
O.matches=function(R,Q){return O(R,null,null,Q)
};
O.find=function(T,R,S){var U,W;
if(!T){return[]
}for(var X=0,Y=L.order.length;
X<Y;
X++){var V=L.order[X],W;
if((W=L.match[V].exec(T))){var Q=RegExp.leftContext;
if(Q.substr(Q.length-1)!=="\\"){W[1]=(W[1]||"").replace(/\\/g,"");
U=L.find[V](W,R,S);
if(U!=null){T=T.replace(L.match[V],"");
break
}}}}if(!U){U=R.getElementsByTagName("*")
}return{set:U,expr:T}
};
O.filter=function(U,W,R,d){var g=U,c=[],Y=W,a,V,Z=W&&W[0]&&C(W[0]);
while(U&&W.length){for(var X in L.filter){if((a=L.match[X].exec(U))!=null){var Q=L.filter[X],f,S;
V=false;
if(Y==c){c=[]
}if(L.preFilter[X]){a=L.preFilter[X](a,Y,R,c,d,Z);
if(!a){V=f=true
}else{if(a===true){continue
}}}if(a){for(var b=0;
(S=Y[b])!=null;
b++){if(S){f=Q(S,a,b,Y);
var T=d^!!f;
if(R&&f!=null){if(T){V=true
}else{Y[b]=false
}}else{if(T){c.push(S);
V=true
}}}}}if(f!==undefined){if(!R){Y=c
}U=U.replace(L.match[X],"");
if(!V){return[]
}break
}}}if(U==g){if(V==null){throw"Syntax error, unrecognized expression: "+U
}else{break
}}g=U
}return Y
};
var L=O.selectors={order:["ID","NAME","TAG"],match:{ID:/#((?:[\w\u00c0-\uFFFF_-]|\\.)+)/,CLASS:/\.((?:[\w\u00c0-\uFFFF_-]|\\.)+)/,NAME:/\[name=['"]*((?:[\w\u00c0-\uFFFF_-]|\\.)+)['"]*\]/,ATTR:/\[\s*((?:[\w\u00c0-\uFFFF_-]|\\.)+)\s*(?:(\S?=)\s*(['"]*)(.*?)\3|)\s*\]/,TAG:/^((?:[\w\u00c0-\uFFFF\*_-]|\\.)+)/,CHILD:/:(only|nth|last|first)-child(?:\((even|odd|[\dn+-]*)\))?/,POS:/:(nth|eq|gt|lt|first|last|even|odd)(?:\((\d*)\))?(?=[^-]|$)/,PSEUDO:/:((?:[\w\u00c0-\uFFFF_-]|\\.)+)(?:\((['"]*)((?:\([^\)]+\)|[^\2\(\)]*)+)\2\))?/},attrMap:{"class":"className","for":"htmlFor"},attrHandle:{href:function(Q){return Q.getAttribute("href")
}},relative:{"+":function(T,R,U){var W=typeof R==="string",S=W&&!/\W/.test(R),V=W&&!S;
if(S&&!U){R=R.toUpperCase()
}for(var X=0,Y=T.length,Q;
X<Y;
X++){if((Q=T[X])){while((Q=Q.previousSibling)&&Q.nodeType!==1){}T[X]=V||Q&&Q.nodeName===R?Q||false:Q===R
}}if(V){O.filter(R,T,true)
}},">":function(R,W,Q){var T=typeof W==="string";
if(T&&!/\W/.test(W)){W=Q?W:W.toUpperCase();
for(var V=0,X=R.length;
V<X;
V++){var S=R[V];
if(S){var U=S.parentNode;
R[V]=U.nodeName===W?U:false
}}}else{for(var V=0,X=R.length;
V<X;
V++){var S=R[V];
if(S){R[V]=T?S.parentNode:S.parentNode===W
}}if(T){O.filter(W,R,true)
}}},"":function(S,U,Q){var T=I++,V=A;
if(!U.match(/\W/)){var R=U=Q?U:U.toUpperCase();
V=E
}V("parentNode",U,T,S,R,Q)
},"~":function(S,U,Q){var T=I++,V=A;
if(typeof U==="string"&&!U.match(/\W/)){var R=U=Q?U:U.toUpperCase();
V=E
}V("previousSibling",U,T,S,R,Q)
}},find:{ID:function(S,R,Q){if(typeof R.getElementById!=="undefined"&&!Q){var T=R.getElementById(S[1]);
return T?[T]:[]
}},NAME:function(U,R,Q){if(typeof R.getElementsByName!=="undefined"){var V=[],S=R.getElementsByName(U[1]);
for(var T=0,W=S.length;
T<W;
T++){if(S[T].getAttribute("name")===U[1]){V.push(S[T])
}}return V.length===0?null:V
}},TAG:function(R,Q){return Q.getElementsByTagName(R[1])
}},preFilter:{CLASS:function(U,W,V,X,R,Q){U=" "+U[1].replace(/\\/g,"")+" ";
if(Q){return U
}for(var T=0,S;
(S=W[T])!=null;
T++){if(S){if(R^(S.className&&(" "+S.className+" ").indexOf(U)>=0)){if(!V){X.push(S)
}}else{if(V){W[T]=false
}}}}return false
},ID:function(Q){return Q[1].replace(/\\/g,"")
},TAG:function(R,S){for(var Q=0;
S[Q]===false;
Q++){}return S[Q]&&C(S[Q])?R[1]:R[1].toUpperCase()
},CHILD:function(R){if(R[1]=="nth"){var Q=/(-?)(\d*)n((?:\+|-)?\d*)/.exec(R[2]=="even"&&"2n"||R[2]=="odd"&&"2n+1"||!/\D/.test(R[2])&&"0n+"+R[2]||R[2]);
R[2]=(Q[1]+(Q[2]||1))-0;
R[3]=Q[3]-0
}R[0]=I++;
return R
},ATTR:function(S,V,U,W,R,Q){var T=S[1].replace(/\\/g,"");
if(!Q&&L.attrMap[T]){S[1]=L.attrMap[T]
}if(S[2]==="~="){S[4]=" "+S[4]+" "
}return S
},PSEUDO:function(R,U,T,V,Q){if(R[1]==="not"){if(R[3].match(B).length>1||/^\w/.test(R[3])){R[3]=O(R[3],null,null,U)
}else{var S=O.filter(R[3],U,T,true^Q);
if(!T){V.push.apply(V,S)
}return false
}}else{if(L.match.POS.test(R[0])||L.match.CHILD.test(R[0])){return true
}}return R
},POS:function(Q){Q.unshift(true);
return Q
}},filters:{enabled:function(Q){return Q.disabled===false&&Q.type!=="hidden"
},disabled:function(Q){return Q.disabled===true
},checked:function(Q){return Q.checked===true
},selected:function(Q){Q.parentNode.selectedIndex;
return Q.selected===true
},parent:function(Q){return !!Q.firstChild
},empty:function(Q){return !Q.firstChild
},has:function(Q,R,S){return !!O(S[3],Q).length
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
}},setFilters:{first:function(Q,R){return R===0
},last:function(R,S,T,Q){return S===Q.length-1
},even:function(Q,R){return R%2===0
},odd:function(Q,R){return R%2===1
},lt:function(Q,R,S){return R<S[3]-0
},gt:function(Q,R,S){return R>S[3]-0
},nth:function(Q,R,S){return S[3]-0==R
},eq:function(Q,R,S){return S[3]-0==R
}},filter:{PSEUDO:function(R,V,U,Q){var W=V[1],T=L.filters[W];
if(T){return T(R,U,V,Q)
}else{if(W==="contains"){return(R.textContent||R.innerText||"").indexOf(V[3])>=0
}else{if(W==="not"){var S=V[3];
for(var U=0,X=S.length;
U<X;
U++){if(S[U]===R){return false
}}return true
}}}},CHILD:function(R,Y){var V=Y[1],Q=R;
switch(V){case"only":case"first":while(Q=Q.previousSibling){if(Q.nodeType===1){return false
}}if(V=="first"){return true
}Q=R;
case"last":while(Q=Q.nextSibling){if(Q.nodeType===1){return false
}}return true;
case"nth":var Z=Y[2],S=Y[3];
if(Z==1&&S==0){return true
}var W=Y[0],T=R.parentNode;
if(T&&(T.sizcache!==W||!R.nodeIndex)){var X=0;
for(Q=T.firstChild;
Q;
Q=Q.nextSibling){if(Q.nodeType===1){Q.nodeIndex=++X
}}T.sizcache=W
}var U=R.nodeIndex-S;
if(Z==0){return U==0
}else{return(U%Z==0&&U/Z>=0)
}}},ID:function(Q,R){return Q.nodeType===1&&Q.getAttribute("id")===R
},TAG:function(Q,R){return(R==="*"&&Q.nodeType===1)||Q.nodeName===R
},CLASS:function(Q,R){return(" "+(Q.className||Q.getAttribute("class"))+" ").indexOf(R)>-1
},ATTR:function(R,T){var U=T[1],W=L.attrHandle[U]?L.attrHandle[U](R):R[U]!=null?R[U]:R.getAttribute(U),Q=W+"",S=T[2],V=T[4];
return W==null?S==="!=":S==="="?Q===V:S==="*="?Q.indexOf(V)>=0:S==="~="?(" "+Q+" ").indexOf(V)>=0:!V?Q&&W!==false:S==="!="?Q!=V:S==="^="?Q.indexOf(V)===0:S==="$="?Q.substr(Q.length-V.length)===V:S==="|="?Q===V||Q.substr(0,V.length+1)===V+"-":false
},POS:function(R,U,T,Q){var V=U[2],S=L.setFilters[V];
if(S){return S(R,T,U,Q)
}}}};
var H=L.match.POS;
for(var F in L.match){L.match[F]=new RegExp(L.match[F].source+/(?![^\[]*\])(?![^\(]*\))/.source)
}var P=function(Q,R){Q=Array.prototype.slice.call(Q);
if(R){R.push.apply(R,Q);
return R
}return Q
};
try{Array.prototype.slice.call(document.documentElement.childNodes)
}catch(G){P=function(Q,R){var T=R||[];
if(M.call(Q)==="[object Array]"){Array.prototype.push.apply(T,Q)
}else{if(typeof Q.length==="number"){for(var S=0,U=Q.length;
S<U;
S++){T.push(Q[S])
}}else{for(var S=0;
Q[S];
S++){T.push(Q[S])
}}}return T
}
}var N;
if(document.documentElement.compareDocumentPosition){N=function(R,S){var Q=R.compareDocumentPosition(S)&4?-1:R===S?0:1;
if(Q===0){D=true
}return Q
}
}else{if("sourceIndex" in document.documentElement){N=function(R,S){var Q=R.sourceIndex-S.sourceIndex;
if(Q===0){D=true
}return Q
}
}else{if(document.createRange){N=function(R,T){var S=R.ownerDocument.createRange(),U=T.ownerDocument.createRange();
S.setStart(R,0);
S.setEnd(R,0);
U.setStart(T,0);
U.setEnd(T,0);
var Q=S.compareBoundaryPoints(Range.START_TO_END,U);
if(Q===0){D=true
}return Q
}
}}}(function(){var R=document.createElement("div"),Q="script"+(new Date).getTime();
R.innerHTML="<a name='"+Q+"'/>";
var S=document.documentElement;
S.insertBefore(R,S.firstChild);
if(!!document.getElementById(Q)){L.find.ID=function(V,U,T){if(typeof U.getElementById!=="undefined"&&!T){var W=U.getElementById(V[1]);
return W?W.id===V[1]||typeof W.getAttributeNode!=="undefined"&&W.getAttributeNode("id").nodeValue===V[1]?[W]:undefined:[]
}};
L.filter.ID=function(T,V){var U=typeof T.getAttributeNode!=="undefined"&&T.getAttributeNode("id");
return T.nodeType===1&&U&&U.nodeValue===V
}
}S.removeChild(R)
})();
(function(){var Q=document.createElement("div");
Q.appendChild(document.createComment(""));
if(Q.getElementsByTagName("*").length>0){L.find.TAG=function(V,R){var S=R.getElementsByTagName(V[1]);
if(V[1]==="*"){var T=[];
for(var U=0;
S[U];
U++){if(S[U].nodeType===1){T.push(S[U])
}}S=T
}return S
}
}Q.innerHTML="<a href='#'></a>";
if(Q.firstChild&&typeof Q.firstChild.getAttribute!=="undefined"&&Q.firstChild.getAttribute("href")!=="#"){L.attrHandle.href=function(R){return R.getAttribute("href",2)
}
}})();
if(document.querySelectorAll){(function(){var S=O,Q=document.createElement("div");
Q.innerHTML="<p class='TEST'></p>";
if(Q.querySelectorAll&&Q.querySelectorAll(".TEST").length===0){return 
}O=function(U,V,X,W){V=V||document;
if(!W&&V.nodeType===9&&!C(V)){try{return P(V.querySelectorAll(U),X)
}catch(T){}}return S(U,V,X,W)
};
for(var R in S){O[R]=S[R]
}})()
}if(document.getElementsByClassName&&document.documentElement.getElementsByClassName){(function(){var Q=document.createElement("div");
Q.innerHTML="<div class='test e'></div><div class='test'></div>";
if(Q.getElementsByClassName("e").length===0){return 
}Q.lastChild.className="e";
if(Q.getElementsByClassName("e").length===1){return 
}L.order.splice(1,0,"CLASS");
L.find.CLASS=function(T,S,R){if(typeof S.getElementsByClassName!=="undefined"&&!R){return S.getElementsByClassName(T[1])
}}
})()
}function E(Q,W,X,R,V,T){var U=Q=="previousSibling"&&!T;
for(var Z=0,a=R.length;
Z<a;
Z++){var S=R[Z];
if(S){if(U&&S.nodeType===1){S.sizcache=X;
S.sizset=Z
}S=S[Q];
var Y=false;
while(S){if(S.sizcache===X){Y=R[S.sizset];
break
}if(S.nodeType===1&&!T){S.sizcache=X;
S.sizset=Z
}if(S.nodeName===W){Y=S;
break
}S=S[Q]
}R[Z]=Y
}}}function A(Q,W,X,R,V,T){var U=Q=="previousSibling"&&!T;
for(var Z=0,a=R.length;
Z<a;
Z++){var S=R[Z];
if(S){if(U&&S.nodeType===1){S.sizcache=X;
S.sizset=Z
}S=S[Q];
var Y=false;
while(S){if(S.sizcache===X){Y=R[S.sizset];
break
}if(S.nodeType===1){if(!T){S.sizcache=X;
S.sizset=Z
}if(typeof W!=="string"){if(S===W){Y=true;
break
}}else{if(O.filter(W,[S]).length>0){Y=S;
break
}}}S=S[Q]
}R[Z]=Y
}}}var J=document.compareDocumentPosition?function(Q,R){return Q.compareDocumentPosition(R)&16
}:function(Q,R){return Q!==R&&(Q.contains?Q.contains(R):true)
};
var C=function(Q){return Q.nodeType===9&&Q.documentElement.nodeName!=="HTML"||!!Q.ownerDocument&&Q.ownerDocument.documentElement.nodeName!=="HTML"
};
var K=function(X,Q){var U=[],T="",S,V=Q.nodeType?[Q]:Q;
while((S=L.match.PSEUDO.exec(X))){T+=S[0];
X=X.replace(L.match.PSEUDO,"")
}X=L.relative[X]?X+"*":X;
for(var R=0,W=V.length;
R<W;
R++){O(X,V[R],U)
}return O.filter(T,U)
};
window.tinymce.dom.Sizzle=O
})();
(function(E){var C=E.each,F=E.DOM,A=E.isIE,D=E.isWebKit,B;
E.create("tinymce.dom.EventUtils",{EventUtils:function(){this.inits=[];
this.events=[]
},add:function(G,N,H,J){var M,L=this,K=L.events,I;
if(N instanceof Array){I=[];
C(N,function(O){I.push(L.add(G,O,H,J))
});
return I
}if(G&&G.hasOwnProperty&&G instanceof Array){I=[];
C(G,function(O){O=F.get(O);
I.push(L.add(O,N,H,J))
});
return I
}G=F.get(G);
if(!G){return 
}M=function(O){if(L.disabled){return 
}O=O||window.event;
if(O&&A){if(!O.target){O.target=O.srcElement
}if(!O.preventDefault){O.preventDefault=function(){O.returnValue=false
}
}if(!O.stopPropagation){O.stopPropagation=function(){O.cancelBubble=true
}
}}if(!J){return H(O)
}return H.call(J,O)
};
if(N=="unload"){E.unloads.unshift({func:M});
return M
}if(N=="init"){if(L.domLoaded){M()
}else{L.inits.push(M)
}return M
}K.push({obj:G,name:N,func:H,cfunc:M,scope:J});
L._add(G,N,M);
return H
},remove:function(H,G,I){var L=this,M=L.events,K=false,J;
if(H&&H.hasOwnProperty&&H instanceof Array){J=[];
C(H,function(N){N=F.get(N);
J.push(L.remove(N,G,I))
});
return J
}H=F.get(H);
C(M,function(N,O){if(N.obj==H&&N.name==G&&(!I||(N.func==I||N.cfunc==I))){M.splice(O,1);
L._remove(H,G,N.cfunc);
K=true;
return false
}});
return K
},clear:function(G){var I=this,K=I.events,J,H;
if(G){G=F.get(G);
for(J=K.length-1;
J>=0;
J--){H=K[J];
if(H.obj===G){I._remove(H.obj,H.name,H.cfunc);
H.obj=H.cfunc=null;
K.splice(J,1)
}}}},cancel:function(G){if(!G){return false
}this.stop(G);
return this.prevent(G)
},stop:function(G){if(G.stopPropagation){G.stopPropagation()
}else{G.cancelBubble=true
}return false
},prevent:function(G){if(G.preventDefault){G.preventDefault()
}else{G.returnValue=false
}return false
},destroy:function(){var G=this;
C(G.events,function(H,I){G._remove(H.obj,H.name,H.cfunc);
H.obj=H.cfunc=null
});
G.events=[];
G=null
},_add:function(H,G,I){if(H.attachEvent){H.attachEvent("on"+G,I)
}else{if(H.addEventListener){H.addEventListener(G,I,false)
}else{H["on"+G]=I
}}},_remove:function(H,G,I){if(H){try{if(H.detachEvent){H.detachEvent("on"+G,I)
}else{if(H.removeEventListener){H.removeEventListener(G,I,false)
}else{H["on"+G]=null
}}}catch(J){}}},_pageInit:function(G){var H=this;
if(H.domLoaded){return 
}H.domLoaded=true;
C(H.inits,function(I){I()
});
H.inits=[]
},_wait:function(G){var I=this,H=G.document;
if(G.tinyMCE_GZ&&tinyMCE_GZ.loaded){I.domLoaded=1;
return 
}if(H.attachEvent){H.attachEvent("onreadystatechange",function(){if(H.readyState==="complete"){H.detachEvent("onreadystatechange",arguments.callee);
I._pageInit(G)
}});
if(H.documentElement.doScroll&&G==G.top){(function(){if(I.domLoaded){return 
}try{H.documentElement.doScroll("left")
}catch(J){setTimeout(arguments.callee,0);
return 
}I._pageInit(G)
})()
}}else{if(H.addEventListener){I._add(G,"DOMContentLoaded",function(){I._pageInit(G)
})
}}I._add(G,"load",function(){I._pageInit(G)
})
}});
B=E.dom.Event=new E.dom.EventUtils();
B._wait(window);
E.addUnload(function(){B.destroy()
})
})(tinymce);
(function(B){var A=B.each;
B.create("tinymce.dom.Element",{Element:function(C,E){var G=this,D,F;
E=E||{};
G.id=C;
G.dom=D=E.dom||B.DOM;
G.settings=E;
if(!B.isIE){F=G.dom.get(G.id)
}A(["getPos","getRect","getParent","add","setStyle","getStyle","setStyles","setAttrib","setAttribs","getAttrib","addClass","removeClass","hasClass","getOuterHTML","setOuterHTML","remove","show","hide","isHidden","setHTML","get"],function(H){G[H]=function(){var J=[C],I;
for(I=0;
I<arguments.length;
I++){J.push(arguments[I])
}J=D[H].apply(D,J);
G.update(H);
return J
}
})
},on:function(C,D,E){return B.dom.Event.add(this.id,C,D,E)
},getXY:function(){return{x:parseInt(this.getStyle("left")),y:parseInt(this.getStyle("top"))}
},getSize:function(){var C=this.dom.get(this.id);
return{w:parseInt(this.getStyle("width")||C.clientWidth),h:parseInt(this.getStyle("height")||C.clientHeight)}
},moveTo:function(D,C){this.setStyles({left:D,top:C})
},moveBy:function(E,C){var D=this.getXY();
this.moveTo(D.x+E,D.y+C)
},resizeTo:function(D,C){this.setStyles({width:D,height:C})
},resizeBy:function(E,C){var D=this.getSize();
this.resizeTo(D.w+E,D.h+C)
},update:function(E){var D=this,F,C=D.dom;
if(B.isIE6&&D.settings.blocker){E=E||"";
if(E.indexOf("get")===0||E.indexOf("has")===0||E.indexOf("is")===0){return 
}if(E=="remove"){C.remove(D.blocker);
return 
}if(!D.blocker){D.blocker=C.uniqueId();
F=C.add(D.settings.container||C.getRoot(),"iframe",{id:D.blocker,style:"position:absolute;",frameBorder:0,src:'javascript:""'});
C.setStyle(F,"opacity",0)
}else{F=C.get(D.blocker)
}C.setStyle(F,"left",D.getStyle("left",1));
C.setStyle(F,"top",D.getStyle("top",1));
C.setStyle(F,"width",D.getStyle("width",1));
C.setStyle(F,"height",D.getStyle("height",1));
C.setStyle(F,"display",D.getStyle("display",1));
C.setStyle(F,"zIndex",parseInt(D.getStyle("zIndex",1)||0)-1)
}}})
})(tinymce);
(function(E){function C(F){return F.replace(/[\n\r]+/g,"")
}var A=E.is,B=E.isIE,D=E.each;
E.create("tinymce.dom.Selection",{Selection:function(F,G,H){var I=this;
I.dom=F;
I.win=G;
I.serializer=H;
D(["onBeforeSetContent","onBeforeGetContent","onSetContent","onGetContent"],function(J){I[J]=new E.util.Dispatcher(I)
});
if(!I.win.getSelection){I.tridentSel=new E.dom.TridentSelection(I)
}E.addUnload(I.destroy,I)
},getContent:function(L){var M=this,K=M.getRng(),G=M.dom.create("body"),I=M.getSel(),J,H,F;
L=L||{};
J=H="";
L.get=true;
L.format=L.format||"html";
M.onBeforeGetContent.dispatch(M,L);
if(L.format=="text"){return M.isCollapsed()?"":(K.text||(I.toString?I.toString():""))
}if(K.cloneContents){F=K.cloneContents();
if(F){G.appendChild(F)
}}else{if(A(K.item)||A(K.htmlText)){G.innerHTML=K.item?K.item(0).outerHTML:K.htmlText
}else{G.innerHTML=K.toString()
}}if(/^\s/.test(G.innerHTML)){J=" "
}if(/\s+$/.test(G.innerHTML)){H=" "
}L.getInner=true;
L.content=M.isCollapsed()?"":J+M.serializer.serialize(G,L)+H;
M.onGetContent.dispatch(M,L);
return L.content
},setContent:function(I,J){var K=this,H=K.getRng(),F,G=K.win.document;
J=J||{format:"html"};
J.set=true;
I=J.content=K.dom.processHTML(I);
K.onBeforeSetContent.dispatch(K,J);
I=J.content;
if(H.insertNode){I+='<span id="__caret">_</span>';
H.deleteContents();
H.insertNode(K.getRng().createContextualFragment(I));
F=K.dom.get("__caret");
H=G.createRange();
H.setStartBefore(F);
H.setEndAfter(F);
K.setRng(H);
K.dom.remove("__caret")
}else{if(H.item){G.execCommand("Delete",false,null);
H=K.getRng()
}H.pasteHTML(I)
}K.onSetContent.dispatch(K,J)
},getStart:function(){var H=this,G=H.getRng(),F;
if(B){if(G.item){return G.item(0)
}G=G.duplicate();
G.collapse(1);
F=G.parentElement();
if(F&&F.nodeName=="BODY"){return F.firstChild
}return F
}else{F=G.startContainer;
if(F.nodeName=="BODY"){return F.firstChild
}return H.dom.getParent(F,"*")
}},getEnd:function(){var H=this,G=H.getRng(),F;
if(B){if(G.item){return G.item(0)
}G=G.duplicate();
G.collapse(0);
F=G.parentElement();
if(F&&F.nodeName=="BODY"){return F.lastChild
}return F
}else{F=G.endContainer;
if(F.nodeName=="BODY"){return F.lastChild
}return H.dom.getParent(F,"*")
}},getBookmark:function(H){var S=this,P=S.getRng(),W,O,Q,K=S.dom.getViewPort(S.win),J,M,F,N,I=-16777215,R,U=S.dom.getRoot(),V=0,T=0,G;
O=K.x;
Q=K.y;
if(H=="simple"){return{rng:P,scrollX:O,scrollY:Q}
}if(B){if(P.item){J=P.item(0);
D(S.dom.select(J.nodeName),function(X,Y){if(J==X){M=Y;
return false
}});
return{tag:J.nodeName,index:M,scrollX:O,scrollY:Q}
}W=S.dom.doc.body.createTextRange();
W.moveToElementText(U);
W.collapse(true);
F=Math.abs(W.move("character",I));
W=P.duplicate();
W.collapse(true);
M=Math.abs(W.move("character",I));
W=P.duplicate();
W.collapse(false);
N=Math.abs(W.move("character",I))-M;
return{start:M-F,length:N,scrollX:O,scrollY:Q}
}J=S.getNode();
R=S.getSel();
if(!R){return null
}if(J&&J.nodeName=="IMG"){return{scrollX:O,scrollY:Q}
}function L(X,b,Y){var Z=S.dom.doc.createTreeWalker(X,NodeFilter.SHOW_TEXT,null,false),a,d=0,c={};
while((a=Z.nextNode())!=null){if(a==b){c.start=d
}if(a==Y){c.end=d;
return c
}d+=C(a.nodeValue||"").length
}return null
}if(R.anchorNode==R.focusNode&&R.anchorOffset==R.focusOffset){J=L(U,R.anchorNode,R.focusNode);
if(!J){return{scrollX:O,scrollY:Q}
}C(R.anchorNode.nodeValue||"").replace(/^\s+/,function(X){V=X.length
});
return{start:Math.max(J.start+R.anchorOffset-V,0),end:Math.max(J.end+R.focusOffset-V,0),scrollX:O,scrollY:Q,beg:R.anchorOffset-V==0}
}else{J=L(U,P.startContainer,P.endContainer);
if(!J){return{scrollX:O,scrollY:Q}
}return{start:Math.max(J.start+P.startOffset-V,0),end:Math.max(J.end+P.endOffset-T,0),scrollX:O,scrollY:Q,beg:P.startOffset-V==0}
}},moveToBookmark:function(H){var G=this,O=G.getRng(),F=G.getSel(),L=G.dom.getRoot(),I,N,K;
function M(S,Z,Q){var T=G.dom.doc.createTreeWalker(S,NodeFilter.SHOW_TEXT,null,false),X,a=0,U={},Y,R,V,W;
while((X=T.nextNode())!=null){V=W=0;
K=X.nodeValue||"";
N=C(K).length;
a+=N;
if(a>=Z&&!U.startNode){Y=Z-(a-N);
if(H.beg&&Y>=N){continue
}U.startNode=X;
U.startOffset=Y+W
}if(a>=Q){U.endNode=X;
U.endOffset=Q-(a-N)+W;
return U
}}return null
}if(!H){return false
}G.win.scrollTo(H.scrollX,H.scrollY);
if(B){if(O=H.rng){try{O.select()
}catch(J){}return true
}G.win.focus();
if(H.tag){O=L.createControlRange();
D(G.dom.select(H.tag),function(Q,R){if(R==H.index){O.addElement(Q)
}})
}else{try{if(H.start<0){return true
}O=F.createRange();
O.moveToElementText(L);
O.collapse(true);
O.moveStart("character",H.start);
O.moveEnd("character",H.length)
}catch(P){return true
}}try{O.select()
}catch(J){}return true
}if(!F){return false
}if(H.rng){F.removeAllRanges();
F.addRange(H.rng)
}else{if(A(H.start)&&A(H.end)){try{I=M(L,H.start,H.end);
if(I){O=G.dom.doc.createRange();
O.setStart(I.startNode,I.startOffset);
O.setEnd(I.endNode,I.endOffset);
F.removeAllRanges();
F.addRange(O)
}if(!E.isOpera){G.win.focus()
}}catch(J){}}}},select:function(O,J){var G=this,P=G.getRng(),F=G.getSel(),H,I,K,L=G.win.document;
function N(Q,R){var S,T;
if(Q){S=L.createTreeWalker(Q,NodeFilter.SHOW_TEXT,null,false);
while(Q=S.nextNode()){T=Q;
if(E.trim(Q.nodeValue).length!=0){if(R){return Q
}else{T=Q
}}}}return T
}if(B){try{H=L.body;
if(/^(IMG|TABLE)$/.test(O.nodeName)){P=H.createControlRange();
P.addElement(O)
}else{P=H.createTextRange();
P.moveToElementText(O)
}P.select()
}catch(M){}}else{if(J){I=N(O,1)||G.dom.select("br:first",O)[0];
K=N(O,0)||G.dom.select("br:last",O)[0];
if(I&&K){P=L.createRange();
if(I.nodeName=="BR"){P.setStartBefore(I)
}else{P.setStart(I,0)
}if(K.nodeName=="BR"){P.setEndBefore(K)
}else{P.setEnd(K,K.nodeValue.length)
}}else{P.selectNode(O)
}}else{P.selectNode(O)
}G.setRng(P)
}return O
},isCollapsed:function(){var H=this,F=H.getRng(),G=H.getSel();
if(!F||F.item){return false
}return !G||F.boundingWidth==0||F.collapsed
},collapse:function(I){var H=this,G=H.getRng(),F;
if(G.item){F=G.item(0);
G=this.win.document.body.createTextRange();
G.moveToElementText(F)
}G.collapse(!!I);
H.setRng(G)
},getSel:function(){var F=this,G=this.win;
return G.getSelection?G.getSelection():G.document.selection
},getRng:function(F){var I=this,H,G;
if(F&&I.tridentSel){return I.tridentSel.getRangeAt(0)
}try{if(H=I.getSel()){G=H.rangeCount>0?H.getRangeAt(0):(H.createRange?H.createRange():I.win.document.createRange())
}}catch(J){}if(!G){G=B?I.win.document.body.createTextRange():I.win.document.createRange()
}return G
},setRng:function(F){var G,H=this;
if(!H.tridentSel){G=H.getSel();
if(G){G.removeAllRanges();
G.addRange(F)
}}else{if(F.cloneRange){H.tridentSel.addRange(F);
return 
}try{F.select()
}catch(I){}}},setNode:function(F){var G=this;
G.setContent(G.dom.getOuterHTML(F));
return F
},getNode:function(){var I=this,G=I.getRng(),H=I.getSel(),F;
if(!B){if(!G){return I.dom.getRoot()
}F=G.commonAncestorContainer;
if(!G.collapsed){if(E.isWebKit&&H.anchorNode&&H.anchorNode.nodeType==1){return H.anchorNode.childNodes[H.anchorOffset]
}if(G.startContainer==G.endContainer){if(G.startOffset-G.endOffset<2){if(G.startContainer.hasChildNodes()){F=G.startContainer.childNodes[G.startOffset]
}}}}return I.dom.getParent(F,"*")
}return G.item?G.item(0):G.parentElement()
},getSelectedBlocks:function(L,M){var J=this,I=J.dom,F,K,G,H=[];
F=I.getParent(L||J.getStart(),I.isBlock);
K=I.getParent(M||J.getEnd(),I.isBlock);
if(F){H.push(F)
}if(F&&K&&F!=K){G=F;
while((G=G.nextSibling)&&G!=K){if(I.isBlock(G)){H.push(G)
}}}if(K&&F!=K){H.push(K)
}return H
},destroy:function(F){var G=this;
G.win=null;
if(G.tridentSel){G.tridentSel.destroy()
}if(!F){E.removeUnload(G.destroy)
}}})
})(tinymce);
(function(A){A.create("tinymce.dom.XMLWriter",{node:null,XMLWriter:function(C){function B(){var D=document.implementation;
if(!D||!D.createDocument){try{return new ActiveXObject("MSXML2.DOMDocument")
}catch(E){}try{return new ActiveXObject("Microsoft.XmlDom")
}catch(E){}}else{return D.createDocument("","",null)
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
},writeCDATA:function(B){this.node.appendChild(this.doc.createCDATASection(B))
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
(function(A){A.create("tinymce.dom.StringWriter",{str:null,tags:null,count:0,settings:null,indent:null,StringWriter:function(B){this.settings=A.extend({indent_char:" ",indentation:0},B);
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
},writeAttribute:function(C,B){var D=this;
D.writeRaw(" "+D.encode(C)+'="'+D.encode(B)+'"')
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
(function(E){var C=E.extend,D=E.each,A=E.util.Dispatcher,F=E.isIE,B=E.isGecko;
function G(H){return H.replace(/([?+*])/g,".$1")
}E.create("tinymce.dom.Serializer",{Serializer:function(H){var I=this;
I.key=0;
I.onPreProcess=new A(I);
I.onPostProcess=new A(I);
try{I.writer=new E.dom.XMLWriter()
}catch(J){I.writer=new E.dom.StringWriter()
}I.settings=H=C({dom:E.DOM,valid_nodes:0,node_filter:0,attr_filter:0,invalid_attrs:/^(mce_|_moz_)/,closed:/^(br|hr|input|meta|img|link|param|area)$/,entity_encoding:"named",entities:"160,nbsp,161,iexcl,162,cent,163,pound,164,curren,165,yen,166,brvbar,167,sect,168,uml,169,copy,170,ordf,171,laquo,172,not,173,shy,174,reg,175,macr,176,deg,177,plusmn,178,sup2,179,sup3,180,acute,181,micro,182,para,183,middot,184,cedil,185,sup1,186,ordm,187,raquo,188,frac14,189,frac12,190,frac34,191,iquest,192,Agrave,193,Aacute,194,Acirc,195,Atilde,196,Auml,197,Aring,198,AElig,199,Ccedil,200,Egrave,201,Eacute,202,Ecirc,203,Euml,204,Igrave,205,Iacute,206,Icirc,207,Iuml,208,ETH,209,Ntilde,210,Ograve,211,Oacute,212,Ocirc,213,Otilde,214,Ouml,215,times,216,Oslash,217,Ugrave,218,Uacute,219,Ucirc,220,Uuml,221,Yacute,222,THORN,223,szlig,224,agrave,225,aacute,226,acirc,227,atilde,228,auml,229,aring,230,aelig,231,ccedil,232,egrave,233,eacute,234,ecirc,235,euml,236,igrave,237,iacute,238,icirc,239,iuml,240,eth,241,ntilde,242,ograve,243,oacute,244,ocirc,245,otilde,246,ouml,247,divide,248,oslash,249,ugrave,250,uacute,251,ucirc,252,uuml,253,yacute,254,thorn,255,yuml,402,fnof,913,Alpha,914,Beta,915,Gamma,916,Delta,917,Epsilon,918,Zeta,919,Eta,920,Theta,921,Iota,922,Kappa,923,Lambda,924,Mu,925,Nu,926,Xi,927,Omicron,928,Pi,929,Rho,931,Sigma,932,Tau,933,Upsilon,934,Phi,935,Chi,936,Psi,937,Omega,945,alpha,946,beta,947,gamma,948,delta,949,epsilon,950,zeta,951,eta,952,theta,953,iota,954,kappa,955,lambda,956,mu,957,nu,958,xi,959,omicron,960,pi,961,rho,962,sigmaf,963,sigma,964,tau,965,upsilon,966,phi,967,chi,968,psi,969,omega,977,thetasym,978,upsih,982,piv,8226,bull,8230,hellip,8242,prime,8243,Prime,8254,oline,8260,frasl,8472,weierp,8465,image,8476,real,8482,trade,8501,alefsym,8592,larr,8593,uarr,8594,rarr,8595,darr,8596,harr,8629,crarr,8656,lArr,8657,uArr,8658,rArr,8659,dArr,8660,hArr,8704,forall,8706,part,8707,exist,8709,empty,8711,nabla,8712,isin,8713,notin,8715,ni,8719,prod,8721,sum,8722,minus,8727,lowast,8730,radic,8733,prop,8734,infin,8736,ang,8743,and,8744,or,8745,cap,8746,cup,8747,int,8756,there4,8764,sim,8773,cong,8776,asymp,8800,ne,8801,equiv,8804,le,8805,ge,8834,sub,8835,sup,8836,nsub,8838,sube,8839,supe,8853,oplus,8855,otimes,8869,perp,8901,sdot,8968,lceil,8969,rceil,8970,lfloor,8971,rfloor,9001,lang,9002,rang,9674,loz,9824,spades,9827,clubs,9829,hearts,9830,diams,338,OElig,339,oelig,352,Scaron,353,scaron,376,Yuml,710,circ,732,tilde,8194,ensp,8195,emsp,8201,thinsp,8204,zwnj,8205,zwj,8206,lrm,8207,rlm,8211,ndash,8212,mdash,8216,lsquo,8217,rsquo,8218,sbquo,8220,ldquo,8221,rdquo,8222,bdquo,8224,dagger,8225,Dagger,8240,permil,8249,lsaquo,8250,rsaquo,8364,euro",bool_attrs:/(checked|disabled|readonly|selected|nowrap)/,valid_elements:"*[*]",extended_valid_elements:0,valid_child_elements:0,invalid_elements:0,fix_table_elements:1,fix_list_elements:true,fix_content_duplication:true,convert_fonts_to_spans:false,font_size_classes:0,font_size_style_values:0,apply_source_formatting:0,indent_mode:"simple",indent_char:"\t",indent_levels:1,remove_linebreaks:1,remove_redundant_brs:1,element_format:"xhtml"},H);
I.dom=H.dom;
if(H.remove_redundant_brs){I.onPostProcess.add(function(L,K){K.content=K.content.replace(/(<br \/>\s*)+<\/(p|h[1-6]|div|li)>/gi,function(O,M,N){if(/^<br \/>\s*<\//.test(O)){return"</"+N+">"
}return O
})
})
}if(H.element_format=="html"){I.onPostProcess.add(function(L,K){K.content=K.content.replace(/<([^>]+) \/>/g,"<$1>")
})
}if(H.fix_list_elements){I.onPreProcess.add(function(R,U){var M,P,Q=["ol","ul"],S,T,K,N=/^(OL|UL)$/,O;
function L(W,V){var Y=V.split(","),X;
while((W=W.previousSibling)!=null){for(X=0;
X<Y.length;
X++){if(W.nodeName==Y[X]){return W
}}}return null
}for(P=0;
P<Q.length;
P++){M=I.dom.select(Q[P],U.node);
for(S=0;
S<M.length;
S++){T=M[S];
K=T.parentNode;
if(N.test(K.nodeName)){O=L(T,"LI");
if(!O){O=I.dom.create("li");
O.innerHTML="&nbsp;";
O.appendChild(T);
K.insertBefore(O,K.firstChild)
}else{O.appendChild(T)
}}}}})
}if(H.fix_table_elements){I.onPreProcess.add(function(L,K){D(I.dom.select("p table",K.node),function(M){I.dom.split(I.dom.getParent(M,"p"),M)
})
})
}},setEntities:function(L){var N=this,J,H,K={},M="",I;
if(N.entityLookup){return 
}J=L.split(",");
for(H=0;
H<J.length;
H+=2){I=J[H];
if(I==34||I==38||I==60||I==62){continue
}K[String.fromCharCode(J[H])]=J[H+1];
I=parseInt(J[H]).toString(16);
M+="\\u"+"0000".substring(I.length)+I
}if(!M){N.settings.entity_encoding="raw";
return 
}N.entitiesRE=new RegExp("["+M+"]","g");
N.entityLookup=K
},setValidChildRules:function(H){this.childRules=null;
this.addValidChildRules(H)
},addValidChildRules:function(I){var J=this,H,L,K;
if(!I){return 
}H="A|BR|SPAN|BDO|MAP|OBJECT|IMG|TT|I|B|BIG|SMALL|EM|STRONG|DFN|CODE|Q|SAMP|KBD|VAR|CITE|ABBR|ACRONYM|SUB|SUP|#text|#comment";
L="A|BR|SPAN|BDO|OBJECT|APPLET|IMG|MAP|IFRAME|TT|I|B|U|S|STRIKE|BIG|SMALL|FONT|BASEFONT|EM|STRONG|DFN|CODE|Q|SAMP|KBD|VAR|CITE|ABBR|ACRONYM|SUB|SUP|INPUT|SELECT|TEXTAREA|LABEL|BUTTON|#text|#comment";
K="H[1-6]|P|DIV|ADDRESS|PRE|FORM|TABLE|LI|OL|UL|TD|CAPTION|BLOCKQUOTE|CENTER|DL|DT|DD|DIR|FIELDSET|FORM|NOSCRIPT|NOFRAMES|MENU|ISINDEX|SAMP";
D(I.split(","),function(O){var N=O.split(/\[|\]/),M;
O="";
D(N[1].split("|"),function(P){if(O){O+="|"
}switch(P){case"%itrans":P=L;
break;
case"%itrans_na":P=L.substring(2);
break;
case"%istrict":P=H;
break;
case"%istrict_na":P=H.substring(2);
break;
case"%btrans":P=K;
break;
case"%bstrict":P=K;
break
}O+=P
});
M=new RegExp("^("+O.toLowerCase()+")$","i");
D(N[0].split("/"),function(P){J.childRules=J.childRules||{};
J.childRules[P]=M
})
});
I="";
D(J.childRules,function(N,M){if(I){I+="|"
}I+=M
});
J.parentElementsRE=new RegExp("^("+I.toLowerCase()+")$","i")
},setRules:function(H){var I=this;
I._setup();
I.rules={};
I.wildRules=[];
I.validElements={};
return I.addRules(H)
},addRules:function(I){var J=this,H;
if(!I){return 
}J._setup();
D(I.split(","),function(K){var O=K.split(/\[|\]/),L=O[0].split("/"),N,M,P,Q=[];
if(H){M=E.extend([],H.attribs)
}if(O.length>1){D(O[1].split("|"),function(R){var T={},S;
M=M||[];
R=R.replace(/::/g,"~");
R=/^([!\-])?([\w*.?~_\-]+|)([=:<])?(.+)?$/.exec(R);
R[2]=R[2].replace(/~/g,":");
if(R[1]=="!"){N=N||[];
N.push(R[2])
}if(R[1]=="-"){for(S=0;
S<M.length;
S++){if(M[S].name==R[2]){M.splice(S,1);
return 
}}}switch(R[3]){case"=":T.defaultVal=R[4]||"";
break;
case":":T.forcedVal=R[4];
break;
case"<":T.validVals=R[4].split("?");
break
}if(/[*.?]/.test(R[2])){P=P||[];
T.nameRE=new RegExp("^"+G(R[2])+"$");
P.push(T)
}else{T.name=R[2];
M.push(T)
}Q.push(R[2])
})
}D(L,function(S,T){var R=S.charAt(0),U=1,V={};
if(H){if(H.noEmpty){V.noEmpty=H.noEmpty
}if(H.fullEnd){V.fullEnd=H.fullEnd
}if(H.padd){V.padd=H.padd
}}switch(R){case"-":V.noEmpty=true;
break;
case"+":V.fullEnd=true;
break;
case"#":V.padd=true;
break;
default:U=0
}L[T]=S=S.substring(U);
J.validElements[S]=1;
if(/[*.?]/.test(L[0])){V.nameRE=new RegExp("^"+G(L[0])+"$");
J.wildRules=J.wildRules||{};
J.wildRules.push(V)
}else{V.name=L[0];
if(L[0]=="@"){H=V
}J.rules[S]=V
}V.attribs=M;
if(N){V.requiredAttribs=N
}if(P){S="";
D(Q,function(W){if(S){S+="|"
}S+="("+G(W)+")"
});
V.validAttribsRE=new RegExp("^"+S.toLowerCase()+"$");
V.wildAttribs=P
}})
});
I="";
D(J.validElements,function(K,L){if(I){I+="|"
}if(L!="@"){I+=L
}});
J.validElementsRE=new RegExp("^("+G(I.toLowerCase())+")$")
},findRule:function(H){var K=this,I=K.rules,L,J;
K._setup();
J=I[H];
if(J){return J
}I=K.wildRules;
for(L=0;
L<I.length;
L++){if(I[L].nameRE.test(H)){return I[L]
}}return null
},findAttribRule:function(K,H){var J,I=K.wildAttribs;
for(J=0;
J<I.length;
J++){if(I[J].nameRE.test(H)){return I[J]
}}return null
},serialize:function(H,I){var J,K=this;
K._setup();
I=I||{};
I.format=I.format||"html";
K.processObj=I;
H=H.cloneNode(true);
K.key=""+(parseInt(K.key)+1);
if(!I.no_events){I.node=H;
K.onPreProcess.dispatch(K,I)
}K.writer.reset();
K._serializeNode(H,I.getInner);
I.content=K.writer.getContent();
if(!I.no_events){K.onPostProcess.dispatch(K,I)
}K._postProcess(I);
I.node=null;
return E.trim(I.content)
},_postProcess:function(M){var L=this,J=L.settings,K=M.content,H=[],I;
if(M.format=="html"){I=L._protect({content:K,patterns:[{pattern:/(<script[^>]*>)(.*?)(<\/script>)/g},{pattern:/(<style[^>]*>)(.*?)(<\/style>)/g},{pattern:/(<pre[^>]*>)(.*?)(<\/pre>)/g,encode:1},{pattern:/(<!--\[CDATA\[)(.*?)(\]\]-->)/g}]});
K=I.content;
if(J.entity_encoding!=="raw"){K=L._encode(K)
}if(!M.set){K=K.replace(/<p>\s+<\/p>|<p([^>]+)>\s+<\/p>/g,J.entity_encoding=="numeric"?"<p$1>&#160;</p>":"<p$1>&nbsp;</p>");
if(J.remove_linebreaks){K=K.replace(/\r?\n|\r/g," ");
K=K.replace(/(<[^>]+>)\s+/g,"$1 ");
K=K.replace(/\s+(<\/[^>]+>)/g," $1");
K=K.replace(/<(p|h[1-6]|blockquote|hr|div|table|tbody|tr|td|body|head|html|title|meta|style|pre|script|link|object) ([^>]+)>\s+/g,"<$1 $2>");
K=K.replace(/<(p|h[1-6]|blockquote|hr|div|table|tbody|tr|td|body|head|html|title|meta|style|pre|script|link|object)>\s+/g,"<$1>");
K=K.replace(/\s+<\/(p|h[1-6]|blockquote|hr|div|table|tbody|tr|td|body|head|html|title|meta|style|pre|script|link|object)>/g,"</$1>")
}if(J.apply_source_formatting&&J.indent_mode=="simple"){K=K.replace(/<(\/?)(ul|hr|table|meta|link|tbody|tr|object|body|head|html|map)(|[^>]+)>\s*/g,"\n<$1$2$3>\n");
K=K.replace(/\s*<(p|h[1-6]|blockquote|div|title|style|pre|script|td|li|area)(|[^>]+)>/g,"\n<$1$2>");
K=K.replace(/<\/(p|h[1-6]|blockquote|div|title|style|pre|script|td|li)>\s*/g,"</$1>\n");
K=K.replace(/\n\n/g,"\n")
}}K=L._unprotect(K,I);
K=K.replace(/<!--\[CDATA\[([\s\S]+)\]\]-->/g,"<![CDATA[$1]]>");
K=K.replace(/(type|language)=\"mce-/g,'$1="');
if(J.entity_encoding=="raw"){K=K.replace(/<p>&nbsp;<\/p>|<p([^>]+)>&nbsp;<\/p>/g,"<p$1>\u00a0</p>")
}}M.content=K
},_serializeNode:function(O,V){var H=this,U=H.settings,J=H.writer,S,Y,M,L,N,K,R,Z,I,X,Q,P,T,W;
if(!U.node_filter||U.node_filter(O)){switch(O.nodeType){case 1:if(O.hasAttribute?O.hasAttribute("mce_bogus"):O.getAttribute("mce_bogus")){return 
}T=false;
S=O.hasChildNodes();
X=O.getAttribute("mce_name")||O.nodeName.toLowerCase();
if(F){if(O.scopeName!=="HTML"&&O.scopeName!=="html"){X=O.scopeName+":"+X
}}if(X.indexOf("mce:")===0){X=X.substring(4)
}if(!H.validElementsRE||!H.validElementsRE.test(X)||(H.invalidElementsRE&&H.invalidElementsRE.test(X))||V){T=true;
break
}if(F){if(U.fix_content_duplication){if(O.mce_serialized==H.key){return 
}O.mce_serialized=H.key
}if(X.charAt(0)=="/"){X=X.substring(1)
}}else{if(B){if(O.nodeName==="BR"&&O.getAttribute("type")=="_moz"){return 
}}}if(H.childRules){if(H.parentElementsRE.test(H.elementName)){if(!H.childRules[H.elementName].test(X)){T=true;
break
}}H.elementName=X
}Q=H.findRule(X);
X=Q.name||X;
W=U.closed.test(X);
if((!S&&Q.noEmpty)||(F&&!X)){T=true;
break
}if(Q.requiredAttribs){K=Q.requiredAttribs;
for(L=K.length-1;
L>=0;
L--){if(this.dom.getAttrib(O,K[L])!==""){break
}}if(L==-1){T=true;
break
}}J.writeStartElement(X);
if(Q.attribs){for(L=0,R=Q.attribs,N=R.length;
L<N;
L++){K=R[L];
I=H._getAttrib(O,K);
if(I!==null){J.writeAttribute(K.name,I)
}}}if(Q.validAttribsRE){R=H.dom.getAttribs(O);
for(L=R.length-1;
L>-1;
L--){Z=R[L];
if(Z.specified){K=Z.nodeName.toLowerCase();
if(U.invalid_attrs.test(K)||!Q.validAttribsRE.test(K)){continue
}P=H.findAttribRule(Q,K);
I=H._getAttrib(O,P,K);
if(I!==null){J.writeAttribute(K,I)
}}}}if(X==="script"&&E.trim(O.innerHTML)){J.writeText("// ");
J.writeCDATA(O.innerHTML.replace(/<!--|-->|<\[CDATA\[|\]\]>/g,""));
S=false;
break
}if(Q.padd){if(S&&(M=O.firstChild)&&M.nodeType===1&&O.childNodes.length===1){if(M.hasAttribute?M.hasAttribute("mce_bogus"):M.getAttribute("mce_bogus")){J.writeText("\u00a0")
}}else{if(!S){J.writeText("\u00a0")
}}}break;
case 3:if(H.childRules&&H.parentElementsRE.test(H.elementName)){if(!H.childRules[H.elementName].test(O.nodeName)){return 
}}return J.writeText(O.nodeValue);
case 4:return J.writeCDATA(O.nodeValue);
case 8:return J.writeComment(O.nodeValue)
}}else{if(O.nodeType==1){S=O.hasChildNodes()
}}if(S&&!W){M=O.firstChild;
while(M){H._serializeNode(M);
H.elementName=X;
M=M.nextSibling
}}if(!T){if(!W){J.writeFullEndElement()
}else{J.writeEndElement()
}}},_protect:function(I){var J=this;
I.items=I.items||[];
function K(L){return L.replace(/[\r\n\\]/g,function(M){if(M==="\n"){return"\\n"
}else{if(M==="\\"){return"\\\\"
}}return"\\r"
})
}function H(L){return L.replace(/\\[\\rn]/g,function(M){if(M==="\\n"){return"\n"
}else{if(M==="\\\\"){return"\\"
}}return"\r"
})
}D(I.patterns,function(L){I.content=H(K(I.content).replace(L.pattern,function(P,O,M,N){M=H(M);
if(L.encode){M=J._encode(M)
}I.items.push(M);
return O+"<!--mce:"+(I.items.length-1)+"-->"+N
}))
});
return I
},_unprotect:function(I,H){I=I.replace(/\<!--mce:([0-9]+)--\>/g,function(J,K){return H.items[parseInt(K)]
});
H.items=[];
return I
},_encode:function(H){var J=this,I=J.settings,K;
if(I.entity_encoding!=="raw"){if(I.entity_encoding.indexOf("named")!=-1){J.setEntities(I.entities);
K=J.entityLookup;
H=H.replace(J.entitiesRE,function(M){var L;
if(L=K[M]){M="&"+L+";"
}return M
})
}if(I.entity_encoding.indexOf("numeric")!=-1){H=H.replace(/[\u007E-\uFFFF]/g,function(L){return"&#"+L.charCodeAt(0)+";"
})
}}return H
},_setup:function(){var I=this,H=this.settings;
if(I.done){return 
}I.done=1;
I.setRules(H.valid_elements);
I.addRules(H.extended_valid_elements);
I.addValidChildRules(H.valid_child_elements);
if(H.invalid_elements){I.invalidElementsRE=new RegExp("^("+G(H.invalid_elements.replace(/,/g,"|").toLowerCase())+")$")
}if(H.attrib_value_filter){I.attribValueFilter=H.attribValueFilter
}},_getAttrib:function(H,K,L){var I,J;
L=L||K.name;
if(K.forcedVal&&(J=K.forcedVal)){if(J==="{$uid}"){return this.dom.uniqueId()
}return J
}J=this.dom.getAttrib(H,L);
if(this.settings.bool_attrs.test(L)&&J){J=(""+J).toLowerCase();
if(J==="false"||J==="0"){return null
}J=L
}switch(L){case"rowspan":case"colspan":if(J=="1"){J=""
}break
}if(this.attribValueFilter){J=this.attribValueFilter(L,J,H)
}if(K.validVals){for(I=K.validVals.length-1;
I>=0;
I--){if(J==K.validVals[I]){break
}}if(I==-1){return null
}}if(J===""&&typeof (K.defaultVal)!="undefined"){J=K.defaultVal;
if(J==="{$uid}"){return this.dom.uniqueId()
}return J
}else{if(L=="class"&&this.processObj.get){J=J.replace(/\s?mceItem\w+\s?/g,"")
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
(function(C){var A=C.DOM,B=C.is;
C.create("tinymce.ui.Control",{Control:function(D,E){this.id=D;
this.settings=E=E||{};
this.rendered=false;
this.onRender=new C.util.Dispatcher(this);
this.classPrefix="";
this.scope=E.scope||this;
this.disabled=0;
this.active=0
},setDisabled:function(E){var D;
if(E!=this.disabled){D=A.get(this.id);
if(D&&this.settings.unavailable_prefix){if(E){this.prevTitle=D.title;
D.title=this.settings.unavailable_prefix+": "+D.title
}else{D.title=this.prevTitle
}}this.setState("Disabled",E);
this.setState("Enabled",!E);
this.disabled=E
}},isDisabled:function(){return this.disabled
},setActive:function(D){if(D!=this.active){this.setState("Active",D);
this.active=D
}},isActive:function(){return this.active
},setState:function(D,F){var E=A.get(this.id);
D=this.classPrefix+D;
if(F){A.addClass(E,D)
}else{A.removeClass(E,D)
}},isRendered:function(){return this.rendered
},renderHTML:function(){},renderTo:function(D){A.setHTML(D,this.renderHTML())
},postRender:function(){var D=this,E;
if(B(D.disabled)){E=D.disabled;
D.disabled=-1;
D.setDisabled(E)
}if(B(D.active)){E=D.active;
D.active=-1;
D.setActive(E)
}},remove:function(){A.remove(this.id);
this.destroy()
},destroy:function(){C.dom.Event.clear(this.id)
}})
})(tinymce);
tinymce.create("tinymce.ui.Container:tinymce.ui.Control",{Container:function(A,B){this.parent(A,B);
this.controls=[];
this.lookup={}
},add:function(A){this.lookup[A.id]=A;
this.controls.push(A);
return A
},get:function(A){return this.lookup[A]
}});
tinymce.create("tinymce.ui.Separator:tinymce.ui.Control",{Separator:function(A,B){this.parent(A,B);
this.classPrefix="mceSeparator"
},renderHTML:function(){return tinymce.DOM.createHTML("span",{"class":this.classPrefix})
}});
(function(D){var E=D.is,A=D.DOM,C=D.each,B=D.walk;
D.create("tinymce.ui.MenuItem:tinymce.ui.Control",{MenuItem:function(F,G){this.parent(F,G);
this.classPrefix="mceMenuItem"
},setSelected:function(F){this.setState("Selected",F);
this.selected=F
},isSelected:function(){return this.selected
},postRender:function(){var F=this;
F.parent();
if(E(F.selected)){F.setSelected(F.selected)
}}})
})(tinymce);
(function(D){var E=D.is,A=D.DOM,C=D.each,B=D.walk;
D.create("tinymce.ui.Menu:tinymce.ui.MenuItem",{Menu:function(F,G){var H=this;
H.parent(F,G);
H.items={};
H.collapsed=false;
H.menuCount=0;
H.onAddItem=new D.util.Dispatcher(this)
},expand:function(F){var G=this;
if(F){B(G,function(H){if(H.expand){H.expand()
}},"items",G)
}G.collapsed=false
},collapse:function(F){var G=this;
if(F){B(G,function(H){if(H.collapse){H.collapse()
}},"items",G)
}G.collapsed=true
},isCollapsed:function(){return this.collapsed
},add:function(F){if(!F.settings){F=new D.ui.MenuItem(F.id||A.uniqueId(),F)
}this.onAddItem.dispatch(this,F);
return this.items[F.id]=F
},addSeparator:function(){return this.add({separator:true})
},addMenu:function(F){if(!F.collapse){F=this.createMenu(F)
}this.menuCount++;
return this.add(F)
},hasMenus:function(){return this.menuCount!==0
},remove:function(F){delete this.items[F.id]
},removeAll:function(){var F=this;
B(F,function(G){if(G.removeAll){G.removeAll()
}else{G.remove()
}G.destroy()
},"items",F);
F.items={}
},createMenu:function(F){var G=new D.ui.Menu(F.id||A.uniqueId(),F);
G.onAddItem.add(this.onAddItem.dispatch,this.onAddItem);
return G
}})
})(tinymce);
(function(D){var E=D.is,F=D.DOM,C=D.each,B=D.dom.Event,A=D.dom.Element;
D.create("tinymce.ui.DropMenu:tinymce.ui.Menu",{DropMenu:function(G,H){H=H||{};
H.container=H.container||F.doc.body;
H.offset_x=H.offset_x||0;
H.offset_y=H.offset_y||0;
H.vp_offset_x=H.vp_offset_x||0;
H.vp_offset_y=H.vp_offset_y||0;
if(E(H.icons)&&!H.icons){H["class"]+=" mceNoIcons"
}this.parent(G,H);
this.onShowMenu=new D.util.Dispatcher(this);
this.onHideMenu=new D.util.Dispatcher(this);
this.classPrefix="mceMenu"
},createMenu:function(G){var I=this,H=I.settings,J;
G.container=G.container||H.container;
G.parent=I;
G.constrain=G.constrain||H.constrain;
G["class"]=G["class"]||H["class"];
G.vp_offset_x=G.vp_offset_x||H.vp_offset_x;
G.vp_offset_y=G.vp_offset_y||H.vp_offset_y;
J=new D.ui.DropMenu(G.id||F.uniqueId(),G);
J.onAddItem.add(I.onAddItem.dispatch,I.onAddItem);
return J
},update:function(){var J=this,I=J.settings,L=F.get("menu_"+J.id+"_tbl"),G=F.get("menu_"+J.id+"_co"),K,H;
K=I.max_width?Math.min(L.clientWidth,I.max_width):L.clientWidth;
H=I.max_height?Math.min(L.clientHeight,I.max_height):L.clientHeight;
if(!F.boxModel){J.element.setStyles({width:K+2,height:H+2})
}else{J.element.setStyles({width:K,height:H})
}if(I.max_width){F.setStyle(G,"width",K)
}if(I.max_height){F.setStyle(G,"height",H);
if(L.clientHeight<I.max_height){F.setStyle(G,"overflow","hidden")
}}},showMenu:function(I,L,G){var S=this,K=S.settings,J,R=F.getViewPort(),U,N,T,H,Q=2,O,P,M=S.classPrefix;
S.collapse(1);
if(S.isMenuVisible){return 
}if(!S.rendered){J=F.add(S.settings.container,S.renderNode());
C(S.items,function(V){V.postRender()
});
S.element=new A("menu_"+S.id,{blocker:1,container:K.container})
}else{J=F.get("menu_"+S.id)
}if(!D.isOpera){F.setStyles(J,{left:-65535,top:-65535})
}F.show(J);
S.update();
I+=K.offset_x||0;
L+=K.offset_y||0;
R.w-=4;
R.h-=4;
if(K.constrain){U=J.clientWidth-Q;
N=J.clientHeight-Q;
T=R.x+R.w;
H=R.y+R.h;
if((I+K.vp_offset_x+U)>T){I=G?G-U:Math.max(0,(T-K.vp_offset_x)-U)
}if((L+K.vp_offset_y+N)>H){L=Math.max(0,(H-K.vp_offset_y)-N)
}}F.setStyles(J,{left:I,top:L});
S.element.update();
S.isMenuVisible=1;
S.mouseClickFunc=B.add(J,"click",function(W){var V;
W=W.target;
if(W&&(W=F.getParent(W,"tr"))&&!F.hasClass(W,M+"ItemSub")){V=S.items[W.id];
if(V.isDisabled()){return 
}O=S;
while(O){if(O.hideMenu){O.hideMenu()
}O=O.settings.parent
}if(V.settings.onclick){V.settings.onclick(W)
}return B.cancel(W)
}});
if(S.hasMenus()){S.mouseOverFunc=B.add(J,"mouseover",function(V){var Y,W,X;
V=V.target;
if(V&&(V=F.getParent(V,"tr"))){Y=S.items[V.id];
if(S.lastMenu){S.lastMenu.collapse(1)
}if(Y.isDisabled()){return 
}if(V&&F.hasClass(V,M+"ItemSub")){W=F.getRect(V);
Y.showMenu((W.x+W.w-Q),W.y-Q,W.x);
S.lastMenu=Y;
F.addClass(F.get(Y.id).firstChild,M+"ItemActive")
}}})
}S.onShowMenu.dispatch(S);
if(K.keyboard_focus){B.add(J,"keydown",S._keyHandler,S);
F.select("a","menu_"+S.id)[0].focus();
S._focusIdx=0
}},hideMenu:function(G){var J=this,H=F.get("menu_"+J.id),I;
if(!J.isMenuVisible){return 
}B.remove(H,"mouseover",J.mouseOverFunc);
B.remove(H,"click",J.mouseClickFunc);
B.remove(H,"keydown",J._keyHandler);
F.hide(H);
J.isMenuVisible=0;
if(!G){J.collapse(1)
}if(J.element){J.element.hide()
}if(I=F.get(J.id)){F.removeClass(I.firstChild,J.classPrefix+"ItemActive")
}J.onHideMenu.dispatch(J)
},add:function(G){var I=this,H;
G=I.parent(G);
if(I.isRendered&&(H=F.get("menu_"+I.id))){I._add(F.select("tbody",H)[0],G)
}return G
},collapse:function(G){this.parent(G);
this.hideMenu(1)
},remove:function(G){F.remove(G.id);
this.destroy();
return this.parent(G)
},destroy:function(){var H=this,G=F.get("menu_"+H.id);
B.remove(G,"mouseover",H.mouseOverFunc);
B.remove(G,"click",H.mouseClickFunc);
if(H.element){H.element.remove()
}F.remove(G)
},renderNode:function(){var J=this,I=J.settings,G,K,H,L;
L=F.create("div",{id:"menu_"+J.id,"class":I["class"],style:"position:absolute;left:0;top:0;z-index:200000"});
H=F.add(L,"div",{id:"menu_"+J.id+"_co","class":J.classPrefix+(I["class"]?" "+I["class"]:"")});
J.element=new A("menu_"+J.id,{blocker:1,container:I.container});
if(I.menu_line){F.add(H,"span",{"class":J.classPrefix+"Line"})
}G=F.add(H,"table",{id:"menu_"+J.id+"_tbl",border:0,cellPadding:0,cellSpacing:0});
K=F.add(G,"tbody");
C(J.items,function(M){J._add(K,M)
});
J.rendered=true;
return L
},_keyHandler:function(G){var H=this,I=G.keyCode;
function J(K){var M=H._focusIdx+K,L=F.select("a","menu_"+H.id)[M];
if(L){H._focusIdx=M;
L.focus()
}}switch(I){case 38:J(-1);
return ;
case 40:J(1);
return ;
case 13:return ;
case 27:return this.hideMenu()
}},_add:function(L,N){var M,G=N.settings,H,J,K,I=this.classPrefix,O;
if(G.separator){J=F.add(L,"tr",{id:N.id,"class":I+"ItemSeparator"});
F.add(J,"td",{"class":I+"ItemSeparator"});
if(M=J.previousSibling){F.addClass(M,"mceLast")
}return 
}M=J=F.add(L,"tr",{id:N.id,"class":I+"Item "+I+"ItemEnabled"});
M=K=F.add(M,"td");
M=H=F.add(M,"a",{href:"javascript:;",onclick:"return false;",onmousedown:"return false;"});
F.addClass(K,G["class"]);
O=F.add(M,"span",{"class":"mceIcon"+(G.icon?" mce_"+G.icon:"")});
if(G.icon_src){F.add(O,"img",{src:G.icon_src})
}M=F.add(M,G.element||"span",{"class":"mceText",title:N.settings.title},N.settings.title);
if(N.settings.style){F.setAttrib(M,"style",N.settings.style)
}if(L.childNodes.length==1){F.addClass(J,"mceFirst")
}if((M=J.previousSibling)&&F.hasClass(M,I+"ItemSeparator")){F.addClass(J,"mceFirst")
}if(N.collapse){F.addClass(J,I+"ItemSub")
}if(M=J.previousSibling){F.removeClass(M,"mceLast")
}F.addClass(J,"mceLast")
}})
})(tinymce);
(function(A){var B=A.DOM;
A.create("tinymce.ui.Button:tinymce.ui.Control",{Button:function(C,D){this.parent(C,D);
this.classPrefix="mceButton"
},renderHTML:function(){var C=this.classPrefix,D=this.settings,E,F;
F=B.encode(D.label||"");
E='<a id="'+this.id+'" href="javascript:;" class="'+C+" "+C+"Enabled "+D["class"]+(F?" "+C+"Labeled":"")+'" onmousedown="return false;" onclick="return false;" title="'+B.encode(D.title)+'">';
if(D.image){E+='<img class="mceIcon" src="'+D.image+'" />'+F+"</a>"
}else{E+='<span class="mceIcon '+D["class"]+'"></span>'+(F?'<span class="'+C+'Label">'+F+"</span>":"")+"</a>"
}return E
},postRender:function(){var D=this,C=D.settings;
A.dom.Event.add(D.id,"click",function(E){if(!D.isDisabled()){return C.onclick.call(C.scope,E)
}})
}})
})(tinymce);
(function(D){var E=D.DOM,A=D.dom.Event,C=D.each,B=D.util.Dispatcher;
D.create("tinymce.ui.ListBox:tinymce.ui.Control",{ListBox:function(F,G){var H=this;
H.parent(F,G);
H.items=[];
H.onChange=new B(H);
H.onPostRender=new B(H);
H.onAdd=new B(H);
H.onRenderMenu=new D.util.Dispatcher(this);
H.classPrefix="mceListBox"
},select:function(H){var I=this,F,G;
if(H==undefined){return I.selectByIndex(-1)
}if(H&&H.call){G=H
}else{G=function(J){return J==H
}
}if(H!=I.selectedValue){C(I.items,function(J,K){if(G(J.value)){F=1;
I.selectByIndex(K);
return false
}});
if(!F){I.selectByIndex(-1)
}}},selectByIndex:function(I){var H=this,G,F;
if(I!=H.selectedIndex){G=E.get(H.id+"_text");
F=H.items[I];
if(F){H.selectedValue=F.value;
H.selectedIndex=I;
E.setHTML(G,E.encode(F.title));
E.removeClass(G,"mceTitle")
}else{E.setHTML(G,E.encode(H.settings.title));
E.addClass(G,"mceTitle");
H.selectedValue=H.selectedIndex=null
}G=0
}},add:function(F,I,G){var H=this;
G=G||{};
G=D.extend(G,{title:F,value:I});
H.items.push(G);
H.onAdd.dispatch(H,G)
},getLength:function(){return this.items.length
},renderHTML:function(){var G="",I=this,H=I.settings,F=I.classPrefix;
G='<table id="'+I.id+'" cellpadding="0" cellspacing="0" class="'+F+" "+F+"Enabled"+(H["class"]?(" "+H["class"]):"")+'"><tbody><tr>';
G+="<td>"+E.createHTML("a",{id:I.id+"_text",href:"javascript:;","class":"mceText",onclick:"return false;",onmousedown:"return false;"},E.encode(I.settings.title))+"</td>";
G+="<td>"+E.createHTML("a",{id:I.id+"_open",tabindex:-1,href:"javascript:;","class":"mceOpen",onclick:"return false;",onmousedown:"return false;"},"<span></span>")+"</td>";
G+="</tr></tbody></table>";
return G
},showMenu:function(){var I=this,F,G,H=E.get(this.id),J;
if(I.isDisabled()||I.items.length==0){return 
}if(I.menu&&I.menu.isMenuVisible){return I.hideMenu()
}if(!I.isMenuRendered){I.renderMenu();
I.isMenuRendered=true
}F=E.getPos(this.settings.menu_container);
G=E.getPos(H);
J=I.menu;
J.settings.offset_x=G.x;
J.settings.offset_y=G.y;
J.settings.keyboard_focus=!D.isOpera;
if(I.oldID){J.items[I.oldID].setSelected(0)
}C(I.items,function(K){if(K.value===I.selectedValue){J.items[K.id].setSelected(1);
I.oldID=K.id
}});
J.showMenu(0,H.clientHeight);
A.add(E.doc,"mousedown",I.hideMenu,I);
E.addClass(I.id,I.classPrefix+"Selected")
},hideMenu:function(F){var G=this;
if(F&&F.type=="mousedown"&&(F.target.id==G.id+"_text"||F.target.id==G.id+"_open")){return 
}if(!F||!E.getParent(F.target,".mceMenu")){E.removeClass(G.id,G.classPrefix+"Selected");
A.remove(E.doc,"mousedown",G.hideMenu,G);
if(G.menu){G.menu.hideMenu()
}}},renderMenu:function(){var F=this,G;
G=F.settings.control_manager.createDropMenu(F.id+"_menu",{menu_line:1,"class":F.classPrefix+"Menu mceNoIcons",max_width:150,max_height:150});
G.onHideMenu.add(F.hideMenu,F);
G.add({title:F.settings.title,"class":"mceMenuItemTitle",onclick:function(){if(F.settings.onselect("")!==false){F.select("")
}}});
C(F.items,function(H){H.id=E.uniqueId();
H.onclick=function(){if(F.settings.onselect(H.value)!==false){F.select(H.value)
}};
G.add(H)
});
F.onRenderMenu.dispatch(F,G);
F.menu=G
},postRender:function(){var G=this,F=G.classPrefix;
A.add(G.id,"click",G.showMenu,G);
A.add(G.id+"_text","focus",function(H){if(!G._focused){G.keyDownHandler=A.add(G.id+"_text","keydown",function(I){var L=-1,K,J=I.keyCode;
C(G.items,function(M,N){if(G.selectedValue==M.value){L=N
}});
if(J==38){K=G.items[L-1]
}else{if(J==40){K=G.items[L+1]
}else{if(J==13){K=G.selectedValue;
G.selectedValue=null;
G.settings.onselect(K);
return A.cancel(I)
}}}if(K){G.hideMenu();
G.select(K.value)
}})
}G._focused=1
});
A.add(G.id+"_text","blur",function(){A.remove(G.id+"_text","keydown",G.keyDownHandler);
G._focused=0
});
if(D.isIE6||!E.boxModel){A.add(G.id,"mouseover",function(){if(!E.hasClass(G.id,F+"Disabled")){E.addClass(G.id,F+"Hover")
}});
A.add(G.id,"mouseout",function(){if(!E.hasClass(G.id,F+"Disabled")){E.removeClass(G.id,F+"Hover")
}})
}G.onPostRender.dispatch(G,E.get(G.id))
},destroy:function(){this.parent();
A.clear(this.id+"_text")
}})
})(tinymce);
(function(D){var E=D.DOM,A=D.dom.Event,C=D.each,B=D.util.Dispatcher;
D.create("tinymce.ui.NativeListBox:tinymce.ui.ListBox",{NativeListBox:function(F,G){this.parent(F,G);
this.classPrefix="mceNativeListBox"
},setDisabled:function(F){E.get(this.id).disabled=F
},isDisabled:function(){return E.get(this.id).disabled
},select:function(H){var I=this,F,G;
if(H==undefined){return I.selectByIndex(-1)
}if(H&&H.call){G=H
}else{G=function(J){return J==H
}
}if(H!=I.selectedValue){C(I.items,function(J,K){if(G(J.value)){F=1;
I.selectByIndex(K);
return false
}});
if(!F){I.selectByIndex(-1)
}}},selectByIndex:function(F){E.get(this.id).selectedIndex=F+1;
this.selectedValue=this.items[F]?this.items[F].value:null
},add:function(F,I,J){var G,H=this;
J=J||{};
J.value=I;
if(H.isRendered()){E.add(E.get(this.id),"option",J,F)
}G={title:F,value:I,attribs:J};
H.items.push(G);
H.onAdd.dispatch(H,G)
},getLength:function(){return E.get(this.id).options.length-1
},renderHTML:function(){var F,G=this;
F=E.createHTML("option",{value:""},"-- "+G.settings.title+" --");
C(G.items,function(H){F+=E.createHTML("option",{value:H.value},H.title)
});
F=E.createHTML("select",{id:G.id,"class":"mceNativeListBox"},F);
return F
},postRender:function(){var G=this,F;
G.rendered=true;
function H(I){var J=G.items[I.target.selectedIndex-1];
if(J&&(J=J.value)){G.onChange.dispatch(G,J);
if(G.settings.onselect){G.settings.onselect(J)
}}}A.add(G.id,"change",H);
A.add(G.id,"keydown",function(I){var J;
A.remove(G.id,"change",F);
J=A.add(G.id,"blur",function(){A.add(G.id,"change",H);
A.remove(G.id,"blur",J)
});
if(I.keyCode==13||I.keyCode==32){H(I);
return A.cancel(I)
}});
G.onPostRender.dispatch(G,E.get(G.id))
}})
})(tinymce);
(function(D){var A=D.DOM,B=D.dom.Event,C=D.each;
D.create("tinymce.ui.MenuButton:tinymce.ui.Button",{MenuButton:function(E,F){this.parent(E,F);
this.onRenderMenu=new D.util.Dispatcher(this);
F.menu_container=F.menu_container||A.doc.body
},showMenu:function(){var H=this,E,F,G=A.get(H.id),I;
if(H.isDisabled()){return 
}if(!H.isMenuRendered){H.renderMenu();
H.isMenuRendered=true
}if(H.isMenuVisible){return H.hideMenu()
}E=A.getPos(H.settings.menu_container);
F=A.getPos(G);
I=H.menu;
I.settings.offset_x=F.x;
I.settings.offset_y=F.y;
I.settings.vp_offset_x=F.x;
I.settings.vp_offset_y=F.y;
I.settings.keyboard_focus=H._focused;
I.showMenu(0,G.clientHeight);
B.add(A.doc,"mousedown",H.hideMenu,H);
H.setState("Selected",1);
H.isMenuVisible=1
},renderMenu:function(){var E=this,F;
F=E.settings.control_manager.createDropMenu(E.id+"_menu",{menu_line:1,"class":this.classPrefix+"Menu",icons:E.settings.icons});
F.onHideMenu.add(E.hideMenu,E);
E.onRenderMenu.dispatch(E,F);
E.menu=F
},hideMenu:function(E){var F=this;
if(E&&E.type=="mousedown"&&A.getParent(E.target,function(G){return G.id===F.id||G.id===F.id+"_open"
})){return 
}if(!E||!A.getParent(E.target,".mceMenu")){F.setState("Selected",0);
B.remove(A.doc,"mousedown",F.hideMenu,F);
if(F.menu){F.menu.hideMenu()
}}F.isMenuVisible=0
},postRender:function(){var F=this,E=F.settings;
B.add(F.id,"click",function(){if(!F.isDisabled()){if(E.onclick){E.onclick(F.value)
}F.showMenu()
}})
}})
})(tinymce);
(function(D){var A=D.DOM,B=D.dom.Event,C=D.each;
D.create("tinymce.ui.SplitButton:tinymce.ui.MenuButton",{SplitButton:function(E,F){this.parent(E,F);
this.classPrefix="mceSplitButton"
},renderHTML:function(){var E,G=this,F=G.settings,H;
E="<tbody><tr>";
if(F.image){H=A.createHTML("img ",{src:F.image,"class":"mceAction "+F["class"]})
}else{H=A.createHTML("span",{"class":"mceAction "+F["class"]},"")
}E+="<td>"+A.createHTML("a",{id:G.id+"_action",href:"javascript:;","class":"mceAction "+F["class"],onclick:"return false;",onmousedown:"return false;",title:F.title},H)+"</td>";
H=A.createHTML("span",{"class":"mceOpen "+F["class"]});
E+="<td>"+A.createHTML("a",{id:G.id+"_open",href:"javascript:;","class":"mceOpen "+F["class"],onclick:"return false;",onmousedown:"return false;",title:F.title},H)+"</td>";
E+="</tr></tbody>";
return A.createHTML("table",{id:G.id,"class":"mceSplitButton mceSplitButtonEnabled "+F["class"],cellpadding:"0",cellspacing:"0",onmousedown:"return false;",title:F.title},E)
},postRender:function(){var F=this,E=F.settings;
if(E.onclick){B.add(F.id+"_action","click",function(){if(!F.isDisabled()){E.onclick(F.value)
}})
}B.add(F.id+"_open","click",F.showMenu,F);
B.add(F.id+"_open","focus",function(){F._focused=1
});
B.add(F.id+"_open","blur",function(){F._focused=0
});
if(D.isIE6||!A.boxModel){B.add(F.id,"mouseover",function(){if(!A.hasClass(F.id,"mceSplitButtonDisabled")){A.addClass(F.id,"mceSplitButtonHover")
}});
B.add(F.id,"mouseout",function(){if(!A.hasClass(F.id,"mceSplitButtonDisabled")){A.removeClass(F.id,"mceSplitButtonHover")
}})
}},destroy:function(){this.parent();
B.clear(this.id+"_action");
B.clear(this.id+"_open")
}})
})(tinymce);
(function(D){var E=D.DOM,B=D.dom.Event,A=D.is,C=D.each;
D.create("tinymce.ui.ColorSplitButton:tinymce.ui.SplitButton",{ColorSplitButton:function(F,G){var H=this;
H.parent(F,G);
H.settings=G=D.extend({colors:"000000,993300,333300,003300,003366,000080,333399,333333,800000,FF6600,808000,008000,008080,0000FF,666699,808080,FF0000,FF9900,99CC00,339966,33CCCC,3366FF,800080,999999,FF00FF,FFCC00,FFFF00,00FF00,00FFFF,00CCFF,993366,C0C0C0,FF99CC,FFCC99,FFFF99,CCFFCC,CCFFFF,99CCFF,CC99FF,FFFFFF",grid_width:8,default_color:"#888888"},H.settings);
H.onShowMenu=new D.util.Dispatcher(H);
H.onHideMenu=new D.util.Dispatcher(H);
H.value=G.default_color
},showMenu:function(){var J=this,I,F,G,H;
if(J.isDisabled()){return 
}if(!J.isMenuRendered){J.renderMenu();
J.isMenuRendered=true
}if(J.isMenuVisible){return J.hideMenu()
}G=E.get(J.id);
E.show(J.id+"_menu");
E.addClass(G,"mceSplitButtonSelected");
H=E.getPos(G);
E.setStyles(J.id+"_menu",{left:H.x,top:H.y+G.clientHeight,zIndex:200000});
G=0;
B.add(E.doc,"mousedown",J.hideMenu,J);
J.onShowMenu.dispatch(J);
if(J._focused){J._keyHandler=B.add(J.id+"_menu","keydown",function(K){if(K.keyCode==27){J.hideMenu()
}});
E.select("a",J.id+"_menu")[0].focus()
}J.isMenuVisible=1
},hideMenu:function(F){var G=this;
if(F&&F.type=="mousedown"&&E.getParent(F.target,function(H){return H.id===G.id+"_open"
})){return 
}if(!F||!E.getParent(F.target,".mceSplitButtonMenu")){E.removeClass(G.id,"mceSplitButtonSelected");
B.remove(E.doc,"mousedown",G.hideMenu,G);
B.remove(G.id+"_menu","keydown",G._keyHandler);
E.hide(G.id+"_menu")
}G.onHideMenu.dispatch(G);
G.isMenuVisible=0
},renderMenu:function(){var G=this,K,H=0,F=G.settings,L,I,M,J;
J=E.add(F.menu_container,"div",{id:G.id+"_menu","class":F.menu_class+" "+F["class"],style:"position:absolute;left:0;top:-1000px;"});
K=E.add(J,"div",{"class":F["class"]+" mceSplitButtonMenu"});
E.add(K,"span",{"class":"mceMenuLine"});
L=E.add(K,"table",{"class":"mceColorSplitMenu"});
I=E.add(L,"tbody");
H=0;
C(A(F.colors,"array")?F.colors:F.colors.split(","),function(N){N=N.replace(/^#/,"");
if(!H--){M=E.add(I,"tr");
H=F.grid_width-1
}L=E.add(M,"td");
L=E.add(L,"a",{href:"javascript:;",style:{backgroundColor:"#"+N},mce_color:"#"+N})
});
if(F.more_colors_func){L=E.add(I,"tr");
L=E.add(L,"td",{colspan:F.grid_width,"class":"mceMoreColors"});
L=E.add(L,"a",{id:G.id+"_more",href:"javascript:;",onclick:"return false;","class":"mceMoreColors"},F.more_colors_title);
B.add(L,"click",function(N){F.more_colors_func.call(F.more_colors_scope||this);
return B.cancel(N)
})
}E.addClass(K,"mceColorSplitMenu");
B.add(G.id+"_menu","click",function(O){var N;
O=O.target;
if(O.nodeName=="A"&&(N=O.getAttribute("mce_color"))){G.setColor(N)
}return B.cancel(O)
});
return J
},setColor:function(F){var G=this;
E.setStyle(G.id+"_preview","backgroundColor",F);
G.value=F;
G.hideMenu();
G.settings.onselect(F)
},postRender:function(){var G=this,F=G.id;
G.parent();
E.add(F+"_action","div",{id:F+"_preview","class":"mceColorPreview"});
E.setStyle(G.id+"_preview","backgroundColor",G.value)
},destroy:function(){this.parent();
B.clear(this.id+"_menu");
B.clear(this.id+"_more");
E.remove(this.id+"_menu")
}})
})(tinymce);
tinymce.create("tinymce.ui.Toolbar:tinymce.ui.Container",{renderHTML:function(){var B=this,G="",E,D,I=tinymce.DOM,A=B.settings,H,J,F,C;
C=B.controls;
for(H=0;
H<C.length;
H++){D=C[H];
J=C[H-1];
F=C[H+1];
if(H===0){E="mceToolbarStart";
if(D.Button){E+=" mceToolbarStartButton"
}else{if(D.SplitButton){E+=" mceToolbarStartSplitButton"
}else{if(D.ListBox){E+=" mceToolbarStartListBox"
}}}G+=I.createHTML("td",{"class":E},I.createHTML("span",null,"<!-- IE -->"))
}if(J&&D.ListBox){if(J.Button||J.SplitButton){G+=I.createHTML("td",{"class":"mceToolbarEnd"},I.createHTML("span",null,"<!-- IE -->"))
}}if(I.stdMode){G+='<td style="position: relative">'+D.renderHTML()+"</td>"
}else{G+="<td>"+D.renderHTML()+"</td>"
}if(F&&D.ListBox){if(F.Button||F.SplitButton){G+=I.createHTML("td",{"class":"mceToolbarStart"},I.createHTML("span",null,"<!-- IE -->"))
}}}E="mceToolbarEnd";
if(D.Button){E+=" mceToolbarEndButton"
}else{if(D.SplitButton){E+=" mceToolbarEndSplitButton"
}else{if(D.ListBox){E+=" mceToolbarEndListBox"
}}}G+=I.createHTML("td",{"class":E},I.createHTML("span",null,"<!-- IE -->"));
return I.createHTML("table",{id:B.id,"class":"mceToolbar"+(A["class"]?" "+A["class"]:""),cellpadding:"0",cellspacing:"0",align:B.settings.align||""},"<tbody><tr>"+G+"</tr></tbody>")
}});
(function(A){var B=A.util.Dispatcher,C=A.each;
A.create("tinymce.AddOnManager",{items:[],urls:{},lookup:{},onAdd:new B(this),get:function(D){return this.lookup[D]
},requireLangPack:function(D){var F,E=A.EditorManager.settings;
if(E&&E.language){F=this.urls[D]+"/langs/"+E.language+".js";
if(!A.dom.Event.domLoaded&&!E.strict_mode){A.ScriptLoader.load(F)
}else{A.ScriptLoader.add(F)
}}},add:function(D,E){this.items.push(E);
this.lookup[D]=E;
this.onAdd.dispatch(this,D,E);
return E
},load:function(D,G,H,E){var F=this;
if(F.urls[D]){return 
}if(G.indexOf("/")!=0&&G.indexOf("://")==-1){G=A.baseURL+"/"+G
}F.urls[D]=G.substring(0,G.lastIndexOf("/"));
A.ScriptLoader.add(G,H,E)
}});
A.PluginManager=new A.AddOnManager();
A.ThemeManager=new A.AddOnManager()
}(tinymce));
(function(E){var D=E.each,C=E.extend,F=E.DOM,B=E.dom.Event,H=E.ThemeManager,A=E.PluginManager,G=E.explode;
E.create("static tinymce.EditorManager",{editors:{},i18n:{},activeEditor:null,preInit:function(){var J=this,I=window.location;
E.documentBaseURL=I.href.replace(/[\?#].*$/,"").replace(/[\/\\][^\/]+$/,"");
if(!/[\/\\]$/.test(E.documentBaseURL)){E.documentBaseURL+="/"
}E.baseURL=new E.util.URI(E.documentBaseURL).toAbsolute(E.baseURL);
E.EditorManager.baseURI=new E.util.URI(E.baseURL);
if(document.domain&&I.hostname!=document.domain){E.relaxedDomain=document.domain
}J.onBeforeUnload=new E.util.Dispatcher(J);
B.add(window,"beforeunload",function(K){J.onBeforeUnload.dispatch(J,K)
})
},init:function(I){var J=this,N,O=E.ScriptLoader,K,L,Q=[],M;
function P(S,R,U){var T=S[R];
if(!T){return 
}if(E.is(T,"string")){U=T.replace(/\.\w+$/,"");
U=U?E.resolve(U):0;
T=E.resolve(T)
}return T.apply(U||this,Array.prototype.slice.call(arguments,2))
}I=C({theme:"simple",language:"en",strict_loading_mode:document.contentType=="application/xhtml+xml"},I);
J.settings=I;
if(!B.domLoaded&&!I.strict_loading_mode){if(I.language){O.add(E.baseURL+"/langs/"+I.language+".js")
}if(I.theme&&I.theme.charAt(0)!="-"&&!H.urls[I.theme]){H.load(I.theme,"themes/"+I.theme+"/editor_template"+E.suffix+".js")
}if(I.plugins){N=G(I.plugins);
if(E.inArray(N,"compat2x")!=-1){A.load("compat2x","plugins/compat2x/editor_plugin"+E.suffix+".js")
}D(N,function(R){if(R&&R.charAt(0)!="-"&&!A.urls[R]){if(!E.isWebKit&&R=="safari"){return 
}A.load(R,"plugins/"+R+"/editor_plugin"+E.suffix+".js")
}})
}O.loadQueue()
}B.add(document,"init",function(){var T,R;
P(I,"onpageload");
if(I.browsers){T=false;
D(G(I.browsers),function(U){switch(U){case"ie":case"msie":if(E.isIE){T=true
}break;
case"gecko":if(E.isGecko){T=true
}break;
case"safari":case"webkit":if(E.isWebKit){T=true
}break;
case"opera":if(E.isOpera){T=true
}break
}});
if(!T){return 
}}switch(I.mode){case"exact":T=I.elements||"";
if(T.length>0){D(G(T),function(U){if(F.get(U)){M=new E.Editor(U,I);
Q.push(M);
M.render(1)
}else{K=0;
D(document.forms,function(V){D(V.elements,function(W){if(W.name===U){U="mce_editor_"+K;
F.setAttrib(W,"id",U);
M=new E.Editor(U,I);
Q.push(M);
M.render(1)
}})
})
}})
}break;
case"textareas":case"specific_textareas":function S(U,V){return V.constructor===RegExp?V.test(U.className):F.hasClass(U,V)
}D(F.select("textarea"),function(U){if(I.editor_deselector&&S(U,I.editor_deselector)){return 
}if(!I.editor_selector||S(U,I.editor_selector)){L=F.get(U.name);
if(!U.id&&!L){U.id=U.name
}if(!U.id||J.get(U.id)){U.id=F.uniqueId()
}M=new E.Editor(U.id,I);
Q.push(M);
M.render(1)
}});
break
}if(I.oninit){T=R=0;
D(Q,function(U){R++;
if(!U.initialized){U.onInit.add(function(){T++;
if(T==R){P(I,"oninit")
}})
}else{T++
}if(T==R){P(I,"oninit")
}})
}})
},get:function(I){return this.editors[I]
},getInstanceById:function(I){return this.get(I)
},add:function(I){this.editors[I.id]=I;
this._setActive(I);
return I
},remove:function(I){var J=this;
if(!J.editors[I.id]){return null
}delete J.editors[I.id];
if(J.activeEditor==I){D(J.editors,function(K){J._setActive(K);
return false
})
}I.destroy();
return I
},execCommand:function(N,I,J){var O=this,K=O.get(J),M;
switch(N){case"mceFocus":K.focus();
return true;
case"mceAddEditor":case"mceAddControl":if(!O.get(J)){new E.Editor(J,O.settings).render()
}return true;
case"mceAddFrameControl":M=J.window;
M.tinyMCE=tinyMCE;
M.tinymce=E;
E.DOM.doc=M.document;
E.DOM.win=M;
K=new E.Editor(J.element_id,J);
K.render();
if(E.isIE){function L(){K.destroy();
M.detachEvent("onunload",L);
M=M.tinyMCE=M.tinymce=null
}M.attachEvent("onunload",L)
}J.page_window=null;
return true;
case"mceRemoveEditor":case"mceRemoveControl":if(K){K.remove()
}return true;
case"mceToggleEditor":if(!K){O.execCommand("mceAddControl",0,J);
return true
}if(K.isHidden()){K.show()
}else{K.hide()
}return true
}if(O.activeEditor){return O.activeEditor.execCommand(N,I,J)
}return false
},execInstanceCommand:function(I,J,K,L){var M=this.get(I);
if(M){return M.execCommand(J,K,L)
}return false
},triggerSave:function(){D(this.editors,function(I){I.save()
})
},addI18n:function(J,I){var L,K=this.i18n;
if(!E.is(J,"string")){D(J,function(N,M){D(N,function(O,P){D(O,function(Q,R){if(P==="common"){K[M+"."+R]=Q
}else{K[M+"."+P+"."+R]=Q
}})
})
})
}else{D(I,function(N,M){K[J+"."+M]=N
})
}},_setActive:function(I){this.selectedInstance=this.activeEditor=I
}});
E.EditorManager.preInit()
})(tinymce);
var tinyMCE=window.tinyMCE=tinymce.EditorManager;
(function(C){var B=C.DOM,F=C.dom.Event,K=C.extend,E=C.util.Dispatcher;
var G=C.each,P=C.isGecko,O=C.isIE,L=C.isWebKit;
var M=C.is,I=C.ThemeManager,N=C.PluginManager,H=C.EditorManager;
var A=C.inArray,D=C.grep,J=C.explode;
C.create("tinymce.Editor",{Editor:function(Q,R){var S=this;
S.id=S.editorId=Q;
S.execCommands={};
S.queryStateCommands={};
S.queryValueCommands={};
S.plugins={};
G(["onPreInit","onBeforeRenderUI","onPostRender","onInit","onRemove","onActivate","onDeactivate","onClick","onEvent","onMouseUp","onMouseDown","onDblClick","onKeyDown","onKeyUp","onKeyPress","onContextMenu","onSubmit","onReset","onPaste","onPreProcess","onPostProcess","onBeforeSetContent","onBeforeGetContent","onSetContent","onGetContent","onLoadContent","onSaveContent","onNodeChange","onChange","onBeforeExecCommand","onExecCommand","onUndo","onRedo","onVisualAid","onSetProgressState"],function(T){S[T]=new E(S)
});
S.settings=R=K({id:Q,language:"en",docs_language:"en",theme:"simple",skin:"default",delta_width:0,delta_height:0,popup_css:"",plugins:"",document_base_url:C.documentBaseURL,add_form_submit_trigger:1,submit_patch:1,add_unload_trigger:1,convert_urls:1,relative_urls:1,remove_script_host:1,table_inline_editing:0,object_resizing:1,cleanup:1,accessibility_focus:1,custom_shortcuts:1,custom_undo_redo_keyboard_shortcuts:1,custom_undo_redo_restore_selection:1,custom_undo_redo:1,doctype:'<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">',visual_table_class:"mceItemTable",visual:1,inline_styles:true,convert_fonts_to_spans:true,font_size_style_values:"xx-small,x-small,small,medium,large,x-large,xx-large",apply_source_formatting:1,directionality:"ltr",forced_root_block:"p",valid_elements:"@[id|class|style|title|dir<ltr?rtl|lang|xml::lang|onclick|ondblclick|onmousedown|onmouseup|onmouseover|onmousemove|onmouseout|onkeypress|onkeydown|onkeyup],a[rel|rev|charset|hreflang|tabindex|accesskey|type|name|href|target|title|class|onfocus|onblur],strong/b,em/i,strike,u,#p[align],-ol[type|compact],-ul[type|compact],-li,br,img[longdesc|usemap|src|border|alt=|title|hspace|vspace|width|height|align],-sub,-sup,-blockquote[cite],-table[border=0|cellspacing|cellpadding|width|frame|rules|height|align|summary|bgcolor|background|bordercolor],-tr[rowspan|width|height|align|valign|bgcolor|background|bordercolor],tbody,thead,tfoot,#td[colspan|rowspan|width|height|align|valign|bgcolor|background|bordercolor|scope],#th[colspan|rowspan|width|height|align|valign|scope],caption,-div,-span,-code,-pre,address,-h1,-h2,-h3,-h4,-h5,-h6,hr[size|noshade],-font[face|size|color],dd,dl,dt,cite,abbr,acronym,del[datetime|cite],ins[datetime|cite],object[classid|width|height|codebase|*],param[name|value],embed[type|width|height|src|*],script[src|type],map[name],area[shape|coords|href|alt|target],bdo,button,col[align|char|charoff|span|valign|width],colgroup[align|char|charoff|span|valign|width],dfn,fieldset,form[action|accept|accept-charset|enctype|method],input[accept|alt|checked|disabled|maxlength|name|readonly|size|src|type|value|tabindex|accesskey],kbd,label[for],legend,noscript,optgroup[label|disabled],option[disabled|label|selected|value],q[cite],samp,select[disabled|multiple|name|size],small,textarea[cols|rows|disabled|name|readonly],tt,var,big",hidden_input:1,padd_empty_editor:1,render_ui:1,init_theme:1,force_p_newlines:1,indentation:"30px",keep_styles:1,fix_table_elements:1,removeformat_selector:"span,b,strong,em,i,font,u,strike"},R);
S.documentBaseURI=new C.util.URI(R.document_base_url||C.documentBaseURL,{base_uri:tinyMCE.baseURI});
S.baseURI=H.baseURI;
S.execCallback("setup",S)
},render:function(T){var S=this,R=S.settings,Q=S.id,V=C.ScriptLoader;
if(!F.domLoaded){F.add(document,"init",function(){S.render()
});
return 
}if(!T){R.strict_loading_mode=1;
tinyMCE.settings=R
}if(!S.getElement()){return 
}if(R.strict_loading_mode){V.settings.strict_mode=R.strict_loading_mode;
C.DOM.settings.strict=1
}if(!/TEXTAREA|INPUT/i.test(S.getElement().nodeName)&&R.hidden_input&&B.getParent(Q,"form")){B.insertAfter(B.create("input",{type:"hidden",name:Q}),Q)
}if(C.WindowManager){S.windowManager=new C.WindowManager(S)
}if(R.encoding=="xml"){S.onGetContent.add(function(X,W){if(W.save){W.content=B.encode(W.content)
}})
}if(R.add_form_submit_trigger){S.onSubmit.addToTop(function(){if(S.initialized){S.save();
S.isNotDirty=1
}})
}if(R.add_unload_trigger){S._beforeUnload=tinyMCE.onBeforeUnload.add(function(){if(S.initialized&&!S.destroyed&&!S.isHidden()){S.save({format:"raw",no_events:true})
}})
}C.addUnload(S.destroy,S);
if(R.submit_patch){S.onBeforeRenderUI.add(function(){var W=S.getElement().form;
if(!W){return 
}if(W._mceOldSubmit){return 
}if(!W.submit.nodeType&&!W.submit.length){S.formElement=W;
W._mceOldSubmit=W.submit;
W.submit=function(){H.triggerSave();
S.isNotDirty=1;
return S.formElement._mceOldSubmit(S.formElement)
}
}W=null
})
}function U(){if(R.language){V.add(C.baseURL+"/langs/"+R.language+".js")
}if(R.theme&&R.theme.charAt(0)!="-"&&!I.urls[R.theme]){I.load(R.theme,"themes/"+R.theme+"/editor_template"+C.suffix+".js")
}G(J(R.plugins),function(W){if(W&&W.charAt(0)!="-"&&!N.urls[W]){if(!L&&W=="safari"){return 
}N.load(W,"plugins/"+W+"/editor_plugin"+C.suffix+".js")
}});
V.loadQueue(function(){if(!S.removed){S.init()
}})
}if(R.plugins.indexOf("compat2x")!=-1){N.load("compat2x","plugins/compat2x/editor_plugin"+C.suffix+".js");
V.loadQueue(U)
}else{U()
}},init:function(){var Z,b=this,a=b.settings,S,W,U=b.getElement(),R,T,Q,X,V,c;
H.add(b);
if(a.theme){a.theme=a.theme.replace(/-/,"");
R=I.get(a.theme);
b.theme=new R();
if(b.theme.init&&a.init_theme){b.theme.init(b,I.urls[a.theme]||C.documentBaseURL.replace(/\/$/,""))
}}G(J(a.plugins.replace(/\-/g,"")),function(d){var f=N.get(d),e=N.urls[d]||C.documentBaseURL.replace(/\/$/,""),g;
if(f){g=new f(b,e);
b.plugins[d]=g;
if(g.init){g.init(b,e)
}}});
if(a.popup_css!==false){if(a.popup_css){a.popup_css=b.documentBaseURI.toAbsolute(a.popup_css)
}else{a.popup_css=b.baseURI.toAbsolute("themes/"+a.theme+"/skins/"+a.skin+"/dialog.css")
}}if(a.popup_css_add){a.popup_css+=","+b.documentBaseURI.toAbsolute(a.popup_css_add)
}b.controlManager=new C.ControlManager(b);
b.undoManager=new C.UndoManager(b);
b.undoManager.onAdd.add(function(d,e){if(!e.initial){return b.onChange.dispatch(b,e,d)
}});
b.undoManager.onUndo.add(function(d,e){return b.onUndo.dispatch(b,e,d)
});
b.undoManager.onRedo.add(function(d,e){return b.onRedo.dispatch(b,e,d)
});
if(a.custom_undo_redo){b.onExecCommand.add(function(g,d,e,f,h){if(d!="Undo"&&d!="Redo"&&d!="mceRepaint"&&(!h||!h.skip_undo)){b.undoManager.add()
}})
}b.onExecCommand.add(function(e,d){if(!/^(FontName|FontSize)$/.test(d)){b.nodeChanged()
}});
if(P){function Y(e,d){if(!d||!d.initial){b.execCommand("mceRepaint")
}}b.onUndo.add(Y);
b.onRedo.add(Y);
b.onSetContent.add(Y)
}b.onBeforeRenderUI.dispatch(b,b.controlManager);
if(a.render_ui){S=a.width||U.style.width||U.offsetWidth;
W=a.height||U.style.height||U.offsetHeight;
b.orgDisplay=U.style.display;
c=/^[0-9\.]+(|px)$/i;
if(c.test(""+S)){S=Math.max(parseInt(S)+(R.deltaWidth||0),100)
}if(c.test(""+W)){W=Math.max(parseInt(W)+(R.deltaHeight||0),100)
}R=b.theme.renderUI({targetNode:U,width:S,height:W,deltaWidth:a.delta_width,deltaHeight:a.delta_height});
b.editorContainer=R.editorContainer
}B.setStyles(R.sizeContainer||R.editorContainer,{width:S,height:W});
W=(R.iframeHeight||W)+(typeof (W)=="number"?(R.deltaHeight||0):"");
if(W<100){W=100
}b.iframeHTML=a.doctype+'<html><head xmlns="http://www.w3.org/1999/xhtml"><base href="'+b.documentBaseURI.getURI()+'" />';
b.iframeHTML+='<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />';
if(C.relaxedDomain){b.iframeHTML+='<script type="text/javascript">document.domain = "'+C.relaxedDomain+'";<\/script>'
}X=a.body_id||"tinymce";
if(X.indexOf("=")!=-1){X=b.getParam("body_id","","hash");
X=X[b.id]||X
}V=a.body_class||"";
if(V.indexOf("=")!=-1){V=b.getParam("body_class","","hash");
V=V[b.id]||""
}b.iframeHTML+='</head><body id="'+X+'" class="mceContentBody '+V+'"></body></html>';
if(C.relaxedDomain){if(O||(C.isOpera&&parseFloat(opera.version())>=9.5)){Q='javascript:(function(){document.open();document.domain="'+document.domain+'";var ed = window.parent.tinyMCE.get("'+b.id+'");document.write(ed.iframeHTML);document.close();ed.setupIframe();})()'
}else{if(C.isOpera){Q='javascript:(function(){document.open();document.domain="'+document.domain+'";document.close();ed.setupIframe();})()'
}}}Z=B.add(R.iframeContainer,"iframe",{id:b.id+"_ifr",src:Q||'javascript:""',frameBorder:"0",style:{width:"100%",height:W}});
b.contentAreaContainer=R.iframeContainer;
B.get(R.editorContainer).style.display=b.orgDisplay;
B.get(b.id).style.display="none";
if(!O||!C.relaxedDomain){b.setupIframe()
}U=Z=R=null
},setupIframe:function(){var T=this,S=T.settings,Y=B.get(T.id),X=T.getDoc(),Q,V;
if(!O||!C.relaxedDomain){X.open();
X.write(T.iframeHTML);
X.close()
}if(!O){try{if(!S.readonly){X.designMode="On"
}}catch(W){}}if(O){V=T.getBody();
B.hide(V);
if(!S.readonly){V.contentEditable=true
}B.show(V)
}T.dom=new C.DOM.DOMUtils(T.getDoc(),{keep_values:true,url_converter:T.convertURL,url_converter_scope:T,hex_colors:S.force_hex_style_colors,class_filter:S.class_filter,update_styles:1,fix_ie_paragraphs:1});
T.serializer=new C.dom.Serializer({entity_encoding:S.entity_encoding,entities:S.entities,valid_elements:S.verify_html===false?"*[*]":S.valid_elements,extended_valid_elements:S.extended_valid_elements,valid_child_elements:S.valid_child_elements,invalid_elements:S.invalid_elements,fix_table_elements:S.fix_table_elements,fix_list_elements:S.fix_list_elements,fix_content_duplication:S.fix_content_duplication,convert_fonts_to_spans:S.convert_fonts_to_spans,font_size_classes:S.font_size_classes,font_size_style_values:S.font_size_style_values,apply_source_formatting:S.apply_source_formatting,remove_linebreaks:S.remove_linebreaks,element_format:S.element_format,dom:T.dom});
T.selection=new C.dom.Selection(T.dom,T.getWin(),T.serializer);
T.forceBlocks=new C.ForceBlocks(T,{forced_root_block:S.forced_root_block});
T.editorCommands=new C.EditorCommands(T);
T.serializer.onPreProcess.add(function(a,Z){return T.onPreProcess.dispatch(T,Z,a)
});
T.serializer.onPostProcess.add(function(a,Z){return T.onPostProcess.dispatch(T,Z,a)
});
T.onPreInit.dispatch(T);
if(!S.gecko_spellcheck){T.getBody().spellcheck=0
}if(!S.readonly){T._addEvents()
}T.controlManager.onPostRender.dispatch(T,T.controlManager);
T.onPostRender.dispatch(T);
if(S.directionality){T.getBody().dir=S.directionality
}if(S.nowrap){T.getBody().style.whiteSpace="nowrap"
}if(S.auto_resize){T.onNodeChange.add(T.resizeToContent,T)
}if(S.custom_elements){function U(a,Z){G(J(S.custom_elements),function(c){var b;
if(c.indexOf("~")===0){c=c.substring(1);
b="span"
}else{b="div"
}Z.content=Z.content.replace(new RegExp("<("+c+")([^>]*)>","g"),"<"+b+' mce_name="$1"$2>');
Z.content=Z.content.replace(new RegExp("</("+c+")>","g"),"</"+b+">")
})
}T.onBeforeSetContent.add(U);
T.onPostProcess.add(function(a,Z){if(Z.set){U(a,Z)
}})
}if(S.handle_node_change_callback){T.onNodeChange.add(function(Z,a,b){T.execCallback("handle_node_change_callback",T.id,b,-1,-1,true,T.selection.isCollapsed())
})
}if(S.save_callback){T.onSaveContent.add(function(a,b){var Z=T.execCallback("save_callback",T.id,b.content,T.getBody());
if(Z){b.content=Z
}})
}if(S.onchange_callback){T.onChange.add(function(Z,a){T.execCallback("onchange_callback",T,a)
})
}if(S.convert_newlines_to_brs){T.onBeforeSetContent.add(function(a,Z){if(Z.initial){Z.content=Z.content.replace(/\r?\n/g,"<br />")
}})
}if(S.fix_nesting&&O){T.onBeforeSetContent.add(function(a,Z){Z.content=T._fixNesting(Z.content)
})
}if(S.preformatted){T.onPostProcess.add(function(a,Z){Z.content=Z.content.replace(/^\s*<pre.*?>/,"");
Z.content=Z.content.replace(/<\/pre>\s*$/,"");
if(Z.set){Z.content='<pre class="mceItemHidden">'+Z.content+"</pre>"
}})
}if(S.verify_css_classes){T.serializer.attribValueFilter=function(a,c){var b,Z;
if(a=="class"){if(!T.classesRE){Z=T.dom.getClasses();
if(Z.length>0){b="";
G(Z,function(d){b+=(b?"|":"")+d["class"]
});
T.classesRE=new RegExp("("+b+")","gi")
}}return !T.classesRE||/(\bmceItem\w+\b|\bmceTemp\w+\b)/g.test(c)||T.classesRE.test(c)?c:""
}return c
}
}if(S.convert_fonts_to_spans){T._convertFonts()
}if(S.inline_styles){T._convertInlineElements()
}if(S.cleanup_callback){T.onBeforeSetContent.add(function(a,Z){Z.content=T.execCallback("cleanup_callback","insert_to_editor",Z.content,Z)
});
T.onPreProcess.add(function(a,Z){if(Z.set){T.execCallback("cleanup_callback","insert_to_editor_dom",Z.node,Z)
}if(Z.get){T.execCallback("cleanup_callback","get_from_editor_dom",Z.node,Z)
}});
T.onPostProcess.add(function(a,Z){if(Z.set){Z.content=T.execCallback("cleanup_callback","insert_to_editor",Z.content,Z)
}if(Z.get){Z.content=T.execCallback("cleanup_callback","get_from_editor",Z.content,Z)
}})
}if(S.save_callback){T.onGetContent.add(function(a,Z){if(Z.save){Z.content=T.execCallback("save_callback",T.id,Z.content,T.getBody())
}})
}if(S.handle_event_callback){T.onEvent.add(function(a,Z,b){if(T.execCallback("handle_event_callback",Z,a,b)===false){F.cancel(Z)
}})
}T.onSetContent.add(function(){T.addVisual(T.getBody())
});
if(S.padd_empty_editor){T.onPostProcess.add(function(a,Z){Z.content=Z.content.replace(/^(<p[^>]*>(&nbsp;|&#160;|\s|\u00a0|)<\/p>[\r\n]*|<br \/>[\r\n]*)$/,"")
})
}if(P){function R(a,Z){G(a.dom.select("a"),function(b){var c=b.parentNode;
if(a.dom.isBlock(c)&&c.lastChild===b){a.dom.add(c,"br",{mce_bogus:1})
}})
}T.onExecCommand.add(function(a,Z){if(Z==="CreateLink"){R(a)
}});
T.onSetContent.add(T.selection.onSetContent.add(R));
if(!S.readonly){try{X.designMode="Off";
X.designMode="On"
}catch(W){}}}setTimeout(function(){if(T.removed){return 
}T.load({initial:true,format:(S.cleanup_on_startup?"html":"raw")});
T.startContent=T.getContent({format:"raw"});
T.undoManager.add({initial:true});
T.initialized=true;
T.onInit.dispatch(T);
T.execCallback("setupcontent_callback",T.id,T.getBody(),T.getDoc());
T.execCallback("init_instance_callback",T);
T.focus(true);
T.nodeChanged({initial:1});
if(S.content_css){C.each(J(S.content_css),function(Z){T.dom.loadCSS(T.documentBaseURI.toAbsolute(Z))
})
}if(S.auto_focus){setTimeout(function(){var Z=H.get(S.auto_focus);
Z.selection.select(Z.getBody(),1);
Z.selection.collapse(1);
Z.getWin().focus()
},100)
}},1);
Y=null
},focus:function(S){var Q,T=this,R=T.settings.content_editable;
if(!S){if(!R&&(!O||T.selection.getNode().ownerDocument!=T.getDoc())){T.getWin().focus()
}}if(H.activeEditor!=T){if((Q=H.activeEditor)!=null){Q.onDeactivate.dispatch(Q,T)
}T.onActivate.dispatch(T,Q)
}H._setActive(T)
},execCallback:function(Q){var T=this,R=T.settings[Q],S;
if(!R){return 
}if(T.callbackLookup&&(S=T.callbackLookup[Q])){R=S.func;
S=S.scope
}if(M(R,"string")){S=R.replace(/\.\w+$/,"");
S=S?C.resolve(S):0;
R=C.resolve(R);
T.callbackLookup=T.callbackLookup||{};
T.callbackLookup[Q]={func:R,scope:S}
}return R.apply(S||T,Array.prototype.slice.call(arguments,1))
},translate:function(S){var Q=this.settings.language||"en",R=H.i18n;
if(!S){return""
}return R[Q+"."+S]||S.replace(/{\#([^}]+)\}/g,function(T,U){return R[Q+"."+U]||"{#"+U+"}"
})
},getLang:function(Q,R){return H.i18n[(this.settings.language||"en")+"."+Q]||(M(R)?R:"{#"+Q+"}")
},getParam:function(Q,T,V){var S=C.trim,U=M(this.settings[Q])?this.settings[Q]:T,R;
if(V==="hash"){R={};
if(M(U,"string")){G(U.indexOf("=")>0?U.split(/[;,](?![^=;,]*(?:[;,]|$))/):U.split(","),function(W){W=W.split("=");
if(W.length>1){R[S(W[0])]=S(W[1])
}else{R[S(W[0])]=S(W)
}})
}else{R=U
}return R
}return U
},nodeChanged:function(R){var T=this,S=T.selection,Q=S.getNode()||T.getBody();
if(T.initialized){T.onNodeChange.dispatch(T,R?R.controlManager||T.controlManager:T.controlManager,O&&Q.ownerDocument!=T.getDoc()?T.getBody():Q,S.isCollapsed(),R)
}},addButton:function(Q,R){var S=this;
S.buttons=S.buttons||{};
S.buttons[Q]=R
},addCommand:function(Q,R,S){this.execCommands[Q]={func:R,scope:S||this}
},addQueryStateHandler:function(Q,R,S){this.queryStateCommands[Q]={func:R,scope:S||this}
},addQueryValueHandler:function(Q,R,S){this.queryValueCommands[Q]={func:R,scope:S||this}
},addShortcut:function(T,R,V,S){var U=this,Q;
if(!U.settings.custom_shortcuts){return false
}U.shortcuts=U.shortcuts||{};
if(M(V,"string")){Q=V;
V=function(){U.execCommand(Q,false,null)
}
}if(M(V,"object")){Q=V;
V=function(){U.execCommand(Q[0],Q[1],Q[2])
}
}G(J(T),function(X){var W={func:V,scope:S||this,desc:R,alt:false,ctrl:false,shift:false};
G(J(X,"+"),function(Y){switch(Y){case"alt":case"ctrl":case"shift":W[Y]=true;
break;
default:W.charCode=Y.charCodeAt(0);
W.keyCode=Y.toUpperCase().charCodeAt(0)
}});
U.shortcuts[(W.ctrl?"ctrl":"")+","+(W.alt?"alt":"")+","+(W.shift?"shift":"")+","+W.keyCode]=W
});
return true
},execCommand:function(Q,R,W,V){var T=this,S=0,X,U;
if(!/^(mceAddUndoLevel|mceEndUndoLevel|mceBeginUndoLevel|mceRepaint|SelectAll)$/.test(Q)&&(!V||!V.skip_focus)){T.focus()
}X={};
T.onBeforeExecCommand.dispatch(T,Q,R,W,X);
if(X.terminate){return false
}if(T.execCallback("execcommand_callback",T.id,T.selection.getNode(),Q,R,W)){T.onExecCommand.dispatch(T,Q,R,W,V);
return true
}if(X=T.execCommands[Q]){U=X.func.call(X.scope,R,W);
if(U!==true){T.onExecCommand.dispatch(T,Q,R,W,V);
return U
}}G(T.plugins,function(Y){if(Y.execCommand&&Y.execCommand(Q,R,W)){T.onExecCommand.dispatch(T,Q,R,W,V);
S=1;
return false
}});
if(S){return true
}if(T.theme&&T.theme.execCommand&&T.theme.execCommand(Q,R,W)){T.onExecCommand.dispatch(T,Q,R,W,V);
return true
}if(C.GlobalCommands.execCommand(T,Q,R,W)){T.onExecCommand.dispatch(T,Q,R,W,V);
return true
}if(T.editorCommands.execCommand(Q,R,W)){T.onExecCommand.dispatch(T,Q,R,W,V);
return true
}T.getDoc().execCommand(Q,R,W);
T.onExecCommand.dispatch(T,Q,R,W,V)
},queryCommandState:function(Q){var T=this,R,S;
if(T._isHidden()){return 
}if(R=T.queryStateCommands[Q]){S=R.func.call(R.scope);
if(S!==true){return S
}}R=T.editorCommands.queryCommandState(Q);
if(R!==-1){return R
}try{return this.getDoc().queryCommandState(Q)
}catch(U){}},queryCommandValue:function(Q){var T=this,R,S;
if(T._isHidden()){return 
}if(R=T.queryValueCommands[Q]){S=R.func.call(R.scope);
if(S!==true){return S
}}R=T.editorCommands.queryCommandValue(Q);
if(M(R)){return R
}try{return this.getDoc().queryCommandValue(Q)
}catch(U){}},show:function(){var Q=this;
B.show(Q.getContainer());
B.hide(Q.id);
Q.load()
},hide:function(){var R=this,Q=R.getDoc();
if(O&&Q){Q.execCommand("SelectAll")
}R.save();
B.hide(R.getContainer());
B.setStyle(R.id,"display",R.orgDisplay)
},isHidden:function(){return !B.isHidden(this.id)
},setProgressState:function(S,R,Q){this.onSetProgressState.dispatch(this,S,R,Q);
return S
},resizeToContent:function(){var Q=this;
B.setStyle(Q.id+"_ifr","height",Q.getBody().scrollHeight)
},load:function(Q){var T=this,R=T.getElement(),S;
if(R){Q=Q||{};
Q.load=true;
S=T.setContent(M(R.value)?R.value:R.innerHTML,Q);
Q.element=R;
if(!Q.no_events){T.onLoadContent.dispatch(T,Q)
}Q.element=R=null;
return S
}},save:function(Q){var U=this,R=U.getElement(),T,S;
if(!R||!U.initialized){return 
}Q=Q||{};
Q.save=true;
if(!Q.no_events){U.undoManager.typing=0;
U.undoManager.add()
}Q.element=R;
T=Q.content=U.getContent(Q);
if(!Q.no_events){U.onSaveContent.dispatch(U,Q)
}T=Q.content;
if(!/TEXTAREA|INPUT/i.test(R.nodeName)){R.innerHTML=T;
if(S=B.getParent(U.id,"form")){G(S.elements,function(V){if(V.name==U.id){V.value=T;
return false
}})
}}else{R.value=T
}Q.element=R=null;
return T
},setContent:function(R,Q){var S=this;
Q=Q||{};
Q.format=Q.format||"html";
Q.set=true;
Q.content=R;
if(!Q.no_events){S.onBeforeSetContent.dispatch(S,Q)
}if(!C.isIE&&(R.length===0||/^\s+$/.test(R))){Q.content=S.dom.setHTML(S.getBody(),'<br mce_bogus="1" />');
Q.format="raw"
}Q.content=S.dom.setHTML(S.getBody(),C.trim(Q.content));
if(Q.format!="raw"&&S.settings.cleanup){Q.getInner=true;
Q.content=S.dom.setHTML(S.getBody(),S.serializer.serialize(S.getBody(),Q))
}if(!Q.no_events){S.onSetContent.dispatch(S,Q)
}return Q.content
},getContent:function(Q){var S=this,R;
Q=Q||{};
Q.format=Q.format||"html";
Q.get=true;
if(!Q.no_events){S.onBeforeGetContent.dispatch(S,Q)
}if(Q.format!="raw"&&S.settings.cleanup){Q.getInner=true;
R=S.serializer.serialize(S.getBody(),Q)
}else{R=S.getBody().innerHTML
}R=R.replace(/^\s*|\s*$/g,"");
Q.content=R;
if(!Q.no_events){S.onGetContent.dispatch(S,Q)
}return Q.content
},isDirty:function(){var Q=this;
return C.trim(Q.startContent)!=C.trim(Q.getContent({format:"raw",no_events:1}))&&!Q.isNotDirty
},getContainer:function(){var Q=this;
if(!Q.container){Q.container=B.get(Q.editorContainer||Q.id+"_parent")
}return Q.container
},getContentAreaContainer:function(){return this.contentAreaContainer
},getElement:function(){return B.get(this.settings.content_element||this.id)
},getWin:function(){var R=this,Q;
if(!R.contentWindow){Q=B.get(R.id+"_ifr");
if(Q){R.contentWindow=Q.contentWindow
}}return R.contentWindow
},getDoc:function(){var Q=this,R;
if(!Q.contentDocument){R=Q.getWin();
if(R){Q.contentDocument=R.document
}}return Q.contentDocument
},getBody:function(){return this.bodyElement||this.getDoc().body
},convertURL:function(U,Q,R){var T=this,S=T.settings;
if(S.urlconverter_callback){return T.execCallback("urlconverter_callback",U,R,true,Q)
}if(!S.convert_urls||(R&&R.nodeName=="LINK")||U.indexOf("file:")===0){return U
}if(S.relative_urls){return T.documentBaseURI.toRelative(U)
}U=T.documentBaseURI.toAbsolute(U,S.remove_script_host);
return U
},addVisual:function(Q){var S=this,R=S.settings;
Q=Q||S.getBody();
if(!M(S.hasVisual)){S.hasVisual=R.visual
}G(S.dom.select("table,a",Q),function(T){var U;
switch(T.nodeName){case"TABLE":U=S.dom.getAttrib(T,"border");
if(!U||U=="0"){if(S.hasVisual){S.dom.addClass(T,R.visual_table_class)
}else{S.dom.removeClass(T,R.visual_table_class)
}}return ;
case"A":U=S.dom.getAttrib(T,"name");
if(U){if(S.hasVisual){S.dom.addClass(T,"mceItemAnchor")
}else{S.dom.removeClass(T,"mceItemAnchor")
}}return 
}});
S.onVisualAid.dispatch(S,Q,S.hasVisual)
},remove:function(){var R=this,Q=R.getContainer();
R.removed=1;
R.hide();
R.execCallback("remove_instance_callback",R);
R.onRemove.dispatch(R);
R.onExecCommand.listeners=[];
H.remove(R);
B.remove(Q)
},destroy:function(Q){var R=this;
if(R.destroyed){return 
}if(!Q){C.removeUnload(R.destroy);
tinyMCE.onBeforeUnload.remove(R._beforeUnload);
if(R.theme&&R.theme.destroy){R.theme.destroy()
}R.controlManager.destroy();
R.selection.destroy();
R.dom.destroy();
if(!R.settings.content_editable){F.clear(R.getWin());
F.clear(R.getDoc())
}F.clear(R.getBody());
F.clear(R.formElement)
}if(R.formElement){R.formElement.submit=R.formElement._mceOldSubmit;
R.formElement._mceOldSubmit=null
}R.contentAreaContainer=R.formElement=R.container=R.settings.content_element=R.bodyElement=R.contentDocument=R.contentWindow=null;
if(R.selection){R.selection=R.selection.win=R.selection.dom=R.selection.dom.doc=null
}R.destroyed=1
},_addEvents:function(){var R=this,S,X=R.settings,Q={mouseup:"onMouseUp",mousedown:"onMouseDown",click:"onClick",keyup:"onKeyUp",keydown:"onKeyDown",keypress:"onKeyPress",submit:"onSubmit",reset:"onReset",contextmenu:"onContextMenu",dblclick:"onDblClick",paste:"onPaste"};
function T(Z,Y){var a=Z.type;
if(R.removed){return 
}if(R.onEvent.dispatch(R,Z,Y)!==false){R[Q[Z.fakeType||Z.type]].dispatch(R,Z,Y)
}}G(Q,function(Y,Z){switch(Z){case"contextmenu":if(C.isOpera){R.dom.bind(R.getBody(),"mousedown",function(a){if(a.ctrlKey){a.fakeType="contextmenu";
T(a)
}})
}else{R.dom.bind(R.getBody(),Z,T)
}break;
case"paste":R.dom.bind(R.getBody(),Z,function(a){T(a)
});
break;
case"submit":case"reset":R.dom.bind(R.getElement().form||B.getParent(R.id,"form"),Z,T);
break;
default:R.dom.bind(X.content_editable?R.getBody():R.getDoc(),Z,T)
}});
R.dom.bind(X.content_editable?R.getBody():(P?R.getDoc():R.getWin()),"focus",function(Y){R.focus(true)
});
if(C.isGecko){R.dom.bind(R.getDoc(),"DOMNodeInserted",function(Y){var Z;
Y=Y.target;
if(Y.nodeType===1&&Y.nodeName==="IMG"&&(Z=Y.getAttribute("mce_src"))){Y.src=R.documentBaseURI.toAbsolute(Z)
}})
}if(P){function V(){var b=this,Z=b.getDoc(),a=b.settings;
if(P&&!a.readonly){if(b._isHidden()){try{if(!a.content_editable){Z.designMode="On"
}}catch(Y){}}try{Z.execCommand("styleWithCSS",0,false)
}catch(Y){if(!b._isHidden()){try{Z.execCommand("useCSS",0,true)
}catch(Y){}}}if(!a.table_inline_editing){try{Z.execCommand("enableInlineTableEditing",false,false)
}catch(Y){}}if(!a.object_resizing){try{Z.execCommand("enableObjectResizing",false,false)
}catch(Y){}}}}R.onBeforeExecCommand.add(V);
R.onMouseDown.add(V)
}R.onMouseUp.add(R.nodeChanged);
R.onClick.add(R.nodeChanged);
R.onKeyUp.add(function(a,Z){var Y=Z.keyCode;
if((Y>=33&&Y<=36)||(Y>=37&&Y<=40)||Y==13||Y==45||Y==46||Y==8||(C.isMac&&(Y==91||Y==93))||Z.ctrlKey){R.nodeChanged()
}});
R.onReset.add(function(){R.setContent(R.startContent,{format:"raw"})
});
if(X.custom_shortcuts){if(X.custom_undo_redo_keyboard_shortcuts){R.addShortcut("ctrl+z",R.getLang("undo_desc"),"Undo");
R.addShortcut("ctrl+y",R.getLang("redo_desc"),"Redo")
}if(P){R.addShortcut("ctrl+b",R.getLang("bold_desc"),"Bold");
R.addShortcut("ctrl+i",R.getLang("italic_desc"),"Italic");
R.addShortcut("ctrl+u",R.getLang("underline_desc"),"Underline")
}for(S=1;
S<=6;
S++){R.addShortcut("ctrl+"+S,"",["FormatBlock",false,"<h"+S+">"])
}R.addShortcut("ctrl+7","",["FormatBlock",false,"<p>"]);
R.addShortcut("ctrl+8","",["FormatBlock",false,"<div>"]);
R.addShortcut("ctrl+9","",["FormatBlock",false,"<address>"]);
function W(Y){var Z=null;
if(!Y.altKey&&!Y.ctrlKey&&!Y.metaKey){return Z
}G(R.shortcuts,function(a){if(C.isMac&&a.ctrl!=Y.metaKey){return 
}else{if(!C.isMac&&a.ctrl!=Y.ctrlKey){return 
}}if(a.alt!=Y.altKey){return 
}if(a.shift!=Y.shiftKey){return 
}if(Y.keyCode==a.keyCode||(Y.charCode&&Y.charCode==a.charCode)){Z=a;
return false
}});
return Z
}R.onKeyUp.add(function(a,Z){var Y=W(Z);
if(Y){return F.cancel(Z)
}});
R.onKeyPress.add(function(a,Z){var Y=W(Z);
if(Y){return F.cancel(Z)
}});
R.onKeyDown.add(function(a,Z){var Y=W(Z);
if(Y){Y.func.call(Y.scope);
return F.cancel(Z)
}})
}if(C.isIE){R.dom.bind(R.getDoc(),"controlselect",function(Y){var Z=R.resizeInfo,a;
Y=Y.target;
if(Y.nodeName!=="IMG"){return 
}if(Z){R.dom.unbind(Z.node,Z.ev,Z.cb)
}if(!R.dom.hasClass(Y,"mceItemNoResize")){ev="resizeend";
a=R.dom.bind(Y,ev,function(b){var c;
b=b.target;
if(c=R.dom.getStyle(b,"width")){R.dom.setAttrib(b,"width",c.replace(/[^0-9%]+/g,""));
R.dom.setStyle(b,"width","")
}if(c=R.dom.getStyle(b,"height")){R.dom.setAttrib(b,"height",c.replace(/[^0-9%]+/g,""));
R.dom.setStyle(b,"height","")
}})
}else{ev="resizestart";
a=R.dom.bind(Y,"resizestart",F.cancel,F)
}Z=R.resizeInfo={node:Y,ev:ev,cb:a}
});
R.onKeyDown.add(function(Z,Y){switch(Y.keyCode){case 8:if(R.selection.getRng().item){R.selection.getRng().item(0).removeNode();
return F.cancel(Y)
}}})
}if(C.isOpera){R.onClick.add(function(Z,Y){F.prevent(Y)
})
}if(X.custom_undo_redo){function U(){R.undoManager.typing=0;
R.undoManager.add()
}if(C.isIE){R.dom.bind(R.getWin(),"blur",function(Z){var Y;
if(R.selection){Y=R.selection.getNode();
if(!R.removed&&Y.ownerDocument&&Y.ownerDocument!=R.getDoc()){U()
}}})
}else{R.dom.bind(R.getDoc(),"blur",function(){if(R.selection&&!R.removed){U()
}})
}R.onMouseDown.add(U);
R.onKeyUp.add(function(Z,Y){if((Y.keyCode>=33&&Y.keyCode<=36)||(Y.keyCode>=37&&Y.keyCode<=40)||Y.keyCode==13||Y.keyCode==45||Y.ctrlKey){R.undoManager.typing=0;
R.undoManager.add()
}});
R.onKeyDown.add(function(Z,Y){if((Y.keyCode>=33&&Y.keyCode<=36)||(Y.keyCode>=37&&Y.keyCode<=40)||Y.keyCode==13||Y.keyCode==45){if(R.undoManager.typing){R.undoManager.add();
R.undoManager.typing=0
}return 
}if(!R.undoManager.typing){R.undoManager.add();
R.undoManager.typing=1
}})
}},_convertInlineElements:function(){var U=this,S=U.settings,Q=U.dom,V,X,Y,T,R;
function W(a,Z){if(!S.inline_styles){return 
}if(Z.get){G(U.dom.select("table,u,strike",Z.node),function(b){switch(b.nodeName){case"TABLE":if(V=Q.getAttrib(b,"height")){Q.setStyle(b,"height",V);
Q.setAttrib(b,"height","")
}break;
case"U":case"STRIKE":b.style.textDecoration=b.nodeName=="U"?"underline":"line-through";
Q.setAttrib(b,"mce_style","");
Q.setAttrib(b,"mce_name","span");
break
}})
}else{if(Z.set){G(U.dom.select("table,span",Z.node).reverse(),function(b){if(b.nodeName=="TABLE"){if(V=Q.getStyle(b,"height")){Q.setAttrib(b,"height",V.replace(/[^0-9%]+/g,""))
}}else{if(b.style.textDecoration=="underline"){Y="u"
}else{if(b.style.textDecoration=="line-through"){Y="strike"
}else{Y=""
}}if(Y){b.style.textDecoration="";
Q.setAttrib(b,"mce_style","");
X=Q.create(Y,{style:Q.getAttrib(b,"style")});
Q.replace(X,b,1)
}}})
}}}U.onPreProcess.add(W);
if(!S.cleanup_on_startup){U.onSetContent.add(function(a,Z){if(Z.initial){W(U,{node:U.getBody(),set:1})
}})
}},_convertFonts:function(){var R=this,Q=R.settings,W=R.dom,S,U,V,T;
if(!Q.inline_styles){return 
}S=[8,10,12,14,18,24,36];
U=["xx-small","x-small","small","medium","large","x-large","xx-large"];
if(V=Q.font_size_style_values){V=J(V)
}if(T=Q.font_size_classes){T=J(T)
}function X(c){var b,Y,Z,a;
if(!Q.inline_styles){return 
}Z=R.dom.select("font",c);
for(a=Z.length-1;
a>=0;
a--){b=Z[a];
Y=W.create("span",{style:W.getAttrib(b,"style"),"class":W.getAttrib(b,"class")});
W.setStyles(Y,{fontFamily:W.getAttrib(b,"face"),color:W.getAttrib(b,"color"),backgroundColor:b.style.backgroundColor});
if(b.size){if(V){W.setStyle(Y,"fontSize",V[parseInt(b.size)-1])
}else{W.setAttrib(Y,"class",T[parseInt(b.size)-1])
}}W.setAttrib(Y,"mce_style","");
W.replace(Y,b,1)
}}R.onPreProcess.add(function(Z,Y){if(Y.get){X(Y.node)
}});
R.onSetContent.add(function(Z,Y){if(Y.initial){X(Y.node)
}})
},_isHidden:function(){var Q;
if(!P){return 0
}Q=this.selection.getSel();
return(!Q||!Q.rangeCount||Q.rangeCount==0)
},_fixNesting:function(R){var Q=[],S;
R=R.replace(/<(\/)?([^\s>]+)[^>]*?>/g,function(V,W,T){var U;
if(W==="/"){if(!Q.length){return""
}if(T!==Q[Q.length-1].tag){for(S=Q.length-1;
S>=0;
S--){if(Q[S].tag===T){Q[S].close=1;
break
}}return""
}else{Q.pop();
if(Q.length&&Q[Q.length-1].close){V=V+"</"+Q[Q.length-1].tag+">";
Q.pop()
}}}else{if(/^(br|hr|input|meta|img|link|param)$/i.test(T)){return V
}if(/\/>$/.test(V)){return V
}Q.push({tag:T})
}return V
});
for(S=Q.length-1;
S>=0;
S--){R+="</"+Q[S].tag+">"
}return R
}})
})(tinymce);
(function(E){var C=E.each,F=E.isIE,B=E.isGecko,A=E.isOpera,D=E.isWebKit;
E.create("tinymce.EditorCommands",{EditorCommands:function(G){this.editor=G
},execCommand:function(H,I,G){var K=this,L=K.editor,J;
switch(H){case"mceResetDesignMode":case"mceBeginUndoLevel":return true;
case"unlink":K.UnLink();
return true;
case"JustifyLeft":case"JustifyCenter":case"JustifyRight":case"JustifyFull":K.mceJustify(H,H.substring(7).toLowerCase());
return true;
default:J=this[H];
if(J){J.call(this,I,G);
return true
}}return false
},Indent:function(){var L=this.editor,G=L.dom,I=L.selection,H,K,J;
K=L.settings.indentation;
J=/[a-z%]+$/i.exec(K);
K=parseInt(K);
if(L.settings.inline_styles&&(!this.queryStateInsertUnorderedList()&&!this.queryStateInsertOrderedList())){C(I.getSelectedBlocks(),function(M){G.setStyle(M,"paddingLeft",(parseInt(M.style.paddingLeft||0)+K)+J)
});
return 
}L.getDoc().execCommand("Indent",false,null);
if(F){G.getParent(I.getNode(),function(M){if(M.nodeName=="BLOCKQUOTE"){M.dir=M.style.cssText=""
}})
}},Outdent:function(){var L=this.editor,G=L.dom,I=L.selection,H,M,K,J;
K=L.settings.indentation;
J=/[a-z%]+$/i.exec(K);
K=parseInt(K);
if(L.settings.inline_styles&&(!this.queryStateInsertUnorderedList()&&!this.queryStateInsertOrderedList())){C(I.getSelectedBlocks(),function(N){M=Math.max(0,parseInt(N.style.paddingLeft||0)-K);
G.setStyle(N,"paddingLeft",M?M+J:"")
});
return 
}L.getDoc().execCommand("Outdent",false,null)
},mceSetContent:function(G,H){this.editor.setContent(H)
},mceToggleVisualAid:function(){var G=this.editor;
G.hasVisual=!G.hasVisual;
G.addVisual()
},mceReplaceContent:function(H,I){var G=this.editor.selection;
G.setContent(I.replace(/\{\$selection\}/g,G.getContent({format:"text"})))
},mceInsertLink:function(J,K){var L=this.editor,I=L.selection,H=L.dom.getParent(I.getNode(),"a");
if(E.is(K,"string")){K={href:K}
}function G(M){C(K,function(N,O){L.dom.setAttrib(M,O,N)
})
}if(!H){L.execCommand("CreateLink",false,"javascript:mctmp(0);");
C(L.dom.select("a[href=javascript:mctmp(0);]"),function(M){G(M)
})
}else{if(K.href){G(H)
}else{L.dom.remove(H,1)
}}},UnLink:function(){var H=this.editor,G=H.selection;
if(G.isCollapsed()){G.select(G.getNode())
}H.getDoc().execCommand("unlink",false,null);
G.collapse(0)
},FontName:function(J,K){var I=this,L=I.editor,H=L.selection,G;
if(!K){if(H.isCollapsed()){H.select(H.getNode())
}}else{if(L.settings.convert_fonts_to_spans){I._applyInlineStyle("span",{style:{fontFamily:K}})
}else{L.getDoc().execCommand("FontName",false,K)
}}},FontSize:function(I,J){var K=this.editor,G=K.settings,H,L;
if(G.convert_fonts_to_spans&&J>=1&&J<=7){L=E.explode(G.font_size_style_values);
H=E.explode(G.font_size_classes);
if(H){J=H[J-1]||J
}else{J=L[J-1]||J
}}if(J>=1&&J<=7){K.getDoc().execCommand("FontSize",false,J)
}else{this._applyInlineStyle("span",{style:{fontSize:J}})
}},queryCommandValue:function(G){var H=this["queryValue"+G];
if(H){return H.call(this,G)
}return false
},queryCommandState:function(G){var H;
switch(G){case"JustifyLeft":case"JustifyCenter":case"JustifyRight":case"JustifyFull":return this.queryStateJustify(G,G.substring(7).toLowerCase());
default:if(H=this["queryState"+G]){return H.call(this,G)
}}return -1
},_queryState:function(G){try{return this.editor.getDoc().queryCommandState(G)
}catch(H){}},_queryVal:function(G){try{return this.editor.getDoc().queryCommandValue(G)
}catch(H){}},queryValueFontSize:function(){var H=this.editor,I=0,G;
if(G=H.dom.getParent(H.selection.getNode(),"span")){I=G.style.fontSize
}if(!I&&(A||D)){if(G=H.dom.getParent(H.selection.getNode(),"font")){I=G.size
}return I
}return I||this._queryVal("FontSize")
},queryValueFontName:function(){var H=this.editor,I=0,G;
if(G=H.dom.getParent(H.selection.getNode(),"font")){I=G.face
}if(G=H.dom.getParent(H.selection.getNode(),"span")){I=G.style.fontFamily.replace(/, /g,",").replace(/[\'\"]/g,"").toLowerCase()
}if(!I){I=this._queryVal("FontName")
}return I
},mceJustify:function(I,H){var L=this.editor,J=L.selection,P=J.getNode(),G=P.nodeName,O,M,N=L.dom,K;
if(L.settings.inline_styles&&this.queryStateJustify(I,H)){K=1
}O=N.getParent(P,L.dom.isBlock);
if(G=="IMG"){if(H=="full"){return 
}if(K){if(H=="center"){N.setStyle(O||P.parentNode,"textAlign","")
}N.setStyle(P,"float","");
this.mceRepaint();
return 
}if(H=="center"){if(O&&/^(TD|TH)$/.test(O.nodeName)){O=0
}if(!O||O.childNodes.length>1){M=N.create("p");
M.appendChild(P.cloneNode(false));
if(O){N.insertAfter(M,O)
}else{N.insertAfter(M,P)
}N.remove(P);
P=M.firstChild;
O=M
}N.setStyle(O,"textAlign",H);
N.setStyle(P,"float","")
}else{N.setStyle(P,"float",H);
N.setStyle(O||P.parentNode,"textAlign","")
}this.mceRepaint();
return 
}if(L.settings.inline_styles&&L.settings.forced_root_block){if(K){H=""
}C(J.getSelectedBlocks(N.getParent(J.getStart(),N.isBlock),N.getParent(J.getEnd(),N.isBlock)),function(Q){N.setAttrib(Q,"align","");
N.setStyle(Q,"textAlign",H=="full"?"justify":H)
});
return 
}else{if(!K){L.getDoc().execCommand(I,false,null)
}}if(L.settings.inline_styles){if(K){N.getParent(L.selection.getNode(),function(Q){if(Q.style&&Q.style.textAlign){N.setStyle(Q,"textAlign","")
}});
return 
}C(N.select("*"),function(Q){var R=Q.align;
if(R){if(R=="full"){R="justify"
}N.setStyle(Q,"textAlign",R);
N.setAttrib(Q,"align","")
}})
}},mceSetCSSClass:function(G,H){this.mceSetStyleInfo(0,{command:"setattrib",name:"class",value:H})
},getSelectedElement:function(){var S=this,I=S.editor,J=I.dom,V=I.selection,P=V.getRng(),L,M,U,H,N,Q,G,O,R,T;
if(V.isCollapsed()||P.item){return V.getNode()
}T=I.settings.merge_styles_invalid_parents;
if(E.is(T,"string")){T=new RegExp(T,"i")
}if(F){L=P.duplicate();
L.collapse(true);
U=L.parentElement();
M=P.duplicate();
M.collapse(false);
H=M.parentElement();
if(U!=H){L.move("character",1);
U=L.parentElement()
}if(U==H){L=P.duplicate();
L.moveToElementText(U);
if(L.compareEndPoints("StartToStart",P)==0&&L.compareEndPoints("EndToEnd",P)==0){return T&&T.test(U.nodeName)?null:U
}}}else{function K(W){return J.getParent(W,"*")
}U=P.startContainer;
H=P.endContainer;
N=P.startOffset;
Q=P.endOffset;
if(!P.collapsed){if(U==H){if(N-Q<2){if(U.hasChildNodes()){O=U.childNodes[N];
return T&&T.test(O.nodeName)?null:O
}}}}if(U.nodeType!=3||H.nodeType!=3){return null
}if(N==0){O=K(U);
if(O&&O.firstChild!=U){O=null
}}if(N==U.nodeValue.length){G=U.nextSibling;
if(G&&G.nodeType==1){O=U.nextSibling
}}if(Q==0){G=H.previousSibling;
if(G&&G.nodeType==1){R=G
}}if(Q==H.nodeValue.length){R=K(H);
if(R&&R.lastChild!=H){R=null
}}if(O==R){return T&&O&&T.test(O.nodeName)?null:O
}}return null
},mceSetStyleInfo:function(K,L){var H=this,Q=H.editor,O=Q.getDoc(),R=Q.dom,P,N,G=Q.selection,I=L.wrapper||"span",N=G.getBookmark(),J;
function M(S,T){if(S.nodeType==1){switch(L.command){case"setattrib":return R.setAttrib(S,L.name,L.value);
case"setstyle":return R.setStyle(S,L.name,L.value);
case"removeformat":return R.setAttrib(S,"class","")
}}}J=Q.settings.merge_styles_invalid_parents;
if(E.is(J,"string")){J=new RegExp(J,"i")
}if((P=H.getSelectedElement())&&!Q.settings.force_span_wrappers){M(P,1)
}else{O.execCommand("FontName",false,"__");
C(R.select("span,font"),function(S){var U,T;
if(R.getAttrib(S,"face")=="__"||S.style.fontFamily==="__"){U=R.create(I,{mce_new:"1"});
M(U);
C(S.childNodes,function(V){U.appendChild(V.cloneNode(true))
});
R.replace(U,S)
}})
}C(R.select(I).reverse(),function(S){var T=S.parentNode;
if(!R.getAttrib(S,"mce_new")){T=R.getParent(S,"*[mce_new]");
if(T){R.remove(S,1)
}}});
C(R.select(I).reverse(),function(S){var T=S.parentNode;
if(!T||!R.getAttrib(S,"mce_new")){return 
}if(Q.settings.force_span_wrappers&&T.nodeName!="SPAN"){return 
}if(T.nodeName==I.toUpperCase()&&T.childNodes.length==1){return R.remove(T,1)
}if(S.nodeType==1&&(!J||!J.test(T.nodeName))&&T.childNodes.length==1){M(T);
R.setAttrib(S,"class","")
}});
C(R.select(I).reverse(),function(S){if(R.getAttrib(S,"mce_new")||(R.getAttribs(S).length<=1&&S.className==="")){if(!R.getAttrib(S,"class")&&!R.getAttrib(S,"style")){return R.remove(S,1)
}R.setAttrib(S,"mce_new","")
}});
G.moveToBookmark(N)
},queryStateJustify:function(G,J){var K=this.editor,H=K.selection.getNode(),I=K.dom;
if(H&&H.nodeName=="IMG"){if(I.getStyle(H,"float")==J){return 1
}return H.parentNode.style.textAlign==J
}H=I.getParent(K.selection.getStart(),function(L){return L.nodeType==1&&L.style.textAlign
});
if(J=="full"){J="justify"
}if(K.settings.inline_styles){return(H&&H.style.textAlign==J)
}return this._queryState(G)
},ForeColor:function(G,H){var I=this.editor;
if(I.settings.convert_fonts_to_spans){this._applyInlineStyle("span",{style:{color:H}});
return 
}else{I.getDoc().execCommand("ForeColor",false,H)
}},HiliteColor:function(J,H){var K=this,L=K.editor,I=L.getDoc();
if(L.settings.convert_fonts_to_spans){this._applyInlineStyle("span",{style:{backgroundColor:H}});
return 
}function G(N){if(!B){return 
}try{I.execCommand("styleWithCSS",0,N)
}catch(M){I.execCommand("useCSS",0,!N)
}}if(B||A){G(true);
I.execCommand("hilitecolor",false,H);
G(false)
}else{I.execCommand("BackColor",false,H)
}},FormatBlock:function(I,O){var H=this,K=H.editor,G=K.selection,M=K.dom,P,L,J;
function N(Q){return/^(P|DIV|H[1-6]|ADDRESS|BLOCKQUOTE|PRE)$/.test(Q.nodeName)
}P=M.getParent(G.getNode(),function(Q){return N(Q)
});
if(P){if((F&&N(P.parentNode))||P.nodeName=="DIV"){L=K.dom.create(O);
C(M.getAttribs(P),function(Q){M.setAttrib(L,Q.nodeName,M.getAttrib(P,Q.nodeName))
});
J=G.getBookmark();
M.replace(L,P,1);
G.moveToBookmark(J);
K.nodeChanged();
return 
}}O=K.settings.forced_root_block?(O||"<p>"):O;
if(O.indexOf("<")==-1){O="<"+O+">"
}if(E.isGecko){O=O.replace(/<(div|blockquote|code|dt|dd|dl|samp)>/gi,"$1")
}K.getDoc().execCommand("FormatBlock",false,O)
},mceCleanup:function(){var H=this.editor,G=H.selection,I=G.getBookmark();
H.setContent(H.getContent());
G.moveToBookmark(I)
},mceRemoveNode:function(I,H){var K=this.editor,J=K.selection,L,G=H||J.getNode();
if(G==K.getBody()){return 
}L=J.getBookmark();
K.dom.remove(G,1);
J.moveToBookmark(L);
K.nodeChanged()
},mceSelectNodeDepth:function(I,H){var K=this.editor,J=K.selection,G=0;
K.dom.getParent(J.getNode(),function(L){if(L.nodeType==1&&G++==H){J.select(L);
K.nodeChanged();
return false
}},K.getBody())
},mceSelectNode:function(G,H){this.editor.selection.select(H)
},mceInsertContent:function(H,G){this.editor.selection.setContent(G)
},mceInsertRawHTML:function(H,G){var I=this.editor;
I.selection.setContent("tiny_mce_marker");
I.setContent(I.getContent().replace(/tiny_mce_marker/g,G))
},mceRepaint:function(){var H,J,G=this.editor;
if(E.isGecko){try{H=G.selection;
J=H.getBookmark(true);
if(H.getSel()){H.getSel().selectAllChildren(G.getBody())
}H.collapse(true);
H.moveToBookmark(J)
}catch(I){}}},queryStateUnderline:function(){var H=this.editor,G=H.selection.getNode();
if(G&&G.nodeName=="A"){return false
}return this._queryState("Underline")
},queryStateOutdent:function(){var H=this.editor,G;
if(H.settings.inline_styles){if((G=H.dom.getParent(H.selection.getStart(),H.dom.isBlock))&&parseInt(G.style.paddingLeft)>0){return true
}if((G=H.dom.getParent(H.selection.getEnd(),H.dom.isBlock))&&parseInt(G.style.paddingLeft)>0){return true
}}return this.queryStateInsertUnorderedList()||this.queryStateInsertOrderedList()||(!H.settings.inline_styles&&!!H.dom.getParent(H.selection.getNode(),"BLOCKQUOTE"))
},queryStateInsertUnorderedList:function(){return this.editor.dom.getParent(this.editor.selection.getNode(),"UL")
},queryStateInsertOrderedList:function(){return this.editor.dom.getParent(this.editor.selection.getNode(),"OL")
},queryStatemceBlockQuote:function(){return !!this.editor.dom.getParent(this.editor.selection.getStart(),function(G){return G.nodeName==="BLOCKQUOTE"
})
},_applyInlineStyle:function(J,O,L){var H=this,K=H.editor,M=K.dom,P,I={},N,G;
J=J.toUpperCase();
if(L&&L.check_classes&&O["class"]){L.check_classes.push(O["class"])
}function Q(){C(M.select(J).reverse(),function(S){var T=0;
C(M.getAttribs(S),function(U){if(U.nodeName.substring(0,1)!="_"&&M.getAttrib(S,U.nodeName)!=""){T++
}});
if(T==0){M.remove(S,1)
}})
}function R(){var S;
C(M.select("span,font"),function(T){if(T.style.fontFamily=="mceinline"||T.face=="mceinline"){if(!S){S=K.selection.getBookmark()
}O._mce_new="1";
M.replace(M.create(J,O),T,1)
}});
C(M.select(J+"[_mce_new]"),function(T){function U(V){if(V.nodeType==1){C(O.style,function(W,X){M.setStyle(V,X,"")
});
if(O["class"]&&V.className&&L){C(L.check_classes,function(W){if(M.hasClass(V,W)){M.removeClass(V,W)
}})
}}}C(M.select(J,T),U);
if(T.parentNode&&T.parentNode.nodeType==1&&T.parentNode.childNodes.length==1){U(T.parentNode)
}M.getParent(T.parentNode,function(V){if(V.nodeType==1){if(O.style){C(O.style,function(Y,W){var X;
if(!I[W]&&(X=M.getStyle(V,W))){if(X===Y){M.setStyle(T,W,"")
}I[W]=1
}})
}if(O["class"]&&V.className&&L){C(L.check_classes,function(W){if(M.hasClass(V,W)){M.removeClass(T,W)
}})
}}return false
});
T.removeAttribute("_mce_new")
});
Q();
K.selection.moveToBookmark(S);
return !!S
}K.focus();
K.getDoc().execCommand("FontName",false,"mceinline");
R();
if(N=H._applyInlineStyle.keyhandler){K.onKeyUp.remove(N);
K.onKeyPress.remove(N);
K.onKeyDown.remove(N);
K.onSetContent.remove(H._applyInlineStyle.chandler)
}if(K.selection.isCollapsed()){if(!F){C(M.getParents(K.selection.getNode(),"span"),function(S){C(O.style,function(U,V){var T;
if(T=M.getStyle(S,V)){if(T==U){M.setStyle(S,V,"");
G=2;
return false
}G=1;
return false
}});
if(G){return false
}});
if(G==2){P=K.selection.getBookmark();
Q();
K.selection.moveToBookmark(P);
window.setTimeout(function(){K.nodeChanged()
},1);
return 
}}H._pendingStyles=E.extend(H._pendingStyles||{},O.style);
H._applyInlineStyle.chandler=K.onSetContent.add(function(){delete H._pendingStyles
});
H._applyInlineStyle.keyhandler=N=function(S){if(H._pendingStyles){O.style=H._pendingStyles;
delete H._pendingStyles
}if(R()){K.onKeyDown.remove(H._applyInlineStyle.keyhandler);
K.onKeyPress.remove(H._applyInlineStyle.keyhandler)
}if(S.type=="keyup"){K.onKeyUp.remove(H._applyInlineStyle.keyhandler)
}};
K.onKeyDown.add(N);
K.onKeyPress.add(N);
K.onKeyUp.add(N)
}else{H._pendingStyles=0
}}})
})(tinymce);
(function(A){A.create("tinymce.UndoManager",{index:0,data:null,typing:0,UndoManager:function(D){var C=this,B=A.util.Dispatcher;
C.editor=D;
C.data=[];
C.onAdd=new B(this);
C.onUndo=new B(this);
C.onRedo=new B(this)
},add:function(G){var D=this,E,F=D.editor,H,C=F.settings,B;
G=G||{};
G.content=G.content||F.getContent({format:"raw",no_events:1});
G.content=G.content.replace(/^\s*|\s*$/g,"");
B=D.data[D.index>0&&(D.index==0||D.index==D.data.length)?D.index-1:D.index];
if(!G.initial&&B&&G.content==B.content){return null
}if(C.custom_undo_redo_levels){if(D.data.length>C.custom_undo_redo_levels){for(E=0;
E<D.data.length-1;
E++){D.data[E]=D.data[E+1]
}D.data.length--;
D.index=D.data.length
}}if(C.custom_undo_redo_restore_selection&&!G.initial){G.bookmark=H=G.bookmark||F.selection.getBookmark()
}if(D.index<D.data.length){D.index++
}if(D.data.length===0&&!G.initial){return null
}D.data.length=D.index+1;
D.data[D.index++]=G;
if(G.initial){D.index=0
}if(D.data.length==2&&D.data[0].initial){D.data[0].bookmark=H
}D.onAdd.dispatch(D,G);
F.isNotDirty=0;
return G
},undo:function(){var C=this,E=C.editor,B=B,D;
if(C.typing){C.add();
C.typing=0
}if(C.index>0){if(C.index==C.data.length&&C.index>1){D=C.index;
C.typing=0;
if(!C.add()){C.index=D
}--C.index
}B=C.data[--C.index];
E.setContent(B.content,{format:"raw"});
E.selection.moveToBookmark(B.bookmark);
C.onUndo.dispatch(C,B)
}return B
},redo:function(){var C=this,D=C.editor,B=null;
if(C.index<C.data.length-1){B=C.data[++C.index];
D.setContent(B.content,{format:"raw"});
D.selection.moveToBookmark(B.bookmark);
C.onRedo.dispatch(C,B)
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
(function(F){var A,G,B,H,E,C;
A=F.dom.Event;
G=F.isIE;
B=F.isGecko;
H=F.isOpera;
E=F.each;
C=F.extend;
function D(I){I=I.innerHTML;
I=I.replace(/<(img|hr|table|input|select|textarea)[ \>]/gi,"-");
I=I.replace(/<[^>]+>/g,"");
return I.replace(/[ \t\r\n]+/g,"")==""
}F.create("tinymce.ForceBlocks",{ForceBlocks:function(L){var K=this,J=L.settings,I;
K.editor=L;
K.dom=L.dom;
I=(J.forced_root_block||"p").toLowerCase();
J.element=I.toUpperCase();
L.onPreInit.add(K.setup,K);
K.reOpera=new RegExp("(\\u00a0|&#160;|&nbsp;)</"+I+">","gi");
K.rePadd=new RegExp("<p( )([^>]+)><\\/p>|<p( )([^>]+)\\/>|<p( )([^>]+)>\\s+<\\/p>|<p><\\/p>|<p\\/>|<p>\\s+<\\/p>".replace(/p/g,I),"gi");
K.reNbsp2BR1=new RegExp("<p( )([^>]+)>[\\s\\u00a0]+<\\/p>|<p>[\\s\\u00a0]+<\\/p>".replace(/p/g,I),"gi");
K.reNbsp2BR2=new RegExp("<%p()([^>]+)>(&nbsp;|&#160;)<\\/%p>|<%p>(&nbsp;|&#160;)<\\/%p>".replace(/%p/g,I),"gi");
K.reBR2Nbsp=new RegExp("<p( )([^>]+)>\\s*<br \\/>\\s*<\\/p>|<p>\\s*<br \\/>\\s*<\\/p>".replace(/p/g,I),"gi");
function M(O,N){if(H){N.content=N.content.replace(K.reOpera,"</"+I+">")
}N.content=N.content.replace(K.rePadd,"<"+I+"$1$2$3$4$5$6>\u00a0</"+I+">");
if(!G&&!H&&N.set){N.content=N.content.replace(K.reNbsp2BR1,"<"+I+"$1$2><br /></"+I+">");
N.content=N.content.replace(K.reNbsp2BR2,"<"+I+"$1$2><br /></"+I+">")
}else{N.content=N.content.replace(K.reBR2Nbsp,"<"+I+"$1$2>\u00a0</"+I+">")
}}L.onBeforeSetContent.add(M);
L.onPostProcess.add(M);
if(J.forced_root_block){L.onInit.add(K.forceRoots,K);
L.onSetContent.add(K.forceRoots,K);
L.onBeforeGetContent.add(K.forceRoots,K)
}},setup:function(){var K=this,L=K.editor,J=L.settings;
if(J.forced_root_block){L.onKeyUp.add(K.forceRoots,K);
L.onPreProcess.add(K.forceRoots,K)
}if(J.force_br_newlines){if(G){L.onKeyPress.add(function(M,O){var N,P=M.selection;
if(O.keyCode==13&&P.getNode().nodeName!="LI"){P.setContent('<br id="__" /> ',{format:"raw"});
N=M.dom.get("__");
N.removeAttribute("id");
P.select(N);
P.collapse();
return A.cancel(O)
}})
}return 
}if(!G&&J.force_p_newlines){L.onKeyPress.add(function(M,N){if(N.keyCode==13&&!N.shiftKey){if(!K.insertPara(N)){A.cancel(N)
}}});
if(B){L.onKeyDown.add(function(M,N){if((N.keyCode==8||N.keyCode==46)&&!N.shiftKey){K.backspaceDelete(N,N.keyCode==8)
}})
}}function I(O,M){var N=L.dom.create(M);
E(O.attributes,function(P){if(P.specified&&P.nodeValue){N.setAttribute(P.nodeName.toLowerCase(),P.nodeValue)
}});
E(O.childNodes,function(P){N.appendChild(P.cloneNode(true))
});
O.parentNode.replaceChild(N,O);
return N
}L.onPreProcess.add(function(M,N){E(M.dom.select("p,h1,h2,h3,h4,h5,h6,div",N.node),function(O){if(D(O)){E(M.dom.select("span,em,strong,b,i",N.node),function(P){if(!P.hasChildNodes()){P.appendChild(M.getDoc().createTextNode("\u00a0"));
return false
}})
}})
});
if(G){if(J.element!="P"){L.onKeyPress.add(function(M,N){K.lastElm=M.selection.getNode().nodeName
});
L.onKeyUp.add(function(R,P){var N,Q=R.selection,O=Q.getNode(),M=R.getBody();
if(M.childNodes.length===1&&O.nodeName=="P"){O=I(O,J.element);
Q.select(O);
Q.collapse();
R.nodeChanged()
}else{if(P.keyCode==13&&!P.shiftKey&&K.lastElm!="P"){N=R.dom.getParent(O,"p");
if(N){I(N,J.element);
R.nodeChanged()
}}}})
}}},find:function(N,K,J){var L=this.editor,M=L.getDoc().createTreeWalker(N,4,null,false),I=-1;
while(N=M.nextNode()){I++;
if(K==0&&N==J){return I
}if(K==1&&I==J){return N
}}return -1
},forceRoots:function(d,Y){var W=this,d=W.editor,R=d.getBody(),X=d.getDoc(),M=d.selection,U=M.getSel(),S=M.getRng(),Q=-2,e,b,i,h,V=-16777215;
var T,g,O,c,P,f=R.childNodes,L,N,a;
for(L=f.length-1;
L>=0;
L--){T=f[L];
if(T.nodeType==3||(!W.dom.isBlock(T)&&T.nodeType!=8)){if(!g){if(T.nodeType!=3||/[^\s]/g.test(T.nodeValue)){if(Q==-2&&S){if(!G){if(S.startContainer.nodeType==1&&(N=S.startContainer.childNodes[S.startOffset])&&N.nodeType==1){a=N.getAttribute("id");
N.setAttribute("id","__mce")
}else{if(d.dom.getParent(S.startContainer,function(I){return I===R
})){b=S.startOffset;
i=S.endOffset;
Q=W.find(R,0,S.startContainer);
e=W.find(R,0,S.endContainer)
}}}else{h=X.body.createTextRange();
h.moveToElementText(R);
h.collapse(1);
O=h.move("character",V)*-1;
h=S.duplicate();
h.collapse(1);
c=h.move("character",V)*-1;
h=S.duplicate();
h.collapse(0);
P=(h.move("character",V)*-1)-c;
Q=c-O;
e=P
}}g=d.dom.create(d.settings.forced_root_block);
g.appendChild(T.cloneNode(1));
T.parentNode.replaceChild(g,T)
}}else{if(g.hasChildNodes()){g.insertBefore(T,g.firstChild)
}else{g.appendChild(T)
}}}else{g=null
}}if(Q!=-2){if(!G){g=R.getElementsByTagName(d.settings.element)[0];
S=X.createRange();
if(Q!=-1){S.setStart(W.find(R,1,Q),b)
}else{S.setStart(g,0)
}if(e!=-1){S.setEnd(W.find(R,1,e),i)
}else{S.setEnd(g,0)
}if(U){U.removeAllRanges();
U.addRange(S)
}}else{try{S=U.createRange();
S.moveToElementText(R);
S.collapse(1);
S.moveStart("character",Q);
S.moveEnd("character",e);
S.select()
}catch(Z){}}}else{if(!G&&(N=d.dom.get("__mce"))){if(a){N.setAttribute("id",a)
}else{N.removeAttribute("id")
}S=X.createRange();
S.setStartBefore(N);
S.setEndBefore(N);
M.setRng(S)
}}},getParentBlock:function(I){var J=this.dom;
return J.getParent(I,J.isBlock)
},insertPara:function(X){var AD=this,AE=AD.editor,d=AE.dom,W=AE.getDoc(),S=AE.settings,AB=AE.selection.getSel(),y=AB.getRangeAt(0),T=W.body;
var r,h,t,a,b,AG,AI,AF,AA,AJ,c,U,AH,AC,s,f=d.getViewPort(AE.getWin()),e,Z,g;
r=W.createRange();
r.setStart(AB.anchorNode,AB.anchorOffset);
r.collapse(true);
h=W.createRange();
h.setStart(AB.focusNode,AB.focusOffset);
h.collapse(true);
t=r.compareBoundaryPoints(r.START_TO_END,h)<0;
a=t?AB.anchorNode:AB.focusNode;
b=t?AB.anchorOffset:AB.focusOffset;
AG=t?AB.focusNode:AB.anchorNode;
AI=t?AB.focusOffset:AB.anchorOffset;
if(a===AG&&/^(TD|TH)$/.test(a.nodeName)){if(a.firstChild.nodeName=="BR"){d.remove(a.firstChild)
}if(a.childNodes.length==0){AE.dom.add(a,S.element,null,"<br />");
U=AE.dom.add(a,S.element,null,"<br />")
}else{s=a.innerHTML;
a.innerHTML="";
AE.dom.add(a,S.element,null,s);
U=AE.dom.add(a,S.element,null,"<br />")
}y=W.createRange();
y.selectNodeContents(U);
y.collapse(1);
AE.selection.setRng(y);
return false
}if(a==T&&AG==T&&T.firstChild&&AE.dom.isBlock(T.firstChild)){a=AG=a.firstChild;
b=AI=0;
r=W.createRange();
r.setStart(a,0);
h=W.createRange();
h.setStart(AG,0)
}a=a.nodeName=="HTML"?W.body:a;
a=a.nodeName=="BODY"?a.firstChild:a;
AG=AG.nodeName=="HTML"?W.body:AG;
AG=AG.nodeName=="BODY"?AG.firstChild:AG;
AF=AD.getParentBlock(a);
AA=AD.getParentBlock(AG);
AJ=AF?AF.nodeName:S.element;
if(AD.dom.getParent(AF,"ol,ul,pre")){return true
}if(AF&&(AF.nodeName=="CAPTION"||/absolute|relative|fixed/gi.test(d.getStyle(AF,"position",1)))){AJ=S.element;
AF=null
}if(AA&&(AA.nodeName=="CAPTION"||/absolute|relative|fixed/gi.test(d.getStyle(AF,"position",1)))){AJ=S.element;
AA=null
}if(/(TD|TABLE|TH|CAPTION)/.test(AJ)||(AF&&AJ=="DIV"&&/left|right/gi.test(d.getStyle(AF,"float",1)))){AJ=S.element;
AF=AA=null
}c=(AF&&AF.nodeName==AJ)?AF.cloneNode(0):AE.dom.create(AJ);
U=(AA&&AA.nodeName==AJ)?AA.cloneNode(0):AE.dom.create(AJ);
U.removeAttribute("id");
if(/^(H[1-6])$/.test(AJ)&&a.nodeValue&&b==a.nodeValue.length){U=AE.dom.create(S.element)
}s=AH=a;
do{if(s==T||s.nodeType==9||AD.dom.isBlock(s)||/(TD|TABLE|TH|CAPTION)/.test(s.nodeName)){break
}AH=s
}while((s=s.previousSibling?s.previousSibling:s.parentNode));
s=AC=AG;
do{if(s==T||s.nodeType==9||AD.dom.isBlock(s)||/(TD|TABLE|TH|CAPTION)/.test(s.nodeName)){break
}AC=s
}while((s=s.nextSibling?s.nextSibling:s.parentNode));
if(AH.nodeName==AJ){r.setStart(AH,0)
}else{r.setStartBefore(AH)
}r.setEnd(a,b);
c.appendChild(r.cloneContents()||W.createTextNode(""));
try{h.setEndAfter(AC)
}catch(Y){}h.setStart(AG,AI);
U.appendChild(h.cloneContents()||W.createTextNode(""));
y=W.createRange();
if(!AH.previousSibling&&AH.parentNode.nodeName==AJ){y.setStartBefore(AH.parentNode)
}else{if(r.startContainer.nodeName==AJ&&r.startOffset==0){y.setStartBefore(r.startContainer)
}else{y.setStart(r.startContainer,r.startOffset)
}}if(!AC.nextSibling&&AC.parentNode.nodeName==AJ){y.setEndAfter(AC.parentNode)
}else{y.setEnd(h.endContainer,h.endOffset)
}y.deleteContents();
if(H){AE.getWin().scrollTo(0,f.y)
}if(c.firstChild&&c.firstChild.nodeName==AJ){c.innerHTML=c.firstChild.innerHTML
}if(U.firstChild&&U.firstChild.nodeName==AJ){U.innerHTML=U.firstChild.innerHTML
}if(D(c)){c.innerHTML="<br />"
}function V(N,L){var M=[],I,K,J;
N.innerHTML="";
if(S.keep_styles){K=L;
do{if(/^(SPAN|STRONG|B|EM|I|FONT|STRIKE|U)$/.test(K.nodeName)){I=K.cloneNode(false);
d.setAttrib(I,"id","");
M.push(I)
}}while(K=K.parentNode)
}if(M.length>0){for(J=M.length-1,I=N;
J>=0;
J--){I=I.appendChild(M[J])
}M[0].innerHTML=H?"&nbsp;":"<br />";
return M[0]
}else{N.innerHTML=H?"&nbsp;":"<br />"
}}if(D(U)){g=V(U,AG)
}if(H&&parseFloat(opera.version())<9.5){y.insertNode(c);
y.insertNode(U)
}else{y.insertNode(U);
y.insertNode(c)
}U.normalize();
c.normalize();
function n(I){return W.createTreeWalker(I,NodeFilter.SHOW_TEXT,null,false).nextNode()||I
}y=W.createRange();
y.selectNodeContents(B?n(g||U):g||U);
y.collapse(1);
AB.removeAllRanges();
AB.addRange(y);
e=AE.dom.getPos(U).y;
Z=U.clientHeight;
if(e<f.y||e+Z>f.y+f.h){AE.getWin().scrollTo(0,e<f.y?e:e-f.h+25)
}return false
},backspaceDelete:function(M,S){var Q=this,N=Q.editor,J=N.getBody(),O,L=N.selection,P=L.getRng(),K=P.startContainer,O,I,T;
if(K&&N.dom.isBlock(K)&&!/^(TD|TH)$/.test(K.nodeName)&&S){if(K.childNodes.length==0||(K.childNodes.length==1&&K.firstChild.nodeName=="BR")){O=K;
while((O=O.previousSibling)&&!N.dom.isBlock(O)){}if(O){if(K!=J.firstChild){I=N.dom.doc.createTreeWalker(O,NodeFilter.SHOW_TEXT,null,false);
while(T=I.nextNode()){O=T
}P=N.getDoc().createRange();
P.setStart(O,O.nodeValue?O.nodeValue.length:0);
P.setEnd(O,O.nodeValue?O.nodeValue.length:0);
L.setRng(P);
N.dom.remove(K)
}return A.cancel(M)
}}}function R(V){var U;
V=V.target;
if(V&&V.parentNode&&V.nodeName=="BR"&&(O=Q.getParentBlock(V))){U=V.previousSibling;
A.remove(J,"DOMNodeInserted",R);
if(U&&U.nodeType==3&&/\s+$/.test(U.nodeValue)){return 
}if(V.previousSibling||V.nextSibling){N.dom.remove(V)
}}}A._add(J,"DOMNodeInserted",R);
window.setTimeout(function(){A._remove(J,"DOMNodeInserted",R)
},1)
}})
})(tinymce);
(function(E){var A=E.DOM,B=E.dom.Event,D=E.each,C=E.extend;
E.create("tinymce.ControlManager",{ControlManager:function(I,F){var G=this,H;
F=F||{};
G.editor=I;
G.controls={};
G.onAdd=new E.util.Dispatcher(G);
G.onPostRender=new E.util.Dispatcher(G);
G.prefix=F.prefix||I.id+"_";
G._cls={};
G.onPostRender.add(function(){D(G.controls,function(J){J.postRender()
})
})
},get:function(F){return this.controls[this.prefix+F]||this.controls[F]
},setActive:function(F,H){var G=null;
if(G=this.get(F)){G.setActive(H)
}return G
},setDisabled:function(F,H){var G=null;
if(G=this.get(F)){G.setDisabled(H)
}return G
},add:function(F){var G=this;
if(F){G.controls[F.id]=F;
G.onAdd.dispatch(F,G)
}return F
},createControl:function(F){var G,H=this,I=H.editor;
D(I.plugins,function(J){if(J.createControl){G=J.createControl(F,H);
if(G){return false
}}});
switch(F){case"|":case"separator":return H.createSeparator()
}if(!G&&I.buttons&&(G=I.buttons[F])){return H.createButton(F,G)
}return H.add(G)
},createDropMenu:function(N,F,L){var G=this,K=G.editor,J,M,I,H;
F=C({"class":"mceDropDown",constrain:K.settings.constrain_menus},F);
F["class"]=F["class"]+" "+K.getParam("skin")+"Skin";
if(I=K.getParam("skin_variant")){F["class"]+=" "+K.getParam("skin")+"Skin"+I.substring(0,1).toUpperCase()+I.substring(1)
}N=G.prefix+N;
H=L||G._cls.dropmenu||E.ui.DropMenu;
J=G.controls[N]=new H(N,F);
J.onAddItem.add(function(O,P){var Q=P.settings;
Q.title=K.getLang(Q.title,Q.title);
if(!Q.onclick){Q.onclick=function(R){K.execCommand(Q.cmd,Q.ui||false,Q.value)
}
}});
K.onRemove.add(function(){J.destroy()
});
if(E.isIE){J.onShowMenu.add(function(){K.focus();
M=K.selection.getBookmark(1)
});
J.onHideMenu.add(function(){if(M){K.selection.moveToBookmark(M);
M=0
}})
}return G.add(J)
},createListBox:function(F,J,G){var K=this,L=K.editor,I,H,M;
if(K.get(F)){return null
}J.title=L.translate(J.title);
J.scope=J.scope||L;
if(!J.onselect){J.onselect=function(N){L.execCommand(J.cmd,J.ui||false,N||J.value)
}
}J=C({title:J.title,"class":"mce_"+F,scope:J.scope,control_manager:K},J);
F=K.prefix+F;
if(L.settings.use_native_selects){H=new E.ui.NativeListBox(F,J)
}else{M=G||K._cls.listbox||E.ui.ListBox;
H=new M(F,J)
}K.controls[F]=H;
if(E.isWebKit){H.onPostRender.add(function(N,O){B.add(O,"mousedown",function(){L.bookmark=L.selection.getBookmark("simple")
});
B.add(O,"focus",function(){L.selection.moveToBookmark(L.bookmark);
L.bookmark=null
})
})
}if(H.hideMenu){L.onMouseDown.add(H.hideMenu,H)
}return K.add(H)
},createButton:function(F,J,G){var K=this,L=K.editor,I,H,M;
if(K.get(F)){return null
}J.title=L.translate(J.title);
J.label=L.translate(J.label);
J.scope=J.scope||L;
if(!J.onclick&&!J.menu_button){J.onclick=function(){L.execCommand(J.cmd,J.ui||false,J.value)
}
}J=C({title:J.title,"class":"mce_"+F,unavailable_prefix:L.getLang("unavailable",""),scope:J.scope,control_manager:K},J);
F=K.prefix+F;
if(J.menu_button){M=G||K._cls.menubutton||E.ui.MenuButton;
H=new M(F,J);
L.onMouseDown.add(H.hideMenu,H)
}else{M=K._cls.button||E.ui.Button;
H=new M(F,J)
}return K.add(H)
},createMenuButton:function(F,H,G){H=H||{};
H.menu_button=1;
return this.createButton(F,H,G)
},createSplitButton:function(F,J,G){var K=this,L=K.editor,I,H,M;
if(K.get(F)){return null
}J.title=L.translate(J.title);
J.scope=J.scope||L;
if(!J.onclick){J.onclick=function(N){L.execCommand(J.cmd,J.ui||false,N||J.value)
}
}if(!J.onselect){J.onselect=function(N){L.execCommand(J.cmd,J.ui||false,N||J.value)
}
}J=C({title:J.title,"class":"mce_"+F,scope:J.scope,control_manager:K},J);
F=K.prefix+F;
M=G||K._cls.splitbutton||E.ui.SplitButton;
H=K.add(new M(F,J));
L.onMouseDown.add(H.hideMenu,H);
return H
},createColorSplitButton:function(N,F,L){var H=this,J=H.editor,K,I,G,M;
if(H.get(N)){return null
}F.title=J.translate(F.title);
F.scope=F.scope||J;
if(!F.onclick){F.onclick=function(O){if(E.isIE){M=J.selection.getBookmark(1)
}J.execCommand(F.cmd,F.ui||false,O||F.value)
}
}if(!F.onselect){F.onselect=function(O){J.execCommand(F.cmd,F.ui||false,O||F.value)
}
}F=C({title:F.title,"class":"mce_"+N,menu_class:J.getParam("skin")+"Skin",scope:F.scope,more_colors_title:J.getLang("more_colors")},F);
N=H.prefix+N;
G=L||H._cls.colorsplitbutton||E.ui.ColorSplitButton;
I=new G(N,F);
J.onMouseDown.add(I.hideMenu,I);
J.onRemove.add(function(){I.destroy()
});
if(E.isIE){I.onShowMenu.add(function(){J.focus();
M=J.selection.getBookmark(1)
});
I.onHideMenu.add(function(){if(M){J.selection.moveToBookmark(M);
M=0
}})
}return H.add(I)
},createToolbar:function(F,I,G){var H,J=this,K;
F=J.prefix+F;
K=G||J._cls.toolbar||E.ui.Toolbar;
H=new K(F,I);
if(J.get(F)){return null
}return J.add(H)
},createSeparator:function(F){var G=F||this._cls.separator||E.ui.Separator;
return new G()
},setControlType:function(F,G){return this._cls[F.toLowerCase()]=G
},destroy:function(){D(this.controls,function(F){F.destroy()
});
this.controls=null
}})
})(tinymce);
(function(D){var B=D.util.Dispatcher,C=D.each,E=D.isIE,A=D.isOpera;
D.create("tinymce.WindowManager",{WindowManager:function(G){var F=this;
F.editor=G;
F.onOpen=new B(F);
F.onClose=new B(F);
F.params={};
F.features={}
},open:function(Q,O){var R=this,L="",I,J,N=R.editor.settings.dialog_type=="modal",G,H,M,P=D.DOM.getViewPort(),F;
Q=Q||{};
O=O||{};
H=A?P.w:screen.width;
M=A?P.h:screen.height;
Q.name=Q.name||"mc_"+new Date().getTime();
Q.width=parseInt(Q.width||320);
Q.height=parseInt(Q.height||240);
Q.resizable=true;
Q.left=Q.left||parseInt(H/2)-(Q.width/2);
Q.top=Q.top||parseInt(M/2)-(Q.height/2);
O.inline=false;
O.mce_width=Q.width;
O.mce_height=Q.height;
O.mce_auto_focus=Q.auto_focus;
if(N){if(E){Q.center=true;
Q.help=false;
Q.dialogWidth=Q.width+"px";
Q.dialogHeight=Q.height+"px";
Q.scroll=Q.scrollbars||false
}}C(Q,function(T,S){if(D.is(T,"boolean")){T=T?"yes":"no"
}if(!/^(name|url)$/.test(S)){if(E&&N){L+=(L?";":"")+S+":"+T
}else{L+=(L?",":"")+S+"="+T
}}});
R.features=Q;
R.params=O;
R.onOpen.dispatch(R,Q,O);
F=Q.url||Q.file;
F=D._addVer(F);
try{if(E&&N){G=1;
window.showModalDialog(F,window,L)
}else{G=window.open(F,Q.name,L)
}}catch(K){}if(!G){alert(R.editor.getLang("popup_blocked"))
}},close:function(F){F.close();
this.onClose.dispatch(this)
},createInstance:function(J,K,L,F,G,H){var I=D.resolve(J);
return new I(K,L,F,G,H)
},confirm:function(G,I,F,H){H=H||window;
I.call(F||this,H.confirm(this._decode(this.editor.getLang(G,G))))
},alert:function(H,J,F,I){var G=this;
I=I||window;
I.alert(G._decode(G.editor.getLang(H,H)));
if(J){J.call(F||G)
}},_decode:function(F){return D.DOM.decode(F).replace(/\\n/g,"\n")
}})
}(tinymce));
(function(A){A.CommandManager=function(){var E={},B={},D={};
function C(F,G,H,I){if(typeof (G)=="string"){G=[G]
}A.each(G,function(J){F[J.toLowerCase()]={func:H,scope:I}
})
}A.extend(this,{add:function(F,G,H){C(E,F,G,H)
},addQueryStateHandler:function(F,G,H){C(B,F,G,H)
},addQueryValueHandler:function(F,G,H){C(D,F,G,H)
},execCommand:function(I,F,G,H,J){if(F=E[F.toLowerCase()]){if(F.func.call(I||F.scope,G,H,J)!==false){return true
}}},queryCommandValue:function(){if(cmd=D[cmd.toLowerCase()]){return cmd.func.call(scope||cmd.scope,ui,value,args)
}},queryCommandState:function(){if(cmd=B[cmd.toLowerCase()]){return cmd.func.call(scope||cmd.scope,ui,value,args)
}}})
};
A.GlobalCommands=new A.CommandManager()
})(tinymce);
(function(A){function B(G,L,H,C){var F,I,K,D,J;
function E(N,O){do{if(N.parentNode==O){return N
}N=N.parentNode
}while(N)
}function M(N){C(N);
A.walk(N,C,"childNodes")
}F=G.findCommonAncestor(L,H);
K=E(L,F)||L;
D=E(H,F)||H;
for(I=L;
I&&I!=K;
I=I.parentNode){for(J=I.nextSibling;
J;
J=J.nextSibling){M(J)
}}if(K!=D){for(I=K.nextSibling;
I&&I!=D;
I=I.nextSibling){M(I)
}}else{M(K)
}for(I=H;
I&&I!=D;
I=I.parentNode){for(J=I.previousSibling;
J;
J=J.previousSibling){M(J)
}}}A.GlobalCommands.add("RemoveFormat",function(){var F=this,G=F.dom,Q=F.selection,O=Q.getRng(1),N=[],K,M,I,C,L,E,P,J;
function H(S){var T;
G.getParent(S,function(U){if(G.is(U,F.getParam("removeformat_selector"))){T=U
}return G.isBlock(U)
},F.getBody());
return T
}function D(S){if(G.is(S,F.getParam("removeformat_selector"))){N.push(S)
}}function R(S){D(S);
A.walk(S,D,"childNodes")
}K=Q.getBookmark();
C=O.startContainer;
E=O.endContainer;
L=O.startOffset;
P=O.endOffset;
C=C.nodeType==1?C.childNodes[Math.min(L,C.childNodes.length-1)]:C;
E=E.nodeType==1?E.childNodes[Math.min(L==P?P:P-1,E.childNodes.length-1)]:E;
if(C==E){M=H(C);
if(C.nodeType==3){if(M&&M.nodeType==1){J=C.splitText(L);
J.splitText(P-L);
G.split(M,J);
Q.moveToBookmark(K)
}return 
}R(G.split(M,C)||C)
}else{M=H(C);
I=H(E);
if(M){if(C.nodeType==3){if(L==C.nodeValue.length){C.nodeValue+="\uFEFF"
}C=C.splitText(L)
}}if(I){if(E.nodeType==3){E.splitText(P)
}}if(M&&M==I){G.replace(G.create("span",{id:"__end"},E.cloneNode(true)),E)
}if(M){M=G.split(M,C)
}else{M=C
}if(J=G.get("__end")){E=J;
I=H(E)
}if(I){I=G.split(I,E)
}else{I=E
}B(G,M,I,D);
if(C.nodeValue=="\uFEFF"){C.nodeValue=""
}R(E);
R(C)
}A.each(N,function(S){G.remove(S,1)
});
G.remove("__end",1);
Q.moveToBookmark(K)
})
})(tinymce);
(function(A){A.GlobalCommands.add("mceBlockQuote",function(){var G=this,C=G.selection,J=G.dom,E,F,K,L,B,M,D,H,N;
function I(O){return J.getParent(O,function(P){return P.nodeName==="BLOCKQUOTE"
})
}E=J.getParent(C.getStart(),J.isBlock);
F=J.getParent(C.getEnd(),J.isBlock);
if(B=I(E)){if(E!=F||E.childNodes.length>1||(E.childNodes.length==1&&E.firstChild.nodeName!="BR")){L=C.getBookmark()
}if(I(F)){D=B.cloneNode(false);
while(K=F.nextSibling){D.appendChild(K.parentNode.removeChild(K))
}}if(D){J.insertAfter(D,B)
}N=C.getSelectedBlocks(E,F);
for(H=N.length-1;
H>=0;
H--){J.insertAfter(N[H],B)
}if(/^\s*$/.test(B.innerHTML)){J.remove(B,1)
}if(D&&/^\s*$/.test(D.innerHTML)){J.remove(D,1)
}if(!L){if(!A.isIE){M=G.getDoc().createRange();
M.setStart(E,0);
M.setEnd(E,0);
C.setRng(M)
}else{C.select(E);
C.collapse(0);
if(J.getParent(C.getStart(),J.isBlock)!=E){M=C.getRng();
M.move("character",-1);
M.select()
}}}else{G.selection.moveToBookmark(L)
}return 
}if(A.isIE&&!E&&!F){G.getDoc().execCommand("Indent");
K=I(C.getNode());
K.style.margin=K.dir="";
return 
}if(!E||!F){return 
}if(E!=F||E.childNodes.length>1||(E.childNodes.length==1&&E.firstChild.nodeName!="BR")){L=C.getBookmark()
}A.each(C.getSelectedBlocks(I(C.getStart()),I(C.getEnd())),function(O){if(O.nodeName=="BLOCKQUOTE"&&!B){B=O;
return 
}if(!B){B=J.create("blockquote");
O.parentNode.insertBefore(B,O)
}if(O.nodeName=="BLOCKQUOTE"&&B){K=O.firstChild;
while(K){B.appendChild(K.cloneNode(true));
K=K.nextSibling
}J.remove(O);
return 
}B.appendChild(J.remove(O))
});
if(!L){if(!A.isIE){M=G.getDoc().createRange();
M.setStart(E,0);
M.setEnd(E,0);
C.setRng(M)
}else{C.select(E);
C.collapse(1)
}}else{C.moveToBookmark(L)
}})
})(tinymce);
(function(A){A.each(["Cut","Copy","Paste"],function(B){A.GlobalCommands.add(B,function(){var E=this,C=E.getDoc();
try{C.execCommand(B,false,null);
if(!C.queryCommandSupported(B)){throw"Error"
}}catch(D){E.windowManager.alert(E.getLang("clipboard_no_support"))
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