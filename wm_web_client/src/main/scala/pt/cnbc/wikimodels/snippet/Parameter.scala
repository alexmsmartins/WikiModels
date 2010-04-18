/*
 * Product.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.snippet

class Parameter {
    var id:String = null
    var name:String = null
    var parameter_value:String = null
    var parameter_constant:String = null
    var parameter_note:String = null


    // Getter
    def get_parameter_id = id

    // Setter
    def set_parameter_id (value:String):Unit = {id = value}

    // Getter
    def get_parameter_name = name

    // Setter
    def set_parameter_name (value:String):Unit = {name = value}

    // Getter
    def get_parameter_value = parameter_value

    // Setter
    def set_parameter_value (value:String):Unit = {parameter_value = value}

    // Getter
    def get_parameter_constant = parameter_constant

    // Setter
    def set_parameter_constant (value:String):Unit = {parameter_constant = value}

    // Getter
    def get_parameter_note = parameter_note

    // Setter
    def set_parameter_note (value:String):Unit = {parameter_note = value}
}
