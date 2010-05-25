/*
 * ModelsDAO.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataAccess

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model

import thewebsemantic.Bean2RDF
import thewebsemantic.RDF2Bean
import thewebsemantic.Sparql

import scala.Collection
import scalaj.collection.Imports._
import scala.collection.JavaConversions._

import pt.cnbc.wikimodels.dataModel.SBMLModel
import pt.cnbc.wikimodels.dataModel.Element
import pt.cnbc.wikimodels.dataModel.SBMLModels
import pt.cnbc.wikimodels.exceptions.BadFormatException
import pt.cnbc.wikimodels.ontology.ManipulatorWrapper

class SBMLModelsDAO {

  /**
   * Allows testing procedures. This is not to be used from outside this class
   */
  var kb: Model = null


  def deepLoadSBMLModel(modelMetaid: String): SBMLModel = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      deepLoadSBMLModel(modelMetaid, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException =>
        Console.println("Bean of " + SBMLModel.getClass + "and " +
                "id is not found")
        ex.printStackTrace()
        null
      case ex =>
        ex.printStackTrace()
        null
    }
  }

  def deepLoadSBMLModel(modelMetaId: String, model: Model): SBMLModel = {
    var ret: SBMLModel = null

    Console.print("After loading Jena Model")
    if (modelMetaId == null) throw new java.lang.NullPointerException("modelMetaId is null")
    if (model == null) throw new java.lang.NullPointerException("model is null")
    Console.print("Jena Model content")
    val queryString =
    """
    PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    SELECT ?s WHERE
    { ?s rdf:type sbml:Model .
    """ + "?s sbml:metaid \"" + modelMetaId + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val l: java.util.LinkedList[SBMLModel]
    = Sparql.exec(model, classOf[SBMLModel], queryString)
    Console.println("Found " + l.size + " SBMLModels with metaid " + modelMetaId)

    if (l.size > 0) {
      val sbmlmodel = l.iterator.next

      val funcDefDAO = new FunctionDefinitionsDAO()
      sbmlmodel.listOfFunctionDefinitions = funcDefDAO.loadFunctionDefinitionsInModel(
        sbmlmodel.metaid, model)

      val compDAO = new CompartmentsDAO()
      sbmlmodel.listOfCompartments = compDAO.loadCompartmentsInModel(
        sbmlmodel.metaid, model)

      val speciesDAO = new SpeciessDAO()
      sbmlmodel.listOfSpecies = speciesDAO.loadSpeciesInModel(
        sbmlmodel.metaid, model)

      val paramDAO = new ParametersDAO()
      sbmlmodel.listOfParameters = paramDAO.loadParametersInModel(
        sbmlmodel.metaid, model)

      val constDAO = new ConstraintsDAO()
      sbmlmodel.listOfConstraints = constDAO.loadConstraintsInModel(
        sbmlmodel.metaid, model)

      val reactDAO = new ReactionsDAO()
      sbmlmodel.listOfReactions = reactDAO.loadReactionsInModel(
        sbmlmodel.metaid, model)
      sbmlmodel
    } else null
  }


  def loadSBMLModels(): SBMLModels = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      loadSBMLModels(myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException =>
        Console.println("Bean of " + SBMLModel.getClass + "and id is not found")
        ex.printStackTrace()
        null
    }
  }

  def loadSBMLModels(model: Model): SBMLModels = {
    val sbmlmodels = new SBMLModels()
    sbmlmodels.listOfModels = loadListOfSBMLModels(model)
    sbmlmodels
  }

  def loadListOfSBMLModels(myModel: Model): java.util.Collection[SBMLModel] = {
    Console.print("After loading Jena Model")
    var reader = new RDF2Bean(myModel)
    Console.print("After creating a new RDF2Bean")
    reader.load[SBMLModel](classOf[SBMLModel])
  }


  /**
   * Saves an SBMLModel into the KnowledgeBase
   * @param true if
   * @return true if
   */
  def createSBMLModel(sbmlmodel: SBMLModel): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      createSBMLModel(sbmlmodel, myModel)
    } catch {
      case ex: Exception => {
        Console.println("Saving model " + sbmlmodel +
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
  def createSBMLModel(sbmlmodel: SBMLModel, model: Model): Boolean = {
    try {
      val writer = new Bean2RDF(model)

      Console.println("SBML Model before removing lists")
      Console.println(sbmlmodel.toXML.toString)
      Console.println("")

      //Code to keep save from saving the subelements since we need to check if their metaids already exist
      val tmpsbmlmodel = new SBMLModel()
      tmpsbmlmodel.listOfFunctionDefinitions = sbmlmodel.listOfFunctionDefinitions
      sbmlmodel.listOfFunctionDefinitions = null
      tmpsbmlmodel.listOfCompartments = sbmlmodel.listOfCompartments
      sbmlmodel.listOfCompartments = null
      tmpsbmlmodel.listOfSpecies = sbmlmodel.listOfSpecies
      sbmlmodel.listOfSpecies = null
      tmpsbmlmodel.listOfParameters = sbmlmodel.listOfParameters
      sbmlmodel.listOfParameters = null
      tmpsbmlmodel.listOfConstraints = sbmlmodel.listOfConstraints
      sbmlmodel.listOfConstraints = null
      tmpsbmlmodel.listOfReactions = sbmlmodel.listOfReactions
      sbmlmodel.listOfReactions = null

      Console.println("Temp SBML Model after addomg lists")
      Console.println(tmpsbmlmodel.toXML.toString)
      Console.println("")

      Console.println("SBML Model after removing lists")
      Console.println(sbmlmodel.toXML.toString)
      Console.println("")

      writer.save(sbmlmodel)
      val funcDefDAO = new FunctionDefinitionsDAO()
      tmpsbmlmodel.listOfFunctionDefinitions.map(
        funcDefDAO.trytoCreateFunctionDefinitionInModel(
          sbmlmodel.metaid, _, model))

      val compDAO = new CompartmentsDAO()
      tmpsbmlmodel.listOfCompartments.map(
        compDAO.trytoCreateCompartmentInModel(
          sbmlmodel.metaid, _, model))

      val speciesDAO = new SpeciessDAO()
      tmpsbmlmodel.listOfSpecies.map(
        speciesDAO.trytoCreateSpeciesInModel(
          sbmlmodel.metaid, _, model))

      val paramDAO = new ParametersDAO()
      tmpsbmlmodel.listOfParameters.map(
        paramDAO.trytoCreateParameterInModel(
          sbmlmodel.metaid, _, model))

      val constDAO = new ConstraintsDAO()
      tmpsbmlmodel.listOfConstraints.map(
        constDAO.trytoCreateConstraintInModel(
          sbmlmodel.metaid, _, model))

      val reactDAO = new ReactionsDAO()
      tmpsbmlmodel.listOfReactions.map(
        reactDAO.trytoCreateReactionInModel(
          sbmlmodel.metaid, _, model))
      true
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + SBMLModel.getClass + "and " +
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
   * Method that creates a new Model in the KnowledgeBase after checking
   * if everything is valid with the model that is being created
   * This method also issues an available metaid
   *
   */
  def trytoCreateSBMLModel(sbmlModel: SBMLModel): String = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      trytoCreateSBMLModel(sbmlModel, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + SBMLModel.getClass + "and " +
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

  def trytoCreateSBMLModel(sbmlModel: SBMLModel, model: Model): String = {
    if (if (metaidExists(sbmlModel.metaid) == false) {
      createSBMLModel(sbmlModel, model)
    } else {
      sbmlModel.metaid = generateNewMetaIdFrom(sbmlModel,
        model)
      createSBMLModel(sbmlModel,
        model)
    } == true)
      {
        sbmlModel.metaid
      } else null

  }

  def generateNewMetaIdFrom(sbmlentity: Element): String = {
    val myModel: Model = ManipulatorWrapper.loadModelfromDB
    generateNewMetaIdFrom(sbmlentity, myModel)
  }

  /**
   * Generates a new metaid.
   * It uses the metaid, id or name depending on which is not empty
   * and using this exact order
   */
  def generateNewMetaIdFrom(sbmlentity: Element, model: Model): String = {
    if (sbmlentity.metaid == null || sbmlentity.metaid.trim == "") {
      if (sbmlentity.theId == null || sbmlentity.theId.trim == "") {
        throw new BadFormatException("ids are mandatory")
        null
      } else {
        generateNewMetaIdFromString(sbmlentity.theId, model)
      }
    } else {
      generateNewMetaIdFromString(sbmlentity.metaid, model)
    }
  }

  def generateNewMetaIdFromString(string: String): String = {
    val myModel: Model = ManipulatorWrapper.loadModelfromDB
    generateNewMetaIdFromString(string, myModel)
  }

  def generateNewMetaIdFromString(string: String, model: Model): String = {
    var i = 0
    while (metaidExists("" + string + i) == true) {
      i = i + 1
    }
    "" + string + i
  }

  /**
   * checks if metaid exists in WikiModels KnowledgeBase
   * the metaid is meant to be unique in all of the KB and used as id for the
   * entities created within it
   */
  def metaidExists(metaid: String): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      metaidExists(metaid, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + SBMLModel.getClass + "and " +
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

  def metaidExists(metaid: String, model: Model): Boolean = {
    /*val reasoner:Reasoner = ReasonerRegistry.getOWLReasoner
     //val ontModelSpec:OntModelSpec = null
     //val ont:OntModel = ModelFactory.createOntologyModel(ontModelSpec, model)
     val ont:InfModel = ModelFactory.createInfModel(reasoner, model)*/

    val queryString =
    """
    PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    ASK
    """ + "{ ?s sbml:metaid \"" + metaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> }"

    val query: Query = QueryFactory.create(queryString);
    val qe: QueryExecution = QueryExecutionFactory.create(query, model);
    val result: Boolean = qe.execAsk;

    Console.println("SPARQL query \n" + queryString + "\nIs " + result)

    result
  }

  def modelMetaidExists(metaid: String): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      modelMetaidExists(metaid, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + SBMLModel.getClass + "and " +
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

  def modelMetaidExists(metaid: String, model: Model): Boolean = {
    //val reasoner:Reasoner = ReasonerRegistry.getOWLReasoner
    //val ontModelSpec:OntModelSpec = null
    //val ont:OntModel = ModelFactory.createOntologyModel(ontModelSpec, model)
    //val ont:InfModel = ModelFactory.createInfModel(reasoner, model)
    val queryString =
    """
    PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    ASK
    { ?s rdf:type sbml:Model .
    """ + "?s sbml:metaid \"" + metaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val query: Query = QueryFactory.create(queryString);
    val qe: QueryExecution = QueryExecutionFactory.create(query, model);
    val results: Boolean = qe.execAsk;

    Console.println("SPARQL query \n" + queryString + "\nIs " + results)

    results
  }

  def modelIdExists(id: String): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      modelIdExists(id, myModel)
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + SBMLModel.getClass + "and " +
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

  def modelIdExists(id: String, model: Model): Boolean = {
    //val reasoner:Reasoner = ReasonerRegistry.getOWLReasoner
    //val ontModelSpec:OntModelSpec = null
    //val ont:OntModel = ModelFactory.createOntologyModel(ontModelSpec, model)
    //val ont:InfModel = ModelFactory.createInfModel(reasoner, model)
    val queryString =
    """
    PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
    PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
    ASK
    { ?s rdf:type sbml:Model .
    """ + "?s sbml:id \"" + id + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

    val query: Query = QueryFactory.create(queryString);
    val qe: QueryExecution = QueryExecutionFactory.create(query, model);
    val results: Boolean = qe.execAsk;
    Console.println("SPARQL query \n" + queryString + "\nIs " + results)
    results
  }

  /**
   * Saves an SBMLModel into the KnowledgeBase
   * @param true if
   * @return true if
   */
  def updateSBMLModel(sbmlmodel: SBMLModel): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      updateSBMLModel(sbmlmodel, myModel)
    } catch {
      case ex: Exception => {
        Console.println("Saving model " + sbmlmodel +
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
  def updateSBMLModel(sbmlmodel: SBMLModel, model: Model): Boolean = {
    try {
      if (metaidExists(sbmlmodel.metaid)) {
        val writer = new Bean2RDF(model)
        writer.save(sbmlmodel)

        val funcDefDAO = new FunctionDefinitionsDAO()
        sbmlmodel.listOfFunctionDefinitions.map(
          funcDefDAO.updateFunctionDefinition(
            _, model))

        val compDAO = new CompartmentsDAO()
        sbmlmodel.listOfCompartments.map(
          compDAO.updateCompartment(
            _, model))

        val speciesDAO = new SpeciessDAO()
        sbmlmodel.listOfSpecies.map(
          speciesDAO.updateSpecies(
            _, model))

        val paramDAO = new ParametersDAO()
        sbmlmodel.listOfParameters.map(
          paramDAO.updateParameter(
            _, model))

        val constDAO = new ConstraintsDAO()
        sbmlmodel.listOfConstraints.map(
          constDAO.updateConstraint(
            _, model))

        val reactDAO = new ReactionsDAO()
        sbmlmodel.listOfReactions.map(
          reactDAO.updateReaction(
            _, model))

        true
      } else false
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + SBMLModel.getClass + "and " +
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
   * Deletes an SBMLModel in the KnowledgeBase
   * @param true if
   * @return true if
   */
  def deleteSBMLModel(sbmlmodel: SBMLModel): Boolean = {
    try {
      val myModel: Model = ManipulatorWrapper.loadModelfromDB
      deleteSBMLModel(sbmlmodel, myModel)
    } catch {
      case ex: Exception => {
        Console.println("Deleting model " + sbmlmodel +
                "was not possible")
        ex.printStackTrace

        false
      }
    }
  }

  /**
   * Deletes an SBMLModel in the KnowledgeBase
   * @return true if deleting the new model was possible and false otherwise
   */
  def deleteSBMLModel(sbmlmodel: SBMLModel, model: Model): Boolean = {
    try {
      if (modelMetaidExists(sbmlmodel.metaid)) {
        //TODO This can and should be optinized to a SPARQL query that only gets the metaids of teh subelements
        //of the model using propery 'has[element]' or even 'hasPart'. Using hasPart depends on the existence of a
        //InfModel and might not be the best choice
        val deepSbmlModel = deepLoadSBMLModel(sbmlmodel.metaid, model)

        if (deepSbmlModel.listOfFunctionDefinitions != null && deepSbmlModel.listOfFunctionDefinitions.size != 0) {
          val funcDefDAO = new FunctionDefinitionsDAO()
          deepSbmlModel.listOfFunctionDefinitions.map(
            funcDefDAO.deleteFunctionDefinition(
              _, model))
        }
        if (deepSbmlModel.listOfCompartments != null && deepSbmlModel.listOfCompartments.size != 0) {
          val compDAO = new CompartmentsDAO()
          deepSbmlModel.listOfCompartments.map(
            compDAO.deleteCompartment(
              _, model))
        }
        if (deepSbmlModel.listOfSpecies != null && deepSbmlModel.listOfSpecies.size != 0) {
          val speciesDAO = new SpeciessDAO()
          deepSbmlModel.listOfSpecies.map(
            speciesDAO.deleteSpecies(
              _, model))
        }
        if (deepSbmlModel.listOfParameters != null && deepSbmlModel.listOfParameters.size != 0) {
          val paramDAO = new ParametersDAO()

          deepSbmlModel.listOfParameters.map(
            paramDAO.deleteParameter(
              _, model))
        }
        if (deepSbmlModel.listOfConstraints != null && deepSbmlModel.listOfConstraints.size != 0) {
          val constDAO = new ConstraintsDAO()

          deepSbmlModel.listOfConstraints.map(
            constDAO.deleteConstraint(
              _, model))
        }
        if (deepSbmlModel.listOfReactions != null && deepSbmlModel.listOfReactions.size != 0) {
          val reactDAO = new ReactionsDAO()

          deepSbmlModel.listOfReactions.map(
            reactDAO.deleteReaction(
              _, model))
        }
        val writer = new Bean2RDF(model)
        writer.delete(deepSbmlModel)
        true
      } else false
    } catch {
      case ex: thewebsemantic.NotFoundException => {
        Console.println("Bean of " + SBMLModel.getClass + "and " +
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

  /*def makeComment(comment:Comment)
   def getComments(metaid:String)*/
}
