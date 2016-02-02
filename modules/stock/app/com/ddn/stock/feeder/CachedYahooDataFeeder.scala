package com.ddn.stock.feeder

import java.time._

import com.ddn.stock.model.StockExchange
import com.google.inject.{Inject, Singleton}
import play.api.cache.CacheApi

import scala.io.Source

@Singleton
class CachedYahooDataFeeder @Inject()(cache: CacheApi) extends Feeder {

  val baseUrl = "http://table.finance.yahoo.com/table.csv?s="

  //val baseUrl = "http://table.finance.yahoo.com/table.csv?a=10&b=5&c=2015&d=11&e=6&f=2015&s=600000.ss"

  def fetchAll(stockCode: String): Array[StockExchange] = cache.getOrElse[Array[StockExchange]](stockCode) {
    val url = baseUrl + s"$stockCode"

    val exchangeDataList = Source.fromURL(url).getLines().drop(1).toList.map(line => {
      val items = line.split(",")
      StockExchange(LocalDate.parse(items(0)), items(1).toDouble, items(2).toDouble,
        items(3).toDouble, items(4).toDouble, items(5).toDouble, items(6).toDouble)
    })

    exchangeDataList.toArray
  }


  def fetchSlice(stockCode: String, fromDate: LocalDate, toDate: LocalDate): Array[StockExchange] = {
    fetchAll(stockCode).filter {
      ext =>
        (ext.date.isEqual(fromDate) || ext.date.isAfter(fromDate)) &&
          (ext.date.isBefore(toDate) || ext.date.isEqual(toDate))
    }
  }
}
