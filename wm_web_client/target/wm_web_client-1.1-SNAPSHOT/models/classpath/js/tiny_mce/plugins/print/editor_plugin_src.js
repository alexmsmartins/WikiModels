(function(){tinymce.create("tinymce.plugins.Print",{init:function(A,B){A.addCommand("mcePrint",function(){A.getWin().print()
});
A.addButton("print",{title:"print.print_desc",cmd:"mcePrint"})
},getInfo:function(){return{longname:"Print",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/print",version:tinymce.majorVersion+"."+tinymce.minorVersion}
}});
tinymce.PluginManager.add("print",tinymce.plugins.Print)
})();