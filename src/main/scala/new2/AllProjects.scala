package new2

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import play.api.libs.json.{Format, Json}

object AllProjects extends PlayJsonSupport{

  // needed to run the route
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  // domain model

  final case class Project(id: Int, createdAt: String,title:String,displayName:String,expiredAt:String)
  object Project{
    implicit val formatter:Format[Project]=Json.format[Project]
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

  )

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  //

}
}

