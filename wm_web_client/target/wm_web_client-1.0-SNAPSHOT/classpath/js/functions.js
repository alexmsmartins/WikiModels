var DDSPEED=10;
var DDTIMER=15;
function dsp(B){if(document.getElementById){var A=B.firstChild;
A=B.firstChild.innerHTML?B.firstChild:B.firstChild.nextSibling;
A.innerHTML=A.innerHTML=="+"?"-":"+";
A=B.parentNode.nextSibling.style?B.parentNode.nextSibling:B.parentNode.nextSibling.nextSibling;
A.style.display=A.style.display=="block"?"none":"block"
}}if(!document.getElementById){document.write('<style type="text/css"><!--\n.dspcont{display:block;}\n//--></style>')
}function ddMenu(D,B){var A=document.getElementById(D+"-ddheader");
var C=document.getElementById(D+"-ddcontent");
clearInterval(C.timer);
if(B==1){clearTimeout(A.timer);
if(C.maxh&&C.maxh<=C.offsetHeight){return 
}else{if(!C.maxh){C.style.display="block";
C.style.height="auto";
C.maxh=C.offsetHeight;
C.style.height="0px"
}}C.timer=setInterval(function(){ddSlide(C,1)
},DDTIMER)
}else{A.timer=setTimeout(function(){ddCollapse(C)
},50)
}}function ddCollapse(A){A.timer=setInterval(function(){ddSlide(A,-1)
},DDTIMER)
}function cancelHide(C){var A=document.getElementById(C+"-ddheader");
var B=document.getElementById(C+"-ddcontent");
clearTimeout(A.timer);
clearInterval(B.timer);
if(B.offsetHeight<B.maxh){B.timer=setInterval(function(){ddSlide(B,1)
},DDTIMER)
}}function ddSlide(D,C){var B=D.offsetHeight;
var A;
if(C==1){A=(Math.round((D.maxh-B)/DDSPEED))
}else{A=(Math.round(B/DDSPEED))
}if(A<=1&&C==1){A=1
}D.style.height=B+(A*C)+"px";
D.style.opacity=B/D.maxh;
D.style.filter="alpha(opacity="+(B*100/D.maxh)+")";
if((B<2&&C!=1)||(B>(D.maxh-2)&&C==1)){clearInterval(D.timer)
}};