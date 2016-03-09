package com.ddn.stock.service.impl

import java.time.LocalDate
import javax.inject.Singleton

import com.ddn.stock.model.Exchange
import com.ddn.stock.service.ExchangeService

/**
 * User: bigfish
 * Date: 16-3-6
 * Time: 下午4:46
 */

@Singleton
class ExchangeServiceImpl extends ExchangeService {

  override def slice(stockCode: String, fromDate: LocalDate, toDate: LocalDate): Seq[Exchange] = ???

  override def all(stockCode: String): Seq[Exchange] = ???
}
