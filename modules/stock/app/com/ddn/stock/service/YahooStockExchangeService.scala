package com.bigbigniu.stock.service

import java.time.LocalDate

import com.ddn.stock.feeder.CachedYahooDataFeeder
import com.ddn.stock.service.StockExchangeService
import com.google.inject.{Inject, Singleton}

import scala.io.Source

@Singleton
class YahooStockExchangeService @Inject() (feeder:CachedYahooDataFeeder) extends StockExchangeService {

  def slice(stockCode:String, fromDate:LocalDate, toDate:LocalDate) = feeder.fetchSlice(stockCode, fromDate, toDate)

  def all(stockCode:String) = feeder.fetchAll(stockCode)

}
