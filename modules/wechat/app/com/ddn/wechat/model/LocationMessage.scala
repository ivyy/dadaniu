package com.ddn.wechat.model

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午12:37
 */
case class LocationMessage(toUserName:String, fromUserName:String, createTime:Long, messageType:String,
                            locationX:Double, locationY:Double, scale:Double, label:String, messageId:String)
extends BaseWechatMessage
