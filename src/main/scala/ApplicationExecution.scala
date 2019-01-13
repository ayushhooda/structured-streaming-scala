import org.apache.spark.sql.SparkSession

object ApplicationExecution extends App {

  // Entry Point to all functionality in spark
  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("structured-streaming-demo")
    .getOrCreate()

  // For implicit conversions like converting RDDs to DataFrames
  //  import sparkSession.implicits._

}
