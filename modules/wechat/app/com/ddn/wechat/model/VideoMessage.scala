package com.ddn.wechat.model

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午12:48
 */
case class VideoMessage(toUserName: String, fromUserName: String,
                        createTime: Long, messageType: String,
                         mediaId:String, thumbMediaId:String,
                         messageId:String)
extends BaseWechatMessage
