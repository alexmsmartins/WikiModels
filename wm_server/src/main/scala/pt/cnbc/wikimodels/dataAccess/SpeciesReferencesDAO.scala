/*
 * SpeciesReferencesDAO.scala
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

import pt.cnbc.wikimodels.dataModel.ModifierSpeciesReference
import pt.cnbc.wikimodels.dataModel.SpeciesReference
import pt.cnbc.wikimodels.dataModel.Comment
import pt.cnbc.wikimodels.dataModel.Element
import pt.cnbc.wikimodels.dataModel.SBMLModel
import pt.cnbc.wikimodels.exceptions.NotImplementedException
import pt.cnbc.wikimodels.ontology.ManipulatorWrapper
import pt.cnbc.wikimodels.ontology.{Namespaces => NS}
import thewebsemantic.Sparql

class SpeciesReferencesDAO {
  /**
   * Allows testing procedures. This is not to be used from outside this class
   */
  var kb: Model = null
  val sbmlModelsDAO = new SBMLModelsDAO()
  val reactionsDAO = new ReactionsDAO()

  def loadSpeciesReference(speciesReferenceMetaid: String): SpeciesReference = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      loadSpeciesReference(speciesReferenceMetaid, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException =>
        Console.println("Bean of " + SpeciesReference.getClass + "and " +
                "id is not found")
        ex.printStackTrace()
        null
      case ex =>
        ex.printStackTrace()
        null
    }
  }

  def loadSpeciesReference(speciesReferenceMetaid: String, model: Model): SpeciesReference = {
    var ret: SpeciesReference = null

    Console.print("After loading Jena Model")
    Console.print("Jena Model content")
    val queryString =
    """
    PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    SELECT ?s WHERE
    { ?s rdf:type sbml:SpeciesReference .
    """ + "?s sbml:metaid \"" + speciesReferenceMetaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val l: java.util.LinkedList[SpeciesReference]
    = Sparql.exec(model, classOf[SpeciesReference], queryString)
    Console.println("Found " + l.size + " SpeciesReferences with metaid " + speciesReferenceMetaid)
    if (l.size > 0)
      l.iterator.next
    else null
  }

  def loadReactantsInReaction(reactionMetaId: String,
                              model: Model): java.util.Collection[SpeciesReference] = {
    Console.print("After loading Jena Model")
    Console.print("Jena Model content")
    val queryString =
    """
PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT ?s WHERE
{ ?m rdf:type sbml:Reaction .
  ?m sbml:metaid """" + reactionMetaId + """"^^<http://www.w3.org/2001/XMLSchema#string> .
          ?m sbml:hasReactant ?s .
          ?s rdf:type sbml:SpeciesReference .
         } """

    val l: java.util.LinkedList[SpeciesReference]
    = Sparql.exec(model, classOf[SpeciesReference], queryString)
    Console.println("Found " + l.size + " Reactants from model " + reactionMetaId)
    if (l.size > 0)
      l
    else null
  }

  def loadProductsInReaction(reactionMetaId: String,
                             model: Model): java.util.Collection[SpeciesReference] = {
    Console.print("After loading Jena Model")
    Console.print("Jena Model content")
    val queryString =
    """
PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT ?s WHERE
{ ?m rdf:type sbml:Reaction .
  ?m sbml:metaid """" + reactionMetaId + """"^^<http://www.w3.org/2001/XMLSchema#string> .
          ?m sbml:hasProduct ?s .
          ?s rdf:type sbml:SpeciesReference .
         } """

    val l: java.util.LinkedList[SpeciesReference]
    = Sparql.exec(model, classOf[SpeciesReference], queryString)
    Console.println("Found " + l.size + " Products from reaction " + reactionMetaId)
    if (l.size > 0)
      l
    else null
  }

  def loadModifiersInReaction(reactionMetaId: String,
                              model: Model): java.util.Collection[ModifierSpeciesReference] = {
    Console.print("After loading Jena Model")
    Console.print("Jena Model content")
    val queryString =
    """
PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
SELECT ?s WHERE
{ ?m rdf:type sbml:Reaction .
  ?m sbml:metaid """" + reactionMetaId + """"^^<http://www.w3.org/2001/XMLSchema#string> .
          ?m sbml:hasModifier ?s .
          ?s rdf:type sbml:ModifierSpeciesReference .
         } """

    val l: java.util.LinkedList[ModifierSpeciesReference]
    = Sparql.exec(model, classOf[ModifierSpeciesReference], queryString)
    Console.println("Found " + l.size + " Modifiers from reaction " + reactionMetaId)
    if (l.size > 0)
      l
    else null
  }

  def loadSpeciesReference(): java.util.Collection[SpeciesReference] = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      Console.print("After loading Jena Model")
      var reader = new RDF2Bean(myModel)
      Console.print("After creating a new RDF2Bean")
      val l: java.util.List[SpeciesReference] = reader.load(new SpeciesReference().getClass)
              .asInstanceOf[java.util.List  [SpeciesReference]]
      l
    } catch {
      case ex: thewebsemantic.NotFoundException =>
        Console.println("Bean of " + SpeciesReference.getClass + "and id is not found")
        ex.printStackTrace()
        null
    }
  }

  /**
   * Saves an SpeciesReference into the KnowledgeBase
   * @param true if
   * @return true if
   */
  def createSpeciesReference(speciesReference: SpeciesReference): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      createSpeciesReference(speciesReference, myModel)
    } catch {
      case ex: Exception => {
        Console.println("Saving SpeciesReference " + speciesReference +
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
  def createSpeciesReference(speciesReference: SpeciesReference, model: Model): Boolean = {
    try {
      val writer = new Bean2RDF(model)
      writer.save(speciesReference)
      true
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + SpeciesReference.getClass + "and " +
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
   * Saves an ModifierSpeciesReference into the KnowledgeBase
   * @param true if
   * @return true if
   */
  def createModifierSpeciesReference(speciesReference: ModifierSpeciesReference): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      createModifierSpeciesReference(speciesReference, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + ModifierSpeciesReference.getClass + "and " +
                "id is not found")
        ex.printStackTrace()
        false
      }
      case ex => {
        Console.println("Saving ModifierSpeciesReference " + speciesReference +
                "was not possible")
        ex.printStackTrace

        false
      }
    }
  }

  /**
   * Creates a new ModifierSpeciesReference individual in the Knowledgebase
   * @return true if creating the new ModifierSpeciesReference was possible and false otherwise
   */
  def createModifierSpeciesReference(speciesReference: ModifierSpeciesReference, model: Model): Boolean = {
    val writer = new Bean2RDF(model)
    writer.save(speciesReference)
    true
  }

  def trytoCreateReactantInReaction(reactionMetaid: String,
                                    speciesReference: SpeciesReference,
                                    model: Model): String = {
    if (reactionsDAO.reactionMetaidExists(reactionMetaid)) {
      val speciesReferenceMetaid = trytoCreateSpeciesReference(speciesReference, model)
      if (speciesReferenceMetaid != null) {
        //Jena API used directly
        val reactionRes = model.createResource(
          NS.sbml + "Reaction/" + reactionMetaid)
        val speciesReferenceRes = model.createResource(
          NS.sbml + "SpeciesReference/" + speciesReferenceMetaid)

        reactionRes.addProperty(model
                .getProperty(NS.sbml + "hasReactant"),
          speciesReferenceRes)
        speciesReferenceMetaid
      } else null
    } else null
  }

  def trytoCreateProductInReaction(reactionMetaid: String,
                                   speciesReference: SpeciesReference,
                                   model: Model): String = {
    if (reactionsDAO.reactionMetaidExists(reactionMetaid)) {
      val speciesReferenceMetaid = trytoCreateSpeciesReference(speciesReference, model)
      if (speciesReferenceMetaid != null) {
        //Jena API used directly
        val reactionRes = model.createResource(
          NS.sbml + "Reaction/" + reactionMetaid)
        val speciesReferenceRes = model.createResource(
          NS.sbml + "SpeciesReference/" + speciesReferenceMetaid)

        reactionRes.addProperty(model
                .getProperty(NS.sbml + "hasProduct"),
          speciesReferenceRes)
        speciesReferenceMetaid
      } else null
    } else null
  }

  def trytoCreateModifierInReaction(reactionMetaid: String,
                                    speciesReference: ModifierSpeciesReference,
                                    model: Model): String = {
    if (reactionsDAO.reactionMetaidExists(reactionMetaid)) {
      val speciesReferenceMetaid = trytoCreateModifierSpeciesReference(speciesReference, model)
      if (speciesReferenceMetaid != null) {
        //Jena API used directly
        val reactionRes = model.createResource(
          NS.sbml + "Reaction/" + reactionMetaid)
        val speciesReferenceRes = model.createResource(
          NS.sbml + "ModifierSpeciesReference/" + speciesReferenceMetaid)

        reactionRes.addProperty(model
                .getProperty(NS.sbml + "hasModifier"),
          speciesReferenceRes)
        speciesReferenceMetaid
      } else null
    } else null
  }


  /**
   * Method that creates a new Model in the KnowledgeBase after checking
   * if everything is valid with the model that is being created
   * This method also issues an available metaid
   *
   */
  def trytoCreateSpeciesReference(speciesReference: SpeciesReference): String = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      trytoCreateSpeciesReference(speciesReference, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + SpeciesReference.getClass + "and " +
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

  def trytoCreateSpeciesReference(speciesReference: SpeciesReference, model: Model): String = {
    if (if (sbmlModelsDAO.metaIdExists(speciesReference.metaid, model) == false) {
      createSpeciesReference(speciesReference, model)
    } else {
      speciesReference.metaid = sbmlModelsDAO.generateNewMetaIdFrom(speciesReference,
        model)
      createSpeciesReference(speciesReference,
        model)
    } == true)
      {
        speciesReference.metaid
      } else null

  }

  /**
   * Method that creates a new ModifierSpeciesReference in the KnowledgeBase after checking
   * if everything is valid with the ModifierSpeciesReference that is being created
   * This method also issues an available metaid
   *
   */
  def trytoCreateModifierSpeciesReference(speciesReference: ModifierSpeciesReference): String = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      trytoCreateModifierSpeciesReference(speciesReference, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + ModifierSpeciesReference.getClass + "and " +
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

  def trytoCreateModifierSpeciesReference(speciesReference: ModifierSpeciesReference, model: Model): String = {
    if (if (sbmlModelsDAO.metaIdExists(speciesReference.metaid, model) == false) {
      createModifierSpeciesReference(speciesReference, model)
    } else {
      speciesReference.metaid = sbmlModelsDAO.generateNewMetaIdFrom(speciesReference,
        model)
      createModifierSpeciesReference(speciesReference,
        model)
    } == true)
      {
        speciesReference.metaid
      } else null

  }


  def speciesReferenceMetaidExists(metaid: String): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      speciesReferenceMetaidExists(metaid, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + SpeciesReference.getClass + "and " +
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

  def speciesReferenceMetaidExists(metaid: String, model: Model): Boolean = {
    //val reasoner:Reasoner = ReasonerRegistry.getOWLReasoner
    //val ontModelSpec:OntModelSpec = null
    //val ont:OntModel = ModelFactory.createOntologyModel(ontModelSpec, model)
    //val ont:InfModel = ModelFactory.createInfModel(reasoner, model)
    val queryString =
    """
    PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    ASK
    { ?s rdf:type sbml:SpeciesReference .
    """ + "?s sbml:metaid \"" + metaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val query: Query = QueryFactory.create(queryString);
    val qe: QueryExecution = QueryExecutionFactory.create(query, model);
    val results: Boolean = qe.execAsk;

    Console.println("SPARQL query \n" + queryString + "\nIs " + results)

    results
  }

  def modifierSpeciesReferenceMetaidExists(metaid: String): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      modifierSpeciesReferenceMetaidExists(metaid, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + ModifierSpeciesReference.getClass + "and " +
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

  def modifierSpeciesReferenceMetaidExists(metaid: String, model: Model): Boolean = {
    //val reasoner:Reasoner = ReasonerRegistry.getOWLReasoner
    //val ontModelSpec:OntModelSpec = null
    //val ont:OntModel = ModelFactory.createOntologyModel(ontModelSpec, model)
    //val ont:InfModel = ModelFactory.createInfModel(reasoner, model)
    val queryString =
    """
    PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    ASK
    { ?s rdf:type sbml:ModifierSpeciesReference .
    """ + "?s sbml:metaid \"" + metaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val query: Query = QueryFactory.create(queryString);
    val qe: QueryExecution = QueryExecutionFactory.create(query, model);
    val results: Boolean = qe.execAsk;

    Console.println("SPARQL query \n" + queryString + "\nIs " + results)

    results
  }

  /**
   * Updates a SpeciesReference into the KnowledgeBase
   * @param true if
   * @return true if
   */
  def updateSpeciesReference(speciesReference: SpeciesReference): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      updateSpeciesReference(speciesReference, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + SpeciesReference.getClass + "and " +
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
   * Updates a SpeciesReference individual in the Knowledgebase
   * @return true if updating the speciesReference was possible and false otherwise
   */
  def updateSpeciesReference(speciesReference: SpeciesReference, model: Model): Boolean = {
    if (sbmlModelsDAO.metaIdExists(speciesReference.metaid, model)) {
      val writer = new Bean2RDF(model)
      writer.save(speciesReference)
      true
    } else false
  }

  /**
   * Updates a SpeciesReference into the KnowledgeBase
   * @param true if
   * @return true if
   */
  def updateModifierSpeciesReference(speciesReference: ModifierSpeciesReference): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      updateModifierSpeciesReference(speciesReference, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + SpeciesReference.getClass + "and " +
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
   * Updates a SpeciesReference individual in the Knowledgebase
   * @return true if updating the speciesReference was possible and false otherwise
   */
  def updateModifierSpeciesReference(speciesReference: ModifierSpeciesReference, model: Model): Boolean = {
    if (sbmlModelsDAO.metaIdExists(speciesReference.metaid, model)) {
      val writer = new Bean2RDF(model)
      writer.save(speciesReference)
      true
    } else false
  }

  /**
   * Deletes an SpeciesReference in the KnowledgeBase
   * @param true if
   * @return true if
   */
  def deleteSpeciesReference(speciesReference: SpeciesReference): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      deleteSpeciesReference(speciesReference, myModel)
    } catch {
      case ex: Exception => {
        Console.println("Deleting speciesReference " + speciesReference +
                "was not possible")
        ex.printStackTrace

        false
      }
    }
  }

  /**
   * Deletes an SpeciesReference in the KnowledgeBase
   * @return true if deleting the new speciesReference was possible and false otherwise
   */
  def deleteSpeciesReference(speciesReference: SpeciesReference, model: Model): Boolean = {
    try {
      if (speciesReferenceMetaidExists(speciesReference.metaid)) {
        val writer = new Bean2RDF(model)
        writer.delete(speciesReference)
        true
      } else false
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + SpeciesReference.getClass + "and " +
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
   * Deletes an SpeciesReference in the KnowledgeBase
   * @param true if
   * @return true if
   */
  def deleteModifierSpeciesReference(speciesReference: ModifierSpeciesReference): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      deleteModifierSpeciesReference(speciesReference, myModel)
    } catch {
      case ex: Exception => {
        Console.println("Deleting speciesReference " + speciesReference +
                "was not possible")
        ex.printStackTrace

        false
      }
    }
  }

  /**
   * Deletes an ModifierSpeciesReference in the KnowledgeBase
   * @return true if deleting the new modifierSpeciesReference was possible and false otherwise
   */
  def deleteModifierSpeciesReference(speciesReference: ModifierSpeciesReference, model: Model): Boolean = {
    try {
      if (modifierSpeciesReferenceMetaidExists(speciesReference.metaid)) {
        val writer = new Bean2RDF(model)
        writer.delete(speciesReference)
        true
      } else false
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + SpeciesReference.getClass + "and " +
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
