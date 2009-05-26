var TinyMCE_EditableSelects={editSelectElm:null,init:function(){var A=document.getElementsByTagName("select"),B,D=document,C;
for(B=0;
B<A.length;
B++){if(A[B].className.indexOf("mceEditableSelect")!=-1){C=new Option("(value)","__mce_add_custom__");
C.className="mceAddSelectValue";
A[B].options[A[B].options.length]=C;
A[B].onchange=TinyMCE_EditableSelects.onChangeEditableSelect
}}},onChangeEditableSelect:function(C){var D=document,B,A=window.event?window.event.srcElement:C.target;
if(A.options[A.selectedIndex].value=="__mce_add_custom__"){B=D.createElement("input");
B.id=A.id+"_custom";
B.name=A.name+"_custom";
B.type="text";
B.style.width=A.offsetWidth+"px";
A.parentNode.insertBefore(B,A);
A.style.display="none";
B.focus();
B.onblur=TinyMCE_EditableSelects.onBlurEditableSelectInput;
B.onkeydown=TinyMCE_EditableSelects.onKeyDown;
TinyMCE_EditableSelects.editSelectElm=A
}},onBlurEditableSelectInput:function(){var A=TinyMCE_EditableSelects.editSelectElm;
if(A){if(A.previousSibling.value!=""){addSelectValue(document.forms[0],A.id,A.previousSibling.value,A.previousSibling.value);
selectByValue(document.forms[0],A.id,A.previousSibling.value)
}else{selectByValue(document.forms[0],A.id,"")
}A.style.display="inline";
A.parentNode.removeChild(A.previousSibling);
TinyMCE_EditableSelects.editSelectElm=null
}},onKeyDown:function(A){A=A||window.event;
if(A.keyCode==13){TinyMCE_EditableSelects.onBlurEditableSelectInput()
}}};