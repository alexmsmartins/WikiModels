/*
 * ReactionsDAO.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataAccess

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
import com.hp.hpl.jena.rdf.model.InfModel
import com.hp.hpl.jena.rdf.model.Model
import com.hp.hpl.jena.rdf.model.ModelFactory
import com.hp.hpl.jena.reasoner.Reasoner
import com.hp.hpl.jena.reasoner.ReasonerRegistry

import thewebsemantic.Bean2RDF
import thewebsemantic.RDF2Bean

import scala.Collection

import pt.cnbc.wikimodels.dataModel.Reaction
import pt.cnbc.wikimodels.dataModel.Element
import pt.cnbc.wikimodels.dataModel.SBMLModel
import pt.cnbc.wikimodels.exceptions.NotImplementedException
import pt.cnbc.wikimodels.ontology.ManipulatorWrapper
import pt.cnbc.wikimodels.ontology.{Namespaces => NS}
import thewebsemantic.Sparql

class ReactionsDAO {
    /**
     * Allows testing procedures. This is not to be used from outside this class
     */
    var  kb:Model = null
    val sbmlModelsDAO = new SBMLModelsDAO()

    def loadReaction(reactionMetaid:String):Reaction = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            loadReaction(reactionMetaid, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException =>
                Console.println("Bean of " + Reaction.getClass + "and " +
                                "id is not found")
                ex.printStackTrace()
                null
            case ex =>
                ex.printStackTrace()
                null
        }
    }

    def loadReaction(reactionMetaid:String, model:Model):Reaction = {
        var ret:Reaction = null

        Console.print("After loading Jena Model")
        Console.print("Jena Model content")
        val queryString =
        """
        PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        SELECT ?s WHERE
        { ?s rdf:type sbml:Reaction .
        """ +  "?s sbml:metaid \"" + reactionMetaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

        val l:java.util.LinkedList[Reaction]
        = Sparql.exec(model, classOf[Reaction], queryString)
        Console.println("Found " + l.size + " Reactions with metaid " + reactionMetaid)
        if(l.size > 0)
            l.iterator.next
        else null
    }

    def loadReaction():java.util.Collection[Reaction] = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            Console.print("After loading Jena Model")
            var reader = new RDF2Bean(myModel)
            Console.print("After creating a new RDF2Bean")
            val l:java.util.List[Reaction] = reader.load(new Reaction().getClass )
                .asInstanceOf[java.util.List[Reaction]]
            l
        } catch {
            case ex:thewebsemantic.NotFoundException =>
                Console.println("Bean of " + Reaction.getClass + "and id is not found")
                ex.printStackTrace()
                null
        }
    }



    /**
     * Saves an Reaction into the KnowledgeBase
     * @param  reaction to be created
     * @return true if
     */
    def createReaction(reaction:Reaction):Boolean = {
        var ret = false
        var myModel:Model = null
        try{
            myModel = ManipulatorWrapper.loadModelfromDB
            myModel.begin
            ret = createReaction(reaction, myModel)
            myModel.commit
        } catch {
            case ex:Exception => {
                    Console.println("Saving model " + reaction +
                                    "was not possible")
                    ex.printStackTrace

                    false
                }
        }
        ret
    }

    /**
     * Creates a new SBML model individual in the Knowledgebase
     * @return true if creating the new model was possible and false otherwise
     */
    def createReaction(reaction:Reaction, model:Model):Boolean = {
        try{
            val writer = new Bean2RDF(model)
            writer.save(reaction)
            true
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + Reaction.getClass + "and " +
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

    def trytoCreateReactionInModel(modelMetaid:String,
                                    reaction:Reaction):String = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            trytoCreateReactionInModel(modelMetaid, reaction, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + Reaction.getClass + "and " +
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

    def trytoCreateReactionInModel(modelMetaid:String,
                                    reaction:Reaction,
                                    model:Model):String = {
        if(sbmlModelsDAO.modelMetaidExists(modelMetaid)){
          val reactionMetaid = trytoCreateReaction(reaction, model)
          if(reactionMetaid != null){

            //Jena API used directly
            val sbmlModelRes = model.createResource(
                         NS.sbml + "Model/" + modelMetaid)
            val reactionRes = model.createResource(
                        NS.sbml + "Reaction/" + reactionMetaid)

            sbmlModelRes.addProperty(model
                         .getProperty(NS.sbml + "hasPArameter"),
                                     reactionRes)
            reactionMetaid
          } else null
        } else null
    }

    /**
     * Method that creates a new Model in the KnowledgeBase after checking
     * if everything is valid with the model that is being created
     * This method also issues an available metaid
     *
     */
    def trytoCreateReaction(reaction:Reaction):String = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            trytoCreateReaction(reaction, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + Reaction.getClass + "and " +
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

    def trytoCreateReaction(reaction:Reaction, model:Model):String = {
        if( if( sbmlModelsDAO.metaidExists(reaction.metaid ) == false ){
                createReaction(reaction, model)
            } else {
                reaction.metaid = sbmlModelsDAO.generateNewMetaIdFrom(reaction,
                                                         model)
                createReaction(reaction,
                                model)
            } == true)
        {
            reaction.metaid
        } else null

    }

    def reactionMetaidExists(metaid:String):Boolean = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            reactionMetaidExists(metaid, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + Reaction.getClass + "and " +
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

    def reactionMetaidExists(metaid:String, model:Model):Boolean = {
        val reasoner:Reasoner = ReasonerRegistry.getOWLReasoner
        //val ontModelSpec:OntModelSpec = null
        //val ont:OntModel = ModelFactory.createOntologyModel(ontModelSpec, model)
        val ont:InfModel = ModelFactory.createInfModel(reasoner, model)
        val queryString =
        """
        PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        ASK
        { ?s rdf:type sbml:Reaction .
        """ +  "?s sbml:metaid \"" + metaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

        val query:Query = QueryFactory.create(queryString);
        val qe:QueryExecution = QueryExecutionFactory.create(query, ont);
        val results:Boolean = qe.execAsk;

        Console.println("SPARQL query \n" + queryString + "\nIs " + results)

        results
    }
    
    /**
     * Updates a Reaction into the KnowledgeBase
     * @param  true if
     * @return true if
     */
    def updateReaction(reaction:Reaction):Boolean = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            updateReaction(reaction, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + Reaction.getClass + "and " +
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
     * Updates a Reaction individual in the Knowledgebase
     * @return true if creating the new model was possible and false otherwise
     */
    def updateReaction(reaction:Reaction, model:Model):Boolean = {
        if( sbmlModelsDAO.metaidExists(reaction.metaid ) ){
            val writer = new Bean2RDF(model)
            writer.save(reaction)
            true
        } else false
    }


    /**
     * Deletes an Reaction in the KnowledgeBase
     * @param  true if
     * @return true if
     */
    def deleteReaction(reaction:Reaction):Boolean = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            deleteReaction(reaction, myModel)
        } catch {
            case ex:Exception => {
                    Console.println("Deleting model " + reaction +
                                    "was not possible")
                    ex.printStackTrace

                    false
                }
        }
    }

    /**
     * Deletes an Reaction in the KnowledgeBase
     * @return true if creating the new model was possible and false otherwise
     */
    def deleteReaction(reaction:Reaction, model:Model):Boolean = {
        try{
            if( reactionMetaidExists(reaction.metaid ) ){
                val writer = new Bean2RDF(model)
                writer.delete(reaction)
                //TODO delete subelements
                true
            } else false
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + Reaction.getClass + "and " +
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
}
