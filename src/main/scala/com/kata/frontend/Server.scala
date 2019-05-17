package com.kata.frontend

import com.kata.model.{Attachment, AttachmentContainer, AttachmentUrl, ChatfuelAction, ChatfuelAttribute, Messages, QuickReplyContainer, QuickReplyOption, TextMessage}
import com.typesafe.config.ConfigFactory
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}

import scala.concurrent.ExecutionContext
import scala.concurrent.Await
import scala.util.{Failure, Success}
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.Done
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives.{host, _}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import scala.concurrent.Future
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.model._
import com.typesafe.scalalogging.{Logger, StrictLogging}
import spray.json.DefaultJsonProtocol._
import spray.json.{DeserializationException, JsObject, JsString, JsValue, JsonFormat}


class Server extends StrictLogging with ChatfuelAction with ChatfuelAttribute {

  val binding: Future[ServerBinding] = null

  def init(): Unit = {

    logger.info("INIT")

    val conf = ConfigFactory.load()

    val host = conf.getString("myapp.server-address")
    val port: Int = sys.env.getOrElse("PORT", conf.getString("myapp.server-port")).toInt

    implicit val system: ActorSystem = ActorSystem("chatfuelWebHook")
    implicit val executor: ExecutionContext = system.dispatcher
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    // startup
    val binding = Http().bindAndHandle(route, host, port)
    binding.onComplete {
      case Success(_) => logger.info("Started host:" + host + " port:" + port)
      case Failure(error) => logger.error(s"Failed: ${error.getMessage}")
    }

    import scala.concurrent.duration._
    Await.result(binding, 3.seconds)

    // shutdown
    //println("shutdown...")

    //    binding
    //      .flatMap(_.unbind()) // trigger unbinding from the port
    //      .onComplete(_ => system.terminate()) // and shutdown when done

  }

  implicit val implTextMessage = jsonFormat1(TextMessage)
  implicit val implMessages = jsonFormat1(Messages[TextMessage])

  implicit val implAttachmentUrl = jsonFormat1(AttachmentUrl)
  implicit val implAttachment = jsonFormat2(Attachment)
  implicit val implAttachmentContainer = jsonFormat1(AttachmentContainer)
  implicit val implMessagesWithAttachments = jsonFormat1(Messages[AttachmentContainer])

  //implicit val implQuickReplyOption = jsonFormat3(QuickReplyOption)

  implicit object implQuickReplyOption extends JsonFormat[QuickReplyOption] {

    def write(c: QuickReplyOption) = JsObject(
      "title" -> JsString(c.title),
      "url" -> JsString(c.url),
      "type" -> JsString(c._type))

    def read(value: JsValue) = {
      value.asJsObject.getFields("title", "url", "_type") match {
        case Seq(JsString(title), JsString(url), JsString(_type)) =>
          new QuickReplyOption(title, url, _type)
        case _ => throw new DeserializationException("Tweets expected")
      }
    }
  }


  //implicit val implQuickReply = jsonFormat1(QuickReply)
  implicit val implQuickReplyContainer = jsonFormat2(QuickReplyContainer)
  implicit val implMessagesWithQuickReplies = jsonFormat1(Messages[QuickReplyContainer])

  val pathStr = "chatfuelWebHook"

  def route = path(pathStr) {
    get {
      parameterSeq { params =>

        var incomingParameters: RequestParameters = new RequestParameters(params.toString())
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "GET OK"))
      }
    } ~
      post {
          entity(as[String]) { payload =>

          implicit val body = payload
          logger.info("post: " + body)

          val userInput = getUserInput
          val firstname = getFirstname
          val lastname = getLastname

          if (userInput.equalsIgnoreCase("Hi")) {

            val messages = replyWithTextMessages(Array[String]("hello " + firstname, "ciao Mr " + lastname))
            complete(messages)

          } else if (userInput.equals("Hello")) {

            var messages = replyWithAttachments(Array[(String, String)](("image", "https://rockets.chatfuel.com/assets/welcome.png")))
            complete(messages)

          } else if (userInput.equalsIgnoreCase("Hiya")) {

            var messages = replyWithAttachments(Array[(String, String)](("video", "https://rockets.chatfuel.com/assets/video.mp4")))
            complete(messages)

          } else if (userInput.equalsIgnoreCase("Audio")) {

            var messages = replyWithAttachments(Array[(String, String)](("audio", "https://rockets.chatfuel.com/assets/hello.mp3")))
            complete(messages)

          } else if (userInput.equalsIgnoreCase("File")) {

            var messages = replyWithAttachments(Array[(String, String)](("file", "https://rockets.chatfuel.com/assets/ticket.pdf")))
            complete(messages)

          } else if (userInput.equalsIgnoreCase("Quick")) {

            var messages = replyWithQuickReplies("Did you like it", Array[(String, String, String)](("Yes!", "https://rockets.chatfuel.com/api/sad-match", "json_plugin_url")))
            complete(messages)

          } else {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "POST OK"))
          }

        }

      }
  }


}

object ServerObject extends App with StrictLogging {

  logger.info("ServerObject..")

  var server = new Server
  server.init()

}
