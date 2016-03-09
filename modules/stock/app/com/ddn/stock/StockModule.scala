package com.ddn.stock

import com.ddn.stock.feeder.{Feeder, YahooDataFeeder}
import com.ddn.stock.service.ExchangeService
import com.ddn.stock.service.impl.ExchangeServiceImpl
import com.google.inject.AbstractModule

/**
 * This is a PlayModule that is used to do dependency inject
 *
 *
 */
class StockModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ExchangeService]).to(classOf[ExchangeServiceImpl])
    bind(classOf[Feeder]).to(classOf[YahooDataFeeder])
  }
}
