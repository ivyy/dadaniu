package com.ddn.wechat.model

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午4:50
 */
case class TextResponse(toUserName: String,
                        fromUserName: String,
                        createTime: Long,
                        content: String)
  extends BaseWechatResponse(toUserName, fromUserName, createTime, WechatMessageType.TEXT) {

  /*
    NOTE:
    The wechat server has strict validation of the xml format.
    You need to remove the white space, tabs between tags and the content
   */
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
        <![CDATA[text]]>
      </MsgType>
      <Content>
        {scala.xml.PCData(content)}
      </Content>
    </xml>)
}
