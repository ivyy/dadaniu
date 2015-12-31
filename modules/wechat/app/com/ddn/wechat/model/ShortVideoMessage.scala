package com.ddn.wechat.model

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午12:43
 */
case class ShortVideoMessage(toUserName: String, fromUserName: String,
                             createTime: Long, messageType: String, mediaId: String,
                             thumbMediaId: String, messageId: String)
  extends BaseWechatMessage