package br.com.zambotti.customer

import io.gatling.core.session.Session
import io.gatling.core.Predef.{exec, _}
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.protocol.HttpProtocol

import scala.concurrent.duration._

class CustomerApiSimulation extends Simulation {

  private val execution_group = "Execution Step"
  private val configuration_group = "Configuration Step"
  
  private val getCustomersFeeder = GetCustomersDatabaseFeeder.getFeeder()
  private val createCustomerFeeder = CreateCustomerFeeder.getFeeder(100)
  private val deleteCustomerFeeder = DeleteCustomerFeeder.getFeeder()
  private val httpProtocol: HttpProtocol = CustomerApiConfig.httpProtocol()

  private var token = ""

  def getValueFromSession(session: Session, attributeName: String): String = {
    session(attributeName).as[String].trim
  }

  val getTokenScenarioBuilder: ScenarioBuilder = scenario("Get Token")
    .group(configuration_group) {
      exec(CustomerApiScenarios.getToken())
      .exec(session => {
        token = getValueFromSession(session, "token")
        session
      })
    }

  val getCustomerByIdScenarioBuilder: ScenarioBuilder = scenario("Get Customers by Id")
    .group(execution_group) {
      //Token - Opção 2 - Descomentar se quiser passar como feeder
      //exec(_.set("token", token)).
      exec().feed(getCustomersFeeder).exec(CustomerApiScenarios.getCustomerById)
    }

  val createCustomerScenarioBuilder: ScenarioBuilder = scenario("Create Customer")
    .group(execution_group) {
      //Token - Opção 2 - Descomentar se quiser passar como feeder
      //exec(_.set("token", token)).
      exec().feed(createCustomerFeeder.random).exec(CustomerApiScenarios.createCustomer)
    }

  val deleteCustomerScenarioBuilder: ScenarioBuilder = scenario("Delete Customer")
    .group(execution_group) {
      exec().feed(deleteCustomerFeeder.random).exec(CustomerApiScenarios.deleteCustomerById)
    }

  setUp(
    //getTokenScenarioBuilder.inject(
    //  atOnceUsers(1)
    //),
    //deleteCustomerScenarioBuilder.inject(
    //  atOnceUsers(1)
    //),
    createCustomerScenarioBuilder.inject(
      /* Token - Opção 2 - Descomentar se quiser passar como feeder, pois como é executado de forma
         concorrente precisa ter o tempo de obter o token */
      //nothingFor(2 seconds),
      rampUsersPerSec(10) to 20 during (30 seconds) randomized
    ),
    getCustomerByIdScenarioBuilder.inject(
      /* Token - Opção 2 - Descomentar se quiser passar como feeder, pois como é executado de forma
         concorrente precisa ter o tempo de obter o token */
      //nothingFor(5 seconds),
      constantUsersPerSec(10) during (30 seconds) randomized,
    )
  )
  .protocols(httpProtocol)

  .assertions(details(execution_group).responseTime.mean.lte(500))
  .assertions(details(execution_group).successfulRequests.count.gte(300))
  .assertions(details(execution_group).failedRequests.percent.lte(5))
  .assertions(details(execution_group).requestsPerSec.gte(10))
}