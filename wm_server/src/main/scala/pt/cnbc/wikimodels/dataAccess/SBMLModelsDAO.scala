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

    protected def loadSBMLModel(sbmlmodelID:String, model:Model):SBMLModel = {
        new SBMLModel()
    }

    def loadSBMLModel(sbmlmodelID:String):SBMLModel = {
        val myModel:Model = ManipulatorWrapper.loadModelfromDB
        loadSBMLModel(sbmlmodelID, myModel)
    }

    /**
     * Saves an SBMLModel into the KnowledgeBase
     * @param  true if
     * @return true if
     */
    def createSBMLModel(sbmlmodel:SBMLModel):Boolean = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            createSBMLModel(sbmlmodel, myModel)
        } catch {
            case ex:Exception => {
                    Console.println("Saving model " + sbmlmodel +
                                    "was not possible")
                    ex.printStackTrace

                    false
                }
        }
    }


    /**
     * Creates a new SBML model individual in the Knowledgebase
     * @return true if creating the new model was possible and false otherwise
     */
    def createSBMLModel(sbmlmodel:SBMLModel, model:Model):Boolean = {
        try{
            val writer = new Bean2RDF(model)
            writer.save(sbmlmodel)
            true
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + SBMLModel.getClass + "and " +
                                    "id is not found")
                    ex.printStackTrace()
                    false
                }
            case ex => {
                    Console.println(ex.toString)
                    ex.printStackTrace()
                    false
                }
        }
    }


    def loadSBMLModel():List[SBMLModel] = {
        val myModel:Model = ManipulatorWrapper.loadModelfromDB
        Nil
    }

    /**
     * Method that creates a new Model in the KnowledgeBase after checking
     * if everything is valid with the model that is being created
     * This method also issues an available metaid
     *
     */
    def trytoCreateSBMLModel(sbmlModel:SBMLModel):Boolean = {
        val myModel:Model = ManipulatorWrapper.loadModelfromDB
        trytoCreateSBMLModel(sbmlModel, myModel)
    }

    def trytoCreateSBMLModel(sbmlModel:SBMLModel, model:Model):Boolean = {
        if(true){ //metaidExists(sbmlModel.metaid )  ){
            createSBMLModel(sbmlModel, model)
        } else {
            false
        }
    }

    def modelIDExists(id:String, modelId:String):Boolean = {
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
        ManipulatorWrapper.loadModelfromDB

        false
        
    }


    /*def loadModel():SBMLModel
     def updateModel(model:SBMLModel):Boolean
     def makeComment(comment:Comment)
     def getComments(metaid:String)*/
}
