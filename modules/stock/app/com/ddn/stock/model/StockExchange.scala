package com.ddn.stock.model

import play.api.libs.json.{JsPath, Writes}
import play.api.libs.functional.syntax._
import java.time.LocalDate

/**
 * User: bigfish
 * Date: 15-12-6
 * Time: 下午5:35
 */
case class StockExchange(date:LocalDate, open: Double,
                                  high: Double, low: Double, close: Double,
                                  volume:Double, adjClose:Double)

object StockExchange {

  implicit val yahooStockExchangeInfoWrites: Writes[StockExchange] =
    ((JsPath \ "date").write[LocalDate] and (JsPath \ "open").write[Double]
      and (JsPath \ "high").write[Double]
      and (JsPath \ "low").write[Double]
      and (JsPath \ "close").write[Double]
      and (JsPath \ "volume").write[Double]
      and (JsPath \ "adjClose").write[Double])(unlift(StockExchange.unapply))
}