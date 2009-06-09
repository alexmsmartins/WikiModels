(function(){tinymce.create("tinymce.plugins.StylePlugin",{init:function(A,B){A.addCommand("mceStyleProps",function(){A.windowManager.open({file:B+"/props.htm",width:480+parseInt(A.getLang("style.delta_width",0)),height:320+parseInt(A.getLang("style.delta_height",0)),inline:1},{plugin_url:B,style_text:A.selection.getNode().style.cssText})
});
A.addCommand("mceSetElementStyle",function(D,C){if(e=A.selection.getNode()){A.dom.setAttrib(e,"style",C);
A.execCommand("mceRepaint")
}});
A.onNodeChange.add(function(D,C,E){C.setDisabled("styleprops",E.nodeName==="BODY")
});
A.addButton("styleprops",{title:"style.desc",cmd:"mceStyleProps"})
},getInfo:function(){return{longname:"Style",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/style",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("style",tinymce.plugins.StylePlugin)
})();