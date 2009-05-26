(function(){tinymce.create("tinymce.plugins.StylePlugin",{init:function(B,A){B.addCommand("mceStyleProps",function(){B.windowManager.open({file:A+"/props.htm",width:480+parseInt(B.getLang("style.delta_width",0)),height:320+parseInt(B.getLang("style.delta_height",0)),inline:1},{plugin_url:A,style_text:B.selection.getNode().style.cssText})
});
B.addCommand("mceSetElementStyle",function(C,D){if(e=B.selection.getNode()){B.dom.setAttrib(e,"style",D);
B.execCommand("mceRepaint")
}});
B.onNodeChange.add(function(D,E,C){E.setDisabled("styleprops",C.nodeName==="BODY")
});
B.addButton("styleprops",{title:"style.desc",cmd:"mceStyleProps"})
},getInfo:function(){return{longname:"Style",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/style",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("style",tinymce.plugins.StylePlugin)
})();