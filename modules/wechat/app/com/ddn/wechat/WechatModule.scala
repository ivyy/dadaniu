package com.ddn.wechat

import com.ddn.wechat.actor.{WechatAppActor, WechatClientActor, WechatDaemonActor}
import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport

/**
 * User: bigfish
 * Date: 15-12-30
 * Time: 上午11:34
 */
class WechatModule extends AbstractModule with AkkaGuiceSupport {
  override def configure(): Unit = {
    bindActor[WechatDaemonActor]("wechat-daemon-actor")
    bindActorFactory[WechatClientActor, WechatClientActor.Factory]
    bindActorFactory[WechatAppActor, WechatAppActor.Factory]
  }
}
