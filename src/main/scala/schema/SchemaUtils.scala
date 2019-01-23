package schema

import models.Temperature
import org.apache.spark.sql.Encoders
import org.apache.spark.sql.types.StructType

object SchemaUtils {

  val temperatureSchema: StructType = Encoders.product[Temperature].schema
}
