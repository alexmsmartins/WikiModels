(function(A){A.fn.hoverIntent=function(I,H){var J={sensitivity:7,interval:100,timeout:0};
J=A.extend(J,H?{over:I,out:H}:I);
var L,K,F,D;
var E=function(M){L=M.pageX;
K=M.pageY
};
var C=function(N,M){M.hoverIntent_t=clearTimeout(M.hoverIntent_t);
if((Math.abs(F-L)+Math.abs(D-K))<J.sensitivity){A(M).unbind("mousemove",E);
M.hoverIntent_s=1;
return J.over.apply(M,[N])
}else{F=L;
D=K;
M.hoverIntent_t=setTimeout(function(){C(N,M)
},J.interval)
}};
var G=function(N,M){M.hoverIntent_t=clearTimeout(M.hoverIntent_t);
M.hoverIntent_s=0;
return J.out.apply(M,[N])
};
var B=function(P){var O=(P.type=="mouseover"?P.fromElement:P.toElement)||P.relatedTarget;
while(O&&O!=this){try{O=O.parentNode
}catch(P){O=this
}}if(O==this){return false
}var N=jQuery.extend({},P);
var M=this;
if(M.hoverIntent_t){M.hoverIntent_t=clearTimeout(M.hoverIntent_t)
}if(P.type=="mouseover"){F=N.pageX;
D=N.pageY;
A(M).bind("mousemove",E);
if(M.hoverIntent_s!=1){M.hoverIntent_t=setTimeout(function(){C(N,M)
},J.interval)
}}else{A(M).unbind("mousemove",E);
if(M.hoverIntent_s==1){M.hoverIntent_t=setTimeout(function(){G(N,M)
},J.timeout)
}}};
return this.mouseover(B).mouseout(B)
}
})(jQuery);