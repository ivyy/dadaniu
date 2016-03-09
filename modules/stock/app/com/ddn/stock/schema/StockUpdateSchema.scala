package com.ddn.stock.schema

import java.sql.Date

import com.ddn.stock.model.StockUpdate
import slick.driver.JdbcProfile

/**
 * User: bigfish
 * Date: 16-3-6
 * Time: 上午8:27
 */
trait StockUpdateSchema {
  protected val driver: JdbcProfile

  import driver.api._

  class StockUpdateTable(tag: Tag) extends Table[StockUpdate](tag, "STOCK_UPDATE") {

    def stockCode = column[String]("STOCK_CODE", O.PrimaryKey)

    def lastUpdate = column[Date]("LAST_UPDATE")

    def * = (stockCode, lastUpdate) <>(StockUpdate.tupled, StockUpdate.unapply)
  }

}
