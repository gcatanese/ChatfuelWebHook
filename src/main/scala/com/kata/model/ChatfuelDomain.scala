package com.kata.model

final case class Messages[T](messages: Array[T])

final case class TextMessage(text: String)

final case class AttachmentContainer(attachment: Attachment)
final case class Attachment(`type`: String, payload: AttachmentUrl)
final case class AttachmentUrl(url: String)

//final case class QuickReplyContainer(text: String, quick_replies: List[QuickReplyOption])
final case class QuickReplyContainer[P](text: String, quick_replies: P)
final case class QuickReplyOption(title: String, url: String, _type: String)
final case class QuickReplyOptionWithBlocks(title: String, block_names: List[String])



