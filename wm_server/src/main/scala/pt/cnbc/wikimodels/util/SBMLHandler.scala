/*
 * SBMLHandler.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.util
import scala.xml.Node
import org.sbml.libsbml.SBMLDocument
import org.sbml.libsbml.SBMLReader
import scala.xml.Elem
import scala.xml.NodeSeq
import scala.xml.UnprefixedAttribute
import scala.xml.TopScope

class SBMLHandler {
    LibSBMLLoader()



    /**
     * This method should be run before any other method in this object
     * It olads the libSBML bindings for java.
     */
    def validateSBML(sbml:Node, sbmlLevel:Int, sbmlVersion:Int):Boolean = {
        validateSBML(sbml.toString, sbmlLevel, sbmlVersion)
    }

    def validateSBML(sbml:String, sbmlLevel:Int, sbmlVersion:Int) = {
        val reader = new SBMLReader()
        reader.readSBMLFromString(
            """<?xml version="1.0" encoding="UTF-8"?>\n\
            """ + sbml.toString)

        val sbmlDoc = new SBMLDocument(sbmlLevel, sbmlVersion)


        Console.println("checkL2v4Compatibility errors:" + sbmlDoc.checkL2v4Compatibility )
        Console.println("checkConsistency errors:" + sbmlDoc.checkConsistency)
        Console.println("checkInternalConsistency errors:" + sbmlDoc.checkInternalConsistency)
        if(sbmlDoc.checkInternalConsistency >0){
        Console.println("Error: " + sbmlDoc.getError(0).getErrorId + "\n" +
                        "Category:"+ sbmlDoc.getError(0).getCategoryAsString + "\n" +
                        "Line:"+ sbmlDoc.getError(0).getLine + "\n" +
                        "Column:"+ sbmlDoc.getError(0).getColumn + "\n" +
                        "Message:"+ sbmlDoc.getError(0).getMessage + "\n" +
                        "Sevegory:"+ sbmlDoc.getError(0).getSeverityAsString + "\n")
        }

        val checkCompat = (sbmlLevel, sbmlVersion) match {
            case (2,1) => (sbmlDoc.checkL2v1Compatibility == 0)
            case (2,2) => (sbmlDoc.checkL2v4Compatibility == 0)
            case (2,3) => (sbmlDoc.checkL2v4Compatibility == 0)
            case (2,4) => (sbmlDoc.checkL2v4Compatibility == 0)
            case _ => false //more options in the future
        }
        //TODO INCLUDE checkInternalConsistency when bug in BIOMD0000000141 is pinpionted
        (checkCompat &&
        (sbmlDoc.checkL2v4Compatibility == 0) &&
        (sbmlDoc.checkConsistency == 0)) /* &&
        (sbmlDoc.checkInternalConsistency == 0)*/
    }


    /**
     * Produces the XML of the <notes section of any SBase
     * @returns a <notes> sections with the content of notesContent
     * embebed inside or null if there is no content to embeb
     */
    def spitNotes(notesContent:Node):Elem =
        if(notesContent != null){
            <notes>
                {addNamespaceToXML(notesContent,
                                   "http://www.w3c.org/1999/xhtml")}
            </notes> } else null

    def addNamespaceToXML(ns:NodeSeq, namespace:String):NodeSeq = {
        ns.map(i => {
                   new Elem(null,
                            i.label,
                            new  UnprefixedAttribute("xmlns",
                                         namespace,
                                         null),
                            TopScope,
                            i.descendant:_*)
            }
        )
    }
}

object LibSBMLLoader {
    def apply() = {
        try {
            //            System.setProperty("java.library.path", "/usr/local/lib:/usr/local:" + System.getProperty("java.library.path"));
            //System.out.println(System.getProperty("java.library.path"));

            //            System.loadLibrary("libsbml");
            System.load("/usr/local/lib/libsbmlj.so");
            /* Extra check to be sure we have access to libSBML: */
            Class.forName("org.sbml.libsbml.libsbml");
        } catch {
            case e:SecurityException  =>{
                    System.err.println("A security manager exists and its"+
                                       "<code>checkLink</code> method doesn't"+
                                       "allow loading of the specified dynamic "+
                                       "library: ")
                    e.printStackTrace
                }
            case e:UnsatisfiedLinkError => {
                    Console.println("UnsatisfiedLinkError  if the file "+
                                    "does not exist: ")
                    e.printStackTrace
                }
            case e:NullPointerException=>{
                    Console.println("<code>filename</code> is "+
                                    "<code>null</code>")
                    e.printStackTrace
                }
        }
    }
}
