package com.kata.model

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import Matchers._

class IncomingParametersTest extends FlatSpec with BeforeAndAfter {

  "Firstname " should "be Beppe" in {
    val map = Map("firstname" -> "Beppe", "lastname" -> "Catanese ")
    var incomingParameters: IncomingParameters = new IncomingParameters(map)

    incomingParameters.getFirstname shouldBe "Beppe"

  }

  it should "be empty" in {
    val map = Map("dummy" -> "xx")
    var incomingParameters: IncomingParameters = new IncomingParameters(map)

    incomingParameters.getFirstname shouldBe ""

  }

  "Lastname " should "be Catanese" in {
    val map = Map("firstname" -> "Beppe", "lastname" -> "Catanese")
    var incomingParameters: IncomingParameters = new IncomingParameters(map)

    incomingParameters.getLastname shouldBe "Catanese"

  }

  it should "be empty" in {
    val map = Map("dummy" -> "xx")
    var incomingParameters: IncomingParameters = new IncomingParameters(map)

    incomingParameters.getLastname shouldBe ""

  }


}
