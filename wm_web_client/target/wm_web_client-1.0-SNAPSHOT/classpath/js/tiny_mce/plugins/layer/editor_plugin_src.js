(function(){tinymce.create("tinymce.plugins.Layer",{init:function(A,B){var C=this;
C.editor=A;
A.addCommand("mceInsertLayer",C._insertLayer,C);
A.addCommand("mceMoveForward",function(){C._move(1)
});
A.addCommand("mceMoveBackward",function(){C._move(-1)
});
A.addCommand("mceMakeAbsolute",function(){C._toggleAbsolute()
});
A.addButton("moveforward",{title:"layer.forward_desc",cmd:"mceMoveForward"});
A.addButton("movebackward",{title:"layer.backward_desc",cmd:"mceMoveBackward"});
A.addButton("absolute",{title:"layer.absolute_desc",cmd:"mceMakeAbsolute"});
A.addButton("insertlayer",{title:"layer.insertlayer_desc",cmd:"mceInsertLayer"});
A.onInit.add(function(){if(tinymce.isIE){A.getDoc().execCommand("2D-Position",false,true)
}});
A.onNodeChange.add(C._nodeChange,C);
A.onVisualAid.add(C._visualAid,C)
},getInfo:function(){return{longname:"Layer",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/layer",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_nodeChange:function(B,A,E){var C,D;
C=this._getParentLayer(E);
D=B.dom.getParent(E,"DIV,P,IMG");
if(!D){A.setDisabled("absolute",1);
A.setDisabled("moveforward",1);
A.setDisabled("movebackward",1)
}else{A.setDisabled("absolute",0);
A.setDisabled("moveforward",!C);
A.setDisabled("movebackward",!C);
A.setActive("absolute",C&&C.style.position.toLowerCase()=="absolute")
}},_visualAid:function(A,C,B){var D=A.dom;
tinymce.each(D.select("div,p",C),function(E){if(/^(absolute|relative|static)$/i.test(E.style.position)){if(B){D.addClass(E,"mceItemVisualAid")
}else{D.removeClass(E,"mceItemVisualAid")
}}})
},_move:function(G){var B=this.editor,E,F=[],D=this._getParentLayer(B.selection.getNode()),C=-1,H=-1,A;
A=[];
tinymce.walk(B.getBody(),function(I){if(I.nodeType==1&&/^(absolute|relative|static)$/i.test(I.style.position)){A.push(I)
}},"childNodes");
for(E=0;
E<A.length;
E++){F[E]=A[E].style.zIndex?parseInt(A[E].style.zIndex):0;
if(C<0&&A[E]==D){C=E
}}if(G<0){for(E=0;
E<F.length;
E++){if(F[E]<F[C]){H=E;
break
}}if(H>-1){A[C].style.zIndex=F[H];
A[H].style.zIndex=F[C]
}else{if(F[C]>0){A[C].style.zIndex=F[C]-1
}}}else{for(E=0;
E<F.length;
E++){if(F[E]>F[C]){H=E;
break
}}if(H>-1){A[C].style.zIndex=F[H];
A[H].style.zIndex=F[C]
}else{A[C].style.zIndex=F[C]+1
}}B.execCommand("mceRepaint")
},_getParentLayer:function(A){return this.editor.dom.getParent(A,function(B){return B.nodeType==1&&/^(absolute|relative|static)$/i.test(B.style.position)
})
},_insertLayer:function(){var A=this.editor,B=A.dom.getPos(A.dom.getParent(A.selection.getNode(),"*"));
A.dom.add(A.getBody(),"div",{style:{position:"absolute",left:B.x,top:(B.y>20?B.y:20),width:100,height:100},"class":"mceItemVisualAid"},A.selection.getContent()||A.getLang("layer.content"))
},_toggleAbsolute:function(){var A=this.editor,B=this._getParentLayer(A.selection.getNode());
if(!B){B=A.dom.getParent(A.selection.getNode(),"DIV,P,IMG")
}if(B){if(B.style.position.toLowerCase()=="absolute"){A.dom.setStyles(B,{position:"",left:"",top:"",width:"",height:""});
A.dom.removeClass(B,"mceItemVisualAid")
}else{if(B.style.left==""){B.style.left=20+"px"
}if(B.style.top==""){B.style.top=20+"px"
}if(B.style.width==""){B.style.width=B.width?(B.width+"px"):"100px"
}if(B.style.height==""){B.style.height=B.height?(B.height+"px"):"100px"
}B.style.position="absolute";
A.addVisual(A.getBody())
}A.execCommand("mceRepaint");
A.nodeChanged()
}}});
tinymce.PluginManager.add("layer",tinymce.plugins.Layer)
})();