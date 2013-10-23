/*
 * ModelShowTable.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.client.snippet

import scala.xml.NodeSeq
import net.liftmodules.widgets.tablesorter.TableSorter

class ModelShowTable {
    def render(xhtml: NodeSeq): NodeSeq = TableSorter("table-id")
}
