(function(){var A=tinymce.each;
tinymce.create("tinymce.plugins.TablePlugin",{init:function(B,C){var D=this;
D.editor=B;
D.url=C;
A([["table","table.desc","mceInsertTable",true],["delete_table","table.del","mceTableDelete"],["delete_col","table.delete_col_desc","mceTableDeleteCol"],["delete_row","table.delete_row_desc","mceTableDeleteRow"],["col_after","table.col_after_desc","mceTableInsertColAfter"],["col_before","table.col_before_desc","mceTableInsertColBefore"],["row_after","table.row_after_desc","mceTableInsertRowAfter"],["row_before","table.row_before_desc","mceTableInsertRowBefore"],["row_props","table.row_desc","mceTableRowProps",true],["cell_props","table.cell_desc","mceTableCellProps",true],["split_cells","table.split_cells_desc","mceTableSplitCells",true],["merge_cells","table.merge_cells_desc","mceTableMergeCells",true]],function(E){B.addButton(E[0],{title:E[1],cmd:E[2],ui:E[3]})
});
if(B.getParam("inline_styles")){B.onPreProcess.add(function(E,G){var F=E.dom;
A(F.select("table",G.node),function(I){var H;
if(H=F.getAttrib(I,"width")){F.setStyle(I,"width",H);
F.setAttrib(I,"width")
}if(H=F.getAttrib(I,"height")){F.setStyle(I,"height",H);
F.setAttrib(I,"height")
}})
})
}B.onInit.add(function(){if(B&&B.plugins.contextmenu){B.plugins.contextmenu.onContextMenu.add(function(G,E,I){var J,H=B.selection,F=H.getNode()||B.getBody();
if(B.dom.getParent(I,"td")||B.dom.getParent(I,"th")){E.removeAll();
if(F.nodeName=="A"&&!B.dom.getAttrib(F,"name")){E.add({title:"advanced.link_desc",icon:"link",cmd:B.plugins.advlink?"mceAdvLink":"mceLink",ui:true});
E.add({title:"advanced.unlink_desc",icon:"unlink",cmd:"UnLink"});
E.addSeparator()
}if(F.nodeName=="IMG"&&F.className.indexOf("mceItem")==-1){E.add({title:"advanced.image_desc",icon:"image",cmd:B.plugins.advimage?"mceAdvImage":"mceImage",ui:true});
E.addSeparator()
}E.add({title:"table.desc",icon:"table",cmd:"mceInsertTable",ui:true,value:{action:"insert"}});
E.add({title:"table.props_desc",icon:"table_props",cmd:"mceInsertTable",ui:true});
E.add({title:"table.del",icon:"delete_table",cmd:"mceTableDelete",ui:true});
E.addSeparator();
J=E.addMenu({title:"table.cell"});
J.add({title:"table.cell_desc",icon:"cell_props",cmd:"mceTableCellProps",ui:true});
J.add({title:"table.split_cells_desc",icon:"split_cells",cmd:"mceTableSplitCells",ui:true});
J.add({title:"table.merge_cells_desc",icon:"merge_cells",cmd:"mceTableMergeCells",ui:true});
J=E.addMenu({title:"table.row"});
J.add({title:"table.row_desc",icon:"row_props",cmd:"mceTableRowProps",ui:true});
J.add({title:"table.row_before_desc",icon:"row_before",cmd:"mceTableInsertRowBefore"});
J.add({title:"table.row_after_desc",icon:"row_after",cmd:"mceTableInsertRowAfter"});
J.add({title:"table.delete_row_desc",icon:"delete_row",cmd:"mceTableDeleteRow"});
J.addSeparator();
J.add({title:"table.cut_row_desc",icon:"cut",cmd:"mceTableCutRow"});
J.add({title:"table.copy_row_desc",icon:"copy",cmd:"mceTableCopyRow"});
J.add({title:"table.paste_row_before_desc",icon:"paste",cmd:"mceTablePasteRowBefore"});
J.add({title:"table.paste_row_after_desc",icon:"paste",cmd:"mceTablePasteRowAfter"});
J=E.addMenu({title:"table.col"});
J.add({title:"table.col_before_desc",icon:"col_before",cmd:"mceTableInsertColBefore"});
J.add({title:"table.col_after_desc",icon:"col_after",cmd:"mceTableInsertColAfter"});
J.add({title:"table.delete_col_desc",icon:"delete_col",cmd:"mceTableDeleteCol"})
}else{E.add({title:"table.desc",icon:"table",cmd:"mceInsertTable",ui:true})
}})
}});
B.onKeyDown.add(function(E,F){if(F.keyCode==9&&E.dom.getParent(E.selection.getNode(),"TABLE")){if(!tinymce.isGecko&&!tinymce.isOpera){tinyMCE.execInstanceCommand(E.editorId,"mceTableMoveToNextRow",true);
return tinymce.dom.Event.cancel(F)
}E.undoManager.add()
}});
if(!tinymce.isIE){if(B.getParam("table_selection",true)){B.onClick.add(function(E,F){F=F.target;
if(F.nodeName==="TABLE"){E.selection.select(F)
}})
}}B.onNodeChange.add(function(F,E,H){var G=F.dom.getParent(H,"td,th,caption");
E.setActive("table",H.nodeName==="TABLE"||!!G);
if(G&&G.nodeName==="CAPTION"){G=null
}E.setDisabled("delete_table",!G);
E.setDisabled("delete_col",!G);
E.setDisabled("delete_table",!G);
E.setDisabled("delete_row",!G);
E.setDisabled("col_after",!G);
E.setDisabled("col_before",!G);
E.setDisabled("row_after",!G);
E.setDisabled("row_before",!G);
E.setDisabled("row_props",!G);
E.setDisabled("cell_props",!G);
E.setDisabled("split_cells",!G||(parseInt(F.dom.getAttrib(G,"colspan","1"))<2&&parseInt(F.dom.getAttrib(G,"rowspan","1"))<2));
E.setDisabled("merge_cells",!G)
});
if(!tinymce.isIE){B.onBeforeSetContent.add(function(E,F){if(F.initial){F.content=F.content.replace(/<(td|th)([^>]+|)>\s*<\/(td|th)>/g,tinymce.isOpera?"<$1$2>&nbsp;</$1>":'<$1$2><br mce_bogus="1" /></$1>')
}})
}},execCommand:function(E,D,F){var C=this.editor,B;
switch(E){case"mceTableMoveToNextRow":case"mceInsertTable":case"mceTableRowProps":case"mceTableCellProps":case"mceTableSplitCells":case"mceTableMergeCells":case"mceTableInsertRowBefore":case"mceTableInsertRowAfter":case"mceTableDeleteRow":case"mceTableInsertColBefore":case"mceTableInsertColAfter":case"mceTableDeleteCol":case"mceTableCutRow":case"mceTableCopyRow":case"mceTablePasteRowBefore":case"mceTablePasteRowAfter":case"mceTableDelete":C.execCommand("mceBeginUndoLevel");
this._doExecCommand(E,D,F);
C.execCommand("mceEndUndoLevel");
return true
}return false
},getInfo:function(){return{longname:"Tables",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/table",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_doExecCommand:function(Q,z,AE){var t=this.editor,AT=t,G=this.url;
var M=t.selection.getNode();
var u=t.dom.getParent(M,"tr");
var AQ=t.dom.getParent(M,"td,th");
var c=t.dom.getParent(M,"table");
var J=t.contentWindow.document;
var AU=c?c.getAttribute("border"):"";
if(u&&AQ==null){AQ=u.cells[0]
}function AP(y,x){for(var AW=0;
AW<y.length;
AW++){if(y[AW].length>0&&AP(y[AW],x)){return true
}if(y[AW]==x){return true
}}return false
}function AJ(x,i){var y;
AD=E(c);
x=x||0;
i=i||0;
x=Math.max(N.cellindex+x,0);
i=Math.max(N.rowindex+i,0);
t.execCommand("mceRepaint");
y=D(AD,i,x);
if(y){t.selection.select(y.firstChild||y);
t.selection.collapse(1)
}}function AH(){var i=J.createElement("td");
if(!tinymce.isIE){i.innerHTML='<br mce_bogus="1"/>'
}}function I(y){var x=t.dom.getAttrib(y,"colspan");
var i=t.dom.getAttrib(y,"rowspan");
x=x==""?1:parseInt(x);
i=i==""?1:parseInt(i);
return{colspan:x,rowspan:i}
}function AL(AW,AY){var i,AX;
for(AX=0;
AX<AW.length;
AX++){for(i=0;
i<AW[AX].length;
i++){if(AW[AX][i]==AY){return{cellindex:i,rowindex:AX}
}}}return null
}function D(x,y,i){if(x[y]&&x[y][i]){return x[y][i]
}return null
}function X(Ab,AW){var AY=[],y=0,AZ,AX,AW,Aa;
for(AZ=0;
AZ<Ab.rows.length;
AZ++){for(AX=0;
AX<Ab.rows[AZ].cells.length;
AX++,y++){AY[y]=Ab.rows[AZ].cells[AX]
}}for(AZ=0;
AZ<AY.length;
AZ++){if(AY[AZ]==AW){if(Aa=AY[AZ+1]){return Aa
}}}}function E(Ad){var i=[],Ae=Ad.rows,Ab,Aa,AX,AY,Ac,AW,AZ;
for(Aa=0;
Aa<Ae.length;
Aa++){for(Ab=0;
Ab<Ae[Aa].cells.length;
Ab++){AX=Ae[Aa].cells[Ab];
AY=I(AX);
for(Ac=Ab;
i[Aa]&&i[Aa][Ac];
Ac++){}for(AZ=Aa;
AZ<Aa+AY.rowspan;
AZ++){if(!i[AZ]){i[AZ]=[]
}for(AW=Ac;
AW<Ac+AY.colspan;
AW++){i[AZ][AW]=AX
}}}}return i
}function L(Af,Ac,AX,AW){var y=E(Af),Ae=AL(y,AX);
var Ag,Ab;
if(AW.cells.length!=Ac.childNodes.length){Ag=Ac.childNodes;
Ab=null;
for(var Ad=0;
AX=D(y,Ae.rowindex,Ad);
Ad++){var AZ=true;
var Aa=I(AX);
if(AP(Ag,AX)){AW.childNodes[Ad]._delete=true
}else{if((Ab==null||AX!=Ab)&&Aa.colspan>1){for(var AY=Ad;
AY<Ad+AX.colSpan;
AY++){AW.childNodes[AY]._delete=true
}}}if((Ab==null||AX!=Ab)&&Aa.rowspan>1){AX.rowSpan=Aa.rowspan+1
}Ab=AX
}Y(c)
}}function m(x,i){while((x=x.previousSibling)!=null){if(x.nodeName==i){return x
}}return null
}function AF(AW,AX){var x=AX.split(",");
while((AW=AW.nextSibling)!=null){for(var y=0;
y<x.length;
y++){if(AW.nodeName.toLowerCase()==x[y].toLowerCase()){return AW
}}}return null
}function Y(AW){if(AW.rows==0){return 
}var y=AW.rows[0];
do{var x=AF(y,"TR");
if(y._delete){y.parentNode.removeChild(y);
continue
}var AX=y.cells[0];
if(AX.cells>1){do{var i=AF(AX,"TD,TH");
if(AX._delete){AX.parentNode.removeChild(AX)
}}while((AX=i)!=null)
}}while((y=x)!=null)
}function O(AW,AZ,AY){AW.rowSpan=1;
var x=AF(AZ,"TR");
for(var AX=1;
AX<AY&&x;
AX++){var y=J.createElement("td");
if(!tinymce.isIE){y.innerHTML='<br mce_bogus="1"/>'
}if(tinymce.isIE){x.insertBefore(y,x.cells(AW.cellIndex))
}else{x.insertBefore(y,x.cells[AW.cellIndex])
}x=AF(x,"TR")
}}function q(Ae,Ag,Aa){var y=E(Ag);
var AW=Aa.cloneNode(false);
var Af=AL(y,Aa.cells[0]);
var Ab=null;
var AZ=t.dom.getAttrib(Ag,"border");
var AY=null;
for(var Ad=0;
AY=D(y,Af.rowindex,Ad);
Ad++){var Ac=null;
if(Ab!=AY){for(var AX=0;
AX<Aa.cells.length;
AX++){if(AY==Aa.cells[AX]){Ac=AY.cloneNode(true);
break
}}}if(Ac==null){Ac=Ae.createElement("td");
if(!tinymce.isIE){Ac.innerHTML='<br mce_bogus="1"/>'
}}Ac.colSpan=1;
Ac.rowSpan=1;
AW.appendChild(Ac);
Ab=AY
}return AW
}switch(Q){case"mceTableMoveToNextRow":var j=X(c,AQ);
if(!j){t.execCommand("mceTableInsertRowAfter",AQ);
j=X(c,AQ)
}t.selection.select(j);
t.selection.collapse(true);
return true;
case"mceTableRowProps":if(u==null){return true
}if(z){t.windowManager.open({url:G+"/row.htm",width:400+parseInt(t.getLang("table.rowprops_delta_width",0)),height:295+parseInt(t.getLang("table.rowprops_delta_height",0)),inline:1},{plugin_url:G})
}return true;
case"mceTableCellProps":if(AQ==null){return true
}if(z){t.windowManager.open({url:G+"/cell.htm",width:400+parseInt(t.getLang("table.cellprops_delta_width",0)),height:295+parseInt(t.getLang("table.cellprops_delta_height",0)),inline:1},{plugin_url:G})
}return true;
case"mceInsertTable":if(z){t.windowManager.open({url:G+"/table.htm",width:400+parseInt(t.getLang("table.table_delta_width",0)),height:320+parseInt(t.getLang("table.table_delta_height",0)),inline:1},{plugin_url:G,action:AE?AE.action:0})
}return true;
case"mceTableDelete":var d=t.dom.getParent(t.selection.getNode(),"table");
if(d){d.parentNode.removeChild(d);
t.execCommand("mceRepaint")
}return true;
case"mceTableSplitCells":case"mceTableMergeCells":case"mceTableInsertRowBefore":case"mceTableInsertRowAfter":case"mceTableDeleteRow":case"mceTableInsertColBefore":case"mceTableInsertColAfter":case"mceTableDeleteCol":case"mceTableCutRow":case"mceTableCopyRow":case"mceTablePasteRowBefore":case"mceTablePasteRowAfter":if(!c){return true
}if(u&&c!=u.parentNode){c=u.parentNode
}if(c&&u){switch(Q){case"mceTableCutRow":if(!u||!AQ){return true
}t.tableRowClipboard=q(J,c,u);
t.execCommand("mceTableDeleteRow");
break;
case"mceTableCopyRow":if(!u||!AQ){return true
}t.tableRowClipboard=q(J,c,u);
break;
case"mceTablePasteRowBefore":if(!u||!AQ){return true
}var U=t.tableRowClipboard.cloneNode(true);
var H=m(u,"TR");
if(H!=null){L(c,H,H.cells[0],U)
}u.parentNode.insertBefore(U,u);
break;
case"mceTablePasteRowAfter":if(!u||!AQ){return true
}var v=AF(u,"TR");
var U=t.tableRowClipboard.cloneNode(true);
L(c,u,AQ,U);
if(v==null){u.parentNode.appendChild(U)
}else{v.parentNode.insertBefore(U,v)
}break;
case"mceTableInsertRowBefore":if(!u||!AQ){return true
}var AD=E(c);
var N=AL(AD,AQ);
var U=J.createElement("tr");
var T=null;
N.rowindex--;
if(N.rowindex<0){N.rowindex=0
}for(var AC=0;
AQ=D(AD,N.rowindex,AC);
AC++){if(AQ!=T){var b=I(AQ);
if(b.rowspan==1){var g=J.createElement("td");
if(!tinymce.isIE){g.innerHTML='<br mce_bogus="1"/>'
}g.colSpan=AQ.colSpan;
U.appendChild(g)
}else{AQ.rowSpan=b.rowspan+1
}T=AQ
}}u.parentNode.insertBefore(U,u);
AJ(0,1);
break;
case"mceTableInsertRowAfter":if(!u||!AQ){return true
}var AD=E(c);
var N=AL(AD,AQ);
var U=J.createElement("tr");
var T=null;
for(var AC=0;
AQ=D(AD,N.rowindex,AC);
AC++){if(AQ!=T){var b=I(AQ);
if(b.rowspan==1){var g=J.createElement("td");
if(!tinymce.isIE){g.innerHTML='<br mce_bogus="1"/>'
}g.colSpan=AQ.colSpan;
U.appendChild(g)
}else{AQ.rowSpan=b.rowspan+1
}T=AQ
}}if(U.hasChildNodes()){var v=AF(u,"TR");
if(v){v.parentNode.insertBefore(U,v)
}else{c.appendChild(U)
}}AJ(0,1);
break;
case"mceTableDeleteRow":if(!u||!AQ){return true
}var AD=E(c);
var N=AL(AD,AQ);
if(AD.length==1&&c.nodeName=="TBODY"){t.dom.remove(t.dom.getParent(c,"table"));
return true
}var a=u.cells;
var v=AF(u,"TR");
for(var AC=0;
AC<a.length;
AC++){if(a[AC].rowSpan>1){var g=a[AC].cloneNode(true);
var b=I(a[AC]);
g.rowSpan=b.rowspan-1;
var AK=v.cells[AC];
if(AK==null){v.appendChild(g)
}else{v.insertBefore(g,AK)
}}}var T=null;
for(var AC=0;
AQ=D(AD,N.rowindex,AC);
AC++){if(AQ!=T){var b=I(AQ);
if(b.rowspan>1){AQ.rowSpan=b.rowspan-1
}else{u=AQ.parentNode;
if(u.parentNode){u._delete=true
}}T=AQ
}}Y(c);
AJ(0,-1);
break;
case"mceTableInsertColBefore":if(!u||!AQ){return true
}var AD=E(t.dom.getParent(c,"table"));
var N=AL(AD,AQ);
var T=null;
for(var AA=0;
AQ=D(AD,AA,N.cellindex);
AA++){if(AQ!=T){var b=I(AQ);
if(b.colspan==1){var g=J.createElement(AQ.nodeName);
if(!tinymce.isIE){g.innerHTML='<br mce_bogus="1"/>'
}g.rowSpan=AQ.rowSpan;
AQ.parentNode.insertBefore(g,AQ)
}else{AQ.colSpan++
}T=AQ
}}AJ();
break;
case"mceTableInsertColAfter":if(!u||!AQ){return true
}var AD=E(t.dom.getParent(c,"table"));
var N=AL(AD,AQ);
var T=null;
for(var AA=0;
AQ=D(AD,AA,N.cellindex);
AA++){if(AQ!=T){var b=I(AQ);
if(b.colspan==1){var g=J.createElement(AQ.nodeName);
if(!tinymce.isIE){g.innerHTML='<br mce_bogus="1"/>'
}g.rowSpan=AQ.rowSpan;
var AK=AF(AQ,"TD,TH");
if(AK==null){AQ.parentNode.appendChild(g)
}else{AK.parentNode.insertBefore(g,AK)
}}else{AQ.colSpan++
}T=AQ
}}AJ(1);
break;
case"mceTableDeleteCol":if(!u||!AQ){return true
}var AD=E(c);
var N=AL(AD,AQ);
var T=null;
if((AD.length>1&&AD[0].length<=1)&&c.nodeName=="TBODY"){t.dom.remove(t.dom.getParent(c,"table"));
return true
}for(var AA=0;
AQ=D(AD,AA,N.cellindex);
AA++){if(AQ!=T){var b=I(AQ);
if(b.colspan>1){AQ.colSpan=b.colspan-1
}else{if(AQ.parentNode){AQ.parentNode.removeChild(AQ)
}}T=AQ
}}AJ(-1);
break;
case"mceTableSplitCells":if(!u||!AQ){return true
}var K=I(AQ);
var Z=K.colspan;
var e=K.rowspan;
if(Z>1||e>1){AQ.colSpan=1;
for(var AM=1;
AM<Z;
AM++){var g=J.createElement("td");
if(!tinymce.isIE){g.innerHTML='<br mce_bogus="1"/>'
}u.insertBefore(g,AF(AQ,"TD,TH"));
if(e>1){O(g,u,e)
}}O(AQ,u,e)
}c=t.dom.getParent(t.selection.getNode(),"table");
break;
case"mceTableMergeCells":var AO=[];
var p=t.selection.getSel();
var AD=E(c);
if(tinymce.isIE||p.rangeCount==1){if(z){var S=I(AQ);
t.windowManager.open({url:G+"/merge_cells.htm",width:240+parseInt(t.getLang("table.merge_cells_delta_width",0)),height:110+parseInt(t.getLang("table.merge_cells_delta_height",0)),inline:1},{action:"update",numcols:S.colspan,numrows:S.rowspan,plugin_url:G});
return true
}else{var s=parseInt(AE.numrows);
var C=parseInt(AE.numcols);
var N=AL(AD,AQ);
if((""+s)=="NaN"){s=1
}if((""+C)=="NaN"){C=1
}var B=c.rows;
for(var AA=N.rowindex;
AA<AD.length;
AA++){var AG=[];
for(var AC=N.cellindex;
AC<AD[AA].length;
AC++){var F=D(AD,AA,AC);
if(F&&!AP(AO,F)&&!AP(AG,F)){var l=AL(AD,F);
if(l.cellindex<N.cellindex+C&&l.rowindex<N.rowindex+s){AG[AG.length]=F
}}}if(AG.length>0){AO[AO.length]=AG
}var F=D(AD,N.rowindex,N.cellindex);
A(AT.dom.select("br",F),function(y,x){if(x>0&&AT.dom.getAttrib("mce_bogus")){AT.dom.remove(y)
}})
}}}else{var a=[];
var p=t.selection.getSel();
var w=null;
var AN=null;
var W=-1,AV=-1,V,AS;
if(p.rangeCount<2){return true
}for(var AM=0;
AM<p.rangeCount;
AM++){var AI=p.getRangeAt(AM);
var AQ=AI.startContainer.childNodes[AI.startOffset];
if(!AQ){break
}if(AQ.nodeName=="TD"||AQ.nodeName=="TH"){a[a.length]=AQ
}}var B=c.rows;
for(var AA=0;
AA<B.length;
AA++){var AG=[];
for(var AC=0;
AC<B[AA].cells.length;
AC++){var F=B[AA].cells[AC];
for(var AM=0;
AM<a.length;
AM++){if(F==a[AM]){AG[AG.length]=F
}}}if(AG.length>0){AO[AO.length]=AG
}}var AN=[];
var w=null;
for(var AA=0;
AA<AD.length;
AA++){for(var AC=0;
AC<AD[AA].length;
AC++){AD[AA][AC]._selected=false;
for(var AM=0;
AM<a.length;
AM++){if(AD[AA][AC]==a[AM]){if(W==-1){W=AC;
AV=AA
}V=AC;
AS=AA;
AD[AA][AC]._selected=true
}}}}for(var AA=AV;
AA<=AS;
AA++){for(var AC=W;
AC<=V;
AC++){if(!AD[AA][AC]._selected){alert("Invalid selection for merge.");
return true
}}}}var R=1,P=1;
var r=-1;
for(var AA=0;
AA<AO.length;
AA++){var f=0;
for(var AC=0;
AC<AO[AA].length;
AC++){var b=I(AO[AA][AC]);
f+=b.colspan;
if(r!=-1&&b.rowspan!=r){alert("Invalid selection for merge.");
return true
}r=b.rowspan
}if(f>P){P=f
}r=-1
}var o=-1;
for(var AC=0;
AC<AO[0].length;
AC++){var k=0;
for(var AA=0;
AA<AO.length;
AA++){var b=I(AO[AA][AC]);
k+=b.rowspan;
if(o!=-1&&b.colspan!=o){alert("Invalid selection for merge.");
return true
}o=b.colspan
}if(k>R){R=k
}o=-1
}AQ=AO[0][0];
AQ.rowSpan=R;
AQ.colSpan=P;
for(var AA=0;
AA<AO.length;
AA++){for(var AC=0;
AC<AO[AA].length;
AC++){var n=AO[AA][AC].innerHTML;
var h=n.replace(/[ \t\r\n]/g,"");
if(h!="<br/>"&&h!="<br>"&&h!='<br mce_bogus="1"/>'&&(AC+AA>0)){AQ.innerHTML+=n
}if(AO[AA][AC]!=AQ&&!AO[AA][AC]._deleted){var N=AL(AD,AO[AA][AC]);
var AR=AO[AA][AC].parentNode;
AR.removeChild(AO[AA][AC]);
AO[AA][AC]._deleted=true;
if(!AR.hasChildNodes()){AR.parentNode.removeChild(AR);
var AB=null;
for(var AC=0;
cellElm=D(AD,N.rowindex,AC);
AC++){if(cellElm!=AB&&cellElm.rowSpan>1){cellElm.rowSpan--
}AB=cellElm
}if(AQ.rowSpan>1){AQ.rowSpan--
}}}}}A(AT.dom.select("br",AQ),function(y,x){if(x>0&&AT.dom.getAttrib(y,"mce_bogus")){AT.dom.remove(y)
}});
break
}c=t.dom.getParent(t.selection.getNode(),"table");
t.addVisual(c);
t.nodeChanged()
}return true
}return false
}});
tinymce.PluginManager.add("table",tinymce.plugins.TablePlugin)
})();