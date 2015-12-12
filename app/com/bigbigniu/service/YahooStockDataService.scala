package com.bigbigniu.service

import com.bigbigniu.model.YahooStockExchangeInfo

import scala.io.Source

/**
 * User: bigfish
 * Date: 15-12-6
 * Time: 下午5:15
 */
object YahooStockDataService {

  //val baseUrl = "http://table.finance.yahoo.com/table.csv?a=10&b=5&c=2015&d=11&e=6&f=2015&s=600000.ss"

  val baseUrl = "http://table.finance.yahoo.com/table.csv?"

  def loadFromYahoo(stockCode: String, fromDate: String, toDate: String):List[YahooStockExchangeInfo] = {
    val c = fromDate.substring(0,4).toInt //From year
    val a = fromDate.substring(4,6).toInt - 1 //From month
    val b = fromDate.substring(6).toInt //From day

    val f = toDate.substring(0,4).toInt
    val d = toDate.substring(4,6).toInt - 1
    val e = toDate.substring(6).toInt

    val url = baseUrl + s"a=$a&b=$b&c=$c&d=$d&e=$e&f=$f&s=$stockCode"

    val exchangeDataList = Source.fromURL(url).getLines().drop(1).toList.map(line => {
      val items = line.split(",")
      YahooStockExchangeInfo(items(0), items(1).toDouble, items(2).toDouble,
        items(3).toDouble, items(4).toDouble, items(5).toDouble, items(6).toDouble)
    })

    exchangeDataList
  }
}
