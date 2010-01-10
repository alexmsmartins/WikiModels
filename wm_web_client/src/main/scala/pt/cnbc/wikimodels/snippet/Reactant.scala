/*
 * Reactant.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.snippet

class Reactant {
    var id:String = null
    var name:String = null
    var reactant_specie:String = null
    var reactant_stoic:String = null
    var reactant_stoic_math:String = null
    var reactant_note:String = null


    // Getter
    def get_reactant_id = id

    // Setter
    def set_reactant_id (value:String):Unit = {id = value}

    // Getter
    def get_reactant_name = name

    // Setter
    def set_reactant_name (value:String):Unit = {name = value}

    // Getter
    def get_reactant_specie = reactant_specie

    // Setter
    def set_reactant_specie (value:String):Unit = {reactant_specie = value}

    // Getter
    def get_reactant_stoic = reactant_stoic

    // Setter
    def set_reactant_stoic (value:String):Unit = {reactant_stoic = value}

    // Getter
    def get_reactant_stoic_math = reactant_stoic_math

    // Setter
    def set_reactant_stoic_math (value:String):Unit = {reactant_stoic_math = value}

    // Getter
    def get_reactant_note = reactant_note

    // Setter
    def set_reactant_note (value:String):Unit = {reactant_note = value}
}
