package com.yumlonne.app

import org.scalatra.test.scalatest._

class ChatServletTests extends ScalatraFunSuite {

  addServlet(classOf[ChatServlet], "/*")

  test("GET / on ChatServlet should return status 200") {
    get("/") {
      status should equal (200)
    }
  }

}
