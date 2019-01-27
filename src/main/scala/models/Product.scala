package models

import kafka.KafkaUtils.createSource
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, SparkSession}
import schema.SchemaUtils.productSchema

/**
  * Structure of Product
  * @param id - product id
  * @param name - product name
  * @param price - product price
  */
case class Product(id: String, name: String, price: Float)

object Product {
  def getProductDS(sparkSession: SparkSession, topic: String)(implicit productEncoderEncoder: Encoder[Product]): Dataset[Product] = {
    createSource[Product](sparkSession, topic, (df: DataFrame) => df.as[Product])
  }
}
