package com.ddn.stock.service

import java.time.LocalDate

import com.ddn.stock.model.StockExchange

/**
 * User: bigfish
 * Date: 16-1-31
 * Time: 下午12:34
 */
trait StockExchangeService {
  def slice(stockCode:String, fromDate:LocalDate, toDate:LocalDate): Array[StockExchange]
  def all(stockCode:String):Array[StockExchange]
}
