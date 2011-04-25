

import sbt._

class FooProject(info: ProjectInfo) extends DefaultWebProject(info) {

   val lift = "net.liftweb" %% "lift-mapper" % "2.2" % "compile"
   val jetty6 = "org.mortbay.jetty" % "jetty" % "6.1.25" % "test"
   val h2 = "com.h2database" % "h2" % "1.2.121" % "runtime"
   // alternately use derby
   //val derby = "org.apache.derby" % "derby" % "10.2.2.0" % "runtime"
   val servlet = "javax.servlet" % "servlet-api" % "2.5" % "provided"
   //val junit = "junit" % "junit" % "3.8.1" % "test"    

    val mavenLocal = "Local Maven Repository" at "file://"+Path.userHome+"/.m2/repository/"
    val bryanjswift = "Bryan J Swift Repository" at "http://repos.bryanjswift.com/maven2/"
    //val junitInterface = "com.novocode" % "junit-interface" % "0.4.0" % "test"
  }


// vim: set ts=4 sw=4 et:
