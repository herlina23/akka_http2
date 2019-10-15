package new3

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Framing
import akka.stream.scaladsl.JavaFlowSupport.Sink
import play.api.libs.json.Json

import scala.concurrent.Future
import scala.util.{Failure, Success}
import akka.stream.scaladsl.{FileIO, Sink, Source}
import weatherForecast.Forecast

//res.entity jadikan suatu jsvalue

object Stream1 {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f8c2705abbe43a56eaca373e8d86070e"))

    responseFuture
      .onComplete {
        case Success(res) =>
          val ddf = res.entity.dataBytes
            .map(_.utf8String)
            .map{
              str =>
                val jsonValue = Json.parse(str)
                val forecastOpt = jsonValue.asOpt[Forecast]
                forecastOpt
            }
            .runForeach(println)

          res.discardEntityBytes()
          //res.entity
        case Failure(_)   => sys.error("something wrong")
      }
  }
}

case class ByteString(str: String)