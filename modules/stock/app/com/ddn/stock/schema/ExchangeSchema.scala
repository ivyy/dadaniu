package com.ddn.stock.schema

import java.sql.Date

import com.ddn.stock.model.Exchange
import io.strongtyped.active.slick.{Lens, EntityActions}
import slick.ast.BaseTypedType
import slick.driver.JdbcProfile

/**
 * User: bigfish
 * Date: 16-3-6
 * Time: 上午7:49
 */
trait ExchangeSchema {

  protected val driver: JdbcProfile

  import driver.api._

  class ExchangeTable(tag: Tag) extends Table[Exchange](tag, "EXCHANGE") {

    def code = column[String]("CODE", O.PrimaryKey)

    def date = column[Date]("DATE")

    def open = column[Double]("OPEN")

    def high = column[Double]("HIGH")

    def low = column[Double]("LOW")

    def close = column[Double]("CLOSE")

    def volume = column[Double]("VOLUME")

    def adjClose = column[Double]("ADJ_CLOSE")

    def id = column[Long]("ID")

    def * = (code, date, open, high, low, close, volume, adjClose, id.?) <> ((Exchange.apply _).tupled, Exchange.unapply)
  }

}

