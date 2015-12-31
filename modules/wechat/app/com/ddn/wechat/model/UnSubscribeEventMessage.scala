package com.ddn.wechat.model

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午1:10
 */
case class UnSubscribeEventMessage(toUserName: String,
                                 fromUserName: String,
                                 createTime: Long,
                                 messageType: String, event:String)
extends BaseWechatMessage
