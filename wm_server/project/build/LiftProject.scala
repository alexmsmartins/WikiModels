import sbt._

class LiftProject(info: ProjectInfo) extends DefaultWebProject(info) {

    // Add Maven Local repository for SBT to search for (disable if this doesn't suit you)
      val mavenLocal = "Local Maven Repository" at "file://"+Path.userHome+"/.m2/repository"

      val bryanjswift = "Bryan J Swift Repository" at "http://repos.bryanjswift.com/maven2/"
      val junitInterface = "com.novocode" % "junit-interface" % "0.4.0" % "test"
    }
// vim: set ts=4 sw=4 et:
