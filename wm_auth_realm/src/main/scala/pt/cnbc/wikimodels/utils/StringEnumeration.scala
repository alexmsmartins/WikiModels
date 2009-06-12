/*
 * GroupsEnueration.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.utils

import java.lang.String
import java.util.Enumeration

case class StringEnumeration(val list:List[String]) extends java.util.Enumeration[String]{
    var it = list.elements

    override def hasMoreElements:Boolean = {
        it.hasNext
    }

    override def nextElement:String = {
        it.next
    }
}
