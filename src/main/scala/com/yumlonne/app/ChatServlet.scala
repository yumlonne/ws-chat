package com.yumlonne.app

import org.scalatra._

class ChatServlet extends ScalatraServlet {

  get("/") {
    views.html.hello()
  }

}
