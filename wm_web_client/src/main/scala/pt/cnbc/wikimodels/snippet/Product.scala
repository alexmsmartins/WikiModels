/*
 * Product.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.snippet

class Product {
    var id:String = null
    var name:String = null
    var product_specie:String = null
    var product_stoic:String = null
    var product_stoic_math:String = null
    var product_note:String = null


    // Getter
    def get_product_id = id

    // Setter
    def set_product_id (value:String):Unit = {id = value}

    // Getter
    def get_product_name = name

    // Setter
    def set_product_name (value:String):Unit = {name = value}

    // Getter
    def get_product_specie = product_specie

    // Setter
    def set_product_specie (value:String):Unit = {product_specie = value}

    // Getter
    def get_product_stoic = product_stoic

    // Setter
    def set_product_stoic (value:String):Unit = {product_stoic = value}

    // Getter
    def get_product_stoic_math = product_stoic_math

    // Setter
    def set_product_stoic_math (value:String):Unit = {product_stoic_math = value}

    // Getter
    def get_product_note = product_note

    // Setter
    def set_product_note (value:String):Unit = {product_note = value}
}
