/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.client.snippet

import scala.xml._

import net.liftweb._
import common._
import util.Helpers._
import http._
import sitemap._
import mapper._
import S._
import SHtml._
import util._
import Helpers._
import java.lang.String

//Javascript handling imports
import _root_.net.liftweb.http.js.{JE,JsCmd,JsCmds}
import JsCmds._ // For implicifts
import JE.{JsRaw,Str}

import alexmsmartins.log.LoggerWrapper

/**
 * Snippet based on information from http://docs.disqus.com/developers/universal/
 * @author: alex
 * Date: 13-12-2011
 * Time: 10:48
 */
trait CommentSnip extends  LoggerWrapper {

  /**
   *  Tells Disqus which website account (called a forum on Disqus) this system belongs to.
   */
  lazy val disqusShortname = "wikimodels" //TODO this should be loaded from a properties file or something,common to all of wikimodels

  /**
   * Rturns a snipped that binds DISQUS commenting system to the HTML of the page
   * @parameter disqusIdentifier tells Disqus how to uniquely identify the current page
   */
  def disqusFromMetaId(disqusIdentifier:String):NodeSeq = {
       <span>
         <div id="disqus_thread"></div>
         {Script(
           JsRaw("""
             /* * * CONFIGURATION VARIABLES: EDIT BEFORE PASTING INTO YOUR WEBPAGE * * */
             var disqus_developer = 1; // developer mode is on
             var disqus_shortname = '""" + disqusShortname + """'; // required: replace example with your forum shortname
             var disqus_identifier = '""" + disqusIdentifier + """';

             /* * * DON'T EDIT BELOW THIS LINE * * */
             (function() {
               var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
               dsq.src = 'http://' + disqus_shortname + '.disqus.com/embed.js';
               (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
             })();
         """))}
         <noscript>Please enable JavaScript to view the <a href="http://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
           <a href="http://disqus.com" class="dsq-brlink">blog comments powered by <span class="logo-disqus">Disqus</span></a>
       </span>
  }

}