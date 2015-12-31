package com.ddn.wechat.actor

import javax.inject._

import akka.actor._
import com.ddn.common.util.WechatUtil
import com.google.inject.assistedinject.Assisted
import play.api.libs.json.JsArray
import play.api.libs.ws
import play.api.libs.ws.WSClient

import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * User: bigfish
 * Date: 15-12-29
 * Time: 下午1:58
 */
object WechatClientActor {

  trait Factory {
    def apply(@Assisted("appId") appId:String, @Assisted("appSecret") appSecret:String):Actor
  }

  case class RefreshAccessToken(appId: String, appSecret: String)

  case class ValidToken(token: String, expiresIn: Int)

  case class RefreshAccessTokenError(errCode: String, errMsg: String)

  case object GetAccessToken

}


class WechatClientActor @Inject()(ws: WSClient, @Assisted("appId") appId:String, @Assisted("appSecret") appSecret:String)
  extends Actor {

  import WechatClientActor._

  private val APP_ID = "wx5a897dc176520b54"
  private val APP_SECRET = "9d62a4c300a319ae803709f81151ce73"
  private val ACCESS_TOKEN_URL = s"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=$APP_ID&secret=$APP_SECRET"
  private val GET_IP_LIST_URL = s"https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token="

  //private def timeout = lastUpdatedTimeInSeconds + expiresIn > WechatApplication.wechatMsgCreateTime


//  def getCallbackIpList:List[String] = {
//    val url = GET_IP_LIST_URL +
//    val futureResult = ws.url(url).get().map {
//      response =>
//        if (response.body.contains("error_code")) {
//          Nil
//        } else {
//          (response.json \ "ip_list").as[JsArray].value.map(_.toString).toList
//        }
//    }
//
//    Await.result(futureResult, Duration.Inf)
//  }

  override def receive: Actor.Receive = {

    case RefreshAccessToken(appId, appSecret) =>
      val reply = ws.url(ACCESS_TOKEN_URL).get().map {
        response =>
          if (response.body.contains("access_token")) {
            ValidToken((response.json \ "access_token").as[String], (response.json \ "expires_in").as[Int])
          } else {
            RefreshAccessTokenError((response.json \ "errcode").as[String], (response.json \ "errmsg").as[String])
          }
      }
      sender() ! reply
    case _ => println("receive unknow message")
  }
}
