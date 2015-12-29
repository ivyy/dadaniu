package com.ddn.wechat.model

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午4:56
 */
case class ImageResponse(toUserName: String, fromUserName: String,
                         createTime: Long, mediaId: String)
  extends BaseWechatResponse(toUserName, fromUserName, createTime, WechatMessageType.IMAGE) {
  override def toXml =
    scala.xml.Utility.trim(<xml>
      <ToUserName>
        {scala.xml.PCData(toUserName)}
      </ToUserName>
      <FromUserName>
        {scala.xml.PCData(fromUserName)}
      </FromUserName>
      <CreateTime>
        {createTime}
      </CreateTime>
      <MsgType>
        <![CDATA[image]]>
      </MsgType>
      <Image>
        <MediaId>
          {scala.xml.PCData(mediaId)}
        </MediaId>
      </Image>
    </xml>)
}
