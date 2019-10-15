package new3

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling._
import akka.stream.ActorMaterializer
import spray.json.JsonParser

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object Stream3 {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    import system.dispatcher

    val http = Http()
    val response: Future[HttpResponse] =
      http.singleRequest(HttpRequest(HttpMethods.GET, Uri("http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f8c2705abbe43a56eaca373e8d86070e")))

    val result = response.map {
      case HttpResponse(StatusCodes.OK, headers, entity, _) =>
        Unmarshal(entity.httpEntity).to[String]
        println(entity)

      case x => s"Unexpected status code ${x.status}"

    }


  }

}