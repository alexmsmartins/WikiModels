/*
 * DataHandler.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataAccess

import com.hp.hpl.jena.rdf.model.Model
import com.hp.hpl.jena.rdf.model.ModelFactory

import pt.cnbc.wikimodels.dataModel.User
import pt.cnbc.wikimodels.ontology.ManipulatorWrapper

import scala.collection.mutable.LinkedList

import thewebsemantic.Bean2RDF
import thewebsemantic.RDF2Bean
import org.slf4j.LoggerFactory

/**
 * Class responsible for the access to Jena
 */
object UsersDAO {
  val logger = LoggerFactory.getLogger(getClass)

    def saveUser(u:User) = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            var writer = new Bean2RDF(myModel)
            writer.save(u)
        } catch {
            case ex:thewebsemantic.NotFoundException =>
                logger.debug("Bean of " + User.getClass + "and " +
                                "id is not found")
                ex.printStackTrace()
                null
            case ex =>
                logger.debug(ex.toString)
                ex.printStackTrace()
                null
        }
    }

    def loadUser(userName:String):User = {
        var ret:User = null
        saveUser(User("xxxx", "xxxxp", "Alexxx", "Martinxx", "alex@xxx.com"))
        try{
            var myModel:Model = ManipulatorWrapper.loadModelfromDB
            logger.debug("Number of individuals in loaded model is " + myModel.size)

            logger.debug("After loading Jena Model")
            var reader = new RDF2Bean(myModel)
            logger.debug("After creating a new RDF2Bean")
            val l:java.util.Collection[User]
                = reader.load( new User().getClass)
                    .asInstanceOf[java.util.Collection[User]]
            logger.debug("Found " + l.size + " users with username " + userName)
            val iter = l.iterator
            var next:User=null
            for(i <- 0 until l.size-1){
                next = iter.next
                if(next.userName == userName)
                    ret = next
            }
            return ret
        } catch {
            case ex:thewebsemantic.NotFoundException =>
                logger.debug("Bean of " + User.getClass + "and " +
                                "id is not found")
                ex.printStackTrace()
                null
            case ex =>
                ex.printStackTrace()
                null
        }
    }

    def loadUsers():java.util.Collection[User] = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            logger.debug("After loading Jena Model")
            var reader = new RDF2Bean(myModel)
            logger.debug("After creating a new RDF2Bean")
            val l:java.util.List[User] = reader.load(new User().getClass )
                .asInstanceOf[java.util.List[User]]
            //logger.debug("User XML = " + c.toList(0).toXML.toString)

            l
            /*var l:List[User] = Nil
            (for(i <- 0 to lusers.size - 1) yield  lusers(i).asInstanceOf[User])
                .toList*/
        } catch {
            case ex:thewebsemantic.NotFoundException =>
                logger.debug("Bean of " + User.getClass + "and id is not found")
                ex.printStackTrace()
                null
        }
    }
}
