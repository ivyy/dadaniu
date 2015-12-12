package com.bigbigniu.model

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads, Writes}

/**
 * User: bigfish
 * Date: 15-12-12
 * Time: 下午3:45
 */
case class Plate(code: String, shortName: String, description: String, id: Option[Long] = None)

object Plate {

  implicit val plateWrites: Writes[Plate] = (
    (JsPath \ "code").write[String]
      and (JsPath \ "shortName").write[String]
      and (JsPath \ "description").write[String]
      and (JsPath \ "id").writeNullable[Long]
    )(unlift(Plate.unapply))

  implicit val plateReads: Reads[Plate] = (
    (JsPath \ "code").read[String]
      and (JsPath \ "shortName").read[String]
      and (JsPath \ "description").read[String]
      and (JsPath \ "id").readNullable[Long]
    )(Plate.apply _)
}
