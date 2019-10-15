package new3
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import akka.http.scaladsl.model.headers.`Set-Cookie`
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.Future
import akka.http.scaladsl.unmarshalling.Unmarshal
import play.api.libs.json.{JsObject, Json}

object Stream2 {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem()
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f8c2705abbe43a56eaca373e8d86070e"))

    responseFuture.map {
      case response @ HttpResponse(StatusCodes.OK, _, _, _) =>
        val setCookies = response.headers[`Set-Cookie`]
        //val body = response.entity.httpEntity.toString
        println(s"Cookies set by a server: $setCookies")
        //Ok(Json.toJson(response))
        response.discardEntityBytes()
      case _ => sys.error("something wrong")
    }


  }
}