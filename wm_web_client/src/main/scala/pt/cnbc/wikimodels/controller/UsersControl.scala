package pt.cnbc.wikimodels.controller

import scala.collection.mutable.HashSet
import scala.actors.Actor
import scala.actors.Actor._
import net.liftweb.http._
import net.liftweb.sitemap.Loc.If

import net.liftweb.util.Helpers._
import pt.cnbc.wikimodels.snippet._

// Messages
case class UserLogin(login: String, password: String)

// Data Structures
case class UserEntry(login: String, password: String, var score: Int)

object UsersControl extends Actor {
    val listeners = new HashSet[Actor]

    def notifyListeners (log: String, pass: String) = {
        println("Recebi o login: "+log+"; e a password: "+pass)
    }

    def act = {
        loop {
            react {
                case UserLogin(login: String, password: String) => UserEntry(login, password, 1)
                    notifyListeners(login, password)
            }
        }
    }

    start  // This starts our singleton Actor
}
