package com.ddn.wechat.model

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午5:08
 */
case class MusicResponse(toUserName: String,
                         fromUserName: String,
                         createTime: Long,
                         title: String,
                         description: String,
                         musicUrl: String,
                         hqMusicUrl: String,
                         thumbMediaId: String)
  extends BaseWechatResponse(toUserName, fromUserName, createTime, "music") {

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
        <![CDATA[music]]>
      </MsgType>
      <Music>
        <Title>
          {scala.xml.PCData(title)}
        </Title>
        <Description>
          {scala.xml.PCData(description)}
        </Description>
        <MusicUrl>
          {scala.xml.PCData(musicUrl)}
        </MusicUrl>
        <HQMusicUrl>
          {scala.xml.PCData(hqMusicUrl)}
        </HQMusicUrl>
        <ThumbMediaId>
          {scala.xml.PCData(thumbMediaId)}
        </ThumbMediaId>
      </Music>
    </xml>)
}
