name := "wm_web_client"

version := "0.1"

scalaVersion := "2.10.2"

//xsbt-web-plugin configuration
seq(webSettings: _*)

//JRebel configuration
seq(jrebelSettings: _*)

//using xsbt-web-plugin and want to reload web resources
jrebel.webLinks <++= webappResources in Compile

// If using JRebel with 0.1.0 of the sbt web plugin
//jettyScanDirs := Nil
// using 0.2.4+ of the sbt web plugin
//scanDirectories in Compile := Nil

//resolvers += "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"

// you can also add multiple repositories at the same time
resolvers ++= Seq(
  "Scala Tools Releases" at "http://scala-tools.org/repo-releases/",
  "Java.net Maven2 Repository" at "http://download.java.net/maven/2/",
  "Local Maven Repository" at "file://"+(Path.userHome /  ".m2" / "repository").absolutePath
)

// if you have issues pulling dependencies from the scala-tools repositories (checksums don't match), you can disable checksums
//checksums := Nil

libraryDependencies ++= {
  val liftVersion = "2.5" // Put the current/latest lift version here
  Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-mapper" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-wizard" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-record" % liftVersion % "compile->default",
    "net.liftmodules" %% "widgets_2.5" % "1.3" % "compile->default",
    "net.liftmodules" %% "fobo_2.5" % "1.0" % "compile->default"
  )
}

// when using the sbt web app plugin 0.2.4+, use "container" instead of "jetty" for the context
// Customize any further dependencies as desired
libraryDependencies ++= Seq(
  "org.eclipse.jetty" % "jetty-webapp" % "8.0.4.v20111024" % "container", // For Jetty 8
  //"org.eclipse.jetty" % "jetty-webapp" % "7.3.0.v20110203" % "container", // For Jetty 7
  //"org.mortbay.jetty" % "jetty" % "6.1.22" % "jetty,test", // For Jetty 6, add scope test to make jetty avl. for tests
  "org.scala-tools.testing" % "specs_2.9.0" % "1.6.8" % "test", // For specs.org tests
  "junit" % "junit" % "4.8" % "test->default", // For JUnit 4 testing
  "javax.servlet" % "servlet-api" % "2.5" % "provided->default",
  "com.h2database" % "h2" % "1.2.138", // In-process database, useful for development systems
  "ch.qos.logback" % "logback-classic" % "0.9.26" % "compile->default" // Logging
)

libraryDependencies ++= Seq(
  "UsefullScalaStuff" % "UsefullScalaStuff" % "0.1",
  "pt.cnbc.wikimodels" % "wm_libjsbml" % "0.1-SNAPSHOT",
  "pt.cnbc.wikimodels" % "wm_rest_api" % "0.1-SNAPSHOT",
  "pt.cnbc.wikimodels" % "wm_math_parser" % "0.1-SNAPSHOT"
)

// by default, it listens on port 8080; use the following to override
port in container.Configuration := 9999
