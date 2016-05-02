name := """Cheickode"""

version := "1.0-SNAPSHOT"

lazy val core = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
 javaWs,
 javaJdbc,
 "mysql" % "mysql-connector-java" % "5.1.35"
)
sbtPlugin := true
// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
