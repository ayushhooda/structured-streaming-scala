package operations

import org.apache.spark.sql.expressions.scalalang.typed
import org.apache.spark.sql.{Dataset, Encoder, KeyValueGroupedDataset}

object StreamingOperations {

  case class Temperature(place: String, farhenheit: Float)
  val roomTemperature = 56

  // Selection Query

  /**
    * filters data on basis of temperature of places greater than room temperature
    * @param ds - dataset of temperature
    * @return - dataset of places having temperature greater than room temperature
    */
  def filterTemp(ds: Dataset[Temperature])(implicit stringEncoder: Encoder[String]): Dataset[String] = {
    ds.filter(_.farhenheit > roomTemperature).map(_.place)
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
    ds.groupByKey(_.place).agg(typed.avg(_.farhenheit))
  }

}
