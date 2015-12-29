package com.ddn.wechat.model

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午12:17
 */
case class ImageMessage(toUserName: String, fromUserName: String,
                        createTime: Long, messageType: String,
                        pictureUrl: String, mediaId: String, messageId: String)
  extends BaseWechatMessage(toUserName, fromUserName, createTime, messageType)
