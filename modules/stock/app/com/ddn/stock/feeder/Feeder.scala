package com.ddn.stock.feeder

import java.sql.Date

import com.ddn.stock.model.Exchange

trait Feeder {

  def fetchAll(stockCode: String): Seq[Exchange]

  def fetchFrom(stockCode: String, from: Date): Seq[Exchange]
}
