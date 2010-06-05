package pt.cnbc.wikimodels.setup


import org.apache.log4j.Logger
import com.hp.hpl.jena.rdf.model.ModelFactory
import com.hp.hpl.jena.rdf.model.Model
import com.hp.hpl.jena.sdb.StoreDesc
import com.hp.hpl.jena.rdf.model.Resource
import com.hp.hpl.jena.vocabulary.VCARD
import com.hp.hpl.jena.sdb.store.LayoutType
import com.hp.hpl.jena.sdb.store.DatabaseType
import com.hp.hpl.jena.sdb.sql.JDBC
import com.hp.hpl.jena.sdb.sql.SDBConnection
import com.hp.hpl.jena.sdb.Store
import com.hp.hpl.jena.sdb.SDBFactory
import com.hp.hpl.jena.shared.NotFoundException
import com.hp.hpl.jena.graph.GraphEvents

import java.io.File
import scala.Array

import pt.cnbc.wikimodels.ontology.ManipulatorWrapper

/**
 * Hello world!
 *
 */
object Setup {
  ///Setup ontologies
  def saveOntologiesOnKB = {
    val modelFromKB = ManipulatorWrapper.loadModelfromDB
    try {
      saveOntologiesOn(modelFromKB)
    } catch {
      case e: NullPointerException => {
        Console.println("""WikiModels setup failed:
Error accessing ontologies directory.""" + e)
        System.exit(1)
      }
      case e => {
        Console.println("""WikiModels setup failed:
Unexpected Error.""" + e)
      }
    }
  }

  def saveOntologiesOn(model:Model): Model = {
    var homeDir = ""
    var file: File = null
    //Get the current directory
    file = new File(System.getProperty("user.dir"))
    file.listFiles()
            .filter(i => i.isFile && !i.isHidden) //select non hidden files
            .map(i => i.getName) //get the names of files
            .filter(i => i.matches("""\S+(.rdf|.owl)""")) //filter the ones which end with .rdf and owl
            .map(i => { Console.println("Loading file " + i + "to KB")
                        model.add(
                          ManipulatorWrapper.loadModelfromfile(i))
              }) //Merge Ontologies with KnowledgeBase
    if(model==null)
      throw new Exception("saveOntologiesOn - Model is null")

    model
  }

  /**
   * Setup user's passwords
   */
  def savePasswordsOnKB = {
    ""
  }

  def main(args: Array[String]): Unit = {
    ManipulatorWrapper.initializeDB
    saveOntologiesOnKB
    savePasswordsOnKB
  }
}
