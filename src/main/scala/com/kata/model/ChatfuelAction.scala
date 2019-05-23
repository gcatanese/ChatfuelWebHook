package com.kata.model

import com.typesafe.scalalogging.StrictLogging

trait ChatfuelAction extends StrictLogging {

  def replyWithTextMessage(implicit textMessage: String): Messages[TextMessage] = {
    replyWithTextMessages(Array[String](textMessage))
  }

  def replyWithTextMessages(implicit textMessages: Array[String]): Messages[TextMessage] = {
    new Messages[TextMessage](textMessages.map(x => new TextMessage(x)))
  }

  def replyWithAttachments(implicit attachments: Array[(String, String)]): Messages[AttachmentContainer] = {
    new Messages[AttachmentContainer](attachments.map((x => new AttachmentContainer(new Attachment(x._1, new AttachmentUrl(x._2))))))
  }

  def replyWithQuickReplies(implicit text: String, quickReplies: Array[(String, String, String)]): Messages[QuickReplyContainer[List[QuickReplyOptionWithType]]] = {
    val list = quickReplies.map(x => new QuickReplyOptionWithType(x._1, x._2, x._3)).toList

    new Messages[QuickReplyContainer[List[QuickReplyOptionWithType]]](Array[QuickReplyContainer[List[QuickReplyOptionWithType]]](new QuickReplyContainer(text, list)))
  }

  def replyWithQuickReplies(implicit text: String, quickReplies: Array[(String, List[String])]): Messages[QuickReplyContainer[List[QuickReplyOption]]] = {
    val list = quickReplies.map(x => new QuickReplyOption(x._1, x._2)).toList

    new Messages[QuickReplyContainer[List[QuickReplyOption]]](Array[QuickReplyContainer[List[QuickReplyOption]]](new QuickReplyContainer(text, list)))
  }

  def replyWithGalleries(implicit galleryContainer: GalleryContainer): Messages[GalleryContainer] = {
    new Messages[GalleryContainer](Array[GalleryContainer](galleryContainer))
  }

  def replyWithLists(implicit listContainer: ListContainer): Messages[ListContainer] = {
    new Messages[ListContainer](Array[ListContainer](listContainer))
  }
}
