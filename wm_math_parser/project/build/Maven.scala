import sbt._

class MathMLParserFooProject(info: ProjectInfo) extends DefaultProject(info) {

  val mavenLocal = "Local Maven Repository" at
  "file://"+Path.userHome+"/.m2/repository"

  val bryanjswift = "Bryan J Swift Repository" at "http://repos.bryanjswift.com/maven2/"
  val junitInterface = "com.novocode" % "junit-interface" % "0.4.0" % "test"
}
// vim: set ts=4 sw=4 et:
