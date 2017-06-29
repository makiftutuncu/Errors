import sbt.Keys._

name         := "errors"
organization := "com.github.mehmetakiftutuncu"
version      := "1.2"
description  := "Errors is an easy-to-use library written in Scala for providing immutable, lightweight, extensible way to represent errors in your project."
homepage     := Option(url("https://github.com/mehmetakiftutuncu/Errors"))
startYear    := Option(2016)
licenses     := Seq("MIT" -> url("https://opensource.org/licenses/MIT"))
scmInfo      := Option(ScmInfo(url("https://github.com/mehmetakiftutuncu/Errors"), "https://github.com/mehmetakiftutuncu/Errors.git"))
developers   := List(Developer("makiftutuncu", "Mehmet Akif Tütüncü", "m.akif.tutuncu@gmail.com", url("https://makiftutuncu.wordpress.com")))

scalaVersion := "2.12.2"

crossScalaVersions := Seq("2.11.11", "2.12.2")

// Update shell prompt of this module to get rid of ugly ">"
shellPrompt := {
  state: State => "[" + scala.Console.CYAN + name.value + scala.Console.RESET + "] $ "
}

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "3.9.1" % Test
)

scalacOptions in Test ++= Seq("-Yrangepos")

publishMavenStyle := true

exportJars := true

useGpg := true

publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)

publishArtifact in Test := false

pomIncludeRepository := { _ => false }
