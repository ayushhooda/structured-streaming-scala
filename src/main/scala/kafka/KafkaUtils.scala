package kafka

import java.sql.Timestamp

import com.typesafe.config.{Config, ConfigFactory}
import models.Temperature
import org.apache.spark.sql.types.{StructType, TimestampType}
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, SparkSession}
import org.apache.spark.sql.functions._

object KafkaUtils {

  val config: Config = ConfigFactory.load()

  /**
    * creates a kafka source.
    * @param spark - spark session for application.
    * @param topic - kafka topic from where is data is to be read.
    * @param schema - schema for the Json to be read from Kafka.
    * @param func - higher order function to convert DataFrame to Dataset of type T.
    * @return - Dataset of type T
    */
  def createSource[T](spark: SparkSession, topic: String, func: DataFrame => Dataset[T])(implicit schema: StructType): Dataset[T] = {
    import spark.implicits._
    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", config.getString("kafka.server"))
      .option("group.id", config.getString("kafka.group.id"))
      .option("startingOffsets", "earliest")
      .option("maxOffsetsPerTrigger", 1)
      .option("subscribe", topic)
      .load()
      .selectExpr("CAST(value AS STRING)")
      .as[String]
      .select(from_json($"value", schema).as("data"))
      .select("data.*")
    func(df)
  }

  def createSourceForAggregation(spark: SparkSession, topic: String)
                                   (implicit schema: StructType) = {
    import spark.implicits._
    spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", config.getString("kafka.server"))
      .option("group.id", config.getString("kafka.group.id"))
      .option("startingOffsets", "earliest")
      .option("maxOffsetsPerTrigger", 5)
      .option("subscribe", topic)
      .option("includeTimestamp", true)
      .load()
      .select(from_json(col("value").cast("STRING"), schema).as("data"),
        col("timestamp").cast("TIMESTAMP").as("timestamp"))
      //.select(col("data"), col("timestamp"))
      //.as[(Temperature, Long)]
      .select(col("data.*"), col("timestamp"))
      //.as[(String, Double, Timestamp)].map {
        //case (place, fahrenheit, timestamp) => (Temperature(place, fahrenheit), timestamp)
      //}
  }

  /**
    * creates a kafka sink
    * @param ds - Dataset
    * @param topic - kafka topic where data is to be written
    * @tparam T - structure of data that is to be written
    */
  def createSink[T](spark: SparkSession, ds: Dataset[T], topic: String): Unit = {
    import spark.implicits._
    ds.map(_.toString.getBytes).toDF("value")
      .writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", config.getString("kafka.server"))
      .option("topic", topic)
      .start
      .awaitTermination()
  }

}
