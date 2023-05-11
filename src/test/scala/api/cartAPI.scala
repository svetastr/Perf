package api

import io.gatling.core.structure._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import config.BaseHelpers.shopAPI

object cartAPI {
  def addToNewCart(): ChainBuilder = {
    exec(
      http("POST:Add to Cart - new")
        .post(shopAPI+"/cart/?store=DEFAULT")
        .body(RawFileBody("bodies/requests/add_table_to_cart.json"))
        .check(jsonPath("$.code").find.saveAs("cart_ID"))
        .resources(http("OP:Add to Cart - new")
          .options(shopAPI+"/cart/?store=DEFAULT")
        )
    )
  }
  def addToExistCart(): ChainBuilder = {
    exec(
      http("PUT:Add to Cart - existing")
        .put(shopAPI+"/cart/${cart_ID}?store=DEFAULT")
        .body(ElFileBody("bodies/requests/add_chair_to_cart.json"))
        .resources(http("OP:Add to Cart - existing")
          .options(shopAPI+"/cart/${cart_ID}?store=DEFAULT"))
    )
  }

  def getCart(): ChainBuilder = {
    exec(
      http("GET: Cart")
        .get(shopAPI+"/cart/${cart_ID}?lang=en")
        .resources(http("OP: Cart")
          .options(shopAPI+"/cart/${cart_ID}?lang=en"))
    )
  }
  def defaultCart(): ChainBuilder = {
    exec(
      http("GET:Default Cart")
        .get(shopAPI+"/cart/${cart_ID}?store=DEFAULT")
        .resources(http("OP:Default Cart")
          .options(shopAPI+"/cart/${cart_ID}?store=DEFAULT"))
    )
  }
  def cartTotal(): ChainBuilder = {
    exec(
      http("GET:Total")
        .get(shopAPI+"/cart/${cart_ID}/total/")
        .resources(http("OP:Total")
          .options(shopAPI+"/cart/${cart_ID}/total/"))
    )
  }

  def shipping(): ChainBuilder = {
    exec(
      http("POST:Shipping")
        .post(shopAPI+"/cart/${cart_ID}/shipping")
        .body(StringBody("""{}"""))
        .check(status.is(503)) //not to break up the script running
        .resources(http("OP:Shipping")
          .options(shopAPI+"/cart/${cart_ID}/shipping"))
    )
  }

  def shipCountry(): ChainBuilder = {
    exec(
      http("GET:Shipping Country")
        .get(shopAPI + "/shipping/country?store=DEFAULT&lang=en")
        .resources(http("OP:Shipping Country")
          .options(shopAPI + "/shipping/country?store=DEFAULT&lang=en"))
    )
  }

  def zones: ChainBuilder = {
    exec(
      http("GET:Zones")
        .get(shopAPI + "/zones/?code=")
        .resources(http("OP:Zones")
          .options(shopAPI + "/zones/?code="))
    )
  }

}
