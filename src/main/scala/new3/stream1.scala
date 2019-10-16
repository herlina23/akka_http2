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
import akka.util.ByteString

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import play.api.libs.json.{JsError, JsSuccess, Json}
import akka.stream.alpakka.file.scaladsl.Directory
import java.nio.file.{Files, Path}
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import play.api.libs.json.{JsError, JsSuccess, Json}
import scala.util.Success
import scala.util.Failure
import akka.NotUsed
import java.nio.file.{Files, Paths}
import akka.stream._
import akka.stream.scaladsl.{FileIO, Sink, Source}
import akka.util.ByteString
import scala.concurrent.Future
import java.io._
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import play.api.libs.json.{JsError, JsSuccess, Json}
import scala.util.Success
import scala.util.Failure
import akka.NotUsed
import java.nio.file.{Files, Paths}
import akka.stream._
import akka.stream.scaladsl.{FileIO, Sink, Source}
import akka.util.ByteString
import scala.concurrent.Future
import akka.Done

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
          val jsonBody = res.entity.dataBytes
            .runFold(ByteString(""))(_ ++ _)
            .map(_.utf8String)
            .map{
              str =>
                val jsonValue = Json.parse(str)
                val forecastOpt = jsonValue.asOpt[Forecast]
                forecastOpt
                //jsonValue
            }
           // .runForeach(println)

          val hasil = jsonBody.map { coba =>
            println(coba)
          }

          for {
            _ <- hasil.recover {
              case e: Throwable =>
                system.log.error(e, "Error on running main process!")

            }
            _ <- system.terminate()
          } yield {
            sys.exit()
          }

//          res.discardEntityBytes()
//          //res.entity
        case Failure(_)   => sys.error("something wrong")
      }
  }
}

//case class ByteString(str: String)