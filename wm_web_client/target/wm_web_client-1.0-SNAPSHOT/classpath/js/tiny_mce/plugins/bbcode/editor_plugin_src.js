(function(){tinymce.create("tinymce.plugins.BBCodePlugin",{init:function(A,B){var D=this,C=A.getParam("bbcode_dialect","punbb").toLowerCase();
A.onBeforeSetContent.add(function(E,F){F.content=D["_"+C+"_bbcode2html"](F.content)
});
A.onPostProcess.add(function(E,F){if(F.set){F.content=D["_"+C+"_bbcode2html"](F.content)
}if(F.get){F.content=D["_"+C+"_html2bbcode"](F.content)
}})
},getInfo:function(){return{longname:"BBCode Plugin",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/bbcode",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_punbb_html2bbcode:function(A){A=tinymce.trim(A);
function B(C,D){A=A.replace(C,D)
}B(/<a.*?href=\"(.*?)\".*?>(.*?)<\/a>/gi,"[url=$1]$2[/url]");
B(/<font.*?color=\"(.*?)\".*?class=\"codeStyle\".*?>(.*?)<\/font>/gi,"[code][color=$1]$2[/color][/code]");
B(/<font.*?color=\"(.*?)\".*?class=\"quoteStyle\".*?>(.*?)<\/font>/gi,"[quote][color=$1]$2[/color][/quote]");
B(/<font.*?class=\"codeStyle\".*?color=\"(.*?)\".*?>(.*?)<\/font>/gi,"[code][color=$1]$2[/color][/code]");
B(/<font.*?class=\"quoteStyle\".*?color=\"(.*?)\".*?>(.*?)<\/font>/gi,"[quote][color=$1]$2[/color][/quote]");
B(/<span style=\"color: ?(.*?);\">(.*?)<\/span>/gi,"[color=$1]$2[/color]");
B(/<font.*?color=\"(.*?)\".*?>(.*?)<\/font>/gi,"[color=$1]$2[/color]");
B(/<span style=\"font-size:(.*?);\">(.*?)<\/span>/gi,"[size=$1]$2[/size]");
B(/<font>(.*?)<\/font>/gi,"$1");
B(/<img.*?src=\"(.*?)\".*?\/>/gi,"[img]$1[/img]");
B(/<span class=\"codeStyle\">(.*?)<\/span>/gi,"[code]$1[/code]");
B(/<span class=\"quoteStyle\">(.*?)<\/span>/gi,"[quote]$1[/quote]");
B(/<strong class=\"codeStyle\">(.*?)<\/strong>/gi,"[code][b]$1[/b][/code]");
B(/<strong class=\"quoteStyle\">(.*?)<\/strong>/gi,"[quote][b]$1[/b][/quote]");
B(/<em class=\"codeStyle\">(.*?)<\/em>/gi,"[code][i]$1[/i][/code]");
B(/<em class=\"quoteStyle\">(.*?)<\/em>/gi,"[quote][i]$1[/i][/quote]");
B(/<u class=\"codeStyle\">(.*?)<\/u>/gi,"[code][u]$1[/u][/code]");
B(/<u class=\"quoteStyle\">(.*?)<\/u>/gi,"[quote][u]$1[/u][/quote]");
B(/<\/(strong|b)>/gi,"[/b]");
B(/<(strong|b)>/gi,"[b]");
B(/<\/(em|i)>/gi,"[/i]");
B(/<(em|i)>/gi,"[i]");
B(/<\/u>/gi,"[/u]");
B(/<span style=\"text-decoration: ?underline;\">(.*?)<\/span>/gi,"[u]$1[/u]");
B(/<u>/gi,"[u]");
B(/<blockquote[^>]*>/gi,"[quote]");
B(/<\/blockquote>/gi,"[/quote]");
B(/<br \/>/gi,"\n");
B(/<br\/>/gi,"\n");
B(/<br>/gi,"\n");
B(/<p>/gi,"");
B(/<\/p>/gi,"\n");
B(/&nbsp;/gi," ");
B(/&quot;/gi,'"');
B(/&lt;/gi,"<");
B(/&gt;/gi,">");
B(/&amp;/gi,"&");
return A
},_punbb_bbcode2html:function(A){A=tinymce.trim(A);
function B(C,D){A=A.replace(C,D)
}B(/\n/gi,"<br />");
B(/\[b\]/gi,"<strong>");
B(/\[\/b\]/gi,"</strong>");
B(/\[i\]/gi,"<em>");
B(/\[\/i\]/gi,"</em>");
B(/\[u\]/gi,"<u>");
B(/\[\/u\]/gi,"</u>");
B(/\[url=([^\]]+)\](.*?)\[\/url\]/gi,'<a href="$1">$2</a>');
B(/\[url\](.*?)\[\/url\]/gi,'<a href="$1">$1</a>');
B(/\[img\](.*?)\[\/img\]/gi,'<img src="$1" />');
B(/\[color=(.*?)\](.*?)\[\/color\]/gi,'<font color="$1">$2</font>');
B(/\[code\](.*?)\[\/code\]/gi,'<span class="codeStyle">$1</span>&nbsp;');
B(/\[quote.*?\](.*?)\[\/quote\]/gi,'<span class="quoteStyle">$1</span>&nbsp;');
return A
}});
tinymce.PluginManager.add("bbcode",tinymce.plugins.BBCodePlugin)
})();