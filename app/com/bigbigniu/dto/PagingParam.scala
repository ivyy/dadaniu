package com.bigbigniu.dto

import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
 * User: bigfish
 * Date: 15-12-6
 * Time: 上午11:52
 */
case class PagingParam(currentPage: Int, itemsPerPage: Int, totalItems:Int) {
  def start = (currentPage - 1) * itemsPerPage
}

object PagingParam {

  val DEFAULT_ITEMS_PER_PAGE = 20

  val DEFAULT = PagingParam(1,DEFAULT_ITEMS_PER_PAGE, 0)

  implicit val pagingParamWrites: Writes[PagingParam] = (

    ((JsPath \ "currentPage").write[Int]
      and (JsPath \ "itemsPerPage").write[Int]
      and (JsPath \ "totalItems").write[Int])(unlift(PagingParam.unapply))
    )

    implicit val pagingParamReads: Reads[PagingParam] = (
      ((JsPath \ "currentPage").read[Int]
        and (JsPath \ "itemsPerPage").read[Int]
        and (JsPath \ "totalItems").read[Int])(PagingParam.apply _)
      )
}
