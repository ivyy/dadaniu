package com.bigbigniu.model

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads, Writes}

/**
 * User: bigfish
 * Date: 15-12-10
 * Time: 上午6:44
 */
case class Stock(code:String, shortName:String, fullName:String,
                 industry:String, website:Option[String] = None)

object Stock {

  implicit val stockWrites: Writes[Stock] = (
    (JsPath \ "code").write[String]
    and (JsPath \ "shortName").write[String]
    and (JsPath \ "fullName").write[String]
    and (JsPath \ "industry").write[String]
    and (JsPath \ "website").writeNullable[String]
    )(unlift(Stock.unapply))

  implicit val stockReads: Reads[Stock] = (
    ((JsPath \ "code").read[String]
      and (JsPath \ "shortName").read[String]
      and (JsPath \ "fullName").read[String]
      and (JsPath \ "industry").read[String]
      and (JsPath \ "website").readNullable[String])
    )(Stock.apply _)
}


