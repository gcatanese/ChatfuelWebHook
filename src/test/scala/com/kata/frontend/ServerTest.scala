package com.kata.frontend

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import Matchers._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.server._
import Directives._


class ServerTest extends FlatSpec with BeforeAndAfter with ScalatestRouteTest {

  println("testxxx")

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

  "Post " should "be 2 messages" in {
    Post("/chatfuelWebHook?firstname=Beppe&lastname=Catanese") ~> server.route ~> check {
      responseAs[String] shouldEqual "{\"messages\":[{\"text\":\"hello\"},{\"text\":\"ciao\"}]}"
    }
  }

}
