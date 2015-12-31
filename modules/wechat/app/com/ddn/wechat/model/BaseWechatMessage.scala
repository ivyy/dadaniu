package com.ddn.wechat.model

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 上午11:14
 */
abstract class BaseWechatMessage {
  def toUserName:String
  def fromUserName:String
  def createTime:Long
  def messageType:String
}
