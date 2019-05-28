package com.kata.model

import com.typesafe.scalalogging.StrictLogging
import org.scalatest.FlatSpec

class ChatfuelBroadcastTest extends FlatSpec with ChatfuelBroadcast with StrictLogging {

  "Map " should "contain 3 elements" in {

    val parameters = BroadcastingParameters("5cc189229a6d47000680b6bd", "2283597285040135",
      "mELtlMAHYqR0BvgEiMq8zVek3uYUK3OJMbtyrdNPTrQB9ndV0fM7lWTFZbM4MZvD", "APPLICATION_UPDATE", "BL1")

    send(parameters);
  }

}
