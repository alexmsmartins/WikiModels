(function(){tinymce.create("tinymce.plugins.Layer",{init:function(B,A){var C=this;
C.editor=B;
B.addCommand("mceInsertLayer",C._insertLayer,C);
B.addCommand("mceMoveForward",function(){C._move(1)
});
B.addCommand("mceMoveBackward",function(){C._move(-1)
});
B.addCommand("mceMakeAbsolute",function(){C._toggleAbsolute()
});
B.addButton("moveforward",{title:"layer.forward_desc",cmd:"mceMoveForward"});
B.addButton("movebackward",{title:"layer.backward_desc",cmd:"mceMoveBackward"});
B.addButton("absolute",{title:"layer.absolute_desc",cmd:"mceMakeAbsolute"});
B.addButton("insertlayer",{title:"layer.insertlayer_desc",cmd:"mceInsertLayer"});
B.onInit.add(function(){if(tinymce.isIE){B.getDoc().execCommand("2D-Position",false,true)
}});
B.onNodeChange.add(C._nodeChange,C);
B.onVisualAid.add(C._visualAid,C)
},getInfo:function(){return{longname:"Layer",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/layer",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_nodeChange:function(A,B,C){var E,D;
E=this._getParentLayer(C);
D=A.dom.getParent(C,"DIV,P,IMG");
if(!D){B.setDisabled("absolute",1);
B.setDisabled("moveforward",1);
B.setDisabled("movebackward",1)
}else{B.setDisabled("absolute",0);
B.setDisabled("moveforward",!E);
B.setDisabled("movebackward",!E);
B.setActive("absolute",E&&E.style.position.toLowerCase()=="absolute")
}},_visualAid:function(B,D,A){var C=B.dom;
tinymce.each(C.select("div,p",D),function(E){if(/^(absolute|relative|static)$/i.test(E.style.position)){if(A){C.addClass(E,"mceItemVisualAid")
}else{C.removeClass(E,"mceItemVisualAid")
}}})
},_move:function(D){var A=this.editor,F,E=[],G=this._getParentLayer(A.selection.getNode()),H=-1,C=-1,B;
B=[];
tinymce.walk(A.getBody(),function(I){if(I.nodeType==1&&/^(absolute|relative|static)$/i.test(I.style.position)){B.push(I)
}},"childNodes");
for(F=0;
F<B.length;
F++){E[F]=B[F].style.zIndex?parseInt(B[F].style.zIndex):0;
if(H<0&&B[F]==G){H=F
}}if(D<0){for(F=0;
F<E.length;
F++){if(E[F]<E[H]){C=F;
break
}}if(C>-1){B[H].style.zIndex=E[C];
B[C].style.zIndex=E[H]
}else{if(E[H]>0){B[H].style.zIndex=E[H]-1
}}}else{for(F=0;
F<E.length;
F++){if(E[F]>E[H]){C=F;
break
}}if(C>-1){B[H].style.zIndex=E[C];
B[C].style.zIndex=E[H]
}else{B[H].style.zIndex=E[H]+1
}}A.execCommand("mceRepaint")
},_getParentLayer:function(A){return this.editor.dom.getParent(A,function(B){return B.nodeType==1&&/^(absolute|relative|static)$/i.test(B.style.position)
})
},_insertLayer:function(){var B=this.editor,A=B.dom.getPos(B.dom.getParent(B.selection.getNode(),"*"));
B.dom.add(B.getBody(),"div",{style:{position:"absolute",left:A.x,top:(A.y>20?A.y:20),width:100,height:100},"class":"mceItemVisualAid"},B.selection.getContent()||B.getLang("layer.content"))
},_toggleAbsolute:function(){var B=this.editor,A=this._getParentLayer(B.selection.getNode());
if(!A){A=B.dom.getParent(B.selection.getNode(),"DIV,P,IMG")
}if(A){if(A.style.position.toLowerCase()=="absolute"){B.dom.setStyles(A,{position:"",left:"",top:"",width:"",height:""});
B.dom.removeClass(A,"mceItemVisualAid")
}else{if(A.style.left==""){A.style.left=20+"px"
}if(A.style.top==""){A.style.top=20+"px"
}if(A.style.width==""){A.style.width=A.width?(A.width+"px"):"100px"
}if(A.style.height==""){A.style.height=A.height?(A.height+"px"):"100px"
}A.style.position="absolute";
B.addVisual(B.getBody())
}B.execCommand("mceRepaint");
B.nodeChanged()
}}});
tinymce.PluginManager.add("layer",tinymce.plugins.Layer)
})();