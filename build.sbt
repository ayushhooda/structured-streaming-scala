name := "structured-streaming"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.4.0",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % "2.4.0",
  "org.apache.spark" %% "spark-sql" % "2.4.0",
  "com.typesafe" % "config" % "1.3.3"
)
