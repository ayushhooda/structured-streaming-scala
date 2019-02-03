package data

import models.Outlet

object OutletData {

  val outletData = Seq(Outlet("delhiOutlet", " Delhi Outlet", "delhi", ProductData.productData.toList),
    Outlet("rajasthanOutlet", " Delhi Outlet", "delhi", ProductData.productData.toList),
    Outlet("himachalOutlet", " Delhi Outlet", "delhi", ProductData.productData.toList))

}
