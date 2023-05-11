package api

import io.gatling.core.structure._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import config.BaseHelpers.shopAPI

object defaultStoreAPI {
  def defaultStore(): ChainBuilder = {
    exec(
      http("GET:Default Sore")
          .get(shopAPI+"/store/DEFAULT")
        .resources(http("OP:Default Store")
          .options(shopAPI+"/store/DEFAULT"))
        .resources(http("GET:Content Default")
          .get(shopAPI+"/content/pages/?page=0&count=20&store=DEFAULT&lang=en"))
        .resources(http("OP:Content Default")
          .options(shopAPI+"/content/pages/?page=0&count=20&store=DEFAULT&lang=en"))
    )
  }
}
