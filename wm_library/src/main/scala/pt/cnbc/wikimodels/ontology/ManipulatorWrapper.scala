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

import java.io.{OutputStream, InputStream}

object ManipulatorWrapper {
  protected var jenaModel:Model = null

  def initializeDB = {
    /*Console.println(this.getClass.getResource("/sdb.ttl").getPath)
val sdbModel = ModelFactory.createDefaultModel()
sdbModel.read( this.getClass.getResourceAsStream("/sdb.ttl"),
                            "TTL")
Console.print(sdbModel)
val store = SDBFactory.connectStore( StoreDesc.read(sdbModel) )*/
    val store = SDBFactory.connectStore("/home/alex/develop/estagio/workspace/wikimodels/wm_setup/sdb.ttl")

    store.getTableFormatter.create
    store.close
  }

  def cleanUpDB = {
    val store = SDBFactory.connectStore("/home/alex/develop/estagio/workspace/wikimodels/wm_setup/sdb.ttl")
    store.getTableFormatter.truncate
    store.close
  }

  def iterateAndPrintModel(model: Model):String = {
    var modelContent: String = ""
    val iter = model.listStatements


    while (iter.hasNext) {
      var stmt = iter.nextStatement // get next statement
      var subject = stmt.getSubject // get the subject
      var predicate = stmt.getPredicate // get the predicate
      var objct = stmt.getObject // get the object

      Console.print(subject)
      modelContent += subject
      Console.print(" " + predicate + " ");
      modelContent += " " + predicate + " "

      if (objct.isInstanceOf[Resource]) {
        System.out.print(objct);
        modelContent += objct
      } else {
        // object is a literal
        System.out.print(" \"" + objct + "\"");
        modelContent += " \"" + objct + "\""
      }
      System.out.println(" .");
      modelContent += " ."
    }
    modelContent
  }

  def loadModelfromDB: Model = {
    if (jenaModel != null && jenaModel.supportsTransactions == true) jenaModel
    else
      try {
        Console.println("current directory is " + System.getProperty("user.dir"))

        //gets the content of the file from the wm_library jar
        val myConfigFile = ManipulatorWrapper.getClass.getClassLoader().getResource("sdb.ttl")
        /*val myConfigFileStream = ManipulatorWrapper.getClass.getClassLoader().getResourceAsStream("sdb.ttl")
   val outputstream = new FileOutputStream("sdb.ttl")
   var array = Array[Byte]()
   val nBytes = myConfigFileStream.read
   outputstream.write(array, 0, nBytes)
   //Console.println("current URL is " + myConfigFile)

   val store = SDBFactory.connectStore(System.getProperty("user.dir") + "/sdb.ttl")*/
        val store = SDBFactory.connectStore("/home/alex/develop/estagio/workspace/wikimodels/wm_setup/sdb.ttl")
        jenaModel = SDBFactory.connectDefaultModel(store)
        jenaModel
      } catch {
        case ex: Exception => Console.print(
          """Strange mistake.
     It resulted in the followning exception:""" +
                  ex.printStackTrace)
        throw ex
      }
  }

  def saveModelToFile(model: OntModel, fileName: String) = {
    try {
      saveModelToOutputStream(model, new java.io.FileOutputStream(fileName))
    } catch {
      case ex: java.io.IOException => Console.print(
        """Could not write to file.\n\
   It resulted in the followning exception:""" + ex.printStackTrace)
      case ex: Exception => Console.print(
        """Strange mistake.
   It resulted in the followning exception:""" + ex.printStackTrace)
    }
  }

  def saveModelToOutputStream(model: OntModel, out: OutputStream) = {
    model.write(out, "RDF/XML-ABBREV")
  }


  def loadModelfromfile(fileName: String): OntModel = {
    try {
      loadModelFromInputStream(new java.io.FileInputStream(fileName))
    } catch {
      case ex: java.io.IOException =>
        Console.print(
          """Could not read to file.\n\
     It resulted in the followning exception:""" +
                  ex.printStackTrace)
        throw ex
      case ex: Exception => Console.print(
        """Strange mistake.
   It resulted in the followning exception:""" +
                ex.printStackTrace);
      throw ex
    }
  }

  def loadModelFromInputStream(in: InputStream): OntModel = {
    ModelFactory.createOntologyModel.read(in, null, null)
            .asInstanceOf[OntModel]
  }


  def executeSPARQLQuery(queryString: String, model: OntModel): ResultSet = {
    val query: Query = QueryFactory.create(queryString);

    // Execute the query and obtain results
    val qe: QueryExecution = QueryExecutionFactory.create(query, model);
    val results: ResultSet = qe.execSelect();

    // Output query results
    ResultSetFormatter.out(System.out, results, query);

    // Important - free up resources used running the query
    results
  }

  /**
   * Is this really necessary?
   */
  def freeSPARQLQueryResources(qe: QueryExecution) = {
    qe.close();
  }
}
