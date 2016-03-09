package com.ddn.stock.feeder

import java.sql.Date

import com.ddn.stock.model.Exchange
import com.google.inject.{Inject, Singleton}

import scala.io.Source

@Singleton
class YahooDataFeeder @Inject() extends Feeder {

  val baseUrl = "http://table.finance.yahoo.com/table.csv?s="

  //val baseUrl = "http://table.finance.yahoo.com/table.csv?a=10&b=5&c=2015&d=11&e=6&f=2015&s=600000.ss"

  private def fetch(stockCode: String, url: String): Seq[Exchange] = {

    Source.fromURL(url).getLines().drop(1).toList.map(line => {
      val items = line.split(",")
      Exchange(stockCode, Date.valueOf(items(0)), items(1).toDouble, items(2).toDouble,
        items(3).toDouble, items(4).toDouble, items(5).toDouble, items(6).toDouble)
    })
  }

  def fetchAll(stockCode: String): Seq[Exchange] = {

    val url = baseUrl + s"$stockCode"

    fetch(stockCode, url)
  }


  def fetchFrom(stockCode: String, from: Date): Seq[Exchange] = {
    val localDate = from.toLocalDate
    val a = localDate.getMonthValue - 1
    val b = localDate.getDayOfMonth
    val c = localDate.getYear

    val url = baseUrl + s"$stockCode&a=$a&b=$b&c=$c"

    fetch(stockCode, url)
  }
}
