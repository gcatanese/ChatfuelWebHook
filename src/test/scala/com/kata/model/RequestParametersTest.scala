package com.kata.model

import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class RequestParametersTest extends FlatSpec with ChatfuelAttribute {

  "Map " should "contain 3 elements" in {
    val body = "firstname=Beppe&lastname=Catanese&last+user+freeform+input=File"
    var incomingParameters: RequestParameters = new RequestParameters(body)

    incomingParameters.getMap(body).size shouldBe 3

  }

  "Firstname " should "be Beppe" in {
    val body = "firstname=Beppe&lastname=Catanese&last+user+freeform+input=File"
    var incomingParameters: RequestParameters = new RequestParameters(body)

    incomingParameters.getParameter("firstname") shouldBe "Beppe"

  }

  it should "be empty" in {
    val body = "dummy=1"
    var incomingParameters: RequestParameters = new RequestParameters(body)

    incomingParameters.getParameter("firstname") shouldBe ""

  }

  it should "be empty again" in {
    val body = ""
    var incomingParameters: RequestParameters = new RequestParameters(body)

    incomingParameters.getParameter("firstname") shouldBe ""

  }

}
