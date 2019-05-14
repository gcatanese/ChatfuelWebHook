package com.kata.model

import com.typesafe.scalalogging.StrictLogging

trait ChatfuelAttribute extends StrictLogging {

  def getUserInput(implicit body: String): String = {

    var attribute = ""

    val incomingParameters: RequestParameters = new RequestParameters(body)
    attribute = incomingParameters.getParameter("last+user+freeform+input")

    attribute
  }

  def getFirstname(implicit body: String): String = {

    var attribute = ""

    val incomingParameters: RequestParameters = new RequestParameters(body)
    attribute = incomingParameters.getParameter("first+name")

    attribute
  }

  def getLastname(implicit body: String): String = {

    var attribute = ""

    val incomingParameters: RequestParameters = new RequestParameters(body)
    attribute = incomingParameters.getParameter("first+lastname")

    attribute
  }

  def getProfilePicUrl(implicit body: String): String = {

    var attribute = ""

    val incomingParameters: RequestParameters = new RequestParameters(body)
    attribute = incomingParameters.getParameter("profile+pic+url")

    attribute
  }

  def getTimezone(implicit body: String): String = {
    var attribute = ""

    val incomingParameters: RequestParameters = new RequestParameters(body)
    attribute = incomingParameters.getParameter("timezone")

    attribute
  }

  def getLastSeen(implicit body: String): String = {
    var attribute = ""

    val incomingParameters: RequestParameters = new RequestParameters(body)
    attribute = incomingParameters.getParameter("last+seen")

    attribute
  }

  def getMessengerUserId(implicit body: String): String = {
    var attribute = ""

    val incomingParameters: RequestParameters = new RequestParameters(body)
    attribute = incomingParameters.getParameter("messenger+messenger+id")

    attribute
  }

  def getChatfuelUserId(implicit body: String): String = {
    var attribute = ""

    val incomingParameters: RequestParameters = new RequestParameters(body)
    attribute = incomingParameters.getParameter("chatfuel+user+id")

    attribute
  }

  def getSignedUp(implicit body: String): String = {
    var attribute = ""

    val incomingParameters: RequestParameters = new RequestParameters(body)
    attribute = incomingParameters.getParameter("signed+up")

    attribute
  }

  def getSessions(implicit body: String): String = {
    var attribute = ""

    val incomingParameters: RequestParameters = new RequestParameters(body)
    attribute = incomingParameters.getParameter("sessions")

    attribute
  }

  def getSource(implicit body: String): String = {
    var attribute = ""

    val incomingParameters: RequestParameters = new RequestParameters(body)
    attribute = incomingParameters.getParameter("source")

    attribute
  }

  def getOriginalSource(implicit body: String): String = {
    var attribute = ""

    val incomingParameters: RequestParameters = new RequestParameters(body)
    attribute = incomingParameters.getParameter("original+source")

    attribute
  }

  def getGender(implicit body: String): String = {
    var attribute = ""

    val incomingParameters: RequestParameters = new RequestParameters(body)
    attribute = incomingParameters.getParameter("gender")

    attribute
  }



  class RequestParameters(var body: String)  {

    def getParameter(parameterName: String): String = {

      var ret = ""

      val parameterValue = getMap(body).get(parameterName)

      if (parameterValue.isDefined) {
        ret = parameterValue.get
      }

      ret
    }

    def getMap(body: String): Map[String, String] = {
      var parameters = Map.empty[String, String]

      if (body != null && !body.isEmpty) {
        try {
          parameters = body
            .split("&")
            .map { pair =>
              val Array(k, v) = pair.split("=") // split key-value pair and parse to Int
              (k.trim -> v)
            }.toMap

          logger.info(parameters.toString())

        } catch {
          case e: Exception => logger.warn("Invalid input:" + body)
        }
      }

      parameters
    }
  }
}
