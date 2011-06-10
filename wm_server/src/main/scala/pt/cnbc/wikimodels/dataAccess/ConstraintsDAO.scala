/*
 * ConstraintsDAO.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataAccess

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model

import thewebsemantic.Bean2RDF
import thewebsemantic.RDF2Bean

import scala.Collection

import pt.cnbc.wikimodels.dataModel.Constraint
import pt.cnbc.wikimodels.ontology.ManipulatorWrapper
import pt.cnbc.wikimodels.ontology.{Namespaces => NS}
import thewebsemantic.Sparql
import org.slf4j.LoggerFactory

class ConstraintsDAO {
  val logger = LoggerFactory.getLogger(getClass)

  /**
   * Allows testing procedures. This is not to be used from outside this class
   */
  var kb: Model = null
  val sbmlModelsDAO = new SBMLModelsDAO()

  def loadConstraint(constraintMetaid: String): Constraint = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      loadConstraint(constraintMetaid, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException =>
        logger.debug("Bean of " + Constraint.getClass + "and " +
                "id is not found")
        ex.printStackTrace()
        null
      case ex =>
        ex.printStackTrace()
        null
    }
  }

  def loadConstraint(constraintMetaid: String, model: Model): Constraint = {
    var ret: Constraint = null

    logger.debug("After loading Jena Model")
    logger.debug("Jena Model content")
    val queryString =
    """
    PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    SELECT ?s WHERE
    { ?s rdf:type sbml:Constraint .
    """ + "?s sbml:metaid \"" + constraintMetaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val l: java.util.LinkedList[Constraint]
    = Sparql.exec(model, classOf[Constraint], queryString)
    logger.debug("Found " + l.size + " Constraints with metaid " + constraintMetaid)
    if (l.size > 0)
      l.iterator.next
    else null
  }

  def loadConstraintsInModel(modelMetaId: String,
                             model: Model): java.util.Collection[Constraint] = {
    val c =
    logger.debug("After loading Jena Model")
    logger.debug("Jena Model content")
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

    val l: java.util.LinkedList[Constraint]
    = Sparql.exec(model, classOf[Constraint], queryString)
    logger.debug("Found " + l.size + " Constraints from model " + modelMetaId)
    if (l.size > 0)
      l
    else null
  }

  def loadConstraint(): java.util.Collection[Constraint] = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      logger.debug("After loading Jena Model")
      var reader = new RDF2Bean(myModel)
      logger.debug("After creating a new RDF2Bean")
      val l: java.util.List[Constraint] = reader.load(new Constraint().getClass)
              .asInstanceOf[java.util.List[Constraint]]
      l
    } catch {
      case ex: thewebsemantic.NotFoundException =>
        logger.debug("Bean of " + Constraint.getClass + "and id is not found")
        ex.printStackTrace()
        null
    }
  }


  /**
   * Saves an Constraint into the KnowledgeBase
   * @param true if
   * @return true if
   */
  def createConstraint(constraint: Constraint): Boolean = {
    var ret = false
    var myModel: Model = null
    try {
      myModel = ManipulatorWrapper.loadModelfromDB
      myModel.begin
      ret = createConstraint(constraint, myModel)
      myModel.commit
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        logger.debug("Bean of " + Constraint.getClass + "and " +
                "id is not found")
        ex.printStackTrace()
        false
      }
      case ex => {
        logger.debug("Saving model " + constraint +
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
  def createConstraint(constraint: Constraint, model: Model): Boolean = {
    val writer = new Bean2RDF(model)
    writer.save(constraint)
    true
  }

  def tryToCreateConstraintInModel(modelMetaid: String,
                                   constraint: Constraint): String = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      tryToCreateConstraintInModel(modelMetaid, constraint, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        logger.debug("Bean of " + Constraint.getClass + "and " +
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

  def tryToCreateConstraintInModel(modelMetaid: String,
                                   constraint: Constraint,
                                   model: Model): String = {
    if (sbmlModelsDAO.modelMetaIdExists(modelMetaid, model)) {
      val constraintMetaid = tryToCreateConstraint(constraint, model)
      if (constraintMetaid != null) {

        //Jena API used directly
        val sbmlModelRes = model.createResource(
          NS.sbml + "Model/" + modelMetaid)
        val constraintRes = model.createResource(
          NS.sbml + "Constraint/" + constraintMetaid)

        sbmlModelRes.addProperty(model
                .getProperty(NS.sbml + "hasConstraint"),
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
  def tryToCreateConstraint(constraint: Constraint): String = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      tryToCreateConstraint(constraint, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        logger.debug("Bean of " + Constraint.getClass + "and " +
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

  def tryToCreateConstraint(constraint: Constraint, model: Model): String = {
    if (if (constraint.metaid != null &&
            constraint.metaid.trim != "" &&
            !sbmlModelsDAO.metaIdExists(constraint.metaid, model)) {
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

  def constraintMetaidExists(metaid: String): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      constraintMetaidExists(metaid, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        logger.debug("Bean of " + Constraint.getClass + "and " +
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

  def constraintMetaidExists(metaid: String, model: Model): Boolean = {
    //val reasoner:Reasoner = ReasonerRegistry.getOWLReasoner
    //val ontModelSpec:OntModelSpec = null
    //val ont:OntModel = ModelFactory.createOntologyModel(ontModelSpec, model)
    //val ont:InfModel = ModelFactory.createInfModel(reasoner, model)
    val queryString =
    """
    PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    ASK
    { ?s rdf:type sbml:Constraint .
    """ + "?s sbml:metaid \"" + metaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val query: Query = QueryFactory.create(queryString);
    val qe: QueryExecution = QueryExecutionFactory.create(query, model);
    val results: Boolean = qe.execAsk;

    logger.debug("SPARQL query \n" + queryString + "\nIs " + results)

    results
  }

  /**
   * Updates a Constraint into the KnowledgeBase
   * @param true if
   * @return true if
   */
  def updateConstraint(constraint: Constraint): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      updateConstraint(constraint, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        logger.debug("Bean of " + Constraint.getClass + "and " +
                "id is not found")
        ex.printStackTrace()
        false
      }
      case ex => {
        logger.debug(ex.toString)
        ex.printStackTrace()
        false
      }
    }
  }

  /**
   * Updates a Constraint individual in the Knowledgebase
   * @return true if creating the new model was possible and false otherwise
   */
  def updateConstraint(constraint: Constraint, model: Model): Boolean = {
    if (sbmlModelsDAO.metaIdExists(constraint.metaid, model)) {
      val writer = new Bean2RDF(model)
      writer.save(constraint)
      true
    } else false
  }


  /**
   * Deletes an Constraint in the KnowledgeBase
   * @param true if
   * @return true if
   */
  def deleteConstraint(constraint: Constraint): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      deleteConstraint(constraint, myModel)
    } catch {
      case ex: Exception => {
        logger.debug("Deleting model " + constraint +
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
  def deleteConstraint(constraint: Constraint, model: Model): Boolean = {
    try {
      if (constraintMetaidExists(constraint.metaid)) {
        val writer = new Bean2RDF(model)
        writer.delete(constraint)
        //TODO delete subelements
        true
      } else false
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        logger.debug("Bean of " + Constraint.getClass + "and " +
                "id is not found")
        ex.printStackTrace()
        false
      }
      case ex => {
        logger.debug(ex.toString)
        ex.printStackTrace()
        false
      }
    }
  }
}
