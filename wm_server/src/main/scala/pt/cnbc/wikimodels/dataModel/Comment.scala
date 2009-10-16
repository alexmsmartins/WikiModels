	/*
 * Comment.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel
import scala.xml.Elem
import pt.cnbc.wikimodels.exceptions.NotImplementedException

class Comment(userName:String,
              elementMetaId:String,
              content:Elem) extends DataModel {

    def toXML:Elem = {
        //TODO implement toXML from Comment class
        throw new NotImplementedException("toXML in class Comment is not implemented yet")
    }
}
