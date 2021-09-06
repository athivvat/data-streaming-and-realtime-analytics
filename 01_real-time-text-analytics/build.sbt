name := "real-time-text-analytics"

version := "0.1"

scalaVersion := "2.13.6"

lazy val wordCount = (project in file("."))
  .settings(
    name := "WordCount",
    libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.8.0"
  )