package com.ddn.wechat.model

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 上午11:46
 */

case class TextMessage(toUserName: String,
                       fromUserName: String,
                       createTime: Long,
                       messageType: String,
                       content: String,
                       msgId: String) extends BaseWechatMessage