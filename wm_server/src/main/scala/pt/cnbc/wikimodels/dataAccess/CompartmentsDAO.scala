/*
 * CompartmentsDAO.scala
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
import thewebsemantic.Sparql

import scala.Collection
import scala.collection.JavaConversions._

import pt.cnbc.wikimodels.dataModel.Compartment
import pt.cnbc.wikimodels.dataModel.Element
import pt.cnbc.wikimodels.dataModel.SBMLModel
import pt.cnbc.wikimodels.exceptions.NotImplementedException
import pt.cnbc.wikimodels.ontology.ManipulatorWrapper
import pt.cnbc.wikimodels.ontology.{Namespaces => NS}

class CompartmentsDAO {
  /**
   * Allows testing procedures. This is not to be used from outside this class
   */
  var  kb:Model = null
  val sbmlModelsDAO = new SBMLModelsDAO()

  def loadCompartment(compartmentMetaid:String):Compartment = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      loadCompartment(compartmentMetaid, myModel)
    } catch {
      case ex:thewebsemantic.NotFoundException =>
        Console.println("Bean of " + Compartment.getClass + "and " +
                        "id is not found")
        ex.printStackTrace()
        null
      case ex =>
        ex.printStackTrace()
        null
    }
  }

  def loadCompartment(compartmentMetaid:String, model:Model):Compartment = {
    var ret:Compartment = null

    Console.print("After loading Jena Model")
    Console.print("Jena Model content")
    val queryString =
      """
        PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        SELECT ?s WHERE
        { ?s rdf:type sbml:Compartment .
        """ +  "?s sbml:metaid \"" + compartmentMetaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val l:java.util.LinkedList[Compartment]
    = Sparql.exec(model, classOf[Compartment], queryString)
    Console.println("Found " + l.size + " Compartments with metaid " + compartmentMetaid)
    if(l.size > 0)
      l.iterator.next
    else null
  }

  def loadCompartmentsInModel(modelMetaId:String,
                            model:Model):java.util.Collection[Compartment] = {
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
          ?m sbml:hasCompartment ?s .
          ?s rdf:type sbml:Compartment .
         } """

    val l:java.util.LinkedList[Compartment]
    = Sparql.exec(model, classOf[Compartment], queryString)
    Console.println("Found " + l.size + " Compartments from model " + modelMetaId)
    if(l.size > 0)
      l
    else null
  }

  def loadCompartment():java.util.Collection[Compartment] = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      Console.print("After loading Jena Model")
      var reader = new RDF2Bean(myModel)
      Console.print("After creating a new RDF2Bean")
      val l:java.util.List[Compartment] = reader.load(new Compartment().getClass )
      .asInstanceOf[java.util.List[Compartment]]
      //Console.print("User XML = " + c.toList(0).toXML.toString)

      l
      /*var l:List[User] = Nil
       (for(i <- 0 to lusers.size - 1) yield  lusers(i).asInstanceOf[User])
       .toList*/
    } catch {
      case ex:thewebsemantic.NotFoundException =>
        Console.println("Bean of " + Compartment.getClass + "and id is not found")
        ex.printStackTrace()
        null
    }
  }





  /**
   * Saves an Compartment into the KnowledgeBase
   * @param  true if
   * @return true if
   */
  def createCompartment(compartment:Compartment):Boolean = {
    var ret = false
    var myModel:Model = null
    try{
      myModel = ManipulatorWrapper.loadModelfromDB
      myModel.begin
      ret = createCompartment(compartment, myModel)
      myModel.commit
    } catch {
      case ex:Exception => {
          Console.println("Saving model " + compartment +
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
  def createCompartment(compartment:Compartment, model:Model):Boolean = {
    try{
      val writer = new Bean2RDF(model)
      writer.save(compartment)
      true
    } catch {
      case ex:thewebsemantic.NotFoundException => {
          Console.println("Bean of " + Compartment.getClass + "and " +
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

  def trytoCreateCompartmentInModel(modelMetaid:String,
                                    compartment:Compartment):String = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      trytoCreateCompartmentInModel(modelMetaid, compartment, myModel)
    } catch {
      case ex:thewebsemantic.NotFoundException => {
          Console.println("Bean of " + Compartment.getClass + "and " +
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

  def trytoCreateCompartmentInModel(modelMetaid:String,
                                    compartment:Compartment,
                                    model:Model):String = {
    if(sbmlModelsDAO.modelMetaidExists(modelMetaid)){
      val compartmentMetaid = trytoCreateCompartment(compartment, model)
      if(compartmentMetaid != null){
        //Jena API used directly
        val sbmlModelRes = model.createResource(
          NS.sbml + "Model/" + modelMetaid)
        val compartmentRes = model.createResource(
          NS.sbml + "Compartment/" + compartmentMetaid)

        sbmlModelRes.addProperty(model
                                 .getProperty(NS.sbml + "hasCompartment"),
                                 compartmentRes)
        compartmentMetaid
      } else null
    } else null
  }

  /**
   * Method that creates a new Model in the KnowledgeBase after checking
   * if everything is valid with the model that is being created
   * This method also issues an available metaid
   *
   */
  def trytoCreateCompartment(compartment:Compartment):String = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      trytoCreateCompartment(compartment, myModel)
    } catch {
      case ex:thewebsemantic.NotFoundException => {
          Console.println("Bean of " + Compartment.getClass + "and " +
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

  def trytoCreateCompartment(compartment:Compartment, model:Model):String = {
    if( if( sbmlModelsDAO.metaidExists(compartment.metaid ) == false ){
        createCompartment(compartment, model)
      } else {
        compartment.metaid = sbmlModelsDAO.generateNewMetaIdFrom(compartment,
                                                                 model)
        createCompartment(compartment,
                          model)
      } == true)
    {
      compartment.metaid
    } else null

  }

  def compartmentMetaidExists(metaid:String):Boolean = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      compartmentMetaidExists(metaid, myModel)
    } catch {
      case ex:thewebsemantic.NotFoundException => {
          Console.println("Bean of " + Compartment.getClass + "and " +
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

  def compartmentMetaidExists(metaid:String, model:Model):Boolean = {
    val reasoner:Reasoner = ReasonerRegistry.getOWLReasoner
    //val ontModelSpec:OntModelSpec = null
    //val ont:OntModel = ModelFactory.createOntologyModel(ontModelSpec, model)
    val ont:InfModel = ModelFactory.createInfModel(reasoner, model)
    val queryString =
      """
        PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        ASK
        { ?s rdf:type sbml:Compartment .
        """ +  "?s sbml:metaid \"" + metaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val query:Query = QueryFactory.create(queryString);
    val qe:QueryExecution = QueryExecutionFactory.create(query, ont);
    val results:Boolean = qe.execAsk;

    Console.println("SPARQL query \n" + queryString + "\nIs " + results)

    results
  }
    
  /**
   * Updates a Compartment into the KnowledgeBase
   * @param  true if
   * @return true if
   */
  def updateCompartment(compartment:Compartment):Boolean = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      updateCompartment(compartment, myModel)
    } catch {
      case ex:thewebsemantic.NotFoundException => {
          Console.println("Bean of " + Compartment.getClass + "and " +
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
   * Updates a Compartment individual in the Knowledgebase
   * @return true if creating the new model was possible and false otherwise
   */
  def updateCompartment(compartment:Compartment, model:Model):Boolean = {
    if( sbmlModelsDAO.metaidExists(compartment.metaid ) ){
      val writer = new Bean2RDF(model)
      writer.save(compartment)
      true
    } else false
  }


  /**
   * Deletes an Compartment in the KnowledgeBase
   * @param  true if
   * @return true if
   */
  def deleteCompartment(compartment:Compartment):Boolean = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      deleteCompartment(compartment, myModel)
    } catch {
      case ex:Exception => {
          Console.println("Deleting model " + compartment +
                          "was not possible")
          ex.printStackTrace

          false
        }
    }
  }

  /**
   * Deletes an Compartment in the KnowledgeBase
   * @return true if creating the new model was possible and false otherwise
   */
  def deleteCompartment(compartment:Compartment, model:Model):Boolean = {
    try{
      if( compartmentMetaidExists(compartment.metaid ) ){
        val writer = new Bean2RDF(model)
        writer.delete(compartment)
        //TODO delete subelements
        true
      } else false
    } catch {
      case ex:thewebsemantic.NotFoundException => {
          Console.println("Bean of " + Compartment.getClass + "and " +
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
