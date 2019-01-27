package schema

import models.{Order, Outlet, Product, Temperature}
import org.apache.spark.sql.Encoders
import org.apache.spark.sql.types.StructType

object SchemaUtils {

  implicit val temperatureSchema: StructType = Encoders.product[Temperature].schema
  implicit val productSchema: StructType = Encoders.product[Product].schema
  implicit val outletSchema: StructType = Encoders.product[Outlet].schema
  implicit val orderSchema: StructType = Encoders.product[Order].schema

}
