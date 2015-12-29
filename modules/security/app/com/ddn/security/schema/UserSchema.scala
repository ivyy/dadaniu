package com.ddn.security.schema

import slick.driver.JdbcProfile
import com.ddn.security.model._

/**
 * User: bigfish
 * Date: 15-12-20
 * Time: 下午3:03
 */
trait UserSchema {

  protected val driver: JdbcProfile

  import driver.api._

  class UserTable(tag: Tag) extends Table[User](tag, "USER") {

    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def name = column[String]("NAME")

    def password = column[String]("PASSWORD")

    def * = (name, password, id.?) <> ((User.apply _).tupled, User.unapply _)
  }

}
