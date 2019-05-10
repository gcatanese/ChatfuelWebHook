package com.kata.model

class IncomingParameters(var parameters: Map[String, String]) {

  def getLastUserFreeformInput: String = {
    getParameter("last_user_freeform_input")
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
