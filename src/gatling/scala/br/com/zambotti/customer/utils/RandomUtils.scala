package br.com.zambotti.customer.utils

import scala.util.Random
import java.time.LocalDate
import java.time.temporal.ChronoUnit.DAYS

object RandomUtils {
  def getRandomString(length: Int): String = {
    Random.alphanumeric.filter(_.isLetter).take(length).mkString.toUpperCase
  }

  def getRandomStringFromAList(values: List[String]): String = {
    values(Random.nextInt(values.size))
  }

  def getRandomNumberFromAList(numbers: List[Long]): Long = {
    numbers(Random.nextInt(numbers.size))
  }

  def getRandomNumber(min: Int, max: Int): Int = {
    min + Random.nextInt((max - min) + 1)
  }

  def getRandomDateUntil(to: LocalDate): LocalDate = {
    val from: LocalDate = LocalDate.now().minusYears(100)
    from.plusDays(new Random(System.nanoTime).nextInt(DAYS.between(from, to).toInt))
  }
}