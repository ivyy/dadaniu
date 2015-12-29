package com.ddn.wechat.model

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午5:04
 */
case class VideoResponse(toUserName: String,
                         fromUserName: String,
                         createTime: Long,
                         mediaId: String, title: String, description: String)
  extends BaseWechatResponse(toUserName, fromUserName, createTime, WechatMessageType.VIDEO) {

  override def toXml =
    (<xml>
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
        <![CDATA[video]]>
      </MsgType>
      <Video>
        <MediaId>
          {scala.xml.PCData(mediaId)}
        </MediaId>
        <Title>
          {scala.xml.PCData(title)}
        </Title>
        <Description>
          {scala.xml.PCData(description)}
        </Description>
      </Video>
    </xml>)
}
