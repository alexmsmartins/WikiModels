	/*
 * Comment.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataModel
import thewebsemantic.{Id, RdfProperty, Namespace}
import reflect.BeanInfo
import xml.Elem

@BeanInfo
@Namespace("http://rdfs.org/sioc/types#")
class Comment() extends DataModel {
  @Id
  var metaid:String = null

  @RdfProperty("http://wikimodels.cnbc.pt/ontologies/sbml.owl#hasParameter")
  var userName:String = null

  @RdfProperty("http://rdfs.org/sioc/ns#title")
  var title:String = null

  @RdfProperty("http://rdfs.org/sioc/ns#content")
  var content:String = null

  @RdfProperty("http://rdfs.org/sioc/ns#has_reply")
  var replies:List[Comment] = new java.util.ArrayList()

  @RdfProperty("http://rdfs.org/sioc/ns#reply_of")
  var replyOf:Comment = null


  def this(metaId:String,
          userName:String,
          content:Elem,
          replyTo:String) = {
    this()
    this.metaid = metaId
    this.userName= userName
    this.content = content.toString
    //this.replyOf
  }


  def toXML:Elem = {
    <comment metaid={metaid} title={title} userName={userName} replyTo={replyOf.metaid}>
      { if(this.replies !=null)
      <listOfReplies>
        {replies.map(_.toXML)}
      </listOfReplies>
        else scala.xml.Null
      }
      <content>
        {if (content==null) scala.xml.XML.loadString(content)
         else scala.xml.Null
        }
      </content>
    </comment>
  }
}
