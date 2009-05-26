function writeFlash(A){writeEmbed("D27CDB6E-AE6D-11cf-96B8-444553540000","http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0","application/x-shockwave-flash",A)
}function writeShockWave(A){writeEmbed("166B1BCA-3F9C-11CF-8075-444553540000","http://download.macromedia.com/pub/shockwave/cabs/director/sw.cab#version=8,5,1,0","application/x-director",A)
}function writeQuickTime(A){writeEmbed("02BF25D5-8C17-4B23-BC80-D3488ABDDC6B","http://www.apple.com/qtactivex/qtplugin.cab#version=6,0,2,0","video/quicktime",A)
}function writeRealMedia(A){writeEmbed("CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA","http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0","audio/x-pn-realaudio-plugin",A)
}function writeWindowsMedia(A){A.url=A.src;
writeEmbed("6BF52A52-394A-11D3-B153-00C04F79FAA6","http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701","application/x-mplayer2",A)
}function writeEmbed(B,A,C,E){var D="",F;
D+='<object classid="clsid:'+B+'" codebase="'+A+'"';
D+=typeof (E.id)!="undefined"?'id="'+E.id+'"':"";
D+=typeof (E.name)!="undefined"?'name="'+E.name+'"':"";
D+=typeof (E.width)!="undefined"?'width="'+E.width+'"':"";
D+=typeof (E.height)!="undefined"?'height="'+E.height+'"':"";
D+=typeof (E.align)!="undefined"?'align="'+E.align+'"':"";
D+=">";
for(F in E){D+='<param name="'+F+'" value="'+E[F]+'">'
}D+='<embed type="'+C+'"';
for(F in E){D+=F+'="'+E[F]+'" '
}D+="></embed></object>";
document.write(D)
};