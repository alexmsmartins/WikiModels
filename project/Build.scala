import sbt._
import Keys._

object HelloBuild extends Build {

    val sampleKeyA = settingKey[String]("demo key A")
    val sampleKeyB = settingKey[String]("demo key B")
    val sampleKeyC = settingKey[String]("demo key C")
    val sampleKeyD = settingKey[String]("demo key D")

    override lazy val settings = super.settings ++
        Seq(sampleKeyA := "A: in Build.settings in Build.scala", resolvers := Seq())

    lazy val root = Project(id = "hello",
                            base = file("."),
                            settings = Project.defaultSettings ++ Seq(sampleKeyB := "B: in the root project settings in Build.scala"))
}
