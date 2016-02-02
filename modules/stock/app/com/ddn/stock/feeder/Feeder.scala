package com.ddn.stock.feeder

import java.time.LocalDate
import java.util.Date

import com.ddn.stock.model.StockExchange

trait Feeder {

  def fetchAll(stockCode: String): Array[StockExchange]

  def fetchSlice(stockCode: String, fromDate: LocalDate, toDate: LocalDate): Array[StockExchange]
}
