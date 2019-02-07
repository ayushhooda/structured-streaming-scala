package operations

import models.{Outlet, Temperature, Product, Order}
import org.apache.spark.sql.expressions.scalalang.typed
import org.apache.spark.sql.{Dataset, Encoder}

object StreamingOperations {

  val roomTemperature = 27

  /**
    * Selection Query - Filters data on basis of temperature of places greater than room temperature
    * @param ds - dataset of temperature
    * @return - dataset of places having temperature greater than room temperature
    */
  def filterTemp(ds: Dataset[Temperature])(implicit stringEncoder: Encoder[String]): Dataset[Temperature] = {
    ds.filter(_.fahrenheit > roomTemperature)
  }

  /**
    * Projection Query - Projects all places
    * @param ds - dataset of temperature
    * @return - dataset of all places
    */
  def listAllPlaces(ds: Dataset[Temperature])(implicit stringEncoder: Encoder[String]): Dataset[String] = {
    ds.map(_.place)
  }

  /**
    * Aggregation Query - Finds out aggregate temperature for particular places
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

  def staticStaticJoin(outletData: Dataset[Outlet], productData: Dataset[Product])
                      (implicit stringOutletEncoder: Encoder[(String, Outlet)]) = {
    val outletDataMap = outletData.map(x=>x.place -> x)
    //val productDataMap = productData.map(x=>x.)
  }

  /**
    * Left Outer Join - Joins the temperature data with the outlet data
    * @param stream - temperature stream
    * @param static - static outlet data
    * @return - temperature data joined with outlet details if the outlet exists corresponding to that place
    */
  //def outlet(stream: Dataset[Temperature], static: Dataset[Outlet]): Dataset[(Temperature, Outlet)] = {
   // stream.jo
  //}

}
