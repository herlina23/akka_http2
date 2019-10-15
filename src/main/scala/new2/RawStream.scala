package new2
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

object RawStream extends PlayJsonSupport {
  // needed to run the route
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  // domain model


  final case class ResultFill(
                               id: Long,
                               resource_type: String,
                               specific_resource_type: String,
                               stream_type: String,
                               from_id: String,
                               from_name: String,
                               from_display_picture: String,
                               content: String,
                               final_sentiment: String,
                               timestamp: String,
                               object_content: String)
  object ResultFill{
    implicit val formatter:Format[ResultFill]=Json.format[ResultFill]
  }
  final case class Result(results: List[ResultFill])
  object Result{
    implicit val formatter:Format[Result]=Json.format[Result]
  }
  final case class Paging(next:String)
  object Paging{
    implicit val formatter:Format[Paging]=Json.format[Paging]
  }

  final case class PagingFormat(paging:Option[Paging])
  object PagingFormat{
    implicit val formatter:Format[PagingFormat]=Json.format[PagingFormat]
  }


  def main(args: Array[String]) {

    val route =
      concat(
        pathPrefix("objects"/LongNumber/"rawStream"){ id=>
          get{
            parameter("timestamp_start","timestamp_end","next","count"){(ts,te,nx,ct)=>
              complete(Result(List(ResultFill(id,"social_media","twitter","keyword","123","megabot","https://twitter.com/123/pictures/or/something/like/that?size=large","this is a tweet or toot","neutral","2019-09-22T17:00:00+0000","kpk"),ResultFill(id,"social_media","twitter","keyword","123","megabot","https://twitter.com/123/pictures/or/something/like/that?size=large","this is a tweet or toot","neutral","2019-09-22T17:00:00+0000","kpk"))),PagingFormat(Some(Paging("123092309234098"))))
            }
          }

        },


      )

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    //

  }

}
