/*
 * ParametersDAO.scala
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

import pt.cnbc.wikimodels.dataModel.Parameter
import pt.cnbc.wikimodels.dataModel.Comment
import pt.cnbc.wikimodels.dataModel.Element
import pt.cnbc.wikimodels.dataModel.SBMLModel
import pt.cnbc.wikimodels.exceptions.NotImplementedException
import pt.cnbc.wikimodels.ontology.ManipulatorWrapper
import pt.cnbc.wikimodels.ontology.{Namespaces => NS}

class ParametersDAO {
    /**
     * Allows testing procedures. This is not to be used from outside this class
     */
    var  kb:Model = null
    val sbmlModelsDAO = new SBMLModelsDAO()

    def loadParameter(parameterMetaid:String):Parameter = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            loadParameter(parameterMetaid, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException =>
                Console.println("Bean of " + Parameter.getClass + "and " +
                                "id is not found")
                ex.printStackTrace()
                null
            case ex =>
                ex.printStackTrace()
                null
        }
    }

    def loadParameter(parameterMetaid:String, model:Model):Parameter = {
        var ret:Parameter = null

        Console.print("After loading Jena Model")
        var reader = new RDF2Bean(model)
        Console.print("After creating a new RDF2Bean")
        val l
        = reader.load( new Parameter().getClass, parameterMetaid  )
                .asInstanceOf[java.util.Collection[Parameter]]
        Console.println("Found " + l.size + " Parameters with metaid " + parameterMetaid)
        if(l.size > 0)
            l.iterator.next
        else null
    }

    def loadParameter():java.util.Collection[Parameter] = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            Console.print("After loading Jena Model")
            var reader = new RDF2Bean(myModel)
            Console.print("After creating a new RDF2Bean")
            val l:java.util.List[Parameter] = reader.load(new Parameter().getClass )
                .asInstanceOf[java.util.List[Parameter]]
            //Console.print("User XML = " + c.toList(0).toXML.toString)

            l
            /*var l:List[User] = Nil
            (for(i <- 0 to lusers.size - 1) yield  lusers(i).asInstanceOf[User])
                .toList*/
        } catch {
            case ex:thewebsemantic.NotFoundException =>
                Console.println("Bean of " + Parameter.getClass + "and id is not found")
                ex.printStackTrace()
                null
        }
    }



    /**
     * Saves an Parameter into the KnowledgeBase
     * @param  true if
     * @return true if
     */
    def createParameter(parameter:Parameter):Boolean = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            createParameter(parameter, myModel)
        } catch {
            case ex:Exception => {
                    Console.println("Saving model " + parameter +
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
    def createParameter(parameter:Parameter, model:Model):Boolean = {
        try{
            val writer = new Bean2RDF(model)
            writer.save(parameter)
            true
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + Parameter.getClass + "and " +
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

    def trytoCreateParameterInModel(modelMetaid:String,
                                    parameter:Parameter):String = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            trytoCreateParameterInModel(modelMetaid, parameter, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + Parameter.getClass + "and " +
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

    def trytoCreateParameterInModel(modelMetaid:String,
                                    parameter:Parameter,
                                    model:Model):String = {
        if(sbmlModelsDAO.modelMetaidExists(modelMetaid)){
            val parameterMetaid = trytoCreateParameter(parameter, model)

            //Jena API used directly
            val sbmlModelRes = model.createResource(
                         NS.sbml + "Model/" + modelMetaid)
            val parameterRes = model.createResource(
                        NS.sbml + "Model/" + parameterMetaid)

            sbmlModelRes.addProperty(model
                         .getProperty(NS.sbml + "hasPArameter"),
                                     parameterRes)
            parameterMetaid
        } else null
    }

    /**
     * Method that creates a new Model in the KnowledgeBase after checking
     * if everything is valid with the model that is being created
     * This method also issues an available metaid
     *
     */
    def trytoCreateParameter(parameter:Parameter):String = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            trytoCreateParameter(parameter, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + Parameter.getClass + "and " +
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

    def trytoCreateParameter(parameter:Parameter, model:Model):String = {
        if( if( sbmlModelsDAO.metaidExists(parameter.metaid ) == false ){
                createParameter(parameter, model)
            } else {
                parameter.metaid = sbmlModelsDAO.generateNewMetaIdFrom(parameter,
                                                         model)
                createParameter(parameter,
                                model)
            } == true)
        {
            parameter.metaid
        } else null

    }

    def parameterMetaidExists(metaid:String):Boolean = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            parameterMetaidExists(metaid, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + Parameter.getClass + "and " +
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

    def parameterMetaidExists(metaid:String, model:Model):Boolean = {
        val reasoner:Reasoner = ReasonerRegistry.getOWLReasoner
        //val ontModelSpec:OntModelSpec = null
        //val ont:OntModel = ModelFactory.createOntologyModel(ontModelSpec, model)
        val ont:InfModel = ModelFactory.createInfModel(reasoner, model)
        val queryString =
        """
        PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        ASK
        { ?s rdf:type sbml:Parameter .
        """ +  "?s sbml:metaid \"" + metaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

        val query:Query = QueryFactory.create(queryString);
        val qe:QueryExecution = QueryExecutionFactory.create(query, ont);
        val results:Boolean = qe.execAsk;

        Console.println("SPARQL query \n" + queryString + "\nIs " + results)

        results
    }
    
    /**
     * Updates a Parameter into the KnowledgeBase
     * @param  true if
     * @return true if
     */
    def updateParameter(parameter:Parameter):Boolean = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            updateParameter(parameter, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + Parameter.getClass + "and " +
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
     * Updates a Parameter individual in the Knowledgebase
     * @return true if creating the new model was possible and false otherwise
     */
    def updateParameter(parameter:Parameter, model:Model):Boolean = {
        if( sbmlModelsDAO.metaidExists(parameter.metaid ) ){
            val writer = new Bean2RDF(model)
            writer.save(parameter)
            true
        } else false
    }


    /**
     * Deletes an Parameter in the KnowledgeBase
     * @param  true if
     * @return true if
     */
    def deleteParameter(parameter:Parameter):Boolean = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            deleteParameter(parameter, myModel)
        } catch {
            case ex:Exception => {
                    Console.println("Deleting model " + parameter +
                                    "was not possible")
                    ex.printStackTrace

                    false
                }
        }
    }

    /**
     * Deletes an Parameter in the KnowledgeBase
     * @return true if creating the new model was possible and false otherwise
     */
    def deleteParameter(parameter:Parameter, model:Model):Boolean = {
        try{
            if( parameterMetaidExists(parameter.metaid ) ){
                val writer = new Bean2RDF(model)
                writer.delete(parameter)
                //TODO delete subelements
                true
            } else false
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + Parameter.getClass + "and " +
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
