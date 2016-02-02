package com.ddn.wechat.actor

import javax.inject._

import akka.actor.Actor
import akka.event.Logging
import com.ddn.common.util.WechatUtil
import com.ddn.wechat.actor.WechatClientActor.{RefreshAccessTokenError, ValidToken}
import com.ddn.wechat.actor.WechatDaemonActor.HandleMessage
import com.ddn.wechat.server.DummyWechatMessageHandler
import com.google.inject.assistedinject.Assisted
import org.apache.commons.codec.digest.DigestUtils
import play.api.libs.concurrent.InjectedActorSupport

/**
 * User: bigfish
 * Date: 15-12-30
 * Time: 下午4:55
 */


class WechatAppActor @Inject()(clientActorFactory: WechatClientActor.Factory,
                               @Assisted("appId") appId: String,
                               @Assisted("appSecret") appSecret: String,
                               @Assisted("token") token: String)

  extends Actor with InjectedActorSupport {

  import WechatAppActor._

  val log = Logging(context.system, this)

  val clientActor = injectedChild(clientActorFactory(appId, appSecret), appId)

  @volatile
  private var validToken: ValidToken = null

  @volatile
  private var lastUpdatedTime = WechatUtil.currentTimeInSeconds

  private def validate(signature: String, timestamp: String, nonce: String) = {
    val str = Array(token, timestamp, nonce).sorted.mkString
    DigestUtils.sha1Hex(str) == signature
  }

  override def receive: Receive = {

    case Validate(signature, timestamp, nonce, echoStr) =>
      if (validate(signature, timestamp, nonce)) {
        sender() ! echoStr
      } else {
        sender() ! ""
      }
    case HandleMessage(_, message) =>
      println("handling message" + message)
      sender() ! DummyWechatMessageHandler.handle(message)
    case token: ValidToken =>
      validToken = token
      lastUpdatedTime = WechatUtil.currentTimeInSeconds
    case error: RefreshAccessTokenError =>
      println(error)
      println("Get acess token error, fall into SERVER_ONLY mode")
    case message@_ =>
      log.info("received unknown message: \n" + message)
  }
}

object WechatAppActor {

  case class Validate(signature: String, timestamp: String, nonce: String, echoStr: String)

  trait Factory {
    def apply(@Assisted("appId") appId: String,
              @Assisted("appSecret") appSecret: String,
              @Assisted("token") token: String): Actor
  }

}
