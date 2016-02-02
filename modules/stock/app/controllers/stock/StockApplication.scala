package controllers.stock

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

import com.ddn.common.dto.{PagingParam, PagingResponse}
import com.ddn.stock.analyzer.ExchangeAnalyzer
import com.ddn.stock.model.Stock
import com.ddn.stock.schema.StockSchema
import com.ddn.stock.service.StockExchangeService
import play.api.cache._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc._
import slick.driver.JdbcProfile

/**
 * User: bigfish
 * Date: 15-12-6
 * Time: 下午4:02
 */
class StockApplication @Inject()(cache: CacheApi, dbConfigProvider: DatabaseConfigProvider,
                                 stockExchangeService: StockExchangeService)
  extends Controller with StockSchema with HasDatabaseConfig[JdbcProfile] {

  @Inject
  var analyser:ExchangeAnalyzer = null

  import driver.api._

  val Stocks = TableQuery[StockTable]

  val dbConfig = dbConfigProvider.get[JdbcProfile]


  def loadStockExchangeData(stockCode: String, from: Option[String], to: Option[String]) = Action {
    if (from.isEmpty || to.isEmpty) {
      Ok(Json.toJson(stockExchangeService.all(stockCode)))
    } else {
      val fromDate = LocalDate.parse(from.get, DateTimeFormatter.ofPattern("yyyyMMdd"))
      val toDate = LocalDate.parse(to.get, DateTimeFormatter.ofPattern("yyyyMMdd"))
      Ok(Json.toJson(stockExchangeService.slice(stockCode, fromDate, toDate)))
    }

  }

  def simpleMovingAverage(stockCode:String, days:Int, range:Int) = Action {
    implicit request =>
      val array = analyser.simpleMovingAverage(stockCode, days)(range)
      Ok(Json.toJson(array))
  }

  def weightedMovingAverage(stockCode:String, days:Int, range:Int) = Action {
    implicit request =>
      val array = analyser.weightedMovingAverage(stockCode, days)(range)
      Ok(Json.toJson(array))
  }


  //return the main page
  def mainPage = Action {
    Ok(views.html.stockMgt())
  }

  //return the chart page
  def stockChart = Action {
    Ok(views.html.stockChart())
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
