package controllers.stock

import javax.inject.Inject

import com.ddn.stock.model.Plate
import com.ddn.stock.schema.PlateSchema
import play.api.db.slick.{HasDatabaseConfig, DatabaseConfigProvider}
import play.api.libs.json.Json
import play.api.mvc.{Action, BodyParsers, Controller}
import slick.driver.JdbcProfile
import play.api.libs.concurrent.Execution.Implicits.defaultContext

/**
 * User: bigfish
 * Date: 15-12-12
 * Time: 下午3:32
 */
class PlateApplication @Inject()(dbConfigProvider: DatabaseConfigProvider)
  extends Controller with PlateSchema with HasDatabaseConfig[JdbcProfile] {

  import driver.api._

  val Plates = TableQuery[PlateTable]

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  def mainPage = Action {
    Ok(views.html.plateMgt())
  }

  def plateList = Action.async {
    implicit request => {
      dbConfig.db.run(Plates.result).map(result => Ok(Json.toJson(result)))
    }
  }

  def addPlate = Action.async(BodyParsers.parse.json) {
    implicit request => {
      val plate = request.body.validate[Plate].get
      dbConfig.db.run((Plates returning Plates.map(_.id)) += plate).map(result => {
        Ok(Json.toJson(plate.copy(id = Some(result))))
      })
    }
  }

  def deletePlate(id: Long) = Action.async(BodyParsers.parse.json) {
    implicit request => {
      dbConfig.db.run(Plates.filter(_.id === id).delete).map(_ => {
        Ok("ok")
      })
    }
  }

  def updatePlate(id: Long) = Action.async(BodyParsers.parse.json) {
    implicit request => {
      val updated = request.body.validate[Plate].get
      dbConfig.db.run(Plates.filter(_.id === id).update(updated)).map(_ => Ok(Json.toJson(updated)))
    }
  }
}
