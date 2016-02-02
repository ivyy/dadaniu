package com.ddn.wechat

import com.ddn.wechat.actor.{WechatAppActor, WechatClientActor, WechatDaemonActor}
import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport

class WechatModule extends AbstractModule with AkkaGuiceSupport {
  override def configure(): Unit = {
    bindActor[WechatDaemonActor]("wechat-daemon-actor")
    bindActorFactory[WechatClientActor, WechatClientActor.Factory]
    bindActorFactory[WechatAppActor, WechatAppActor.Factory]
  }
}
