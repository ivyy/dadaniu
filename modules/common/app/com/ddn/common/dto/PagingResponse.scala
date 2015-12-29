package com.ddn.common.dto

import play.api.libs.json.{JsPath, Writes, JsValue}
import play.api.libs.functional.syntax._

/**
 * User: bigfish
 * Date: 15-12-6
 * Time: 下午1:06
 */
case class PagingResponse(paging: PagingParam, data: JsValue)

object PagingResponse {
  implicit val pagingResponseWrites: Writes[PagingResponse]
    = ((JsPath \ "paging").write[PagingParam] and (JsPath \ "data").write[JsValue])(unlift(PagingResponse.unapply))
}


