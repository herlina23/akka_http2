package new1

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import play.api.libs.json.{Format, Json}


object File5 extends PlayJsonSupport{

  // needed to run the route
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  // domain model

  final case class Project(id: Int, createdAt: String,title:String,displayName:String,expiredAt:String)
  object Project{
    implicit val formatter:Format[Project]=Json.format[Project]
  }
  final case class ObjectList(
                               id: Long,
                               streamType: String,
                               socialMedia: String,
                               content: String,
                               extraContent: String)
  object ObjectList{
    implicit val formatter:Format[ObjectList]=Json.format[ObjectList]
  }

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
        path("projects") {
          concat(
            get {
              complete(List(Project(135,"2017-09-22T08:29:49.000Z","AngkasaPura","Angkasa Pura","2020-03-06T11:56:26.000+0000"),Project(175,"2017-10-25T04:36:44.000Z","E-Commerce","E-Commerce","2019-12-31T00:00:00.000+0000")))
            }
          )
        },

        pathPrefix("projects" / LongNumber / "objects"){ id=>


          get{
            complete(List(ObjectList(id,"keyword","twitter","kpk",""),ObjectList(id,"account","twitter","775899503027220480","ovo_id")))
          }
        },

        pathPrefix("objects"/LongNumber/"rawStream"){ id=>
          get{
            parameter("timestamp_start","timestamp_end","next","count"){(ts,te,nx,ct)=>
              complete(Result(List(ResultFill(id,"social_media","twitter","keyword","123","megabot","https://twitter.com/123/pictures/or/something/like/that?size=large","this is a tweet or toot","neutral","2019-09-22T17:00:00+0000","kpk"),ResultFill(id,"social_media","twitter","keyword","123","megabot","https://twitter.com/123/pictures/or/something/like/that?size=large","this is a tweet or toot","neutral","2019-09-22T17:00:00+0000","kpk"))),PagingFormat(Some(Paging("123092309234098"))))
            }
          }
        },

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


