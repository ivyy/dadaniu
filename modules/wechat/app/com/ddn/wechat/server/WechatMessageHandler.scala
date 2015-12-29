package com.ddn.wechat.server

import com.ddn.wechat.model._
import controllers.wechat.WechatApplication

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午4:26
 */
object WechatMessageHandler {

  def handle(message: BaseWechatMessage): BaseWechatResponse = {
    message match {
      case msg: TextMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatApplication.wechatMsgCreateTime, msg.content)
      case msg: VoiceMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatApplication.wechatMsgCreateTime, "VoiceMessage Handled")
      case msg: LinkMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatApplication.wechatMsgCreateTime, "LinkMessage Handled")
      case msg: LocationMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatApplication.wechatMsgCreateTime, "LocationMessage Handled:" + msg)
      case msg: ShortVideoMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatApplication.wechatMsgCreateTime, "ShortVideoMessage Handled")
      case msg: VideoMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatApplication.wechatMsgCreateTime, "VideoMessage Handled")
      case msg: ImageMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatApplication.wechatMsgCreateTime, "ImageMessage Handled")
      case msg: ClickEventMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatApplication.wechatMsgCreateTime, "ClickEventMessage Handled")
      case msg: LocationEventMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatApplication.wechatMsgCreateTime, "LocationEventMessage Handled")
      case msg: ScanSubscribedEventMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatApplication.wechatMsgCreateTime, "LocationEventMessage Handled")
      case msg: SubscribeEventMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatApplication.wechatMsgCreateTime, "LocationEventMessage Handled")
      case msg: UnSubscribeEventMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatApplication.wechatMsgCreateTime, "LocationEventMessage Handled")
      case msg: ViewEventMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatApplication.wechatMsgCreateTime, "LocationEventMessage Handled")
      case msg: UnknownWechatMessage =>
        TextResponse(msg.fromUserName, msg.toUserName, WechatApplication.wechatMsgCreateTime, "UnknownMessage dropped")
    }
  }

}
