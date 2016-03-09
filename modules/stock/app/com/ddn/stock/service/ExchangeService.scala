package com.ddn.stock.service

import java.time.LocalDate

import com.ddn.stock.model.Exchange

/**
 * User: bigfish
 * Date: 16-1-31
 * Time: 下午12:34
 */
trait ExchangeService {
  def slice(stockCode:String, fromDate:LocalDate, toDate:LocalDate): Seq[Exchange]
  def all(stockCode:String):Seq[Exchange]
}
