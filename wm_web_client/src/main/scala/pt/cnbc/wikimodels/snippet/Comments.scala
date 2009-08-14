/*
 * Comments.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.snippet

import net.liftweb.http._
import net.liftweb._
import http._
import util._
import Helpers._
import sitemap.Loc.If
import scala.xml.{ XML, Elem, Group, Node, NodeSeq, Null, Text, TopScope }
import S._
import net.liftweb.http.js._
import js.JsCmds
import js.JsCmds._
import js.Jx
import js.JE
import js.JE._


class Comments {
    
    def newComment () = {
        val list = List[(String,String)]()
        val novo = S.getHeaders(list)
        Console.println("Valor ="+novo.map(s => s))
    }
}
