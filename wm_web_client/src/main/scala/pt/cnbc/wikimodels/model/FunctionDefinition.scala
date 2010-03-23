/*
 * functionDefinition.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.model
import java.util.Vector

class FunctionDefinition {
    var function_def_name: Vector[String] = null
    var function_def_id: Vector[String] = null
    var function_def_math: Vector[String] = null
    var function_def_note: Vector[String] = null

    def setName(name: String) = {
        function_def_name.add(name)
    }
    def setId(id: String) = {
        function_def_id.add(id)
    }
    def setMath(math: String) = {
        function_def_math.add(math)
    }
    def setNote(note: String) = {
        function_def_note.add(note)
    }

    def getName(): Vector[String] = {
        return function_def_name
    }
    def getId(): Vector[String] = {
        return function_def_id
    }
    def getMath(): Vector[String] = {
        return function_def_math
    }
    def getNote(): Vector[String] = {
        return function_def_note
    }
}
