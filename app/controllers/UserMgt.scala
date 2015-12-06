package controllers

import javax.inject.Inject

import dto.PagingParam._
import dto.{PagingParam, PagingResponse}
import model._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc.{Action, BodyParsers, Controller}
import schema.UserSchema
import slick.driver.JdbcProfile

import PagingResponse._
import User._

import scala.concurrent.Await

/**
 * User: bigfish
 * Date: 15-10-6
 * Time: 下午1:26
 */
class UserMgt @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Controller
with UserSchema with HasDatabaseConfig[JdbcProfile] {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import driver.api._

  val Users = TableQuery[UserTable]
  /*
    returns the main user management page
   */
  def mainPage = Action {
    Ok(views.html.userMgt())
  }

  def usersList = Action.async {
    implicit request => {
      dbConfig.db.run(Users.result).map(res =>
        Ok(Json.toJson(res.toList))
      )
    }
  }

  def usersPagingList = Action.async(BodyParsers.parse.json) {
    implicit request => {
      val pagingParam = request.body.validate[PagingParam].getOrElse(PagingParam.DEFAULT)

      var newPaging  = pagingParam

      val future = dbConfig.db.run(Users.length.result)

      future.onSuccess {
        case length =>
          newPaging = pagingParam.copy(totalItems = length)
      }

      dbConfig.db.run(Users.drop(pagingParam.start).take(pagingParam.itemsPerPage).result).map(result => {
        val resp = PagingResponse(newPaging, Json.toJson(result.toList))
        Ok(Json.toJson(resp))
      })
    }
  }

  def addUser = Action.async(BodyParsers.parse.json) {

    implicit request => {
      val user = request.body.validate[User].get
      val insertQuery = (Users returning Users.map(_.id)) += user

      dbConfig.db.run(insertQuery).map {
        result =>
          Ok(Json.toJson(user.copy(id = Some(result))))
      }
    }
  }

  def deleteUser(id: Long) = Action {
    val query = Users.filter(_.id === id).delete
    dbConfig.db.run(query)
    Ok("ok")
  }

  def deleteUsers = Action.async(BodyParsers.parse.json) {
    implicit request => {
      val userIds = request.body.validate[List[Long]].get
      val deleteQuery = Users.filter(_.id.inSet(userIds)).delete
      dbConfig.db.run(deleteQuery).map {
        result =>
          Ok(Json.toJson(userIds))
      }
    }
  }

  def updateUser(id: Long) = Action.async(BodyParsers.parse.json) {
    implicit request => {
      val newUser = request.body.validate[User].get
      val query = Users.filter(_.id === newUser.id).update(newUser)
      dbConfig.db.run(query).map {
        result => Ok(Json.toJson(newUser.id))
      }
    }
  }
}
