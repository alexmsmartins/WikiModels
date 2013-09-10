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

import java.io.{File, FileOutputStream, OutputStream, InputStream}
import java.nio.file._
import java.util
import java.nio.file.WatchEvent.{Modifier, Kind}
import java.net.URI

object ManipulatorWrapper {
  protected var jenaModel:Model = null

  //val sdbTtlLocationURL  = getClass.getResource("sdb.ttl")
  //val sdbTtlLocation = sdbTtlLocationURL.getPath

  val sdbTtl = "sdb.ttl"  //FIXME a property file should be able to change this. Implement it.

  //load sdb.ttl from the jar file
  val sdbTtlStream: InputStream = ManipulatorWrapper.getClass.getClassLoader.getResourceAsStream(sdbTtl)
  //save it in the disk
  Files.copy(sdbTtlStream, Paths.get("./sdb_from_jar.ttl"),StandardCopyOption.REPLACE_EXISTING)


  val x =
    ManipulatorWrapper.
    getClass.
    getClassLoader.
    getResource("sdb_from_jar.ttl")

  val sdbTtlLocation: String = "/home/alex/develop/workspace/WikiModels/wm_server/sdb_from_jar.ttl"//x.getPath
  Console.println("sdbTtlLocation = " + sdbTtlLocation)



  def initializeDB = {
    /*Console.println(this.getClass.getResource("/sdb.ttl").getPath)
val sdbModel = ModelFactory.createDefaultModel()
sdbModel.read( this.getClass.getResourceAsStream("/sdb.ttl"),
                            "TTL")
Console.print(sdbModel)
val store = SDBFactory.connectStore( StoreDesc.read(sdbModel) )*/
    val store = SDBFactory.connectStore(sdbTtlLocation)

    store.getTableFormatter.create
    store.close
  }

  def cleanUpDB = {
    val store = SDBFactory.connectStore(sdbTtlLocation)
    store.getTableFormatter.truncate
    store.close
  }

  def iterateAndPrintModel(model: Model):String = {
    var modelContent: String = ""
    val iter = model.listStatements


    while (iter.hasNext) {
      val stmt = iter.nextStatement // get next statement
      val subject = stmt.getSubject // get the subject
      val predicate = stmt.getPredicate // get the predicate
      val objct = stmt.getObject // get the object

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
        //val myConfigFile = ManipulatorWrapper.getClass.getClassLoader().getResource(sdbTtl).getPath
        /*val myConfigFileStream = ManipulatorWrapper.getClass.getClassLoader().getResourceAsStream("sdb.ttl")
   val outputStream = new FileOutputStream("sdb.ttl")
   var array = Array[Byte]()
   val nBytes = myConfigFileStream.read
   outputStream.write(array, 0, nBytes)
   //Console.println("current URL is " + myConfigFile)

   val store = SDBFactory.connectStore(System.getProperty("user.dir") + "/sdb.ttl")*/
        val store = SDBFactory.connectStore(sdbTtlLocation )
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
