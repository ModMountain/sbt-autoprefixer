sbtPlugin := true

organization := "com.modmountain.sbt"

name := "sbt-autoprefixer"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.10.4"

resolvers ++= Seq(
  Resolver.mavenLocal,
  "Typesafe Releases Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.0.0")