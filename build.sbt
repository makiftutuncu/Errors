import sbt.Keys._

lazy val errorsProject = Project("errors", file(".")).settings(
  version := "1.0",

  scalaVersion := "2.11.7",

  // Update shell prompt of this module to get rid of ugly ">"
  shellPrompt := {
    state: State => "[" + scala.Console.CYAN + name.value + scala.Console.RESET + "] $ "
  },

  libraryDependencies ++= Seq(
    "org.specs2" %% "specs2-core" % "3.7" % Test
  ),

  scalacOptions in Test ++= Seq("-Yrangepos")
)

lazy val sampleProject = Project("sample", file("sample")).settings(
  version := "1.0",
  scalaVersion := "2.11.7"
).dependsOn(errorsProject)
