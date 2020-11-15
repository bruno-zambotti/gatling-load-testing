package br.com.zambotti.customer.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import br.com.zambotti.customer.models.Customer

abstract class AbstractCustomerBuilder {
  var name: String
  var surname: String
  var birthDate: String
  var gender: String
  var streetName: String
  var number: Long
  var complement: String
  var postalCode: String
  var city: String
  var province: String
  var country: String
  var addressType: Long
  var phoneNumber: String

  def withExampleData(): CustomerBuilder
  def withRandomData(): CustomerBuilder
  def withName(name: String): CustomerBuilder
  def withSurname(surname: String): CustomerBuilder
  def withBirthDate(birthDate: String): CustomerBuilder
  def withGender(gender: String): CustomerBuilder
  def withStreetName(streetName: String): CustomerBuilder
  def withNumber(number: Long): CustomerBuilder
  def withComplement(complement: String): CustomerBuilder
  def withPostalCode(postalCode: String): CustomerBuilder
  def withCity(city: String): CustomerBuilder
  def withProvince(province: String): CustomerBuilder
  def withCountry(country: String): CustomerBuilder
  def withAddressType(addressType: Long): CustomerBuilder
  def withPhoneNumber(phoneNumber: String): CustomerBuilder

  def build: Customer
}

class CustomerValues(builder: AbstractCustomerBuilder) extends Customer {
  name = builder.name
  surname = builder.surname
  birthDate = builder.birthDate
  gender = builder.gender
  streetName = builder.streetName
  number = builder.number
  complement = builder.complement
  postalCode = builder.postalCode
  city = builder.city
  province = builder.province
  country = builder.country
  addressType = builder.addressType
  phoneNumber = builder.phoneNumber
}

class CustomerBuilder extends AbstractCustomerBuilder {
  var name = ""
  var surname = ""
  var birthDate = ""
  var gender = ""
  var streetName = ""
  var number = 0
  var complement = ""
  var postalCode = ""
  var city = ""
  var province = ""
  var country = ""
  var addressType = 0
  var phoneNumber = ""
  
  override def withExampleData(): CustomerBuilder = {
    this.withName("Name")
    this.withSurname("Surname")
    this.withBirthDate("12/10/1981")
    this.withGender("M")
    this.withStreetName("streetName")
    this.withNumber(1)
    this.withComplement("complement")
    this.withPostalCode("03325885")
    this.withCity("city")
    this.withProvince("province")
    this.withCountry("country")
    this.withAddressType(1)
    this.withPhoneNumber("988562245")
    this  
  }

  override def withRandomData(): CustomerBuilder = {
    this.withName(RandomUtils.getRandomString(5))
    this.withSurname(RandomUtils.getRandomString(10))
    this.withBirthDate(RandomUtils.getRandomDateUntil(
      LocalDate.now().minusYears(18)).format(
        DateTimeFormatter.ofPattern("dd/MM/yyyy")))
    this.withGender(RandomUtils.getRandomStringFromAList(List("M", "F")))
    this.withStreetName(RandomUtils.getRandomString(15))
    this.withNumber(RandomUtils.getRandomNumber(1, 99999))
    this.withComplement(RandomUtils.getRandomString(10))
    this.withPostalCode(RandomUtils.getRandomNumber(10000000, 99999999).toString)
    this.withCity(RandomUtils.getRandomStringFromAList(
      List("SAO PAULO", "CAMPINAS", "SOROCABA", "BARUERI", "OSASCO")))
    this.withProvince("SAO PAULO")
    this.withCountry("BRAZIL")
    this.withAddressType(RandomUtils.getRandomNumberFromAList(List(1, 2, 3, 4)))
    this.withPhoneNumber(RandomUtils.getRandomNumber(20000000, 69999999).toString)
    this
  }

  override def withName(name: String): CustomerBuilder = {
    this.name = name
    this
  }
  override def withSurname(surname: String): CustomerBuilder = {
    this.surname = surname
    this
  }
  override def withBirthDate(birthDate: String): CustomerBuilder = {
    this.birthDate = birthDate
    this
  }
  override def withGender(gender: String): CustomerBuilder = {
    this.gender = gender
    this
  }
  override def withStreetName(streetName: String): CustomerBuilder = {
    this.streetName = streetName
    this
  }
  override def withNumber(number: Long): CustomerBuilder = {
    this.number = number
    this
  }
  override def withComplement(complement: String): CustomerBuilder = {
    this.complement = complement
    this
  }
  override def withPostalCode(postalCode: String): CustomerBuilder = {
    this.postalCode = postalCode
    this
  }
  override def withCity(city: String): CustomerBuilder = {
    this.city = city
    this
  }
  override def withProvince(province: String): CustomerBuilder = {
    this.province = province
    this
  }
  override def withCountry(country: String): CustomerBuilder = {
    this.country = country
    this
  }
  override def withAddressType(addressType: Long): CustomerBuilder = {
    this.addressType = addressType
    this
  }
  override def withPhoneNumber(phoneNumber: String): CustomerBuilder = {
    this.phoneNumber = phoneNumber
    this
  }
  
  override def build = new CustomerValues(this)
}
