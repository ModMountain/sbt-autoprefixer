sbtPlugin := true

organization := "com.modmountain.sbt"

name := "sbt-autoprefixer"

version := "0.1.0"

scalaVersion := "2.10.4"

resolvers ++= Seq(
  Resolver.mavenLocal,
  "Typesafe Releases Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.0.0")

publishMavenStyle := true
publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}
publishArtifact in Test := false
pomIncludeRepository := { _ => false }
pomExtra := <url>https://github.com/ModMountain/sbt-autoprefixer</url>
  <scm>
    <url>git@github.com:ModMountain/sbt-autoprefixer.git</url>
    <connection>scm:git:git@github.com:ModMountain/sbt-autoprefixer.git</connection>
  </scm>
  <developers>
    <developer>
      <id>sirsavary</id>
      <name>Jesse Savary</name>
      <url>http://www.jessesavary.com</url>
    </developer>
  </developers>