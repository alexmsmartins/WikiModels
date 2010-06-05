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
import thewebsemantic.Sparql

class ParametersDAO {
  /**
   * Allows testing procedures. This is not to be used from outside this class
   */
  var kb: Model = null
  val sbmlModelsDAO = new SBMLModelsDAO()

  def loadParameter(parameterMetaid: String): Parameter = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      loadParameter(parameterMetaid, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException =>
        Console.println("Bean of " + Parameter.getClass + "and " +
                "id is not found")
        ex.printStackTrace()
        null
      case ex =>
        ex.printStackTrace()
        null
    }
  }

  def loadParameter(parameterMetaid: String, model: Model): Parameter = {
    var ret: Parameter = null

    Console.print("After loading Jena Model")
    Console.print("Jena Model content")
    val queryString =
    """
    PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    SELECT ?s WHERE
    { ?s rdf:type sbml:Parameter .
    """ + "?s sbml:metaid \"" + parameterMetaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val l: java.util.LinkedList[Parameter]
    = Sparql.exec(model, classOf[Parameter], queryString)
    Console.println("Found " + l.size + " Parameters with metaid " + parameterMetaid)
    if (l.size > 0)
      l.iterator.next
    else null
  }

  def loadParametersInModel(modelMetaId: String,
                            model: Model): java.util.Collection[Parameter] = {
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
          ?m sbml:hasParameter ?s .
          ?s rdf:type sbml:Parameter .
         } """

    val l: java.util.LinkedList[Parameter]
    = Sparql.exec(model, classOf[Parameter], queryString)
    Console.println("Found " + l.size + " Parameters from model " + modelMetaId)
    if (l.size > 0)
      l
    else null
  }

  def loadParameter(): java.util.Collection[Parameter] = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      Console.print("After loading Jena Model")
      var reader = new RDF2Bean(myModel)
      Console.print("After creating a new RDF2Bean")
      val l: java.util.List[Parameter] = reader.load(new Parameter().getClass)
              .asInstanceOf[java.util.List[Parameter]]
      l
    } catch {
      case ex: thewebsemantic.NotFoundException =>
        Console.println("Bean of " + Parameter.getClass + "and id is not found")
        ex.printStackTrace()
        null
    }
  }

  /**
   * Saves an Parameter into the KnowledgeBase
   * @param true if
   * @return true if
   */
  def createParameter(parameter: Parameter): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      createParameter(parameter, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + Parameter.getClass + "and " +
                "id is not found")
        ex.printStackTrace()
        false
      }
      case ex => {
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
  def createParameter(parameter: Parameter, model: Model): Boolean = {
    val writer = new Bean2RDF(model)
    writer.save(parameter)
    true
  }

  def tryToCreateParameterInModel(modelMetaid: String,
                                  parameter: Parameter): String = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      tryToCreateParameterInModel(modelMetaid, parameter, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
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

  def tryToCreateParameterInModel(modelMetaid: String,
                                  parameter: Parameter,
                                  model: Model): String = {
    if (sbmlModelsDAO.modelMetaIdExists(modelMetaid, model)) {
      val parameterMetaid = tryToCreateParameter(parameter, model)
      if (parameterMetaid != null) {
        //Jena API used directly
        val sbmlModelRes = model.createResource(
          NS.sbml + "Model/" + modelMetaid)
        val parameterRes = model.createResource(
          NS.sbml + "Parameter/" + parameterMetaid)

        sbmlModelRes.addProperty(model
                .getProperty(NS.sbml + "hasParameter"),
          parameterRes)
        parameterMetaid
      } else null
    } else null
  }

  def tryToCreateParameterInKineticLaw(kineticLawMetaId: String,
                                  parameter: Parameter): String = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      tryToCreateParameterInKineticLaw(kineticLawMetaId, parameter, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
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

  def tryToCreateParameterInKineticLaw(kineticLawMetaId: String,
                                  parameter: Parameter,
                                  model: Model): String = {
    if(new ReactionsDAO().reactionMetaidExists(kineticLawMetaId, model)) {
      val parameterMetaid = tryToCreateParameter(parameter, model)
      if (parameterMetaid != null) {
        //Jena API used directly
        val kineticLawRes = model.createResource(
          NS.sbml + "Reaction/" + kineticLawMetaId)
        val parameterRes = model.createResource(
          NS.sbml + "Parameter/" + parameterMetaid)

        kineticLawRes.addProperty(model
                .getProperty(NS.sbml + "hasParameter"),
          parameterRes)
        parameterMetaid
      } else null
    } else null
  }

  /**
   * Method that creates a new Model in the KnowledgeBase after checking
   * if everything is valid with the model that is being created
   * This method also issues an available metaid
   *
   */
  def tryToCreateParameter(parameter: Parameter): String = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      tryToCreateParameter(parameter, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
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

  def tryToCreateParameter(parameter: Parameter, model: Model): String = {
    if (if (parameter.metaid != null &&
            parameter.metaid.trim != "" &&
            !sbmlModelsDAO.metaIdExists(parameter.metaid, model)) {
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

  def parameterMetaidExists(metaid: String): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      parameterMetaidExists(metaid, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
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

  def parameterMetaidExists(metaid: String, model: Model): Boolean = {
    //val reasoner:Reasoner = ReasonerRegistry.getOWLReasoner
    //val ontModelSpec:OntModelSpec = null
    //val ont:OntModel = ModelFactory.createOntologyModel(ontModelSpec, model)
    //val ont:InfModel = ModelFactory.createInfModel(reasoner, model)
    val queryString =
    """
    PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    ASK
    { ?s rdf:type sbml:Parameter .
    """ + "?s sbml:metaid \"" + metaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val query: Query = QueryFactory.create(queryString);
    val qe: QueryExecution = QueryExecutionFactory.create(query, model);
    val results: Boolean = qe.execAsk;

    Console.println("SPARQL query \n" + queryString + "\nIs " + results)

    results
  }

  /**
   * Updates a Parameter into the KnowledgeBase
   * @param true if
   * @return true if
   */
  def updateParameter(parameter: Parameter): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      updateParameter(parameter, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
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
  def updateParameter(parameter: Parameter, model: Model): Boolean = {
    if (sbmlModelsDAO.metaIdExists(parameter.metaid, model)) {
      val writer = new Bean2RDF(model)
      writer.save(parameter)
      true
    } else false
  }


  /**
   * Deletes an Parameter in the KnowledgeBase
   * @param true if
   * @return true if
   */
  def deleteParameter(parameter: Parameter): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      deleteParameter(parameter, myModel)
    } catch {
      case ex: Exception => {
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
  def deleteParameter(parameter: Parameter, model: Model): Boolean = {
    try {
      if (parameterMetaidExists(parameter.metaid)) {
        val writer = new Bean2RDF(model)
        writer.delete(parameter)
        //TODO delete subelements
        true
      } else false
    } catch {
      case ex: thewebsemantic.NotFoundException => {
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
