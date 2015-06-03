name := "MilkaRecognizer"
 
version := "1.0"
 
scalaVersion := "2.11.6"
 
resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
EclipseKeys.withSource := true

libraryDependencies += "com.typesafe.scala-logging" % "scala-logging_2.11" % "3.1.0"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3"
libraryDependencies += "com.typesafe" % "config" % "1.3.0"
libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4"
libraryDependencies := {
  CrossVersion.partialVersion(scalaVersion.value) match {
    // if scala 2.11+ is used, add dependency on scala-xml module
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
      libraryDependencies.value ++ Seq(
        "org.scala-lang.modules" %% "scala-xml" % "1.0.3",
        "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3",
        "org.scala-lang.modules" %% "scala-swing" % "1.0.1")
    case _ =>
      // or just libraryDependencies.value if you don't depend on scala-swing
      libraryDependencies.value :+ "org.scala-lang" % "scala-swing" % scalaVersion.value
  }
}
libraryDependencies += "com.typesafe.scala-logging" % "scala-logging_2.11" % "3.1.0"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3"