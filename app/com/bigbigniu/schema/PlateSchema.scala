package com.bigbigniu.schema

import com.bigbigniu.model.Plate
import slick.driver.JdbcProfile

/**
 * User: bigfish
 * Date: 15-12-12
 * Time: 下午3:53
 */
trait PlateSchema {

  protected val driver: JdbcProfile

  import driver.api._

  class PlateTable(tag: Tag) extends Table[Plate](tag, "PLATE") {

    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def code = column[String]("CODE")

    def shortName = column[String]("SHORT_NAME")

    def description = column[String]("DESCRIPTION")

    def * = (code, shortName, description, id.?) <>
      ((Plate.apply _).tupled, Plate.unapply _)
  }

}
