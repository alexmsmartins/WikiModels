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
import net.liftweb.common._
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


class Comments {
    /*val ss = S.params("id")
     val e = ss.elements
     val n = e.next
     Console.println("Valor aqui "+ss+" e "+n)*/
    def newComment (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
                var comment:String = null
                var title:String = null
                var count:Int = 0
                var existingComments:NodeSeq = null
                var commentCreated:NodeSeq = null
                var finalComment:NodeSeq = null
                val dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                val date = new Date();
                val dateMsg = dateFormat.format(date);
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
                            <comment metaId={count.toString} userName={user} aboutMetaId={count.toString} subject={title} date={dateMsg}>
                                <notes>{XML.loadString(comment)}
                                </notes>
                            </comment>
                        }
                        if(existingComments.theSeq != null){
                            finalComment = {
                                <comments>{existingComments.theSeq}
                                    {commentCreated.theSeq}</comments>
                            }
                        }
                        else {
                            finalComment = {
                                <comments>
                                    {commentCreated.theSeq}</comments>
                            }
                        }
                        XML.save("comment.xml", finalComment.iterator.next)
                    }
                }
                bind("createComment", xhtml,
                     "title" -> SHtml.text("", title = _, ("id", "commentTitle"), ("maxlength", "20000")),
                     "commentBox" -> SHtml.textarea("", comment = _, ("id", "commentArea"), ("maxlength", "20000")),
                     "close" -> <input type="button" id="buttonCancelComment" value="Cancel Comment" onclick="window.close();" />,
                     "save" -> SHtml.submit("Save Comment", createNewComment,("id","buttonSaveComment"), ("onclick","window.close()")))
            }
        case _ => Text("")
    }
    def newModelComment (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
                var comment:String = null
                var title:String = null
                var count:Int = 0
                var existingComments:NodeSeq = null
                var commentCreated:NodeSeq = null
                var finalComment:NodeSeq = null
                val dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                val date = new Date();
                val dateMsg = dateFormat.format(date);
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
                            <comment metaId={count.toString} userName={user} aboutMetaId={count.toString} subject={title} date={dateMsg}>
                                <notes>{XML.loadString(comment)}
                                </notes>
                            </comment>
                        }
                        if(existingComments.theSeq != null){
                            finalComment = {
                                <comments>{existingComments.theSeq}
                                    {commentCreated.theSeq}</comments>
                            }
                        }
                        else {
                            finalComment = {
                                <comments>
                                    {commentCreated.theSeq}</comments>
                            }
                        }
                        XML.save("comment.xml", finalComment.iterator.next)
                    }
                }
                bind("createComment", xhtml,
                     "title" -> SHtml.text("", title = _, ("id", "commentTitle"), ("maxlength", "20000")),
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
                     "close" -> <input type="button" id="buttonCloseView" value="Close" onclick="window.close();" />,
                     "data" -> {
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
                            <div id="view_box">
                                <ul id="model_tree_comment" class="treeview-gray">{
                                        for(com <- readComment \ "comment";
                                            sub <- com \\ "@subject";
                                            date <- com \\ "@date";
                                            name <- (com \\ "@userName");
                                            note = (com \\ "notes")) yield{
                                            val noteCom = note.toString
                                            if(noteCom.contains("notes")){
                                                var1 = noteCom.replaceAll("notes", "div")
                                            }
                                            val com_notes = XML.loadString(var1)
                                            <li class="closed"><span>{sub}</span>
                                                <ul style="background-color:transparent; border-left:1px solid gray;">
                                                    <li><tr>
                                                            <td>&nbsp;</td>
                                                            <td id="userTableLinks"><a id="comReply">Reply</a>
                                                                <a id="comPer">Permalink</a></td>
                                                        </tr>
                                                        <tr>
                                                            <td id="userTable1">&nbsp;<b>{name}</b><br />{date}</td>
                                                            <td id="userTable2">&nbsp;{com_notes}</td>
                                                        </tr><br />
                                                    </li>
                                                    <li class="closed"><span>Reply Title</span>
                                                        <ul style="background-color:transparent; border-left:1px solid silver;">
                                                            <li><tr>
                                                                    <td>&nbsp;</td>
                                                                    <td id="userTableLinks"><a id="comReply">Reply</a>
                                                                        <a id="comPer">Permalink</a></td>
                                                                </tr>
                                                                <tr>
                                                                    <td id="userTable1">&nbsp;<b>admin</b><br />{date}</td>
                                                                    <td id="userTable2">&nbsp;This is the reply of the comment.</td>
                                                                </tr><br />
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </li>
                                        }
                                    }
                                </ul></div>
                        }
                    }
                )
            }
        case _ => Text("")
    }
    def viewAllComments (xhtml : NodeSeq) : NodeSeq = User.currentUserName match {
        case Full(user) => {
                val readComment:NodeSeq = XML.load("comment.xml")
                var var1:String = ""
                var var2:String = ""
                var var3:String = ""

                bind("view", xhtml,
                     "close" -> <input type="button" id="buttonCloseView" value="Close" onclick="window.close();" />,
                     "data" -> {
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
                            <div id="view_box">
                                <ul id="model_tree_comment" class="treeview-gray">{
                                        for(com <- readComment \ "comment";
                                            sub <- com \\ "@subject";
                                            date <- com \\ "@date";
                                            name <- (com \\ "@userName");
                                            note = (com \\ "notes")) yield{
                                            val noteCom = note.toString
                                            if(noteCom.contains("notes")){
                                                var1 = noteCom.replaceAll("notes", "div")
                                            }
                                            val com_notes = XML.loadString(var1)
                                            <li class="closed"><span>Model Comments</span>
                                                <ul style="background-color:transparent; border-left:1px solid gray;">
                                                    <li class="closed"><span>{sub}</span>
                                                        <ul style="background-color:transparent; border-left:1px solid silver;">
                                                            <li><tr>
                                                                    <td>&nbsp;</td>
                                                                    <td id="userTableLinks"><a id="comReply">Reply</a>
                                                                        <a id="comPer">Permalink</a></td>
                                                                </tr>
                                                                <tr>
                                                                    <td id="userTable1">&nbsp;<b>{name}</b><br />{date}</td>
                                                                    <td id="userTable2">&nbsp;{com_notes}</td>
                                                                </tr><br />
                                                            </li>
                                                            <li class="closed"><span>Reply</span>
                                                                <ul style="background-color:transparent; border-left:1px solid teal;">
                                                                    <li><tr>
                                                                            <td>&nbsp;</td>
                                                                            <td id="userTableLinks"><a id="comReply">Reply</a>
                                                                                <a id="comPer">Permalink</a></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td id="userTable1">&nbsp;<b>admin</b><br />{date}</td>
                                                                            <td id="userTable2">&nbsp;This is the reply of the comment.</td>
                                                                        </tr><br />
                                                                    </li>
                                                                </ul>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </li>
                                            <li class="closed"><span>Reactions Comments</span>
                                                <ul style="background-color:transparent; border-left:1px solid gray;">
                                                    <li class="closed"><span>Reaction Comment Title</span>
                                                        <ul style="background-color:transparent; border-left:1px solid silver;">
                                                            <li><tr>
                                                                    <td>&nbsp;</td>
                                                                    <td id="userTableLinks"><a id="comReply">Reply</a>
                                                                        <a id="comPer">Permalink</a></td>
                                                                </tr>
                                                                <tr>
                                                                    <td id="userTable1">&nbsp;<b>admin</b><br />{date}</td>
                                                                    <td id="userTable2">&nbsp;This is the comment of the reaction</td>
                                                                </tr><br />
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </li>
                                            <li class="closed"><span>Function Definition Comments</span>
                                                <ul style="background-color:transparent; border-left:1px solid gray;">
                                                    <li class="closed"><span>Funtion definition title #1</span>
                                                        <ul style="background-color:transparent; border-left:1px solid silver;">
                                                            <li><tr>
                                                                    <td>&nbsp;</td>
                                                                    <td id="userTableLinks"><a id="comReply">Reply</a>
                                                                        <a id="comPer">Permalink</a></td>
                                                                </tr>
                                                                <tr>
                                                                    <td id="userTable1">&nbsp;<b>{name}</b><br />{date}</td>
                                                                    <td id="userTable2">&nbsp;Function definition comment about this...</td>
                                                                </tr><br />
                                                            </li>
                                                            <li class="closed"><span>Function definition reply</span>
                                                                <ul style="background-color:transparent; border-left:1px solid teal;">
                                                                    <li><tr>
                                                                            <td>&nbsp;</td>
                                                                            <td id="userTableLinks"><a id="comReply">Reply</a>
                                                                                <a id="comPer">Permalink</a></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td id="userTable1">&nbsp;<b>admin</b><br />{date}</td>
                                                                            <td id="userTable2">&nbsp;This is the reply of the comment.</td>
                                                                        </tr><br />
                                                                    </li>
                                                                </ul>
                                                            </li>
                                                            <li class="closed"><span>Function definition reply #2</span>
                                                                <ul style="background-color:transparent; border-left:1px solid teal;">
                                                                    <li><tr>
                                                                            <td>&nbsp;</td>
                                                                            <td id="userTableLinks"><a id="comReply">Reply</a>
                                                                                <a id="comPer">Permalink</a></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td id="userTable1">&nbsp;<b>admin</b><br />{date}</td>
                                                                            <td id="userTable2">&nbsp;This is the reply of the comment.</td>
                                                                        </tr><br />
                                                                    </li>
                                                                </ul>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                    <li class="closed"><span>Funtion definition title #2</span>
                                                        <ul style="background-color:transparent; border-left:1px solid silver;">
                                                            <li><tr>
                                                                    <td>&nbsp;</td>
                                                                    <td id="userTableLinks"><a id="comReply">Reply</a>
                                                                        <a id="comPer">Permalink</a></td>
                                                                </tr>
                                                                <tr>
                                                                    <td id="userTable1">&nbsp;<b>{name}</b><br />{date}</td>
                                                                    <td id="userTable2">&nbsp;Function definition comment about this...</td>
                                                                </tr><br />
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </li>
                                        }
                                    }
                                </ul></div>
                        }
                    }
                )
            }
        case _ => Text("")
    }

}
