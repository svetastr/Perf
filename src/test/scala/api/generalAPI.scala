package api

import io.gatling.core.structure._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import config.BaseHelpers._

object generalAPI {
  def ping: ChainBuilder = {
    exec(
      http("GET:Ping")
        .get(shopURL+"/actuator/health/ping")
        .resources(http("OP:Ping")
        .options(shopURL+"/actuator/health/ping"))
    )
  }

  def headerMessage: ChainBuilder = {
    exec(
      http("GET: Header Message")
        .get(shopAPI + "/content/boxes/headerMessage/?lang=en")
        .check(status.is(404)) //not to break up the script running
        .resources(http("OP:Header Message")
          .options(shopAPI + "/content/boxes/headerMessage/?lang=en"))
        .check(status.is(404)) //not to break up the script running
    )
  }

  def config: ChainBuilder = {
    exec(
      http("GET:Config")
        .get(shopAPI+"/config/")
        .resources(http("OP:Config")
          .options(shopAPI+"/config/"))
    )
  }

}
