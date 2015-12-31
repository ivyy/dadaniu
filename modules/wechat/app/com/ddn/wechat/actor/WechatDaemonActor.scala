package com.ddn.wechat.actor

import javax.inject.Inject

import akka.actor.{Actor, ActorRef, PoisonPill, Props}
import akka.event.Logging
import com.ddn.wechat.model.BaseWechatMessage
import com.ddn.wechat.server.DummyWechatMessageHandler
import play.api.libs.concurrent.InjectedActorSupport

import scala.collection._


/**
 * this is a daemon actor that is in charge of:
 *
 * 1. start and shutdown an wechat application
 * 2. relay messages to different actor app actor
 */
class WechatDaemonActor @Inject() (wechatAppActorFactory: WechatAppActor.Factory)
  extends Actor with InjectedActorSupport {

  import WechatAppActor._
  import WechatDaemonActor._

  val log = Logging(context.system, this)

  val wechatAppActors = mutable.Map[String, ActorRef]()

  //this should be loaded from database or configuration
  val apps = Map("dadaniu" ->("wx25d8010190a2ef84", "6b8344941ae51a946516e826e4f4a30c", "dadaniu"),
    "meishiqinzi" ->("wx5a897dc176520b54", "9d62a4c300a319ae803709f81151ce73", "meishiqinzi"))

  override def receive: Receive = {
    case message@ValidateWechatApp(appName, signature, timestamp, nonce, echoStr) =>
      val actor = wechatAppActors(appName)
      actor forward Validate(signature, timestamp, nonce, echoStr)
    case StartWechatApp(internalWechatAppName) =>
      println("starting wechat application " + internalWechatAppName)

      val appId = apps(internalWechatAppName)._1
      val appSecret = apps(internalWechatAppName)._2
      val token = apps(internalWechatAppName)._3
      if (!wechatAppActors.contains(internalWechatAppName)) {
        val actor = injectedChild(wechatAppActorFactory(appId, appSecret, token), internalWechatAppName)
        sender() ! s"start $internalWechatAppName with success"
        wechatAppActors.put(internalWechatAppName, actor)
      } else {
        sender() ! s"$internalWechatAppName already started"
      }
    case msg@HandleMessage(internalWechatAppName, message) =>
        if (wechatAppActors.contains(internalWechatAppName)) {
          val actor = wechatAppActors(internalWechatAppName)
          actor.forward(msg)
        } else {
          sender() ! DummyWechatMessageHandler.textReponse(message, s"wechat app $internalWechatAppName not started")
        }
    case ShutdownWechatApp(internalWechatAppName) =>
      println("shutting down wechat application: " + internalWechatAppName)
      wechatAppActors(internalWechatAppName) ! PoisonPill
      wechatAppActors.remove(internalWechatAppName)
      sender() ! s"shutdown $internalWechatAppName with success"
    case _ => log.info("received unknown message")
  }

}

object WechatDaemonActor {

  def props = Props[WechatDaemonActor]

  case class StartWechatApp(internalWechatAppName: String)

  case class ShutdownWechatApp(internalWechatAppName: String)

  case class ValidateWechatApp(internalWechatAppName: String, signature: String, timestamp: String, nonce: String, echoStr: String)

  case class HandleMessage(internalWechatAppName:String, message: BaseWechatMessage)

}
