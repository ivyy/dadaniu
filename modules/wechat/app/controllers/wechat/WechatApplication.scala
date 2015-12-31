package controllers.wechat

import javax.inject._

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import com.ddn.wechat.actor.{WechatAppActor, WechatDaemonActor}
import com.ddn.wechat.model.BaseWechatResponse
import com.ddn.wechat.server.{DummyWechatMessageHandler, WechatMessageParser}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, BodyParsers, Controller}

import scala.concurrent.Future
import scala.concurrent.duration._

/**
 * User: bigfish
 * Date: 15-12-26
 * Time: 下午5:24
 */
@Singleton
class WechatApplication @Inject()(@Named("wechat-daemon-actor") wechatDaemonActor: ActorRef)
  extends Controller {

  import WechatDaemonActor._


  implicit val timeout: Timeout = 5 seconds

  def validateWechatApp(wechatAppKey: String) = Action.async {
    implicit request =>
      val signature = request.getQueryString("signature").getOrElse("")
      val timestamp = request.getQueryString("timestamp").getOrElse("")
      val nonce = request.getQueryString("nonce").getOrElse("")
      val echoStr = request.getQueryString("echostr").getOrElse("")
      val future = wechatDaemonActor ? ValidateWechatApp(wechatAppKey, signature, timestamp, nonce, echoStr)
      future.mapTo[String].map(Ok(_))
  }

  def handleMessage(internalWechatAppName: String) = Action.async(BodyParsers.parse.xml) {

    implicit request => {
      val message = WechatMessageParser.parse(request.body)
      println("\n received message\n" + message)
      val future = wechatDaemonActor ? HandleMessage(internalWechatAppName, message)
      future.mapTo[BaseWechatResponse].map(response => Ok(response.toXml))
    }
  }

  def startWechatApp(internalWechatAppName: String) = Action.async {
    (wechatDaemonActor ? StartWechatApp(internalWechatAppName)).mapTo[String].map(result => Ok(result))
  }

  def shutdownWechatApp(internalWechatAppName: String) = Action.async {
    (wechatDaemonActor ? ShutdownWechatApp(internalWechatAppName)).mapTo[String].map(result => Ok(result))
  }
}

