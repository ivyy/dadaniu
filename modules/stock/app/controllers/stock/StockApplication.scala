package controllers.stock

import javax.inject.Inject

import com.bigbigniu.stock.service.YahooStockDataService
import com.ddn.common.dto.{PagingParam, PagingResponse}
import com.ddn.stock.model.{Stock, YahooStockExchangeInfo}
import com.ddn.stock.schema.StockSchema
import play.api.cache._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc._
import slick.driver.JdbcProfile

import scala.concurrent.duration._

/**
 * User: bigfish
 * Date: 15-12-6
 * Time: 下午4:02
 */
class StockApplication @Inject()(cache: CacheApi, dbConfigProvider: DatabaseConfigProvider)
  extends Controller with StockSchema with HasDatabaseConfig[JdbcProfile] {

  import driver.api._

  val Stocks = TableQuery[StockTable]

  val dbConfig = dbConfigProvider.get[JdbcProfile]


  def loadStockExchangeData(stockCode: String, fromDate: String, toDate: String) = Action {

    val exchangeDataList = cache.getOrElse[List[YahooStockExchangeInfo]](stockCode, 1.minutes) {
      YahooStockDataService.loadFromYahoo(stockCode, fromDate, toDate)
    }

    Ok(Json.toJson(exchangeDataList))
  }


  //return the main page
  def mainPage = Action {
    Ok(views.html.stockMgt())
  }

  def stockList = Action.async {
    implicit request => {
      dbConfig.db.run(Stocks.result).map(result => Ok(Json.toJson(result)))
    }
  }

  def stockPagingList = Action.async(BodyParsers.parse.json) {
    implicit request => {
      val oldPagingParam = request.body.validate[PagingParam].getOrElse(PagingParam.DEFAULT)
      var newPagingParam = oldPagingParam
      dbConfig.db.run(Stocks.length.result).onSuccess {
        case length => newPagingParam = oldPagingParam.copy(totalItems = length)
      }

      dbConfig.db.run(Stocks.drop(oldPagingParam.start).take(oldPagingParam.itemsPerPage).result).map(result => {
        val resp = PagingResponse(newPagingParam, Json.toJson(result.toList))
        Ok(Json.toJson(resp))
      })
    }
  }

  def addStock = Action.async(BodyParsers.parse.json) {
    implicit request => {
      val newStock = request.body.validate[Stock].get
      dbConfig.db.run(Stocks += newStock).map(_ => {
        Ok(Json.toJson(newStock))
      })
    }
  }

  def deleteStock(code: String) = Action.async(BodyParsers.parse.json) {
    implicit request => {
      dbConfig.db.run(Stocks.filter(_.code === code).delete).map(_ => {
        Ok(code)
      })
    }
  }

  def updateStock(code: String) = Action.async(BodyParsers.parse.json) {
    implicit request => {
      val updated = request.body.validate[Stock].get
      dbConfig.db.run(Stocks.filter(_.code === code).update(updated)).map(_ => Ok(Json.toJson(updated)))
    }
  }
}
