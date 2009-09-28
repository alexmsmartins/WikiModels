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
    
    def newComment (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
                var comment:String = null
                var count:int = 0
                var existingComments:NodeSeq = null
                var commentCreated:NodeSeq = null
                var finalComment:NodeSeq = null
                //val novo = S.getHeaders(list)
                val readListComments = XML.load("comment.xml")
                //Console.println("Valor ="+novo.map(s => s))
                def createNewComment () = {
                    existingComments = (readListComments \ "comment")
                    if (comment.length == 0) {
                        S.error("Invalid Data")
                    } else {
                        count = count+1
                        println("CODE "+comment)
                        commentCreated = {
                            <comment metaId={count} userName={user} aboutMetaId={count}>
                                <notes>{XML.loadString(comment)}
                                </notes>
                            </comment>
                        }
                        if(existingComments.theSeq != null){
                            finalComment = {
                                <comments>{existingComments.theSeq}
                                    {commentCreated.theSeq}</comments>
                            }}
                        else {
                            finalComment = {
                                <comments>
                                    {commentCreated.theSeq}</comments>
                            }
                        }
                        XML.save("comment.xml", finalComment.elements.next)
                    }
                }
                bind("createComment", xhtml,
                     "commentBox" -> SHtml.textarea("", comment = _, ("id", "commentArea"), ("maxlength", "20000")),
                     "close" -> <input type="button" id="buttonCancelComment" value="Cancel Comment" onclick="window.close();" />,
                     "save" -> SHtml.submit("Save Comment", createNewComment,("id","buttonSaveComment"), ("onclick","window.close()")))
            }
        case _ => Text("")
    }
    
    def viewComments (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
                val readComment:NodeSeq = XML.load("comment.xml")
                var var1:String = ""
                var var2:String = ""
                var var3:String = ""

                bind("view", xhtml,
                     "data" -> {
                        println("COMMENT "+(readComment \\ "comment").toString)
                        if((readComment \\ "comment").toString == ""){
                            <div>
                                <tr>
                                    <td id="userTable">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td id="cTable"><br />&nbsp;There are no comments<br /><br /></td>
                                </tr>
                            </div>
                        } else {
                            for(val fun <- readComment \ "comment";
                                val fun2 <- (fun \\ "@userName");
                                val addFun = (fun \\ "notes")) yield{
                                var com = addFun.toString
                                if(com.contains("notes")){
                                    var1 = com.replaceAll("notes", "div")
                                }
                                val com_notes = XML.loadString(var1)
                                <div>
                                    <tr>
                                        <td id="userTable">&nbsp;<b>User - </b>{fun2}</td>
                                    </tr>
                                    <tr>
                                        <td id="cTable">&nbsp;{com_notes}<br /></td>
                                    </tr>
                                    <tr>
                                        <td id="cReply"><a onclick="window.open('../models/new_comment.html','New Comment','width=600,height=300');">Reply</a></td>
                                    </tr><br /><br />
                                </div>}
                        }})
            }
        case _ => Text("")
    }

}
