package com.ddn.wechat.server

import com.ddn.common.util.WechatUtil
import com.ddn.wechat.model._
import controllers.wechat.WechatApplication

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午4:26
 */
object DummyWechatMessageHandler {

  def handle(message: BaseWechatMessage): BaseWechatResponse = {
    message match {
      case msg: TextMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, msg.content)
      case msg: VoiceMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "VoiceMessage Handled")
      case msg: LinkMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "LinkMessage Handled")
      case msg: LocationMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "LocationMessage Handled:" + msg)
      case msg: ShortVideoMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "ShortVideoMessage Handled")
      case msg: VideoMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "VideoMessage Handled")
      case msg: ImageMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "ImageMessage Handled")
      case msg: ClickEventMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "ClickEventMessage Handled")
      case msg: LocationEventMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "LocationEventMessage Handled")
      case msg: ScanSubscribedEventMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "LocationEventMessage Handled")
      case msg: SubscribeEventMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "LocationEventMessage Handled")
      case msg: UnSubscribeEventMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "LocationEventMessage Handled")
      case msg: ViewEventMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "LocationEventMessage Handled")
      case msg: UnknownWechatMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "UnknownMessage dropped")
    }
  }

  def textReponse(message: BaseWechatMessage, content:String) =
    TextResponse(message.fromUserName, message.toUserName, WechatUtil.currentTimeInSeconds, content)
}
