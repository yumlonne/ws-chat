package com.yumlonne.app

import org.scalatra._

import scala.util.Try

import spray.json._
import DefaultJsonProtocol._
import com.yumlonne.app.model.json.Implicits._

import scala.collection.mutable.ArrayBuffer
import com.yumlonne.app.model._
import com.yumlonne.app.model.json.WebSocket._


class ChatServlet extends ScalatraServlet {

  get("/room/list") {
    val json = RoomRepository.data.values.map(_.toJson).mkString(",")
    s"[$json]"
  }

  get("/room/new") {
    params.get("name").fold("""{"is_success":false,"reason":"name not found"}""") { name =>
      val roomId = java.util.UUID.randomUUID.toString
      val room = Room(roomId, name, ArrayBuffer[String]())
      RoomRepository.data += roomId -> room
      s"""{
        "is_success":true
      }""".stripMargin
    }
  }

  get("/room/join") {
    val name = params.get("name").getOrElse("NoName")
    val roomIdOpt = params.get("room_id")

    val wsNewOpt = Try {
      scala.io.Source
        .fromURL("http://ws.yumlonne.com")
        .mkString
        .parseJson
        .convertTo[New]
    }.toOption

    val resultEither = for {
      roomId <- roomIdOpt.toRight("room_id not found")
      wsNew  <- wsNewOpt.toRight("Internal API error")
      room   <- RoomRepository.data.find(_._1 == roomId).map(_._2).toRight("room not found")
    } yield {
      room.users += wsNew.userId
      wsNew
    }

    resultEither.fold(
      l => s"""{"is_success":false,reason:"$l"}""",
      r => r.toJson.compactPrint
    )

  }

  get("/chat/post") {
    val userIdOpt = params.get("user_id")
    val messageOpt = params.get("message")

    val resultEither = for {
      userId <- userIdOpt.toRight("user_id not found")
      message <- messageOpt.toRight("message not found")
      room <- RoomRepository.data.values.find(_.users contains userId).toRight("room not found")
    } yield {
      val targets = room.users.mkString(",")
      val url = s"http://ws.yumlonne.com/post?targets=$targets"

      println(url)

      scala.io.Source.fromURL(url)

      s"""{
        "is_success":true,
      }""".stripMargin
    }

    resultEither.fold(
      l => s"""{"is_success":false,reason:"$l"}""",
      r => r
    )

  }

}
