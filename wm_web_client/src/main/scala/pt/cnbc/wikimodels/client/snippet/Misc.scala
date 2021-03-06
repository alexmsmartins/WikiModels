/*
 * Copyright 2007-2010 WorldWide Conferencing, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pt.cnbc.wikimodels{
package client {
package snippet {


import _root_.net.liftweb._
import http._
import mapper._
import S._
import SHtml._

import common._
import util._
import Helpers._

import net.liftweb.common.{Box,Full,Empty,Failure,ParamFailure}

import _root_.java.util.Locale

import _root_.pt.cnbc.wikimodels.util.SBMLDocHandler._
import _root_.pt.cnbc.wikimodels.client.model.{User => Usr}

import xml._

class Misc {
  //TODO - check if this class has unused code
  //TODO - refactor the code in Misc to a file and class with a more explanatory name

  private object selectedUser extends RequestVar[Box[Usr]](Empty)

  /**
   * Get the XHTML containing a list of users
   */
  def users: NodeSeq = {
    Usr.find() match {
      case Empty => Usr.create.firstName("Archer").lastName("Dog").email("archer@dogfood.com").password("mypassword").save
      case _ =>
    }
    // the header
    <tr>{Usr.htmlHeaders}<th>Edit</th><th>Delete</th></tr> ::
    // get and display each of the users
    Usr.findAll(OrderBy(Usr.id, Ascending)).flatMap(u => <tr>{u.htmlLine}
        <td>{link("/simple/edit", () => selectedUser(Full(u)), Text("Edit"))}</td>
        <td>{link("/simple/delete", () => selectedUser(Full(u)), Text("Delete"))}</td>
                                                           </tr>)
  }

  /**
   * Confirm deleting a user
   */
  def confirmDelete(xhtml: Group): NodeSeq = {
    (for (user <- selectedUser.is) // find the user
     yield {
        def deleteUser() {
          notice("User "+(user.firstName+" "+user.lastName)+" deleted")
          user.delete_!
          redirectTo("/simple/index.html")
        }

        // bind the incoming XHTML to a "delete" button.
        // when the delete button is pressed, call the "deleteUser"
        // function (which is a closure and bound the "user" object
        // in the current content)
        bind("xmp", xhtml, "username" -> (user.firstName.is+" "+user.lastName.is),
             "delete" -> submit("Delete", deleteUser _))

        // if the was no ID or the user couldn't be found,
        // display an error and redirect
      }) openOr {S.error("User not found"); redirectTo("/simple/index.html")}
  }

  // called when the form is submitted
  private def saveUser(user: Usr) = user.validate match {
    // no validation errors, save the user, and go
    // back to the "list" page
    case Nil => user.save; redirectTo("/simple/index.html")

      // oops... validation errors
      // display the errors and make sure our selected user is still the same
    case x => S.error(x); selectedUser(Full(user))
  }

  /**
   * Add a user
   */
  def add(xhtml: Group): NodeSeq =
  selectedUser.is.openOr(new Usr).toForm(Empty, saveUser _) ++ <tr>
    <td><a href="/simple/index.html">Cancel</a></td>
    <td><input type="submit" value="Create"/></td>
                                                                </tr>

  /**
   * Edit a user
   */
  def edit(xhtml: Group): NodeSeq =
  selectedUser.map(_.
                   // get the form data for the user and when the form
                   // is submitted, call the passed function.
                   // That means, when the user submits the form,
                   // the fields that were typed into will be populated into
                   // "user" and "saveUser" will be called.  The
                   // form fields are bound to the model's fields by this
                   // call.
                   toForm(Empty, saveUser _) ++ <tr>
      <td><a href="/simple/index.html">Cancel</a></td>
      <td><input type="submit" value="Save"/></td>
                                                </tr>

                   // bail out if the ID is not supplied or the user's not found
  ) openOr {S.error("User not found"); redirectTo("/simple/index.html")}

  // the request-local variable that hold the file parameter
  private object theUpload extends RequestVar[Box[FileParamHolder]](Empty)

  /**
   * Bind the appropriate XHTML to the form
   */
  def upload(xhtml: Group): NodeSeq =
    if (S.get_?) {
      Console.print("jgdfsghwqefjiofqeuhqefwiohiu")
      uploadDialogue(xhtml, Empty)
    }
    else {
      val fileBox = theUpload.is.map(v => v.file)
      fileBox match {
        case Full(file) => {
          debug("imported File content was obtained")
          debug(new String(file,"UTF-8"))
          val modelBox:Box[Elem] = extractModelTagfromSBML(new String(file, "UTF-8"))
          modelBox match {
            case Full(model) => {
              debug("Model XML tag was extracted")
              User.restfulConnection.postRequest("/model/", model)
              User.restfulConnection.getStatusCode match {
                case 201 => {
                  debug("Creating the model in the server succeded")
                  bind("ul", chooseTemplate("choose", "post", xhtml),
                    "file_name" -> theUpload.is.map(v => Text(v.fileName)),
                    "mime_type" -> theUpload.is.map(v => Box.legacyNullTest(v.mimeType).map(Text(_)).openOr(Text("No mime type supplied"))), // Text(v.mimeType)),
                    "length" -> theUpload.is.map(v => Text(v.file.length.toString)),
                    "md5" -> theUpload.is.map(v => Text(hexEncode(md5(v.file))))
                  );
                }
                case 500 => uploadDialogue(xhtml, Failure(Text("[STATUS" + User.restfulConnection.getStatusCode) + ": A server internal error occured in the WikiModels KnowledgeBase. Please report it to Alexandre Martins at alexmsmartins@gmail.com with the model that caused the error."))
                case _ => {
                  debug("Creating the model in the server failed with status code "+ User.restfulConnection.getStatusCode + ". Please report it to Alexandre Martins at alexmsmartins@gmail.com.")
                  uploadDialogue(xhtml, Failure("Importing the model to the knowledgebase wasn't possible. The statuscode was " + User.restfulConnection.getStatusCode))
                }
              }
            }
            case x => uploadDialogue(xhtml, x)
          }
        }
        case _ => uploadDialogue(xhtml, Failure("No such file was located."))
      }
    }


  private def uploadDialogue(xhtml:Group, error:Box[Elem]):  NodeSeq  = {
    //variable error message makes it possible for an implicit conversion from String -> NodeSeq to BoxBindParameter
    val errorMessage:Text = error match {
                        case Failure(msg,_,_) => Text(msg)
                        case _ => Text("")
                      }
    bind("ul", chooseTemplate("choose", "get", xhtml),
                      "error_message" -> errorMessage,
                      "file_upload" -> fileUpload(ul => theUpload(Full(ul))))
  }


  def lang = {
    "#lang" #> locale.getDisplayLanguage(locale) &
    "#select" #> SHtml.selectObj(locales.map(lo => (lo, lo.getDisplayName)),
                                 definedLocale, setLocale)
  }

  private def locales =
  Locale.getAvailableLocales.toList.sortWith(_.getDisplayName < _.getDisplayName)

  private def setLocale(loc: Locale) = definedLocale(Full(loc))
}

object definedLocale extends SessionVar[Box[Locale]](Empty)


}
}
}
