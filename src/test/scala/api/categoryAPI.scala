package api

import config.BaseHelpers.shopAPI
import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._

object categoryAPI {
  def defaultCategory(): ChainBuilder = {
    exec(
      http("GET: Default Category")
        .get(shopAPI+"/category/?count=20&page=0&store=DEFAULT&lang=en")
        .resources(http("OP:Default Category")
          .options(shopAPI+"/category/?count=20&page=0&store=DEFAULT&lang=en")
        )
    )
  }
  def defaultStoreCategory(product_ID: String = "50"): ChainBuilder = {
    exec(
      http(s"GET:Default Store Category (for product ${product_ID})")
        .get(shopAPI+s"/category/${product_ID}?store=DEFAULT&lang=en")
        .resources(http(s"OP:Default Store Category (for product ${product_ID})")
          .options(shopAPI+s"/category/${product_ID}?store=DEFAULT&lang=en")
        )
    )
  }

  def storeCategory(category_ID: String = "50"): ChainBuilder = {
    exec(
      http(s"GET:Store Category (for category ${category_ID})")
        .get(shopAPI + s"/products/?&store=DEFAULT&lang=en&page=0&count=15&category=${category_ID}")
        .resources(http(s"OP:Store Category (for category ${category_ID})")
          .options(shopAPI + s"/products/?&store=DEFAULT&lang=en&page=0&count=15&category=${category_ID}")
        )
    )
  }


  def categoryManufactures(product_ID: String = "50"): ChainBuilder = {
    exec(
      http(s"GET:Store Manufactures (for product ${product_ID})")
        .get(shopAPI+s"/category/${product_ID}/manufacturers/?store=DEFAULT&lang=en")
        .resources(http(s"OP:Store Manufactures (for product ${product_ID})")
          .options(shopAPI+s"/category/${product_ID}/manufacturers/?store=DEFAULT&lang=en")
        )
    )
  }

  def categoryVariants(product_ID: String = "50"): ChainBuilder = {
    exec(
      http(s"GET:Category Variants (for product ${product_ID})")
        .get(shopAPI+s"/category/${product_ID}/variants/?store=DEFAULT&lang=en")
        .check(status.is(404)) //not to break up the script running
        .resources(http(s"OP:Category Variants (for product ${product_ID})")
          .options(shopAPI+s"/category/${product_ID}/variants/?store=DEFAULT&lang=en")
          .check(status.is(404)) //not to break up the script running
        )
    )
  }

}
