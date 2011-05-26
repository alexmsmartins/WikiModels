

import sbt._

class LiftProject(info: ProjectInfo) extends DefaultWebProject(info) {
  val liftVersion = "2.2"

  val lift = "net.liftweb" %% "lift-mapper" % liftVersion % "compile"
  val liftWidget = "net.liftweb" %% "lift-widgets" % liftVersion % "compile"
  val wm_rest_api = "pt.cnbc.wikimodels" % "wm_rest_api" % "0.1-SNAPSHOT" % "compile"
  val jetty6 = "org.mortbay.jetty" % "jetty" % "6.1.25" % "test"
   //val h2 = "com.h2database" % "h2" % "1.2.121" % "runtime"
   // alternately use derby
   // val derby = "org.apache.derby" % "derby" % "10.2.2.0" % "runtime"
   //val servlet = "javax.servlet" % "servlet-api" % "2.5" % "provided"
   //val junit = "junit" % "junit" % "3.8.1" % "test"    

    val mavenLocal = "Local Maven Repository" at "file://"+Path.userHome+"/.m2/repository/"
    val bryanjswift = "Bryan J Swift Repository" at "http://repos.bryanjswift.com/maven2/"
    //val junitInterface = "com.novocode" % "junit-interface" % "0.4.0" % "test"

    //chenges port where jetty listens to
    override val jettyPort = 9999
    //Stop file change detection (While using JRebel)
    override def scanDirectories = Nil
  }


// vim: set ts=4 sw=4 et:
