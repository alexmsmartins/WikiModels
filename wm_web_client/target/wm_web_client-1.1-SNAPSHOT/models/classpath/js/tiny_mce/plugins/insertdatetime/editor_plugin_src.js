(function(){tinymce.create("tinymce.plugins.InsertDateTime",{init:function(A,B){var C=this;
C.editor=A;
A.addCommand("mceInsertDate",function(){var D=C._getDateTime(new Date(),A.getParam("plugin_insertdate_dateFormat",A.getLang("insertdatetime.date_fmt")));
A.execCommand("mceInsertContent",false,D)
});
A.addCommand("mceInsertTime",function(){var D=C._getDateTime(new Date(),A.getParam("plugin_insertdate_timeFormat",A.getLang("insertdatetime.time_fmt")));
A.execCommand("mceInsertContent",false,D)
});
A.addButton("insertdate",{title:"insertdatetime.insertdate_desc",cmd:"mceInsertDate"});
A.addButton("inserttime",{title:"insertdatetime.inserttime_desc",cmd:"mceInsertTime"})
},getInfo:function(){return{longname:"Insert date/time",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/insertdatetime",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_getDateTime:function(D,A){var C=this.editor;
function B(G,E){G=""+G;
if(G.length<E){for(var F=0;
F<(E-G.length);
F++){G="0"+G
}}return G
}A=A.replace("%D","%m/%d/%y");
A=A.replace("%r","%I:%M:%S %p");
A=A.replace("%Y",""+D.getFullYear());
A=A.replace("%y",""+D.getYear());
A=A.replace("%m",B(D.getMonth()+1,2));
A=A.replace("%d",B(D.getDate(),2));
A=A.replace("%H",""+B(D.getHours(),2));
A=A.replace("%M",""+B(D.getMinutes(),2));
A=A.replace("%S",""+B(D.getSeconds(),2));
A=A.replace("%I",""+((D.getHours()+11)%12+1));
A=A.replace("%p",""+(D.getHours()<12?"AM":"PM"));
A=A.replace("%B",""+C.getLang("insertdatetime.months_long").split(",")[D.getMonth()]);
A=A.replace("%b",""+C.getLang("insertdatetime.months_short").split(",")[D.getMonth()]);
A=A.replace("%A",""+C.getLang("insertdatetime.day_long").split(",")[D.getDay()]);
A=A.replace("%a",""+C.getLang("insertdatetime.day_short").split(",")[D.getDay()]);
A=A.replace("%%","%");
return A
}});
tinymce.PluginManager.add("insertdatetime",tinymce.plugins.InsertDateTime)
})();