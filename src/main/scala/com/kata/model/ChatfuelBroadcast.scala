package com.kata.model

import akka.http.scaladsl.model._
import HttpMethods._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.Future
import scala.util.{Failure, Success}

trait ChatfuelBroadcast extends StrictLogging {

  private val BASE_URL = "https://api.chatfuel.com/bots"

  def send(parameters: BroadcastingParameters): Unit = {

    implicit val system = ActorSystem()
    implicit val ec = system.dispatcher
    implicit val materializer = ActorMaterializer()

    post(parameters) onComplete {
      case Failure(ex) => logger.error("Error: " + ex)
      case Success(response) => logger.info("Response: " + response)
    }

  }

  def post(parameters: BroadcastingParameters): Future[HttpResponse] = {

    implicit val system = ActorSystem()
    implicit val ec = system.dispatcher
    implicit val materializer = ActorMaterializer()

    val http = Http(system)

    implicit val params = parameters

    val apiChatfuelUrl = BASE_URL + "/" + parameters.botId + "/users/" + parameters.userId +
      "/send?chatfuel_token=" + parameters.chatfuelToken + "&chatfuel_message_tag=" + parameters.chatfuelMessageTag +
      "&chatfuel_block_name=" + parameters.chatfuelBlockName

    logger.info("POST to " + apiChatfuelUrl)

    val x = HttpRequest(POST, uri = apiChatfuelUrl)

    http.singleRequest(x)

  }


}
