package com.ddn.wechat.model

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午12:23
 */
case class LinkMessage(toUserName: String,
                       fromUserName: String,
                       createTime: Long,
                       messageType: String,
                       title: String,
                       description: String,
                       url: String,
                       messageId: String)
  extends BaseWechatMessage

