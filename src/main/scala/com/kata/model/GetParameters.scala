package com.kata.model

/**
  * Chatfuel parameters from GET request
  * @param parameters Something like Map("firstname" -> "Beppe", "lastname" -> "Catanese", "last_user_freeform_input" -> "Hi")
  */
class GetParameters(var parameters: Map[String, String]) {

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

    val parameterValue = parameters.get(parameterName)

    if (parameterValue.isDefined) {
      ret = parameterValue.get
    }

    ret
  }

}
