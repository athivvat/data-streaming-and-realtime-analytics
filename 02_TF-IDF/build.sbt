name := "02_TF-IDF"

version := "0.1"

scalaVersion := "2.12.2"

lazy val wordCount = (project in file("."))
  .settings(
    name := "IF-IDF",
    libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.8.0",
    libraryDependencies += "org.apache.spark" %% "spark-core" % "3.1.2"
  )