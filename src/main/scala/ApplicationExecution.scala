import org.apache.spark.sql.{Dataset, SparkSession}
import models.{Order, Outlet, Temperature, Product}

object ApplicationExecution extends App {

  // Entry Point to all functionality in spark
  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("structured-streaming-demo")
    .getOrCreate()

  // For implicit conversions like converting RDDs to DataFrames
  import sparkSession.implicits._

  // Creating kafka source for temperature data
  val temperatureDS: Dataset[Temperature] = Temperature.getTemperatureDS(sparkSession, "")

  // Creating kafka source for order data
  val orderDS: Dataset[Order] = Order.getOrderDS(sparkSession, "")

  // Creating kafka source for outlet data
  val outletDS: Dataset[Outlet] = Outlet.getOutletDS(sparkSession, "")

  // Creating kafka source for product data
  val productDS: Dataset[Product] = Product.getProductDS(sparkSession, "")


}
