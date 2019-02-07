package rdd

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RddOperations extends App {

  val conf: SparkConf = new SparkConf().setAppName("rdd-knolx").setMaster("local[*]")

  val sparkContext = new SparkContext(conf)

  val foodData: Array[(String, Int)] = Array(("mango", 1), ("apple", 1), ("mango", 2), ("orange", 1), ("apple", 1), ("apple", 1))

  val foodRdd: RDD[(String, Int)] = sparkContext.parallelize(foodData)

  val result: Array[(String, Int)] = foodRdd.reduceByKey(_ + _).filter(_._2 > 1).collect

  result.foreach(println(_))

}
