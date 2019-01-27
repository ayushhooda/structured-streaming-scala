package models

/**
  * Structure of Order Details
  * @param orderId - order id of order
  * @param product - product with it's count
  * @param outletId - outlet id of outlet to which the order belongs
  */
case class Order(orderId: String, product: List[(Product, Int)], outletId: String)
