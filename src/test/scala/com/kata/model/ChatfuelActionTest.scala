package com.kata.model

import com.typesafe.scalalogging.StrictLogging
import org.scalatest.FlatSpec
import org.scalatest.Matchers._

class ChatfuelActionTest extends FlatSpec with ChatfuelAction with StrictLogging {

  "replyWithTextMessage" should "contain 1 element" in {
    replyWithTextMessage("hi").messages.length shouldBe 1
  }

  "replyWithTextMessages" should "contain 2 elements" in {

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

  "replyWithAttachments" should "contain 1 element" in {

    var output = replyWithAttachments(Array[(String, String)](("image", "https://rockets.chatfuel.com/assets/welcome.png")))
    output.messages.length shouldBe 1
  }

  it should "contain an image" in {

    var output = replyWithAttachments(Array[(String, String)](("image", "https://rockets.chatfuel.com/assets/welcome.png")))
    output.messages(0).attachment.`type` shouldBe "image"
  }

  it should "contain the URL of the image" in {

    var output = replyWithAttachments(Array[(String, String)](("image", "https://rockets.chatfuel.com/assets/welcome.png")))
    output.messages(0).attachment.payload.url shouldBe "https://rockets.chatfuel.com/assets/welcome.png"
  }

  "replyWithQuickReplies" should "contain 1 element" in {

    var list = List[String]("Like it!", "Thats ok", "Not impressed", "No way");
    var output = replyWithQuickReplies("May I ask?", Array[(String, List[String])](("What do you think about it?", list)))

    output.messages.length shouldBe 1
  }

  it should "contain 4 options" in {

    var list = List[String]("Like it!", "Thats ok", "Not impressed", "No way");
    var output = replyWithQuickReplies("May I ask?", Array[(String, List[String])](("What do you think about it?", list)))

    output.messages(0).quick_replies(0).block_names.length shouldBe 4
  }

  it should "contain 'Like it!'" in {

    var list = List[String]("Like it!", "Thats ok", "Not impressed", "No way");
    var output = replyWithQuickReplies("May I ask?", Array[(String, List[String])](("What do you think about it?", list)))

    output.messages(0).quick_replies(0).block_names(0) shouldBe "Like it!"
  }

}
