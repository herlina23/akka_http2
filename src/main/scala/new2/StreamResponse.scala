package new2

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import play.api.libs.json.{Format, Json}


object StreamResponse extends PlayJsonSupport{

  // needed to run the route
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  // domain model

  final case class UpdateSentiment(id:String,object_id:String,value:String)
  object UpdateSentiment{
    implicit val formatter:Format[UpdateSentiment]=Json.format[UpdateSentiment]
  }

  final case class UpdateResponse(status:String)
  object UpdateResponse{
    implicit val formatter:Format[UpdateResponse]=Json.format[UpdateResponse]
  }


  def main(args: Array[String]) {
    val route =
      concat(
        path("rawStream") {
          concat(
            post{
              entity(as[List[UpdateSentiment]]) { sentiment =>
                complete(UpdateResponse("OK"))
              }
            },
          )
        },

      )

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    //

  }
}
