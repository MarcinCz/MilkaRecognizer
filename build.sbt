name := "MilkaRecognizer"
 
version := "1.0"
 
scalaVersion := "2.11.6"
 
resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
EclipseKeys.withSource := true

libraryDependencies += "com.typesafe.scala-logging" % "scala-logging_2.11" % "3.1.0"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3"
libraryDependencies += "com.typesafe" % "config" % "1.3.0"
libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4"