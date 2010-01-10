/*
 * Modifier.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.snippet

class Modifier {
    var id:String = null
    var name:String = null
    var modifier_specie:String = null
    var modifier_stoic:String = null
    var modifier_stoic_math:String = null
    var modifier_note:String = null


    // Getter
    def get_modifier_id = id

    // Setter
    def set_modifier_id (value:String):Unit = {id = value}

    // Getter
    def get_modifier_name = name

    // Setter
    def set_modifier_name (value:String):Unit = {name = value}

    // Getter
    def get_modifier_specie = modifier_specie

    // Setter
    def set_modifier_specie (value:String):Unit = {modifier_specie = value}

    // Getter
    def get_modifier_stoic = modifier_stoic

    // Setter
    def set_modifier_stoic (value:String):Unit = {modifier_stoic = value}

    // Getter
    def get_modifier_stoic_math = modifier_stoic_math

    // Setter
    def set_modifier_stoic_math (value:String):Unit = {modifier_stoic_math = value}

    // Getter
    def get_modifier_note = modifier_note

    // Setter
    def set_modifier_note (value:String):Unit = {modifier_note = value}
}
