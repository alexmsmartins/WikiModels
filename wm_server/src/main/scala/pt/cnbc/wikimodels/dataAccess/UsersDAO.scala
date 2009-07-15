/*
 * DataHandler.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * @author Alexandre Martins
 */

package pt.cnbc.wikimodels.dataAccess


import pt.cnbc.wikimodels.dataModel.User
import pt.cnbc.wikimodels.ontology.ManipulatorWrapper

import com.hp.hpl.jena.rdf.model.Model

import thewebsemantic.Bean2RDF
import thewebsemantic.RDF2Bean

import scala.Collection


/**
 * Class responsible for the access to Jena
 */
object UsersDAO {

    implicit def toNiceObject[T <: AnyRef](x : T) = new NiceObject(x)
    
    def saveUser(u:User) = {
        try{
            val lUsers = loadUsers().filter(i => i.userName == u.userName)
            if(lUsers.size == 1) {
                val myModel:Model = ManipulatorWrapper.loadModelfromDB
                var writer = new Bean2RDF(myModel)
                writer.save(u)
            } else if(lUsers.size == 0) null else
            throw new Exception("More than one user as the same user name " +
                                "in the KnowledgeBase")
        } catch {
            case ex:thewebsemantic.NotFoundException =>
                Console.println("Bean of " + User.getClass + "and " +
                                "id is not found")
                ex.printStackTrace()
            null
            case ex =>
                Console.println(ex.toString)
                ex.printStackTrace()
            null
        }
    }

    def loadUser(userName:String):User = {
        try{
            val lUsers = loadUsers().filter(i => i.userName == userName)
            if(lUsers.size == 1) lUsers.first
            else if(lUsers.size == 0) null else
            throw new Exception("More than one user as the same user name " +
                                "in the KnowledgeBase")
        } catch {
            case ex:thewebsemantic.NotFoundException =>
                Console.println("Bean of " + User.getClass + "and " +
                                "id is not found")
                ex.printStackTrace()
            null
            case ex =>
                Console.println(ex.toString)
                ex.printStackTrace()
            null
        }
    }
    
    def loadUsers():List[User] = {
        try{
            val myModel:Model = ManipulatorWrapper.loadModelfromDB
            var reader = new RDF2Bean(myModel);
            reader.load(User.getClass)
                  .asInstanceOf[List[User]]
        } catch {
            case ex:thewebsemantic.NotFoundException =>
                Console.println("Bean of " + User.getClass + "and id is not found")
                ex.printStackTrace()
            null
        }
    }
}

class NiceObject[T <: AnyRef](x : T) {
  def niceClass : Class[_ <: T] = x.getClass.asInstanceOf[Class[T]]
}
