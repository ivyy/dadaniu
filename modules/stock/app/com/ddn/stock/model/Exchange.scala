package com.ddn.stock.model

import java.sql.Date

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Writes}

/**
 * User: bigfish
 * Date: 15-12-6
 * Time: 下午5:35
 */
case class Exchange(code: String, date: Date, open: Double, high: Double,
                    low: Double, close: Double, volume: Double, adjClose: Double, id:Option[Long] = None)

object Exchange {

  implicit val yahooStockExchangeInfoWrites: Writes[Exchange] =
    ((JsPath \ "code").write[String]
      and (JsPath \ "date").write[Date]
      and (JsPath \ "open").write[Double]
      and (JsPath \ "high").write[Double]
      and (JsPath \ "low").write[Double]
      and (JsPath \ "close").write[Double]
      and (JsPath \ "volume").write[Double]
      and (JsPath \ "adjClose").write[Double]
      and (JsPath \ "id").writeNullable[Long])(unlift(Exchange.unapply))
}