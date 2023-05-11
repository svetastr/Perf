package config

import io.gatling.core.structure._
import io.gatling.core.Predef._
import io.gatling.http.Predef.http
import io.gatling.http.protocol.HttpProtocolBuilder

object BaseHelpers {

  val shopURL = "http://localhost:8080"
  val shopAPI = "http://localhost:8080/api/v1"

  val random_chair = csv("./src/test/resources/feeders/chairs.csv").random


  def thinkTimer(Min :Int=2,Max :Int=5 ): ChainBuilder ={
    pause(Min,Max)
  }

  val httpProtocol : HttpProtocolBuilder = http
    .inferHtmlResources()
    .authorizationHeader("")
    .contentTypeHeader("application/json")
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .doNotTrackHeader("1")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:100.0) Gecko/20100101 Firefox/100.0")
    .disableFollowRedirect
}
