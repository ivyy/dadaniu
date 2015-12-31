package com.ddn.wechat.model

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午1:22
 */
case class LocationEventMessage(toUserName: String,
                                fromUserName: String,
                                createTime: Long,
                                messageType: String, event: String,
                                latitude: Double, longitude: Double, precision: Double)
  extends BaseWechatMessage