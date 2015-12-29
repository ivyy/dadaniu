package com.ddn.stock.schema

import com.ddn.stock.model.Stock
import slick.driver.JdbcProfile

/**
 * User: bigfish
 * Date: 15-12-10
 * Time: 上午6:57
 */
trait StockSchema {

  protected val driver: JdbcProfile

  import driver.api._

  class StockTable(tag: Tag) extends Table[Stock](tag, "STOCK") {
    def code = column[String]("CODE", O.PrimaryKey)
    def shortName = column[String]("SHORT_NAME")
    def fullName = column[String]("FULL_NAME")
    def industry = column[String]("INDUSTRY")
    def website = column[String]("WEBSITE")

    def * = (code, shortName, fullName, industry, website.?) <>
      ((Stock.apply _).tupled, Stock.unapply _)
  }

}
