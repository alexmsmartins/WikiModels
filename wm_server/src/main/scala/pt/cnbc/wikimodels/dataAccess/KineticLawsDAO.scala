/*
 * KineticLawsDAO.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataAccess

import com.hp.hpl.jena.query.Query
import pt.cnbc.wikimodels.exceptions.BadFormatException
import pt.cnbc.wikimodels.util.SBMLHandler;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model

import scala.collection.JavaConversions._

import thewebsemantic.Bean2RDF
import thewebsemantic.RDF2Bean
import thewebsemantic.Sparql

import pt.cnbc.wikimodels.ontology.ManipulatorWrapper
import pt.cnbc.wikimodels.dataModel.KineticLaw
import pt.cnbc.wikimodels.ontology.{Namespaces => NS}
import org.slf4j.LoggerFactory

class KineticLawsDAO {
  val logger = LoggerFactory.getLogger(getClass)

  /**
   * Allows testing procedures. This is not to be used from outside this class
   */
  var kb: Model = null
  val sbmlModelsDAO = new SBMLModelsDAO()
  val reactionsDAO = new ReactionsDAO()

  def loadKineticLaw(kineticLawMetaid: String): KineticLaw = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      loadKineticLaw(kineticLawMetaid, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException =>
        logger.debug("Bean of " + KineticLaw.getClass + "and " +
                "id is not found")
        ex.printStackTrace()
        null
      case ex =>
        ex.printStackTrace()
        null
    }
  }

  def loadKineticLaw(kineticLawMetaid: String, model: Model): KineticLaw = {
    var ret: KineticLaw = null

    logger.debug("After loading Jena Model")
    logger.debug("Jena Model content")
    val queryString =
    """
    PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    SELECT ?s WHERE
    { ?s rdf:type sbml:KineticLaw .
    """ + "?s sbml:metaid \"" + kineticLawMetaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val l: java.util.LinkedList[KineticLaw]
    = Sparql.exec(model, classOf[KineticLaw], queryString)
    logger.debug("Found " + l.size + " KineticLaws with metaid " + kineticLawMetaid)
    if (l.size > 0)
      l.iterator.next
    else null
  }

  def loadKineticLawInReaction(reactionMetaId: String,
                               model: Model): KineticLaw = {
    logger.debug("After loading Jena Model")
    logger.debug("Jena Model content")
    val queryString =
    """
PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT ?s WHERE
{ ?m rdf:type sbml:Reaction .
  ?m sbml:metaid """" + reactionMetaId + """"^^<http://www.w3.org/2001/XMLSchema#string> .
          ?m sbml:hasOneKineticLaw ?s .
          ?s rdf:type sbml:KineticLaw .
         } """

    val l: java.util.LinkedList[KineticLaw]
    = Sparql.exec(model, classOf[KineticLaw], queryString)
    logger.debug("Found " + l.size + " KineticLaws from reaction " + reactionMetaId)
    if (l.size > 0) {
      l.iterator.next.asInstanceOf[KineticLaw]
    } else null
  }

  def loadKineticLaw(): java.util.Collection[KineticLaw] = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      logger.debug("After loading Jena Model")
      var reader = new RDF2Bean(myModel)
      logger.debug("After creating a new RDF2Bean")
      val l: java.util.List[KineticLaw] = reader.load(new KineticLaw().getClass)
              .asInstanceOf[java.util.List[KineticLaw]]
      l
    } catch {
      case ex: thewebsemantic.NotFoundException =>
        logger.debug("Bean of " + KineticLaw.getClass + "and id is not found")
        ex.printStackTrace()
        null
    }
  }

  /**
   * Saves an KineticLaw into the KnowledgeBase
   * @param true if
   * @return true if
   */
  def createKineticLaw(kineticLaw: KineticLaw): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      createKineticLaw(kineticLaw, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        logger.debug("Bean of " + KineticLaw.getClass + "and " +
                "id is not found")
        ex.printStackTrace()
        false
      }
      case ex => {
        logger.debug("Saving kineticLaw " + kineticLaw +
                "was not possible")
        ex.printStackTrace

        false
      }
    }
  }

  /**
   * Creates a new SBML kineticLaw individual in the Knowledgebase
   * @return true if creating the new kineticLaw was possible and false otherwise
   */
  def createKineticLaw(kineticLaw: KineticLaw, model: Model): Boolean = {
    val writer = new Bean2RDF(model)

    var tmpKinLaw = new KineticLaw()
    tmpKinLaw.listOfParameters =  kineticLaw.listOfParameters
    kineticLaw.listOfParameters = null
    writer.save(kineticLaw)

    val parameterDAO = new ParametersDAO
    tmpKinLaw.listOfParameters.map(
      parameterDAO.tryToCreateParameterInKineticLaw(
        kineticLaw.metaid,_, model ) )
    true
  }

  def tryToCreateKineticLawInReaction(reactionMetaid: String,
                                      kineticLaw: KineticLaw): String = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      tryToCreateKineticLawInReaction(reactionMetaid, kineticLaw, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        logger.debug("Bean of " + KineticLaw.getClass + "and " +
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

  def tryToCreateKineticLawInReaction(reactionMetaid: String,
                                      kineticLaw: KineticLaw,
                                      model: Model): String = {
    if (reactionsDAO.reactionMetaidExists(reactionMetaid, model)) {
      val kineticLawMetaid = tryToCreateKineticLaw(kineticLaw, model)
      if (kineticLawMetaid != null) {
        //Jena API used directly
        val reactionRes = model.createResource(
          NS.sbml + "Reaction/" + reactionMetaid)
        val kineticLawRes = model.createResource(
          NS.sbml + "KineticLaw/" + kineticLawMetaid)

        reactionRes.addProperty(model
                .getProperty(NS.sbml + "hasOneKineticLaw"),
          kineticLawRes)
        kineticLawMetaid
      } else null
    } else null
  }

  /**
   * Method that creates a new KineticLaw in the KnowledgeBase after checking
   * if everything is valid with the kineticLaw that is being created
   * This method also issues an available metaid
   *
   */
  def tryToCreateKineticLaw(kineticLaw: KineticLaw): String = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      tryToCreateKineticLaw(kineticLaw, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        logger.debug("Bean of " + KineticLaw.getClass + "and " +
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

  def tryToCreateKineticLaw(kineticLaw: KineticLaw, model: Model): String = {
    if (if (kineticLaw.metaid != null &&
            kineticLaw.metaid.trim != "" &&
            !sbmlModelsDAO.metaIdExists(kineticLaw.metaid, model)) {
          createKineticLaw(kineticLaw, model)
        } else {
          kineticLaw.metaid = sbmlModelsDAO.generateNewMetaIdFromString("kineticLaw_",
            model)
          createKineticLaw(kineticLaw, model)
        } == true)
      {
        if (kineticLaw.metaid == null || kineticLaw.metaid.trim == "") {
          throw new BadFormatException("kineticLawDAO.tryTocreatekineticLaw error")
        }
        kineticLaw.metaid
      } else null
  }

  def kineticLawMetaidExists(metaid: String): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      kineticLawMetaidExists(metaid, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        logger.debug("Bean of " + KineticLaw.getClass + "and " +
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

  def kineticLawMetaidExists(metaid: String, model: Model): Boolean = {
    val queryString =
    """
    PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    ASK
    { ?s rdf:type sbml:KineticLaw .
    """ + "?s sbml:metaid \"" + metaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val query: Query = QueryFactory.create(queryString);
    val qe: QueryExecution = QueryExecutionFactory.create(query, model);
    val results: Boolean = qe.execAsk;

    logger.debug("SPARQL query \n" + queryString + "\nIs " + results)

    results
  }

  /**
   * Updates a KineticLaw into the KnowledgeBase
   * @param true if
   * @return true if
   */
  def updateKineticLaw(kineticLaw: KineticLaw): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      updateKineticLaw(kineticLaw, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        logger.debug("Bean of " + KineticLaw.getClass + "and " +
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
   * Updates a KineticLaw individual in the Knowledgebase
   * @return true if creating the new mkineticLaw was possible and false otherwise
   */
  def updateKineticLaw(kineticLaw: KineticLaw, model: Model): Boolean = {
    if (sbmlModelsDAO.metaIdExists(kineticLaw.metaid, model)) {
      val writer = new Bean2RDF(model)
      writer.save(kineticLaw)
      true
    } else false
  }


  /**
   * Deletes an KineticLaw in the KnowledgeBase
   * @param true if
   * @return true if
   */
  def deleteKineticLaw(kineticLaw: KineticLaw): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      deleteKineticLaw(kineticLaw, myModel)
    } catch {
      case ex: Exception => {
        logger.debug("Deleting kineticLaw " + kineticLaw +
                "was not possible")
        ex.printStackTrace

        false
      }
    }
  }

  /**
   * Deletes an KineticLaw in the KnowledgeBase
   * @return true if deleting the new kineticLaw was possible and false otherwise
   */
  def deleteKineticLaw(kineticLaw: KineticLaw, model: Model): Boolean = {
    try {
      if (kineticLawMetaidExists(kineticLaw.metaid)) {
        val writer = new Bean2RDF(model)
        writer.delete(kineticLaw)
        //TODO delete subelements
        true
      } else false
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        logger.debug("Bean of " + KineticLaw.getClass + "and " +
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
