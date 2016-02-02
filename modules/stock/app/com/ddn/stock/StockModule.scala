package com.ddn.stock

import com.bigbigniu.stock.service.YahooStockExchangeService
import com.ddn.stock.feeder.{CachedYahooDataFeeder, Feeder}
import com.ddn.stock.service.StockExchangeService
import com.google.inject.AbstractModule

/**
 * User: bigfish
 * Date: 16-1-31
 * Time: 下午12:55
 */
class StockModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[StockExchangeService]).to(classOf[YahooStockExchangeService])
    bind(classOf[Feeder]).to(classOf[CachedYahooDataFeeder])
  }
}
