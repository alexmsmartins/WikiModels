/*
 * ModelsDAO.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataAccess

import pt.cnbc.wikimodels.dataModel.SBMLModel
import pt.cnbc.wikimodels.ontology.ManipulatorWrapper
import thewebsemantic.RDF2Bean
import com.hp.hpl.jena.rdf.model.Model
import scala.Collection

object SBMLModelsDAO {
    def loadSBMLModel(sbmlmodelID:String):SBMLModel = {
        new SBMLModel()
    }

    def loadSBMLModels():List[SBMLModel] = {
        null
    }
}
