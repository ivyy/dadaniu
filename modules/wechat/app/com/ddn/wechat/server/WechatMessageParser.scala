package com.ddn.wechat.server

import scala.xml.NodeSeq

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 上午11:44
 */
object WechatMessageParser {

  import com.ddn.wechat.model._

  private def toUtf8(text:String) = new String(text.getBytes("ISO8859_1"), "UTF-8")

  def parse(message: NodeSeq): BaseWechatMessage = {

    val fromUserName = (message \ "FromUserName").text
    val toUserName = (message \ "ToUserName").text
    val messageType = (message \ "MsgType").text
    val createTime = (message \ "CreateTime").text.toLong

    if (messageType == WechatMessageType.TEXT) {
      val content = toUtf8((message \ "Content").text)
      val messageId = (message \ "MsgId").text
      TextMessage(toUserName, fromUserName, createTime, messageType, content, messageId)
    } else if (messageType == WechatMessageType.IMAGE) {
      val pictureUrl = (message \ "PicUrl").text
      val mediaId = (message \ "MediaId").text
      val messageId = (message \ "MsgId").text
      ImageMessage(toUserName, fromUserName, createTime, messageType, pictureUrl, mediaId, messageId)
    } else if (messageType == WechatMessageType.LINK) {
      val title = (message \ "Title").text
      val description = toUtf8((message \ "Description").text)
      val url = (message \ "Url").text
      val messageId = (message \ "MsgId").text

      LinkMessage(toUserName, fromUserName, createTime, messageType, title, description, url, messageId)
    } else if (messageType == WechatMessageType.VOICE) {
      val mediaId = (message \ "MediaId").text
      val messageId = (message \ "MsgId").text
      val format = (message \ "Format").text
      val recognition = toUtf8((message \ "Recognition").text)
      VoiceMessage(toUserName, fromUserName, createTime, messageType, mediaId, format, messageId, recognition)
    } else if (messageType == WechatMessageType.LOCATION) {
      val locationX = (message \ "Location_X").text.toDouble
      val locationY = (message \ "Location_Y").text.toDouble
      val scale = (message \ "Scale").text.toDouble
      val label = toUtf8((message \ "Label").text)
      val messageId = (message \ "MsgId").text
      LocationMessage(toUserName, fromUserName, createTime, messageType, locationX, locationY, scale, label, messageId)
    } else if (messageType == WechatMessageType.SHORT_VIDEO) {
      val thumbMediaId = (message \ "ThumbMediaId").text
      val messageId = (message \ "MsgId").text
      val mediaId = (message \ "MediaId").text
      ShortVideoMessage(toUserName, fromUserName, createTime, messageType, mediaId, thumbMediaId, messageId)
    } else if (messageType == WechatMessageType.VIDEO) {
      val thumbMediaId = (message \ "ThumbMediaId").text
      val messageId = (message \ "MsgId").text
      val mediaId = (message \ "MediaId").text
      VideoMessage(toUserName, fromUserName, createTime, messageType, mediaId, thumbMediaId, messageId)
    } else if (messageType == WechatMessageType.EVENT) {
      val eventType = (message \ "Event").text
      if (eventType == WechatEventType.SUBSCRIBE) {
        //note: subscribe event can come from two sources: when user scan a QR code, or when user search the
        //account and subscribe
        val eventKey = (message \ "EventKey").text
        val ticket = (message \ "Ticket").text
        SubscribeEventMessage(toUserName, fromUserName, createTime, messageType, eventType, eventKey, ticket)
      } else if (eventType == WechatEventType.UNSUBSCRIBE) {
        UnSubscribeEventMessage(toUserName, fromUserName, createTime, messageType, eventType)
      } else if (eventType == WechatEventType.SCAN) {
        //in this case user must have subscribe to the account
        val eventKey = (message \ "EventKey").text
        val ticket = (message \ "Ticket").text
        ScanSubscribedEventMessage(
          toUserName, fromUserName, createTime, messageType,
          eventType, eventKey, ticket
        )
      } else if (eventType == WechatEventType.CLICK) {
        val eventKey = (message \ "EventKey").text
        ClickEventMessage(toUserName, fromUserName, createTime, messageType, eventType, eventKey)
      } else if (eventType == WechatEventType.LOCATION) {
        val latitude = (message \ "Latitude").text.toDouble
        val longitude = (message \ "Longitude").text.toDouble
        val precision = (message \ "Precision").text.toDouble
        LocationEventMessage(toUserName, fromUserName, createTime, messageType, eventType, latitude, longitude, precision)
      } else if (eventType == WechatEventType.VIEW) {
        val eventKey = (message \ "EventKey").text
        ViewEventMessage(toUserName, fromUserName, createTime, messageType, eventType, eventKey)
      } else {
        UnknownWechatMessage(toUserName, fromUserName, messageType, createTime)
      }
    } else {
      UnknownWechatMessage(toUserName, fromUserName, messageType, createTime)
    }

  }
}