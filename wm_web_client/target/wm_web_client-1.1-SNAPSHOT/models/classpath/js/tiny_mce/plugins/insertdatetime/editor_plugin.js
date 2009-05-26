(function(){tinymce.create("tinymce.plugins.InsertDateTime",{init:function(B,A){var C=this;
C.editor=B;
B.addCommand("mceInsertDate",function(){var D=C._getDateTime(new Date(),B.getParam("plugin_insertdate_dateFormat",B.getLang("insertdatetime.date_fmt")));
B.execCommand("mceInsertContent",false,D)
});
B.addCommand("mceInsertTime",function(){var D=C._getDateTime(new Date(),B.getParam("plugin_insertdate_timeFormat",B.getLang("insertdatetime.time_fmt")));
B.execCommand("mceInsertContent",false,D)
});
B.addButton("insertdate",{title:"insertdatetime.insertdate_desc",cmd:"mceInsertDate"});
B.addButton("inserttime",{title:"insertdatetime.inserttime_desc",cmd:"mceInsertTime"})
},getInfo:function(){return{longname:"Insert date/time",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/insertdatetime",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_getDateTime:function(C,B){var D=this.editor;
function A(E,G){E=""+E;
if(E.length<G){for(var F=0;
F<(G-E.length);
F++){E="0"+E
}}return E
}B=B.replace("%D","%m/%d/%y");
B=B.replace("%r","%I:%M:%S %p");
B=B.replace("%Y",""+C.getFullYear());
B=B.replace("%y",""+C.getYear());
B=B.replace("%m",A(C.getMonth()+1,2));
B=B.replace("%d",A(C.getDate(),2));
B=B.replace("%H",""+A(C.getHours(),2));
B=B.replace("%M",""+A(C.getMinutes(),2));
B=B.replace("%S",""+A(C.getSeconds(),2));
B=B.replace("%I",""+((C.getHours()+11)%12+1));
B=B.replace("%p",""+(C.getHours()<12?"AM":"PM"));
B=B.replace("%B",""+D.getLang("insertdatetime.months_long").split(",")[C.getMonth()]);
B=B.replace("%b",""+D.getLang("insertdatetime.months_short").split(",")[C.getMonth()]);
B=B.replace("%A",""+D.getLang("insertdatetime.day_long").split(",")[C.getDay()]);
B=B.replace("%a",""+D.getLang("insertdatetime.day_short").split(",")[C.getDay()]);
B=B.replace("%%","%");
return B
}});
tinymce.PluginManager.add("insertdatetime",tinymce.plugins.InsertDateTime)
})();