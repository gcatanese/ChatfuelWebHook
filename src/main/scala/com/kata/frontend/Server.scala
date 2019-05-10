package com.kata.frontend

import com.kata.model.{Attachment, AttachmentContainer, AttachmentUrl, GetParameters, Messages, PostParameters, TextMessage}
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
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Future
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.model._
import spray.json._

class Server {

  val binding: Future[ServerBinding] = null

  def init(): Unit = {

    println("init")

    val conf = ConfigFactory.load()

    val host = conf.getString("myapp.server-address")
    val port: Int = sys.env.getOrElse("PORT", conf.getString("myapp.server-port")).toInt

    implicit val system: ActorSystem = ActorSystem("chatfuelWebHook")
    implicit val executor: ExecutionContext = system.dispatcher
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    // startup
    val binding = Http().bindAndHandle(route, host, port)
    binding.onComplete {
      case Success(_) => println("Started host:" + host + " port:" + port)
      case Failure(error) => println(s"Failed: ${error.getMessage}")
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
  //  implicit val implAttachmentUrl = jsonFormat(AttachmentUrl, "url")
  //  implicit val implAttachment = jsonFormat(Attachment, "type", "payload")
  //  implicit val implMessagesWithAttachments = jsonFormat(Messages[Attachment], "messages")

  def route = path("chatfuelWebHook") {
    get {
      parameterMap { params =>
        println("get: " + params)
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "GET OK"))
      }
    } ~
      post {
        entity(as[String]) { body =>
          println("post: " + body)

          var incomingParameters: PostParameters = new PostParameters(body)

          val text = incomingParameters.getLastUserFreeformInput
          println("text: " + text)

          if (text.equals("Hi")) {
            val messages = new Messages[TextMessage](Array(new TextMessage("hello"), new TextMessage("ciao")))

            val json = messages.toJson
            println("json: " + json)

            complete(messages)
          } else if (text.equals("Hello")) {
            val attachmentUrl = new AttachmentUrl("https://rockets.chatfuel.com/assets/welcome.png")
            val attachment = new Attachment("image", attachmentUrl)
            var attachmentContainer = new AttachmentContainer(attachment)
            var messages = new Messages[AttachmentContainer](Array(attachmentContainer))

            val json = messages.toJson
            println("json" + json)

            complete(messages)


          } else if (text.equals("Hiya")) {
            val attachmentUrl = new AttachmentUrl("https://rockets.chatfuel.com/assets/video.mp4")
            val attachment = new Attachment("video", attachmentUrl)
            var attachmentContainer = new AttachmentContainer(attachment)
            var messages = new Messages[AttachmentContainer](Array(attachmentContainer))

            val json = messages.toJson
            println("json" + json)

            complete(messages)
          } else if (text.equals("Audio")) {
            val attachmentUrl = new AttachmentUrl("https://rockets.chatfuel.com/assets/hello.mp3")
            val attachment = new Attachment("audio", attachmentUrl)
            var attachmentContainer = new AttachmentContainer(attachment)
            var messages = new Messages[AttachmentContainer](Array(attachmentContainer))

            val json = messages.toJson
            println("json" + json)

            complete(messages)
          } else if (text.equals("File")) {

            val attachmentUrl = new AttachmentUrl("https://rockets.chatfuel.com/assets/ticket.pdf")
            val attachment = new Attachment("file", attachmentUrl)
            var attachmentContainer = new AttachmentContainer(attachment)
            var messages = new Messages[AttachmentContainer](Array(attachmentContainer))

            val json = messages.toJson
            println("json" + json)

            complete(messages)
          } else {
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "POST OK"))
          }

        }

      }
  }


}

object ServerObject extends App {
  println("obj")

  var server = new Server
  server.init()

}
