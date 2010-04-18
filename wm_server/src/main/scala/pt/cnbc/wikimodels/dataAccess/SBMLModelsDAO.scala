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
import scalaj.collection.Imports._

import pt.cnbc.wikimodels.dataModel.SBMLModel
import pt.cnbc.wikimodels.dataModel.Comment
import pt.cnbc.wikimodels.dataModel.Element
import pt.cnbc.wikimodels.exceptions.NotImplementedException
import pt.cnbc.wikimodels.ontology.ManipulatorWrapper
import com.hp.hpl.jena.rdf.model.InfModel
import thewebsemantic.Sparql

class SBMLModelsDAO {

    /**
     * Allows testing procedures. This is not to be used from outside this class
     */
    var  kb:Model = null


    def loadSBMLModel(modelMetaid:String):SBMLModel = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            loadSBMLModel(modelMetaid, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException =>
                Console.println("Bean of " + SBMLModel.getClass + "and " +
                                "id is not found")
                ex.printStackTrace()
                null
            case ex =>
                ex.printStackTrace()
                null
        }
    }

    def loadSBMLModel(modelMetaid:String, model:Model):SBMLModel = {
        var ret:SBMLModel = null

        Console.print("After loading Jena Model")
        if(modelMetaid == null) throw new java.lang.NullPointerException("modelMetaId is null")
        if(model == null) throw new java.lang.NullPointerException("model is null")
        Console.print("Jena Model content")
        val queryString =
        """
        PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        SELECT ?s WHERE
        { ?s rdf:type sbml:Model .
        """ +  "?s sbml:metaid \"" + modelMetaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

        val l:java.util.LinkedList[SBMLModel]
        = Sparql.exec(model, classOf[SBMLModel], queryString)
        Console.println("Found " + l.size + " SBMLModels with metaid " + modelMetaid)
        if(l.size > 0)
          return l.iterator.next
        else null
    }

    def loadSBMLModel():java.util.Collection[SBMLModel] = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            Console.print("After loading Jena Model")
            var reader = new RDF2Bean(myModel)
            Console.print("After creating a new RDF2Bean")
            val l:java.util.List[SBMLModel] = reader.load(new SBMLModel().getClass )
                .asInstanceOf[java.util.List[SBMLModel]]
            //Console.print("User XML = " + c.toList(0).toXML.toString)

            l
            /*var l:List[User] = Nil
            (for(i <- 0 to lusers.size - 1) yield  lusers(i).asInstanceOf[User])
                .toList*/
        } catch {
            case ex:thewebsemantic.NotFoundException =>
                Console.println("Bean of " + SBMLModel.getClass + "and id is not found")
                ex.printStackTrace()
                null
        }
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
    def trytoCreateSBMLModel(sbmlModel:SBMLModel):String = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            trytoCreateSBMLModel(sbmlModel, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + SBMLModel.getClass + "and " +
                                    "id is not found")
                    ex.printStackTrace()
                    null
                }
            case ex => {
                    ex.printStackTrace()
                    null
                }
        }
    }

    def trytoCreateSBMLModel(sbmlModel:SBMLModel, model:Model):String = {
        if( if( metaidExists(sbmlModel.metaid ) == false ){
                createSBMLModel(sbmlModel, model)
            } else {
                sbmlModel.metaid = generateNewMetaIdFrom(sbmlModel,
                                                         model)
                createSBMLModel(sbmlModel,
                                model)
            } == true)
        {
            sbmlModel.metaid
        } else null
        
    }

    def generateNewMetaIdFrom(sbmlentity:Element):String = {
        val myModel:Model = ManipulatorWrapper.loadModelfromDB
        generateNewMetaIdFrom(sbmlentity, myModel)
    }

    /**
     * Generates a new metaid.
     * It uses the metaid, id or name depending on which is not empty
     * and using this exact order
     */
    def generateNewMetaIdFrom(sbmlentity:Element, model:Model):String = {
        if(sbmlentity.metaid == null || sbmlentity.metaid.trim == ""){
            if(sbmlentity.theId == null || sbmlentity.theId.trim == ""){
                if(sbmlentity.theName == null || sbmlentity.theName.trim == ""){
                    generateNewMetaIdFromString("", model)
                } else {
                    generateNewMetaIdFromString(sbmlentity.theName, model)
                }
            } else {
                generateNewMetaIdFromString(sbmlentity.theId, model)
            }
        } else {
            generateNewMetaIdFromString(sbmlentity.metaid, model)
        }
    }

    def generateNewMetaIdFromString(string:String):String ={
        val myModel:Model = ManipulatorWrapper.loadModelfromDB
        generateNewMetaIdFromString(string, myModel)
    }
    
    def generateNewMetaIdFromString(string:String, model:Model):String ={
        var i = 0
        while( metaidExists(""+ string + i ) == true ){
            i=i+1
        }
        "" + string + i
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
        /*val reasoner:Reasoner = ReasonerRegistry.getOWLReasoner
        //val ontModelSpec:OntModelSpec = null
        //val ont:OntModel = ModelFactory.createOntologyModel(ontModelSpec, model)
        val ont:InfModel = ModelFactory.createInfModel(reasoner, model)*/

        val queryString =
        """
        PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        ASK
        """ + "{ ?s sbml:metaid \"" + metaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> }"

        val query:Query = QueryFactory.create(queryString);
        val qe:QueryExecution = QueryExecutionFactory.create(query, model);
        val result:Boolean = qe.execAsk;

        Console.println("SPARQL query \n" + queryString + "\nIs " + result)

        result
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
        //val ontModelSpec:OntModelSpec = null
        //val ont:OntModel = ModelFactory.createOntologyModel(ontModelSpec, model)
        val ont:InfModel = ModelFactory.createInfModel(reasoner, model)
        val queryString =
        """
        PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        ASK
        { ?s rdf:type sbml:Model .
        """ +  "?s sbml:metaid \"" + metaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

        val query:Query = QueryFactory.create(queryString);
        val qe:QueryExecution = QueryExecutionFactory.create(query, ont);
        val results:Boolean = qe.execAsk;

        Console.println("SPARQL query \n" + queryString + "\nIs " + results)
        
        results
    }

    def modelIdExists(id:String):Boolean = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            modelIdExists(id, myModel)
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

    def modelIdExists(id:String, model:Model):Boolean = {
        val reasoner:Reasoner = ReasonerRegistry.getOWLReasoner
        //val ontModelSpec:OntModelSpec = null
        //val ont:OntModel = ModelFactory.createOntologyModel(ontModelSpec, model)
        val ont:InfModel = ModelFactory.createInfModel(reasoner, model)
        val queryString =
        """
        PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        ASK
        { ?s rdf:type sbml:Model .
        """ +  "?s sbml:id \"" + id + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

        val query:Query = QueryFactory.create(queryString);
        val qe:QueryExecution = QueryExecutionFactory.create(query, ont);
        val results:Boolean = qe.execAsk;
        Console.println("SPARQL query \n" + queryString + "\nIs " + results)
        results
    }

    /**
     * Saves an SBMLModel into the KnowledgeBase
     * @param  true if
     * @return true if
     */
    def updateSBMLModel(sbmlmodel:SBMLModel):Boolean = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            updateSBMLModel(sbmlmodel, myModel)
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
    def updateSBMLModel(sbmlmodel:SBMLModel, model:Model):Boolean = {
        try{
            if(  metaidExists(sbmlmodel.metaid ) ){
                val writer = new Bean2RDF(model)
                writer.save(sbmlmodel)
                true
            } else false
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
     * Deletes an SBMLModel in the KnowledgeBase
     * @param  true if
     * @return true if
     */
    def deleteSBMLModel(sbmlmodel:SBMLModel):Boolean = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            deleteSBMLModel(sbmlmodel, myModel)
        } catch {
            case ex:Exception => {
                    Console.println("Deleting model " + sbmlmodel +
                                    "was not possible")
                    ex.printStackTrace

                    false
                }
        }
    }

    /**
     * Deletes an SBMLModel in the KnowledgeBase
     * @return true if creating the new model was possible and false otherwise
     */
    def deleteSBMLModel(sbmlmodel:SBMLModel, model:Model):Boolean = {
        try{
            if( modelMetaidExists(sbmlmodel.metaid ) ){
                val writer = new Bean2RDF(model)
                writer.delete(sbmlmodel)
                //TODO delete subelements
                true
            } else false
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

    /*def makeComment(comment:Comment)
     def getComments(metaid:String)*/
}
