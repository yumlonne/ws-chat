package com.yumlonne.app.model

import collection.mutable.ArrayBuffer

case class Room(id: String, name: String, users: ArrayBuffer[String]) {
  def toJson: String = s"""{
    "id":"$id",
    "name":"$name"
  }""".stripMargin
}
