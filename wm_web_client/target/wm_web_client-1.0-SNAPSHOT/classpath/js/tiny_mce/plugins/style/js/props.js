tinyMCEPopup.requireLangPack();
var defaultFonts="Arial, Helvetica, sans-serif=Arial, Helvetica, sans-serif;Times New Roman, Times, serif=Times New Roman, Times, serif;Courier New, Courier, mono=Courier New, Courier, mono;Times New Roman, Times, serif=Times New Roman, Times, serif;Georgia, Times New Roman, Times, serif=Georgia, Times New Roman, Times, serif;Verdana, Arial, Helvetica, sans-serif=Verdana, Arial, Helvetica, sans-serif;Geneva, Arial, Helvetica, sans-serif=Geneva, Arial, Helvetica, sans-serif";
var defaultSizes="9;10;12;14;16;18;24;xx-small;x-small;small;medium;large;x-large;xx-large;smaller;larger";
var defaultMeasurement="+pixels=px;points=pt;inches=in;centimetres=cm;millimetres=mm;picas=pc;ems=em;exs=ex;%";
var defaultSpacingMeasurement="pixels=px;points=pt;inches=in;centimetres=cm;millimetres=mm;picas=pc;+ems=em;exs=ex;%";
var defaultIndentMeasurement="pixels=px;+points=pt;inches=in;centimetres=cm;millimetres=mm;picas=pc;ems=em;exs=ex;%";
var defaultWeight="normal;bold;bolder;lighter;100;200;300;400;500;600;700;800;900";
var defaultTextStyle="normal;italic;oblique";
var defaultVariant="normal;small-caps";
var defaultLineHeight="normal";
var defaultAttachment="fixed;scroll";
var defaultRepeat="no-repeat;repeat;repeat-x;repeat-y";
var defaultPosH="left;center;right";
var defaultPosV="top;center;bottom";
var defaultVAlign="baseline;sub;super;top;text-top;middle;bottom;text-bottom";
var defaultDisplay="inline;block;list-item;run-in;compact;marker;table;inline-table;table-row-group;table-header-group;table-footer-group;table-row;table-column-group;table-column;table-cell;table-caption;none";
var defaultBorderStyle="none;solid;dashed;dotted;double;groove;ridge;inset;outset";
var defaultBorderWidth="thin;medium;thick";
var defaultListType="disc;circle;square;decimal;lower-roman;upper-roman;lower-alpha;upper-alpha;none";
function init(){var B=document.getElementById("container"),A;
B.style.cssText=tinyMCEPopup.getWindowArg("style_text");
A=getBrowserHTML("background_image_browser","background_image","image","advimage");
document.getElementById("background_image_browser").innerHTML=A;
document.getElementById("text_color_pickcontainer").innerHTML=getColorPickerHTML("text_color_pick","text_color");
document.getElementById("background_color_pickcontainer").innerHTML=getColorPickerHTML("background_color_pick","background_color");
document.getElementById("border_color_top_pickcontainer").innerHTML=getColorPickerHTML("border_color_top_pick","border_color_top");
document.getElementById("border_color_right_pickcontainer").innerHTML=getColorPickerHTML("border_color_right_pick","border_color_right");
document.getElementById("border_color_bottom_pickcontainer").innerHTML=getColorPickerHTML("border_color_bottom_pick","border_color_bottom");
document.getElementById("border_color_left_pickcontainer").innerHTML=getColorPickerHTML("border_color_left_pick","border_color_left");
fillSelect(0,"text_font","style_font",defaultFonts,";",true);
fillSelect(0,"text_size","style_font_size",defaultSizes,";",true);
fillSelect(0,"text_size_measurement","style_font_size_measurement",defaultMeasurement,";",true);
fillSelect(0,"text_case","style_text_case","capitalize;uppercase;lowercase",";",true);
fillSelect(0,"text_weight","style_font_weight",defaultWeight,";",true);
fillSelect(0,"text_style","style_font_style",defaultTextStyle,";",true);
fillSelect(0,"text_variant","style_font_variant",defaultVariant,";",true);
fillSelect(0,"text_lineheight","style_font_line_height",defaultLineHeight,";",true);
fillSelect(0,"text_lineheight_measurement","style_font_line_height_measurement",defaultMeasurement,";",true);
fillSelect(0,"background_attachment","style_background_attachment",defaultAttachment,";",true);
fillSelect(0,"background_repeat","style_background_repeat",defaultRepeat,";",true);
fillSelect(0,"background_hpos_measurement","style_background_hpos_measurement",defaultMeasurement,";",true);
fillSelect(0,"background_vpos_measurement","style_background_vpos_measurement",defaultMeasurement,";",true);
fillSelect(0,"background_hpos","style_background_hpos",defaultPosH,";",true);
fillSelect(0,"background_vpos","style_background_vpos",defaultPosV,";",true);
fillSelect(0,"block_wordspacing","style_wordspacing","normal",";",true);
fillSelect(0,"block_wordspacing_measurement","style_wordspacing_measurement",defaultSpacingMeasurement,";",true);
fillSelect(0,"block_letterspacing","style_letterspacing","normal",";",true);
fillSelect(0,"block_letterspacing_measurement","style_letterspacing_measurement",defaultSpacingMeasurement,";",true);
fillSelect(0,"block_vertical_alignment","style_vertical_alignment",defaultVAlign,";",true);
fillSelect(0,"block_text_align","style_text_align","left;right;center;justify",";",true);
fillSelect(0,"block_whitespace","style_whitespace","normal;pre;nowrap",";",true);
fillSelect(0,"block_display","style_display",defaultDisplay,";",true);
fillSelect(0,"block_text_indent_measurement","style_text_indent_measurement",defaultIndentMeasurement,";",true);
fillSelect(0,"box_width_measurement","style_box_width_measurement",defaultMeasurement,";",true);
fillSelect(0,"box_height_measurement","style_box_height_measurement",defaultMeasurement,";",true);
fillSelect(0,"box_float","style_float","left;right;none",";",true);
fillSelect(0,"box_clear","style_clear","left;right;both;none",";",true);
fillSelect(0,"box_padding_left_measurement","style_padding_left_measurement",defaultMeasurement,";",true);
fillSelect(0,"box_padding_top_measurement","style_padding_top_measurement",defaultMeasurement,";",true);
fillSelect(0,"box_padding_bottom_measurement","style_padding_bottom_measurement",defaultMeasurement,";",true);
fillSelect(0,"box_padding_right_measurement","style_padding_right_measurement",defaultMeasurement,";",true);
fillSelect(0,"box_margin_left_measurement","style_margin_left_measurement",defaultMeasurement,";",true);
fillSelect(0,"box_margin_top_measurement","style_margin_top_measurement",defaultMeasurement,";",true);
fillSelect(0,"box_margin_bottom_measurement","style_margin_bottom_measurement",defaultMeasurement,";",true);
fillSelect(0,"box_margin_right_measurement","style_margin_right_measurement",defaultMeasurement,";",true);
fillSelect(0,"border_style_top","style_border_style_top",defaultBorderStyle,";",true);
fillSelect(0,"border_style_right","style_border_style_right",defaultBorderStyle,";",true);
fillSelect(0,"border_style_bottom","style_border_style_bottom",defaultBorderStyle,";",true);
fillSelect(0,"border_style_left","style_border_style_left",defaultBorderStyle,";",true);
fillSelect(0,"border_width_top","style_border_width_top",defaultBorderWidth,";",true);
fillSelect(0,"border_width_right","style_border_width_right",defaultBorderWidth,";",true);
fillSelect(0,"border_width_bottom","style_border_width_bottom",defaultBorderWidth,";",true);
fillSelect(0,"border_width_left","style_border_width_left",defaultBorderWidth,";",true);
fillSelect(0,"border_width_top_measurement","style_border_width_top_measurement",defaultMeasurement,";",true);
fillSelect(0,"border_width_right_measurement","style_border_width_right_measurement",defaultMeasurement,";",true);
fillSelect(0,"border_width_bottom_measurement","style_border_width_bottom_measurement",defaultMeasurement,";",true);
fillSelect(0,"border_width_left_measurement","style_border_width_left_measurement",defaultMeasurement,";",true);
fillSelect(0,"list_type","style_list_type",defaultListType,";",true);
fillSelect(0,"list_position","style_list_position","inside;outside",";",true);
fillSelect(0,"positioning_type","style_positioning_type","absolute;relative;static",";",true);
fillSelect(0,"positioning_visibility","style_positioning_visibility","inherit;visible;hidden",";",true);
fillSelect(0,"positioning_width_measurement","style_positioning_width_measurement",defaultMeasurement,";",true);
fillSelect(0,"positioning_height_measurement","style_positioning_height_measurement",defaultMeasurement,";",true);
fillSelect(0,"positioning_overflow","style_positioning_overflow","visible;hidden;scroll;auto",";",true);
fillSelect(0,"positioning_placement_top_measurement","style_positioning_placement_top_measurement",defaultMeasurement,";",true);
fillSelect(0,"positioning_placement_right_measurement","style_positioning_placement_right_measurement",defaultMeasurement,";",true);
fillSelect(0,"positioning_placement_bottom_measurement","style_positioning_placement_bottom_measurement",defaultMeasurement,";",true);
fillSelect(0,"positioning_placement_left_measurement","style_positioning_placement_left_measurement",defaultMeasurement,";",true);
fillSelect(0,"positioning_clip_top_measurement","style_positioning_clip_top_measurement",defaultMeasurement,";",true);
fillSelect(0,"positioning_clip_right_measurement","style_positioning_clip_right_measurement",defaultMeasurement,";",true);
fillSelect(0,"positioning_clip_bottom_measurement","style_positioning_clip_bottom_measurement",defaultMeasurement,";",true);
fillSelect(0,"positioning_clip_left_measurement","style_positioning_clip_left_measurement",defaultMeasurement,";",true);
TinyMCE_EditableSelects.init();
setupFormData();
showDisabledControls()
}function setupFormData(){var E=document.getElementById("container"),D=document.forms[0],C,A,B;
selectByValue(D,"text_font",E.style.fontFamily,true,true);
selectByValue(D,"text_size",getNum(E.style.fontSize),true,true);
selectByValue(D,"text_size_measurement",getMeasurement(E.style.fontSize));
selectByValue(D,"text_weight",E.style.fontWeight,true,true);
selectByValue(D,"text_style",E.style.fontStyle,true,true);
selectByValue(D,"text_lineheight",getNum(E.style.lineHeight),true,true);
selectByValue(D,"text_lineheight_measurement",getMeasurement(E.style.lineHeight));
selectByValue(D,"text_case",E.style.textTransform,true,true);
selectByValue(D,"text_variant",E.style.fontVariant,true,true);
D.text_color.value=tinyMCEPopup.editor.dom.toHex(E.style.color);
updateColor("text_color_pick","text_color");
D.text_underline.checked=inStr(E.style.textDecoration,"underline");
D.text_overline.checked=inStr(E.style.textDecoration,"overline");
D.text_linethrough.checked=inStr(E.style.textDecoration,"line-through");
D.text_blink.checked=inStr(E.style.textDecoration,"blink");
D.background_color.value=tinyMCEPopup.editor.dom.toHex(E.style.backgroundColor);
updateColor("background_color_pick","background_color");
D.background_image.value=E.style.backgroundImage.replace(new RegExp("url\\('?([^']*)'?\\)","gi"),"$1");
selectByValue(D,"background_repeat",E.style.backgroundRepeat,true,true);
selectByValue(D,"background_attachment",E.style.backgroundAttachment,true,true);
selectByValue(D,"background_hpos",getNum(getVal(E.style.backgroundPosition,0)),true,true);
selectByValue(D,"background_hpos_measurement",getMeasurement(getVal(E.style.backgroundPosition,0)));
selectByValue(D,"background_vpos",getNum(getVal(E.style.backgroundPosition,1)),true,true);
selectByValue(D,"background_vpos_measurement",getMeasurement(getVal(E.style.backgroundPosition,1)));
selectByValue(D,"block_wordspacing",getNum(E.style.wordSpacing),true,true);
selectByValue(D,"block_wordspacing_measurement",getMeasurement(E.style.wordSpacing));
selectByValue(D,"block_letterspacing",getNum(E.style.letterSpacing),true,true);
selectByValue(D,"block_letterspacing_measurement",getMeasurement(E.style.letterSpacing));
selectByValue(D,"block_vertical_alignment",E.style.verticalAlign,true,true);
selectByValue(D,"block_text_align",E.style.textAlign,true,true);
D.block_text_indent.value=getNum(E.style.textIndent);
selectByValue(D,"block_text_indent_measurement",getMeasurement(E.style.textIndent));
selectByValue(D,"block_whitespace",E.style.whiteSpace,true,true);
selectByValue(D,"block_display",E.style.display,true,true);
D.box_width.value=getNum(E.style.width);
selectByValue(D,"box_width_measurement",getMeasurement(E.style.width));
D.box_height.value=getNum(E.style.height);
selectByValue(D,"box_height_measurement",getMeasurement(E.style.height));
if(tinymce.isGecko){selectByValue(D,"box_float",E.style.cssFloat,true,true)
}else{selectByValue(D,"box_float",E.style.styleFloat,true,true)
}selectByValue(D,"box_clear",E.style.clear,true,true);
setupBox(D,E,"box_padding","padding","");
setupBox(D,E,"box_margin","margin","");
setupBox(D,E,"border_style","border","Style");
setupBox(D,E,"border_width","border","Width");
setupBox(D,E,"border_color","border","Color");
updateColor("border_color_top_pick","border_color_top");
updateColor("border_color_right_pick","border_color_right");
updateColor("border_color_bottom_pick","border_color_bottom");
updateColor("border_color_left_pick","border_color_left");
D.elements.border_color_top.value=tinyMCEPopup.editor.dom.toHex(D.elements.border_color_top.value);
D.elements.border_color_right.value=tinyMCEPopup.editor.dom.toHex(D.elements.border_color_right.value);
D.elements.border_color_bottom.value=tinyMCEPopup.editor.dom.toHex(D.elements.border_color_bottom.value);
D.elements.border_color_left.value=tinyMCEPopup.editor.dom.toHex(D.elements.border_color_left.value);
selectByValue(D,"list_type",E.style.listStyleType,true,true);
selectByValue(D,"list_position",E.style.listStylePosition,true,true);
D.list_bullet_image.value=E.style.listStyleImage.replace(new RegExp("url\\('?([^']*)'?\\)","gi"),"$1");
selectByValue(D,"positioning_type",E.style.position,true,true);
selectByValue(D,"positioning_visibility",E.style.visibility,true,true);
selectByValue(D,"positioning_overflow",E.style.overflow,true,true);
D.positioning_zindex.value=E.style.zIndex?E.style.zIndex:"";
D.positioning_width.value=getNum(E.style.width);
selectByValue(D,"positioning_width_measurement",getMeasurement(E.style.width));
D.positioning_height.value=getNum(E.style.height);
selectByValue(D,"positioning_height_measurement",getMeasurement(E.style.height));
setupBox(D,E,"positioning_placement","","",["top","right","bottom","left"]);
C=E.style.clip.replace(new RegExp("rect\\('?([^']*)'?\\)","gi"),"$1");
C=C.replace(/,/g," ");
if(!hasEqualValues([getVal(C,0),getVal(C,1),getVal(C,2),getVal(C,3)])){D.positioning_clip_top.value=getNum(getVal(C,0));
selectByValue(D,"positioning_clip_top_measurement",getMeasurement(getVal(C,0)));
D.positioning_clip_right.value=getNum(getVal(C,1));
selectByValue(D,"positioning_clip_right_measurement",getMeasurement(getVal(C,1)));
D.positioning_clip_bottom.value=getNum(getVal(C,2));
selectByValue(D,"positioning_clip_bottom_measurement",getMeasurement(getVal(C,2)));
D.positioning_clip_left.value=getNum(getVal(C,3));
selectByValue(D,"positioning_clip_left_measurement",getMeasurement(getVal(C,3)))
}else{D.positioning_clip_top.value=getNum(getVal(C,0));
selectByValue(D,"positioning_clip_top_measurement",getMeasurement(getVal(C,0)));
D.positioning_clip_right.value=D.positioning_clip_bottom.value=D.positioning_clip_left.value
}}function getMeasurement(A){return A.replace(/^([0-9.]+)(.*)$/,"$2")
}function getNum(A){if(new RegExp("^(?:[0-9.]+)(?:[a-z%]+)$","gi").test(A)){return A.replace(/[^0-9.]/g,"")
}return A
}function inStr(A,B){return new RegExp(B,"gi").test(A)
}function getVal(C,B){var A=C.split(" ");
if(A.length>1){return A[B]
}return""
}function setValue(B,C,A){if(B.elements[C].type=="text"){B.elements[C].value=A
}else{selectByValue(B,C,A,true,true)
}}function setupBox(D,E,B,F,C,A){if(typeof (A)=="undefined"){A=["Top","Right","Bottom","Left"]
}if(isSame(E,F,C,A)){D.elements[B+"_same"].checked=true;
setValue(D,B+"_top",getNum(E.style[F+A[0]+C]));
D.elements[B+"_top"].disabled=false;
D.elements[B+"_right"].value="";
D.elements[B+"_right"].disabled=true;
D.elements[B+"_bottom"].value="";
D.elements[B+"_bottom"].disabled=true;
D.elements[B+"_left"].value="";
D.elements[B+"_left"].disabled=true;
if(D.elements[B+"_top_measurement"]){selectByValue(D,B+"_top_measurement",getMeasurement(E.style[F+A[0]+C]));
D.elements[B+"_left_measurement"].disabled=true;
D.elements[B+"_bottom_measurement"].disabled=true;
D.elements[B+"_right_measurement"].disabled=true
}}else{D.elements[B+"_same"].checked=false;
setValue(D,B+"_top",getNum(E.style[F+A[0]+C]));
D.elements[B+"_top"].disabled=false;
setValue(D,B+"_right",getNum(E.style[F+A[1]+C]));
D.elements[B+"_right"].disabled=false;
setValue(D,B+"_bottom",getNum(E.style[F+A[2]+C]));
D.elements[B+"_bottom"].disabled=false;
setValue(D,B+"_left",getNum(E.style[F+A[3]+C]));
D.elements[B+"_left"].disabled=false;
if(D.elements[B+"_top_measurement"]){selectByValue(D,B+"_top_measurement",getMeasurement(E.style[F+A[0]+C]));
selectByValue(D,B+"_right_measurement",getMeasurement(E.style[F+A[1]+C]));
selectByValue(D,B+"_bottom_measurement",getMeasurement(E.style[F+A[2]+C]));
selectByValue(D,B+"_left_measurement",getMeasurement(E.style[F+A[3]+C]));
D.elements[B+"_left_measurement"].disabled=false;
D.elements[B+"_bottom_measurement"].disabled=false;
D.elements[B+"_right_measurement"].disabled=false
}}}function isSame(F,G,E,B){var C=[],D,A;
if(typeof (B)=="undefined"){B=["Top","Right","Bottom","Left"]
}if(typeof (E)=="undefined"||E==null){E=""
}C[0]=F.style[G+B[0]+E];
C[1]=F.style[G+B[1]+E];
C[2]=F.style[G+B[2]+E];
C[3]=F.style[G+B[3]+E];
for(D=0;
D<C.length;
D++){if(C[D]==null){return false
}for(A=0;
A<C.length;
A++){if(C[A]!=C[D]){return false
}}}return true
}function hasEqualValues(B){var C,A;
for(C=0;
C<B.length;
C++){if(B[C]==null){return false
}for(A=0;
A<B.length;
A++){if(B[A]!=B[C]){return false
}}}return true
}function applyAction(){var B=document.getElementById("container"),A=tinyMCEPopup.editor;
generateCSS();
tinyMCEPopup.restoreSelection();
A.dom.setAttrib(A.selection.getNode(),"style",tinyMCEPopup.editor.dom.serializeStyle(tinyMCEPopup.editor.dom.parseStyle(B.style.cssText)))
}function updateAction(){applyAction();
tinyMCEPopup.close()
}function generateCSS(){var E=document.getElementById("container"),D=document.forms[0],A=new RegExp("[0-9]+","g"),C,B;
E.style.cssText="";
E.style.fontFamily=D.text_font.value;
E.style.fontSize=D.text_size.value+(isNum(D.text_size.value)?(D.text_size_measurement.value||"px"):"");
E.style.fontStyle=D.text_style.value;
E.style.lineHeight=D.text_lineheight.value+(isNum(D.text_lineheight.value)?D.text_lineheight_measurement.value:"");
E.style.textTransform=D.text_case.value;
E.style.fontWeight=D.text_weight.value;
E.style.fontVariant=D.text_variant.value;
E.style.color=D.text_color.value;
C="";
C+=D.text_underline.checked?" underline":"";
C+=D.text_overline.checked?" overline":"";
C+=D.text_linethrough.checked?" line-through":"";
C+=D.text_blink.checked?" blink":"";
C=C.length>0?C.substring(1):C;
if(D.text_none.checked){C="none"
}E.style.textDecoration=C;
E.style.backgroundColor=D.background_color.value;
E.style.backgroundImage=D.background_image.value!=""?"url("+D.background_image.value+")":"";
E.style.backgroundRepeat=D.background_repeat.value;
E.style.backgroundAttachment=D.background_attachment.value;
if(D.background_hpos.value!=""){C="";
C+=D.background_hpos.value+(isNum(D.background_hpos.value)?D.background_hpos_measurement.value:"")+" ";
C+=D.background_vpos.value+(isNum(D.background_vpos.value)?D.background_vpos_measurement.value:"");
E.style.backgroundPosition=C
}E.style.wordSpacing=D.block_wordspacing.value+(isNum(D.block_wordspacing.value)?D.block_wordspacing_measurement.value:"");
E.style.letterSpacing=D.block_letterspacing.value+(isNum(D.block_letterspacing.value)?D.block_letterspacing_measurement.value:"");
E.style.verticalAlign=D.block_vertical_alignment.value;
E.style.textAlign=D.block_text_align.value;
E.style.textIndent=D.block_text_indent.value+(isNum(D.block_text_indent.value)?D.block_text_indent_measurement.value:"");
E.style.whiteSpace=D.block_whitespace.value;
E.style.display=D.block_display.value;
E.style.width=D.box_width.value+(isNum(D.box_width.value)?D.box_width_measurement.value:"");
E.style.height=D.box_height.value+(isNum(D.box_height.value)?D.box_height_measurement.value:"");
E.style.styleFloat=D.box_float.value;
if(tinymce.isGecko){E.style.cssFloat=D.box_float.value
}E.style.clear=D.box_clear.value;
if(!D.box_padding_same.checked){E.style.paddingTop=D.box_padding_top.value+(isNum(D.box_padding_top.value)?D.box_padding_top_measurement.value:"");
E.style.paddingRight=D.box_padding_right.value+(isNum(D.box_padding_right.value)?D.box_padding_right_measurement.value:"");
E.style.paddingBottom=D.box_padding_bottom.value+(isNum(D.box_padding_bottom.value)?D.box_padding_bottom_measurement.value:"");
E.style.paddingLeft=D.box_padding_left.value+(isNum(D.box_padding_left.value)?D.box_padding_left_measurement.value:"")
}else{E.style.padding=D.box_padding_top.value+(isNum(D.box_padding_top.value)?D.box_padding_top_measurement.value:"")
}if(!D.box_margin_same.checked){E.style.marginTop=D.box_margin_top.value+(isNum(D.box_margin_top.value)?D.box_margin_top_measurement.value:"");
E.style.marginRight=D.box_margin_right.value+(isNum(D.box_margin_right.value)?D.box_margin_right_measurement.value:"");
E.style.marginBottom=D.box_margin_bottom.value+(isNum(D.box_margin_bottom.value)?D.box_margin_bottom_measurement.value:"");
E.style.marginLeft=D.box_margin_left.value+(isNum(D.box_margin_left.value)?D.box_margin_left_measurement.value:"")
}else{E.style.margin=D.box_margin_top.value+(isNum(D.box_margin_top.value)?D.box_margin_top_measurement.value:"")
}if(!D.border_style_same.checked){E.style.borderTopStyle=D.border_style_top.value;
E.style.borderRightStyle=D.border_style_right.value;
E.style.borderBottomStyle=D.border_style_bottom.value;
E.style.borderLeftStyle=D.border_style_left.value
}else{E.style.borderStyle=D.border_style_top.value
}if(!D.border_width_same.checked){E.style.borderTopWidth=D.border_width_top.value+(isNum(D.border_width_top.value)?D.border_width_top_measurement.value:"");
E.style.borderRightWidth=D.border_width_right.value+(isNum(D.border_width_right.value)?D.border_width_right_measurement.value:"");
E.style.borderBottomWidth=D.border_width_bottom.value+(isNum(D.border_width_bottom.value)?D.border_width_bottom_measurement.value:"");
E.style.borderLeftWidth=D.border_width_left.value+(isNum(D.border_width_left.value)?D.border_width_left_measurement.value:"")
}else{E.style.borderWidth=D.border_width_top.value+(isNum(D.border_width_top.value)?D.border_width_top_measurement.value:"")
}if(!D.border_color_same.checked){E.style.borderTopColor=D.border_color_top.value;
E.style.borderRightColor=D.border_color_right.value;
E.style.borderBottomColor=D.border_color_bottom.value;
E.style.borderLeftColor=D.border_color_left.value
}else{E.style.borderColor=D.border_color_top.value
}E.style.listStyleType=D.list_type.value;
E.style.listStylePosition=D.list_position.value;
E.style.listStyleImage=D.list_bullet_image.value!=""?"url("+D.list_bullet_image.value+")":"";
E.style.position=D.positioning_type.value;
E.style.visibility=D.positioning_visibility.value;
if(E.style.width==""){E.style.width=D.positioning_width.value+(isNum(D.positioning_width.value)?D.positioning_width_measurement.value:"")
}if(E.style.height==""){E.style.height=D.positioning_height.value+(isNum(D.positioning_height.value)?D.positioning_height_measurement.value:"")
}E.style.zIndex=D.positioning_zindex.value;
E.style.overflow=D.positioning_overflow.value;
if(!D.positioning_placement_same.checked){E.style.top=D.positioning_placement_top.value+(isNum(D.positioning_placement_top.value)?D.positioning_placement_top_measurement.value:"");
E.style.right=D.positioning_placement_right.value+(isNum(D.positioning_placement_right.value)?D.positioning_placement_right_measurement.value:"");
E.style.bottom=D.positioning_placement_bottom.value+(isNum(D.positioning_placement_bottom.value)?D.positioning_placement_bottom_measurement.value:"");
E.style.left=D.positioning_placement_left.value+(isNum(D.positioning_placement_left.value)?D.positioning_placement_left_measurement.value:"")
}else{C=D.positioning_placement_top.value+(isNum(D.positioning_placement_top.value)?D.positioning_placement_top_measurement.value:"");
E.style.top=C;
E.style.right=C;
E.style.bottom=C;
E.style.left=C
}if(!D.positioning_clip_same.checked){C="rect(";
C+=(isNum(D.positioning_clip_top.value)?D.positioning_clip_top.value+D.positioning_clip_top_measurement.value:"auto")+" ";
C+=(isNum(D.positioning_clip_right.value)?D.positioning_clip_right.value+D.positioning_clip_right_measurement.value:"auto")+" ";
C+=(isNum(D.positioning_clip_bottom.value)?D.positioning_clip_bottom.value+D.positioning_clip_bottom_measurement.value:"auto")+" ";
C+=(isNum(D.positioning_clip_left.value)?D.positioning_clip_left.value+D.positioning_clip_left_measurement.value:"auto");
C+=")";
if(C!="rect(auto auto auto auto)"){E.style.clip=C
}}else{C="rect(";
B=isNum(D.positioning_clip_top.value)?D.positioning_clip_top.value+D.positioning_clip_top_measurement.value:"auto";
C+=B+" ";
C+=B+" ";
C+=B+" ";
C+=B+")";
if(C!="rect(auto auto auto auto)"){E.style.clip=C
}}E.style.cssText=E.style.cssText
}function isNum(A){return new RegExp("[0-9]+","g").test(A)
}function showDisabledControls(){var C=document.forms,B,A;
for(B=0;
B<C.length;
B++){for(A=0;
A<C[B].elements.length;
A++){if(C[B].elements[A].disabled){tinyMCEPopup.editor.dom.addClass(C[B].elements[A],"disabled")
}else{tinyMCEPopup.editor.dom.removeClass(C[B].elements[A],"disabled")
}}}}function fillSelect(G,I,D,B,J,C){var F,E,A,H;
G=document.forms[G];
J=typeof (J)=="undefined"?";":J;
if(C){addSelectValue(G,I,"","")
}E=tinyMCEPopup.getParam(D,B).split(J);
for(F=0;
F<E.length;
F++){H=false;
if(E[F].charAt(0)=="+"){E[F]=E[F].substring(1);
H=true
}A=E[F].split("=");
if(A.length>1){addSelectValue(G,I,A[0],A[1]);
if(H){selectByValue(G,I,A[1])
}}else{addSelectValue(G,I,A[0],A[0]);
if(H){selectByValue(G,I,A[0])
}}}}function toggleSame(D,C){var B=document.forms[0].elements,A;
if(D.checked){B[C+"_top"].disabled=false;
B[C+"_right"].disabled=true;
B[C+"_bottom"].disabled=true;
B[C+"_left"].disabled=true;
if(B[C+"_top_measurement"]){B[C+"_top_measurement"].disabled=false;
B[C+"_right_measurement"].disabled=true;
B[C+"_bottom_measurement"].disabled=true;
B[C+"_left_measurement"].disabled=true
}}else{B[C+"_top"].disabled=false;
B[C+"_right"].disabled=false;
B[C+"_bottom"].disabled=false;
B[C+"_left"].disabled=false;
if(B[C+"_top_measurement"]){B[C+"_top_measurement"].disabled=false;
B[C+"_right_measurement"].disabled=false;
B[C+"_bottom_measurement"].disabled=false;
B[C+"_left_measurement"].disabled=false
}}showDisabledControls()
}function synch(A,C){var B=document.forms[0];
B.elements[C].value=B.elements[A].value;
if(B.elements[A+"_measurement"]){selectByValue(B,C+"_measurement",B.elements[A+"_measurement"].value)
}}tinyMCEPopup.onInit.add(init);