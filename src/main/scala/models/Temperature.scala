package models

import kafka.KafkaUtils._
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, SparkSession}
import schema.SchemaUtils.temperatureSchema
/**
  * Structure of Temperature
  * @param place - location to which the temperature belongs
  * @param fahrenheit - measurement unit
  */
case class Temperature(place: String, fahrenheit: Float)

object Temperature {
  def getTemperatureDS(sparkSession: SparkSession, topic: String)(implicit temperatureEncoder: Encoder[Temperature]): Dataset[Temperature] = {
    createSource[Temperature](sparkSession, topic, (df: DataFrame) => df.as[Temperature])
  }
}
