/*
 * FunctionDefinitionsDAO.scala
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

import pt.cnbc.wikimodels.dataModel.FunctionDefinition
import pt.cnbc.wikimodels.dataModel.Element
import pt.cnbc.wikimodels.dataModel.SBMLModel
import pt.cnbc.wikimodels.exceptions.NotImplementedException
import pt.cnbc.wikimodels.ontology.ManipulatorWrapper
import pt.cnbc.wikimodels.ontology.{Namespaces => NS}
import thewebsemantic.Sparql

class FunctionDefinitionsDAO {
  /**
   * Allows testing procedures. This is not to be used from outside this class
   */
  var  kb:Model = null
  val sbmlModelsDAO = new SBMLModelsDAO()

  def loadFunctionDefinition(functionDefinitionMetaid:String):FunctionDefinition = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      loadFunctionDefinition(functionDefinitionMetaid, myModel)
    } catch {
      case ex:thewebsemantic.NotFoundException =>
        Console.println("Bean of " + FunctionDefinition.getClass + "and " +
                        "id is not found")
        ex.printStackTrace()
        null
      case ex =>
        ex.printStackTrace()
        null
    }
  }

  def loadFunctionDefinition(functionDefinitionMetaid:String, model:Model):FunctionDefinition = {
    var ret:FunctionDefinition = null

    Console.print("After loading Jena Model")
    Console.print("Jena Model content")
    val queryString =
      """
        PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        SELECT ?s WHERE
        { ?s rdf:type sbml:FunctionDefinition .
        """ +  "?s sbml:metaid \"" + functionDefinitionMetaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val l:java.util.LinkedList[FunctionDefinition]
    = Sparql.exec(model, classOf[FunctionDefinition], queryString)
    Console.println("Found " + l.size + " FunctionDefinitions with metaid " + functionDefinitionMetaid)
    if(l.size > 0)
      l.iterator.next
    else null
  }

  def loadFunctionDefinitionsInModel(modelMetaId:String,
                            model:Model):java.util.Collection[FunctionDefinition] = {
    val c =
    Console.print("After loading Jena Model")
    Console.print("Jena Model content")
    val queryString =
      """
        PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        SELECT ?s WHERE
        { ?m rdf:type sbml:Model .
          ?m sbml:metaid """" + modelMetaId + """"^^<http://www.w3.org/2001/XMLSchema#string> .
          ?m sbml:hasFunctionDefinition ?s .
          ?s rdf:type sbml:FunctionDefinition .
         } """

    val l:java.util.LinkedList[FunctionDefinition]
    = Sparql.exec(model, classOf[FunctionDefinition], queryString)
    Console.println("Found " + l.size + " FunctionDefinitions from model " + modelMetaId)
    if(l.size > 0)
      l
    else null
  }

  def loadFunctionDefinition():java.util.Collection[FunctionDefinition] = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      Console.print("After loading Jena Model")
      var reader = new RDF2Bean(myModel)
      Console.print("After creating a new RDF2Bean")
      val l:java.util.List[FunctionDefinition] = reader.load(new FunctionDefinition().getClass )
      .asInstanceOf[java.util.List[FunctionDefinition]]
      l
    } catch {
      case ex:thewebsemantic.NotFoundException =>
        Console.println("Bean of " + FunctionDefinition.getClass + "and id is not found")
        ex.printStackTrace()
        null
    }
  }



  /**
   * Saves an FunctionDefinition into the KnowledgeBase
   * @param  true if
   * @return true if
   */
  def createFunctionDefinition(functionDefinition:FunctionDefinition):Boolean = {
    var ret = false
    var myModel:Model = null
    try{
      myModel = ManipulatorWrapper.loadModelfromDB
      myModel.begin
      ret = createFunctionDefinition(functionDefinition, myModel)
      myModel.commit
    } catch {
      case ex:Exception => {
          Console.println("Saving model " + functionDefinition +
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
  def createFunctionDefinition(functionDefinition:FunctionDefinition, model:Model):Boolean = {
    try{
      val writer = new Bean2RDF(model)
      writer.save(functionDefinition)
      true
    } catch {
      case ex:thewebsemantic.NotFoundException => {
          Console.println("Bean of " + FunctionDefinition.getClass + "and " +
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

  def trytoCreateFunctionDefinitionInModel(modelMetaid:String,
                                           functionDefinition:FunctionDefinition):String = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      trytoCreateFunctionDefinitionInModel(modelMetaid, functionDefinition, myModel)
    } catch {
      case ex:thewebsemantic.NotFoundException => {
          Console.println("Bean of " + FunctionDefinition.getClass + "and " +
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

  def trytoCreateFunctionDefinitionInModel(modelMetaid:String,
                                           functionDefinition:FunctionDefinition,
                                           model:Model):String = {
    if(sbmlModelsDAO.modelMetaidExists(modelMetaid)){
      val functionDefinitionMetaid = trytoCreateFunctionDefinition(functionDefinition, model)
      if(functionDefinitionMetaid != null){


        //Jena API used directly
        val sbmlModelRes = model.createResource(
          NS.sbml + "Model/" + modelMetaid)
        val functionDefinitionRes = model.createResource(
          NS.sbml + "FunctionDefinition/" + functionDefinitionMetaid)

        sbmlModelRes.addProperty(model
                                 .getProperty(NS.sbml + "hasFunctionDefinition"),
                                 functionDefinitionRes)
        functionDefinitionMetaid
      } else null
    } else null
  }

  /**
   * Method that creates a new Model in the KnowledgeBase after checking
   * if everything is valid with the model that is being created
   * This method also issues an available metaid
   *
   */
  def trytoCreateFunctionDefinition(functionDefinition:FunctionDefinition):String = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      trytoCreateFunctionDefinition(functionDefinition, myModel)
    } catch {
      case ex:thewebsemantic.NotFoundException => {
          Console.println("Bean of " + FunctionDefinition.getClass + "and " +
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

  def trytoCreateFunctionDefinition(functionDefinition:FunctionDefinition, model:Model):String = {
    if( if( sbmlModelsDAO.metaidExists(functionDefinition.metaid ) == false ){
        createFunctionDefinition(functionDefinition, model)
      } else {
        functionDefinition.metaid = sbmlModelsDAO.generateNewMetaIdFrom(functionDefinition,
                                                                        model)
        createFunctionDefinition(functionDefinition,
                                 model)
      } == true)
    {
      functionDefinition.metaid
    } else null

  }

  def functionDefinitionMetaidExists(metaid:String):Boolean = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      functionDefinitionMetaidExists(metaid, myModel)
    } catch {
      case ex:thewebsemantic.NotFoundException => {
          Console.println("Bean of " + FunctionDefinition.getClass + "and " +
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

  def functionDefinitionMetaidExists(metaid:String, model:Model):Boolean = {
    val reasoner:Reasoner = ReasonerRegistry.getOWLReasoner
    val ont:InfModel = ModelFactory.createInfModel(reasoner, model)
    val queryString =
      """
        PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        ASK
        { ?s rdf:type sbml:FunctionDefinition .
        """ +  "?s sbml:metaid \"" + metaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val query:Query = QueryFactory.create(queryString);
    val qe:QueryExecution = QueryExecutionFactory.create(query, ont);
    val results:Boolean = qe.execAsk;

    Console.println("SPARQL query \n" + queryString + "\nIs " + results)

    results
  }
    
  /**
   * Updates a FunctionDefinition into the KnowledgeBase
   * @param  true if
   * @return true if
   */
  def updateFunctionDefinition(functionDefinition:FunctionDefinition):Boolean = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      updateFunctionDefinition(functionDefinition, myModel)
    } catch {
      case ex:thewebsemantic.NotFoundException => {
          Console.println("Bean of " + FunctionDefinition.getClass + "and " +
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
   * Updates a FunctionDefinition individual in the Knowledgebase
   * @return true if creating the new model was possible and false otherwise
   */
  def updateFunctionDefinition(functionDefinition:FunctionDefinition, model:Model):Boolean = {
    if( sbmlModelsDAO.metaidExists(functionDefinition.metaid ) ){
      val writer = new Bean2RDF(model)
      writer.save(functionDefinition)
      true
    } else false
  }


  /**
   * Deletes an FunctionDefinition in the KnowledgeBase
   * @param  true if
   * @return true if
   */
  def deleteFunctionDefinition(functionDefinition:FunctionDefinition):Boolean = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      deleteFunctionDefinition(functionDefinition, myModel)
    } catch {
      case ex:Exception => {
          Console.println("Deleting model " + functionDefinition +
                          "was not possible")
          ex.printStackTrace

          false
        }
    }
  }

  /**
   * Deletes an FunctionDefinition in the KnowledgeBase
   * @return true if creating the new model was possible and false otherwise
   */
  def deleteFunctionDefinition(functionDefinition:FunctionDefinition, model:Model):Boolean = {
    try{
      if( functionDefinitionMetaidExists(functionDefinition.metaid ) ){
        val writer = new Bean2RDF(model)
        writer.delete(functionDefinition)
        //TODO delete subelements
        true
      } else false
    } catch {
      case ex:thewebsemantic.NotFoundException => {
          Console.println("Bean of " + FunctionDefinition.getClass + "and " +
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
