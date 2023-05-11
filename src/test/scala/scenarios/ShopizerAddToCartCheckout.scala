package scenarios

import config.BaseHelpers.thinkTimer
import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._

object ShopizerAddToCartCheckout {

  def scenarioShopizerAddTableToCart: ScenarioBuilder = {
    scenario("Shopizer Add Table To Cart")
      .exec(flushHttpCache)
      .exec(flushCookieJar)
      .exitBlockOnFail(
        group("Main page open") {
          exec(api.generalAPI.ping)
            .exec(api.productAPI.featuredItem())
            .exec(api.generalAPI.headerMessage)
            .exec(api.defaultStoreAPI.defaultStore())
            .exec(api.categoryAPI.defaultCategory())
            .repeat(2) {
              exec(api.productAPI.chairPrice())
              exec(api.productAPI.tablePrice())
                .exec(thinkTimer())
            }
        }
          .group("Tables category open") {
            exec(api.generalAPI.ping)
              .exec(api.generalAPI.headerMessage)
              .exec(api.defaultStoreAPI.defaultStore())
              .exec(api.categoryAPI.storeCategory())
              .exec(api.categoryAPI.defaultCategory())
              .exec(api.categoryAPI.defaultStoreCategory())
              .exec(api.categoryAPI.categoryManufactures())
              .exec(api.categoryAPI.categoryVariants())
              .exec(api.productAPI.tablePrice())
              .exec(thinkTimer())
          }
          .group("Tables page open") {
            exec(api.generalAPI.ping)
              .exec(api.defaultStoreAPI.defaultStore())
              .exec(api.categoryAPI.defaultCategory())
              .exec(api.productAPI.tablePrice())
              .exec(api.productAPI.tableOpening())
              .exec(thinkTimer())
          }
          .group("Add table to cart") {
            exec(api.cartAPI.addToNewCart())
              .exec(api.cartAPI.getCart())
              .exec(thinkTimer())
          }
      )
  }

  def scenarioShopizerAddTableAndChairToCart: ScenarioBuilder = {
    scenario("Shopizer Add Table&Chair To Cart")
      .exec(flushHttpCache)
      .exec(flushCookieJar)
      .exitBlockOnFail(
        group("Main page open") {
          exec(api.generalAPI.ping)
            .exec(api.productAPI.featuredItem())
            .exec(api.defaultStoreAPI.defaultStore())
            .exec(api.categoryAPI.defaultCategory())
            .repeat(2) {
              exec(api.productAPI.tablePrice())
              exec(api.productAPI.chairPrice())
                          }
            .exec(thinkTimer())
        }
          .group("Tables category open") {
            exec(api.generalAPI.ping)
              .exec(api.generalAPI.headerMessage)
              .exec(api.defaultStoreAPI.defaultStore())
              .exec(api.categoryAPI.storeCategory())
              .exec(api.categoryAPI.defaultCategory())
              .exec(api.categoryAPI.defaultStoreCategory())
              .exec(api.categoryAPI.categoryManufactures())
              .exec(api.categoryAPI.categoryVariants())
              .exec(api.productAPI.tablePrice())
              .exec(thinkTimer())
          }
          .group("Tables page open") {
            exec(api.generalAPI.ping)
              .exec(api.defaultStoreAPI.defaultStore())
              .exec(api.productAPI.tableOpening())
              .exec(api.generalAPI.headerMessage)
              .exec(api.categoryAPI.defaultCategory())
              .exec(api.productAPI.tablePrice())
              .exec(thinkTimer())
          }
          .group("Add table to cart") {
            exec(api.cartAPI.addToNewCart())
              .exec(api.cartAPI.getCart())
              .exec(thinkTimer())
          }

          .group("Chairs category open") {
            exec(api.generalAPI.ping)
              .exec(api.defaultStoreAPI.defaultStore())
              .exec(api.categoryAPI.defaultCategory())
              .exec(api.categoryAPI.storeCategory("51"))
              .exec(api.categoryAPI.categoryVariants("51"))
              .exec(api.categoryAPI.categoryManufactures("51"))
              .exec(api.categoryAPI.defaultStoreCategory("51"))
              .exec(api.productAPI.chairPrice())
              .exec(thinkTimer())
          }
          .group("Chair page open") {
            exec(api.generalAPI.ping)
              .exec(api.defaultStoreAPI.defaultStore())
              .exec(api.categoryAPI.defaultCategory())
              .exec(api.productAPI.chairOpening())
              .exec(thinkTimer())
          }
          .group("Add chair to cart") {
            exec(api.cartAPI.addToExistCart())
              .exec(api.cartAPI.getCart())
              .exec(thinkTimer())
          }
      )
  }

  def scenarioShopizerCheckoutwithTable: ScenarioBuilder = {
    scenario("Open Checkout with Table")
      .exec(flushHttpCache)
      .exec(flushCookieJar)
      .exitBlockOnFail(
        group("Main page open") {
          exec(api.generalAPI.ping)
            .exec(api.productAPI.featuredItem())
            .exec(api.defaultStoreAPI.defaultStore())
            .exec(api.categoryAPI.defaultCategory())
            .repeat(2) {
              exec(api.productAPI.tablePrice())
              exec(api.productAPI.chairPrice())
            }
            .exec(thinkTimer())
        }
          .group("Tables category open") {
            exec(api.generalAPI.ping)
              .exec(api.generalAPI.headerMessage)
              .exec(api.defaultStoreAPI.defaultStore())
              .exec(api.categoryAPI.storeCategory())
              .exec(api.categoryAPI.defaultCategory())
              .exec(api.categoryAPI.defaultStoreCategory())
              .exec(api.categoryAPI.categoryManufactures())
              .exec(api.categoryAPI.categoryVariants())
              .exec(api.productAPI.tablePrice())
              .exec(thinkTimer())
          }
          .group("Tables page open") {
            exec(api.generalAPI.ping)
              .exec(api.defaultStoreAPI.defaultStore())
              .exec(api.productAPI.tableOpening())
              .exec(api.generalAPI.headerMessage)
              .exec(api.categoryAPI.defaultCategory())
              .exec(api.productAPI.tablePrice())
              .exec(thinkTimer())
          }
          .group("Add table to cart") {
            exec(api.cartAPI.addToNewCart())
              .exec(api.cartAPI.getCart())
              .exec(thinkTimer())
          }
      .group("Open cart with Table") {
          exec(api.generalAPI.ping)
            .exec(api.defaultStoreAPI.defaultStore())
            .exec(api.categoryAPI.defaultCategory())
            .repeat(2) {
              exec(api.cartAPI.defaultCart())
            }
            .exec(thinkTimer())
        }
          .group("Open checkout with Table") {
            exec(api.generalAPI.ping)
              .exec(api.defaultStoreAPI.defaultStore())
              .exec(api.categoryAPI.defaultCategory())
              .exec(api.generalAPI.config)
              .repeat(2) {
                exec(api.cartAPI.zones)
              }
              .exec(api.cartAPI.shipCountry())
              .exec(api.cartAPI.shipping())
              .exec(api.cartAPI.defaultCart())
              .exec(api.cartAPI.cartTotal())
              .exec(thinkTimer())
          }
      )
  }

  def scenarioShopizerCheckoutwithTableAndChair: ScenarioBuilder = {
    scenario("Open Checkout with Table and Chair")
      .exitBlockOnFail(
        group("Main page open") {
          exec(api.generalAPI.ping)
            .exec(api.productAPI.featuredItem())
            .exec(api.generalAPI.headerMessage)
            .exec(api.defaultStoreAPI.defaultStore())
            .exec(api.categoryAPI.defaultCategory())
            .repeat(2) {
              exec(api.productAPI.chairPrice())
            }
            .exec(thinkTimer())
        }
          .group("Tables category open") {
            exec(api.generalAPI.ping)
              .exec(api.generalAPI.headerMessage)
              .exec(api.defaultStoreAPI.defaultStore())
              .exec(api.categoryAPI.storeCategory())
              .exec(api.categoryAPI.defaultCategory())
              .exec(api.categoryAPI.defaultStoreCategory())
              .exec(api.categoryAPI.categoryManufactures())
              .exec(api.categoryAPI.categoryVariants())
              .exec(api.productAPI.tablePrice())
              .exec(thinkTimer())
          }
          .group("Tables page open") {
            exec(api.generalAPI.ping)
              .exec(api.defaultStoreAPI.defaultStore())
              .exec(api.productAPI.tableOpening())
              .exec(api.generalAPI.headerMessage)
              .exec(api.categoryAPI.defaultCategory())
              .exec(api.productAPI.tablePrice())
              .exec(thinkTimer())
          }
          .group("Add table to cart") {
            exec(api.cartAPI.addToNewCart())
              .exec(api.cartAPI.getCart())
              .exec(thinkTimer())
          }
          .group("Chairs category open") {
            exec(api.generalAPI.ping)
              .exec(api.defaultStoreAPI.defaultStore())
              .exec(api.categoryAPI.defaultCategory())
              .exec(api.categoryAPI.storeCategory("51"))
              .exec(api.categoryAPI.categoryVariants("51"))
              .exec(api.categoryAPI.categoryManufactures("51"))
              .exec(api.categoryAPI.defaultStoreCategory("51"))
              .exec(api.productAPI.chairPrice())
              .exec(thinkTimer())
          }
          .group("Chair page open") {
            exec(api.generalAPI.ping)
              .exec(api.defaultStoreAPI.defaultStore())
              .exec(api.categoryAPI.defaultCategory())
              .exec(api.productAPI.chairOpening())
              .exec(thinkTimer())
          }
          .group("Add chair to cart") {
            exec(api.cartAPI.addToExistCart())
              .exec(api.cartAPI.getCart())
              .exec(thinkTimer())
          }
          .group("Open cart with table and chair") {
            exec(api.generalAPI.ping)
              .exec(api.defaultStoreAPI.defaultStore())
              .exec(api.categoryAPI.defaultCategory())
              .repeat(2) {
                exec(api.cartAPI.defaultCart())
              }
              .exec(thinkTimer())
          }
          .group("Open checkout with table and chair") {
            exec(api.generalAPI.ping)
              .exec(api.defaultStoreAPI.defaultStore())
              .exec(api.categoryAPI.defaultCategory())
              .exec(api.generalAPI.config)
              .repeat(2) {
                exec(api.cartAPI.zones)
              }
              .exec(api.cartAPI.shipCountry())
              .exec(api.cartAPI.shipping())
              .exec(api.cartAPI.defaultCart())
              .exec(api.cartAPI.cartTotal())
              .exec(thinkTimer())
          }
      )
  }

}