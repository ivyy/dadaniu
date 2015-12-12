package com.bigbigniu.schema

/**
 * User: bigfish
 * Date: 15-10-6
 * Time: 上午11:53
 */

import com.bigbigniu.model.User
import slick.driver.JdbcProfile

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
