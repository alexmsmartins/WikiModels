/*
 * SpeciessDAO.scala
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

import pt.cnbc.wikimodels.dataModel.Species
import pt.cnbc.wikimodels.dataModel.Element
import pt.cnbc.wikimodels.dataModel.SBMLModel
import pt.cnbc.wikimodels.exceptions.NotImplementedException
import pt.cnbc.wikimodels.ontology.ManipulatorWrapper
import pt.cnbc.wikimodels.ontology.{Namespaces => NS}

class SpeciessDAO {
    /**
     * Allows testing procedures. This is not to be used from outside this class
     */
    var  kb:Model = null
    val sbmlModelsDAO = new SBMLModelsDAO()

    def loadSpecies(speciesMetaid:String):Species = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            loadSpecies(speciesMetaid, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException =>
                Console.println("Bean of " + Species.getClass + "and " +
                                "id is not found")
                ex.printStackTrace()
                null
            case ex =>
                ex.printStackTrace()
                null
        }
    }

    def loadSpecies(speciesMetaid:String, model:Model):Species = {
        var ret:Species = null

        Console.print("After loading Jena Model")
        var reader = new RDF2Bean(model)
        Console.print("After creating a new RDF2Bean")
        val l
        = reader.load( new Species().getClass, speciesMetaid  )
                .asInstanceOf[java.util.Collection[Species]]
        Console.println("Found " + l.size + " Speciess with metaid " + speciesMetaid)
        if(l.size > 0)
            l.iterator.next
        else null
    }

    def loadSpecies():java.util.Collection[Species] = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            Console.print("After loading Jena Model")
            var reader = new RDF2Bean(myModel)
            Console.print("After creating a new RDF2Bean")
            val l:java.util.List[Species] = reader.load(new Species().getClass )
                .asInstanceOf[java.util.List[Species]]
            l
        } catch {
            case ex:thewebsemantic.NotFoundException =>
                Console.println("Bean of " + Species.getClass + "and id is not found")
                ex.printStackTrace()
                null
        }
    }



    /**
     * Saves an Species into the KnowledgeBase
     * @param  true if
     * @return true if
     */
    def createSpecies(species:Species):Boolean = {
        var ret = false
        var myModel:Model = null
        try{
            myModel = ManipulatorWrapper.loadModelfromDB
            myModel.begin
            ret = createSpecies(species, myModel)
            myModel.commit
        } catch {
            case ex:Exception => {
                    Console.println("Saving model " + species +
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
    def createSpecies(species:Species, model:Model):Boolean = {
        try{
            val writer = new Bean2RDF(model)
            writer.save(species)
            true
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + Species.getClass + "and " +
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

    def trytoCreateSpeciesInModel(modelMetaid:String,
                                    species:Species):String = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            trytoCreateSpeciesInModel(modelMetaid, species, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + Species.getClass + "and " +
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

    def trytoCreateSpeciesInModel(modelMetaid:String,
                                    species:Species,
                                    model:Model):String = {
        if(sbmlModelsDAO.modelMetaidExists(modelMetaid)){
            val speciesMetaid = trytoCreateSpecies(species, model)

            //Jena API used directly
            val sbmlModelRes = model.createResource(
                         NS.sbml + "Model/" + modelMetaid)
            val speciesRes = model.createResource(
                        NS.sbml + "Model/" + speciesMetaid)

            sbmlModelRes.addProperty(model
                         .getProperty(NS.sbml + "hasPArameter"),
                                     speciesRes)
            speciesMetaid
        } else null
    }

    /**
     * Method that creates a new Model in the KnowledgeBase after checking
     * if everything is valid with the model that is being created
     * This method also issues an available metaid
     *
     */
    def trytoCreateSpecies(species:Species):String = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            trytoCreateSpecies(species, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + Species.getClass + "and " +
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

    def trytoCreateSpecies(species:Species, model:Model):String = {
        if( if( sbmlModelsDAO.metaidExists(species.metaid ) == false ){
                createSpecies(species, model)
            } else {
                species.metaid = sbmlModelsDAO.generateNewMetaIdFrom(species,
                                                         model)
                createSpecies(species,
                                model)
            } == true)
        {
            species.metaid
        } else null

    }

    def speciesMetaidExists(metaid:String):Boolean = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            speciesMetaidExists(metaid, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + Species.getClass + "and " +
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

    def speciesMetaidExists(metaid:String, model:Model):Boolean = {
        val reasoner:Reasoner = ReasonerRegistry.getOWLReasoner
        //val ontModelSpec:OntModelSpec = null
        //val ont:OntModel = ModelFactory.createOntologyModel(ontModelSpec, model)
        val ont:InfModel = ModelFactory.createInfModel(reasoner, model)
        val queryString =
        """
        PREFIX sbml: <http://wikimodels.cnbc.pt/ontologies/sbml.owl#>
        PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
        ASK
        { ?s rdf:type sbml:Species .
        """ +  "?s sbml:metaid \"" + metaid + "\"^^<http://www.w3.org/2001/XMLSchema#string> } "

        val query:Query = QueryFactory.create(queryString);
        val qe:QueryExecution = QueryExecutionFactory.create(query, ont);
        val results:Boolean = qe.execAsk;

        Console.println("SPARQL query \n" + queryString + "\nIs " + results)

        results
    }
    
    /**
     * Updates a Species into the KnowledgeBase
     * @param  true if
     * @return true if
     */
    def updateSpecies(species:Species):Boolean = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            updateSpecies(species, myModel)
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + Species.getClass + "and " +
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
     * Updates a Species individual in the Knowledgebase
     * @return true if creating the new model was possible and false otherwise
     */
    def updateSpecies(species:Species, model:Model):Boolean = {
        if( sbmlModelsDAO.metaidExists(species.metaid ) ){
            val writer = new Bean2RDF(model)
            writer.save(species)
            true
        } else false
    }


    /**
     * Deletes an Species in the KnowledgeBase
     * @param  true if
     * @return true if
     */
    def deleteSpecies(species:Species):Boolean = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            deleteSpecies(species, myModel)
        } catch {
            case ex:Exception => {
                    Console.println("Deleting model " + species +
                                    "was not possible")
                    ex.printStackTrace

                    false
                }
        }
    }

    /**
     * Deletes an Species in the KnowledgeBase
     * @return true if creating the new model was possible and false otherwise
     */
    def deleteSpecies(species:Species, model:Model):Boolean = {
        try{
            if( speciesMetaidExists(species.metaid ) ){
                val writer = new Bean2RDF(model)
                writer.delete(species)
                //TODO delete subelements
                true
            } else false
        } catch {
            case ex:thewebsemantic.NotFoundException => {
                    Console.println("Bean of " + Species.getClass + "and " +
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
