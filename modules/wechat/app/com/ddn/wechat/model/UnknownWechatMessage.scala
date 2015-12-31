package com.ddn.wechat.model

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午12:03
 */
case class UnknownWechatMessage(toUserName:String, fromUserName:String, messageType:String, createTime:Long)
  extends BaseWechatMessage
