package data

import models.Product
object ProductData {

  val firstProduct = Product("product1", "Product1", 150.0)
  val secondProduct = Product("product2", "Product2", 110.0)
  val thirdProduct = Product("product3", "Product3", 120.0)
  val fourthProduct = Product("product4", "Product4", 140.0)
  val fifthProduct = Product("product5", "Product5", 130.0)
  val sixthProduct = Product("product6", "Product6", 160.0)
  val seventhProduct = Product("product7", "Product7", 170.0)
  val eighthProduct = Product("product8", "Product8", 180.0)
  val ninthProduct = Product("product9", "Product9", 190.0)
  val tenthProduct = Product("product10", "Product10", 200.0)

  val productData: Seq[Product] = Seq(firstProduct, secondProduct, thirdProduct, fourthProduct, fifthProduct,
    sixthProduct, seventhProduct, eighthProduct, ninthProduct, tenthProduct)

}
