/*
 * ModelsDAO.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataAccess

import com.hp.hpl.jena.rdf.model.Model

import thewebsemantic.Bean2RDF
import thewebsemantic.RDF2Bean

import scala.Collection

import pt.cnbc.wikimodels.dataModel.SBMLModel
import pt.cnbc.wikimodels.dataModel.Comment
import pt.cnbc.wikimodels.exceptions.NotImplementedException
import pt.cnbc.wikimodels.ontology.ManipulatorWrapper

class SBMLModelsDAO {

    /**
     * Allows testing procedures. This is not to be used from outside this class
     */
    var  kb:Model = null

    def loadSBMLModel(sbmlmodelID:String):SBMLModel = {
        new SBMLModel()
    }

    def loadSBMLModels():List[SBMLModel] = {
        Nil
    }

    /**
     * Method that creates a new Model in the KnowledgeBase
      * issuing it an available metaid
     */
    def trytoCreateSBMLModel(sbmlModel:SBMLModel):Boolean = {
        if( metaidExists(sbmlModel.metaid )  ){
           false
        } else {
           false
        }
    }

    def modelIDExists(id:String):Boolean = {
       false
    }

    /**
     * checks if metaid exists in WikiModels KnowledgeBase
     * the metaid is meant to be unique in all of the KB adn used as id for the
     * entities created within it
     */
    def metaidExists(metaid:String):Boolean = {
        val myModel:Model = ManipulatorWrapper.loadModelfromDB
        myModel.getGraph.contains(null, null, null)
    }

    def obtainNewMetaId():String = throw new NotImplementedException(
        "obtainNewMetaId method is not implemented")

    /**
     * Checks if id in Model is already taken
     * @param
     */
    def idInModelExists(modelid:String, id:String) = {
        false
        
    }


    /*def loadModel():SBMLModel
    def updateModel(model:SBMLModel):Boolean
    def makeComment(comment:Comment)
    def getComments(metaid:String)*/
}
