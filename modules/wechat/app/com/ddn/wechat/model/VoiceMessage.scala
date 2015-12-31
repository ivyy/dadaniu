package com.ddn.wechat.model

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午12:31
 */
case class VoiceMessage(toUserName:String, fromUserName:String,
                        createTime:Long, messageType:String,
                        mediaId:String, format:String, messageId:String, recognition:String)
extends BaseWechatMessage
