/*
 * ImplicitConversions.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.util

import pt.cnbc.wikimodels.dataModel.SBMLModel
import pt.cnbc.wikimodels.dataModel.Parameter


/**
 * id and name are attributes that are present in many descendatns of Sbase
 * according to the SBML specification. Yet their are not present in every one
 * of them and, as such, are not declared in the base class of the data model
 * hierachy.
 * This object compensates for this by allowing access to these field in a
 * seamless way.
 */
object ImplicitConversions {
        class IdAndName {
            var id:String = null
            var name:String = null
            def this(sbmlModel:SBMLModel) = {
                this()
                this.id = sbmlModel.id
                this.name = sbmlModel.name
            }

            def this(parameter:Parameter) = {
                this()
                this.id = parameter.id
                this.name = parameter.name
            }
        }

        implicit def model2IdName(sbmlModel:SBMLModel) =
            new IdAndName(sbmlModel)
        implicit def parameter2IdName(parameter:Parameter) =
            new IdAndName(parameter)
}
