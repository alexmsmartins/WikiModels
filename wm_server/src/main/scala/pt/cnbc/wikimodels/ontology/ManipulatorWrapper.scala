/*
 * .ManipulatorWrapper.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.ontology

import org.apache.log4j.Logger
import com.hp.hpl.jena.graph.GraphEvents
import com.hp.hpl.jena.ontology.OntModel
import com.hp.hpl.jena.query.Query
import com.hp.hpl.jena.query.QueryExecution
import com.hp.hpl.jena.query.QueryExecutionFactory
import com.hp.hpl.jena.query.QueryFactory
import com.hp.hpl.jena.query.QuerySolution
import com.hp.hpl.jena.query.ResultSet
import com.hp.hpl.jena.query.ResultSetFormatter
import com.hp.hpl.jena.rdf.model.ModelFactory
import com.hp.hpl.jena.rdf.model.Model
import com.hp.hpl.jena.rdf.model.Resource
import com.hp.hpl.jena.sdb.StoreDesc
import com.hp.hpl.jena.sdb.store.LayoutType
import com.hp.hpl.jena.sdb.store.DatabaseType
import com.hp.hpl.jena.sdb.sql.JDBC
import com.hp.hpl.jena.sdb.sql.SDBConnection
import com.hp.hpl.jena.sdb.Store
import com.hp.hpl.jena.sdb.SDBFactory
import com.hp.hpl.jena.shared.NotFoundException
import com.hp.hpl.jena.vocabulary.VCARD

import java.io.File
import java.net.URL

import scala.Array


object ManipulatorWrapper {

    def initializeDB = {
        val store = SDBFactory.connectStore("sdb.ttl")
        store.getTableFormatter.create
        store.close
    }

    def cleanUpDB = {
        val store = SDBFactory.connectStore("sdb.ttl")
        store.getTableFormatter.truncate
        store.close
    }

    def iterateAndPrintModel(model:Model) = {
        val iter = model.listStatements


        while(iter.hasNext){
            var stmt      = iter.nextStatement  // get next statement
            var subject   = stmt.getSubject     // get the subject
            var predicate = stmt.getPredicate   // get the predicate
            var objct    = stmt.getObject      // get the object

            System.out.print(subject)
            System.out.print(" " + predicate + " ");
            if (objct.isInstanceOf[Resource]) {
                System.out.print(objct);
            } else {
                // object is a literal
                System.out.print(" \"" + objct + "\"");
            }
            System.out.println(" .");
        }
    }

    def loadModelfromDB:Model = {
        try{
            Console.println("current directory is " + System.getProperty("user.dir"))
            //val myConfigFile:URL = ManipulatorWrapper.getClass.getClassLoader().getResource("/sdb.ttl");
            var store = SDBFactory.connectStore("/home/alex/develop/estagio/workspace/wikimodels/wm_server/src/main/sdb.ttl")
            SDBFactory.connectDefaultModel(store)
        } catch {
            case ex:Exception => Console.print(
                    """Strange mistake.
                It resulted in the followning exception:""" +
                    ex.printStackTrace)
                throw ex
        }
    }


    def saveModelToFile(model:OntModel, fileName:String) ={
        try{
            val out = new java.io.FileOutputStream(fileName)
            model.write(out, "RDF/XML-ABBREV")
        } catch {
            case ex:java.io.IOException => Console.print(
                    """Could not write to file.\n\
It resulted in the followning exception:""" + ex.printStackTrace)
            case ex:Exception => Console.print(
                    """Strange mistake.
It resulted in the followning exception:""" + ex.printStackTrace)
        }

    }

    def loadModelfromfile(fileName:String):OntModel = {
        try{
            val in = new java.io.FileInputStream(fileName)
            ModelFactory.createOntologyModel.read(in, null, null)
            .asInstanceOf[OntModel]
        } catch {
            case ex:java.io.IOException =>
                Console.print(
                    """Could not read to file.\n\
                    It resulted in the followning exception:""" +
                    ex.printStackTrace)
                null
            case ex:Exception => Console.print(
                    """Strange mistake.
                    It resulted in the followning exception:""" +
                    ex.printStackTrace);
                null
        }
    }


    def executeSPARQLQuery(queryString:String, model:OntModel):ResultSet = {
        val query:Query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        val qe:QueryExecution = QueryExecutionFactory.create(query, model);
        val results:ResultSet = qe.execSelect();

        // Output query results
        ResultSetFormatter.out(System.out, results, query);

        // Important - free up resources used running the query
        results
    }

    /**
     * Is this really necessary?
     */
    def freeSPARQLQueryResources(qe:QueryExecution) = {
        qe.close();
    }
}
