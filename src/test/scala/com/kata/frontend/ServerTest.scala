package com.kata.frontend

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import Matchers._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.server._
import Directives._


class ServerTest extends FlatSpec with BeforeAndAfter with ScalatestRouteTest {

  var server = new Server

  "Get " should "be OK" in {
      Get("/chatfuelWebHook?firstname=Beppe&lastname=Catanese") ~> server.route ~> check {
      responseAs[String] shouldEqual "GET OK"
    }
  }

  "Get without parameters" should "be OK" in {
      Get("/chatfuelWebHook") ~> server.route ~> check {
        responseAs[String] shouldEqual "GET OK"
      }
  }

  "Post " should "be 2 text messages" in {
    val body = "firstname=Beppe&lastname=Catanese&last_user_freeform_input=Hi"
    Post("/chatfuelWebHook").withEntity(body) ~> server.route ~> check {
      responseAs[String] shouldEqual "{\"messages\":[{\"text\":\"hello\"},{\"text\":\"ciao\"}]}"
    }
  }

  it should "be an IMAGE attachment" in {
    val body = "firstname=Beppe&lastname=Catanese&last_user_freeform_input=Hello"
    Post("/chatfuelWebHook").withEntity(body) ~> server.route ~> check {
      responseAs[String] shouldEqual "{\"messages\":[{\"attachment\":{\"payload\":{\"url\":\"https://rockets.chatfuel.com/assets/welcome.png\"},\"type\":\"image\"}}]}"
    }
  }

  it should "be a VIDEO attachment" in {
    val body = "firstname=Beppe&lastname=Catanese&last_user_freeform_input=Hiya"
    Post("/chatfuelWebHook").withEntity(body) ~> server.route ~> check {
      responseAs[String] shouldEqual "{\"messages\":[{\"attachment\":{\"payload\":{\"url\":\"https://rockets.chatfuel.com/assets/video.mp4\"},\"type\":\"video\"}}]}"
    }
  }

  it should "be an AUDIO attachment" in {
    val body = "firstname=Beppe&lastname=Catanese&last_user_freeform_input=Audio"
    Post("/chatfuelWebHook").withEntity(body) ~> server.route ~> check {
      responseAs[String] shouldEqual "{\"messages\":[{\"attachment\":{\"payload\":{\"url\":\"https://rockets.chatfuel.com/assets/hello.mp3\"},\"type\":\"audio\"}}]}"
    }
  }

  it should "be a FILE attachment" in {
    val body = "firstname=Beppe&lastname=Catanese&last_user_freeform_input=File"
    Post("/chatfuelWebHook").withEntity(body) ~> server.route ~> check {
      responseAs[String] shouldEqual "{\"messages\":[{\"attachment\":{\"payload\":{\"url\":\"https://rockets.chatfuel.com/assets/ticket.pdf\"},\"type\":\"file\"}}]}"
    }
  }


}
