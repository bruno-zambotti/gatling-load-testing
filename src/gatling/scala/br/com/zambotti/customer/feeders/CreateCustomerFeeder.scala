package br.com.zambotti.customer

import br.com.zambotti.customer.models.Customer
import br.com.zambotti.customer.utils.{CustomerBuilder, CustomerValues}
import io.gatling.core.config.GatlingConfiguration

object CreateCustomerFeeder {

  def getFeeder(size: Int)(implicit gatlingConfiguration: GatlingConfiguration): Array[Map[String, Any]] = {
    var values = Array[Map[String, Any]]()
    for(i <- 0 until size) {
      values ++= Array(getValues(getCustomer(new CustomerBuilder().withRandomData().build)))
    }
    values
  }

  def getValues(values: Customer) = {
    values.getClass.getDeclaredFields.foldLeft(Map.empty[String, Any]) { (a, f) =>
      f.setAccessible(true)
      a + (f.getName -> f.get(values))
    }
  }

  def getCustomer(customerValues: CustomerValues): Customer = {
    val customer: Customer = new Customer
    customer.name = customerValues.name
    customer.surname = customerValues.surname
    customer.birthDate = customerValues.birthDate
    customer.gender = customerValues.gender
    customer.streetName = customerValues.streetName
    customer.number = customerValues.number
    customer.complement = customerValues.complement
    customer.postalCode = customerValues.postalCode
    customer.city = customerValues.city
    customer.province = customerValues.province
    customer.country = customerValues.country
    customer.addressType = customerValues.addressType
    customer.phoneNumber = customerValues.phoneNumber
    customer
  }
}
