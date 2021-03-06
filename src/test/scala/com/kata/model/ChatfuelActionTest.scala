package com.kata.model

import com.typesafe.scalalogging.StrictLogging
import org.scalatest.FlatSpec
import org.scalatest.Matchers._

class ChatfuelActionTest extends FlatSpec with ChatfuelAction with StrictLogging {

  "replyWithTextMessage" should "contain 1 message" in {
    replyWithTextMessage("hi").messages.length shouldBe 1
  }

  "replyWithTextMessages" should "contain 2 messages" in {

    val list = Array[String]("Hello", "How are you?")
    replyWithTextMessages(list).messages.length shouldBe 2
  }

  it should "include 'Hello'" in {

    val list = Array[String]("Hello", "How are you?")
    replyWithTextMessages(list).messages(0).text shouldBe "Hello"
  }

  it should "include 'How are you?'" in {

    val list = Array[String]("Hello", "How are you?")
    replyWithTextMessages(list).messages(1).text shouldBe "How are you?"
  }

  "replyWithAttachments" should "contain 1 message" in {

    val output = replyWithAttachments(Array[(String, String)](("image", "https://rockets.chatfuel.com/assets/welcome.png")))
    output.messages.length shouldBe 1
  }

  it should "contain an image" in {

    val output = replyWithAttachments(Array[(String, String)](("image", "https://rockets.chatfuel.com/assets/welcome.png")))
    output.messages(0).attachment.`type` shouldBe "image"
  }

  it should "contain the URL of the image" in {

    val output = replyWithAttachments(Array[(String, String)](("image", "https://rockets.chatfuel.com/assets/welcome.png")))
    output.messages(0).attachment.payload.url shouldBe "https://rockets.chatfuel.com/assets/welcome.png"
  }

  "replyWithQuickReplies" should "contain 1 message" in {

    val list = List[String]("Like it!", "Thats ok", "Not impressed", "No way");
    val output = replyWithQuickReplies("May I ask?", Array[(String, List[String])](("What do you think about it?", list)))

    output.messages.length shouldBe 1
  }

  it should "contain 4 options" in {

    val list = List[String]("Like it!", "Thats ok", "Not impressed", "No way");
    val output = replyWithQuickReplies("May I ask?", Array[(String, List[String])](("What do you think about it?", list)))

    output.messages(0).quick_replies(0).block_names.length shouldBe 4
  }

  it should "contain 'Like it!'" in {

    val list = List[String]("Like it!", "Thats ok", "Not impressed", "No way");
    val output = replyWithQuickReplies("May I ask?", Array[(String, List[String])](("What do you think about it?", list)))

    output.messages(0).quick_replies(0).block_names(0) shouldBe "Like it!"
  }

  "replyWithGalleries" should "contain 1 message" in {

    val bt1 = Button("web_url", "https://rockets.chatfuel.com/store", "View Item")
    val element1 = Element("Chatfuel Rockets Jersey", "https://rockets.chatfuel.com/assets/shirt.jpg", "Size: M", List[Button](bt1))
    val galleryPayload = GalleryPayload("generic", "square", List[Element](element1))
    val gallery = Gallery("template", galleryPayload)

    val output = replyWithGalleries(GalleryContainer(gallery))

    output.messages.length shouldBe 1
  }

  it should "contain 1 element" in {

    val bt1 = Button("web_url", "https://rockets.chatfuel.com/store", "View Item")
    val element1 = Element("Chatfuel Rockets Jersey", "https://rockets.chatfuel.com/assets/shirt.jpg", "Size: M", List[Button](bt1))
    val galleryPayload = GalleryPayload("generic", "square", List[Element](element1))
    val gallery = Gallery("template", galleryPayload)

    val output = replyWithGalleries(GalleryContainer(gallery))

    output.messages(0).attachment.payload.elements.length shouldBe 1
  }

  it should "contain a web_url button" in {

    val bt1 = Button("web_url", "https://rockets.chatfuel.com/store", "View Item")
    val element1 = Element("Chatfuel Rockets Jersey", "https://rockets.chatfuel.com/assets/shirt.jpg", "Size: M", List[Button](bt1))
    val galleryPayload = GalleryPayload("generic", "square", List[Element](element1))
    val gallery = Gallery("template", galleryPayload)

    val output = replyWithGalleries(GalleryContainer(gallery))

    output.messages(0).attachment.payload.elements(0).buttons(0).`type` shouldBe "web_url"
  }

  "replyWithLists" should "contain 1 message" in {

    var bt1 = Button("web_url", "https://rockets.chatfuel.com/store", "View Item")
    var element1 = Element("Chatfuel Rockets Jersey", "https://rockets.chatfuel.com/assets/shirt.jpg", "Size: M", List[Button](bt1))
    var listPayload = ListPayload("list", "large", List[Element](element1))
    var list = ListItem("template", listPayload)

    val output = replyWithLists(ListContainer(list))

    output.messages.length shouldBe 1
  }

  it should "contain 1 element" in {

    var bt1 = Button("web_url", "https://rockets.chatfuel.com/store", "View Item")
    var element1 = Element("Chatfuel Rockets Jersey", "https://rockets.chatfuel.com/assets/shirt.jpg", "Size: M", List[Button](bt1))
    var listPayload = ListPayload("list", "large", List[Element](element1))
    var list = ListItem("template", listPayload)

    val output = replyWithLists(ListContainer(list))

    output.messages(0).attachment.payload.elements.length shouldBe 1
  }

  it should "contain template_type 'list'" in {

    var bt1 = Button("web_url", "https://rockets.chatfuel.com/store", "View Item")
    var element1 = Element("Chatfuel Rockets Jersey", "https://rockets.chatfuel.com/assets/shirt.jpg", "Size: M", List[Button](bt1))
    var listPayload = ListPayload("list", "large", List[Element](element1))
    var list = ListItem("template", listPayload)

    val output = replyWithLists(ListContainer(list))

    output.messages(0).attachment.payload.template_type shouldBe "list"
  }

}
