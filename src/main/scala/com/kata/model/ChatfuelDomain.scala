package com.kata.model

final case class Messages[T](messages: Array[T])

final case class TextMessage(text: String)

final case class AttachmentContainer(attachment: Attachment)
final case class Attachment(`type`: String, payload: AttachmentUrl)
final case class AttachmentUrl(url: String)

final case class QuickReplyContainer[P](text: String, quick_replies: P)
final case class QuickReplyOption(title: String, block_names: List[String])
final case class QuickReplyOptionWithType(title: String, url: String, _type: String)

final case class Button(`type`: String, url : String, title : String)
final case class Element(title: String, image_url : String, subtitle : String, buttons: List[Button])

final case class GalleryContainer(attachment: Gallery)
final case class GalleryPayload(template_type: String, image_aspect_ratio: String, elements : List[Element])
final case class Gallery(`type`: String, payload: GalleryPayload)

final case class ListContainer(attachment: ListItem)
final case class ListItem(`type`: String, payload: ListPayload)
final case class ListPayload(template_type: String, top_element_style: String, elements : List[Element])



