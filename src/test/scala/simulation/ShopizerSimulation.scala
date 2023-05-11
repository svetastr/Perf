package simulation

import config.BaseHelpers._
import io.gatling.core.Predef._
import scenarios.ShopizerAddToCartCheckout._

class ShopizerSimulation extends Simulation {

  setUp(
    //assume that total 100 users, 50% added chair, 30% from all users opened cart/checkout
    scenarioShopizerAddTableToCart.inject(rampUsers(35).during(60)).protocols(httpProtocol),
    scenarioShopizerAddTableAndChairToCart.inject(rampUsers(35).during(60)).protocols(httpProtocol),
    scenarioShopizerCheckoutwithTable.inject(rampUsers(15).during(60)).protocols(httpProtocol),
    scenarioShopizerCheckoutwithTableAndChair.inject(rampUsers(15).during(60)).protocols(httpProtocol)


   // to run 100 users for the whole flow*/
   //  scenarioShopizerCheckoutwithTableAndChair.inject(rampUsers(10).during(60)).protocols(httpProtocol)


  )
  //mvn clean gatling:test - command to run test
}
