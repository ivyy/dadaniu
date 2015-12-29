package com.ddn.wechat.client

import javax.inject.Inject

import controllers.wechat.WechatApplication
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsArray
import play.api.libs.ws.WSClient

import scala.concurrent.Await
import scala.concurrent.duration.Duration


/**
 * WechatClient is responsible for communication with tencent wechat server, for example,
 * refresh the access token, publish menu, etc.
 */


trait WechatClient {

  //NOTE: This is a test account
//  private val APP_ID = "wx25d8010190a2ef84"
//  private val APP_SECRET = "6b8344941ae51a946516e826e4f4a30c"
    private val APP_ID = "wx5a897dc176520b54"
    private val APP_SECRET = "9d62a4c300a319ae803709f81151ce73"
  private val ACCESS_TOKEN_URL = s"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=$APP_ID&secret=$APP_SECRET"
  private val GET_IP_LIST_URL = s"https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token="

  @Inject private var ws: WSClient = null

  //according to the wechat document, this should be centralized.
  //the best way is to use akka to control the access
  object AccessToken {
    private var token = ""
    private var expiresIn = 0
    private var lastUpdatedTimeInSeconds = 0L

    private def timeout = lastUpdatedTimeInSeconds + expiresIn > WechatApplication.wechatMsgCreateTime

    def value: String = {

      if (token.isEmpty || timeout) {
        //renew the token
        val futureResult = ws.url(ACCESS_TOKEN_URL).get().map {
          response =>
            val result = response.json
            if (response.body.contains("access_token")) {
              token = (result \ "access_token").as[String]
              expiresIn = (result \ "expires_in").as[Int]
              lastUpdatedTimeInSeconds = WechatApplication.wechatMsgCreateTime
            } else {
              val errorCode = (result \ "errcode").as[String]
              val errMessage = (result \ "errmsg").as[String]
              println(s"====unable to refresh access token from wechat server:$errorCode, $errMessage")
            }
            token
        }
        Await.result(futureResult, Duration.Inf)
      } else token
    }
  }

  def getCallbackIpList:List[String] = {
    val url = GET_IP_LIST_URL + AccessToken.value
    val futureResult = ws.url(url).get().map {
      response =>
        if (response.body.contains("error_code")) {
          Nil
        } else {
          (response.json \ "ip_list").as[JsArray].value.map(_.toString).toList
        }
    }

    Await.result(futureResult, Duration.Inf)
  }

}
