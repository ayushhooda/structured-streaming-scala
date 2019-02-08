import com.typesafe.config.ConfigFactory
import kafka.KafkaUtils
import org.apache.spark.sql.{Dataset, SparkSession}
import models.{Order, Outlet, Product, Temperature}
import operations.StreamingOperations
import org.apache.spark.sql.expressions.scalalang.typed
import schema.SchemaUtils.temperatureSchema
import org.apache.spark.sql.functions._

object ApplicationExecution extends App {

  val config = ConfigFactory.load()

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("structured-streaming-demo")
    .getOrCreate()
  sparkSession.sparkContext.setLogLevel("WARN")
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

  // Indicates if dataset/dataframe is streaming or not
  println("------------------" + allPlaces.isStreaming)


  // Projecttion Query
  //allPlaces.writeStream.format("console").option("numRows", 50).start().awaitTermination()


  // Selection Query
  //Temperature.saveTemperature(sparkSession, filteredTemperature, config.getString("kafka.filteredTemperatureTopic"))



  val agg = KafkaUtils.createSourceForAggregation(sparkSession, config.getString("kafka.temperatureTopic"))

  //agg.writeStream.format("console").outputMode("append").start().awaitTermination()
  agg.groupBy(window(col("timestamp"), "4 seconds", "2 seconds"),
    col("place")).count()
   //.withWatermark("timestamp", "1 minutes")
   .writeStream.format("console").outputMode("update").start().awaitTermination()



}
