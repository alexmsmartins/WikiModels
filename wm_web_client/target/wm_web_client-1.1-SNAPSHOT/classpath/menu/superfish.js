(function(B){B.fn.superfish=function(J){var F=B.fn.superfish,I=F.c,E=B(['<span class="',I.arrowClass,'"> &#187;</span>'].join("")),H=function(){var K=B(this),L=C(K);
clearTimeout(L.sfTimer);
K.showSuperfishUl().siblings().hideSuperfishUl()
},D=function(){var K=B(this),M=C(K),L=F.op;
clearTimeout(M.sfTimer);
M.sfTimer=setTimeout(function(){L.retainPath=(B.inArray(K[0],L.$path)>-1);
K.hideSuperfishUl();
if(L.$path.length&&K.parents(["li.",L.hoverClass].join("")).length<1){H.call(L.$path)
}},L.delay)
},C=function(K){var L=K.parents(["ul.",I.menuClass,":first"].join(""))[0];
F.op=F.o[L.serial];
return L
},G=function(K){K.addClass(I.anchorClass).append(E.clone())
};
return this.each(function(){var K=this.serial=F.o.length;
var M=B.extend({},F.defaults,J);
M.$path=B("li."+M.pathClass,this).slice(0,M.pathLevels).each(function(){B(this).addClass([M.hoverClass,I.bcClass].join(" ")).filter("li:has(ul)").removeClass(M.pathClass)
});
F.o[K]=F.op=M;
B("li:has(ul)",this)[(B.fn.hoverIntent&&!M.disableHI)?"hoverIntent":"hover"](H,D).each(function(){if(M.autoArrows){G(B(">a:first-child",this))
}}).not("."+I.bcClass).hideSuperfishUl();
var L=B("a",this);
L.each(function(N){var O=L.eq(N).parents("li");
L.eq(N).focus(function(){H.call(O)
}).blur(function(){D.call(O)
})
});
M.onInit.call(this)
}).each(function(){var K=[I.menuClass];
if(F.op.dropShadows&&!(B.browser.msie&&B.browser.version<7)){K.push(I.shadowClass)
}B(this).addClass(K.join(" "))
})
};
var A=B.fn.superfish;
A.o=[];
A.op={};
A.IE7fix=function(){var C=A.op;
if(B.browser.msie&&B.browser.version>6&&C.dropShadows&&C.animation.opacity!=undefined){this.toggleClass(A.c.shadowClass+"-off")
}};
A.c={bcClass:"sf-breadcrumb",menuClass:"sf-js-enabled",anchorClass:"sf-with-ul",arrowClass:"sf-sub-indicator",shadowClass:"sf-shadow"};
A.defaults={hoverClass:"sfHover",pathClass:"overideThisToUse",pathLevels:1,delay:800,animation:{opacity:"show"},speed:"normal",autoArrows:true,dropShadows:true,disableHI:false,onInit:function(){},onBeforeShow:function(){},onShow:function(){},onHide:function(){}};
B.fn.extend({hideSuperfishUl:function(){var E=A.op,D=(E.retainPath===true)?E.$path:"";
E.retainPath=false;
var C=B(["li.",E.hoverClass].join(""),this).add(this).not(D).removeClass(E.hoverClass).find(">ul").hide().css("visibility","hidden");
E.onHide.call(C);
return this
},showSuperfishUl:function(){var E=A.op,D=A.c.shadowClass+"-off",C=this.addClass(E.hoverClass).find(">ul:hidden").css("visibility","visible");
A.IE7fix.call(C);
E.onBeforeShow.call(C);
C.animate(E.animation,E.speed,function(){A.IE7fix.call(C);
E.onShow.call(C)
});
return this
}})
})(jQuery);