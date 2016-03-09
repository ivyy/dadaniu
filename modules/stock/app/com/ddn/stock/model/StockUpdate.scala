package com.ddn.stock.model

import java.sql.Date

/**
 * User: bigfish
 * Date: 16-3-6
 * Time: 上午7:51
 */
case class StockUpdate(stockCode: String, lastUpdate: Date)

//object StockUpdate {
//  implicit  val stockUpdateWrites : Writes[StockUpdate] =
//    (
//      (JsPath \ "stockCode").write[String]
//      and (JsPath \ "lastUpdate").write[LocalDate]
//      )(unlift(StockUpdate.unapply))
//}