var Validator={isEmail:function(A){return this.test(A,"^[-!#$%&'*+\\./0-9=?A-Z^_`a-z{|}~]+@[-!#$%&'*+\\/0-9=?A-Z^_`a-z{|}~]+.[-!#$%&'*+\\./0-9=?A-Z^_`a-z{|}~]+$")
},isAbsUrl:function(A){return this.test(A,"^(news|telnet|nttp|file|http|ftp|https)://[-A-Za-z0-9\\.]+\\/?.*$")
},isSize:function(A){return this.test(A,"^[0-9]+(%|in|cm|mm|em|ex|pt|pc|px)?$")
},isId:function(A){return this.test(A,"^[A-Za-z_]([A-Za-z0-9_])*$")
},isEmpty:function(C){var A,B;
if(C.nodeName=="SELECT"&&C.selectedIndex<1){return true
}if(C.type=="checkbox"&&!C.checked){return true
}if(C.type=="radio"){for(B=0,A=C.form.elements;
B<A.length;
B++){if(A[B].type=="radio"&&A[B].name==C.name&&A[B].checked){return false
}}return true
}return new RegExp("^\\s*$").test(C.nodeType==1?C.value:C)
},isNumber:function(A,B){return !isNaN(A.nodeType==1?A.value:A)&&(!B||!this.test(A,"^-?[0-9]*\\.[0-9]*$"))
},test:function(A,B){A=A.nodeType==1?A.value:A;
return A==""||new RegExp(B).test(A)
}};
var AutoValidator={settings:{id_cls:"id",int_cls:"int",url_cls:"url",number_cls:"number",email_cls:"email",size_cls:"size",required_cls:"required",invalid_cls:"invalid",min_cls:"min",max_cls:"max"},init:function(A){var B;
for(B in A){this.settings[B]=A[B]
}},validate:function(D){var B,A,C=this.settings,E=0;
A=this.tags(D,"label");
for(B=0;
B<A.length;
B++){this.removeClass(A[B],C.invalid_cls)
}E+=this.validateElms(D,"input");
E+=this.validateElms(D,"select");
E+=this.validateElms(D,"textarea");
return E==3
},invalidate:function(A){this.mark(A.form,A)
},reset:function(F){var D=["label","input","select","textarea"];
var C,B,A,E=this.settings;
if(F==null){return 
}for(C=0;
C<D.length;
C++){A=this.tags(F.form?F.form:F,D[C]);
for(B=0;
B<A.length;
B++){this.removeClass(A[B],E.invalid_cls)
}}},validateElms:function(D,E){var A,C,B,I=this.settings,H=true,F=Validator,G;
A=this.tags(D,E);
for(C=0;
C<A.length;
C++){B=A[C];
this.removeClass(B,I.invalid_cls);
if(this.hasClass(B,I.required_cls)&&F.isEmpty(B)){H=this.mark(D,B)
}if(this.hasClass(B,I.number_cls)&&!F.isNumber(B)){H=this.mark(D,B)
}if(this.hasClass(B,I.int_cls)&&!F.isNumber(B,true)){H=this.mark(D,B)
}if(this.hasClass(B,I.url_cls)&&!F.isAbsUrl(B)){H=this.mark(D,B)
}if(this.hasClass(B,I.email_cls)&&!F.isEmail(B)){H=this.mark(D,B)
}if(this.hasClass(B,I.size_cls)&&!F.isSize(B)){H=this.mark(D,B)
}if(this.hasClass(B,I.id_cls)&&!F.isId(B)){H=this.mark(D,B)
}if(this.hasClass(B,I.min_cls,true)){G=this.getNum(B,I.min_cls);
if(isNaN(G)||parseInt(B.value)<parseInt(G)){H=this.mark(D,B)
}}if(this.hasClass(B,I.max_cls,true)){G=this.getNum(B,I.max_cls);
if(isNaN(G)||parseInt(B.value)>parseInt(G)){H=this.mark(D,B)
}}}return H
},hasClass:function(C,B,A){return new RegExp("\\b"+B+(A?"[0-9]+":"")+"\\b","g").test(C.className)
},getNum:function(B,A){A=B.className.match(new RegExp("\\b"+A+"([0-9]+)\\b","g"))[0];
A=A.replace(/[^0-9]/g,"");
return A
},addClass:function(D,C,A){var B=this.removeClass(D,C);
D.className=A?C+(B!=""?(" "+B):""):(B!=""?(B+" "):"")+C
},removeClass:function(B,A){A=B.className.replace(new RegExp("(^|\\s+)"+A+"(\\s+|$)")," ");
return B.className=A!=" "?A:""
},tags:function(B,A){return B.getElementsByTagName(A)
},mark:function(B,C){var A=this.settings;
this.addClass(C,A.invalid_cls);
this.markLabels(B,C,A.invalid_cls);
return false
},markLabels:function(D,E,A){var B,C;
B=this.tags(D,"label");
for(C=0;
C<B.length;
C++){if(B[C].getAttribute("for")==E.id||B[C].htmlFor==E.id){this.addClass(B[C],A)
}}return null
}};