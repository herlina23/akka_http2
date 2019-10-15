package new1

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.Done
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model._
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import play.api.libs.json.{Format, Json}


object File4 extends PlayJsonSupport{

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
                               id: Int,
                               streamType: String,
                               socialMedia: String,
                               content: String,
                               extraContent: String)
  object ObjectList{
    implicit val formatter:Format[ObjectList]=Json.format[ObjectList]
  }

  final case class ResultFill(
                               id: String,
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

  final case class StreamOutput(stream: Result,paging: Option[Paging])
  object StreamOutput{
    implicit val formatter:Format[StreamOutput]=Json.format[StreamOutput]
  }

  final case class UpdateStream(id:Int,object_id:String,value:String)
  object UpdateStream{
    implicit val formatter:Format[UpdateStream]=Json.format[UpdateStream]
  }



  // get response di println aja jadi hasilnya hanya kelihatan di console/terminal








  def main(args: Array[String]) {

    val route =
      concat(
        path("project") {
          concat(
            get {
              parameter("id".as[Int],"createdAt","title","displayName","expiredAt") {(id, createdAt, title, displayName, expiredAt)  =>
                println(id+"\n"+ createdAt+"\n"+title+"\n"+displayName+"\n"+expiredAt+"\n"+"\n")
                complete(Project(id, createdAt, title, displayName, expiredAt))
               // complete("project-done")
              }
            },
            formField("id".as[Int],"createdAt","title","displayName","expiredAt") {
              (id, createdAt,title,displayName,expiredAt) =>
                complete(Project(id, createdAt, title, displayName, expiredAt))
            }
          )
        },
        path("object") {
          concat(
            get {
              parameter("id".as[Int],"streamType","socialMedia","content","extraContent") {(id,streamType,socialMedia,content,extraContent)  =>
                println(id+"\n"+streamType+"\n"+socialMedia+"\n"+content+"\n"+extraContent+"\n"+"\n")// semua parameter di print
                complete(ObjectList(id,streamType,socialMedia,content,extraContent))
                //complete("object-done")
              }
            },
            formField("id".as[Int],"streamType","socialMedia","content","extraContent") {
              (id,streamType,socialMedia,content,extraContent) =>
                complete(ObjectList(id,streamType,socialMedia,content,extraContent))
            }
          )
        },

        path("rawstream") {
          concat(
            get {
              parameter("id","resource_type","specific_resource_type","stream_type","from_id","from_name","from_display_picture","content","final_sentiment","timestamp","object_content","next") {
                (id,resource_type,specific_resource_type,stream_type,from_id,from_name,from_display_picture,content,final_sentiment,timestamp,object_content,next)  =>

                println(id+"\n"+resource_type+"\n"+specific_resource_type+"\n"+stream_type+"\n"+from_id+"\n"+from_name+"\n"+from_display_picture+"\n"+content+"\n"+final_sentiment+"\n"+timestamp+"\n"+object_content+"\n"+next+"\n"+"\n")
                  complete(
                    StreamOutput(
                      Result(List(ResultFill(id,resource_type,specific_resource_type,stream_type,from_id,from_name,from_display_picture,content,final_sentiment,timestamp,object_content))),
                      Some(Paging(next))
                    )
                  )

              }
            },

            formField("id","resource_type","specific_resource_type","stream_type","from_id","from_name","from_display_picture","content","final_sentiment","timestamp","object_content","next") {
              (id,resource_type,specific_resource_type,stream_type,from_id,from_name,from_display_picture,content,final_sentiment,timestamp,object_content,next) =>
                complete(
                  StreamOutput(
                    Result(List(ResultFill(id,resource_type,specific_resource_type,stream_type,from_id,from_name,from_display_picture,content,final_sentiment,timestamp,object_content))),
                    Some(Paging(next))
                  )
                )
            }


          )
        },

        path("updateStream") {
          concat(
            get {
              parameter("id".as[Int],"object_id","value") {(id,object_id,value)  =>
                println(id+"\n"+object_id+"\n"+value+"\n"+"\n")// semua parameter di print
                //complete(ObjectList(id,object_id,value))
                complete("update-done")
              }
            },
            formField("id".as[Int],"object_id","value") {
              (id,object_id,value) =>
                complete(UpdateStream(id,object_id,value))
            },
            put{

              parameter("id".as[Int],"object_id","value") {(id,object_id,value)  =>
                println(id+"\n"+object_id+"\n"+value+"\n"+"\n")// semua parameter di print
                //complete(ObjectList(id,object_id,value))
                complete("put-update-done")
              }

            }

          )
        },

      )

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    //

  }
}


