package com.ddn.wechat.model

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午1:19
 */
case class ClickEventMessage(toUserName: String,
                             fromUserName: String,
                             createTime: Long,
                             messageType: String, event:String, eventKey:String)
extends BaseWechatMessage