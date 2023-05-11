package api

import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._
import config.BaseHelpers.{shopAPI,random_chair}

object productAPI {
  def featuredItem(): ChainBuilder = {
    exec(
      http("GET:Featured Item")
        .get(shopAPI+"/products/group/FEATURED_ITEM?store=DEFAULT&lang=en")
        .resources(http("Featured Item")
          .options(shopAPI+"/products/group/FEATURED_ITEM?store=DEFAULT&lang=en"))
    )
  }

  def chairPrice(): ChainBuilder = {
    foreach(Seq("50", "51", "52"), "chair_ID") {
      exec(
        http("POST: Chair ${chair_ID} Price")
          .post(shopAPI + "/product/${chair_ID}/price/")
          .body(StringBody("""{"options":[]}"""))
          .resources(http("OP:Chair ${chair_ID} Price")
            .options(shopAPI + "/product/${chair_ID}/price/"))
      )
    }
  }

 def tablePrice(table_ID: String = "1"): ChainBuilder = {
    exec(
      http(s"POST: Table ${table_ID} Price")
        .post(shopAPI+s"/product/${table_ID}/price/")
        .body(StringBody("""{"options":[]}"""))
        .resources(http(s"OP:Table ${table_ID} Price")
          .options(shopAPI+s"/product/${table_ID}/price/"))
    )
  }


  def tableOpening(table_ID: String = "1"): ChainBuilder = {
    exec(
      http(s"GET:Table ${table_ID}")
        .get(shopAPI + s"/products/${table_ID}?lang=en&store=DEFAULT")
        .header("Accept-Encoding", "gzip, deflate, br")
        .resources(http(s"OP:Table ${table_ID}")
          .options(shopAPI + s"/products/${table_ID}?lang=en&store=DEFAULT"))
        .resources(http(s"GET:Table Review ${table_ID}")
          .get(shopAPI + s"/products/${table_ID}/reviews?store=DEFAULT")
          .resources(http(s"OP:Table Review ${table_ID}")
            .options(shopAPI + s"/products/${table_ID}/reviews?store=DEFAULT")))
    )
  }

  def chairOpening(): ChainBuilder = {
    feed(random_chair)
    .exec(
      http("GET:Chair ${chair_ID}")
        .get(shopAPI+"/products/${chair_ID}?lang=en&store=DEFAULT")
        .header("Accept-Encoding","gzip, deflate, br")
        .check(jsonPath("$.sku").find.saveAs("chair_item"))
        .resources(http("OP:Chair ${chair_ID}")
          .options(shopAPI+"/products/${chair_ID}?lang=en&store=DEFAULT"))
        .resources(http("GET:Chair Review ${chair_ID}")
            .get(shopAPI+"/products/${chair_ID}/reviews?store=DEFAULT")
            .header("Accept-Encoding","gzip, deflate, br"))
            .resources(http("OP:Chair Review ${chair_ID}")
              .options(shopAPI+"/products/${chair_ID}/reviews?store=DEFAULT"))
        .resources(http("POST:Chair Price ${chair_ID}")
          .post(shopAPI+"/product/${chair_ID}/price/")
          .body(StringBody("""{"options":[]}"""))
          .resources(http("OP:Chair Price ${chair_ID}")
            .options(shopAPI+"/product/${chair_ID}/price/"))
    )
    )
  }

}
