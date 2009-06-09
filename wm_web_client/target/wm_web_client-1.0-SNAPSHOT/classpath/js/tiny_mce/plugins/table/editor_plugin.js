(function(){var A=tinymce.each;
tinymce.create("tinymce.plugins.TablePlugin",{init:function(B,D){var C=this;
C.editor=B;
C.url=D;
A([["table","table.desc","mceInsertTable",true],["delete_table","table.del","mceTableDelete"],["delete_col","table.delete_col_desc","mceTableDeleteCol"],["delete_row","table.delete_row_desc","mceTableDeleteRow"],["col_after","table.col_after_desc","mceTableInsertColAfter"],["col_before","table.col_before_desc","mceTableInsertColBefore"],["row_after","table.row_after_desc","mceTableInsertRowAfter"],["row_before","table.row_before_desc","mceTableInsertRowBefore"],["row_props","table.row_desc","mceTableRowProps",true],["cell_props","table.cell_desc","mceTableCellProps",true],["split_cells","table.split_cells_desc","mceTableSplitCells",true],["merge_cells","table.merge_cells_desc","mceTableMergeCells",true]],function(E){B.addButton(E[0],{title:E[1],cmd:E[2],ui:E[3]})
});
if(B.getParam("inline_styles")){B.onPreProcess.add(function(G,E){var F=G.dom;
A(F.select("table",E.node),function(H){var I;
if(I=F.getAttrib(H,"width")){F.setStyle(H,"width",I);
F.setAttrib(H,"width")
}if(I=F.getAttrib(H,"height")){F.setStyle(H,"height",I);
F.setAttrib(H,"height")
}})
})
}B.onInit.add(function(){if(B&&B.plugins.contextmenu){B.plugins.contextmenu.onContextMenu.add(function(H,J,F){var E,G=B.selection,I=G.getNode()||B.getBody();
if(B.dom.getParent(F,"td")||B.dom.getParent(F,"th")){J.removeAll();
if(I.nodeName=="A"&&!B.dom.getAttrib(I,"name")){J.add({title:"advanced.link_desc",icon:"link",cmd:B.plugins.advlink?"mceAdvLink":"mceLink",ui:true});
J.add({title:"advanced.unlink_desc",icon:"unlink",cmd:"UnLink"});
J.addSeparator()
}if(I.nodeName=="IMG"&&I.className.indexOf("mceItem")==-1){J.add({title:"advanced.image_desc",icon:"image",cmd:B.plugins.advimage?"mceAdvImage":"mceImage",ui:true});
J.addSeparator()
}J.add({title:"table.desc",icon:"table",cmd:"mceInsertTable",ui:true,value:{action:"insert"}});
J.add({title:"table.props_desc",icon:"table_props",cmd:"mceInsertTable",ui:true});
J.add({title:"table.del",icon:"delete_table",cmd:"mceTableDelete",ui:true});
J.addSeparator();
E=J.addMenu({title:"table.cell"});
E.add({title:"table.cell_desc",icon:"cell_props",cmd:"mceTableCellProps",ui:true});
E.add({title:"table.split_cells_desc",icon:"split_cells",cmd:"mceTableSplitCells",ui:true});
E.add({title:"table.merge_cells_desc",icon:"merge_cells",cmd:"mceTableMergeCells",ui:true});
E=J.addMenu({title:"table.row"});
E.add({title:"table.row_desc",icon:"row_props",cmd:"mceTableRowProps",ui:true});
E.add({title:"table.row_before_desc",icon:"row_before",cmd:"mceTableInsertRowBefore"});
E.add({title:"table.row_after_desc",icon:"row_after",cmd:"mceTableInsertRowAfter"});
E.add({title:"table.delete_row_desc",icon:"delete_row",cmd:"mceTableDeleteRow"});
E.addSeparator();
E.add({title:"table.cut_row_desc",icon:"cut",cmd:"mceTableCutRow"});
E.add({title:"table.copy_row_desc",icon:"copy",cmd:"mceTableCopyRow"});
E.add({title:"table.paste_row_before_desc",icon:"paste",cmd:"mceTablePasteRowBefore"});
E.add({title:"table.paste_row_after_desc",icon:"paste",cmd:"mceTablePasteRowAfter"});
E=J.addMenu({title:"table.col"});
E.add({title:"table.col_before_desc",icon:"col_before",cmd:"mceTableInsertColBefore"});
E.add({title:"table.col_after_desc",icon:"col_after",cmd:"mceTableInsertColAfter"});
E.add({title:"table.delete_col_desc",icon:"delete_col",cmd:"mceTableDeleteCol"})
}else{J.add({title:"table.desc",icon:"table",cmd:"mceInsertTable",ui:true})
}})
}});
B.onKeyDown.add(function(F,E){if(E.keyCode==9&&F.dom.getParent(F.selection.getNode(),"TABLE")){if(!tinymce.isGecko&&!tinymce.isOpera){tinyMCE.execInstanceCommand(F.editorId,"mceTableMoveToNextRow",true);
return tinymce.dom.Event.cancel(E)
}F.undoManager.add()
}});
if(!tinymce.isIE){if(B.getParam("table_selection",true)){B.onClick.add(function(F,E){E=E.target;
if(E.nodeName==="TABLE"){F.selection.select(E)
}})
}}B.onNodeChange.add(function(G,H,E){var F=G.dom.getParent(E,"td,th,caption");
H.setActive("table",E.nodeName==="TABLE"||!!F);
if(F&&F.nodeName==="CAPTION"){F=null
}H.setDisabled("delete_table",!F);
H.setDisabled("delete_col",!F);
H.setDisabled("delete_table",!F);
H.setDisabled("delete_row",!F);
H.setDisabled("col_after",!F);
H.setDisabled("col_before",!F);
H.setDisabled("row_after",!F);
H.setDisabled("row_before",!F);
H.setDisabled("row_props",!F);
H.setDisabled("cell_props",!F);
H.setDisabled("split_cells",!F||(parseInt(G.dom.getAttrib(F,"colspan","1"))<2&&parseInt(G.dom.getAttrib(F,"rowspan","1"))<2));
H.setDisabled("merge_cells",!F)
});
if(!tinymce.isIE){B.onBeforeSetContent.add(function(F,E){if(E.initial){E.content=E.content.replace(/<(td|th)([^>]+|)>\s*<\/(td|th)>/g,tinymce.isOpera?"<$1$2>&nbsp;</$1>":'<$1$2><br mce_bogus="1" /></$1>')
}})
}},execCommand:function(C,D,B){var E=this.editor,F;
switch(C){case"mceTableMoveToNextRow":case"mceInsertTable":case"mceTableRowProps":case"mceTableCellProps":case"mceTableSplitCells":case"mceTableMergeCells":case"mceTableInsertRowBefore":case"mceTableInsertRowAfter":case"mceTableDeleteRow":case"mceTableInsertColBefore":case"mceTableInsertColAfter":case"mceTableDeleteCol":case"mceTableCutRow":case"mceTableCopyRow":case"mceTablePasteRowBefore":case"mceTablePasteRowAfter":case"mceTableDelete":E.execCommand("mceBeginUndoLevel");
this._doExecCommand(C,D,B);
E.execCommand("mceEndUndoLevel");
return true
}return false
},getInfo:function(){return{longname:"Tables",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/table",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_doExecCommand:function(Ay,a,Ak){var AB=this.editor,AF=AB,A8=this.url;
var A2=AB.selection.getNode();
var y=AB.dom.getParent(A2,"tr");
var AM=AB.dom.getParent(A2,"td,th");
var Ag=AB.dom.getParent(A2,"table");
var A5=AB.contentWindow.document;
var AD=Ag?Ag.getAttribute("border"):"";
if(y&&AM==null){AM=y.cells[0]
}function AN(D,B){for(var C=0;
C<D.length;
C++){if(D[C].length>0&&AN(D[C],B)){return true
}if(D[C]==B){return true
}}return false
}function Aa(B,C){var D;
Am=BA(Ag);
B=B||0;
C=C||0;
B=Math.max(A1.cellindex+B,0);
C=Math.max(A1.rowindex+C,0);
AB.execCommand("mceRepaint");
D=BB(Am,C,B);
if(D){AB.selection.select(D.firstChild||D);
AB.selection.collapse(1)
}}function Ad(){var B=A5.createElement("td");
if(!tinymce.isIE){B.innerHTML='<br mce_bogus="1"/>'
}}function A6(D){var B=AB.dom.getAttrib(D,"colspan");
var C=AB.dom.getAttrib(D,"rowspan");
B=B==""?1:parseInt(B);
C=C==""?1:parseInt(C);
return{colspan:B,rowspan:C}
}function AW(E,C){var B,D;
for(D=0;
D<E.length;
D++){for(B=0;
B<E[D].length;
B++){if(E[D][B]==C){return{cellindex:B,rowindex:D}
}}}return null
}function BB(B,D,C){if(B[D]&&B[D][C]){return B[D][C]
}return null
}function Aq(C,G){var B=[],H=0,F,E,G,D;
for(F=0;
F<C.rows.length;
F++){for(E=0;
E<C.rows[F].cells.length;
E++,H++){B[H]=C.rows[F].cells[E]
}}for(F=0;
F<B.length;
F++){if(B[F]==G){if(D=B[F+1]){return D
}}}}function BA(D){var I=[],C=D.rows,F,G,K,J,E,B,H;
for(G=0;
G<C.length;
G++){for(F=0;
F<C[G].cells.length;
F++){K=C[G].cells[F];
J=A6(K);
for(E=F;
I[G]&&I[G][E];
E++){}for(H=G;
H<G+J.rowspan;
H++){if(!I[H]){I[H]=[]
}for(B=E;
B<E+J.colspan;
B++){I[H][B]=K
}}}}return I
}function A3(D,G,M,B){var K=BA(D),E=AW(K,M);
var C,H;
if(B.cells.length!=G.childNodes.length){C=G.childNodes;
H=null;
for(var F=0;
M=BB(K,E.rowindex,F);
F++){var J=true;
var I=A6(M);
if(AN(C,M)){B.childNodes[F]._delete=true
}else{if((H==null||M!=H)&&I.colspan>1){for(var L=F;
L<F+M.colSpan;
L++){B.childNodes[L]._delete=true
}}}if((H==null||M!=H)&&I.rowspan>1){M.rowSpan=I.rowspan+1
}H=M
}Ao(Ag)
}}function AO(B,C){while((B=B.previousSibling)!=null){if(B.nodeName==C){return B
}}return null
}function Ai(D,C){var B=C.split(",");
while((D=D.nextSibling)!=null){for(var E=0;
E<B.length;
E++){if(D.nodeName.toLowerCase()==B[E].toLowerCase()){return D
}}}return null
}function Ao(E){if(E.rows==0){return 
}var F=E.rows[0];
do{var B=Ai(F,"TR");
if(F._delete){F.parentNode.removeChild(F);
continue
}var D=F.cells[0];
if(D.cells>1){do{var C=Ai(D,"TD,TH");
if(D._delete){D.parentNode.removeChild(D)
}}while((D=C)!=null)
}}while((F=B)!=null)
}function A0(F,E,C){F.rowSpan=1;
var B=Ai(E,"TR");
for(var D=1;
D<C&&B;
D++){var G=A5.createElement("td");
if(!tinymce.isIE){G.innerHTML='<br mce_bogus="1"/>'
}if(tinymce.isIE){B.insertBefore(G,B.cells(F.cellIndex))
}else{B.insertBefore(G,B.cells[F.cellIndex])
}B=Ai(B,"TR")
}}function AH(E,C,I){var K=BA(C);
var B=I.cloneNode(false);
var D=AW(K,I.cells[0]);
var H=null;
var J=AB.dom.getAttrib(C,"border");
var L=null;
for(var F=0;
L=BB(K,D.rowindex,F);
F++){var G=null;
if(H!=L){for(var M=0;
M<I.cells.length;
M++){if(L==I.cells[M]){G=L.cloneNode(true);
break
}}}if(G==null){G=E.createElement("td");
if(!tinymce.isIE){G.innerHTML='<br mce_bogus="1"/>'
}}G.colSpan=1;
G.rowSpan=1;
B.appendChild(G);
H=L
}return B
}switch(Ay){case"mceTableMoveToNextRow":var AU=Aq(Ag,AM);
if(!AU){AB.execCommand("mceTableInsertRowAfter",AM);
AU=Aq(Ag,AM)
}AB.selection.select(AU);
AB.selection.collapse(true);
return true;
case"mceTableRowProps":if(y==null){return true
}if(a){AB.windowManager.open({url:A8+"/row.htm",width:400+parseInt(AB.getLang("table.rowprops_delta_width",0)),height:295+parseInt(AB.getLang("table.rowprops_delta_height",0)),inline:1},{plugin_url:A8})
}return true;
case"mceTableCellProps":if(AM==null){return true
}if(a){AB.windowManager.open({url:A8+"/cell.htm",width:400+parseInt(AB.getLang("table.cellprops_delta_width",0)),height:295+parseInt(AB.getLang("table.cellprops_delta_height",0)),inline:1},{plugin_url:A8})
}return true;
case"mceInsertTable":if(a){AB.windowManager.open({url:A8+"/table.htm",width:400+parseInt(AB.getLang("table.table_delta_width",0)),height:320+parseInt(AB.getLang("table.table_delta_height",0)),inline:1},{plugin_url:A8,action:Ak?Ak.action:0})
}return true;
case"mceTableDelete":var Ae=AB.dom.getParent(AB.selection.getNode(),"table");
if(Ae){Ae.parentNode.removeChild(Ae);
AB.execCommand("mceRepaint")
}return true;
case"mceTableSplitCells":case"mceTableMergeCells":case"mceTableInsertRowBefore":case"mceTableInsertRowAfter":case"mceTableDeleteRow":case"mceTableInsertColBefore":case"mceTableInsertColAfter":case"mceTableDeleteCol":case"mceTableCutRow":case"mceTableCopyRow":case"mceTablePasteRowBefore":case"mceTablePasteRowAfter":if(!Ag){return true
}if(y&&Ag!=y.parentNode){Ag=y.parentNode
}if(Ag&&y){switch(Ay){case"mceTableCutRow":if(!y||!AM){return true
}AB.tableRowClipboard=AH(A5,Ag,y);
AB.execCommand("mceTableDeleteRow");
break;
case"mceTableCopyRow":if(!y||!AM){return true
}AB.tableRowClipboard=AH(A5,Ag,y);
break;
case"mceTablePasteRowBefore":if(!y||!AM){return true
}var Au=AB.tableRowClipboard.cloneNode(true);
var A7=AO(y,"TR");
if(A7!=null){A3(Ag,A7,A7.cells[0],Au)
}y.parentNode.insertBefore(Au,y);
break;
case"mceTablePasteRowAfter":if(!y||!AM){return true
}var x=Ai(y,"TR");
var Au=AB.tableRowClipboard.cloneNode(true);
A3(Ag,y,AM,Au);
if(x==null){y.parentNode.appendChild(Au)
}else{x.parentNode.insertBefore(Au,x)
}break;
case"mceTableInsertRowBefore":if(!y||!AM){return true
}var Am=BA(Ag);
var A1=AW(Am,AM);
var Au=A5.createElement("tr");
var Av=null;
A1.rowindex--;
if(A1.rowindex<0){A1.rowindex=0
}for(var An=0;
AM=BB(Am,A1.rowindex,An);
An++){if(AM!=Av){var Ah=A6(AM);
if(Ah.rowspan==1){var AY=A5.createElement("td");
if(!tinymce.isIE){AY.innerHTML='<br mce_bogus="1"/>'
}AY.colSpan=AM.colSpan;
Au.appendChild(AY)
}else{AM.rowSpan=Ah.rowspan+1
}Av=AM
}}y.parentNode.insertBefore(Au,y);
Aa(0,1);
break;
case"mceTableInsertRowAfter":if(!y||!AM){return true
}var Am=BA(Ag);
var A1=AW(Am,AM);
var Au=A5.createElement("tr");
var Av=null;
for(var An=0;
AM=BB(Am,A1.rowindex,An);
An++){if(AM!=Av){var Ah=A6(AM);
if(Ah.rowspan==1){var AY=A5.createElement("td");
if(!tinymce.isIE){AY.innerHTML='<br mce_bogus="1"/>'
}AY.colSpan=AM.colSpan;
Au.appendChild(AY)
}else{AM.rowSpan=Ah.rowspan+1
}Av=AM
}}if(Au.hasChildNodes()){var x=Ai(y,"TR");
if(x){x.parentNode.insertBefore(Au,x)
}else{Ag.appendChild(Au)
}}Aa(0,1);
break;
case"mceTableDeleteRow":if(!y||!AM){return true
}var Am=BA(Ag);
var A1=AW(Am,AM);
if(Am.length==1&&Ag.nodeName=="TBODY"){AB.dom.remove(AB.dom.getParent(Ag,"table"));
return true
}var Aj=y.cells;
var x=Ai(y,"TR");
for(var An=0;
An<Aj.length;
An++){if(Aj[An].rowSpan>1){var AY=Aj[An].cloneNode(true);
var Ah=A6(Aj[An]);
AY.rowSpan=Ah.rowspan-1;
var AX=x.cells[An];
if(AX==null){x.appendChild(AY)
}else{x.insertBefore(AY,AX)
}}}var Av=null;
for(var An=0;
AM=BB(Am,A1.rowindex,An);
An++){if(AM!=Av){var Ah=A6(AM);
if(Ah.rowspan>1){AM.rowSpan=Ah.rowspan-1
}else{y=AM.parentNode;
if(y.parentNode){y._delete=true
}}Av=AM
}}Ao(Ag);
Aa(0,-1);
break;
case"mceTableInsertColBefore":if(!y||!AM){return true
}var Am=BA(AB.dom.getParent(Ag,"table"));
var A1=AW(Am,AM);
var Av=null;
for(var Ar=0;
AM=BB(Am,Ar,A1.cellindex);
Ar++){if(AM!=Av){var Ah=A6(AM);
if(Ah.colspan==1){var AY=A5.createElement(AM.nodeName);
if(!tinymce.isIE){AY.innerHTML='<br mce_bogus="1"/>'
}AY.rowSpan=AM.rowSpan;
AM.parentNode.insertBefore(AY,AM)
}else{AM.colSpan++
}Av=AM
}}Aa();
break;
case"mceTableInsertColAfter":if(!y||!AM){return true
}var Am=BA(AB.dom.getParent(Ag,"table"));
var A1=AW(Am,AM);
var Av=null;
for(var Ar=0;
AM=BB(Am,Ar,A1.cellindex);
Ar++){if(AM!=Av){var Ah=A6(AM);
if(Ah.colspan==1){var AY=A5.createElement(AM.nodeName);
if(!tinymce.isIE){AY.innerHTML='<br mce_bogus="1"/>'
}AY.rowSpan=AM.rowSpan;
var AX=Ai(AM,"TD,TH");
if(AX==null){AM.parentNode.appendChild(AY)
}else{AX.parentNode.insertBefore(AY,AX)
}}else{AM.colSpan++
}Av=AM
}}Aa(1);
break;
case"mceTableDeleteCol":if(!y||!AM){return true
}var Am=BA(Ag);
var A1=AW(Am,AM);
var Av=null;
if((Am.length>1&&Am[0].length<=1)&&Ag.nodeName=="TBODY"){AB.dom.remove(AB.dom.getParent(Ag,"table"));
return true
}for(var Ar=0;
AM=BB(Am,Ar,A1.cellindex);
Ar++){if(AM!=Av){var Ah=A6(AM);
if(Ah.colspan>1){AM.colSpan=Ah.colspan-1
}else{if(AM.parentNode){AM.parentNode.removeChild(AM)
}}Av=AM
}}Aa(-1);
break;
case"mceTableSplitCells":if(!y||!AM){return true
}var A4=A6(AM);
var Al=A4.colspan;
var Ac=A4.rowspan;
if(Al>1||Ac>1){AM.colSpan=1;
for(var AT=1;
AT<Al;
AT++){var AY=A5.createElement("td");
if(!tinymce.isIE){AY.innerHTML='<br mce_bogus="1"/>'
}y.insertBefore(AY,Ai(AM,"TD,TH"));
if(Ac>1){A0(AY,y,Ac)
}}A0(AM,y,Ac)
}Ag=AB.dom.getParent(AB.selection.getNode(),"table");
break;
case"mceTableMergeCells":var AQ=[];
var AI=AB.selection.getSel();
var Am=BA(Ag);
if(tinymce.isIE||AI.rangeCount==1){if(a){var Aw=A6(AM);
AB.windowManager.open({url:A8+"/merge_cells.htm",width:240+parseInt(AB.getLang("table.merge_cells_delta_width",0)),height:110+parseInt(AB.getLang("table.merge_cells_delta_height",0)),inline:1},{action:"update",numcols:Aw.colspan,numrows:Aw.rowspan,plugin_url:A8});
return true
}else{var AC=parseInt(Ak.numrows);
var BC=parseInt(Ak.numcols);
var A1=AW(Am,AM);
if((""+AC)=="NaN"){AC=1
}if((""+BC)=="NaN"){BC=1
}var BD=Ag.rows;
for(var Ar=A1.rowindex;
Ar<Am.length;
Ar++){var Af=[];
for(var An=A1.cellindex;
An<Am[Ar].length;
An++){var A9=BB(Am,Ar,An);
if(A9&&!AN(AQ,A9)&&!AN(Af,A9)){var AP=AW(Am,A9);
if(AP.cellindex<A1.cellindex+BC&&AP.rowindex<A1.rowindex+AC){Af[Af.length]=A9
}}}if(Af.length>0){AQ[AQ.length]=Af
}var A9=BB(Am,A1.rowindex,A1.cellindex);
A(AF.dom.select("br",A9),function(C,B){if(B>0&&AF.dom.getAttrib("mce_bogus")){AF.dom.remove(C)
}})
}}}else{var Aj=[];
var AI=AB.selection.getSel();
var i=null;
var AS=null;
var As=-1,AA=-1,At,AG;
if(AI.rangeCount<2){return true
}for(var AT=0;
AT<AI.rangeCount;
AT++){var Ab=AI.getRangeAt(AT);
var AM=Ab.startContainer.childNodes[Ab.startOffset];
if(!AM){break
}if(AM.nodeName=="TD"||AM.nodeName=="TH"){Aj[Aj.length]=AM
}}var BD=Ag.rows;
for(var Ar=0;
Ar<BD.length;
Ar++){var Af=[];
for(var An=0;
An<BD[Ar].cells.length;
An++){var A9=BD[Ar].cells[An];
for(var AT=0;
AT<Aj.length;
AT++){if(A9==Aj[AT]){Af[Af.length]=A9
}}}if(Af.length>0){AQ[AQ.length]=Af
}}var AS=[];
var i=null;
for(var Ar=0;
Ar<Am.length;
Ar++){for(var An=0;
An<Am[Ar].length;
An++){Am[Ar][An]._selected=false;
for(var AT=0;
AT<Aj.length;
AT++){if(Am[Ar][An]==Aj[AT]){if(As==-1){As=An;
AA=Ar
}At=An;
AG=Ar;
Am[Ar][An]._selected=true
}}}}for(var Ar=AA;
Ar<=AG;
Ar++){for(var An=As;
An<=At;
An++){if(!Am[Ar][An]._selected){alert("Invalid selection for merge.");
return true
}}}}var Ax=1,Az=1;
var AE=-1;
for(var Ar=0;
Ar<AQ.length;
Ar++){var AZ=0;
for(var An=0;
An<AQ[Ar].length;
An++){var Ah=A6(AQ[Ar][An]);
AZ+=Ah.colspan;
if(AE!=-1&&Ah.rowspan!=AE){alert("Invalid selection for merge.");
return true
}AE=Ah.rowspan
}if(AZ>Az){Az=AZ
}AE=-1
}var AK=-1;
for(var An=0;
An<AQ[0].length;
An++){var AR=0;
for(var Ar=0;
Ar<AQ.length;
Ar++){var Ah=A6(AQ[Ar][An]);
AR+=Ah.rowspan;
if(AK!=-1&&Ah.colspan!=AK){alert("Invalid selection for merge.");
return true
}AK=Ah.colspan
}if(AR>Ax){Ax=AR
}AK=-1
}AM=AQ[0][0];
AM.rowSpan=Ax;
AM.colSpan=Az;
for(var Ar=0;
Ar<AQ.length;
Ar++){for(var An=0;
An<AQ[Ar].length;
An++){var AL=AQ[Ar][An].innerHTML;
var AV=AL.replace(/[ \t\r\n]/g,"");
if(AV!="<br/>"&&AV!="<br>"&&AV!='<br mce_bogus="1"/>'&&(An+Ar>0)){AM.innerHTML+=AL
}if(AQ[Ar][An]!=AM&&!AQ[Ar][An]._deleted){var A1=AW(Am,AQ[Ar][An]);
var AJ=AQ[Ar][An].parentNode;
AJ.removeChild(AQ[Ar][An]);
AQ[Ar][An]._deleted=true;
if(!AJ.hasChildNodes()){AJ.parentNode.removeChild(AJ);
var Ap=null;
for(var An=0;
cellElm=BB(Am,A1.rowindex,An);
An++){if(cellElm!=Ap&&cellElm.rowSpan>1){cellElm.rowSpan--
}Ap=cellElm
}if(AM.rowSpan>1){AM.rowSpan--
}}}}}A(AF.dom.select("br",AM),function(C,B){if(B>0&&AF.dom.getAttrib(C,"mce_bogus")){AF.dom.remove(C)
}});
break
}Ag=AB.dom.getParent(AB.selection.getNode(),"table");
AB.addVisual(Ag);
AB.nodeChanged()
}return true
}return false
}});
tinymce.PluginManager.add("table",tinymce.plugins.TablePlugin)
})();