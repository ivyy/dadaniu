package com.ddn.wechat.model

/**
 * User: bigfish
 * Date: 15-12-28
 * Time: 下午5:14
 */

case class ArticleItem(title: String, description: String, pictureUrl: String, url: String)

case class NewsResponse(toUserName: String,
                        fromUserName: String,
                        createTime: Long, articleItems: Seq[ArticleItem])
  extends BaseWechatResponse(toUserName, fromUserName, createTime, "news") {
  def toXml =
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
        <![CDATA[news]]>
      </MsgType>
      <ArticleCount>
        {articleItems.length % 10}
      </ArticleCount>
      <Articles>
        {articleItems.take(10).map(
        item =>
          <item>
            <Title>
              {scala.xml.PCData(item.title)}
            </Title>
            <Description>
              {scala.xml.PCData(item.description)}
            </Description>
            <PicUrl>
              {scala.xml.PCData(item.pictureUrl)}
            </PicUrl>
            <Url>
              {scala.xml.PCData(item.url)}
            </Url>
          </item>
      )}
      </Articles>
    </xml>)
}
