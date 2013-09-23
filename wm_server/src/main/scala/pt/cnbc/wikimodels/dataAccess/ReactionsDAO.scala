/*
 * ReactionsDAO.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *  @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataAccess

import com.hp.hpl.jena.query.Query
import pt.cnbc.wikimodels.exceptions.{BadFormatException, NotImplementedException};
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

import scala.collection.JavaConversions._
//import scala.Collection

import pt.cnbc.wikimodels.ontology.ManipulatorWrapper
import pt.cnbc.wikimodels.dataModel._
import pt.cnbc.wikimodels.ontology.{Namespaces => NS}
import thewebsemantic.Sparql

class ReactionsDAO {
  /**
   * Allows testing procedures. This is not to be used from outside this class
   */
  var kb: Model = null
  val sbmlModelsDAO = new SBMLModelsDAO()

  def loadReaction(reactionMetaid: String): Reaction = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      loadReaction(reactionMetaid, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException =>
        Console.println("Bean of " + Reaction.getClass + "and " +
                "id is not found")
        ex.printStackTrace()
        null
      case ex =>
        ex.printStackTrace()
        null
    }
  }

  def loadReaction(reactionMetaid: String, model: Model): Reaction = {
    var ret: Reaction = null

    Console.print("After loading Jena Model")
    Console.print("Jena Model content")
    val queryString =
    """
    PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    SELECT ?s WHERE
    { ?s rdf:type sbml:Reaction .
    """ + "?s sbml:metaid \"" + reactionMetaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val l: java.util.LinkedList[Reaction]
    = Sparql.exec(model, classOf[Reaction], queryString)
    Console.println("Found " + l.size + " Reactions with metaid " + reactionMetaid)
    if (l.size > 0) {
      val reaction = l.iterator.next
      val specRefDAO = new SpeciesReferencesDAO()

      reaction.listOfReactants = specRefDAO.loadReactantsInReaction(
        reaction.metaid, model)

      reaction.listOfProducts = specRefDAO.loadProductsInReaction(
        reaction.metaid, model)

      reaction.listOfModifiers = specRefDAO.loadModifiersInReaction(
        reaction.metaid, model)

      val kinLawDAO = new KineticLawsDAO()
      reaction.kineticLaw = kinLawDAO.loadKineticLawInReaction(
        reaction.metaid, model)

      reaction
    } else null
  }

  def loadReactionsInModel(modelMetaId: String,
                           model: Model): java.util.Collection[Reaction] = {
    Console.print("After loading Jena Model")
    Console.print("Jena Model content")
    val queryString =
    """
    PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    SELECT ?s WHERE
    { ?m rdf:type sbml:Model .
      ?m sbml:metaid """" + modelMetaId + """"^^<http://www.w3.org/2001/XMLSchema#string> .
      ?m sbml:hasReaction ?s .
      ?s rdf:type sbml:Reaction .
     } """

    val l: java.util.LinkedList[Reaction]
    = Sparql.exec(model, classOf[Reaction], queryString)
    Console.println("Found " + l.size + " Reactions from model " + modelMetaId)
    if (l.size > 0){
      l.map(i => loadReaction(i.metaid, model))
    }
    else null
  }

  def loadReaction(): java.util.Collection[Reaction] = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      Console.print("After loading Jena Model")
      var reader = new RDF2Bean(myModel)
      Console.print("After creating a new RDF2Bean")
      val l: java.util.List[Reaction] = reader.load(new Reaction().getClass)
              .asInstanceOf[java.util.List[Reaction]]
      l
    } catch {
      case ex: thewebsemantic.NotFoundException =>
        Console.println("Bean of " + Reaction.getClass + "and id is not found")
        ex.printStackTrace()
        null
    }
  }

  /**
   * Saves an Reaction into the KnowledgeBase
   * @param reaction to be created
   * @return true if
   */
  def createReaction(reaction: Reaction): Boolean = {
    var ret = false
    var myModel: Model = null
    try {
      myModel = ManipulatorWrapper.loadModelfromDB
      myModel.begin
      ret = createReaction(reaction, myModel)
      myModel.commit
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + Reaction.getClass + "and " +
                "id is not found")
        ex.printStackTrace()
        false
      }
      case ex => {
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
  def createReaction(reaction: Reaction, model: Model): Boolean = {
    Console.println("CreateReaction(" + reaction.metaid + ", model)")    
    val writer = new Bean2RDF(model)

    //Code to keep save from saving the sub-elements since we need to check if their metaids already exist
    val tmpreaction = new Reaction()
    tmpreaction.listOfReactants = reaction.listOfReactants
    reaction.listOfReactants = null
    tmpreaction.listOfProducts = reaction.listOfProducts
    reaction.listOfProducts = null
    tmpreaction.listOfModifiers = reaction.listOfModifiers
    reaction.listOfModifiers = null
    tmpreaction.kineticLaw = reaction.kineticLaw
    reaction.kineticLaw = null

    writer.save(reaction)

    val specRefDAO = new SpeciesReferencesDAO()
    tmpreaction.listOfReactants.map(
      specRefDAO.tryToCreateReactantInReaction(
        reaction.metaid, _, model))
    tmpreaction.listOfProducts.map(
      specRefDAO.tryToCreateProductInReaction(
        reaction.metaid, _, model))
    tmpreaction.listOfModifiers.map(
      specRefDAO.tryToCreateModifierInReaction(
        reaction.metaid, _, model))

    if(tmpreaction.kineticLaw != null){
      val kinLawDAO = new KineticLawsDAO()
      kinLawDAO.tryToCreateKineticLawInReaction(
        reaction.metaid, tmpreaction.kineticLaw, model)
    }
    true
  }

  def tryToCreateReactionInModel(modelMetaid: String,
                                 reaction: Reaction): String = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      tryToCreateReactionInModel(modelMetaid, reaction, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
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

  def tryToCreateReactionInModel(modelMetaid: String,
                                 reaction: Reaction,
                                 model: Model): String = {
    if (sbmlModelsDAO.modelMetaIdExists(modelMetaid, model)) {
      val reactionMetaid = tryToCreateReaction(reaction, model)
      if (reactionMetaid != null) {

        //Jena API used directly
        val sbmlModelRes = model.createResource(
          NS.sbml + "Model/" + modelMetaid)
        val reactionRes = model.createResource(
          NS.sbml + "Reaction/" + reactionMetaid)

        sbmlModelRes.addProperty(model
                .getProperty(NS.sbml + "hasReaction"),
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
  def tryToCreateReaction(reaction: Reaction): String = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      tryToCreateReaction(reaction, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
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

  def tryToCreateReaction(reaction: Reaction, model: Model): String = {
    if (if (reaction.metaid != null &&
            reaction.metaid.trim != "" &&
            !sbmlModelsDAO.metaIdExists(reaction.metaid, model)) {
          createReaction(reaction, model)
        } else {
          reaction.metaid = sbmlModelsDAO.generateNewMetaIdFrom(reaction,
            model)
          createReaction(reaction, model)
        } == true)
    {
      if (reaction.metaid == null || reaction.metaid.trim == "") {
        throw new BadFormatException("ReactionDAO.tryTocreateReaction error")
      }
      reaction.metaid
    } else null
  }

  def reactionMetaidExists(metaid: String): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      reactionMetaidExists(metaid, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
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

  def reactionMetaidExists(metaid: String, model: Model): Boolean = {
    val queryString =
    """
    PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    ASK
    { ?s rdf:type sbml:Reaction .
    """ + "?s sbml:metaid \"" + metaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val query: Query = QueryFactory.create(queryString);
    val qe: QueryExecution = QueryExecutionFactory.create(query, model);
    val results: Boolean = qe.execAsk;

    Console.println("SPARQL query \n" + queryString + "\nIs " + results)

    results
  }

  /**
   * Updates a Reaction into the KnowledgeBase
   * @param true if
   * @return true if
   */
  def updateReaction(reaction: Reaction): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      updateReaction(reaction, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
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
  def updateReaction(reaction: Reaction, model: Model): Boolean = {
    if (sbmlModelsDAO.metaIdExists(reaction.metaid, model)) {
      val writer = new Bean2RDF(model)
      writer.save(reaction)

      val specRefDAO = new SpeciesReferencesDAO()
      reaction.listOfReactants.map(
        specRefDAO.updateSpeciesReference(
          _, model))

      reaction.listOfProducts.map(
        specRefDAO.updateSpeciesReference(
          _, model))

      reaction.listOfModifiers.map(
        specRefDAO.updateModifierSpeciesReference(
          _, model))

      if(reaction.kineticLaw != null){
        val kinLawDAO = new KineticLawsDAO()
        kinLawDAO.updateKineticLaw(reaction.kineticLaw)
      }
      true
    } else false
  }


  /**
   * Deletes an Reaction in the KnowledgeBase
   * @param true if
   * @return true if
   */
  def deleteReaction(reaction: Reaction): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      deleteReaction(reaction, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + Reaction.getClass + "and " +
                "id is not found")
        ex.printStackTrace()
        false
      }
      case ex => {
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
  def deleteReaction(reaction: Reaction, model: Model): Boolean = {
    if (reactionMetaidExists(reaction.metaid)) {
      val writer = new Bean2RDF(model)
      writer.delete(reaction)
      //TODO delete subelements
      true
    } else false
  }
}
