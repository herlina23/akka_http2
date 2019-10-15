package new3
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import akka.http.scaladsl.model.headers.`Set-Cookie`
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import scala.concurrent.duration._

import scala.concurrent.{Await, ExecutionContextExecutor, Future}
import akka.http.scaladsl.unmarshalling.Unmarshal
import play.api.libs.json.{JsObject, Json}
import weatherForecast.Forecast
import wf.Forecast123
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.ExecutionContext.Implicits.{ global => ImplicitsGlobal }
import scala.concurrent.ExecutionContext.global

object Stream5 {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem()
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    //implicit val executionContext: ExecutionContextExecutor = system.dispatcher
    //implicit val executionContext= system.startTime

    //"http://laundry-microservice-users.herokuapp.com/api/v1/users"
    //"http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f8c2705abbe43a56eaca373e8d86070e"

    val source = Uri("http://laundry-microservice-users.herokuapp.com/api/v1/users")

//    val finished = Http().singleRequest(HttpRequest(uri = source)).flatMap { response =>
//      response.entity.dataBytes.runForeach { chunk =>
//        //val str = chunk.utf8String
//        val str = chunk.utf8String.mkString
//        val jsonValue = Json.parse(str)
//        val forecastOpt = jsonValue.asOpt[Forecast]
////        val foreApp = jsonValue.asOpt[Forecast123]
//        println(forecastOpt)
//      }
//    }

    val finished = Http().singleRequest(HttpRequest(uri = source))
      .map { response =>
     response.entity.dataBytes.map{
       dt=>
         val str = dt.utf8String
//         val jsv = Json.parse(str)
//         val frs = jsv.asOpt[Forecast]
        str
     }

    }


    val finalWork = finished.flatMap{
      tr=>tr.runForeach {
        println
      }
    }






  }
}