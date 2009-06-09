(function(){tinymce.create("tinymce.plugins.BBCodePlugin",{init:function(B,A){var C=this,D=B.getParam("bbcode_dialect","punbb").toLowerCase();
B.onBeforeSetContent.add(function(F,E){E.content=C["_"+D+"_bbcode2html"](E.content)
});
B.onPostProcess.add(function(F,E){if(E.set){E.content=C["_"+D+"_bbcode2html"](E.content)
}if(E.get){E.content=C["_"+D+"_html2bbcode"](E.content)
}})
},getInfo:function(){return{longname:"BBCode Plugin",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/bbcode",version:tinymce.majorVersion+"."+tinymce.minorVersion}
},_punbb_html2bbcode:function(B){B=tinymce.trim(B);
function A(D,C){B=B.replace(D,C)
}A(/<a.*?href=\"(.*?)\".*?>(.*?)<\/a>/gi,"[url=$1]$2[/url]");
A(/<font.*?color=\"(.*?)\".*?class=\"codeStyle\".*?>(.*?)<\/font>/gi,"[code][color=$1]$2[/color][/code]");
A(/<font.*?color=\"(.*?)\".*?class=\"quoteStyle\".*?>(.*?)<\/font>/gi,"[quote][color=$1]$2[/color][/quote]");
A(/<font.*?class=\"codeStyle\".*?color=\"(.*?)\".*?>(.*?)<\/font>/gi,"[code][color=$1]$2[/color][/code]");
A(/<font.*?class=\"quoteStyle\".*?color=\"(.*?)\".*?>(.*?)<\/font>/gi,"[quote][color=$1]$2[/color][/quote]");
A(/<span style=\"color: ?(.*?);\">(.*?)<\/span>/gi,"[color=$1]$2[/color]");
A(/<font.*?color=\"(.*?)\".*?>(.*?)<\/font>/gi,"[color=$1]$2[/color]");
A(/<span style=\"font-size:(.*?);\">(.*?)<\/span>/gi,"[size=$1]$2[/size]");
A(/<font>(.*?)<\/font>/gi,"$1");
A(/<img.*?src=\"(.*?)\".*?\/>/gi,"[img]$1[/img]");
A(/<span class=\"codeStyle\">(.*?)<\/span>/gi,"[code]$1[/code]");
A(/<span class=\"quoteStyle\">(.*?)<\/span>/gi,"[quote]$1[/quote]");
A(/<strong class=\"codeStyle\">(.*?)<\/strong>/gi,"[code][b]$1[/b][/code]");
A(/<strong class=\"quoteStyle\">(.*?)<\/strong>/gi,"[quote][b]$1[/b][/quote]");
A(/<em class=\"codeStyle\">(.*?)<\/em>/gi,"[code][i]$1[/i][/code]");
A(/<em class=\"quoteStyle\">(.*?)<\/em>/gi,"[quote][i]$1[/i][/quote]");
A(/<u class=\"codeStyle\">(.*?)<\/u>/gi,"[code][u]$1[/u][/code]");
A(/<u class=\"quoteStyle\">(.*?)<\/u>/gi,"[quote][u]$1[/u][/quote]");
A(/<\/(strong|b)>/gi,"[/b]");
A(/<(strong|b)>/gi,"[b]");
A(/<\/(em|i)>/gi,"[/i]");
A(/<(em|i)>/gi,"[i]");
A(/<\/u>/gi,"[/u]");
A(/<span style=\"text-decoration: ?underline;\">(.*?)<\/span>/gi,"[u]$1[/u]");
A(/<u>/gi,"[u]");
A(/<blockquote[^>]*>/gi,"[quote]");
A(/<\/blockquote>/gi,"[/quote]");
A(/<br \/>/gi,"\n");
A(/<br\/>/gi,"\n");
A(/<br>/gi,"\n");
A(/<p>/gi,"");
A(/<\/p>/gi,"\n");
A(/&nbsp;/gi," ");
A(/&quot;/gi,'"');
A(/&lt;/gi,"<");
A(/&gt;/gi,">");
A(/&amp;/gi,"&");
return B
},_punbb_bbcode2html:function(B){B=tinymce.trim(B);
function A(D,C){B=B.replace(D,C)
}A(/\n/gi,"<br />");
A(/\[b\]/gi,"<strong>");
A(/\[\/b\]/gi,"</strong>");
A(/\[i\]/gi,"<em>");
A(/\[\/i\]/gi,"</em>");
A(/\[u\]/gi,"<u>");
A(/\[\/u\]/gi,"</u>");
A(/\[url=([^\]]+)\](.*?)\[\/url\]/gi,'<a href="$1">$2</a>');
A(/\[url\](.*?)\[\/url\]/gi,'<a href="$1">$1</a>');
A(/\[img\](.*?)\[\/img\]/gi,'<img src="$1" />');
A(/\[color=(.*?)\](.*?)\[\/color\]/gi,'<font color="$1">$2</font>');
A(/\[code\](.*?)\[\/code\]/gi,'<span class="codeStyle">$1</span>&nbsp;');
A(/\[quote.*?\](.*?)\[\/quote\]/gi,'<span class="quoteStyle">$1</span>&nbsp;');
return B
}});
tinymce.PluginManager.add("bbcode",tinymce.plugins.BBCodePlugin)
})();