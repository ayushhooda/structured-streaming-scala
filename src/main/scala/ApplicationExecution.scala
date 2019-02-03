import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.{Dataset, SparkSession}
import models.{Order, Outlet, Product, Temperature}
import operations.StreamingOperations

object ApplicationExecution extends App {

  val config = ConfigFactory.load()

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("structured-streaming-demo")
    .getOrCreate()
  sparkSession.conf.set("spark.sql.streaming.checkpointLocation", "fake-hdfs")

  // For implicit conversions like converting RDDs to DataFrames
  import sparkSession.implicits._

  // Creating kafka source for temperature data
  val temperatureDS: Dataset[Temperature] = Temperature.getTemperatureDS(sparkSession, config.getString("kafka.temperatureTopic"))

  val filteredTemperature = StreamingOperations.filterTemp(temperatureDS)

  val allPlaces = StreamingOperations.listAllPlaces(temperatureDS)

  val aggregateTemperatureForPlaces = StreamingOperations.averageTemperature(temperatureDS)

  // Creating kafka source for order data
  val orderDS: Dataset[Order] = Order.getOrderDS(sparkSession, "kafka.orderTopic")

  // Creating kafka source for outlet data
  val outletDS: Dataset[Outlet] = Outlet.getOutletDS(sparkSession, "kafka.outletTopic")

  // Creating kafka source for product data
  val productDS: Dataset[Product] = Product.getProductDS(sparkSession, "kafka.productTopic")

  allPlaces.writeStream.outputMode("append")
    .format("console").start().awaitTermination()

}
