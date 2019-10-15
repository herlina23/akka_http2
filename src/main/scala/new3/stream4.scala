package new3

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.stream.ActorMaterializer
import play.api.libs.json.Json
import play.api.libs.json.{JsError, JsSuccess, Json}

//https://stackoverflow.com/questions/33123280/akka-http-send-continuous-chunked-http-response-stream

object Client extends App {

  implicit val system = ActorSystem("client")
  import system.dispatcher
  implicit val materializer = ActorMaterializer()
  val source = Uri("http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f8c2705abbe43a56eaca373e8d86070e")

  val finished = Http().singleRequest(HttpRequest(uri = source)).flatMap { response =>
    response.entity.dataBytes.runForeach { chunk =>
      println(chunk.utf8String)
    }
  }





}