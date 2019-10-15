package new2
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import play.api.libs.json.{Format, Json}

object ObjList extends PlayJsonSupport {

  // needed to run the route
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  // domain model


  final case class ObjectList(
                               id: Long,
                               streamType: String,
                               socialMedia: String,
                               content: String,
                               extraContent: String)
  object ObjectList{
    implicit val formatter:Format[ObjectList]=Json.format[ObjectList]
  }

  def main(args: Array[String]) {

    val route =
      concat(
        pathPrefix("projects"/LongNumber/"objects"){ id=>
          get{
            complete(List(ObjectList(id,"keyword","twitter","kpk",""),ObjectList(id,"account","twitter","775899503027220480","ovo_id")))
          }
        },



      )

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    //

  }

}
