package new1

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.Done
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.LinkParams.title
import akka.http.scaladsl.server.directives.FormFieldDirectives
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import play.api.libs.json.{Format, Json}
// for JSON serialization/deserialization following dependency is required:
// "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.7"
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

import scala.io.StdIn

import scala.concurrent.Future

object File2 extends PlayJsonSupport{

  // needed to run the route
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  // needed for the future map/flatmap in the end and future in fetchItem and saveOrder
  implicit val executionContext = system.dispatcher

  var orders: List[Item] = Nil

  // domain model
  final case class Item(id: Long, createdAt: String,title:String,displayName:String,expiredAt:String)
   object Item{
    implicit val formatter:Format[Item]=Json.format[Item]

  }
  final case class Order(items: List[Item])
  object Order{
    implicit val formatter:Format[Order]=Json.format[Order]
  }









  def main(args: Array[String]) {


    //ubah hardcode dari response yang menjadi suatu method atau case class jadi seperti formField

    val route =
      path("user") {
        concat(
          get {
            parameter("id".as[Long]) {(id)  =>
              complete(Order(List(Item(id,"2015-08-14T10:54:39.000Z","Bandung","bandung","2020-05-20T00:00:00.000+0000"))))
            }
          },
          formField("id".as[Int],"createdAt","title","displayName","expiredAt") {
            (id, createdAt,title,displayName,expiredAt) =>
              complete(Order(List(Item(id, createdAt, title, displayName, expiredAt))))
          }
        )
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    //

  }
}
