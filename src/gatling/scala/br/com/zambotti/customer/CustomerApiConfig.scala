package br.com.zambotti.customer

import io.gatling.core.config.GatlingConfiguration
import io.gatling.http.protocol.HttpProtocol
import io.gatling.core.Predef._
import io.gatling.http.Predef.http

object CustomerApiConfig {

  private val host = Configuration.getString("gatling.configuration.host")
  private val authUsername = Configuration.getString("gatling.configuration.auth.username")
  private val authPassword = Configuration.getString("gatling.configuration.auth.password")
  private val token = getToken()

  def httpProtocol()(implicit gatlingConfiguration: GatlingConfiguration): HttpProtocol = {
    http(gatlingConfiguration)
      .baseUrl(host)
      .header("Content-Type", "application/json")
      //Token - Opção 1 - Comentar se não quiser passar no httpProcol e passar por exemplo como feeder
      .header("Authorization", "Bearer ".concat(token))
  }

  def getToken(): String =
    ujson.read(requests.post(
      host.concat("/users/login"), headers = Map("Content-Type" -> "application/json"),
      data = ujson.write(Map("username" -> authUsername, "password" -> authPassword))).text()
    )("token").toString().replace("\"", "")
}
