package br.com.zambotti.customer

import io.gatling.core.config.GatlingConfiguration

object DeleteCustomerFeeder {

  def getFeeder()(implicit gatlingConfiguration: GatlingConfiguration): Array[Map[String, Long]] = {
    Array(Map("customerId" -> 1))
  }

}
