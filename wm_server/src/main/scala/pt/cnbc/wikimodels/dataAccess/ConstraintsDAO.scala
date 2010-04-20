/*
 * ConstraintsDAO.scala
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

import pt.cnbc.wikimodels.dataModel.Constraint
import pt.cnbc.wikimodels.dataModel.Element
import pt.cnbc.wikimodels.dataModel.SBMLModel
import pt.cnbc.wikimodels.exceptions.NotImplementedException
import pt.cnbc.wikimodels.ontology.ManipulatorWrapper
import pt.cnbc.wikimodels.ontology.{Namespaces => NS}
import thewebsemantic.Sparql

class ConstraintsDAO {
  /**
   * Allows testing procedures. This is not to be used from outside this class
   */
  var  kb:Model = null
  val sbmlModelsDAO = new SBMLModelsDAO()

  def loadConstraint(constraintMetaid:String):Constraint = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      loadConstraint(constraintMetaid, myModel)
    } catch {
      case ex:thewebsemantic.NotFoundException =>
        Console.println("Bean of " + Constraint.getClass + "and " +
                        "id is not found")
        ex.printStackTrace()
        null
      case ex =>
        ex.printStackTrace()
        null
    }
  }

  def loadConstraint(constraintMetaid:String, model:Model):Constraint = {
    var ret:Constraint = null

    Console.print("After loading Jena Model")
    Console.print("Jena Model content")
    val queryString =
      """
        PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        SELECT ?s WHERE
        { ?s rdf:type sbml:Constraint .
        """ +  "?s sbml:metaid \"" + constraintMetaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val l:java.util.LinkedList[Constraint]
    = Sparql.exec(model, classOf[Constraint], queryString)
    Console.println("Found " + l.size + " Constraints with metaid " + constraintMetaid)
    if(l.size > 0)
      l.iterator.next
    else null
  }

  def loadConstraintsInModel(modelMetaId:String,
                            model:Model):java.util.Collection[Constraint] = {
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
          ?m sbml:hasConstraint ?s .
          ?s rdf:type sbml:Constraint .
         } """

    val l:java.util.LinkedList[Constraint]
    = Sparql.exec(model, classOf[Constraint], queryString)
    Console.println("Found " + l.size + " Constraints from model " + modelMetaId)
    if(l.size > 0)
      l
    else null
  }

  def loadConstraint():java.util.Collection[Constraint] = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      Console.print("After loading Jena Model")
      var reader = new RDF2Bean(myModel)
      Console.print("After creating a new RDF2Bean")
      val l:java.util.List[Constraint] = reader.load(new Constraint().getClass )
      .asInstanceOf[java.util.List[Constraint]]
      l
    } catch {
      case ex:thewebsemantic.NotFoundException =>
        Console.println("Bean of " + Constraint.getClass + "and id is not found")
        ex.printStackTrace()
        null
    }
  }



  /**
   * Saves an Constraint into the KnowledgeBase
   * @param  true if
   * @return true if
   */
  def createConstraint(constraint:Constraint):Boolean = {
    var ret = false
    var myModel:Model = null
    try{
      myModel = ManipulatorWrapper.loadModelfromDB
      myModel.begin
      ret = createConstraint(constraint, myModel)
      myModel.commit
    } catch {
      case ex:Exception => {
          Console.println("Saving model " + constraint +
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
  def createConstraint(constraint:Constraint, model:Model):Boolean = {
    try{
      val writer = new Bean2RDF(model)
      writer.save(constraint)
      true
    } catch {
      case ex:thewebsemantic.NotFoundException => {
          Console.println("Bean of " + Constraint.getClass + "and " +
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

  def trytoCreateConstraintInModel(modelMetaid:String,
                                   constraint:Constraint):String = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      trytoCreateConstraintInModel(modelMetaid, constraint, myModel)
    } catch {
      case ex:thewebsemantic.NotFoundException => {
          Console.println("Bean of " + Constraint.getClass + "and " +
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

  def trytoCreateConstraintInModel(modelMetaid:String,
                                   constraint:Constraint,
                                   model:Model):String = {
    if(sbmlModelsDAO.modelMetaidExists(modelMetaid)){
      val constraintMetaid = trytoCreateConstraint(constraint, model)
      if(constraintMetaid != null){

        //Jena API used directly
        val sbmlModelRes = model.createResource(
          NS.sbml + "Model/" + modelMetaid)
        val constraintRes = model.createResource(
          NS.sbml + "Constraint/" + constraintMetaid)

        sbmlModelRes.addProperty(model
                                 .getProperty(NS.sbml + "hasPArameter"),
                                 constraintRes)
        constraintMetaid
      } else null
    } else null
  }

  /**
   * Method that creates a new Model in the KnowledgeBase after checking
   * if everything is valid with the model that is being created
   * This method also issues an available metaid
   *
   */
  def trytoCreateConstraint(constraint:Constraint):String = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      trytoCreateConstraint(constraint, myModel)
    } catch {
      case ex:thewebsemantic.NotFoundException => {
          Console.println("Bean of " + Constraint.getClass + "and " +
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

  def trytoCreateConstraint(constraint:Constraint, model:Model):String = {
    if( if( sbmlModelsDAO.metaidExists(constraint.metaid ) == false ){
        createConstraint(constraint, model)
      } else {
        constraint.metaid = sbmlModelsDAO.generateNewMetaIdFrom(constraint,
                                                                model)
        createConstraint(constraint,
                         model)
      } == true)
    {
      constraint.metaid
    } else null

  }

  def constraintMetaidExists(metaid:String):Boolean = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      constraintMetaidExists(metaid, myModel)
    } catch {
      case ex:thewebsemantic.NotFoundException => {
          Console.println("Bean of " + Constraint.getClass + "and " +
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

  def constraintMetaidExists(metaid:String, model:Model):Boolean = {
    val reasoner:Reasoner = ReasonerRegistry.getOWLReasoner
    //val ontModelSpec:OntModelSpec = null
    //val ont:OntModel = ModelFactory.createOntologyModel(ontModelSpec, model)
    val ont:InfModel = ModelFactory.createInfModel(reasoner, model)
    val queryString =
      """
        PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        ASK
        { ?s rdf:type sbml:Constraint .
        """ +  "?s sbml:metaid \"" + metaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val query:Query = QueryFactory.create(queryString);
    val qe:QueryExecution = QueryExecutionFactory.create(query, ont);
    val results:Boolean = qe.execAsk;

    Console.println("SPARQL query \n" + queryString + "\nIs " + results)

    results
  }
    
  /**
   * Updates a Constraint into the KnowledgeBase
   * @param  true if
   * @return true if
   */
  def updateConstraint(constraint:Constraint):Boolean = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      updateConstraint(constraint, myModel)
    } catch {
      case ex:thewebsemantic.NotFoundException => {
          Console.println("Bean of " + Constraint.getClass + "and " +
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
   * Updates a Constraint individual in the Knowledgebase
   * @return true if creating the new model was possible and false otherwise
   */
  def updateConstraint(constraint:Constraint, model:Model):Boolean = {
    if( sbmlModelsDAO.metaidExists(constraint.metaid ) ){
      val writer = new Bean2RDF(model)
      writer.save(constraint)
      true
    } else false
  }


  /**
   * Deletes an Constraint in the KnowledgeBase
   * @param  true if
   * @return true if
   */
  def deleteConstraint(constraint:Constraint):Boolean = {
    try{
      val myModel:Model = ManipulatorWrapper.loadModelfromDB
      deleteConstraint(constraint, myModel)
    } catch {
      case ex:Exception => {
          Console.println("Deleting model " + constraint +
                          "was not possible")
          ex.printStackTrace

          false
        }
    }
  }

  /**
   * Deletes an Constraint in the KnowledgeBase
   * @return true if creating the new model was possible and false otherwise
   */
  def deleteConstraint(constraint:Constraint, model:Model):Boolean = {
    try{
      if( constraintMetaidExists(constraint.metaid ) ){
        val writer = new Bean2RDF(model)
        writer.delete(constraint)
        //TODO delete subelements
        true
      } else false
    } catch {
      case ex:thewebsemantic.NotFoundException => {
          Console.println("Bean of " + Constraint.getClass + "and " +
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
