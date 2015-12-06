package model

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads, Writes}

/**
 * User: bigfish
 * Date: 15-10-6
 * Time: 上午11:57
 */

/*
  In order to be used in play json lib, the option[T] field must be put at last
 */
case class User(name: String, password: String, id: Option[Long] = None)

object User {

  implicit val userWrites: Writes[User] =
    (
      (JsPath \ "name").write[String]
        and (JsPath \ "password").write[String]
        and (JsPath \ "id").writeNullable[Long]
      )(unlift(User.unapply))

  implicit val userReads: Reads[User] = (
    (JsPath \ "name").read[String]
      and (JsPath \ "password").read[String]
      and (JsPath \ "id").readNullable[Long]
    )(User.apply _)

}
