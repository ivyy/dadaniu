package com.ddn.wechat.model

import scala.xml.NodeSeq

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午4:40
 */
abstract class BaseWechatResponse(toUserName: String, fromUserName: String,
                                  createTime: Long, messageType: String) {
    def toXml:NodeSeq
}
