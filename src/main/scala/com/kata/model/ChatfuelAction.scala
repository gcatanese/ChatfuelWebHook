package com.kata.model

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, get, parameterSeq, path}
import akka.http.scaladsl.server.{PathMatcher, Route}
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

}
