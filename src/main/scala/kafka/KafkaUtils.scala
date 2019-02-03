package kafka

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.sql.functions.from_json

object KafkaUtils {

  val config: Config = ConfigFactory.load()

  /**
    * creates a kafka source.
    * @param spark - spark session for application.
    * @param topic - kafka topic from where is data is to be read.
    * @param schema - schema for the Json to be read from Kafka.
    * @param func - higher order function to convert DataFrame to Dataset of type T.
    * @return - DataFrame
    */
  def createSource[T](spark: SparkSession, topic: String, func: DataFrame => Dataset[T])(implicit schema: StructType): Dataset[T] = {
    import spark.implicits._
    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", config.getString("kafka.server"))
      .option("group.id", config.getString("kafka.group.id"))
      .option("auto.offset.reset", "earliest")
      .option("subscribe", topic)
      .load()
      .selectExpr("CAST(value AS STRING)")
      .as[String]
      .select(from_json($"value", schema).as("data"))
      .select("data.*")
    func(df)
  }

  /**
    * creates a kafka sink
    * @param ds - Dataset
    * @param topic - kafka topic where data is to be written
    * @tparam T - structure of data that is to be written
    */
  def createSink[T](ds: Dataset[T], topic: String): Unit = {
    ds.toDF
      .writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", config.getString("kafka.server"))
      .option("topic", topic)
      .start
      .awaitTermination()
  }

}
