package com.kata.model

class IncomingParameters(var parameters: Map[String, String]) {

  def getFirstname: String = {
    var ret = ""

    val firstname = parameters.get("firstname")

    if (firstname.isDefined) {
      ret = firstname.get
    }
    ret
  }

  def getLastname: String = {
    var ret = ""

    val lastname = parameters.get("lastname")

    if (lastname.isDefined) {
      ret = lastname.get
    }
    ret
  }


}
