package com.kata.frontend

import com.kata.model.{Messages, TextMessage}
import com.typesafe.config.ConfigFactory
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}

import scala.concurrent.ExecutionContext
import scala.concurrent.Await
import scala.util.{Failure, Success}
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.Done
import akka.http.javadsl.ServerBinding
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives.{host, _}
import akka.http.scaladsl.model.StatusCodes
// for JSON serialization/deserialization following dependency is required:
// "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.7"
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

import scala.io.StdIn

import scala.concurrent.Future

import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.model._

import spray.json._

class Server {

  val binding : Future[ServerBinding] = null

  def init(): Unit = {

    val conf = ConfigFactory.load()

    val host = conf.getString("myapp.server-address")
    val port: Int = sys.env.getOrElse("PORT", conf.getString("myapp.server-port")).toInt

    implicit val system: ActorSystem = ActorSystem("chatfuelWebHook")
    implicit val executor: ExecutionContext = system.dispatcher
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    implicit val implTextMessage = jsonFormat1(TextMessage)
    implicit val implMessages = jsonFormat1(Messages)

    // startup
    val binding = Http().bindAndHandle(route, host, port)
    binding.onComplete {
      case Success(_) => println("Started host:" + host + " port:" + port)
      case Failure(error) => println(s"Failed: ${error.getMessage}")
    }

    import scala.concurrent.duration._
    Await.result(binding, 3.seconds)

    // shutdown
    binding
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done

  }

  implicit val implTextMessage = jsonFormat1(TextMessage)
  implicit val implMessages = jsonFormat1(Messages)

  def route = path("chatfuelWebHook") {
    get {
      println("get!")
      parameterMap { params =>
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "GET OK"))
      }
    } ~
      post {
        extractRequest { request =>
          entity(as[String]) { body =>
            //println("post Item headers: " + request.headers)

            val textMessage = new TextMessage("hello")
            var messages = new Messages(Seq(new TextMessage("hello"), new TextMessage("ciao")))

            complete(messages)
          }
        }
      }
  }


}

object Server extends App {


}
