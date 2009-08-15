/*
 * ModelsDAO.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataAccess

import com.hp.hpl.jena.rdf.model.Model
import com.hp.hpl.jena.rdf.model.ModelFactory
import com.hp.hpl.jena.reasoner.Reasoner
import com.hp.hpl.jena.reasoner.ReasonerRegistry
import com.hp.hpl.jena.ontology.OntModel
import com.hp.hpl.jena.ontology.OntModelSpec
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.impl.OntClassImpl;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import thewebsemantic.Bean2RDF
import thewebsemantic.RDF2Bean

import scala.Collection

import pt.cnbc.wikimodels.dataModel.SBMLModel
import pt.cnbc.wikimodels.dataModel.Comment
import pt.cnbc.wikimodels.dataModel.Element
import pt.cnbc.wikimodels.exceptions.NotImplementedException
import pt.cnbc.wikimodels.ontology.ManipulatorWrapper
import pt.cnbc.wikimodels.util.ImplicitConversions

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

    /**
     * Method that creates a new Model in the KnowledgeBase after checking
     * if everything is valid with the model that is being created
     * This method also issues an available metaid
     *
     */
    def trytoCreateSBMLModel(sbmlModel:SBMLModel):Boolean = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            trytoCreateSBMLModel(sbmlModel, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + SBMLModel.getClass + "and " +
                                    "id is not found")
                    ex.printStackTrace()
                    false
                }
            case ex => {
                    ex.printStackTrace()
                    false
                }
        }
    }

    def trytoCreateSBMLModel(sbmlModel:SBMLModel, model:Model):Boolean = {

        if( !modelMetaidExists(sbmlModel.metaid )  ){
            createSBMLModel(sbmlModel, model)
        } else {
            createSBMLModel(generateNewMetaIdfor(sbmlModel, model),
                            model)
        }
    }

    def generateNewMetaIdfor(sbmlentity:Element):Element = {
        var xx = sbmlentity.asInstanceOf[ImplicitConversions.IdAndName].id
        """if(xx == null || xx.trim == ""){
            if(!metaidExists( xx ))
                sbmlentity.metaid
                else

        }"""
        null
    }

    def generateNewMetaIdfor(sbmlModel:SBMLModel, model:Model):SBMLModel = {
        sbmlModel
    }


    /**
     * checks if metaid exists in WikiModels KnowledgeBase
     * the metaid is meant to be unique in all of the KB adn used as id for the
     * entities created within it
     */
    def metaidExists(metaid:String):Boolean = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            metaidExists(metaid, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + SBMLModel.getClass + "and " +
                                    "id is not found")
                    ex.printStackTrace()
                    false
                }
            case ex => {
                    ex.printStackTrace()
                    false
                }
        }
    }

    def metaidExists(metaid:String, model:Model):Boolean = {
        val reasoner:Reasoner = ReasonerRegistry.getOWLReasoner
        val ontModelSpec:OntModelSpec = null
        val ont:OntModel = ModelFactory.createOntologyModel(ontModelSpec, model)

        val queryString =
                """
        PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
        PREFIX rdf: <<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        ASK
        { ?s sbml:metaid sbml:""" + metaid + " }"

        val query:Query = QueryFactory.create(queryString);
        val qe:QueryExecution = QueryExecutionFactory.create(query, ont);
        val results:Boolean = qe.execAsk;

        Console.println("SPARQL query \n" + queryString + "\nIs " + results)

        results
    }

    def modelMetaidExists(metaid:String):Boolean = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            modelMetaidExists(metaid, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + SBMLModel.getClass + "and " +
                                    "id is not found")
                    ex.printStackTrace()
                    false
                }
            case ex => {
                    ex.printStackTrace()
                    false
                }
        }

    }

    def modelMetaidExists(metaid:String, model:Model):Boolean = {
        val reasoner:Reasoner = ReasonerRegistry.getOWLReasoner
        val ontModelSpec:OntModelSpec = null
        val ont:OntModel = ModelFactory.createOntologyModel(ontModelSpec, model)

        val queryString =
                """
        PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
        PREFIX rdf: <<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        ASK
        { ?s rdf:type sbml:Model .
          ?s sbml:metaid sbml:""" + metaid + " }"

        val query:Query = QueryFactory.create(queryString);
        val qe:QueryExecution = QueryExecutionFactory.create(query, ont);
        val results:Boolean = qe.execAsk;

        Console.println("SPARQL query \n" + queryString + "\nIs " + results)
        
        results
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
