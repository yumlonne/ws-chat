package com.yumlonne.app.model.json

import spray.json._
import DefaultJsonProtocol._

import com.yumlonne.app.model.json.WebSocket._

object Implicits extends DefaultJsonProtocol {
  implicit val NewJsonObjectFormat = jsonFormat2(WebSocket.New.apply)
}
