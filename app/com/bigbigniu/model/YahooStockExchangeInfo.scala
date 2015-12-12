package com.bigbigniu.model

import play.api.libs.json.{JsPath, Writes}
import play.api.libs.functional.syntax._

/**
 * User: bigfish
 * Date: 15-12-6
 * Time: 下午5:35
 */
case class YahooStockExchangeInfo(date:String, open: Double,
                                  high: Double, low: Double, close: Double,
                                  volume:Double, adjClose:Double)

object YahooStockExchangeInfo {

  implicit val yahooStockExchangeInfoWrites: Writes[YahooStockExchangeInfo] =
    ((JsPath \ "date").write[String] and (JsPath \ "open").write[Double]
      and (JsPath \ "high").write[Double]
      and (JsPath \ "low").write[Double]
      and (JsPath \ "close").write[Double]
      and (JsPath \ "volume").write[Double]
      and (JsPath \ "adjClose").write[Double])(unlift(YahooStockExchangeInfo.unapply))
}