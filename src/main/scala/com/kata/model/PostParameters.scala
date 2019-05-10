package com.kata.model

import com.typesafe.scalalogging.StrictLogging

/**
  * Chatfuel parameters from POST request
  *
  * @param body Something like firstname=Beppe&lastname=Catanese&last_user_freeform_input=File
  */
class PostParameters(var body: String) extends StrictLogging {

  def getLastUserFreeformInput: String = {
    getParameter("last+user+freeform+input")
  }

  def getFirstname: String = {
    getParameter("firstname")
  }

  def getLastname: String = {
    getParameter("lastname")
  }

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
