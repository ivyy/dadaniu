package com.ddn.wechat.model

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午5:01
 */
case class VoiceResponse(toUserName: String,
                         fromUserName: String,
                         createTime: Long,
                         mediaId: String)
  extends BaseWechatResponse(toUserName, fromUserName, createTime, WechatMessageType.VOICE) {

  override def toXml =
    scala.xml.Utility.trim(<xml>
      <ToUserName>
        {scala.xml.PCData(toUserName)}
      </ToUserName>
      <FromUserName>
        {scala.xml.PCData(fromUserName)}
      </FromUserName>
      <CreateTime>{createTime}</CreateTime>
      <MsgType>
        <![CDATA[voice]]>
      </MsgType>
      <Voice>
        <MediaId>
          {scala.xml.PCData(mediaId)}
        </MediaId>
      </Voice>
    </xml>
    )

}
