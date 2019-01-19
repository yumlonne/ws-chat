package com.yumlonne.app.model.json

object WebSocket {
  case class New(is_success: Boolean, user_id: String) {
    def isSuccess = is_success
    def userId = user_id
  }
}
