/*
 * AddNotesTag.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.util

import scala.xml.transform.RewriteRule
import scala.xml.Elem
import scala.xml.MetaData
import scala.xml.Node
import scala.xml.TopScope
import scala.xml.UnprefixedAttribute

import pt.cnbc.wikimodels.exceptions.BadFormatException

class AddNotesTagRule extends RewriteRule{
    /** a name for this rewrite rule */
    override val name = this.toString()
    override def transform(ns: Seq[Node]): Seq[Node] = {
    <notes>{ns.map(i => {
                    new Elem(null,
                             i.label,
                             new  UnprefixedAttribute("xmlns",
                                                      "http://www.w3c.org/1999/xhtml",
                                                      scala.xml.Null),
                             TopScope,
                             i.child:_*)
                } ) }</notes>;
    }
}
