
import com.typesafe.sbt.packager.docker.Cmd

ThisBuild / organization := "com.github.vkorchik"
ThisBuild / scalaVersion := "2.12.6"

ThisBuild / version := "0.1"

lazy val commonSettings = {
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http" % "10.1.1",
    "com.typesafe.akka" %% "akka-actor" % "2.5.12",
    "com.typesafe.akka" %% "akka-stream" % "2.5.12",
    "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    "com.typesafe.akka" %% "akka-http-testkit" % "10.1.1" % Test

  )
}

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging, DockerPlugin)
  .settings(commonSettings)
  .settings(
    name := "docker-int"
  )
  .settings(
    Docker / version := version.value,
    Docker / maintainer := "vkorchik",

    dockerUpdateLatest := true,
    dockerEntrypoint := Seq("bin/docker-entrypoint.sh"),

    Universal / mappings += (Compile / resourceDirectory).value / "docker-entrypoint.sh" -> "bin/docker-entrypoint.sh",
    Universal / javaOptions ++= {
      Seq(s"-Dconfig.resource=application.conf")
    },
    dockerExposedPorts := Seq(8182),
    dockerCommands := Seq(
      Cmd("FROM", "openjdk:8u121-jdk")
    ) ++ dockerCommands.value.filter {
      case Cmd(cmd, _) => cmd != "FROM"
      case _ => true
    }
  )

lazy val int = (project in file("int"))
  .settings(commonSettings)
  .settings(libraryDependencies ++= Seq(
    "com.whisk" %% "docker-testkit-scalatest" % "0.9.5",
    "com.whisk" %% "docker-testkit-impl-spotify" % "0.9.5")
  )
