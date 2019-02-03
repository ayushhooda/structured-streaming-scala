package models

import kafka.KafkaUtils._
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, SparkSession}
import schema.SchemaUtils.outletSchema

/**
  * Structure of the Outlet
  * @param id - outlet id
  * @param name - outlet name
  * @param place - outlet location
  * @param menu - list of items it contains
  */
case class Outlet(id: String, name: String, place: String, menu: List[Product])

object Outlet {

  /**
    * Creates Dataset of type Outlet
    * @param sparkSession - Spark Session
    * @param topic - Kafka topic
    * @param outletEncoder - Encoder of type Outlet
    * @return - Dataset of type Outlet
    */
  def getOutletDS(sparkSession: SparkSession, topic: String)(implicit outletEncoder: Encoder[Outlet]): Dataset[Outlet] = {
    createSource[Outlet](sparkSession, topic, (df: DataFrame) => df.as[Outlet])
  }
}
