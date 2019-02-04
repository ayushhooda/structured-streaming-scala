package data

object InsertDataToKafka extends App {

  import java.util.Properties

  import org.apache.kafka.clients.producer._
  import com.google.gson.Gson


  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)

  val temperatureTopic = "temperature-topic"
  val orderTopic = "order-topic"
  val outletTopic = "outlet-topic"
  val productTopic = "product-topic"

  for(temp <- TemperatureData.temperatureData) {
    val a = (new Gson).toJson(temp)
    val record = new ProducerRecord(temperatureTopic, "key", a)
    producer.send(record)
    Thread.sleep(100)
  }

//  for(product <- ProductData.productData) {
//    val a = (new Gson).toJson(product)
//    val record = new ProducerRecord(productTopic, "key", a)
//    producer.send(record)
//  }
//
//  for(outlet <- OutletData.outletData) {
//    val a = (new Gson).toJson(outlet)
//    val record = new ProducerRecord(outletTopic, "key", a)
//    producer.send(record)
//  }
//
//  for(order <- OrderData.orderData) {
//    val a = (new Gson).toJson(order)
//    val record = new ProducerRecord(orderTopic, "key", a)
//    producer.send(record)
//  }

  producer.close()

  Thread.sleep(9000)

}
