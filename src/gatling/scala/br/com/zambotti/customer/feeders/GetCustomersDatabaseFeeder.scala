package br.com.zambotti.customer

import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.feeder.FeederBuilder
import io.gatling.jdbc.Predef.jdbcFeeder

object GetCustomersDatabaseFeeder {

  val url = Configuration.getString("gatling.configuration.database.customer.url")
  val username = Configuration.getString("gatling.configuration.database.customer.username")
  val password = Configuration.getString("gatling.configuration.database.customer.password")

  def getFeeder()(implicit gatlingConfiguration: GatlingConfiguration): FeederBuilder = {
    jdbcFeeder(url, username, password, "SELECT ID AS CUSTOMER_ID FROM CUSTOMER WHERE ID > 1")(gatlingConfiguration).random
  }
}
