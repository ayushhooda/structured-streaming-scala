package dataframe

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Row, SparkSession}

object DataFrameOperations extends App {

  val sparkSession = SparkSession
    .builder()
    .master("local")
    .appName("structured-streaming-demo")
    .getOrCreate()

 // val schema = StructType()

  val foodData: Array[(String, Int)] = Array(("mango", 1), ("apple", 1), ("mango", 2), ("orange", 1), ("apple", 1), ("apple", 1))

  val foodRdd: RDD[(String, Int)] = sparkSession.sparkContext.parallelize(foodData)

  //val result: Array[Row] =  sparkSession.sqlContext.createDataFrame(foodRdd).filter()

  //result.foreach(println(_))

}
