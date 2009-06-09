tinyMCEPopup.requireLangPack();
var ExampleDialog={init:function(){var A=document.forms[0];
A.someval.value=tinyMCEPopup.editor.selection.getContent({format:"text"});
A.somearg.value=tinyMCEPopup.getWindowArg("some_custom_arg")
},insert:function(){tinyMCEPopup.editor.execCommand("mceInsertContent",false,document.forms[0].someval.value);
tinyMCEPopup.close()
}};
tinyMCEPopup.onInit.add(ExampleDialog.init,ExampleDialog);