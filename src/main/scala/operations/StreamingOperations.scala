package operations

import models.Temperature
import org.apache.spark.sql.expressions.scalalang.typed
import org.apache.spark.sql.{Dataset, Encoder, KeyValueGroupedDataset}

object StreamingOperations {

  val roomTemperature = 73.4

  // Selection Query
  /**
    * filters data on basis of temperature of places greater than room temperature
    * @param ds - dataset of temperature
    * @return - dataset of places having temperature greater than room temperature
    */
  def filterTemp(ds: Dataset[Temperature])(implicit stringEncoder: Encoder[String]): Dataset[String] = {
    ds.filter(_.fahrenheit > roomTemperature).map(_.place)
  }

  // Projection Query
  /**
    * projects all places
    * @param ds - dataset of temperature
    * @return - dataset of all places
    */
  def listAllPlaces(ds: Dataset[Temperature])(implicit stringEncoder: Encoder[String]): Dataset[String] = {
    ds.map(_.place)
  }

  // Aggregation Query
  /**
    * finds out aggregate temperature for particular places
    * @param ds - dataset of temperature
    * @param stringEncoder
    * @return - dataset of average temperature for all places
    */
  def averageTemperature(ds: Dataset[Temperature])(implicit stringEncoder: Encoder[String]): Dataset[(String, Double)] = {
    ds.groupByKey(_.place).agg(typed.avg(_.fahrenheit))
  }


  // 1. static-stream join
  // 2. stream-stream join
  // 3. watermarking concept

  def joinedData(stream1: Dataset[Temperature], stream2: Dataset[String]): Dataset[(String, Temperature)] = {

  }

}
