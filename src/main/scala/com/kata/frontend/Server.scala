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
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
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

}

object Server extends App {

  val conf = ConfigFactory.load()

  val host = conf.getString("myapp.server-address")
  val port = conf.getInt("myapp.server-port")


  implicit val system: ActorSystem = ActorSystem("chatfuelWebHook")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  implicit val implTextMessage = jsonFormat1(TextMessage)
  implicit val implMessages = jsonFormat1(Messages)


  import akka.http.scaladsl.server.Directives._

  def route = path("chatfuelWebHook") {
    get {
      println("get!")
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "GET OK"))
    } ~
    post {
      extractRequest { request =>
        entity(as[String]) { body =>
          println("post Item headers: " + request.headers)
          println("body: " + body)

          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "POST OK"))
        }
      }
    }
  }

  val binding = Http().bindAndHandle(route, host, port)
  binding.onComplete {
    case Success(_) => println("Success!")
    case Failure(error) => println(s"Failed: ${error.getMessage}")
  }

  import scala.concurrent.duration._
  Await.result(binding, 3.seconds)


}
