package br.com.zambotti.customer

import io.gatling.core.Predef.exec
import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.http.check.HttpCheck
import scala.collection.mutable.ListBuffer

object CustomerApiScenarios {

  val authUsername = Configuration.getString("gatling.configuration.auth.username")
  val authPassword = Configuration.getString("gatling.configuration.auth.password")

  /**
   * Este cenário esta abstraído em um método pois visa demonstrar que é possível obter um
   * valor de sessão em um cenário retornar em outra classe como uma simulação e repassá-lo
   * em outros cenários. Um exemplo de aplicação seria a necessidade de realizar autenticações
   * para usuários distintos em cada requisição, onde as credenciais de acesso poderiam ser
   * passados como argumentos do método.
   */
  def getToken() = {
    http("Get Token")
      .post("/users/login")
      .header("Content-Type", "application/json")
      .body(StringBody("{" +
        "\"username\": \"" + authUsername + "\"," +
        "\"password\": \"" + authPassword + "\" " +
      "}"))
      .check(status.is(_ => 200))
      .check(jsonPath("$.token").saveAs("token"))
  }

  val getCustomerById =
    exec(http("Get Customer by Id")
      .get("/microservices/v1/customer/${CUSTOMER_ID}")
      //Token - Opção 2 - Descomentar se quiser passar como feeder
      //.header("Authorization", "Bearer ${token}")
      .check(status.is(_ => 200)))

  /**
   * Este cenário visa demonstrar como é possível enviar um corpo na requisição realizada
   */
  val createCustomer =
    exec(http("Create Customer")
      .post("/microservices/v1/customer")
      //Token - Opção 2 - Descomentar se quiser passar como feeder
      //.header("Authorization", "Bearer ${token}")
      .body(StringBody("{ " +
        "\"name\": \"${name}\", " +
        "\"surname\": \"${surname}\", " +
        "\"birthDate\": \"${birthDate}\", " +
        "\"gender\": \"${gender}\", " +
        "\"adress\": [ " +
          "{ " +
            "\"streetName\": \"${streetName}\", " +
            "\"number\": ${number}, " +
            "\"complement\": \"${complement}\", " +
            "\"postalCode\": \"${postalCode}\", " +
            "\"city\": \"${city}\", " +
            "\"province\": \"${province}\", " +
            "\"country\": \"${country}\", " +
            "\"type\": ${addressType} " +
          "} " +
        "], " +
        "\"phones\": [ " +
          "{ " +
            "\"number\": \"${number}\" " +
          "} " +
        "] " +
      "}"))
      .check(status.is(_ => 201)))

  /**
   * Este cenário visa demonstrar que é possível encadear execuções e relacionar dependências
   * entre os passos
   */
  val deleteCustomerById =
    exec(http("Get Token")
      .post("/users/login")
      .header("Content-Type", "application/json")
      .body(StringBody("{" +
        "\"username\": \"" + authUsername + "\"," +
        "\"password\": \"" + authPassword + "\" " +
      "}"))
      .check(status.is(_ => 200))
      .check(jsonPath("$.token").saveAs("token")))
    .exec(http("Delete Customer")
      .delete("/microservices/v1/customer/${customerId}")
      .header("Content-Type", "application/json")
      .header("Authorization", "Bearer ${token}")
      .check(status.is(_ => 204)))
    .exec(http("Get Customer by Id")
      .get("/microservices/v1/customer/${customerId}")
      .header("Content-Type", "application/json")
      .header("Authorization", "Bearer ${token}")
      .check(status.is(_ => 404))
      .check(jsonPath("$.message").is("Cliente nao encontrado")))
}
