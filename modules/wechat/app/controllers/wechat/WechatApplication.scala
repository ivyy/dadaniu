package controllers.wechat

import com.ddn.wechat.client.WechatClient
import com.ddn.wechat.server.{WechatMessageHandler, WechatMessageParser}
import org.apache.commons.codec.digest.DigestUtils
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc.{Action, BodyParsers, Controller}

import scala.concurrent.Future

/**
 * User: bigfish
 * Date: 15-12-26
 * Time: 下午5:24
 */
class WechatApplication extends Controller with WechatClient {

  //this shoulbe be moved to a property file or database
  private val TOKENS_MAP = Map("dadaniu" -> "dadaniu")

  def validateWechatApp(wechatAppKey: String) = Action {
    implicit request =>
      val signature = request.getQueryString("signature").getOrElse("")
      val timestamp = request.getQueryString("timestamp").getOrElse("")
      val nonce = request.getQueryString("nonce").getOrElse("")
      val echoStr = request.getQueryString("echostr").getOrElse("")
      val token = TOKENS_MAP(wechatAppKey)
      if (WechatApplication.isValidWechatConnection(signature, timestamp, nonce, token))
        Ok(echoStr)
      else
        Ok("Validate Error")
  }

  def getAccessToken(wechatAppKey: String) = Action {
    Ok(AccessToken.value)
  }

  def getWechatServerIpList(wechatAppKey: String) = Action {
    Ok(Json.toJson(getCallbackIpList))
  }

  def handleMessage(wechatAppKey: String) = Action.async(BodyParsers.parse.xml) {

    implicit request => {
      val message = WechatMessageParser.parse(request.body)
      println("\n received message\n" + message)
      val wechatResponse = WechatMessageHandler.handle(message).toXml
      println("\n response with\n" + wechatResponse)
      (Future {
        wechatResponse
      }).map(Ok(_))
    }
  }
}

object WechatApplication {

  def isValidWechatConnection(signature: String, timestamp: String, nonce: String, token: String) = {
    val str = Array(token, timestamp, nonce).sorted.mkString
    DigestUtils.sha1Hex(str) == signature
  }

  def wechatMsgCreateTime = System.currentTimeMillis / 1000
}
