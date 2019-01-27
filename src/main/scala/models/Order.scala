package models

import kafka.KafkaUtils._
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, SparkSession}
import schema.SchemaUtils.orderSchema

/**
  * Structure of Order Details
  *
  * @param orderId - order id of order
  * @param product - product with it's count
  * @param outletId - outlet id of outlet to which the order belongs
  */
case class Order(orderId: String, product: List[(Product, Int)], outletId: String)

object Order {
  def getOrderDS(sparkSession: SparkSession, topic: String)(implicit orderEncoder: Encoder[Order]): Dataset[Order] = {
    createSource[Order](sparkSession, topic, (df: DataFrame) => df.as[Order])
  }
}
