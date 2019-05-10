package com.kata.model

import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class PostParametersTest extends FlatSpec with BeforeAndAfter {

  "Map " should "contain 3 elements" in {
    val body = "firstname=Beppe&lastname=Catanese&last_user_freeform_input=File"
    var incomingParameters: PostParameters = new PostParameters(body)

    incomingParameters.getMap(body).size shouldBe 3

  }

  "Firstname " should "be Beppe" in {
    val body = "firstname=Beppe&lastname=Catanese&last_user_freeform_input=File"
    var incomingParameters: PostParameters = new PostParameters(body)

    incomingParameters.getParameter("firstname") shouldBe "Beppe"

  }

  it should "be empty" in {
    val body = "dummy=1"
    var incomingParameters: PostParameters = new PostParameters(body)

    incomingParameters.getParameter("firstname") shouldBe ""

  }

  it should "be empty again" in {
    val body = ""
    var incomingParameters: PostParameters = new PostParameters(body)

    incomingParameters.getParameter("firstname") shouldBe ""

  }

}
