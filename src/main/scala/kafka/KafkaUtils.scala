package kafka

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

object KafkaUtils {

  val config: Config = ConfigFactory.load()
  
  /**
    * creates a kafka source.
    * @param spark - spark session for application.
    * @param topic - kafka topic from where is data is to be read.
    * @return - DataFrame
    */
  def createSource[T](spark: SparkSession, topic: String, func: DataFrame => Dataset[T]): Dataset[T] = {
    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("group.id", "group1")
      .option("subscribe", topic)
      .load
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
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("topic", topic)
      .start
      .awaitTermination()
  }

}
