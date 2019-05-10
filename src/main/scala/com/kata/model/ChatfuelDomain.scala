package com.kata.model

final case class Messages[T](messages: Array[T])

final case class TextMessage(text: String)

final case class AttachmentContainer(attachment: Attachment)
final case class Attachment(`type`:String, payload: AttachmentUrl)
final case class AttachmentUrl(url: String)



