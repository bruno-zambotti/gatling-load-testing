package br.com.zambotti.customer

import com.typesafe.config.{Config, ConfigFactory}

object Configuration {

  def getConfig: Config = {
    ConfigFactory.load()
  }

  def getString(key: String): String = {
    this.getConfig.getString(key)
  }

}
